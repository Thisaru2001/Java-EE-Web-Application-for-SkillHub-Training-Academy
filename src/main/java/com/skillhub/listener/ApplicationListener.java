package com.skillhub.listener;

import com.skillhub.service.AppDataStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class ApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        AppDataStore appDataStore = AppDataStore.getInstance();
        context.setAttribute("appDataStore", appDataStore);
        context.setAttribute("appName", "SkillHub Training Academy");
        LOGGER.info("SkillHub Training Academy application started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("SkillHub Training Academy application shut down.");
    }
}
