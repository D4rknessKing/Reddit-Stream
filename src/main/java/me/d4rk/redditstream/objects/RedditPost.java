package me.d4rk.redditstream.objects;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.d4rk.redditstream.RedditStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class RedditPost  {

    private RedditSubreddit subreddit;
    private String author, title, selftext, link, permalink;
    private boolean spoiler, nsfw, quarantine, complete;
    private int score, upvotes, downvotes;
    private long createdAt, updatedAt;

    public RedditPost(String permalink){
        this.permalink = permalink;
        this.complete = false;
    }

    public RedditPost(RedditSubreddit subreddit, String author, String title, String selftext,
                      String link, String permalink, int score, int upvotes, int downvotes,
                      boolean spoiler, boolean nsfw, boolean quarantine, long createdAt) {
        this.subreddit = subreddit;
        this.author = author;
        this.title = title;
        this.selftext = selftext;
        this.link = link;
        this.permalink = permalink;
        this.score = score;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.spoiler = spoiler;
        this.nsfw = nsfw;
        this.quarantine = quarantine;
        this.createdAt = createdAt;
        this.updatedAt = System.currentTimeMillis();

        this.complete = true;
    }

    public RedditSubreddit getSubreddit() {
        checkComplete();
        return subreddit;
    }

    public RedditSubreddit getSubreddit(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getSubreddit();
    }

    public String getAuthor() {
        checkComplete();
        return author;
    }

    public String getAuthor(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getAuthor();
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

    public String getSelftext() {
        checkComplete();
        return selftext;
    }

    public String getSelftext(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getSelftext();
    }

    public String getLink() {
        checkComplete();
        return link;
    }

    public String getLink(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getLink();
    }

    public String getPermalink() {
        checkComplete();
        return permalink;
    }

    public String getPermalink(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getPermalink();
    }

    public boolean isSpoiler() {
        checkComplete();
        return spoiler;
    }

    public boolean isSpoiler(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return isSpoiler();
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

    public int getScore() {
        checkComplete();
        return score;
    }

    public int getScore(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getScore();
    }

    public int getUpvotes() {
        checkComplete();
        return upvotes;
    }

    public int getUpvotes(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getUpvotes();
    }

    public int getDownvotes() {
        checkComplete();
        return downvotes;
    }

    public int getDownvotes(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return getDownvotes();
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
        if(System.currentTimeMillis() >= updatedAt+RedditStream.delay){
            updateObject();
        }
    }

    private void updateObject(){
        try{
            JSONArray json = Unirest
                    .get("https://www.reddit.com" + permalink)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                    .asJson().getBody().getArray();
            JSONObject jo = json.getJSONObject(0).getJSONObject("data").getJSONArray("children").getJSONObject(0).getJSONObject("data");
            author = jo.getString("author");
            title = jo.getString("title");
            selftext = jo.getString("selftext");
            link = jo.getString("url");
            score = jo.getInt("score");
            upvotes = jo.getInt("ups");
            downvotes = jo.getInt("downs");
            nsfw = jo.getBoolean("over_18");
            spoiler = jo.getBoolean("spoiler");
            quarantine = jo.getBoolean("quarantine");
            createdAt = jo.getLong("created");
        }catch (UnirestException e){
            e.printStackTrace();
        }

    }

}
