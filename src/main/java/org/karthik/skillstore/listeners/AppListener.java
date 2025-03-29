package org.karthik.skillstore.listeners;

import org.karthik.skillstore.dbutil.Database;
import org.karthik.skillstore.utils.ApplicationPropertiesHelper;
import org.karthik.skillstore.utils.StartupTasks;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class AppListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("AppListener contextInitialized");
        try {
            ApplicationPropertiesHelper.getProperties();
            System.out.println("Loaded properties file");
            System.out.println("Properties: " + Database.properties);
            System.out.println();
            Database.openPool();
            System.out.println("Created connection pool");
            System.out.println();
//            StartupTasks.createDatabaseTables();
//            System.out.println("Created tables in database");


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    public void contextDestroyed(ServletContextEvent sce) {
        Database.closePool();
    }

}
