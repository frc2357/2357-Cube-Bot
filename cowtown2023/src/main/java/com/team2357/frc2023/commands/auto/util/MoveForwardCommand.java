package com.team2357.frc2023.commands.auto.util;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.state.RobotState;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveForwardCommand extends CommandBase {
    private Translation2d m_translation;

    public MoveForwardCommand(double speed) {
        m_translation = new Translation2d(0.0, speed);
        addRequirements(Robot.drive);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void execute() {
        Robot.drive.drive(m_translation, 0, true, false);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.drive.drive(new Translation2d(), 0, RobotState.isFieldRelative(), false);
    }
}
