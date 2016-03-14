package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.utils.AutonomousInfo;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class DriveDistance extends Command {

    double goalDistance = 0.0;
    double startPosition;
    double distance;
    Direction direction;
    double startHeading;
    boolean shouldCorrect;
    AutonomousInfo info;
    double desiredHeading;
    double desiredOffset;

    /*
     * @param din The distance to go in feet
     * @param direction The direction to go (FORWARD or REVERSE)
     * @param info The AutonomousInfo object from the Robot object. Chances are that it should be Robot.autonomousInfo
     * @param desiredOffset The difference in degrees that this command should go from the heading value in info
     * @param shouldCorrect Whether it should look at the info to get the heading. If not, you can pass `null` for info
     */
    public DriveDistance(double din, Direction direction, AutonomousInfo info, double desiredOffset, boolean shouldCorrect) {
        super("DriveWithGamepad");
        requires(Robot.drivetrain);
        this.goalDistance = Utils.TALONdistance(din);
        this.direction = direction;
        this.shouldCorrect = shouldCorrect;
        this.info = info;
        this.desiredOffset = desiredOffset;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startPosition = Robot.drivetrain.getPosition();
        Robot.drivetrain.stop();
        if(shouldCorrect)
            this.desiredHeading = info.getHeading() + desiredOffset;
        else
            this.desiredHeading = Robot.drivetrain.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(shouldCorrect)
        {
            if(Math.abs(Robot.drivetrain.getAngle() - this.desiredHeading) <= DrivetrainSubsystem.GYRO_TOLERANCE)
            {
                if(Robot.drivetrain.getAngle() > this.desiredHeading)
                {
                    Robot.drivetrain.tankDrive(-DrivetrainSubsystem.AUTO_TURN_SPEED,DrivetrainSubsystem.AUTO_TURN_SPEED);
                    return;
                }
                else if(Robot.drivetrain.getAngle() < this.desiredHeading)
                {
                    Robot.drivetrain.tankDrive(DrivetrainSubsystem.AUTO_TURN_SPEED,-DrivetrainSubsystem.AUTO_TURN_SPEED);
                    return;
                }
            }
            else
            {
                shouldCorrect = false;
                startPosition = Robot.drivetrain.getPosition();
                startHeading = Robot.drivetrain.getAngle();
            }
        }
        else{
            if (direction == Direction.FORWARD) {
                Robot.drivetrain.driveForward(-DrivetrainSubsystem.FORWARD_SPEED, desiredHeading);
            } else {
                Robot.drivetrain.driveForward(DrivetrainSubsystem.FORWARD_SPEED, desiredHeading);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        double totalDistance = Math.abs(Robot.drivetrain.getPosition() - startPosition);
        SmartDashboard.putNumber("Distance", totalDistance);
        return totalDistance >= goalDistance;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drivetrain.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
