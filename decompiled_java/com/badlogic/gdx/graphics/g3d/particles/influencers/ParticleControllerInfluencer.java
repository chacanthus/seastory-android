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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$ObjectChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$SaveData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public abstract class ParticleControllerInfluencer extends Influencer {
    public class Random extends ParticleControllerInfluencer {
        class ParticleControllerPool extends Pool {
            public ParticleControllerPool(Random arg1) {
                Random.this = arg1;
                super();
            }

            public void clear() {
                int v1 = 0;
                int v0 = Random.this.pool.getFree();
                while(v1 < v0) {
                    Random.this.pool.obtain().dispose();
                    ++v1;
                }

                super.clear();
            }

            public ParticleController newObject() {
                ParticleController v0 = Random.this.templates.random().copy();
                v0.init();
                return v0;
            }

            public Object newObject() {
                return this.newObject();
            }
        }

        ParticleControllerPool pool;

        public Random() {
            this();
            this.pool = new ParticleControllerPool(this);
        }

        public Random(Random particleControllerRandom) {
            this(((ParticleControllerInfluencer)particleControllerRandom));
            this.pool = new ParticleControllerPool(this);
        }

        public Random(ParticleController[] templates) {
            this(templates);
            this.pool = new ParticleControllerPool(this);
        }

        public void activateParticles(int startIndex, int count) {
            int v2 = startIndex;
            int v0 = startIndex + count;
            while(v2 < v0) {
                Object v1 = this.pool.obtain();
                ((ParticleController)v1).start();
                this.particleControllerChannel.data[v2] = v1;
                ++v2;
            }
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Random copy() {
            return new Random(this);
        }

        public void dispose() {
            this.pool.clear();
            super.dispose();
        }

        public void init() {
            this.pool.clear();
            int v0;
            for(v0 = 0; v0 < this.controller.emitter.maxParticleCount; ++v0) {
                this.pool.free(this.pool.newObject());
            }
        }

        public void killParticles(int startIndex, int count) {
            int v2 = startIndex;
            int v0 = startIndex + count;
            while(v2 < v0) {
                Object v1 = this.particleControllerChannel.data[v2];
                ((ParticleController)v1).end();
                this.pool.free(v1);
                this.particleControllerChannel.data[v2] = null;
                ++v2;
            }
        }
    }

    public class Single extends ParticleControllerInfluencer {
        public Single() {
            this();
        }

        public Single(Single particleControllerSingle) {
            this(((ParticleControllerInfluencer)particleControllerSingle));
        }

        public Single(ParticleController[] templates) {
            this(templates);
        }

        public void activateParticles(int startIndex, int count) {
            int v1 = startIndex;
            int v0 = startIndex + count;
            while(v1 < v0) {
                this.particleControllerChannel.data[v1].start();
                ++v1;
            }
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Single copy() {
            return new Single(this);
        }

        public void init() {
            Object v2 = this.templates.first();
            int v3 = 0;
            int v0 = this.controller.particles.capacity;
            while(v3 < v0) {
                ParticleController v1 = ((ParticleController)v2).copy();
                v1.init();
                this.particleControllerChannel.data[v3] = v1;
                ++v3;
            }
        }

        public void killParticles(int startIndex, int count) {
            int v1 = startIndex;
            int v0 = startIndex + count;
            while(v1 < v0) {
                this.particleControllerChannel.data[v1].end();
                ++v1;
            }
        }
    }

    ObjectChannel particleControllerChannel;
    public Array templates;

    public ParticleControllerInfluencer() {
        super();
        this.templates = new Array(true, 1, ParticleController.class);
    }

    public ParticleControllerInfluencer(ParticleControllerInfluencer influencer) {
        this(influencer.templates.items);
    }

    public ParticleControllerInfluencer(ParticleController[] templates) {
        super();
        this.templates = new Array(((Object[])templates));
    }

    public void allocateChannels() {
        this.particleControllerChannel = this.controller.particles.addChannel(ParticleChannels.ParticleController);
    }

    public void dispose() {
        if(this.controller != null) {
            int v1;
            for(v1 = 0; v1 < this.controller.particles.size; ++v1) {
                Object v0 = this.particleControllerChannel.data[v1];
                if(v0 != null) {
                    ((ParticleController)v0).dispose();
                    this.particleControllerChannel.data[v1] = null;
                }
            }
        }
    }

    public void end() {
        int v0;
        for(v0 = 0; v0 < this.controller.particles.size; ++v0) {
            this.particleControllerChannel.data[v0].end();
        }
    }

    public void load(AssetManager manager, ResourceData resources) {
        SaveData v0 = resources.getSaveData();
        Iterator v7 = v0.load("indices").iterator();
    label_4:
        AssetDescriptor v1 = v0.loadAsset();
        if(v1 == null) {
            return;
        }

        Object v2 = manager.get(v1);
        if(v2 == null) {
            throw new RuntimeException("Template is null");
        }

        Array v3 = ((ParticleEffect)v2).getControllers();
        Object v4 = v7.next();
        int v6 = 0;
        int v8 = ((IntArray)v4).size;
        while(true) {
            if(v6 >= v8) {
                goto label_4;
            }

            this.templates.add(v3.get(((IntArray)v4).get(v6)));
            ++v6;
        }
    }

    public void save(AssetManager manager, ResourceData resources) {
        SaveData v2 = resources.createSaveData();
        Array v5 = manager.getAll(ParticleEffect.class, new Array());
        Array v1 = new Array(this.templates);
        Array v6 = new Array();
        int v7 = 0;
        while(v7 < v5.size) {
            if(v1.size <= 0) {
                break;
            }

            Object v3 = v5.get(v7);
            Array v4 = ((ParticleEffect)v3).getControllers();
            Iterator v10 = v1.iterator();
            IntArray v9 = null;
            while(v10.hasNext()) {
                int v8 = v4.indexOf(v10.next(), true);
                if(v8 <= -1) {
                    continue;
                }

                if(v9 == null) {
                    v9 = new IntArray();
                }

                v10.remove();
                v9.add(v8);
            }

            if(v9 != null) {
                v2.saveAsset(manager.getAssetFileName(v3), ParticleEffect.class);
                v6.add(v9);
            }

            ++v7;
        }

        v2.save("indices", v6);
    }
}

