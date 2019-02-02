#ifndef DTTV_JNI_LOG_H
#define DTTV_JNI_LOG_H

#include <android/log.h>

#define LOGGER_V(tag, format, ...) \
    do { \
        __android_log_print(ANDROID_LOG_VERBOSE, tag, format, ##__VA_ARGS__); \
    } while(0)

#define LOGGER_D(tag, format, ...) \
    do { \
        __android_log_print(ANDROID_LOG_DEBUG, tag, format, ##__VA_ARGS__); \
    } while( 0 )

#define LOGGER_W(tag, format, ...) \
    do { \
        __android_log_print(ANDROID_LOG_WARN, tag, format, ##__VA_ARGS__); \
    } while( 0 )

#define LOGGER_I(tag, format, ...) \
    do { \
        __android_log_print(ANDROID_LOG_INFO, tag, format, ##__VA_ARGS__); \
    } while(0)

#define LOGGER_E(tag, format, ...) \
    do { \
        __android_log_print(ANDROID_LOG_ERROR, tag, format, ##__VA_ARGS__); \
    } while(0)

#endif