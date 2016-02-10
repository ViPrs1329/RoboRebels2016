package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.ControllerMap;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Ramper;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANSpeedController.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestTankDrivetrain extends Subsystem {
	CANTalon rightFront;
    CANTalon rightRear;
    CANTalon leftFront;
    CANTalon leftRear;
    Ramper leftRamper;
    Ramper rightRamper;
    RobotDrive drive;
    double leftStickValue;
    double rightStickValue;

    public TestTankDrivetrain() {
        Debug.println("[test drivetrain Subsystem] Instantiating...");
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
        
        rightRear.changeControlMode(CANTalon.TalonControlMode.Follower);
        rightRear.set(RobotMap.RIGHT_FRONT_TALON_CHANNEL);
        leftRear.changeControlMode(CANTalon.TalonControlMode.Follower);
        leftRear.set(RobotMap.LEFT_FRONT_TALON_CHANNEL);
        drive = new RobotDrive(leftFront, rightFront);
        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        drive.tankDrive(0, 0);
    }

    public void tankDrive(double leftValue, double rightValue)
    {
//    	leftStickValue = leftRamper.scale(leftValue);
//    	rightStickValue = rightRamper.scale(rightValue);
    	rightStickValue = rightValue;
    	leftStickValue = leftValue;
        drive.tankDrive(leftStickValue, rightStickValue);
    }
    public void tankDrive(Joystick joystick)
    { 
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
      	talon.setControlMode(TalonControlMode.Speed.value);
//    	talon.changeControlMode(TalonControlMode.Voltage);//
//    	talon.setVoltageCompensationRampRate(24.0);//
//    	talon.set(4.0);//
    	talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	talon.configEncoderCodesPerRev(Constants.COUNTS_PER_REV);
    	talon.setPID(Constants.TALON_PROPORTION, Constants.TALON_INTEGRATION, Constants.TALON_DIFFERENTIAL, Constants.TALON_FEEDFORWARD, 0,
                0, 0);
    	talon.setVoltageRampRate(Constants.VOLTAGE_RAMP_RATE);
    	talon.enable();
        talon.reverseSensor(true);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Left stick", leftStickValue);
        SmartDashboard.putNumber("Left front speed", leftFront.getSpeed());
        SmartDashboard.putNumber("Left rear speed", leftRear.getSpeed());
        SmartDashboard.putNumber("Right stick", rightStickValue);
        SmartDashboard.putNumber("Right front speed", rightFront.getSpeed());
        SmartDashboard.putNumber("Error", rightFront.getError());
        SmartDashboard.putNumber("Front Right Voltage", rightFront.getOutputVoltage());
        SmartDashboard.putNumber("Rear Right Voltage", rightRear.getOutputVoltage());
        SmartDashboard.putNumber("Front Left Voltage", leftFront.getOutputVoltage());
        SmartDashboard.putNumber("Rear Left Voltage", leftRear.getOutputVoltage());
        SmartDashboard.putNumber("Right rear speed", rightRear.getSpeed());
    }
	public void setPID(double p, double i, double d)
	{
		CANTalon[] masterTalons = {leftFront, rightFront};
		for(CANTalon c : masterTalons)
		{
			c.setPID(p, i, d, Constants.TALON_FEEDFORWARD, 0, 0, 0);
		}
		System.out.println(leftFront.getP());
	}
}
