#!/bin/bash
docker compose up mongo
sleep 5
docker exec mongo /setup_mongo.sh
