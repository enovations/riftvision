package com.sourcegasm.riftvision.control;

import com.sourcegasm.riftvision.sensors.OculusSensors;

/**
 * Created by klemen on 19.8.2015.
 */
public class YawController {
    public double getYawMove(DroneController droneController, OculusSensors sensors){
        double droneYaw = droneController.getNavData().getYaw();
        double oculusYaw = sensors.getSmoothedYaw();
        double result = droneYaw - oculusYaw;
        if(result > 180) result -= 360;
        else if(result < -180) result += 360;
        result /= 100;
        if(result>0.5) return 0.5;
        if(result<-0.5) return -0.5;
        return result;
    }
}
