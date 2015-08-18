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

    private static Thread recieverThread = new Thread();

    public static void startReceiving(){
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
                        Euler euler = new Quaternion(line).toEuler();
                        System.out.println(euler.roll + ", " + euler.pitch + ", " + euler.yaw);
                        packet.setLength(buffer.length);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        });
        recieverThread.start();
    }

    public static void stopReceving(){
        recieverThread.stop();
    }

    private static void killPython(){
        try {
            Runtime.getRuntime().exec("gksudo killall python");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
