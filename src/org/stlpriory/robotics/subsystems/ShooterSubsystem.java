package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterSubsystem extends Subsystem {
    public static final int RIGHT_SHOOTER = 1;
    public static final int LEFT_SHOOTER  = 3;
    public static final double KEEPING_SPEED = .1;
    public static final double SUCK_SPEED = 1;
    public static final double SHOOT_SPEED = 1;

    private final Talon rightShooter;
    private final Talon leftShooter;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public ShooterSubsystem() {
        this.rightShooter = new Talon(RIGHT_SHOOTER);
        this.leftShooter  = new Talon(LEFT_SHOOTER);
    }
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void shoot() {
        this.rightShooter.set(SHOOT_SPEED);
        this.leftShooter.set(-SHOOT_SPEED);
    }

    public void suck() {
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
