// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

public class PerformanceCounters {
    public final Array counters;
    private long lastTick;
    private static final float nano2seconds = 0f;

    public PerformanceCounters() {
        super();
        this.lastTick = 0;
        this.counters = new Array();
    }

    public PerformanceCounter add(String name) {
        PerformanceCounter v0 = new PerformanceCounter(name);
        this.counters.add(v0);
        return v0;
    }

    public PerformanceCounter add(String name, int windowSize) {
        PerformanceCounter v0 = new PerformanceCounter(name, windowSize);
        this.counters.add(v0);
        return v0;
    }

    public void tick() {
        long v0 = TimeUtils.nanoTime();
        if(this.lastTick > 0) {
            this.tick((((float)(v0 - this.lastTick))) * 0f);
        }

        this.lastTick = v0;
    }

    public void tick(float deltaTime) {
        int v0;
        for(v0 = 0; v0 < this.counters.size; ++v0) {
            this.counters.get(v0).tick(deltaTime);
        }
    }

    public StringBuilder toString(StringBuilder sb) {
        sb.setLength(0);
        int v0;
        for(v0 = 0; v0 < this.counters.size; ++v0) {
            if(v0 != 0) {
                sb.append("; ");
            }

            this.counters.get(v0).toString(sb);
        }

        return sb;
    }
}

