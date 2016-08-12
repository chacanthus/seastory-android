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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.Pools;

public class BitmapFontCache {
    private final Color color;
    private float currentTint;
    private final BitmapFont font;
    private int glyphCount;
    private int[] idx;
    private boolean integer;
    private final Array layouts;
    private IntArray[] pageGlyphIndices;
    private float[][] pageVertices;
    private final Array pooledLayouts;
    private static final Color tempColor;
    private int[] tempGlyphCount;
    private static final float whiteTint;
    private float x;
    private float y;

    static  {
        BitmapFontCache.tempColor = new Color(1f, 1f, 1f, 1f);
        BitmapFontCache.whiteTint = Color.WHITE.toFloatBits();
    }

    public BitmapFontCache(BitmapFont font, boolean integer) {
        super();
        this.layouts = new Array();
        this.pooledLayouts = new Array();
        this.color = new Color(1f, 1f, 1f, 1f);
        this.font = font;
        this.integer = integer;
        int v2 = font.regions.size;
        if(v2 == 0) {
            throw new IllegalArgumentException("The specified font must contain at least one texture page.");
        }

        this.pageVertices = new float[v2][];
        this.idx = new int[v2];
        if(v2 > 1) {
            this.pageGlyphIndices = new IntArray[v2];
            int v0 = 0;
            int v1 = this.pageGlyphIndices.length;
            while(v0 < v1) {
                this.pageGlyphIndices[v0] = new IntArray();
                ++v0;
            }
        }

        this.tempGlyphCount = new int[v2];
    }

    public BitmapFontCache(BitmapFont font) {
        this(font, font.usesIntegerPositions());
    }

    private void addGlyph(Glyph glyph, float x, float y, float color) {
        float v6 = this.font.data.scaleX;
        float v7 = this.font.data.scaleY;
        x += (((float)glyph.xoffset)) * v6;
        y += (((float)glyph.yoffset)) * v7;
        float v13 = (((float)glyph.width)) * v6;
        float v2 = (((float)glyph.height)) * v7;
        float v8 = glyph.u;
        float v9 = glyph.u2;
        float v10 = glyph.v;
        float v11 = glyph.v2;
        float v14 = x + v13;
        float v15 = y + v2;
        if(this.integer) {
            x = ((float)Math.round(x));
            y = ((float)Math.round(y));
            v14 = ((float)Math.round(v14));
            v15 = ((float)Math.round(v15));
        }

        int v5 = glyph.page;
        int v3 = this.idx[v5];
        this.idx[v5] += 20;
        if(this.pageGlyphIndices != null) {
            IntArray v16 = this.pageGlyphIndices[v5];
            int v17 = this.glyphCount;
            this.glyphCount = v17 + 1;
            v16.add(v17);
        }

        float[] v12 = this.pageVertices[v5];
        int v4 = v3 + 1;
        v12[v3] = x;
        v3 = v4 + 1;
        v12[v4] = y;
        v4 = v3 + 1;
        v12[v3] = color;
        v3 = v4 + 1;
        v12[v4] = v8;
        v4 = v3 + 1;
        v12[v3] = v10;
        v3 = v4 + 1;
        v12[v4] = x;
        v4 = v3 + 1;
        v12[v3] = v15;
        v3 = v4 + 1;
        v12[v4] = color;
        v4 = v3 + 1;
        v12[v3] = v8;
        v3 = v4 + 1;
        v12[v4] = v11;
        v4 = v3 + 1;
        v12[v3] = v14;
        v3 = v4 + 1;
        v12[v4] = v15;
        v4 = v3 + 1;
        v12[v3] = color;
        v3 = v4 + 1;
        v12[v4] = v9;
        v4 = v3 + 1;
        v12[v3] = v11;
        v3 = v4 + 1;
        v12[v4] = v14;
        v4 = v3 + 1;
        v12[v3] = y;
        v3 = v4 + 1;
        v12[v4] = color;
        v12[v3] = v9;
        v12[v3 + 1] = v10;
    }

