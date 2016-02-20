package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.hardware.CIMcoderSpecs;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {
    public static final int SERVO_CHANNEL = 9;

    public static final int LEFT_SHOOTER_MOTOR_CHANNEL  = 3;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_A = 6;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_B = 7;
    
    public static final int RIGHT_SHOOTER_MOTOR_CHANNEL = 0;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_A = 8;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_B = 9;
    
    public static final double KEEPING_SPEED = .1;
    public static final double SUCK_SPEED = .5;
    public static final double SHOOT_SPEED = 1;
    
    // This is the minimum encoder reading that we should try to shoot at. 
    public static final double MIN_SHOOTING_SPEED = 35000; 
    public static final int MAX_DIFFERENCE = 10;
    public static final double DECREASE_VALUE = .01;

	private static final double KICKER_OUT_POSITION = 0;

	private static final double KICKER_IN_POSITION = 180;
	

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
        this.leftShooter  = new Talon(LEFT_SHOOTER_MOTOR_CHANNEL);
        
        this.rightEncoder = new Encoder(RIGHT_MOTOR_ENCODER_CHANNEL_A, RIGHT_MOTOR_ENCODER_CHANNEL_B);
        this.rightEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);

        this.leftEncoder  = new Encoder(LEFT_MOTOR_ENCODER_CHANNEL_A, LEFT_MOTOR_ENCODER_CHANNEL_B, true);
        this.leftEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);
        
        this.kicker  = new Servo(SERVO_CHANNEL);
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void extendLoaderArm() {
        this.kicker.set(KICKER_OUT_POSITION);
    }    
    public void retractLoaderArm() {
        this.kicker.set(KICKER_IN_POSITION);
    }
    
    public boolean isLoaderArmRetracted() {
        return this.kicker.get() == KICKER_IN_POSITION;
    }
    public boolean isLoaderArmExtended()
    {
    	return this.kicker.get() == KICKER_OUT_POSITION;
    }
    public void startShooter() {
        this.leftShooter.set(-SHOOT_SPEED);
        this.rightShooter.set(SHOOT_SPEED);
    }

    public void suck() {
        // The sequence of actions to pick up a ball are: 
        // (1) precondition - assume there is not ball loaded into the ball holder
        //    (do we need a way to detect whether a ball is loaded or not????)
        // (2) precondition - assume the loader arm is retracted
        // (3) start the shooter motors spinning inward
        // (4) stop the shooter motors once we detect a ball is loaded???
        this.rightShooter.set(-SUCK_SPEED);
        this.leftShooter.set(SUCK_SPEED);
    }

    public void keep() {
        this.rightShooter.set(-KEEPING_SPEED);
        this.leftShooter.set(KEEPING_SPEED);
    }
    public void setSpeeds(double right, double left)
    {
    	this.rightShooter.set(right);
    	this.leftShooter.set(left);
    }
    public void stop() {
        this.rightShooter.set(0);
        this.leftShooter.set(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right shooter speed", this.rightShooter.getSpeed());
        SmartDashboard.putNumber("Left shooter speed", this.leftShooter.getSpeed());
        SmartDashboard.putNumber("Right encoder speed", this.rightEncoder.getRate());
    }
    
    public double getRightSpeed()
    {
    	return rightEncoder.getRate();
    }
    
    public double getLeftSpeed() {
		return leftEncoder.getRate();
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
