#!/usr/bin/env bash

MODULES=java.base,java.logging,java.net.http
OUTPUT=jpicoc
VERSION=0.2
JARNAME=PiccodeScript-$VERSION-jar-with-dependencies.jar

APP_NAME=picoc

log() {
  printf "[INFO]: %s\n" "$1"
}

checkAndClean() {
  if [ -d "$OUTPUT" ]; then
    log "Cleaning up from previous packaging"
    rm -rf $OUTPUT $APP_NAME
  else
    log "Nothing to clean"
  fi
}

build() {
  log "Creating custom jdk runtime-image"
  jlink --add-modules $MODULES --output $OUTPUT
  log "Packaging app into native-image"
  jpackage --type app-image -n $APP_NAME --main-jar $JARNAME --runtime-image $OUTPUT -i target
}

main() {
  checkAndClean
  build
}

main
