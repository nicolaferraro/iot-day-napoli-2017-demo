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
                .log("Received: ${body}")
                .transform(simple("${body.temperature} at timestamp ${body.timestamp}"))
                .setHeader("group", constant("avg"))
                .bean("analysisLog", "add");
//


    }
}
