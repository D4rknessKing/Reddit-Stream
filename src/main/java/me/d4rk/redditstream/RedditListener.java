package me.d4rk.redditstream;

import me.d4rk.redditstream.objects.RedditComment;
import me.d4rk.redditstream.objects.RedditPost;

public interface RedditListener {

    void onPostCreated(RedditPost post);
    void onCommentCreated(RedditComment comment);

}
