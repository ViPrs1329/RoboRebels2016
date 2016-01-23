class BallHolder extends Subsystem {
    private Talon rightMotor;
    private Talon leftMotor;
    public BallHolder() 
    {
        rightMotor = new Talon(0);
        leftMotor = new Talon(1);
    }
    public void set(double speed)
    {
        leftMotor.set(speed);
        rightMotor.set(speed);
    }
}
