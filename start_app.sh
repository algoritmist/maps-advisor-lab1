#!/bin/bash
scripts/start_replicas.sh &
sleep 20
scripts/setup_spring.sh &
sleep 20
docker compose up spring-app mongo-express
