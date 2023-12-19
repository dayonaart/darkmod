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
