package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.commands.IntakeStowCommandGroup;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerStopMotorsCommand extends CommandBase {
    public IntakeRollerStopMotorsCommand() {
        addRequirements(Robot.intakeRoller);
    }

    @Override
    public void initialize() {
        Robot.intakeRoller.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
