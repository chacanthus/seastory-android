// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

public class FreeType {
    public class Bitmap extends Pointer {
        Bitmap(long address) {
            super(address);
        }

        private static native ByteBuffer getBuffer(long arg0) {
        }

        public ByteBuffer getBuffer() {
            ByteBuffer v0;
            if(this.getRows() == 0) {
                v0 = BufferUtils.newByteBuffer(1);
            }
            else {
                v0 = Bitmap.getBuffer(this.address);
            }

            return v0;
        }

        private static native int getNumGray(long arg0) {
        }

        public int getNumGray() {
            return Bitmap.getNumGray(this.address);
        }

        private static native int getPitch(long arg0) {
        }

        public int getPitch() {
            return Bitmap.getPitch(this.address);
        }

        private static native int getPixelMode(long arg0) {
        }

        public int getPixelMode() {
            return Bitmap.getPixelMode(this.address);
        }

        public Pixmap getPixmap(Format format) {
            return this.getPixmap(format, Color.WHITE);
        }

        public Pixmap getPixmap(Format format, Color color) {
            Pixmap v5;
            int v9 = this.getWidth();
            ByteBuffer v6 = this.getBuffer();
            if(color == Color.WHITE) {
                v5 = new Pixmap(v9, this.getRows(), Format.Alpha);
                BufferUtils.copy(((Buffer)v6), v5.getPixels(), v5.getPixels().capacity());
            }
            else {
                v5 = new Pixmap(v9, this.getRows(), Format.RGBA8888);
                int v7 = this.getPitch();
                int v8 = Color.rgba8888(color);
                IntBuffer v4 = v5.getPixels().asIntBuffer();
                int v11;
                for(v11 = 0; v11 < this.getRows(); ++v11) {
                    int v12 = v11 * v7;
                    int v13 = v11 * v9;
                    int v10;
                    for(v10 = 0; v10 < v9; ++v10) {
                        v4.put(v13 + v10, v8 & -256 | (((int)((((float)((v8 & 255) * (v6.get(v12 + v10) & 255)))) / 255f))));
                    }
                }
            }

            Pixmap v3 = v5;
            if(format != v5.getFormat()) {
                v3 = new Pixmap(v5.getWidth(), v5.getHeight(), format);
                Blending v2 = Pixmap.getBlending();
                Pixmap.setBlending(Blending.None);
                v3.drawPixmap(v5, 0, 0);
                Pixmap.setBlending(v2);
                v5.dispose();
            }

            return v3;
        }

        private static native int getRows(long arg0) {
        }

        public int getRows() {
            return Bitmap.getRows(this.address);
        }

        private static native int getWidth(long arg0) {
        }

        public int getWidth() {
            return Bitmap.getWidth(this.address);
        }
    }

    public class Face extends Pointer implements Disposable {
        Library library;

        public Face(long address, Library library) {
            super(address);
            this.library = library;
        }

        public void dispose() {
            Face.doneFace(this.address);
            Object v0 = this.library.fontData.get(this.address);
            if(v0 != null) {
                this.library.fontData.remove(this.address);
                BufferUtils.disposeUnsafeByteBuffer(((ByteBuffer)v0));
            }
        }

        private static native void doneFace(long arg0) {
        }

        private static native int getAscender(long arg0) {
        }

        public int getAscender() {
            return Face.getAscender(this.address);
        }

        private static native int getCharIndex(long arg0, int arg1) {
        }

        public int getCharIndex(int charCode) {
            return Face.getCharIndex(this.address, charCode);
        }

        private static native int getDescender(long arg0) {
        }

        public int getDescender() {
            return Face.getDescender(this.address);
        }

        private static native int getFaceFlags(long arg0) {
        }

        public int getFaceFlags() {
            return Face.getFaceFlags(this.address);
        }

        private static native long getGlyph(long arg0) {
        }

        public GlyphSlot getGlyph() {
            return new GlyphSlot(Face.getGlyph(this.address));
        }

        private static native int getHeight(long arg0) {
        }

        public int getHeight() {
            return Face.getHeight(this.address);
        }

        private static native int getKerning(long arg0, int arg1, int arg2, int arg3) {
        }

        public int getKerning(int leftGlyph, int rightGlyph, int kernMode) {
            return Face.getKerning(this.address, leftGlyph, rightGlyph, kernMode);
        }

