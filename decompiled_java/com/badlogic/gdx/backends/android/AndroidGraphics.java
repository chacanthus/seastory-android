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
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics$BufferFormat;
import com.badlogic.gdx.Graphics$DisplayMode;
import com.badlogic.gdx.Graphics$GraphicsType;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20API18;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18;
import com.badlogic.gdx.backends.android.surfaceview.GdxEglConfigChooser;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.WindowedMean;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;
import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

public class AndroidGraphics implements GLSurfaceView$Renderer, Graphics {
    class AndroidDisplayMode extends DisplayMode {
        protected AndroidDisplayMode(AndroidGraphics arg1, int width, int height, int refreshRate, int bitsPerPixel) {
            AndroidGraphics.this = arg1;
            super(width, height, refreshRate, bitsPerPixel);
        }
    }

    private static final String LOG_TAG = "AndroidGraphics";
    AndroidApplicationBase app;
    private BufferFormat bufferFormat;
    protected final AndroidApplicationConfiguration config;
    volatile boolean created;
    protected float deltaTime;
    private float density;
    volatile boolean destroy;
    EGLContext eglContext;
    static volatile boolean enforceContinuousRendering;
    String extensions;
    protected int fps;
    protected long frameId;
    protected long frameStart;
    protected int frames;
    GL20 gl20;
    GL30 gl30;
    int height;
    private boolean isContinuous;
    protected long lastFrameTime;
    protected WindowedMean mean;
    volatile boolean pause;
    private float ppcX;
    private float ppcY;
    private float ppiX;
    private float ppiY;
    volatile boolean resume;
    volatile boolean running;
    Object synch;
    int[] value;
    final View view;
    int width;

    static  {
        AndroidGraphics.enforceContinuousRendering = false;
    }

    public AndroidGraphics(AndroidApplicationBase application, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy) {
        this(application, config, resolutionStrategy, true);
    }

    public AndroidGraphics(AndroidApplicationBase application, AndroidApplicationConfiguration config, ResolutionStrategy resolutionStrategy, boolean focusableView) {
        super();
        this.lastFrameTime = System.nanoTime();
        this.deltaTime = 0f;
        this.frameStart = System.nanoTime();
        this.frameId = -1;
        this.frames = 0;
        this.mean = new WindowedMean(5);
        this.created = false;
        this.running = false;
        this.pause = false;
        this.resume = false;
        this.destroy = false;
        this.ppiX = 0f;
        this.ppiY = 0f;
        this.ppcX = 0f;
        this.ppcY = 0f;
        this.density = 1f;
        this.bufferFormat = new BufferFormat(5, 6, 5, 0, 16, 0, 0, false);
        this.isContinuous = true;
        this.value = new int[1];
        this.synch = new Object();
        this.config = config;
        this.app = application;
        this.view = this.createGLSurfaceView(application, resolutionStrategy);
        this.preserveEGLContextOnPause();
        if(focusableView) {
            this.view.setFocusable(true);
            this.view.setFocusableInTouchMode(true);
        }
    }

    protected boolean checkGL20() {
        boolean v4;
        EGL v0 = EGLContext.getEGL();
        EGLDisplay v1 = ((EGL10)v0).eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        ((EGL10)v0).eglInitialize(v1, new int[2]);
        int[] v2 = new int[9];
        v2[0] = 12324;
        v2[1] = 4;
        v2[2] = 12323;
        v2[3] = 4;
        v2[4] = 12322;
        v2[5] = 4;
        v2[6] = 12352;
        v2[7] = 4;
        v2[8] = 12344;
        EGLConfig[] v3 = new EGLConfig[10];
        int[] v5 = new int[1];
        ((EGL10)v0).eglChooseConfig(v1, v2, v3, 10, v5);
        ((EGL10)v0).eglTerminate(v1);
        if(v5[0] > 0) {
            v4 = true;
        }
        else {
            v4 = false;
        }

        return v4;
    }

