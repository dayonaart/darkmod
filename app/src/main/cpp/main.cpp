/*
 * Copyright (c) 2023
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created By Dayona
 */

#include <jni.h>
#include <string>
#include "Includes/Logger.h"
#include "register.h"
#include "hack-thread.h"
#include "Includes/Utils.h"
#include "utilities.h"


void createThread(JNIEnv *env, jclass clazz, jobject context) {
    pthread_t ptid;
    pthread_create(&ptid, nullptr, hack_thread, nullptr);
}


extern "C"
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jclass c = env->FindClass("gg/day/dark/Start");
    if (c == nullptr) return JNI_ERR;
    static const JNINativeMethod methods[] = {
            {"checkOverlayPermission", "(Landroid/content/Context;)V",
                                                                reinterpret_cast<void *>(checkOverlayPermission)},
            {"changeState",            "(Ljava/lang/String;IZLjava/lang/String;)V",
                                                                reinterpret_cast<void *>(changeStateStationManager)},
            {"base64Icon",             "()Ljava/lang/String;",  reinterpret_cast<void *>(icon)},
            {"getListMenu",
                                       "()[Ljava/lang/String;", reinterpret_cast<void *>(getFeatureListStationManager)},
            {"createThread",           "(Landroid/content/Context;)V",
                                                                reinterpret_cast<void *>(createThread)},
    };
    int rc = env->RegisterNatives(c, methods, sizeof(methods) / sizeof(JNINativeMethod));
    if (rc != JNI_OK) return rc;
    return JNI_VERSION_1_6;
}


