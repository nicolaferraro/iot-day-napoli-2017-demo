package iot.day;

import org.springframework.stereotype.Component;

@Component
public class DeviceStatusManager {

    private volatile int numberOfDevices = 1;

    private volatile int numberOfDefectiveDevices = 0;

    private volatile double meanTemperature = 20;

    private volatile double temperatureStdDev = 2;

    public DeviceStatusManager() {
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(int numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }


    public int getNumberOfDefectiveDevices() {
        return numberOfDefectiveDevices;
    }

    public void setNumberOfDefectiveDevices(int numberOfDefectiveDevices) {
        this.numberOfDefectiveDevices = numberOfDefectiveDevices;
    }

    public double getMeanTemperature() {
        return meanTemperature;
    }

    public void setMeanTemperature(double meanTemperature) {
        this.meanTemperature = meanTemperature;
    }

    public double getTemperatureStdDev() {
        return temperatureStdDev;
    }

    public void setTemperatureStdDev(double temperatureStdDev) {
        this.temperatureStdDev = temperatureStdDev;
    }
}
