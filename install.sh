#!/usr/bin/bash

OUTPUT=~/.local/
INPUT=picoc
STDLIB=std

log() {
  printf "[INFO]: %s\n" "$1"
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
  log "Copying $1 to $2"
  cp -r $1 $2
}

main() {
  log "Installation stated"
  copyToDest $INPUT/bin $OUTPUT
  copyToDest $INPUT/lib $OUTPUT
  copyToDest $STDLIB $OUTPUT/lib/app
  log "Installation is done"
}

main
