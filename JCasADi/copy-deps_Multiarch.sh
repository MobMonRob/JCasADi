#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	rm -rdf "$currentNativesPath"
	mkdir -p "$currentNativesPath"

	local -r wrapperTarget="$(realpath "$wrapperDir/$currentTarget")"

	cp -L -l "$wrapperTarget"/lib* "$currentNativesPath"

	local -r localImplPackageDir="./src/main/$implPackageDir"
	rm -rdf "$localImplPackageDir"
	mkdir -p "$localImplPackageDir"

	cp -L -l -R "$wrapperTarget/$implPackageDir/" -T "$localImplPackageDir"
}

run_bash run $@

