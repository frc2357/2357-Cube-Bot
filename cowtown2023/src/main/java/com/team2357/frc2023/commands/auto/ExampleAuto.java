package com.team2357.frc2023.commands.auto;

import com.pathplanner.lib.PathConstraints;
import com.team2357.frc2023.trajectoryutil.TrajectoryUtil;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ExampleAuto extends ParallelCommandGroup {
    public ExampleAuto() {
        addCommands(
            // Mechanism commands
            new SequentialCommandGroup(
                
            ),
            // Drive commands
            new SequentialCommandGroup(
                new WaitCommand(0),
                TrajectoryUtil.createTrajectoryPathCommand(getClass().getSimpleName(), new PathConstraints(1, 1), true)
            ) 
        );
    }

    @Override
    public String toString() {
        return "Example Auto";
    }
}
