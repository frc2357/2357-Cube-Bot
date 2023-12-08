package com.team2357.frc2023.commands.auto;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;
import com.team2357.frc2023.choreolib.ChoreoSwerveControllerCommand;
import com.team2357.frc2023.choreolib.TrajectoryManager;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ChoreoRotoTuningPathCommand extends SequentialCommandGroup{
    public ChoreoRotoTuningPathCommand(){
    //     new PIDController(
    //         AutoConstants.kPThetaController, 0, 0);
    // thetaController.enableContinuousInput(-Math.PI, Math.PI);
    Robot.drive.resetPoseEstimator(new Pose2d());
    addCommands(
        
        new ChoreoSwerveControllerCommand(
            TrajectoryManager.getInstance().getTrajectory("RotoTuningPath.json"),
            Robot.drive.getPoseSupplier(), // Functional interface to feed supplier
            Robot.drive.getKinematics(),

            // Position controllers
            Constants.CHOREO.CHOREO_X_CONTROLLER,
            Constants.CHOREO.CHOREO_Y_CONTROLLER,
            Constants.CHOREO.CHOREO_ROTATION_CONTROLLER,
            Robot.drive.getSwerveStatesConsumer(),
            true,
            Robot.drive));
    }

    @Override
    public String toString(){
        return "Choreo Roto Tuning Path";
    }
}
