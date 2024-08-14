package org.example.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    //1- Create properties object
    //make it private, so we are limiting access to object
    //static is to make sure its created and loaded before everything else
    private static Properties properties = new Properties();

    static {

        try {

            //2- open file using FileinputStream
            //3- Load the properties object with file
            FileInputStream file = new FileInputStream("configuration.properties");
            properties.load(file);

            //close the file in the memory
            file.close();

        } catch (IOException e) {
            System.err.println("FILE NOT FOUND WITH GIVEN PATH!");
            e.printStackTrace();
        }

    }


    //create a utility method to use the object to read
    public static String getProperty(String keyword) {

        return properties.getProperty(keyword);
    }
}
