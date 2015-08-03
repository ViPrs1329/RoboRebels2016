package org.stlpriory.robotics.utils;
// WARNING: THE CONSTANTS FOR THIS (MAX_DECELLERATION AND MAX_ACCELLERATION) WERE CHOSEN AT RANDOM AND MIGHT
// CAUSE PROBLEMS IF THEY ARE USED WITHOUT BEING CHANGED. BE VERY CAREFUL. 
public class Ramper {
	private double currentSpeed;

	public Ramper() {
		this(0);
	}

	public Ramper(double speed) {
		this.currentSpeed = speed;
	}

	public double scale(double target) {
		if (target > currentSpeed) {
			if (target - currentSpeed > Constants.MAX_DECELLERATION) {
				if (currentSpeed < 0)
					currentSpeed += Constants.MAX_DECELLERATION;
				else if (currentSpeed >= 0)
					currentSpeed += Constants.MAX_ACCELLERATION;
				return currentSpeed;
			} else {
				currentSpeed = target;
				return currentSpeed;
			}
		} else if (target < currentSpeed) {
			if (currentSpeed - target > Constants.MAX_DECELLERATION) {
				if (currentSpeed <= 0)
					currentSpeed -= Constants.MAX_ACCELLERATION;
				else if (currentSpeed > 0)
					currentSpeed -= Constants.MAX_DECELLERATION;
				return currentSpeed;
			} else {
				currentSpeed = target;
				return currentSpeed;
			}
		}
		return target;
	}

}
