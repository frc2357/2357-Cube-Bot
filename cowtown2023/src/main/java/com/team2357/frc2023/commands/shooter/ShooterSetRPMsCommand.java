package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterSetRPMsCommand extends CommandBase {
    private double m_topRPMs, m_bottomRPMs;

    public ShooterSetRPMsCommand(double topRPMs, double bottomRPMs) {
        m_topRPMs = topRPMs;
        m_bottomRPMs = bottomRPMs;
        addRequirements(Robot.shooter);
    }

    @Override
    public void initialize() {
        Robot.shooter.setRPMs(m_topRPMs, m_bottomRPMs);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // Call ShooterStopMotorsCommand to stop the motors
    }
}
