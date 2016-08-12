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

import com.badlogic.gdx.math.Vector2;

public class InputListener implements EventListener {
    private static final Vector2 tmpCoords;

    static  {
        InputListener.tmpCoords = new Vector2();
    }

    public InputListener() {
        super();
    }

    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
    }

    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
    }

    public boolean handle(Event e) {
        boolean v0;
        if(!(e instanceof InputEvent)) {
            v0 = false;
        }
        else {
            Event v1 = e;
            switch(com.badlogic.gdx.scenes.scene2d.InputListener$1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[((InputEvent)v1).getType().ordinal()]) {
                case 1: {
                    goto label_22;
                }
                case 2: {
                    goto label_25;
                }
                case 3: {
                    goto label_28;
                }
            }

            ((InputEvent)v1).toCoordinates(((InputEvent)v1).getListenerActor(), InputListener.tmpCoords);
            switch(com.badlogic.gdx.scenes.scene2d.InputListener$1.$SwitchMap$com$badlogic$gdx$scenes$scene2d$InputEvent$Type[((InputEvent)v1).getType().ordinal()]) {
                case 4: {
                    goto label_31;
                }
                case 5: {
                    goto label_40;
                }
                case 6: {
                    goto label_50;
                }
                case 7: {
                    goto label_58;
                }
                case 8: {
                    goto label_64;
                }
                case 9: {
                    goto label_71;
                }
                case 10: {
                    goto label_81;
                }
            }

            v0 = false;
            goto label_5;
        label_81:
            this.exit(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getRelatedActor());
            v0 = false;
            goto label_5;
        label_50:
            this.touchDragged(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getPointer());
            v0 = true;
            goto label_5;
        label_71:
            this.enter(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getRelatedActor());
            v0 = false;
            goto label_5;
        label_40:
            this.touchUp(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
            v0 = true;
            goto label_5;
        label_58:
            v0 = this.mouseMoved(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y);
            goto label_5;
        label_31:
            v0 = this.touchDown(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getPointer(), ((InputEvent)v1).getButton());
            goto label_5;
        label_64:
            v0 = this.scrolled(((InputEvent)v1), InputListener.tmpCoords.x, InputListener.tmpCoords.y, ((InputEvent)v1).getScrollAmount());
            goto label_5;
        label_22:
            v0 = this.keyDown(((InputEvent)v1), ((InputEvent)v1).getKeyCode());
            goto label_5;
        label_25:
            v0 = this.keyUp(((InputEvent)v1), ((InputEvent)v1).getKeyCode());
            goto label_5;
        label_28:
            v0 = this.keyTyped(((InputEvent)v1), ((InputEvent)v1).getCharacter());
        }

    label_5:
        return v0;
    }

    public boolean keyDown(InputEvent event, int keycode) {
        return 0;
    }

    public boolean keyTyped(InputEvent event, char character) {
        return 0;
    }

    public boolean keyUp(InputEvent event, int keycode) {
        return 0;
    }

    public boolean mouseMoved(InputEvent event, float x, float y) {
        return 0;
    }

    public boolean scrolled(InputEvent event, float x, float y, int amount) {
        return 0;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return 0;
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
    }
}

