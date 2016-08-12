// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class GradientColorValue extends ParticleValue {
    private float[] colors;
    private static float[] temp;
    public float[] timeline;

    static  {
        GradientColorValue.temp = new float[3];
    }

    public GradientColorValue() {
        super();
        this.colors = new float[]{1f, 1f, 1f};
        float[] v0 = new float[1];
        v0[0] = 0f;
        this.timeline = v0;
    }

    public void getColor(float percent, float[] out, int index) {
        int v7 = 0;
        int v1 = -1;
        float[] v10 = this.timeline;
        int v5 = v10.length;
        int v4 = 1;
        while(v4 < v5) {
            if(v10[v4] > percent) {
                v1 = v4;
            }
            else {
                v7 = v4;
                ++v4;
                continue;
            }

            break;
        }

        float v8 = v10[v7];
        v7 *= 3;
        float v6 = this.colors[v7];
        float v3 = this.colors[v7 + 1];
        float v0 = this.colors[v7 + 2];
        if(v1 == -1) {
            out[index] = v6;
            out[index + 1] = v3;
            out[index + 2] = v0;
        }
        else {
            float v2 = (percent - v8) / (v10[v1] - v8);
            v1 *= 3;
            out[index] = (this.colors[v1] - v6) * v2 + v6;
            out[index + 1] = (this.colors[v1 + 1] - v3) * v2 + v3;
            out[index + 2] = (this.colors[v1 + 2] - v0) * v2 + v0;
        }
    }

    public float[] getColor(float percent) {
        this.getColor(percent, GradientColorValue.temp, 0);
        return GradientColorValue.temp;
    }

    public float[] getColors() {
        return this.colors;
    }

    public float[] getTimeline() {
        return this.timeline;
    }

    public void load(GradientColorValue value) {
        super.load(((ParticleValue)value));
        this.colors = new float[value.colors.length];
        System.arraycopy(value.colors, 0, this.colors, 0, this.colors.length);
        this.timeline = new float[value.timeline.length];
        System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.colors = json.readValue("colors", float[].class, jsonData);
        this.timeline = json.readValue("timeline", float[].class, jsonData);
    }

    public void setColors(float[] colors) {
        this.colors = colors;
    }

    public void setTimeline(float[] timeline) {
        this.timeline = timeline;
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("colors", this.colors);
        json.writeValue("timeline", this.timeline);
    }
}

