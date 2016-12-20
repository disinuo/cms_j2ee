package listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class CounterListener
 *
 */
@WebListener
public class CounterListener implements  
ServletContextListener, ServletContextAttributeListener,HttpSessionListener {
	int counter_visitor;
	int counter_login;
	int type_num=2;//the type of user
	String counterFilePath= "/Users/disinuo/git/cms_j2ee/WebContent/counter.txt";
    /**
     * Default constructor. 
     */
    public CounterListener() {
        // TODO Auto-generated constructor stub
    }
    public void attributeReplaced(ServletContextAttributeEvent scae) {
    	System.out.println("ServletContextattribute replaced");
    	writeCounter(scae);
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent cse) {
    	try {
    		System.out.println("Reading Start"); 		
    		BufferedReader reader = new BufferedReader(new FileReader(counterFilePath));
    		
    		counter_login = Integer.parseInt( reader.readLine() ); 
    		counter_visitor = Integer.parseInt( reader.readLine() );
    		reader.close();
    	}catch (Exception e) {
    		System.out.println("contextInitialized error!!");
    		System.out.println(e);
    	}
    	ServletContext servletContext= cse.getServletContext();
    	servletContext.setAttribute("counter_visitor", Integer.toString(counter_visitor));
    	servletContext.setAttribute("counter_login", Integer.toString(counter_login));
    	System.out.println("listener initialized");
    }
    
    synchronized void writeCounter(ServletContextAttributeEvent scae) {
    	ServletContext servletContext= scae.getServletContext();
    	counter_login = Integer.parseInt((String) servletContext.getAttribute("counter_login"));
    	counter_visitor = Integer.parseInt((String) servletContext.getAttribute("counter_visitor"));
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
    		writer.write(Integer.toString(counter_login)+"\n");
    		writer.write(Integer.toString(counter_visitor)+"\n");
    		writer.close();
    		System.out.println("Writing");
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }
     
}
