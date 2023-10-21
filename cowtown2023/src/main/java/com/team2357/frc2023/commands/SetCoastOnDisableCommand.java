package com.team2357.frc2023.commands;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetCoastOnDisableCommand extends CommandBase{

    Timer m_timer;

    @Override
    public void initialize() {
        m_timer = new Timer();
        m_timer.start();
    }

    @Override
    public boolean isFinished() {
        return m_timer.hasElapsed(Constants.SWERVE.TIME_TO_COAST_SECONDS);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.drive.setBrakeMode(false);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
