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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class RegionInfluencer extends Influencer {
    public class Animated extends RegionInfluencer {
        FloatChannel lifeChannel;

        public Animated() {
            this();
        }

        public Animated(Texture texture) {
            this(texture);
        }

        public Animated(TextureRegion textureRegion) {
            TextureRegion[] v0 = new TextureRegion[1];
            v0[0] = textureRegion;
            this(v0);
        }

        public Animated(Animated regionInfluencer) {
            this(((RegionInfluencer)regionInfluencer));
        }

        public void allocateChannels() {
            super.allocateChannels();
            this.lifeChannel = this.controller.particles.addChannel(ParticleChannels.Life);
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Animated copy() {
            return new Animated(this);
        }

        public void update() {
            int v1 = 0;
            int v2 = 2;
            int v0 = this.controller.particles.size * this.regionChannel.strideSize;
            while(v1 < v0) {
                Object v3 = this.regions.get(((int)(this.lifeChannel.data[v2] * (((float)(this.regions.size - 1))))));
                this.regionChannel.data[v1] = ((AspectTextureRegion)v3).u;
                this.regionChannel.data[v1 + 1] = ((AspectTextureRegion)v3).v;
                this.regionChannel.data[v1 + 2] = ((AspectTextureRegion)v3).u2;
                this.regionChannel.data[v1 + 3] = ((AspectTextureRegion)v3).v2;
                this.regionChannel.data[v1 + 4] = 0.5f;
                this.regionChannel.data[v1 + 5] = ((AspectTextureRegion)v3).halfInvAspectRatio;
                v1 += this.regionChannel.strideSize;
                v2 += this.lifeChannel.strideSize;
            }
        }
    }

    public class AspectTextureRegion {
        public float halfInvAspectRatio;
        public float u;
        public float u2;
        public float v;
        public float v2;

        public AspectTextureRegion() {
            super();
        }

        public AspectTextureRegion(AspectTextureRegion aspectTextureRegion) {
            super();
            this.set(aspectTextureRegion);
        }

        public AspectTextureRegion(TextureRegion region) {
            super();
            this.set(region);
        }

        public void set(TextureRegion region) {
            this.u = region.getU();
            this.v = region.getV();
            this.u2 = region.getU2();
            this.v2 = region.getV2();
            this.halfInvAspectRatio = 0.5f * ((((float)region.getRegionHeight())) / (((float)region.getRegionWidth())));
        }

        public void set(AspectTextureRegion aspectTextureRegion) {
            this.u = aspectTextureRegion.u;
            this.v = aspectTextureRegion.v;
            this.u2 = aspectTextureRegion.u2;
            this.v2 = aspectTextureRegion.v2;
            this.halfInvAspectRatio = aspectTextureRegion.halfInvAspectRatio;
        }
    }

    public class Random extends RegionInfluencer {
        public Random() {
            this();
        }

        public Random(Texture texture) {
            this(texture);
        }

        public Random(TextureRegion textureRegion) {
            TextureRegion[] v0 = new TextureRegion[1];
            v0[0] = textureRegion;
            this(v0);
        }

        public Random(Random regionInfluencer) {
            this(((RegionInfluencer)regionInfluencer));
        }

        public void activateParticles(int startIndex, int count) {
            int v1 = startIndex * this.regionChannel.strideSize;
            int v0 = v1 + this.regionChannel.strideSize * count;
            while(v1 < v0) {
                Object v2 = this.regions.random();
                this.regionChannel.data[v1] = ((AspectTextureRegion)v2).u;
                this.regionChannel.data[v1 + 1] = ((AspectTextureRegion)v2).v;
                this.regionChannel.data[v1 + 2] = ((AspectTextureRegion)v2).u2;
                this.regionChannel.data[v1 + 3] = ((AspectTextureRegion)v2).v2;
                this.regionChannel.data[v1 + 4] = 0.5f;
                this.regionChannel.data[v1 + 5] = ((AspectTextureRegion)v2).halfInvAspectRatio;
                v1 += this.regionChannel.strideSize;
            }
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Random copy() {
            return new Random(this);
        }
    }

    public class Single extends RegionInfluencer {
        public Single() {
            this();
        }

        public Single(Texture texture) {
            this(texture);
        }

        public Single(TextureRegion textureRegion) {
            TextureRegion[] v0 = new TextureRegion[1];
            v0[0] = textureRegion;
            this(v0);
        }

        public Single(Single regionInfluencer) {
            this(((RegionInfluencer)regionInfluencer));
        }

        public ParticleControllerComponent copy() {
            return this.copy();
        }

        public Single copy() {
            return new Single(this);
        }

        public void init() {
            Object v2 = this.regions.items[0];
            int v1 = 0;
            int v0 = this.controller.emitter.maxParticleCount * this.regionChannel.strideSize;
            while(v1 < v0) {
                this.regionChannel.data[v1] = ((AspectTextureRegion)v2).u;
                this.regionChannel.data[v1 + 1] = ((AspectTextureRegion)v2).v;
                this.regionChannel.data[v1 + 2] = ((AspectTextureRegion)v2).u2;
                this.regionChannel.data[v1 + 3] = ((AspectTextureRegion)v2).v2;
                this.regionChannel.data[v1 + 4] = 0.5f;
                this.regionChannel.data[v1 + 5] = ((AspectTextureRegion)v2).halfInvAspectRatio;
                v1 += this.regionChannel.strideSize;
            }
        }
    }

    FloatChannel regionChannel;
    public Array regions;

    public RegionInfluencer() {
        this(1);
        AspectTextureRegion v0 = new AspectTextureRegion();
        v0.v = 0f;
        v0.u = 0f;
        v0.v2 = 1f;
        v0.u2 = 1f;
        v0.halfInvAspectRatio = 0.5f;
        this.regions.add(v0);
    }

    public RegionInfluencer(int regionsCount) {
        super();
        this.regions = new Array(false, regionsCount, AspectTextureRegion.class);
    }

    public RegionInfluencer(Texture texture) {
        TextureRegion[] v0 = new TextureRegion[1];
        v0[0] = new TextureRegion(texture);
        this(v0);
    }

    public RegionInfluencer(TextureRegion[] regions) {
        super();
        this.regions = new Array(false, regions.length, AspectTextureRegion.class);
        this.add(regions);
    }

    public RegionInfluencer(RegionInfluencer regionInfluencer) {
        this(regionInfluencer.regions.size);
        this.regions.ensureCapacity(regionInfluencer.regions.size);
        int v0;
        for(v0 = 0; v0 < regionInfluencer.regions.size; ++v0) {
            this.regions.add(new AspectTextureRegion(regionInfluencer.regions.get(v0)));
        }
    }

    public void add(TextureRegion[] regions) {
        this.regions.ensureCapacity(regions.length);
        TextureRegion[] v0 = regions;
        int v2 = v0.length;
        int v1;
        for(v1 = 0; v1 < v2; ++v1) {
            this.regions.add(new AspectTextureRegion(v0[v1]));
        }
    }

    public void allocateChannels() {
        this.regionChannel = this.controller.particles.addChannel(ParticleChannels.TextureRegion);
    }

    public void clear() {
        this.regions.clear();
    }

    public void read(Json json, JsonValue jsonData) {
        this.regions.clear();
        this.regions.addAll(json.readValue("regions", Array.class, AspectTextureRegion.class, jsonData));
    }

    public void write(Json json) {
        json.writeValue("regions", this.regions, Array.class, AspectTextureRegion.class);
    }
}