    public GlyphLayout addText(CharSequence str, float x, float y) {
        return this.addText(str, x, y, 0, str.length(), 0f, 8, false);
    }

    public GlyphLayout addText(CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        return this.addText(str, x, y, 0, str.length(), targetWidth, halign, wrap);
    }

    public GlyphLayout addText(CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        Object v0 = Pools.obtain(GlyphLayout.class);
        this.pooledLayouts.add(v0);
        ((GlyphLayout)v0).setText(this.font, str, start, end, this.color, targetWidth, halign, wrap, null);
        this.addText(((GlyphLayout)v0), x, y);
        return ((GlyphLayout)v0);
    }

    public void addText(GlyphLayout layout, float x, float y) {
        this.addToCache(layout, x, this.font.data.ascent + y);
    }

    private void addToCache(GlyphLayout layout, float x, float y) {
        int v16 = this.font.regions.size;
        if(this.pageVertices.length < v16) {
            float[][] v14 = new float[v16][];
            System.arraycopy(this.pageVertices, 0, v14, 0, this.pageVertices.length);
            this.pageVertices = v14;
            int[] v12 = new int[v16];
            System.arraycopy(this.idx, 0, v12, 0, this.idx.length);
            this.idx = v12;
            IntArray[] v13 = new IntArray[v16];
            int v17 = 0;
            if(this.pageGlyphIndices != null) {
                v17 = this.pageGlyphIndices.length;
                System.arraycopy(this.pageGlyphIndices, 0, v13, 0, this.pageGlyphIndices.length);
            }

            int v9;
            for(v9 = v17; v9 < v16; ++v9) {
                v13[v9] = new IntArray();
            }

            this.pageGlyphIndices = v13;
            this.tempGlyphCount = new int[v16];
        }

        this.layouts.add(layout);
        this.requireGlyphs(layout);
        v9 = 0;
        int v11 = layout.runs.size;
        while(v9 < v11) {
            Object v18 = layout.runs.get(v9);
            Array v6 = v18.glyphs;
            FloatArray v19 = v18.xAdvances;
            float v4 = v18.color.toFloatBits();
            float v7 = x + v18.x;
            float v8 = y + v18.y;
            int v10 = 0;
            int v15 = v6.size;
            while(v10 < v15) {
                this.addGlyph(v6.get(v10), v7, v8, v4);
                v7 += v19.get(v10);
                ++v10;
            }

            ++v9;
        }

        this.currentTint = BitmapFontCache.whiteTint;
    }

    public void clear() {
        this.x = 0f;
        this.y = 0f;
        Pools.freeAll(this.pooledLayouts, true);
        this.pooledLayouts.clear();
        this.layouts.clear();
        int v0 = 0;
        int v1 = this.idx.length;
        while(v0 < v1) {
            if(this.pageGlyphIndices != null) {
                this.pageGlyphIndices[v0].clear();
            }

            this.idx[v0] = 0;
            ++v0;
        }
    }

    public void draw(Batch spriteBatch) {
        Array v2 = this.font.getRegions();
        int v0 = 0;
        int v1 = this.pageVertices.length;
        while(v0 < v1) {
            if(this.idx[v0] > 0) {
                spriteBatch.draw(v2.get(v0).getTexture(), this.pageVertices[v0], 0, this.idx[v0]);
            }

            ++v0;
        }
    }

    public void draw(Batch spriteBatch, float alphaModulation) {
        if(alphaModulation == 1f) {
            this.draw(spriteBatch);
        }
        else {
            Color v0 = this.getColor();
            float v1 = v0.a;
            v0.a *= alphaModulation;
            this.setColors(v0);
            this.draw(spriteBatch);
            v0.a = v1;
            this.setColors(v0);
        }
    }

