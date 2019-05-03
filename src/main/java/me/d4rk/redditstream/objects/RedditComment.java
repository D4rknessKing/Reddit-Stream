package me.d4rk.redditstream.objects;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.d4rk.redditstream.RedditStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class RedditComment {

    private RedditPost post;
    private RedditSubreddit subreddit;
    private String author, body, link, permalink;
    private boolean spoiler, nsfw, quarantine;
    private int score, upvotes, downvotes;
    private long createdAt, updatedAt;

    public RedditComment(RedditPost post, RedditSubreddit subreddit, String author, String body, String permalink,
                         int score, int upvotes, int downvotes, boolean spoiler, boolean nsfw, boolean quarantine,
                         long createdAt) {
        this.post = post;
        this.subreddit = subreddit;
        this.author = author;
        this.body = body;
        this.permalink = permalink;
        this.score = score;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.spoiler = spoiler;
        this.nsfw = nsfw;
        this.quarantine = quarantine;
        this.createdAt = createdAt;
        this.updatedAt = System.currentTimeMillis();
    }

    public RedditPost getPost() {
        return post;
    }

    public RedditPost getPost(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return post;
    }

    public RedditSubreddit getSubreddit() {
        return subreddit;
    }

    public RedditSubreddit getSubreddit(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthor(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getBody(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return body;
    }


    public String getLink() {
        return link;
    }

    public String getLink(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return link;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getPermalink(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return permalink;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public boolean isSpoiler(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return spoiler;
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

    public int getScore() {
        return score;
    }

    public int getScore(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return score;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getUpvotes(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public int getDownvotes(boolean updatePost) {
        if(updatePost){
            checkTime();
        }
        return downvotes;
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
            body = jo.getString("body");
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
