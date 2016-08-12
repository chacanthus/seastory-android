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

import android.os.Build$VERSION;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService$Engine;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder$Callback;
import android.view.WindowManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxNativesLoader;

public abstract class AndroidLiveWallpaperService extends WallpaperService {
    public class AndroidWallpaperEngine extends WallpaperService$Engine {
        protected int engineFormat;
        protected int engineHeight;
        protected boolean engineIsVisible;
        protected int engineWidth;
        boolean offsetsConsumed;
        float xOffset;
        float xOffsetStep;
        int xPixelOffset;
        float yOffset;
        float yOffsetStep;
        int yPixelOffset;

        public AndroidWallpaperEngine(AndroidLiveWallpaperService arg4) {
            AndroidLiveWallpaperService.this = arg4;
            super(((WallpaperService)arg4));
            this.engineIsVisible = false;
            this.offsetsConsumed = true;
            this.xOffset = 0f;
            this.yOffset = 0f;
            this.xOffsetStep = 0f;
            this.yOffsetStep = 0f;
            this.xPixelOffset = 0;
            this.yPixelOffset = 0;
            if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > AndroidWallpaperEngine() " + this.hashCode());
            }
        }

        protected void notifyOffsetsChanged() {
            if(AndroidLiveWallpaperService.this.linkedEngine == this && ((AndroidLiveWallpaperService.this.app.listener instanceof AndroidWallpaperListener)) && !this.offsetsConsumed) {
                this.offsetsConsumed = true;
                AndroidLiveWallpaperService.this.app.postRunnable(new Runnable() {
                    public void run() {  // has try-catch handlers
                        int v7;
                        try {
                            if(this.this$1.this$0.linkedEngine == this.this$1) {
                                v7 = 1;
                            }
                            else {
                                goto label_29;
                            }

                            goto label_10;
                        }
                        catch(Throwable v0) {
                            goto label_32;
                        }

                    label_29:
                        v7 = 0;
                    label_10:
                        if(v7 == 0) {
                            return;
                        }

                        goto label_11;
                        try {
                        label_32:
                            throw v0;
                        }
                        catch(Throwable v0) {
                            goto label_32;
                        }

                    label_11:
                        this.this$1.this$0.app.listener.offsetChange(this.this$1.xOffset, this.this$1.yOffset, this.this$1.xOffsetStep, this.this$1.yOffsetStep, this.this$1.xPixelOffset, this.this$1.yPixelOffset);
                    }
                });
            }
        }

        protected void notifyPreviewState() {
            if(AndroidLiveWallpaperService.this.linkedEngine == this && ((AndroidLiveWallpaperService.this.app.listener instanceof AndroidWallpaperListener))) {
                AndroidLiveWallpaperService.this.app.postRunnable(new Runnable() {
                    public void run() {  // has try-catch handlers
                        int v1 = 0;
                        try {
                            if(!this.this$1.this$0.isPreviewNotified || this.this$1.this$0.notifiedPreviewState != this.val$currentPreviewState) {
                                this.this$1.this$0.notifiedPreviewState = this.val$currentPreviewState;
                                this.this$1.this$0.isPreviewNotified = true;
                                v1 = 1;
                            }

                            if(v1 == 0) {
                                return;
                            }
                        }
                        catch(Throwable v2) {
                            try {
                            label_32:
                                throw v2;
                            }
                            catch(Throwable v2) {
                                goto label_32;
                            }
                        }

                        AndroidLiveWallpaper v0 = this.this$1.this$0.app;
                        if(v0 != null) {
                            v0.listener.previewStateChange(this.val$currentPreviewState);
                        }
                    }
                });
            }
        }

