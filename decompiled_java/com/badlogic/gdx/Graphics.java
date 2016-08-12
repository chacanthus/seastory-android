// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public abstract interface Graphics {
    public class BufferFormat {
        public final int a;
        public final int b;
        public final boolean coverageSampling;
        public final int depth;
        public final int g;
        public final int r;
        public final int samples;
        public final int stencil;

        public BufferFormat(int r, int g, int b, int a, int depth, int stencil, int samples, boolean coverageSampling) {
            super();
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;
            this.depth = depth;
            this.stencil = stencil;
            this.samples = samples;
            this.coverageSampling = coverageSampling;
        }

        public String toString() {
            return "r: " + this.r + ", g: " + this.g + ", b: " + this.b + ", a: " + this.a + ", depth: " + this.depth + ", stencil: " + this.stencil + ", num samples: " + this.samples + ", coverage sampling: " + this.coverageSampling;
        }
    }

    public class DisplayMode {
        public final int bitsPerPixel;
        public final int height;
        public final int refreshRate;
        public final int width;

        protected DisplayMode(int width, int height, int refreshRate, int bitsPerPixel) {
            super();
            this.width = width;
            this.height = height;
            this.refreshRate = refreshRate;
            this.bitsPerPixel = bitsPerPixel;
        }

        public String toString() {
            return this.width + "x" + this.height + ", bpp: " + this.bitsPerPixel + ", hz: " + this.refreshRate;
        }
    }

    public enum GraphicsType {
        public static final enum GraphicsType AndroidGL;
        public static final enum GraphicsType Angle;
        public static final enum GraphicsType JGLFW;
        public static final enum GraphicsType LWJGL;
        public static final enum GraphicsType Mock;
        public static final enum GraphicsType WebGL;
        public static final enum GraphicsType iOSGL;

        static  {
            GraphicsType.AndroidGL = new GraphicsType("AndroidGL", 0);
            GraphicsType.LWJGL = new GraphicsType("LWJGL", 1);
            GraphicsType.Angle = new GraphicsType("Angle", 2);
            GraphicsType.WebGL = new GraphicsType("WebGL", 3);
            GraphicsType.iOSGL = new GraphicsType("iOSGL", 4);
            GraphicsType.JGLFW = new GraphicsType("JGLFW", 5);
            GraphicsType.Mock = new GraphicsType("Mock", 6);
            GraphicsType[] v0 = new GraphicsType[7];
            v0[0] = GraphicsType.AndroidGL;
            v0[1] = GraphicsType.LWJGL;
            v0[2] = GraphicsType.Angle;
            v0[3] = GraphicsType.WebGL;
            v0[4] = GraphicsType.iOSGL;
            v0[5] = GraphicsType.JGLFW;
            v0[6] = GraphicsType.Mock;
            GraphicsType.$VALUES = v0;
        }

        private GraphicsType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static GraphicsType valueOf(String name) {
            return Enum.valueOf(GraphicsType.class, name);
        }

        public static GraphicsType[] values() {
            return GraphicsType.$VALUES.clone();
        }
    }

    public abstract BufferFormat getBufferFormat();

    public abstract float getDeltaTime();

    public abstract float getDensity();

    public abstract DisplayMode getDesktopDisplayMode();

    public abstract DisplayMode[] getDisplayModes();

    public abstract long getFrameId();

    public abstract int getFramesPerSecond();

    public abstract GL20 getGL20();

    public abstract GL30 getGL30();

    public abstract int getHeight();

    public abstract float getPpcX();

    public abstract float getPpcY();

    public abstract float getPpiX();

    public abstract float getPpiY();

    public abstract float getRawDeltaTime();

    public abstract GraphicsType getType();

    public abstract int getWidth();

    public abstract boolean isContinuousRendering();

    public abstract boolean isFullscreen();

    public abstract boolean isGL30Available();

    public abstract void requestRendering();

    public abstract void setContinuousRendering(boolean arg0);

    public abstract boolean setDisplayMode(int arg0, int arg1, boolean arg2);

    public abstract boolean setDisplayMode(DisplayMode arg0);

    public abstract void setTitle(String arg0);

    public abstract void setVSync(boolean arg0);

    public abstract boolean supportsDisplayModeChange();

    public abstract boolean supportsExtension(String arg0);
}

