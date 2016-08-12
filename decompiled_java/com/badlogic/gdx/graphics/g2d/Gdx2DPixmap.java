// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Gdx2DPixmap implements Disposable {
    public static final int GDX2D_BLEND_NONE = 0;
    public static final int GDX2D_BLEND_SRC_OVER = 1;
    public static final int GDX2D_FORMAT_ALPHA = 1;
    public static final int GDX2D_FORMAT_LUMINANCE_ALPHA = 2;
    public static final int GDX2D_FORMAT_RGB565 = 5;
    public static final int GDX2D_FORMAT_RGB888 = 3;
    public static final int GDX2D_FORMAT_RGBA4444 = 6;
    public static final int GDX2D_FORMAT_RGBA8888 = 4;
    public static final int GDX2D_SCALE_LINEAR = 1;
    public static final int GDX2D_SCALE_NEAREST;
    long basePtr;
    int format;
    int height;
    long[] nativeData;
    ByteBuffer pixelPtr;
    int width;

    static  {
        Gdx2DPixmap.setBlend(1);
        Gdx2DPixmap.setScale(1);
    }

    public Gdx2DPixmap(int width, int height, int format) throws GdxRuntimeException {
        super();
        this.nativeData = new long[4];
        this.pixelPtr = Gdx2DPixmap.newPixmap(this.nativeData, width, height, format);
        if(this.pixelPtr == null) {
            throw new GdxRuntimeException("Error loading pixmap.");
        }

        this.basePtr = this.nativeData[0];
        this.width = ((int)this.nativeData[1]);
        this.height = ((int)this.nativeData[2]);
        this.format = ((int)this.nativeData[3]);
    }

    public Gdx2DPixmap(byte[] encodedData, int offset, int len, int requestedFormat) throws IOException {
        super();
        this.nativeData = new long[4];
        this.pixelPtr = Gdx2DPixmap.load(this.nativeData, encodedData, offset, len);
        if(this.pixelPtr == null) {
            throw new IOException("Error loading pixmap: " + Gdx2DPixmap.getFailureReason());
        }

        this.basePtr = this.nativeData[0];
        this.width = ((int)this.nativeData[1]);
        this.height = ((int)this.nativeData[2]);
        this.format = ((int)this.nativeData[3]);
        if(requestedFormat != 0 && requestedFormat != this.format) {
            this.convert(requestedFormat);
        }
    }

    public Gdx2DPixmap(InputStream in, int requestedFormat) throws IOException {
        super();
        this.nativeData = new long[4];
        ByteArrayOutputStream v1 = new ByteArrayOutputStream(1024);
        byte[] v0 = new byte[1024];
        while(true) {
            int v2 = in.read(v0);
            if(v2 == -1) {
                break;
            }

            v1.write(v0, 0, v2);
        }

        v0 = v1.toByteArray();
        this.pixelPtr = Gdx2DPixmap.load(this.nativeData, v0, 0, v0.length);
        if(this.pixelPtr == null) {
            throw new IOException("Error loading pixmap: " + Gdx2DPixmap.getFailureReason());
        }

        this.basePtr = this.nativeData[0];
        this.width = ((int)this.nativeData[1]);
        this.height = ((int)this.nativeData[2]);
        this.format = ((int)this.nativeData[3]);
        if(requestedFormat != 0 && requestedFormat != this.format) {
            this.convert(requestedFormat);
        }
    }

    public Gdx2DPixmap(ByteBuffer pixelPtr, long[] nativeData) {
        super();
        this.nativeData = new long[4];
        this.pixelPtr = pixelPtr;
        this.basePtr = nativeData[0];
        this.width = ((int)nativeData[1]);
        this.height = ((int)nativeData[2]);
        this.format = ((int)nativeData[3]);
    }

    public void clear(int color) {
        Gdx2DPixmap.clear(this.basePtr, color);
    }

    private static native void clear(long arg0, int arg1) {
    }

    private void convert(int requestedFormat) {
        Gdx2DPixmap v0 = new Gdx2DPixmap(this.width, this.height, requestedFormat);
        v0.drawPixmap(this, 0, 0, 0, 0, this.width, this.height);
        this.dispose();
        this.basePtr = v0.basePtr;
        this.format = v0.format;
        this.height = v0.height;
        this.nativeData = v0.nativeData;
        this.pixelPtr = v0.pixelPtr;
        this.width = v0.width;
    }

    public void dispose() {
        Gdx2DPixmap.free(this.basePtr);
    }

    public void drawCircle(int x, int y, int radius, int color) {
        Gdx2DPixmap.drawCircle(this.basePtr, x, y, radius, color);
    }

    private static native void drawCircle(long arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {
        Gdx2DPixmap.drawLine(this.basePtr, x, y, x2, y2, color);
    }

    private static native void drawLine(long arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
    }

    public void drawPixmap(Gdx2DPixmap src, int srcX, int srcY, int dstX, int dstY, int width, int height) {
        Gdx2DPixmap.drawPixmap(src.basePtr, this.basePtr, srcX, srcY, width, height, dstX, dstY, width, height);
    }

    public void drawPixmap(Gdx2DPixmap src, int srcX, int srcY, int srcWidth, int srcHeight, int dstX, int dstY, int dstWidth, int dstHeight) {
        Gdx2DPixmap.drawPixmap(src.basePtr, this.basePtr, srcX, srcY, srcWidth, srcHeight, dstX, dstY, dstWidth, dstHeight);
    }

    private static native void drawPixmap(long arg0, long arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, int arg9) {
    }

    public void drawRect(int x, int y, int width, int height, int color) {
        Gdx2DPixmap.drawRect(this.basePtr, x, y, width, height, color);
    }

    private static native void drawRect(long arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
    }

    public void fillCircle(int x, int y, int radius, int color) {
        Gdx2DPixmap.fillCircle(this.basePtr, x, y, radius, color);
    }

    private static native void fillCircle(long arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public void fillRect(int x, int y, int width, int height, int color) {
        Gdx2DPixmap.fillRect(this.basePtr, x, y, width, height, color);
    }

    private static native void fillRect(long arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int color) {
        Gdx2DPixmap.fillTriangle(this.basePtr, x1, y1, x2, y2, x3, y3, color);
    }

    private static native void fillTriangle(long arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
    }

    private static native void free(long arg0) {
    }

    public static native String getFailureReason() {
    }

    public int getFormat() {
        return this.format;
    }

    public String getFormatString() {
        String v0;
        switch(this.format) {
            case 1: {
                v0 = "alpha";
                break;
            }
            case 2: {
                v0 = "luminance alpha";
                break;
            }
            case 3: {
                v0 = "rgb888";
                break;
            }
            case 4: {
                v0 = "rgba8888";
                break;
            }
            case 5: {
                v0 = "rgb565";
                break;
            }
            case 6: {
                v0 = "rgba4444";
                break;
            }
            default: {
                v0 = "unknown";
                break;
            }
        }

        return v0;
    }

    public int getGLFormat() {
        return this.getGLInternalFormat();
    }

    public int getGLInternalFormat() {
        switch(this.format) {
            case 1: {
                goto label_10;
            }
            case 2: {
                goto label_12;
            }
            case 3: 
            case 5: {
                goto label_14;
            }
            case 4: 
            case 6: {
                goto label_16;
            }
        }

        throw new GdxRuntimeException("unknown format: " + this.format);
    label_10:
        int v0 = 6406;
        goto label_11;
    label_12:
        v0 = 6410;
        goto label_11;
    label_14:
        v0 = 6407;
        goto label_11;
    label_16:
        v0 = 6408;
    label_11:
        return v0;
    }

    public int getGLType() {
        switch(this.format) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                goto label_10;
            }
            case 5: {
                goto label_12;
            }
            case 6: {
                goto label_14;
            }
        }

        throw new GdxRuntimeException("unknown format: " + this.format);
    label_10:
        int v0 = 5121;
        goto label_11;
    label_12:
        v0 = 33635;
        goto label_11;
    label_14:
        v0 = 32819;
    label_11:
        return v0;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPixel(int x, int y) {
        return Gdx2DPixmap.getPixel(this.basePtr, x, y);
    }

    private static native int getPixel(long arg0, int arg1, int arg2) {
    }

    public ByteBuffer getPixels() {
        return this.pixelPtr;
    }

    public int getWidth() {
        return this.width;
    }

    private static native ByteBuffer load(long[] arg0, byte[] arg1, int arg2, int arg3) {
    }

    private static native ByteBuffer newPixmap(long[] arg0, int arg1, int arg2, int arg3) {
    }

    public static Gdx2DPixmap newPixmap(int width, int height, int format) {  // has try-catch handlers
        Gdx2DPixmap v1;
        try {
            v1 = new Gdx2DPixmap(width, height, format);
        }
        catch(IllegalArgumentException v0) {
            v1 = null;
        }

        return v1;
    }

    public static Gdx2DPixmap newPixmap(InputStream in, int requestedFormat) {  // has try-catch handlers
        Gdx2DPixmap v1;
        try {
            v1 = new Gdx2DPixmap(in, requestedFormat);
        }
        catch(IOException v0) {
            v1 = null;
        }

        return v1;
    }

    public static native void setBlend(int arg0) {
    }

    public void setPixel(int x, int y, int color) {
        Gdx2DPixmap.setPixel(this.basePtr, x, y, color);
    }

    private static native void setPixel(long arg0, int arg1, int arg2, int arg3) {
    }

    public static native void setScale(int arg0) {
    }
}

