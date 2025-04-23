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
./mvnw spring-boot:run
