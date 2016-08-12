// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public final class ParticleSystem implements RenderableProvider {
    private Array batches;
    private Array effects;
    private static ParticleSystem instance;

    private ParticleSystem() {
        super();
        this.batches = new Array();
        this.effects = new Array();
    }

    public void add(ParticleEffect effect) {
        this.effects.add(effect);
    }

    public void add(ParticleBatch arg2) {
        this.batches.add(arg2);
    }

    public void begin() {
        Iterator v1 = this.batches.iterator();
        while(v1.hasNext()) {
            v1.next().begin();
        }
    }

    public void draw() {
        Iterator v1 = this.effects.iterator();
        while(v1.hasNext()) {
            v1.next().draw();
        }
    }

    public void end() {
        Iterator v1 = this.batches.iterator();
        while(v1.hasNext()) {
            v1.next().end();
        }
    }

    public static ParticleSystem get() {
        if(ParticleSystem.instance == null) {
            ParticleSystem.instance = new ParticleSystem();
        }

        return ParticleSystem.instance;
    }

    public Array getBatches() {
        return this.batches;
    }

    public void getRenderables(Array arg4, Pool arg5) {
        Iterator v1 = this.batches.iterator();
        while(v1.hasNext()) {
            v1.next().getRenderables(arg4, arg5);
        }
    }

    public void remove(ParticleEffect effect) {
        this.effects.removeValue(effect, true);
    }

    public void removeAll() {
        this.effects.clear();
    }

    public void update() {
        Iterator v1 = this.effects.iterator();
        while(v1.hasNext()) {
            v1.next().update();
        }
    }

    public void updateAndDraw() {
        Iterator v1 = this.effects.iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            ((ParticleEffect)v0).update();
            ((ParticleEffect)v0).draw();
        }
    }
}

