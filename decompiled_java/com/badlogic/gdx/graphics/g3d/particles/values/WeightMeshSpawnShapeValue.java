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

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.CumulativeDistribution;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class WeightMeshSpawnShapeValue extends MeshSpawnShapeValue {
    private CumulativeDistribution distribution;

    public WeightMeshSpawnShapeValue() {
        super();
        this.distribution = new CumulativeDistribution();
    }

    public WeightMeshSpawnShapeValue(WeightMeshSpawnShapeValue value) {
        super(((MeshSpawnShapeValue)value));
        this.distribution = new CumulativeDistribution();
        this.load(((ParticleValue)value));
    }

    public void calculateWeights() {
        int v18;
        int v17;
        int v16;
        this.distribution.clear();
        VertexAttributes v12 = this.mesh.getVertexAttributes();
        int v15 = this.mesh.getNumIndices();
        int v20 = this.mesh.getNumVertices();
        int v21 = ((short)(v12.vertexSize / 4));
        int v19 = ((short)(v12.findByUsage(1).offset / 4));
        float[] v22 = new float[v20 * v21];
        this.mesh.getVertices(v22);
        if(v15 > 0) {
            short[] v14 = new short[v15];
            this.mesh.getIndices(v14);
            int v13;
            for(v13 = 0; v13 < v15; v13 += 3) {
                v16 = v14[v13] * v21 + v19;
                v17 = v14[v13 + 1] * v21 + v19;
                v18 = v14[v13 + 2] * v21 + v19;
                this.distribution.add(new Triangle(v22[v16], v22[v16 + 1], v22[v16 + 2], v22[v17], v22[v17 + 1], v22[v17 + 2], v22[v18], v22[v18 + 1], v22[v18 + 2]), Math.abs(((v22[v17 + 1] - v22[v18 + 1]) * v22[v16] + (v22[v18 + 1] - v22[v16 + 1]) * v22[v17] + (v22[v16 + 1] - v22[v17 + 1]) * v22[v18]) / 2f));
            }
        }
        else {
            for(v13 = 0; v13 < v20; v13 += v21) {
                v16 = v13 + v19;
                v17 = v16 + v21;
                v18 = v17 + v21;
                this.distribution.add(new Triangle(v22[v16], v22[v16 + 1], v22[v16 + 2], v22[v17], v22[v17 + 1], v22[v17 + 2], v22[v18], v22[v18 + 1], v22[v18 + 2]), Math.abs(((v22[v17 + 1] - v22[v18 + 1]) * v22[v16] + (v22[v18 + 1] - v22[v16 + 1]) * v22[v17] + (v22[v16 + 1] - v22[v17 + 1]) * v22[v18]) / 2f));
            }
        }

        this.distribution.generateNormalized();
    }

    public SpawnShapeValue copy() {
        return new WeightMeshSpawnShapeValue(this);
    }

    public void init() {
        this.calculateWeights();
    }

    public void spawnAux(Vector3 vector, float percent) {
        Object v2 = this.distribution.value();
        float v0 = MathUtils.random();
        float v1 = MathUtils.random();
        vector.set(((Triangle)v2).x1 + (((Triangle)v2).x2 - ((Triangle)v2).x1) * v0 + (((Triangle)v2).x3 - ((Triangle)v2).x1) * v1, ((Triangle)v2).y1 + (((Triangle)v2).y2 - ((Triangle)v2).y1) * v0 + (((Triangle)v2).y3 - ((Triangle)v2).y1) * v1, ((Triangle)v2).z1 + (((Triangle)v2).z2 - ((Triangle)v2).z1) * v0 + (((Triangle)v2).z3 - ((Triangle)v2).z1) * v1);
    }
}

