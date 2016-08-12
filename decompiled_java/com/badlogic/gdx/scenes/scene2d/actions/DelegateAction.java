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

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

public abstract class DelegateAction extends Action {
    protected Action action;

    public DelegateAction() {
        super();
    }

    public final boolean act(float delta) {  // has try-catch handlers
        boolean v1_1;
        Pool v0 = this.getPool();
        this.setPool(null);
        try {
            v1_1 = this.delegate(delta);
        }
        catch(Throwable v1) {
            this.setPool(v0);
            throw v1;
        }

        this.setPool(v0);
        return v1_1;
    }

    protected abstract boolean delegate(float arg0);

    public Action getAction() {
        return this.action;
    }

    public void reset() {
        super.reset();
        this.action = null;
    }

    public void restart() {
        if(this.action != null) {
            this.action.restart();
        }
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setActor(Actor actor) {
        if(this.action != null) {
            this.action.setActor(actor);
        }

        super.setActor(actor);
    }

    public void setTarget(Actor target) {
        if(this.action != null) {
            this.action.setTarget(target);
        }

        super.setTarget(target);
    }

    public String toString() {
        String v0;
        StringBuilder v1 = new StringBuilder().append(super.toString());
        if(this.action == null) {
            v0 = "";
        }
        else {
            v0 = "(" + this.action + ")";
        }

        return v1.append(v0).toString();
    }
}

