#include <jni.h>
#include <string>
#include "Includes/Logger.h"
#include "register.h"
#include "hack-thread.h"
#include "Includes/Utils.h"

using namespace std;

extern "C"
JNIEXPORT int JNICALL
Java_gg_day_dark_Start_checkOverlayPermission(JNIEnv *env, jclass thiz,
                                              jobject context) {
    return checkOverlayPermission(env, context);
}

void init(JNIEnv *env, jclass obj, jobject thiz) {
    pthread_t ptid;
    pthread_create(&ptid, NULL, hack_thread, NULL);
    utils::make_toast(env, thiz, "Mod by Dayona");
}

void switchState(JNIEnv *env, jclass thiz, jstring modName,
                 jint modIndex, jboolean state) {
    const char *nativeString = env->GetStringUTFChars(modName, nullptr);
    LOGD("INDEX : %i | VALUE : %hhu | MOD NAME %s:", modIndex, state, nativeString);
    switch (modIndex) {
        case 0:
            unlimitedMoney = state;
            break;
        case 1:
            break;
        default:
            break;
    }
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
            {"switchState", "(Ljava/lang/String;IZ)V",     reinterpret_cast<void *>(switchState)},
            {"base64Icon",  "()Ljava/lang/String;",        reinterpret_cast<void *>(icon)},
            {"getListMenu",
                            "()[Ljava/lang/String;",       reinterpret_cast<void *>(getFeatureList)},
            {"init",        "(Lgg/day/dark/ModService;)V", reinterpret_cast<void *>(init)},
    };
    int rc = env->RegisterNatives(c, methods, sizeof(methods) / sizeof(JNINativeMethod));
    if (rc != JNI_OK) return rc;
    return JNI_VERSION_1_6;
}