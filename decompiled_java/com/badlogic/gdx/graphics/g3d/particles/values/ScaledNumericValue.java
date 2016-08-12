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

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ScaledNumericValue extends RangedNumericValue {
    private float highMax;
    private float highMin;
    private boolean relative;
    private float[] scaling;
    public float[] timeline;

    public ScaledNumericValue() {
        super();
        float[] v0 = new float[1];
        v0[0] = 1f;
        this.scaling = v0;
        v0 = new float[1];
        v0[0] = 0f;
        this.timeline = v0;
        this.relative = false;
    }

    public float getHighMax() {
        return this.highMax;
    }

    public float getHighMin() {
        return this.highMin;
    }

    public float getScale(float percent) {
        float v7;
        int v0 = -1;
        int v2 = this.timeline.length;
        int v1 = 1;
        while(v1 < v2) {
            if(this.timeline[v1] > percent) {
                v0 = v1;
            }
            else {
                ++v1;
                continue;
            }

            break;
        }

        if(v0 == -1) {
            v7 = this.scaling[v2 - 1];
        }
        else {
            int v3 = v0 - 1;
            v7 = (this.scaling[v0] - this.scaling[v3]) * ((percent - this.timeline[v3]) / (this.timeline[v0] - this.timeline[v3])) + this.scaling[v3];
        }

        return v7;
    }

    public float[] getScaling() {
        return this.scaling;
    }

    public float[] getTimeline() {
        return this.timeline;
    }

    public boolean isRelative() {
        return this.relative;
    }

    public void load(ScaledNumericValue value) {
        super.load(((RangedNumericValue)value));
        this.highMax = value.highMax;
        this.highMin = value.highMin;
        this.scaling = new float[value.scaling.length];
        System.arraycopy(value.scaling, 0, this.scaling, 0, this.scaling.length);
        this.timeline = new float[value.timeline.length];
        System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
        this.relative = value.relative;
    }

    public float newHighValue() {
        return this.highMin + (this.highMax - this.highMin) * MathUtils.random();
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.highMin = json.readValue("highMin", Float.TYPE, jsonData).floatValue();
        this.highMax = json.readValue("highMax", Float.TYPE, jsonData).floatValue();
        this.relative = json.readValue("relative", Boolean.TYPE, jsonData).booleanValue();
        this.scaling = json.readValue("scaling", float[].class, jsonData);
        this.timeline = json.readValue("timeline", float[].class, jsonData);
    }

    public void setHigh(float value) {
        this.highMin = value;
        this.highMax = value;
    }

    public void setHigh(float min, float max) {
        this.highMin = min;
        this.highMax = max;
    }

    public void setHighMax(float highMax) {
        this.highMax = highMax;
    }

    public void setHighMin(float highMin) {
        this.highMin = highMin;
    }

    public void setRelative(boolean relative) {
        this.relative = relative;
    }

    public void setScaling(float[] values) {
        this.scaling = values;
    }

    public void setTimeline(float[] timeline) {
        this.timeline = timeline;
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("highMin", Float.valueOf(this.highMin));
        json.writeValue("highMax", Float.valueOf(this.highMax));
        json.writeValue("relative", Boolean.valueOf(this.relative));
        json.writeValue("scaling", this.scaling);
        json.writeValue("timeline", this.timeline);
    }
}

