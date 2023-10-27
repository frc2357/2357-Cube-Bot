package com.team2357.frc2023.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import com.team2357.frc2023.commands.auto.util.MoveForwardCommand;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerStopMotorsCommand;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup;
import com.team2357.frc2023.commands.shooter.ShooterStopMotorsCommand;
import com.team2357.frc2023.commands.shooter.ShootCubeCommandGroup.SHOOTER_RPMS;

public class ScoreMidMoveForward extends SequentialCommandGroup {
    public ScoreMidMoveForward() {
        addCommands(
            new ParallelDeadlineGroup(
                new WaitCommand(2),
                new ShootCubeCommandGroup(SHOOTER_RPMS.MID)
            ),
            new ParallelCommandGroup(
                new ShooterStopMotorsCommand(),
                new IntakeRollerStopMotorsCommand()
            ),
            new ParallelDeadlineGroup(
                new WaitCommand(2.5),
                new MoveForwardCommand(1.75)
            )
        );
    }

    @Override
    public String toString() {
        return "Mid/Forward";
    }
}
