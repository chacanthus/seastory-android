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

public final class CylinderSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public CylinderSpawnShapeValue() {
        super();
    }

    public CylinderSpawnShapeValue(CylinderSpawnShapeValue cylinderSpawnShapeValue) {
        super(((PrimitiveSpawnShapeValue)cylinderSpawnShapeValue));
        this.load(((ParticleValue)cylinderSpawnShapeValue));
    }

    public SpawnShapeValue copy() {
        return new CylinderSpawnShapeValue(this);
    }

    public void spawnAux(Vector3 vector, float percent) {
        int v4;
        int v3;
        float v6;
        float v5;
        float v9 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        float v1 = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        float v0 = this.spawnDepth + this.spawnDepthDiff * this.spawnDepthValue.getScale(percent);
        float v8 = MathUtils.random(v1) - v1 / 2f;
        if(this.edges) {
            v5 = v9 / 2f;
            v6 = v0 / 2f;
        }
        else {
            v5 = MathUtils.random(v9) / 2f;
            v6 = MathUtils.random(v0) / 2f;
        }

        float v7 = 0f;
        if(v5 == 0f) {
            v3 = 1;
        }
        else {
            v3 = 0;
        }

        if(v6 == 0f) {
            v4 = 1;
        }
        else {
            v4 = 0;
        }

        if(v3 == 0 && v4 == 0) {
            v7 = MathUtils.random(360f);
        }
        else if(v3 != 0) {
            if(MathUtils.random(1) == 0) {
                v7 = -90f;
            }
            else {
                v7 = 90f;
            }
        }
        else if(v4 != 0) {
            if(MathUtils.random(1) == 0) {
                v7 = 0f;
            }
            else {
                v7 = 180f;
            }
        }

        vector.set(MathUtils.cosDeg(v7) * v5, v8, MathUtils.sinDeg(v7) * v6);
    }
}

