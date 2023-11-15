package com.team2357.frc2023.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;
import com.team2357.lib.util.Utility;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeRollerSubsystem extends SubsystemBase {

    private CANSparkMax m_topIntakeMotor;
    private CANSparkMax m_bottomIntakeMotor;

    private SparkMaxPIDController m_topPIDController;
    private SparkMaxPIDController m_bottomPIDController;

    public IntakeRollerSubsystem() {
        m_topIntakeMotor = new CANSparkMax(Constants.CAN_ID.TOP_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
        m_bottomIntakeMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
        configure();
    }

    public void configure() {
        m_topIntakeMotor.setInverted(Constants.INTAKE_ROLLER.TOP_MOTOR_INVERTED);
        m_bottomIntakeMotor.setInverted(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INVERTED);

        m_topIntakeMotor.enableVoltageCompensation(12);
        m_topIntakeMotor.setIdleMode(Constants.INTAKE_ROLLER.MOTOR_IDLE_MODE);
        m_topIntakeMotor.setSmartCurrentLimit(Constants.INTAKE_ROLLER.TOP_MOTOR_STALL_LIMIT_AMPS,
        Constants.INTAKE_ROLLER.TOP_MOTOR_FREE_LIMIT_AMPS);

        m_bottomIntakeMotor.enableVoltageCompensation(12);
        m_bottomIntakeMotor.setIdleMode(Constants.INTAKE_ROLLER.MOTOR_IDLE_MODE);
        m_bottomIntakeMotor.setSmartCurrentLimit(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_STALL_LIMIT_AMPS,
        Constants.INTAKE_ROLLER.BOTTOM_MOTOR_FREE_LIMIT_AMPS);

        m_topPIDController = m_topIntakeMotor.getPIDController();
        m_bottomPIDController = m_bottomIntakeMotor.getPIDController();

        m_topPIDController.setP(Constants.INTAKE_ROLLER.TOP_MOTOR_P);
        m_topPIDController.setI(Constants.INTAKE_ROLLER.TOP_MOTOR_I);
        m_topPIDController.setD(Constants.INTAKE_ROLLER.TOP_MOTOR_D);
        m_topPIDController.setFF(Constants.INTAKE_ROLLER.TOP_MOTOR_FF);

        m_bottomPIDController.setP(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_P);
        m_bottomPIDController.setI(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_I);
        m_bottomPIDController.setD(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_D);
        m_bottomPIDController.setFF(Constants.INTAKE_ROLLER.BOTTOM_MOTOR_FF);

        m_topPIDController.setOutputRange(-1, 1);
        m_bottomPIDController.setOutputRange(-1, 1);

    }

    public void eject() {
        setRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_EJECT_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_EJECT_RPMS);
        // setPercentOutput(Constants.INTAKE_ROLLER.TOP_MOTOR_EJECT_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_EJECT_RPMS);
    }

    public void intake() {
        setRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_INTAKE_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INTAKE_RPMS);
        // setPercentOutput(Constants.INTAKE_ROLLER.TOP_MOTOR_INTAKE_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INTAKE_RPMS);
    }

    public void index() {
        setRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_INDEX_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INDEX_RPMS);
        // setPercentOutput(Constants.INTAKE_ROLLER.TOP_MOTOR_INDEX_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_INDEX_RPMS);
    }

    public void roll() {
        setRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_ROLL_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_ROLL_RPMS);
        // setPercentOutput(Constants.INTAKE_ROLLER.TOP_MOTOR_ROLL_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_ROLL_RPMS);
    }

    public void reverseIndex() {
        setRPMs(Constants.INTAKE_ROLLER.TOP_MOTOR_REVERSE_INDEX_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_REVERSE_INDEX_RPMS);
        // setPercentOutput(Constants.INTAKE_ROLLER.TOP_MOTOR_ROLL_RPMS, Constants.INTAKE_ROLLER.BOTTOM_MOTOR_ROLL_RPMS);
    }

    public void stop() {
        runIntake(0.0, 0.0);
    }

    public void setPercentOutput(double topPO, double bottomPO) {
        // m_topIntakeMotor.set(topPO);
        // m_bottomIntakeMotor.set(bottomPO);
    }

    public void setRPMs(double topRPMs, double bottomRPMs) {
        // m_topPIDController.setReference(topRPMs, ControlType.kVelocity);
        // m_bottomPIDController.setReference(bottomRPMs, ControlType.kVelocity);
    }

    public boolean isAtAmps(int amps) {
        return m_topIntakeMotor.getOutputCurrent() >= amps || m_bottomIntakeMotor.getOutputCurrent() >= amps;
    }

    public void runIntake(double topPercentOutput, double bottomPercentOutput) {
        // m_topIntakeMotor.set(topPercentOutput);
        // m_bottomIntakeMotor.set(bottomPercentOutput);
    }

    public void setAxisRollerSpeeds(double topValue, double bottomValue) {
        double topSpeed = (-topValue) * Constants.INTAKE_ROLLER.TOP_AXIS_MAX_SPEED;
        double bottomSpeed = (-bottomValue) * Constants.INTAKE_ROLLER.BOTTOM_AXIS_MAX_SPEED;
        // m_topIntakeMotor.set(topSpeed);
        // m_bottomIntakeMotor.set(bottomSpeed);
    }

    public boolean hasDroppedRPMs(double topRPMs, double bottomRPMs,
            int drop) {
        boolean topDrop = Utility.isWithinTolerance(m_topIntakeMotor.getEncoder().getVelocity(), topRPMs - drop, 50); 
        boolean bottomDrop = Utility.isWithinTolerance(m_bottomIntakeMotor.getEncoder().getVelocity(), bottomRPMs - drop, 50); 
        return topDrop || bottomDrop;
    }

}
