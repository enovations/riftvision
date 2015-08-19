package com.sourcegasm.riftvision.helper;

import java.util.regex.Pattern;

/**
 * Created by klemen on 18.8.2015.
 */
public class Quaternion {
	public double q0;
	public double q1;
	public double q2;
	public double q3;

	public Quaternion() {
	}

	public Quaternion(String csvLine) {
		String[] qs = csvLine.split(Pattern.quote(";"));
		q0 = Double.parseDouble(qs[0]);
		q1 = Double.parseDouble(qs[1]);
		q2 = Double.parseDouble(qs[2]);
		q3 = Double.parseDouble(qs[3]);
	}

	public Euler toEuler() {
		Euler result = new Euler();
		result.roll = Math.atan2(2 * (q3 * q0 + q1 * q2), (q0 * q0) + (q1 * q1) - (q2 * q2) - (q3 * q3)) * 180.0
				/ Math.PI;
		result.pitch = Math.atan2(2 * (q1 * q0 + q2 * q3), (q0 * q0) - (q1 * q1) - (q2 * q2) + (q3 * q3)) * 180.0
				/ Math.PI;
		result.yaw = -Math.asin(2 * (q1 * q3 - q2 * q0)) * 180.0 / Math.PI;
		return result;
	}
}
