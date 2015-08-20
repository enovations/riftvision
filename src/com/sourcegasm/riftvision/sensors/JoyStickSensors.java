package com.sourcegasm.riftvision.sensors;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.sourcegasm.riftvision.helper.Euler;
import com.sourcegasm.riftvision.helper.Quaternion;

/**
 * Created by klemen on 18.8.2015.
 */
public class JoyStickSensors {

	private double rawRool;
	private double rawPitch;
	private double rawYaw;

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

					/*while (true) {
						dsocket.receive(packet);

						String[] mami_array = new String(packet.getData()).split("\\;");

						if (mami_array.length > 1) {

							if (visual.global_main.flightMode.getMode() != FlightMode.eMode.TAG_MODE)
								pitch = (float) (ExpoController.getExpo(Integer
										.parseInt(mami_array[1].trim())));

							roll = (float) (ExpoController.getExpo(Integer
									.parseInt(mami_array[0].trim())));

							if (visual.global_main.flightMode.getMode() != FlightMode.eMode.TAG_MODE)
								yaw = (float) (ExpoController.getExpo((Integer
										.parseInt(mami_array[2].trim()))) / 4.7);

							int mamih = Integer.parseInt(mami_array[3].trim());
							if (mamih == 2)
								height = -0.28f;
							else if (mamih == -1)
								height = 0.28f;
							else
								height = 0;

						}
					}*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		recieverThread.start();
	}

	public double getRawRool() {
		return rawRool;
	}

	public double getRawPitch() {
		return rawPitch;
	}

	public double getRawYaw() {
		return rawYaw;
	}

}
