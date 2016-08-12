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

public class FloatCounter {
    public float average;
    public int count;
    public float latest;
    public float max;
    public final WindowedMean mean;
    public float min;
    public float total;
    public float value;

    public FloatCounter(int windowSize) {
        WindowedMean v0;
        super();
        if(windowSize > 1) {
            v0 = new WindowedMean(windowSize);
        }
        else {
            v0 = null;
        }

        this.mean = v0;
        this.reset();
    }

    public void put(float value) {
        this.latest = value;
        this.total += value;
        ++this.count;
        this.average = this.total / (((float)this.count));
        if(this.mean != null) {
            this.mean.addValue(value);
            this.value = this.mean.getMean();
        }
        else {
            this.value = this.latest;
        }

        if(this.mean == null || (this.mean.hasEnoughData())) {
            if(this.value < this.min) {
                this.min = this.value;
            }

            if(this.value <= this.max) {
                return;
            }

            this.max = this.value;
        }
    }

    public void reset() {
        this.count = 0;
        this.total = 0f;
        this.min = 340282346638528860000000000000000000000f;
        this.max = 0f;
        this.average = 0f;
        this.latest = 0f;
        this.value = 0f;
        if(this.mean != null) {
            this.mean.clear();
        }
    }
}