        private static native int getMaxAdvanceHeight(long arg0) {
        }

        public int getMaxAdvanceHeight() {
            return Face.getMaxAdvanceHeight(this.address);
        }

        private static native int getMaxAdvanceWidth(long arg0) {
        }

        public int getMaxAdvanceWidth() {
            return Face.getMaxAdvanceWidth(this.address);
        }

        private static native int getNumGlyphs(long arg0) {
        }

        public int getNumGlyphs() {
            return Face.getNumGlyphs(this.address);
        }

        private static native long getSize(long arg0) {
        }

        public Size getSize() {
            return new Size(Face.getSize(this.address));
        }

        private static native int getStyleFlags(long arg0) {
        }

        public int getStyleFlags() {
            return Face.getStyleFlags(this.address);
        }

        private static native int getUnderlinePosition(long arg0) {
        }

        public int getUnderlinePosition() {
            return Face.getUnderlinePosition(this.address);
        }

        private static native int getUnderlineThickness(long arg0) {
        }

        public int getUnderlineThickness() {
            return Face.getUnderlineThickness(this.address);
        }

        private static native boolean hasKerning(long arg0) {
        }

        public boolean hasKerning() {
            return Face.hasKerning(this.address);
        }

        private static native boolean loadChar(long arg0, int arg1, int arg2) {
        }

        public boolean loadChar(int charCode, int loadFlags) {
            return Face.loadChar(this.address, charCode, loadFlags);
        }

        private static native boolean loadGlyph(long arg0, int arg1, int arg2) {
        }

        public boolean loadGlyph(int glyphIndex, int loadFlags) {
            return Face.loadGlyph(this.address, glyphIndex, loadFlags);
        }

        private static native boolean selectSize(long arg0, int arg1) {
        }

        public boolean selectSize(int strikeIndex) {
            return Face.selectSize(this.address, strikeIndex);
        }

        private static native boolean setCharSize(long arg0, int arg1, int arg2, int arg3, int arg4) {
        }

        public boolean setCharSize(int charWidth, int charHeight, int horzResolution, int vertResolution) {
            return Face.setCharSize(this.address, charWidth, charHeight, horzResolution, vertResolution);
        }

        private static native boolean setPixelSizes(long arg0, int arg1, int arg2) {
        }

        public boolean setPixelSizes(int pixelWidth, int pixelHeight) {
            return Face.setPixelSizes(this.address, pixelWidth, pixelHeight);
        }
    }

    public class Glyph extends Pointer implements Disposable {
        private boolean rendered;

        Glyph(long address) {
            super(address);
        }

        public void dispose() {
            Glyph.done(this.address);
        }

        private static native void done(long arg0) {
        }

        private static native long getBitmap(long arg0) {
        }

        public Bitmap getBitmap() {
            if(!this.rendered) {
                throw new GdxRuntimeException("Glyph is not yet rendered");
            }

            return new Bitmap(Glyph.getBitmap(this.address));
        }

        private static native int getLeft(long arg0) {
        }

        public int getLeft() {
            if(!this.rendered) {
                throw new GdxRuntimeException("Glyph is not yet rendered");
            }

            return Glyph.getLeft(this.address);
        }

        private static native int getTop(long arg0) {
        }

        public int getTop() {
            if(!this.rendered) {
                throw new GdxRuntimeException("Glyph is not yet rendered");
            }

            return Glyph.getTop(this.address);
        }

        private static native long strokeBorder(long arg0, long arg1, boolean arg2) {
        }

        public void strokeBorder(Stroker stroker, boolean inside) {
            this.address = Glyph.strokeBorder(this.address, stroker.address, inside);
        }

        private static native long toBitmap(long arg0, int arg1) {
        }

        public void toBitmap(int renderMode) {
            long v0 = Glyph.toBitmap(this.address, renderMode);
            if(v0 == 0) {
                throw new GdxRuntimeException("Couldn\'t render glyph");
            }

            this.address = v0;
            this.rendered = true;
        }
    }

    public class GlyphMetrics extends Pointer {
        GlyphMetrics(long address) {
            super(address);
        }

        private static native int getHeight(long arg0) {
        }

        public int getHeight() {
            return GlyphMetrics.getHeight(this.address);
        }

        private static native int getHoriAdvance(long arg0) {
        }

        public int getHoriAdvance() {
            return GlyphMetrics.getHoriAdvance(this.address);
        }

