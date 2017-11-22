# IoT Day - Napoli 2017

This demo shows how to analyze IoT streaming data using Apache Spark on Openshift.

Data generation is simulated using a Spring-Boot web application leveraging [Apache Camel](https://camel.apache.org) as integration library.

A instance of Apache Kafka (in-memory) from the [EnMasse project](https://github.com/EnMasseProject/barnabas) will be used 
for the ingestion.

Apache Spark will run on Openshift using [Radanalytics](https://radanalytics.io/) images.    

## Prerequisites

You need the Openshift `oc` binaries that can be downloaded from the [Openshift origin repository](https://github.com/openshift/origin/releases).

You can run the containerized version of Openshift, or use You can use [Minishift](https://github.com/minishift/minishift).

You also need the Oshinko binaries that can be downloaded from the [Radanalytics](https://github.com/radanalyticsio/oshinko-cli/releases) repository.

## Running the demo

### 1. Start Openshift

For this demo, a development instance of Openshift is required.
 
After installing the Openshift `oc` binary, you can just run the following command to start a containerized version of Openshift: 

```
oc cluster up
```

As alternative, you can use Minishift and run `minishift start` instead of the previous command.

### 2. Deploy Kafka (and Zookeeper)

This project includes a Apache Kafka template from the EnMasse project. 
You can install it using the following commands:

```
oc create -f ./kafka-inmemory/openshift-template.yml
oc new-app barnabas
```

### 3. Build the root module

Build the root module of this project using:

```
mvn clean install -N
```

### 4. Deploy the simulator

Jump into the simulator directory and deploy it:

```
cd iot-simulator
mvn clean fabric8:deploy
cd ..
```

### 5. Create a Spark cluster

You need to create a Spark cluster using the Oshinko cli:

```
oshinko create iot-cluster
```

### 6. Deploy the analysis module

Jump into the analysis directory and deploy the spark driver:

```
cd iot-analysis
mvn clean fabric8:deploy
```

