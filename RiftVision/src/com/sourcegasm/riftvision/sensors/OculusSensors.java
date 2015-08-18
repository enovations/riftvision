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

    public static double smoothedRool;
    public static double smoothedPitch;
    public static double smoothedYaw;

    private static LowPassFilter rollLowFilter;
    private static LowPassFilter pitchLowFilter;
    private static LowPassFilter yawLowFilter;

    private static HighPassFilter rollHighFilter;
    private static HighPassFilter pitchHighFilter;
    private static HighPassFilter yawHighFilter;

    private static Thread recieverThread = new Thread();

    public static void startReceiving() {
        int lowPassSmoothing =2;
        rollLowFilter = new LowPassFilter(lowPassSmoothing);
        pitchLowFilter = new LowPassFilter(lowPassSmoothing);
        yawLowFilter = new LowPassFilter(lowPassSmoothing);

        int highPassSmoothing = 10;
        rollHighFilter = new HighPassFilter(highPassSmoothing);
        pitchHighFilter = new HighPassFilter(highPassSmoothing);
        yawHighFilter = new HighPassFilter(highPassSmoothing);

        recieverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String currrentDir = new File(".").getAbsolutePath().replaceAll("/\\.", "");
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        killPython();
                    }
                }));

                try {
                    Runtime.getRuntime().exec("gksudo python " + currrentDir + "/../Sensors/mami.py");
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

                        rollLowFilter.calculate(euler.roll);
                        smoothedRool = rollLowFilter.smoothedValue;
                        pitchLowFilter.calculate(euler.pitch);
                        smoothedPitch = pitchLowFilter.smoothedValue;
                        yawLowFilter.calculate(euler.yaw);
                        smoothedYaw = pitchLowFilter.smoothedValue;

                        /*rollLowFilter.calculate(euler.roll);
                        rollHighFilter.calculate(rollLowFilter.smoothedValue);
                        smoothedRool = rollHighFilter.smoothedValue;*/



                        //System.out.println(smoothedRool + ", " + smoothedPitch + ", " + smoothedYaw + ", ");
                        System.out.println(smoothedRool);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        recieverThread.start();
    }

    public static void stopReceving() {
        recieverThread.stop();
    }

    private static void killPython() {
        try {
            Runtime.getRuntime().exec("gksudo killall python");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
