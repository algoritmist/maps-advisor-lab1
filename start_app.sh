#!/bin/bash
./configure_spring.sh &
./start_replicas.sh &
docker compose up
