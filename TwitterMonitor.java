import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class TwitterMonitor {
	
	private static long lastID = 0;
	
	public static void main (String[] args) {
		while(true) {
			// The factory instance is re-useable and thread safe.
			ConfigurationBuilder cb = new ConfigurationBuilder();
	    	cb.setDebugEnabled(true)
	    	  .setOAuthConsumerKey("************************")
	    	  .setOAuthConsumerSecret("************************")
	    	  .setOAuthAccessToken("************************")
	    	  .setOAuthAccessTokenSecret("************************");
		    Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		    List<Status> statuses = null;
			try {
				statuses = twitter.getUserTimeline();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}			
			
		    System.out.println("Showing user timeline.");
		    for (Status status : statuses) {
		    	if(status.getId() > TwitterMonitor.lastID) {
			    	System.out.println(status.getId());
			    	TwitterMonitor.lastID = status.getId();
			        System.out.println(status.getText());
					try {
						GoogleMail.Send("***********", "***********", "trigger@recipe.ifttt.com", status.getText(), "");
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
		    System.out.println("------------------------");
		    try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
