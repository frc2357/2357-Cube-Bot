package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerRollCubeCommand extends CommandBase {

    public IntakeRollerRollCubeCommand() {
        addRequirements(Robot.intakeRoller);
    }

    @Override
    public void execute() {
        Robot.intakeRoller.roll();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intakeRoller.stop();
    }
    
}
