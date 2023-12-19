package gg.day.dark;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ModService extends Service {
    private ModMenu modMenu;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Start.init(this);
        modMenu = new ModMenu();
        modMenu.createMenu(this);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            public void run() {
                thread();
                handler.postDelayed(this, 1000);
            }
        });
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onTaskRemoved(rootIntent);
    }

    private void thread() {
        if (modMenu.rootFrame != null && isNotInGame()) {
            modMenu.rootFrame.setVisibility(View.INVISIBLE);
        } else {
            assert modMenu.rootFrame != null;
            modMenu.rootFrame.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (modMenu.rootFrame != null)
            modMenu.windowManager.removeView(modMenu.rootFrame);
        if (modMenu.icon != null)
            modMenu.icon.removeView(modMenu.iconLauncher);
        Toast.makeText(getBaseContext(), "Game Stopped", Toast.LENGTH_SHORT).show();
    }

    private boolean isNotInGame() {
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(runningAppProcessInfo);
        return runningAppProcessInfo.importance != 100;
    }
}
