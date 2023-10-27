package com.team2357.frc2023.commands.slide;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class IntakeSlideRetractCommandGroup extends SequentialCommandGroup {
    public IntakeSlideRetractCommandGroup() {
        super(
            new IntakeSlidePartialRetractCommand(),
            new IntakeSlideFinishRetractCommand()  
        );
    }
}
