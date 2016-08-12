// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.emitters;

import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;

public abstract class Emitter extends ParticleControllerComponent implements Serializable {
    public int maxParticleCount;
    public int minParticleCount;
    public float percent;

    public Emitter() {
        super();
        this.maxParticleCount = 4;
    }

    public Emitter(Emitter regularEmitter) {
        super();
        this.maxParticleCount = 4;
        this.set(regularEmitter);
    }

    public void end() {
        this.controller.particles.size = 0;
    }

    public int getMaxParticleCount() {
        return this.maxParticleCount;
    }

    public int getMinParticleCount() {
        return this.minParticleCount;
    }

    public void init() {
        this.controller.particles.size = 0;
    }

    public void read(Json json, JsonValue jsonData) {
        this.minParticleCount = json.readValue("minParticleCount", Integer.TYPE, jsonData).intValue();
        this.maxParticleCount = json.readValue("maxParticleCount", Integer.TYPE, jsonData).intValue();
    }

    public void set(Emitter emitter) {
        this.minParticleCount = emitter.minParticleCount;
        this.maxParticleCount = emitter.maxParticleCount;
    }

    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
    }

    public void setMinParticleCount(int minParticleCount) {
        this.minParticleCount = minParticleCount;
    }

    public void setParticleCount(int aMin, int aMax) {
        this.setMinParticleCount(aMin);
        this.setMaxParticleCount(aMax);
    }

    public void write(Json json) {
        json.writeValue("minParticleCount", Integer.valueOf(this.minParticleCount));
        json.writeValue("maxParticleCount", Integer.valueOf(this.maxParticleCount));
    }
}

