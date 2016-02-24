package org.stlpriory.robotics.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods to make working with files a bit easier
 * @see java.util.File
 */
public class FileUtils {

    public static File createDirectory(final String directoryName) {
        return new File(directoryName);
    }

    public static File createFile(final String fileName, final File directoryName) {
        return new File(directoryName, fileName);
    }

    /**
     * Writes the string content to the specified file appending to the end
     * of the file if it already exists.
     * 
     * @param file the {@link File} to write to
     * @param content the content to write
     * @throws FileNotFoundException if the specified {@link File} does not exist or is a directory
     * @throws IOException if an error occurs while writing the {@link File}
     */
    public static void writeTo(final File file, final String content) throws FileNotFoundException, IOException {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = (new BufferedWriter(fw));
             PrintWriter out = new PrintWriter(bw)) {

            out.println(content);
        }
    }

    /**
     * Read the string content from the specified file
     * @param file the {@link File} to read from
     * @return the content
     * @throws FileNotFoundException if the specified {@link File} does not exist or is a directory
     * @throws IOException if an error occurs while reading the {@link File}
     */
    public static List<String> readFrom(final File file) throws FileNotFoundException, IOException {
        try (FileReader fr = new FileReader(file); 
             BufferedReader br = new BufferedReader(fr)) {

            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}