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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Image extends Widget {
    private int align;
    private Drawable drawable;
    private float imageHeight;
    private float imageWidth;
    private float imageX;
    private float imageY;
    private Scaling scaling;

    public Image() {
        this(null);
    }

    public Image(TextureRegion region) {
        this(new TextureRegionDrawable(region), Scaling.stretch, 1);
    }

    public Image(Drawable drawable) {
        this(drawable, Scaling.stretch, 1);
    }

    public Image(Texture texture) {
        this(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    public Image(NinePatch patch) {
        this(new NinePatchDrawable(patch), Scaling.stretch, 1);
    }

    public Image(Drawable drawable, Scaling scaling, int align) {
        super();
        this.align = 1;
        this.setDrawable(drawable);
        this.scaling = scaling;
        this.align = align;
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public Image(Skin skin, String drawableName) {
        this(skin.getDrawable(drawableName), Scaling.stretch, 1);
    }

    public Image(Drawable drawable, Scaling scaling) {
        this(drawable, scaling, 1);
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        Color v11 = this.getColor();
        batch.setColor(v11.r, v11.g, v11.b, v11.a * parentAlpha);
        float v12 = this.getX();
        float v13 = this.getY();
        float v8 = this.getScaleX();
        float v9 = this.getScaleY();
        if((this.drawable instanceof TransformDrawable)) {
            float v10 = this.getRotation();
            if(v8 == 1f && v9 == 1f && v10 == 0f) {
                goto label_38;
            }

            this.drawable.draw(batch, v12 + this.imageX, v13 + this.imageY, this.getOriginX() - this.imageX, this.getOriginY() - this.imageY, this.imageWidth, this.imageHeight, v8, v9, v10);
        }
        else {
        label_38:
            if(this.drawable == null) {
                return;
            }

            this.drawable.draw(batch, v12 + this.imageX, v13 + this.imageY, this.imageWidth * v8, this.imageHeight * v9);
        }
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public float getImageHeight() {
        return this.imageHeight;
    }

    public float getImageWidth() {
        return this.imageWidth;
    }

    public float getImageX() {
        return this.imageX;
    }

    public float getImageY() {
        return this.imageY;
    }

    public float getMinHeight() {
        return 0;
    }

    public float getMinWidth() {
        return 0;
    }

    public float getPrefHeight() {
        float v0;
        if(this.drawable != null) {
            v0 = this.drawable.getMinHeight();
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public float getPrefWidth() {
        float v0;
        if(this.drawable != null) {
            v0 = this.drawable.getMinWidth();
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public void layout() {
        float v7 = 2f;
        if(this.drawable != null) {
            float v2 = this.drawable.getMinWidth();
            float v1 = this.drawable.getMinHeight();
            float v4 = this.getWidth();
            float v0 = this.getHeight();
            Vector2 v3 = this.scaling.apply(v2, v1, v4, v0);
            this.imageWidth = v3.x;
            this.imageHeight = v3.y;
            if((this.align & 8) != 0) {
                this.imageX = 0f;
            }
            else if((this.align & 16) != 0) {
                this.imageX = ((float)(((int)(v4 - this.imageWidth))));
            }
            else {
                this.imageX = ((float)(((int)(v4 / v7 - this.imageWidth / v7))));
            }

            if((this.align & 2) == 0) {
                goto label_47;
            }

            this.imageY = ((float)(((int)(v0 - this.imageHeight))));
            return;
        label_47:
            if((this.align & 4) == 0) {
                goto label_52;
            }

            this.imageY = 0f;
            return;
        label_52:
            this.imageY = ((float)(((int)(v0 / v7 - this.imageHeight / v7))));
        }
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public void setDrawable(Drawable drawable) {
        if(this.drawable != drawable) {
            if(drawable != null) {
                if(this.getPrefWidth() == drawable.getMinWidth() && this.getPrefHeight() == drawable.getMinHeight()) {
                    goto label_11;
                }

                this.invalidateHierarchy();
            }
            else {
                this.invalidateHierarchy();
            }

        label_11:
            this.drawable = drawable;
        }
    }

    public void setDrawable(Skin skin, String drawableName) {
        this.setDrawable(skin.getDrawable(drawableName));
    }

    public void setScaling(Scaling scaling) {
        if(scaling == null) {
            throw new IllegalArgumentException("scaling cannot be null.");
        }

        this.scaling = scaling;
    }
}

