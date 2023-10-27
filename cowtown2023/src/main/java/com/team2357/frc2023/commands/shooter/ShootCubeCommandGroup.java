package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerIndexCubeCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerStopMotorsCommand;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

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
            new SequentialCommandGroup(
                new ShooterSetRPMsCommand(rpms.top, rpms.bottom),
                new IntakeRollerIndexCubeCommand()
            ).finallyDo((interrupted) -> {
                new ParallelCommandGroup(
                    new ShooterStopMotorsCommand(),
                    new IntakeRollerStopMotorsCommand()
                ).schedule();
            })
        );

        // super(
        // new ShooterSetRPMsCommand(rpms.top, rpms.bottom),
        // new IntakeRollerIndexCubeCommand()
        // );
    }

}
