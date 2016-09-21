package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stlpriory.robotics.hardware.MiniCIMMotorSpecs;

public class ShooterSubsystem extends Subsystem {
    public static final int SERVO_CHANNEL = 5;

    public static final int LEFT_SHOOTER_MOTOR_CHANNEL = 0;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_A = 8;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_B = 9;

    public static final int RIGHT_SHOOTER_MOTOR_CHANNEL = 1;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_A = 6;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_B = 7;

    public static final double KEEPING_SPEED = .1;
    public static final double SUCK_SPEED = .4;
    public static final double SHOOT_SPEED = 1;
    public double difference = 0;
    // This is the minimum encoder reading that we should try to shoot at. 
    // Minimum shooting speed is 90% of the max speed for the CIM motor
    public static final double MIN_SHOOTING_SPEED = 0.9 * MiniCIMMotorSpecs.MAX_SPEED_RPM;
    // Allowable error between left and right motors is 5% of the max speed for the CIM motor 
    public static final double MAX_DIFFERENCE = 0.05 * MiniCIMMotorSpecs.MAX_SPEED_RPM;
    // Increment to use when decreasing the throttle setting [-1.0, 1.0]
    public static final double DECREASE_VALUE = .001;

    private static final double KICKER_OUT_POSITION = 1;

    private static final double KICKER_IN_POSITION = 0;


    private final Talon rightShooter;
    private final Talon leftShooter;

    private final Encoder rightEncoder;
    private final Encoder leftEncoder;

    private final Servo kicker;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ShooterSubsystem() {
        this.rightShooter = new Talon(RIGHT_SHOOTER_MOTOR_CHANNEL);
        this.leftShooter = new Talon(LEFT_SHOOTER_MOTOR_CHANNEL);

        this.rightEncoder = new Encoder(RIGHT_MOTOR_ENCODER_CHANNEL_A, RIGHT_MOTOR_ENCODER_CHANNEL_B);
        this.rightEncoder.setDistancePerPulse(1);

        this.leftEncoder = new Encoder(LEFT_MOTOR_ENCODER_CHANNEL_A, LEFT_MOTOR_ENCODER_CHANNEL_B, true);
        this.leftEncoder.setDistancePerPulse(1);

        this.kicker = new Servo(SERVO_CHANNEL);
        retractLoaderArm();
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void extendLoaderArm() {
        System.out.println("extendLoaderArm():  servo.get() = " + this.kicker.get() + ", servo.set(" + KICKER_OUT_POSITION + ")");
        this.kicker.set(KICKER_OUT_POSITION);
    }

    public void retractLoaderArm() {
        System.out.println("retractLoaderArm():  servo.get() = " + this.kicker.get() + ", servo.set(" + KICKER_IN_POSITION + ")");
        this.kicker.set(KICKER_IN_POSITION);
    }

    public boolean isLoaderArmRetracted() {
        System.out.println("isLoaderArmRetracted():  " + this.kicker.get() + " == " + KICKER_IN_POSITION);
        return Math.abs(this.kicker.get() - KICKER_IN_POSITION) < 0.01;
    }

    public boolean isLoaderArmExtended() {
        System.out.println("isLoaderArmExtended():  " + this.kicker.get() + " == " + KICKER_OUT_POSITION);
        return Math.abs(this.kicker.get() - KICKER_OUT_POSITION) < 0.01;
    }

    public void shoot() {
        System.out.println("shoot(): setting speeds to " + SHOOT_SPEED);
        this.leftShooter.set(SHOOT_SPEED);
        this.rightShooter.set(-SHOOT_SPEED);
    }

    public void suck() {
        // The sequence of actions to pick up a ball are: 
        // (1) precondition - assume there is not ball loaded into the ball holder
        //    (do we need a way to detect whether a ball is loaded or not????)
        // (2) precondition - assume the loader arm is retracted
        // (3) start the shooter motors spinning inward
        // (4) stop the shooter motors once we detect a ball is loaded???
        System.out.println("suck(): setting speeds to " + SUCK_SPEED);
        this.setSpeeds(-SUCK_SPEED, -SUCK_SPEED);
    }

    public void keep() {
        this.rightShooter.set(-KEEPING_SPEED);
        this.leftShooter.set(KEEPING_SPEED);
    }

    public void setSpeeds(double right, double left) {
        System.out.println("setSpeeds( " + right + ", " + left + ")");
        this.rightShooter.set(right);
        this.leftShooter.set(-left);
    }

    public void stop() {
        System.out.println("stop(): setting speeds to " + 0);
        this.rightShooter.set(0);
        this.leftShooter.set(0);
//        (new Exception()).printStackTrace();
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right shooter speed", this.rightShooter.getSpeed());
        SmartDashboard.putNumber("Left shooter speed", this.leftShooter.getSpeed());
        SmartDashboard.putNumber("Right encoder speed", getRightSpeed());
        SmartDashboard.putNumber("Left encoder speed", getLeftSpeed());
        SmartDashboard.putNumber("Servo", kicker.get());
        SmartDashboard.putNumber("Difference", difference);
//        System.out.println("UPDATING");
    }

    public void reset() {
        this.rightEncoder.reset();
        this.leftEncoder.reset();
    }

    @SuppressWarnings("deprecation")
    public double getRightSpeed() {
//    	System.out.println(rightEncoder.getRaw());
        return (rightEncoder.getPeriod() * 10000) / 5;
    }

    @SuppressWarnings("deprecation")
    public double getLeftSpeed() {
        return (leftEncoder.getPeriod() * 10000) / 5;
    }

    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

    public double getSpeed() {
        return Math.min(getLeftSpeed(), getRightSpeed());
    }
}
