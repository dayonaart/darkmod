package gg.day.dark;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Start {
    private static native int checkOverlayPermission(Context context);

    public static native void switchState(String modName, int modIndex, boolean state);

    public static native String base64Icon();

    public static native String[] getListMenu();

    public static native void init(ModService modService);

    static {
        System.loadLibrary("daydark");
    }

    public static void Mod(Context context) {
        if (checkOverlayPermission(context) != 1) return;
        Intent modService = new Intent(context, ModService.class);
        context.startService(modService);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
