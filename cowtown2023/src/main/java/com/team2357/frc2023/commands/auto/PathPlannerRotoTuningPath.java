package com.team2357.frc2023.commands.auto;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.TrajectoryUtil.TrajectoryUtil;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PathPlannerRotoTuningPath extends SequentialCommandGroup{
    public PathPlannerRotoTuningPath(){
        addCommands(
            new TrajectoryUtil().createDrivePathCommand(
                TrajectoryUtil.createPathPlannerTrajectory("RotoTuningPath",
                Constants.PATHPLANNER.DEFAULT_PATH_CONSTRAINTS), true)
        );
    }

    @Override
    public String toString(){
        return "PathPlanner Rotation Tuning Path";
    }
}
