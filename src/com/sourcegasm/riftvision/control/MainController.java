package com.sourcegasm.riftvision.control;

import java.io.IOException;

import com.sourcegasm.riftvision.sensors.OculusSensors;

/**
 * Created by klemen on 19.8.2015. ^selfish
 */
public class MainController {
	public OculusSensors sensors;
	private Thread thread;
	public DroneController droneController;
	private HeightController heightController;

	public void startController(DroneController tempDroneController) {
		droneController = tempDroneController;
		heightController = new HeightController();
		new YawController();
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					float roll = (float) (ExpoController.getExpo(sensors.getSmoothedRool()) / 90.0);
					float pitch = (float) (ExpoController.getExpo(sensors.getSmoothedPitch()) / 90.0);
					try {
						// droneController.getDrone().move(roll, pitch, (float)
						// heightController.getHeightMove(), (float)
						// yawController.getYawMove(droneController, sensors));
						droneController.getDrone().move(roll, pitch, (float) heightController.getHeightMove(), 0);
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.currentThread();
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void stopController() {
		try {
			droneController.getDrone().move(0, 0, 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		thread.stop();
	}

	public DroneController getDroneController() {
		return droneController;
	}

	public HeightController getHeightController() {
		return heightController;
	}
}
