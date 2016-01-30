package org.stlpriory.robotics.utils;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

public class TwoButton extends Trigger {
	private JoystickButton firstButton;
	private JoystickButton secondButton;

	public TwoButton(JoystickButton buttonOne, JoystickButton buttonTwo, Command c)
	{
		this.whenActive(c);
		firstButton = buttonOne;
		secondButton = buttonTwo;
	}
	@Override
	public boolean get() {
		return false;
		// This doesn't work...
//		if(firstButton.get() && secondButton.get())
//		{
//			return true;
//		}
//		else
//		{
//			return false;
//		}
	}
	

}
