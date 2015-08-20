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
					int port = 1234;
					DatagramSocket dsocket = new DatagramSocket(port);
					byte[] buffer = new byte[2048];
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

					while (true) {
						dsocket.receive(packet);
						String line = new String(buffer, 0, packet.getLength());
						packet.setLength(buffer.length);

						Euler euler = new Quaternion(line).toEuler();
						rawRool = euler.roll;
						rawPitch = euler.pitch;
						rawYaw = euler.yaw;
					}
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
