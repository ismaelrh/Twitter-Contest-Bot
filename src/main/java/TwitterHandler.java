import twitter4j.*;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * This class contains methods to comunicate with Twitter.
 */
public class TwitterHandler {

    Twitter twitter; //Twitter singleton

    public TwitterHandler(){
        twitter  =  TwitterFactory.getSingleton();
        LoggerClass.log(Level.INFO,"OK. Twitter Handler initialized");
    }


    /**
     * It returns MAX(available_tweets,200,limit) tweets from the
     * search of the specified query.
     * In case of error, returns null.
     */
    public QueryResult searchTweets(String queryTerm, int limit){

        try{
            Query query = new Query(queryTerm);
            query.setCount(limit);
            QueryResult result = twitter.search(query);
            LoggerClass.log(Level.INFO, "OK. Searching tweets for term " + queryTerm + " and limit " + limit + ". " + result.getCount() + " results.");
            return result;
        }
        catch(Exception ex){
            LoggerClass.log(Level.SEVERE, "ERROR searching tweets for term " + queryTerm + " and limit " + limit + ".");
            System.err.println("[ERROR] Can't search tweets");
            ex.printStackTrace();
            return null;
        }

    }

    /*
    public void getLimit(){
        try{
            System.out.println(twitter.getRateLimitStatus().);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }*/


    /**
     * Returns a list of id of the users the configured account follows.
     * Returns null in case of error.
     */
    public ArrayList<Long> getFollowing(){

        ArrayList<Long> following = new ArrayList<Long>();
        try{
            long lCursor = -1;
            IDs friendsIDs = twitter.getFriendsIDs(twitter.getId(),lCursor);
            do
            {
                for (long i : friendsIDs.getIDs())
                {
                    following.add(i);
                }
            }while(friendsIDs.hasNext());

            return following;
        }
        catch(Exception ex){
            System.err.println("[ERROR] Can't get following users.");
            ex.printStackTrace();
            return null;
        }
    }

    /*
    * Follows the specified user, returning true in case of success.
    * If error, returns false.
     */
    public boolean doFollow(long user){

        try{
            twitter.friendsFollowers().createFriendship(user);
            return true;
        }
        catch(Exception ex){
            System.err.println("[ERROR] Can't follow " + user);
            ex.printStackTrace();
            return false;
        }
    }


    /*
    * Follows the specified user, returning true in case of success.
    * If error, returns false.
     */
    public boolean doUnfollow(long user){

        try{
            twitter.friendsFollowers().destroyFriendship(user);
            return true;
        }
        catch(Exception ex){
            System.err.println("[ERROR] Can't unfollow " + user);
            ex.printStackTrace();
            return false;
        }
    }


    /*
    * Re-tweets the specified tweet, returning true in case of success.
    * If error, returns false.
     */
    public boolean doRT(Status tweet){
        try{
            twitter.retweetStatus(tweet.getId());

            return true;
        }
        catch(Exception ex){
            System.err.println("[ERROR] Can't retweet " + tweet.getId());
            ex.printStackTrace();
            return false;
        }

    }

    /*
    * Favorites the specified tweet, returning true in case of success.
    * If error, returns false.
     */
    public boolean doFav(Status tweet){
        try{
            twitter.createFavorite(tweet.getId());
            return true;
        }
        catch(Exception ex){
            System.err.println("[ERROR] Can't favorite " + tweet.getId());
            ex.printStackTrace();
            return false;
        }

    }



}
