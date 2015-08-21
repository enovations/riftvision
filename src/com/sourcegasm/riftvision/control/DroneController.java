package com.sourcegasm.riftvision.control;

import java.io.IOException;
import java.net.UnknownHostException;

import com.codeminders.ardrone.ARDrone;
import com.codeminders.ardrone.NavData;
import com.codeminders.ardrone.NavDataListener;

//Controlling the quad copter based on enabled mode

public class DroneController {

	ARDrone drone = null;
	NavData data = null;

	public DroneController() {
		try {

			drone = new ARDrone();
			drone.connect();

			drone.addNavDataListener(new NavDataListener() {

				@Override
				public void navDataReceived(NavData fdata) {
					data = fdata;
				}
			});

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
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