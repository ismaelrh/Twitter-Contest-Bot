import twitter4j.Status;

import java.util.logging.Level;

/**
 * This class analyzes individual tweets.
 */
public class TweetAnalyzer {


    public int minimumRT = 30; //Minimum RT required to be considered
    public String lang = "es"; //Language to be considered


    public TweetAnalyzer(){
        LoggerClass.log(Level.INFO,"OK. Tweet Analyzer initialized");
    }

    /**
     * If is a valid "contest" tweet, returns a "AnalysisResult" object.
     * Otherwise, returns null.
     * A tweet is valid if:
     * - It is in the required language
     * - It has at least "minimumRT" retweets.
     * - Is an original tweet, not a retweet.
     */
    public AnalysisResult analyzeTweet(Status status){

        int retweetCount = status.getRetweetCount(); //RT count
        String lang = status.getLang(); //Language

        String text = status.getText();
        String lower_text = text.toLowerCase();


        if(retweetCount >= minimumRT && !status.isRetweet()){
            //Is valid

            AnalysisResult result = new AnalysisResult();

            //Indicate to follow the user
            result.setFollowTo(status.getUser().getId());

            //Indicate to RT the Tweet
            result.setRt(true);

            //Indicate to FAV the Tweet if it contains "fav"
            if(lower_text.contains("fav")){
                result.setFav(true);
            }

            //Indicate to reply or not to the tweet
            result.setTweet(false);

            return result;

        }
        else{
            //Tweet is not valiD
            return null;
        }
    }





}
