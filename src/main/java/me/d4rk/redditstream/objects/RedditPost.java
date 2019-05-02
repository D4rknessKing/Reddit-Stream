package me.d4rk.redditstream.objects;

public class RedditPost {

    private String subreddit,author, title, selftext, link, permalink;
    private boolean spoiler, nsfw, quarantine;

    public RedditPost(String sub, String aut, String tit, String sel, String lin, String per, boolean spo, boolean nsf, boolean qua) {
        subreddit = sub;
        author = aut;
        title = tit;
        selftext = sel;
        link = lin;
        permalink = per;
        spoiler = spo;
        nsfw = nsf;
        quarantine = qua;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSelftext() {
        return selftext;
    }

    public String getLink() {
        return link;
    }

    public String getPermalink() {
        return permalink;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isQuarantine() {
        return quarantine;
    }
}
