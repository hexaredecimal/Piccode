#!/usr/bin/env bash


set -Eeuo pipefail

VERSION=1.0
EDITOR=VSCode

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
VSCODE_EXTENSION_DIR="$HOME/.vscode/extensions/piccodescript"

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
		if [[ -d "$HOME/.vscode/" ]] then
			VSCODE_EXTENSION_DIR="$HOME/.vscode/extensions/piccodescript"
			log "Set $EDITOR config to $VSCODE_EXTENSION_DIR"
		elif [[ -d "$HOME/.vscode-oss/" ]] then
			VSCODE_EXTENSION_DIR="$HOME/.vscode-oss/extensions/piccodescript"
			log "Set $EDITOR config to $VSCODE_EXTENSION_DIR"
		else
			log "Error: Unable to find Visual Studio Code Path in $HOME!"
			XYZ #cause an error
		fi
}

checkExension() {
  log "Looking for previous versions of this extension"
	if [[ -d $VSCODE_EXTENSION_DIR ]]
	then
        log "Removing old installation"
		rm -vrf $VSCODE_EXTENSION_DIR
        log "Prepare for new installation"
        mkdir -vp $VSCODE_EXTENSION_DIR
	else 
		log "No previous installation found. Preparing folder"
        mkdir -vp $VSCODE_EXTENSION_DIR
	fi
}

install() {
    log "Installation started"
    log "Copying files to $VSCODE_EXTENSION_DIR"
	cp -vr $SCRIPT_DIR/* $VSCODE_EXTENSION_DIR
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

