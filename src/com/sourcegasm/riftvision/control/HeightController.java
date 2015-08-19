package com.sourcegasm.riftvision.control;

/**
 * Created by klemen on 19.8.2015.
 */
public class HeightController {
	private int up = 0;

	public double getHeightMove() {
		double result = up / 100.0;
		if (up > 0)
			--up;
		else if (up < 0)
			++up;
		//TODO: max values, up/down
		return result;
	}

	public void reset(){
		up = 0;
	}

	public void increaseHeightMove() {
		up += 20;
	}

	public void decreaseHeightMove() {
		up -= 20;
	}
}
