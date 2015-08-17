package com.sourcegasm.riftvision;

import java.util.regex.Pattern;

/**
 * Created by zigapk on 8/17/15.
 */
public class QuaternionToEulerConverter {

}
class Quaternion{
    double q0;
    double q1;
    double q2;
    double q3;

    public Quaternion(){}
    public Quaternion(String csvLine){
        String[] qs = csvLine.split(Pattern.quote(";"));
        this.q0 = Double.parseDouble(qs[0]);
        this.q1 = Double.parseDouble(qs[1]);
        this.q2 = Double.parseDouble(qs[2]);
        this.q3 = Double.parseDouble(qs[3]);
    }

    public Euler toEuler(){
        Euler result = new Euler();
        result.rool = Math.atan((2*(q1*q0+q2*q3))/((q0*q0)-(q1*q1)-(q2*q2)+(q3*q3)));
        result.pitch = -Math.asin(2*(q1*q3-q2*q0));
        result.yaw = Math.atan((2*(q3*q0+q1*q2))/((q0*q0)+(q1*q1)-(q2*q2)-(q3*q3)));
        return result;
    }
}
class Euler{
    double rool;
    double pitch;
    double yaw;
}
