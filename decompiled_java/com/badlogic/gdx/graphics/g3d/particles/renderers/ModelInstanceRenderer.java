// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.renderers;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;

public class ModelInstanceRenderer extends ParticleControllerRenderer {
    private boolean hasColor;
    private boolean hasRotation;
    private boolean hasScale;

    public ModelInstanceRenderer() {
        super(new ModelInstanceControllerRenderData());
    }

    public ModelInstanceRenderer(ModelInstanceParticleBatch batch) {
        this();
        this.setBatch(((ParticleBatch)batch));
    }

    public void allocateChannels() {
        this.renderData.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
    }

    public ParticleControllerComponent copy() {
        return new ModelInstanceRenderer(this.batch);
    }

    public void init() {
        boolean v0;
        boolean v2 = true;
        this.renderData.modelInstanceChannel = this.controller.particles.getChannel(ParticleChannels.ModelInstance);
        this.renderData.colorChannel = this.controller.particles.getChannel(ParticleChannels.Color);
        this.renderData.scaleChannel = this.controller.particles.getChannel(ParticleChannels.Scale);
        this.renderData.rotationChannel = this.controller.particles.getChannel(ParticleChannels.Rotation3D);
        if(this.renderData.colorChannel != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasColor = v0;
        if(this.renderData.scaleChannel != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasScale = v0;
        if(this.renderData.rotationChannel == null) {
            v2 = false;
        }

        this.hasRotation = v2;
    }

    public boolean isCompatible(ParticleBatch arg2) {
        return arg2 instanceof ModelInstanceParticleBatch;
    }

    public void update() {
        float v10;
        int v17 = 0;
        int v19 = 0;
        int v14 = this.controller.particles.size;
        while(v17 < v14) {
            Object v18 = this.renderData.modelInstanceChannel.data[v17];
            if(this.hasScale) {
                v10 = this.renderData.scaleChannel.data[v17];
            }
            else {
                v10 = 1f;
            }

            float v6 = 0f;
            float v7 = 0f;
            float v8 = 0f;
            float v9 = 1f;
            if(this.hasRotation) {
                int v20 = v17 * this.renderData.rotationChannel.strideSize;
                v6 = this.renderData.rotationChannel.data[v20];
                v7 = this.renderData.rotationChannel.data[v20 + 1];
                v8 = this.renderData.rotationChannel.data[v20 + 2];
                v9 = this.renderData.rotationChannel.data[v20 + 3];
            }

            v18.transform.set(this.renderData.positionChannel.data[v19], this.renderData.positionChannel.data[v19 + 1], this.renderData.positionChannel.data[v19 + 2], v6, v7, v8, v9, v10, v10, v10);
            if(this.hasColor) {
                int v16 = v17 * this.renderData.colorChannel.strideSize;
                Attribute v15 = v18.materials.get(0).get(ColorAttribute.Diffuse);
                Attribute v13 = v18.materials.get(0).get(BlendingAttribute.Type);
                ((ColorAttribute)v15).color.r = this.renderData.colorChannel.data[v16];
                ((ColorAttribute)v15).color.g = this.renderData.colorChannel.data[v16 + 1];
                ((ColorAttribute)v15).color.b = this.renderData.colorChannel.data[v16 + 2];
                if(v13 != null) {
                    ((BlendingAttribute)v13).opacity = this.renderData.colorChannel.data[v16 + 3];
                }
            }

            ++v17;
            v19 += this.renderData.positionChannel.strideSize;
        }

        super.update();
    }
}

