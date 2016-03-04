package org.stlpriory.robotics.utils;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class TwoButton extends Trigger {
	private JoystickButton firstButton;
	private JoystickButton secondButton;

	public TwoButton(JoystickButton buttonOne, JoystickButton buttonTwo)
	{
		
		firstButton = buttonOne;
		secondButton = buttonTwo;
	}
	@Override
	public boolean get() {

		if(firstButton.get() && secondButton.get())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

}
