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

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

public abstract class TemporalAction extends Action {
    private boolean began;
    private boolean complete;
    private float duration;
    private Interpolation interpolation;
    private boolean reverse;
    private float time;

    public TemporalAction() {
        super();
    }

    public TemporalAction(float duration) {
        super();
        this.duration = duration;
    }

    public TemporalAction(float duration, Interpolation interpolation) {
        super();
        this.duration = duration;
        this.interpolation = interpolation;
    }

    public boolean act(float delta) {  // has try-catch handlers
        float v0;
        boolean v2 = true;
        if(!this.complete) {
            Pool v1 = this.getPool();
            this.setPool(null);
            try {
                if(!this.began) {
                    this.begin();
                    this.began = true;
                }

                this.time += delta;
                if(this.time < this.duration) {
                    goto label_33;
                }

                goto label_18;
            }
            catch(Throwable v2_1) {
                goto label_44;
            }

        label_33:
            v2 = false;
            try {
            label_18:
                this.complete = v2;
                if(this.complete) {
                    v0 = 1f;
                }
                else {
                    v0 = this.time / this.duration;
                    if(this.interpolation != null) {
                        v0 = this.interpolation.apply(v0);
                    }
                }

                if(this.reverse) {
                    --v0;
                }

                this.update(v0);
                if(this.complete) {
                    this.end();
                }

                v2 = this.complete;
            }
            catch(Throwable v2_1) {
            label_44:
                this.setPool(v1);
                throw v2_1;
            }

            this.setPool(v1);
        }

        return v2;
    }

    protected void begin() {
    }

    protected void end() {
    }

    public void finish() {
        this.time = this.duration;
    }

    public float getDuration() {
        return this.duration;
    }

    public Interpolation getInterpolation() {
        return this.interpolation;
    }

    public float getTime() {
        return this.time;
    }

    public boolean isReverse() {
        return this.reverse;
    }

    public void reset() {
        super.reset();
        this.reverse = false;
        this.interpolation = null;
    }

    public void restart() {
        this.time = 0f;
        this.began = false;
        this.complete = false;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void setTime(float time) {
        this.time = time;
    }

    protected abstract void update(float arg0);
}

