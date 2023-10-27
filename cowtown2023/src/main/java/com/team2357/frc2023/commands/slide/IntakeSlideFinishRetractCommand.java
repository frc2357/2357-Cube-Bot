package com.team2357.frc2023.commands.slide;

import com.team2357.frc2023.Robot;
import com.team2357.lib.util.Utility;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlideFinishRetractCommand extends CommandBase {
    public IntakeSlideFinishRetractCommand() {
        addRequirements(Robot.slide);
    }

    @Override
    public void initialize() {
        Robot.slide.setSlideAxisSpeed(0.1);
    }

    @Override
    public boolean isFinished() {
        return (Utility.isWithinTolerance(Robot.slide.getMasterRPMs(), 0, 15)
                && Utility.isWithinTolerance(Robot.slide.getFollowerRPMs(), 0, 15));
    }

    @Override
    public void end(boolean interrupted) {
        Robot.slide.stopSlideMotors();
    }
}
