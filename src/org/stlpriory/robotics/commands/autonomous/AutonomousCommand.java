package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.commands.drivetrain.Rotate;
import org.stlpriory.robotics.commands.drivetrain.Rotate.RotationDirection;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;
import org.stlpriory.robotics.commands.autonomous.DriveForwardUntilFlat;
import org.stlpriory.robotics.commands.autonomous.DriveForwardDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

    public  AutonomousCommand() {

        /*
         * This is the coding system for our config file. 
         * Even Turing couldn't vanquish *this*.
         * 1: Opposing ramps
         * 2: Movable ramps
         * 3: Rough terrain
         * 4: Low bar
         * 5: Portcullis
         * 6: Moat
         * 7: Drawbridge
         * 8: Sallyport
         * 9: Rock wall
         */

        int configNumber = Integer.parseInt(Robot.AUTONOMOUS_PROPS.getProperty("autonomousProp", "-1"));
        if(configNumber != -1 || configNumber != 5 || configNumber != 7 || configNumber != 8){
            // Get to the obstacle
            addSequential(new DriveForwardDistance(1));
            if(configNumber == 2)
            {
                // We should move the arm down here to move the ramp
            }
            addSequential(new DriveForwardUntilFlat(6000, Direction.FORWARD));


            // This was just for testing
            // addSequential(new Rotate(45, .15, RotationDirection.CLOCKWISE));
        }
    }
}
