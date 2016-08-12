// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.StringBuilder;

public class Label extends Widget {
    public class LabelStyle {
        public Drawable background;
        public BitmapFont font;
        public Color fontColor;

        public LabelStyle(BitmapFont font, Color fontColor) {
            super();
            this.font = font;
            this.fontColor = fontColor;
        }

        public LabelStyle() {
            super();
        }

        public LabelStyle(LabelStyle style) {
            super();
            this.font = style.font;
            if(style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }

            this.background = style.background;
        }
    }

    private final Vector2 bounds;
    private BitmapFontCache cache;
    private boolean ellipsis;
    private float fontScaleX;
    private float fontScaleY;
    private int labelAlign;
    private float lastPrefHeight;
    private final GlyphLayout layout;
    private int lineAlign;
    private boolean sizeInvalid;
    private LabelStyle style;
    private static final Color tempColor;
    private final StringBuilder text;
    private boolean wrap;

    static  {
        Label.tempColor = new Color();
    }

    public Label(CharSequence text, LabelStyle style) {
        super();
        this.layout = new GlyphLayout();
        this.bounds = new Vector2();
        this.text = new StringBuilder();
        this.labelAlign = 8;
        this.lineAlign = 8;
        this.sizeInvalid = true;
        this.fontScaleX = 1f;
        this.fontScaleY = 1f;
        if(text != null) {
            this.text.append(text);
        }

        this.setStyle(style);
        if(text != null && text.length() > 0) {
            this.setSize(this.getPrefWidth(), this.getPrefHeight());
        }
    }

    public Label(CharSequence text, Skin skin) {
        this(text, skin.get(LabelStyle.class));
    }

    public Label(CharSequence text, Skin skin, String styleName) {
        this(text, skin.get(styleName, LabelStyle.class));
    }

    public Label(CharSequence text, Skin skin, String fontName, Color color) {
        this(text, new LabelStyle(skin.getFont(fontName), color));
    }

    public Label(CharSequence text, Skin skin, String fontName, String colorName) {
        this(text, new LabelStyle(skin.getFont(fontName), skin.getColor(colorName)));
    }

