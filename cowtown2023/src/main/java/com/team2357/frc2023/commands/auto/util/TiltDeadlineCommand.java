package com.team2357.frc2023.commands.auto.util;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TiltDeadlineCommand extends CommandBase {
    private double m_maxDegrees = 0.0;
    private double m_tolerance = 1.0;

    @Override
    public void initialize() {
        System.out.println("----------- Start balance");
    }

    @Override
    public boolean isFinished() {
        double rollDegrees = Math.abs(Robot.drive.getRoll().getDegrees());
        System.out.println("rollDegrees: " + rollDegrees);

        if (rollDegrees < m_maxDegrees - m_tolerance) {
            System.out.println("----------- FINISHED");
            return true;
        }

        m_maxDegrees = Math.max(m_maxDegrees, rollDegrees);
        return false;
    }
}
