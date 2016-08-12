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
import android.content.res.Configuration;
import android.os.Build$VERSION;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout$LayoutParams;
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
import java.util.Arrays;

public class AndroidDaydream extends DreamService implements AndroidApplicationBase {
    protected AndroidAudio audio;
    AndroidClipboard clipboard;
    protected final Array executedRunnables;
    protected AndroidFiles files;
    protected boolean firstResume;
    protected AndroidGraphics graphics;
    protected Handler handler;
    protected AndroidInput input;
    protected final Array lifecycleListeners;
    protected ApplicationListener listener;
    protected int logLevel;
    protected AndroidNet net;
    protected final Array runnables;

    static  {
        GdxNativesLoader.load();
    }

    public AndroidDaydream() {
        super();
        this.firstResume = true;
        this.runnables = new Array();
        this.executedRunnables = new Array();
        this.lifecycleListeners = new Array();
        this.logLevel = 2;
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

    protected FrameLayout$LayoutParams createLayoutParams() {
        FrameLayout$LayoutParams v0 = new FrameLayout$LayoutParams(-1, -1);
        v0.gravity = 17;
        return v0;
    }

    protected void createWakeLock(boolean use) {
        if(use) {
            this.getWindow().addFlags(128);
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
        this.handler.post(new Runnable() {
            public void run() {
                AndroidDaydream.this.finish();
            }
        });
    }

    public ApplicationListener getApplicationListener() {
        return this.listener;
    }

    public Window getApplicationWindow() {
        return this.getWindow();
    }

    public Audio getAudio() {
        return this.audio;
    }

    public Clipboard getClipboard() {
        if(this.clipboard == null) {
            this.clipboard = new AndroidClipboard(((Context)this));
        }

        return this.clipboard;
    }

    public Context getContext() {
        return this;
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
        return this.handler;
    }

    public AndroidInput getInput() {
        return this.input;
    }

    public Input getInput() {
        return this.getInput();
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
        return new AndroidPreferences(this.getSharedPreferences(name, 0));
    }

    public Array getRunnables() {
        return this.runnables;
    }

    public ApplicationType getType() {
        return ApplicationType.Android;
    }

    public int getVersion() {
        return Build$VERSION.SDK_INT;
    }

    protected void hideStatusBar(AndroidApplicationConfiguration config) {  // has try-catch handlers
        if(!config.hideStatusBar) {
            return;
        }

        if(this.getVersion() >= 11) {
            goto label_6;
        }

        return;
    label_6:
        View v2 = this.getWindow().getDecorView();
        try {
            Class v3 = View.class;
            Method v1 = v3.getMethod("setSystemUiVisibility", Integer.TYPE);
            v1.invoke(v2, Integer.valueOf(0));
            v3_1 = new Object[1];
            v3_1[0] = Integer.valueOf(1);
            v1.invoke(v2, v3_1);
        }
        catch(Exception v0) {
            this.log("AndroidApplication", "Can\'t hide status bar", ((Throwable)v0));
        }
    }

    private void init(ApplicationListener listener, AndroidApplicationConfiguration config, boolean isForView) {
        ResolutionStrategy v0_1;
        if(config.resolutionStrategy == null) {
            FillResolutionStrategy v0 = new FillResolutionStrategy();
        }
        else {
            v0_1 = config.resolutionStrategy;
        }

        this.graphics = new AndroidGraphics(((AndroidApplicationBase)this), config, v0_1);
        this.input = AndroidInputFactory.newAndroidInput(((Application)this), ((Context)this), this.graphics.view, config);
        this.audio = new AndroidAudio(((Context)this), config);
        this.getFilesDir();
        this.files = new AndroidFiles(this.getAssets(), this.getFilesDir().getAbsolutePath());
        this.net = new AndroidNet(((AndroidApplicationBase)this));
        this.listener = listener;
        this.handler = new Handler();
        this.addLifecycleListener(new LifecycleListener() {
            public void dispose() {
                AndroidDaydream.this.audio.dispose();
                AndroidDaydream.this.audio = null;
            }

            public void pause() {
                AndroidDaydream.this.audio.pause();
            }

            public void resume() {
                AndroidDaydream.this.audio.resume();
            }
        });
        Gdx.app = ((Application)this);
        Gdx.input = this.getInput();
        Gdx.audio = this.getAudio();
        Gdx.files = this.getFiles();
        Gdx.graphics = this.getGraphics();
        Gdx.net = this.getNet();
        if(!isForView) {
            this.setFullscreen(true);
            this.setContentView(this.graphics.getView(), this.createLayoutParams());
        }

        this.createWakeLock(config.useWakelock);
        this.hideStatusBar(config);
    }

    public void initialize(ApplicationListener listener) {
        this.initialize(listener, new AndroidApplicationConfiguration());
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.init(listener, config, false);
    }

    public View initializeForView(ApplicationListener listener) {
        return this.initializeForView(listener, new AndroidApplicationConfiguration());
    }

    public View initializeForView(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.init(listener, config, true);
        return this.graphics.getView();
    }

    public void log(String tag, String message, Throwable exception) {
        if(this.logLevel >= 2) {
            Log.i(tag, message, exception);
        }
    }

    public void log(String tag, String message) {
        if(this.logLevel >= 2) {
            Log.i(tag, message);
        }
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        boolean v0 = false;
        if(config.hardKeyboardHidden == 1) {
            v0 = true;
        }

        this.input.keyboardAvailable = v0;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onDreamingStarted() {
        Gdx.app = ((Application)this);
        Gdx.input = this.getInput();
        Gdx.audio = this.getAudio();
        Gdx.files = this.getFiles();
        Gdx.graphics = this.getGraphics();
        Gdx.net = this.getNet();
        this.getInput().registerSensorListeners();
        if(this.graphics != null) {
            this.graphics.onResumeGLSurfaceView();
        }

        if(!this.firstResume) {
            this.graphics.resume();
        }
        else {
            this.firstResume = false;
        }

        super.onDreamingStarted();
    }

    public void onDreamingStopped() {
        boolean v0 = this.graphics.isContinuousRendering();
        this.graphics.setContinuousRendering(true);
        this.graphics.pause();
        this.input.unregisterSensorListeners();
        Arrays.fill(this.input.realId, -1);
        Arrays.fill(this.input.touched, false);
        this.graphics.clearManagedCaches();
        this.graphics.destroy();
        this.graphics.setContinuousRendering(v0);
        this.graphics.onPauseGLSurfaceView();
        super.onDreamingStopped();
    }

    public void postRunnable(Runnable runnable) {  // has try-catch handlers
        try {
            this.runnables.add(runnable);
            Gdx.graphics.requestRendering();
            return;
        label_7:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_7;
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

    public void useImmersiveMode(boolean b) {
        throw new UnsupportedOperationException();
    }
}

