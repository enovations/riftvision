package com.sourcegasm.riftvision.sensors;

import java.io.File;
import java.io.IOException;

public class SensorPythonGateway {

	public static void killPython() {
		try {
			Runtime.getRuntime().exec("gksudo killall python");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startPythonScripts() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                SensorPythonGateway.killPython();
            }
        }));
		try {
			String currrentDir = new File(".").getAbsolutePath().replaceAll("/\\.", "");
            Runtime.getRuntime().exec("gksudo python " + currrentDir + "/mami.py &");
            Runtime.getRuntime().exec("gksudo python " + currrentDir + "/zigatova_mami.py &");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
