package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerIndexCubeCommand extends CommandBase {

    public IntakeRollerIndexCubeCommand() {
        addRequirements(Robot.intake);
    }

    @Override
    public void execute() {
        Robot.intake.index();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intake.stop();
    }
    
}
