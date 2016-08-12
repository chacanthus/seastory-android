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

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class CumulativeDistribution {
    public class CumulativeValue {
        public float frequency;
        public float interval;
        public Object value;

        public CumulativeValue(CumulativeDistribution arg1, Object arg2, float frequency, float interval) {
            CumulativeDistribution.this = arg1;
            super();
            this.value = arg2;
            this.frequency = frequency;
            this.interval = interval;
        }
    }

    private Array values;

    public CumulativeDistribution() {
        super();
        this.values = new Array(false, 10, CumulativeValue.class);
    }

    public void add(Object arg4) {
        this.values.add(new CumulativeValue(this, arg4, 0f, 0f));
    }

    public void add(Object arg4, float intervalSize) {
        this.values.add(new CumulativeValue(this, arg4, 0f, intervalSize));
    }

    public void clear() {
        this.values.clear();
    }

    public void generate() {
        float v1 = 0f;
        int v0;
        for(v0 = 0; v0 < this.values.size; ++v0) {
            v1 += this.values.items[v0].interval;
            this.values.items[v0].frequency = v1;
        }
    }

    public void generateNormalized() {
        float v2 = 0f;
        int v0;
        for(v0 = 0; v0 < this.values.size; ++v0) {
            v2 += this.values.items[v0].interval;
        }

        float v1 = 0f;
        for(v0 = 0; v0 < this.values.size; ++v0) {
            v1 += this.values.items[v0].interval / v2;
            this.values.items[v0].frequency = v1;
        }
    }

    public void generateUniform() {
        float v0 = 1f / (((float)this.values.size));
        int v1;
        for(v1 = 0; v1 < this.values.size; ++v1) {
            this.values.items[v1].interval = v0;
            this.values.items[v1].frequency = (((float)(v1 + 1))) * v0;
        }
    }

    public float getInterval(int index) {
        return this.values.items[index].interval;
    }

    public Object getValue(int index) {
        return this.values.items[index].value;
    }

    public void setInterval(int index, float intervalSize) {
        this.values.items[index].interval = intervalSize;
    }

    public void setInterval(Object arg4, float intervalSize) {
        Object v1;
        Iterator v0 = this.values.iterator();
        do {
            if(v0.hasNext()) {
                v1 = v0.next();
                if(((CumulativeValue)v1).value != arg4) {
                    continue;
                }

                break;
            }

            return;
        }
        while(true);

        ((CumulativeValue)v1).interval = intervalSize;
    }

    public int size() {
        return this.values.size;
    }

    public Object value() {
        return this.value(MathUtils.random());
    }

    public Object value(float probability) {
        Object v3_1;
        CumulativeValue v3 = null;
        int v0 = this.values.size - 1;
        int v2 = 0;
        while(v2 <= v0) {
            int v1 = v2 + (v0 - v2) / 2;
            v3_1 = this.values.items[v1];
            if(probability < ((CumulativeValue)v3_1).frequency) {
                v0 = v1 - 1;
                continue;
            }
            else if(probability > ((CumulativeValue)v3_1).frequency) {
                v2 = v1 + 1;
                continue;
            }

            break;
        }

        return ((CumulativeValue)v3_1).value;
    }
}

