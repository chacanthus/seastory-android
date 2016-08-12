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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.graphics.g3d.particles.ParticleControllerComponent;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.SpawnShapeValue;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SpawnInfluencer extends Influencer {
    FloatChannel positionChannel;
    public SpawnShapeValue spawnShapeValue;

    public SpawnInfluencer() {
        super();
        this.spawnShapeValue = new PointSpawnShapeValue();
    }

    public SpawnInfluencer(SpawnInfluencer source) {
        super();
        this.spawnShapeValue = source.spawnShapeValue.copy();
    }

    public SpawnInfluencer(SpawnShapeValue spawnShapeValue) {
        super();
        this.spawnShapeValue = spawnShapeValue;
    }

    public void activateParticles(int startIndex, int count) {
        int v1 = startIndex * this.positionChannel.strideSize;
        int v0 = v1 + this.positionChannel.strideSize * count;
        while(v1 < v0) {
            this.spawnShapeValue.spawn(SpawnInfluencer.TMP_V1, this.controller.emitter.percent);
            SpawnInfluencer.TMP_V1.mul(this.controller.transform);
            this.positionChannel.data[v1] = SpawnInfluencer.TMP_V1.x;
            this.positionChannel.data[v1 + 1] = SpawnInfluencer.TMP_V1.y;
            this.positionChannel.data[v1 + 2] = SpawnInfluencer.TMP_V1.z;
            v1 += this.positionChannel.strideSize;
        }
    }

    public void allocateChannels() {
        this.positionChannel = this.controller.particles.addChannel(ParticleChannels.Position);
    }

    public ParticleControllerComponent copy() {
        return this.copy();
    }

    public SpawnInfluencer copy() {
        return new SpawnInfluencer(this);
    }

    public void init() {
        this.spawnShapeValue.init();
    }

    public void load(AssetManager manager, ResourceData data) {
        this.spawnShapeValue.load(manager, data);
    }

    public void read(Json json, JsonValue jsonData) {
        this.spawnShapeValue = json.readValue("spawnShape", SpawnShapeValue.class, jsonData);
    }

    public void save(AssetManager manager, ResourceData data) {
        this.spawnShapeValue.save(manager, data);
    }

    public void start() {
        this.spawnShapeValue.start();
    }

    public void write(Json json) {
        json.writeValue("spawnShape", this.spawnShapeValue, SpawnShapeValue.class);
    }
}

