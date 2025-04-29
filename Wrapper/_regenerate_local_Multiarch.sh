#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform

	# Would be faster if shared between platforms.
	export previousPlatform="$currentPlatform"
	#setCurrentPlatform "$platformNoarch"
	#if [[ "$(isSuccessTokenSet)" == "false" ]]; then
	#	clearCurrentPlatform
		./generate-wrapper_Multiarch.sh
	#	setSuccessToken
	#fi
	#setCurrentPlatform "$previousPlatform"

	bash "./generate-dynlib_$currentPlatform.sh"

	./copy-deps_Multiarch.sh

	setSuccessToken
}


run_bash run $@

