
package org.stlpriory.robotics;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Files;

import org.stlpriory.robotics.commands.ZeroPot;
import org.stlpriory.robotics.subsystems.BallHolderSubsystem;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem;
import org.stlpriory.robotics.subsystems.ShooterSubsystem;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.PropertiesUtils;
import org.stlpriory.robotics.utils.FileUtils;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding 
 * to each mode, as described in the IterativeRobot documentation. If you change the name of this 
 * class or the package after creating this project, you must also update the manifest file in the 
 * resource directory.
 */
public class Robot extends IterativeRobot {
    public enum RobotType {PNEUMABOT, TANKBOT};
    public static final String POT_ZERO_VALUE = "pot-zero-value";

    // Select which robot to compile for
    public static final RobotType robotType = RobotType.TANKBOT;

    // Initialize robot subsystems
    public static DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    public static BallHolderSubsystem ballHolder = new BallHolderSubsystem();
    public static ShooterSubsystem shooter = new ShooterSubsystem();

    // Human operator interface
    public static final OI oi = new OI();
    
    // Robot configuration file and properties
    public static final File CONFIG_FILE_DIRECTORY = FileUtils.getSystemTempDirectory();
    public static final File CONFIG_FILE = new File(CONFIG_FILE_DIRECTORY, "robotConfig.xml");
    public static final Properties ROBOT_PROPS = new Properties();
    
    private Command autonomousCommand;
    private Timer timer = new Timer();
    
    // ==================================================================================
    //                            ROBOT INIT SECTION
    // ==================================================================================

    @Override
    public void robotInit() {
        Debug.println("[Robot.robotInit()] Initializing...");
        System.out.println("init started");
        timer.start();
        
        // Load robot configuration file
        loadRobotConfigFile();
        
        timer.stop();
        
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
    
    // ==================================================================================
    //                      P U B L I C   M E T H O D S
    // ==================================================================================

    public void loadRobotConfigFile() {
        try {
            // Clear any robot properties before reloading
            ROBOT_PROPS.clear();
            
            if (Files.exists(CONFIG_FILE.toPath())) {
                // Read the existing properties file
                Properties props = PropertiesUtils.load(CONFIG_FILE);
                // Put all the loaded values into the robot properties
                ROBOT_PROPS.putAll(props);
            } else {
                // Create a new empty properties file
                CONFIG_FILE.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveRobotConfigFile() {
        try {
            if (Files.exists(CONFIG_FILE.toPath())) {
                // Write the properties file overwriting any existing file
                PropertiesUtils.save(ROBOT_PROPS, CONFIG_FILE);
            } else {
                // Create a new empty properties file
                CONFIG_FILE.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    /**
     * Call the updateStatus methods on all subsystems
     */
    public void updateStatus() {
		drivetrain.updateStatus();
		ballHolder.updateStatus();
		shooter.updateStatus();
    }
    
    
    @Override
    public void testInit() {
        LiveWindow.run();
    }
}
