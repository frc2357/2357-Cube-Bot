package com.team2357.frc2023.commands;

import com.team2357.frc2023.controls.SwerveDriveControls;
import com.team2357.frc2023.state.RobotState;
import com.team2357.frc2023.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDriveCommand extends CommandBase {
    private final SwerveDriveSubsystem m_drivetrainSubsystem;
    private final SwerveDriveControls m_controls;

    public DefaultDriveCommand(SwerveDriveSubsystem drivetrainSubsystem,
            SwerveDriveControls controls) {
        m_drivetrainSubsystem = drivetrainSubsystem;
        m_controls = controls;

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() {
        m_drivetrainSubsystem.drive(
                new Translation2d(m_controls.getY(), m_controls.getX()),
                m_controls.getRotation(),
                RobotState.isFieldRelative(), false);
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrainSubsystem.drive(new Translation2d(), 0, RobotState.isFieldRelative(), false);
    }
}
