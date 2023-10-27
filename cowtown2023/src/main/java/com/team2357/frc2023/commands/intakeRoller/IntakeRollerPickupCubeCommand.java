package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerPickupCubeCommand extends CommandBase {

    public IntakeRollerPickupCubeCommand() {
        addRequirements(Robot.intakeRoller);
    }

    @Override
    public void execute() {
        Robot.intakeRoller.intake();
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
