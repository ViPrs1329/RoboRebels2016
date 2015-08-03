
package org.stlpriory.robotics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.stlpriory.robotics.commands.autonomous.AutonomousCommand;
import org.stlpriory.robotics.subsystems.CANDrivetrain;
import org.stlpriory.robotics.subsystems.Elevator;
import org.stlpriory.robotics.subsystems.ExampleSubsystem;
import org.stlpriory.robotics.utils.Debug;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final Elevator elevator = new Elevator();
	public static final CANDrivetrain drivetrain = new CANDrivetrain();
	public static OI oi;
    private Timer timer = new Timer();

    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        Debug.println("[Robot.robotInit()] Initializing...");
        timer.start();
        
		oi = new OI();
        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand();
        
        timer.stop();
        Debug.println("[RoboRebels.robotInit()] Done in " + timer.get() * 1e6 + " ms");
        Debug.println("------------------------------------------");
        Debug.println("           Robot ready!");
        Debug.println("------------------------------------------");
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	Robot.elevator.setElevatorPulsing(false);
        // schedule the autonomous command (example)
    	autonomousCommand = new AutonomousCommand();
        if (autonomousCommand != null) { 
        	autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        Robot.elevator.setElevatorPulsing(true);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
