package com.team2357.frc2023.commands;

import com.swervelib.SwerveController;
import com.team2357.frc2023.controls.SwerveDriveControls;
import com.team2357.frc2023.state.RobotState;
import com.team2357.frc2023.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDriveCommand extends CommandBase {
    private final SwerveDriveSubsystem m_swerve;
    private final SwerveDriveControls m_controls;

    public DefaultDriveCommand(SwerveDriveSubsystem swerve,
            SwerveDriveControls controls) {
        m_swerve = swerve;
        m_controls = controls;

        addRequirements(swerve);
    }

    @Override
    public void execute() {
        ChassisSpeeds targetSpeeds = m_swerve.getTargetSpeeds(m_controls.getX(), m_controls.getY(),
                new Rotation2d(m_controls.getRotation() * Math.PI));

        m_swerve.drive(SwerveController.getTranslation2d(targetSpeeds), targetSpeeds.omegaRadiansPerSecond, true, false);
    }

    @Override
    public void end(boolean interrupted) {
        m_swerve.drive(new Translation2d(), 0, RobotState.isFieldRelative(), false);
    }
}
