package com.team2357.frc2023.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2023.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private CANSparkMax m_topShooterMotor;
    private CANSparkMax m_bottomShooterMotor;
    
    public ShooterSubsystem() {
        m_topShooterMotor = new CANSparkMax(Constants.CAN_ID.TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
        m_bottomShooterMotor = new CANSparkMax(Constants.CAN_ID.BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
    }

    public void runShooter(double topPercentOutput, double bottomPercentOutput) {
        m_topShooterMotor.set(topPercentOutput);
        m_bottomShooterMotor.set(bottomPercentOutput);
    }

    public void stopShooter() {
        m_topShooterMotor.set(0.0);
        m_bottomShooterMotor.set(0.0);
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


}
