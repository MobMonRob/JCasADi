#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform

	if [[ "$currentPlatform" != "$platformLinux" ]]; then
		exit 1
	fi

	./generate-wrapper_Multiarch.sh

	bash "./generate-dynlib_$currentPlatform.sh"

	./copy-deps_Multiarch.sh

	if [[ "$currentPlatform" == "$platformWindows" ]]; then
		./copy-mingW-deps_Windows64.sh
	fi

	setSuccessToken
}


run_bash run $@

