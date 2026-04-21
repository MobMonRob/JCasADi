cmake_minimum_required(VERSION 3.12)
project(JCasADi_cmake VERSION 0.1 LANGUAGES CXX)

add_compile_options(-D_GLIBCXX_USE_CXX11_ABI=0)

#SET(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -Wl,-rpath -Wl,${CMAKE_SOURCE_DIR}/build")

# for using casadi
#find_package(casadi REQUIRED)
# For multi threading
find_package(Threads REQUIRED)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE "Release")
endif()

# set the g++ compiler flags
set(CMAKE_CXX_FLAGS "-Wall -Wextra -Wno-unused-parameter -pthread")
set(CMAKE_CXX_FLAGS_DEBUG "-g")
set(CMAKE_CXX_FLAGS_RELEASE "-O3")
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(CMAKE_CXX_COMPILER_LOADED)
    message(STATUS "Compiler Path: ${CMAKE_CXX_COMPILER}")
    message(STATUS "Compiler ID: ${CMAKE_CXX_COMPILER_ID}")
    message(STATUS "Compiler Version: ${CMAKE_CXX_COMPILER_VERSION}")
endif()

set(CASADI_DIR ${CMAKE_SOURCE_DIR}/../casadi_prebuilt/target/Linux64/casadi)

message(${CASADI_DIR})

# set casadi include directory
set(CASADI_INCLUDE_DIR ${CASADI_DIR}/include)

# find casadi library
#find_library(CASADI_LIBRARY
#    NAMES casadi
#    HINTS ${CASADI_DIR})
#if(CASADI_LIBRARY)
#    set(CASADI_LIBRARIES ${CASADI_LIBRARIES} ${CASADI_LIBRARY})
#endif()

#message(${CASADI_LIBRARY})

add_library(CASADI_LIB SHARED IMPORTED)
set_target_properties(CASADI_LIB PROPERTIES
    IMPORTED_LOCATION "${CASADI_DIR}/libcasadi.so"
    IMPORTED_NO_SONAME TRUE
)

# find the following packages
find_package(Threads REQUIRED)

# include header directories
include_directories(
    ${CMAKE_SOURCE_DIR}/include
    ${CASADI_INCLUDE_DIR}
    ${CODE_GEN_INCLUDE_DIR})

# library directories
#link_directories(${CASADI_DIRS})

# find all the header files
file(GLOB HEADER_FILES_HPP ${CMAKE_SOURCE_DIR}/include/*.hpp)
file(GLOB HEADER_FILES_H ${CMAKE_SOURCE_DIR}/include/*.h)

# add executable files for test

add_executable(test ./test.cpp)
target_link_libraries(test CASADI_LIB)

