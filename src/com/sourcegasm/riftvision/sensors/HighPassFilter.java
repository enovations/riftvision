package com.sourcegasm.riftvision.sensors;

/**
 * Created by klemen on 18.8.2015.
 */
public class HighPassFilter {
    private boolean first = true;
    public double smoothedValue;
    public int smoothing;

    public HighPassFilter(int smoothing){
        this.smoothing = smoothing;
    }

    public double calculate(double newValue) {
        if(first) {
            smoothedValue = newValue;
            first = false;
            return newValue;
        }else {
            smoothedValue = newValue - (smoothedValue + (newValue - smoothedValue));
            return smoothedValue;
        }
    }

    public void clear(){
        first = true;
    }
}
