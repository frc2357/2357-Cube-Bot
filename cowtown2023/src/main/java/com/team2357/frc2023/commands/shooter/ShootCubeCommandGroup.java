package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Robot;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerIndexCubeCommand;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootCubeCommandGroup extends SequentialCommandGroup {
    
    public enum SHOOTER_RPMS {
        LOW(Constants.SHOOTER.TOP_MOTOR_LOW_RPMS, Constants.SHOOTER.BOTTOM_MOTOR_LOW_RPMS),
        MID(Constants.SHOOTER.TOP_MOTOR_MID_RPMS, Constants.SHOOTER.BOTTOM_MOTOR_MID_RPMS),
        HIGH(Constants.SHOOTER.TOP_MOTOR_HIGH_RPMS, Constants.SHOOTER.BOTTOM_MOTOR_HIGH_RPMS),
        FAR(Constants.SHOOTER.TOP_MOTOR_FAR_RPMS, Constants.SHOOTER.BOTTOM_MOTOR_FAR_RPMS);

        public double top, bottom;

        SHOOTER_RPMS(double topRPMs, double bottomRPMs) {
            this.top = topRPMs;
            this.bottom = bottomRPMs;
        }
    }

    private SHOOTER_RPMS m_rpms;

    public ShootCubeCommandGroup(SHOOTER_RPMS rpms) {
        super(
            new ShooterSetRPMsCommand(rpms.top, rpms.bottom), 
            new IntakeRollerIndexCubeCommand()
        );
    }



}
