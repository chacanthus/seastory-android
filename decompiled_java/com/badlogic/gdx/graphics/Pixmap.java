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

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Pixmap implements Disposable {
    public enum Blending {
        static  {
            Blending.None = new Blending("None", 0);
            Blending.SourceOver = new Blending("SourceOver", 1);
            Blending[] v0 = new Blending[2];
            v0[0] = Blending.None;
            v0[1] = Blending.SourceOver;
            Blending.$VALUES = v0;
        }

        private Blending(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Blending valueOf(String name) {
            return Enum.valueOf(Blending.class, name);
        }

        public static Blending[] values() {
            return Blending.$VALUES.clone();
        }
    }

    public enum Filter {
        public static final enum Filter BiLinear;

        static  {
            Filter.NearestNeighbour = new Filter("NearestNeighbour", 0);
            Filter.BiLinear = new Filter("BiLinear", 1);
            Filter[] v0 = new Filter[2];
            v0[0] = Filter.NearestNeighbour;
            v0[1] = Filter.BiLinear;
            Filter.$VALUES = v0;
        }

        private Filter(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Filter valueOf(String name) {
            return Enum.valueOf(Filter.class, name);
        }

        public static Filter[] values() {
            return Filter.$VALUES.clone();
        }
    }

    public enum Format {
        public static final enum Format Alpha;
        public static final enum Format Intensity;
        public static final enum Format LuminanceAlpha;
        public static final enum Format RGB565;
        public static final enum Format RGB888;
        public static final enum Format RGBA4444;
        public static final enum Format RGBA8888;

        static  {
            Format.Alpha = new Format("Alpha", 0);
            Format.Intensity = new Format("Intensity", 1);
            Format.LuminanceAlpha = new Format("LuminanceAlpha", 2);
            Format.RGB565 = new Format("RGB565", 3);
            Format.RGBA4444 = new Format("RGBA4444", 4);
            Format.RGB888 = new Format("RGB888", 5);
            Format.RGBA8888 = new Format("RGBA8888", 6);
            Format[] v0 = new Format[7];
            v0[0] = Format.Alpha;
            v0[1] = Format.Intensity;
            v0[2] = Format.LuminanceAlpha;
            v0[3] = Format.RGB565;
            v0[4] = Format.RGBA4444;
            v0[5] = Format.RGB888;
            v0[6] = Format.RGBA8888;
            Format.$VALUES = v0;
        }

        private Format(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Format fromGdx2DPixmapFormat(int format) {
            Format v0;
            if(format == 1) {
                v0 = Format.Alpha;
            }
            else if(format == 2) {
                v0 = Format.LuminanceAlpha;
            }
            else if(format == 5) {
                v0 = Format.RGB565;
            }
            else if(format == 6) {
                v0 = Format.RGBA4444;
            }
            else if(format == 3) {
                v0 = Format.RGB888;
            }
            else if(format == 4) {
                v0 = Format.RGBA8888;
            }
            else {
                goto label_24;
            }

            return v0;
        label_24:
            throw new GdxRuntimeException("Unknown Gdx2DPixmap Format: " + format);
        }

        public static int toGdx2DPixmapFormat(Format format) {
            int v0 = 1;
            if(format != Format.Alpha && format != Format.Intensity) {
                if(format == Format.LuminanceAlpha) {
                    v0 = 2;
                }
                else if(format == Format.RGB565) {
                    v0 = 5;
                }
                else if(format == Format.RGBA4444) {
                    v0 = 6;
                }
                else if(format == Format.RGB888) {
                    v0 = 3;
                }
                else if(format == Format.RGBA8888) {
                    v0 = 4;
                }
                else {
                    throw new GdxRuntimeException("Unknown Format: " + format);
                }
            }

            return v0;
        }

        public static Format valueOf(String name) {
            return Enum.valueOf(Format.class, name);
        }

        public static Format[] values() {
            return Format.$VALUES.clone();
        }
    }

    private static Blending blending;
    int color;
    private boolean disposed;
    final Gdx2DPixmap pixmap;

    static  {
        Pixmap.blending = Blending.SourceOver;
    }

    public Pixmap(int width, int height, Format format) {
        super();
        this.color = 0;
        this.pixmap = new Gdx2DPixmap(width, height, Format.toGdx2DPixmapFormat(format));
        this.setColor(0f, 0f, 0f, 0f);
        this.fill();
    }

    public Pixmap(FileHandle file) {  // has try-catch handlers
        super();
        this.color = 0;
        try {
            byte[] v0 = file.readBytes();
            this.pixmap = new Gdx2DPixmap(v0, 0, v0.length, 0);
            return;
        }
        catch(Exception v1) {
            throw new GdxRuntimeException("Couldn\'t load file: " + file, ((Throwable)v1));
        }
    }

    public Pixmap(Gdx2DPixmap pixmap) {
        super();
        this.color = 0;
        this.pixmap = pixmap;
    }

    public Pixmap(byte[] encodedData, int offset, int len) {  // has try-catch handlers
        super();
        this.color = 0;
        try {
            this.pixmap = new Gdx2DPixmap(encodedData, offset, len, 0);
            return;
        }
        catch(IOException v0) {
            throw new GdxRuntimeException("Couldn\'t load pixmap from image data", ((Throwable)v0));
        }
    }

    public void dispose() {
        if(this.disposed) {
            throw new GdxRuntimeException("Pixmap already disposed!");
        }

        this.pixmap.dispose();
        this.disposed = true;
    }

    public void drawCircle(int x, int y, int radius) {
        this.pixmap.drawCircle(x, y, radius, this.color);
    }

    public void drawLine(int x, int y, int x2, int y2) {
        this.pixmap.drawLine(x, y, x2, y2, this.color);
    }

    public void drawPixel(int x, int y) {
        this.pixmap.setPixel(x, y, this.color);
    }

    public void drawPixel(int x, int y, int color) {
        this.pixmap.setPixel(x, y, color);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {
        this.drawPixmap(pixmap, x, y, 0, 0, pixmap.getWidth(), pixmap.getHeight());
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcx, int srcy, int srcWidth, int srcHeight) {
        this.pixmap.drawPixmap(pixmap.pixmap, srcx, srcy, x, y, srcWidth, srcHeight);
    }

    public void drawPixmap(Pixmap pixmap, int srcx, int srcy, int srcWidth, int srcHeight, int dstx, int dsty, int dstWidth, int dstHeight) {
        this.pixmap.drawPixmap(pixmap.pixmap, srcx, srcy, srcWidth, srcHeight, dstx, dsty, dstWidth, dstHeight);
    }

    public void drawRectangle(int x, int y, int width, int height) {
        this.pixmap.drawRect(x, y, width, height, this.color);
    }

    public void fill() {
        this.pixmap.clear(this.color);
    }

    public void fillCircle(int x, int y, int radius) {
        this.pixmap.fillCircle(x, y, radius, this.color);
    }

    public void fillRectangle(int x, int y, int width, int height) {
        this.pixmap.fillRect(x, y, width, height, this.color);
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.pixmap.fillTriangle(x1, y1, x2, y2, x3, y3, this.color);
    }

    public static Blending getBlending() {
        return Pixmap.blending;
    }

    public Format getFormat() {
        return Format.fromGdx2DPixmapFormat(this.pixmap.getFormat());
    }

    public int getGLFormat() {
        return this.pixmap.getGLFormat();
    }

    public int getGLInternalFormat() {
        return this.pixmap.getGLInternalFormat();
    }

    public int getGLType() {
        return this.pixmap.getGLType();
    }

    public int getHeight() {
        return this.pixmap.getHeight();
    }

    public int getPixel(int x, int y) {
        return this.pixmap.getPixel(x, y);
    }

    public ByteBuffer getPixels() {
        if(this.disposed) {
            throw new GdxRuntimeException("Pixmap already disposed");
        }

        return this.pixmap.getPixels();
    }

    public int getWidth() {
        return this.pixmap.getWidth();
    }

    public static void setBlending(Blending blending) {
        int v0;
        Pixmap.blending = blending;
        if(blending == Blending.None) {
            v0 = 0;
        }
        else {
            v0 = 1;
        }

        Gdx2DPixmap.setBlend(v0);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = Color.rgba8888(r, g, b, a);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = Color.rgba8888(color.r, color.g, color.b, color.a);
    }

    public static void setFilter(Filter filter) {
        int v0;
        if(filter == Filter.NearestNeighbour) {
            v0 = 0;
        }
        else {
            v0 = 1;
        }

        Gdx2DPixmap.setScale(v0);
    }
}

