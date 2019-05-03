package me.d4rk.redditstream.objects;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.d4rk.redditstream.RedditStream;
import org.json.JSONObject;

public class RedditSubreddit {

    private String name, title, description;
    private Boolean nsfw, quarantine, complete;
    private int subscribers;
    private long createdAt, updatedAt;

    public RedditSubreddit(String name) {
        this.name = name;
        this.complete = false;
    }

    public RedditSubreddit(String name, String title, String description, boolean nsfw,
                           boolean quarantine, int subscribers, long createdAt) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.nsfw = nsfw;
        this.quarantine = quarantine;
        this.subscribers = subscribers;
        this.createdAt = createdAt;
        this.updatedAt = System.currentTimeMillis();

        this.complete = true;
    }

    public String getName() {
        checkComplete();
        return name;
    }

    public String getName(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getName();
    }

    public String getTitle() {
        checkComplete();
        return title;
    }

    public String getTitle(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getTitle();
    }

    public String getDescription() {
        checkComplete();
        return description;
    }

    public String getDescription(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getDescription();
    }

    public boolean isNsfw() {
        checkComplete();
        return nsfw;
    }

    public boolean isNsfw(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return isNsfw();
    }

    public boolean isQuarantine() {
        checkComplete();
        return quarantine;
    }

    public boolean isQuarantine(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return isQuarantine();
    }

    public int getSubscribers() {
        checkComplete();
        return subscribers;
    }

    public int getSubscribers(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getSubscribers();
    }

    public long getCreatedAt() {
        checkComplete();
        return createdAt;
    }

    public long getCreatedAt(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getCreatedAt();
    }

    private void checkComplete() {
        if(!complete) {
            updateObject();
        }
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