    public void draw(Batch spriteBatch, int start, int end) {
        if(this.pageVertices.length == 1) {
            spriteBatch.draw(this.font.getRegion().getTexture(), this.pageVertices[0], start * 20, (end - start) * 20);
        }
        else {
            Array v9 = this.font.getRegions();
            int v4 = 0;
            int v8 = this.pageVertices.length;
            while(v4 < v8) {
                int v7 = -1;
                int v1 = 0;
                IntArray v3 = this.pageGlyphIndices[v4];
                int v5 = 0;
                int v6 = v3.size;
                while(v5 < v6) {
                    int v2 = v3.get(v5);
                    if(v2 >= end) {
                        break;
                    }

                    if(v7 == -1 && v2 >= start) {
                        v7 = v5;
                    }

                    if(v2 >= start) {
                        ++v1;
                    }

                    ++v5;
                }

                if(v7 != -1 && v1 != 0) {
                    spriteBatch.draw(v9.get(v4).getTexture(), this.pageVertices[v4], v7 * 20, v1 * 20);
                }

                ++v4;
            }
        }
    }

    public Color getColor() {
        return this.color;
    }

    public BitmapFont getFont() {
        return this.font;
    }

    public Array getLayouts() {
        return this.layouts;
    }

    public float[] getVertices() {
        return this.getVertices(0);
    }

