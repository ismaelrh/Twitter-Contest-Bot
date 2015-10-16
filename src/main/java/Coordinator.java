import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.User;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Class that handles and coordinates all.
 */
public class Coordinator {


    TwitterHandler handler;
    TweetAnalyzer analyzer;
    FollowingList followingList;
    RetweetedList retweetedList;
    String searchQuery;
    int searchLimit;
    int searchsPerInterval;
    int interval;

    /**
     * Initializes a coordinator
     * @param searchQuery query of search
     * @param searchLimit number of tweets retrieved in every search
     * @param interval in seconds, amount of time to wait between searches.
     */
   public Coordinator(String searchQuery, int searchLimit,  int interval){

        LoggerClass.log(Level.INFO,"Initializing coordinator. Search query: " + searchQuery
                + " - Interval: " + searchLimit + " each " + interval + "s.");

       this.searchQuery = searchQuery;
       this.searchLimit = searchLimit;
       this.interval = interval;

        handler =  new TwitterHandler(); //Twitter interaction handler
        analyzer =  new TweetAnalyzer(); //Tweet analyzer

        retweetedList = new RetweetedList("retweeted-tweets.txt"); //Already-retweeted list manager
        retweetedList.populate(); //Populate retweeted list from file

        followingList = new FollowingList(1990); //Already-following users list manager
        ArrayList<Long> following = handler.getFollowing();
        followingList.populate(following); //Populate following list from API


   }


    public void run(){

        int iterationNumber = 0;
        while(true){
            try {
                iterationNumber++;
                LoggerClass.log(Level.INFO,"START ITERATION " + iterationNumber);
                QueryResult result = handler.searchTweets(searchQuery,searchLimit);

                if(result!=null){
                    ArrayList<Status> tweets = new ArrayList<Status>();
                    for(Status status: result.getTweets()){
                        tweets.add(status);

                    }
                    int newFollows = processNewTweets(tweets); //Process

                    LoggerClass.log(Level.INFO, "END ITERATION. New Follows: " + newFollows + ".");
                }

                Thread.sleep(1000 * interval);   //Sleep
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }




    }

    public int processNewTweets(ArrayList<Status> tweets){
        int newFollowedCount = 0;
        for(Status tweet: tweets){

            if(!retweetedList.contains(tweet.getId())){
                //Not already retweeted

                //1- Analyze tweet
                AnalysisResult analyzerResult =  analyzer.analyzeTweet(tweet);

                //2- Check if tweet is valid
                if(analyzerResult!=null){

                    Long toFollow = analyzerResult.getFollowTo(); //Not null
                    boolean userIsContained = followingList.contains(toFollow);

                    //3- If we are already following the user, nothing is done.
                    // If not, we follow him and RT and FAV if we have to.
                    if(!userIsContained){

                        Long toUnfollow = followingList.add(toFollow); //Can be null

                        //Unfollow if limit has been reached
                        if(toUnfollow!=null){
                            handler.doUnfollow(toUnfollow);
                            LoggerClass.log(Level.INFO, "[UNFOLLOW] Max following reached. Unfollowing " + toUnfollow);

                        }

                        String followInfo = "[FOLLOW] @" + toFollow;
                        handler.doFollow(toFollow); //Not contained, we follow

                        newFollowedCount ++;
                        if(analyzerResult.isRt()){
                            //Re-tweet
                            handler.doRT(tweet);
                            retweetedList.add(tweet.getId());
                            followInfo += " - RT";
                        }

                        if(analyzerResult.isFav()){
                            //Fav
                            handler.doFav(tweet);
                            followInfo += " - FAV";
                        }

                        LoggerClass.log(Level.INFO, followInfo);
                        //todo: reply
                    }

                }

            }


        }
return newFollowedCount;

    }


}
