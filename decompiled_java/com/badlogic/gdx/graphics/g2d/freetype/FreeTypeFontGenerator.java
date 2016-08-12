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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont$BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont$Glyph;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker$Page;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class FreeTypeFontGenerator implements Disposable {
    public class FreeTypeBitmapFontData extends BitmapFontData implements Disposable {
        FreeTypeFontGenerator generator;
        Array glyphs;
        String packPrefix;
        PixmapPacker packer;
        FreeTypeFontParameter parameter;
        Array regions;
        Stroker stroker;

        public FreeTypeBitmapFontData() {
            super();
        }

        public void dispose() {
            if(this.stroker != null) {
                this.stroker.dispose();
            }

            if(this.packer != null) {
                this.packer.dispose();
            }
        }

        public Glyph getGlyph(char ch) {
            Glyph v2;
            Glyph v11 = super.getGlyph(ch);
            if(v11 != null || this.generator == null || ch == 0) {
            label_87:
                v2 = v11;
            }
            else {
                this.generator.setPixelSizes(0, this.parameter.size);
                v11 = this.generator.createGlyph(ch, this, this.parameter, this.stroker, (this.ascent + this.capHeight) / this.scaleY, this.packPrefix, this.packer);
                if(v11 == null) {
                    v2 = null;
                }
                else {
                    this.setGlyph(ch, v11);
                    this.setGlyphRegion(v11, this.regions.get(v11.page));
                    this.glyphs.add(v11);
                    if(this.parameter.kerning) {
                        Face v10 = this.generator.face;
                        int v12 = v10.getCharIndex(ch);
                        int v13 = 0;
                        int v15 = this.glyphs.size;
                        while(true) {
                            if(v13 < v15) {
                                Object v16 = this.glyphs.get(v13);
                                int v17 = v10.getCharIndex(v16.id);
                                int v14 = v10.getKerning(v12, v17, 0);
                                if(v14 != 0) {
                                    v11.setKerning(v16.id, FreeType.toInt(v14));
                                }

                                v14 = v10.getKerning(v17, v12, 0);
                                if(v14 != 0) {
                                    v16.setKerning(ch, FreeType.toInt(v14));
                                }

                                ++v13;
                                continue;
                            }
                            else {
                                goto label_87;
                            }
                        }
                    }
                    else {
                        goto label_87;
                    }
                }
            }

            return v2;
        }
    }

    public class FreeTypeFontParameter {
        public boolean borderStraight;
        public String characters;
        public boolean incremental;
        public boolean kerning;
        public PixmapPacker packer;
        public int size;

        public FreeTypeFontParameter() {
            super();
            this.size = 16;
            this.color = Color.WHITE;
            this.borderWidth = 0f;
            this.borderColor = Color.BLACK;
            this.borderStraight = false;
            this.shadowOffsetX = 0;
            this.shadowOffsetY = 0;
            this.shadowColor = new Color(0f, 0f, 0f, 0.75f);
            this.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?\'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u007F\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F?��??��??�ס�?��??����?�ơ�������?�ҡ�������?��������??????��?????????��??????����?????����??????��?????????��??????����?????��?";
            this.kerning = true;
            this.packer = null;
            this.flip = false;
            this.genMipMaps = false;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
        }
    }

    public class GlyphAndBitmap {
        public Bitmap bitmap;
        public Glyph glyph;

        public GlyphAndBitmap(FreeTypeFontGenerator arg1) {
            FreeTypeFontGenerator.this = arg1;
            super();
        }
    }

    public static final String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?\'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u007F\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F?��??��??�ס�?��??����?�ơ�������?�ҡ�������?��������??????��?????????��??????����?????����??????��?????????��??????����?????��?";
    public static final int NO_MAXIMUM = -1;
    boolean bitmapped;
    final Face face;
    final Library library;
    private static int maxTextureSize;
    final String name;
    private int pixelHeight;
    private int pixelWidth;

    static  {
        FreeTypeFontGenerator.maxTextureSize = 1024;
    }

    public FreeTypeFontGenerator(FileHandle font) {  // has try-catch handlers
        ByteBuffer v0;
        int v5;
        super();
        this.bitmapped = false;
        this.name = font.pathWithoutExtension();
        int v3 = ((int)font.length());
        this.library = FreeType.initFreeType();
        if(this.library == null) {
            throw new GdxRuntimeException("Couldn\'t initialize FreeType");
        }

        InputStream v4 = font.read();
        if(v3 == 0) {
            if(v3 > 0) {
                v5 = ((int)((((float)v3)) * 1.5f));
            }
            else {
                v5 = 16384;
            }

            try {
                byte[] v1 = StreamUtils.copyStreamToByteArray(v4, v5);
                v0 = BufferUtils.newUnsafeByteBuffer(v1.length);
                BufferUtils.copy(v1, 0, ((Buffer)v0), v1.length);
                goto label_27;
            label_42:
                v0 = BufferUtils.newUnsafeByteBuffer(v3);
                StreamUtils.copyStream(v4, v0);
            }
            catch(Throwable v5_1) {
            }
            catch(IOException v2) {
                try {
                    throw new GdxRuntimeException(((Throwable)v2));
                }
                catch(Throwable v5_1) {
                    StreamUtils.closeQuietly(((Closeable)v4));
                    throw v5_1;
                }
            }
        }
        else {
            goto label_42;
        }

    label_27:
        StreamUtils.closeQuietly(((Closeable)v4));
        this.face = this.library.newMemoryFace(v0, 0);
        if(this.face == null) {
            throw new GdxRuntimeException("Couldn\'t create face for font: " + font);
        }

        if(!this.checkForBitmapFont()) {
            this.setPixelSizes(0, 15);
        }
    }

    private boolean checkForBitmapFont() {
        if((this.face.getFaceFlags() & FreeType.FT_FACE_FLAG_FIXED_SIZES) == FreeType.FT_FACE_FLAG_FIXED_SIZES && (this.face.getFaceFlags() & FreeType.FT_FACE_FLAG_HORIZONTAL) == FreeType.FT_FACE_FLAG_HORIZONTAL && (this.face.loadChar(32, FreeType.FT_LOAD_DEFAULT)) && this.face.getGlyph().getFormat() == 1651078259) {
            this.bitmapped = true;
        }

        return this.bitmapped;
    }

    Glyph createGlyph(char c, FreeTypeBitmapFontData data, FreeTypeFontParameter parameter, Stroker stroker, float baseLine, String packPrefix, PixmapPacker packer) {  // has try-catch handlers
        int v3;
        Glyph v21;
        int v28;
        if(this.face.getCharIndex(c) == 0) {
            v28 = 1;
        }
        else {
            v28 = 0;
        }

        if(v28 != 0) {
            v21 = data.getGlyph('\u0000');
            if(v21 == null) {
                goto label_14;
            }

            goto label_11;
        }

    label_14:
        if(this.face.loadChar(c, FreeType.FT_LOAD_DEFAULT)) {
            goto label_33;
        }

        Gdx.app.log("FreeTypeFontGenerator", "Couldn\'t load char \'" + c + "\'");
        v21 = null;
        goto label_11;
    label_33:
        GlyphSlot v34 = this.face.getGlyph();
        com.badlogic.gdx.graphics.g2d.freetype.FreeType$Glyph v25 = v34.getGlyph();
        try {
            v25.toBitmap(FreeType.FT_RENDER_MODE_NORMAL);
        }
        catch(GdxRuntimeException v20) {
            v25.dispose();
            Gdx.app.log("FreeTypeFontGenerator", "Couldn\'t render char \'" + c + "\'");
            v21 = null;
            goto label_11;
        }

        Bitmap v24 = v25.getBitmap();
        Pixmap v26 = v24.getPixmap(Format.RGBA8888, parameter.color);
        if(parameter.borderWidth > 0f || parameter.shadowOffsetX != 0 || parameter.shadowOffsetY != 0) {
            Bitmap v15 = v24;
            if(parameter.borderWidth > 0f) {
                com.badlogic.gdx.graphics.g2d.freetype.FreeType$Glyph v16 = v34.getGlyph();
                v16.strokeBorder(stroker, false);
                v16.toBitmap(FreeType.FT_RENDER_MODE_NORMAL);
                v15 = v16.getBitmap();
                Pixmap v17 = v15.getPixmap(Format.RGBA8888, parameter.borderColor);
                v17.drawPixmap(v26, v25.getLeft() - v16.getLeft(), -(v25.getTop() - v16.getTop()));
                v26.dispose();
                v25.dispose();
                v26 = v17;
                v25 = v16;
            }

            if(parameter.shadowOffsetX == 0 && parameter.shadowOffsetY == 0) {
                goto label_143;
            }

            Pixmap v33 = v15.getPixmap(Format.RGBA8888, parameter.shadowColor);
            Pixmap v32 = new Pixmap(v33.getWidth() + Math.abs(parameter.shadowOffsetX), v33.getHeight() + Math.abs(parameter.shadowOffsetY), Format.RGBA8888);
            Blending v14 = Pixmap.getBlending();
            Pixmap.setBlending(Blending.None);
            v32.drawPixmap(v33, Math.max(parameter.shadowOffsetX, 0), Math.max(parameter.shadowOffsetY, 0));
            Pixmap.setBlending(v14);
            v32.drawPixmap(v26, Math.max(-parameter.shadowOffsetX, 0), Math.max(-parameter.shadowOffsetY, 0));
            v26.dispose();
            v26 = v32;
        }

    label_143:
        GlyphMetrics v27 = v34.getMetrics();
        v21 = new Glyph();
        v21.id = c;
        v21.width = v26.getWidth();
        v21.height = v26.getHeight();
        v21.xoffset = v25.getLeft();
        if(parameter.flip) {
            v3 = -v25.getTop() + (((int)baseLine));
        }
        else {
            v3 = -(v21.height - v25.getTop()) - (((int)baseLine));
        }

        v21.yoffset = v3;
        v21.xadvance = FreeType.toInt(v27.getHoriAdvance()) + (((int)parameter.borderWidth));
        if(this.bitmapped) {
            v26.setColor(Color.CLEAR);
            v26.fill();
            ByteBuffer v18 = v24.getBuffer();
            int v37 = Color.WHITE.toIntBits();
            int v19 = Color.CLEAR.toIntBits();
            int v22;
            for(v22 = 0; v22 < v21.height; ++v22) {
                int v23 = v22 * v24.getPitch();
                int v36;
                for(v36 = 0; v36 < v21.width + v21.xoffset; ++v36) {
                    if((v18.get(v36 / 8 + v23) >>> 7 - v36 % 8 & 1) == 1) {
                        v3 = v37;
                    }
                    else {
                        v3 = v19;
                    }

                    v26.drawPixel(v36, v22, v3);
                }
            }
        }

        String v29 = packPrefix + c;
        Rectangle v31 = packer.pack(v29, v26);
        int v30 = packer.getPageIndex(v29);
        if(v30 == -1) {
            throw new IllegalStateException("Packer was not able to insert glyph into a page: " + v29);
        }

        v21.page = v30;
        v21.srcX = ((int)v31.x);
        v21.srcY = ((int)v31.y);
        if(data.regions != null) {
            if(v30 >= data.regions.size) {
                data.regions.add(this.createRegion(packer.getPages().get(v30), parameter));
            }
            else {
                Texture v35 = data.regions.get(v30).getTexture();
                v35.bind();
                Gdx.gl.glTexSubImage2D(v35.glTarget, 0, v21.srcX, v21.srcY, v21.width, v21.height, v26.getGLFormat(), v26.getGLType(), v26.getPixels());
            }
        }

        v26.dispose();
        v25.dispose();
        if(v28 == 0) {
            goto label_11;
        }

        data.setGlyph(0, v21);
    label_11:
        return v21;
    }

    private TextureRegion createRegion(Page p, FreeTypeFontParameter parameter) {
        com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator$1 v6 = new Texture() {
            public void dispose() {
                super.dispose();
                this.getTextureData().consumePixmap().dispose();
            }
        };
        ((Texture)v6).setFilter(parameter.minFilter, parameter.magFilter);
        return new TextureRegion(((Texture)v6));
    }

    public void dispose() {
        this.face.dispose();
        this.library.dispose();
    }

    public FreeTypeBitmapFontData generateData(int size) {
        return this.generateData(size, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?\'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u007F\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F?��??��??�ס�?��??����?�ơ�������?�ҡ�������?��������??????��?????????��??????����?????����??????��?????????��??????����?????��?", false, null);
    }

    public FreeTypeBitmapFontData generateData(int size, String characters, boolean flip, PixmapPacker packer) {
        FreeTypeFontParameter v0 = new FreeTypeFontParameter();
        v0.size = size;
        v0.characters = characters;
        v0.flip = flip;
        v0.packer = packer;
        return this.generateData(v0);
    }

    public FreeTypeBitmapFontData generateData(int size, String characters, boolean flip) {
        return this.generateData(size, characters, flip, null);
    }

    public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter) {
        return this.generateData(parameter, new FreeTypeBitmapFontData());
    }

    public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter, FreeTypeBitmapFontData data) {
        int v6_1;
        int v4_3;
        String v4_1;
        String v10;
        int v3;
        float v4;
        if(parameter == null) {
            parameter = new FreeTypeFontParameter();
        }

        String v14 = parameter.characters;
        int v15 = v14.length();
        boolean v23 = parameter.incremental;
        this.setPixelSizes(0, parameter.size);
        SizeMetrics v19 = this.face.getSize().getMetrics();
        data.flipped = parameter.flip;
        data.ascent = ((float)FreeType.toInt(v19.getAscender()));
        data.descent = ((float)FreeType.toInt(v19.getDescender()));
        data.lineHeight = ((float)FreeType.toInt(v19.getHeight()));
        float v9 = data.ascent;
        if((this.bitmapped) && data.lineHeight == 0f) {
            int v5;
            for(v5 = 32; v5 < this.face.getNumGlyphs() + 32; ++v5) {
                if(this.face.loadChar(v5, FreeType.FT_LOAD_DEFAULT)) {
                    int v27 = FreeType.toInt(this.face.getGlyph().getMetrics().getHeight());
                    if((((float)v27)) > data.lineHeight) {
                        v4 = ((float)v27);
                    }
                    else {
                        v4 = data.lineHeight;
                    }

                    data.lineHeight = v4;
                }
            }
        }

        if(this.face.loadChar(32, FreeType.FT_LOAD_DEFAULT)) {
            data.spaceWidth = ((float)FreeType.toInt(this.face.getGlyph().getMetrics().getHoriAdvance()));
        }
        else {
            data.spaceWidth = ((float)this.face.getMaxAdvanceWidth());
        }

        Glyph v34 = new Glyph();
        v34.xadvance = ((int)data.spaceWidth);
        v34.id = 32;
        data.setGlyph(32, v34);
        char[] v12 = data.xChars;
        int v26 = v12.length;
        int v22 = 0;
        while(true) {
            if(v22 < v26) {
                if(!this.face.loadChar(v12[v22], FreeType.FT_LOAD_DEFAULT)) {
                    ++v22;
                    continue;
                }
                else {
                    break;
                }
            }

            goto label_137;
        }

        data.xHeight = ((float)FreeType.toInt(this.face.getGlyph().getMetrics().getHeight()));
    label_137:
        if(data.xHeight == 0f) {
            throw new GdxRuntimeException("No x-height character found in font");
        }

        v12 = data.capChars;
        v26 = v12.length;
        v22 = 0;
        while(true) {
            if(v22 < v26) {
                if(!this.face.loadChar(v12[v22], FreeType.FT_LOAD_DEFAULT)) {
                    ++v22;
                    continue;
                }
                else {
                    break;
                }
            }

            goto label_169;
        }

        data.capHeight = ((float)FreeType.toInt(this.face.getGlyph().getMetrics().getHeight()));
    label_169:
        if(!this.bitmapped && data.capHeight == 1f) {
            throw new GdxRuntimeException("No cap character found in font");
        }

        data.ascent -= data.capHeight;
        data.down = -data.lineHeight;
        if(parameter.flip) {
            data.ascent = -data.ascent;
            data.down = -data.down;
        }

        int v29 = 0;
        PixmapPacker v2 = parameter.packer;
        if(v2 == null) {
            if(v23) {
                v3 = FreeTypeFontGenerator.maxTextureSize;
            }
            else {
                int v28 = ((int)Math.ceil(((double)data.lineHeight)));
                v3 = MathUtils.nextPowerOfTwo(((int)Math.sqrt(((double)(v28 * v28 * v15)))));
                if(FreeTypeFontGenerator.maxTextureSize > 0) {
                    v3 = Math.min(v3, FreeTypeFontGenerator.maxTextureSize);
                }
            }

            v29 = 1;
            v2 = new PixmapPacker(v3, v3, Format.RGBA8888, 2, false);
        }

        if(v29 != 0) {
            v10 = "";
        }
        else {
            StringBuilder v6 = new StringBuilder().append(this.name).append('_').append(parameter.size);
            if(parameter.flip) {
                v4_1 = "_flip_";
            }
            else {
                Character v4_2 = Character.valueOf('_');
            }

            v10 = v6.append(v4_1).toString();
        }

        Stroker v8 = null;
        if(parameter.borderWidth > 0f) {
            v8 = this.library.createStroker();
            int v7 = ((int)(parameter.borderWidth * 64f));
            if(parameter.borderStraight) {
                v4_3 = FreeType.FT_STROKER_LINECAP_BUTT;
            }
            else {
                v4_3 = FreeType.FT_STROKER_LINECAP_ROUND;
            }

            if(parameter.borderStraight) {
                v6_1 = FreeType.FT_STROKER_LINEJOIN_MITER_FIXED;
            }
            else {
                v6_1 = FreeType.FT_STROKER_LINEJOIN_ROUND;
            }

            v8.set(v7, v4_3, v6_1, 0);
        }

        if(v23) {
            data.generator = this;
            data.parameter = parameter;
            data.stroker = v8;
            data.packPrefix = v10;
            data.packer = v2;
            data.glyphs = new Array(v15 + 32);
        }

        int v21;
        for(v21 = 0; v21 < v15; ++v21) {
            char v5_1 = v14.charAt(v21);
            Glyph v20 = this.createGlyph(v5_1, data, parameter, v8, v9, v10, v2);
            if(v20 != null) {
                data.setGlyph(v5_1, v20);
                if(v23) {
                    data.glyphs.add(v20);
                }
            }
        }

        if(v8 != null && !v23) {
            v8.dispose();
        }

        if(parameter.kerning) {
            for(v21 = 0; v21 < v15; ++v21) {
                char v17 = v14.charAt(v21);
                Glyph v16 = data.getGlyph(v17);
                if(v16 != null) {
                    int v18 = this.face.getCharIndex(v17);
                    int v24 = v21;
                    while(v24 < v15) {
                        char v32 = v14.charAt(v24);
                        Glyph v31 = data.getGlyph(v32);
                        if(v31 != null) {
                            int v33 = this.face.getCharIndex(v32);
                            int v25 = this.face.getKerning(v18, v33, 0);
                            if(v25 != 0) {
                                v16.setKerning(v32, FreeType.toInt(v25));
                            }

                            v25 = this.face.getKerning(v33, v18, 0);
                            if(v25 == 0) {
                                goto label_349;
                            }

                            v31.setKerning(v17, FreeType.toInt(v25));
                        }

                    label_349:
                        ++v24;
                    }
                }
            }
        }

        if(v29 != 0) {
            Array v30 = v2.getPages();
            data.regions = new Array(v30.size);
            for(v21 = 0; v21 < v30.size; ++v21) {
                data.regions.add(this.createRegion(v30.get(v21), parameter));
            }
        }

        return data;
    }

    public BitmapFont generateFont(int size) {
        return this.generateFont(size, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?\'.,;:()[]{}<>|/@\\^$-%+=#_&~*\u007F\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F?��??��??�ס�?��??����?�ơ�������?�ҡ�������?��������??????��?????????��??????����?????����??????��?????????��??????����?????��?", false);
    }

    public BitmapFont generateFont(int size, String characters, boolean flip) {
        FreeTypeBitmapFontData v0 = this.generateData(size, characters, flip, null);
        BitmapFont v1 = new BitmapFont(((BitmapFontData)v0), v0.regions, false);
        v1.setOwnsTexture(true);
        return v1;
    }

    public BitmapFont generateFont(FreeTypeFontParameter parameter) {
        return this.generateFont(parameter, new FreeTypeBitmapFontData());
    }

    public BitmapFont generateFont(FreeTypeFontParameter parameter, FreeTypeBitmapFontData data) {
        this.generateData(parameter, data);
        BitmapFont v0 = new BitmapFont(((BitmapFontData)data), data.regions, false);
        v0.setOwnsTexture(true);
        return v0;
    }

    public GlyphAndBitmap generateGlyphAndBitmap(int c, int size, boolean flip) {
        int v7;
        Bitmap v1;
        GlyphAndBitmap v5;
        this.setPixelSizes(0, size);
        int v0 = FreeType.toInt(this.face.getSize().getMetrics().getAscender());
        if(this.face.getCharIndex(c) == 0) {
            v5 = null;
        }
        else if(!this.face.loadChar(c, FreeType.FT_LOAD_DEFAULT)) {
            throw new GdxRuntimeException("Unable to load character!");
        }
        else {
            GlyphSlot v6 = this.face.getGlyph();
            if(this.bitmapped) {
                v1 = v6.getBitmap();
            }
            else if(!v6.renderGlyph(FreeType.FT_RENDER_MODE_LIGHT)) {
                v1 = null;
            }
            else {
                v1 = v6.getBitmap();
            }

            GlyphMetrics v4 = v6.getMetrics();
            Glyph v3 = new Glyph();
            if(v1 != null) {
                v3.width = v1.getWidth();
                v3.height = v1.getRows();
            }
            else {
                v3.width = 0;
                v3.height = 0;
            }

            v3.xoffset = v6.getBitmapLeft();
            if(flip) {
                v7 = -v6.getBitmapTop() + v0;
            }
            else {
                v7 = -(v3.height - v6.getBitmapTop()) - v0;
            }

            v3.yoffset = v7;
            v3.xadvance = FreeType.toInt(v4.getHoriAdvance());
            v3.srcX = 0;
            v3.srcY = 0;
            v3.id = c;
            v5 = new GlyphAndBitmap(this);
            v5.glyph = v3;
            v5.bitmap = v1;
        }

        return v5;
    }

    public static int getMaxTextureSize() {
        return FreeTypeFontGenerator.maxTextureSize;
    }

    public int scaleForPixelHeight(int height) {
        this.setPixelSizes(0, height);
        SizeMetrics v2 = this.face.getSize().getMetrics();
        return height * height / (FreeType.toInt(v2.getAscender()) - FreeType.toInt(v2.getDescender()));
    }

    public int scaleForPixelWidth(int width, int numChars) {
        SizeMetrics v3 = this.face.getSize().getMetrics();
        int v4 = (FreeType.toInt(v3.getAscender()) - FreeType.toInt(v3.getDescender())) * width / (FreeType.toInt(v3.getMaxAdvance()) * numChars);
        this.setPixelSizes(0, v4);
        return v4;
    }

    public int scaleToFitSquare(int width, int height, int numChars) {
        return Math.min(this.scaleForPixelHeight(height), this.scaleForPixelWidth(width, numChars));
    }

    public static void setMaxTextureSize(int texSize) {
        FreeTypeFontGenerator.maxTextureSize = texSize;
    }

    void setPixelSizes(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        if(!this.bitmapped && !this.face.setPixelSizes(pixelWidth, pixelHeight)) {
            throw new GdxRuntimeException("Couldn\'t set size for font");
        }
    }
}

