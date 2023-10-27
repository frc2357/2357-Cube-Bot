package com.team2357.frc2023.commands.slide;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlidePartialRetractCommand extends CommandBase {
    public IntakeSlidePartialRetractCommand() {
        addRequirements(Robot.slide);
    }

    @Override
    public void initialize() {
        Robot.slide.retract();
    }

    @Override
    public boolean isFinished() {
        return Robot.slide.isRetracted();
    }
}
