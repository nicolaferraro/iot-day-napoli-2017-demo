#!/bin/bash

# This script is useful if you want to preload all the required images

docker pull fabric8/s2i-java:2.0
docker pull enmasseproject/kafka-statefulsets:0.1.0-rc1
docker pull enmasseproject/zookeeper:0.1.0-rc1
docker pull radanalyticsio/radanalytics-java-spark:v0.4.1
docker pull radanalyticsio/radanalytics-java-spark:stable
docker pull radanalyticsio/openshift-spark:2.2-latest