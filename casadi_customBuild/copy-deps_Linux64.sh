#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	setCurrentPlatform "$platformLinux"

	# Deactivated. Can lead to linking errors.
	# Executing system needs to have these installed instead.
	#cp -L -u "/lib/x86_64-linux-gnu/libstdc++.so.6" "$currentTarget/casadi"
	#cp -L -u "/lib/x86_64-linux-gnu/libm.so.6" "$currentTarget/casadi"
	#cp -L -u "/lib/x86_64-linux-gnu/libgcc_s.so.1" "$currentTarget/casadi"
	#cp -L -u "/lib/x86_64-linux-gnu/libc.so.6" "$currentTarget/casadi"
	#cp -L -u "/lib/x86_64-linux-gnu/libgfortran.so.5" "$currentTarget/casadi"
}

run_bash run $@

