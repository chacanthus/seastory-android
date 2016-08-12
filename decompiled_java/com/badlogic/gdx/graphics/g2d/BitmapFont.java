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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BitmapFont implements Disposable {
    public class BitmapFontData {
        public float ascent;
        public char[] breakChars;
        public char[] capChars;
        public float capHeight;
        public float descent;
        public float down;
        public boolean flipped;
        public FileHandle fontFile;
        public final Glyph[][] glyphs;
        public String[] imagePaths;
        public float lineHeight;
        public boolean markupEnabled;
        public float scaleX;
        public float scaleY;
        public float spaceWidth;
        public char[] xChars;
        public float xHeight;

        public BitmapFontData(FileHandle fontFile, boolean flip) {
            super();
            this.capHeight = 1f;
            this.scaleX = 1f;
            this.scaleY = 1f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            this.fontFile = fontFile;
            this.flipped = flip;
            this.load(fontFile, flip);
        }

        public BitmapFontData() {
            super();
            this.capHeight = 1f;
            this.scaleX = 1f;
            this.scaleY = 1f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }

        public Glyph getFirstGlyph() {
            Glyph[][] v0 = this.glyphs;
            int v5 = v0.length;
            int v4;
            for(v4 = 0; v4 < v5; ++v4) {
                Glyph[] v7 = v0[v4];
                if(v7 != null) {
                    Glyph[] v1 = v7;
                    int v6 = v1.length;
                    int v3;
                    for(v3 = 0; v3 < v6; ++v3) {
                        Glyph v2 = v1[v3];
                        if(v2 != null && v2.height != 0 && v2.width != 0) {
                            return v2;
                        }
                    }
                }
            }

            throw new GdxRuntimeException("No glyphs found.");
        }

        public FileHandle getFontFile() {
            return this.fontFile;
        }

        public Glyph getGlyph(char ch) {
            Glyph v1;
            Glyph[] v0 = this.glyphs[ch / 512];
            if(v0 != null) {
                v1 = v0[ch & 511];
            }
            else {
                v1 = null;
            }

            return v1;
        }

        public void getGlyphs(GlyphRun run, CharSequence str, int start, int end) {
            int v10 = 91;
            boolean v4 = this.markupEnabled;
            float v5 = this.scaleX;
            Array v2 = run.glyphs;
            FloatArray v7 = run.xAdvances;
            Glyph v3 = null;
            int v6;
            for(v6 = start; true; v6 = start) {
                if(v6 >= end) {
                    break;
                }

                start = v6 + 1;
                char v0 = str.charAt(v6);
                Glyph v1 = this.getGlyph(v0);
                if(v1 != null) {
                    goto label_14;
                }

                v6 = start;
                continue;
            label_14:
                v2.add(v1);
                if(v3 != null) {
                    v7.add((((float)(v3.xadvance + v3.getKerning(v0)))) * v5);
                }

                v3 = v1;
                if((v4) && v0 == v10 && start < end && str.charAt(start) == v10) {
                    ++start;
                }
            }

            if(v3 != null) {
                v7.add((((float)v3.xadvance)) * v5);
            }
        }

        public String getImagePath(int index) {
            return this.imagePaths[index];
        }

        public String[] getImagePaths() {
            return this.imagePaths;
        }

        public int getWrapIndex(Array arg4, int start) {
            int v1;
            if(this.isWhitespace(((char)arg4.get(start).id))) {
                v1 = start + 1;
            }
            else {
                v1 = start - 1;
                while(true) {
                    if(v1 >= 1) {
                        char v0 = ((char)arg4.get(v1).id);
                        if(this.isWhitespace(v0)) {
                            ++v1;
                        }
                        else if(!this.isBreakChar(v0)) {
                            --v1;
                            continue;
                        }
                    }
                    else {
                        break;
                    }

                    goto label_6;
                }

                v1 = start;
            }

        label_6:
            return v1;
        }

        public boolean hasGlyph(char ch) {
            boolean v0;
            if(this.getGlyph(ch) != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public boolean isBreakChar(char c) {
            boolean v4 = false;
            if(this.breakChars != null) {
                char[] v0 = this.breakChars;
                int v3 = v0.length;
                int v2 = 0;
                while(v2 < v3) {
                    if(c == v0[v2]) {
                        v4 = true;
                    }
                    else {
                        ++v2;
                        continue;
                    }

                    break;
                }
            }

            return v4;
        }

        public boolean isWhitespace(char c) {
            boolean v0;
            switch(c) {
                case 9: 
                case 10: 
                case 13: 
                case 32: {
                    v0 = true;
                    break;
                }
                default: {
                    v0 = false;
                    break;
                }
            }

            return v0;
        }

        public void load(FileHandle fontFile, boolean flip) {  // has try-catch handlers
            int v16;
            Glyph[][] v4;
            char v31_9;
            StringTokenizer v28;
            Glyph v13;
            String v11;
            String[] v24;
            int v20;
            String[] v31_7;
            String v31_5;
            int v22;
            float v6;
            boolean v31_4;
            int v31_3;
            String[] v9;
            GdxRuntimeException v31_2;
            String v19;
            if(this.imagePaths != null) {
                throw new IllegalStateException("Already loaded.");
            }

            InputStreamReader v31 = new InputStreamReader(fontFile.read());
            BufferedReader v25 = new BufferedReader(v31, 512);
            try {
                v25.readLine();
                v19 = v25.readLine();
                if(v19 == null) {
                    v31_2 = new GdxRuntimeException("File is empty.");
                    throw v31_2;
                }

                v9 = v19.split(" ", 7);
                v31_3 = v9.length;
                if(v31_3 < 3) {
                    v31_2 = new GdxRuntimeException("Invalid header.");
                    throw v31_2;
                }

                v31_4 = v9[1].startsWith("lineHeight=");
                if(!v31_4) {
                    v31_2 = new GdxRuntimeException("Missing: lineHeight");
                    throw v31_2;
                }

                this.lineHeight = ((float)Integer.parseInt(v9[1].substring(11)));
                v31_4 = v9[2].startsWith("base=");
                if(!v31_4) {
                    v31_2 = new GdxRuntimeException("Missing: base");
                    throw v31_2;
                }

                v6 = ((float)Integer.parseInt(v9[2].substring(5)));
                v22 = 1;
                v31_3 = v9.length;
                if(v31_3 < 6) {
                    goto label_108;
                }

                v31_5 = v9[5];
                if(v31_5 == null) {
                    goto label_108;
                }

                v31_4 = v9[5].startsWith("pages=");
                if(!v31_4) {
                    goto label_108;
                }
            }
            catch(Exception v10) {
                goto label_22;
            }
            catch(Throwable v31_1) {
                goto label_35;
            }

            v31 = null;
            int v32 = 5;
            try {
                v22 = Math.max(1, Integer.parseInt(v9[v32].substring(6)));
                goto label_108;
            }
            catch(Throwable v31_1) {
            }
            catch(Exception v10) {
            }
            catch(NumberFormatException v31_6) {
                try {
                label_108:
                    v31_7 = new String[v22];
                    this.imagePaths = v31_7;
                    v20 = 0;
                    while(true) {
                    label_115:
                        if(v20 >= v22) {
                            goto label_211;
                        }

                        v19 = v25.readLine();
                        if(v19 == null) {
                            v31_2 = new GdxRuntimeException("Missing additional page definitions.");
                            throw v31_2;
                        }

                        v24 = v19.split(" ", 4);
                        v31_4 = v24[2].startsWith("file=");
                        if(!v31_4) {
                            v31_2 = new GdxRuntimeException("Missing: file");
                            throw v31_2;
                        }

                        v31_4 = v24[1].startsWith("id=");
                        if(v31_4) {
                            break;
                        }

                        goto label_176;
                    }
                }
                catch(Exception v10) {
                    goto label_22;
                }
                catch(Throwable v31_1) {
                    goto label_35;
                }

                try {
                    v31_5 = v24[1].substring(3);
                    if(Integer.parseInt(v31_5) != v20) {
                        v31_2 = new GdxRuntimeException("Page IDs must be indices starting at 0: " + v24[1].substring(3));
                        throw v31_2;
                    }

                    goto label_176;
                }
                catch(Throwable v31_1) {
                }
                catch(Exception v10) {
                }
                catch(NumberFormatException v10_1) {
                    try {
                        v31_2 = new GdxRuntimeException("Invalid page id: " + v24[1].substring(3), ((Throwable)v10_1));
                        throw v31_2;
                    label_176:
                        v31_4 = v24[2].endsWith("\"");
                        if(v31_4) {
                            v31_5 = v24[2];
                            v11 = v31_5.substring(6, v24[2].length() - 1);
                        }
                        else {
                            v31_5 = v24[2];
                            v11 = v31_5.substring(5, v24[2].length());
                        }

                        v31_7 = this.imagePaths;
                        v31_7[v20] = fontFile.parent().child(v11).path().replaceAll("\\\\", "/");
                        ++v20;
                        goto label_115;
                    label_211:
                        v31 = null;
                        this.descent = 0f;
                        while(true) {
                        label_215:
                            v19 = v25.readLine();
                            if(v19 != null) {
                                v31_4 = v19.startsWith("kernings ");
                                if(!v31_4) {
                                    v31_4 = v19.startsWith("char ");
                                    if(!v31_4) {
                                        continue;
                                    }

                                    v13 = new Glyph();
                                    v28 = new StringTokenizer(v19, " =");
                                    v28.nextToken();
                                    v28.nextToken();
                                    int v8 = Integer.parseInt(v28.nextToken());
                                    v31 = null;
                                    if(v8 > (((int)v31))) {
                                        continue;
                                    }

                                    this.setGlyph(v8, v13);
                                    v13.id = v8;
                                    v28.nextToken();
                                    v13.srcX = Integer.parseInt(v28.nextToken());
                                    v28.nextToken();
                                    v13.srcY = Integer.parseInt(v28.nextToken());
                                    v28.nextToken();
                                    v13.width = Integer.parseInt(v28.nextToken());
                                    v28.nextToken();
                                    v13.height = Integer.parseInt(v28.nextToken());
                                    v28.nextToken();
                                    v31_3 = Integer.parseInt(v28.nextToken());
                                    v13.xoffset = v31_3;
                                    v28.nextToken();
                                    if(flip) {
                                        v31_3 = Integer.parseInt(v28.nextToken());
                                        v13.yoffset = v31_3;
                                    }
                                    else {
                                        v31_3 = -(v13.height + Integer.parseInt(v28.nextToken()));
                                        v13.yoffset = v31_3;
                                    }

                                    v28.nextToken();
                                    v13.xadvance = Integer.parseInt(v28.nextToken());
                                    v31_4 = v28.hasMoreTokens();
                                    if(v31_4) {
                                        v28.nextToken();
                                    }

                                    v31_4 = v28.hasMoreTokens();
                                    if(!v31_4) {
                                        goto label_394;
                                    }

                                    goto label_390;
                                }
                            }

                            goto label_217;
                        }
                    }
                    catch(Exception v10) {
                        goto label_22;
                    }
                    catch(Throwable v31_1) {
                        goto label_35;
                    }

                    try {
                    label_390:
                        v31_3 = Integer.parseInt(v28.nextToken());
                        v13.page = v31_3;
                        goto label_394;
                    }
                    catch(Throwable v31_1) {
                    }
                    catch(Exception v10) {
                        try {
                        label_22:
                            throw new GdxRuntimeException("Error loading font file: " + fontFile, ((Throwable)v10));
                        }
                        catch(Throwable v31_1) {
                        label_35:
                            StreamUtils.closeQuietly(((Closeable)v25));
                            throw v31_1;
                        }
                    }
                    catch(NumberFormatException v31_6) {
                        try {
                        label_394:
                            v31_3 = v13.width;
                            if(v31_3 <= 0) {
                                goto label_215;
                            }

                            v31_3 = v13.height;
                            if(v31_3 <= 0) {
                                goto label_215;
                            }

                            float v31_8 = Math.min((((float)v13.yoffset)) + v6, this.descent);
                            this.descent = v31_8;
                            goto label_215;
                            while(true) {
                            label_217:
                                v19 = v25.readLine();
                                if(v19 != null) {
                                    v31_4 = v19.startsWith("kerning ");
                                    if(v31_4) {
                                        v28 = new StringTokenizer(v19, " =");
                                        v28.nextToken();
                                        v28.nextToken();
                                        int v12 = Integer.parseInt(v28.nextToken());
                                        v28.nextToken();
                                        v31_5 = v28.nextToken();
                                        int v26 = Integer.parseInt(v31_5);
                                        if(v12 < 0) {
                                            continue;
                                        }

                                        v31 = null;
                                        if(v12 > (((int)v31))) {
                                            continue;
                                        }

                                        if(v26 < 0) {
                                            continue;
                                        }

                                        v31 = null;
                                        if(v26 > (((int)v31))) {
                                            continue;
                                        }

                                        v13 = this.getGlyph(((char)v12));
                                        v28.nextToken();
                                        v31_5 = v28.nextToken();
                                        int v3 = Integer.parseInt(v31_5);
                                        if(v13 == null) {
                                            continue;
                                        }

                                        v13.setKerning(v26, v3);
                                        continue;
                                    }
                                }

                                break;
                            }

                            v31 = null;
                            Glyph v27 = this.getGlyph(((char)v31));
                            if(v27 == null) {
                                v27 = new Glyph();
                                v27.id = 32;
                                v31 = null;
                                Glyph v30 = this.getGlyph(((char)v31));
                                if(v30 == null) {
                                    v30 = this.getFirstGlyph();
                                }

                                v27.xadvance = v30.xadvance;
                                v31 = null;
                                this.setGlyph(((int)v31), v27);
                            }

                            if(v27 != null) {
                                v31_8 = ((float)(v27.xadvance + v27.width));
                            }
                            else {
                                v31 = null;
                            }

                            this.spaceWidth = ((float)v31);
                            Glyph v29 = null;
                            int v14 = 0;
                            while(true) {
                                v31_3 = this.xChars.length;
                                if(v14 < v31_3) {
                                    v31_9 = this.xChars[v14];
                                    v29 = this.getGlyph(v31_9);
                                    if(v29 == null) {
                                        ++v14;
                                        continue;
                                    }
                                }

                                break;
                            }

                            if(v29 == null) {
                                v29 = this.getFirstGlyph();
                            }

                            v31_8 = ((float)v29.height);
                            this.xHeight = v31_8;
                            Glyph v7 = null;
                            v14 = 0;
                            while(true) {
                                v31_3 = this.capChars.length;
                                if(v14 < v31_3) {
                                    v31_9 = this.capChars[v14];
                                    v7 = this.getGlyph(v31_9);
                                    if(v7 == null) {
                                        ++v14;
                                        continue;
                                    }
                                }

                                break;
                            }

                            if(v7 == null) {
                                v4 = this.glyphs;
                                int v17 = v4.length;
                                v16 = 0;
                                goto label_314;
                            }
                            else {
                                v31_8 = ((float)v7.height);
                                this.capHeight = v31_8;
                                goto label_507;
                            label_314:
                                while(v16 < v17) {
                                    Glyph[] v21 = v4[v16];
                                    if(v21 != null) {
                                        Glyph[] v5 = v21;
                                        int v18 = v5.length;
                                        int v15;
                                        for(v15 = 0; v15 < v18; ++v15) {
                                            v13 = v5[v15];
                                            if(v13 != null) {
                                                v31_3 = v13.height;
                                                if(v31_3 != 0) {
                                                    v31_3 = v13.width;
                                                    if(v31_3 != 0) {
                                                        v31_8 = Math.max(this.capHeight, ((float)v13.height));
                                                        this.capHeight = v31_8;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    ++v16;
                                }
                            }

                        label_507:
                            this.ascent = v6 - this.capHeight;
                            v31_8 = -this.lineHeight;
                            this.down = v31_8;
                            if(flip) {
                                this.ascent = -this.ascent;
                                v31_8 = -this.down;
                                this.down = v31_8;
                            }
                        }
                        catch(Exception v10) {
                            goto label_22;
                        }
                        catch(Throwable v31_1) {
                            goto label_35;
                        }

                        StreamUtils.closeQuietly(((Closeable)v25));
                        return;
                    }
                }
            }
        }

        public void scale(float amount) {
            this.setScale(this.scaleX + amount, this.scaleY + amount);
        }

        public void setGlyph(int ch, Glyph glyph) {
            Glyph[] v0 = this.glyphs[ch / 512];
            if(v0 == null) {
                Glyph[][] v1 = this.glyphs;
                v0 = new Glyph[512];
                v1[ch / 512] = v0;
            }

            v0[ch & 511] = glyph;
        }

        public void setGlyphRegion(Glyph glyph, TextureRegion region) {
            float v4 = 1f / (((float)region.getTexture().getWidth()));
            float v3 = 1f / (((float)region.getTexture().getHeight()));
            float v5 = 0f;
            float v6 = 0f;
            float v9 = region.u;
            float v10 = region.v;
            float v8 = ((float)region.getRegionWidth());
            float v7 = ((float)region.getRegionHeight());
            if((region instanceof AtlasRegion)) {
                v5 = region.offsetX;
                v6 = (((float)(region.originalHeight - region.packedHeight))) - region.offsetY;
            }

            float v11 = ((float)glyph.srcX);
            float v12 = ((float)(glyph.srcX + glyph.width));
            float v13 = ((float)glyph.srcY);
            float v14 = ((float)(glyph.srcY + glyph.height));
            if(v5 > 0f) {
                v11 -= v5;
                if(v11 < 0f) {
                    glyph.width = ((int)((((float)glyph.width)) + v11));
                    glyph.xoffset = ((int)((((float)glyph.xoffset)) - v11));
                    v11 = 0f;
                }

                v12 -= v5;
                if(v12 <= v8) {
                    goto label_88;
                }

                glyph.width = ((int)((((float)glyph.width)) - (v12 - v8)));
                v12 = v8;
            }

        label_88:
            if(v6 > 0f) {
                v13 -= v6;
                if(v13 < 0f) {
                    glyph.height = ((int)((((float)glyph.height)) + v13));
                    v13 = 0f;
                }

                v14 -= v6;
                if(v14 <= v7) {
                    goto label_119;
                }

                float v1 = v14 - v7;
                glyph.height = ((int)((((float)glyph.height)) - v1));
                glyph.yoffset = ((int)((((float)glyph.yoffset)) + v1));
                v14 = v7;
            }

        label_119:
            glyph.u = v11 * v4 + v9;
            glyph.u2 = v12 * v4 + v9;
            if(this.flipped) {
                glyph.v = v13 * v3 + v10;
                glyph.v2 = v14 * v3 + v10;
            }
            else {
                glyph.v2 = v13 * v3 + v10;
                glyph.v = v14 * v3 + v10;
            }
        }

        public void setLineHeight(float height) {
            float v0;
            this.lineHeight = this.scaleY * height;
            if(this.flipped) {
                v0 = this.lineHeight;
            }
            else {
                v0 = -this.lineHeight;
            }

            this.down = v0;
        }

        public void setScale(float scaleX, float scaleY) {
            if(scaleX == 0f) {
                throw new IllegalArgumentException("scaleX cannot be 0.");
            }

            if(scaleY == 0f) {
                throw new IllegalArgumentException("scaleY cannot be 0.");
            }

            float v0 = scaleX / this.scaleX;
            float v1 = scaleY / this.scaleY;
            this.lineHeight *= v1;
            this.spaceWidth *= v0;
            this.xHeight *= v1;
            this.capHeight *= v1;
            this.ascent *= v1;
            this.descent *= v1;
            this.down *= v1;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }

        public void setScale(float scaleXY) {
            this.setScale(scaleXY, scaleXY);
        }
    }

    public class Glyph {
        public int id;
        public int srcX;
        public int srcY;
        public float u;
        public float u2;
        public float v;
        public float v2;
        public int yoffset;

        public Glyph() {
            super();
            this.page = 0;
        }

        public int getKerning(char ch) {
            int v1;
            if(this.kerning != null) {
                byte[] v0 = this.kerning[ch >>> 9];
                if(v0 != null) {
                    v1 = v0[ch & 511];
                }
                else {
                    goto label_9;
                }
            }
            else {
            label_9:
                v1 = 0;
            }

            return v1;
        }

        public void setKerning(int ch, int value) {
            if(this.kerning == null) {
                this.kerning = new byte[128][];
            }

            byte[] v0 = this.kerning[ch >>> 9];
            if(v0 == null) {
                byte[][] v1 = this.kerning;
                v0 = new byte[512];
                v1[ch >>> 9] = v0;
            }

            v0[ch & 511] = ((byte)value);
        }

        public String toString() {
            return Character.toString(((char)this.id));
        }
    }

    private static final int LOG2_PAGE_SIZE = 9;
    private static final int PAGES = 128;
    private static final int PAGE_SIZE = 512;
    private final BitmapFontCache cache;
    final BitmapFontData data;
    private boolean flipped;
    private boolean integer;
    private boolean ownsTexture;
    Array regions;

    public BitmapFont() {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), false, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip, boolean integer) {
        this(new BitmapFontData(fontFile, flip), new TextureRegion(new Texture(imageFile, false)), integer);
        this.ownsTexture = true;
    }

    public BitmapFont(FileHandle fontFile) {
        this(fontFile, false);
    }

    public BitmapFont(FileHandle fontFile, boolean flip) {
        this(new BitmapFontData(fontFile, flip), null, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
        this(fontFile, imageFile, flip, true);
    }

    public BitmapFont(BitmapFontData data, TextureRegion region, boolean integer) {
        Array v0_1;
        if(region != null) {
            TextureRegion[] v0 = new TextureRegion[1];
            v0[0] = region;
            v0_1 = Array.with(((Object[])v0));
        }
        else {
            v0_1 = null;
        }

        this(data, v0_1, integer);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region) {
        this(fontFile, region, false);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
        this(new BitmapFontData(fontFile, flip), region, true);
    }

    public BitmapFont(BitmapFontData data, Array arg9, boolean integer) {
        FileHandle v0;
        super();
        if(arg9 == null || arg9.size == 0) {
            int v2 = data.imagePaths.length;
            this.regions = new Array(v2);
            int v1;
            for(v1 = 0; v1 < v2; ++v1) {
                if(data.fontFile == null) {
                    v0 = Gdx.files.internal(data.imagePaths[v1]);
                }
                else {
                    v0 = Gdx.files.getFileHandle(data.imagePaths[v1], data.fontFile.type());
                }

                this.regions.add(new TextureRegion(new Texture(v0, false)));
            }

            this.ownsTexture = true;
        }
        else {
            this.regions = arg9;
            this.ownsTexture = false;
        }

        this.cache = new BitmapFontCache(this, integer);
        this.flipped = data.flipped;
        this.data = data;
        this.integer = integer;
        this.load(data);
    }

    public BitmapFont(boolean flip) {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), flip, true);
    }

    public void dispose() {
        if(this.ownsTexture) {
            int v0;
            for(v0 = 0; v0 < this.regions.size; ++v0) {
                this.regions.get(v0).getTexture().dispose();
            }
        }
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
        this.cache.clear();
        GlyphLayout v0 = this.cache.addText(str, x, y);
        this.cache.draw(batch);
        return v0;
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        this.cache.clear();
        GlyphLayout v7 = this.cache.addText(str, x, y, targetWidth, halign, wrap);
        this.cache.draw(batch);
        return v7;
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        this.cache.clear();
        GlyphLayout v9 = this.cache.addText(str, x, y, start, end, targetWidth, halign, wrap);
        this.cache.draw(batch);
        return v9;
    }

    public void draw(Batch batch, GlyphLayout layout, float x, float y) {
        this.cache.clear();
        this.cache.addText(layout, x, y);
        this.cache.draw(batch);
    }

    public float getAscent() {
        return this.data.ascent;
    }

    public BitmapFontCache getCache() {
        return this.cache;
    }

    public float getCapHeight() {
        return this.data.capHeight;
    }

    public Color getColor() {
        return this.cache.getColor();
    }

    public BitmapFontData getData() {
        return this.data;
    }

    public float getDescent() {
        return this.data.descent;
    }

    public float getLineHeight() {
        return this.data.lineHeight;
    }

    public TextureRegion getRegion() {
        return this.regions.first();
    }

    public TextureRegion getRegion(int index) {
        return this.regions.get(index);
    }

    public Array getRegions() {
        return this.regions;
    }

    public float getScaleX() {
        return this.data.scaleX;
    }

    public float getScaleY() {
        return this.data.scaleY;
    }

    public float getSpaceWidth() {
        return this.data.spaceWidth;
    }

    public float getXHeight() {
        return this.data.xHeight;
    }

    static int indexOf(CharSequence text, char ch, int start) {
        int v0 = text.length();
        while(true) {
            if(start >= v0) {
                break;
            }
            else if(text.charAt(start) != ch) {
                ++start;
                continue;
            }

            goto label_4;
        }

        start = v0;
    label_4:
        return start;
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    private void load(BitmapFontData data) {
        Glyph[][] v0 = data.glyphs;
        int v5 = v0.length;
        int v4;
        for(v4 = 0; v4 < v5; ++v4) {
            Glyph[] v7 = v0[v4];
            if(v7 != null) {
                Glyph[] v1 = v7;
                int v6 = v1.length;
                int v3;
                for(v3 = 0; v3 < v6; ++v3) {
                    Glyph v2 = v1[v3];
                    if(v2 != null) {
                        Object v8 = this.regions.get(v2.page);
                        if(v8 == null) {
                            throw new IllegalArgumentException("BitmapFont texture region array cannot contain null elements.");
                        }
                        else {
                            data.setGlyphRegion(v2, ((TextureRegion)v8));
                        }
                    }
                }
            }
        }
    }

    public boolean ownsTexture() {
        return this.ownsTexture;
    }

    public void setColor(float r, float g, float b, float a) {
        this.cache.getColor().set(r, g, b, a);
    }

    public void setColor(Color color) {
        this.cache.getColor().set(color);
    }

    public void setFixedWidthGlyphs(CharSequence glyphs) {
        Glyph v2;
        BitmapFontData v0 = this.data;
        int v4 = 0;
        int v3 = 0;
        int v1 = glyphs.length();
        while(v3 < v1) {
            v2 = v0.getGlyph(glyphs.charAt(v3));
            if(v2 != null && v2.xadvance > v4) {
                v4 = v2.xadvance;
            }

            ++v3;
        }

        v3 = 0;
        v1 = glyphs.length();
        while(v3 < v1) {
            v2 = v0.getGlyph(glyphs.charAt(v3));
            if(v2 != null) {
                v2.xoffset += (v4 - v2.xadvance) / 2;
                v2.xadvance = v4;
                v2.kerning = null;
            }

            ++v3;
        }
    }

    public void setOwnsTexture(boolean ownsTexture) {
        this.ownsTexture = ownsTexture;
    }

    public void setUseIntegerPositions(boolean integer) {
        this.integer = integer;
        this.cache.setUseIntegerPositions(integer);
    }

    public String toString() {
        String v0;
        if(this.data.fontFile != null) {
            v0 = this.data.fontFile.nameWithoutExtension();
        }
        else {
            v0 = super.toString();
        }

        return v0;
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }
}

