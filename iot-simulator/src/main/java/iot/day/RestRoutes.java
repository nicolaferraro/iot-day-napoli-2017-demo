package iot.day;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class RestRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        rest().put("/devices")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                    .convertBodyTo(String.class)
                    .bean("deviceStatusManager", "setNumberOfDevices");

        rest().put("/defectiveDevices")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setNumberOfDefectiveDevices");

        rest().put("/meanTemperature")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setMeanTemperature");

        rest().put("/temperatureStdDev")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setTemperatureStdDev");

        rest().get("/avg")
                .consumes("application/json")
                .produces("application/json")
                .route()
                .setHeader("group", constant("avg"))
                .bean("analysisLog", "get")
                .marshal().json(JsonLibrary.Jackson);



    }
}
