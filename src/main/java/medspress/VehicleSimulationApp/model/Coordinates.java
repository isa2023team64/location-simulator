package medspress.VehicleSimulationApp.model;

public class Coordinates {
    private double latitude;
    private double longitude;
    private int deliveryId;

    public Coordinates(double latitude, double longitude, int deliveryId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.deliveryId = deliveryId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getDeliveryId() {
        return deliveryId;
    }
}
