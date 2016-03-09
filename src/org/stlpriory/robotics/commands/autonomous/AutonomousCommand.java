package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.commands.drivetrain.Rotate;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;

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
//        	if(configNumber == 3)
//        	{ 
        		addSequential(new DriveDistance(5, Direction.FORWARD));
        		addSequential(new Rotate(500));
//        		return;
//        	}
//            addSequential(new DriveDistance(1, Direction.FORWARD));
//            if(configNumber == 2)
//            {
                // We should move the arm down here to move the ramp
//            }
//            addSequential(new DriveForwardUntilFlat(6000, Direction.FORWARD));


            // This was just for testing
            // addSequential(new Rotate(45, .15, RotationDirection.CLOCKWISE));
        }
    }
}
