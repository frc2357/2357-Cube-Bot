/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Before Running:
 * Open shuffleBoard, select File->Load Layout and select the 
 * shuffleboard.json that is in the root directory of this example
 */

/**
 * REV Smart Motion Guide
 * 
 * The SPARK MAX includes a new control mode, REV Smart Motion which is used to 
 * control the position of the motor, and includes a max velocity and max 
 * acceleration parameter to ensure the motor moves in a smooth and predictable 
 * way. This is done by generating a motion profile on the fly in SPARK MAX and 
 * controlling the velocity of the motor to follow this profile.
 * 
 * Since REV Smart Motion uses the velocity to track a profile, there are only 
 * two steps required to configure this mode:
 *    1) Tune a velocity PID loop for the mechanism
 *    2) Configure the smart motion parameters
 * 
 * Tuning the Velocity PID Loop
 * 
 * The most important part of tuning any closed loop control such as the velocity 
 * PID, is to graph the inputs and outputs to understand exactly what is happening. 
 * For tuning the Velocity PID loop, at a minimum we recommend graphing:
 *
 *    1) The velocity of the mechanism (‘Process variable’)
 *    2) The commanded velocity value (‘Setpoint’)
 *    3) The applied output
 *
 * This example will use ShuffleBoard to graph the above parameters. Make sure to
 * load the shuffleboard.json file in the root of this directory to get the full
 * effect of the GUI layout.
 */
public class Robot extends TimedRobot {
  // All PID values are for the intake slide mechanism
  private static final int deviceID = 1;
  private CANSparkMax m_masterSlideMotor, m_followerSlideMotor, m_topShooterMotor, m_bottomShooterMotor, m_topIntakeMotor, m_bottomIntakeMotor;
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;

  public double topShooterLowPO, bottomShooterLowPO, topShooterMidPO, bottomShooterMidPO, topShooterHighPO, bottomShooterHighPO, topShooterFarPO, bottomShooterFarPO;

  public double topIntakePickupPO, bottomIntakePickupPO, topIntakeEjectPO, bottomIntakeEjectPO, topIntakeIndexPO, bottomIntakeIndexPO;

  // can ids
  final int BOTTOM_INTAKE_ROLLER_MOTOR_ID = 19;
  final int TOP_INTAKE_ROLLER_MOTOR_ID = 20;
  final int MASTER_INTAKE_SLIDE_MOTOR_ID = 21;
  final int FOLLOWER_INTAKE_SLIDE_MOTOR_ID = 22;
  final int TOP_SHOOTER_MOTOR_ID = 23;
  final int BOTTOM_SHOOTER_MOTOR_ID = 24;

  XboxController controller = new XboxController(0);

  // TODO: Add config and current limiting

