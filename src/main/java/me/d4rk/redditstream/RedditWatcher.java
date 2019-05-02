package me.d4rk.redditstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedditWatcher {

    List<String> subreddits = new ArrayList<>();

    public RedditWatcher(String watchedSubreddit){
        subreddits.add(watchedSubreddit);
    }

    public RedditWatcher(List<String> watchedSubreddits) {
        subreddits.addAll(watchedSubreddits);
    }

    public RedditWatcher(String[] watchedSubreddits) {
        subreddits.addAll(Arrays.asList(watchedSubreddits));
    }

    public int getTotalCount(){
        return +subreddits.size();
    }
}
