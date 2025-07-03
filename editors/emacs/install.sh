#!/usr/bin/env bash

set -Eeuo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
EXT=el
FILE=$SCRIPT_DIR/*.$EXT
VERSION=1.0
EDITOR=Emacs
INSTALL=$HOME/.emacs.local
CONFIG=$HOME/.emacs.d/init.el

INSTALL_LINE="\n(add-to-list 'load-path \"~/.emacs.local\")\n\
(load-file \"~/.emacs.local/piccodescript-mode.el\")\n\
(require 'piccode-mode)\n\
(add-to-list 'auto-mode-alist '(\"\\\\.pics\\\\'\" . piccode-mode))\n\n"

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


writeConfig() {
    if grep -q "(require 'piccode-mode)'" "$CONFIG"; then
        log "Note: Plugin already activated"
    else
        echo -e $INSTALL_LINE >> $CONFIG
    fi
}

checkConfig() {
    if ! [[ -f "$CONFIG" ]]; then
        log "Error: Unable to find $CONFIG."
        log "Add the following lines to your config."
        log "$INSTALL_LINE"
    else
        writeConfig
    fi
}

checkInstallDir() {
    if [ -d "$INSTALL" ]; then
        log "$INSTALL is already present"
    else
        log "Creating $INSTALL directory"
        mkdir $INSTALL
        log "Done creating $INSTALL"
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
  checkInstallDir
  log "Installation stated"
  copyToDest $FILE $INSTALL
  log "Installation is done"
  checkConfig
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
