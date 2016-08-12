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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.FloatCounter;

public class PerformanceCounter {
    public float current;
    private long lastTick;
    public final FloatCounter load;
    public final String name;
    private static final float nano2seconds = 0f;
    private long startTime;
    public final FloatCounter time;
    public boolean valid;

    public PerformanceCounter(String name) {
        this(name, 5);
    }

    public PerformanceCounter(String name, int windowSize) {
        super();
        this.startTime = 0;
        this.lastTick = 0;
        this.current = 0f;
        this.valid = false;
        this.name = name;
        this.time = new FloatCounter(windowSize);
        this.load = new FloatCounter(1);
    }

    public void reset() {
        this.time.reset();
        this.load.reset();
        this.startTime = 0;
        this.lastTick = 0;
        this.current = 0f;
        this.valid = false;
    }

    public void start() {
        this.startTime = TimeUtils.nanoTime();
        this.valid = false;
    }

    public void stop() {
        long v6 = 0;
        if(this.startTime > v6) {
            this.current += (((float)(TimeUtils.nanoTime() - this.startTime))) * 0f;
            this.startTime = v6;
            this.valid = true;
        }
    }

    public void tick() {
        long v0 = TimeUtils.nanoTime();
        if(this.lastTick > 0) {
            this.tick((((float)(v0 - this.lastTick))) * 0f);
        }

        this.lastTick = v0;
    }

    public void tick(float delta) {
        float v0;
        float v4 = 1f;
        if(!this.valid) {
            Gdx.app.error("PerformanceCounter", "Invalid data, check if you called PerformanceCounter#stop()");
        }
        else {
            this.time.put(this.current);
            if(delta == 0f) {
                v0 = 0f;
            }
            else {
                v0 = this.current / delta;
            }

            FloatCounter v2 = this.load;
            if(delta <= v4) {
                v0 = delta * v0 + (v4 - delta) * this.load.latest;
            }

            v2.put(v0);
            this.current = 0f;
            this.valid = false;
        }
    }

    public StringBuilder toString(StringBuilder sb) {
        sb.append(this.name).append(": [time: ").append(this.time.value).append(", load: ").append(this.load.value).append("]");
        return sb;
    }

    public String toString() {
        return this.toString(new StringBuilder()).toString();
    }
}

