
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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class ModUtilities {

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
        sw.setTextColor(Color.WHITE);
        sw.setWidth(500);
        sw.setTextSize(10);
        sw.setPadding(10, 5, 0, 5);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> callback.onClick(modName, index, isChecked, null));
        linearLayout.addView(sw);
        return linearLayout;
    }

    @SuppressLint("SetTextI18n")
    public LinearLayout registerTextField(String modName, int index, ChangeCallback callback) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 5, 0, 5);
        TextView textView = new TextView(context);
        textView.setText(modName);
        textView.setTextColor(Color.WHITE);
        LinearLayout modL = new LinearLayout(context);
        modL.setOrientation(LinearLayout.HORIZONTAL);
        EditText editText = new EditText(context);
        editText.setText("0x360BC4", TextView.BufferType.EDITABLE);
        editText.setTextColor(Color.BLACK);
        editText.setBackgroundColor(Color.WHITE);
        editText.setWidth(300);
        Button btn = new Button(context);
        btn.setText("Update");
        btn.setTextColor(Color.WHITE);
        btn.setOnClickListener(v -> callback.onClick(modName, index, false, editText.getText().toString()));
        modL.addView(editText);
        modL.addView(btn);
        linearLayout.addView(modL);
        return linearLayout;
    }

    public LinearLayout registerSlider(String modName, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(10, 5, 0, 5);
        TextView textView = new TextView(context);
        textView.setText(modName);
        textView.setTextSize(10);
        textView.setTextColor(Color.WHITE);
        SeekBar seekBar = new SeekBar(context);
        seekBar.setLayoutParams(params);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(seekBar);
        return linearLayout;
    }
}

interface ChangeCallback {
    void onClick(String modName, int index, boolean switchState, String txtState);
}


