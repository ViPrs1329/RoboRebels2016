package org.stlpriory.robotics.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility methods to make working with .properties files a bit easier
 * @see java.util.Properties
 */
public class PropertiesUtils {
    
    /**
     * Loads the specified properties file into memory
     * 
     * @param file the properties {@link File} to load
     * @return a {@link Properties} based on the specified {@link File}
     * @throws FileNotFoundException if the specified {@link File} does not exist or is a directory
     * @throws IOException if an error occurs while reading the {@link File}
     */
    public static Properties load(final File file) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        try (FileReader reader = new FileReader(file)) {
            props.load(reader);
        }        
        return props;
    }
    
    /**
     * <p>
     * Save a Properties object to a file. The file will be overwritten if it exists.
     * </p>
     * 
     * @param props The properties
     * @param fileName The file name
     * @throws FileNotFoundException if the specified {@link File} does not exist or is a directory
     * @throws IOException if an error occurs while writing the {@link File}
     */
    public static void save(final Properties props, final String fileName) throws FileNotFoundException, IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            props.store(writer, null);
        }        
    }

    /**
     * Combines multiple {@link Properties} into a single {@link Properties} without modifying 
     * any of the original {@link Properties}. If the same key is defined in two {@link Properties}, 
     * it will be assigned the value appearing in the latest {@link Properties} in the list.
     * 
     * @param props the {@link Properties} to concatenate
     * @return a single {@link Properties} containing all of the key value pairs
     */
    public static Properties concat(final Properties... props) {
        Properties out = new Properties();
        for (Properties prop : props) {
            if (prop != null) {
                prop.forEach(out::put);
            }
        }
        return out;
    }
}
