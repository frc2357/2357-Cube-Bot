// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2023;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.util.Units;

public final class Constants {
    public static final class CAN_ID {
        public static final int PIGEON_ID = 5;

        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 11;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR_ID = 12;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 13;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR_ID = 14;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 15;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR_ID = 16;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 17;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR_ID = 18;
        public static final int BACK_RIGHT_MODULE_STEER_ENCODER_ID = 22;

        public static final int TOP_INTAKE_ROLLER_MOTOR_ID = 20;
        public static final int BOTTOM_INTAKE_ROLLER_MOTOR_ID = 19;
    }

    public static final class INTAKE_ROLLER {
        // Configuration
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;
        
        public static final double TOP_AXIS_MAX_SPEED = 1;
        public static final double BOTTOM_AXIS_MAX_SPEED = 1;

        public static final int TOP_MOTOR_STALL_LIMIT_AMPS = 30;
        public static final int TOP_MOTOR_FREE_LIMIT_AMPS = 30;

        public static final int BOTTOM_MOTOR_STALL_LIMIT_AMPS = 30;
        public static final int BOTTOM_MOTOR_FREE_LIMIT_AMPS = 30;

        public static final IdleMode MOTOR_IDLE_MODE = IdleMode.kBrake;

        public static final double TOP_MOTOR_P = 0.0;
        public static final double TOP_MOTOR_I = 0.0;
        public static final double TOP_MOTOR_D = 0.0;
        public static final double TOP_MOTOR_FF = 0.0;

        public static final double BOTTOM_MOTOR_P = 0.0;
        public static final double BOTTOM_MOTOR_I = 0.0;
        public static final double BOTTOM_MOTOR_D = 0.0;
        public static final double BOTTOM_MOTOR_FF = 0.0;

        // Motor speeds
        public static final double TOP_MOTOR_INTAKE_RPMS = +0.5;
        public static final double BOTTOM_MOTOR_INTAKE_RPMS = +0.75;
        
        public static final double TOP_MOTOR_EJECT_RPMS = -0.5;
        public static final double BOTTOM_MOTOR_EJECT_RPMS = +0.35;
        
        public static final double TOP_MOTOR_INDEX_RPMS = +0.5;
        public static final double BOTTOM_MOTOR_INDEX_RPMS = -0.35;

        public static final double TOP_MOTOR_ROLL_RPMS = +0.5;
        public static final double BOTTOM_MOTOR_ROLL_RPMS = +0.35;

    }

    public static final class SWERVE {
        public static final double TRACKWIDTH_METERS = Units.inchesToMeters(18.75);
        public static final double WHEELBASE_METERS = Units.inchesToMeters(18.75);

        public static final double ABSOLUTE_ENCODER_CONVERSION_FACTOR = 360;

        // TODO: get some drive practice and tune these accordingly
        public static final double MAX_VELOCITY_METERS_PER_SECOND = 14.5;
        public static final double MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND = 4;

        public static final double TIME_TO_COAST_SECONDS = 5000;
    }

    public static final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.1;

        public static final double RUMBLE_INTENSITY = 0.5;
        public static final double RUMBLE_TIMEOUT_SECONDS_ON_TELEOP_AUTO = 1;
    }

}
