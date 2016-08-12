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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DragListener extends InputListener {
    private int button;
    private float deltaX;
    private float deltaY;
    private boolean dragging;
    private int pressedPointer;
    private float stageTouchDownX;
    private float stageTouchDownY;
    private float tapSquareSize;
    private float touchDownX;
    private float touchDownY;

    public DragListener() {
        super();
        this.tapSquareSize = 14f;
        this.touchDownX = -1f;
        this.touchDownY = -1f;
        this.stageTouchDownX = -1f;
        this.stageTouchDownY = -1f;
        this.pressedPointer = -1;
    }

    public void cancel() {
        this.dragging = false;
        this.pressedPointer = -1;
    }

    public void drag(InputEvent event, float x, float y, int pointer) {
    }

    public void dragStart(InputEvent event, float x, float y, int pointer) {
    }

    public void dragStop(InputEvent event, float x, float y, int pointer) {
    }

    public int getButton() {
        return this.button;
    }

    public float getDeltaX() {
        return this.deltaX;
    }

    public float getDeltaY() {
        return this.deltaY;
    }

    public float getStageTouchDownX() {
        return this.stageTouchDownX;
    }

    public float getStageTouchDownY() {
        return this.stageTouchDownY;
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

    public boolean isDragging() {
        return this.dragging;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setTapSquareSize(float halfTapSquareSize) {
        this.tapSquareSize = halfTapSquareSize;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        boolean v0 = false;
        int v2 = -1;
        if(this.pressedPointer == v2 && (pointer != 0 || this.button == v2 || button == this.button)) {
            this.pressedPointer = pointer;
            this.touchDownX = x;
            this.touchDownY = y;
            this.stageTouchDownX = event.getStageX();
            this.stageTouchDownY = event.getStageY();
            v0 = true;
        }

        return v0;
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if(pointer == this.pressedPointer) {
            if(!this.dragging && (Math.abs(this.touchDownX - x) > this.tapSquareSize || Math.abs(this.touchDownY - y) > this.tapSquareSize)) {
                this.dragging = true;
                this.dragStart(event, x, y, pointer);
                this.deltaX = x;
                this.deltaY = y;
            }

            if(!this.dragging) {
                return;
            }

            this.deltaX -= x;
            this.deltaY -= y;
            this.drag(event, x, y, pointer);
            this.deltaX = x;
            this.deltaY = y;
        }
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if(pointer == this.pressedPointer) {
            if(this.dragging) {
                this.dragStop(event, x, y, pointer);
            }

            this.cancel();
        }
    }
}

