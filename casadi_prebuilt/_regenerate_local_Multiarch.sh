#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform

	bash "./build_$currentPlatform.sh"

	setSuccessToken
}

run_bash run $@

