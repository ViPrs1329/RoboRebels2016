package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {
    public static final int EXTEND_CHANNEL = 4;

    public static final int LEFT_MOTOR_CHANNEL  = 3;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_A = 6;
    public static final int LEFT_MOTOR_ENCODER_CHANNEL_B = 7;
    
    public static final int RIGHT_MOTOR_CHANNEL = 1;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_A = 8;
    public static final int RIGHT_MOTOR_ENCODER_CHANNEL_B = 9;
    
    public static final double KEEPING_SPEED = .1;
    public static final double SUCK_SPEED = 1;
    public static final double SHOOT_SPEED = 1;

    private final Talon rightShooter;
    private final Talon leftShooter;
    
//    private final Encoder rightEncoder;
//    private final Encoder leftEncoder; 
    
    //private final DoubleSolenoid ballLoader;
    private final Solenoid extendLoaderArm;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ShooterSubsystem() {
        this.rightShooter = new Talon(RIGHT_MOTOR_CHANNEL);
        this.leftShooter  = new Talon(LEFT_MOTOR_CHANNEL);
        
//        this.rightEncoder = new Encoder(RIGHT_MOTOR_ENCODER_CHANNEL_A, RIGHT_MOTOR_ENCODER_CHANNEL_B, false);
//        this.rightEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);
//        
//        this.leftEncoder  = new Encoder(LEFT_MOTOR_ENCODER_CHANNEL_A, LEFT_MOTOR_ENCODER_CHANNEL_B, true);
//        this.leftEncoder.setDistancePerPulse(CIMcoderSpecs.PULSES_PER_REV);
        
        this.extendLoaderArm  = new Solenoid(EXTEND_CHANNEL);
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void extendLoaderArm() {
        this.extendLoaderArm.set(true);
    }

    public void retractLoaderArm() {
        this.extendLoaderArm.set(false);
    }
    
    public boolean isLoaderArmRetracted() {
        return !this.extendLoaderArm.get();
    }
    
    public void startShooter() {
        this.leftShooter.set(-SHOOT_SPEED);
        this.rightShooter.set(SHOOT_SPEED);

//        while (true) {
//            // Scale the left motor speed if the difference of the encoder values is > 10%
//            double rEncoderRate = this.rightEncoder.getRate();
//            double lEncoderRate = this.leftEncoder.getRate();
//            double percentDiff  = 100.0 * Math.abs( (rEncoderRate - lEncoderRate) / rEncoderRate);
//            if (percentDiff > 10) {
//                double scaleFactor = rEncoderRate / lEncoderRate;
//                this.leftShooter.set(-SHOOT_SPEED*scaleFactor);
//            } else {
//                break;
//            }
//        }
    }
    
    public void stopShooter() {
        stop();
    }
    
    public void shoot() {
        // The sequence of actions to shoot a ball are: 
        // (1) precondition - assume the ball is already loaded into the ball holder 
        //    (do we need a way to detect whether a ball is loaded or not????)
        // (2) precondition - assume the loader arm is retracted
        // (3) start the shooter motors spinning outward until they are at full speeed (~ 1 sec)
        // (4) extending the loader arm and to push the ball into the spinning wheels
        // (5) retract the loader arm
        // (6) stop the shooter motors
        this.rightShooter.set(SHOOT_SPEED);
        this.leftShooter.set(-SHOOT_SPEED);
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

    public void stop() {
        this.rightShooter.set(0);
        this.leftShooter.set(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right shooter speed", this.rightShooter.getSpeed());
        SmartDashboard.putNumber("Left shooter speed", this.leftShooter.getSpeed());
    }
    
    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
