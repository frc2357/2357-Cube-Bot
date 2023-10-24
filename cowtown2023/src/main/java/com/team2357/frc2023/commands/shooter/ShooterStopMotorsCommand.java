package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterStopMotorsCommand extends CommandBase {
    public ShooterStopMotorsCommand() {
        addRequirements(Robot.shooter);
    }

    @Override
    public void initialize() {
        Robot.shooter.stopShooterMotors();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
