# JCasADi
## Description
Java Wrapper for [CasADi](https://web.casadi.org/).

## Caveats
CasADi is not entirely thread-safe. This property is inherited by this Java wrapper. Avoid using multi-threading with CasADi symbolics.

Due to threading issues, no longer needed C++ objects will not be deleted automatically. To do that manually but comfortably, regularly call `MANUAL_CLEANER.cleanupUnreachable()` (from the generated `core__.java` file) within the same thread as you create and use the generated CasADi proxy classes.


## Tested prerequisites
* Kubuntu 20.04 x64 LTS
* git 2.25.1: `sudo apt install git`
* g++ 9.4.0: `sudo apt install build-essential`
* // x86_64-w64-mingw32-g++ 9.4.0: `sudo apt install mingw-w64 mingw-w64-tools`
* CMake 3.16.3 `sudo apt install cmake`
* SWIG 4.0.1 (3.x does not work!) `sudo apt install swig`
* openjdk 17 LTS: `sudo apt install openjdk-17-jdk openjdk-17-demo openjdk-17-doc openjdk-17-jre-headless openjdk-17-source`
* Netbeans 19 `sudo apt install snap`, `sudo snap install netbeans --classic`


## Prepare build
Clone this repository.

Clone and build the Netbeans Maven project [JNativeLibLoader](https://github.com/MobMonRob/JNativeLibLoader). You will need to rebuild it if it's codebase has changed and if you want to use the changes.


## How to build
Open and build the Netbeans Maven project `./JCasADi`.

The deployable jar should now be in the following path: `./JCasADi/target/JCasADi-1.0-SNAPSHOT-jar-with-dependencies.jar`.


## Rebuild
If the wrapper configuration was changed, after `git pull` execute Netbeans clean&build instead of build or run to be able to use the newest wrapper in Java.

