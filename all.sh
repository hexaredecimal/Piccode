#!/usr/bin/env bash

MODULES=java.base,java.logging,java.net.http
OUTPUT=jpicoc
VERSION=0.2
JARNAME=PiccodeScript-$VERSION-jar-with-dependencies.jar

INSTALL=$HOME/.local/
INPUT=picoc
STDLIB=std

APP_NAME=picoc

log() {
  printf "[INFO]: %s\n" "$1"
}

splash() {
  log "                        "
  log "▄▖▘       ▌  ▄▖    ▘  ▗ "
  log "▙▌▌▛▘▛▘▛▌▛▌█▌▚ ▛▘▛▘▌▛▌▜▘"
  log "▌ ▌▙▖▙▖▙▌▙▌▙▖▄▌▙▖▌ ▌▙▌▐▖"
  log "                    ▌   "
  log "         =====          "
  log "   INSTALLATION SCRIPT  "
  log "         =====          "
  log "                        "
  log "   (c) Hexaredecimal    "
  log "                        "
}

checkAndClean() {
  if [ -d "$OUTPUT" ]; then
    log "Cleaning up from previous packaging"
    rm -rf $OUTPUT $APP_NAME
  else
    log "Nothing to clean"
  fi
}

buildImage() {
  log "Creating custom jdk runtime-image"
  jlink --add-modules $MODULES --output $OUTPUT
  log "Packaging app into native-image"
  jpackage --type app-image -n $APP_NAME --main-jar $JARNAME --runtime-image $OUTPUT -i target
}

checkDir() {
  if [ -d "$OUTPUT" ]; then
    log "Adding the $OUTPUT directory"
    mkdir -p $OUTPUT
  else
    log "$OUTPUT directory is already created"
  fi
}

copyToDest() {
  log "copying $1 to $2"
  cp -r $1 $2
}

rootCopyToDest() {
  log "root copying $1 to $2"
  sudo cp -r $1 $2
}

install() {
  checkDir
  log "Installation stated"
  rootCopyToDest $INPUT/bin $INSTALL
  rootCopyToDest $INPUT/lib $INSTALL
  copyToDest $STDLIB $OUTPUT/lib/app
  log "Installation is done"
}

main() {
  splash
  checkAndClean
  buildImage
  install
}

main
