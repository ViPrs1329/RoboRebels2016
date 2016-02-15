//package org.stlpriory.robotics.commands.drivetrain;
//
//import org.stlpriory.robotics.Robot;
//import org.stlpriory.robotics.utils.Utils;
//
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class DriveForward extends Command {
//    public static final double DEFAULT_FORWARD_SPEED = .7;
//
//    private double goalDistance = 0.0;
//    private double startTime;
//    private double distance;
//    private double totalDistance = 0.0;
//    private double timeCurrent;
//    private double distance2;
//    private double lastVelocity;
//    private Timer timer = new Timer();
//    private boolean forward;
//
//    public DriveForward(final double din, final boolean isForward) {
//        super("DriveForward");
//        // Use requires() here to declare subsystem dependencies
//        // eg. requires(chassis);
//        // Variable "din" needs to be in feet
//        // If isFoward is true, it will drive forwards, otherwise it will drive
//        // in reverse.
//        // requires(Robot.drivetrain);
//        setTimeout(.1);
//        this.goalDistance = Utils.TALONdistance(din);
//        this.forward = isForward;
//
//    }
//
//    // Called just before this Command runs the first time
//    @Override
//    protected void initialize() {
//        this.timer.start();
//        this.startTime = this.timer.get();
//        this.totalDistance = 0.0;
//    }
//
//    // Called repeatedly when this Command is scheduled to run
//    @Override
//    protected void execute() {
//        if (this.forward) {
//            Robot.drivetrain.tankDrive(DEFAULT_FORWARD_SPEED, DEFAULT_FORWARD_SPEED);
//        } else {
//            Robot.drivetrain.tankDrive(-1 * DEFAULT_FORWARD_SPEED, -1 * DEFAULT_FORWARD_SPEED);
//        }
//    }
//
//    // Make this return true when this Command no longer needs to run execute()
//    @Override
//    protected boolean isFinished() {
//        if (getDistance() >= this.goalDistance) {
//            return true;
//        }
//        SmartDashboard.putNumber("Distance", getDistance());
//        return false;
//    }
//
//    private double getDistance() {
//        this.timeCurrent = this.timer.get();
//        this.distance = (Robot.drivetrain.getSpeedInRPM() * (this.timeCurrent - this.startTime));
//        this.distance2 = (this.lastVelocity * (this.timeCurrent - this.startTime));
//        this.totalDistance = this.totalDistance + ((this.distance + this.distance2) / 2);//using trapezoid rule to estimate distance
//        System.out.println("distance " + this.totalDistance);
//        this.startTime = this.timer.get();
//        this.lastVelocity = Robot.drivetrain.getSpeedInRPM();
//        return this.distance;
//    }
//
//    // Called once after isFinished returns true
//    @Override
//    protected void end() {
//        Robot.drivetrain.tankDrive(0, 0);
//        System.out.println("I finished Driving");
//
//    }
//
//    // Called when another command which requires one or more of the same
//    // subsystems is scheduled to run
//    @Override
//    protected void interrupted() {
//        Robot.drivetrain.tankDrive(0, 0);
//    }
//}
