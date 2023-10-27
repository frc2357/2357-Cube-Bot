package com.team2357.frc2023.commands.slide;

import com.team2357.frc2023.Robot;
import com.team2357.lib.util.Utility;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlideFinishExtendCommand extends CommandBase {
    private long startMillis;

    public IntakeSlideFinishExtendCommand() {
        addRequirements(Robot.slide);
    }

    @Override
    public void initialize() {
        startMillis = System.currentTimeMillis();
        Robot.slide.setSlideAxisSpeed(-0.2);
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - startMillis > 50)
                && (Utility.isWithinTolerance(Robot.slide.getMasterRPMs(), 0, 15)
                        && Utility.isWithinTolerance(Robot.slide.getFollowerRPMs(), 0, 15));
    }

    @Override
    public void end(boolean interrupted) {
        Robot.slide.stopSlideMotors();
    }
}
