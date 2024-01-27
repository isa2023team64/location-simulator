package medspress.VehicleSimulationApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulationConfig {

    @Value("${simulation.thread-sleep-time}")
    private int threadSleepTime;

    public int getThreadSleepTime() {
        return threadSleepTime;
    }

    
    public void setThreadSleepTime(int threadSleepTime) {
        this.threadSleepTime = threadSleepTime;
    }
}
