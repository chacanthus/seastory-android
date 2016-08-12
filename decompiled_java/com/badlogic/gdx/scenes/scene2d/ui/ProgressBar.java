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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

public class ProgressBar extends Widget implements Disableable {
    public class ProgressBarStyle {
        public ProgressBarStyle() {
            super();
        }

        public ProgressBarStyle(ProgressBarStyle style) {
            super();
            this.background = style.background;
            this.disabledBackground = style.disabledBackground;
            this.knob = style.knob;
            this.disabledKnob = style.disabledKnob;
            this.knobBefore = style.knobBefore;
            this.knobAfter = style.knobAfter;
            this.disabledKnobBefore = style.disabledKnobBefore;
            this.disabledKnobAfter = style.disabledKnobAfter;
        }

        public ProgressBarStyle(Drawable background, Drawable knob) {
            super();
            this.background = background;
            this.knob = knob;
        }
    }

    private float animateDuration;
    private float animateFromValue;
    private Interpolation animateInterpolation;
    private float animateTime;
    boolean disabled;
    private float max;
    private float min;
    float position;
    boolean shiftIgnoresSnap;
    private float[] snapValues;
    private float stepSize;
    private ProgressBarStyle style;
    private float threshold;
    private float value;
    final boolean vertical;
    private Interpolation visualInterpolation;

