package com.team2357.frc2023.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeRollerSubsystem extends SubsystemBase {

    private CANSparkMax m_topIntakeMotor;
    private CANSparkMax m_bottomIntakeMotor;

    public IntakeRollerSubsystem() {
        m_topIntakeMotor = new CANSparkMax(Constants.CAN_ID.TOP_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
        m_bottomIntakeMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
    }

    public void configure() {
        m_topIntakeMotor.setInverted(Constants.INTAKE_ROLLER.TOP_MOTOR_INVERTED);
        m_bottomIntakeMotor.setInverted(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INVERTED);

        m_topIntakeMotor.setIdleMode(Constants.INTAKE_ROLLER.MOTOR_IDLE_MODE);
        m_topIntakeMotor.setSmartCurrentLimit(Constants.INTAKE_ROLLER.TOP_MOTOR_STALL_LIMIT_AMPS,
        Constants.INTAKE_ROLLER.TOP_MOTOR_FREE_LIMIT_AMPS);

        m_bottomIntakeMotor.setIdleMode(Constants.INTAKE_ROLLER.MOTOR_IDLE_MODE);
        m_bottomIntakeMotor.setSmartCurrentLimit(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_STALL_LIMIT_AMPS,
        Constants.INTAKE_ROLLER.BOTTOM_MOTOR_FREE_LIMIT_AMPS);

    }

    public void eject() {
        runIntake(Constants.INTAKE_ROLLER.TOP_MOTOR_EJECT_PERCENT_OUTPUT, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_EJECT_PERCENT_OUTPUT);
    }

    public void intake() {
        runIntake(Constants.INTAKE_ROLLER.TOP_MOTOR_INTAKE_PERCENT_OUTPUT, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INTAKE_PERCENT_OUTPUT);
    }

    public void index() {
        runIntake(Constants.INTAKE_ROLLER.TOP_MOTOR_INDEX_PERCENT_OUTPUT, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INDEX_PERCENT_OUTPUT);
    }

    public void roll() {
        runIntake(Constants.INTAKE_ROLLER.TOP_MOTOR_ROLL_PERCENT_OUTPUT, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_ROLL_PERCENT_OUTPUT);
    }

    public void stop() {
        runIntake(0.0, 0.0);
    }

    public void runIntake(double topPercentOutput, double bottomPercentOutput) {
        m_topIntakeMotor.set(topPercentOutput);
        m_bottomIntakeMotor.set(bottomPercentOutput);
    }

}
