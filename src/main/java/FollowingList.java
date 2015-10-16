import twitter4j.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;

/**
 * It maintains a list of people who we are following.
 */

//TODO: Save list in file, not retrieve from API to conserve order.
public class FollowingList {

    private LinkedList<Long> fifo ;
    private int fifoMaxSize;


    public FollowingList(int size){
        fifo = new LinkedList<Long>();
        this.fifoMaxSize = size;
        LoggerClass.log(Level.INFO, "OK. Following list created.");
    }


    /**
     * Populates following list from array.
     */
    public void populate(ArrayList<Long> list){
        for(Long id: list){
            fifo.add(id);
        }

        LoggerClass.log(Level.INFO, "OK. Following list populated from API. Size: " + fifo.size() + ".");

    }

    /**
     * Adds the user to the queue. If limit has been reached, it returns a User who has to be unfollowed.
     * If no limit has been reached, it returns null.
     */
    public Long add(long user){

        Long toBeUnfollowed = null;

        //Only add if username not repeated

        if(!fifo.contains(user)){
            fifo.add(user); //We add the user
            if(fifo.size() >  fifoMaxSize){
                toBeUnfollowed = fifo.pop();
            }
        }


        return toBeUnfollowed;

    }


    /**
     * Returns true only if user is contained in the list.
     */
    public boolean contains(Long user){

        return fifo.contains(user);
    }




}


