# Twitter-Contest-Bot
A Twitter bot that search for "Follow and Retweet to WIN..." tweets and participates in such contests.
Inspired by http://qz.com/476914/i-built-a-twitter-bot-that-entered-and-won-1000-online-contests-for-me/

Features:
* Search for tweets with the desired words and a minimun number of re-tweets.
* Ability to Retweet, Follow the creator and favorite the tweet depending on the words of the tweet.
* Smart tweet analysis: easily change when a tweet is considered "useful".
* (Future) Ability to reply the tweet creator if he asks to.
* Maintains a follower FIFO in order to subscribe to the maximun number of possible contests (2000).
* Persistent state: restart the program and keep operating as you didn't.
* Customize search frequency in order to avoid API limits.
* Modular design - Easy to change how tweets are analized, how are retrieved...
* Logs events to file



