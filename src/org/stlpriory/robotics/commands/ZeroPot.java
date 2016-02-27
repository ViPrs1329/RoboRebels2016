package org.stlpriory.robotics.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.utils.PropertiesUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Adam on 2/26/2016.
 */
public class ZeroPot extends Command
{
    private Properties properties;
    private boolean isFinished;
    @Override
    protected void initialize()
    {
        properties = Robot.getProperties();
        properties.setProperty("pot-zero-value",String.valueOf(Robot.ballHolder.getAngle()));
        isFinished = false;
    }

    @Override
    protected void execute()
    {
        try
        {
            PropertiesUtils.save(properties,"~/config.txt");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Robot.updateProperties(properties);
        isFinished = true;
    }

    @Override
    protected boolean isFinished()
    {
        return isFinished;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {

    }
}