        private void notifySurfaceChanged(int format, int width, int height, boolean forceUpdate) {
            if((forceUpdate) || format != AndroidLiveWallpaperService.this.viewFormat || width != AndroidLiveWallpaperService.this.viewWidth || height != AndroidLiveWallpaperService.this.viewHeight) {
                this.engineFormat = format;
                this.engineWidth = width;
                this.engineHeight = height;
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    AndroidLiveWallpaperService.this.viewFormat = this.engineFormat;
                    AndroidLiveWallpaperService.this.viewWidth = this.engineWidth;
                    AndroidLiveWallpaperService.this.viewHeight = this.engineHeight;
                    AndroidLiveWallpaperService.this.view.surfaceChanged(this.getSurfaceHolder(), AndroidLiveWallpaperService.this.viewFormat, AndroidLiveWallpaperService.this.viewWidth, AndroidLiveWallpaperService.this.viewHeight);
                }
                else if(AndroidLiveWallpaperService.DEBUG) {
                    Log.d("WallpaperService", " > engine is not active, skipping surfaceChanged event");
                }
            }
            else if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > surface is current, skipping surfaceChanged event");
            }
        }

        private void notifyVisibilityChanged(boolean visible) {
            if(this.engineIsVisible != visible) {
                this.engineIsVisible = visible;
                if(this.engineIsVisible) {
                    this.onResume();
                }
                else {
                    this.onPause();
                }
            }
            else if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > visible state is current, skipping visibilityChanged event!");
            }
        }

        public Bundle onCommand(String pAction, int pX, int pY, int pZ, Bundle pExtras, boolean pResultRequested) {
            boolean v0;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v1 = "WallpaperService";
                StringBuilder v2 = new StringBuilder().append(" > AndroidWallpaperEngine - onCommand(").append(pAction).append(" ").append(pX).append(" ").append(pY).append(" ").append(pZ).append(" ").append(pExtras).append(" ").append(pResultRequested).append(")").append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v1, v2.append(v0).toString());
            }

            return super.onCommand(pAction, pX, pY, pZ, pExtras, pResultRequested);
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            boolean v0;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v1 = "WallpaperService";
                StringBuilder v2 = new StringBuilder().append(" > AndroidWallpaperEngine - onCreate() ").append(this.hashCode()).append(" running: ").append(AndroidLiveWallpaperService.this.engines).append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v1, v2.append(v0).append(", thread: ").append(Thread.currentThread().toString()).toString());
            }

            super.onCreate(surfaceHolder);
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            this.offsetsConsumed = false;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xOffsetStep = xOffsetStep;
            this.yOffsetStep = yOffsetStep;
            this.xPixelOffset = xPixelOffset;
            this.yPixelOffset = yPixelOffset;
            this.notifyOffsetsChanged();
            if(!Gdx.graphics.isContinuousRendering()) {
                Gdx.graphics.requestRendering();
            }

            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }

        public void onPause() {
            boolean v0;
            --AndroidLiveWallpaperService.this.visibleEngines;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v2 = "WallpaperService";
                StringBuilder v3 = new StringBuilder().append(" > AndroidWallpaperEngine - onPause() ").append(this.hashCode()).append(", running: ").append(AndroidLiveWallpaperService.this.engines).append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v2, v3.append(v0).append(", visible: ").append(AndroidLiveWallpaperService.this.visibleEngines).toString());
            }

            Log.i("WallpaperService", "engine paused");
            if(AndroidLiveWallpaperService.this.visibleEngines >= AndroidLiveWallpaperService.this.engines) {
                Log.e("WallpaperService", "wallpaper lifecycle error, counted too many visible engines! repairing..");
                AndroidLiveWallpaperService.this.visibleEngines = Math.max(AndroidLiveWallpaperService.this.engines - 1, 0);
            }

            if(AndroidLiveWallpaperService.this.linkedEngine != null && AndroidLiveWallpaperService.this.visibleEngines == 0) {
                AndroidLiveWallpaperService.this.app.onPause();
            }

            if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > AndroidWallpaperEngine - onPause() done!");
            }
        }

        public void onResume() {
            boolean v0;
            ++AndroidLiveWallpaperService.this.visibleEngines;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v3 = "WallpaperService";
                StringBuilder v4 = new StringBuilder().append(" > AndroidWallpaperEngine - onResume() ").append(this.hashCode()).append(", running: ").append(AndroidLiveWallpaperService.this.engines).append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v3, v4.append(v0).append(", visible: ").append(AndroidLiveWallpaperService.this.visibleEngines).toString());
            }

            Log.i("WallpaperService", "engine resumed");
            if(AndroidLiveWallpaperService.this.linkedEngine != null) {
                if(AndroidLiveWallpaperService.this.linkedEngine != this) {
                    AndroidLiveWallpaperService.this.setLinkedEngine(this);
                    AndroidLiveWallpaperService.this.view.surfaceDestroyed(this.getSurfaceHolder());
                    this.notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                    AndroidLiveWallpaperService.this.view.surfaceCreated(this.getSurfaceHolder());
                }
                else {
                    this.notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                }

                if(AndroidLiveWallpaperService.this.visibleEngines == 1) {
                    AndroidLiveWallpaperService.this.app.onResume();
                }

                this.notifyPreviewState();
                this.notifyOffsetsChanged();
                if(Gdx.graphics.isContinuousRendering()) {
                    return;
                }

                Gdx.graphics.requestRendering();
            }
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            boolean v0;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v2 = "WallpaperService";
                StringBuilder v3 = new StringBuilder().append(" > AndroidWallpaperEngine - onSurfaceChanged() isPreview: ").append(this.isPreview()).append(", ").append(this.hashCode()).append(", running: ").append(AndroidLiveWallpaperService.this.engines).append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v2, v3.append(v0).append(", sufcace valid: ").append(this.getSurfaceHolder().getSurface().isValid()).toString());
            }

            Log.i("WallpaperService", "engine surface changed");
            super.onSurfaceChanged(holder, format, width, height);
            this.notifySurfaceChanged(format, width, height, true);
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            boolean v0;
            ++AndroidLiveWallpaperService.this.engines;
            AndroidLiveWallpaperService.this.setLinkedEngine(this);
            if(AndroidLiveWallpaperService.DEBUG) {
                String v3 = "WallpaperService";
                StringBuilder v4 = new StringBuilder().append(" > AndroidWallpaperEngine - onSurfaceCreated() ").append(this.hashCode()).append(", running: ").append(AndroidLiveWallpaperService.this.engines).append(", linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v3, v4.append(v0).toString());
            }

            Log.i("WallpaperService", "engine surface created");
            super.onSurfaceCreated(holder);
            if(AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.visibleEngines = 0;
            }

            if(AndroidLiveWallpaperService.this.engines == 1 && AndroidLiveWallpaperService.this.app == null) {
                AndroidLiveWallpaperService.this.viewFormat = 0;
                AndroidLiveWallpaperService.this.viewWidth = 0;
                AndroidLiveWallpaperService.this.viewHeight = 0;
                AndroidLiveWallpaperService.this.app = new AndroidLiveWallpaper(AndroidLiveWallpaperService.this);
                AndroidLiveWallpaperService.this.onCreateApplication();
                if(AndroidLiveWallpaperService.this.app.graphics == null) {
                    throw new Error("You must override \'AndroidLiveWallpaperService.onCreateApplication\' method and call \'initialize\' from its body.");
                }
            }

            AndroidLiveWallpaperService.this.view = AndroidLiveWallpaperService.this.app.graphics.view;
            this.getSurfaceHolder().removeCallback(AndroidLiveWallpaperService.this.view);
            this.engineFormat = AndroidLiveWallpaperService.this.viewFormat;
            this.engineWidth = AndroidLiveWallpaperService.this.viewWidth;
            this.engineHeight = AndroidLiveWallpaperService.this.viewHeight;
            if(AndroidLiveWallpaperService.this.engines == 1) {
                AndroidLiveWallpaperService.this.view.surfaceCreated(holder);
            }
            else {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(holder);
                this.notifySurfaceChanged(this.engineFormat, this.engineWidth, this.engineHeight, false);
                AndroidLiveWallpaperService.this.view.surfaceCreated(holder);
            }

            this.notifyPreviewState();
            this.notifyOffsetsChanged();
            if(!Gdx.graphics.isContinuousRendering()) {
                Gdx.graphics.requestRendering();
            }
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            boolean v0;
            --AndroidLiveWallpaperService.this.engines;
            if(AndroidLiveWallpaperService.DEBUG) {
                String v2 = "WallpaperService";
                StringBuilder v3 = new StringBuilder().append(" > AndroidWallpaperEngine - onSurfaceDestroyed() ").append(this.hashCode()).append(", running: ").append(AndroidLiveWallpaperService.this.engines).append(" ,linked: ");
                if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                Log.d(v2, v3.append(v0).append(", isVisible: ").append(this.engineIsVisible).toString());
            }

            Log.i("WallpaperService", "engine surface destroyed");
            if(AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.onDeepPauseApplication();
            }

            if(AndroidLiveWallpaperService.this.linkedEngine == this && AndroidLiveWallpaperService.this.view != null) {
                AndroidLiveWallpaperService.this.view.surfaceDestroyed(holder);
            }

            this.engineFormat = 0;
            this.engineWidth = 0;
            this.engineHeight = 0;
            if(AndroidLiveWallpaperService.this.engines == 0) {
                AndroidLiveWallpaperService.this.linkedEngine = null;
            }

            super.onSurfaceDestroyed(holder);
        }

        public void onTouchEvent(MotionEvent event) {
            if(AndroidLiveWallpaperService.this.linkedEngine == this) {
                AndroidLiveWallpaperService.this.app.input.onTouch(null, event);
            }
        }

        public void onVisibilityChanged(boolean visible) {
            boolean v0 = this.isVisible();
            if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > AndroidWallpaperEngine - onVisibilityChanged(paramVisible: " + visible + " reportedVisible: " + v0 + ") " + this.hashCode() + ", sufcace valid: " + this.getSurfaceHolder().getSurface().isValid());
            }

            super.onVisibilityChanged(visible);
            if((v0) || !visible) {
                this.notifyVisibilityChanged(visible);
            }
            else if(AndroidLiveWallpaperService.DEBUG) {
                Log.d("WallpaperService", " > fake visibilityChanged event! Android WallpaperService likes do that!");
            }
        }
    }

    static boolean DEBUG = false;
    static final String TAG = "WallpaperService";
    protected volatile AndroidLiveWallpaper app;
    protected int engines;
    protected volatile boolean isPreviewNotified;
    protected volatile AndroidWallpaperEngine linkedEngine;
    protected volatile boolean notifiedPreviewState;
    volatile int[] sync;
    protected SurfaceHolder$Callback view;
    protected int viewFormat;
    protected int viewHeight;
    protected int viewWidth;
    protected int visibleEngines;

    static  {
        GdxNativesLoader.load();
        AndroidLiveWallpaperService.DEBUG = false;
    }

    public AndroidLiveWallpaperService() {
        super();
        this.app = null;
        this.view = null;
        this.engines = 0;
        this.visibleEngines = 0;
        this.linkedEngine = null;
        this.isPreviewNotified = false;
        this.notifiedPreviewState = false;
        this.sync = new int[0];
    }

    protected void finalize() throws Throwable {
        Log.i("WallpaperService", "service finalized");
        super.finalize();
    }

    public AndroidLiveWallpaper getLiveWallpaper() {
        return this.app;
    }

    public SurfaceHolder getSurfaceHolder() {  // has try-catch handlers
        SurfaceHolder v0_1;
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - getSurfaceHolder()");
        }

        try {
            if(this.linkedEngine == null) {
                v0_1 = null;
            }
            else {
                v0_1 = this.linkedEngine.getSurfaceHolder();
            }

            return v0_1;
        label_14:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_14;
        }
    }

    public WindowManager getWindowManager() {
        return this.getSystemService("window");
    }

    public void initialize(ApplicationListener listener) {
        this.initialize(listener, new AndroidApplicationConfiguration());
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - initialize()");
        }

        this.app.initialize(listener, config);
        if((config.getTouchEventsForLiveWallpaper) && Integer.parseInt(Build$VERSION.SDK) >= 7) {
            this.linkedEngine.setTouchEventsEnabled(true);
        }
    }

    public void onCreate() {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - onCreate() " + this.hashCode());
        }

        Log.i("WallpaperService", "service created");
        super.onCreate();
    }

    public void onCreateApplication() {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - onCreateApplication()");
        }
    }

    public WallpaperService$Engine onCreateEngine() {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - onCreateEngine()");
        }

        Log.i("WallpaperService", "engine created");
        return new AndroidWallpaperEngine(this);
    }

    public void onDeepPauseApplication() {
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - onDeepPauseApplication()");
        }

        if(this.app != null) {
            this.app.graphics.clearManagedCaches();
        }
    }

    public void onDestroy() {
        AndroidLiveWallpaper v3 = null;
        if(AndroidLiveWallpaperService.DEBUG) {
            Log.d("WallpaperService", " > AndroidLiveWallpaperService - onDestroy() " + this.hashCode());
        }

        Log.i("WallpaperService", "service destroyed");
        super.onDestroy();
        if(this.app != null) {
            this.app.onDestroy();
            this.app = v3;
            this.view = ((SurfaceHolder$Callback)v3);
        }
    }

    protected void setLinkedEngine(AndroidWallpaperEngine linkedEngine) {  // has try-catch handlers
        try {
            this.linkedEngine = linkedEngine;
            return;
        label_4:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_4;
        }
    }
}

