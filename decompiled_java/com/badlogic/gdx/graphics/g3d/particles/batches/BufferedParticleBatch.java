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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter$Distance;
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderData;
import com.badlogic.gdx.utils.Array;

public abstract class BufferedParticleBatch implements ParticleBatch {
    protected int bufferedParticlesCount;
    protected Camera camera;
    protected int currentCapacity;
    protected Array renderData;
    protected ParticleSorter sorter;

    protected BufferedParticleBatch(Class arg4) {
        super();
        this.currentCapacity = 0;
        this.sorter = new Distance();
        this.renderData = new Array(false, 10, arg4);
    }

    protected abstract void allocParticlesData(int arg0);

    public void begin() {
        this.renderData.clear();
        this.bufferedParticlesCount = 0;
    }

    public void draw(ParticleControllerRenderData arg3) {
        if(arg3.controller.particles.size > 0) {
            this.renderData.add(arg3);
            this.bufferedParticlesCount += arg3.controller.particles.size;
        }
    }

    public void end() {
        if(this.bufferedParticlesCount > 0) {
            this.ensureCapacity(this.bufferedParticlesCount);
            this.flush(this.sorter.sort(this.renderData));
        }
    }

    public void ensureCapacity(int capacity) {
        if(this.currentCapacity < capacity) {
            this.sorter.ensureCapacity(capacity);
            this.allocParticlesData(capacity);
            this.currentCapacity = capacity;
        }
    }

    protected abstract void flush(int[] arg0);

    public int getBufferedCount() {
        return this.bufferedParticlesCount;
    }

    public ParticleSorter getSorter() {
        return this.sorter;
    }

    public void resetCapacity() {
        this.bufferedParticlesCount = 0;
        this.currentCapacity = 0;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        this.sorter.setCamera(camera);
    }

    public void setSorter(ParticleSorter sorter) {
        this.sorter = sorter;
        sorter.setCamera(this.camera);
        sorter.ensureCapacity(this.currentCapacity);
    }
}

