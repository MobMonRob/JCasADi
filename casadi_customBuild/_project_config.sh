#!/bin/bash


readonly projectDir="$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
readonly casadiDir="$noarchTmp/casadi"
readonly mingwLibPath1="/usr/x86_64-w64-mingw32/lib"

local -r minwLibPath2Beginning="/usr/lib/gcc/x86_64-w64-mingw32"
local -r minwLibPath2FoundArray=($(find "$minwLibPath2Beginning"/*-posix -maxdepth 0 -mindepth 0 -type d -printf '%f\n'))
readonly mingwLibPath2="$minwLibPath2Beginning/${minwLibPath2FoundArray[0]}"
