package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerEjectCubeCommand extends CommandBase {

    public IntakeRollerEjectCubeCommand() {
        addRequirements(Robot.intakeRoller);
    }

    @Override
    public void execute() {
        Robot.intakeRoller.eject();
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
