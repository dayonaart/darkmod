package gg.day.dark;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.content.Context;
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

    private ModUtilities modUtilities;
    WindowManager windowManager;
    WindowManager.LayoutParams wParam;
    FrameLayout rootFrame;
    LinearLayout boxMenu;
    ScrollView scrollView;

    private Context context;
    private int initialX, initialY;
    private float initialTouchX, initialTouchY;
    LinearLayout icon;
    ImageView iconLauncher;

    public void createMenu(Context context) {
        this.context = context;
        modUtilities = new ModUtilities(context);
        setRootFrame();
        windowManager = this.setWindowManager();
    }

    @SuppressLint("WrongConstant")
    private WindowManager setWindowManager() {
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
        initScrollView();
        setBoxMenu();
        setIcon();
        rootFrame.addView(icon);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setBoxMenu() {
        boxMenu = new LinearLayout(context);
        boxMenu.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        boxMenu.setOrientation(LinearLayout.VERTICAL);
        boxMenu.addView(scrollView);
    }

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener motionListener() {
        return (view, motionEvent) -> {
            int m = motionEvent.getAction();
            switch (m) {
                case MotionEvent.ACTION_UP:
                    scrollView.setAlpha(1f);
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
                    scrollView.setAlpha(0.2f);
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
    private void initScrollView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        scrollView = new ScrollView(context);
        scrollView.setPadding(10, 10, 10, 10);
        scrollView.setLayoutParams(params);
        scrollView.setBackgroundColor(Color.GRAY);
        //HEADER
        RelativeLayout headerLayout = new RelativeLayout(context);
        headerLayout.setPadding(0, 0, 10, 10);
        headerLayout.setBackgroundColor(Color.MAGENTA);
        headerLayout.setOnTouchListener(motionListener());
        TextView headerText = new TextView(context);
        headerText.setTypeface(null, Typeface.BOLD);
        headerText.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams headerP = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        headerP.addRule(RelativeLayout.CENTER_HORIZONTAL);
        headerText.setText("MOD BY DAYONA");
        headerText.setLayoutParams(headerP);
        headerLayout.addView(headerText);
        //MOD LAYOUT
        LinearLayout modLayout = new LinearLayout(context);
        modLayout.setOrientation(LinearLayout.VERTICAL);
        modLayout.setLayoutParams(params);
        //MOD LIST
        LinearLayout modListLayout = new LinearLayout(context);
        modListLayout.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        modListLayout.setOrientation(LinearLayout.VERTICAL);
        modListLayout.setPadding(0, 40, 0, 40);
        //FOOTER
        RelativeLayout footerLayout = new RelativeLayout(context);
        footerLayout.setBackgroundColor(Color.YELLOW);
        footerLayout.setPadding(30, 10, 30, 10);
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
        scrollView.addView(modLayout);
        modLayout.addView(headerLayout);
        modLayout.addView(modListLayout);

        //MOD ITERATION
        for (int i = 0; i < Start.getListMenu().length; i++) {
            LinearLayout sw = modUtilities.registerSwitcher(Start.getListMenu()[i], 0, Start::switchState);
            modListLayout.addView(sw);
        }
        modLayout.addView(footerLayout);
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
        iconLauncher.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        iconLauncher.setOnTouchListener(motionListener());
        icon.addView(iconLauncher);
    }

    private View.OnClickListener hideKillBtn(int index) {
        return view -> {
            switch (index) {
                case 0:
                    Toast.makeText(context, String.valueOf(index), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    rootFrame.removeView(boxMenu);
                    rootFrame.addView(icon);
                    break;
            }
        };
    }
}
