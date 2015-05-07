package com.sveil.other.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author richard
 */
public class LoadParam implements ServletContextListener{ 
    Logger logger = LoggerFactory.getLogger(LoadParam.class);

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
    }

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        arg0.getServletContext().setAttribute("contextPath", arg0.getServletContext().getContextPath());
    }
}
