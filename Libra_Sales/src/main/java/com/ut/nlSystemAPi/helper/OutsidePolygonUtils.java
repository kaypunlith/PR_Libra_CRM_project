package com.ut.nlSystemAPi.helper;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class OutsidePolygonUtils {

    public static boolean isLocationOutsideOfZone(String zoneLats, String zoneLongs, String pointLat, String pointLong) {
        // Validate inputs
        if (zoneLats == null || zoneLongs == null || pointLat == null || pointLong == null) {
            return true; // Invalid inputs, consider point outside
        }

        try {
            // Parse zone coordinates (assuming comma-separated strings)
            String[] latStrings = zoneLats.split(",");
            String[] longStrings = zoneLongs.split(",");
            if (latStrings.length < 3 || latStrings.length != longStrings.length) {
                return true; // Invalid polygon, needs at least 3 points
            }

            double[] zoneLatitudes = new double[latStrings.length];
            double[] zoneLongitudes = new double[longStrings.length];
            for (int i = 0; i < latStrings.length; i++) {
                zoneLatitudes[i] = Double.parseDouble(latStrings[i].trim());
                zoneLongitudes[i] = Double.parseDouble(longStrings[i].trim());
            }

            // Parse customer coordinates
            double latitude = Double.parseDouble(pointLat.trim());
            double longitude = Double.parseDouble(pointLong.trim());

            // Create polygon
            Path2D.Double myPolygon = new Path2D.Double();
            myPolygon.moveTo(zoneLatitudes[0], zoneLongitudes[0]); // First point
            for (int i = 1; i < zoneLatitudes.length; i++) {
                myPolygon.lineTo(zoneLatitudes[i], zoneLongitudes[i]); // Draw lines
            }
            myPolygon.closePath(); // Close the polygon

            // Check if point is inside
            Point2D.Double point = new Point2D.Double(latitude, longitude);
            return !myPolygon.contains(point); // True if outside, false if inside
        } catch (NumberFormatException e) {
            // Handle parsing errors
            return true; // Consider point outside if coordinates are invalid
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return dist;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}