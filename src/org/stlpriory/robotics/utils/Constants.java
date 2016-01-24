package org.stlpriory.robotics.utils;

public class Constants {

	public static final int DRIVE_MAX_OUTPUT = 1;
	public static final double TALON_FEEDFORWARD = 0;
	public static final double TALON_DIFFERENTIAL = 0;
	public static final double TALON_INTEGRATION = 0;
	public static final double TALON_PROPORTION = 0;
	public static final int TALON_CONTROL_MODE = 1;
	public static final double KEEPING_SPEED = .1;
	//v  not sure what this is  v
	public static final int ELEVATOR_SHAFT_HEIGHT = 20;
	public static final int DRIVER_STATION_USB_PORT1 = 1; //Fix this number
	public static final double MAX_ACCELLERATION = .05;
	public static final double MAX_DECELLERATION = .06;
	public static final double ELEVATOR_SPEED = .5;
	public static final double TALON_UNIT = 63; //this number is the conversion factor from feet to 'TALON' units.
	public static final double DEFAULT_ROTATION_SPEED = -.5; // Negative is counterclockwise
	public static final double DEFAULT_STRAFE_SPEED = .3;
	public static final double DEFAULT_FORWARD_SPEED = .7;
	public static final int POTENTIOMETER_SCALE_FACTOR = 360;//range of potentiometers
	public static final int POTENTIOMETER_OFFSET = 0;
}
			