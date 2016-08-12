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

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public abstract class PrimitiveSpawnShapeValue extends SpawnShapeValue {
    public enum SpawnSide {
        public static final enum SpawnSide both;
        public static final enum SpawnSide bottom;
        public static final enum SpawnSide top;

        static  {
            SpawnSide.both = new SpawnSide("both", 0);
            SpawnSide.top = new SpawnSide("top", 1);
            SpawnSide.bottom = new SpawnSide("bottom", 2);
            SpawnSide[] v0 = new SpawnSide[3];
            v0[0] = SpawnSide.both;
            v0[1] = SpawnSide.top;
            v0[2] = SpawnSide.bottom;
            SpawnSide.$VALUES = v0;
        }

        private SpawnSide(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static SpawnSide valueOf(String name) {
            return Enum.valueOf(SpawnSide.class, name);
        }

        public static SpawnSide[] values() {
            return SpawnSide.$VALUES.clone();
        }
    }

    protected static final Vector3 TMP_V1;
    boolean edges;
    protected float spawnDepth;
    protected float spawnDepthDiff;
    public ScaledNumericValue spawnDepthValue;
    protected float spawnHeight;
    protected float spawnHeightDiff;
    public ScaledNumericValue spawnHeightValue;
    protected float spawnWidth;
    protected float spawnWidthDiff;
    public ScaledNumericValue spawnWidthValue;

    static  {
        PrimitiveSpawnShapeValue.TMP_V1 = new Vector3();
    }

    public PrimitiveSpawnShapeValue() {
        super();
        this.edges = false;
        this.spawnWidthValue = new ScaledNumericValue();
        this.spawnHeightValue = new ScaledNumericValue();
        this.spawnDepthValue = new ScaledNumericValue();
    }

    public PrimitiveSpawnShapeValue(PrimitiveSpawnShapeValue value) {
        super(((SpawnShapeValue)value));
        this.edges = false;
        this.spawnWidthValue = new ScaledNumericValue();
        this.spawnHeightValue = new ScaledNumericValue();
        this.spawnDepthValue = new ScaledNumericValue();
    }

    public ScaledNumericValue getSpawnDepth() {
        return this.spawnDepthValue;
    }

    public ScaledNumericValue getSpawnHeight() {
        return this.spawnHeightValue;
    }

    public ScaledNumericValue getSpawnWidth() {
        return this.spawnWidthValue;
    }

    public boolean isEdges() {
        return this.edges;
    }

    public void load(ParticleValue value) {
        super.load(value);
        this.edges = value.edges;
        this.spawnWidthValue.load(value.spawnWidthValue);
        this.spawnHeightValue.load(value.spawnHeightValue);
        this.spawnDepthValue.load(value.spawnDepthValue);
    }

    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        this.spawnWidthValue = json.readValue("spawnWidthValue", ScaledNumericValue.class, jsonData);
        this.spawnHeightValue = json.readValue("spawnHeightValue", ScaledNumericValue.class, jsonData);
        this.spawnDepthValue = json.readValue("spawnDepthValue", ScaledNumericValue.class, jsonData);
        this.edges = json.readValue("edges", Boolean.TYPE, jsonData).booleanValue();
    }

    public void setActive(boolean active) {
        super.setActive(active);
        this.spawnWidthValue.setActive(true);
        this.spawnHeightValue.setActive(true);
        this.spawnDepthValue.setActive(true);
    }

    public void setDimensions(float width, float height, float depth) {
        this.spawnWidthValue.setHigh(width);
        this.spawnHeightValue.setHigh(height);
        this.spawnDepthValue.setHigh(depth);
    }

    public void setEdges(boolean edges) {
        this.edges = edges;
    }

    public void start() {
        this.spawnWidth = this.spawnWidthValue.newLowValue();
        this.spawnWidthDiff = this.spawnWidthValue.newHighValue();
        if(!this.spawnWidthValue.isRelative()) {
            this.spawnWidthDiff -= this.spawnWidth;
        }

        this.spawnHeight = this.spawnHeightValue.newLowValue();
        this.spawnHeightDiff = this.spawnHeightValue.newHighValue();
        if(!this.spawnHeightValue.isRelative()) {
            this.spawnHeightDiff -= this.spawnHeight;
        }

        this.spawnDepth = this.spawnDepthValue.newLowValue();
        this.spawnDepthDiff = this.spawnDepthValue.newHighValue();
        if(!this.spawnDepthValue.isRelative()) {
            this.spawnDepthDiff -= this.spawnDepth;
        }
    }

    public void write(Json json) {
        super.write(json);
        json.writeValue("spawnWidthValue", this.spawnWidthValue);
        json.writeValue("spawnHeightValue", this.spawnHeightValue);
        json.writeValue("spawnDepthValue", this.spawnDepthValue);
        json.writeValue("edges", Boolean.valueOf(this.edges));
    }
}

