package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShooterRunCommand extends CommandBase {
    
    private double m_topPercentOutput, m_bottomPercentOutput;

    public ShooterRunCommand(double topPercentOutput, double bottomPercentOutput) {
        m_topPercentOutput = topPercentOutput;
        m_bottomPercentOutput = bottomPercentOutput;
        addRequirements(Robot.shooter);
    }

    @Override
    public void initialize() {
        Robot.shooter.set(m_topPercentOutput, m_bottomPercentOutput);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopShooterMotors();
    }
    
}
