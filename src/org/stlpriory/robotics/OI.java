package org.stlpriory.robotics;

import org.stlpriory.robotics.commands.BallHolderDown;
import org.stlpriory.robotics.commands.BallHolderStop;
import org.stlpriory.robotics.commands.BallHolderUp;
import org.stlpriory.robotics.commands.ExampleCommand;
import org.stlpriory.robotics.commands.Hold;
import org.stlpriory.robotics.commands.HolderToBottom;
import org.stlpriory.robotics.commands.HolderToTop;
import org.stlpriory.robotics.commands.Suck;
import org.stlpriory.robotics.commands.Throw;
import org.stlpriory.robotics.utils.ControllerMap;
import org.stlpriory.robotics.utils.Debug;
import org.stlpriory.robotics.utils.TwoButton;

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
    private JoystickButton holderTop;
    private JoystickButton holderBottom;
    private TwoButton holderMiddle;

    public OI() {
        Debug.println("[OI] Instantiating ...");
        Debug.println("[OI] Intitalizing gamepad to Driver's station USB port"  );

        this.xboxController = new Joystick(0);

        holderUpButton = new JoystickButton(xboxController, ControllerMap.B_BUTTON);
        holderUpButton.whileHeld(new BallHolderUp());
        holderUpButton.whenReleased(new BallHolderStop());

        holderDownButton = new JoystickButton(xboxController, ControllerMap.A_BUTTON);
        holderDownButton.whileHeld(new BallHolderDown());
        
        throwButton = new JoystickButton(xboxController, ControllerMap.Y_BUTTON);
        throwButton.whileHeld(new Throw());
        
        suckButton = new JoystickButton(xboxController, ControllerMap.X_BUTTON);
        suckButton.whileHeld(new Suck());
        
        holdSwitch = new JoystickButton(xboxController, ControllerMap.START_BUTTON);
        holdSwitch.toggleWhenPressed(new Hold());
        
        holderTop = new JoystickButton(xboxController, ControllerMap.RIGHT_BUMPER);
        holderTop.whenPressed(new HolderToTop());
        
        holderBottom = new JoystickButton(xboxController, ControllerMap.LEFT_BUMPER);
        holderBottom.whenPressed(new HolderToBottom());
        
        holderMiddle = new TwoButton(new JoystickButton(xboxController,ControllerMap.LEFT_BUMPER),
        							 new JoystickButton(xboxController,ControllerMap.RIGHT_BUMPER),
        							 new ExampleCommand());
        
        Debug.println("[OI] Instantiation complete.");        
    }

    public Joystick getGamePad() {
        return this.xboxController;
    }
}
