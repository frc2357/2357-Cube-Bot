package com.team2357.frc2023.commands;

import com.team2357.frc2023.Robot;
import com.team2357.frc2023.commands.slide.IntakeSlideRetractCommandGroup;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class IntakeStowCommandGroup extends ParallelCommandGroup {
    public IntakeStowCommandGroup() {
        super(
            new IntakeSlideRetractCommandGroup(),
            new InstantCommand(() -> {
                Robot.intakeRoller.stop();
            })
        );
    }
}