        private static native int getHoriBearingX(long arg0) {
        }

        public int getHoriBearingX() {
            return GlyphMetrics.getHoriBearingX(this.address);
        }

        private static native int getHoriBearingY(long arg0) {
        }

        public int getHoriBearingY() {
            return GlyphMetrics.getHoriBearingY(this.address);
        }

        private static native int getVertAdvance(long arg0) {
        }

        public int getVertAdvance() {
            return GlyphMetrics.getVertAdvance(this.address);
        }

        private static native int getVertBearingX(long arg0) {
        }

        public int getVertBearingX() {
            return GlyphMetrics.getVertBearingX(this.address);
        }

        private static native int getVertBearingY(long arg0) {
        }

        public int getVertBearingY() {
            return GlyphMetrics.getVertBearingY(this.address);
        }

        private static native int getWidth(long arg0) {
        }

        public int getWidth() {
            return GlyphMetrics.getWidth(this.address);
        }
    }

    public class GlyphSlot extends Pointer {
        GlyphSlot(long address) {
            super(address);
        }

        private static native int getAdvanceX(long arg0) {
        }

        public int getAdvanceX() {
            return GlyphSlot.getAdvanceX(this.address);
        }

        private static native int getAdvanceY(long arg0) {
        }

        public int getAdvanceY() {
            return GlyphSlot.getAdvanceY(this.address);
        }

        private static native long getBitmap(long arg0) {
        }

        public Bitmap getBitmap() {
            return new Bitmap(GlyphSlot.getBitmap(this.address));
        }

        private static native int getBitmapLeft(long arg0) {
        }

        public int getBitmapLeft() {
            return GlyphSlot.getBitmapLeft(this.address);
        }

        private static native int getBitmapTop(long arg0) {
        }

        public int getBitmapTop() {
            return GlyphSlot.getBitmapTop(this.address);
        }

        private static native int getFormat(long arg0) {
        }

        public int getFormat() {
            return GlyphSlot.getFormat(this.address);
        }

        private static native long getGlyph(long arg0) {
        }

        public Glyph getGlyph() {
            long v0 = GlyphSlot.getGlyph(this.address);
            if(v0 == 0) {
                throw new GdxRuntimeException("Couldn\'t get glyph");
            }

            return new Glyph(v0);
        }

        private static native int getLinearHoriAdvance(long arg0) {
        }

        public int getLinearHoriAdvance() {
            return GlyphSlot.getLinearHoriAdvance(this.address);
        }

        private static native int getLinearVertAdvance(long arg0) {
        }

        public int getLinearVertAdvance() {
            return GlyphSlot.getLinearVertAdvance(this.address);
        }

        private static native long getMetrics(long arg0) {
        }

        public GlyphMetrics getMetrics() {
            return new GlyphMetrics(GlyphSlot.getMetrics(this.address));
        }

        private static native boolean renderGlyph(long arg0, int arg1) {
        }

        public boolean renderGlyph(int renderMode) {
            return GlyphSlot.renderGlyph(this.address, renderMode);
        }
    }

    public class Library extends Pointer implements Disposable {
        LongMap fontData;

        Library(long address) {
            super(address);
            this.fontData = new LongMap();
        }

        public Stroker createStroker() {
            long v0 = Library.strokerNew(this.address);
            if(v0 == 0) {
                throw new GdxRuntimeException("Couldn\'t create FreeType stroker");
            }

            return new Stroker(v0);
        }

        public void dispose() {
            Library.doneFreeType(this.address);
            Iterator v1 = this.fontData.values().iterator();
            while(v1.hasNext()) {
                BufferUtils.disposeUnsafeByteBuffer(v1.next());
            }
        }

        private static native void doneFreeType(long arg0) {
        }

        public Face newFace(FileHandle font, int faceIndex) {
            byte[] v0 = font.readBytes();
            return this.newMemoryFace(v0, v0.length, faceIndex);
        }

        private static native long newMemoryFace(long arg0, ByteBuffer arg1, int arg2, int arg3) {
        }

        public Face newMemoryFace(byte[] data, int dataSize, int faceIndex) {
            ByteBuffer v0 = BufferUtils.newUnsafeByteBuffer(data.length);
            BufferUtils.copy(data, 0, ((Buffer)v0), data.length);
            return this.newMemoryFace(v0, faceIndex);
        }

