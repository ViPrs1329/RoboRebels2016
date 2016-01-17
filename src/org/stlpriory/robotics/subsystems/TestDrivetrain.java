package org.stlpriory.robotics.subsystems;

import org.stlpriory.robotics.commands.drivetrain.DriveWithGamepad;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TestDrivetrain {
// 	For the love of all that is holy, please never, ever, ever do anything like this 
//	anywhere.  	
    Talon testWheel;

    public TestDrivetrain() {
        Debug.println("[test drivetrain Subsystem] Instantiating...");

        testWheel = new Talon(0);

        Debug.println("[DriveTrain Subsystem] Instantiation complete.");
    }

    public void stop() {
        testWheel.set(0);
    }

    /**
     * We are going to pretend that this one motor is a whole mecanum drive so
     * that we don't have to change any other code to remove the TestDrivetrain. 
     */
    public void mecanum_drive(Joystick joystick) {
        /*
         * Three-axis joystick mecanum control.
         * Let x represent strafe left/right
         * Let y represent rev/fwd
         * Let z represent spin CCW/CW axes
         * where each varies from -1 to +1.
         * So:
         * y = -1 corresponds to full speed reverse,
         * y= +1 corresponds to full speed forward,
         * x= -1 corresponds to full speed strafe left,
         * x= +1 corresponds to full speed strafe right,
         * z= -1 corresponds to full speed spin CCW,
         * z= +1 corresponds to full speed spin CW
         *
         * Axis indexes:
         * 1 - LeftX
         * 2 - LeftY
         * 3 - Triggers (Each trigger = 0 to 1, axis value = right - left)
         * 4 - RightX
         * 5 - RightY
         * 6 - DPad Left/Right
         */

        double rawLeftX = joystick.getRawAxis(1);
        double rawLeftY = joystick.getRawAxis(2);
        double rawZ = joystick.getRawAxis(3);

        double scaledLeftX = Utils.scale(rawLeftX);
        double scaledLeftY = Utils.scale(rawLeftY);

        double right = -scaledLeftX;
        double forward = scaledLeftY;
        double rotation = -rawZ;
        double clockwise = rawZ;
//        DriverStation.reportError("The input coming in is " + String.valueOf(joystick.toString()), false);
        this.testWheel.set(forward);
    }

    public void mecanum_drive(final double forward, final double right, final double rotation) {
        this.testWheel.set(forward);
    }

    public void arcade_drive(final double forward, final double rotation) {
    	this.testWheel.set(forward);
    }

    public void initDefaultCommand() {
        Debug.println("[DriveTrain.initDefaultCommand()] Setting default command to " + DriveWithGamepad.class.getName());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
//        setDefaultCommand(new DriveWithGamepad());
    }
}
