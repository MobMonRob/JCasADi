#!/bin/bash

invokeDir=$(pwd)

scriptPath="$(realpath -s "${BASH_SOURCE[0]}")"
scriptDir="$(dirname "$scriptPath")"
cd "$scriptDir"

# --- Konfiguration ---
# Name der Quelldatei
SOURCE_FILE="test.cpp"
# Name des resultierenden Programms
TARGET_EXEC="test"

# Relativer Pfad zum Library-Verzeichnis (wo die .a oder .so liegt)
LIB_PATH="../casadi_prebuilt/target/Linux64/casadi"
# Name der Library (ohne 'lib' Präfix und Endung, z.B. 'cool' für libcool.so)
LIB_NAME="casadi"
# Pfad zu den Header-Dateien
INCLUDE_PATH="../casadi_prebuilt/target/Linux64/casadi/include"

# Verzeichnis für Build-Artefakte
BUILD_DIR="./build"

# --- Build Prozess ---

# 1. Build-Ordner erstellen, falls er nicht existiert
rm -rdf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

LIB_SONAME="libcasadi.so.3.7"
ln -sf "../$LIB_PATH/lib$LIB_NAME.so" "$BUILD_DIR/$LIB_SONAME"

echo "Starte Kompilierung von $SOURCE_FILE..."

# 2. Kompilieren und Linken
# -I : Include Pfad
# -L : Library Pfad
# -l : Library Name
# -o : Output Pfad
g++ -D_GLIBCXX_USE_CXX11_ABI=0 "$SOURCE_FILE" \
    -I"$INCLUDE_PATH" \
    -L"$LIB_PATH" \
    -l"$LIB_NAME" \
    -Wl,-rpath,'$ORIGIN' \
    -o "$BUILD_DIR/$TARGET_EXEC"

# Prüfen, ob der Build erfolgreich war
if [ $? -eq 0 ]; then
    echo "--------------------------------------"
    echo "Build erfolgreich!"
    echo "Programm abgelegt unter: $BUILD_DIR/$TARGET_EXEC"
else
    echo "--------------------------------------"
    echo "Fehler während des Build-Vorgangs."
    exit 1
fi

cd ./build


cd "$invokeDir"
