#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	rm -rdf "$casadiDir"
	mkdir -p "$casadiDir"

	git clone https://github.com/casadi/casadi.git --branch="3.7.0" --depth=1 --recursive "$casadiDir"
}

run_bash run $@
