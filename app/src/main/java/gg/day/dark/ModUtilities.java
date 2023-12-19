
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.Switch;

public class ModUtilities {

    private final Properties prop = new Properties();
    private final Context context;

    public ModUtilities(Context context) {
        this.context = context;
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public LinearLayout registerSwitcher(String modName, int index, ChangeCallback callback) {
        LinearLayout linearLayout = new LinearLayout(context);
        Switch sw = new Switch(context);
        ColorStateList swState = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        Color.BLUE,
                        Color.GREEN, // ON
                        Color.DKGRAY // OFF
                }
        );
        sw.getThumbDrawable().setTintList(swState);
        sw.getTrackDrawable().setTintList(swState);
        sw.setText(modName);
        sw.setWidth(500);
        sw.setTextSize(prop.TEXT_MODS_SIZE);
        sw.setTextColor(prop.TEXT_COLOR_2);
        sw.setPadding(10, 5, 0, 5);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> callback.onClick(modName, index, isChecked));
        linearLayout.addView(sw);
        return linearLayout;
    }
}

interface ChangeCallback {
    void onClick(String modName, int index, boolean state);
}
