package medspress.VehicleSimulationApp.service;
import org.springframework.stereotype.Service;

import medspress.VehicleSimulationApp.model.Coordinates;

@Service
public class VehicleSimulationService {
     public Coordinates simulateVehicleMovement() {
        // Simulate vehicle movement and generate coordinates
        // Replace this with your actual logic to generate coordinates
        return new Coordinates(123.456, 789.012);
    }
}
