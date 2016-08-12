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

import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView$EGLConfigChooser;
import android.opengl.GLSurfaceView$Renderer;
import android.os.Build$VERSION;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20API18;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.utils.GdxRuntimeException;
import javax.microedition.khronos.opengles.GL10;

public final class AndroidGraphicsLiveWallpaper extends AndroidGraphics {
    public AndroidGraphicsLiveWallpaper(AndroidLiveWallpaper lwp, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy) {
        super(((AndroidApplicationBase)lwp), config, resolutionStrategy, false);
    }

    protected View createGLSurfaceView(AndroidApplicationBase application, ResolutionStrategy resolutionStrategy) {
        com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper$2 v0_1;
        if(!this.checkGL20()) {
            throw new GdxRuntimeException("Libgdx requires OpenGL ES 2.0");
        }

        GLSurfaceView$EGLConfigChooser v7 = this.getEglConfigChooser();
        if(Build$VERSION.SDK_INT > 10 || !this.config.useGLSurfaceView20API18) {
            v0_1 = new GLSurfaceView20() {
                public SurfaceHolder getHolder() {
                    return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
                }

                public void onDestroy() {
                    this.onDetachedFromWindow();
                }
            };
            if(v7 != null) {
                ((GLSurfaceView20)v0_1).setEGLConfigChooser(v7);
            }
            else {
                ((GLSurfaceView20)v0_1).setEGLConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil);
            }

            ((GLSurfaceView20)v0_1).setRenderer(((GLSurfaceView$Renderer)this));
        }
        else {
            com.badlogic.gdx.backends.android.AndroidGraphicsLiveWallpaper$1 v0 = new GLSurfaceView20API18() {
                public SurfaceHolder getHolder() {
                    return AndroidGraphicsLiveWallpaper.this.getSurfaceHolder();
                }

                public void onDestroy() {
                    this.onDetachedFromWindow();
                }
            };
            if(v7 != null) {
                ((GLSurfaceView20API18)v0).setEGLConfigChooser(v7);
            }
            else {
                ((GLSurfaceView20API18)v0).setEGLConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil);
            }

            ((GLSurfaceView20API18)v0).setRenderer(((GLSurfaceView$Renderer)this));
        }

        return ((View)v0_1);
    }

    SurfaceHolder getSurfaceHolder() {  // has try-catch handlers
        try {
            return this.app.service.getSurfaceHolder();
        label_8:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_8;
        }
    }

    protected void logManagedCachesStatus() {
        if(AndroidLiveWallpaperService.DEBUG) {
            super.logManagedCachesStatus();
        }
    }

    public void onDestroyGLSurfaceView() {  // has try-catch handlers
        if(this.view == null) {
            return;
        }

        if(!(this.view instanceof GLSurfaceView)) {
            if((this.view instanceof GLSurfaceViewAPI18)) {
                goto label_8;
            }

            return;
        }

        try {
        label_8:
            this.view.getClass().getMethod("onDestroy", new Class[0]).invoke(this.view, new Object[0]);
            if(!AndroidLiveWallpaperService.DEBUG) {
                return;
            }

            Log.d("WallpaperService", " > AndroidLiveWallpaper - onDestroy() stopped GLThread managed by GLSurfaceView");
        }
        catch(Throwable v0) {
            Log.e("WallpaperService", "failed to destroy GLSurfaceView\'s thread! GLSurfaceView.onDetachedFromWindow impl changed since API lvl 16!");
            v0.printStackTrace();
        }
    }

    public void onDrawFrame(GL10 gl) {  // has try-catch handlers
        AndroidApplicationBase v8_1;
        int v0;
        long v6 = System.nanoTime();
        this.deltaTime = (((float)(v6 - this.lastFrameTime))) / 1000000000f;
        this.lastFrameTime = v6;
        if(!this.resume) {
            this.mean.addValue(this.deltaTime);
        }
        else {
            this.deltaTime = 0f;
        }

        try {
            boolean v4 = this.running;
            boolean v2 = this.pause;
            boolean v1 = this.destroy;
            boolean v3 = this.resume;
            if(this.resume) {
                this.resume = false;
                this.synch.notifyAll();
            }

            if(this.pause) {
                this.pause = false;
                this.synch.notifyAll();
            }

            if(this.destroy) {
                this.destroy = false;
                this.synch.notifyAll();
            }

            if(!v3) {
                goto label_49;
            }
        }
        catch(Throwable v8) {
            try {
            label_78:
                throw v8;
            }
            catch(Throwable v8) {
                goto label_78;
            }
        }

        this.app.getApplicationListener().resume();
        Gdx.app.log("AndroidGraphics", "resumed");
    label_49:
        if(!v4) {
            goto label_94;
        }

        this.app.getRunnables();
        try {
            this.app.getExecutedRunnables().clear();
            this.app.getExecutedRunnables().addAll(this.app.getRunnables());
            this.app.getRunnables().clear();
            v0 = 0;
            while(true) {
            label_64:
                if(v0 >= this.app.getExecutedRunnables().size) {
                    goto label_84;
                }

                try {
                    this.app.getExecutedRunnables().get(v0).run();
                    break;
                }
                catch(Throwable v5) {
                    try {
                        v5.printStackTrace();
                        break;
                    }
                    catch(Throwable v8) {
                        goto label_83;
                    }
                }
            }
        }
        catch(Throwable v8) {
            goto label_83;
        }

        ++v0;
        goto label_64;
        try {
        label_84:
            v8_1 = this.app;
        }
        catch(Throwable v8) {
            goto label_83;
        }

        v8_1.getInput().processEvents();
        ++this.frameId;
        this.app.getApplicationListener().render();
        goto label_94;
        try {
        label_83:
            throw v8;
        }
        catch(Throwable v8) {
            goto label_83;
        }

    label_94:
        if(v2) {
            this.app.getApplicationListener().pause();
            Gdx.app.log("AndroidGraphics", "paused");
        }

        if(v1) {
            this.app.getApplicationListener().dispose();
            Gdx.app.log("AndroidGraphics", "destroyed");
        }

        if(v6 - this.frameStart > 1000000000) {
            this.fps = this.frames;
            this.frames = 0;
            this.frameStart = v6;
        }

        ++this.frames;
    }

    void resume() {  // has try-catch handlers
        try {
            this.running = true;
            this.resume = true;
            while(true) {
                if(!this.resume) {
                    return;
                }

                try {
                    this.synch.wait();
                    continue;
                }
                catch(InterruptedException v0) {
                    try {
                        Gdx.app.log("AndroidGraphics", "waiting for resume synchronization failed!");
                        continue;
                        return;
                    label_17:
                        throw v1;
                    }
                    catch(Throwable v1) {
                        goto label_17;
                    }
                }
            }
        }
        catch(Throwable v1) {
            goto label_17;
        }
    }
}

