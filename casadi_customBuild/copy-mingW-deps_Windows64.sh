#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	setCurrentPlatform "$platformWindows"

	cp -L -u "$mingwLibPath1/libwinpthread-1.dll" "$currentTarget/casadi"
	cp -L -u "$mingwLibPath2/libgcc_s_seh-1.dll" "$currentTarget/casadi"
	cp -L -u "$mingwLibPath2/libstdc++-6.dll" "$currentTarget/casadi"
	cp -L -u "$mingwLibPath2/libgfortran-5.dll" "$currentTarget/casadi"
	cp -L -u "$mingwLibPath2/libquadmath-0.dll" "$currentTarget/casadi"
}

run_bash run $@

