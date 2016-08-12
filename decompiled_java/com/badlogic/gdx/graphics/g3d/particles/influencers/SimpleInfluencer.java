// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.influencers;

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$ChannelDescriptor;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class SimpleInfluencer extends Influencer {
    FloatChannel interpolationChannel;
    FloatChannel lifeChannel;
    public ScaledNumericValue value;
    FloatChannel valueChannel;
    ChannelDescriptor valueChannelDescriptor;

    public SimpleInfluencer() {
        super();
        this.value = new ScaledNumericValue();
        this.value.setHigh(1f);
    }

    public SimpleInfluencer(SimpleInfluencer billboardScaleinfluencer) {
        this();
        this.set(billboardScaleinfluencer);
    }

    public void activateParticles(int startIndex, int count) {
        float v2;
        float v4;
        int v1;
        int v0;
        int v3;
        if(!this.value.isRelative()) {
            v3 = startIndex * this.valueChannel.strideSize;
            v0 = startIndex * this.interpolationChannel.strideSize;
            v1 = v3 + this.valueChannel.strideSize * count;
            while(v3 < v1) {
                v4 = this.value.newLowValue();
                v2 = this.value.newHighValue() - v4;
                this.interpolationChannel.data[v0] = v4;
                this.interpolationChannel.data[v0 + 1] = v2;
                this.valueChannel.data[v3] = this.value.getScale(0f) * v2 + v4;
                v3 += this.valueChannel.strideSize;
                v0 += this.interpolationChannel.strideSize;
            }
        }
        else {
            v3 = startIndex * this.valueChannel.strideSize;
            v0 = startIndex * this.interpolationChannel.strideSize;
            v1 = v3 + this.valueChannel.strideSize * count;
            while(v3 < v1) {
                v4 = this.value.newLowValue();
                v2 = this.value.newHighValue();
                this.interpolationChannel.data[v0] = v4;
                this.interpolationChannel.data[v0 + 1] = v2;
                this.valueChannel.data[v3] = this.value.getScale(0f) * v2 + v4;
                v3 += this.valueChannel.strideSize;
                v0 += this.interpolationChannel.strideSize;
            }
        }
    }

    public void allocateChannels() {
        this.valueChannel = this.controller.particles.addChannel(this.valueChannelDescriptor);
        ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
        this.interpolationChannel = this.controller.particles.addChannel(ParticleChannels.Interpolation);
        this.lifeChannel = this.controller.particles.addChannel(ParticleChannels.Life);
    }

    public void read(Json json, JsonValue jsonData) {
        this.value = json.readValue("value", ScaledNumericValue.class, jsonData);
    }

    private void set(SimpleInfluencer scaleInfluencer) {
        this.value.load(scaleInfluencer.value);
        this.valueChannelDescriptor = scaleInfluencer.valueChannelDescriptor;
    }

    public void update() {
        int v2 = 0;
        int v0 = 0;
        int v3 = 2;
        int v1 = this.controller.particles.size * this.valueChannel.strideSize;
        while(v2 < v1) {
            this.valueChannel.data[v2] = this.interpolationChannel.data[v0] + this.interpolationChannel.data[v0 + 1] * this.value.getScale(this.lifeChannel.data[v3]);
            v2 += this.valueChannel.strideSize;
            v0 += this.interpolationChannel.strideSize;
            v3 += this.lifeChannel.strideSize;
        }
    }

    public void write(Json json) {
        json.writeValue("value", this.value);
    }
}

