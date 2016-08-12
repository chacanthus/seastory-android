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

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public final class EllipseSpawnShapeValue extends PrimitiveSpawnShapeValue {
    SpawnSide side;

    public EllipseSpawnShapeValue() {
        super();
        this.side = SpawnSide.both;
    }

    public EllipseSpawnShapeValue(EllipseSpawnShapeValue value) {
        super(((PrimitiveSpawnShapeValue)value));
        this.side = SpawnSide.both;
        this.load(((ParticleValue)value));
    }

    public SpawnShapeValue copy() {
        return new EllipseSpawnShapeValue(this);
    }

    public SpawnSide getSide() {
        return this.side;
    }

    public void load(ParticleValue value) {
        super.load(value);
        this.side = value.side;
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.side = json.readValue("side", SpawnSide.class, jsonData);
    }

    public void setSide(SpawnSide side) {
        this.side = side;
    }

    public void spawnAux(Vector3 vector, float percent) {
        float v9;
        float v8;
        float v7;
        float v11 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        float v3 = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        float v2 = this.spawnDepth + this.spawnDepthDiff * this.spawnDepthValue.getScale(percent);
        float v4 = 6.283185f;
        if(this.side == SpawnSide.top) {
            v4 = 3.141593f;
        }
        else if(this.side == SpawnSide.bottom) {
            v4 = -3.141593f;
        }

        float v10 = MathUtils.random(0f, v4);
        if(!this.edges) {
            v7 = MathUtils.random(v11 / 2f);
            v8 = MathUtils.random(v3 / 2f);
            v9 = MathUtils.random(v2 / 2f);
        label_95:
            float v12 = MathUtils.random(-1f, 1f);
            float v6 = ((float)Math.sqrt(((double)(1f - v12 * v12))));
            vector.set(v7 * v6 * MathUtils.cos(v10), v8 * v6 * MathUtils.sin(v10), v9 * v12);
        }
        else if(v11 == 0f) {
            vector.set(0f, v3 / 2f * MathUtils.sin(v10), v2 / 2f * MathUtils.cos(v10));
        }
        else if(v3 == 0f) {
            vector.set(v11 / 2f * MathUtils.cos(v10), 0f, v2 / 2f * MathUtils.sin(v10));
        }
        else if(v2 == 0f) {
            vector.set(v11 / 2f * MathUtils.cos(v10), v3 / 2f * MathUtils.sin(v10), 0f);
        }
        else {
            v7 = v11 / 2f;
            v8 = v3 / 2f;
            v9 = v2 / 2f;
            goto label_95;
        }
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("side", this.side);
    }
}

