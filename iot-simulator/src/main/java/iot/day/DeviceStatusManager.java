package iot.day;

import org.springframework.stereotype.Component;

@Component
public class DeviceStatusManager {

    private volatile int numberOfDevices = 1;

    public DeviceStatusManager() {
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(int numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }
}
