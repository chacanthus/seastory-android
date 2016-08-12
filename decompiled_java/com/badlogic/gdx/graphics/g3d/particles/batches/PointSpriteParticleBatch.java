// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles.batches;

import com.badlogic.gdx.Application$ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Config;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader$ParticleType;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$SaveData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.PointSpriteControllerRenderData;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class PointSpriteParticleBatch extends BufferedParticleBatch {
    protected static final VertexAttributes CPU_ATTRIBUTES = null;
    protected static final int CPU_COLOR_OFFSET = 0;
    protected static final int CPU_POSITION_OFFSET = 0;
    protected static final int CPU_REGION_OFFSET = 0;
    protected static final int CPU_SIZE_AND_ROTATION_OFFSET = 0;
    protected static final int CPU_VERTEX_SIZE = 0;
    protected static final Vector3 TMP_V1 = null;
    private static boolean pointSpritesEnabled = false;
    Renderable renderable;
    protected static final int sizeAndRotationUsage = 512;
    private float[] vertices;

    static  {
        PointSpriteParticleBatch.pointSpritesEnabled = false;
        PointSpriteParticleBatch.TMP_V1 = new Vector3();
        VertexAttribute[] v1 = new VertexAttribute[4];
        v1[0] = new VertexAttribute(1, 3, "a_position");
        v1[1] = new VertexAttribute(2, 4, "a_color");
        v1[2] = new VertexAttribute(16, 4, "a_region");
        v1[3] = new VertexAttribute(512, 3, "a_sizeAndRotation");
        PointSpriteParticleBatch.CPU_ATTRIBUTES = new VertexAttributes(v1);
        PointSpriteParticleBatch.CPU_VERTEX_SIZE = ((short)(PointSpriteParticleBatch.CPU_ATTRIBUTES.vertexSize / 4));
        PointSpriteParticleBatch.CPU_POSITION_OFFSET = ((short)(PointSpriteParticleBatch.CPU_ATTRIBUTES.findByUsage(1).offset / 4));
        PointSpriteParticleBatch.CPU_COLOR_OFFSET = ((short)(PointSpriteParticleBatch.CPU_ATTRIBUTES.findByUsage(2).offset / 4));
        PointSpriteParticleBatch.CPU_REGION_OFFSET = ((short)(PointSpriteParticleBatch.CPU_ATTRIBUTES.findByUsage(16).offset / 4));
        PointSpriteParticleBatch.CPU_SIZE_AND_ROTATION_OFFSET = ((short)(PointSpriteParticleBatch.CPU_ATTRIBUTES.findByUsage(512).offset / 4));
    }

    public PointSpriteParticleBatch() {
        this(1000);
    }

    public PointSpriteParticleBatch(int capacity) {
        super(PointSpriteControllerRenderData.class);
        if(!PointSpriteParticleBatch.pointSpritesEnabled) {
            PointSpriteParticleBatch.enablePointSprites();
        }

        this.allocRenderable();
        this.ensureCapacity(capacity);
        this.renderable.shader = new ParticleShader(this.renderable, new Config(ParticleType.Point));
        this.renderable.shader.init();
    }

    protected void allocParticlesData(int capacity) {
        this.vertices = new float[PointSpriteParticleBatch.CPU_VERTEX_SIZE * capacity];
        if(this.renderable.mesh != null) {
            this.renderable.mesh.dispose();
        }

        this.renderable.mesh = new Mesh(false, capacity, 0, PointSpriteParticleBatch.CPU_ATTRIBUTES);
    }

    protected void allocRenderable() {
        this.renderable = new Renderable();
        this.renderable.primitiveType = 0;
        this.renderable.meshPartOffset = 0;
        Renderable v1 = this.renderable;
        Attribute[] v3 = new Attribute[3];
        v3[0] = new BlendingAttribute(1, 771, 1f);
        v3[1] = new DepthTestAttribute(515, false);
        v3[2] = TextureAttribute.createDiffuse(null);
        v1.material = new Material(v3);
    }

    private static void enablePointSprites() {
        Gdx.gl.glEnable(34370);
        if(Gdx.app.getType() == ApplicationType.Desktop) {
            Gdx.gl.glEnable(34913);
        }

        PointSpriteParticleBatch.pointSpritesEnabled = true;
    }

    protected void flush(int[] offsets) {
        int v14 = 0;
        Iterator v4 = this.renderData.iterator();
    label_4:
        if(!v4.hasNext()) {
            goto label_169;
        }

        Object v3 = v4.next();
        FloatChannel v13 = ((PointSpriteControllerRenderData)v3).scaleChannel;
        FloatChannel v9 = ((PointSpriteControllerRenderData)v3).regionChannel;
        FloatChannel v7 = ((PointSpriteControllerRenderData)v3).positionChannel;
        FloatChannel v1 = ((PointSpriteControllerRenderData)v3).colorChannel;
        FloatChannel v11 = ((PointSpriteControllerRenderData)v3).rotationChannel;
        int v6 = 0;
        while(true) {
            if(v6 >= ((PointSpriteControllerRenderData)v3).controller.particles.size) {
                goto label_4;
            }

            int v5 = offsets[v14] * PointSpriteParticleBatch.CPU_VERTEX_SIZE;
            int v10 = v6 * v9.strideSize;
            int v8 = v6 * v7.strideSize;
            int v2 = v6 * v1.strideSize;
            int v12 = v6 * v11.strideSize;
            this.vertices[PointSpriteParticleBatch.CPU_POSITION_OFFSET + v5] = v7.data[v8];
            this.vertices[PointSpriteParticleBatch.CPU_POSITION_OFFSET + v5 + 1] = v7.data[v8 + 1];
            this.vertices[PointSpriteParticleBatch.CPU_POSITION_OFFSET + v5 + 2] = v7.data[v8 + 2];
            this.vertices[PointSpriteParticleBatch.CPU_COLOR_OFFSET + v5] = v1.data[v2];
            this.vertices[PointSpriteParticleBatch.CPU_COLOR_OFFSET + v5 + 1] = v1.data[v2 + 1];
            this.vertices[PointSpriteParticleBatch.CPU_COLOR_OFFSET + v5 + 2] = v1.data[v2 + 2];
            this.vertices[PointSpriteParticleBatch.CPU_COLOR_OFFSET + v5 + 3] = v1.data[v2 + 3];
            this.vertices[PointSpriteParticleBatch.CPU_SIZE_AND_ROTATION_OFFSET + v5] = v13.data[v13.strideSize * v6];
            this.vertices[PointSpriteParticleBatch.CPU_SIZE_AND_ROTATION_OFFSET + v5 + 1] = v11.data[v12];
            this.vertices[PointSpriteParticleBatch.CPU_SIZE_AND_ROTATION_OFFSET + v5 + 2] = v11.data[v12 + 1];
            this.vertices[PointSpriteParticleBatch.CPU_REGION_OFFSET + v5] = v9.data[v10];
            this.vertices[PointSpriteParticleBatch.CPU_REGION_OFFSET + v5 + 1] = v9.data[v10 + 1];
            this.vertices[PointSpriteParticleBatch.CPU_REGION_OFFSET + v5 + 2] = v9.data[v10 + 2];
            this.vertices[PointSpriteParticleBatch.CPU_REGION_OFFSET + v5 + 3] = v9.data[v10 + 3];
            ++v6;
            ++v14;
        }

    label_169:
        this.renderable.meshPartSize = this.bufferedParticlesCount;
        this.renderable.mesh.setVertices(this.vertices, 0, this.bufferedParticlesCount * PointSpriteParticleBatch.CPU_VERTEX_SIZE);
    }

    public void getRenderables(Array arg3, Pool arg4) {
        if(this.bufferedParticlesCount > 0) {
            arg3.add(arg4.obtain().set(this.renderable));
        }
    }

    public Texture getTexture() {
        return this.renderable.material.get(TextureAttribute.Diffuse).textureDescription.texture;
    }

    public void load(AssetManager manager, ResourceData resources) {
        SaveData v0 = resources.getSaveData("pointSpriteBatch");
        if(v0 != null) {
            this.setTexture(manager.get(v0.loadAsset()));
        }
    }

    public void save(AssetManager manager, ResourceData resources) {
        resources.createSaveData("pointSpriteBatch").saveAsset(manager.getAssetFileName(this.getTexture()), Texture.class);
    }

    public void setTexture(Texture texture) {
        this.renderable.material.get(TextureAttribute.Diffuse).textureDescription.texture = ((GLTexture)texture);
    }
}

