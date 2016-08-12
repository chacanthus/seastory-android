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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build$VERSION;
import android.os.Debug;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.badlogic.gdx.utils.GdxRuntimeException;

public class AndroidFragmentApplication extends Fragment implements AndroidApplicationBase {
    public abstract interface Callbacks {
        public abstract void exit();
    }

    private final Array androidEventListeners;
    protected AndroidAudio audio;
    protected Callbacks callbacks;
    AndroidClipboard clipboard;
    protected final Array executedRunnables;
    protected AndroidFiles files;
    protected boolean firstResume;
    protected AndroidGraphics graphics;
    public Handler handler;
    protected AndroidInput input;
    protected final Array lifecycleListeners;
    protected ApplicationListener listener;
    protected int logLevel;
    protected AndroidNet net;
    protected final Array runnables;

    static  {
        GdxNativesLoader.load();
    }

    public AndroidFragmentApplication() {
        super();
        this.firstResume = true;
        this.runnables = new Array();
        this.executedRunnables = new Array();
        this.lifecycleListeners = new Array();
        this.androidEventListeners = new Array();
        this.logLevel = 2;
    }

    public void addAndroidEventListener(AndroidEventListener listener) {  // has try-catch handlers
        try {
            this.androidEventListeners.add(listener);
            return;
        label_5:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_5;
        }
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
            this.getActivity().getWindow().addFlags(128);
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
                AndroidFragmentApplication.this.callbacks.exit();
            }
        });
    }

    public ApplicationListener getApplicationListener() {
        return this.listener;
    }

    public Window getApplicationWindow() {
        return this.getActivity().getWindow();
    }

    public Audio getAudio() {
        return this.audio;
    }

    public Clipboard getClipboard() {
        if(this.clipboard == null) {
            this.clipboard = new AndroidClipboard(this.getActivity());
        }

        return this.clipboard;
    }

    public Context getContext() {
        return this.getActivity();
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
        return new AndroidPreferences(this.getActivity().getSharedPreferences(name, 0));
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

    public WindowManager getWindowManager() {
        return this.getContext().getSystemService("window");
    }

    public View initializeForView(ApplicationListener listener) {
        return this.initializeForView(listener, new AndroidApplicationConfiguration());
    }

    public View initializeForView(ApplicationListener listener, AndroidApplicationConfiguration config) {  // has try-catch handlers
        ResolutionStrategy v4_1;
        if(this.getVersion() < 8) {
            throw new GdxRuntimeException("LibGDX requires Android API Level 8 or later.");
        }

        if(config.resolutionStrategy == null) {
            FillResolutionStrategy v4 = new FillResolutionStrategy();
        }
        else {
            v4_1 = config.resolutionStrategy;
        }

        this.graphics = new AndroidGraphics(((AndroidApplicationBase)this), config, v4_1);
        this.input = AndroidInputFactory.newAndroidInput(((Application)this), this.getActivity(), this.graphics.view, config);
        this.audio = new AndroidAudio(this.getActivity(), config);
        this.files = new AndroidFiles(this.getResources().getAssets(), this.getActivity().getFilesDir().getAbsolutePath());
        this.net = new AndroidNet(((AndroidApplicationBase)this));
        this.listener = listener;
        this.handler = new Handler();
        this.addLifecycleListener(new LifecycleListener() {
            public void dispose() {
                AndroidFragmentApplication.this.audio.dispose();
            }

            public void pause() {
                AndroidFragmentApplication.this.audio.pause();
            }

            public void resume() {
                AndroidFragmentApplication.this.audio.resume();
            }
        });
        Gdx.app = ((Application)this);
        Gdx.input = this.getInput();
        Gdx.audio = this.getAudio();
        Gdx.files = this.getFiles();
        Gdx.graphics = this.getGraphics();
        Gdx.net = this.getNet();
        this.createWakeLock(config.useWakelock);
        this.useImmersiveMode(config.useImmersiveMode);
        if(!config.useImmersiveMode) {
            goto label_68;
        }

        if(this.getVersion() < 19) {
            goto label_68;
        }

        try {
            Class v3 = Class.forName("com.badlogic.gdx.backends.android.AndroidVisibilityListener");
            Object v2 = v3.newInstance();
            Method v1 = v3.getDeclaredMethod("createListener", AndroidApplicationBase.class);
            v1.invoke(v2, this);
        }
        catch(Exception v0) {
            this.log("AndroidApplication", "Failed to create AndroidVisibilityListener", ((Throwable)v0));
        }

    label_68:
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {  // has try-catch handlers
        super.onActivityResult(requestCode, resultCode, data);
        int v0 = 0;
        try {
            while(v0 < this.androidEventListeners.size) {
                this.androidEventListeners.get(v0).onActivityResult(requestCode, resultCode, data);
                ++v0;
            }

            return;
        label_13:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_13;
        }
    }

    public void onAttach(Activity activity) {
        if((activity instanceof Callbacks)) {
            this.callbacks = activity;
        }
        else if((this.getParentFragment() instanceof Callbacks)) {
            this.callbacks = this.getParentFragment();
        }
        else if((this.getTargetFragment() instanceof Callbacks)) {
            this.callbacks = this.getTargetFragment();
        }
        else {
            goto label_18;
        }

        super.onAttach(activity);
        return;
    label_18:
        throw new RuntimeException("Missing AndroidFragmentApplication.Callbacks. Please implement AndroidFragmentApplication.Callbacks on the parent activity, fragment or target fragment.");
    }

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        boolean v0 = false;
        if(config.hardKeyboardHidden == 1) {
            v0 = true;
        }

        this.input.keyboardAvailable = v0;
    }

    public void onDetach() {
        super.onDetach();
        this.callbacks = null;
    }

    public void onPause() {
        boolean v0 = this.graphics.isContinuousRendering();
        boolean v1 = AndroidGraphics.enforceContinuousRendering;
        AndroidGraphics.enforceContinuousRendering = true;
        this.graphics.setContinuousRendering(true);
        this.graphics.pause();
        this.input.onPause();
        if((this.isRemoving()) || (this.getActivity().isFinishing())) {
            this.graphics.clearManagedCaches();
            this.graphics.destroy();
        }

        AndroidGraphics.enforceContinuousRendering = v1;
        this.graphics.setContinuousRendering(v0);
        this.graphics.onPauseGLSurfaceView();
        super.onPause();
    }

    public void onResume() {
        Gdx.app = ((Application)this);
        Gdx.input = this.getInput();
        Gdx.audio = this.getAudio();
        Gdx.files = this.getFiles();
        Gdx.graphics = this.getGraphics();
        Gdx.net = this.getNet();
        this.input.onResume();
        if(this.graphics != null) {
            this.graphics.onResumeGLSurfaceView();
        }

        if(!this.firstResume) {
            this.graphics.resume();
        }
        else {
            this.firstResume = false;
        }

        super.onResume();
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

    public void removeAndroidEventListener(AndroidEventListener listener) {  // has try-catch handlers
        try {
            this.androidEventListeners.removeValue(listener, true);
            return;
        label_6:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_6;
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
        this.getActivity().runOnUiThread(runnable);
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void useImmersiveMode(boolean use) {  // has try-catch handlers
        if(!use) {
            return;
        }

        if(this.getVersion() >= 19) {
            goto label_5;
        }

        return;
        try {
        label_5:
            View v3 = this.graphics.getView();
            Class v4 = View.class;
            Method v2 = v4.getMethod("setSystemUiVisibility", Integer.TYPE);
            v2.invoke(v3, Integer.valueOf(5894));
        }
        catch(Exception v1) {
            this.log("AndroidApplication", "Failed to setup immersive mode, a throwable has occurred.", ((Throwable)v1));
        }
    }
}

