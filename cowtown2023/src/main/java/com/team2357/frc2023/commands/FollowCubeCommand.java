package com.team2357.frc2023.commands;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;
import com.team2357.lib.util.Utility;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class FollowCubeCommand extends CommandBase {
    private int hangTimer = 0;

    public FollowCubeCommand() {
        addRequirements(Robot.drive);
    }

    @Override
    public void initialize() {
        Robot.frontLimelight.setGamepiecePipelineActive();
    }

    @Override
    public void execute() {
        double rotationError = Robot.frontLimelight.getTX();
        double translationError = Robot.frontLimelight.getTY();
        double translationSpeed = 0;
        boolean rotate = !Utility.isWithinTolerance(rotationError, Constants.SWERVE.ROTATION_OFFSET,
        Constants.SWERVE.ROTATION_TOLERANCE);
        if (!Utility.isWithinTolerance(translationError, Constants.SWERVE.TRANSLATION_OFFSET,
                Constants.SWERVE.TRANSLATION_TOLERANCE)) {
            translationSpeed = Constants.SWERVE.GAMEPIECE_TRACKING_TRANSLATION_SPEED;
        }
        Robot.drive.translateAndRotateToGamepiece(rotationError, translationError, translationSpeed, rotate);
    }

    @Override
    public boolean isFinished() {
        if(!Robot.frontLimelight.validTargetExists()){
            hangTimer++;
        }
        else{
            hangTimer = 0;
        }

        return 
            (Utility.isWithinTolerance(Robot.frontLimelight.getTX(), Constants.SWERVE.ROTATION_OFFSET, Constants.SWERVE.ROTATION_TOLERANCE) &&
            Utility.isWithinTolerance(Robot.frontLimelight.getTY(), Constants.SWERVE.TRANSLATION_OFFSET, Constants.SWERVE.TRANSLATION_TOLERANCE))
            & hangTimer >= Constants.SWERVE.GAMEPIECE_TRACKING_LOST_TARGET_ALLOWED_LOOPS;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.drive.drive(new Translation2d(), 0, false, false);
        System.out.println(interrupted);
    }
}
