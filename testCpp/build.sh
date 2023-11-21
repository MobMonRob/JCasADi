#!/bin/bash

invokeDir=$(pwd)

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

rm -rdf ./build
cmake -B./build -S.
cd ./build
make

cd "$invokeDir"