        public Face newMemoryFace(ByteBuffer buffer, int faceIndex) {
            long v0 = Library.newMemoryFace(this.address, buffer, buffer.remaining(), faceIndex);
            if(v0 == 0) {
                BufferUtils.disposeUnsafeByteBuffer(buffer);
                throw new GdxRuntimeException("Couldn\'t load font");
            }

            this.fontData.put(v0, buffer);
            return new Face(v0, this);
        }

        private static native long strokerNew(long arg0) {
        }
    }

    class Pointer {
        long address;

        Pointer(long address) {
            super();
            this.address = address;
        }
    }

    public class Size extends Pointer {
        Size(long address) {
            super(address);
        }

        private static native long getMetrics(long arg0) {
        }

        public SizeMetrics getMetrics() {
            return new SizeMetrics(Size.getMetrics(this.address));
        }
    }

    public class SizeMetrics extends Pointer {
        SizeMetrics(long address) {
            super(address);
        }

        private static native int getAscender(long arg0) {
        }

        public int getAscender() {
            return SizeMetrics.getAscender(this.address);
        }

        private static native int getDescender(long arg0) {
        }

        public int getDescender() {
            return SizeMetrics.getDescender(this.address);
        }

        private static native int getHeight(long arg0) {
        }

        public int getHeight() {
            return SizeMetrics.getHeight(this.address);
        }

        private static native int getMaxAdvance(long arg0) {
        }

        public int getMaxAdvance() {
            return SizeMetrics.getMaxAdvance(this.address);
        }

        public int getXScale() {
            return SizeMetrics.getXscale(this.address);
        }

        private static native int getXppem(long arg0) {
        }

        public int getXppem() {
            return SizeMetrics.getXppem(this.address);
        }

        private static native int getXscale(long arg0) {
        }

        private static native int getYppem(long arg0) {
        }

        public int getYppem() {
            return SizeMetrics.getYppem(this.address);
        }

        private static native int getYscale(long arg0) {
        }

        public int getYscale() {
            return SizeMetrics.getYscale(this.address);
        }
    }

    public class Stroker extends Pointer implements Disposable {
        Stroker(long address) {
            super(address);
        }

        public void dispose() {
            Stroker.done(this.address);
        }

        private static native void done(long arg0) {
        }

        private static native void set(long arg0, int arg1, int arg2, int arg3, int arg4) {
        }

        public void set(int radius, int lineCap, int lineJoin, int miterLimit) {
            Stroker.set(this.address, radius, lineCap, lineJoin, miterLimit);
        }
    }

