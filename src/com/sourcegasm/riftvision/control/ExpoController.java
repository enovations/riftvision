package com.sourcegasm.riftvision.control;

/**
 * Created by ziga on 21.8.2014.
 */

public class ExpoController {
	public static double getExpo(double power) {
		double result;
		if (power < 0)
			result = -(40 * Math.exp(-0.035 * power) - 40);
		else
			result = 40 * Math.exp(0.035 * power) - 40;
		if (result > 25)
			return 25;
		if (result < -25)
			return -25;
		return result;
	}

}
