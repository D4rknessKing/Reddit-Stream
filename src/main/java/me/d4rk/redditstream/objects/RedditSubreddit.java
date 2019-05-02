package me.d4rk.redditstream.objects;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.d4rk.redditstream.RedditStream;
import org.json.JSONObject;

public class RedditSubreddit {

    private String name, title, description;
    private boolean nsfw, quarantine;
    private int subscribers;
    private long createdAt, updatedAt;

    public RedditSubreddit(String nam) {
        name = nam;
        updateObject();
    }

    public RedditSubreddit(String nam, String tit, String des, boolean nsf, boolean qua, int sub, long cre) {
        name = nam;
        title = tit;
        description = des;
        nsfw = nsf;
        quarantine = qua;
        subscribers = sub;
        createdAt = cre;
        updatedAt = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public String getName(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return description;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isNsfw(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return nsfw;
    }

    public boolean isQuarantine() {
        return quarantine;
    }

    public boolean isQuarantine(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return quarantine;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public int getSubscribers(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return subscribers;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getCreatedAt(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return createdAt;
    }

    private void checkTime(){
        if(System.currentTimeMillis() >= updatedAt+ RedditStream.delay){
            updateObject();
        }
    }

    private void updateObject(){
        try{
            JSONObject json = Unirest
                    .get("https://www.reddit.com/r/" + name + "/about.json")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                    .asJson().getBody().getObject();
            JSONObject jo = json.getJSONObject("data");
            name = jo.getString("display_name");
            title = jo.getString("title");
            description = jo.getString("public_description");
            nsfw = jo.getBoolean("over_18");
            quarantine = jo.getBoolean("quarantine");
            subscribers = jo.getInt("subscribers");
            createdAt = jo.getLong("created");
        }catch (UnirestException e){
            e.printStackTrace();
        }

    }
}
