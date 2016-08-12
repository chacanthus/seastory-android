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

public class RangedNumericValue extends ParticleValue {
    private float lowMax;
    private float lowMin;

    public RangedNumericValue() {
        super();
    }

    public float getLowMax() {
        return this.lowMax;
    }

    public float getLowMin() {
        return this.lowMin;
    }

    public void load(RangedNumericValue value) {
        super.load(((ParticleValue)value));
        this.lowMax = value.lowMax;
        this.lowMin = value.lowMin;
    }

    public float newLowValue() {
        return this.lowMin + (this.lowMax - this.lowMin) * MathUtils.random();
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.lowMin = json.readValue("lowMin", Float.TYPE, jsonData).floatValue();
        this.lowMax = json.readValue("lowMax", Float.TYPE, jsonData).floatValue();
    }

    public void setLow(float value) {
        this.lowMin = value;
        this.lowMax = value;
    }

    public void setLow(float min, float max) {
        this.lowMin = min;
        this.lowMax = max;
    }

    public void setLowMax(float lowMax) {
        this.lowMax = lowMax;
    }

    public void setLowMin(float lowMin) {
        this.lowMin = lowMin;
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("lowMin", Float.valueOf(this.lowMin));
        json.writeValue("lowMax", Float.valueOf(this.lowMax));
    }
}

