package org.stlpriory.robotics.utils;

/**
 *
 */
public class Debug {

    private static boolean DEBUG_MODE = true;
//    public static File DEBUG_FOLDER = FileUtils.createDirectory("temp");
//    public static File DEBUG_FILE = FileUtils.createFile("debug.out", DEBUG_FOLDER);

    /**
     * If in debug mode, prints specified string without a newline
     *
     * @param s
     */
    public static void print(String s) {
        if (isDebugMode()) {
            System.out.print(s);
            //FileUtils.writeTo(s, DEBUG_FILE);
        }
    }

    /**
     * If in debug mode, prints specified string with a newline
     *
     * @param s
     */
    public static void println(String s) {
        if (isDebugMode()) {
            System.out.println(s);
            //FileUtils.writeTo(s, DEBUG_FILE);
        }
    }

    /**
     * If in debug mode, prints specified string with a newline
     *
     * @param s
     */
    public static void err(String s) {
        if (isDebugMode()) {
            System.err.println(s);
            //FileUtils.writeTo(s, DEBUG_FILE);
        }
    }

    /**
     * Returns true if in debug mode, false otherwise.
     */
    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    /**
     * Set the value for mode
     */
    public static void setDebugMode(boolean mode) {
        DEBUG_MODE = mode;
    }
}