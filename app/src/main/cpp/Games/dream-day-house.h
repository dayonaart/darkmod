//
// Created by dayona on 12/21/23.
//

#ifndef DAYDARK_DREAM_DAY_HOUSE_H
#define DAYDARK_DREAM_DAY_HOUSE_H
bool _unlimitedMoney;
bool _unlimitedCoin;
bool _unlimitedPoint;
bool _isCheatApk;
bool _isLandScapeMode;

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

void startHookStationManager() {
    //GET TICKET
    DobbyHook((void *) utils::get_absolute_address(0x360BC4), (void *) getMoney,
              (void **) &oldGetMoney);

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
    const char *features[] = {"Switch_Unlimited Ticket"};
    int Total_Feature = (sizeof features / sizeof features[0]);
    ret = (jobjectArray)
            env->NewObjectArray(Total_Feature, env->FindClass("java/lang/String"),
                                env->NewStringUTF(""));
    for (int i = 0; i < Total_Feature; i++)
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));
    return (ret);
}

#endif //DAYDARK_DREAM_DAY_HOUSE_H
