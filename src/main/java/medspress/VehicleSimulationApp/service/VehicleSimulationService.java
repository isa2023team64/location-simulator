package medspress.VehicleSimulationApp.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import medspress.VehicleSimulationApp.model.Coordinates;

@Service
public class VehicleSimulationService {

    public List<Coordinates> simulateVehicleMovement(Coordinates start, Coordinates end) {

        List<Coordinates> coordinatesList = new ArrayList<>();

        double totalDistance = calculateDistance(start, end);
        double distanceStep = 0.00001;
    
        double currentDistance = 0;
    
        while (currentDistance < totalDistance) {
            double ratio = currentDistance / totalDistance;
            double latitude = start.getLatitude() + (ratio * (end.getLatitude() - start.getLatitude()));
            double longitude = start.getLongitude() + (ratio * (end.getLongitude() - start.getLongitude()));
            coordinatesList.add(new Coordinates(latitude, longitude));
            currentDistance += distanceStep;
        }
    
        return coordinatesList;
    }

    private double calculateDistance(Coordinates point1, Coordinates point2) {

        double latDiff = point2.getLatitude() - point1.getLatitude();
        double lonDiff = point2.getLongitude() - point1.getLongitude();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
}
