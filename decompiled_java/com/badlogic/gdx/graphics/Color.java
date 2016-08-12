// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.utils.NumberUtils;

public class Color {
    public static final Color BLACK;
    public static final Color BLUE;
    public static final Color CLEAR;
    public static final Color CYAN;
    public static final Color DARK_GRAY;
    public static final Color GRAY;
    public static final Color GREEN;
    public static final Color LIGHT_GRAY;
    public static final Color MAGENTA;
    public static final Color MAROON;
    public static final Color NAVY;
    public static final Color OLIVE;
    public static final Color ORANGE;
    public static final Color PINK;
    public static final Color PURPLE;
    public static final Color RED;
    public static final Color TEAL;
    public static final Color WHITE;
    public static final Color YELLOW;
    public float a;
    public float b;
    public float g;
    public float r;

    static  {
        Color.CLEAR = new Color(0f, 0f, 0f, 0f);
        Color.WHITE = new Color(1f, 1f, 1f, 1f);
        Color.BLACK = new Color(0f, 0f, 0f, 1f);
        Color.RED = new Color(1f, 0f, 0f, 1f);
        Color.GREEN = new Color(0f, 1f, 0f, 1f);
        Color.BLUE = new Color(0f, 0f, 1f, 1f);
        Color.LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f, 1f);
        Color.GRAY = new Color(0.5f, 0.5f, 0.5f, 1f);
        Color.DARK_GRAY = new Color(0.25f, 0.25f, 0.25f, 1f);
        Color.PINK = new Color(1f, 0.68f, 0.68f, 1f);
        Color.ORANGE = new Color(1f, 0.78f, 0f, 1f);
        Color.YELLOW = new Color(1f, 1f, 0f, 1f);
        Color.MAGENTA = new Color(1f, 0f, 1f, 1f);
        Color.CYAN = new Color(0f, 1f, 1f, 1f);
        Color.OLIVE = new Color(0.5f, 0.5f, 0f, 1f);
        Color.PURPLE = new Color(0.5f, 0f, 0.5f, 1f);
        Color.MAROON = new Color(0.5f, 0f, 0f, 1f);
        Color.TEAL = new Color(0f, 0.5f, 0.5f, 1f);
        Color.NAVY = new Color(0f, 0f, 0.5f, 1f);
    }

    public Color() {
        super();
    }

    public Color(float r, float g, float b, float a) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.clamp();
    }

    public Color(int rgba8888) {
        super();
        Color.rgba8888ToColor(this, rgba8888);
    }

    public Color(Color color) {
        super();
        this.set(color);
    }

    public Color add(float r, float g, float b, float a) {
        this.r += r;
        this.g += g;
        this.b += b;
        this.a += a;
        return this.clamp();
    }

    public Color add(Color color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        this.a += color.a;
        return this.clamp();
    }

    public static int alpha(float alpha) {
        return ((int)(255f * alpha));
    }

    public static int argb8888(float a, float r, float g, float b) {
        return (((int)(a * 255f))) << 24 | (((int)(r * 255f))) << 16 | (((int)(g * 255f))) << 8 | (((int)(b * 255f)));
    }

    public static int argb8888(Color color) {
        return (((int)(color.a * 255f))) << 24 | (((int)(color.r * 255f))) << 16 | (((int)(color.g * 255f))) << 8 | (((int)(color.b * 255f)));
    }

    public static void argb8888ToColor(Color color, int value) {
        color.a = (((float)((-16777216 & value) >>> 24))) / 255f;
        color.r = (((float)((16711680 & value) >>> 16))) / 255f;
        color.g = (((float)((65280 & value) >>> 8))) / 255f;
        color.b = (((float)(value & 255))) / 255f;
    }

    public Color clamp() {
        float v2 = 1f;
        if(this.r < 0f) {
            this.r = 0f;
        }
        else if(this.r > v2) {
            this.r = v2;
        }

        if(this.g < 0f) {
            this.g = 0f;
        }
        else if(this.g > v2) {
            this.g = v2;
        }

        if(this.b < 0f) {
            this.b = 0f;
        }
        else if(this.b > v2) {
            this.b = v2;
        }

        if(this.a < 0f) {
            this.a = 0f;
        }
        else if(this.a > v2) {
            this.a = v2;
        }

        return this;
    }

    public Color cpy() {
        return new Color(this);
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if(this != (((Color)o))) {
            if(o != null && this.getClass() == o.getClass()) {
                if(this.toIntBits() != o.toIntBits()) {
                    v1 = false;
                    goto label_3;
                }
                else {
                    goto label_3;
                }
            }

            v1 = false;
        }

    label_3:
        return v1;
    }

    public int hashCode() {
        int v2;
        int v0;
        int v1 = 0;
        if(this.r != 0f) {
            v0 = NumberUtils.floatToIntBits(this.r);
        }
        else {
            v0 = 0;
        }

        int v3 = v0 * 31;
        if(this.g != 0f) {
            v2 = NumberUtils.floatToIntBits(this.g);
        }
        else {
            v2 = 0;
        }

        v3 = (v3 + v2) * 31;
        if(this.b != 0f) {
            v2 = NumberUtils.floatToIntBits(this.b);
        }
        else {
            v2 = 0;
        }

        v2 = (v3 + v2) * 31;
        if(this.a != 0f) {
            v1 = NumberUtils.floatToIntBits(this.a);
        }

        return v2 + v1;
    }

    public Color lerp(float r, float g, float b, float a, float t) {
        this.r += (r - this.r) * t;
        this.g += (g - this.g) * t;
        this.b += (b - this.b) * t;
        this.a += (a - this.a) * t;
        return this.clamp();
    }

    public Color lerp(Color target, float t) {
        this.r += (target.r - this.r) * t;
        this.g += (target.g - this.g) * t;
        this.b += (target.b - this.b) * t;
        this.a += (target.a - this.a) * t;
        return this.clamp();
    }

    public static int luminanceAlpha(float luminance, float alpha) {
        return (((int)(luminance * 255f))) << 8 | (((int)(255f * alpha)));
    }

    public Color mul(Color color) {
        this.r *= color.r;
        this.g *= color.g;
        this.b *= color.b;
        this.a *= color.a;
        return this.clamp();
    }

    public Color mul(float value) {
        this.r *= value;
        this.g *= value;
        this.b *= value;
        this.a *= value;
        return this.clamp();
    }

    public Color mul(float r, float g, float b, float a) {
        this.r *= r;
        this.g *= g;
        this.b *= b;
        this.a *= a;
        return this.clamp();
    }

    public Color premultiplyAlpha() {
        this.r *= this.a;
        this.g *= this.a;
        this.b *= this.a;
        return this;
    }

    public static int rgb565(float r, float g, float b) {
        return (((int)(r * 31f))) << 11 | (((int)(63f * g))) << 5 | (((int)(b * 31f)));
    }

    public static int rgb565(Color color) {
        return (((int)(color.r * 31f))) << 11 | (((int)(color.g * 63f))) << 5 | (((int)(color.b * 31f)));
    }

    public static void rgb565ToColor(Color color, int value) {
        color.r = (((float)((63488 & value) >>> 11))) / 31f;
        color.g = (((float)((value & 2016) >>> 5))) / 63f;
        color.b = (((float)((value & 31) >>> 0))) / 31f;
    }

    public static int rgb888(float r, float g, float b) {
        return (((int)(r * 255f))) << 16 | (((int)(g * 255f))) << 8 | (((int)(b * 255f)));
    }

    public static int rgb888(Color color) {
        return (((int)(color.r * 255f))) << 16 | (((int)(color.g * 255f))) << 8 | (((int)(color.b * 255f)));
    }

    public static void rgb888ToColor(Color color, int value) {
        color.r = (((float)((16711680 & value) >>> 16))) / 255f;
        color.g = (((float)((65280 & value) >>> 8))) / 255f;
        color.b = (((float)(value & 255))) / 255f;
    }

    public static int rgba4444(float r, float g, float b, float a) {
        return (((int)(r * 15f))) << 12 | (((int)(g * 15f))) << 8 | (((int)(b * 15f))) << 4 | (((int)(a * 15f)));
    }

    public static int rgba4444(Color color) {
        return (((int)(color.r * 15f))) << 12 | (((int)(color.g * 15f))) << 8 | (((int)(color.b * 15f))) << 4 | (((int)(color.a * 15f)));
    }

    public static void rgba4444ToColor(Color color, int value) {
        color.r = (((float)((61440 & value) >>> 12))) / 15f;
        color.g = (((float)((value & 3840) >>> 8))) / 15f;
        color.b = (((float)((value & 240) >>> 4))) / 15f;
        color.a = (((float)(value & 15))) / 15f;
    }

    public static int rgba8888(float r, float g, float b, float a) {
        return (((int)(r * 255f))) << 24 | (((int)(g * 255f))) << 16 | (((int)(b * 255f))) << 8 | (((int)(a * 255f)));
    }

    public static int rgba8888(Color color) {
        return (((int)(color.r * 255f))) << 24 | (((int)(color.g * 255f))) << 16 | (((int)(color.b * 255f))) << 8 | (((int)(color.a * 255f)));
    }

    public static void rgba8888ToColor(Color color, int value) {
        color.r = (((float)((-16777216 & value) >>> 24))) / 255f;
        color.g = (((float)((16711680 & value) >>> 16))) / 255f;
        color.b = (((float)((65280 & value) >>> 8))) / 255f;
        color.a = (((float)(value & 255))) / 255f;
    }

    public Color set(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
        return this;
    }

    public Color set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this.clamp();
    }

    public Color set(int rgba) {
        Color.rgba8888ToColor(this, rgba);
        return this;
    }

    public Color sub(float r, float g, float b, float a) {
        this.r -= r;
        this.g -= g;
        this.b -= b;
        this.a -= a;
        return this.clamp();
    }

    public Color sub(Color color) {
        this.r -= color.r;
        this.g -= color.g;
        this.b -= color.b;
        this.a -= color.a;
        return this.clamp();
    }

    public static float toFloatBits(float r, float g, float b, float a) {
        return NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
    }

    public static float toFloatBits(int r, int g, int b, int a) {
        return NumberUtils.intToFloatColor(a << 24 | b << 16 | g << 8 | r);
    }

    public float toFloatBits() {
        return NumberUtils.intToFloatColor((((int)(this.a * 255f))) << 24 | (((int)(this.b * 255f))) << 16 | (((int)(this.g * 255f))) << 8 | (((int)(this.r * 255f))));
    }

    public static int toIntBits(int r, int g, int b, int a) {
        return a << 24 | b << 16 | g << 8 | r;
    }

    public int toIntBits() {
        return (((int)(this.a * 255f))) << 24 | (((int)(this.b * 255f))) << 16 | (((int)(this.g * 255f))) << 8 | (((int)(this.r * 255f)));
    }

    public String toString() {
        String v0;
        for(v0 = Integer.toHexString((((int)(this.r * 255f))) << 24 | (((int)(this.g * 255f))) << 16 | (((int)(this.b * 255f))) << 8 | (((int)(this.a * 255f)))); v0.length() < 8; v0 = "0" + v0) {
        }

        return v0;
    }

    public static Color valueOf(String hex) {
        int v0;
        int v8 = 6;
        int v6 = 16;
        float v9 = 255f;
        int v3 = Integer.valueOf(hex.substring(0, 2), v6).intValue();
        int v2 = Integer.valueOf(hex.substring(2, 4), v6).intValue();
        int v1 = Integer.valueOf(hex.substring(4, v8), v6).intValue();
        if(hex.length() != 8) {
            v0 = 255;
        }
        else {
            v0 = Integer.valueOf(hex.substring(v8, 8), v6).intValue();
        }

        return new Color((((float)v3)) / v9, (((float)v2)) / v9, (((float)v1)) / v9, (((float)v0)) / v9);
    }
}

