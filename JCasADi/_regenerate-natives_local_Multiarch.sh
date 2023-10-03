#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform
	rm -rdf "$currentNativesPath"
	mkdir -p "$currentNativesPath"

	./copy-deps_Multiarch.sh

	setSuccessToken
}

run_bash run $@

