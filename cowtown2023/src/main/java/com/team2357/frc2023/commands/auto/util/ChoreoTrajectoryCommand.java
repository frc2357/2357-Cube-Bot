package com.team2357.frc2023.commands.auto.util;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;
import com.team2357.frc2023.choreolib.ChoreoSwerveControllerCommand;
import com.team2357.frc2023.choreolib.ChoreoTrajectory;
import com.team2357.frc2023.choreolib.TrajectoryManager;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChoreoTrajectoryCommand extends SequentialCommandGroup{
    private String fileName;
    public ChoreoTrajectoryCommand(String trajectoryFileName){
        fileName = trajectoryFileName.replace(".json","");
        Constants.CHOREO.CHOREO_ROTATION_CONTROLLER.enableContinuousInput(-Math.PI, Math.PI);
        ChoreoTrajectory traj = TrajectoryManager.getInstance().getTrajectory(trajectoryFileName);
        addCommands(
            new InstantCommand(() -> Robot.drive.resetPoseEstimator(traj.getInitialPose()), Robot.drive),
            new ChoreoSwerveControllerCommand(
                traj,
                Robot.drive.getChoreoPoseSupplier(),
                Constants.CHOREO.CHOREO_X_CONTROLLER,
                Constants.CHOREO.CHOREO_Y_CONTROLLER,
                Constants.CHOREO.CHOREO_ROTATION_CONTROLLER,
                Robot.drive.getChassisSpeedsConsumer(),
                false,//DO NOT SET THIS TO TRUE!
                //The robot went crazy when I did, it could be dangerous right now.
                Robot.drive));
    }

    @Override
    public String toString(){
        return fileName;
    }
}
