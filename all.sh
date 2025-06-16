#!/usr/bin/env bash

MODULES=java.base,java.logging
OUTPUT=jpicoc
VERSION=0.2
JARNAME=PiccodeScript-$VERSION-jar-with-dependencies.jar
JARDIR=target/

INSTALL=$HOME/.local/
INPUT=picoc
STDLIB=std

APP_NAME=picoc

log() {
  printf "[INFO] %s\n" "$1"
}

splash() {
  log "                        "
  log "▄▖▘       ▌  ▄▖    ▘  ▗ "
  log "▙▌▌▛▘▛▘▛▌▛▌█▌▚ ▛▘▛▘▌▛▌▜▘"
  log "▌ ▌▙▖▙▖▙▌▙▌▙▖▄▌▙▖▌ ▌▙▌▐▖"
  log "                    ▌   "
  log "         =====          "
  log "       BUILD AND        "
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

checkJarAndBuild() {
  if [ -d "$JARDIR" ]; then
    log "Project has been built. run mvn package to force building the jar file"
  else
    log "Building from scratch."
    mvn package
    log "Verifying the compiler."
		./verify.sh
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
  copyToDest $STDLIB $INPUT/lib/app
  rootCopyToDest $INPUT/bin $INSTALL
  rootCopyToDest $INPUT/lib $INSTALL
  log "Installation is done"
}

verify() {
	./verify.sh
}

finalCleanUp() {
	log "Final clean up"
	rm -rf $OUTPUT $APP_NAME $JARDIR
	log "Final clean up completed"
}

finalMessage() {
	log ""
	log "Please make sure to add $INSTALL/bin to your PATH"
	log "Try running picoc --version to test the installation"
	log "Run picoc --h for help and picoc run --repl for a quick repl session"
	log ""
	log "Thank you for installing PiccodeScript."
}

main() {
  splash
  checkJarAndBuild
  checkAndClean
  buildImage
  install
	finalCleanUp
	finalMessage
}

main
