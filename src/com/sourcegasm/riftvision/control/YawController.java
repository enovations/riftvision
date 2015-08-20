package com.sourcegasm.riftvision.control;

import com.sourcegasm.riftvision.sensors.OculusSensors;

/**
 * Created by klemen on 19.8.2015.
 */
public class YawController {

    private double oculusZero = 0;
    private double droneZero = 0;

	public double getYawMove(DroneController droneController, OculusSensors sensors) {
		double droneYaw = newAngle(droneController.getNavData().getYaw(), droneZero);
		double oculusYaw = newAngle(sensors.getSmoothedYaw(), oculusZero);
		double result = newAngle(oculusYaw, droneYaw);
		result /= 180;
		if (result > 0.5)
			return 0.5;
		if (result < -0.5)
			return -0.5;
		return result;
	}

    private static double newAngle(double angle, double correction){
        double result = angle - correction;
        if (result > 180)
            result -= 360;
        else if (result < -180)
            result += 360;
        return result;
    }

	public void setZero(DroneController droneController, OculusSensors sensors){
        while (droneController.getNavData() == null){}
        droneZero = droneController.getNavData().getYaw();
        oculusZero = sensors.getSmoothedYaw();
	}
}
