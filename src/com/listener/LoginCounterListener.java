package com.listener;

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
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class CounterListener
 *
 */
@WebListener
public class LoginCounterListener implements  
ServletContextListener, ServletContextAttributeListener,HttpSessionListener {
	int counter;
	String counterFilePath= "/Users/disinuo/git/j2ee小作业/j2ee4/WebContent/counter_login.txt";
    /**
     * Default constructor. 
     */
    public LoginCounterListener() {}
    
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
    
    synchronized void writeCounter(ServletContextAttributeEvent scae) {
    	ServletContext servletContext= scae.getServletContext();
    	counter = Integer.parseInt((String) servletContext.getAttribute("counter_login"));
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter(counterFilePath));
    		writer.write(Integer.toString(counter)+"\n");
    		writer.close();
    		System.out.println("Writing");
    	}catch (Exception e) {
    		System.out.println(e.toString());
    	}
    }
     
}
