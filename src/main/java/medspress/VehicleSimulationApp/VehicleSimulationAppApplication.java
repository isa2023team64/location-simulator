package medspress.VehicleSimulationApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Scanner;

@SpringBootApplication
public class VehicleSimulationAppApplication {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    public static void main(String[] args) {
        SpringApplication.run(VehicleSimulationAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message to publish (type 'exit' to quit): ");
                String input = scanner.nextLine();

                if ("exit".equalsIgnoreCase(input.trim())) {
                    System.out.println("Exiting...");
                    break;
                }

                kafkaTemplate.send(topic, input);
                System.out.println("Message published to Kafka: " + input);
            }

            scanner.close();
        };
    }
}
