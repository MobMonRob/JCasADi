#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	local -r wrapLibTargetDir="$wrapLibDir/$currentTarget"
	cp -L -l "$wrapLibTargetDir"/casadi/lib* "$currentTarget"
}

run_bash run $@

