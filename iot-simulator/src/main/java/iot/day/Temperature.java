package iot.day;

public class Temperature {

    private String deviceId;

    private double temperature;

    public Temperature() {
    }

    public Temperature(String deviceId, double temperature) {
        this.deviceId = deviceId;
        this.temperature = temperature;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "deviceId='" + deviceId + '\'' +
                ", temperature=" + temperature +
                '}';
    }

}
