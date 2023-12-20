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
#ifndef DAYDARK_HACK_THREAD_H
#define DAYDARK_HACK_THREAD_H

#include "Includes/Utils.h"
#include <dobby.h>

bool hookInitialized = false;
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

void *hack_thread(void *) {
    LOGD("load target lib .....");
    do {
        sleep(1);
    } while (!utils::isLibraryLoaded(libName));
#if defined(__aarch64__)
    //ARM64
#else
    LOGD("found the il2cpp lib. Address is: %p", (void *) utils::find_library(libName));
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

#endif

    //Anti-leech
    /*if (!iconValid || !initValid || !settingsValid) {
        //Bad function to make it crash
        sleep(5);
        int *p = 0;
        *p = 0;
    }*/

    return nullptr;
}


#endif //DAYDARK_HACK_THREAD_H