  @Override
  public void robotInit() {
    // initialize motor
    m_masterSlideMotor = new CANSparkMax(MASTER_INTAKE_SLIDE_MOTOR_ID, MotorType.kBrushless);
    m_followerSlideMotor = new CANSparkMax(FOLLOWER_INTAKE_SLIDE_MOTOR_ID, MotorType.kBrushless);

    m_topShooterMotor = new CANSparkMax(TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
    m_bottomShooterMotor = new CANSparkMax(BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);

    m_topIntakeMotor = new CANSparkMax(TOP_INTAKE_MOTOR_ID, MotorType.kBrushless);
    m_bottomIntakeMotor = new CANSparkMax(BOTTOM_INTAKE_MOTOR_ID, MotorType.kBrushless);

    m_masterSlideMotor.restoreFactoryDefaults();
    m_followerSlideMotor.restoreFactoryDefaults();
    m_masterSlideMotor.setInverted(false);
    m_followerSlideMotor.setInverted(false);

    m_followerSlideMotor.follow(m_masterSlideMotor);

    m_masterSlideMotor.setSmartCurrentLimit(10, 10);
    m_masterSlideMotor.setIdleMode(IdleMode.kBrake);


    m_topShooterMotor.restoreFactoryDefaults();
    m_bottomShooterMotor.restoreFactoryDefaults();
    m_topShooterMotor.setInverted(false);
    m_bottomShooterMotor.setInverted(false);

    m_topShooterMotor.setSmartCurrentLimit(10, 10);
    m_bottomIntakeMotor.setSmartCurrentLimit(10, 10);
    m_topShooterMotor.setIdleMode(IdleMode.kCoast);
    m_bottomShooterMotor.setIdleMode(IdleMode.kCoast);
    m_topShooterMotor.setOpenLoopRampRate(0.25);
    m_bottomShooterMotor.setOpenLoopRampRate(0.25);

    
    m_topIntakeMotor.restoreFactoryDefaults();
    m_bottomIntakeMotor.restoreFactoryDefaults();
    m_topIntakeMotor.setInverted(false);
    m_bottomIntakeMotor.setInverted(false);

    m_topIntakeMotor.setSmartCurrentLimit(10, 10);
    m_bottomIntakeMotor.setSmartCurrentLimit(10, 10);
    m_topIntakeMotor.setIdleMode(IdleMode.kCoast);
    m_bottomIntakeMotor.setIdleMode(IdleMode.kCoast);
    m_topIntakeMotor.setOpenLoopRampRate(0.25);
    m_bottomIntakeMotor.setOpenLoopRampRate(0.25);

    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */

    // initialze PID controller and encoder objects
    m_pidController = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000156; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    // Smart Motion Coefficients
    maxVel = 2000; // RPM
    maxAcc = 1500;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    /**
     * Smart Motion coefficients are set on a SparkMaxPIDController object
     * 
     * - setSmartMotionMaxVelocity() will limit the velocity in RPM of
     * the pid controller in Smart Motion mode
     * - setSmartMotionMinOutputVelocity() will put a lower bound in
     * RPM of the pid controller in Smart Motion mode
     * - setSmartMotionMaxAccel() will limit the acceleration in RPM^2
     * of the pid controller in Smart Motion mode
     * - setSmartMotionAllowedClosedLoopError() will set the max allowed
     * error for the pid controller in Smart Motion mode
     */
    int smartMotionSlot = 0;
    m_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    m_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    m_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);

    topShooterLowPO = 0;
    topShooterMidPO = 0;
    topShooterHighPO = 0;
    topShooterFarPO = 0;
    bottomShooterLowPO = 0;
    bottomShooterMidPO = 0;
    bottomShooterHighPO = 0;
    bottomShooterFarPO = 0;

    SmartDashboard.putNumber("Top Shooter Low PO", topShooterLowPO);
    SmartDashboard.putNumber("Bottom Shooter Low PO", bottomShooterLowPO);
    SmartDashboard.putNumber("Top Shooter Mid PO", topShooterMidPO);
    SmartDashboard.putNumber("Bottom Shooter Mid PO", bottomShooterMidPO);
    SmartDashboard.putNumber("Top Shooter High PO", topShooterHighPO);
    SmartDashboard.putNumber("Bottom Shooter High PO", bottomShooterHighPO);
    SmartDashboard.putNumber("Top Shooter Far PO", topShooterLFaPO);
    SmartDashboard.putNumber("Bottom Shooter Far PO", bottomShooterFarPO);
    
    topIntakePickupPO = 0;
    bottomIntakePickupPO = 0;
    topIntakeEjectPO = 0;
    bottomIntakeEjectPO = 0;
    topIntakeIndexPO = 0;
    bottomIntakeIndexPO = 0;

    SmartDashboard.putNumber("Top Intake Pickup PO", topIntakePickupPO);
    SmartDashboard.putNumber("Bottom Intake Pickup PO", bottomIntakePickupPO);
    SmartDashboard.putNumber("Top Intake Eject PO", topIntakeEjectPO);
    SmartDashboard.putNumber("Bottom Intake Eject PO", bottomIntakeEjectPO);
    SmartDashboard.putNumber("Top Intake Index PO", topIntakeIndexPO);
    SmartDashboard.putNumber("Bottom Intake Index PO", bottomIntakeIndexPO);
    
  }

