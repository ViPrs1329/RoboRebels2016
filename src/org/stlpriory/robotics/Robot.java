
package org.stlpriory.robotics;

import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.stlpriory.robotics.utils.Debug;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.stlpriory.robotics.utils.PropertiesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding 
 * to each mode, as described in the IterativeRobot documentation. If you change the name of this 
 * class or the package after creating this project, you must also update the manifest file in the 
 * resource directory.
 */
public class Robot extends IterativeRobot {
    public enum RobotType {PNEUMABOT, TANKBOT};
    public static final String POT_ZERO_VALUE = "pot-zero-value";
    public static final String CONFIG_FILE = "~/config.txt";

    // Select which robot to compile for
    public static final RobotType robotType = RobotType.PNEUMABOT;

    // Human operator interface
    public static OI oi = new OI();
    public Joystick xboxController;

    // Initialize robot subsystems
    public static DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    public static BallHolderSubsystem ballHolder = new BallHolderSubsystem();
    public static ShooterSubsystem shooter = new ShooterSubsystem();
    
    private Command autonomousCommand;
    private Timer timer = new Timer();
    // This Properties is never used, it just keeps away NullPointerExceptions
    private static Properties properties = new Properties();
    // ==================================================================================
    //                            ROBOT INIT SECTION
    // ==================================================================================

    @Override
    public void robotInit() {
        Debug.println("[Robot.robotInit()] Initializing...");
        timer.start();

        // Initialize the human operator interface ...
        this.xboxController = oi.getController();
        timer.stop();

        try
        {
            properties = PropertiesUtils.load(new File(CONFIG_FILE));
        } 
        catch (IOException e)
        {
            properties = new Properties();
            properties.setProperty(POT_ZERO_VALUE, "42");
            e.printStackTrace();
            System.err.println("!!Cannot load config file, setting default values!!");
        }
        Debug.println("[RoboRebels.robotInit()] Done in " + timer.get() * 1e6 + " ms");
        Debug.println("------------------------------------------");
        Debug.println("           Robot ready!");
        Debug.println("------------------------------------------");
    }

    @Override
    public void disabledInit() {
    }

    // ==================================================================================
    //                          AUTONOMOUS MODE SECTION
//     ==================================================================================

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    // ==================================================================================
    //                            TELEOP MODE SECTION
    // ==================================================================================

    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        // Record initial status values
        updateStatus();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        // Record updated status values
        updateStatus();
    }
    
    @Override
    public void disabledPeriodic() {
    }

    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
         drivetrain.updateStatus();
         ballHolder.updateStatus();
         shooter.updateStatus();
    }
    public static Properties getProperties()
    {
        return properties;
    }
    public static void setProperties(Properties properties)
    {
         Robot.properties = properties;
    }
    @Override
    public void testInit() {
        LiveWindow.run();
    }
}
