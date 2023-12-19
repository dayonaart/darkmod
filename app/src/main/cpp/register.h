//
// Created by dayona on 12/8/23.
//

#ifndef ANDROICPP_REGISTER_H
#define ANDROICPP_REGISTER_H

#include "unistd.h"
#include "sstream"
#include "constant.h"

using namespace std;

void startActivityPermission(JNIEnv *env, jobject ctx);

bool checkOverlayPermission(JNIEnv *env, jobject ctx) {
    int sdkVer = android_get_device_api_level();
    if (sdkVer >= 23) {
        jclass Settings = env->FindClass("android/provider/Settings");
        jmethodID canDraw = env->GetStaticMethodID(Settings, "canDrawOverlays",
                                                   "(Landroid/content/Context;)Z");
        if (!env->CallStaticBooleanMethod(Settings, canDraw, ctx)) {
            startActivityPermission(env, ctx);
            return false;
        }
        return true;
    }
    return true;
}

void startActivityPermission(JNIEnv *env, jobject ctx) {
    jclass native_context = env->GetObjectClass(ctx);
    jmethodID startActivity = env->GetMethodID(native_context, "startActivity",
                                               "(Landroid/content/Intent;)V");

    jmethodID pack = env->GetMethodID(native_context, "getPackageName",
                                      "()Ljava/lang/String;");
    jstring packageName = static_cast<jstring>(env->CallObjectMethod(ctx, pack));

    const char *pkg = env->GetStringUTFChars(packageName, 0);

    stringstream pkgg;
    pkgg << "package:";
    pkgg << pkg;
    string pakg = pkgg.str();

    jclass Uri = env->FindClass("android/net/Uri");
    jmethodID parse = env->GetStaticMethodID(Uri, "parse",
                                             "(Ljava/lang/String;)Landroid/net/Uri;");
    jobject UriMethod = env->CallStaticObjectMethod(Uri, parse, env->NewStringUTF(pakg.c_str()));
    jclass intentClass = env->FindClass("android/content/Intent");
    jmethodID newIntent = env->GetMethodID(intentClass, "<init>",
                                           "(Ljava/lang/String;Landroid/net/Uri;)V");
    jobject intent = env->NewObject(intentClass, newIntent, env->NewStringUTF(
            "android.settings.action.MANAGE_OVERLAY_PERMISSION"), UriMethod);
    env->CallVoidMethod(ctx, startActivity, intent);
}

jobjectArray getFeatureList(JNIEnv *env, jclass thiz) {
    jobjectArray ret;
    const char *features[] = {"Switch_Unlimited Money"};
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass("java/lang/String"),
                                env->NewStringUTF(""));
    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));
    return (ret);
}


#endif //ANDROICPP_REGISTER_H
