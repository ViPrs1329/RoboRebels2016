package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.commands.drivetrain.Rotate;
import org.stlpriory.robotics.commands.drivetrain.Rotate.RotationDirection;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

	public  AutonomousCommand() {
		// Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both
    	//the chassis and the
        // arm.
		
		addSequential(new DriveForwardDistance(5, Direction.FORWARD));
		addSequential(new Rotate(45, .5, RotationDirection.CLOCKWISE));
	}
}
