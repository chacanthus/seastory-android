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

import android.opengl.GLSurfaceView$EGLConfigChooser;
import android.util.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class GdxEglConfigChooser implements GLSurfaceView$EGLConfigChooser {
    public static final int EGL_COVERAGE_BUFFERS_NV = 12512;
    public static final int EGL_COVERAGE_SAMPLES_NV = 12513;
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final String TAG = "GdxEglConfigChooser";
    protected int mAlphaSize;
    protected int mBlueSize;
    protected final int[] mConfigAttribs;
    protected int mDepthSize;
    protected int mGreenSize;
    protected int mNumSamples;
    protected int mRedSize;
    protected int mStencilSize;
    private int[] mValue;

    public GdxEglConfigChooser(int r, int g, int b, int a, int depth, int stencil, int numSamples) {
        super();
        this.mValue = new int[1];
        this.mRedSize = r;
        this.mGreenSize = g;
        this.mBlueSize = b;
        this.mAlphaSize = a;
        this.mDepthSize = depth;
        this.mStencilSize = stencil;
        this.mNumSamples = numSamples;
        this.mConfigAttribs = new int[]{12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344};
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        int[] v5 = new int[1];
        egl.eglChooseConfig(display, this.mConfigAttribs, null, 0, v5);
        int v4 = v5[0];
        if(v4 <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }

        EGLConfig[] v3 = new EGLConfig[v4];
        egl.eglChooseConfig(display, this.mConfigAttribs, v3, v4, v5);
        return this.chooseConfig(egl, display, v3);
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
        EGLConfig v10 = null;
        EGLConfig v11 = null;
        EGLConfig v20 = null;
        EGLConfig[] v8 = configs;
        int v16 = v8.length;
        int v15;
        for(v15 = 0; v15 < v16; ++v15) {
            EGLConfig v4 = v8[v15];
            int v12 = this.findConfigAttrib(egl, display, v4, 12325, 0);
            int v19 = this.findConfigAttrib(egl, display, v4, 12326, 0);
            if(v12 >= this.mDepthSize && v19 >= this.mStencilSize) {
                int v18 = this.findConfigAttrib(egl, display, v4, 12324, 0);
                int v13 = this.findConfigAttrib(egl, display, v4, 12323, 0);
                int v9 = this.findConfigAttrib(egl, display, v4, 12322, 0);
                int v7 = this.findConfigAttrib(egl, display, v4, 12321, 0);
                if(v20 == null && v18 == 5 && v13 == 6 && v9 == 5 && v7 == 0) {
                    v20 = v4;
                }

                if(v10 == null && v18 == this.mRedSize && v13 == this.mGreenSize && v9 == this.mBlueSize && v7 == this.mAlphaSize) {
                    v10 = v4;
                    if(this.mNumSamples != 0) {
                        goto label_85;
                    }

                    break;
                }

            label_85:
                int v14 = this.findConfigAttrib(egl, display, v4, 12338, 0);
                int v17 = this.findConfigAttrib(egl, display, v4, 12337, 0);
                if(v11 == null && v14 == 1 && v17 >= this.mNumSamples && v18 == this.mRedSize && v13 == this.mGreenSize && v9 == this.mBlueSize && v7 == this.mAlphaSize) {
                    v11 = v4;
                    goto label_29;
                }

                v14 = this.findConfigAttrib(egl, display, v4, 12512, 0);
                v17 = this.findConfigAttrib(egl, display, v4, 12513, 0);
                if(v11 != null) {
                    goto label_29;
                }

                if(v14 != 1) {
                    goto label_29;
                }

                if(v17 < this.mNumSamples) {
                    goto label_29;
                }

                if(v18 != this.mRedSize) {
                    goto label_29;
                }

                if(v13 != this.mGreenSize) {
                    goto label_29;
                }

                if(v9 != this.mBlueSize) {
                    goto label_29;
                }

                if(v7 != this.mAlphaSize) {
                    goto label_29;
                }

                v11 = v4;
            }

        label_29:
        }

        if(v11 == null) {
            if(v10 != null) {
                v11 = v10;
            }
            else {
                v11 = v20;
            }
        }

        return v11;
    }

    private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
        if(egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
            defaultValue = this.mValue[0];
        }

        return defaultValue;
    }

    private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
        int[] v1 = new int[]{12320, 12321, 12322, 12323, 12324, 12325, 12326, 12327, 12328, 12329, 12330, 12331, 12332, 12333, 12334, 12335, 12336, 12337, 12338, 12339, 12340, 12343, 12342, 12341, 12345, 12346, 12347, 12348, 12349, 12350, 12351, 12352, 12354, 12512, 12513};
        String[] v4 = new String[35];
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
        v4[33] = "EGL_COVERAGE_BUFFERS_NV";
        v4[34] = "EGL_COVERAGE_SAMPLES_NV";
        int[] v5 = new int[1];
        int v2;
        for(v2 = 0; v2 < v1.length; ++v2) {
            int v0 = v1[v2];
            String v3 = v4[v2];
            if(egl.eglGetConfigAttrib(display, config, v0, v5)) {
                Log.w("GdxEglConfigChooser", String.format("  %s: %d\n", v3, Integer.valueOf(v5[0])));
            }
            else {
                egl.eglGetError();
            }
        }
    }

    private void printConfigs(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
        int v1 = configs.length;
        Log.w("GdxEglConfigChooser", String.format("%d configurations", Integer.valueOf(v1)));
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            v4 = new Object[1];
            v4[0] = Integer.valueOf(v0);
            Log.w("GdxEglConfigChooser", String.format("Configuration %d:\n", v4));
            this.printConfig(egl, display, configs[v0]);
        }
    }
}

