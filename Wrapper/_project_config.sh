#!/bin/bash

readonly projectDir="$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"
readonly wrapLibName="casadi"
readonly wrapLibDir="$projectDir/_dependencies/casadi_customBuild"

readonly javaIncludeLinux="$projectDir/jdk-include/java-11-openjdk-amd64/include"
readonly javaIncludeWindows="$projectDir/jdk-include/jdk-11.0.11+9/include"