    private void computeSize() {
        this.sizeInvalid = false;
        if(this.wrap) {
            float v4 = this.getWidth();
            if(this.style.background != null) {
                v4 -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
            }

            this.layout.setText(this.cache.getFont(), this.text, Color.WHITE, v4, 8, true);
        }
        else {
            this.layout.setText(this.cache.getFont(), this.text);
        }

        this.bounds.set(this.layout.width, this.layout.height);
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        Color v6 = Label.tempColor.set(this.getColor());
        v6.a *= parentAlpha;
        if(this.style.background != null) {
            batch.setColor(v6.r, v6.g, v6.b, v6.a);
            this.style.background.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

        if(this.style.fontColor != null) {
            v6.mul(this.style.fontColor);
        }

        this.cache.tint(v6);
        this.cache.setPosition(this.getX(), this.getY());
        this.cache.draw(batch);
    }

    protected BitmapFontCache getBitmapFontCache() {
        return this.cache;
    }

    public float getFontScaleX() {
        return this.fontScaleX;
    }

    public float getFontScaleY() {
        return this.fontScaleY;
    }

    public float getPrefHeight() {
        if(this.sizeInvalid) {
            this.scaleAndComputeSize();
        }

        float v1 = this.bounds.y - this.style.font.getDescent() * 2f;
        Drawable v0 = this.style.background;
        if(v0 != null) {
            v1 += v0.getTopHeight() + v0.getBottomHeight();
        }

        return v1;
    }

    public float getPrefWidth() {
        float v1;
        if(this.wrap) {
            v1 = 0f;
        }
        else {
            if(this.sizeInvalid) {
                this.scaleAndComputeSize();
            }

            v1 = this.bounds.x;
            Drawable v0 = this.style.background;
            if(v0 == null) {
                goto label_3;
            }

            v1 += v0.getLeftWidth() + v0.getRightWidth();
        }

    label_3:
        return v1;
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public StringBuilder getText() {
        return this.text;
    }

    public Vector2 getTextBounds() {
        if(this.sizeInvalid) {
            this.scaleAndComputeSize();
        }

        return this.bounds;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    public void layout() {
        String v11;
        float v8;
        float v2;
        BitmapFont v3 = this.cache.getFont();
        float v14 = v3.getScaleX();
        float v15 = v3.getScaleY();
        if(this.fontScaleX != 1f || this.fontScaleY != 1f) {
            v3.getData().setScale(this.fontScaleX, this.fontScaleY);
        }

        if(this.sizeInvalid) {
            this.computeSize();
        }

        if(this.wrap) {
            float v16 = this.getPrefHeight();
            if(v16 != this.lastPrefHeight) {
                this.lastPrefHeight = v16;
                this.invalidateHierarchy();
            }
        }

        float v19 = this.getWidth();
        float v13 = this.getHeight();
        float v18 = this.layout.width;
        float v17 = this.layout.height;
        Drawable v12 = this.style.background;
        float v20 = 0f;
        float v21 = 0f;
        if(v12 != null) {
            v20 = v12.getLeftWidth();
            v21 = v12.getBottomHeight();
            v19 -= v12.getLeftWidth() + v12.getRightWidth();
            v13 -= v12.getBottomHeight() + v12.getTopHeight();
        }

        if((this.labelAlign & 2) != 0) {
            if(this.cache.getFont().isFlipped()) {
                v2 = 0f;
            }
            else {
                v2 = v13 - v17;
            }

            v21 = v21 + v2 + this.style.font.getDescent();
        }
        else {
            if((this.labelAlign & 4) == 0) {
                goto label_154;
            }

            if(this.cache.getFont().isFlipped()) {
                v2 = v13 - v17;
            }
            else {
                v2 = 0f;
            }

            v21 = v21 + v2 - this.style.font.getDescent();
            goto label_76;
        label_154:
            v21 += ((float)(((int)((v13 - v17) / 2f))));
        }

    label_76:
        if(!this.cache.getFont().isFlipped()) {
            v21 += v17;
        }

        if((this.labelAlign & 8) == 0) {
            if((this.labelAlign & 16) != 0) {
                v20 += v19 - v18;
            }
            else {
                v20 += ((float)(((int)((v19 - v18) / 2f))));
            }
        }

        GlyphLayout v2_1 = this.layout;
        StringBuilder v4 = this.text;
        int v6 = this.text.length;
        Color v7 = Color.WHITE;
        if(!this.ellipsis || v19 >= v18) {
            v8 = v18;
        }
        else {
            v8 = v19;
        }

        int v9 = this.lineAlign;
        boolean v10 = this.wrap;
        if(this.ellipsis) {
            v11 = "...";
        }
        else {
            v11 = null;
        }

        v2_1.setText(v3, ((CharSequence)v4), 0, v6, v7, v8, v9, v10, v11);
        this.cache.setText(this.layout, v20, v21);
        if(this.fontScaleX != 1f || this.fontScaleY != 1f) {
            v3.getData().setScale(v14, v15);
        }
    }

    private void scaleAndComputeSize() {
        float v6 = 1f;
        BitmapFont v0 = this.cache.getFont();
        float v1 = v0.getScaleX();
        float v2 = v0.getScaleY();
        if(this.fontScaleX != v6 || this.fontScaleY != v6) {
            v0.getData().setScale(this.fontScaleX, this.fontScaleY);
        }

        this.computeSize();
        if(this.fontScaleX != v6 || this.fontScaleY != v6) {
            v0.getData().setScale(v1, v2);
        }
    }

    public void setAlignment(int alignment) {
        this.setAlignment(alignment, alignment);
    }

    public void setAlignment(int labelAlign, int lineAlign) {
        this.labelAlign = labelAlign;
        if((lineAlign & 8) != 0) {
            this.lineAlign = 8;
        }
        else if((lineAlign & 16) != 0) {
            this.lineAlign = 16;
        }
        else {
            this.lineAlign = 1;
        }

        this.invalidate();
    }

    public void setEllipsis(boolean ellipsis) {
        this.ellipsis = ellipsis;
    }

    public void setFontScale(float fontScale) {
        this.fontScaleX = fontScale;
        this.fontScaleY = fontScale;
        this.invalidateHierarchy();
    }

    public void setFontScale(float fontScaleX, float fontScaleY) {
        this.fontScaleX = fontScaleX;
        this.fontScaleY = fontScaleY;
        this.invalidateHierarchy();
    }

    public void setFontScaleX(float fontScaleX) {
        this.fontScaleX = fontScaleX;
        this.invalidateHierarchy();
    }

    public void setFontScaleY(float fontScaleY) {
        this.fontScaleY = fontScaleY;
        this.invalidateHierarchy();
    }

    public void setStyle(LabelStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        if(style.font == null) {
            throw new IllegalArgumentException("Missing LabelStyle font.");
        }

        this.style = style;
        this.cache = new BitmapFontCache(style.font, style.font.usesIntegerPositions());
        this.invalidateHierarchy();
    }

    public void setText(CharSequence newText) {
        String v3_1;
        if(newText == null) {
            v3_1 = "";
        }

        if(((((CharSequence)v3_1)) instanceof StringBuilder)) {
            if(!this.text.equals(v3_1)) {
                this.text.setLength(0);
                this.text.append(((StringBuilder)v3_1));
                goto label_13;
            }
        }
        else if(!this.textEquals(((CharSequence)v3_1))) {
            this.text.setLength(0);
            this.text.append(((CharSequence)v3_1));
        label_13:
            this.invalidateHierarchy();
        }
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
        this.invalidateHierarchy();
    }

    public boolean textEquals(CharSequence other) {
        boolean v3 = false;
        int v2 = this.text.length;
        char[] v0 = this.text.chars;
        if(v2 == other.length()) {
            int v1 = 0;
            while(true) {
                if(v1 >= v2) {
                    break;
                }
                else if(v0[v1] == other.charAt(v1)) {
                    ++v1;
                    continue;
                }

                goto label_7;
            }

            v3 = true;
        }

    label_7:
        return v3;
    }

    public String toString() {
        return super.toString() + ": " + this.text;
    }
}

