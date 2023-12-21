
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

package gg.day.dark;

import android.content.Context;

public class Start {

    private static native void checkOverlayPermission(Context context);

    public static native void changeState(String modName, int modIndex, boolean switchState, String txtState);

    public static native String base64Icon();

    public static native String[] getListMenu();

    public static native void createThread(Context context);

    public static native void saveSetting(String path);

    private static native String readSetting(String path);


    static {
        System.loadLibrary("daydark");
    }

    public static void Mod(Context context) {
        checkOverlayPermission(context);
    }

    public static String[] readSettingList(Context context) {
        if (readSetting(context.getApplicationInfo().dataDir).split("_").length == 1) {
            return null;
        }
        return readSetting(context.getApplicationInfo().dataDir).split("_");
    }
}
