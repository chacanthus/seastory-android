// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AfterAction extends DelegateAction {
    private Array waitForActions;

    public AfterAction() {
        super();
        this.waitForActions = new Array(false, 4);
    }

    protected boolean delegate(float delta) {
        boolean v4;
        Array v1 = this.target.getActions();
        if(v1.size == 1) {
            this.waitForActions.clear();
        }

        int v2;
        for(v2 = this.waitForActions.size - 1; v2 >= 0; --v2) {
            if(v1.indexOf(this.waitForActions.get(v2), true) == -1) {
                this.waitForActions.removeIndex(v2);
            }
        }

        if(this.waitForActions.size > 0) {
            v4 = false;
        }
        else {
            v4 = this.action.act(delta);
        }

        return v4;
    }

    public void restart() {
        super.restart();
        this.waitForActions.clear();
    }

    public void setTarget(Actor target) {
        if(target != null) {
            this.waitForActions.addAll(target.getActions());
        }

        super.setTarget(target);
    }
}

