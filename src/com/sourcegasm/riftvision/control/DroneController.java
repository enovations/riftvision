package com.sourcegasm.riftvision.control;

import java.io.IOException;
import java.net.UnknownHostException;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.NavData;

//Controlling the quad copter based on enabled mode

public class DroneController {

	ARDrone drone = null;
	NavData data = null;

	public DroneController() {
		try {

			drone = new ARDrone();
			drone.connect();

			drone.addNavDataListener(fdata -> data = fdata);

		} catch (final UnknownHostException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public ARDrone getDrone() {
		return drone;
	}

	public NavData getNavData() {
		return data;
	}

}