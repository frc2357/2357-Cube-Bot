package com.team2357.frc2023.commands.slide;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeSlideExtendCommandGroup extends SequentialCommandGroup {
    public IntakeSlideExtendCommandGroup() {
        super(
            new IntakeSlidePartialExtendCommand(),
            new IntakeSlideFinishExtendCommand()  
        );
    }
}
