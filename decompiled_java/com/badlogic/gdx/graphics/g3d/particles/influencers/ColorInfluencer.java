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

import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.values.GradientColorValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class ColorInfluencer extends Influencer {
    public class Random extends ColorInfluencer {
        FloatChannel colorChannel;

        public Random() {
            this();
        }

        public void activateParticles(int startIndex, int count) {
            int v1 = startIndex * this.colorChannel.strideSize;
            int v0 = v1 + this.colorChannel.strideSize * count;
            while(v1 < v0) {
                this.colorChannel.data[v1] = MathUtils.random();
                this.colorChannel.data[v1 + 1] = MathUtils.random();
                this.colorChannel.data[v1 + 2] = MathUtils.random();
                this.colorChannel.data[v1 + 3] = MathUtils.random();
                v1 += this.colorChannel.strideSize;
            }
        }

        public void allocateChannels() {
            this.colorChannel = this.controller.particles.addChannel(ParticleChannels.Color);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Random copy() {
            return new Random();
        }
    }

    public class Single extends ColorInfluencer {
        FloatChannel alphaInterpolationChannel;
        public ScaledNumericValue alphaValue;
        public GradientColorValue colorValue;
        FloatChannel lifeChannel;

        public Single() {
            this();
            this.colorValue = new GradientColorValue();
            this.alphaValue = new ScaledNumericValue();
            this.alphaValue.setHigh(1f);
        }

        public Single(Single billboardColorInfluencer) {
            super();
            this.set(billboardColorInfluencer);
        }

        public void activateParticles(int startIndex, int count) {
            int v4 = startIndex * this.colorChannel.strideSize;
            int v0 = startIndex * this.alphaInterpolationChannel.strideSize;
            int v5 = this.lifeChannel.strideSize * startIndex + 2;
            int v3 = v4 + this.colorChannel.strideSize * count;
            while(v4 < v3) {
                float v2 = this.alphaValue.newLowValue();
                float v1 = this.alphaValue.newHighValue() - v2;
                this.colorValue.getColor(0f, this.colorChannel.data, v4);
                this.colorChannel.data[v4 + 3] = this.alphaValue.getScale(this.lifeChannel.data[v5]) * v1 + v2;
                this.alphaInterpolationChannel.data[v0] = v2;
                this.alphaInterpolationChannel.data[v0 + 1] = v1;
                v4 += this.colorChannel.strideSize;
                v0 += this.alphaInterpolationChannel.strideSize;
                v5 += this.lifeChannel.strideSize;
            }
        }

        public void allocateChannels() {
            super.allocateChannels();
            ParticleChannels.Interpolation.id = this.controller.particleChannels.newId();
            this.alphaInterpolationChannel = this.controller.particles.addChannel(ParticleChannels.Interpolation);
            this.lifeChannel = this.controller.particles.addChannel(ParticleChannels.Life);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Single copy() {
            return new Single(this);
        }

        public void read(Json json, JsonValue jsonData) {
            this.alphaValue = json.readValue("alpha", ScaledNumericValue.class, jsonData);
            this.colorValue = json.readValue("color", GradientColorValue.class, jsonData);
        }

        public void set(Single colorInfluencer) {
            this.colorValue.load(colorInfluencer.colorValue);
            this.alphaValue.load(colorInfluencer.alphaValue);
        }

        public void update() {
            int v2 = 0;
            int v0 = 0;
            int v3 = 2;
            int v1 = this.controller.particles.size * this.colorChannel.strideSize;
            while(v2 < v1) {
                float v4 = this.lifeChannel.data[v3];
                this.colorValue.getColor(v4, this.colorChannel.data, v2);
                this.colorChannel.data[v2 + 3] = this.alphaInterpolationChannel.data[v0] + this.alphaInterpolationChannel.data[v0 + 1] * this.alphaValue.getScale(v4);
                v2 += this.colorChannel.strideSize;
                v0 += this.alphaInterpolationChannel.strideSize;
                v3 += this.lifeChannel.strideSize;
            }
        }

        public void write(Json json) {
            json.writeValue("alpha", this.alphaValue);
            json.writeValue("color", this.colorValue);
        }
    }

    FloatChannel colorChannel;

    public ColorInfluencer() {
        super();
    }

    public void allocateChannels() {
        this.colorChannel = this.controller.particles.addChannel(ParticleChannels.Color);
    }
}

