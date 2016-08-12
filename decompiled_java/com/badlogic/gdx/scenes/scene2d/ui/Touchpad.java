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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

public class Touchpad extends Widget {
    public class TouchpadStyle {
        public TouchpadStyle() {
            super();
        }

        public TouchpadStyle(TouchpadStyle style) {
            super();
            this.background = style.background;
            this.knob = style.knob;
        }

        public TouchpadStyle(Drawable background, Drawable knob) {
            super();
            this.background = background;
            this.knob = knob;
        }
    }

    private final Circle deadzoneBounds;
    private float deadzoneRadius;
    private final Circle knobBounds;
    private final Vector2 knobPercent;
    private final Vector2 knobPosition;
    boolean resetOnTouchUp;
    private TouchpadStyle style;
    private final Circle touchBounds;
    boolean touched;

    public Touchpad(float deadzoneRadius, Skin skin) {
        this(deadzoneRadius, skin.get(TouchpadStyle.class));
    }

    public Touchpad(float deadzoneRadius, TouchpadStyle style) {
        float v3 = 2f;
        super();
        this.resetOnTouchUp = true;
        this.knobBounds = new Circle(0f, 0f, 0f);
        this.touchBounds = new Circle(0f, 0f, 0f);
        this.deadzoneBounds = new Circle(0f, 0f, 0f);
        this.knobPosition = new Vector2();
        this.knobPercent = new Vector2();
        if(deadzoneRadius < 0f) {
            throw new IllegalArgumentException("deadzoneRadius must be > 0");
        }

        this.deadzoneRadius = deadzoneRadius;
        this.knobPosition.set(this.getWidth() / v3, this.getHeight() / v3);
        this.setStyle(style);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0 = false;
                if(!Touchpad.this.touched) {
                    Touchpad.this.touched = true;
                    Touchpad.this.calculatePositionAndValue(x, y, false);
                    v0 = true;
                }

                return v0;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Touchpad.this.calculatePositionAndValue(x, y, false);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Touchpad.this.touched = false;
                Touchpad.this.calculatePositionAndValue(x, y, Touchpad.this.resetOnTouchUp);
            }
        });
    }

    public Touchpad(float deadzoneRadius, Skin skin, String styleName) {
        this(deadzoneRadius, skin.get(styleName, TouchpadStyle.class));
    }

    void calculatePositionAndValue(float x, float y, boolean isTouchUp) {
        float v6 = this.knobPosition.x;
        float v7 = this.knobPosition.y;
        float v4 = this.knobPercent.x;
        float v5 = this.knobPercent.y;
        float v0 = this.knobBounds.x;
        float v1 = this.knobBounds.y;
        this.knobPosition.set(v0, v1);
        this.knobPercent.set(0f, 0f);
        if(!isTouchUp && !this.deadzoneBounds.contains(x, y)) {
            this.knobPercent.set((x - v0) / this.knobBounds.radius, (y - v1) / this.knobBounds.radius);
            float v3 = this.knobPercent.len();
            if(v3 > 1f) {
                this.knobPercent.scl(1f / v3);
            }

            if(!this.knobBounds.contains(x, y)) {
                goto label_61;
            }

            this.knobPosition.set(x, y);
            goto label_45;
        label_61:
            this.knobPosition.set(this.knobPercent).nor().scl(this.knobBounds.radius).add(this.knobBounds.x, this.knobBounds.y);
        }

    label_45:
        if(v4 != this.knobPercent.x || v5 != this.knobPercent.y) {
            Object v2 = Pools.obtain(ChangeEvent.class);
            if(this.fire(((Event)v2))) {
                this.knobPercent.set(v4, v5);
                this.knobPosition.set(v6, v7);
            }

            Pools.free(v2);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        float v10 = 2f;
        this.validate();
        Color v12 = this.getColor();
        batch.setColor(v12.r, v12.g, v12.b, v12.a * parentAlpha);
        float v2 = this.getX();
        float v3 = this.getY();
        float v4 = this.getWidth();
        float v5 = this.getHeight();
        Drawable v0 = this.style.background;
        if(v0 != null) {
            v0.draw(batch, v2, v3, v4, v5);
        }

        Drawable v6 = this.style.knob;
        if(v6 != null) {
            v6.draw(batch, v2 + (this.knobPosition.x - v6.getMinWidth() / v10), v3 + (this.knobPosition.y - v6.getMinHeight() / v10), v6.getMinWidth(), v6.getMinHeight());
        }
    }

    public float getKnobPercentX() {
        return this.knobPercent.x;
    }

    public float getKnobPercentY() {
        return this.knobPercent.y;
    }

    public float getKnobX() {
        return this.knobPosition.x;
    }

    public float getKnobY() {
        return this.knobPosition.y;
    }

    public float getPrefHeight() {
        float v0;
        if(this.style.background != null) {
            v0 = this.style.background.getMinHeight();
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public float getPrefWidth() {
        float v0;
        if(this.style.background != null) {
            v0 = this.style.background.getMinWidth();
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public boolean getResetOnTouchUp() {
        return this.resetOnTouchUp;
    }

    public TouchpadStyle getStyle() {
        return this.style;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if(!this.touchBounds.contains(x, y)) {
            this = null;
        }

        return this;
    }

    public boolean isTouched() {
        return this.touched;
    }

    public void layout() {
        float v5 = 2f;
        float v1 = this.getWidth() / v5;
        float v0 = this.getHeight() / v5;
        float v2 = Math.min(v1, v0);
        this.touchBounds.set(v1, v0, v2);
        if(this.style.knob != null) {
            v2 -= Math.max(this.style.knob.getMinWidth(), this.style.knob.getMinHeight()) / v5;
        }

        this.knobBounds.set(v1, v0, v2);
        this.deadzoneBounds.set(v1, v0, this.deadzoneRadius);
        this.knobPosition.set(v1, v0);
        this.knobPercent.set(0f, 0f);
    }

    public void setDeadzone(float deadzoneRadius) {
        if(deadzoneRadius < 0f) {
            throw new IllegalArgumentException("deadzoneRadius must be > 0");
        }

        this.deadzoneRadius = deadzoneRadius;
        this.invalidate();
    }

    public void setResetOnTouchUp(boolean reset) {
        this.resetOnTouchUp = reset;
    }

    public void setStyle(TouchpadStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null");
        }

        this.style = style;
        this.invalidateHierarchy();
    }
}

