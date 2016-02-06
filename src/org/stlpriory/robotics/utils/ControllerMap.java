package org.stlpriory.robotics.utils;

public class ControllerMap {
    public static final int A_BUTTON = 1;
    public static final int B_BUTTON = 2;
    public static final int X_BUTTON = 3;
    public static final int Y_BUTTON = 4;
    public static final int RIGHT_BUMPER = 5;
    public static final int LEFT_BUMPER = 6;
    public static final int BACK_BUTTON = 7;
    public static final int START_BUTTON = 8;
    public static final int LEFT_STICK = 9;
    public static final int RIGHT_STICK = 10;
    public static final int LEFT_STICK_X_AXIS = 0;
    public static final int LEFT_TRIGGER = 2;
    public static final int RIGHT_TRIGGER = 3;
    // The triggers are the difference of the right and the left. IE, all left would be 1, all right would be -1, and nothing would be 0.
    public static final int LEFT_STICK_Y_AXIS = 1;
    public static final int RIGHT_STICK_X_AXIS = 4;
    public static final int RIGHT_STICK_Y_AXIS = 5;
    // You can only see left and right on the D-pad, not up and down. Something something interface standard. Brilliant. 
    public static final int D_PAD = 6;
}
