
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

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ModMenu {

    WindowManager windowManager;
    private WindowManager.LayoutParams wParam;
    FrameLayout rootFrame;
    private LinearLayout boxMenu;
    private LinearLayout parentMod;
    LinearLayout icon;
    ImageView iconLauncher;
    private final ModUtilities modUtilities;
    private final Context context;
    private int initialX, initialY;
    private float initialTouchX, initialTouchY;

    public ModMenu(Context context) {
        this.context = context;
        modUtilities = new ModUtilities(context);
        setRootFrame();
        windowManager = this.setWindowManager();
    }

    @SuppressLint("WrongConstant")
    private WindowManager setWindowManager() {
        //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        int iParams = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? 2038 : 2002;
        wParam = new WindowManager.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, iParams, 8, -3);
        wParam.gravity = 51;
        wParam.x = 0;
        wParam.y = 100;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.addView(rootFrame, wParam);
        return wm;
    }

    private void setRootFrame() {
        rootFrame = new FrameLayout(context);
        setModBody();
        setBoxMenu();
        setIcon();
        rootFrame.addView(icon);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setBoxMenu() {
        boxMenu = new LinearLayout(context);
        boxMenu.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        boxMenu.setOrientation(LinearLayout.VERTICAL);
        boxMenu.addView(parentMod);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener motionListener() {
        return (view, motionEvent) -> {
            int m = motionEvent.getAction();
            switch (m) {
                case MotionEvent.ACTION_UP:
                    parentMod.setAlpha(1f);
                    int rawX = (int) (motionEvent.getRawX() - initialTouchX);
                    int rawY = (int) (motionEvent.getRawY() - initialTouchY);
                    try {
                        if (rawX < 10 && rawY < 10) {
                            rootFrame.removeView(icon);
                            rootFrame.addView(boxMenu);
                        }
                    } catch (Exception ignored) {
                    }

                    return true;
                case MotionEvent.ACTION_DOWN:
                    initialX = wParam.x;
                    initialY = wParam.y;
                    initialTouchX = motionEvent.getRawX();
                    initialTouchY = motionEvent.getRawY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    parentMod.setAlpha(0.2f);
                    wParam.x = initialX + ((int) (motionEvent.getRawX() - initialTouchX));
                    wParam.y = initialY + ((int) (motionEvent.getRawY() - initialTouchY));
                    windowManager.updateViewLayout(rootFrame, wParam);
                    return true;
                default:
                    return false;

            }
        };
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void setModBody() {
        //PARENT LAYOUT
        LinearLayout.LayoutParams parentP = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        parentMod = new LinearLayout(context);
        parentMod.setOrientation(LinearLayout.VERTICAL);
        parentMod.setLayoutParams(parentP);
        parentMod.setBackground(Utilities.roundedShapeTB(20, 20, Color.DKGRAY));
        parentMod.setPadding(10, 10, 10, 10);
        //MOD SCROLL
        LinearLayout.LayoutParams scrollParam = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        ScrollView scrollView = new ScrollView(context);
        scrollView.setPadding(10, 10, 10, 10);
        scrollView.setLayoutParams(scrollParam);
        scrollView.setBackgroundColor(Color.GREEN);
        //HEADER
        RelativeLayout headerLayout = new RelativeLayout(context);
        headerLayout.setPadding(0, 0, 10, 10);
        headerLayout.setBackground(Utilities.roundedShapeTB(20, 0, Color.LTGRAY));
        headerLayout.setOnTouchListener(motionListener());
        TextView headerText = new TextView(context);
        headerText.setTypeface(null, Typeface.BOLD);
        headerText.setTextColor(Color.BLUE);
        RelativeLayout.LayoutParams headerP = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        headerP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        headerText.setText("MODDED BY DAYONA");
        headerText.setLayoutParams(headerP);
        headerText.setPadding(0, 10, 0, 10);
        headerLayout.addView(headerText);
        //MOD LAYOUT
        LinearLayout modLayout = new LinearLayout(context);
        modLayout.setOrientation(LinearLayout.VERTICAL);
        modLayout.setLayoutParams(scrollParam);
        modLayout.setBackgroundColor(Color.BLACK);
        //MOD LIST
        LinearLayout modListLayout = new LinearLayout(context);
        modListLayout.setOrientation(LinearLayout.VERTICAL);
        modListLayout.setPadding(20, 40, 5, 40);
        //FOOTER
        RelativeLayout footerLayout = new RelativeLayout(context);
        footerLayout.setBackground(Utilities.roundedShapeTB(0, 20, Color.LTGRAY));
        footerLayout.setPadding(30, 20, 30, 20);
        footerLayout.setOnTouchListener(motionListener());
        //KILL/HIDE BUTTON ITERATION
        for (int i = 0; i < 2; i++) {
            TextView btn = new TextView(context);
            btn.setText(i == 0 ? "KILL" : "HIDE");
            btn.setTypeface(null, Typeface.BOLD);
            btn.setTextColor(Color.BLUE);
            RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            btnParams.addRule(i == 0 ? RelativeLayout.ALIGN_START : RelativeLayout.ALIGN_PARENT_END);
            btn.setLayoutParams(btnParams);
            btn.setOnClickListener(hideKillBtn(i));
            footerLayout.addView(btn);
        }
        //ADD MOD TO SCROLL VIEW
        modLayout.addView(modListLayout);
        scrollView.addView(modLayout);
        //MOD ITERATION
        for (int i = 0; i < Start.getListMenu().length; i++) {
            modListLayout.addView(modChild(i));
        }
        //ADD ALL TO PARENT MOD
        parentMod.addView(headerLayout);
        parentMod.addView(scrollView);
        parentMod.addView(footerLayout);
    }

    LinearLayout modChild(int i) {
        String[] m = Start.getListMenu()[i].split("_");
        switch (m[0]) {
            case "Switch":
                return modUtilities.registerSwitcher(m[1], i, Start::changeState);
            case "TextField":
                return modUtilities.registerTextField(m[1], i, Start::changeState);
            case "Slider":
                return modUtilities.registerSlider(m[1], i);
            default:
                LinearLayout other = new LinearLayout(context);
                TextView tv = new TextView(context);
                tv.setText(m[1]);
                tv.setTextColor(Color.WHITE);
                other.addView(tv);
                return other;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setIcon() {
        icon = new LinearLayout(context);
        icon.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        iconLauncher = new ImageView(context);
        iconLauncher.setLayoutParams(icon.getLayoutParams());
        int applyDimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, context.getResources().getDisplayMetrics());
        iconLauncher.getLayoutParams().height = applyDimension;
        iconLauncher.getLayoutParams().width = applyDimension;
        byte[] decode = Base64.decode(Start.base64Icon(), 0);
        iconLauncher.setScaleType(ImageView.ScaleType.FIT_XY);
        Bitmap bitmap = Utilities.roundCornerImage(BitmapFactory.decodeByteArray(decode, 0, decode.length), 40);
        iconLauncher.setImageBitmap(bitmap);
        iconLauncher.setOnTouchListener(motionListener());
        icon.addView(iconLauncher);
    }

    private View.OnClickListener hideKillBtn(int index) {
        return view -> {
            switch (index) {
                case 0:
                    Toast.makeText(context, "Remember the last icon position!", Toast.LENGTH_SHORT).show();
                    rootFrame.removeView(boxMenu);
                    rootFrame.addView(icon);
                    icon.setAlpha(0f);
                    break;
                case 1:
                    rootFrame.removeView(boxMenu);
                    rootFrame.addView(icon);
                    icon.setAlpha(1f);
                    break;
            }
        };
    }

}
