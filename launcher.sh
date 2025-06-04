#!/bin/sh

# compile sources in background on change
apt-get update && apt-get install -y inotify-tools

# run compiler in background
(
  while inotifywait -r -e modify,create,delete,move src; do
    ./mvnw compile
  done
) &

# start spring boot dev server
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
