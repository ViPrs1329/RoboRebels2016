package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.RobotMap;
import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Constants;
import org.stlpriory.robotics.utils.ControllerMap;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Ramper;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TestTankDrivetrain extends Subsystem {
    public CANTalon rightFront;
    public CANTalon rightRear;
    public CANTalon leftFront;
    public CANTalon leftRear;
    Ramper leftRamper;
    Ramper rightRamper;
    RobotDrive drive;
    double leftStickValue;
    double rightStickValue;

    public TestTankDrivetrain() {
        Debug.println("[test drivetrain Subsystem] Instantiating...");
        this.rightFront = new CANTalon(RobotMap.RIGHT_FRONT_TALON_CHANNEL);
        initTalon(this.rightFront);
        this.rightRear = new CANTalon(RobotMap.RIGHT_REAR_TALON_CHANNEL);
        initTalon(this.rightRear);
        this.leftFront = new CANTalon(RobotMap.LEFT_FRONT_TALON_CHANNEL);
        initTalon(this.leftFront);
        this.leftRear = new CANTalon(RobotMap.LEFT_REAR_TALON_CHANNEL);
        initTalon(this.leftRear);
        this.leftRamper = new Ramper();
        this.rightRamper = new Ramper();

//        this.rightRear.changeControlMode(CANTalon.TalonControlMode.Follower);
//        this.rightRear.set(RobotMap.RIGHT_FRONT_TALON_CHANNEL);
//        
//        this.leftRear.changeControlMode(CANTalon.TalonControlMode.Follower);
//        this.leftRear.set(RobotMap.LEFT_FRONT_TALON_CHANNEL);
        
        this.drive = new RobotDrive(this.leftFront, this.rightFront);
        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        this.drive.tankDrive(0, 0);
    }

    public void tankDrive(final double leftValue, final double rightValue) {
        //    	leftStickValue = leftRamper.scale(leftValue);
        //    	rightStickValue = rightRamper.scale(rightValue);
        this.rightStickValue = rightValue;
        this.leftStickValue  = leftValue;
        this.drive.tankDrive(this.leftStickValue, this.rightStickValue);
    }

    public void tankDrive(final Joystick joystick) {
        tankDrive(joystick.getRawAxis(ControllerMap.LEFT_STICK_Y_AXIS), joystick.getRawAxis(ControllerMap.RIGHT_STICK_Y_AXIS));
    }

    public double getRobotSpeed() {
        double leftFrontSpeed  = this.leftFront.getEncVelocity();
        double rightFrontSpeed = this.rightFront.getEncVelocity();
        double leftRearSpeed   = this.leftRear.getEncVelocity();
        double rightRearSpeed  = this.rightRear.getEncVelocity();
        
        leftFrontSpeed  = Math.abs(leftFrontSpeed);
        rightFrontSpeed = Math.abs(rightFrontSpeed);
        leftRearSpeed   = Math.abs(leftRearSpeed);
        rightRearSpeed  = Math.abs(rightRearSpeed);

        double speed = 0;
        if (leftFrontSpeed != 0) {
            speed = leftFrontSpeed;
        }
        if (rightFrontSpeed != 0) {
            speed = rightFrontSpeed;
        }
        if (leftRearSpeed != 0) {
            speed = leftRearSpeed;
        }
        if (rightRearSpeed != 0) {
            speed = rightRearSpeed;
        }

        if (leftFrontSpeed != 0) {
            speed = Math.min(speed, leftFrontSpeed);
        }
        if (rightFrontSpeed != 0) {
            speed = Math.min(speed, rightFrontSpeed);
        }
        if (leftRearSpeed != 0) {
            speed = Math.min(speed, leftRearSpeed);
        }
        if (rightRearSpeed != 0) {
            speed = Math.min(speed, rightRearSpeed);
        }

        return speed;
    }

    @Override
    public void initDefaultCommand() {
        Debug.println("[DriveTrain.initDefaultCommand()] Setting default command to " + DriveWithGamepad.class.getName());
        setDefaultCommand(new DriveWithGamepad());
    }

    private void initTalon(final CANTalon talon) {
        talon.changeControlMode(TalonControlMode.Speed);
        talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        talon.configEncoderCodesPerRev(Constants.COUNTS_PER_REV);
        talon.setProfile(0);
        talon.setP(0);
        talon.setI(0);
        talon.setD(0);
        talon.setF(0);
//        talon.setPID(Constants.TALON_PROPORTION, 
//                     Constants.TALON_INTEGRATION, 
//                     Constants.TALON_DIFFERENTIAL, 
//                     Constants.TALON_FEEDFORWARD, 
//                     0,
//                     0, 0);
        talon.setVoltageRampRate(Constants.VOLTAGE_RAMP_RATE);
        talon.enable();
        talon.reverseSensor(false);
    }

    public void updateStatus() {
//        SmartDashboard.putNumber("Left stick", this.leftStickValue);
//        SmartDashboard.putNumber("Left front speed", this.leftFront.getSpeed());
//        SmartDashboard.putNumber("Left rear speed", this.leftRear.getSpeed());
//        SmartDashboard.putNumber("Right stick", this.rightStickValue);
//        SmartDashboard.putNumber("Right front speed", this.rightFront.getSpeed());
//        SmartDashboard.putNumber("Error", this.rightFront.getError());
//        SmartDashboard.putNumber("Front Right Voltage", this.rightFront.get());
//        SmartDashboard.putNumber("Rear Right Voltage", this.rightRear.get());
//        SmartDashboard.putNumber("Front Left Voltage", this.leftFront.get());
//        SmartDashboard.putNumber("Rear Left Voltage", this.leftRear.get());
//        SmartDashboard.putNumber("Right rear speed", this.rightRear.getSpeed());
    }

//    public void setPID(final double p, final double i, final double d) {
//        CANTalon[] masterTalons = { this.leftFront, this.rightFront };
//        for (CANTalon c : masterTalons) {
//            c.setPID(p, i, d, Constants.TALON_FEEDFORWARD, 0, 0, 0);
//        }
//        System.out.println(this.leftFront.getP());
//    }
}
