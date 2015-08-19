package com.sourcegasm.riftvision.control;

/**
 * Created by ziga on 21.8.2014.
 */

public class ExpoController {
	public static double getExpo(double power) {
		double result;
		if (power < 0)
			result = -(20 * Math.exp(-0.035 * power) - 20);
		else
			result = 20 * Math.exp(0.035 * power) - 20;
		if (result > 20)
			return 20;
		if (result < -20)
			return -20;
		return result;
	}

}
