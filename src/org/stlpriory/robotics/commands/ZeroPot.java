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
<<<<<<< HEAD
        properties = Robot.getProperties();
        properties.setProperty("pot-zero-value",String.valueOf(Robot.ballHolder.getAngle()));
        isFinished = false;
=======
        System.out.println("errors");
>>>>>>> c925e7f09421f5bd114535c3cfdd3a67dad89910
    }

    @Override
    protected void execute()
    {
        properties = Robot.getProperties();
        properties.setProperty(Robot.POT_ZERO_VALUE, String.valueOf(Robot.ballHolder.getAngle()));
        try
        {
            PropertiesUtils.save(properties, Robot.CONFIG_FILE);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Robot.setProperties(properties);
    }

    @Override
    protected boolean isFinished()
    {
        return true;
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
