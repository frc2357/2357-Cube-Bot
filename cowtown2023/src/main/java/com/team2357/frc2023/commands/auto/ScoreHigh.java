package com.team2357.frc2023.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.team2357.frc2023.commands.intakeRoller.IntakeRollerStopMotorsCommand;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup;
import com.team2357.frc2023.commands.shooter.ShooterStopMotorsCommand;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup.SHOOTER_RPMS;

public class ScoreHigh extends SequentialCommandGroup {
    public ScoreHigh() {
        addCommands(
            new ParallelDeadlineGroup(
                new WaitCommand(2),
                new ShootCubeCommandGroup(SHOOTER_RPMS.HIGH)
            ),
            new ParallelCommandGroup(
                new ShooterStopMotorsCommand(),
                new IntakeRollerStopMotorsCommand()
            )
        );
    }

    @Override
    public String toString() {
        return "High";
    }
}
