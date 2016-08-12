// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$Configurable;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json$Serializable;
import com.badlogic.gdx.utils.JsonValue;

public abstract class SpawnShapeValue extends ParticleValue implements Configurable, Serializable {
    public RangedNumericValue xOffsetValue;
    public RangedNumericValue yOffsetValue;
    public RangedNumericValue zOffsetValue;

    public SpawnShapeValue() {
        super();
        this.xOffsetValue = new RangedNumericValue();
        this.yOffsetValue = new RangedNumericValue();
        this.zOffsetValue = new RangedNumericValue();
    }

    public SpawnShapeValue(SpawnShapeValue spawnShapeValue) {
        this();
    }

    public abstract SpawnShapeValue copy();

    public void init() {
    }

    public void load(ParticleValue value) {
        super.load(value);
        this.xOffsetValue.load(value.xOffsetValue);
        this.yOffsetValue.load(value.yOffsetValue);
        this.zOffsetValue.load(value.zOffsetValue);
    }

    public void load(AssetManager manager, ResourceData data) {
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.xOffsetValue = json.readValue("xOffsetValue", RangedNumericValue.class, jsonData);
        this.yOffsetValue = json.readValue("yOffsetValue", RangedNumericValue.class, jsonData);
        this.zOffsetValue = json.readValue("zOffsetValue", RangedNumericValue.class, jsonData);
    }

    public void save(AssetManager manager, ResourceData data) {
    }

    public final Vector3 spawn(Vector3 vector, float percent) {
        this.spawnAux(vector, percent);
        if(this.xOffsetValue.active) {
            vector.x += this.xOffsetValue.newLowValue();
        }

        if(this.yOffsetValue.active) {
            vector.y += this.yOffsetValue.newLowValue();
        }

        if(this.zOffsetValue.active) {
            vector.z += this.zOffsetValue.newLowValue();
        }

        return vector;
    }

    public abstract void spawnAux(Vector3 arg0, float arg1);

    public void start() {
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("xOffsetValue", this.xOffsetValue);
        json.writeValue("yOffsetValue", this.yOffsetValue);
        json.writeValue("zOffsetValue", this.zOffsetValue);
    }
}

