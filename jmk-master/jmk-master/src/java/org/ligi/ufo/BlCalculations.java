package org.ligi.ufo;

/**
 * Created by asay on 17.11.2014.
 */
public class BlCalculations {

    public double getRadians(double n) {
        return (n * (Math.PI / 180));
    }

    public double getDegrees(double n) {
        return (n * (180 / Math.PI));
    }

    public double getBearing(double startLat, double startLong, double endLat, double endLong) {
        startLat = getRadians(startLat);
        startLong = getRadians(startLong);
        endLat = getRadians(endLat);
        endLong = getRadians(endLong);

        double dLong = endLong - startLong;

        double dPhi = Math.log(Math.tan(endLat / 2.0 + Math.PI / 4.0) / Math.tan(startLat / 2.0 + Math.PI / 4.0));
        if (Math.abs(dLong) > Math.PI) {
            if (dLong > 0.0) {
                dLong = -(2.0 * Math.PI - dLong);
            } else {
                dLong = (2.0 * Math.PI + dLong);
            }
        }

        return((getDegrees( Math.atan2(dLong, dPhi)) + 360.0) % 360.0);
    }

}
