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

public final class RectangleSpawnShapeValue extends PrimitiveSpawnShapeValue {
    public RectangleSpawnShapeValue() {
        super();
    }

    public RectangleSpawnShapeValue(RectangleSpawnShapeValue value) {
        super(((PrimitiveSpawnShapeValue)value));
        this.load(((ParticleValue)value));
    }

    public SpawnShapeValue copy() {
        return new RectangleSpawnShapeValue(this);
    }

    public void spawnAux(Vector3 vector, float percent) {
        float v5;
        float v4;
        float v3;
        float v6 = this.spawnWidth + this.spawnWidthDiff * this.spawnWidthValue.getScale(percent);
        float v2 = this.spawnHeight + this.spawnHeightDiff * this.spawnHeightValue.getScale(percent);
        float v1 = this.spawnDepth + this.spawnDepthDiff * this.spawnDepthValue.getScale(percent);
        if(this.edges) {
            int v0 = MathUtils.random(-1, 1);
            if(v0 == -1) {
                if(MathUtils.random(1) == 0) {
                    v3 = -v6 / 2f;
                }
                else {
                    v3 = v6 / 2f;
                }

                if(v3 != 0f) {
                    goto label_61;
                }

                if(MathUtils.random(1) == 0) {
                    v4 = -v2 / 2f;
                }
                else {
                    v4 = v2 / 2f;
                }

                if(MathUtils.random(1) != 0) {
                    goto label_58;
                }

                v5 = -v1 / 2f;
                goto label_48;
            label_58:
                v5 = v1 / 2f;
                goto label_48;
            label_61:
                v4 = MathUtils.random(v2) - v2 / 2f;
                v5 = MathUtils.random(v1) - v1 / 2f;
            }
            else {
                if(v0 != 0) {
                    goto label_110;
                }

                if(MathUtils.random(1) == 0) {
                    v5 = -v1 / 2f;
                }
                else {
                    v5 = v1 / 2f;
                }

                if(v5 != 0f) {
                    goto label_101;
                }

                if(MathUtils.random(1) == 0) {
                    v4 = -v2 / 2f;
                }
                else {
                    v4 = v2 / 2f;
                }

                if(MathUtils.random(1) != 0) {
                    goto label_98;
                }

                v3 = -v6 / 2f;
                goto label_48;
            label_98:
                v3 = v6 / 2f;
                goto label_48;
            label_101:
                v4 = MathUtils.random(v2) - v2 / 2f;
                v3 = MathUtils.random(v6) - v6 / 2f;
                goto label_48;
            label_110:
                if(MathUtils.random(1) == 0) {
                    v4 = -v2 / 2f;
                }
                else {
                    v4 = v2 / 2f;
                }

                if(v4 != 0f) {
                    goto label_140;
                }

                if(MathUtils.random(1) == 0) {
                    v3 = -v6 / 2f;
                }
                else {
                    v3 = v6 / 2f;
                }

                if(MathUtils.random(1) != 0) {
                    goto label_137;
                }

                v5 = -v1 / 2f;
                goto label_48;
            label_137:
                v5 = v1 / 2f;
                goto label_48;
            label_140:
                v3 = MathUtils.random(v6) - v6 / 2f;
                v5 = MathUtils.random(v1) - v1 / 2f;
            }

        label_48:
            vector.x = v3;
            vector.y = v4;
            vector.z = v5;
        }
        else {
            vector.x = MathUtils.random(v6) - v6 / 2f;
            vector.y = MathUtils.random(v2) - v2 / 2f;
            vector.z = MathUtils.random(v1) - v1 / 2f;
        }
    }
}

