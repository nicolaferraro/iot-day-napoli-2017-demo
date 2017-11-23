package iot.day;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class SimulatorRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:clock?period=10000")
                .bean("temperatureDevicesSimulator", "generateMeasurements")
                .split().body()
                .setHeader(KafkaConstants.KEY).simple("body.deviceId", String.class)
                .marshal().json(JsonLibrary.Jackson)
                .log("Generated: ${body}")
                .to("kafka:temperature");

        from("kafka:temperature.avg")
                .unmarshal().json(JsonLibrary.Jackson, Temperature.class)
                .log("Received from temperature.avg topic: ${body}")
                .transform(simple("${body.temperature}"))
                .setHeader("group", constant("avg"))
                .bean("analysisLog", "add");

        from("kafka:temperature.favg")
                .unmarshal().json(JsonLibrary.Jackson, Temperature.class)
                .log("Received from temperature.favg topic: ${body}")
                .transform(simple("${body.temperature}"))
                .setHeader("group", constant("favg"))
                .bean("analysisLog", "add");

        from("kafka:action")
                .log("Received from action topic: ${body}")
                .setHeader("group", constant("action"))
                .bean("analysisLog", "add");
    }
}
