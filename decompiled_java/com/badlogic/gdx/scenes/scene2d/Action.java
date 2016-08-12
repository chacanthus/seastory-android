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

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool$Poolable;

public abstract class Action implements Poolable {
    protected Actor actor;
    private Pool pool;
    protected Actor target;

    public Action() {
        super();
    }

    public abstract boolean act(float arg0);

    public Actor getActor() {
        return this.actor;
    }

    public Pool getPool() {
        return this.pool;
    }

    public Actor getTarget() {
        return this.target;
    }

    public void reset() {
        this.actor = null;
        this.target = null;
        this.pool = null;
        this.restart();
    }

    public void restart() {
    }

    public void setActor(Actor actor) {
        this.actor = actor;
        if(this.target == null) {
            this.setTarget(actor);
        }

        if(actor == null && this.pool != null) {
            this.pool.free(this);
            this.pool = null;
        }
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    public String toString() {
        String v1 = this.getClass().getName();
        int v0 = v1.lastIndexOf(46);
        if(v0 != -1) {
            v1 = v1.substring(v0 + 1);
        }

        if(v1.endsWith("Action")) {
            v1 = v1.substring(0, v1.length() - 6);
        }

        return v1;
    }
}

