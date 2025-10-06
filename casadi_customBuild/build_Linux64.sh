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

	# build_flags from https://github.com/casadi/casadi/blob/3.7.0/.github/workflows/binaries.yml#L7
	## Changes: -DWITH_MATLAB_IPC=OFF -DWITH_PYTHON_GIL_RELEASE=OFF
	# Zum Testen des Bauens nur (Schneller Build, Langsame Ausführung): -DCMAKE_BUILD_TYPE="Dummy"
	### Dieser ist CMake nicht bekannt und wird von CasADi im Gegensatz zum leeren Type nicht überschrieben. Damit wird kein Optimierungsflag gesetzt beim GCC. Damit nimmt der GCC den Default, welcher -O0 ist.
	### Funktioniert aber nicht für Plugins, wenn die ihre eigenen Optimierungen anders setzen.
	# Release: -DCMAKE_BUILD_TYPE="Release"
	# -DWITH_BUILD_REQUIRED=OFF
	cmake -DCMAKE_BUILD_TYPE="Release" "$scriptDir/$casadiDir" -DWITH_EXTRA_CHECKS=OFF -DWITH_SO_VERSION=OFF -DWITH_SELFCONTAINED=ON -DWITH_EXAMPLES=OFF -DCMAKE_INSTALL_PREFIX="$localTarget2"  -DWITH_COMMON=OFF -DWITH_BUILD_REQUIRED=ON -DWITH_BUILD_BONMIN=ON -DWITH_BONMIN=ON -DWITH_IPOPT=ON -DWITH_BUILD_IPOPT=ON -DWITH_BUILD_LAPACK=ON -DWITH_LAPACK=ON -DWITH_MUMPS=ON -DWITH_CLP=ON -DWITH_BUILD_CLP=ON -DWITH_CBC=ON -DWITH_BUILD_CBC=ON -DWITH_THREAD=ON -DWITH_QPOASES=ON -DWITH_HPIPM=ON -DWITH_BLASFEO=ON -DWITH_BUILD_HPIPM=ON -DWITH_BUILD_BLASFEO=ON -DWITH_HIGHS=ON -DWITH_BUILD_HIGHS=ON -DWITH_BUILD_SPRAL=ON -DWITH_SPRAL=ON -DWITH_PROXQP=ON -DWITH_OSQP=ON -DWITH_SUPERSCS=ON -DWITH_KNITRO=ON -DWITH_MOCKUP_KNITRO=ON -DWITH_CPLEX=ON -DWITH_MOCKUP_CPLEX=ON -DWITH_GUROBI=ON -DWITH_MOCKUP_GUROBI=ON -DWITH_HSL=ON -DWITH_MOCKUP_HSL=ON -DWITH_WORHP=ON -DWITH_MOCKUP_WORHP=ON -DWITH_SUNDIALS=ON -DWITH_BUILD_SUNDIALS=ON -DWITH_BUILD_CSPARSE=ON -DWITH_BUILD_METIS=ON -DWITH_BUILD_BLASFEO=ON -DWITH_BUILD_SUPERSCS=ON -DWITH_BUILD_OSQP=ON -DWITH_BUILD_EIGEN3=ON -DWITH_BUILD_SIMDE=ON -DWITH_BUILD_PROXQP=ON -DWITH_SNOPT=ON -DWITH_MOCKUP_SNOPT=ON -DWITH_AMPL=ON -DWITH_BLOCKSQP=ON -DWITH_SLEQP=ON -DWITH_SLEQP_BUILD=ON -DWITH_ALPAQA=ON -DWITH_BUILD_ALPAQA=ON -DWITH_DAQP=ON -DWITH_BUILD_DAQP=ON -DWITH_FATROP=ON -DWITH_BUILD_FATROP=ON -DWITH_MATLAB_IPC=OFF -DWITH_MADNLP=ON -DWITH_MOCKUP_MADNLP=ON -DWITH_PYTHON_GIL_RELEASE=OFF -DWITH_THREADSAFE_SYMBOLICS=ON -DWITH_CLARABEL=OFF -DWITH_BUILD_CLARABEL=OFF -DWITH_RUMOCA=OFF -DWITH_BUILD_RUMOCA=OFF -DWITH_LIBZIP=ON -DWITH_BUILD_LIBZIP=ON -DWITH_ZLIB=ON -DWITH_BUILD_ZLIB=ON -DWITH_GHC_FILESYSTEM=ON -DWITH_BUILD_GHC_FILESYSTEM=ON
	# -DWITH_BUILD_SUNDIALS=OFF -DWITH_SUNDIALS=OFF -DWITH_BUILD_CSPARSE=OFF -DWITH_CSPARSE=OFF -DWITH_BUILD_TINYXML=OFF -DWITH_TINYXML=OFF -DWITH_MOCKUP_REQUIRED=OFF -DWITH_IPOPT=ON -DWITH_BUILD_IPOPT=ON -DWITH_MUMPS=ON -DWITH_BUILD_MUMPS=ON -DWITH_METIS=ON -DWITH_BUILD_METIS=ON -DWITH_FATROP=ON -DWITH_BUILD_FATROP=ON -DWITH_LAPACK=ON -DWITH_BUILD_LAPACK=ON -DWITH_BLASFEO=ON -DWITH_BUILD_BLASFEO=ON
	#/home/fabian/Desktop/minimal_cmake_example/

	# DBG (auch für Compiler Flags)
	#-v
	#time nice -n19 ionice -c2 -n7 cmake --build . -j1
	time nice -n19 ionice -c2 -n7 cmake --build . -j"$(nproc)"

	rm -rdf "$localTarget2/casadi"
	mkdir -p "$localTarget2"
	cmake --install .

	cd "$scriptDir"

	# Replace symlinks with files
	find "$localTarget2/casadi" -maxdepth 1 -type l -exec sh -c 'for i in "$@"; do cp --preserve --remove-destination "$(readlink -f "$i")" "$i"; done' sh {} +
	# Remvoe files which are not .so
	find "$localTarget2/casadi" -maxdepth 1 -type f ! -name '*.so' -delete
}

run_bash run $@

