#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	setCurrentPlatform "$platformWindows"

	local -r localTarget="$scriptDir/$currentTarget"

	local -r tmpDir="./$currentTmp/build"
	rm -rdf "$tmpDir"
	mkdir -p "$tmpDir"

	cd "$tmpDir"

	export LD_RUN_PATH="\$ORIGIN"

	# Zum Testen des Bauens nur (Schneller Build, Langsame Ausführung) (5 min): -DCMAKE_CXX_FLAGS:STRING="-O0" -DCMAKE_BUILD_TYPE="Debug"
	# Für Release: (Ohne)
	cmake -DCMAKE_CXX_FLAGS:STRING="-O0" -DCMAKE_BUILD_TYPE="Debug" -DCMAKE_INSTALL_PREFIX="$localTarget" -DWITH_SELFCONTAINED=ON -DWITH_BUILD_SUNDIALS=OFF -DWITH_SUNDIALS=OFF -DWITH_BUILD_CSPARSE=OFF -DWITH_CSPARSE=OFF -DWITH_BUILD_TINYXML=OFF -DWITH_TINYXML=OFF -DWITH_MOCKUP_REQUIRED=OFF -DWITH_EXAMPLES=OFF "$scriptDir/$casadiDir" -DCMAKE_TOOLCHAIN_FILE="$scriptDir/mingw.cmake"
	#/home/fabian/Desktop/minimal_cmake_example/

	time make --jobs="$((2*$(nproc)))"

	rm -rdf "$localTarget/casadi"
	mkdir -p "$localTarget"
	make install

	cd "$scriptDir"

	# Windows only
	rm -f "$windowsTarget/casadi"/*.dll.a
}

run_bash run $@

