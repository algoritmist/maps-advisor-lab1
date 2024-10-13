docker-compose --env-file test_env.env up &
sleep 30
./gradlew test