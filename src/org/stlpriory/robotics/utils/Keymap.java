package org.stlpriory.robotics.utils;

public class Keymap {

	/*
     * The bottons on the XBox controller follow this mapping
     * 1:  A
     * 2:  B
     * 3:  X
     * 4:  Y
     * 5:  Left Bumper
     * 6:  Right Bumper
     * 7:  Back
     * 8:  Start
     * 9:  Left thumbstickck
     * 10: Right thumbstick
     *
     * The axis on the controller follow this mapping
     * (all output is between -1 to 1)
     * 1:  Left stick X axis  (left:negative, right:positve)
     * 2:  Left stick Y axis  (up:negative, down:positive)
     * 3:  Triggers           (left:positive, right:negative)
     * 4:  Right stick X axis (left:negative, right:positive)
     * 5:  Right stick Y axis (up:negative, down:positive)
     * 6:  Directional pad
     */
	
	
	
	public static final int SHIFT_SUPER_LOW_BUTTON = 8;
	public static final int GRAB_BUTTON_KEY_MAP = 3; 
	public static final int RELEASE_BUTTON_KEY_MAP = -3; 
	public static final int RIGHT_THUMB_STICK_KEY_MAP = 10; 
	public static final int LEFT_THUMB_STICK_KEY_MAP = 9; 
	public static final int ELEVATOR_UP_BUTTON_KEY_MAP = 4;
	public static final int ELEVATOR_DOWN_BUTTON_KEY_MAP = 1;
	public static final int DRIVETAIN_SHIFT_HIGH_BUTTON_KEY_MAP = 2;
	public static final int DRIVETRAIN_SHIFT_LOW_BUTTON_KEY_MAP = 3;
	public static final int TOGGLE_PULSE = 5; // I just picked this at random. Change if necessary. 
}
