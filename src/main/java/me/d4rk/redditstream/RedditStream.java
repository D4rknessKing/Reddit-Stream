package me.d4rk.redditstream;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.d4rk.redditstream.objects.RedditPost;
import me.d4rk.redditstream.objects.RedditSubreddit;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RedditStream {

    public static int delay = 5000;
    private Thread streamThread;
    private RedditListener listener = null;
    private RedditWatcher watcher = null;
    private List<String> oldEntries = new ArrayList<>();

    public RedditStream(RedditListener redditListener) {
        listener = redditListener;
        initializeRedditStream();
    }

    public RedditStream(RedditListener redditListener, int pingDelay) {
        listener = redditListener;
        delay = pingDelay;
        initializeRedditStream();
    }

    public void interrupt(){
        interruptRedditStream();
    }

    public void setWatcher(RedditWatcher redditWatcher){
        watcher = redditWatcher;
    }

    private void handleRedditResponses(RedditWatcher list) throws UnirestException {
        if(list.getTotalCount() <= 0){
            return;
        }

        JSONObject json = Unirest
                .get("https://www.reddit.com/r/all/new/.json?limit=100")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                .asJson().getBody().getObject();

        for (Object o : json.getJSONObject("data").getJSONArray("children")) {
            JSONObject jo = ((JSONObject) o).getJSONObject("data");
            String permalink = "https://www.reddit.com" + jo.getString("permalink");
            if(!oldEntries.contains(permalink)){
                RedditPost post = new RedditPost(
                        new RedditSubreddit(jo.getString("subreddit")),
                        jo.getString("author"),
                        jo.getString("title"),
                        jo.getString("selftext"),
                        jo.getString("url"),
                        permalink,
                        jo.getInt("score"),
                        jo.getInt("ups"),
                        jo.getInt("downs"),
                        jo.getBoolean("over_18"),
                        jo.getBoolean("spoiler"),
                        jo.getBoolean("quarantine"),
                        jo.getLong("created")
                );
                if(watcher != null){
                    for (String sub : watcher.subreddits) {
                        if(sub.equalsIgnoreCase(post.getSubreddit().getName())){
                            listener.onPostCreated(post);
                            break;
                        }
                    }
                }else{
                    listener.onPostCreated(post);
                }
            }
            oldEntries.add(permalink);
        }


    }

    private void initializeRedditStream(){
        streamThread = new Thread(() -> {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try{
                        handleRedditResponses(watcher);
                    }catch(UnirestException e){
                        e.printStackTrace();
                    }
                }
            }, delay, delay);
        });
        streamThread.run();
    }

    private void interruptRedditStream() {
        streamThread.interrupt();
    }

    private Thread.State getRedditStreamThreadState() {
        return streamThread.getState();
    }



}
