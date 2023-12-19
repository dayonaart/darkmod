# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("daydark")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#
# In this top level CMakeLists.txt, ${CMAKE_PROJECT_NAME} is used to define
# the target library name; in the sub-module's CMakeLists.txt, ${PROJECT_NAME}
# is preferred for the same purpose.
#
# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
# for GameActivity/NativeActivity derived applications, the same library name must be
# used in the AndroidManifest.xml file.

enable_language(CXX)
enable_language(ASM)

set(SOURCE_PATH ${CMAKE_CURRENT_SOURCE_DIR})

add_library(${PROJECT_NAME} SHARED)

target_include_directories(${PROJECT_NAME}
        PRIVATE
        ${SOURCE_PATH})

target_sources(${PROJECT_NAME}
        PRIVATE
        main.cpp
        Patching/Patch.cpp)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/Dobby/include)

set_target_properties(${PROJECT_NAME}
        PROPERTIES
        CXX_STANDARD 17
        CXX_STANDARD_REQUIRED YES
        CXX_EXTENSIONS YES)

target_compile_features(${PROJECT_NAME}
        INTERFACE
        cxx_std_17)

message(STATUS "CMAKE_BUILD_TYPE is ${CMAKE_BUILD_TYPE}")
if (CMAKE_BUILD_TYPE STREQUAL "Debug")
    set(BUILD_TYPE_COMPILER_FLAGS
            -g3)
    set(BUILD_TYPE_LINKER_FLAGS)
else ()
    set(BUILD_TYPE_COMPILER_FLAGS
            -fvisibility=hidden
            -fvisibility-inlines-hidden
            -fomit-frame-pointer
            -ffunction-sections
            -fdata-sections
            -g0
            -Ofast
            -DNDEBUG)
    set(BUILD_TYPE_LINKER_FLAGS
            -Wl
            --strip-all)
endif ()
target_compile_options(${PROJECT_NAME}
        INTERFACE
        -Wno-error=format-security
        -fpermissive
        -fno-rtti
        -fno-exceptions
        ${BUILD_TYPE_COMPILER_FLAGS})

set_property(TARGET ${PROJECT_NAME}
        APPEND
        PROPERTY LINK_FLAGS
        ${BUILD_TYPE_LINKER_FLAGS})
# Requires cmake >= 3.13
#target_link_options(${PROJECT_NAME}
#    INTERFACE
#
#    ${BUILD_TYPE_LINKER_FLAGS}
#)

find_library(log-lib
        log)

set(DOBBY_LIB ${CMAKE_CURRENT_SOURCE_DIR}/Dobby/${CMAKE_ANDROID_ARCH_ABI}/libdobby.a)

target_link_libraries(${PROJECT_NAME}
        ${log-lib}
        ${DOBBY_LIB})