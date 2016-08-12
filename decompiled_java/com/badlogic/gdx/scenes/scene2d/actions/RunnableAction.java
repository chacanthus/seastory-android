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

public class RunnableAction extends Action {
    private boolean ran;
    private Runnable runnable;

    public RunnableAction() {
        super();
    }

    public boolean act(float delta) {
        if(!this.ran) {
            this.ran = true;
            this.run();
        }

        return 1;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public void reset() {
        super.reset();
        this.runnable = null;
    }

    public void restart() {
        this.ran = false;
    }

    public void run() {  // has try-catch handlers
        Pool v0 = this.getPool();
        this.setPool(null);
        try {
            this.runnable.run();
        }
        catch(Throwable v1) {
            this.setPool(v0);
            throw v1;
        }

        this.setPool(v0);
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}

