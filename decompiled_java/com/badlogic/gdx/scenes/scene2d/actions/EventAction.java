﻿// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class EventAction extends Action {
    boolean active;
    final Class eventClass;
    private final EventListener listener;
    boolean result;

    public EventAction(Class arg2) {
        super();
        this.listener = new EventListener() {
            public boolean handle(Event event) {
                boolean v0;
                if(!EventAction.this.active || !ClassReflection.isInstance(EventAction.this.eventClass, event)) {
                    v0 = false;
                }
                else {
                    EventAction.this.result = EventAction.this.handle(event);
                    v0 = EventAction.this.result;
                }

                return v0;
            }
        };
        this.eventClass = arg2;
    }

    public boolean act(float delta) {
        this.active = true;
        return this.result;
    }

    public abstract boolean handle(Event arg0);

    public boolean isActive() {
        return this.active;
    }

    public void restart() {
        this.result = false;
        this.active = false;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTarget(Actor newTarget) {
        if(this.target != null) {
            this.target.removeListener(this.listener);
        }

        super.setTarget(newTarget);
        if(newTarget != null) {
            newTarget.addListener(this.listener);
        }
    }
}

