package com.ncjavaedu.ediary.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Created by abogdanov on 05.05.17.
 */
public class DriverUnregisterListener implements ServletContextListener {
    private Logger logger;

    @Override
    public void contextInitialized(ServletContextEvent event){

    }

    @Override
    public void contextDestroyed(ServletContextEvent event){
        logger = LoggerFactory.getLogger(getClass());
        deregisterDrivers(getDrivers());
    }

    Enumeration<Driver> getDrivers(){
        return DriverManager.getDrivers();
    }

    void deregisterDrivers(Enumeration<Driver> drivers){
        while(drivers.hasMoreElements()){
            deregisterDriver(drivers.nextElement());
        }
    }

    void deregisterDriver(Driver driver){
        try{
            DriverManager.deregisterDriver(driver);
            logger.info("Deregistering JDBC driver: {}", driver);
        } catch (SQLException e){
            logger.warn("Error deregistering JDBC driver: {}. Root cause : ",driver, e);
        }
    }
}
