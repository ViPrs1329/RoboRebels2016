package org.stlpriory.robotics.commands.drivetrain;

import org.stlpriory.robotics.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//We may need to make a class in Utils to convert inAngle to whatever units the robot uses for rotation
public class Rotate extends Command {
    public static final double DEFAULT_ROTATION_SPEED = -.5; // Counterclockwise is negative

    double goalAngle = 0.0;
    double startTime;
    double angle;
    double speed;
    double totalAngle = 0.0;
    double timeCurrent;
    Timer timer = new Timer();
    boolean direction;

    public Rotate(final double inAngle, final double speed, final boolean direction) {
        super("DriveWithGamepad");
        // if direction is true, turns left
        // else turns right
        requires(Robot.drivetrain);
        this.speed = speed;
        this.direction = direction;
        //		if (direction)
        //			speed *= (-1.0);
        setTimeout(1.5);
        this.goalAngle = inAngle;
    }

    public Rotate(final double inAngle, final double speed) {
        this(inAngle, speed, false);
    }

    public Rotate(final double inAngle) {
        this(inAngle, DEFAULT_ROTATION_SPEED, false);
    }

    public Rotate(final double inAngle, final boolean direction) {
        this(inAngle, DEFAULT_ROTATION_SPEED, direction);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.timer.start();
        this.startTime = this.timer.get();
        this.totalAngle = 0.;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (this.direction) {
            Robot.drivetrain.tankDrive(this.speed, 0);
        } else {
            Robot.drivetrain.tankDrive(0, this.speed);
        }
        SmartDashboard.putNumber("Robot Speed", Robot.drivetrain.getSpeedInRPM());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        this.timeCurrent = this.timer.get();
        this.angle = (Robot.drivetrain.getSpeedInRPM() * (this.timeCurrent - this.startTime));
        this.totalAngle = this.totalAngle + this.angle;
        if (this.totalAngle >= this.goalAngle) {
            return true;
        }
        this.startTime = this.timer.get();
        SmartDashboard.putNumber("Angle", this.totalAngle);
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //		Robot.drivetrain.mecanum_drive(0.0, 0, 0);

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        //		Robot.drivetrain.mecanum_drive(0.0, 0, 0);

    }
}
