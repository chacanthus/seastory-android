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
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import java.util.Arrays;

public class DynamicsInfluencer extends Influencer {
    private FloatChannel accellerationChannel;
    private FloatChannel angularVelocityChannel;
    boolean has2dAngularVelocity;
    boolean has3dAngularVelocity;
    boolean hasAcceleration;
    private FloatChannel positionChannel;
    private FloatChannel previousPositionChannel;
    private FloatChannel rotationChannel;
    public Array velocities;

    public DynamicsInfluencer() {
        super();
        this.velocities = new Array(true, 3, DynamicsModifier.class);
    }

    public DynamicsInfluencer(DynamicsInfluencer velocityInfluencer) {
        this(velocityInfluencer.velocities.toArray(DynamicsModifier.class));
    }

    public DynamicsInfluencer(DynamicsModifier[] velocities) {
        super();
        this.velocities = new Array(true, velocities.length, DynamicsModifier.class);
        DynamicsModifier[] v0 = velocities;
        int v2 = v0.length;
        int v1;
        for(v1 = 0; v1 < v2; ++v1) {
            this.velocities.add(v0[v1].copy());
        }
    }

    public void activateParticles(int startIndex, int count) {
        int v0;
        int v1;
        float v8 = 1f;
        if(this.hasAcceleration) {
            v1 = startIndex * this.positionChannel.strideSize;
            v0 = v1 + this.positionChannel.strideSize * count;
            while(v1 < v0) {
                this.previousPositionChannel.data[v1] = this.positionChannel.data[v1];
                this.previousPositionChannel.data[v1 + 1] = this.positionChannel.data[v1 + 1];
                this.previousPositionChannel.data[v1 + 2] = this.positionChannel.data[v1 + 2];
                v1 += this.positionChannel.strideSize;
            }
        }

        if(this.has2dAngularVelocity) {
            v1 = startIndex * this.rotationChannel.strideSize;
            v0 = v1 + this.rotationChannel.strideSize * count;
            while(v1 < v0) {
                this.rotationChannel.data[v1] = v8;
                this.rotationChannel.data[v1 + 1] = 0f;
                v1 += this.rotationChannel.strideSize;
            }
        }
        else if(this.has3dAngularVelocity) {
            v1 = startIndex * this.rotationChannel.strideSize;
            v0 = v1 + this.rotationChannel.strideSize * count;
            while(v1 < v0) {
                this.rotationChannel.data[v1] = 0f;
                this.rotationChannel.data[v1 + 1] = 0f;
                this.rotationChannel.data[v1 + 2] = 0f;
                this.rotationChannel.data[v1 + 3] = v8;
                v1 += this.rotationChannel.strideSize;
            }
        }

        int v2;
        for(v2 = 0; v2 < this.velocities.size; ++v2) {
            this.velocities.items[v2].activateParticles(startIndex, count);
        }
    }

    public void allocateChannels() {
        boolean v1;
        boolean v2 = true;
        int v0;
        for(v0 = 0; v0 < this.velocities.size; ++v0) {
            this.velocities.items[v0].allocateChannels();
        }

        this.accellerationChannel = this.controller.particles.getChannel(ParticleChannels.Acceleration);
        if(this.accellerationChannel != null) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        this.hasAcceleration = v1;
        if(this.hasAcceleration) {
            this.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
            this.previousPositionChannel = this.controller.particles.addChannel(ParticleChannels.PreviousPosition);
        }

        this.angularVelocityChannel = this.controller.particles.getChannel(ParticleChannels.AngularVelocity2D);
        if(this.angularVelocityChannel != null) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        this.has2dAngularVelocity = v1;
        if(this.has2dAngularVelocity) {
            this.rotationChannel = this.controller.particles.addChannel(ParticleChannels.Rotation2D);
            this.has3dAngularVelocity = false;
        }
        else {
            this.angularVelocityChannel = this.controller.particles.getChannel(ParticleChannels.AngularVelocity3D);
            if(this.angularVelocityChannel == null) {
                v2 = false;
            }

            this.has3dAngularVelocity = v2;
            if(!this.has3dAngularVelocity) {
                return;
            }

            this.rotationChannel = this.controller.particles.addChannel(ParticleChannels.Rotation3D);
        }
    }

    public ParticleControllerComponent copy() {
        return this.copy();
    }

    public DynamicsInfluencer copy() {
        return new DynamicsInfluencer(this);
    }

    public void init() {
        int v0;
        for(v0 = 0; v0 < this.velocities.size; ++v0) {
            this.velocities.items[v0].init();
        }
    }

