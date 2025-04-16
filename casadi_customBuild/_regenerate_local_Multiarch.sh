#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform

	bash "./build_$currentPlatform.sh"

	if [[ "$currentPlatform" == "$platformWindows" ]]; then
		./copy-mingW-deps_Windows64.sh
	fi

	setSuccessToken
}

run_bash run $@

