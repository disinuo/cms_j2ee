package listener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Application Lifecycle Listener implementation class CounterListener
 *
 */
@WebListener
public class LoginCounterListener implements  
ServletContextListener, ServletContextAttributeListener,HttpSessionListener {
	int counter;
	String counterFilePath= "/Users/disinuo/git/dsn_j2ee/web/counter_login.txt";
    /**
     * Default constructor. 
     */
    public LoginCounterListener() {}

	@Override
	public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {

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
    		System.out.println("login listener Reading Start"); 		
    		BufferedReader reader = new BufferedReader(new FileReader(counterFilePath));
    		counter = Integer.parseInt( reader.readLine() );
    		reader.close();
    	}catch (Exception e) {
    		System.out.println("contextInitialized error!!");
    		System.out.println(e);
    	}
    	ServletContext servletContext= cse.getServletContext();
    	servletContext.setAttribute("counter_login", Integer.toString(counter));
    	System.out.println("listener initialized");
    }

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	synchronized void writeCounter(ServletContextAttributeEvent scae) {
//    	ServletContext servletContext= scae.getServletContext();
//    	counter = Integer.parseInt((String) servletContext.getAttribute("counter_login"));
//    	try {
//    		BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
//    		writer.write(Integer.toString(counter)+"\n");
//    		writer.close();
//    		System.out.println("Writing");
//    	}catch (Exception e) {
//    		System.out.println(e.toString());
//    	}
    }

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

	}
}
