#!/bin/bash
docker compose up mongo
sleep 5
docker exec mongo sh /data/scripts/setup_mongo.sh
