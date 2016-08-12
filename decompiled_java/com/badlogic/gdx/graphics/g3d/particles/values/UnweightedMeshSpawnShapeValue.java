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

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public final class UnweightedMeshSpawnShapeValue extends MeshSpawnShapeValue {
    private short[] indices;
    private int positionOffset;
    private int triangleCount;
    private int vertexCount;
    private int vertexSize;
    private float[] vertices;

    public UnweightedMeshSpawnShapeValue() {
        super();
    }

    public UnweightedMeshSpawnShapeValue(UnweightedMeshSpawnShapeValue value) {
        super(((MeshSpawnShapeValue)value));
        this.load(((ParticleValue)value));
    }

    public SpawnShapeValue copy() {
        return new UnweightedMeshSpawnShapeValue(this);
    }

    public void setMesh(Mesh mesh, Model model) {
        super.setMesh(mesh, model);
        this.vertexSize = mesh.getVertexSize() / 4;
        this.positionOffset = mesh.getVertexAttribute(1).offset / 4;
        int v0 = mesh.getNumIndices();
        if(v0 > 0) {
            this.indices = new short[v0];
            mesh.getIndices(this.indices);
            this.triangleCount = this.indices.length / 3;
        }
        else {
            this.indices = null;
        }

        this.vertexCount = mesh.getNumVertices();
        this.vertices = new float[this.vertexCount * this.vertexSize];
        mesh.getVertices(this.vertices);
    }

    public void spawnAux(Vector3 vector, float percent) {
        int v12;
        int v11;
        int v10;
        if(this.indices == null) {
            v10 = MathUtils.random(this.vertexCount - 3) * this.vertexSize + this.positionOffset;
            v11 = v10 + this.vertexSize;
            v12 = v11 + this.vertexSize;
            Triangle.pick(this.vertices[v10], this.vertices[v10 + 1], this.vertices[v10 + 2], this.vertices[v11], this.vertices[v11 + 1], this.vertices[v11 + 2], this.vertices[v12], this.vertices[v12 + 1], this.vertices[v12 + 2], vector);
        }
        else {
            int v13 = MathUtils.random(this.triangleCount - 1) * 3;
            v10 = this.indices[v13] * this.vertexSize + this.positionOffset;
            v11 = this.indices[v13 + 1] * this.vertexSize + this.positionOffset;
            v12 = this.indices[v13 + 2] * this.vertexSize + this.positionOffset;
            Triangle.pick(this.vertices[v10], this.vertices[v10 + 1], this.vertices[v10 + 2], this.vertices[v11], this.vertices[v11 + 1], this.vertices[v11 + 2], this.vertices[v12], this.vertices[v12 + 1], this.vertices[v12 + 2], vector);
        }
    }
}

