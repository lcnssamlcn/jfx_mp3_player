#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
APPL_VERSION="1.0"
APPL="mp3_player-${APPL_VERSION}.command"


cd "$HOME/Desktop"
ln -s "${SCRIPT_DIR}/run_osx.command" "${APPL}"
