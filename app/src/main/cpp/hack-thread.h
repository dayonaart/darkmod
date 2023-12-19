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

bool unlimitedMoney = false;

long (*oldGetMoney)(void *instance);

bool hookInitialized = false;

long getMoney(void *instance) {
    if (instance != nullptr) {
        if (!hookInitialized) {
            hookInitialized = true;
            LOGD("GameManager initialize hooked");
            return oldGetMoney(instance);
        }
        if (unlimitedMoney) {
            LOGD("MONEY VALUE hooked");
            return 999999999;
        } else {
            return oldGetMoney(instance);
        }
    } else {
        return oldGetMoney(instance);
    }
}

void *hack_thread(void *) {
    LOGD("load target lib .....");
    do {
        sleep(1);
    } while (!utils::isLibraryLoaded(libName));
    LOGD("found the il2cpp lib. Address is: %p", (void *) utils::find_library(libName));
    DobbyHook((void *) utils::get_absolute_address(0x360BC4), (void *) getMoney,
              (void **) &oldGetMoney);
    return nullptr;
}

#endif //DAYDARK_HACK_THREAD_H
