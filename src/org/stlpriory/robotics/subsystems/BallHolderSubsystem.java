package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHolderSubsystem extends Subsystem {
    public static final int RIGHT_WINDOW_MOTOR = 0; 
    public static final int LEFT_WINDOW_MOTOR = 2;
    public static final int POTENTIOMETER = 0;
    public static final int POTENTIOMETER_SCALE_FACTOR = 1;
    public static final int POTENTIOMETER_OFFSET = 0;
    
    public enum Direction {
        UP, DOWN
    };

    private final Talon rightMotor;
    private final Talon leftMotor;
    private final AnalogPotentiometer pot;

    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public BallHolderSubsystem() {
        this.rightMotor = new Talon(RIGHT_WINDOW_MOTOR);
        this.leftMotor  = new Talon(LEFT_WINDOW_MOTOR);
        this.pot = new AnalogPotentiometer(POTENTIOMETER, POTENTIOMETER_SCALE_FACTOR, POTENTIOMETER_OFFSET);

    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void set(final double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public double getAngle() {
        return this.pot.get();
    }

    public void set(final Direction dir, double speed) {
        speed = Math.abs(speed) * (dir == Direction.UP ? 1 : -1);
        this.set(speed);
    }

    public void stop() {
        set(0);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right ballholder speed", this.rightMotor.getSpeed());
        SmartDashboard.putNumber("Left ballholder speed", this.leftMotor.getSpeed());
        SmartDashboard.putNumber("Ballholder potentiometer", this.pot.get());
    }
    
    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
