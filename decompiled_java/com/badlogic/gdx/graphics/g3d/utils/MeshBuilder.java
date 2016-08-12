// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ShortArray;
import java.util.Iterator;

public class MeshBuilder implements MeshPartBuilder {
    private VertexAttributes attributes;
    private int colOffset;
    private int colSize;
    private final Color color;
    private int cpOffset;
    private boolean hasColor;
    private boolean hasUVTransform;
    private ShortArray indices;
    private static IntIntMap indicesMap;
    private int istart;
    private short lastIndex;
    private final Matrix4 matTmp1;
    private static final Array matrices4Array;
    private static final Pool matrices4Pool;
    private int norOffset;
    private final Matrix3 normalTransform;
    private MeshPart part;
    private Array parts;
    private int posOffset;
    private int posSize;
    private final Matrix4 positionTransform;
    private int primitiveType;
    private int stride;
    private final Color tempC1;
    private final Vector3 tempV1;
    private final Vector3 tempV2;
    private final Vector3 tempV3;
    private final Vector3 tempV4;
    private final Vector3 tempV5;
    private final Vector3 tempV6;
    private final Vector3 tempV7;
    private final Vector3 tempV8;
    private static final ShortArray tmpIndices;
    private final Vector3 tmpNormal;
    private static final FloatArray tmpVertices;
    private float uOffset;
    private float uScale;
    private int uvOffset;
    private float vOffset;
    private float vScale;
    private static final Vector3 vTmp;
    private static final Array vectorArray;
    private static final Pool vectorPool;
    private final VertexInfo vertTmp1;
    private final VertexInfo vertTmp2;
    private final VertexInfo vertTmp3;
    private final VertexInfo vertTmp4;
    private final VertexInfo vertTmp5;
    private final VertexInfo vertTmp6;
    private final VertexInfo vertTmp7;
    private final VertexInfo vertTmp8;
    private float[] vertex;
    private boolean vertexTransformationEnabled;
    private FloatArray vertices;
    private short vindex;