    public static int FT_ENCODING_ADOBE_CUSTOM;
    public static int FT_ENCODING_ADOBE_EXPERT;
    public static int FT_ENCODING_ADOBE_LATIN_1;
    public static int FT_ENCODING_ADOBE_STANDARD;
    public static int FT_ENCODING_APPLE_ROMAN;
    public static int FT_ENCODING_BIG5;
    public static int FT_ENCODING_GB2312;
    public static int FT_ENCODING_JOHAB;
    public static int FT_ENCODING_MS_SYMBOL;
    public static int FT_ENCODING_NONE;
    public static int FT_ENCODING_OLD_LATIN_2;
    public static int FT_ENCODING_SJIS;
    public static int FT_ENCODING_UNICODE;
    public static int FT_ENCODING_WANSUNG;
    public static int FT_FACE_FLAG_CID_KEYED;
    public static int FT_FACE_FLAG_EXTERNAL_STREAM;
    public static int FT_FACE_FLAG_FAST_GLYPHS;
    public static int FT_FACE_FLAG_FIXED_SIZES;
    public static int FT_FACE_FLAG_FIXED_WIDTH;
    public static int FT_FACE_FLAG_GLYPH_NAMES;
    public static int FT_FACE_FLAG_HINTER;
    public static int FT_FACE_FLAG_HORIZONTAL;
    public static int FT_FACE_FLAG_KERNING;
    public static int FT_FACE_FLAG_MULTIPLE_MASTERS;
    public static int FT_FACE_FLAG_SCALABLE;
    public static int FT_FACE_FLAG_SFNT;
    public static int FT_FACE_FLAG_TRICKY;
    public static int FT_FACE_FLAG_VERTICAL;
    public static int FT_KERNING_DEFAULT;
    public static int FT_KERNING_UNFITTED;
    public static int FT_KERNING_UNSCALED;
    public static int FT_LOAD_CROP_BITMAP;
    public static int FT_LOAD_DEFAULT;
    public static int FT_LOAD_FORCE_AUTOHINT;
    public static int FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH;
    public static int FT_LOAD_IGNORE_TRANSFORM;
    public static int FT_LOAD_LINEAR_DESIGN;
    public static int FT_LOAD_MONOCHROME;
    public static int FT_LOAD_NO_AUTOHINT;
    public static int FT_LOAD_NO_BITMAP;
    public static int FT_LOAD_NO_HINTING;
    public static int FT_LOAD_NO_RECURSE;
    public static int FT_LOAD_NO_SCALE;
    public static int FT_LOAD_PEDANTIC;
    public static int FT_LOAD_RENDER;
    public static int FT_LOAD_VERTICAL_LAYOUT;
    public static int FT_PIXEL_MODE_GRAY;
    public static int FT_PIXEL_MODE_GRAY2;
    public static int FT_PIXEL_MODE_GRAY4;
    public static int FT_PIXEL_MODE_LCD;
    public static int FT_PIXEL_MODE_LCD_V;
    public static int FT_PIXEL_MODE_MONO;
    public static int FT_PIXEL_MODE_NONE;
    public static int FT_RENDER_MODE_LCD;
    public static int FT_RENDER_MODE_LCD_V;
    public static int FT_RENDER_MODE_LIGHT;
    public static int FT_RENDER_MODE_MAX;
    public static int FT_RENDER_MODE_MONO;
    public static int FT_RENDER_MODE_NORMAL;
    public static int FT_STROKER_LINECAP_BUTT;
    public static int FT_STROKER_LINECAP_ROUND;
    public static int FT_STROKER_LINECAP_SQUARE;
    public static int FT_STROKER_LINEJOIN_BEVEL;
    public static int FT_STROKER_LINEJOIN_MITER;
    public static int FT_STROKER_LINEJOIN_MITER_FIXED;
    public static int FT_STROKER_LINEJOIN_MITER_VARIABLE;
    public static int FT_STROKER_LINEJOIN_ROUND;
    public static int FT_STYLE_FLAG_BOLD;
    public static int FT_STYLE_FLAG_ITALIC;

