package com.team2357.frc2023.commands;

import com.team2357.frc2023.commands.intakeRoller.IntakeRollerPickupCubeCommand;
import com.team2357.frc2023.commands.slide.IntakeSlidePartialExtendCommand;
import com.team2357.frc2023.commands.slide.IntakeSlideExtendCommandGroup;
import com.team2357.frc2023.commands.slide.IntakeSlideFinishRetractCommand;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeDeployCommandGroup extends ParallelCommandGroup {
    public IntakeDeployCommandGroup() {
        super(
            new IntakeSlideExtendCommandGroup(),
            new IntakeRollerPickupCubeCommand()
        );
    }
}
