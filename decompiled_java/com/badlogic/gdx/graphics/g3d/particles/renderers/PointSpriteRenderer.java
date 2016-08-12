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

import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels$ColorInitializer;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels$Rotation2dInitializer;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels$ScaleInitializer;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels$TextureRegionInitializer;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;

public class PointSpriteRenderer extends ParticleControllerRenderer {
    public PointSpriteRenderer() {
        super(new PointSpriteControllerRenderData());
    }

    public PointSpriteRenderer(PointSpriteParticleBatch batch) {
        this();
        this.setBatch(((ParticleBatch)batch));
    }

    public void allocateChannels() {
        this.renderData.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
        this.renderData.regionChannel = this.controller.particles.addChannel(ParticleChannels.TextureRegion, TextureRegionInitializer.get());
        this.renderData.colorChannel = this.controller.particles.addChannel(ParticleChannels.Color, ColorInitializer.get());
        this.renderData.scaleChannel = this.controller.particles.addChannel(ParticleChannels.Scale, ScaleInitializer.get());
        this.renderData.rotationChannel = this.controller.particles.addChannel(ParticleChannels.Rotation2D, Rotation2dInitializer.get());
    }

    public ParticleControllerComponent copy() {
        return new PointSpriteRenderer(this.batch);
    }

    public boolean isCompatible(ParticleBatch arg2) {
        return arg2 instanceof PointSpriteParticleBatch;
    }
}

