package medspress.VehicleSimulationApp.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import medspress.VehicleSimulationApp.model.Coordinates;
import medspress.VehicleSimulationApp.service.VehicleSimulationService;

@RestController
public class PublishMessage {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private VehicleSimulationService vehicleSimulationService;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    @GetMapping("/publish/KafkaMessage/{message}")
    public ResponseEntity<String> publishMessage(@PathVariable String message) {
        System.out.println("Publishing " + message + " to topic " + topic);
        kafkaTemplate.send(topic, message);
        return new ResponseEntity<>("Message published", HttpStatus.OK);
    }

    @GetMapping(value = "/coordinatesFromKafka", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> coordinatesFromKafka(@RequestBody Coordinates coordinates) {
        String message = String.format("{\"latitude\": %f, \"longitude\": %f}", coordinates.getLatitude(), coordinates.getLongitude());
        kafkaTemplate.send(topic, message);
        return new ResponseEntity<>("Message published", HttpStatus.OK);
    }

    @PostMapping("/sendCoordinates")
    public ResponseEntity<String> sendCoordinatesToKafka(@RequestBody Coordinates coordinates) {
        String message = String.format("{\"latitude\": %f, \"longitude\": %f}", coordinates.getLatitude(), coordinates.getLongitude());
        kafkaTemplate.send(topic, message);
        return new ResponseEntity<>("Coordinates published", HttpStatus.OK);
    }

    @PostMapping(value = "/delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeDelivery(@RequestBody List<Coordinates> deliveryRequest) {
        try {
            Coordinates start = deliveryRequest.get(0);
            Coordinates end = deliveryRequest.get(deliveryRequest.size() - 1);

            List<Coordinates> coordinatesList = vehicleSimulationService.simulateVehicleMovement(start, end);

            for (Coordinates coordinates : coordinatesList) {
                String message = String.format("{\"latitude\": %f, \"longitude\": %f}", coordinates.getLatitude(), coordinates.getLongitude());
                kafkaTemplate.send(topic, message);

                // Introduce a 30-second sleep between sending each set of coordinates
                Thread.sleep(30000);
            }

            return new ResponseEntity<>("Coordinates published", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error publishing coordinates", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
