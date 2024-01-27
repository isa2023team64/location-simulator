package medspress.VehicleSimulationApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import medspress.VehicleSimulationApp.config.SimulationConfig;

import java.util.Scanner;

@SpringBootApplication
public class VehicleSimulationAppApplication {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${spring.kafka.topic}")
    private String topic;

    @Autowired
    private SimulationConfig simulationConfig;

    public static void main(String[] args) {
        SpringApplication.run(VehicleSimulationAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                printMainMenu();

                System.out.print("Enter option number: ");
                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        setSleepTime(scanner);
                        break;
                    case "2":
                        publishMessage(scanner);
                        break;
                    case "3":
                        System.out.println("Exiting...");                       
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
                }
            }
        };
    }

    private void printMainMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Set sleep time");
        System.out.println("2. Publish message");
        System.out.println("3. Exit");
    }

    private void setSleepTime(Scanner scanner) {
        System.out.print("Enter sleep time (in seconds): ");
        String sleepTimeInput = scanner.nextLine();

        try {
            int sleepTime = Integer.parseInt(sleepTimeInput);
            simulationConfig.setThreadSleepTime(sleepTime);
            System.out.println("Thread sleep time updated to " + sleepTime + " seconds.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid sleep time format. Please enter a valid integer.");
        }
    }

    private void publishMessage(Scanner scanner) {
        System.out.print("Enter message to publish: ");
        String message = scanner.nextLine();

        kafkaTemplate.send(topic, message);
        System.out.println("Message published to Kafka: " + message);
    }
}
