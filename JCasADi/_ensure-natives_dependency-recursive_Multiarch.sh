#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

perPlatform() {
	if [[ "$(isSuccessTokenSet)" == "false" ]]; then
		./_regenerate-natives_dependency-recursive_Multiarch.sh
	fi
}

run() {
	changePlatformTo "$platformLinux"
	perPlatform

	#changePlatformTo "$platformWindows"
	#perPlatform
}

run_bash run $@

