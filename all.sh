#!/usr/bin/env bash

set -Eeuo pipefail

JARDIR=target
VERSION=1.0
JARNAME=$JARDIR/PiccodeScript-$VERSION-jar-with-dependencies.jar

INSTALL=$HOME/.local/bin
RUNNER=picoc
STDLIB=std


log() {
  printf "[INFO] %s\n" "$1"
}

splash() {
  log "                        "
  log "▄▖▘       ▌  ▄▖    ▘  ▗ "
  log "▙▌▌▛▘▛▘▛▌▛▌█▌▚ ▛▘▛▘▌▛▌▜▘"
  log "▌ ▌▙▖▙▖▙▌▙▌▙▖▄▌▙▖▌ ▌▙▌▐▖"
  log "                    ▌   "
  log "                        "
  log "          $VERSION      "
  log "                        "
  log "         =====          "
  log "       BUILD AND        "
  log "   INSTALLATION SCRIPT  "
  log "         =====          "
  log "                        "
  log "   (c) Hexaredecimal    "
  log "                        "
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

copyToDest() {
  log "copying $1 to $2"
  cp -r $1 $2
}

rootCopyToDest() {
  log "root copying $1 to $2"
  sudo cp -r $1 $2
}

rootMakeExec() {
  log "root adding executable permissions to $1"
  sudo chmod +x $1
}


checkDir() {
  if [ -d "$INSTALL" ]; then
    log "Making the $INSTALL directory"
    mkdir -p $INSTALL
  else
    log "$INSTALL directory is already created"
  fi
}

install() {
  checkDir
  log "Installation stated"
  copyToDest $STDLIB $INSTALL
  rootCopyToDest $RUNNER $INSTALL
  rootCopyToDest $JARNAME $INSTALL
  rootMakeExec $INSTALL/$RUNNER
  log "Installation is done"
}

finalCleanUp() {
  log "Final clean up"
  rm -rf $JARDIR
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

handle_error() {
  log "Something went wrong"
  finalCleanUp
}

trap handle_error ERR

main() {
  splash
  checkJarAndBuild
  install
  finalCleanUp
  finalMessage
}

main
