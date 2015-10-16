/**
 * This class represents a result of an Analysis performed by "TweetAnalyzer" class,
 * containing information about whom to follow, if fav has to be done, if we have to reply, etc.
 */
public class AnalysisResult {


    public long followTo; //Id of the user to follow.
    public boolean rt = false; //True if tweet has to be RT
    public boolean fav = false; //True if tweet has to be FAV
    public boolean tweet = false; //True if tweet has to be replied
    public String tweetContains = ""; //Text that the reply has to contain (Example a #hashtag)

    public AnalysisResult(){

    };


    public AnalysisResult(long followTo, boolean rt, boolean fav, boolean tweet, String tweetContains) {
        this.followTo = followTo;
        this.rt = rt;
        this.fav = fav;
        this.tweet = tweet;
        this.tweetContains = tweetContains;
    }

    public long getFollowTo() {
        return followTo;
    }

    public void setFollowTo(long followTo) {
        this.followTo = followTo;
    }

    public boolean isRt() {
        return rt;
    }

    public void setRt(boolean rt) {
        this.rt = rt;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isTweet() {
        return tweet;
    }

    public void setTweet(boolean tweet) {
        this.tweet = tweet;
    }

    public String getTweetContains() {
        return tweetContains;
    }

    public void setTweetContains(String tweetContains) {
        this.tweetContains = tweetContains;
    }



}
