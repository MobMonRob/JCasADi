# JCasADi
## Description
Java Wrapper for [CasADi](https://web.casadi.org/) 3.7.0.


## Caveats
CasADi is not entirely thread-safe. This property is inherited by this Java wrapper. Avoid using multi-threading with CasADi symbolics.

Due to the aforementioned threading issues, no longer needed C++ objects will not be deleted automatically. To do that manually but comfortably, regularly call `MANUAL_CLEANER.cleanupUnreachable()` (from the [`WrapUtil`](JCasADi/src/main/java/de/dhbw/rahmlab/casadi/implUtil/WrapUtil.java) file) within the same thread as you create and use objects of the generated CasADi SWIG-proxy-classes.


## Tested prerequisites
* Kubuntu 24.04 x64 LTS
* git 2.43.0: `sudo apt install git`
* g++, gcc 13.3.0: `sudo apt install build-essential`
* gfortran 13.3.0: `sudo apt install gfortran`
* x86_64-w64-mingw32-g++, x86_64-w64-mingw32-gcc 13.2.0: `sudo apt install mingw-w64 mingw-w64-tools`
* x86_64-w64-mingw32-gfortran-posix 13.2.0 `sudo apt install gfortran-mingw-w64-x86-64-posix`
* CMake 3.28.3 `sudo apt install cmake`
* SWIG 4.2.0 (3.x does not work!) `sudo apt install swig`
* openjdk 21 LTS: `sudo apt install openjdk-21-jdk openjdk-21-demo openjdk-21-doc openjdk-21-jre-headless openjdk-21-source`
* Netbeans 25 `sudo apt install snap`, `sudo snap install netbeans --classic`


## Prepare build
Clone this repository.

Clone and build the Netbeans Maven project [JNativeLibLoader](https://github.com/MobMonRob/JNativeLibLoader). You will need to rebuild it if it's codebase has changed and if you want to use the changes.


## How to build
Open and build the Netbeans Maven project `./JCasADi`.

The deployable jar should now be in the following path: `./JCasADi/target/JCasADi-1.0-SNAPSHOT-jar-with-dependencies.jar`.


## Rebuild
If the wrapper configuration was changed, after `git pull` execute Netbeans clean&build instead of build or run to be able to use the newest wrapper in Java.

