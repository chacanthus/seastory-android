// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.TimeUtils;

public class ClickListener extends InputListener {
    private int button;
    private boolean cancelled;
    private long lastTapTime;
    private boolean over;
    private boolean pressed;
    private int pressedButton;
    private int pressedPointer;
    private int tapCount;
    private long tapCountInterval;
    private float tapSquareSize;
    private float touchDownX;
    private float touchDownY;
    public static float visualPressedDuration;
    private long visualPressedTime;

    static  {
        ClickListener.visualPressedDuration = 0.1f;
    }

    public ClickListener() {
        super();
        this.tapSquareSize = 14f;
        this.touchDownX = -1f;
        this.touchDownY = -1f;
        this.pressedPointer = -1;
        this.pressedButton = -1;
        this.tapCountInterval = 400000000;
    }

    public ClickListener(int button) {
        super();
        this.tapSquareSize = 14f;
        this.touchDownX = -1f;
        this.touchDownY = -1f;
        this.pressedPointer = -1;
        this.pressedButton = -1;
        this.tapCountInterval = 400000000;
        this.button = button;
    }

    public void cancel() {
        if(this.pressedPointer != -1) {
            this.cancelled = true;
            this.pressed = false;
        }
    }

    public void clicked(InputEvent event, float x, float y) {
    }

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if(pointer == -1 && !this.cancelled) {
            this.over = true;
        }
    }

    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if(pointer == -1 && !this.cancelled) {
            this.over = false;
        }
    }

    public int getButton() {
        return this.button;
    }

    public int getPressedButton() {
        return this.pressedButton;
    }

    public int getPressedPointer() {
        return this.pressedPointer;
    }

    public int getTapCount() {
        return this.tapCount;
    }

    public float getTapSquareSize() {
        return this.tapSquareSize;
    }

    public float getTouchDownX() {
        return this.touchDownX;
    }

    public float getTouchDownY() {
        return this.touchDownY;
    }

    public boolean inTapSquare() {
        boolean v0;
        if(this.touchDownX != -1f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean inTapSquare(float x, float y) {
        boolean v0 = false;
        float v2 = -1f;
        if((this.touchDownX != v2 || this.touchDownY != v2) && (Math.abs(x - this.touchDownX) < this.tapSquareSize && Math.abs(y - this.touchDownY) < this.tapSquareSize)) {
            v0 = true;
        }

        return v0;
    }

    public void invalidateTapSquare() {
        this.touchDownX = -1f;
        this.touchDownY = -1f;
    }

    public boolean isOver() {
        boolean v0;
        if((this.over) || (this.pressed)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isOver(Actor actor, float x, float y) {
        boolean v1 = true;
        Actor v0 = actor.hit(x, y, true);
        if(v0 == null || !v0.isDescendantOf(actor)) {
            v1 = this.inTapSquare(x, y);
        }

        return v1;
    }

    public boolean isPressed() {
        return this.pressed;
    }

    public boolean isVisualPressed() {
        long v6 = 0;
        boolean v0 = true;
        if(!this.pressed) {
            if(this.visualPressedTime <= v6) {
                v0 = false;
            }
            else if(this.visualPressedTime <= TimeUtils.millis()) {
                this.visualPressedTime = v6;
                v0 = false;
            }
        }

        return v0;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setTapCountInterval(float tapCountInterval) {
        this.tapCountInterval = ((long)(1000000000f * tapCountInterval));
    }

    public void setTapSquareSize(float halfTapSquareSize) {
        this.tapSquareSize = halfTapSquareSize;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        boolean v0 = false;
        if(!this.pressed && (pointer != 0 || this.button == -1 || button == this.button)) {
            this.pressed = true;
            this.pressedPointer = pointer;
            this.pressedButton = button;
            this.touchDownX = x;
            this.touchDownY = y;
            this.visualPressedTime = TimeUtils.millis() + (((long)(ClickListener.visualPressedDuration * 1000f)));
            v0 = true;
        }

        return v0;
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if(pointer == this.pressedPointer && !this.cancelled) {
            this.pressed = this.isOver(event.getListenerActor(), x, y);
            if((this.pressed) && pointer == 0 && this.button != -1 && !Gdx.input.isButtonPressed(this.button)) {
                this.pressed = false;
            }

            if(this.pressed) {
                return;
            }

            this.invalidateTapSquare();
        }
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        int v8 = -1;
        if(pointer == this.pressedPointer) {
            if(!this.cancelled) {
                boolean v2 = this.isOver(event.getListenerActor(), x, y);
                if((v2) && pointer == 0 && this.button != v8 && button != this.button) {
                    v2 = false;
                }

                if(!v2) {
                    goto label_27;
                }

                long v0 = TimeUtils.nanoTime();
                if(v0 - this.lastTapTime > this.tapCountInterval) {
                    this.tapCount = 0;
                }

                ++this.tapCount;
                this.lastTapTime = v0;
                this.clicked(event, x, y);
            }

        label_27:
            this.pressed = false;
            this.pressedPointer = v8;
            this.pressedButton = v8;
            this.cancelled = false;
        }
    }
}

