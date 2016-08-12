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

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer$Task;

public class DragScrollListener extends DragListener {
    Interpolation interpolation;
    float maxSpeed;
    float minSpeed;
    long rampTime;
    private ScrollPane scroll;
    private Task scrollDown;
    private Task scrollUp;
    long startTime;
    float tickSecs;

    public DragScrollListener(ScrollPane scroll) {
        super();
        this.interpolation = Interpolation.exp5In;
        this.minSpeed = 15f;
        this.maxSpeed = 75f;
        this.tickSecs = 0.05f;
        this.rampTime = 1750;
        this.scroll = scroll;
        this.scrollUp = new Task() {
            public void run() {
                this.val$scroll.setScrollY(this.val$scroll.getScrollY() - DragScrollListener.this.getScrollPixels());
            }
        };
        this.scrollDown = new Task() {
            public void run() {
                this.val$scroll.setScrollY(this.val$scroll.getScrollY() + DragScrollListener.this.getScrollPixels());
            }
        };
    }

    public void drag(InputEvent event, float x, float y, int pointer) {
        if(x < 0f || x >= this.scroll.getWidth()) {
        label_33:
            this.scrollUp.cancel();
            this.scrollDown.cancel();
        }
        else if(y >= this.scroll.getHeight()) {
            this.scrollDown.cancel();
            if(!this.scrollUp.isScheduled()) {
                this.startTime = System.currentTimeMillis();
                Timer.schedule(this.scrollUp, this.tickSecs, this.tickSecs);
            }
        }
        else if(y < 0f) {
            this.scrollUp.cancel();
            if(!this.scrollDown.isScheduled()) {
                this.startTime = System.currentTimeMillis();
                Timer.schedule(this.scrollDown, this.tickSecs, this.tickSecs);
            }
        }
        else {
            goto label_33;
        }
    }

    public void dragStop(InputEvent event, float x, float y, int pointer) {
        this.scrollUp.cancel();
        this.scrollDown.cancel();
    }

    float getScrollPixels() {
        return this.interpolation.apply(this.minSpeed, this.maxSpeed, Math.min(1f, (((float)(System.currentTimeMillis() - this.startTime))) / (((float)this.rampTime))));
    }

    public void setup(float minSpeedPixels, float maxSpeedPixels, float tickSecs, float rampSecs) {
        this.minSpeed = minSpeedPixels;
        this.maxSpeed = maxSpeedPixels;
        this.tickSecs = tickSecs;
        this.rampTime = ((long)(1000f * rampSecs));
    }
}

