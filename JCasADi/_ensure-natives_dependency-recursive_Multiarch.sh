#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

perPlatform() {
	if [[ "$(isSuccessTokenSet)" == "false" ]]; then
		bash "$wrapperDir/_ensure_dependency-recursive_Multiarch.sh"

		./_regenerate-natives_local_Multiarch.sh
	fi
}

run() {
	changePlatformTo "$platformLinux"
	perPlatform

	#changePlatformTo "$platformWindows"
	#perPlatform
}

run_bash run $@

