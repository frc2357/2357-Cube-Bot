package com.team2357.frc2023.commands.shooter;

import com.team2357.frc2023.Constants;
import com.team2357.frc2023.commands.intakeRoller.IntakeRollerReverseIndexCubeCommand;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShooterIntakeCommandGroup extends SequentialCommandGroup {
    public ShooterIntakeCommandGroup() {
        super(
            new ParallelDeadlineGroup(
                new IntakeRollerReverseIndexCubeCommand(), 
                new ShooterSetRPMsCommand(Constants.SHOOTER.TOP_MOTOR_INTAKE_RPMS,
                Constants.SHOOTER.BOTTOM_MOTOR_INTAKE_RPMS)
            ).andThen(new ShooterStopMotorsCommand())
        );
    }
}
