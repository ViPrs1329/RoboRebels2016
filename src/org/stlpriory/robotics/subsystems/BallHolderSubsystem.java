package org.stlpriory.robotics.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallHolderSubsystem extends Subsystem {
    public static final int RIGHT_WINDOW_MOTOR = 0;
    public static final int LEFT_WINDOW_MOTOR = 2;

    public static final int POT_CHANNEL = 0;
    /*
     * The scaling factor multiplied by the analog voltage value to obtain the angle in degrees
     * For example, let's say you have an ideal 10-turn linear potentiometer attached to a motor
     * attached by chains and a 25x gear reduction to an arm. If the potentiometer (attached to
     * the motor shaft) turned its full 3600 degrees, the arm would rotate 144 degrees. Therefore,
     * the POT_FULL_RANGE scale factor would be 144 degrees / 5V,  or 28.8 degrees/volt.
     * Since the RoboRebels ball holder uses a 1x gear reduction between the potentiometer and the
     * rotating arm the POT_FULL_RANGE scale factor would be 3600 degrees / 5V or 720 degrees/volt.
     */
    public static final int POT_FULL_RANGE = 1;
//    public static final int POT_FULL_RANGE = 720;
    
    /*
     * The offset in degrees that the angle sensor will subtract from the underlying value before
     * returning the angle. Specifying the offset is necessary because often to prevent the potentiometer
     * from breaking due to minor shifting in alignment of the mechanism, the potentiometer may
     * be installed with the "zero-point" of the mechanism (e.g., arm in this case) a little ways into
     * the potentiometer's range (for example 30 degrees). In this case, the offset value of 30 is
     * determined from the mechanical design.
     */
    public static final int POT_OFFSET_DEG = 0;

    // These are for the potentiometer specifically
    public static final double MAX_ANGLE = .66;
    public static final double MIN_ANGLE = .6;
    public static final double TOLERANCE = .0005;

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
        this.leftMotor = new Talon(LEFT_WINDOW_MOTOR);
        this.pot = new AnalogPotentiometer(POT_CHANNEL, POT_FULL_RANGE, POT_OFFSET_DEG);
    }

    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void set(final double speed) {
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public double getAngle() {
        // The potentiometer is backwards.
        return 1.0 - this.pot.get();
    }

    public void set(final Direction dir, double speed) {
        speed = Math.abs(speed) * (dir == Direction.UP ? 1 : -1);
        this.set(speed);
    }

    public boolean inRange() {
        double angle = getAngle();
        System.out.printf("%f > %f > %f ???%n", MAX_ANGLE, angle, MIN_ANGLE);
        return (angle > MIN_ANGLE) && (angle < MAX_ANGLE);
    }

    public void stop() {
        set(0);
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
