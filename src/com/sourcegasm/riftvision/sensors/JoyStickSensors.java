package com.sourcegasm.riftvision.sensors;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.sourcegasm.riftvision.control.ExpoController;
import com.sourcegasm.riftvision.helper.Euler;
import com.sourcegasm.riftvision.helper.Quaternion;

/**
 * Created by klemen on 18.8.2015.
 */
public class JoyStickSensors {

	private double rawRoll;
	private double rawPitch;
	private double rawYaw;
	private double rawHeight;

	private Thread recieverThread = new Thread();

	public void startReceiving() {

		recieverThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					int port = 1235;
					DatagramSocket dsocket = new DatagramSocket(port);
					byte[] buffer = new byte[2048];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

					while (true) {
						dsocket.receive(packet);

						String[] mami_array = new String(packet.getData()).split("\\;");

						if (mami_array.length > 1) {

							rawPitch = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[1].trim())));

							rawRoll = (float) (ExpoController.getJoyStickExpo(Integer.parseInt(mami_array[0].trim())));

							rawYaw = (float) (ExpoController.getJoyStickExpo((Integer.parseInt(mami_array[2].trim()))) / 4.7);

							rawHeight = (float) (ExpoController.getJoyStickExpo((Integer.parseInt(mami_array[3].trim()))) / 4.7);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		recieverThread.start();
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

}
