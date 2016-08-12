// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

public final class WindowedMean {
    int added_values;
    boolean dirty;
    int last_value;
    float mean;
    float[] values;

    public WindowedMean(int window_size) {
        super();
        this.added_values = 0;
        this.mean = 0f;
        this.dirty = true;
        this.values = new float[window_size];
    }

    public void addValue(float value) {
        if(this.added_values < this.values.length) {
            ++this.added_values;
        }

        float[] v0 = this.values;
        int v1 = this.last_value;
        this.last_value = v1 + 1;
        v0[v1] = value;
        if(this.last_value > this.values.length - 1) {
            this.last_value = 0;
        }

        this.dirty = true;
    }

    public void clear() {
        this.added_values = 0;
        this.last_value = 0;
        int v0;
        for(v0 = 0; v0 < this.values.length; ++v0) {
            this.values[v0] = 0f;
        }

        this.dirty = true;
    }

    public float getLatest() {
        int v0;
        float[] v1 = this.values;
        if(this.last_value - 1 == -1) {
            v0 = this.values.length - 1;
        }
        else {
            v0 = this.last_value - 1;
        }

        return v1[v0];
    }

    public float getMean() {
        float v2;
        if(this.hasEnoughData()) {
            if(this.dirty) {
                float v1 = 0f;
                int v0;
                for(v0 = 0; v0 < this.values.length; ++v0) {
                    v1 += this.values[v0];
                }

                this.mean = v1 / (((float)this.values.length));
                this.dirty = false;
            }

            v2 = this.mean;
        }
        else {
            v2 = 0f;
        }

        return v2;
    }

    public float getOldest() {
        float v0;
        if(this.last_value == this.values.length - 1) {
            v0 = this.values[0];
        }
        else {
            v0 = this.values[this.last_value + 1];
        }

        return v0;
    }

    public int getWindowSize() {
        return this.values.length;
    }

    public boolean hasEnoughData() {
        boolean v0;
        if(this.added_values >= this.values.length) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public float standardDeviation() {
        float v3;
        if(!this.hasEnoughData()) {
            v3 = 0f;
        }
        else {
            float v1 = this.getMean();
            float v2 = 0f;
            int v0;
            for(v0 = 0; v0 < this.values.length; ++v0) {
                v2 += (this.values[v0] - v1) * (this.values[v0] - v1);
            }

            v3 = ((float)Math.sqrt(((double)(v2 / (((float)this.values.length))))));
        }

        return v3;
    }
}

