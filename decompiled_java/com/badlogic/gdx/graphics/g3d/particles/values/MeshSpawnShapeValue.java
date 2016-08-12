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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$SaveData;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

public abstract class MeshSpawnShapeValue extends SpawnShapeValue {
    public class Triangle {
        float x1;
        float x2;
        float x3;
        float y1;
        float y2;
        float y3;
        float z1;
        float z2;
        float z3;

        public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
            super();
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
            this.x3 = x3;
            this.y3 = y3;
            this.z3 = z3;
        }

        public static Vector3 pick(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, Vector3 vector) {
            float v0 = MathUtils.random();
            float v1 = MathUtils.random();
            return vector.set((x2 - x1) * v0 + x1 + (x3 - x1) * v1, (y2 - y1) * v0 + y1 + (y3 - y1) * v1, (z2 - z1) * v0 + z1 + (z3 - z1) * v1);
        }

        public Vector3 pick(Vector3 vector) {
            float v0 = MathUtils.random();
            float v1 = MathUtils.random();
            return vector.set(this.x1 + (this.x2 - this.x1) * v0 + (this.x3 - this.x1) * v1, this.y1 + (this.y2 - this.y1) * v0 + (this.y3 - this.y1) * v1, this.z1 + (this.z2 - this.z1) * v0 + (this.z3 - this.z1) * v1);
        }
    }

    protected Mesh mesh;
    protected Model model;

    public MeshSpawnShapeValue() {
        super();
    }

    public MeshSpawnShapeValue(MeshSpawnShapeValue value) {
        super(((SpawnShapeValue)value));
    }

    public void load(AssetManager manager, ResourceData data) {
        SaveData v2 = data.getSaveData();
        AssetDescriptor v0 = v2.loadAsset();
        if(v0 != null) {
            Object v1 = manager.get(v0);
            this.setMesh(((Model)v1).meshes.get(v2.load("index").intValue()), ((Model)v1));
        }
    }

    public void load(ParticleValue value) {
        super.load(value);
        this.setMesh(value.mesh, value.model);
    }

    public void save(AssetManager manager, ResourceData data) {
        if(this.model != null) {
            SaveData v0 = data.createSaveData();
            v0.saveAsset(manager.getAssetFileName(this.model), Model.class);
            v0.save("index", Integer.valueOf(this.model.meshes.indexOf(this.mesh, true)));
        }
    }

    public void setMesh(Mesh mesh, Model model) {
        if(mesh.getVertexAttribute(1) == null) {
            throw new GdxRuntimeException("Mesh vertices must have Usage.Position");
        }

        this.model = model;
        this.mesh = mesh;
    }

    public void setMesh(Mesh mesh) {
        this.setMesh(mesh, null);
    }
}