    public void clearManagedCaches() {
        Mesh.clearAllMeshes(this.app);
        Texture.clearAllTextures(this.app);
        Cubemap.clearAllCubemaps(this.app);
        ShaderProgram.clearAllShaderPrograms(this.app);
        FrameBuffer.clearAllFrameBuffers(this.app);
        this.logManagedCachesStatus();
    }

    protected View createGLSurfaceView(AndroidApplicationBase application, ResolutionStrategy resolutionStrategy) {
        GLSurfaceView20API18 v0;
        if(!this.checkGL20()) {
            throw new GdxRuntimeException("Libgdx requires OpenGL ES 2.0");
        }

        GLSurfaceView$EGLConfigChooser v7 = this.getEglConfigChooser();
        if(Build$VERSION.SDK_INT > 10 || !this.config.useGLSurfaceView20API18) {
            GLSurfaceView20 v0_1 = new GLSurfaceView20(application.getContext(), resolutionStrategy);
            if(v7 != null) {
                v0_1.setEGLConfigChooser(v7);
            }
            else {
                v0_1.setEGLConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil);
            }

            v0_1.setRenderer(((GLSurfaceView$Renderer)this));
        }
        else {
            v0 = new GLSurfaceView20API18(application.getContext(), resolutionStrategy);
            if(v7 != null) {
                v0.setEGLConfigChooser(v7);
            }
            else {
                v0.setEGLConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil);
            }