    static  {
        FreeType.FT_PIXEL_MODE_NONE = 0;
        FreeType.FT_PIXEL_MODE_MONO = 1;
        FreeType.FT_PIXEL_MODE_GRAY = 2;
        FreeType.FT_PIXEL_MODE_GRAY2 = 3;
        FreeType.FT_PIXEL_MODE_GRAY4 = 4;
        FreeType.FT_PIXEL_MODE_LCD = 5;
        FreeType.FT_PIXEL_MODE_LCD_V = 6;
        FreeType.FT_ENCODING_NONE = 0;
        FreeType.FT_ENCODING_MS_SYMBOL = FreeType.encode('s', 'y', 'm', 'b');
        FreeType.FT_ENCODING_UNICODE = FreeType.encode('u', 'n', 'i', 'c');
        FreeType.FT_ENCODING_SJIS = FreeType.encode('s', 'j', 'i', 's');
        FreeType.FT_ENCODING_GB2312 = FreeType.encode('g', 'b', ' ', ' ');
        FreeType.FT_ENCODING_BIG5 = FreeType.encode('b', 'i', 'g', '5');
        FreeType.FT_ENCODING_WANSUNG = FreeType.encode('w', 'a', 'n', 's');
        FreeType.FT_ENCODING_JOHAB = FreeType.encode('j', 'o', 'h', 'a');
        FreeType.FT_ENCODING_ADOBE_STANDARD = FreeType.encode('A', 'D', 'O', 'B');
        FreeType.FT_ENCODING_ADOBE_EXPERT = FreeType.encode('A', 'D', 'B', 'E');
        FreeType.FT_ENCODING_ADOBE_CUSTOM = FreeType.encode('A', 'D', 'B', 'C');
        FreeType.FT_ENCODING_ADOBE_LATIN_1 = FreeType.encode('l', 'a', 't', '1');
        FreeType.FT_ENCODING_OLD_LATIN_2 = FreeType.encode('l', 'a', 't', '2');
        FreeType.FT_ENCODING_APPLE_ROMAN = FreeType.encode('a', 'r', 'm', 'n');
        FreeType.FT_FACE_FLAG_SCALABLE = 1;
        FreeType.FT_FACE_FLAG_FIXED_SIZES = 2;
        FreeType.FT_FACE_FLAG_FIXED_WIDTH = 4;
        FreeType.FT_FACE_FLAG_SFNT = 8;
        FreeType.FT_FACE_FLAG_HORIZONTAL = 16;
        FreeType.FT_FACE_FLAG_VERTICAL = 32;
        FreeType.FT_FACE_FLAG_KERNING = 64;
        FreeType.FT_FACE_FLAG_FAST_GLYPHS = 128;
        FreeType.FT_FACE_FLAG_MULTIPLE_MASTERS = 256;
        FreeType.FT_FACE_FLAG_GLYPH_NAMES = 512;
        FreeType.FT_FACE_FLAG_EXTERNAL_STREAM = 1024;
        FreeType.FT_FACE_FLAG_HINTER = 2048;
        FreeType.FT_FACE_FLAG_CID_KEYED = 4096;
        FreeType.FT_FACE_FLAG_TRICKY = 8192;
        FreeType.FT_STYLE_FLAG_ITALIC = 1;
        FreeType.FT_STYLE_FLAG_BOLD = 2;
        FreeType.FT_LOAD_DEFAULT = 0;
        FreeType.FT_LOAD_NO_SCALE = 1;
        FreeType.FT_LOAD_NO_HINTING = 2;
        FreeType.FT_LOAD_RENDER = 4;
        FreeType.FT_LOAD_NO_BITMAP = 8;
        FreeType.FT_LOAD_VERTICAL_LAYOUT = 16;
        FreeType.FT_LOAD_FORCE_AUTOHINT = 32;
        FreeType.FT_LOAD_CROP_BITMAP = 64;
        FreeType.FT_LOAD_PEDANTIC = 128;
        FreeType.FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = 512;
        FreeType.FT_LOAD_NO_RECURSE = 1024;
        FreeType.FT_LOAD_IGNORE_TRANSFORM = 2048;
        FreeType.FT_LOAD_MONOCHROME = 4096;
        FreeType.FT_LOAD_LINEAR_DESIGN = 8192;
        FreeType.FT_LOAD_NO_AUTOHINT = 32768;
        FreeType.FT_RENDER_MODE_NORMAL = 0;
        FreeType.FT_RENDER_MODE_LIGHT = 1;
        FreeType.FT_RENDER_MODE_MONO = 2;
        FreeType.FT_RENDER_MODE_LCD = 3;
        FreeType.FT_RENDER_MODE_LCD_V = 4;
        FreeType.FT_RENDER_MODE_MAX = 5;
        FreeType.FT_KERNING_DEFAULT = 0;
        FreeType.FT_KERNING_UNFITTED = 1;
        FreeType.FT_KERNING_UNSCALED = 2;
        FreeType.FT_STROKER_LINECAP_BUTT = 0;
        FreeType.FT_STROKER_LINECAP_ROUND = 1;
        FreeType.FT_STROKER_LINECAP_SQUARE = 2;
        FreeType.FT_STROKER_LINEJOIN_ROUND = 0;
        FreeType.FT_STROKER_LINEJOIN_BEVEL = 1;
        FreeType.FT_STROKER_LINEJOIN_MITER_VARIABLE = 2;
        FreeType.FT_STROKER_LINEJOIN_MITER = FreeType.FT_STROKER_LINEJOIN_MITER_VARIABLE;
        FreeType.FT_STROKER_LINEJOIN_MITER_FIXED = 3;
    }

    public FreeType() {
        super();
    }

    private static int encode(char a, char b, char c, char d) {
        return a << 24 | b << 16 | c << 8 | d;
    }

    public static Library initFreeType() {
        new SharedLibraryLoader().load("gdx-freetype");
        long v0 = FreeType.initFreeTypeJni();
        if(v0 == 0) {
            throw new GdxRuntimeException("Couldn\'t initialize FreeType library");
        }

        return new Library(v0);
    }

    private static native long initFreeTypeJni() {
    }

    public static int toInt(int value) {
        int v0;
        if(value < 0) {
            v0 = value - 32 >> 6;
        }
        else {
            v0 = value + 32 >> 6;
        }

        return v0;
    }
}

