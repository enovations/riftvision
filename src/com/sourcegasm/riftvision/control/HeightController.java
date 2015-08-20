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
        return result;
    }

    public void increaseHeightMove() {
        if (up < 100) up += 40;
    }

    public void decreaseHeightMove() {
        if (up > -100) up -= 40;
    }

    public void reset() {
        up = 0;
    }
}
