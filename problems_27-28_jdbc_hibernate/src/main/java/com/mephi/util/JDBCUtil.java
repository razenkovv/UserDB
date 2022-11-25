package com.mephi.util;

import org.apache.commons.dbcp2.BasicDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class JDBCUtil {
    private static final BasicDataSource dataSource;
    private static final Properties properties;
    private static final String propertiesFile = "C:\\Users\\razen\\java\\java_labs\\UserDB\\problems_27-28_jdbc_hibernate\\src\\DBCP.properties";

    static {
        properties = new Properties();
        dataSource = new BasicDataSource();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);

            String url = properties.getProperty("url");
            if (url != null) dataSource.setUrl(url);
            else throw new IllegalArgumentException("Can't find 'url' in the properties file");

            String username = properties.getProperty("username");
            if (username != null) dataSource.setUsername(username);
            else throw new IllegalArgumentException("Can't find 'username' in the properties file");

            String password = properties.getProperty("password");
            if (password != null) dataSource.setPassword(password);
            else throw new IllegalArgumentException("Can't find 'password' in the properties file");

            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(20);

        } catch (FileNotFoundException e) {
            System.out.println("File " + propertiesFile + " not found");
            e.printStackTrace();
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error while reading from stream");
            e.printStackTrace();
        }
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

    public static Properties getProperties() {
        return properties;
    }
}