            v0.setRenderer(((GLSurfaceView$Renderer)this));
        }

        return ((View)v0);
    }

    void destroy() {  // has try-catch handlers
        try {
            this.running = false;
            this.destroy = true;
            while(true) {
                if(!this.destroy) {
                    return;
                }

                try {
                    this.synch.wait();
                    continue;
                }
                catch(InterruptedException v0) {
                    try {
                        Gdx.app.log("AndroidGraphics", "waiting for destroy synchronization failed!");
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

    private int getAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attrib, int defValue) {
        if(egl.eglGetConfigAttrib(display, config, attrib, this.value)) {
            defValue = this.value[0];
        }

        return defValue;
    }

    public BufferFormat getBufferFormat() {
        return this.bufferFormat;
    }

    public float getDeltaTime() {
        float v0;
        if(this.mean.getMean() == 0f) {
            v0 = this.deltaTime;
        }
        else {
            v0 = this.mean.getMean();
        }

        return v0;
    }

    public float getDensity() {
        return this.density;
    }

    public DisplayMode getDesktopDisplayMode() {
        DisplayMetrics v6 = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(v6);
        return new AndroidDisplayMode(this, v6.widthPixels, v6.heightPixels, 0, 0);
    }

    public DisplayMode[] getDisplayModes() {
        DisplayMode[] v0 = new DisplayMode[1];
        v0[0] = this.getDesktopDisplayMode();
        return v0;
    }

    protected GLSurfaceView$EGLConfigChooser getEglConfigChooser() {
        return new GdxEglConfigChooser(this.config.r, this.config.g, this.config.b, this.config.a, this.config.depth, this.config.stencil, this.config.numSamples);
    }

    public long getFrameId() {
        return this.frameId;
    }

    public int getFramesPerSecond() {
        return this.fps;
    }

    public GL20 getGL20() {
        return this.gl20;
    }

    public GL30 getGL30() {
        return this.gl30;
    }

    public int getHeight() {
        return this.height;
    }

    public float getPpcX() {
        return this.ppcX;
    }

    public float getPpcY() {
        return this.ppcY;
    }

    public float getPpiX() {
        return this.ppiX;
    }

    public float getPpiY() {
        return this.ppiY;
    }

    public float getRawDeltaTime() {
        return this.deltaTime;
    }

    public GraphicsType getType() {
        return GraphicsType.AndroidGL;
    }

    public View getView() {
        return this.view;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isContinuousRendering() {
        return this.isContinuous;
    }

    public boolean isFullscreen() {
        return 1;
    }

    public boolean isGL30Available() {
        boolean v0;
        if(this.gl30 != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private void logConfig(EGLConfig config) {
        boolean v11;
        EGL v1 = EGLContext.getEGL();
        EGLDisplay v2 = ((EGL10)v1).eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        int v13 = this.getAttrib(((EGL10)v1), v2, config, 12324, 0);
        int v12 = this.getAttrib(((EGL10)v1), v2, config, 12323, 0);
        int v6 = this.getAttrib(((EGL10)v1), v2, config, 12322, 0);
        int v7 = this.getAttrib(((EGL10)v1), v2, config, 12321, 0);
        int v8 = this.getAttrib(((EGL10)v1), v2, config, 12325, 0);
        int v9 = this.getAttrib(((EGL10)v1), v2, config, 12326, 0);
        int v10 = Math.max(this.getAttrib(((EGL10)v1), v2, config, 12337, 0), this.getAttrib(((EGL10)v1), v2, config, 12513, 0));
        if(this.getAttrib(((EGL10)v1), v2, config, 12513, 0) != 0) {
            v11 = true;
        }
        else {
            v11 = false;
        }

        Gdx.app.log("AndroidGraphics", "framebuffer: (" + v13 + ", " + v12 + ", " + v6 + ", " + v7 + ")");
        Gdx.app.log("AndroidGraphics", "depthbuffer: (" + v8 + ")");
        Gdx.app.log("AndroidGraphics", "stencilbuffer: (" + v9 + ")");
        Gdx.app.log("AndroidGraphics", "samples: (" + v10 + ")");
        Gdx.app.log("AndroidGraphics", "coverage sampling: (" + v11 + ")");
        this.bufferFormat = new BufferFormat(v13, v12, v6, v7, v8, v9, v10, v11);
    }

    protected void logManagedCachesStatus() {
        Gdx.app.log("AndroidGraphics", Mesh.getManagedStatus());
        Gdx.app.log("AndroidGraphics", Texture.getManagedStatus());
        Gdx.app.log("AndroidGraphics", Cubemap.getManagedStatus());
        Gdx.app.log("AndroidGraphics", ShaderProgram.getManagedStatus());
        Gdx.app.log("AndroidGraphics", FrameBuffer.getManagedStatus());
    }

    public void onDrawFrame(GL10 gl) {  // has try-catch handlers
        int v2;
        AndroidGraphics v0;
        Iterator v3;
        long v12 = System.nanoTime();
        this.deltaTime = (((float)(v12 - this.lastFrameTime))) / 1000000000f;
        this.lastFrameTime = v12;
        if(!this.resume) {
            this.mean.addValue(this.deltaTime);
        }
        else {
            this.deltaTime = 0f;
        }

        try {
            boolean v9 = this.running;
            boolean v7 = this.pause;
            boolean v4 = this.destroy;
            boolean v8 = this.resume;
            if(this.resume) {
                this.resume = false;
            }

            if(this.pause) {
                this.pause = false;
                this.synch.notifyAll();
            }

            if(this.destroy) {
                this.destroy = false;
                this.synch.notifyAll();
            }

            if(!v8) {
                goto label_83;
            }
        }
        catch(Throwable v11) {
            try {
            label_74:
                throw v11;
            }
            catch(Throwable v11) {
                goto label_74;
            }
        }

        Array v6 = this.app.getLifecycleListeners();
        try {
            v3 = v6.iterator();
            while(v3.hasNext()) {
                v3.next().resume();
            }

            v0 = this;
        }
        catch(Throwable v11) {
            goto label_68;
        }

        v0.app.getApplicationListener().resume();
        Gdx.app.log("AndroidGraphics", "resumed");
        goto label_83;
        try {
        label_68:
            throw v11;
        }
        catch(Throwable v11) {
            goto label_68;
        }

    label_83:
        if(!v9) {
            goto label_134;
        }

        this.app.getRunnables();
        try {
            this.app.getExecutedRunnables().clear();
            this.app.getExecutedRunnables().addAll(this.app.getRunnables());
            this.app.getRunnables().clear();
            v2 = 0;
        }
        catch(Throwable v11) {
            try {
            label_116:
                throw v11;
            }
            catch(Throwable v11) {
                goto label_116;
            }
        }

        while(v2 < this.app.getExecutedRunnables().size) {
            try {
                this.app.getExecutedRunnables().get(v2).run();
            }
            catch(Throwable v10) {
                v10.printStackTrace();
            }

            ++v2;
        }

        this.app.getInput().processEvents();
        ++this.frameId;
        this.app.getApplicationListener().render();
    label_134:
        if(v7) {
            v6 = this.app.getLifecycleListeners();
            try {
                v3 = v6.iterator();
                while(v3.hasNext()) {
                    v3.next().pause();
                }

                v0 = this;
            }
            catch(Throwable v11) {
                goto label_145;
            }

            v0.app.getApplicationListener().pause();
            Gdx.app.log("AndroidGraphics", "paused");
            goto label_154;
            try {
            label_145:
                throw v11;
            }
            catch(Throwable v11) {
                goto label_145;
            }
        }

    label_154:
        if(v4) {
            v6 = this.app.getLifecycleListeners();
            try {
                v3 = v6.iterator();
                while(v3.hasNext()) {
                    v3.next().dispose();
                }

                v0 = this;
            }
            catch(Throwable v11) {
                goto label_165;
            }

            v0.app.getApplicationListener().dispose();
            Gdx.app.log("AndroidGraphics", "destroyed");
            goto label_174;
            try {
            label_165:
                throw v11;
            }
            catch(Throwable v11) {
                goto label_165;
            }
        }

    label_174:
        if(v12 - this.frameStart > 1000000000) {
            this.fps = this.frames;
            this.frames = 0;
            this.frameStart = v12;
        }

        ++this.frames;
    }

    public void onPauseGLSurfaceView() {
        if(this.view != null) {
            if((this.view instanceof GLSurfaceViewAPI18)) {
                this.view.onPause();
            }

            if(!(this.view instanceof GLSurfaceView)) {
                return;
            }

            this.view.onPause();
        }
    }

    public void onResumeGLSurfaceView() {
        if(this.view != null) {
            if((this.view instanceof GLSurfaceViewAPI18)) {
                this.view.onResume();
            }

            if(!(this.view instanceof GLSurfaceView)) {
                return;
            }

            this.view.onResume();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {  // has try-catch handlers
        AndroidApplicationBase v0_1;
        this.width = width;
        this.height = height;
        this.updatePpi();
        gl.glViewport(0, 0, this.width, this.height);
        if(!this.created) {
            this.app.getApplicationListener().create();
            this.created = true;
            try {
                this.running = true;
            label_16:
                v0_1 = this.app;
                goto label_17;
            }
            catch(Throwable v0) {
                try {
                label_21:
                    throw v0;
                }
                catch(Throwable v0) {
                    goto label_21;
                }
            }
        }

        goto label_16;
    label_17:
        v0_1.getApplicationListener().resize(width, height);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.eglContext = EGLContext.getEGL().eglGetCurrentContext();
        this.setupGL(gl);
        this.logConfig(config);
        this.updatePpi();
        Mesh.invalidateAllMeshes(this.app);
        Texture.invalidateAllTextures(this.app);
        Cubemap.invalidateAllCubemaps(this.app);
        ShaderProgram.invalidateAllShaderPrograms(this.app);
        FrameBuffer.invalidateAllFrameBuffers(this.app);
        this.logManagedCachesStatus();
        Display v0 = this.app.getWindowManager().getDefaultDisplay();
        this.width = v0.getWidth();
        this.height = v0.getHeight();
        this.mean = new WindowedMean(5);
        this.lastFrameTime = System.nanoTime();
        gl.glViewport(0, 0, this.width, this.height);
    }

    void pause() {  // has try-catch handlers
        try {
            if(this.running) {
                goto label_4;
            }

            return;
        label_4:
            this.running = false;
            this.pause = true;
            while(true) {
                if(!this.pause) {
                    return;
                }

                try {
                    this.synch.wait(4000);
                    if(!this.pause) {
                        continue;
                    }

                    Gdx.app.error("AndroidGraphics", "waiting for pause synchronization took too long; assuming deadlock and killing");
                    Process.killProcess(Process.myPid());
                    continue;
                }
                catch(InterruptedException v0) {
                    try {
                        Gdx.app.log("AndroidGraphics", "waiting for pause synchronization failed!");
                        continue;
                        return;
                    label_29:
                        throw v1;
                    }
                    catch(Throwable v1) {
                        goto label_29;
                    }
                }
            }
        }
        catch(Throwable v1) {
            goto label_29;
        }
    }

    protected void preserveEGLContextOnPause() {  // has try-catch handlers
        if(Build$VERSION.SDK_INT < 11 || !(this.view instanceof GLSurfaceView20)) {
            if((this.view instanceof GLSurfaceView20API18)) {
                goto label_9;
            }

            return;
        }

        try {
        label_9:
            Class v2 = this.view.getClass();
            Method v2_1 = v2.getMethod("setPreserveEGLContextOnPause", Boolean.TYPE);
            View v3 = this.view;
            v2_1.invoke(v3, Boolean.valueOf(true));
        }
        catch(Exception v0) {
            Gdx.app.log("AndroidGraphics", "Method GLSurfaceView.setPreserveEGLContextOnPause not found");
        }
    }

    public void requestRendering() {
        if(this.view != null) {
            if((this.view instanceof GLSurfaceViewAPI18)) {
                this.view.requestRender();
            }

            if(!(this.view instanceof GLSurfaceView)) {
                return;
            }

            this.view.requestRender();
        }
    }

    void resume() {  // has try-catch handlers
        try {
            this.running = true;
            this.resume = true;
            return;
        label_7:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_7;
        }
    }

    public void setContinuousRendering(boolean isContinuous) {
        int v0;
        boolean v1;
        if(this.view != null) {
            if((AndroidGraphics.enforceContinuousRendering) || (isContinuous)) {
                v1 = true;
            }
            else {
                v1 = false;
            }

            this.isContinuous = v1;
            if(this.isContinuous) {
                v0 = 1;
            }
            else {
                v0 = 0;
            }

            if((this.view instanceof GLSurfaceViewAPI18)) {
                this.view.setRenderMode(v0);
            }

            if((this.view instanceof GLSurfaceView)) {
                this.view.setRenderMode(v0);
            }

            this.mean.clear();
        }
    }

    public boolean setDisplayMode(int width, int height, boolean fullscreen) {
        return 0;
    }

    public boolean setDisplayMode(DisplayMode displayMode) {
        return 0;
    }

    public void setTitle(String title) {
    }

    public void setVSync(boolean vsync) {
    }

    private void setupGL(GL10 gl) {
        if(this.gl20 == null) {
            this.gl20 = new AndroidGL20();
            Gdx.gl = this.gl20;
            Gdx.gl20 = this.gl20;
            Gdx.app.log("AndroidGraphics", "OGL renderer: " + gl.glGetString(7937));
            Gdx.app.log("AndroidGraphics", "OGL vendor: " + gl.glGetString(7936));
            Gdx.app.log("AndroidGraphics", "OGL version: " + gl.glGetString(7938));
            Gdx.app.log("AndroidGraphics", "OGL extensions: " + gl.glGetString(7939));
        }
    }

    public boolean supportsDisplayModeChange() {
        return 0;
    }

    public boolean supportsExtension(String extension) {
        if(this.extensions == null) {
            this.extensions = Gdx.gl.glGetString(7939);
        }

        return this.extensions.contains(((CharSequence)extension));
    }

    private void updatePpi() {
        DisplayMetrics v0 = new DisplayMetrics();
        this.app.getWindowManager().getDefaultDisplay().getMetrics(v0);
        this.ppiX = v0.xdpi;
        this.ppiY = v0.ydpi;
        this.ppcX = v0.xdpi / 2.54f;
        this.ppcY = v0.ydpi / 2.54f;
        this.density = v0.density;
    }
}

