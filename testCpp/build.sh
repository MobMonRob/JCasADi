#!/bin/bash

rm -rdf ./build
cmake -B./build -S.
cd ./build
make
