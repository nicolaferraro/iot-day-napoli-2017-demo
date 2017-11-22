package iot.day;

public class Temperature {

    private String deviceId;

    private double temperature;

    private long timestamp;

    public Temperature() {
    }

    public Temperature(String deviceId, double temperature, long timestamp) {
        this.deviceId = deviceId;
        this.temperature = temperature;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "deviceId='" + deviceId + '\'' +
                ", temperature=" + temperature +
                ", timestamp=" + timestamp +
                '}';
    }

}
