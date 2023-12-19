//
// Created by dayona on 12/18/23.
//

#ifndef ANDROICPP_HACK_THREAD_H
#define ANDROICPP_HACK_THREAD_H

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

#endif //ANDROICPP_HACK_THREAD_H
