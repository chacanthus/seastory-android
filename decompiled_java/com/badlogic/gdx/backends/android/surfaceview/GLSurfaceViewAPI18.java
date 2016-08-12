// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android.surfaceview;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView$EGLConfigChooser;
import android.opengl.GLSurfaceView$Renderer;
import android.os.Build$VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder$Callback;
import android.view.SurfaceView;
import java.io.Writer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLSurfaceViewAPI18 extends SurfaceView implements SurfaceHolder$Callback {
    abstract class BaseConfigChooser implements GLSurfaceView$EGLConfigChooser {
        protected int[] mConfigSpec;

        public BaseConfigChooser(GLSurfaceViewAPI18 arg2, int[] configSpec) {
            GLSurfaceViewAPI18.this = arg2;
            super();
            this.mConfigSpec = this.filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] v5 = new int[1];
            if(!egl.eglChooseConfig(display, this.mConfigSpec, null, 0, v5)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }

            int v4 = v5[0];
            if(v4 <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

            EGLConfig[] v3 = new EGLConfig[v4];
            if(!egl.eglChooseConfig(display, this.mConfigSpec, v3, v4, v5)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }

            EGLConfig v6 = this.chooseConfig(egl, display, v3);
            if(v6 == null) {
                throw new IllegalArgumentException("No config chosen");
            }

            return v6;
        }

        abstract EGLConfig chooseConfig(EGL10 arg0, EGLDisplay arg1, EGLConfig[] arg2);

        private int[] filterConfigSpec(int[] configSpec) {
            if(GLSurfaceViewAPI18.this.mEGLContextClientVersion == 2) {
                int v0 = configSpec.length;
                int[] v1 = new int[v0 + 2];
                System.arraycopy(configSpec, 0, v1, 0, v0 - 1);
                v1[v0 - 1] = 12352;
                v1[v0] = 4;
                v1[v0 + 1] = 12344;
                configSpec = v1;
            }

            return configSpec;
        }
    }

    class ComponentSizeChooser extends BaseConfigChooser {
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;

        public ComponentSizeChooser(GLSurfaceViewAPI18 arg5, int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            GLSurfaceViewAPI18.this = arg5;
            int[] v0 = new int[13];
            v0[0] = 12324;
            v0[1] = redSize;
            v0[2] = 12323;
            v0[3] = greenSize;
            v0[4] = 12322;
            v0[5] = blueSize;
            v0[6] = 12321;
            v0[7] = alphaSize;
            v0[8] = 12325;
            v0[9] = depthSize;
            v0[10] = 12326;
            v0[11] = stencilSize;
            v0[12] = 12344;
            super(arg5, v0);
            this.mValue = new int[1];
            this.mRedSize = redSize;
            this.mGreenSize = greenSize;
            this.mBlueSize = blueSize;
            this.mAlphaSize = alphaSize;
            this.mDepthSize = depthSize;
            this.mStencilSize = stencilSize;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            EGLConfig v3;
            EGLConfig[] v7 = configs;
            int v12 = v7.length;
            int v11;
            for(v11 = 0; v11 < v12; ++v11) {
                v3 = v7[v11];
                int v9 = this.findConfigAttrib(egl, display, v3, 12325, 0);
                int v14 = this.findConfigAttrib(egl, display, v3, 12326, 0);
                if(v9 >= this.mDepthSize && v14 >= this.mStencilSize) {
                    int v13 = this.findConfigAttrib(egl, display, v3, 12324, 0);
                    int v10 = this.findConfigAttrib(egl, display, v3, 12323, 0);
                    int v8 = this.findConfigAttrib(egl, display, v3, 12322, 0);
                    int v6 = this.findConfigAttrib(egl, display, v3, 12321, 0);
                    if(v13 == this.mRedSize && v10 == this.mGreenSize && v8 == this.mBlueSize) {
                        if(v6 != this.mAlphaSize) {
                            goto label_54;
                        }

                        goto label_53;
                    }
                }

            label_54:
            }

            v3 = null;
        label_53:
            return v3;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if(egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                defaultValue = this.mValue[0];
            }

            return defaultValue;
        }
    }

    class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        DefaultContextFactory(GLSurfaceViewAPI18 x0, com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18$1 x1) {
            super(x0);
        }

        private DefaultContextFactory(GLSurfaceViewAPI18 arg2) {
            GLSurfaceViewAPI18.this = arg2;
            super();
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] v0 = new int[3];
            v0[0] = this.EGL_CONTEXT_CLIENT_VERSION;
            v0[1] = GLSurfaceViewAPI18.this.mEGLContextClientVersion;
            v0[2] = 12344;
            EGLContext v1 = EGL10.EGL_NO_CONTEXT;
            if(GLSurfaceViewAPI18.this.mEGLContextClientVersion == 0) {
                v0 = null;
            }

            return egl.eglCreateContext(display, config, v1, v0);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if(!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
                EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
            }
        }
    }

    class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        DefaultWindowSurfaceFactory(com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18$1 x0) {
            super();
        }

        private DefaultWindowSurfaceFactory() {
            super();
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {  // has try-catch handlers
            EGLSurface v1;
            int[] v2 = null;
            try {
                v1 = egl.eglCreateWindowSurface(display, config, nativeWindow, v2);
            }
            catch(IllegalArgumentException v0) {
                Log.e("GLSurfaceViewAPI18", "eglCreateWindowSurface", ((Throwable)v0));
            }

            return v1;
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    public abstract interface EGLContextFactory {
        public abstract EGLContext createContext(EGL10 arg0, EGLDisplay arg1, EGLConfig arg2);

        public abstract void destroyContext(EGL10 arg0, EGLDisplay arg1, EGLContext arg2);
    }

    public abstract interface EGLWindowSurfaceFactory {
        public abstract EGLSurface createWindowSurface(EGL10 arg0, EGLDisplay arg1, EGLConfig arg2, Object arg3);

        public abstract void destroySurface(EGL10 arg0, EGLDisplay arg1, EGLSurface arg2);
    }

    class EglHelper {
        EGL10 mEgl;
        EGLConfig mEglConfig;
        EGLContext mEglContext;
        EGLDisplay mEglDisplay;
        EGLSurface mEglSurface;
        private WeakReference mGLSurfaceViewWeakRef;

        public EglHelper(WeakReference arg1) {
            super();
            this.mGLSurfaceViewWeakRef = arg1;
        }

        GL createGL() {
            LogWriter v2_1;
            GL v1 = this.mEglContext.getGL();
            Object v3 = this.mGLSurfaceViewWeakRef.get();
            if(v3 != null) {
                if(((GLSurfaceViewAPI18)v3).mGLWrapper != null) {
                    v1 = ((GLSurfaceViewAPI18)v3).mGLWrapper.wrap(v1);
                }

                if((((GLSurfaceViewAPI18)v3).mDebugFlags & 3) == 0) {
                    goto label_23;
                }

                int v0 = 0;
                Writer v2 = null;
                if((((GLSurfaceViewAPI18)v3).mDebugFlags & 1) != 0) {
                    v0 = 1;
                }

                if((((GLSurfaceViewAPI18)v3).mDebugFlags & 2) != 0) {
                    v2_1 = new LogWriter();
                }

                v1 = GLDebugHelper.wrap(v1, v0, ((Writer)v2_1));
            }

        label_23:
            return v1;
        }

        public boolean createSurface() {
            boolean v2 = false;
            if(this.mEgl == null) {
                throw new RuntimeException("egl not initialized");
            }

            if(this.mEglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }

            if(this.mEglConfig == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }

            this.destroySurfaceImp();
            Object v1 = this.mGLSurfaceViewWeakRef.get();
            if(v1 != null) {
                this.mEglSurface = ((GLSurfaceViewAPI18)v1).mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, ((GLSurfaceViewAPI18)v1).getHolder());
            }
            else {
                this.mEglSurface = null;
            }

            if(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
                if(this.mEgl.eglGetError() == 12299) {
                    Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
            }
            else if(!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
                EglHelper.logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
            }
            else {
                v2 = true;
            }

            return v2;
        }

        public void destroySurface() {
            this.destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if(this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
                this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                Object v0 = this.mGLSurfaceViewWeakRef.get();
                if(v0 != null) {
                    ((GLSurfaceViewAPI18)v0).mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
                }

                this.mEglSurface = null;
            }
        }

        public void finish() {
            EGLContext v5 = null;
            if(this.mEglContext != null) {
                Object v0 = this.mGLSurfaceViewWeakRef.get();
                if(v0 != null) {
                    ((GLSurfaceViewAPI18)v0).mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
                }

                this.mEglContext = v5;
            }

            if(this.mEglDisplay != null) {
                this.mEgl.eglTerminate(this.mEglDisplay);
                this.mEglDisplay = ((EGLDisplay)v5);
            }
        }

        public static String formatEglError(String function, int error) {
            return function + " failed: " + EglHelper.getErrorString(error);
        }

        private static String getErrorString(int error) {
            String v0;
            switch(error) {
                case 12288: {
                    v0 = "EGL_SUCCESS";
                    break;
                }
                case 12289: {
                    v0 = "EGL_NOT_INITIALIZED";
                    break;
                }
                case 12290: {
                    v0 = "EGL_BAD_ACCESS";
                    break;
                }
                case 12291: {
                    v0 = "EGL_BAD_ALLOC";
                    break;
                }
                case 12292: {
                    v0 = "EGL_BAD_ATTRIBUTE";
                    break;
                }
                case 12293: {
                    v0 = "EGL_BAD_CONFIG";
                    break;
                }
                case 12294: {
                    v0 = "EGL_BAD_CONTEXT";
                    break;
                }
                case 12295: {
                    v0 = "EGL_BAD_CURRENT_SURFACE";
                    break;
                }
                case 12296: {
                    v0 = "EGL_BAD_DISPLAY";
                    break;
                }
                case 12297: {
                    v0 = "EGL_BAD_MATCH";
                    break;
                }
                case 12298: {
                    v0 = "EGL_BAD_NATIVE_PIXMAP";
                    break;
                }
                case 12299: {
                    v0 = "EGL_BAD_NATIVE_WINDOW";
                    break;
                }
                case 12300: {
                    v0 = "EGL_BAD_PARAMETER";
                    break;
                }
                case 12301: {
                    v0 = "EGL_BAD_SURFACE";
                    break;
                }
                case 12302: {
                    v0 = "EGL_CONTEXT_LOST";
                    break;
                }
                default: {
                    v0 = "0x" + Integer.toHexString(error);
                    break;
                }
            }

            return v0;
        }

        public static void logEglErrorAsWarning(String tag, String function, int error) {
            Log.w(tag, EglHelper.formatEglError(function, error));
        }

        public void start() {
            EGLConfig v6 = null;
            this.mEgl = EGLContext.getEGL();
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if(this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }

            if(!this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }

            Object v1 = this.mGLSurfaceViewWeakRef.get();
            if(v1 == null) {
                this.mEglConfig = v6;
                this.mEglContext = ((EGLContext)v6);
            }
            else {
                this.mEglConfig = ((GLSurfaceViewAPI18)v1).mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
                this.mEglContext = ((GLSurfaceViewAPI18)v1).mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            }

            if(this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                this.mEglContext = ((EGLContext)v6);
                this.throwEglException("createContext");
            }

            this.mEglSurface = ((EGLSurface)v6);
        }

        public int swap() {
            int v0;
            if(!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
                v0 = this.mEgl.eglGetError();
            }
            else {
                v0 = 12288;
            }

            return v0;
        }

        private void throwEglException(String function) {
            EglHelper.throwEglException(function, this.mEgl.eglGetError());
        }

        public static void throwEglException(String function, int error) {
            throw new RuntimeException(EglHelper.formatEglError(function, error));
        }
    }

    class GLThread extends Thread {
        private EglHelper mEglHelper;
        private ArrayList mEventQueue;
        private boolean mExited;
        private boolean mFinishedCreatingEglSurface;
        private WeakReference mGLSurfaceViewWeakRef;
        private boolean mHasSurface;
        private boolean mHaveEglContext;
        private boolean mHaveEglSurface;
        private int mHeight;
        private boolean mPaused;
        private boolean mRenderComplete;
        private int mRenderMode;
        private boolean mRequestPaused;
        private boolean mRequestRender;
        private boolean mShouldExit;
        private boolean mShouldReleaseEglContext;
        private boolean mSizeChanged;
        private boolean mSurfaceIsBad;
        private boolean mWaitingForSurface;
        private int mWidth;

        GLThread(WeakReference arg4) {
            super();
            this.mEventQueue = new ArrayList();
            this.mSizeChanged = true;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mRequestRender = true;
            this.mRenderMode = 1;
            this.mGLSurfaceViewWeakRef = arg4;
        }

        public boolean ableToDraw() {
            boolean v0;
            if(!this.mHaveEglContext || !this.mHaveEglSurface || !this.readyToDraw()) {
                v0 = false;
            }
            else {
                v0 = true;
            }

            return v0;
        }

        static boolean access$1102(GLThread x0, boolean x1) {
            x0.mExited = x1;
            return x1;
        }

        public int getRenderMode() {  // has try-catch handlers
            try {
                return this.mRenderMode;
            label_4:
                throw v0;
            }
            catch(Throwable v0) {
                goto label_4;
            }
        }

        private void guardedRun() throws InterruptedException {  // has try-catch handlers
            boolean v12;
            Object v16;
            this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
            this.mHaveEglContext = false;
            this.mHaveEglSurface = false;
            GL10 v8 = null;
            int v3 = 0;
            int v4 = 0;
            int v5 = 0;
            int v10 = 0;
            int v13 = 0;
            int v18 = 0;
            int v6 = 0;
            int v2 = 0;
            int v17 = 0;
            int v9 = 0;
            Object v7 = null;
            try {
                while(true) {
                label_27:
                    break;
                }
            }
            catch(Throwable v19) {
                goto label_220;
            }

            while(true) {
                GLThread v0 = this;
                try {
                    if(!v0.mShouldExit) {
                        goto label_38;
                    }

                    goto label_33;
                }
                catch(Throwable v19) {
                    goto label_218;
                }

                try {
                label_33:
                    this.stopEglSurfaceLocked();
                    this.stopEglContextLocked();
                    return;
                }
                catch(Throwable v19) {
                    goto label_37;
                }

                try {
                label_38:
                    if(!this.mEventQueue.isEmpty()) {
                        v7 = this.mEventQueue.remove(0);
                    }
                    else {
                        goto label_56;
                    }

                    goto label_52;
                }
                catch(Throwable v19) {
                    goto label_218;
                }

            label_56:
                boolean v11 = false;
                try {
                    if(this.mPaused != this.mRequestPaused) {
                        v11 = this.mRequestPaused;
                        this.mPaused = this.mRequestPaused;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    }

                    if(this.mShouldReleaseEglContext) {
                        this.stopEglSurfaceLocked();
                        this.stopEglContextLocked();
                        this.mShouldReleaseEglContext = false;
                        v2 = 1;
                    }

                    if(v10 != 0) {
                        this.stopEglSurfaceLocked();
                        this.stopEglContextLocked();
                        v10 = 0;
                    }

                    if((v11) && (this.mHaveEglSurface)) {
                        this.stopEglSurfaceLocked();
                    }

                    if((v11) && (this.mHaveEglContext)) {
                        v16 = this.mGLSurfaceViewWeakRef.get();
                        if(v16 == null) {
                            v12 = false;
                        }
                        else {
                            v12 = ((GLSurfaceViewAPI18)v16).mPreserveEGLContextOnPause;
                        }

                        if((v12) && !GLSurfaceViewAPI18.sGLThreadManager.shouldReleaseEGLContextWhenPausing()) {
                            goto label_113;
                        }

                        this.stopEglContextLocked();
                    }

                label_113:
                    if((v11) && (GLSurfaceViewAPI18.sGLThreadManager.shouldTerminateEGLWhenPausing())) {
                        this.mEglHelper.finish();
                    }

                    if(!this.mHasSurface && !this.mWaitingForSurface) {
                        if(this.mHaveEglSurface) {
                            this.stopEglSurfaceLocked();
                        }

                        this.mWaitingForSurface = true;
                        this.mSurfaceIsBad = false;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    }

                    if((this.mHasSurface) && (this.mWaitingForSurface)) {
                        this.mWaitingForSurface = false;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    }

                    if(v6 != 0) {
                        v18 = 0;
                        v6 = 0;
                        this.mRenderComplete = true;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    }

                    if(this.readyToDraw()) {
                        if(!this.mHaveEglContext) {
                            if(v2 != 0) {
                                v2 = 0;
                            }
                            else if(GLSurfaceViewAPI18.sGLThreadManager.tryAcquireEglContextLocked(this)) {
                                goto label_231;
                            }
                        }

                        goto label_175;
                    }

                    goto label_249;
                }
                catch(Throwable v19) {
                    goto label_218;
                }

                try {
                label_231:
                    this.mEglHelper.start();
                    goto label_236;
                }
                catch(Throwable v19) {
                    try {
                    label_218:
                        throw v19;
                    }
                    catch(Throwable v19) {
                        goto label_218;
                    }
                    catch(Throwable v19) {
                        break;
                    }
                }
                catch(RuntimeException v15) {
                    try {
                        GLSurfaceViewAPI18.sGLThreadManager.releaseEglContextLocked(this);
                        throw v15;
                    label_236:
                        this.mHaveEglContext = true;
                        v3 = 1;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    label_175:
                        if((this.mHaveEglContext) && !this.mHaveEglSurface) {
                            this.mHaveEglSurface = true;
                            v4 = 1;
                            v5 = 1;
                            v13 = 1;
                        }

                        if(!this.mHaveEglSurface) {
                            goto label_249;
                        }

                        if(this.mSizeChanged) {
                            v13 = 1;
                            v17 = this.mWidth;
                            v9 = this.mHeight;
                            v18 = 1;
                            v4 = 1;
                            this.mSizeChanged = false;
                        }

                        this.mRequestRender = false;
                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    label_52:
                        if(v7 == null) {
                            goto label_252;
                        }

                        goto label_53;
                    }
                    catch(Throwable v19) {
                        goto label_218;
                    }

                    try {
                    label_53:
                        ((Runnable)v7).run();
                        v7 = null;
                        goto label_27;
                    }
                    catch(Throwable v19) {
                        break;
                    }

                label_252:
                    if(v4 != 0) {
                        try {
                            if(this.mEglHelper.createSurface()) {
                                goto label_260;
                            }
                            else {
                                goto label_335;
                            }
                        }
                        catch(Throwable v19) {
                            break;
                        }

                        try {
                        label_260:
                            this.mFinishedCreatingEglSurface = true;
                            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            v4 = 0;
                            goto label_266;
                        }
                        catch(Throwable v19) {
                            goto label_334;
                        }

                        try {
                        label_335:
                            goto label_337;
                        }
                        catch(Throwable v19) {
                            break;
                        }

                        try {
                        label_337:
                            this.mFinishedCreatingEglSurface = true;
                            this.mSurfaceIsBad = true;
                            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                            goto label_27;
                        }
                        catch(Throwable v19) {
                            goto label_348;
                        }
                    }

                label_266:
                    if(v5 != 0) {
                        try {
                            GL v8_1 = this.mEglHelper.createGL();
                            GLSurfaceViewAPI18.sGLThreadManager.checkGLDriver(((GL10)v8_1));
                            v5 = 0;
                        label_277:
                            if(v3 != 0) {
                                v16 = this.mGLSurfaceViewWeakRef.get();
                                if(v16 != null) {
                                    ((GLSurfaceViewAPI18)v16).mRenderer.onSurfaceCreated(((GL10)v8_1), this.mEglHelper.mEglConfig);
                                }

                                v3 = 0;
                            }

                            if(v13 != 0) {
                                v16 = this.mGLSurfaceViewWeakRef.get();
                                if(v16 != null) {
                                    ((GLSurfaceViewAPI18)v16).mRenderer.onSurfaceChanged(((GL10)v8_1), v17, v9);
                                }

                                v13 = 0;
                            }

                            v16 = this.mGLSurfaceViewWeakRef.get();
                            if(v16 != null) {
                                ((GLSurfaceViewAPI18)v16).mRenderer.onDrawFrame(((GL10)v8_1));
                            }

                            int v14 = this.mEglHelper.swap();
                            switch(v14) {
                                case 12288: {
                                    goto label_330;
                                }
                                case 12302: {
                                    v10 = 1;
                                    goto label_330;
                                }
                                default: {
                                    EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", v14);
                                    try {
                                        this.mSurfaceIsBad = true;
                                        GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                                        goto label_330;
                                    }
                                    catch(Throwable v19) {
                                        goto label_352;
                                    }
                                }
                            }
                        }
                        catch(Throwable v19) {
                            break;
                        }
                    }

                    goto label_277;
                label_330:
                    if(v18 == 0) {
                        goto label_27;
                    }

                    v6 = 1;
                    goto label_27;
                    try {
                    label_249:
                        GLSurfaceViewAPI18.sGLThreadManager.wait();
                        continue;
                    }
                    catch(Throwable v19) {
                        goto label_218;
                    }

                    try {
                    label_348:
                        throw v19;
                    }
                    catch(Throwable v19) {
                        goto label_348;
                    }
                    catch(Throwable v19) {
                        break;
                    }

                    try {
                    label_352:
                        throw v19;
                    }
                    catch(Throwable v19) {
                        goto label_352;
                    }
                    catch(Throwable v19) {
                        break;
                    }

                    try {
                    label_334:
                        throw v19;
                    }
                    catch(Throwable v19) {
                        goto label_334;
                    }
                    catch(Throwable v19) {
                        break;
                    }

                    try {
                    label_37:
                        throw v19;
                    }
                    catch(Throwable v19) {
                        goto label_37;
                    }
                }
            }

        label_220:
            try {
                this.stopEglSurfaceLocked();
                this.stopEglContextLocked();
                throw v19;
            }
            catch(Throwable v19) {
                try {
                label_354:
                    throw v19;
                }
                catch(Throwable v19) {
                    goto label_354;
                }
            }
        }

        public void onPause() {  // has try-catch handlers
            try {
                this.mRequestPaused = true;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if(!this.mExited && !this.mPaused) {
                        try {
                            GLSurfaceViewAPI18.access$800().wait();
                            continue;
                        }
                        catch(InterruptedException v0) {
                            try {
                                Thread.currentThread().interrupt();
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

                    return;
                }
            }
            catch(Throwable v1) {
                goto label_17;
            }
        }

        public void onResume() {  // has try-catch handlers
            try {
                this.mRequestPaused = false;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if(!this.mExited && (this.mPaused) && !this.mRenderComplete) {
                        try {
                            GLSurfaceViewAPI18.access$800().wait();
                            continue;
                        }
                        catch(InterruptedException v0) {
                            try {
                                Thread.currentThread().interrupt();
                                continue;
                                return;
                            label_23:
                                throw v1;
                            }
                            catch(Throwable v1) {
                                goto label_23;
                            }
                        }
                    }

                    return;
                }
            }
            catch(Throwable v1) {
                goto label_23;
            }
        }

        public void onWindowResize(int w, int h) {  // has try-catch handlers
            try {
                this.mWidth = w;
                this.mHeight = h;
                this.mSizeChanged = true;
                this.mRequestRender = true;
                this.mRenderComplete = false;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if(!this.mExited && !this.mPaused && !this.mRenderComplete && (this.ableToDraw())) {
                        try {
                            GLSurfaceViewAPI18.access$800().wait();
                            continue;
                        }
                        catch(InterruptedException v0) {
                            try {
                                Thread.currentThread().interrupt();
                                continue;
                                return;
                            label_27:
                                throw v1;
                            }
                            catch(Throwable v1) {
                                goto label_27;
                            }
                        }
                    }

                    return;
                }
            }
            catch(Throwable v1) {
                goto label_27;
            }
        }

        public void queueEvent(Runnable r) {  // has try-catch handlers
            if(r == null) {
                throw new IllegalArgumentException("r must not be null");
            }

            try {
                this.mEventQueue.add(r);
                GLSurfaceViewAPI18.access$800().notifyAll();
                return;
            label_11:
                throw v0;
            }
            catch(Throwable v0) {
                goto label_11;
            }
        }

        private boolean readyToDraw() {
            boolean v0 = true;
            if((this.mPaused) || !this.mHasSurface || (this.mSurfaceIsBad) || this.mWidth <= 0 || this.mHeight <= 0 || !this.mRequestRender && this.mRenderMode != 1) {
                v0 = false;
            }

            return v0;
        }

        public void requestExitAndWait() {  // has try-catch handlers
            try {
                this.mShouldExit = true;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if(this.mExited) {
                        return;
                    }

                    try {
                        GLSurfaceViewAPI18.access$800().wait();
                        continue;
                    }
                    catch(InterruptedException v0) {
                        try {
                            Thread.currentThread().interrupt();
                            continue;
                            return;
                        label_15:
                            throw v1;
                        }
                        catch(Throwable v1) {
                            goto label_15;
                        }
                    }
                }
            }
            catch(Throwable v1) {
                goto label_15;
            }
        }

        public void requestReleaseEglContextLocked() {
            this.mShouldReleaseEglContext = true;
            GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
        }

        public void requestRender() {  // has try-catch handlers
            try {
                this.mRequestRender = true;
                GLSurfaceViewAPI18.access$800().notifyAll();
                return;
            label_7:
                throw v0;
            }
            catch(Throwable v0) {
                goto label_7;
            }
        }

        public void run() {  // has try-catch handlers
            this.setName("GLThread " + this.getId());
            try {
                this.guardedRun();
            }
            catch(Throwable v0) {
                GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
                throw v0;
            }
            catch(InterruptedException v0_1) {
                GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
                return;
            }

            GLSurfaceViewAPI18.sGLThreadManager.threadExiting(this);
        }

        public void setRenderMode(int renderMode) {  // has try-catch handlers
            if(renderMode >= 0 && renderMode <= 1) {
                try {
                    this.mRenderMode = renderMode;
                    GLSurfaceViewAPI18.sGLThreadManager.notifyAll();
                    return;
                label_12:
                    throw v0;
                }
                catch(Throwable v0) {
                    goto label_12;
                }
            }

            throw new IllegalArgumentException("renderMode");
        }

        private void stopEglContextLocked() {
            if(this.mHaveEglContext) {
                this.mEglHelper.finish();
                this.mHaveEglContext = false;
                GLSurfaceViewAPI18.sGLThreadManager.releaseEglContextLocked(this);
            }
        }

        private void stopEglSurfaceLocked() {
            if(this.mHaveEglSurface) {
                this.mHaveEglSurface = false;
                this.mEglHelper.destroySurface();
            }
        }

        public void surfaceCreated() {  // has try-catch handlers
            try {
                this.mHasSurface = true;
                this.mFinishedCreatingEglSurface = false;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if((this.mWaitingForSurface) && !this.mFinishedCreatingEglSurface && !this.mExited) {
                        try {
                            GLSurfaceViewAPI18.access$800().wait();
                            continue;
                        }
                        catch(InterruptedException v0) {
                            try {
                                Thread.currentThread().interrupt();
                                continue;
                                return;
                            label_21:
                                throw v1;
                            }
                            catch(Throwable v1) {
                                goto label_21;
                            }
                        }
                    }

                    return;
                }
            }
            catch(Throwable v1) {
                goto label_21;
            }
        }

        public void surfaceDestroyed() {  // has try-catch handlers
            try {
                this.mHasSurface = false;
                GLSurfaceViewAPI18.access$800().notifyAll();
                while(true) {
                    if(!this.mWaitingForSurface && !this.mExited) {
                        try {
                            GLSurfaceViewAPI18.access$800().wait();
                            continue;
                        }
                        catch(InterruptedException v0) {
                            try {
                                Thread.currentThread().interrupt();
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

                    return;
                }
            }
            catch(Throwable v1) {
                goto label_17;
            }
        }
    }

    class GLThreadManager {
        private static String TAG = null;
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread mEglOwner;
        private boolean mGLESDriverCheckComplete;
        private int mGLESVersion;
        private boolean mGLESVersionCheckComplete;
        private boolean mLimitedGLESContexts;
        private boolean mMultipleGLESContextsAllowed;

        static  {
            GLThreadManager.TAG = "GLThreadManager";
        }

        GLThreadManager(com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18$1 x0) {
            super();
        }

        private GLThreadManager() {
            super();
        }

        public void checkGLDriver(GL10 gl) {  // has try-catch handlers
            boolean v3;
            boolean v1 = true;
            try {
                if(!this.mGLESDriverCheckComplete) {
                    this.checkGLESVersion();
                    String v0 = gl.glGetString(7937);
                    if(this.mGLESVersion < 131072) {
                        if(!v0.startsWith("Q3Dimension MSM7500 ")) {
                            v3 = true;
                        }
                        else {
                            goto label_22;
                        }

                        goto label_14;
                    }

                    goto label_16;
                }

                return;
            }
            catch(Throwable v1_1) {
                goto label_27;
            }

        label_22:
            v3 = false;
            try {
            label_14:
                this.mMultipleGLESContextsAllowed = v3;
                this.notifyAll();
            label_16:
                if(this.mMultipleGLESContextsAllowed) {
                    goto label_24;
                }

                goto label_18;
            }
            catch(Throwable v1_1) {
                goto label_27;
            }

        label_24:
            v1 = false;
            try {
            label_18:
                this.mLimitedGLESContexts = v1;
                this.mGLESDriverCheckComplete = true;
            }
            catch(Throwable v1_1) {
            label_27:
                throw v1_1;
            }
        }

        private void checkGLESVersion() {
            int v2 = 131072;
            if(!this.mGLESVersionCheckComplete) {
                this.mGLESVersion = v2;
                if(this.mGLESVersion >= v2) {
                    this.mMultipleGLESContextsAllowed = true;
                }

                this.mGLESVersionCheckComplete = true;
            }
        }

        public void releaseEglContextLocked(GLThread thread) {
            if(this.mEglOwner == thread) {
                this.mEglOwner = null;
            }

            this.notifyAll();
        }

        public boolean shouldReleaseEGLContextWhenPausing() {  // has try-catch handlers
            try {
                return this.mLimitedGLESContexts;
            }
            catch(Throwable v0) {
                throw v0;
            }
        }

        public boolean shouldTerminateEGLWhenPausing() {  // has try-catch handlers
            try {
                this.checkGLESVersion();
                if(this.mMultipleGLESContextsAllowed) {
                    goto label_5;
                }
            }
            catch(Throwable v0) {
                throw v0;
            }

            boolean v0_1 = true;
            goto label_4;
        label_5:
            v0_1 = false;
        label_4:
            return v0_1;
        }

        public void threadExiting(GLThread thread) {  // has try-catch handlers
            try {
                GLThread.access$1102(thread, true);
                if(this.mEglOwner == thread) {
                    this.mEglOwner = null;
                }

                this.notifyAll();
                return;
            }
            catch(Throwable v0) {
                throw v0;
            }
        }

        public boolean tryAcquireEglContextLocked(GLThread thread) {
            boolean v0 = true;
            if(this.mEglOwner == thread || this.mEglOwner == null) {
                this.mEglOwner = thread;
                this.notifyAll();
            }
            else {
                this.checkGLESVersion();
                if(!this.mMultipleGLESContextsAllowed) {
                    if(this.mEglOwner != null) {
                        this.mEglOwner.requestReleaseEglContextLocked();
                    }

                    v0 = false;
                }
            }

            return v0;
        }
    }

    public abstract interface GLWrapper {
        public abstract GL wrap(GL arg0);
    }

    class LogWriter extends Writer {
        private StringBuilder mBuilder;

        LogWriter() {
            super();
            this.mBuilder = new StringBuilder();
        }

        public void close() {
            this.flushBuilder();
        }

        public void flush() {
            this.flushBuilder();
        }

        private void flushBuilder() {
            if(this.mBuilder.length() > 0) {
                Log.v("GLSurfaceView", this.mBuilder.toString());
                this.mBuilder.delete(0, this.mBuilder.length());
            }
        }

        public void write(char[] buf, int offset, int count) {
            int v1;
            for(v1 = 0; v1 < count; ++v1) {
                char v0 = buf[offset + v1];
                if(v0 == 10) {
                    this.flushBuilder();
                }
                else {
                    this.mBuilder.append(v0);
                }
            }
        }
    }

    class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(GLSurfaceViewAPI18 arg9, boolean withDepthBuffer) {
            int v6;
            int v2 = 8;
            GLSurfaceViewAPI18.this = arg9;
            if(withDepthBuffer) {
                v6 = 16;
            }
            else {
                v6 = 0;
            }

            super(arg9, v2, v2, v2, 0, v6, 0);
        }
    }

    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final boolean LOG_ATTACH_DETACH = false;
    private static final boolean LOG_EGL = false;
    private static final boolean LOG_PAUSE_RESUME = false;
    private static final boolean LOG_RENDERER = false;
    private static final boolean LOG_RENDERER_DRAW_FRAME = false;
    private static final boolean LOG_SURFACE = false;
    private static final boolean LOG_THREADS = false;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    private static final String TAG = "GLSurfaceViewAPI18";
    private int mDebugFlags;
    private boolean mDetached;
    private GLSurfaceView$EGLConfigChooser mEGLConfigChooser;
    private int mEGLContextClientVersion;
    private EGLContextFactory mEGLContextFactory;
    private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
    private GLThread mGLThread;
    private GLWrapper mGLWrapper;
    private boolean mPreserveEGLContextOnPause;
    private GLSurfaceView$Renderer mRenderer;
    private final WeakReference mThisWeakRef;
    private static final GLThreadManager sGLThreadManager;

    static  {
        GLSurfaceViewAPI18.sGLThreadManager = new GLThreadManager(null);
    }

    public GLSurfaceViewAPI18(Context context) {
        super(context);
        this.mThisWeakRef = new WeakReference(this);
        this.init();
    }

    public GLSurfaceViewAPI18(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mThisWeakRef = new WeakReference(this);
        this.init();
    }

    static GLSurfaceView$Renderer access$1000(GLSurfaceViewAPI18 x0) {
        return x0.mRenderer;
    }

    static int access$200(GLSurfaceViewAPI18 x0) {
        return x0.mEGLContextClientVersion;
    }

    static GLSurfaceView$EGLConfigChooser access$300(GLSurfaceViewAPI18 x0) {
        return x0.mEGLConfigChooser;
    }

    static EGLContextFactory access$400(GLSurfaceViewAPI18 x0) {
        return x0.mEGLContextFactory;
    }

    static EGLWindowSurfaceFactory access$500(GLSurfaceViewAPI18 x0) {
        return x0.mEGLWindowSurfaceFactory;
    }

    static GLWrapper access$600(GLSurfaceViewAPI18 x0) {
        return x0.mGLWrapper;
    }

    static int access$700(GLSurfaceViewAPI18 x0) {
        return x0.mDebugFlags;
    }

    static GLThreadManager access$800() {
        return GLSurfaceViewAPI18.sGLThreadManager;
    }

    static boolean access$900(GLSurfaceViewAPI18 x0) {
        return x0.mPreserveEGLContextOnPause;
    }

    private void checkRenderThreadState() {
        if(this.mGLThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    protected void finalize() throws Throwable {  // has try-catch handlers
        try {
            if(this.mGLThread != null) {
                this.mGLThread.requestExitAndWait();
            }
        }
        catch(Throwable v0) {
            super.finalize();
            throw v0;
        }

        super.finalize();
    }

    public int getDebugFlags() {
        return this.mDebugFlags;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.mPreserveEGLContextOnPause;
    }

    public int getRenderMode() {
        return this.mGLThread.getRenderMode();
    }

    private void init() {
        SurfaceHolder v0 = this.getHolder();
        v0.addCallback(((SurfaceHolder$Callback)this));
        if(Build$VERSION.SDK_INT <= 8) {
            v0.setFormat(4);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if((this.mDetached) && this.mRenderer != null) {
            int v0 = 1;
            if(this.mGLThread != null) {
                v0 = this.mGLThread.getRenderMode();
            }

            this.mGLThread = new GLThread(this.mThisWeakRef);
            if(v0 != 1) {
                this.mGLThread.setRenderMode(v0);
            }

            this.mGLThread.start();
        }

        this.mDetached = false;
    }

    protected void onDetachedFromWindow() {
        if(this.mGLThread != null) {
            this.mGLThread.requestExitAndWait();
        }

        this.mDetached = true;
        super.onDetachedFromWindow();
    }

    public void onPause() {
        this.mGLThread.onPause();
    }

    public void onResume() {
        this.mGLThread.onResume();
    }

    public void queueEvent(Runnable r) {
        this.mGLThread.queueEvent(r);
    }

    public void requestRender() {
        this.mGLThread.requestRender();
    }

    public void setDebugFlags(int debugFlags) {
        this.mDebugFlags = debugFlags;
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        this.setEGLConfigChooser(new ComponentSizeChooser(this, redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setEGLConfigChooser(GLSurfaceView$EGLConfigChooser configChooser) {
        this.checkRenderThreadState();
        this.mEGLConfigChooser = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        this.setEGLConfigChooser(new SimpleEGLConfigChooser(this, needDepth));
    }

    public void setEGLContextClientVersion(int version) {
        this.checkRenderThreadState();
        this.mEGLContextClientVersion = version;
    }

    public void setEGLContextFactory(EGLContextFactory factory) {
        this.checkRenderThreadState();
        this.mEGLContextFactory = factory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        this.checkRenderThreadState();
        this.mEGLWindowSurfaceFactory = factory;
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.mGLWrapper = glWrapper;
    }

    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        this.mPreserveEGLContextOnPause = preserveOnPause;
    }

    public void setRenderMode(int renderMode) {
        this.mGLThread.setRenderMode(renderMode);
    }

    public void setRenderer(GLSurfaceView$Renderer renderer) {
        com.badlogic.gdx.backends.android.surfaceview.GLSurfaceViewAPI18$1 v2 = null;
        this.checkRenderThreadState();
        if(this.mEGLConfigChooser == null) {
            this.mEGLConfigChooser = new SimpleEGLConfigChooser(this, true);
        }

        if(this.mEGLContextFactory == null) {
            this.mEGLContextFactory = new DefaultContextFactory(this, v2);
        }

        if(this.mEGLWindowSurfaceFactory == null) {
            this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory(v2);
        }

        this.mRenderer = renderer;
        this.mGLThread = new GLThread(this.mThisWeakRef);
        this.mGLThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        this.mGLThread.onWindowResize(w, h);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.mGLThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.mGLThread.surfaceDestroyed();
    }
}