    public ProgressBar(float min, float max, float stepSize, boolean vertical, ProgressBarStyle style) {
        super();
        this.animateInterpolation = Interpolation.linear;
        this.visualInterpolation = Interpolation.linear;
        if(min > max) {
            throw new IllegalArgumentException("max must be > min. min,max: " + min + ", " + max);
        }

        if(stepSize <= 0f) {
            throw new IllegalArgumentException("stepSize must be > 0: " + stepSize);
        }

        this.setStyle(style);
        this.min = min;
        this.max = max;
        this.stepSize = stepSize;
        this.vertical = vertical;
        this.value = min;
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public ProgressBar(float min, float max, float stepSize, boolean vertical, Skin skin) {
        String v0;
        StringBuilder v1 = new StringBuilder().append("default-");
        if(vertical) {
            v0 = "vertical";
        }
        else {
            v0 = "horizontal";
        }

        this(min, max, stepSize, vertical, skin.get(v1.append(v0).toString(), ProgressBarStyle.class));
    }

    public ProgressBar(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        this(min, max, stepSize, vertical, skin.get(styleName, ProgressBarStyle.class));
    }

    public void act(float delta) {
        super.act(delta);
        if(this.animateTime > 0f) {
            this.animateTime -= delta;
            Stage v0 = this.getStage();
            if(v0 != null && (v0.getActionsRequestRendering())) {
                Gdx.graphics.requestRendering();
            }
        }
    }

    protected float clamp(float value) {
        return MathUtils.clamp(value, this.min, this.max);
    }

    public void draw(Batch batch, float parentAlpha) {
        float v22;
        float v20;
        float v18;
        Drawable v17;
        Drawable v7;
        Drawable v1;
        Drawable v16;
        ProgressBarStyle v26 = this.style;
        boolean v15 = this.disabled;
        if(!v15 || v26.disabledKnob == null) {
            v16 = v26.knob;
        }
        else {
            v16 = v26.disabledKnob;
        }

        if(!v15 || v26.disabledBackground == null) {
            v1 = v26.background;
        }
        else {
            v1 = v26.disabledBackground;
        }

        if(!v15 || v26.disabledKnobBefore == null) {
            v7 = v26.knobBefore;
        }
        else {
            v7 = v26.disabledKnobBefore;
        }

        if(!v15 || v26.disabledKnobAfter == null) {
            v17 = v26.knobAfter;
        }
        else {
            v17 = v26.disabledKnobAfter;
        }

        Color v14 = this.getColor();
        float v28 = this.getX();
        float v4 = this.getY();
        float v27 = this.getWidth();
        float v6 = this.getHeight();
        if(v16 == null) {
            v18 = 0f;
        }
        else {
            v18 = v16.getMinHeight();
        }

        if(v16 == null) {
            v20 = 0f;
        }
        else {
            v20 = v16.getMinWidth();
        }

        float v23 = this.getVisualPercent();
        batch.setColor(v14.r, v14.g, v14.b, v14.a * parentAlpha);
        if(this.vertical) {
            v1.draw(batch, v28 + (((float)(((int)((v27 - v1.getMinWidth()) * 0.5f))))), v4, v1.getMinWidth(), v6);
            float v24 = v6 - (v1.getTopHeight() + v1.getBottomHeight());
            float v19 = 0f;
            if(this.min != this.max) {
                if(v16 == null) {
                    if(v7 == null) {
                        v19 = 0f;
                    }
                    else {
                        v19 = v7.getMinHeight() * 0.5f;
                    }

                    this.position = (v24 - v19) * v23;
                    this.position = Math.min(v24 - v19, this.position);
                }
                else {
                    v19 = v18 * 0.5f;
                    this.position = (v24 - v18) * v23;
                    this.position = Math.min(v24 - v18, this.position) + v1.getBottomHeight();
                }

                this.position = Math.max(0f, this.position);
            }

            if(v7 != null) {
                v22 = 0f;
                if(v1 != null) {
                    v22 = v1.getTopHeight();
                }

                v7.draw(batch, v28 + (((float)(((int)((v27 - v7.getMinWidth()) * 0.5f))))), v4 + v22, v7.getMinWidth(), ((float)(((int)(this.position + v19)))));
            }

            if(v17 != null) {
                v17.draw(batch, v28 + (((float)(((int)((v27 - v17.getMinWidth()) * 0.5f))))), v4 + (((float)(((int)(this.position + v19))))), v17.getMinWidth(), v6 - (((float)(((int)(this.position + v19))))));
            }

            if(v16 == null) {
                return;
            }

            v16.draw(batch, v28 + (((float)(((int)((v27 - v20) * 0.5f))))), ((float)(((int)(this.position + v4)))), v20, v18);
        }
        else {
            v1.draw(batch, v28, v4 + (((float)(((int)((v6 - v1.getMinHeight()) * 0.5f))))), v27, v1.getMinHeight());
            float v25 = v27 - (v1.getLeftWidth() + v1.getRightWidth());
            float v21 = 0f;
            if(this.min != this.max) {
                if(v16 == null) {
                    if(v7 == null) {
                        v21 = 0f;
                    }
                    else {
                        v21 = v7.getMinWidth() * 0.5f;
                    }

                    this.position = (v25 - v21) * v23;
                    this.position = Math.min(v25 - v21, this.position);
                }
                else {
                    v21 = v20 * 0.5f;
                    this.position = (v25 - v20) * v23;
                    this.position = Math.min(v25 - v20, this.position) + v1.getLeftWidth();
                }

                this.position = Math.max(0f, this.position);
            }

            if(v7 != null) {
                v22 = 0f;
                if(v1 != null) {
                    v22 = v1.getLeftWidth();
                }

                v7.draw(batch, v28 + v22, v4 + (((float)(((int)((v6 - v7.getMinHeight()) * 0.5f))))), ((float)(((int)(this.position + v21)))), v7.getMinHeight());
            }

            if(v17 != null) {
                v17.draw(batch, v28 + (((float)(((int)(this.position + v21))))), v4 + (((float)(((int)((v6 - v17.getMinHeight()) * 0.5f))))), v27 - (((float)(((int)(this.position + v21))))), v17.getMinHeight());
            }

            if(v16 == null) {
                return;
            }

            v16.draw(batch, ((float)(((int)(this.position + v28)))), ((float)(((int)((v6 - v18) * 0.5f + v4)))), v20, v18);
        }
    }

    protected float getKnobPosition() {
        return this.position;
    }

    public float getMaxValue() {
        return this.max;
    }

    public float getMinValue() {
        return this.min;
    }

    public float getPercent() {
        return (this.value - this.min) / (this.max - this.min);
    }

    public float getPrefHeight() {
        Drawable v0;
        Drawable v1;
        float v2;
        if(this.vertical) {
            v2 = 140f;
        }
        else {
            if(!this.disabled || this.style.disabledKnob == null) {
                v1 = this.style.knob;
            }
            else {
                v1 = this.style.disabledKnob;
            }

            if(!this.disabled || this.style.disabledBackground == null) {
                v0 = this.style.background;
            }
            else {
                v0 = this.style.disabledBackground;
            }

            if(v1 == null) {
                v2 = 0f;
            }
            else {
                v2 = v1.getMinHeight();
            }

            v2 = Math.max(v2, v0.getMinHeight());
        }

        return v2;
    }

    public float getPrefWidth() {
        float v2;
        Drawable v0;
        Drawable v1;
        if(this.vertical) {
            if(!this.disabled || this.style.disabledKnob == null) {
                v1 = this.style.knob;
            }
            else {
                v1 = this.style.disabledKnob;
            }

            if(!this.disabled || this.style.disabledBackground == null) {
                v0 = this.style.background;
            }
            else {
                v0 = this.style.disabledBackground;
            }

            if(v1 == null) {
                v2 = 0f;
            }
            else {
                v2 = v1.getMinWidth();
            }

            v2 = Math.max(v2, v0.getMinWidth());
        }
        else {
            v2 = 140f;
        }

        return v2;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public ProgressBarStyle getStyle() {
        return this.style;
    }

    public float getValue() {
        return this.value;
    }

    public float getVisualPercent() {
        return this.visualInterpolation.apply((this.getVisualValue() - this.min) / (this.max - this.min));
    }

    public float getVisualValue() {
        float v0;
        if(this.animateTime > 0f) {
            v0 = this.animateInterpolation.apply(this.animateFromValue, this.value, 1f - this.animateTime / this.animateDuration);
        }
        else {
            v0 = this.value;
        }

        return v0;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setAnimateDuration(float duration) {
        this.animateDuration = duration;
    }

    public void setAnimateInterpolation(Interpolation animateInterpolation) {
        if(animateInterpolation == null) {
            throw new IllegalArgumentException("animateInterpolation cannot be null.");
        }

        this.animateInterpolation = animateInterpolation;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setRange(float min, float max) {
        if(min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }

        this.min = min;
        this.max = max;
        if(this.value < min) {
            this.setValue(min);
        }
        else if(this.value > max) {
            this.setValue(max);
        }
    }

    public void setSnapToValues(float[] values, float threshold) {
        this.snapValues = values;
        this.threshold = threshold;
    }

    public void setStepSize(float stepSize) {
        if(stepSize <= 0f) {
            throw new IllegalArgumentException("steps must be > 0: " + stepSize);
        }

        this.stepSize = stepSize;
    }

    public void setStyle(ProgressBarStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.invalidateHierarchy();
    }

    public boolean setValue(float value) {
        boolean v4 = false;
        value = this.clamp((((float)Math.round(value / this.stepSize))) * this.stepSize);
        if(!this.shiftIgnoresSnap || !Gdx.input.isKeyPressed(59) && !Gdx.input.isKeyPressed(60)) {
            value = this.snap(value);
        }

        float v2 = this.value;
        if(value != v2) {
            float v3 = this.getVisualValue();
            this.value = value;
            Object v1 = Pools.obtain(ChangeEvent.class);
            boolean v0 = this.fire(((Event)v1));
            if(v0) {
                this.value = v2;
            }
            else if(this.animateDuration > 0f) {
                this.animateFromValue = v3;
                this.animateTime = this.animateDuration;
            }

            Pools.free(v1);
            if(v0) {
                goto label_21;
            }

            v4 = true;
        }

    label_21:
        return v4;
    }

    public void setVisualInterpolation(Interpolation interpolation) {
        this.visualInterpolation = interpolation;
    }

    private float snap(float value) {
        if(this.snapValues != null) {
            int v0 = 0;
            while(v0 < this.snapValues.length) {
                if(Math.abs(value - this.snapValues[v0]) <= this.threshold) {
                    value = this.snapValues[v0];
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }

        return value;
    }
}

