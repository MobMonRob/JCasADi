#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	setCurrentPlatform "$platformLinux"

	local -r localTarget2="$scriptDir/$currentTarget"

	local -r tmpDir="./$currentTmp/build"
	rm -rdf "$tmpDir"
	mkdir -p "$tmpDir"

	cd "$tmpDir"

	export LD_RUN_PATH="\$ORIGIN"

	export FC=gfortran

	# Zum Testen des Bauens nur (Schneller Build, Langsame Ausführung): -DCMAKE_BUILD_TYPE="Dummy"
	### Dieser ist CMake nicht bekannt und wird von CasADi im Gegensatz zum leeren Type nicht überschrieben. Damit wird kein Optimierungsflag gesetzt beim GCC. Damit nimmt der GCC den Default, welcher -O0 ist.
	### Funktioniert aber nicht für Plugins, wenn die ihre eigenen Optimierungen anders setzen.
	# Release: -DCMAKE_BUILD_TYPE="Release"
	cmake -DCMAKE_BUILD_TYPE="Dummy" "$scriptDir/$casadiDir" -DWITH_EXTRA_CHECKS=OFF -DWITH_SO_VERSION=OFF -DWITH_SELFCONTAINED=ON -DWITH_BUILD_REQUIRED=OFF -DWITH_EXAMPLES=OFF -DCMAKE_INSTALL_PREFIX="$localTarget2" -DWITH_BUILD_SUNDIALS=OFF -DWITH_SUNDIALS=OFF -DWITH_BUILD_CSPARSE=OFF -DWITH_CSPARSE=OFF -DWITH_BUILD_TINYXML=OFF -DWITH_TINYXML=OFF -DWITH_MOCKUP_REQUIRED=OFF -DWITH_IPOPT=ON -DWITH_BUILD_IPOPT=ON -DWITH_MUMPS=ON -DWITH_BUILD_MUMPS=ON -DWITH_METIS=ON -DWITH_BUILD_METIS=ON -DWITH_FATROP=ON -DWITH_BUILD_FATROP=ON -DWITH_LAPACK=ON -DWITH_BUILD_LAPACK=ON -DWITH_BLASFEO=ON -DWITH_BUILD_BLASFEO=ON
	#/home/fabian/Desktop/minimal_cmake_example/

	# DBG (auch für Compiler Flags)
	#-v
	#time nice -n19 ionice -c2 -n7 cmake --build . -j1
	time nice -n19 ionice -c2 -n7 cmake --build . -j"$(nproc)"

	rm -rdf "$localTarget2/casadi"
	mkdir -p "$localTarget2"
	cmake --install .

	cd "$scriptDir"
}

run_bash run $@

