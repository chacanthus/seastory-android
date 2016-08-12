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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ParallelAction extends Action {
    Array actions;
    private boolean complete;

    public ParallelAction() {
        super();
        this.actions = new Array(4);
    }

    public ParallelAction(Action action1) {
        super();
        this.actions = new Array(4);
        this.addAction(action1);
    }

    public ParallelAction(Action action1, Action action2) {
        super();
        this.actions = new Array(4);
        this.addAction(action1);
        this.addAction(action2);
    }

    public ParallelAction(Action action1, Action action2, Action action3) {
        super();
        this.actions = new Array(4);
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4) {
        super();
        this.actions = new Array(4);
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
        this.addAction(action4);
    }

    public ParallelAction(Action action1, Action action2, Action action3, Action action4, Action action5) {
        super();
        this.actions = new Array(4);
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
        this.addAction(action4);
        this.addAction(action5);
    }

    public boolean act(float delta) {  // has try-catch handlers
        int v1;
        Pool v3;
        boolean v4;
        if(this.complete) {
            v4 = true;
        }
        else {
            this.complete = true;
            v3 = this.getPool();
            this.setPool(null);
            try {
                Array v0 = this.actions;
                v1 = 0;
                int v2 = v0.size;
                while(true) {
                label_12:
                    if(v1 < v2 && this.actor != null) {
                        if(!v0.get(v1).act(delta)) {
                            this.complete = false;
                        }

                        if(this.actor != null) {
                            goto label_25;
                        }

                        break;
                    }

                    goto label_27;
                }
            }
            catch(Throwable v4_1) {
                goto label_31;
            }

            this.setPool(v3);
            v4 = true;
            goto label_4;
        label_25:
            ++v1;
            goto label_12;
            try {
            label_27:
                v4 = this.complete;
            }
            catch(Throwable v4_1) {
                goto label_31;
            }

            this.setPool(v3);
        }

    label_4:
        return v4;
    label_31:
        this.setPool(v3);
        throw v4_1;
    }

    public void addAction(Action action) {
        this.actions.add(action);
        if(this.actor != null) {
            action.setActor(this.actor);
        }
    }

    public Array getActions() {
        return this.actions;
    }

    public void reset() {
        super.reset();
        this.actions.clear();
    }

    public void restart() {
        this.complete = false;
        Array v0 = this.actions;
        int v1 = 0;
        int v2 = v0.size;
        while(v1 < v2) {
            v0.get(v1).restart();
            ++v1;
        }
    }

    public void setActor(Actor actor) {
        Array v0 = this.actions;
        int v1 = 0;
        int v2 = v0.size;
        while(v1 < v2) {
            v0.get(v1).setActor(actor);
            ++v1;
        }

        super.setActor(actor);
    }

    public String toString() {
        StringBuilder v1 = new StringBuilder(64);
        v1.append(super.toString());
        v1.append('(');
        Array v0 = this.actions;
        int v2 = 0;
        int v3 = v0.size;
        while(v2 < v3) {
            if(v2 > 0) {
                v1.append(", ");
            }

            v1.append(v0.get(v2));
            ++v2;
        }

        v1.append(')');
        return v1.toString();
    }
}

