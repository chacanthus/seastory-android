// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.utils.Pool$Poolable;

public class Event implements Poolable {
    private boolean bubbles;
    private boolean cancelled;
    private boolean capture;
    private boolean handled;
    private Actor listenerActor;
    private Stage stage;
    private boolean stopped;
    private Actor targetActor;

    public Event() {
        super();
        this.bubbles = true;
    }

    public void cancel() {
        this.cancelled = true;
        this.stopped = true;
        this.handled = true;
    }

    public boolean getBubbles() {
        return this.bubbles;
    }

    public Actor getListenerActor() {
        return this.listenerActor;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Actor getTarget() {
        return this.targetActor;
    }

    public void handle() {
        this.handled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public boolean isCapture() {
        return this.capture;
    }

    public boolean isHandled() {
        return this.handled;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public void reset() {
        this.stage = null;
        this.targetActor = null;
        this.listenerActor = null;
        this.capture = false;
        this.bubbles = true;
        this.handled = false;
        this.stopped = false;
        this.cancelled = false;
    }

    public void setBubbles(boolean bubbles) {
        this.bubbles = bubbles;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public void setListenerActor(Actor listenerActor) {
        this.listenerActor = listenerActor;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTarget(Actor targetActor) {
        this.targetActor = targetActor;
    }

    public void stop() {
        this.stopped = true;
    }
}

