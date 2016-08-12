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
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool$Poolable;
import com.badlogic.gdx.utils.Pools;

public class GlyphLayout implements Poolable {
    public class GlyphRun implements Poolable {
        public GlyphRun() {
            super();
            this.glyphs = new Array();
            this.xAdvances = new FloatArray();
            this.color = new Color();
        }

        public void reset() {
            this.glyphs.clear();
            this.xAdvances.clear();
            this.width = 0f;
        }

        public String toString() {
            StringBuilder v0 = new StringBuilder(this.glyphs.size);
            Array v2 = this.glyphs;
            int v3 = 0;
            int v4 = v2.size;
            while(v3 < v4) {
                v0.append(((char)v2.get(v3).id));
                ++v3;
            }

            v0.append(", #");
            v0.append(this.color);
            v0.append(", ");
            v0.append(this.x);
            v0.append(", ");
            v0.append(this.y);
            v0.append(", ");
            v0.append(this.width);
            return v0.toString();
        }
    }

    private static final Array colorStack;
    public float height;
    public float width;

    static  {
        GlyphLayout.colorStack = new Array(4);
    }

    public GlyphLayout() {
        super();
        this.runs = new Array();
    }

    public GlyphLayout(BitmapFont font, CharSequence str) {
        super();
        this.runs = new Array();
        this.setText(font, str);
    }

    public GlyphLayout(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign, boolean wrap, String truncate) {
        super();
        this.runs = new Array();
        this.setText(font, str, start, end, color, targetWidth, halign, wrap, truncate);
    }

    public GlyphLayout(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
        super();
        this.runs = new Array();
        this.setText(font, str, color, targetWidth, halign, wrap);
    }

    private int parseColorMarkup(CharSequence str, int start, int end, Pool arg14) {
        int v9 = 93;
        int v6 = -1;
        if(start != end) {
            switch(str.charAt(start)) {
                case 35: {
                    goto label_13;
                }
                case 91: {
                    goto label_3;
                }
                case 93: {
                    goto label_57;
                }
            }

            int v3 = start;
            int v4 = start + 1;
            while(true) {
                if(v4 < end) {
                    if(str.charAt(v4) != v9) {
                        ++v4;
                        continue;
                    }
                    else {
                        break;
                    }
                }

                goto label_3;
            }

            Color v5 = Colors.get(str.subSequence(v3, v4).toString());
            if(v5 != null) {
                Object v1 = arg14.obtain();
                GlyphLayout.colorStack.add(v1);
                ((Color)v1).set(v5);
                v6 = v4 - start;
                goto label_3;
            label_57:
                if(GlyphLayout.colorStack.size > 1) {
                    arg14.free(GlyphLayout.colorStack.pop());
                }

                v6 = 0;
                goto label_3;
            label_13:
                int v2 = 0;
                for(v4 = start + 1; v4 < end; ++v4) {
                    int v0 = str.charAt(v4);
                    if(v0 != v9) {
                        goto label_32;
                    }

                    if(v4 < start + 2) {
                        break;
                    }

                    if(v4 > start + 9) {
                        break;
                    }

                    v1 = arg14.obtain();
                    GlyphLayout.colorStack.add(v1);
                    Color.rgb888ToColor(((Color)v1), v2);
                    if(v4 <= start + 7) {
                        ((Color)v1).a = 1f;
                    }

                    v6 = v4 - start;
                    break;
                label_32:
                    if(v0 < 48 || v0 > 57) {
                        if(v0 >= 97 && v0 <= 102) {
                            v2 = v2 * 16 + (v0 - 87);
                            goto label_39;
                        }

                        if(v0 < 65) {
                            break;
                        }

                        if(v0 > 70) {
                            break;
                        }

                        v2 = v2 * 16 + (v0 - 55);
                    }
                    else {
                        v2 = v2 * 16 + (v0 - 48);
                    }

                label_39:
                }
            }
        }

    label_3:
        return v6;
    }

    public void reset() {
        Pools.get(GlyphRun.class).freeAll(this.runs);
        this.runs.clear();
        this.width = 0f;
        this.height = 0f;
    }