    public float[] getVertices(int page) {
        return this.pageVertices[page];
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    private void requireGlyphs(GlyphLayout layout) {
        int v3;
        int v1;
        if(this.pageVertices.length == 1) {
            int v4 = 0;
            v1 = 0;
            v3 = layout.runs.size;
            while(v1 < v3) {
                v4 += layout.runs.get(v1).glyphs.size;
                ++v1;
            }

            this.requirePageGlyphs(0, v4);
        }
        else {
            int[] v6 = this.tempGlyphCount;
            v1 = 0;
            v3 = v6.length;
            while(v1 < v3) {
                v6[v1] = 0;
                ++v1;
            }

            v1 = 0;
            v3 = layout.runs.size;
            while(v1 < v3) {
                Array v0 = layout.runs.get(v1).glyphs;
                int v2 = 0;
                int v5 = v0.size;
                while(v2 < v5) {
                    int v7 = v0.get(v2).page;
                    ++v6[v7];
                    ++v2;
                }

                ++v1;
            }

            v1 = 0;
            v3 = v6.length;
            while(v1 < v3) {
                this.requirePageGlyphs(v1, v6[v1]);
                ++v1;
            }
        }
    }

    private void requirePageGlyphs(int page, int glyphCount) {
        if(this.pageGlyphIndices != null && glyphCount > this.pageGlyphIndices[page].items.length) {
            this.pageGlyphIndices[page].ensureCapacity(glyphCount - this.pageGlyphIndices[page].items.length);
        }

        int v1 = this.idx[page] + glyphCount * 20;
        float[] v2 = this.pageVertices[page];
        if(v2 == null) {
            this.pageVertices[page] = new float[v1];
        }
        else if(v2.length < v1) {
            float[] v0 = new float[v1];
            System.arraycopy(v2, 0, v0, 0, this.idx[page]);
            this.pageVertices[page] = v0;
        }
    }

    public void setAlphas(float alpha) {
        int v0 = (((int)(254f * alpha))) << 24;
        float v7 = 0f;
        float v6 = 0f;
        int v3 = 0;
        int v4 = this.pageVertices.length;
        while(v3 < v4) {
            float[] v9 = this.pageVertices[v3];
            int v2 = 2;
            int v5 = this.idx[v3];
            while(v2 < v5) {
                float v1 = v9[v2];
                if(v1 != v7 || v2 == 2) {
                    v7 = v1;
                    v6 = NumberUtils.intToFloatColor(16777215 & NumberUtils.floatToIntColor(v1) | v0);
                    v9[v2] = v6;
                }
                else {
                    v9[v2] = v6;
                }

                v2 += 5;
            }

            ++v3;
        }
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColors(Color tint) {
        this.setColors(tint.toFloatBits());
    }

    public void setColors(float color) {
        int v1 = 0;
        int v2 = this.pageVertices.length;
        while(v1 < v2) {
            float[] v4 = this.pageVertices[v1];
            int v0 = 2;
            int v3 = this.idx[v1];
            while(v0 < v3) {
                v4[v0] = color;
                v0 += 5;
            }

            ++v1;
        }
    }

    public void setColors(float r, float g, float b, float a) {
        this.setColors(NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r)))));
    }

    public void setColors(float color, int start, int end) {
        int v4;
        int v2;
        float[] v7;
        if(this.pageVertices.length == 1) {
            v7 = this.pageVertices[0];
            v2 = start * 20 + 2;
            v4 = end * 20;
            while(v2 < v4) {
                v7[v2] = color;
                v2 += 5;
            }
        }
        else {
            int v6 = this.pageVertices.length;
            for(v2 = 0; v2 < v6; ++v2) {
                v7 = this.pageVertices[v2];
                IntArray v1 = this.pageGlyphIndices[v2];
                int v3 = 0;
                v4 = v1.size;
                while(v3 < v4) {
                    int v0 = v1.items[v3];
                    if(v0 >= end) {
                        break;
                    }

                    if(v0 >= start) {
                        int v5 = 0;
                        while(v5 < 20) {
                            v7[v3 * 20 + 2 + v5] = color;
                            v5 += 5;
                        }
                    }

                    ++v3;
                }
            }
        }
    }

    public void setColors(Color tint, int start, int end) {
        this.setColors(tint.toFloatBits(), start, end);
    }

    public void setPosition(float x, float y) {
        this.translate(x - this.x, y - this.y);
    }

    public GlyphLayout setText(CharSequence str, float x, float y) {
        this.clear();
        return this.addText(str, x, y, 0, str.length(), 0f, 8, false);
    }

    public GlyphLayout setText(CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        this.clear();
        return this.addText(str, x, y, 0, str.length(), targetWidth, halign, wrap);
    }

    public GlyphLayout setText(CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        this.clear();
        return this.addText(str, x, y, start, end, targetWidth, halign, wrap);
    }

    public void setText(GlyphLayout layout, float x, float y) {
        this.clear();
        this.addText(layout, x, y);
    }

    public void setUseIntegerPositions(boolean use) {
        this.integer = use;
    }

    public void tint(Color tint) {
        float v10 = tint.toFloatBits();
        if(this.currentTint != v10) {
            this.currentTint = v10;
            int[] v16 = this.tempGlyphCount;
            int v5 = 0;
            int v9 = v16.length;
            while(v5 < v9) {
                v16[v5] = 0;
                ++v5;
            }

            v5 = 0;
            v9 = this.layouts.size;
            while(v5 < v9) {
                Object v8 = this.layouts.get(v5);
                int v6 = 0;
                int v11 = ((GlyphLayout)v8).runs.size;
                while(v6 < v11) {
                    Object v15 = ((GlyphLayout)v8).runs.get(v6);
                    Array v4 = ((GlyphRun)v15).glyphs;
                    float v2 = BitmapFontCache.tempColor.set(((GlyphRun)v15).color).mul(tint).toFloatBits();
                    int v7 = 0;
                    int v12 = v4.size;
                    while(v7 < v12) {
                        int v14 = v4.get(v7).page;
                        int v13 = v16[v14] * 20 + 2;
                        ++v16[v14];
                        float[] v18 = this.pageVertices[v14];
                        int v17;
                        for(v17 = 0; v17 < 20; v17 += 5) {
                            v18[v13 + v17] = v2;
                        }

                        ++v7;
                    }

                    ++v6;
                }

                ++v5;
            }
        }
    }

    public void translate(float xAmount, float yAmount) {
        if(xAmount != 0f || yAmount != 0f) {
            if(this.integer) {
                xAmount = ((float)Math.round(xAmount));
                yAmount = ((float)Math.round(yAmount));
            }

            this.x += xAmount;
            this.y += yAmount;
            int v1 = 0;
            int v2 = this.pageVertices.length;
            while(v1 < v2) {
                float[] v4 = this.pageVertices[v1];
                int v0 = 0;
                int v3 = this.idx[v1];
                while(v0 < v3) {
                    v4[v0] += xAmount;
                    int v5 = v0 + 1;
                    v4[v5] += yAmount;
                    v0 += 5;
                }

                ++v1;
            }
        }
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }
}

