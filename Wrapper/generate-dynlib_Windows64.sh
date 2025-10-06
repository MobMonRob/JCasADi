#!/bin/bash

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

source "./_bash_config.sh"

run() {
	mkdir -p "$windowsTarget"

	local -r wrapLibTarget="$wrapLibDir/$windowsTarget"
	local -r wrapLibInclude="$wrapLibTarget/casadi/include" #/casadi/core"
	local -r linkLibDir="$wrapLibTarget/casadi"

	local -r SwigCppArray=($(find "$windowsTmp"/*.cpp -maxdepth 0 -mindepth 0 -type f -printf '%f\n'))

	for swigCpp in ${SwigCppArray[@]}
	do
		echo "->$swigCpp"

	#-c für nicht linken (nur .o erzeugen)
	#-shared .so muss tun, damit sicher der Fehler nicht hier liegt.
	#-O3
	x86_64-w64-mingw32-g++-posix -D_GLIBCXX_USE_CXX11_ABI=1 -cpp -std=c++17 -static-libstdc++ -c -fPIC "$windowsTmp/$swigCpp" \
	-I"$javaIncludeWindows/win32" -I"$javaIncludeWindows" -I"$wrapLibInclude" \
	-o "$windowsTmp/$swigCpp.o"
	done

	local -r oArray=(${SwigCppArray[@]/%/.o})
	local -r pathArray=(${oArray[@]/#/$windowsTmp/})

	x86_64-w64-mingw32-g++-posix -D_GLIBCXX_USE_CXX11_ABI=1 -cpp -std=c++17 -static-libstdc++ -shared -L"$linkLibDir" \
	-Wl,--start-group \
	${pathArray[@]} \
	-lcasadi \
	-Wl,--end-group \
	-Wl,-rpath,'$ORIGIN/.' -o "$windowsTarget/libJ"$wrapLibName".dll" \
	-Wl,--as-needed -Wl,--no-undefined -Wl,--no-allow-shlib-undefined
}

run_bash run $@

