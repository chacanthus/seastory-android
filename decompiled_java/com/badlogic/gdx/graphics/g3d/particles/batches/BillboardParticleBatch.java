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

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray$FloatChannel;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader$AlignMode;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData$SaveData;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardControllerRenderData;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class BillboardParticleBatch extends BufferedParticleBatch {
    public class Config {
        public Config(boolean useGPU, AlignMode mode) {
            super();
            this.useGPU = useGPU;
            this.mode = mode;
        }

        public Config() {
            super();
        }
    }

    class RenderablePool extends Pool {
        public RenderablePool(BillboardParticleBatch arg1) {
            BillboardParticleBatch.this = arg1;
            super();
        }

        public Renderable newObject() {
            return BillboardParticleBatch.this.allocRenderable();
        }

        public Object newObject() {
            return this.newObject();
        }
    }

    private static final VertexAttributes CPU_ATTRIBUTES = null;
    private static final int CPU_COLOR_OFFSET = 0;
    private static final int CPU_POSITION_OFFSET = 0;
    private static final int CPU_UV_OFFSET = 0;
    private static final int CPU_VERTEX_SIZE = 0;
    private static final VertexAttributes GPU_ATTRIBUTES = null;
    private static final int GPU_COLOR_OFFSET = 0;
    private static final int GPU_POSITION_OFFSET = 0;
    private static final int GPU_SIZE_ROTATION_OFFSET = 0;
    private static final int GPU_UV_OFFSET = 0;
    private static final int GPU_VERTEX_SIZE = 0;
    private static final int MAX_PARTICLES_PER_MESH = 8191;
    private static final int MAX_VERTICES_PER_MESH = 32764;
    protected static final Matrix3 TMP_M3 = null;
    protected static final Vector3 TMP_V1 = null;
    protected static final Vector3 TMP_V2 = null;
    protected static final Vector3 TMP_V3 = null;
    protected static final Vector3 TMP_V4 = null;
    protected static final Vector3 TMP_V5 = null;
    protected static final Vector3 TMP_V6 = null;
    private VertexAttributes currentAttributes;
    private int currentVertexSize;
    protected static final int directionUsage = 1024;
    private short[] indices;
    protected AlignMode mode;
    private RenderablePool renderablePool;
    private Array renderables;
    Shader shader;
    protected static final int sizeAndRotationUsage = 512;
    protected Texture texture;
    protected boolean useGPU;
    private float[] vertices;

    static  {
        BillboardParticleBatch.TMP_V1 = new Vector3();
        BillboardParticleBatch.TMP_V2 = new Vector3();
        BillboardParticleBatch.TMP_V3 = new Vector3();
        BillboardParticleBatch.TMP_V4 = new Vector3();
        BillboardParticleBatch.TMP_V5 = new Vector3();
        BillboardParticleBatch.TMP_V6 = new Vector3();
        BillboardParticleBatch.TMP_M3 = new Matrix3();
        VertexAttribute[] v1 = new VertexAttribute[4];
        v1[0] = new VertexAttribute(1, 3, "a_position");
        v1[1] = new VertexAttribute(16, 2, "a_texCoord0");
        v1[2] = new VertexAttribute(2, 4, "a_color");
        v1[3] = new VertexAttribute(512, 4, "a_sizeAndRotation");
        BillboardParticleBatch.GPU_ATTRIBUTES = new VertexAttributes(v1);
        v1 = new VertexAttribute[3];
        v1[0] = new VertexAttribute(1, 3, "a_position");
        v1[1] = new VertexAttribute(16, 2, "a_texCoord0");
        v1[2] = new VertexAttribute(2, 4, "a_color");
        BillboardParticleBatch.CPU_ATTRIBUTES = new VertexAttributes(v1);
        BillboardParticleBatch.GPU_POSITION_OFFSET = ((short)(BillboardParticleBatch.GPU_ATTRIBUTES.findByUsage(1).offset / 4));
        BillboardParticleBatch.GPU_UV_OFFSET = ((short)(BillboardParticleBatch.GPU_ATTRIBUTES.findByUsage(16).offset / 4));
        BillboardParticleBatch.GPU_SIZE_ROTATION_OFFSET = ((short)(BillboardParticleBatch.GPU_ATTRIBUTES.findByUsage(512).offset / 4));
        BillboardParticleBatch.GPU_COLOR_OFFSET = ((short)(BillboardParticleBatch.GPU_ATTRIBUTES.findByUsage(2).offset / 4));
        BillboardParticleBatch.GPU_VERTEX_SIZE = BillboardParticleBatch.GPU_ATTRIBUTES.vertexSize / 4;
        BillboardParticleBatch.CPU_POSITION_OFFSET = ((short)(BillboardParticleBatch.CPU_ATTRIBUTES.findByUsage(1).offset / 4));
        BillboardParticleBatch.CPU_UV_OFFSET = ((short)(BillboardParticleBatch.CPU_ATTRIBUTES.findByUsage(16).offset / 4));
        BillboardParticleBatch.CPU_COLOR_OFFSET = ((short)(BillboardParticleBatch.CPU_ATTRIBUTES.findByUsage(2).offset / 4));
        BillboardParticleBatch.CPU_VERTEX_SIZE = BillboardParticleBatch.CPU_ATTRIBUTES.vertexSize / 4;
    }

    public BillboardParticleBatch() {
        this(AlignMode.Screen, false, 100);
    }

    public BillboardParticleBatch(AlignMode mode, boolean useGPU, int capacity) {
        super(BillboardControllerRenderData.class);
        this.currentVertexSize = 0;
        this.useGPU = false;
        this.mode = AlignMode.Screen;
        this.renderables = new Array();
        this.renderablePool = new RenderablePool(this);
        this.allocIndices();
        this.initRenderData();
        this.ensureCapacity(capacity);
        this.setUseGpu(useGPU);
        this.setAlignMode(mode);
    }

    public BillboardParticleBatch(int capacity) {
        this(AlignMode.Screen, false, capacity);
    }

    private void allocIndices() {
        int v1 = 49146;
        this.indices = new short[v1];
        int v0 = 0;
        int v2;
        for(v2 = 0; v0 < v1; v2 += 4) {
            this.indices[v0] = ((short)v2);
            this.indices[v0 + 1] = ((short)(v2 + 1));
            this.indices[v0 + 2] = ((short)(v2 + 2));
            this.indices[v0 + 3] = ((short)(v2 + 2));
            this.indices[v0 + 4] = ((short)(v2 + 3));
            this.indices[v0 + 5] = ((short)v2);
            v0 += 6;
        }
    }

    public void allocParticlesData(int capacity) {
        this.vertices = new float[this.currentVertexSize * 4 * capacity];
        this.allocRenderables(capacity);
    }

    protected Renderable allocRenderable() {
        Renderable v0 = new Renderable();
        v0.primitiveType = 4;
        v0.meshPartOffset = 0;
        Attribute[] v2 = new Attribute[3];
        v2[0] = new BlendingAttribute(1, 771, 1f);
        v2[1] = new DepthTestAttribute(515, false);
        v2[2] = TextureAttribute.createDiffuse(this.texture);
        v0.material = new Material(v2);
        v0.mesh = new Mesh(false, 32764, 49146, this.currentAttributes);
        v0.mesh.setIndices(this.indices);
        v0.shader = this.shader;
        return v0;
    }

    private void allocRenderables(int capacity) {
        int v3 = MathUtils.ceil(((float)(capacity / 8191)));
        int v0 = this.renderablePool.getFree();
        if(v0 < v3) {
            int v1 = 0;
            int v2 = v3 - v0;
            while(v1 < v2) {
                this.renderablePool.free(this.renderablePool.newObject());
                ++v1;
            }
        }
    }

    private void allocShader() {
        Renderable v0 = this.allocRenderable();
        Shader v1 = this.getShader(v0);
        v0.shader = v1;
        this.shader = v1;
        this.renderablePool.free(v0);
    }

    public void begin() {
        super.begin();
        this.renderablePool.freeAll(this.renderables);
        this.renderables.clear();
    }

    private void clearRenderablesPool() {
        this.renderablePool.freeAll(this.renderables);
        int v1 = 0;
        int v0 = this.renderablePool.getFree();
        while(v1 < v0) {
            this.renderablePool.obtain().mesh.dispose();
            ++v1;
        }

        this.renderables.clear();
    }

    private void fillVerticesGPU(int[] particlesOffset) {
        int v54 = 0;
        Iterator v44 = this.renderData.iterator();
    label_4:
        if(!v44.hasNext()) {
            return;
        }

        Object v43 = v44.next();
        FloatChannel v53 = v43.scaleChannel;
        FloatChannel v48 = v43.regionChannel;
        FloatChannel v46 = v43.positionChannel;
        FloatChannel v41 = v43.colorChannel;
        FloatChannel v50 = v43.rotationChannel;
        int v45 = 0;
        int v40 = v43.controller.particles.size;
        while(true) {
            if(v45 >= v40) {
                goto label_4;
            }

            int v3 = particlesOffset[v54] * this.currentVertexSize * 4;
            float v52 = v53.data[v53.strideSize * v45];
            int v49 = v45 * v48.strideSize;
            int v47 = v45 * v46.strideSize;
            int v42 = v45 * v41.strideSize;
            int v51 = v45 * v50.strideSize;
            float v4 = v46.data[v47];
            float v5 = v46.data[v47 + 1];
            float v6 = v46.data[v47 + 2];
            float v7 = v48.data[v49];
            float v55 = v48.data[v49 + 1];
            float v22 = v48.data[v49 + 2];
            float v8 = v48.data[v49 + 3];
            float v24 = v48.data[v49 + 4] * v52;
            float v33 = v48.data[v49 + 5] * v52;
            float v13 = v41.data[v42];
            float v14 = v41.data[v42 + 1];
            float v15 = v41.data[v42 + 2];
            float v16 = v41.data[v42 + 3];
            float v11 = v50.data[v51];
            float v12 = v50.data[v51 + 1];
            BillboardParticleBatch.putVertex(this.vertices, v3, v4, v5, v6, v7, v8, -v24, -v33, v11, v12, v13, v14, v15, v16);
            v3 += this.currentVertexSize;
            BillboardParticleBatch.putVertex(this.vertices, v3, v4, v5, v6, v22, v8, v24, -v33, v11, v12, v13, v14, v15, v16);
            v3 += this.currentVertexSize;
            BillboardParticleBatch.putVertex(this.vertices, v3, v4, v5, v6, v22, v55, v24, v33, v11, v12, v13, v14, v15, v16);
            BillboardParticleBatch.putVertex(this.vertices, v3 + this.currentVertexSize, v4, v5, v6, v7, v55, -v24, v33, v11, v12, v13, v14, v15, v16);
            ++v45;
            ++v54;
        }
    }

    private void fillVerticesToScreenCPU(int[] particlesOffset) {
        Vector3 v31 = BillboardParticleBatch.TMP_V3.set(this.camera.direction).scl(-1f);
        Vector3 v40 = BillboardParticleBatch.TMP_V4.set(this.camera.up).crs(v31).nor();
        Vector3 v49 = this.camera.up;
        int v48 = 0;
        Iterator v30 = this.renderData.iterator();
    label_23:
        if(!v30.hasNext()) {
            return;
        }

        Object v29 = v30.next();
        FloatChannel v44 = v29.scaleChannel;
        FloatChannel v38 = v29.regionChannel;
        FloatChannel v33 = v29.positionChannel;
        FloatChannel v26 = v29.colorChannel;
        FloatChannel v41 = v29.rotationChannel;
        int v32 = 0;
        int v25 = v29.controller.particles.size;
        while(true) {
            if(v32 >= v25) {
                goto label_23;
            }

            int v4 = particlesOffset[v48] * this.currentVertexSize * 4;
            float v43 = v44.data[v44.strideSize * v32];
            int v39 = v32 * v38.strideSize;
            int v34 = v32 * v33.strideSize;
            int v27 = v32 * v26.strideSize;
            int v42 = v32 * v41.strideSize;
            float v35 = v33.data[v34];
            float v36 = v33.data[v34 + 1];
            float v37 = v33.data[v34 + 2];
            float v6 = v38.data[v39];
            float v50 = v38.data[v39 + 1];
            float v15 = v38.data[v39 + 2];
            float v7 = v38.data[v39 + 3];
            float v46 = v38.data[v39 + 4] * v43;
            float v47 = v38.data[v39 + 5] * v43;
            float v8 = v26.data[v27];
            float v9 = v26.data[v27 + 1];
            float v10 = v26.data[v27 + 2];
            float v11 = v26.data[v27 + 3];
            float v28 = v41.data[v42];
            float v45 = v41.data[v42 + 1];
            BillboardParticleBatch.TMP_V1.set(v40).scl(v46);
            BillboardParticleBatch.TMP_V2.set(v49).scl(v47);
            if(v28 != 1f) {
                BillboardParticleBatch.TMP_M3.setToRotation(v31, v28, v45);
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x, -BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y, -BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v6, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x, BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y, BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v15, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x, BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y, BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v15, v50, v8, v9, v10, v11);
                BillboardParticleBatch.putVertex(this.vertices, v4 + this.currentVertexSize, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x, -BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y, -BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v6, v50, v8, v9, v10, v11);
            }
            else {
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x + v35, -BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y + v36, -BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z + v37), v6, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x + v35, BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y + v36, BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z + v37), v15, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x + v35, BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y + v36, BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z + v37), v15, v50, v8, v9, v10, v11);
                BillboardParticleBatch.putVertex(this.vertices, v4 + this.currentVertexSize, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x + v35, -BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y + v36, -BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z + v37), v6, v50, v8, v9, v10, v11);
            }

            ++v32;
            ++v48;
        }
    }

    private void fillVerticesToViewPointCPU(int[] particlesOffset) {
        int v48 = 0;
        Iterator v30 = this.renderData.iterator();
    label_4:
        if(!v30.hasNext()) {
            return;
        }

        Object v29 = v30.next();
        FloatChannel v44 = v29.scaleChannel;
        FloatChannel v38 = v29.regionChannel;
        FloatChannel v33 = v29.positionChannel;
        FloatChannel v26 = v29.colorChannel;
        FloatChannel v41 = v29.rotationChannel;
        int v32 = 0;
        int v25 = v29.controller.particles.size;
        while(true) {
            if(v32 >= v25) {
                goto label_4;
            }

            int v4 = particlesOffset[v48] * this.currentVertexSize * 4;
            float v43 = v44.data[v44.strideSize * v32];
            int v39 = v32 * v38.strideSize;
            int v34 = v32 * v33.strideSize;
            int v27 = v32 * v26.strideSize;
            int v42 = v32 * v41.strideSize;
            float v35 = v33.data[v34];
            float v36 = v33.data[v34 + 1];
            float v37 = v33.data[v34 + 2];
            float v6 = v38.data[v39];
            float v50 = v38.data[v39 + 1];
            float v15 = v38.data[v39 + 2];
            float v7 = v38.data[v39 + 3];
            float v46 = v38.data[v39 + 4] * v43;
            float v47 = v38.data[v39 + 5] * v43;
            float v8 = v26.data[v27];
            float v9 = v26.data[v27 + 1];
            float v10 = v26.data[v27 + 2];
            float v11 = v26.data[v27 + 3];
            float v28 = v41.data[v42];
            float v45 = v41.data[v42 + 1];
            Vector3 v31 = BillboardParticleBatch.TMP_V3.set(this.camera.position).sub(v35, v36, v37).nor();
            Vector3 v40 = BillboardParticleBatch.TMP_V1.set(this.camera.up).crs(v31).nor();
            Vector3 v49 = BillboardParticleBatch.TMP_V2.set(v31).crs(v40);
            v40.scl(v46);
            v49.scl(v47);
            if(v28 != 1f) {
                BillboardParticleBatch.TMP_M3.setToRotation(v31, v28, v45);
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x, -BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y, -BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v6, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x, BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y, BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v15, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x, BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y, BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v15, v50, v8, v9, v10, v11);
                BillboardParticleBatch.putVertex(this.vertices, v4 + this.currentVertexSize, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x, -BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y, -BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z).mul(BillboardParticleBatch.TMP_M3).add(v35, v36, v37), v6, v50, v8, v9, v10, v11);
            }
            else {
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x + v35, -BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y + v36, -BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z + v37), v6, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x - BillboardParticleBatch.TMP_V2.x + v35, BillboardParticleBatch.TMP_V1.y - BillboardParticleBatch.TMP_V2.y + v36, BillboardParticleBatch.TMP_V1.z - BillboardParticleBatch.TMP_V2.z + v37), v15, v7, v8, v9, v10, v11);
                v4 += this.currentVertexSize;
                BillboardParticleBatch.putVertex(this.vertices, v4, BillboardParticleBatch.TMP_V6.set(BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x + v35, BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y + v36, BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z + v37), v15, v50, v8, v9, v10, v11);
                BillboardParticleBatch.putVertex(this.vertices, v4 + this.currentVertexSize, BillboardParticleBatch.TMP_V6.set(-BillboardParticleBatch.TMP_V1.x + BillboardParticleBatch.TMP_V2.x + v35, -BillboardParticleBatch.TMP_V1.y + BillboardParticleBatch.TMP_V2.y + v36, -BillboardParticleBatch.TMP_V1.z + BillboardParticleBatch.TMP_V2.z + v37), v6, v50, v8, v9, v10, v11);
            }

            ++v32;
            ++v48;
        }
    }

    protected void flush(int[] offsets) {
        if(this.useGPU) {
            this.fillVerticesGPU(offsets);
        }
        else if(this.mode == AlignMode.Screen) {
            this.fillVerticesToScreenCPU(offsets);
        }
        else if(this.mode == AlignMode.ViewPoint) {
            this.fillVerticesToViewPointCPU(offsets);
        }

        int v3 = this.bufferedParticlesCount * 4;
        int v2;
        for(v2 = 0; v2 < v3; v2 += v0) {
            int v0 = Math.min(v3 - v2, 32764);
            Object v1 = this.renderablePool.obtain();
            ((Renderable)v1).meshPartSize = v0 / 4 * 6;
            ((Renderable)v1).mesh.setVertices(this.vertices, this.currentVertexSize * v2, this.currentVertexSize * v0);
            this.renderables.add(v1);
        }
    }

    public AlignMode getAlignMode() {
        return this.mode;
    }

    public void getRenderables(Array arg4, Pool arg5) {
        Iterator v0 = this.renderables.iterator();
        while(v0.hasNext()) {
            arg4.add(arg5.obtain().set(v0.next()));
        }
    }

    private Shader getShader(Renderable renderable) {
        DefaultShader v0_1;
        if(this.useGPU) {
            ParticleShader v0 = new ParticleShader(renderable, new com.badlogic.gdx.graphics.g3d.particles.ParticleShader$Config(this.mode));
        }
        else {
            v0_1 = new DefaultShader(renderable);
        }

        ((Shader)v0_1).init();
        return ((Shader)v0_1);
    }

    public Texture getTexture() {
        return this.texture;
    }

    private void initRenderData() {
        this.setVertexData();
        this.clearRenderablesPool();
        this.allocShader();
        this.resetCapacity();
    }

    public boolean isUseGPU() {
        return this.useGPU;
    }

    public void load(AssetManager manager, ResourceData resources) {
        SaveData v1 = resources.getSaveData("billboardBatch");
        if(v1 != null) {
            this.setTexture(manager.get(v1.loadAsset()));
            Object v0 = v1.load("cfg");
            this.setUseGpu(((Config)v0).useGPU);
            this.setAlignMode(((Config)v0).mode);
        }
    }

    private static void putVertex(float[] vertices, int offset, float x, float y, float z, float u, float v, float scaleX, float scaleY, float cosRotation, float sinRotation, float r, float g, float b, float a) {
        vertices[BillboardParticleBatch.GPU_POSITION_OFFSET + offset] = x;
        vertices[BillboardParticleBatch.GPU_POSITION_OFFSET + offset + 1] = y;
        vertices[BillboardParticleBatch.GPU_POSITION_OFFSET + offset + 2] = z;
        vertices[BillboardParticleBatch.GPU_UV_OFFSET + offset] = u;
        vertices[BillboardParticleBatch.GPU_UV_OFFSET + offset + 1] = v;
        vertices[BillboardParticleBatch.GPU_SIZE_ROTATION_OFFSET + offset] = scaleX;
        vertices[BillboardParticleBatch.GPU_SIZE_ROTATION_OFFSET + offset + 1] = scaleY;
        vertices[BillboardParticleBatch.GPU_SIZE_ROTATION_OFFSET + offset + 2] = cosRotation;
        vertices[BillboardParticleBatch.GPU_SIZE_ROTATION_OFFSET + offset + 3] = sinRotation;
        vertices[BillboardParticleBatch.GPU_COLOR_OFFSET + offset] = r;
        vertices[BillboardParticleBatch.GPU_COLOR_OFFSET + offset + 1] = g;
        vertices[BillboardParticleBatch.GPU_COLOR_OFFSET + offset + 2] = b;
        vertices[BillboardParticleBatch.GPU_COLOR_OFFSET + offset + 3] = a;
    }

    private static void putVertex(float[] vertices, int offset, Vector3 p, float u, float v, float r, float g, float b, float a) {
        vertices[BillboardParticleBatch.CPU_POSITION_OFFSET + offset] = p.x;
        vertices[BillboardParticleBatch.CPU_POSITION_OFFSET + offset + 1] = p.y;
        vertices[BillboardParticleBatch.CPU_POSITION_OFFSET + offset + 2] = p.z;
        vertices[BillboardParticleBatch.CPU_UV_OFFSET + offset] = u;
        vertices[BillboardParticleBatch.CPU_UV_OFFSET + offset + 1] = v;
        vertices[BillboardParticleBatch.CPU_COLOR_OFFSET + offset] = r;
        vertices[BillboardParticleBatch.CPU_COLOR_OFFSET + offset + 1] = g;
        vertices[BillboardParticleBatch.CPU_COLOR_OFFSET + offset + 2] = b;
        vertices[BillboardParticleBatch.CPU_COLOR_OFFSET + offset + 3] = a;
    }

    public void save(AssetManager manager, ResourceData resources) {
        SaveData v0 = resources.createSaveData("billboardBatch");
        v0.save("cfg", new Config(this.useGPU, this.mode));
        v0.saveAsset(manager.getAssetFileName(this.texture), Texture.class);
    }

    public void setAlignMode(AlignMode mode) {
        if(mode != this.mode) {
            this.mode = mode;
            if(this.useGPU) {
                this.initRenderData();
                this.allocRenderables(this.bufferedParticlesCount);
            }
        }
    }

    public void setTexture(Texture texture) {
        this.renderablePool.freeAll(this.renderables);
        this.renderables.clear();
        int v2 = 0;
        int v1 = this.renderablePool.getFree();
        while(v2 < v1) {
            this.renderablePool.obtain().material.get(TextureAttribute.Diffuse).textureDescription.texture = ((GLTexture)texture);
            ++v2;
        }

        this.texture = texture;
    }

    public void setUseGpu(boolean useGPU) {
        if(this.useGPU != useGPU) {
            this.useGPU = useGPU;
            this.initRenderData();
            this.allocRenderables(this.bufferedParticlesCount);
        }
    }

    public void setVertexData() {
        if(this.useGPU) {
            this.currentAttributes = BillboardParticleBatch.GPU_ATTRIBUTES;
            this.currentVertexSize = BillboardParticleBatch.GPU_VERTEX_SIZE;
        }
        else {
            this.currentAttributes = BillboardParticleBatch.CPU_ATTRIBUTES;
            this.currentVertexSize = BillboardParticleBatch.CPU_VERTEX_SIZE;
        }
    }
}

