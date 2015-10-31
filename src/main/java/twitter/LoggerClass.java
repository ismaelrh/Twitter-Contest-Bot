package twitter;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by ismaro3 on 16/10/15.
 */
public class LoggerClass {

    static Logger logger;
    public Handler fileHandler;
    public Handler consoleHandler;
    Formatter plainText;

    public LoggerClass() throws IOException{
        //instance the logger
        logger = Logger.getLogger(LoggerClass.class.getName());
        //instance the filehandler
        fileHandler = new FileHandler("twitter-log.txt",true);
        consoleHandler = new ConsoleHandler();
        //instance formatter, set formatting, and handler
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
        logger.addHandler(consoleHandler);
    }

    private static Logger getLogger(){
        if(logger == null){
            try {
                new LoggerClass();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
        System.out.println(msg);
    }
}
