package org.stlpriory.robotics.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.stlpriory.robotics.Robot;
import org.stlpriory.robotics.commands.drivetrain.Rotate;
import org.stlpriory.robotics.subsystems.DrivetrainSubsystem.Direction;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {
    public enum Obstacle {
        OPPOSING_RAMPS(1),
        MOVABLE_RAMPS(2),
        ROUGH_TERRAIN(3),
        LOW_BAR(4),
        PORTCULLIS(5),
        MOAT(6),
        DRAWBRIDGE(7),
        SALLYPORT(8),
        ROCK_WALL(9);
        private int number;

        Obstacle(int number) {
            this.number = number;
        }

        public int getNumber() {
            return this.number;
        }

        public static Obstacle getObstacle(int number) {
            for (Obstacle o : Obstacle.values()) {
                if (o.getNumber() == number)
                    return o;
            }
            return null;
        }
    }

    private double gyroReading;

    public AutonomousCommand() {
        System.out.println(String.format("Set gyro angle for autonomous; it's %f", gyroReading));
        // This code never worked, but I wanted it to.
        addSequential(new SetAutonomousInfo());
        addSequential(new DriveDistance(5, Direction.FORWARD, Robot.autonomousInfo, 0, true));
//        addSequential(new Rotate(180));
//        addSequential(new DriveDistance(3, Direction.FORWARD, Robot.autonomousInfo, 180, true));


        // This was the working autonomous code at the end of last season.
        // Fascinating, I know.
        // addSequential(new DriveDistance(11, Direction.FORWARD));
    }
}
