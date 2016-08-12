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
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView$EGLConfigChooser;
import android.opengl.GLSurfaceView$EGLContextFactory;
import android.os.Build$VERSION;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public class GLSurfaceView20 extends GLSurfaceView {
    class ConfigChooser implements GLSurfaceView$EGLConfigChooser {
        private static int EGL_OPENGL_ES2_BIT;
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue;
        private static int[] s_configAttribs2;

        static  {
            ConfigChooser.EGL_OPENGL_ES2_BIT = 4;
            int[] v0 = new int[9];
            v0[0] = 12324;
            v0[1] = 4;
            v0[2] = 12323;
            v0[3] = 4;
            v0[4] = 12322;
            v0[5] = 4;
            v0[6] = 12352;
            v0[7] = ConfigChooser.EGL_OPENGL_ES2_BIT;
            v0[8] = 12344;
            ConfigChooser.s_configAttribs2 = v0;
        }

        public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
            super();
            this.mValue = new int[1];
            this.mRedSize = r;
            this.mGreenSize = g;
            this.mBlueSize = b;
            this.mAlphaSize = a;
            this.mDepthSize = depth;
            this.mStencilSize = stencil;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] v5 = new int[1];
            egl.eglChooseConfig(display, ConfigChooser.s_configAttribs2, null, 0, v5);
            int v4 = v5[0];
            if(v4 <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

            EGLConfig[] v3 = new EGLConfig[v4];
            egl.eglChooseConfig(display, ConfigChooser.s_configAttribs2, v3, v4, v5);
            return this.chooseConfig(egl, display, v3);
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
                            goto label_21;
                        }

                        goto label_55;
                    }
                }

            label_21:
            }

            v3 = null;
        label_55:
            return v3;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if(egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                defaultValue = this.mValue[0];
            }

            return defaultValue;
        }

        private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] v1 = new int[]{12320, 12321, 12322, 12323, 12324, 12325, 12326, 12327, 12328, 12329, 12330, 12331, 12332, 12333, 12334, 12335, 12336, 12337, 12338, 12339, 12340, 12343, 12342, 12341, 12345, 12346, 12347, 12348, 12349, 12350, 12351, 12352, 12354};
            String[] v4 = new String[33];
            v4[0] = "EGL_BUFFER_SIZE";
            v4[1] = "EGL_ALPHA_SIZE";
            v4[2] = "EGL_BLUE_SIZE";
            v4[3] = "EGL_GREEN_SIZE";
            v4[4] = "EGL_RED_SIZE";
            v4[5] = "EGL_DEPTH_SIZE";
            v4[6] = "EGL_STENCIL_SIZE";
            v4[7] = "EGL_CONFIG_CAVEAT";
            v4[8] = "EGL_CONFIG_ID";
            v4[9] = "EGL_LEVEL";
            v4[10] = "EGL_MAX_PBUFFER_HEIGHT";
            v4[11] = "EGL_MAX_PBUFFER_PIXELS";
            v4[12] = "EGL_MAX_PBUFFER_WIDTH";
            v4[13] = "EGL_NATIVE_RENDERABLE";
            v4[14] = "EGL_NATIVE_VISUAL_ID";
            v4[15] = "EGL_NATIVE_VISUAL_TYPE";
            v4[16] = "EGL_PRESERVED_RESOURCES";
            v4[17] = "EGL_SAMPLES";
            v4[18] = "EGL_SAMPLE_BUFFERS";
            v4[19] = "EGL_SURFACE_TYPE";
            v4[20] = "EGL_TRANSPARENT_TYPE";
            v4[21] = "EGL_TRANSPARENT_RED_VALUE";
            v4[22] = "EGL_TRANSPARENT_GREEN_VALUE";
            v4[23] = "EGL_TRANSPARENT_BLUE_VALUE";
            v4[24] = "EGL_BIND_TO_TEXTURE_RGB";
            v4[25] = "EGL_BIND_TO_TEXTURE_RGBA";
            v4[26] = "EGL_MIN_SWAP_INTERVAL";
            v4[27] = "EGL_MAX_SWAP_INTERVAL";
            v4[28] = "EGL_LUMINANCE_SIZE";
            v4[29] = "EGL_ALPHA_MASK_SIZE";
            v4[30] = "EGL_COLOR_BUFFER_TYPE";
            v4[31] = "EGL_RENDERABLE_TYPE";
            v4[32] = "EGL_CONFORMANT";
            int[] v5 = new int[1];
            int v2;
            for(v2 = 0; v2 < v1.length; ++v2) {
                int v0 = v1[v2];
                String v3 = v4[v2];
                if(egl.eglGetConfigAttrib(display, config, v0, v5)) {
                    String v6 = GLSurfaceView20.TAG;
                    Log.w(v6, String.format("  %s: %d\n", v3, Integer.valueOf(v5[0])));
                }
                else {
                    while(egl.eglGetError() != 12288) {
                    }
                }
            }
        }

        private void printConfigs(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            int v1 = configs.length;
            String v2 = GLSurfaceView20.TAG;
            Log.w(v2, String.format("%d configurations", Integer.valueOf(v1)));
            int v0;
            for(v0 = 0; v0 < v1; ++v0) {
                v2 = GLSurfaceView20.TAG;
                v4 = new Object[1];
                v4[0] = Integer.valueOf(v0);
                Log.w(v2, String.format("Configuration %d:\n", v4));
                this.printConfig(egl, display, configs[v0]);
            }
        }
    }

    class ContextFactory implements GLSurfaceView$EGLContextFactory {
        private static int EGL_CONTEXT_CLIENT_VERSION;

        static  {
            ContextFactory.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        ContextFactory() {
            super();
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
            Log.w(GLSurfaceView20.TAG, "creating OpenGL ES 2.0 context");
            GLSurfaceView20.checkEglError("Before eglCreateContext", egl);
            int[] v0 = new int[3];
            v0[0] = ContextFactory.EGL_CONTEXT_CLIENT_VERSION;
            v0[1] = 2;
            v0[2] = 12344;
            EGLContext v1 = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, v0);
            GLSurfaceView20.checkEglError("After eglCreateContext", egl);
            return v1;
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            egl.eglDestroyContext(display, context);
        }
    }

    private static final boolean DEBUG;
    static String TAG;
    final ResolutionStrategy resolutionStrategy;

    static  {
        GLSurfaceView20.TAG = "GL2JNIView";
    }

    public GLSurfaceView20(Context context, ResolutionStrategy resolutionStrategy) {
        super(context);
        this.resolutionStrategy = resolutionStrategy;
        this.init(false, 16, 0);
    }

    public GLSurfaceView20(Context context, boolean translucent, int depth, int stencil, ResolutionStrategy resolutionStrategy) {
        super(context);
        this.resolutionStrategy = resolutionStrategy;
        this.init(translucent, depth, stencil);
    }

    static void checkEglError(String prompt, EGL10 egl) {
        while(true) {
            int v0 = egl.eglGetError();
            if(v0 == 12288) {
                return;
            }

            String v1 = GLSurfaceView20.TAG;
            Log.e(v1, String.format("%s: EGL error: 0x%x", prompt, Integer.valueOf(v0)));
        }
    }

    private void init(boolean translucent, int depth, int stencil) {
        ConfigChooser v0;
        int v5 = 5;
        int v1 = 8;
        if(translucent) {
            this.getHolder().setFormat(-3);
        }

        this.setEGLContextFactory(new ContextFactory());
        if(translucent) {
            v0 = new ConfigChooser(v1, v1, v1, v1, depth, stencil);
        }
        else {
            v0 = new ConfigChooser(v5, 6, v5, 0, depth, stencil);
        }

        this.setEGLConfigChooser(((GLSurfaceView$EGLConfigChooser)v0));
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        if(outAttrs != null) {
            outAttrs.imeOptions |= 268435456;
        }

        return new BaseInputConnection() {
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                boolean v1 = true;
                if(Build$VERSION.SDK_INT < 16 || beforeLength != 1 || afterLength != 0) {
                    v1 = super.deleteSurroundingText(beforeLength, afterLength);
                }
                else {
                    this.sendDownUpKeyEventForBackwardCompatibility(67);
                }

                return v1;
            }

            private void sendDownUpKeyEventForBackwardCompatibility(int code) {
                long v4 = SystemClock.uptimeMillis();
                super.sendKeyEvent(new KeyEvent(v4, v4, 0, code, 0, 0, -1, 0, 6));
                super.sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), v4, 1, code, 0, 0, -1, 0, 6));
            }
        };
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasuredDimension v0 = this.resolutionStrategy.calcMeasures(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(v0.width, v0.height);
    }
}

