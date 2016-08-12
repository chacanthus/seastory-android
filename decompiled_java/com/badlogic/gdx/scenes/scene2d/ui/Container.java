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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public class Container extends WidgetGroup {
    private Actor actor;
    private int align;
    private Drawable background;
    private boolean clip;
    private float fillX;
    private float fillY;
    private Value maxHeight;
    private Value maxWidth;
    private Value minHeight;
    private Value minWidth;
    private Value padBottom;
    private Value padLeft;
    private Value padRight;
    private Value padTop;
    private Value prefHeight;
    private Value prefWidth;
    private boolean round;

    public Container() {
        super();
        this.minWidth = Value.minWidth;
        this.minHeight = Value.minHeight;
        this.prefWidth = Value.prefWidth;
        this.prefHeight = Value.prefHeight;
        this.maxWidth = Value.zero;
        this.maxHeight = Value.zero;
        this.padTop = Value.zero;
        this.padLeft = Value.zero;
        this.padBottom = Value.zero;
        this.padRight = Value.zero;
        this.round = true;
        this.setTouchable(Touchable.childrenOnly);
        this.setTransform(false);
    }

    public Container(Actor arg1) {
        this();
        this.setActor(arg1);
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use Container#setActor.");
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        throw new UnsupportedOperationException("Use Container#setActor.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use Container#setActor.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use Container#setActor.");
    }

    public Container align(int align) {
        this.align = align;
        return this;
    }

    public Container background(Drawable background) {
        this.setBackground(background);
        return this;
    }

    public Container bottom() {
        this.align |= 4;
        this.align &= -3;
        return this;
    }

    public Container center() {
        this.align = 1;
        return this;
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        if(this.isTransform()) {
            this.applyTransform(batch, this.computeTransform());
            this.drawBackground(batch, parentAlpha, 0f, 0f);
            if(this.clip) {
                batch.flush();
                float v1 = this.padLeft.get(((Actor)this));
                float v0 = this.padBottom.get(((Actor)this));
                if(this.clipBegin(v1, v0, this.getWidth() - v1 - this.padRight.get(((Actor)this)), this.getHeight() - v0 - this.padTop.get(((Actor)this)))) {
                    this.drawChildren(batch, parentAlpha);
                    batch.flush();
                    this.clipEnd();
                }
            }
            else {
                this.drawChildren(batch, parentAlpha);
            }

            this.resetTransform(batch);
        }
        else {
            this.drawBackground(batch, parentAlpha, this.getX(), this.getY());
            super.draw(batch, parentAlpha);
        }
    }

    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if(this.background != null) {
            Color v6 = this.getColor();
            batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
            this.background.draw(batch, x, y, this.getWidth(), this.getHeight());
        }
    }

    public void drawDebug(ShapeRenderer shapes) {
        boolean v0;
        this.validate();
        if(this.isTransform()) {
            this.applyTransform(shapes, this.computeTransform());
            if(this.clip) {
                shapes.flush();
                float v2 = this.padLeft.get(((Actor)this));
                float v1 = this.padBottom.get(((Actor)this));
                if(this.background == null) {
                    v0 = this.clipBegin(0f, 0f, this.getWidth(), this.getHeight());
                }
                else {
                    v0 = this.clipBegin(v2, v1, this.getWidth() - v2 - this.padRight.get(((Actor)this)), this.getHeight() - v1 - this.padTop.get(((Actor)this)));
                }

                if(!v0) {
                    goto label_21;
                }

                this.drawDebugChildren(shapes);
                this.clipEnd();
            }
            else {
                this.drawDebugChildren(shapes);
            }

        label_21:
            this.resetTransform(shapes);
        }
        else {
            super.drawDebug(shapes);
        }
    }

    public Container fill() {
        this.fillX = 1f;
        this.fillY = 1f;
        return this;
    }

    public Container fill(float x, float y) {
        this.fillX = x;
        this.fillY = y;
        return this;
    }

    public Container fill(boolean fill) {
        float v0;
        float v1 = 1f;
        if(fill) {
            v0 = v1;
        }
        else {
            v0 = 0f;
        }

        this.fillX = v0;
        if(!fill) {
            v1 = 0f;
        }

        this.fillY = v1;
        return this;
    }

    public Container fill(boolean x, boolean y) {
        float v0;
        float v1 = 1f;
        if(x) {
            v0 = v1;
        }
        else {
            v0 = 0f;
        }

        this.fillX = v0;
        if(!y) {
            v1 = 0f;
        }

        this.fillY = v1;
        return this;
    }

    public Container fillX() {
        this.fillX = 1f;
        return this;
    }

    public Container fillY() {
        this.fillY = 1f;
        return this;
    }

    public Actor getActor() {
        return this.actor;
    }

    public int getAlign() {
        return this.align;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public boolean getClip() {
        return this.clip;
    }

    public float getFillX() {
        return this.fillX;
    }

    public float getFillY() {
        return this.fillY;
    }

    public float getMaxHeight() {
        float v0 = this.maxHeight.get(this.actor);
        if(v0 > 0f) {
            v0 += this.padTop.get(((Actor)this)) + this.padBottom.get(((Actor)this));
        }

        return v0;
    }

    public Value getMaxHeightValue() {
        return this.maxHeight;
    }

    public float getMaxWidth() {
        float v0 = this.maxWidth.get(this.actor);
        if(v0 > 0f) {
            v0 += this.padLeft.get(((Actor)this)) + this.padRight.get(((Actor)this));
        }

        return v0;
    }

    public Value getMaxWidthValue() {
        return this.maxWidth;
    }

    public float getMinHeight() {
        return this.minHeight.get(this.actor) + this.padTop.get(((Actor)this)) + this.padBottom.get(((Actor)this));
    }

    public Value getMinHeightValue() {
        return this.minHeight;
    }

    public float getMinWidth() {
        return this.minWidth.get(this.actor) + this.padLeft.get(((Actor)this)) + this.padRight.get(((Actor)this));
    }

    public float getPadBottom() {
        return this.padBottom.get(((Actor)this));
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadLeft() {
        return this.padLeft.get(((Actor)this));
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadRight() {
        return this.padRight.get(((Actor)this));
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadTop() {
        return this.padTop.get(((Actor)this));
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadX() {
        return this.padLeft.get(((Actor)this)) + this.padRight.get(((Actor)this));
    }

    public float getPadY() {
        return this.padTop.get(((Actor)this)) + this.padBottom.get(((Actor)this));
    }

    public float getPrefHeight() {
        float v0 = this.prefHeight.get(this.actor);
        if(this.background != null) {
            v0 = Math.max(v0, this.background.getMinHeight());
        }

        return this.padTop.get(((Actor)this)) + v0 + this.padBottom.get(((Actor)this));
    }

    public Value getPrefHeightValue() {
        return this.prefHeight;
    }

    public float getPrefWidth() {
        float v0 = this.prefWidth.get(this.actor);
        if(this.background != null) {
            v0 = Math.max(v0, this.background.getMinWidth());
        }

        return this.padLeft.get(((Actor)this)) + v0 + this.padRight.get(((Actor)this));
    }

    public Value getPrefWidthValue() {
        return this.prefWidth;
    }

    public Container height(float height) {
        this.height(new Fixed(height));
        return this;
    }

    public Container height(Value height) {
        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minHeight = height;
        this.prefHeight = height;
        this.maxHeight = height;
        return this;
    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor v0 = null;
        if(this.clip) {
            if((touchable) && this.getTouchable() == Touchable.disabled) {
                goto label_8;
            }

            if(x < 0f) {
                goto label_8;
            }

            if(x >= this.getWidth()) {
                goto label_8;
            }

            if(y < 0f) {
                goto label_8;
            }

            if(y >= this.getHeight()) {
                goto label_8;
            }

            goto label_15;
        }
        else {
        label_15:
            v0 = super.hit(x, y, touchable);
        }

    label_8:
        return v0;
    }

    public void layout() {
        float v3;
        float v12;
        if(this.actor != null) {
            float v9 = this.padLeft.get(this);
            float v8 = this.padBottom.get(this);
            float v2 = this.getWidth() - v9 - this.padRight.get(((Actor)this));
            float v1 = this.getHeight() - v8 - this.padTop.get(((Actor)this));
            float v7 = this.minWidth.get(this.actor);
            float v6 = this.minHeight.get(this.actor);
            float v11 = this.prefWidth.get(this.actor);
            float v10 = this.prefHeight.get(this.actor);
            float v5 = this.maxWidth.get(this.actor);
            float v4 = this.maxHeight.get(this.actor);
            if(this.fillX > 0f) {
                v12 = v2 * this.fillX;
            }
            else {
                v12 = Math.min(v11, v2);
            }

            if(v12 < v7) {
                v12 = v7;
            }

            if(v5 > 0f && v12 > v5) {
                v12 = v5;
            }

            if(this.fillY > 0f) {
                v3 = v1 * this.fillY;
            }
            else {
                v3 = Math.min(v10, v1);
            }

            if(v3 < v6) {
                v3 = v6;
            }

            if(v4 > 0f && v3 > v4) {
                v3 = v4;
            }

            float v13 = v9;
            if((this.align & 16) != 0) {
                v13 += v2 - v12;
            }
            else if((this.align & 8) == 0) {
                v13 += (v2 - v12) / 2f;
            }

            float v14 = v8;
            if((this.align & 2) != 0) {
                v14 += v1 - v3;
            }
            else if((this.align & 4) == 0) {
                v14 += (v1 - v3) / 2f;
            }

            if(this.round) {
                v13 = ((float)Math.round(v13));
                v14 = ((float)Math.round(v14));
                v12 = ((float)Math.round(v12));
                v3 = ((float)Math.round(v3));
            }

            this.actor.setBounds(v13, v14, v12, v3);
            if(!(this.actor instanceof Layout)) {
                return;
            }

            this.actor.validate();
        }
    }

    public Container left() {
        this.align |= 8;
        this.align &= -17;
        return this;
    }

    public Container maxHeight(float maxHeight) {
        this.maxHeight = new Fixed(maxHeight);
        return this;
    }

    public Container maxHeight(Value maxHeight) {
        if(maxHeight == null) {
            throw new IllegalArgumentException("maxHeight cannot be null.");
        }

        this.maxHeight = maxHeight;
        return this;
    }

    public Container maxSize(float size) {
        this.maxSize(new Fixed(size));
        return this;
    }

    public Container maxSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.maxWidth = size;
        this.maxHeight = size;
        return this;
    }

    public Container maxSize(float width, float height) {
        this.maxSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Container maxSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.maxWidth = width;
        this.maxHeight = height;
        return this;
    }

    public Container maxWidth(float maxWidth) {
        this.maxWidth = new Fixed(maxWidth);
        return this;
    }

    public Container maxWidth(Value maxWidth) {
        if(maxWidth == null) {
            throw new IllegalArgumentException("maxWidth cannot be null.");
        }

        this.maxWidth = maxWidth;
        return this;
    }

    public Container minHeight(float minHeight) {
        this.minHeight = new Fixed(minHeight);
        return this;
    }

    public Container minHeight(Value minHeight) {
        if(minHeight == null) {
            throw new IllegalArgumentException("minHeight cannot be null.");
        }

        this.minHeight = minHeight;
        return this;
    }

    public Container minSize(float size) {
        this.minSize(new Fixed(size));
        return this;
    }

    public Container minSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.minWidth = size;
        this.minHeight = size;
        return this;
    }

    public Container minSize(float width, float height) {
        this.minSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Container minSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minWidth = width;
        this.minHeight = height;
        return this;
    }

    public Container minWidth(float minWidth) {
        this.minWidth = new Fixed(minWidth);
        return this;
    }

    public Container minWidth(Value minWidth) {
        if(minWidth == null) {
            throw new IllegalArgumentException("minWidth cannot be null.");
        }

        this.minWidth = minWidth;
        return this;
    }

    public Container pad(float pad) {
        Fixed v0 = new Fixed(pad);
        this.padTop = ((Value)v0);
        this.padLeft = ((Value)v0);
        this.padBottom = ((Value)v0);
        this.padRight = ((Value)v0);
        return this;
    }

    public Container pad(float top, float left, float bottom, float right) {
        this.padTop = new Fixed(top);
        this.padLeft = new Fixed(left);
        this.padBottom = new Fixed(bottom);
        this.padRight = new Fixed(right);
        return this;
    }

    public Container pad(Value pad) {
        if(pad == null) {
            throw new IllegalArgumentException("pad cannot be null.");
        }

        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        return this;
    }

    public Container pad(Value top, Value left, Value bottom, Value right) {
        if(top == null) {
            throw new IllegalArgumentException("top cannot be null.");
        }

        if(left == null) {
            throw new IllegalArgumentException("left cannot be null.");
        }

        if(bottom == null) {
            throw new IllegalArgumentException("bottom cannot be null.");
        }

        if(right == null) {
            throw new IllegalArgumentException("right cannot be null.");
        }

        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        return this;
    }

    public Container padBottom(float padBottom) {
        this.padBottom = new Fixed(padBottom);
        return this;
    }

    public Container padBottom(Value padBottom) {
        if(padBottom == null) {
            throw new IllegalArgumentException("padBottom cannot be null.");
        }

        this.padBottom = padBottom;
        return this;
    }

    public Container padLeft(float padLeft) {
        this.padLeft = new Fixed(padLeft);
        return this;
    }

    public Container padLeft(Value padLeft) {
        if(padLeft == null) {
            throw new IllegalArgumentException("padLeft cannot be null.");
        }

        this.padLeft = padLeft;
        return this;
    }

    public Container padRight(float padRight) {
        this.padRight = new Fixed(padRight);
        return this;
    }

    public Container padRight(Value padRight) {
        if(padRight == null) {
            throw new IllegalArgumentException("padRight cannot be null.");
        }

        this.padRight = padRight;
        return this;
    }

    public Container padTop(float padTop) {
        this.padTop = new Fixed(padTop);
        return this;
    }

    public Container padTop(Value padTop) {
        if(padTop == null) {
            throw new IllegalArgumentException("padTop cannot be null.");
        }

        this.padTop = padTop;
        return this;
    }

    public Container prefHeight(float prefHeight) {
        this.prefHeight = new Fixed(prefHeight);
        return this;
    }

    public Container prefHeight(Value prefHeight) {
        if(prefHeight == null) {
            throw new IllegalArgumentException("prefHeight cannot be null.");
        }

        this.prefHeight = prefHeight;
        return this;
    }

    public Container prefSize(float size) {
        this.prefSize(new Fixed(size));
        return this;
    }

    public Container prefSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.prefWidth = size;
        this.prefHeight = size;
        return this;
    }

    public Container prefSize(float width, float height) {
        this.prefSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Container prefSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.prefWidth = width;
        this.prefHeight = height;
        return this;
    }

    public Container prefWidth(float prefWidth) {
        this.prefWidth = new Fixed(prefWidth);
        return this;
    }

    public Container prefWidth(Value prefWidth) {
        if(prefWidth == null) {
            throw new IllegalArgumentException("prefWidth cannot be null.");
        }

        this.prefWidth = prefWidth;
        return this;
    }

    public boolean removeActor(Actor actor) {
        boolean v0;
        if(actor != this.actor) {
            v0 = false;
        }
        else {
            this.setActor(null);
            v0 = true;
        }

        return v0;
    }

    public Container right() {
        this.align |= 16;
        this.align &= -9;
        return this;
    }

    public void setActor(Actor arg3) {
        if((((Container)arg3)) == this) {
            throw new IllegalArgumentException("actor cannot be the Container.");
        }

        if(arg3 != this.actor) {
            if(this.actor != null) {
                super.removeActor(this.actor);
            }

            this.actor = arg3;
            if(arg3 == null) {
                return;
            }

            super.addActor(arg3);
        }
    }

    public void setBackground(Drawable background) {
        this.setBackground(background, true);
    }

    public void setBackground(Drawable background, boolean adjustPadding) {
        if(this.background != background) {
            this.background = background;
            if(adjustPadding) {
                if(background == null) {
                    this.pad(Value.zero);
                }
                else {
                    this.pad(background.getTopHeight(), background.getLeftWidth(), background.getBottomHeight(), background.getRightWidth());
                }

                this.invalidate();
            }
        }
    }

    public void setClip(boolean enabled) {
        this.clip = enabled;
        this.setTransform(enabled);
        this.invalidate();
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public Container size(float size) {
        this.size(new Fixed(size));
        return this;
    }

    public Container size(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.minWidth = size;
        this.minHeight = size;
        this.prefWidth = size;
        this.prefHeight = size;
        this.maxWidth = size;
        this.maxHeight = size;
        return this;
    }

    public Container size(float width, float height) {
        this.size(new Fixed(width), new Fixed(height));
        return this;
    }

    public Container size(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minWidth = width;
        this.minHeight = height;
        this.prefWidth = width;
        this.prefHeight = height;
        this.maxWidth = width;
        this.maxHeight = height;
        return this;
    }

    public Container top() {
        this.align |= 2;
        this.align &= -5;
        return this;
    }

    public Container width(float width) {
        this.width(new Fixed(width));
        return this;
    }

    public Container width(Value width) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        this.minWidth = width;
        this.prefWidth = width;
        this.maxWidth = width;
        return this;
    }
}

