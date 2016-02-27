package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHolderSubsystem extends Subsystem {
    public static final int RIGHT_WINDOW_MOTOR = 8;
    public static final int LEFT_WINDOW_MOTOR = 2;

    public static final int POT_CHANNEL = 0;
    public static final int SWITCH_CHANNEL = 1;
    
    
    /*
     * The scaling factor multiplied by the analog voltage value to obtain the angle in degrees
     * For example, let's say you have an ideal 10-turn linear potentiometer attached to a motor
     * attached by chains and a 25x gear reduction to an arm. If the potentiometer (attached to
     * the motor shaft) turned its full 3600 degrees, the arm would rotate 144 degrees. Therefore,
     * the POT_FULL_RANGE scale factor would be 144 degrees / 5V,  or 28.8 degrees/volt.
     * Since the RoboRebels ball holder uses a 1x gear reduction between the potentiometer and the
     * rotating arm the POT_FULL_RANGE scale factor would be 3600 degrees / 5V or 720 degrees/volt.
     */
    public static final int POT_FULL_RANGE = 10 * 360;
    
    /*
     * The offset in degrees that the angle sensor will subtract from the underlying value before
     * returning the angle. Specifying the offset is necessary because often to prevent the potentiometer
     * from breaking due to minor shifting in alignment of the mechanism, the potentiometer may
     * be installed with the "zero-point" of the mechanism (e.g., arm in this case) a little ways into
     * the potentiometer's range (for example 30 degrees). In this case, the offset value of 30 is
     * determined from the mechanical design.
     */
    public static final int POT_OFFSET_DEG = 0;
    
    public static final double BALL_PICKUP_ANGLE    = 180.0d;
    public static final double LOW_GOAL_SHOOT_ANGLE = 120.0d;
    public static final double HI_GOAL_SHOOT_ANGLE  = 60.0d;
    public static final double ARM_SPEED  = 0.5d;

    public static final double MAX_ANGLE = 1855;
    public static final double MIN_ANGLE = 1740;
    public static final double TOLERANCE = 0.5d;

    public enum Direction {
        UP, DOWN, ELEVEN
    };

    private final Talon rightMotor;
    private final Talon leftMotor; 
//    private Ramper leftRamper, rightRamper;
    
    private final AnalogPotentiometer pot;
    private final double potOffsetInDeg = 0;
    
    private final DigitalInput stowSwitch;


    // ==================================================================================
    //                        C O N S T R U C T O R S
    // ==================================================================================

    public BallHolderSubsystem() {
        this.rightMotor = new Talon(RIGHT_WINDOW_MOTOR);
//        rightRamper = new Ramper();
//        leftRamper = new Ramper();
        this.leftMotor  = new Talon(LEFT_WINDOW_MOTOR);
        
        this.stowSwitch = new DigitalInput(SWITCH_CHANNEL);
        
        this.pot = new AnalogPotentiometer(POT_CHANNEL, POT_FULL_RANGE, POT_OFFSET_DEG);
        
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================
    
    public void moveToStowPosition() {
        while ( !isStowed() ) {
            set(ARM_SPEED);
        }
        stop();
    }
    
    public void moveToBallPickupPosition() {
        while ( getAngle() < BALL_PICKUP_ANGLE ) {
            set(ARM_SPEED);
        }
        stop();
    }
    
    public void moveToLowGoalShootPosition() {
        while ( getAngle() < LOW_GOAL_SHOOT_ANGLE ) {
            set(ARM_SPEED);
        }
        stop();
   }

    public void moveToHighGoalShootPosition() {
        while ( getAngle() < HI_GOAL_SHOOT_ANGLE ) {
            set(ARM_SPEED);
        }
        stop();
    }
    
    public boolean isStowed() {
        return this.stowSwitch.get();
    }
    
    public void set(final double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void stop() {
        set(0);
    }
    
    /**
     * Get the current ball holder arm angle relative to its initial position upon startup
     * @return the potentiometer reading in degrees
     */
    public double getAngle() {
        return this.pot.get() - this.potOffsetInDeg;
    }

    public void set(final Direction dir, double speed) {
        speed = Math.abs(speed) * (dir == Direction.UP ? 1 : -1);
        this.set(speed);
    }

    /**
     * @return true if the ball holder arm is within the allowable range
     */
    public boolean inRange() {
        double angle = Math.abs( getAngle() );
        System.out.printf("%f > %f > %f ???%n", MAX_ANGLE, angle, MIN_ANGLE);
        return (angle > MIN_ANGLE) && (angle < MAX_ANGLE);
    }

    public void updateStatus() {
        SmartDashboard.putNumber("Right ballholder speed", this.rightMotor.getSpeed());
        SmartDashboard.putNumber("Left ballholder speed", this.leftMotor.getSpeed());
        SmartDashboard.putNumber("Potentiometer (deg)", this.pot.get());
    }

    // ==================================================================================
    //                    P R O T E C T E D   M E T H O D S
    // ==================================================================================

    @Override
    protected void initDefaultCommand() {
        // TODO Auto-generated method stub
    }

}
