package com.sourcegasm.riftvision.control;

/**
 * Created by ziga on 21.8.2014.
 */

public class ExpoController {
	
	public static double getExpo(double power) {
		double result;
		if (power < 0)
			result = -(50 * Math.exp(-0.035 * power) - 50);
		else
			result = 50 * Math.exp(0.035 * power) - 50;
		if (result > 30)
			return 30;
		if (result < -30)
			return -30;
		return result;
	}
	
	public static double getJoyStickExpo(double power) {
		if (power < 0)
			return -(0.6 * Math.exp(-0.035 * power) - 0.6);
		return 0.6 * Math.exp(0.035 * power) - 0.6;
	}

}
