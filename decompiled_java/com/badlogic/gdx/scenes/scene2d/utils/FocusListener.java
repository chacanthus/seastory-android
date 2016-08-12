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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public abstract class FocusListener implements EventListener {
    public class FocusEvent extends Event {
        private boolean focused;
        private Actor relatedActor;
        private Type type;

        public FocusEvent() {
            super();
        }

        public Actor getRelatedActor() {
            return this.relatedActor;
        }

        public Type getType() {
            return this.type;
        }

        public boolean isFocused() {
            return this.focused;
        }

        public void reset() {
            super.reset();
            this.relatedActor = null;
        }

        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        public void setRelatedActor(Actor relatedActor) {
            this.relatedActor = relatedActor;
        }

        public void setType(Type focusType) {
            this.type = focusType;
        }
    }

    public FocusListener() {
        super();
    }

    public boolean handle(Event event) {
        if((event instanceof FocusEvent)) {
            Event v0 = event;
            switch(com.badlogic.gdx.scenes.scene2d.utils.FocusListener$1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$utils$FocusListener$FocusEvent$Type[((FocusEvent)v0).getType().ordinal()]) {
                case 1: {
                    goto label_11;
                }
                case 2: {
                    goto label_15;
                }
            }

            return 0;
        label_11:
            this.keyboardFocusChanged(((FocusEvent)v0), event.getTarget(), ((FocusEvent)v0).isFocused());
            return 0;
        label_15:
            this.scrollFocusChanged(((FocusEvent)v0), event.getTarget(), ((FocusEvent)v0).isFocused());
        }

        return 0;
    }

    public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
    }

    public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
    }
}

