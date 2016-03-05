package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//We may need to make a class in Utils to convert inAngle to whatever units the robot uses for rotation
public class Rotate extends Command {
    public static final double DEFAULT_ROTATION_SPEED = -.5; // Counterclockwise is negative
    public enum RotationDirection {CLOCKWISE, COUNTERCLOCKWISE};

    double goalAngle = 0.0;
    double lastTime;
    double speed;
    double totalAngle = 0.0;
    double currentTime;
    Timer timer = new Timer();
    RotationDirection direction;

    public Rotate(final double inAngle, final double speed, final RotationDirection direction) {
        super("DriveWithGamepad");
        // if direction is true, turns left
        // else turns right
        requires(Robot.drivetrain);
        this.speed = speed;
        this.direction = direction;
        //		if (direction)
        //			speed *= (-1.0);
        this.goalAngle = inAngle;
    }

    public Rotate(final double inAngle, final double speed) {
        this(inAngle, speed, RotationDirection.CLOCKWISE);
    }

    public Rotate(final double inAngle) {
        this(inAngle, DEFAULT_ROTATION_SPEED, RotationDirection.CLOCKWISE);
    }

    public Rotate(final double inAngle, final RotationDirection direction) {
        this(inAngle, DEFAULT_ROTATION_SPEED, direction);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.timer.start();
        this.lastTime = this.timer.get();
        this.totalAngle = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (direction == RotationDirection.CLOCKWISE) {
            Robot.drivetrain.tankDrive(this.speed, 0);
        } else {
            Robot.drivetrain.tankDrive(0, this.speed);
        }
        SmartDashboard.putNumber("Robot Speed", Robot.drivetrain.getSpeedInRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        this.currentTime = this.timer.get();
        // TODO: I don't think that getSpeedInRPM() does what we think it does.
        double angle = (Robot.drivetrain.getSpeedInRPM() * (this.currentTime - this.lastTime)); 
        this.totalAngle = this.totalAngle + angle;
        this.lastTime = this.timer.get();
        SmartDashboard.putNumber("Angle", this.totalAngle);
        return this.totalAngle >= this.goalAngle;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.drivetrain.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Robot.drivetrain.stop();

    }
}
