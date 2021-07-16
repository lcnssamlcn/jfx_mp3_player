#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"


cd "$SCRIPT_DIR"
mkdir dist
ant jar
ant run-osx_x64