    public void read(Json json, JsonValue jsonData) {
        this.velocities.addAll(json.readValue("velocities", Array.class, DynamicsModifier.class, jsonData));
    }

    public void set(ParticleController particleController) {
        super.set(particleController);
        int v0;
        for(v0 = 0; v0 < this.velocities.size; ++v0) {
            this.velocities.items[v0].set(particleController);
        }
    }

    public void update() {
        int v9;
        if(this.hasAcceleration) {
            Arrays.fill(this.accellerationChannel.data, 0, this.controller.particles.size * this.accellerationChannel.strideSize, 0f);
        }

        if((this.has2dAngularVelocity) || (this.has3dAngularVelocity)) {
            Arrays.fill(this.angularVelocityChannel.data, 0, this.controller.particles.size * this.angularVelocityChannel.strideSize, 0f);
        }

        int v10;
        for(v10 = 0; v10 < this.velocities.size; ++v10) {
            this.velocities.items[v10].update();
        }

        if(this.hasAcceleration) {
            v9 = 0;
            int v13;
            for(v13 = 0; v9 < this.controller.particles.size; v13 += this.positionChannel.strideSize) {
                float v23 = this.positionChannel.data[v13];
                float v24 = this.positionChannel.data[v13 + 1];
                float v25 = this.positionChannel.data[v13 + 2];
                this.positionChannel.data[v13] = 2f * v23 - this.previousPositionChannel.data[v13] + this.accellerationChannel.data[v13] * this.controller.deltaTimeSqr;
                this.positionChannel.data[v13 + 1] = 2f * v24 - this.previousPositionChannel.data[v13 + 1] + this.accellerationChannel.data[v13 + 1] * this.controller.deltaTimeSqr;
                this.positionChannel.data[v13 + 2] = 2f * v25 - this.previousPositionChannel.data[v13 + 2] + this.accellerationChannel.data[v13 + 2] * this.controller.deltaTimeSqr;
                this.previousPositionChannel.data[v13] = v23;
                this.previousPositionChannel.data[v13 + 1] = v24;
                this.previousPositionChannel.data[v13 + 2] = v25;
                ++v9;
            }
        }

        if(this.has2dAngularVelocity) {
            v9 = 0;
            for(v13 = 0; v9 < this.controller.particles.size; v13 += this.rotationChannel.strideSize) {
                float v18 = this.angularVelocityChannel.data[v9] * this.controller.deltaTime;
                if(v18 != 0f) {
                    float v6 = MathUtils.cosDeg(v18);
                    float v19 = MathUtils.sinDeg(v18);
                    float v7 = this.rotationChannel.data[v13];
                    float v11 = v7 * v6 - this.rotationChannel.data[v13 + 1] * v19;
                    float v12 = this.rotationChannel.data[v13 + 1] * v6 + v7 * v19;
                    this.rotationChannel.data[v13] = v11;
                    this.rotationChannel.data[v13 + 1] = v12;
                }

                ++v9;
            }
        }
        else {
            if(!this.has3dAngularVelocity) {
                return;
            }

            v9 = 0;
            v13 = 0;
            int v5;
            for(v5 = 0; v9 < this.controller.particles.size; v5 += this.angularVelocityChannel.strideSize) {
                DynamicsInfluencer.TMP_Q.set(this.angularVelocityChannel.data[v5], this.angularVelocityChannel.data[v5 + 1], this.angularVelocityChannel.data[v5 + 2], 0f).mul(this.rotationChannel.data[v13], this.rotationChannel.data[v13 + 1], this.rotationChannel.data[v13 + 2], this.rotationChannel.data[v13 + 3]).mul(0.5f * this.controller.deltaTime).add(this.rotationChannel.data[v13], this.rotationChannel.data[v13 + 1], this.rotationChannel.data[v13 + 2], this.rotationChannel.data[v13 + 3]).nor();
                this.rotationChannel.data[v13] = DynamicsInfluencer.TMP_Q.x;
                this.rotationChannel.data[v13 + 1] = DynamicsInfluencer.TMP_Q.y;
                this.rotationChannel.data[v13 + 2] = DynamicsInfluencer.TMP_Q.z;
                this.rotationChannel.data[v13 + 3] = DynamicsInfluencer.TMP_Q.w;
                ++v9;
                v13 += this.rotationChannel.strideSize;
            }
        }
    }

    public void write(Json json) {
        json.writeValue("velocities", this.velocities, Array.class, DynamicsModifier.class);
    }
}

