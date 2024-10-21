#!/bin/bash
./gradlew clean
./gradlew build -x test
docker build -t spring2 .
