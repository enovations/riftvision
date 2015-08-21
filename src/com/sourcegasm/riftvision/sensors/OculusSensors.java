package com.sourcegasm.riftvision.sensors;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.sourcegasm.riftvision.math.Euler;
import com.sourcegasm.riftvision.math.Quaternion;

/**
 * Created by klemen on 18.8.2015.
 */
public class OculusSensors {

	private double rawRoll;
	private double rawPitch;
	private double rawYaw;

	private double smoothedRool;
	private double smoothedPitch;
	private double smoothedYaw;

	private LowPassFilter rollLowFilter;
	private LowPassFilter pitchLowFilter;
	private LowPassFilter yawLowFilter;

	private Thread recieverThread = new Thread();

	public void startReceiving() {
		final double lowPassSmoothing = 2;
		rollLowFilter = new LowPassFilter(lowPassSmoothing);
		pitchLowFilter = new LowPassFilter(lowPassSmoothing);
		yawLowFilter = new LowPassFilter(lowPassSmoothing);

		recieverThread = new Thread(() -> {
			try {
				final int port = 1234;
				final DatagramSocket dsocket = new DatagramSocket(port);

				while (true) {
					final byte[] buffer = new byte[2048];
					final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					dsocket.receive(packet);
					final String line = new String(buffer, 0, packet.getLength());
					packet.setLength(buffer.length);

					final Euler euler = new Quaternion(line).toEuler();
					rawRoll = euler.roll;
					rawPitch = euler.pitch;
					rawYaw = euler.yaw;

					rollLowFilter.calculate(euler.roll);
					smoothedRool = rollLowFilter.smoothedValue;

					pitchLowFilter.calculate(euler.pitch);
					smoothedPitch = pitchLowFilter.smoothedValue;

					yawLowFilter.calculate(euler.yaw);
					smoothedYaw = yawLowFilter.smoothedValue;
				}

			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
		recieverThread.start();
	}

	public double getSmoothedRool() {
		return smoothedRool;
	}

	public double getSmoothedPitch() {
		return smoothedPitch;
	}

	public double getSmoothedYaw() {
		return smoothedYaw;
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