package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.BallHolderDown;
import org.stlpriory.robotics.commands.BallHolderStop;
import org.stlpriory.robotics.commands.BallHolderUp;
import org.stlpriory.robotics.commands.Hold;
import org.stlpriory.robotics.commands.StopShooter;
import org.stlpriory.robotics.commands.Suck;
import org.stlpriory.robotics.commands.Throw;
import org.stlpriory.robotics.commands.drivetrain.DebugCommand;
import org.stlpriory.robotics.commands.drivetrain.ShiftHigh;
import org.stlpriory.robotics.commands.drivetrain.ShiftLow;
import org.stlpriory.robotics.commands.drivetrain.ShiftSuperLow;
import org.stlpriory.robotics.commands.drivetrain.StopDebugCommand;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.Keymap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups that allow control
 * of the robot.
 */


public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    private final Joystick xboxController;
    private JoystickButton holderUpButton;
    private JoystickButton holderDownButton;
    private JoystickButton throwButton;
    private JoystickButton suckButton;
    private JoystickButton holdSwitch;
    public OI() {
        Debug.println("[OI] Instantiating ...");
        Debug.println("[OI] Intitalizing gamepad to Driver's station USB port"  );

        this.xboxController = new Joystick(0);
        holderUpButton = new JoystickButton(xboxController, 4);
        holderUpButton.whileHeld(new BallHolderUp());
        holderUpButton.whenReleased(new BallHolderStop());

        holderDownButton = new JoystickButton(xboxController, 3);
        holderDownButton.whileHeld(new BallHolderDown());
        holderDownButton.whenReleased(new BallHolderStop());
        
        throwButton = new JoystickButton(xboxController, 2);
        throwButton.whileHeld(new Throw());
        throwButton.whenReleased(new StopShooter());
        
        suckButton = new JoystickButton(xboxController, 3);
        suckButton.whileHeld(new Suck());
        suckButton.whenReleased(new StopShooter());
        
        holdSwitch = new JoystickButton(xboxController, 8);
        holdSwitch.whenPressed(new Hold());
        
        
        
        Debug.println("[OI] Instantiation complete.");        
    }

    public Joystick getGamePad() {
        return this.xboxController;
    }
}
