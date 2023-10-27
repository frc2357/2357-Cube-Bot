package com.team2357.frc2023.commands.human.panic;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.controls.AxisInterface;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeSlideAxisCommand extends CommandBase {
    private AxisInterface m_axis;

    public IntakeSlideAxisCommand(AxisInterface axis) {
        m_axis = axis;
        addRequirements(Robot.slide);
    }

    @Override
    public void execute() {
        double axisValue = m_axis.getValue();
        Robot.slide.setSlideAxisSpeed(axisValue);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.slide.stopSlideMotors();
    }
}
