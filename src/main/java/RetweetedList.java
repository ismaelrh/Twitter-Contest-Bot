import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Manages a list of "retweeted" tweets, used to prevent retweeting already retweeted tweets.
 *
 */
public class RetweetedList {

    HashSet<Long> tweets;
    PrintWriter writer;
    String filename;
    public RetweetedList(String filename){

        tweets = new HashSet<Long>();
        this.filename = filename;

       try{
           writer = new PrintWriter(new FileOutputStream(new File(this.filename),true));
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
        LoggerClass.log(Level.INFO,"OK. Retweeted list manager created.");

    }

    /**
     * Reads tweet id's from file and loads it into memory.
     */
    public void populate(){
        //Loads from file
        try{
            Scanner file = new Scanner(new File(this.filename));
            while(file.hasNextLong()){
                long current = file.nextLong();
                tweets.add(current);
            }
            LoggerClass.log(Level.INFO, "OK. Retweeted list populated.");
        }
        catch(Exception ex){
            LoggerClass.log(Level.SEVERE,"ERROR while loading retweeted list from file.");
            LoggerClass.log(Level.SEVERE, ex.getMessage());
        }

    }

    /**
     * Returns true if the given id is already retweeted according to saved data.
     */
    public boolean contains(long id){
        return tweets.contains(id);
    }

    /**
     * Adds an id to the hashset.
     */
    public void add(long id){
        if(!tweets.contains(id)){
            tweets.add(id);
            writer.append(""+id +"\n");
            writer.flush();
        }
    }

    /**
     * Flush and closes the file.
     */
    public void close(){
        writer.flush();
        writer.close();
    }





}
