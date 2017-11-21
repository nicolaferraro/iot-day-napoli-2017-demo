# iot-day-napoli-2017-demo

Requires Openshift.

Requires Kafka from https://github.com/EnMasseProject/barnabas

## Build everything

```
mvn clean install -Dfabric8.skip
```

## Deploy analysis:
From `iot-analysis` dir

```
mvn clean fabric8:deploy
```

## Deploy simulator:
From `iot-simulator` dir

```
mvn clean fabric8:deploy
```