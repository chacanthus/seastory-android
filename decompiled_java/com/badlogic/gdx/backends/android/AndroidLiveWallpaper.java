// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build$VERSION;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application$ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AndroidLiveWallpaper implements AndroidApplicationBase {
    protected AndroidAudio audio;
    AndroidClipboard clipboard;
    protected final Array executedRunnables;
    protected AndroidFiles files;
    protected boolean firstResume;
    protected AndroidGraphicsLiveWallpaper graphics;
    protected AndroidInput input;
    protected final Array lifecycleListeners;
    protected ApplicationListener listener;
    protected int logLevel;
    protected AndroidNet net;
    protected final Array runnables;
    protected AndroidLiveWallpaperService service;

    static  {
        GdxNativesLoader.load();
    }

    public AndroidLiveWallpaper(AndroidLiveWallpaperService service) {
        super();
        this.firstResume = true;
        this.runnables = new Array();
        this.executedRunnables = new Array();
        this.lifecycleListeners = new Array();
        this.logLevel = 2;
        this.service = service;
    }

    public void addLifecycleListener(LifecycleListener listener) {  // has try-catch handlers
        try {
            this.lifecycleListeners.add(listener);
            return;
        label_5:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_5;
        }
    }

    public void debug(String tag, String message) {
        if(this.logLevel >= 3) {
            Log.d(tag, message);
        }
    }

    public void debug(String tag, String message, Throwable exception) {
        if(this.logLevel >= 3) {
            Log.d(tag, message, exception);
        }
    }

    public void error(String tag, String message) {
        if(this.logLevel >= 1) {
            Log.e(tag, message);
        }
    }

    public void error(String tag, String message, Throwable exception) {
        if(this.logLevel >= 1) {
            Log.e(tag, message, exception);
        }
    }

    public void exit() {
    }

    public ApplicationListener getApplicationListener() {
        return this.listener;
    }

    public Window getApplicationWindow() {
        throw new UnsupportedOperationException();
    }

    public Audio getAudio() {
        return this.audio;
    }

    public Clipboard getClipboard() {
        if(this.clipboard == null) {
            this.clipboard = new AndroidClipboard(this.service);
        }

        return this.clipboard;
    }

    public Context getContext() {
        return this.service;
    }

    public Array getExecutedRunnables() {
        return this.executedRunnables;
    }

    public Files getFiles() {
        return this.files;
    }

    public Graphics getGraphics() {
        return this.graphics;
    }

    public Handler getHandler() {
        throw new UnsupportedOperationException();
    }

    public Input getInput() {
        return this.getInput();
    }

    public AndroidInput getInput() {
        return this.input;
    }

    public long getJavaHeap() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public Array getLifecycleListeners() {
        return this.lifecycleListeners;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public long getNativeHeap() {
        return Debug.getNativeHeapAllocatedSize();
    }

    public Net getNet() {
        return this.net;
    }

    public Preferences getPreferences(String name) {
        return new AndroidPreferences(this.service.getSharedPreferences(name, 0));
    }

    public Array getRunnables() {
        return this.runnables;
    }

    public AndroidLiveWallpaperService getService() {
        return this.service;
    }

    public ApplicationType getType() {
        return ApplicationType.Android;
    }

    public int getVersion() {
        return Build$VERSION.SDK_INT;
    }

    public WindowManager getWindowManager() {
        return this.service.getWindowManager();
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        ResolutionStrategy v0_1;
        if(this.getVersion() < 8) {
            throw new GdxRuntimeException("LibGDX requires Android API Level 8 or later.");
        }

        if(config.resolutionStrategy == null) {
            FillResolutionStrategy v0 = new FillResolutionStrategy();
        }
        else {
            v0_1 = config.resolutionStrategy;
        }

        this.graphics = new AndroidGraphicsLiveWallpaper(this, config, v0_1);
        this.input = AndroidInputFactory.newAndroidInput(((Application)this), this.getService(), this.graphics.view, config);
        this.audio = new AndroidAudio(this.getService(), config);
        this.getService().getFilesDir();
        this.files = new AndroidFiles(this.getService().getAssets(), this.getService().getFilesDir().getAbsolutePath());
        this.net = new AndroidNet(((AndroidApplicationBase)this));
        this.listener = listener;
        Gdx.app = ((Application)this);
        Gdx.input = this.input;
        Gdx.audio = this.audio;
        Gdx.files = this.files;
        Gdx.graphics = this.graphics;
        Gdx.net = this.net;
    }

    public void log(String tag, String message) {
        if(this.logLevel >= 2) {
            Log.i(tag, message);
        }
    }

    public void log(String tag, String message, Throwable exception) {
        if(this.logLevel >= 2) {
            Log.i(tag, message, exception);
        }
    }

    public void onDestroy() {
        if(this.graphics != null) {
            this.graphics.onDestroyGLSurfaceView();
        }

        if(this.audio != null) {
            this.audio.dispose();
        }
    }

    public void onPause() {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaper - onPause()");
        }

        this.audio.pause();
        this.input.onPause();
        if(this.graphics != null) {
            this.graphics.onPauseGLSurfaceView();
        }

        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaper - onPause() done!");
        }
    }

    public void onResume() {
        Gdx.app = ((Application)this);
        Gdx.input = this.input;
        Gdx.audio = this.audio;
        Gdx.files = this.files;
        Gdx.graphics = this.graphics;
        Gdx.net = this.net;
        this.input.onResume();
        if(this.graphics != null) {
            this.graphics.onResumeGLSurfaceView();
        }

        if(!this.firstResume) {
            this.audio.resume();
            this.graphics.resume();
        }
        else {
            this.firstResume = false;
        }
    }

    public void postRunnable(Runnable runnable) {  // has try-catch handlers
        try {
            this.runnables.add(runnable);
            return;
        label_5:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_5;
        }
    }

    public void removeLifecycleListener(LifecycleListener listener) {  // has try-catch handlers
        try {
            this.lifecycleListeners.removeValue(listener, true);
            return;
        label_6:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_6;
        }
    }

    public void runOnUiThread(Runnable runnable) {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
        else {
            runnable.run();
        }
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void startActivity(Intent intent) {
        this.service.startActivity(intent);
    }

    public void useImmersiveMode(boolean b) {
        throw new UnsupportedOperationException();
    }
}

