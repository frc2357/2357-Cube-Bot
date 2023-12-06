package com.team2357.frc2023;

import com.team2357.frc2023.commands.auto.ScoreHigh;
import com.team2357.frc2023.commands.auto.ScoreHighBalance;
import com.team2357.frc2023.commands.auto.ScoreHighMoveForward;
import com.team2357.frc2023.commands.auto.ScoreMid;
import com.team2357.frc2023.commands.auto.ScoreMidBalance;
import com.team2357.frc2023.commands.auto.ScoreMidMoveForward;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoCommandChooser {
    private Command[] m_autoCommands;
    private SendableChooser<Command> m_chooser;

    public AutoCommandChooser() {

        m_autoCommands = new Command[] {
            new ScoreHighBalance(),
            new ScoreHighMoveForward(),
            new ScoreHigh(),
            new ScoreMidBalance(),
            new ScoreMidMoveForward(),
            new ScoreMid(),
        };

        m_chooser = new SendableChooser<>();

        m_chooser.setDefaultOption("None", new WaitCommand(0));
        for (Command autoCommand : m_autoCommands) {
            m_chooser.addOption(autoCommand.toString(), autoCommand);
        }

        SmartDashboard.putData("Auto chooser", m_chooser);
    }

    public Command getSelectedAutoCommand() {
        return m_chooser.getSelected();
    }
}