  @Override
  public void teleopPeriodic() {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double maxV = SmartDashboard.getNumber("Max Velocity", 0);
    double minV = SmartDashboard.getNumber("Min Velocity", 0);
    double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
    double allE = SmartDashboard.getNumber("Allowed Closed Loop Error", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { m_pidController.setP(p); kP = p; }
    if((i != kI)) { m_pidController.setI(i); kI = i; }
    if((d != kD)) { m_pidController.setD(d); kD = d; }
    if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      m_pidController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }
    if((maxV != maxVel)) { m_pidController.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
    if((minV != minVel)) { m_pidController.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
    if((maxA != maxAcc)) { m_pidController.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
    if((allE != allowedErr)) { m_pidController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }

    double setPoint, processVariable;
    boolean mode = SmartDashboard.getBoolean("Mode", false);
    if(mode) {
      setPoint = SmartDashboard.getNumber("Set Velocity", 0);
      m_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
      processVariable = m_encoder.getVelocity();
    } else {
      setPoint = SmartDashboard.getNumber("Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      m_pidController.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
      processVariable = m_encoder.getPosition();
    }
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", m_motor.getAppliedOutput());
  }

  @Override
  public void testPeriodic() {
    m_masterSlideMotor.set(deadband(controller.getLeftY(), 0.1) * 0.25);
    if (controller.getBackButtonPressed()) {
      m_encoder.setPosition(0);
    }

    if (controller.getAButtonPressed()) {
      m_topShooterMotor.set(topShooterLowPO);
      m_bottomShooterMotor.set(bottomShooterLowPO);
    } else if (controller.getXButtonPressed()) {
      m_topShooterMotor.set(topShooterMidPO);
      m_bottomShooterMotor.set(bottomShooterMidPO);
    } else if (controller.getYButtonPressed()) {
      m_topShooterMotor.set(topShooterHighPO);
      m_bottomShooterMotor.set(bottomShooterHighPO);
    } else if (controller.getBButtonPressed()) {
      m_topShooterMotor.set(topShooterFarPO);
      m_bottomShooterMotor.set(bottomShooterFarPO);
    } else {
      m_topShooterMotor.set(0.0);
      m_bottomShooterMotor.set(0.0);
    }

    if (controller.getRightTriggerAxis() > 0.5) {
      m_topIntakeMotor.set(topIntakePickupPO);
      m_bottomIntakeMotor.set(bottomIntakePickupPO);
    } else if (controller.getLeftTriggerAxis() > 0.5) {
      m_topIntakeMotor.set(topIntakeEjectPO);
      m_bottomIntakeMotor.set(bottomIntakeEjectPO);
    } else if (controller.getRightBumper()) {
      m_topIntakeMotor.set(topIntakeIndexPO);
      m_bottomIntakeMotor.set(bottomIntakeIndexPO);
    } else {
      m_topIntakeMotor.set(0.0);
      m_bottomIntakeMotor.set(0.0);
    }

    updateShooterValues();
    updateIntakeRollerValues();
  }

  public void updateShooterValues() {
    topShooterLowPO = SmartDashboard.getNumber("Top Shooter Low PO");
    bottomShooterLowPO = SmartDashboard.getNumber("Bottom Shooter Low PO");
    topShooterMidPO = SmartDashboard.getNumber("Top Shooter Mid PO");
    bottomShooterMidPO = SmartDashboard.getNumber("Bottom Shooter Mid PO");
    topShooterHighPO = SmartDashboard.getNumber("Top Shooter High PO");
    bottomShooterHighPO = SmartDashboard.getNumber("Bottom Shooter High PO");
    topShooterFarPO = SmartDashboard.getNumber("Top Shooter Far PO");
    bottomShooterFarPO = SmartDashboard.getNumber("Bottom Shooter Far PO");
  }

  public void updateIntakeRollerValues() {
    topIntakePickupPO = SmartDashboard.getNumber("Top Intake Pickup PO");
    bottomIntakePickupPO = SmartDashboard.getNumber("Bottom Intake Pickup PO");
    topIntakeEjectPO = SmartDashboard.getNumber("Top Intake Eject PO");
    bottomIntakeEjectPO = SmartDashboard.getNumber("Bottom Intake Eject PO");
    topIntakeIndexPO = SmartDashboard.getNumber("Top Intake Index PO");
    bottomIntakeIndexPO = SmartDashboard.getNumber("Bottom Intake Index PO");
  }

  public static double deadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }
}
