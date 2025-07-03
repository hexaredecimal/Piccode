#!/usr/bin/env bash

set -Eeuo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
EXT=lua
FILE=$SCRIPT_DIR/*.$EXT
VERSION=1.0
EDITOR=Lite-XL
INSTALL=$HOME/.config/lite-xl/plugins/piccodescript

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
    log "          FOR           "
    log "          $EDITOR       "
    log "         =====          "
    log "                        "
    log "   (c) Hexaredecimal    "
    log "                        "
}


checkInstallDir() {
    if [ -d "$INSTALL" ]; then
        log "$INSTALL is already present"
    else
        log "Creating $INSTALL directory"
        mkdir -p $INSTALL
        log "Done creating $INSTALL"
    fi
}

copyToDest() {
    log "copying $1 to $2"
    cp -r $1 $2
}


install() {
  checkInstallDir
  log "Installation stated"
  copyToDest $FILE $INSTALL
  log "Installation is done"
}

finalMessage() {
    log ""
    log "Thank you for installing the piccodescript mode for $EDITOR"
}

handle_error() {
    log "Something went wrong"
    log "Aborting"
}

trap handle_error ERR

main() {
  splash
  install
  finalMessage
}

main
