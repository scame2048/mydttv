
cmake_minimum_required(VERSION 3.4.1)

# include dir
set(INCLUDE_DIR ${CMAKE_SOURCE_DIR}/src/main/jni/include)

# shared lib import
add_library(lib_dtp SHARED IMPORTED)
set_target_properties(lib_dtp
                      PROPERTIES IMPORTED_LOCATION
                      ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libdtp.so)

# build application's shared lib
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

# jni source code
aux_source_directory( ${CMAKE_SOURCE_DIR}/src/main/jni        JNISOURCES)
aux_source_directory( ${CMAKE_SOURCE_DIR}/src/main/jni/plugin PLUGINSOURCES)

set (SOURCE ${JNISOURCES}  ${PLUGINSOURCES})

add_library(dttv_jni SHARED  ${SOURCE})
target_include_directories(dttv_jni PRIVATE
                           ${CMAKE_SOURCE_DIR}/src/main/jni
                           ${INCLUDE_DIR}/libdtp)

target_link_libraries(dttv_jni
                      android
                      lib_dtp
                      GLESv2
                      log)
