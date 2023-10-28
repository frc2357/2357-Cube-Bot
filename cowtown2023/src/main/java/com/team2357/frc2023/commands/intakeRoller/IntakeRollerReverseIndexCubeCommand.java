package com.team2357.frc2023.commands.intakeRoller;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeRollerReverseIndexCubeCommand extends CommandBase {

    private long m_startMillis;

    public IntakeRollerReverseIndexCubeCommand() {
        addRequirements(Robot.intakeRoller);
    }

    @Override
    public void initialize() {
        m_startMillis = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        Robot.intakeRoller.reverseIndex();
    }

    @Override
    public boolean isFinished() {
        return (System.currentTimeMillis() - m_startMillis == 250)
                && Robot.intakeRoller.hasDroppedRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_REVERSE_INDEX_RPMS,
                        Constants.INTAKE_ROLLER.BOTTOM_MOTOR_REVERSE_INDEX_RPMS,
                        Constants.INTAKE_ROLLER.REVERSE_INDEX_RPM_DROP);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intakeRoller.stop();
    }

}
