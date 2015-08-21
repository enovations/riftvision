package com.sourcegasm.riftvision.control;

import java.io.IOException;

import com.sourcegasm.riftvision.sensors.JoyStickSensors;
import com.sourcegasm.riftvision.sensors.OculusSensors;

public class MainController {

	public OculusSensors oculusSensors;
	public JoyStickSensors joyStickSensors;

	private Thread thread;
	public DroneController droneController;
	private final YawController yawController = new YawController();
	boolean contiune = false;
	public ControlModes controlMode = ControlModes.JoystickOnly;

	public void startController() {

		yawController.setZero(droneController, oculusSensors);
		contiune = true;

		switch (controlMode) {
		case OculusOnly:

			thread = new Thread(() -> {
				while (contiune) {
					oculusOnlyControllerLiteration();
				}
			});
			thread.start();

			break;

		case OculusYaw:

			thread = new Thread(() -> {
				while (contiune) {
					oculusYawControllerLiteration();
				}
			});
			thread.start();

			break;

		case OculusYawPitch:

			thread = new Thread(() -> {
				while (contiune) {
					oculusYawPitchControllerLiteration();
				}
			});
			thread.start();

			break;

		case JoystickOnly:

			thread = new Thread(() -> {
				while (contiune) {
					joysitckOnlyControllerLiteration();
				}
			});
			thread.start();

			break;
		}
	}

	private void joysitckOnlyControllerLiteration() {

		try {
			droneController.getDrone().move((float) joyStickSensors.getRawRool(), (float) joyStickSensors.getRawPitch(),
					(float) joyStickSensors.getRawHeight(), (float) joyStickSensors.getRawYaw());
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void oculusYawControllerLiteration() {

		final double yaw = yawController.getYawMove(droneController, oculusSensors);
		try {
			droneController.getDrone().move((float) joyStickSensors.getRawRool(), (float) joyStickSensors.getRawPitch(),
					(float) joyStickSensors.getRawHeight(), (float) yaw);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void oculusYawPitchControllerLiteration() {

		final float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);
		final double yaw = yawController.getYawMove(droneController, oculusSensors);
		try {
			droneController.getDrone().move((float) joyStickSensors.getRawRool(), -pitch,
					(float) joyStickSensors.getRawHeight(), (float) yaw);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void oculusOnlyControllerLiteration() {

		final float roll = (float) (ExpoController.getExpo(oculusSensors.getSmoothedRool()) / 90.0);
		final float pitch = (float) (ExpoController.getExpo(oculusSensors.getSmoothedPitch()) / 90.0);
		final double yaw = yawController.getYawMove(droneController, oculusSensors);
		try {
			droneController.getDrone().move(roll, -pitch, (float) joyStickSensors.getRawHeight(), (float) yaw);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stopController() {
		contiune = false;
		try {
			droneController.getDrone().move(0, 0, 0, 0);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		thread.stop();
		thread = null;
	}

	public DroneController getDroneController() {
		return droneController;
	}

	public YawController getYawController() {
		return yawController;
	}

	public ControlModes getNextMode() {
		switch (controlMode) {
		case JoystickOnly:
			return ControlModes.OculusYaw;
		case OculusYaw:
			return ControlModes.OculusYawPitch;
		case OculusYawPitch:
			return ControlModes.OculusOnly;
		case OculusOnly:
			return ControlModes.JoystickOnly;
		}
		return null;
	}

	public ControlModes getNextMode(ControlModes mode) {
		switch (mode) {
		case JoystickOnly:
			return ControlModes.OculusYaw;
		case OculusYaw:
			return ControlModes.OculusYawPitch;
		case OculusYawPitch:
			return ControlModes.OculusOnly;
		case OculusOnly:
			return ControlModes.JoystickOnly;
		}
		return null;
	}

	public ControlModes getPreviousMode() {
		switch (controlMode) {
		case OculusOnly:
			return ControlModes.OculusYawPitch;
		case OculusYawPitch:
			return ControlModes.OculusYaw;
		case OculusYaw:
			return ControlModes.JoystickOnly;
		case JoystickOnly:
			return ControlModes.OculusOnly;
		}
		return null;
	}

	public ControlModes getPreviousMode(ControlModes mode) {
		switch (controlMode) {
		case OculusOnly:
			return ControlModes.OculusYawPitch;
		case OculusYawPitch:
			return ControlModes.OculusYaw;
		case OculusYaw:
			return ControlModes.JoystickOnly;
		case JoystickOnly:
			return ControlModes.OculusOnly;
		}
		return null;
	}

	public void setControlMode(ControlModes mode) {
		stopController();
		controlMode = mode;
		startController();
	}
}
