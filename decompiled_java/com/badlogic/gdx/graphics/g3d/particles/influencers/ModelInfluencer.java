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
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$ObjectChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$SaveData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public abstract class ModelInfluencer extends Influencer {
    public class Random extends ModelInfluencer {
        class ModelInstancePool extends Pool {
            public ModelInstancePool(Random arg1) {
                Random.this = arg1;
                super();
            }

            public ModelInstance newObject() {
                return new ModelInstance(Random.this.models.random());
            }

            public Object newObject() {
                return this.newObject();
            }
        }

        ModelInstancePool pool;

        public Random() {
            this();
            this.pool = new ModelInstancePool(this);
        }

        public Random(Random influencer) {
            this(((ModelInfluencer)influencer));
            this.pool = new ModelInstancePool(this);
        }

        public Random(Model[] models) {
            this(models);
            this.pool = new ModelInstancePool(this);
        }

        public void activateParticles(int startIndex, int count) {
            int v1 = startIndex;
            int v0 = startIndex + count;
            while(v1 < v0) {
                this.modelChannel.data[v1] = this.pool.obtain();
                ++v1;
            }
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Random copy() {
            return new Random(this);
        }

        public void init() {
            this.pool.clear();
        }

        public void killParticles(int startIndex, int count) {
            int v1 = startIndex;
            int v0 = startIndex + count;
            while(v1 < v0) {
                this.pool.free(this.modelChannel.data[v1]);
                this.modelChannel.data[v1] = null;
                ++v1;
            }
        }
    }

    public class Single extends ModelInfluencer {
        public Single() {
            this();
        }

        public Single(Single influencer) {
            this(((ModelInfluencer)influencer));
        }

        public Single(Model[] models) {
            this(models);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Single copy() {
            return new Single(this);
        }

        public void init() {
            Object v1 = this.models.first();
            int v2 = 0;
            int v0 = this.controller.emitter.maxParticleCount;
            while(v2 < v0) {
                this.modelChannel.data[v2] = new ModelInstance(((Model)v1));
                ++v2;
            }
        }
    }

    ObjectChannel modelChannel;
    public Array models;

    public ModelInfluencer() {
        super();
        this.models = new Array(true, 1, Model.class);
    }

    public ModelInfluencer(ModelInfluencer influencer) {
        this(influencer.models.toArray(Model.class));
    }

    public ModelInfluencer(Model[] models) {
        super();
        this.models = new Array(((Object[])models));
    }

    public void allocateChannels() {
        this.modelChannel = this.controller.particles.addChannel(ParticleChannels.ModelInstance);
    }

    public void load(AssetManager manager, ResourceData resources) {
        SaveData v0 = resources.getSaveData();
        while(true) {
            AssetDescriptor v1 = v0.loadAsset();
            if(v1 == null) {
                return;
            }

            Object v2 = manager.get(v1);
            if(v2 == null) {
                throw new RuntimeException("Model is null");
            }

            this.models.add(v2);
        }
    }

    public void save(AssetManager manager, ResourceData resources) {
        SaveData v0 = resources.createSaveData();
        Iterator v1 = this.models.iterator();
        while(v1.hasNext()) {
            v0.saveAsset(manager.getAssetFileName(v1.next()), Model.class);
        }
    }
}

