package com.sourcegasm.riftvision.sensors;

import com.sourcegasm.riftvision.helper.Euler;
import com.sourcegasm.riftvision.helper.Quaternion;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by klemen on 18.8.2015.
 */
public class OculusSensors {

	private double rawRool;
	private double rawPitch;
	private double rawYaw;

	private double smoothedRool;
	private double smoothedPitch;
	private double smoothedYaw;

	private LowPassFilter rollLowFilter;
	private LowPassFilter pitchLowFilter;
	private LowPassFilter yawLowFilter;

	private HighPassFilter rollHighFilter;
	private HighPassFilter pitchHighFilter;
	private HighPassFilter yawHighFilter;

	private Thread recieverThread = new Thread();

	public void startReceiving() {
		double lowPassSmoothing = 2;
		rollLowFilter = new LowPassFilter(lowPassSmoothing);
		pitchLowFilter = new LowPassFilter(lowPassSmoothing);
		yawLowFilter = new LowPassFilter(lowPassSmoothing);

		double highPassSmoothing = 1 / 1.05;
		rollHighFilter = new HighPassFilter(highPassSmoothing);
		pitchHighFilter = new HighPassFilter(highPassSmoothing);
		yawHighFilter = new HighPassFilter(highPassSmoothing);

		recieverThread = new Thread(() -> {
            String currrentDir = new File(".").getAbsolutePath().replaceAll("/\\.", "");
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    killPython();
                }
            }));

            try {
                Runtime.getRuntime().exec("gksudo python " + currrentDir + "/mami.py");
            } catch (IOException e) {
                e.printStackTrace();
            }

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

					//System.out.println(rawRool+", "+rawPitch+", "+rawYaw);

                    rollLowFilter.calculate(euler.roll);
                    rollHighFilter.calculate(rollLowFilter.smoothedValue);
                    smoothedRool = rollHighFilter.smoothedValue;

                    pitchLowFilter.calculate(euler.pitch);
                    pitchHighFilter.calculate(pitchLowFilter.smoothedValue);
                    smoothedPitch = pitchHighFilter.smoothedValue;

                    yawLowFilter.calculate(euler.yaw);
                    yawHighFilter.calculate(yawLowFilter.smoothedValue);
                    smoothedYaw = yawHighFilter.smoothedValue;
                }
            } catch (Exception e) {
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
		return rawRool;
	}

	public double getRawPitch() {
		return rawPitch;
	}

	public double getRawYaw() {
		return rawYaw;
	}

	public void stopReceving() {
		recieverThread.stop();
	}

	public static void killPython() {
		try {
			Runtime.getRuntime().exec("gksudo killall python");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
