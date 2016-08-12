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

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector$GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ActorGestureListener implements EventListener {
    Actor actor;
    private final GestureDetector detector;
    InputEvent event;
    static final Vector2 tmpCoords;
    Actor touchDownTarget;

    static  {
        ActorGestureListener.tmpCoords = new Vector2();
    }

    public ActorGestureListener() {
        this(20f, 0.4f, 1.1f, 0.15f);
    }

    public ActorGestureListener(float halfTapSquareSize, float tapCountInterval, float longPressDuration, float maxFlingDelay) {
        super();
        this.detector = new GestureDetector(halfTapSquareSize, tapCountInterval, longPressDuration, maxFlingDelay, new GestureAdapter() {
            private final Vector2 initialPointer1;
            private final Vector2 initialPointer2;
            private final Vector2 pointer1;
            private final Vector2 pointer2;

            public boolean fling(float velocityX, float velocityY, int button) {
                ActorGestureListener.this.fling(ActorGestureListener.this.event, velocityX, velocityY, button);
                return 1;
            }

            public boolean longPress(float stageX, float stageY) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
                return ActorGestureListener.this.longPress(ActorGestureListener.this.actor, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y);
            }

            public boolean pan(float stageX, float stageY, float deltaX, float deltaY) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
                ActorGestureListener.this.pan(ActorGestureListener.this.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, deltaX, deltaY);
                return 1;
            }

            public boolean pinch(Vector2 stageInitialPointer1, Vector2 stageInitialPointer2, Vector2 stagePointer1, Vector2 stagePointer2) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer1.set(stageInitialPointer1));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.initialPointer2.set(stageInitialPointer2));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer1.set(stagePointer1));
                ActorGestureListener.this.actor.stageToLocalCoordinates(this.pointer2.set(stagePointer2));
                ActorGestureListener.this.pinch(ActorGestureListener.this.event, this.initialPointer1, this.initialPointer2, this.pointer1, this.pointer2);
                return 1;
            }

            public boolean tap(float stageX, float stageY, int count, int button) {
                ActorGestureListener.this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(stageX, stageY));
                ActorGestureListener.this.tap(ActorGestureListener.this.event, ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, count, button);
                return 1;
            }

            public boolean zoom(float initialDistance, float distance) {
                ActorGestureListener.this.zoom(ActorGestureListener.this.event, initialDistance, distance);
                return 1;
            }
        });
    }

    public void fling(InputEvent event, float velocityX, float velocityY, int button) {
    }

    public GestureDetector getGestureDetector() {
        return this.detector;
    }

    public Actor getTouchDownTarget() {
        return this.touchDownTarget;
    }

    public boolean handle(Event e) {
        boolean v0 = false;
        if((e instanceof InputEvent)) {
            Event v1 = e;
            switch(com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener$2.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[((InputEvent)v1).getType().ordinal()]) {
                case 1: {
                    goto label_12;
                }
                case 2: {
                    goto label_38;
                }
                case 3: {
                    goto label_65;
                }
            }

            goto label_4;
        label_65:
            this.event = ((InputEvent)v1);
            this.actor = ((InputEvent)v1).getListenerActor();
            this.detector.touchDragged(((InputEvent)v1).getStageX(), ((InputEvent)v1).getStageY(), ((InputEvent)v1).getPointer());
            v0 = true;
            goto label_4;
        label_38:
            if(!((InputEvent)v1).isTouchFocusCancel()) {
                this.event = ((InputEvent)v1);
                this.actor = ((InputEvent)v1).getListenerActor();
                this.detector.touchUp(((InputEvent)v1).getStageX(), ((InputEvent)v1).getStageY(), ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
                this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(((InputEvent)v1).getStageX(), ((InputEvent)v1).getStageY()));
                this.touchUp(((InputEvent)v1), ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
                v0 = true;
                goto label_4;
            label_12:
                this.actor = ((InputEvent)v1).getListenerActor();
                this.touchDownTarget = ((InputEvent)v1).getTarget();
                this.detector.touchDown(((InputEvent)v1).getStageX(), ((InputEvent)v1).getStageY(), ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
                this.actor.stageToLocalCoordinates(ActorGestureListener.tmpCoords.set(((InputEvent)v1).getStageX(), ((InputEvent)v1).getStageY()));
                this.touchDown(((InputEvent)v1), ActorGestureListener.tmpCoords.x, ActorGestureListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
                v0 = true;
            }
        }

    label_4:
        return v0;
    }

    public boolean longPress(Actor actor, float x, float y) {
        return 0;
    }

    public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
    }

    public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
    }

    public void tap(InputEvent event, float x, float y, int count, int button) {
    }

    public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }

    public void zoom(InputEvent event, float initialDistance, float distance) {
    }
}

