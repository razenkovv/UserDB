package com.mephi.util;

import java.io.FileInputStream;
import java.io.IOException;

import com.mephi.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.*;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static final Properties properties;
    private static final String propertiesFile = "C:\\Users\\razen\\java\\java_labs\\UserDB\\problems_27-28_jdbc_hibernate\\src\\HibernateC3P0.properties";

    static {
        Configuration configuration = new Configuration();
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(properties);
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Properties getProperties() {
        return properties;
    }
}
