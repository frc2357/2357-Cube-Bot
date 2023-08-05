// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2023;

public final class Constants {
    public static final class CAN_ID {
        public static final int PIGEON_ID = 5;

        public static final int FRONT_LEFT_MODULE_DRIVE_MOTOR_ID = 11;
        public static final int FRONT_LEFT_MODULE_STEER_MOTOR_ID = 12;
        public static final int FRONT_LEFT_MODULE_STEER_ENCODER_ID = 19;

        public static final int FRONT_RIGHT_MODULE_DRIVE_MOTOR_ID = 13;
        public static final int FRONT_RIGHT_MODULE_STEER_MOTOR_ID = 14;
        public static final int FRONT_RIGHT_MODULE_STEER_ENCODER_ID = 20;

        public static final int BACK_LEFT_MODULE_DRIVE_MOTOR_ID = 15;
        public static final int BACK_LEFT_MODULE_STEER_MOTOR_ID = 16;
        public static final int BACK_LEFT_MODULE_STEER_ENCODER_ID = 21;

        public static final int BACK_RIGHT_MODULE_DRIVE_MOTOR_ID = 17;
        public static final int BACK_RIGHT_MODULE_STEER_MOTOR_ID = 18;
        public static final int BACK_RIGHT_MODULE_STEER_ENCODER_ID = 22;

        public static final int TOP_SHOOTER_MOTOR_ID = -1;
        public static final int BOTTOM_SHOOTER_MOTOR_ID = -1;
    }

    public static final class SHOOTER {
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;

        public static final double TOP_MOTOR_HIGH_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_HIGH_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_MID_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_MID_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_LOW_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_LOW_PERCENT_OUTPUT = 0.0;

        public static final int TOP_MOTOR_LIMIT_AMPS = 10;
        public static final int BOTTOM_MOTOR_LIMIT_AMPS = 10;
    }

    public static final class INTAKE_ROLLER {
        public static final boolean TOP_MOTOR_INVERTED = false;
        public static final boolean BOTTOM_MOTOR_INVERTED = false;

        public static final double TOP_MOTOR_INTAKE_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_INTAKE_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_EJECT_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_EJECT_PERCENT_OUTPUT = 0.0;
        
        public static final double TOP_MOTOR_INDEX_PERCENT_OUTPUT = 0.0;
        public static final double BOTTOM_MOTOR_INDEX_PERCENT_OUTPUT = 0.0;

        public static final int TOP_MOTOR_LIMIT_AMPS = 10;
        public static final int BOTTOM_MOTOR_LIMIT_AMPS = 10;
    }

    public static final class INTAKE_SLIDE {

    }

    public static final class SWERVE {

    }

    public static final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        public static final int GUNNER_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.05;
        public static final double GUNNER_CONTROLLER_DEADBAND = 0.1;

        public static final double RUMBLE_INTENSITY = 0.5;
        public static final double RUMBLE_TIMEOUT_SECONDS_ON_TELEOP_AUTO = 1;
    }
}
