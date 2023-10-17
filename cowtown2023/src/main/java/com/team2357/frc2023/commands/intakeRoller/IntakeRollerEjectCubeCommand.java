package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerEjectCubeCommand extends CommandBase {

    public IntakeRollerEjectCubeCommand() {
        addRequirements(Robot.intake);
    }

    @Override
    public void execute() {
        Robot.intake.eject();
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
