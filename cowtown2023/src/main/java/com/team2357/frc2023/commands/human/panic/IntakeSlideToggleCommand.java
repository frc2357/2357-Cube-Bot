package com.team2357.frc2023.commands.human.panic;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlideToggleCommand extends CommandBase {
    public IntakeSlideToggleCommand() {
        addRequirements(Robot.slide);
    }

    @Override
    public void initialize() {
        if (Robot.slide.isExtended() || Robot.slide.isExtending()) {
            Robot.slide.retract();
        } else {
            Robot.slide.extend();
        }
    }

    @Override
    public boolean isFinished() {
        return Robot.slide.isExtended() || Robot.slide.isRetracted();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.slide.stopSlideMotors();
    }
}
