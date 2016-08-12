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

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

public class Slider extends ProgressBar {
    public class SliderStyle extends ProgressBarStyle {
        public SliderStyle() {
            super();
        }

        public SliderStyle(SliderStyle style) {
            super(((ProgressBarStyle)style));
        }

        public SliderStyle(Drawable background, Drawable knob) {
            super(background, knob);
        }
    }

    int draggingPointer;
    private Interpolation visualInterpolationInverse;

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        String v0;
        StringBuilder v1 = new StringBuilder().append("default-");
        if(vertical) {
            v0 = "vertical";
        }
        else {
            v0 = "horizontal";
        }

        this(min, max, stepSize, vertical, skin.get(v1.append(v0).toString(), SliderStyle.class));
    }

    public Slider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        super(min, max, stepSize, vertical, ((ProgressBarStyle)style));
        this.draggingPointer = -1;
        this.visualInterpolationInverse = Interpolation.linear;
        this.shiftIgnoresSnap = true;
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0 = false;
                if(!Slider.this.disabled && Slider.this.draggingPointer == -1) {
                    Slider.this.draggingPointer = pointer;
                    Slider.this.calculatePositionAndValue(x, y);
                    v0 = true;
                }

                return v0;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Slider.this.calculatePositionAndValue(x, y);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(pointer == Slider.this.draggingPointer) {
                    Slider.this.draggingPointer = -1;
                    if(!Slider.this.calculatePositionAndValue(x, y)) {
                        Object v0 = Pools.obtain(ChangeEvent.class);
                        Slider.this.fire(((Event)v0));
                        Pools.free(v0);
                    }
                }
            }
        });
    }

    public Slider(float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        this(min, max, stepSize, vertical, skin.get(styleName, SliderStyle.class));
    }

    boolean calculatePositionAndValue(float x, float y) {
        float v5;
        float v11;
        float v4;
        Drawable v1;
        Drawable v3;
        SliderStyle v10 = this.getStyle();
        if(!this.disabled || v10.disabledKnob == null) {
            v3 = v10.knob;
        }
        else {
            v3 = v10.disabledKnob;
        }

        if(!this.disabled || v10.disabledBackground == null) {
            v1 = v10.background;
        }
        else {
            v1 = v10.disabledBackground;
        }

        float v8 = this.position;
        float v7 = this.getMinValue();
        float v6 = this.getMaxValue();
        if(this.vertical) {
            float v2 = this.getHeight() - v1.getTopHeight() - v1.getBottomHeight();
            if(v3 == null) {
                v4 = 0f;
            }
            else {
                v4 = v3.getMinHeight();
            }

            this.position = y - v1.getBottomHeight() - 0.5f * v4;
            v11 = v7 + (v6 - v7) * this.visualInterpolationInverse.apply(this.position / (v2 - v4));
            this.position = Math.max(0f, this.position);
            this.position = Math.min(v2 - v4, this.position);
        }
        else {
            float v13 = this.getWidth() - v1.getLeftWidth() - v1.getRightWidth();
            if(v3 == null) {
                v5 = 0f;
            }
            else {
                v5 = v3.getMinWidth();
            }

            this.position = x - v1.getLeftWidth() - 0.5f * v5;
            v11 = v7 + (v6 - v7) * this.visualInterpolationInverse.apply(this.position / (v13 - v5));
            this.position = Math.max(0f, this.position);
            this.position = Math.min(v13 - v5, this.position);
        }

        boolean v12 = this.setValue(v11);
        if(v11 == v11) {
            this.position = v8;
        }

        return v12;
    }

    public SliderStyle getStyle() {
        return super.getStyle();
    }

    public ProgressBarStyle getStyle() {
        return this.getStyle();
    }

    public boolean isDragging() {
        boolean v0;
        if(this.draggingPointer != -1) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void setStyle(SliderStyle style) {
        if(style == null) {
            throw new NullPointerException("style cannot be null");
        }

        if(!(style instanceof SliderStyle)) {
            throw new IllegalArgumentException("style must be a SliderStyle.");
        }

        super.setStyle(((ProgressBarStyle)style));
    }

    public void setVisualInterpolationInverse(Interpolation interpolation) {
        this.visualInterpolationInverse = interpolation;
    }
}

