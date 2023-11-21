#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	local -r swigJavaOutDir="$currentTarget/java/de/dhbw/rahmlab/"$wrapLibName"/impl"

	local -r wrapLibTarget="$wrapLibDir" #/$linuxTarget"
	local -r wrapLibInclude="$wrapLibTarget/include" #/casadi/core"

	mkdir -p "$swigJavaOutDir"
	mkdir -p "$currentTmp"

	local -r swigModulesDirectory="$projectDir/SWIG_Modules"
	local -r swigLibDirectory="$projectDir/SWIG_Lib"

	local -r SwigModulesArray=($(find "$swigModulesDirectory"/* -maxdepth 0 -mindepth 0 -type f -printf '%f\n'))

	local -r moduleOfInterest="core.i"
	# local -r moduleOfInterest="casadi.i"

	local -r filterModule="true"

	for swigModule in ${SwigModulesArray[@]}
	do
		if [[ $filterModule == "true" ]]; then
			if [[ $swigModule != $moduleOfInterest ]]; then
				continue
			fi
		fi

		echo "->$swigModule"

		#-debug-tmused -debug-tmsearch -debug-typemap
		#-debug-tags
		#-doxygen
		#-ignoremissing -importall
		#-cpperraswarn
		#-O
		swig -doxygen -Wextra -DSWIGWORDSIZE64 -DSWIG_TYPE_TABLE=casadi -c++ -java -package "de.dhbw.rahmlab."$wrapLibName".impl" -outdir "$swigJavaOutDir" -o "$currentTmp/"$swigModule"_wrap.cpp" -I"$wrapLibInclude" -I"$swigLibDirectory" "$swigModulesDirectory/$swigModule"
	done
}

run_bash run $@