    public void setText(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign, boolean wrap, String truncate) {
        Object v4;
        int v15;
        float v25;
        int v14;
        float v17;
        float v16;
        int v11;
        int v20;
        int v9;
        Object v6;
        Object v22_1;
        if(targetWidth == 0f) {
            wrap = false;
        }

        if(truncate != null) {
            wrap = true;
        }

        BitmapFontData v5 = font.data;
        boolean v19 = v5.markupEnabled;
        Pool v10 = Pools.get(GlyphRun.class);
        v10.freeAll(this.runs);
        this.runs.clear();
        float v28 = 0f;
        float v31 = 0f;
        float v27 = 0f;
        int v18 = 0;
        Color v22 = color;
        GlyphLayout.colorStack.add(color);
        Pool v12 = Pools.get(Color.class);
        int v24 = start;
        int v26 = start;
        while(true) {
            int v23 = -1;
            int v21 = 0;
            if(v26 != end) {
                start = v26 + 1;
                switch(str.charAt(v26)) {
                    case 10: {
                        goto label_103;
                    }
                    case 91: {
                        goto label_106;
                    }
                }

                goto label_52;
            label_103:
                v23 = start - 1;
                v21 = 1;
                goto label_52;
            label_106:
                if(v19) {
                    int v13 = this.parseColorMarkup(str, start, end, v12);
                    if(v13 >= 0) {
                        v23 = start - 1;
                        start += v13 + 1;
                        v22_1 = GlyphLayout.colorStack.peek();
                        goto label_52;
                    }
                    else {
                        goto label_52;
                    }
                }
                else {
                label_52:
                    if(v23 != -1) {
                        v6 = v10.obtain();
                        this.runs.add(v6);
                        ((GlyphRun)v6).color.set(color);
                        ((GlyphRun)v6).x = v28;
                        ((GlyphRun)v6).y = v31;
                        v5.getGlyphs(((GlyphRun)v6), str, v24, v23);
                        FloatArray v30 = ((GlyphRun)v6).xAdvances;
                        v9 = 0;
                        v20 = v30.size;
                        while(v9 < v20) {
                            float v29 = v30.get(v9);
                            v28 += v29;
                            if(!wrap || v28 <= targetWidth || v9 <= 0 || v28 - (v29 - (((float)((GlyphRun)v6).glyphs.get(v9).width))) <= targetWidth) {
                                ((GlyphRun)v6).width += v29;
                            }
                            else if(truncate != null) {
                                this.truncate(v5, ((GlyphRun)v6), targetWidth, truncate, v9, v10);
                                goto label_38;
                            }
                            else {
                                Object v7 = v10.obtain();
                                this.runs.add(v7);
                                this.wrap(v5, ((GlyphRun)v6), ((GlyphRun)v7), Math.max(1, v5.getWrapIndex(((GlyphRun)v6).glyphs, v9)), v9);
                                v27 = Math.max(v27, ((GlyphRun)v6).width);
                                v28 = 0f;
                                v31 += v5.down;
                                ++v18;
                                ((GlyphRun)v7).x = 0f;
                                ((GlyphRun)v7).y = v31;
                                v9 = -1;
                                v20 = ((GlyphRun)v7).xAdvances.size;
                                v30 = ((GlyphRun)v7).xAdvances;
                                v6 = v7;
                            }

                            ++v9;
                        }

                        if(v21 != 0) {
                            v27 = Math.max(v27, v28);
                            v28 = 0f;
                            v31 += v5.down;
                            ++v18;
                        }

                        v24 = start;
                        color = ((Color)v22_1);
                    }

                    goto label_161;
                }
            }
            else if(v24 == end) {
            }
            else {
                v23 = end;
                start = v26;
                goto label_52;
            }

        label_38:
            v27 = Math.max(v27, v28);
            v9 = 1;
            v20 = GlyphLayout.colorStack.size;
            while(v9 < v20) {
                v12.free(GlyphLayout.colorStack.get(v9));
                ++v9;
            }

            GlyphLayout.colorStack.clear();
            if((halign & 8) == 0) {
                if((halign & 1) != 0) {
                    v11 = 1;
                }
                else {
                    v11 = 0;
                }

                v16 = 0f;
                v17 = -2147483648f;
                v14 = 0;
                v20 = this.runs.size;
                v9 = 0;
                break;
            label_161:
                v26 = start;
                continue;
            }

            goto label_226;
        }

        while(true) {
            if(v9 >= v20) {
                break;
            }

            v6 = this.runs.get(v9);
            if(((GlyphRun)v6).y != v17) {
                v17 = ((GlyphRun)v6).y;
                v25 = targetWidth - v16;
                if(v11 != 0) {
                    v25 /= 2f;
                    v15 = v14;
                }
                else {
                    v15 = v14;
                }

                while(v15 < v9) {
                    v4 = this.runs.get(v15);
                    ((GlyphRun)v4).x += v25;
                    ++v15;
                }

                v16 = 0f;
                v14 = v15;
            }

            v16 += ((GlyphRun)v6).width;
            ++v9;
        }

        v25 = targetWidth - v16;
        if(v11 != 0) {
            v25 /= 2f;
            v15 = v14;
        }
        else {
            v15 = v14;
        }

        while(v15 < v20) {
            v4 = this.runs.get(v15);
            ((GlyphRun)v4).x += v25;
            ++v15;
        }

    label_226:
        this.width = v27;
        this.height = v5.capHeight + (((float)v18)) * v5.lineHeight;
    }

