package com.sourcegasm.riftvision;

import com.sourcegasm.riftvision.sensors.OculusSensors;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        OculusSensors.startReceiving();
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OculusSensors.stopReceving();
    }
}