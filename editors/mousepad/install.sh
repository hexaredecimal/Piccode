#!/usr/bin/env bash


set -Eeuo pipefail

VERSION=1.0
EDITOR=MOUSEPAD

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
MOUSEPAD_EXTENSION_DIR="$HOME/.local/share/gtksourceview-4/language-specs/"
LANGFILE=piccodescript.lang


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
    log "         $EDITOR        "
    log "         =====          "
    log "                        "
    log "   (c) Hexaredecimal    "
    log "                        "
}

checkConfig() {
    log "Seaching for $EDITOR config folder"
		if [[ -d "$MOUSEPAD_EXTENSION_DIR" ]] then
			log "Set $EDITOR config to $MOUSEPAD_EXTENSION_DIR"
		else
			log "Error: Unable to find Visual Studio Code Path in $HOME!"
			XYZ #cause an error
		fi
}

checkExension() {
  log "Looking for previous versions of this extension"
	if [[ -d $MOUSEPAD_EXTENSION_DIR ]]
	then
        log "Removing old installation"
		rm -vrf $MOUSEPAD_EXTENSION_DIR
        log "Prepare for new installation"
        mkdir -vp $MOUSEPAD_EXTENSION_DIR
	else 
		log "No previous installation found. Preparing folder"
        mkdir -vp $MOUSEPAD_EXTENSION_DIR
	fi
}

install() {
    log "Installation started"
    log "Copying files to $MOUSEPAD_EXTENSION_DIR"
	cp -vr $SCRIPT_DIR/$LANGFILE $MOUSEPAD_EXTENSION_DIR
    log "Installation done"
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
	checkConfig
	checkExension
	install
	finalMessage
}

main