    static  {
        MeshBuilder.tmpIndices = new ShortArray();
        MeshBuilder.tmpVertices = new FloatArray();
        MeshBuilder.vectorPool = new Pool() {
            protected Vector3 newObject() {
                return new Vector3();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        MeshBuilder.vectorArray = new Array();
        MeshBuilder.matrices4Pool = new Pool() {
            protected Matrix4 newObject() {
                return new Matrix4();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        MeshBuilder.matrices4Array = new Array();
        MeshBuilder.vTmp = new Vector3();
        MeshBuilder.indicesMap = null;
    }

    public MeshBuilder() {
        super();
        this.vertTmp1 = new VertexInfo();
        this.vertTmp2 = new VertexInfo();
        this.vertTmp3 = new VertexInfo();
        this.vertTmp4 = new VertexInfo();
        this.vertTmp5 = new VertexInfo();
        this.vertTmp6 = new VertexInfo();
        this.vertTmp7 = new VertexInfo();
        this.vertTmp8 = new VertexInfo();
        this.matTmp1 = new Matrix4();
        this.tempV1 = new Vector3();
        this.tempV2 = new Vector3();
        this.tempV3 = new Vector3();
        this.tempV4 = new Vector3();
        this.tempV5 = new Vector3();
        this.tempV6 = new Vector3();
        this.tempV7 = new Vector3();
        this.tempV8 = new Vector3();
        this.tempC1 = new Color();
        this.vertices = new FloatArray();
        this.indices = new ShortArray();
        this.parts = new Array();
        this.color = new Color(Color.WHITE);
        this.hasColor = false;
        this.uOffset = 0f;
        this.uScale = 1f;
        this.vOffset = 0f;
        this.vScale = 1f;
        this.hasUVTransform = false;
        this.vertexTransformationEnabled = false;
        this.positionTransform = new Matrix4();
        this.normalTransform = new Matrix3();
        this.lastIndex = -1;
        this.tmpNormal = new Vector3();
    }

    private void addMesh(float[] vertices, short[] indices, int indexOffset, int numIndices) {
        short v0_1;
        int v3;
        if(MeshBuilder.indicesMap == null) {
            MeshBuilder.indicesMap = new IntIntMap(numIndices);
        }
        else {
            MeshBuilder.indicesMap.clear();
            MeshBuilder.indicesMap.ensureCapacity(numIndices);
        }

        this.ensureIndices(numIndices);
        if(vertices.length < numIndices) {
            v3 = vertices.length;
        }
        else {
            v3 = numIndices;
        }

        this.ensureVertices(v3);
        int v1;
        for(v1 = 0; v1 < numIndices; ++v1) {
            int v2 = indices[v1];
            int v0 = MeshBuilder.indicesMap.get(v2, -1);
            if(v0 < 0) {
                this.addVertex(vertices, this.stride * v2);
                IntIntMap v3_1 = MeshBuilder.indicesMap;
                v0_1 = this.lastIndex;
                v3_1.put(v2, v0_1);
            }

            this.index(((short)v0_1));
        }
    }

    public void addMesh(Mesh mesh) {
        this.addMesh(mesh, 0, mesh.getNumIndices());
    }

    public void addMesh(Mesh mesh, int indexOffset, int numIndices) {
        if(!this.attributes.equals(mesh.getVertexAttributes())) {
            throw new GdxRuntimeException("Vertex attributes do not match");
        }

        if(numIndices > 0) {
            int v0 = mesh.getNumVertices() * this.stride;
            MeshBuilder.tmpVertices.clear();
            MeshBuilder.tmpVertices.ensureCapacity(v0);
            MeshBuilder.tmpVertices.size = v0;
            mesh.getVertices(MeshBuilder.tmpVertices.items);
            MeshBuilder.tmpIndices.clear();
            MeshBuilder.tmpIndices.ensureCapacity(numIndices);
            MeshBuilder.tmpIndices.size = numIndices;
            mesh.getIndices(indexOffset, numIndices, MeshBuilder.tmpIndices.items, 0);
            this.addMesh(MeshBuilder.tmpVertices.items, MeshBuilder.tmpIndices.items, 0, numIndices);
        }
    }

    public void addMesh(MeshPart meshpart) {
        if(meshpart.primitiveType != this.primitiveType) {
            throw new GdxRuntimeException("Primitive type doesn\'t match");
        }

        this.addMesh(meshpart.mesh, meshpart.indexOffset, meshpart.numVertices);
    }

    private final void addVertex(float[] values, int offset) {
        int v5 = 3;
        int v0 = this.vertices.size;
        this.vertices.addAll(values, offset, this.stride);
        short v1 = this.vindex;
        this.vindex = ((short)(v1 + 1));
        this.lastIndex = v1;
        if(this.vertexTransformationEnabled) {
            MeshBuilder.transformPosition(this.vertices.items, this.posOffset + v0, this.posSize, this.positionTransform);
            if(this.norOffset >= 0) {
                MeshBuilder.transformNormal(this.vertices.items, this.norOffset + v0, v5, this.normalTransform);
            }
        }

        if(this.hasColor) {
            if(this.colOffset >= 0) {
                float[] v1_1 = this.vertices.items;
                int v2 = this.colOffset + v0;
                ((float[])v1)[v2] *= this.color.r;
                v1_1 = this.vertices.items;
                v2 = this.colOffset + v0 + 1;
                v1_1[v2] *= this.color.g;
                v1_1 = this.vertices.items;
                v2 = this.colOffset + v0 + 2;
                v1_1[v2] *= this.color.b;
                if(this.colSize > v5) {
                    v1_1 = this.vertices.items;
                    v2 = this.colOffset + v0 + 3;
                    v1_1[v2] *= this.color.a;
                }
            }
            else if(this.cpOffset >= 0) {
                this.vertices.items[this.cpOffset + v0] = this.tempC1.set(NumberUtils.floatToIntColor(this.vertices.items[this.cpOffset + v0])).mul(this.color).toFloatBits();
            }
        }

        if((this.hasUVTransform) && this.uvOffset >= 0) {
            this.vertices.items[this.uvOffset + v0] = this.uOffset + this.uScale * this.vertices.items[this.uvOffset + v0];
            this.vertices.items[this.uvOffset + v0 + 1] = this.vOffset + this.vScale * this.vertices.items[this.uvOffset + v0 + 1];
        }
    }

    public void arrow(float x1, float y1, float z1, float x2, float y2, float z2, float capLength, float stemThickness, int divisions) {
        Vector3 v4 = this.tmp(x1, y1, z1);
        Vector3 v8 = this.tmp(x2, y2, z2);
        float v11 = v4.dst(v8);
        float v6 = v11 * capLength;
        float v5 = 2f * (((float)((((double)v6)) * Math.sqrt(0.333333))));
        float v13 = v11 - v6;
        float v12 = v5 * stemThickness;
        Vector3 v16 = this.tmp(v8).sub(v4).nor();
        Vector3 v9 = this.tmp(v16).crs(Vector3.Z);
        if(v9.isZero()) {
            v9.set(Vector3.X);
        }

        v9.crs(v16).nor();
        Vector3 v10 = this.tmp(v16).crs(v9).nor();
        Vector3 v7 = this.tmp(v8).sub(v4).nor();
        Matrix4 v17 = this.getVertexTransform(this.tmp());
        Matrix4 v15 = this.tmp();
        float[] v18 = v15.val;
        v18[0] = v10.x;
        v18[4] = v16.x;
        v18[8] = v9.x;
        v18[1] = v10.y;
        v18[5] = v16.y;
        v18[9] = v9.y;
        v18[2] = v10.z;
        v18[6] = v16.z;
        v18[10] = v9.z;
        Matrix4 v14 = this.tmp();
        v15.setTranslation(this.tmp(v7).scl(v13 / 2f).add(x1, y1, z1));
        this.setVertexTransform(v14.set(v15).mul(v17));
        this.cylinder(v12, v13, v12, divisions);
        v15.setTranslation(this.tmp(v7).scl(v13).add(x1, y1, z1));
        this.setVertexTransform(v14.set(v15).mul(v17));
        this.cone(v5, v6, v5, divisions);
        this.setVertexTransform(v17);
        this.cleanup();
    }

    public void begin(VertexAttributes attributes) {
        this.begin(attributes, -1);
    }

    public void begin(long attributes) {
        this.begin(MeshBuilder.createAttributes(attributes), -1);
    }

    public void begin(VertexAttributes attributes, int primitiveType) {
        int v1;
        MeshPart v5 = null;
        int v2 = -1;
        if(this.attributes != null) {
            throw new RuntimeException("Call end() first");
        }

        this.attributes = attributes;
        this.vertices.clear();
        this.indices.clear();
        this.parts.clear();
        this.vindex = 0;
        this.istart = 0;
        this.part = v5;
        this.stride = attributes.vertexSize / 4;
        if(this.vertex == null || this.vertex.length < this.stride) {
            this.vertex = new float[this.stride];
        }

        VertexAttribute v0 = attributes.findByUsage(1);
        if(v0 == null) {
            throw new GdxRuntimeException("Cannot build mesh without position attribute");
        }

        this.posOffset = v0.offset / 4;
        this.posSize = v0.numComponents;
        v0 = attributes.findByUsage(8);
        if(v0 == null) {
            v1 = v2;
        }
        else {
            v1 = v0.offset / 4;
        }

        this.norOffset = v1;
        v0 = attributes.findByUsage(2);
        if(v0 == null) {
            v1 = v2;
        }
        else {
            v1 = v0.offset / 4;
        }

        this.colOffset = v1;
        if(v0 == null) {
            v1 = 0;
        }
        else {
            v1 = v0.numComponents;
        }

        this.colSize = v1;
        v0 = attributes.findByUsage(4);
        if(v0 == null) {
            v1 = v2;
        }
        else {
            v1 = v0.offset / 4;
        }

        this.cpOffset = v1;
        v0 = attributes.findByUsage(16);
        if(v0 != null) {
            v2 = v0.offset / 4;
        }

        this.uvOffset = v2;
        this.setColor(((Color)v5));
        this.setVertexTransform(((Matrix4)v5));
        this.setUVRange(((TextureRegion)v5));
        this.primitiveType = primitiveType;
    }

    public void begin(long attributes, int primitiveType) {
        this.begin(MeshBuilder.createAttributes(attributes), primitiveType);
    }

    public void box(float width, float height, float depth) {
        this.box(this.matTmp1.setToScaling(width, height, depth));
    }

    public void box(Matrix4 transform) {
        this.box(this.tmp(-0.5f, -0.5f, -0.5f).mul(transform), this.tmp(-0.5f, 0.5f, -0.5f).mul(transform), this.tmp(0.5f, -0.5f, -0.5f).mul(transform), this.tmp(0.5f, 0.5f, -0.5f).mul(transform), this.tmp(-0.5f, -0.5f, 0.5f).mul(transform), this.tmp(-0.5f, 0.5f, 0.5f).mul(transform), this.tmp(0.5f, -0.5f, 0.5f).mul(transform), this.tmp(0.5f, 0.5f, 0.5f).mul(transform));
        this.cleanup();
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        this.box(this.matTmp1.setToScaling(width, height, depth).trn(x, y, z));
    }

    public void box(VertexInfo corner000, VertexInfo corner010, VertexInfo corner100, VertexInfo corner110, VertexInfo corner001, VertexInfo corner011, VertexInfo corner101, VertexInfo corner111) {
        this.ensureVertices(8);
        short v2 = this.vertex(corner000);
        short v8 = this.vertex(corner100);
        short v6 = this.vertex(corner110);
        short v4 = this.vertex(corner010);
        short v3 = this.vertex(corner001);
        short v9 = this.vertex(corner101);
        short v7 = this.vertex(corner111);
        short v5 = this.vertex(corner011);
        if(this.primitiveType == 1) {
            this.ensureIndices(24);
            this.rect(v2, v8, v6, v4);
            this.rect(v9, v3, v5, v7);
            this.index(v2, v3, v4, v5, v6, v7, v8, v9);
        }
        else if(this.primitiveType == 0) {
            this.ensureRectangleIndices(2);
            this.rect(v2, v8, v6, v4);
            this.rect(v9, v3, v5, v7);
        }
        else {
            this.ensureRectangleIndices(6);
            this.rect(v2, v8, v6, v4);
            this.rect(v9, v3, v5, v7);
            this.rect(v2, v4, v5, v3);
            this.rect(v9, v7, v6, v8);
            this.rect(v9, v8, v2, v3);
            this.rect(v6, v7, v5, v4);
        }
    }

    public void box(Vector3 corner000, Vector3 corner010, Vector3 corner100, Vector3 corner110, Vector3 corner001, Vector3 corner011, Vector3 corner101, Vector3 corner111) {
        if(this.norOffset >= 0 || this.uvOffset >= 0) {
            this.ensureRectangles(6);
            Vector3 v6 = this.tempV1.set(corner000).lerp(corner110, 0.5f).sub(this.tempV2.set(corner001).lerp(corner111, 0.5f)).nor();
            this.rect(corner000, corner010, corner110, corner100, v6);
            this.rect(corner011, corner001, corner101, corner111, v6.scl(-1f));
            v6 = this.tempV1.set(corner000).lerp(corner101, 0.5f).sub(this.tempV2.set(corner010).lerp(corner111, 0.5f)).nor();
            this.rect(corner001, corner000, corner100, corner101, v6);
            this.rect(corner010, corner011, corner111, corner110, v6.scl(-1f));
            v6 = this.tempV1.set(corner000).lerp(corner011, 0.5f).sub(this.tempV2.set(corner100).lerp(corner111, 0.5f)).nor();
            this.rect(corner001, corner011, corner010, corner000, v6);
            this.rect(corner100, corner110, corner111, corner101, v6.scl(-1f));
        }
        else {
            this.box(this.vertTmp1.set(corner000, null, null, null), this.vertTmp2.set(corner010, null, null, null), this.vertTmp3.set(corner100, null, null, null), this.vertTmp4.set(corner110, null, null, null), this.vertTmp5.set(corner001, null, null, null), this.vertTmp6.set(corner011, null, null, null), this.vertTmp7.set(corner101, null, null, null), this.vertTmp8.set(corner111, null, null, null));
        }
    }

    public void capsule(float radius, float height, int divisions) {
        if(height < 2f * radius) {
            throw new GdxRuntimeException("Height must be at least twice the radius");
        }

        float v1 = 2f * radius;
        this.cylinder(v1, height - v1, v1, divisions, 0f, 360f, false);
        this.sphere(this.matTmp1.setToTranslation(0f, 0.5f * (height - v1), 0f), v1, v1, v1, divisions, divisions, 0f, 360f, 0f, 90f);
        this.sphere(this.matTmp1.setToTranslation(0f, -0.5f * (height - v1), 0f), v1, v1, v1, divisions, divisions, 0f, 360f, 90f, 180f);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        this.circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0f, 360f);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        this.ellipse(radius * 2f, radius * 2f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        this.circle(radius, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0f, 360f);
    }

    public void circle(float radius, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        this.ellipse(radius * 2f, radius * 2f, 0f, 0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal) {
        this.circle(radius, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        this.circle(radius, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, angleFrom, angleTo);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        this.circle(radius, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, tangent.x, tangent.y, tangent.z, binormal.x, binormal.y, binormal.z);
    }

    public void circle(float radius, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        this.circle(radius, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, tangent.x, tangent.y, tangent.z, binormal.x, binormal.y, binormal.z, angleFrom, angleTo);
    }

    private void cleanup() {
        MeshBuilder.vectorPool.freeAll(MeshBuilder.vectorArray);
        MeshBuilder.vectorArray.clear();
        MeshBuilder.matrices4Pool.freeAll(MeshBuilder.matrices4Array);
        MeshBuilder.matrices4Array.clear();
    }

    public void cone(float width, float height, float depth, int divisions) {
        this.cone(width, height, depth, divisions, 0f, 360f);
    }

    public void cone(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        this.ensureTriangles(divisions + 2, divisions);
        float v31 = width * 0.5f;
        float v30 = height * 0.5f;
        float v29 = depth * 0.5f;
        float v25 = 0.017453f * angleFrom;
        float v35 = 0.017453f * (angleTo - angleFrom) / (((float)divisions));
        float v37 = 1f / (((float)divisions));
        VertexInfo v27 = this.vertTmp3.set(null, null, null, null);
        v27.hasNormal = true;
        v27.hasPosition = true;
        v27.hasUV = true;
        short v26 = this.vertex(this.vertTmp4.set(null, null, null, null).setPos(0f, v30, 0f).setNor(0f, 1f, 0f).setUV(0.5f, 0f));
        short v34 = 0;
        int v32;
        for(v32 = 0; v32 <= divisions; ++v32) {
            float v24 = v25 + (((float)v32)) * v35;
            v27.position.set(MathUtils.cos(v24) * v31, 0f, MathUtils.sin(v24) * v29);
            v27.normal.set(v27.position).nor();
            v27.position.y = -v30;
            v27.uv.set(1f - (((float)v32)) * v37, 1f);
            short v33 = this.vertex(v27);
            if(v32 != 0) {
                this.triangle(v26, v33, v34);
            }

            v34 = v33;
        }

        this.ellipse(width, depth, 0f, 0f, divisions, 0f, -v30, 0f, 0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f, 180f - angleTo, 180f - angleFrom);
    }

    public static VertexAttributes createAttributes(long usage) {
        long v12 = 2;
        int v10 = 3;
        int v9 = 2;
        int v8 = 4;
        Array v1 = new Array();
        if((usage & 1) == 1) {
            v1.add(new VertexAttribute(1, v10, "a_position"));
        }

        if((usage & v12) == v12) {
            v1.add(new VertexAttribute(v9, v8, "a_color"));
        }

        if((4 & usage) == 4) {
            v1.add(new VertexAttribute(v8, v8, "a_color"));
        }

        if((8 & usage) == 8) {
            v1.add(new VertexAttribute(8, v10, "a_normal"));
        }

        if((16 & usage) == 16) {
            v1.add(new VertexAttribute(16, v9, "a_texCoord0"));
        }

        VertexAttribute[] v0 = new VertexAttribute[v1.size];
        int v2;
        for(v2 = 0; v2 < v0.length; ++v2) {
            v0[v2] = v1.get(v2);
        }

        return new VertexAttributes(v0);
    }

    public void cylinder(float width, float height, float depth, int divisions) {
        this.cylinder(width, height, depth, divisions, 0f, 360f);
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo, boolean close) {
        float v38 = width * 0.5f;
        float v12 = height * 0.5f;
        float v37 = depth * 0.5f;
        float v34 = 0.017453f * angleFrom;
        float v44 = 0.017453f * (angleTo - angleFrom) / (((float)divisions));
        float v46 = 1f / (((float)divisions));
        VertexInfo v35 = this.vertTmp3.set(null, null, null, null);
        v35.hasNormal = true;
        v35.hasPosition = true;
        v35.hasUV = true;
        VertexInfo v36 = this.vertTmp4.set(null, null, null, null);
        v36.hasNormal = true;
        v36.hasPosition = true;
        v36.hasUV = true;
        short v42 = 0;
        short v43 = 0;
        this.ensureRectangles((divisions + 1) * 2, divisions);
        int v39;
        for(v39 = 0; v39 <= divisions; ++v39) {
            float v33 = v34 + (((float)v39)) * v44;
            float v45 = 1f - (((float)v39)) * v46;
            v35.position.set(MathUtils.cos(v33) * v38, 0f, MathUtils.sin(v33) * v37);
            v35.normal.set(v35.position).nor();
            v35.position.y = -v12;
            v35.uv.set(v45, 1f);
            v36.position.set(v35.position);
            v36.normal.set(v35.normal);
            v36.position.y = v12;
            v36.uv.set(v45, 0f);
            short v41 = this.vertex(v35);
            short v40 = this.vertex(v36);
            if(v39 != 0) {
                this.rect(v42, v40, v41, v43);
            }

            v43 = v41;
            v42 = v40;
        }

        if(close) {
            this.ellipse(width, depth, 0f, 0f, divisions, 0f, v12, 0f, 0f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, angleFrom, angleTo);
            this.ellipse(width, depth, 0f, 0f, divisions, 0f, -v12, 0f, 0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f, 180f - angleTo, 180f - angleFrom);
        }
    }

    public void cylinder(float width, float height, float depth, int divisions, float angleFrom, float angleTo) {
        this.cylinder(width, height, depth, divisions, angleFrom, angleTo, true);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        this.ellipse(width, height, 0f, 0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        if(innerWidth <= 0f || innerHeight <= 0f) {
            this.ensureTriangles(divisions + 2, divisions);
        }
        else {
            if(innerWidth == width && innerHeight == height) {
                this.ensureVertices(divisions + 1);
                this.ensureIndices(divisions + 1);
                if(this.primitiveType != 1) {
                    throw new GdxRuntimeException("Incorrect primitive type : expect GL_LINES because innerWidth == width && innerHeight == height");
                }
                else {
                    goto label_9;
                }
            }

            this.ensureRectangles((divisions + 1) * 2, divisions + 1);
        }

    label_9:
        float v5 = 0.017453f * angleFrom;
        float v14 = 0.017453f * (angleTo - angleFrom) / (((float)divisions));
        Vector3 v15 = this.tempV1.set(tangentX, tangentY, tangentZ).scl(0.5f * width);
        Vector3 v17 = this.tempV2.set(binormalX, binormalY, binormalZ).scl(0.5f * height);
        Vector3 v16 = this.tempV3.set(tangentX, tangentY, tangentZ).scl(0.5f * innerWidth);
        Vector3 v18 = this.tempV4.set(binormalX, binormalY, binormalZ).scl(0.5f * innerHeight);
        VertexInfo v8 = this.vertTmp3.set(null, null, null, null);
        v8.hasNormal = true;
        v8.hasPosition = true;
        v8.hasUV = true;
        v8.uv.set(0.5f, 0.5f);
        v8.position.set(centerX, centerY, centerZ);
        v8.normal.set(normalX, normalY, normalZ);
        VertexInfo v7 = this.vertTmp4.set(null, null, null, null);
        v7.hasNormal = true;
        v7.hasPosition = true;
        v7.hasUV = true;
        v7.uv.set(0.5f, 0.5f);
        v7.position.set(centerX, centerY, centerZ);
        v7.normal.set(normalX, normalY, normalZ);
        short v6 = this.vertex(v7);
        float v19 = 0.5f * (innerWidth / width);
        float v20 = 0.5f * (innerHeight / height);
        short v11 = 0;
        short v12 = 0;
        short v13 = 0;
        int v9;
        for(v9 = 0; v9 <= divisions; ++v9) {
            float v4 = v5 + (((float)v9)) * v14;
            float v21 = MathUtils.cos(v4);
            float v22 = MathUtils.sin(v4);
            v7.position.set(centerX, centerY, centerZ).add(v15.x * v21 + v17.x * v22, v15.y * v21 + v17.y * v22, v15.z * v21 + v17.z * v22);
            v7.uv.set(0.5f + 0.5f * v21, 0.5f + 0.5f * v22);
            short v10 = this.vertex(v7);
            if(innerWidth <= 0f || innerHeight <= 0f) {
                if(v9 != 0) {
                    this.triangle(v10, v11, v6);
                }

                v11 = v10;
            }
            else {
                if(innerWidth == width && innerHeight == height) {
                    if(v9 != 0) {
                        this.line(v10, v11);
                    }

                    v11 = v10;
                    goto label_204;
                }

                v8.position.set(centerX, centerY, centerZ).add(v16.x * v21 + v18.x * v22, v16.y * v21 + v18.y * v22, v16.z * v21 + v18.z * v22);
                v8.uv.set(0.5f + v19 * v21, 0.5f + v20 * v22);
                v11 = v10;
                v10 = this.vertex(v8);
                if(v9 != 0) {
                    this.rect(v10, v11, v13, v12);
                }

                v13 = v11;
                v12 = v10;
            }

        label_204:
        }
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        this.ellipse(width, height, innerWidth, innerHeight, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0f, 360f);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float angleFrom, float angleTo) {
        this.tempV1.set(normalX, normalY, normalZ).crs(0f, 0f, 1f);
        this.tempV2.set(normalX, normalY, normalZ).crs(0f, 1f, 0f);
        if(this.tempV2.len2() > this.tempV1.len2()) {
            this.tempV1.set(this.tempV2);
        }

        this.tempV2.set(this.tempV1.nor()).crs(normalX, normalY, normalZ).nor();
        this.ellipse(width, height, innerWidth, innerHeight, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, this.tempV1.x, this.tempV1.y, this.tempV1.z, this.tempV2.x, this.tempV2.y, this.tempV2.z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, float innerWidth, float innerHeight, int divisions, Vector3 center, Vector3 normal) {
        this.ellipse(width, height, innerWidth, innerHeight, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, 0f, 360f);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ) {
        this.ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, 0f, 360f);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ) {
        this.ellipse(width, height, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, 0f, 360f);
    }

    public void ellipse(float width, float height, int divisions, float centerX, float centerY, float centerZ, float normalX, float normalY, float normalZ, float tangentX, float tangentY, float tangentZ, float binormalX, float binormalY, float binormalZ, float angleFrom, float angleTo) {
        this.ellipse(width, height, 0f, 0f, divisions, centerX, centerY, centerZ, normalX, normalY, normalZ, tangentX, tangentY, tangentZ, binormalX, binormalY, binormalZ, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal) {
        this.ellipse(width, height, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, float angleFrom, float angleTo) {
        this.ellipse(width, height, 0f, 0f, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, angleFrom, angleTo);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal) {
        this.ellipse(width, height, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, tangent.x, tangent.y, tangent.z, binormal.x, binormal.y, binormal.z);
    }

    public void ellipse(float width, float height, int divisions, Vector3 center, Vector3 normal, Vector3 tangent, Vector3 binormal, float angleFrom, float angleTo) {
        this.ellipse(width, height, 0f, 0f, divisions, center.x, center.y, center.z, normal.x, normal.y, normal.z, tangent.x, tangent.y, tangent.z, binormal.x, binormal.y, binormal.z, angleFrom, angleTo);
    }

    public Mesh end() {
        return this.end(new Mesh(true, this.vertices.size / this.stride, this.indices.size, this.attributes));
    }

    public Mesh end(Mesh mesh) {
        this.endpart();
        if(this.attributes == null) {
            throw new GdxRuntimeException("Call begin() first");
        }

        if(!this.attributes.equals(mesh.getVertexAttributes())) {
            throw new GdxRuntimeException("Mesh attributes don\'t match");
        }

        if(mesh.getMaxVertices() * this.stride < this.vertices.size) {
            throw new GdxRuntimeException("Mesh can\'t hold enough vertices: " + mesh.getMaxVertices() + " * " + this.stride + " < " + this.vertices.size);
        }

        if(mesh.getMaxIndices() < this.indices.size) {
            throw new GdxRuntimeException("Mesh can\'t hold enough indices: " + mesh.getMaxIndices() + " < " + this.indices.size);
        }

        mesh.setVertices(this.vertices.items, 0, this.vertices.size);
        mesh.setIndices(this.indices.items, 0, this.indices.size);
        Iterator v0 = this.parts.iterator();
        while(v0.hasNext()) {
            v0.next().mesh = mesh;
        }

        this.parts.clear();
        this.attributes = null;
        this.vertices.clear();
        this.indices.clear();
        return mesh;
    }

    private void endpart() {
        if(this.part != null) {
            this.part.indexOffset = this.istart;
            this.part.numVertices = this.indices.size - this.istart;
            this.istart = this.indices.size;
            this.part = null;
        }
    }

    public void ensureCapacity(int numVertices, int numIndices) {
        this.ensureVertices(numVertices);
        this.ensureIndices(numIndices);
    }

    public void ensureIndices(int numIndices) {
        this.indices.ensureCapacity(numIndices);
    }

    public void ensureRectangleIndices(int numRectangles) {
        if(this.primitiveType == 0) {
            this.ensureIndices(numRectangles * 4);
        }
        else if(this.primitiveType == 1) {
            this.ensureIndices(numRectangles * 8);
        }
        else {
            this.ensureIndices(numRectangles * 6);
        }
    }

    public void ensureRectangles(int numRectangles) {
        this.ensureRectangles(numRectangles * 4, numRectangles);
    }

    public void ensureRectangles(int numVertices, int numRectangles) {
        this.ensureVertices(numVertices);
        this.ensureRectangleIndices(numRectangles);
    }

    public void ensureTriangleIndices(int numTriangles) {
        if(this.primitiveType == 1) {
            this.ensureIndices(numTriangles * 6);
        }
        else {
            if(this.primitiveType != 4 && this.primitiveType != 0) {
                throw new GdxRuntimeException("Incorrect primtive type");
            }

            this.ensureIndices(numTriangles * 3);
        }
    }

    public void ensureTriangles(int numVertices, int numTriangles) {
        this.ensureVertices(numVertices);
        this.ensureTriangleIndices(numTriangles);
    }

    public void ensureTriangles(int numTriangles) {
        this.ensureTriangles(numTriangles * 3, numTriangles);
    }

    public void ensureVertices(int numVertices) {
        this.vertices.ensureCapacity(this.stride * numVertices);
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }

    public MeshPart getMeshPart() {
        return this.part;
    }

    public int getNumIndices() {
        return this.indices.size;
    }

    public int getNumVertices() {
        return this.vertices.size / this.stride;
    }

    public Matrix4 getVertexTransform(Matrix4 out) {
        return out.set(this.positionTransform);
    }

    public void index(short value) {
        this.indices.add(value);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6, short value7, short value8) {
        this.ensureIndices(8);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
        this.indices.add(value7);
        this.indices.add(value8);
    }

    public void index(short value1, short value2) {
        this.ensureIndices(2);
        this.indices.add(value1);
        this.indices.add(value2);
    }

    public void index(short value1, short value2, short value3) {
        this.ensureIndices(3);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
    }

    public void index(short value1, short value2, short value3, short value4) {
        this.ensureIndices(4);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
    }

    public void index(short value1, short value2, short value3, short value4, short value5, short value6) {
        this.ensureIndices(6);
        this.indices.add(value1);
        this.indices.add(value2);
        this.indices.add(value3);
        this.indices.add(value4);
        this.indices.add(value5);
        this.indices.add(value6);
    }

    public boolean isVertexTransformationEnabled() {
        return this.vertexTransformationEnabled;
    }

    public short lastIndex() {
        return this.lastIndex;
    }

    public void line(short index1, short index2) {
        if(this.primitiveType != 1) {
            throw new GdxRuntimeException("Incorrect primitive type");
        }

        this.index(index1, index2);
    }

    public void line(float x1, float y1, float z1, float x2, float y2, float z2) {
        this.line(this.vertTmp1.set(null, null, null, null).setPos(x1, y1, z1), this.vertTmp2.set(null, null, null, null).setPos(x2, y2, z2));
    }

    public void line(VertexInfo p1, VertexInfo p2) {
        this.ensureVertices(2);
        this.line(this.vertex(p1), this.vertex(p2));
    }

    public void line(Vector3 p1, Color c1, Vector3 p2, Color c2) {
        this.line(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null));
    }

    public void line(Vector3 p1, Vector3 p2) {
        this.line(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null));
    }

    public MeshPart part(String id, int primitiveType) {
        return this.part(id, primitiveType, new MeshPart());
    }

    public MeshPart part(String id, int primitiveType, MeshPart meshPart) {
        Color v2 = null;
        if(this.attributes == null) {
            throw new RuntimeException("Call begin() first");
        }

        this.endpart();
        this.part = meshPart;
        this.part.id = id;
        this.part.primitiveType = primitiveType;
        this.primitiveType = primitiveType;
        this.parts.add(this.part);
        this.setColor(v2);
        this.setVertexTransform(((Matrix4)v2));
        this.setUVRange(((TextureRegion)v2));
        return this.part;
    }

    public void patch(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ, int divisionsU, int divisionsV) {
        this.patch(this.vertTmp1.set(null).setPos(x00, y00, z00).setNor(normalX, normalY, normalZ).setUV(0f, 1f), this.vertTmp2.set(null).setPos(x10, y10, z10).setNor(normalX, normalY, normalZ).setUV(1f, 1f), this.vertTmp3.set(null).setPos(x11, y11, z11).setNor(normalX, normalY, normalZ).setUV(1f, 0f), this.vertTmp4.set(null).setPos(x01, y01, z01).setNor(normalX, normalY, normalZ).setUV(0f, 0f), divisionsU, divisionsV);
    }

    public void patch(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01, int divisionsU, int divisionsV) {
        if(divisionsU >= 1 && divisionsV >= 1) {
            this.ensureRectangles((divisionsV + 1) * (divisionsU + 1), divisionsV * divisionsU);
            int v2;
            for(v2 = 0; v2 <= divisionsU; ++v2) {
                float v0 = (((float)v2)) / (((float)divisionsU));
                this.vertTmp5.set(corner00).lerp(corner10, v0);
                this.vertTmp6.set(corner01).lerp(corner11, v0);
                int v3;
                for(v3 = 0; v3 <= divisionsV; ++v3) {
                    short v1 = this.vertex(this.vertTmp7.set(this.vertTmp5).lerp(this.vertTmp6, (((float)v3)) / (((float)divisionsV))));
                    if(v2 > 0 && v3 > 0) {
                        this.rect(((short)(v1 - divisionsV - 2)), ((short)(v1 - 1)), v1, ((short)(v1 - divisionsV - 1)));
                    }
                }
            }

            return;
        }

        throw new GdxRuntimeException("divisionsU and divisionV must be > 0, u,v: " + divisionsU + ", " + divisionsV);
    }

    public void patch(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal, int divisionsU, int divisionsV) {
        this.patch(this.vertTmp1.set(corner00, normal, null, null).setUV(0f, 1f), this.vertTmp2.set(corner10, normal, null, null).setUV(1f, 1f), this.vertTmp3.set(corner11, normal, null, null).setUV(1f, 0f), this.vertTmp4.set(corner01, normal, null, null).setUV(0f, 0f), divisionsU, divisionsV);
    }

    public void rect(short corner00, short corner10, short corner11, short corner01) {
        if(this.primitiveType == 4) {
            this.index(corner00, corner10, corner11, corner11, corner01, corner00);
        }
        else if(this.primitiveType == 1) {
            this.index(corner00, corner10, corner10, corner11, corner11, corner01, corner01, corner00);
        }
        else if(this.primitiveType == 0) {
            this.index(corner00, corner10, corner11, corner01);
        }
        else {
            goto label_30;
        }

        return;
    label_30:
        throw new GdxRuntimeException("Incorrect primitive type");
    }

    public void rect(Vector3 corner00, Vector3 corner10, Vector3 corner11, Vector3 corner01, Vector3 normal) {
        this.rect(this.vertTmp1.set(corner00, normal, null, null).setUV(0f, 1f), this.vertTmp2.set(corner10, normal, null, null).setUV(1f, 1f), this.vertTmp3.set(corner11, normal, null, null).setUV(1f, 0f), this.vertTmp4.set(corner01, normal, null, null).setUV(0f, 0f));
    }

    public void rect(float x00, float y00, float z00, float x10, float y10, float z10, float x11, float y11, float z11, float x01, float y01, float z01, float normalX, float normalY, float normalZ) {
        this.rect(this.vertTmp1.set(null, null, null, null).setPos(x00, y00, z00).setNor(normalX, normalY, normalZ).setUV(0f, 1f), this.vertTmp2.set(null, null, null, null).setPos(x10, y10, z10).setNor(normalX, normalY, normalZ).setUV(1f, 1f), this.vertTmp3.set(null, null, null, null).setPos(x11, y11, z11).setNor(normalX, normalY, normalZ).setUV(1f, 0f), this.vertTmp4.set(null, null, null, null).setPos(x01, y01, z01).setNor(normalX, normalY, normalZ).setUV(0f, 0f));
    }

    public void rect(VertexInfo corner00, VertexInfo corner10, VertexInfo corner11, VertexInfo corner01) {
        this.ensureVertices(4);
        this.rect(this.vertex(corner00), this.vertex(corner10), this.vertex(corner11), this.vertex(corner01));
    }

    public void setColor(Color color) {
        boolean v0;
        Color v1 = this.color;
        if(color != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasColor = v0;
        if(!v0) {
            color = Color.WHITE;
        }

        v1.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        boolean v0;
        this.color.set(r, g, b, a);
        if(!this.color.equals(Color.WHITE)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasColor = v0;
    }

    public void setUVRange(TextureRegion region) {
        boolean v0;
        if(region != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasUVTransform = v0;
        if(!v0) {
            this.vOffset = 0f;
            this.uOffset = 0f;
            this.vScale = 1f;
            this.uScale = 1f;
        }
        else {
            this.setUVRange(region.getU(), region.getV(), region.getU2(), region.getV2());
        }
    }

    public void setUVRange(float u1, float v1, float u2, float v2) {
        boolean v0;
        float v1 = 1f;
        this.uOffset = u1;
        this.vOffset = v1;
        this.uScale = u2 - u1;
        this.vScale = v2 - v1;
        if(!MathUtils.isZero(u1) || !MathUtils.isZero(v1) || !MathUtils.isEqual(u2, v1) || !MathUtils.isEqual(v2, v1)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.hasUVTransform = v0;
    }

    public void setVertexTransform(Matrix4 transform) {
        boolean v0;
        if(transform != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.vertexTransformationEnabled = v0;
        if(v0) {
            this.positionTransform.set(transform);
            this.normalTransform.set(transform).inv().transpose();
        }
        else {
            this.positionTransform.idt();
            this.normalTransform.idt();
        }
    }

    public void setVertexTransformationEnabled(boolean enabled) {
        this.vertexTransformationEnabled = enabled;
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        float v13 = width * 0.5f;
        float v12 = height * 0.5f;
        float v11 = depth * 0.5f;
        float v7 = 0.017453f * angleUFrom;
        float v18 = 0.017453f * (angleUTo - angleUFrom) / (((float)divisionsU));
        float v8 = 0.017453f * angleVFrom;
        float v19 = 0.017453f * (angleVTo - angleVFrom) / (((float)divisionsV));
        float v23 = 1f / (((float)divisionsU));
        float v25 = 1f / (((float)divisionsV));
        VertexInfo v9 = this.vertTmp3.set(null, null, null, null);
        v9.hasNormal = true;
        v9.hasPosition = true;
        v9.hasUV = true;
        int v17 = divisionsU + 3;
        MeshBuilder.tmpIndices.clear();
        MeshBuilder.tmpIndices.ensureCapacity(divisionsU * 2);
        MeshBuilder.tmpIndices.size = v17;
        int v21 = 0;
        this.ensureRectangles((divisionsV + 1) * (divisionsU + 1), divisionsV * divisionsU);
        int v15;
        for(v15 = 0; v15 <= divisionsV; ++v15) {
            float v6 = v8 + (((float)v15)) * v19;
            float v24 = v25 * (((float)v15));
            float v20 = MathUtils.sin(v6);
            float v10 = MathUtils.cos(v6) * v12;
            int v14;
            for(v14 = 0; v14 <= divisionsU; ++v14) {
                float v5 = v7 + (((float)v14)) * v18;
                v9.position.set(MathUtils.cos(v5) * v13 * v20, v10, MathUtils.sin(v5) * v11 * v20).mul(transform);
                v9.normal.set(v9.position).nor();
                v9.uv.set(1f - (((float)v14)) * v23, v24);
                MeshBuilder.tmpIndices.set(v21, this.vertex(v9));
                int v16 = v21 + v17;
                if(v15 > 0 && v14 > 0) {
                    this.rect(MeshBuilder.tmpIndices.get(v21), MeshBuilder.tmpIndices.get((v16 - 1) % v17), MeshBuilder.tmpIndices.get((v16 - (divisionsU + 2)) % v17), MeshBuilder.tmpIndices.get((v16 - (divisionsU + 1)) % v17));
                }

                v21 = (v21 + 1) % MeshBuilder.tmpIndices.size;
            }
        }
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV) {
        this.sphere(width, height, depth, divisionsU, divisionsV, 0f, 360f, 0f, 180f);
    }

    public void sphere(float width, float height, float depth, int divisionsU, int divisionsV, float angleUFrom, float angleUTo, float angleVFrom, float angleVTo) {
        this.sphere(this.matTmp1.idt(), width, height, depth, divisionsU, divisionsV, angleUFrom, angleUTo, angleVFrom, angleVTo);
    }

    public void sphere(Matrix4 transform, float width, float height, float depth, int divisionsU, int divisionsV) {
        this.sphere(transform, width, height, depth, divisionsU, divisionsV, 0f, 360f, 0f, 180f);
    }

    private Matrix4 tmp() {
        Matrix4 v0 = MeshBuilder.matrices4Pool.obtain().idt();
        MeshBuilder.matrices4Array.add(v0);
        return v0;
    }

    private Matrix4 tmp(Matrix4 copyFrom) {
        return this.tmp().set(copyFrom);
    }

    private Vector3 tmp(float x, float y, float z) {
        Vector3 v0 = MeshBuilder.vectorPool.obtain().set(x, y, z);
        MeshBuilder.vectorArray.add(v0);
        return v0;
    }

    private Vector3 tmp(Vector3 copyFrom) {
        return this.tmp(copyFrom.x, copyFrom.y, copyFrom.z);
    }

    private static final void transformNormal(float[] values, int offset, int size, Matrix3 transform) {
        if(size > 2) {
            MeshBuilder.vTmp.set(values[offset], values[offset + 1], values[offset + 2]).mul(transform).nor();
            values[offset] = MeshBuilder.vTmp.x;
            values[offset + 1] = MeshBuilder.vTmp.y;
            values[offset + 2] = MeshBuilder.vTmp.z;
        }
        else if(size > 1) {
            MeshBuilder.vTmp.set(values[offset], values[offset + 1], 0f).mul(transform).nor();
            values[offset] = MeshBuilder.vTmp.x;
            values[offset + 1] = MeshBuilder.vTmp.y;
        }
        else {
            values[offset] = MeshBuilder.vTmp.set(values[offset], 0f, 0f).mul(transform).nor().x;
        }
    }

    private static final void transformPosition(float[] values, int offset, int size, Matrix4 transform) {
        if(size > 2) {
            MeshBuilder.vTmp.set(values[offset], values[offset + 1], values[offset + 2]).mul(transform);
            values[offset] = MeshBuilder.vTmp.x;
            values[offset + 1] = MeshBuilder.vTmp.y;
            values[offset + 2] = MeshBuilder.vTmp.z;
        }
        else if(size > 1) {
            MeshBuilder.vTmp.set(values[offset], values[offset + 1], 0f).mul(transform);
            values[offset] = MeshBuilder.vTmp.x;
            values[offset + 1] = MeshBuilder.vTmp.y;
        }
        else {
            values[offset] = MeshBuilder.vTmp.set(values[offset], 0f, 0f).mul(transform).x;
        }
    }

    public void triangle(short index1, short index2, short index3) {
        if(this.primitiveType == 4 || this.primitiveType == 0) {
            this.index(index1, index2, index3);
        }
        else if(this.primitiveType == 1) {
            this.index(index1, index2, index2, index3, index3, index1);
        }
        else {
            throw new GdxRuntimeException("Incorrect primitive type");
        }
    }

    public void triangle(VertexInfo p1, VertexInfo p2, VertexInfo p3) {
        this.ensureVertices(3);
        this.triangle(this.vertex(p1), this.vertex(p2), this.vertex(p3));
    }

    public void triangle(Vector3 p1, Color c1, Vector3 p2, Color c2, Vector3 p3, Color c3) {
        this.triangle(this.vertTmp1.set(p1, null, c1, null), this.vertTmp2.set(p2, null, c2, null), this.vertTmp3.set(p3, null, c3, null));
    }

    public void triangle(Vector3 p1, Vector3 p2, Vector3 p3) {
        this.triangle(this.vertTmp1.set(p1, null, null, null), this.vertTmp2.set(p2, null, null, null), this.vertTmp3.set(p3, null, null, null));
    }

    public short vertex(VertexInfo info) {
        Color v3;
        Vector3 v2;
        Vector3 v0;
        Vector2 v1 = null;
        if(info.hasPosition) {
            v0 = info.position;
        }
        else {
            v0 = ((Vector3)v1);
        }

        if(info.hasNormal) {
            v2 = info.normal;
        }
        else {
            v2 = ((Vector3)v1);
        }

        if(info.hasColor) {
            v3 = info.color;
        }
        else {
            v3 = ((Color)v1);
        }

        if(info.hasUV) {
            v1 = info.uv;
        }

        return this.vertex(v0, v2, v3, v1);
    }

    public short vertex(Vector3 pos, Vector3 nor, Color col, Vector2 uv) {
        if(this.vindex >= 32767) {
            throw new GdxRuntimeException("Too many vertices used");
        }

        this.vertex[this.posOffset] = pos.x;
        if(this.posSize > 1) {
            this.vertex[this.posOffset + 1] = pos.y;
        }

        if(this.posSize > 2) {
            this.vertex[this.posOffset + 2] = pos.z;
        }

        if(this.norOffset >= 0) {
            if(nor == null) {
                nor = this.tmpNormal.set(pos).nor();
            }

            this.vertex[this.norOffset] = nor.x;
            this.vertex[this.norOffset + 1] = nor.y;
            this.vertex[this.norOffset + 2] = nor.z;
        }

        if(this.colOffset >= 0) {
            if(col == null) {
                col = Color.WHITE;
            }

            this.vertex[this.colOffset] = col.r;
            this.vertex[this.colOffset + 1] = col.g;
            this.vertex[this.colOffset + 2] = col.b;
            if(this.colSize <= 3) {
                goto label_72;
            }

            this.vertex[this.colOffset + 3] = col.a;
        }
        else {
            if(this.cpOffset <= 0) {
                goto label_72;
            }

            if(col == null) {
                col = Color.WHITE;
            }

            this.vertex[this.cpOffset] = col.toFloatBits();
        }

    label_72:
        if(uv != null && this.uvOffset >= 0) {
            this.vertex[this.uvOffset] = uv.x;
            this.vertex[this.uvOffset + 1] = uv.y;
        }

        this.addVertex(this.vertex, 0);
        return this.lastIndex;
    }

    public short vertex(float[] values) {
        int v1 = values.length - this.stride;
        int v0;
        for(v0 = 0; v0 <= v1; v0 += this.stride) {
            this.addVertex(values, v0);
        }

        return this.lastIndex;
    }
}

