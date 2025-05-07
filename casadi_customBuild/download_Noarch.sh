#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	rm -rdf "$casadiDir"
	mkdir -p "$casadiDir"

	git clone https://github.com/casadi/casadi.git --branch="3.7.0" --depth=1 --recursive "$casadiDir"

	# https://stackoverflow.com/questions/42386491/git-apply-skips-patches/62967602#62967602
	# --directory is relative to the directory containing the .git of the current bash working directory.
	git apply ./casadi.patch --directory="casadi_customBuild/$casadiDir" --unsafe-paths -v
}

run_bash run $@
