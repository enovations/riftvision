package com.sourcegasm.riftvision.sensors;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.sourcegasm.riftvision.control.ExpoController;
import com.sourcegasm.riftvision.control.MainController;
import com.sourcegasm.riftvision.game.LapTimer;

/**
 * Created by klemen on 18.8.2015.
 */
public class JoyStickSensors {

	private double rawRoll;
	private double rawPitch;
	private double rawYaw;
	private double rawHeight;

	private Thread recieverThread = new Thread();
	private Thread recieverThread2 = new Thread();

	private final MainController droneController;
	private final LapTimer timer;

	public JoyStickSensors(MainController droneController, LapTimer timer) {
		this.droneController = droneController;
		this.timer = timer;
	}

	public void startReceiving() {

		recieverThread = new Thread(() -> {

			try {
				final DatagramSocket dsocket = new DatagramSocket(1235);

				while (true) {

					final DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
					dsocket.receive(packet);

					final String[] mami_array = new String(packet.getData()).split("\\;");

					if (mami_array.length == 4) {
						rawPitch = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[3].trim())));
						rawRoll = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[2].trim())));
						rawYaw = (float) (ExpoController.getJoyStickExpo((Integer.parseInt(mami_array[0].trim()))));
						rawHeight = ((Integer.parseInt(mami_array[1].trim()))) / -170.0;
					}

				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		recieverThread.start();

		recieverThread2 = new Thread(() -> {

			try {
				final DatagramSocket dsocket = new DatagramSocket(1236);

				while (true) {

					final DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
					dsocket.receive(packet);

					final String[] mami_array = new String(packet.getData()).split("\\;");

					if (mami_array.length > 1) {

						if (Integer.parseInt(mami_array[6].trim()) == 1) {
							try {
								droneController.getDroneController().getDrone().takeOff();
							} catch (final IOException e11) {
								e11.printStackTrace();
							}
							droneController.startController();
						} else if (Integer.parseInt(mami_array[7].trim()) == 1) {
							droneController.stopController();
							try {
								droneController.getDroneController().getDrone().land();
							} catch (final IOException e12) {
								e12.printStackTrace();
							}
						} else if (Integer.parseInt(mami_array[1].trim()) == 1) {
							try {
								droneController.getDroneController().getDrone().trim();
							} catch (final IOException e13) {
								e13.printStackTrace();
							}
						} else if (Integer.parseInt(mami_array[2].trim()) == 1) {
							try {
								droneController.getDroneController().getDrone().clearEmergencySignal();
							} catch (final IOException e14) {
								e14.printStackTrace();
							}
						} else if (Integer.parseInt(mami_array[5].trim()) == 1) {
							System.out.println("asdf");
							droneController.setControlMode(droneController.getNextMode());
						} else if (Integer.parseInt(mami_array[4].trim()) == 1) {
							droneController.setControlMode(droneController.getPreviousMode());
						} else if (Integer.parseInt(mami_array[0].trim()) == 1) {
							timer.startTimer();
						} else if (Integer.parseInt(mami_array[3].trim()) == 1) {
							timer.stopTimer();
						}

					}

				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		recieverThread2.start();
	}

	public double getRawRool() {
		return rawRoll;
	}

	public double getRawPitch() {
		return rawPitch;
	}

	public double getRawYaw() {
		return rawYaw;
	}

	public double getRawHeight() {
		return rawHeight;
	}

}