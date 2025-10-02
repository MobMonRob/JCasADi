#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	setCurrentPlatform "$platformLinux"
	clearCurrentPlatform

	cd "$scriptDir"
	rm -rdf "$currentTmp"
	mkdir -p "$currentTmp"

	cd "$currentTmp"

	wget https://github.com/casadi/casadi/releases/download/3.7.2/casadi-3.7.2-linux64-py313.zip >/dev/null
	unzip casadi-3.7.2-linux64-py313.zip >/dev/null

	cd ./casadi
	find . -maxdepth 1 -type f ! -name '*.so' ! -name 'libquadmath*' ! -name 'libgfortran*' -delete

	# Mockups could be spared with lazy loading.
	find . -maxdepth 1 -type f -name '*ma27*' -delete
	find . -maxdepth 1 -type f -name '*knitro*' -delete
	find . -maxdepth 1 -type f -name '*madnlp*' -delete
	find . -maxdepth 1 -type f -name '*snopt*' -delete
	find . -maxdepth 1 -type f -name '*worhp*' -delete
	find . -maxdepth 1 -type f -name '*matlab*' -delete

	######

	cd "$scriptDir"

	local -r targetCasadiInclude="$currentTarget/casadi/include"
	rm -rdf "$targetCasadiInclude"
	mkdir -p "$targetCasadiInclude"

	cp -L -l "$currentTmp"/casadi/lib* "$currentTarget/casadi"
	cp -L -l -r "$currentTmp"/casadi/include/casadi "$targetCasadiInclude"
}

run_bash run $@

