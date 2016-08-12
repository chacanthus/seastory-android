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
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$ObjectChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ParticleControllerFinalizerInfluencer extends Influencer {
    ObjectChannel controllerChannel;
    boolean hasRotation;
    boolean hasScale;
    FloatChannel positionChannel;
    FloatChannel rotationChannel;
    FloatChannel scaleChannel;

    public ParticleControllerFinalizerInfluencer() {
        super();
    }

    public void allocateChannels() {
        this.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
    }

    public ParticleControllerComponent copy() {
        return this.copy();
    }

    public ParticleControllerFinalizerInfluencer copy() {
        return new ParticleControllerFinalizerInfluencer();
    }

    public void init() {
        boolean v0;
        boolean v1 = true;
        this.controllerChannel = this.controller.particles.getChannel(ParticleChannels.ParticleController);
        if(this.controllerChannel == null) {
            throw new GdxRuntimeException("ParticleController channel not found, specify an influencer which will allocate it please.");
        }

        this.scaleChannel = this.controller.particles.getChannel(ParticleChannels.Scale);
        this.rotationChannel = this.controller.particles.getChannel(ParticleChannels.Rotation3D);
        if(this.scaleChannel != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasScale = v0;
        if(this.rotationChannel == null) {
            v1 = false;
        }

        this.hasRotation = v1;
    }

    public void update() {
        float v8;
        int v10 = 0;
        int v11 = 0;
        int v9 = this.controller.particles.size;
        while(v10 < v9) {
            Object v0 = this.controllerChannel.data[v10];
            if(this.hasScale) {
                v8 = this.scaleChannel.data[v10];
            }
            else {
                v8 = 1f;
            }

            float v4 = 0f;
            float v5 = 0f;
            float v6 = 0f;
            float v7 = 1f;
            if(this.hasRotation) {
                int v12 = v10 * this.rotationChannel.strideSize;
                v4 = this.rotationChannel.data[v12];
                v5 = this.rotationChannel.data[v12 + 1];
                v6 = this.rotationChannel.data[v12 + 2];
                v7 = this.rotationChannel.data[v12 + 3];
            }

            ((ParticleController)v0).setTransform(this.positionChannel.data[v11], this.positionChannel.data[v11 + 1], this.positionChannel.data[v11 + 2], v4, v5, v6, v7, v8);
            ((ParticleController)v0).update();
            ++v10;
            v11 += this.positionChannel.strideSize;
        }
    }
}

