package com.sourcegasm.riftvision.sensors;

/**
 * Created by klemen on 18.8.2015.
 */
public class HighPassFilter {
	private boolean first = true;
	public double smoothedValue;
	public double smoothing;

	private double lastInputValue;

	public HighPassFilter(double smoothing) {
		this.smoothing = smoothing;
	}

	public double calculate(double newValue) {
		if (first) {
			lastInputValue = newValue;
			smoothedValue = newValue;
			first = false;
			return newValue;
		} else {
			// α := RC / (RC + dt)
			smoothedValue = smoothing * (smoothedValue + newValue - lastInputValue);
			lastInputValue = newValue;
			return smoothedValue;
		}
	}

	public void clear() {
		first = true;
	}
}
