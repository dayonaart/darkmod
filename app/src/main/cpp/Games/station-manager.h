//
// Created by dayona on 12/21/23.
//

#ifndef DAYDARK_STATION_MANAGER_H
#define DAYDARK_STATION_MANAGER_H

#include "Includes/Utils.h"
#include "dobby.h"

bool _unlimitedMoney = false;
bool _unlimitedCoin = false;
bool _unlimitedPoint = false;
bool _isCheatApk = false;
bool _isLandScapeMode = false;


long long (*oldGetMoney)(void *instance);

long long getMoney(void *instance) {
    if (instance != nullptr) {
        if (_unlimitedMoney) {
            return 9999999999999999;
        } else {
            return oldGetMoney(instance);
        }
    } else {
        return oldGetMoney(instance);
    }
}

int (*oldGetCoin)(void *instance, int add);

int getCoin(void *instance, int add) {
    if (instance != nullptr) {
        if (_unlimitedCoin) {
            return oldGetCoin(instance, 999999);
        } else {
            return oldGetCoin(instance, add);
        }
    } else {
        return oldGetCoin(instance, add);
    }
}

long long (*oldPoint)(void *instance);

long long getPoint(void *instance) {
    if (instance != nullptr) {
        if (_unlimitedPoint) {
            return 999999999999999;
        } else {
            return oldPoint(instance);
        }
    } else {
        return oldPoint(instance);
    }
}

bool (*oldIsCheatApk)(void *instance);

bool isCheatApk(void *instance) {
    if (instance != nullptr) {
        return _isCheatApk;
    }
    return oldIsCheatApk(instance);
}

bool (*oldIsLandScapeMode)(void *instance);

bool isLandScapeMode(void *instance) {
    if (instance != nullptr) {
        return _isLandScapeMode;
    }
    return oldIsLandScapeMode(instance);
}

bool (*oldIsAdEnable)(void *instance);

bool isAdEnable(void *instance) {
    if (instance != nullptr) {
        return false;
    }
    return oldIsAdEnable(instance);
}

void startHookStationManager() {
    //GET MONEY
    DobbyHook((void *) utils::get_absolute_address(0x360BC4), (void *) getMoney,
              (void **) &oldGetMoney);
    //GET COIN
    DobbyHook((void *) utils::get_absolute_address(0x36ADE8), (void *) getCoin,
              (void **) &oldGetCoin);
    //ADD POINT
    DobbyHook((void *) utils::get_absolute_address(0x341E64), (void *) getPoint,
              (void **) &oldPoint);
    //IS CHEAT APK
    DobbyHook((void *) utils::get_absolute_address(0x3654B8), (void *) isCheatApk,
              (void **) &oldIsCheatApk);
    //IS LANDSCAPE MODE
    DobbyHook((void *) utils::get_absolute_address(0x36DD64), (void *) isLandScapeMode,
              (void **) &oldIsLandScapeMode);

    //DISABLE ADS
    DobbyHook((void *) utils::get_absolute_address(0x379DBC), (void *) isAdEnable,
              (void **) &oldIsAdEnable);


}

void changeStateStationManager(JNIEnv *env, jclass thiz, jstring modName,
                               jint modIndex, jboolean switchState, jstring txtState) {
    const char *modNameChar = env->GetStringUTFChars(modName, nullptr);

    LOGD("INDEX : %i | SWITCH VALUE : %hhu |  MOD NAME %s:", modIndex, switchState, modNameChar);
    switch (modIndex) {
        case 0:
            _unlimitedMoney = switchState;
            break;
        case 1:
            _unlimitedCoin = switchState;
            break;
        case 2:
            _unlimitedPoint = switchState;
            break;
        case 3:
            _isCheatApk = switchState;
            break;
        case 4:
            _isLandScapeMode = switchState;
            break;
        default:
            break;
    }
}

jobjectArray getFeatureListStationManager(JNIEnv *env, jclass thiz) {
    jobjectArray ret;
    const char *features[] = {"Switch_Unlimited Money",
                              "Switch_Unlimited Coin",
                              "Switch_Unlimited Point",
                              "Switch_Use Cheat",
                              "Switch_Landscape Mode"};
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass("java/lang/String"),
                                env->NewStringUTF(""));
    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));
    return (ret);
}

#endif //DAYDARK_STATION_MANAGER_H
