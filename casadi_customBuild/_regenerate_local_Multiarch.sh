#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	clearCurrentPlatform

	bash "./download_Noarch.sh"
	if [[ "$currentPlatform" == "$platformWindows" ]]; then
		# https://stackoverflow.com/questions/42386491/git-apply-skips-patches/62967602#62967602
		# --directory is relative to the directory containing the .git of the current bash working directory.
		git apply ./windows_casadi.patch --directory="casadi_customBuild/$casadiDir" --unsafe-paths -v
	fi

	bash "./build_$currentPlatform.sh"

	if [[ "$currentPlatform" == "$platformWindows" ]]; then
		./copy-mingW-deps_Windows64.sh
	fi

	setSuccessToken
}

run_bash run $@

