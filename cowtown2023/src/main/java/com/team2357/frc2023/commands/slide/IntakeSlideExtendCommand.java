package com.team2357.frc2023.commands.slide;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlideExtendCommand extends CommandBase {
    public IntakeSlideExtendCommand() {
        addRequirements(Robot.slide);
    }

    @Override
    public void initialize() {
        Robot.slide.extend();
    }

    @Override
    public boolean isFinished() {
        return Robot.slide.isExtended();
    }
}
