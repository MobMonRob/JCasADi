#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	bash "$urclDir/_regenerate_dependency-recursive_Multiarch.sh"

	./_regenerate_local_Multiarch.sh
}

run_bash run $@

