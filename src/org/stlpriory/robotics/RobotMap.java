package org.stlpriory.robotics;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
	
	public static final int LEFT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL = 1;
	public static final int RIGHT_FRONT_WHEEL_JAGUAR_PWM_CHANNEL = 2;
	public static final int LEFT_REAR_WHEEL_JAGUAR_PWM_CHANNEL = 3;
	public static final int RIGHT_REAR_WHEEL_JAGUAR_PWM_CHANNEL = 4;
	public static final int LEFT_FRONT_CAN_TALON_CHANNEL = 1;
	public static final int RIGHT_FRONT_CAN_TALON_CHANNEL = 2;
	public static final int LEFT_REAR_CAN_TALON_CHANNEL = 3;
	public static final int RIGHT_REAR_CAN_TALON_CHANNEL = 4;

        // 2016 Testing
	public static final int LEFT_FRONT_TALON_CHANNEL = 1;
	public static final int RIGHT_FRONT_TALON_CHANNEL = 3;
	public static final int RIGHT_REAR_TALON_CHANNEL = 2;
	public static final int LEFT_REAR_TALON_CHANNEL = 0;
}
