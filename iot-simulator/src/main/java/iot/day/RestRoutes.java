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

        rest().get("/devices")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "getNumberOfDevices");

        rest().put("/defectiveDevices")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setNumberOfDefectiveDevices");

        rest().get("/defectiveDevices")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "getNumberOfDefectiveDevices");

        rest().put("/meanTemperature")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setMeanTemperature");

        rest().get("/meanTemperature")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "getMeanTemperature");

        rest().put("/temperatureStdDev")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "setTemperatureStdDev");

        rest().get("/temperatureStdDev")
                .consumes("text/plain")
                .produces("text/plain")
                .route()
                .convertBodyTo(String.class)
                .bean("deviceStatusManager", "getTemperatureStdDev");

        // ------------------------------------------------------------------------

        rest().get("/avg")
                .consumes("application/json")
                .produces("application/json")
                .route()
                .setHeader("group", constant("avg"))
                .bean("analysisLog", "get")
                .marshal().json(JsonLibrary.Jackson);

        rest().get("/favg")
                .consumes("application/json")
                .produces("application/json")
                .route()
                .setHeader("group", constant("favg"))
                .bean("analysisLog", "get")
                .marshal().json(JsonLibrary.Jackson);

        rest().get("/action")
                .consumes("application/json")
                .produces("application/json")
                .route()
                .setHeader("group", constant("action"))
                .bean("analysisLog", "get")
                .marshal().json(JsonLibrary.Jackson);



    }
}