    public void setText(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
        this.setText(font, str, 0, str.length(), color, targetWidth, halign, wrap, null);
    }

    public void setText(BitmapFont font, CharSequence str) {
        this.setText(font, str, 0, str.length(), font.getColor(), 0f, 8, false, null);
    }

    public String toString() {
        String v3;
        if(this.runs.size == 0) {
            v3 = "";
        }
        else {
            StringBuilder v0 = new StringBuilder(128);
            int v1 = 0;
            int v2 = this.runs.size;
            while(v1 < v2) {
                v0.append(this.runs.get(v1).toString());
                v0.append('\n');
                ++v1;
            }

            v0.setLength(v0.length() - 1);
            v3 = v0.toString();
        }

        return v3;
    }

    private void truncate(BitmapFontData fontData, GlyphRun run, float targetWidth, String truncate, int widthIndex, Pool arg12) {
        Object v2 = arg12.obtain();
        fontData.getGlyphs(((GlyphRun)v2), ((CharSequence)truncate), 0, truncate.length());
        float v3 = targetWidth;
        int v1 = ((GlyphRun)v2).glyphs.size;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            v3 -= ((GlyphRun)v2).xAdvances.get(v0);
        }

        while(run.width > v3) {
            --widthIndex;
            run.width -= run.xAdvances.get(widthIndex);
        }

        run.glyphs.truncate(widthIndex);
        run.xAdvances.truncate(widthIndex);
        run.glyphs.addAll(((GlyphRun)v2).glyphs);
        run.xAdvances.addAll(((GlyphRun)v2).xAdvances);
        run.width += v3;
        arg12.free(v2);
    }

    private void wrap(BitmapFontData fontData, GlyphRun first, GlyphRun second, int wrapIndex, int widthIndex) {
        second.color.set(first.color);
        int v2;
        for(v2 = widthIndex; true; v2 = widthIndex) {
            widthIndex = v2 - 1;
            if(v2 <= wrapIndex) {
                break;
            }

            first.width -= first.xAdvances.get(widthIndex);
        }

        second.glyphs.addAll(first.glyphs, wrapIndex, first.glyphs.size - wrapIndex);
        second.xAdvances.addAll(first.xAdvances, wrapIndex, first.xAdvances.size - wrapIndex);
        first.glyphs.truncate(wrapIndex);
        first.xAdvances.truncate(wrapIndex);
        int v1 = wrapIndex - 1;
        while(true) {
            if(v1 >= 0) {
                if(fontData.isWhitespace(((char)first.glyphs.get(v1).id))) {
                    first.width -= first.xAdvances.get(v1);
                    --v1;
                    continue;
                }
                else {
                    break;
                }
            }

            return;
        }

        if(v1 > 0) {
            first.glyphs.truncate(v1 + 1);
            first.xAdvances.truncate(v1 + 1);
        }
    }
}

