package iot.day;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TemperatureDevicesSimulator {

    private DeviceStatusManager deviceStatusManager;

    public TemperatureDevicesSimulator(DeviceStatusManager deviceStatusManager) {
        this.deviceStatusManager = deviceStatusManager;
    }


    public List<Temperature> generateMeasurements() {
        int devices = deviceStatusManager.getNumberOfDevices();
        List<Temperature> temperatures = new LinkedList<>();
        for (int i=0; i<devices; i++) {
            temperatures.add(new Temperature("device-" + i, Math.random() * 20 + 10));
        }
        return temperatures;
    }

}
