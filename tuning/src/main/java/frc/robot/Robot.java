/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
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
 * 1) Tune a velocity PID loop for the mechanism
 * 2) Configure the smart motion parameters
 * 
 * Tuning the Velocity PID Loop
 * 
 * The most important part of tuning any closed loop control such as the
 * velocity
 * PID, is to graph the inputs and outputs to understand exactly what is
 * happening.
 * For tuning the Velocity PID loop, at a minimum we recommend graphing:
 *
 * 1) The velocity of the mechanism (‘Process variable’)
 * 2) The commanded velocity value (‘Setpoint’)
 * 3) The applied output
 *
 * This example will use ShuffleBoard to graph the above parameters. Make sure
 * to
 * load the shuffleboard.json file in the root of this directory to get the full
 * effect of the GUI layout.
 */
public class Robot extends TimedRobot {
  // All PID values are for the intake slide mechanism
  private CANSparkMax m_masterSlideMotor, m_followerSlideMotor, m_topShooterMotor, m_bottomShooterMotor,
      m_topIntakeMotor, m_bottomIntakeMotor;
  private SparkMaxPIDController m_slidePIDController, m_topShooterPIDController, m_bottomShooterPIDController,
      m_topIntakePIDController, m_bottomIntakePIDController;
  private RelativeEncoder m_encoder;
  public double slide_kP, slide_kI, slide_kD, slide_kIz, slide_kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel,
      maxAcc, allowedErr;
  public double topShooter_kP, topShooter_kI, topShooter_kD, topShooter_kFF, bottomShooter_kP, bottomShooter_kI,
      bottomShooter_kD, bottomShooter_kFF;
  public double topIntake_kP, topIntake_kI, topIntake_kD, topIntake_kFF, bottomIntake_kP, bottomIntake_kI,
      bottomIntake_kD, bottomIntake_kFF;

  public double topShooterLowRPMS, bottomShooterLowRPMS, topShooterMidRPMS, bottomShooterMidRPMS, topShooterHighRPMS,
      bottomShooterHighRPMS, topShooterFarRPMS, bottomShooterFarRPMS;

  public double topIntakePickupRPMS, bottomIntakePickupRPMS, topIntakeEjectRPMS, bottomIntakeEjectRPMS,
      topIntakeIndexRPMS, bottomIntakeIndexRPMS;

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
    m_slidePIDController = m_masterSlideMotor.getPIDController();
    m_encoder = m_masterSlideMotor.getEncoder();

    m_topShooterMotor = new CANSparkMax(TOP_SHOOTER_MOTOR_ID, MotorType.kBrushless);
    m_bottomShooterMotor = new CANSparkMax(BOTTOM_SHOOTER_MOTOR_ID, MotorType.kBrushless);
    m_topShooterPIDController = m_topShooterMotor.getPIDController();
    m_bottomShooterPIDController = m_bottomShooterMotor.getPIDController();

