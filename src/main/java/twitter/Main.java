package twitter;

/**
 * twitter.Main class used to run a twitter.Coordinator.
 */
public class Main {

    public static int minimumRetweets = 20;

    public static void main(String[] args){

        try{

           Coordinator c = new Coordinator("sorteo rt min_retweets:" + minimumRetweets, 30,60*60);
            c.run();
            System.out.println("Done iteration");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
