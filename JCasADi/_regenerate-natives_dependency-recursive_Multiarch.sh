#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

perPlatform() {
	bash "$wrapperDir/_regenerate_dependency-recursive_Multiarch.sh"

	./_regenerate-natives_local_Multiarch.sh
}

run() {
	changePlatformTo "$platformLinux"
	perPlatform

	#changePlatformTo "$platformWindows"
	#perPlatform
}

run_bash run $@

