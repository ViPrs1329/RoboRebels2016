package org.stlpriory.robotics.commands;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDeviceStatus;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command used to test PID values
 */
public class CANTalonTestCommand extends Command {
    
    private final CANTalon talon;

    public CANTalonTestCommand(CANTalon talon) {
        requires(Robot.drivetrain);
        this.talon = talon;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Debug.println("CANTalonTestCommand ...");
        Debug.println("   P = "+talon.getP());
        Debug.println("   I = "+talon.getI());
        Debug.println("   D = "+talon.getD());
        Debug.println("   F = "+talon.getF());
        Debug.println("   I Zone = "+talon.getIZone());
        FeedbackDeviceStatus sensorStatus = talon.isSensorPresent(FeedbackDevice.QuadEncoder);
        Debug.println("   isSensorPluggedIn? "+(FeedbackDeviceStatus.FeedbackStatusPresent == sensorStatus));
        
        // ensure the talon is enabled
        if (!talon.isEnabled()) {
            talon.enable();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // talon.get() returns the current status of the Talon (usually a sensor value). 
        // In Current mode, the return value is the output current. 
        // In PercentVbus mode, the return value is the current applied throttle.
        // In Follower mode, the return value is the current applied throttle.
        // In Voltage mode, the return value is in volts
        // In Speed mode, the return value is the current speed. 
        // In Position mode, the return value is the current sensor position. 
        SmartDashboard.putNumber("talon.get()", this.talon.get());
        
        SmartDashboard.putNumber("talon.getError()", this.talon.getError());
        SmartDashboard.putNumber("talon.getSpeed() RPM", this.talon.getSpeed()); // speed in RPM
        SmartDashboard.putNumber("talon.getEncVelocity()", this.talon.getEncVelocity());
        SmartDashboard.putNumber("talon.getAnalogInVelocity()", this.talon.getAnalogInVelocity());
        
//        SmartDashboard.putNumber("talon.getPulseWidthPosition()", this.talon.getPulseWidthPosition());
//        SmartDashboard.putNumber("talon.getPulseWidthRiseToFallUs()", this.talon.getPulseWidthRiseToFallUs());
//        SmartDashboard.putNumber("talon.getPulseWidthRiseToRiseUs()", this.talon.getPulseWidthRiseToRiseUs());
//        SmartDashboard.putNumber("talon.getPulseWidthVelocity()", this.talon.getPulseWidthVelocity());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        this.talon.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.talon.set(0);
    }
}
