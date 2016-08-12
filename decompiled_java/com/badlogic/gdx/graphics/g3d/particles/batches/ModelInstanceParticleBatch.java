// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ModelInstanceControllerRenderData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class ModelInstanceParticleBatch implements ParticleBatch {
    int bufferedParticlesCount;
    Array controllersRenderData;

    public ModelInstanceParticleBatch() {
        super();
        this.controllersRenderData = new Array(false, 5);
    }

    public void begin() {
        this.controllersRenderData.clear();
        this.bufferedParticlesCount = 0;
    }

    public void draw(ModelInstanceControllerRenderData data) {
        this.controllersRenderData.add(data);
        this.bufferedParticlesCount += data.controller.particles.size;
    }

    public void draw(ParticleControllerRenderData x0) {
        this.draw(((ModelInstanceControllerRenderData)x0));
    }

    public void end() {
    }

    public int getBufferedCount() {
        return this.bufferedParticlesCount;
    }

    public void getRenderables(Array arg6, Pool arg7) {
        Iterator v3 = this.controllersRenderData.iterator();
    label_2:
        if(!v3.hasNext()) {
            return;
        }

        Object v1 = v3.next();
        int v2 = 0;
        int v0 = ((ModelInstanceControllerRenderData)v1).controller.particles.size;
        while(true) {
            if(v2 >= v0) {
                goto label_2;
            }

            ((ModelInstanceControllerRenderData)v1).modelInstanceChannel.data[v2].getRenderables(arg6, arg7);
            ++v2;
        }
    }

    public void load(AssetManager manager, ResourceData assetDependencyData) {
    }

    public void save(AssetManager manager, ResourceData assetDependencyData) {
    }
}

