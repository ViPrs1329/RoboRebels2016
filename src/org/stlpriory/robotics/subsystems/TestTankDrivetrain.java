package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.ControllerMap;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Ramper;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestTankDrivetrain extends Subsystem {
    private static final String LEFT_FRONT_SPEED = "Left Front speed";
	private static final String LEFT_REAR_SPEED = "left rear";
	private static final String RIGHT_REAR_SPEED = "right rear";
	private static final String RIGHT_FRONT_SPEED = "right front";
	CANTalon rightFront;
    CANTalon rightRear;
    CANTalon leftFront;
    CANTalon leftRear;
    Ramper leftRamper;
    Ramper rightRamper;
    RobotDrive drive;

    public TestTankDrivetrain() {
        Debug.println("[test drivetrain Subsystem] Instantiating...");
        // Next two lines for testing encoder/talon srx
        rightFront = new CANTalon(RobotMap.RIGHT_FRONT_TALON_CHANNEL);
        initTalon(this.rightFront);
        rightRear = new CANTalon(RobotMap.RIGHT_REAR_TALON_CHANNEL);
        initTalon(this.rightRear);
        leftFront = new CANTalon(RobotMap.LEFT_FRONT_TALON_CHANNEL);
        initTalon(this.leftFront);
        leftRear = new CANTalon(RobotMap.LEFT_REAR_TALON_CHANNEL);
        initTalon(this.leftRear);
        leftRamper = new Ramper();
        rightRamper = new Ramper();
        drive = new RobotDrive(leftFront, leftRear, rightFront, rightRear);
        drive.setInvertedMotor(MotorType.kFrontLeft, true);
        drive.setInvertedMotor(MotorType.kFrontRight, true);
        drive.setInvertedMotor(MotorType.kRearLeft, true);
        drive.setInvertedMotor(MotorType.kRearRight, true);
        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        drive.tankDrive(0, 0);
    }

    public void tankDrive(double leftValue, double rightValue)
    {    	
        drive.tankDrive(leftRamper.scale(leftValue), rightRamper.scale(rightValue));
        SmartDashboard.putNumber(LEFT_FRONT_SPEED, leftFront.getSpeed());
        SmartDashboard.putNumber(LEFT_REAR_SPEED, leftRear.getSpeed());
        SmartDashboard.putNumber(RIGHT_FRONT_SPEED, rightFront.getSpeed());
        SmartDashboard.putNumber(RIGHT_REAR_SPEED, rightRear.getSpeed());
    }
    public void tankDrive(Joystick joystick){
    
        // This should be the left stick y axis and the right stick y axis. 
        tankDrive(joystick.getRawAxis(ControllerMap.LEFT_STICK_Y_AXIS), joystick.getRawAxis(ControllerMap.RIGHT_STICK_Y_AXIS));
    }
    public double getRobotSpeed() {
    	double leftFrontSpeed = leftFront.getEncVelocity();
    	double rightFrontSpeed = rightFront.getEncVelocity();
    	double leftRearSpeed = leftRear.getEncVelocity();
    	double rightRearSpeed = rightRear.getEncVelocity();
    	leftFrontSpeed = Math.abs(leftFrontSpeed);
    	rightFrontSpeed = Math.abs(rightFrontSpeed);
    	leftRearSpeed = Math.abs(leftRearSpeed);
    	rightRearSpeed = Math.abs(rightRearSpeed);
    	
    	double speed = 0;
    	if (leftFrontSpeed != 0) speed = leftFrontSpeed;
    	if (rightFrontSpeed != 0) speed = rightFrontSpeed;
    	if (leftRearSpeed != 0) speed = leftRearSpeed;
    	if (rightRearSpeed != 0) speed = rightRearSpeed;
    	
    	if (leftFrontSpeed != 0) speed = Math.min(speed,leftFrontSpeed);
    	if (rightFrontSpeed != 0) speed = Math.min(speed, rightFrontSpeed);
    	if (leftRearSpeed != 0) speed = Math.min(speed, leftRearSpeed);
    	if (rightRearSpeed != 0) speed = Math.min(speed, rightRearSpeed);
    	
    	return speed;
    }

    public void initDefaultCommand() {
        Debug.println("[DriveTrain.initDefaultCommand()] Setting default command to " 
                + DriveWithGamepad.class.getName());
        setDefaultCommand(new DriveWithGamepad());
    }
    private void initTalon(final CANTalon talon) {
        talon.setPID(Constants.TALON_PROPORTION, Constants.TALON_INTEGRATION, Constants.TALON_DIFFERENTIAL, Constants.TALON_FEEDFORWARD, 0,
                     0, 0);
        talon.reverseSensor(true);
    }

}
