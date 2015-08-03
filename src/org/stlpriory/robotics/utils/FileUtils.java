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

public class FileUtils {
    
    public static File createDirectory(final String directoryName) {
        return new File(directoryName);
    }

    public static File createFile(final String fileName, final File directoryName) {
        return new File(directoryName, fileName);
    }

    public static void writeTo(final String writeTo, final File fileName) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = (new BufferedWriter(fw));
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(writeTo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFrom(final File fileName) {
        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
            
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}