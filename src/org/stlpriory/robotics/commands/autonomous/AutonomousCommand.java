package org.stlpriory.robotics.commands.autonomous;

import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */                                      
public class AutonomousCommand extends CommandGroup {
	public enum Obstacle {
        OPPOSING_RAMPS (1),
        MOVABLE_RAMPS (2),
        ROUGH_TERRAIN (3),
        LOW_BAR (4),
        PORTCULLIS (5),
        MOAT (6),
        DRAWBRIDGE (7),
        SALLYPORT (8),
        ROCK_WALL (9);
        private int number;
        Obstacle(int number)
        {
            this.number = number;
        }
        public int getNumber()
        {
            return this.number;
        }
        public static Obstacle getObstacle(int number)
        {
            for(Obstacle o : Obstacle.values())
            {
                if(o.getNumber() == number)
                    return o;
            }
            return null;
        }
    }
    private double gyroReading;
    public  AutonomousCommand() {
        System.out.println(String.format("Set gyro angle for autonomous; it's %f", gyroReading));
        addSequential(new SetAutonomousInfo());
        addSequential(new DriveDistance(11, Direction.FORWARD, null, 0, false));
        addSequential(new Rotate(180));
        addSequential(new DriveDistance(8, Direction.FORWARD, Robot.autonomousInfo, 180, true));
//        Obstacle obstacle = Obstacle.getObstacle(Integer.parseInt(Robot.AUTONOMOUS_PROPS.getProperty("autonomousProp", "-1")));
//        if(obstacle != null || obstacle != Obstacle.PORTCULLIS || obstacle != Obstacle.DRAWBRIDGE || obstacle != Obstacle.SALLYPORT){
            // Get to the obstacle
            // if(obstacle == ROUGH_TERRAIN)
            // {
            //     addSequential(new DriveDistance(5, Direction.FORWARD));
            //     addSequential(new Rotate(500));
            //     return;
            // }
            // if(obstacle == MOVABLE_RAMPS)
            // {
            //     We should move the arm down here to move the ramp
            // }
            // addSequential(new DriveForwardUntilFlat(6000, Direction.FORWARD));


            // This was just for testing
            // addSequential(new Rotate(45, .15, RotationDirection.CLOCKWISE));
//        }
    }
}
