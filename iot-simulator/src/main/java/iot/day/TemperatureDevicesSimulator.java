package iot.day;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Component
public class TemperatureDevicesSimulator {

    private DeviceStatusManager deviceStatusManager;

    public TemperatureDevicesSimulator(DeviceStatusManager deviceStatusManager) {
        this.deviceStatusManager = deviceStatusManager;
    }


    public List<Temperature> generateMeasurements() {
        int devices = deviceStatusManager.getNumberOfDevices();
        int defectiveDevices = deviceStatusManager.getNumberOfDefectiveDevices();

        List<Temperature> temperatures = new LinkedList<>();

        for (int i=0; i<devices; i++) {
            temperatures.add(new Temperature("device-" + i, new Random().nextGaussian() * deviceStatusManager.getTemperatureStdDev() + deviceStatusManager.getMeanTemperature(), System.currentTimeMillis()));
        }

        for (int i=0; i<defectiveDevices; i++) {
            temperatures.add(new Temperature("device-" + (devices + i), new Random().nextGaussian() * deviceStatusManager.getTemperatureStdDev() * 2 + deviceStatusManager.getMeanTemperature() * 4, System.currentTimeMillis()));
        }
        return temperatures;
    }

}
