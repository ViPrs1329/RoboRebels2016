package org.stlpriory.robotics.utils;

public class Ramper {
    public static final double MAX_ACCELLERATION = .02;
    public static final double MAX_DECELLERATION = .03;

    private double currentSpeed;

    public Ramper() {
        this(0);
    }

    public Ramper(double speed) {
        this.currentSpeed = speed;
    }

    public double scale(double target) {
        if (target > currentSpeed) {
            if (target - currentSpeed > MAX_DECELLERATION) {
                if (currentSpeed < 0)
                    currentSpeed += MAX_DECELLERATION;
                else if (currentSpeed >= 0)
                    currentSpeed += MAX_ACCELLERATION;
                return currentSpeed;
            } else {
                currentSpeed = target;
                return currentSpeed;
            }
        } else if (target < currentSpeed) {
            if (currentSpeed - target > MAX_DECELLERATION) {
                if (currentSpeed <= 0)
                    currentSpeed -= MAX_ACCELLERATION;
                else if (currentSpeed > 0)
                    currentSpeed -= MAX_DECELLERATION;
                return currentSpeed;
            } else {
                currentSpeed = target;
                return currentSpeed;
            }
        }
        return target;
    }

}
