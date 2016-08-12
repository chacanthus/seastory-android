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
import com.badlogic.gdx.utils.Pool;

public class SequenceAction extends ParallelAction {
    private int index;

    public SequenceAction() {
        super();
    }

    public SequenceAction(Action action1) {
        super();
        this.addAction(action1);
    }

    public SequenceAction(Action action1, Action action2) {
        super();
        this.addAction(action1);
        this.addAction(action2);
    }

    public SequenceAction(Action action1, Action action2, Action action3) {
        super();
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
    }

    public SequenceAction(Action action1, Action action2, Action action3, Action action4) {
        super();
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
        this.addAction(action4);
    }

    public SequenceAction(Action action1, Action action2, Action action3, Action action4, Action action5) {
        super();
        this.addAction(action1);
        this.addAction(action2);
        this.addAction(action3);
        this.addAction(action4);
        this.addAction(action5);
    }

    public boolean act(float delta) {  // has try-catch handlers
        boolean v1;
        if(this.index >= this.actions.size) {
            v1 = true;
        }
        else {
            Pool v0 = this.getPool();
            this.setPool(null);
            try {
                if(this.actions.get(this.index).act(delta)) {
                    if(this.actor == null) {
                        goto label_17;
                    }
                    else {
                        goto label_20;
                    }
                }

                goto label_30;
            }
            catch(Throwable v1_1) {
                goto label_34;
            }

        label_17:
            this.setPool(v0);
            v1 = true;
            goto label_6;
            try {
            label_20:
                ++this.index;
                if(this.index < this.actions.size) {
                    goto label_30;
                }
            }
            catch(Throwable v1_1) {
            label_34:
                this.setPool(v0);
                throw v1_1;
            }

            this.setPool(v0);
            v1 = true;
            goto label_6;
        label_30:
            v1 = false;
            this.setPool(v0);
        }

    label_6:
        return v1;
    }

    public void restart() {
        super.restart();
        this.index = 0;
    }
}