    m_topIntakeMotor = new CANSparkMax(TOP_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
    m_bottomIntakeMotor = new CANSparkMax(BOTTOM_INTAKE_ROLLER_MOTOR_ID, MotorType.kBrushless);
    m_topIntakePIDController = m_topIntakeMotor.getPIDController();
    m_bottomIntakePIDController = m_bottomIntakeMotor.getPIDController();

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
     * The RestoreFactoryDefaults method can be used to reset the configuration
     * parameters
     * in the SPARK MAX to their factory default state. If no argument is passed,
     * these
     * parameters will not persist between power cycles
     */

    // initialze PID controller and encoder objects

    // PID coefficients
    slide_kP = 5e-5;
    slide_kI = 1e-6;
    slide_kD = 0;
    slide_kIz = 0;
    slide_kFF = 0.000156;
    kMaxOutput = 1;
    kMinOutput = -1;
    maxRPM = 5700;

    topShooter_kP = 0.0;
    topShooter_kI = 0.0;
    topShooter_kD = 0.0;
    topShooter_kFF = 0.0;
    bottomShooter_kP = 0.0;
    bottomShooter_kI = 0.0;
    bottomShooter_kD = 0.0;
    bottomShooter_kFF = 0.0;

    topIntake_kP = 0.0;
    topIntake_kI = 0.0;
    topIntake_kD = 0.0;
    topIntake_kFF = 0.0;
    bottomIntake_kP = 0.0;
    bottomIntake_kI = 0.0;
    bottomIntake_kD = 0.0;
    bottomIntake_kFF = 0.0;

    // Smart Motion Coefficients
    maxVel = 2000; // RPM
    maxAcc = 1500;

    // set PID coefficients
    m_slidePIDController.setP(slide_kP);
    m_slidePIDController.setI(slide_kI);
    m_slidePIDController.setD(slide_kD);
    m_slidePIDController.setIZone(slide_kIz);
    m_slidePIDController.setFF(slide_kFF);
    m_slidePIDController.setOutputRange(kMinOutput, kMaxOutput);

    m_topShooterPIDController.setP(topShooter_kP);
    m_topShooterPIDController.setI(topShooter_kI);
    m_topShooterPIDController.setD(topShooter_kD);
    m_topShooterPIDController.setFF(topShooter_kFF);
    m_bottomShooterPIDController.setP(bottomShooter_kP);
    m_bottomShooterPIDController.setI(bottomShooter_kI);
    m_bottomShooterPIDController.setD(bottomShooter_kD);
    m_bottomShooterPIDController.setFF(bottomShooter_kFF);

    m_topIntakePIDController.setP(topIntake_kP);
    m_topIntakePIDController.setI(topIntake_kI);
    m_topIntakePIDController.setD(topIntake_kD);
    m_topIntakePIDController.setFF(topIntake_kFF);
    m_bottomIntakePIDController.setP(bottomIntake_kP);
    m_bottomIntakePIDController.setI(bottomIntake_kI);
    m_bottomIntakePIDController.setD(bottomIntake_kD);
    m_bottomIntakePIDController.setFF(bottomIntake_kFF);


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
    m_slidePIDController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    m_slidePIDController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    m_slidePIDController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    m_slidePIDController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("Slide P Gain", slide_kP);
    SmartDashboard.putNumber("Slide I Gain", slide_kI);
    SmartDashboard.putNumber("Slide D Gain", slide_kD);
    SmartDashboard.putNumber("Slide I Zone", slide_kIz);
    SmartDashboard.putNumber("Slide Feed Forward", slide_kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    SmartDashboard.putNumber("Top Shooter P Gain", topShooter_kP);
    SmartDashboard.putNumber("Top Shooter I Gain", topShooter_kI);
    SmartDashboard.putNumber("Top Shooter D Gain", topShooter_kD);
    SmartDashboard.putNumber("Top Shooter Feed Forward", topShooter_kFF);
    SmartDashboard.putNumber("Bottom Shooter P Gain", bottomShooter_kP);
    SmartDashboard.putNumber("Bottom Shooter I Gain", bottomShooter_kI);
    SmartDashboard.putNumber("Bottom Shooter D Gain", bottomShooter_kD);
    SmartDashboard.putNumber("Bottom Shooter Feed Forward", bottomShooter_kFF);

    SmartDashboard.putNumber("Top Intake P Gain", topIntake_kP);
    SmartDashboard.putNumber("Top Intake I Gain", topIntake_kI);
    SmartDashboard.putNumber("Top Intake D Gain", topIntake_kD);
    SmartDashboard.putNumber("Top Intake Feed Forward", topIntake_kFF);
    SmartDashboard.putNumber("Bottom Intake P Gain", bottomIntake_kP);
    SmartDashboard.putNumber("Bottom Intake I Gain", bottomIntake_kI);
    SmartDashboard.putNumber("Bottom Intake D Gain", bottomIntake_kD);
    SmartDashboard.putNumber("Bottom Intake Feed Forward", bottomIntake_kFF);


    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);

    topShooterLowRPMS = 0;
    topShooterMidRPMS = 0;
    topShooterHighRPMS = 0;
    topShooterFarRPMS = 0;
    bottomShooterLowRPMS = 0;
    bottomShooterMidRPMS = 0;
    bottomShooterHighRPMS = 0;
    bottomShooterFarRPMS = 0;

    SmartDashboard.putNumber("Top Shooter Low RPMS", topShooterLowRPMS);
    SmartDashboard.putNumber("Bottom Shooter Low RPMS", bottomShooterLowRPMS);
    SmartDashboard.putNumber("Top Shooter Mid RPMS", topShooterMidRPMS);
    SmartDashboard.putNumber("Bottom Shooter Mid RPMS", bottomShooterMidRPMS);
    SmartDashboard.putNumber("Top Shooter High RPMS", topShooterHighRPMS);
    SmartDashboard.putNumber("Bottom Shooter High RPMS", bottomShooterHighRPMS);
    SmartDashboard.putNumber("Top Shooter Far RPMS", topShooterFarRPMS);
    SmartDashboard.putNumber("Bottom Shooter Far RPMS", bottomShooterFarRPMS);

    topIntakePickupRPMS = 0;
    bottomIntakePickupRPMS = 0;
    topIntakeEjectRPMS = 0;
    bottomIntakeEjectRPMS = 0;
    topIntakeIndexRPMS = 0;
    bottomIntakeIndexRPMS = 0;

    SmartDashboard.putNumber("Top Intake Pickup RPMS", topIntakePickupRPMS);
    SmartDashboard.putNumber("Bottom Intake Pickup RPMS", bottomIntakePickupRPMS);
    SmartDashboard.putNumber("Top Intake Eject RPMS", topIntakeEjectRPMS);
    SmartDashboard.putNumber("Bottom Intake Eject RPMS", bottomIntakeEjectRPMS);
    SmartDashboard.putNumber("Top Intake Index RPMS", topIntakeIndexRPMS);
    SmartDashboard.putNumber("Bottom Intake Index RPMS", bottomIntakeIndexRPMS);

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

    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    if ((p != slide_kP)) {
      m_slidePIDController.setP(p);
      slide_kP = p;
    }
    if ((i != slide_kI)) {
      m_slidePIDController.setI(i);
      slide_kI = i;
    }
    if ((d != slide_kD)) {
      m_slidePIDController.setD(d);
      slide_kD = d;
    }
    if ((iz != slide_kIz)) {
      m_slidePIDController.setIZone(iz);
      slide_kIz = iz;
    }
    if ((ff != slide_kFF)) {
      m_slidePIDController.setFF(ff);
      slide_kFF = ff;
    }
    if ((max != kMaxOutput) || (min != kMinOutput)) {
      m_slidePIDController.setOutputRange(min, max);
      kMinOutput = min;
      kMaxOutput = max;
    }
    if ((maxV != maxVel)) {
      m_slidePIDController.setSmartMotionMaxVelocity(maxV, 0);
      maxVel = maxV;
    }
    if ((minV != minVel)) {
      m_slidePIDController.setSmartMotionMinOutputVelocity(minV, 0);
      minVel = minV;
    }
    if ((maxA != maxAcc)) {
      m_slidePIDController.setSmartMotionMaxAccel(maxA, 0);
      maxAcc = maxA;
    }
    if ((allE != allowedErr)) {
      m_slidePIDController.setSmartMotionAllowedClosedLoopError(allE, 0);
      allowedErr = allE;
    }

    double setPoint, processVariable;
    boolean mode = SmartDashboard.getBoolean("Mode", false);
    if (mode) {
      setPoint = SmartDashboard.getNumber("Set Velocity", 0);
      m_slidePIDController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
      processVariable = m_encoder.getVelocity();
    } else {
      setPoint = SmartDashboard.getNumber("Set Position", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      m_slidePIDController.setReference(setPoint, CANSparkMax.ControlType.kSmartMotion);
      processVariable = m_encoder.getPosition();
    }

    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", m_masterSlideMotor.getAppliedOutput());
  }

  @Override
  public void testPeriodic() {
    m_masterSlideMotor.set(deadband(controller.getLeftY(), 0.1) * 0.25);
    if (controller.getBackButtonPressed()) {
      m_encoder.setPosition(0);
    }

    if (controller.getAButtonPressed()) {
      m_topShooterPIDController.setReference(topShooterLowRPMS, ControlType.kVelocity);
      m_bottomShooterPIDController.setReference(bottomShooterLowRPMS, ControlType.kVelocity);
    } else if (controller.getXButtonPressed()) {
      m_topShooterPIDController.setReference(topShooterMidRPMS, ControlType.kVelocity);
      m_bottomShooterPIDController.setReference(bottomShooterMidRPMS, ControlType.kVelocity);
    } else if (controller.getYButtonPressed()) {
      m_topShooterPIDController.setReference(topShooterHighRPMS, ControlType.kVelocity);
      m_bottomShooterPIDController.setReference(bottomShooterHighRPMS, ControlType.kVelocity);
    } else if (controller.getBButtonPressed()) {
      m_topShooterPIDController.setReference(topShooterFarRPMS, ControlType.kVelocity);
      m_bottomShooterPIDController.setReference(bottomShooterFarRPMS, ControlType.kVelocity);
    } else {
      m_topShooterPIDController.setReference(0.0, ControlType.kVelocity);
      m_bottomShooterPIDController.setReference(0.0, ControlType.kVelocity);
    }

    if (controller.getRightTriggerAxis() > 0.5) {
      m_topIntakePIDController.setReference(topIntakePickupRPMS, ControlType.kVelocity);
      m_bottomIntakePIDController.setReference(bottomIntakePickupRPMS, ControlType.kVelocity);
    } else if (controller.getLeftTriggerAxis() > 0.5) {
      m_topIntakePIDController.setReference(topIntakeEjectRPMS, ControlType.kVelocity);
      m_bottomIntakePIDController.setReference(bottomIntakeEjectRPMS, ControlType.kVelocity);
    } else if (controller.getRightBumper()) {
      m_topIntakePIDController.setReference(topIntakeIndexRPMS, ControlType.kVelocity);
      m_bottomIntakePIDController.setReference(bottomIntakeIndexRPMS, ControlType.kVelocity);
    } else {
      m_topIntakePIDController.setReference(0.0, ControlType.kVelocity);
      m_bottomIntakePIDController.setReference(0.0, ControlType.kVelocity);
    }

    updateShooterValues();
    updateIntakeRollerValues();
  }

  public void updateShooterValues() {
    double tsp = SmartDashboard.getNumber("Top Shooter P Gain", 0.0);
    double tsi = SmartDashboard.getNumber("Top Shooter I Gain", 0.0);
    double tsd = SmartDashboard.getNumber("Top Shooter D Gain", 0.0);
    double tsff = SmartDashboard.getNumber("Top Shooter Feed Forward", 0.0);
    double bsp = SmartDashboard.getNumber("Bottom Shooter P Gain", 0.0);
    double bsi = SmartDashboard.getNumber("Bottom Shooter I Gain", 0.0);
    double bsd = SmartDashboard.getNumber("Bottom Shooter D Gain", 0.0);
    double bsff = SmartDashboard.getNumber("Bottom Shooter Feed Forward", 0.0);

    if ((tsp != topShooter_kP)) {m_topShooterPIDController.setP(tsp);topShooter_kP = tsp;}
    if ((tsi != topShooter_kI)) {m_topShooterPIDController.setI(tsi);topShooter_kI = tsi;}
    if ((tsd != topShooter_kD)) {m_topShooterPIDController.setD(tsd);topShooter_kD = tsd;}
    if ((tsff != topShooter_kFF)) {m_topShooterPIDController.setFF(tsff);topShooter_kFF = tsff;}
    if ((bsp != bottomShooter_kP)) {m_bottomShooterPIDController.setP(bsp);bottomShooter_kP = bsp;}
    if ((bsi != bottomShooter_kI)) {m_bottomShooterPIDController.setI(bsi);bottomShooter_kI = bsi;}
    if ((bsd != bottomShooter_kD)) {m_bottomShooterPIDController.setD(bsd);bottomShooter_kD = bsd;}
    if ((bsff != bottomShooter_kFF)) {m_bottomShooterPIDController.setFF(bsff);bottomShooter_kFF = bsff;}

    topShooterLowRPMS = SmartDashboard.getNumber("Top Shooter Low RPMS", 0);
    bottomShooterLowRPMS = SmartDashboard.getNumber("Bottom Shooter Low RPMS", 0);
    topShooterMidRPMS = SmartDashboard.getNumber("Top Shooter Mid RPMS", 0);
    bottomShooterMidRPMS = SmartDashboard.getNumber("Bottom Shooter Mid RPMS", 0);
    topShooterHighRPMS = SmartDashboard.getNumber("Top Shooter High RPMS", 0);
    bottomShooterHighRPMS = SmartDashboard.getNumber("Bottom Shooter High RPMS", 0);
    topShooterFarRPMS = SmartDashboard.getNumber("Top Shooter Far RPMS", 0);
    bottomShooterFarRPMS = SmartDashboard.getNumber("Bottom Shooter Far RPMS", 0);
  }

  public void updateIntakeRollerValues() {
    double tip = SmartDashboard.getNumber("Top Intake P Gain", 0.0);
    double tii = SmartDashboard.getNumber("Top Intake I Gain", 0.0);
    double tid = SmartDashboard.getNumber("Top Intake D Gain", 0.0);
    double tiff = SmartDashboard.getNumber("Top Intake Feed Forward", 0.0);
    double bip = SmartDashboard.getNumber("Bottom Intake P Gain", 0.0);
    double bii = SmartDashboard.getNumber("Bottom Intake I Gain", 0.0);
    double bid = SmartDashboard.getNumber("Bottom Intake D Gain", 0.0);
    double biff = SmartDashboard.getNumber("Bottom Intake Feed Forward", 0.0);

    if ((tip != topIntake_kP)) {m_topIntakePIDController.setP(tip);topIntake_kP = tip;}
    if ((tii != topIntake_kI)) {m_topIntakePIDController.setI(tii);topIntake_kI = tii;}
    if ((tid != topIntake_kD)) {m_topIntakePIDController.setD(tid);topIntake_kD = tid;}
    if ((tiff != topIntake_kFF)) {m_topIntakePIDController.setFF(tiff);topIntake_kFF = tiff;}
    if ((bip != bottomIntake_kP)) {m_bottomIntakePIDController.setP(bip);bottomIntake_kP = bip;}
    if ((bii != bottomIntake_kI)) {m_bottomIntakePIDController.setI(bii);bottomIntake_kI = bii;}
    if ((bid != bottomIntake_kD)) {m_bottomIntakePIDController.setD(bid);bottomIntake_kD = bid;}
    if ((biff != bottomIntake_kFF)) {m_bottomIntakePIDController.setFF(biff);bottomIntake_kFF = biff;}

    topIntakePickupRPMS = SmartDashboard.getNumber("Top Intake Pickup RPMS", 0);
    bottomIntakePickupRPMS = SmartDashboard.getNumber("Bottom Intake Pickup RPMS", 0);
    topIntakeEjectRPMS = SmartDashboard.getNumber("Top Intake Eject RPMS", 0);
    bottomIntakeEjectRPMS = SmartDashboard.getNumber("Bottom Intake Eject RPMS", 0);
    topIntakeIndexRPMS = SmartDashboard.getNumber("Top Intake Index RPMS", 0);
    bottomIntakeIndexRPMS = SmartDashboard.getNumber("Bottom Intake Index RPMS", 0);
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
