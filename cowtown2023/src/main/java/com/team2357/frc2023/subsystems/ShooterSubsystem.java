package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;
import com.team2357.frc2023.Constants.SHOOTER;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.util.RobotMath;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private CANSparkMax m_topShooterMotor;
    private CANSparkMax m_bottomShooterMotor;
    private SHOOTER m_config;
    private boolean m_isClosedLoopEnabled;
    
    public ShooterSubsystem() {
        m_topShooterMotor = new CANSparkMax(Constants.CAN_ID.TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_bottomShooterMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_isClosedLoopEnabled = false;
    }

    public void configure(SHOOTER config){
        m_config = config;

        m_topShooterMotor.setInverted(m_config.TOP_MOTOR_INVERTED);
        m_bottomShooterMotor.setInverted(m_config.BOTTOM_MOTOR_INVERTED);

        m_topShooterMotor.setSmartCurrentLimit(m_config.TOP_MOTOR_LIMIT_AMPS);
        m_bottomShooterMotor.setSmartCurrentLimit(m_config.BOTTOM_MOTOR_LIMIT_AMPS);

        m_topShooterMotor.getPIDController().setP(m_config.TOP_MOTOR_PID_P);
        m_topShooterMotor.getPIDController().setI(m_config.TOP_MOTOR_PID_I);
        m_topShooterMotor.getPIDController().setD(m_config.TOP_MOTOR_PID_D);

        m_bottomShooterMotor.getPIDController().setP(m_config.BOTTOM_MOTOR_PID_P);
        m_bottomShooterMotor.getPIDController().setI(m_config.BOTTOM_MOTOR_PID_I);
        m_bottomShooterMotor.getPIDController().setD(m_config.BOTTOM_MOTOR_PID_D);
    }

    public void runShooter(double topPercentOutput, double bottomPercentOutput) {
        m_topShooterMotor.set(topPercentOutput);
        m_bottomShooterMotor.set(bottomPercentOutput);
    }

    public void stopShooter() {
        m_topShooterMotor.set(0.0);
        m_bottomShooterMotor.set(0.0);
        m_isClosedLoopEnabled = false;
    }

     // {degrees, top shooter percentage, bottom shooter percentage}
    private static final double[][] degreesToPercentage = {
        //these degrees need to be tuned to find what degree we want for a distance
        { 20, 0, 0 },
        { 15, 0, 0 },
        { 10, 0, 0 },
        { 5, 0, 0 },
        { 0, 0, 0 },
        { -5.0, 0, 0 },
        { -10, 0, 0 },
        { -15, 0, 0 },
        { -20, 0, 0 }
    };

    @Override
    public void periodic(){
        if(m_isClosedLoopEnabled){

        }

    }

    

}
