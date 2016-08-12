// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.graphics.glutils.IndexBufferObject;
import com.badlogic.gdx.graphics.glutils.IndexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.IndexData;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.VertexArray;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectSubData;
import com.badlogic.gdx.graphics.glutils.VertexBufferObjectWithVAO;
import com.badlogic.gdx.graphics.glutils.VertexData;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Mesh implements Disposable {
    public enum VertexDataType {
        public static final enum VertexDataType VertexArray;
        public static final enum VertexDataType VertexBufferObject;
        public static final enum VertexDataType VertexBufferObjectSubData;
        public static final enum VertexDataType VertexBufferObjectWithVAO;

        static  {
            VertexDataType.VertexArray = new VertexDataType("VertexArray", 0);
            VertexDataType.VertexBufferObject = new VertexDataType("VertexBufferObject", 1);
            VertexDataType.VertexBufferObjectSubData = new VertexDataType("VertexBufferObjectSubData", 2);
            VertexDataType.VertexBufferObjectWithVAO = new VertexDataType("VertexBufferObjectWithVAO", 3);
            VertexDataType[] v0 = new VertexDataType[4];
            v0[0] = VertexDataType.VertexArray;
            v0[1] = VertexDataType.VertexBufferObject;
            v0[2] = VertexDataType.VertexBufferObjectSubData;
            v0[3] = VertexDataType.VertexBufferObjectWithVAO;
            VertexDataType.$VALUES = v0;
        }

        private VertexDataType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static VertexDataType valueOf(String name) {
            return Enum.valueOf(VertexDataType.class, name);
        }

        public static VertexDataType[] values() {
            return VertexDataType.$VALUES.clone();
        }
    }

    boolean autoBind;
    final IndexData indices;
    final boolean isVertexArray;
    static final Map meshes;
    private final Vector3 tmpV;
    final VertexData vertices;

    static  {
        Mesh.meshes = new HashMap();
    }

    public Mesh(VertexDataType type, boolean isStatic, int maxVertices, int maxIndices, VertexAttribute[] attributes) {
        super();
        this.autoBind = true;
        this.tmpV = new Vector3();
        switch(com.badlogic.gdx.graphics.Mesh$1.$SwitchMap$com$badlogic$gdx$graphics$Mesh$VertexDataType[type.ordinal()]) {
            case 1: {
                this.vertices = new VertexBufferObject(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObject(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            }
            case 2: {
                this.vertices = new VertexBufferObjectSubData(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObjectSubData(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            }
            case 3: {
                this.vertices = new VertexBufferObjectWithVAO(isStatic, maxVertices, attributes);
                this.indices = new IndexBufferObjectSubData(isStatic, maxIndices);
                this.isVertexArray = false;
                break;
            }
            default: {
                this.vertices = new VertexArray(maxVertices, attributes);
                this.indices = new IndexArray(maxIndices);
                this.isVertexArray = true;
                break;
            }
        }

        Mesh.addManagedMesh(Gdx.app, this);
    }

    protected Mesh(VertexData vertices, IndexData indices, boolean isVertexArray) {
        super();
        this.autoBind = true;
        this.tmpV = new Vector3();
        this.vertices = vertices;
        this.indices = indices;
        this.isVertexArray = isVertexArray;
        Mesh.addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttributes attributes) {
        super();
        this.autoBind = true;
        this.tmpV = new Vector3();
        this.vertices = this.makeVertexBuffer(isStatic, maxVertices, attributes);
        this.indices = new IndexBufferObject(isStatic, maxIndices);
        this.isVertexArray = false;
        Mesh.addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean isStatic, int maxVertices, int maxIndices, VertexAttribute[] attributes) {
        super();
        this.autoBind = true;
        this.tmpV = new Vector3();
        this.vertices = this.makeVertexBuffer(isStatic, maxVertices, new VertexAttributes(attributes));
        this.indices = new IndexBufferObject(isStatic, maxIndices);
        this.isVertexArray = false;
        Mesh.addManagedMesh(Gdx.app, this);
    }

    public Mesh(boolean staticVertices, boolean staticIndices, int maxVertices, int maxIndices, VertexAttributes attributes) {
        super();
        this.autoBind = true;
        this.tmpV = new Vector3();
        this.vertices = this.makeVertexBuffer(staticVertices, maxVertices, attributes);
        this.indices = new IndexBufferObject(staticIndices, maxIndices);
        this.isVertexArray = false;
        Mesh.addManagedMesh(Gdx.app, this);
    }

    private static void addManagedMesh(Application app, Mesh mesh) {
        Object v0 = Mesh.meshes.get(app);
        if(v0 == null) {
            Array v0_1 = new Array();
        }

        ((Array)v0).add(mesh);
        Mesh.meshes.put(app, v0);
    }

    public void bind(ShaderProgram shader) {
        this.bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        this.vertices.bind(shader, locations);
        if(this.indices.getNumIndices() > 0) {
            this.indices.bind();
        }
    }

    public BoundingBox calculateBoundingBox() {
        BoundingBox v0 = new BoundingBox();
        this.calculateBoundingBox(v0);
        return v0;
    }

    public void calculateBoundingBox(BoundingBox bbox) {
        int v2 = this.getNumVertices();
        if(v2 == 0) {
            throw new GdxRuntimeException("No vertices defined");
        }

        FloatBuffer v6 = this.vertices.getBuffer();
        bbox.inf();
        VertexAttribute v4 = this.getVertexAttribute(1);
        int v3 = v4.offset / 4;
        int v5 = this.vertices.getAttributes().vertexSize / 4;
        int v1 = v3;
        switch(v4.numComponents) {
            case 1: {
                int v0;
                for(v0 = 0; v0 < v2; ++v0) {
                    bbox.ext(v6.get(v1), 0f, 0f);
                    v1 += v5;
                }
            }
            case 2: {
                for(v0 = 0; v0 < v2; ++v0) {
                    bbox.ext(v6.get(v1), v6.get(v1 + 1), 0f);
                    v1 += v5;
                }
            }
            case 3: {
                for(v0 = 0; v0 < v2; ++v0) {
                    bbox.ext(v6.get(v1), v6.get(v1 + 1), v6.get(v1 + 2));
                    v1 += v5;
                }
            }
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count) {
        return this.extendBoundingBox(out.inf(), offset, count);
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        return this.extendBoundingBox(out.inf(), offset, count, transform);
    }

    public float calculateRadius(float centerX, float centerY, float centerZ) {
        return this.calculateRadius(centerX, centerY, centerZ, 0, this.getNumIndices(), null);
    }

    public float calculateRadius(float centerX, float centerY, float centerZ, int offset, int count, Matrix4 transform) {
        return ((float)Math.sqrt(((double)this.calculateRadiusSquared(centerX, centerY, centerZ, offset, count, transform))));
    }

    public float calculateRadius(float centerX, float centerY, float centerZ, int offset, int count) {
        return this.calculateRadius(centerX, centerY, centerZ, offset, count, null);
    }

    public float calculateRadius(Vector3 center) {
        return this.calculateRadius(center.x, center.y, center.z, 0, this.getNumIndices(), null);
    }

    public float calculateRadius(Vector3 center, int offset, int count) {
        return this.calculateRadius(center.x, center.y, center.z, offset, count, null);
    }

    public float calculateRadius(Vector3 center, int offset, int count, Matrix4 transform) {
        return this.calculateRadius(center.x, center.y, center.z, offset, count, transform);
    }

    public float calculateRadiusSquared(float centerX, float centerY, float centerZ, int offset, int count, Matrix4 transform) {
        float v10;
        int v5;
        int v7 = this.getNumIndices();
        if(offset >= 0 && count >= 1 && offset + count <= v7) {
            FloatBuffer v13 = this.vertices.getBuffer();
            ShortBuffer v6 = this.indices.getBuffer();
            VertexAttribute v8 = this.getVertexAttribute(1);
            int v9 = v8.offset / 4;
            int v12 = this.vertices.getAttributes().vertexSize / 4;
            int v3 = offset + count;
            float v11 = 0f;
            switch(v8.numComponents) {
                case 1: {
                    for(v4 = offset; v4 < v3; ++v4) {
                        this.tmpV.set(v13.get(v6.get(v4) * v12 + v9), 0f, 0f);
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        v10 = this.tmpV.sub(centerX, centerY, centerZ).len2();
                        if(v10 > v11) {
                            v11 = v10;
                        }
                    }
                }
                case 2: {
                    for(v4 = offset; v4 < v3; ++v4) {
                        v5 = v6.get(v4) * v12 + v9;
                        this.tmpV.set(v13.get(v5), v13.get(v5 + 1), 0f);
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        v10 = this.tmpV.sub(centerX, centerY, centerZ).len2();
                        if(v10 > v11) {
                            v11 = v10;
                        }
                    }
                }
                case 3: {
                    int v4;
                    for(v4 = offset; v4 < v3; ++v4) {
                        v5 = v6.get(v4) * v12 + v9;
                        this.tmpV.set(v13.get(v5), v13.get(v5 + 1), v13.get(v5 + 2));
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        v10 = this.tmpV.sub(centerX, centerY, centerZ).len2();
                        if(v10 > v11) {
                            v11 = v10;
                        }
                    }
                }
            }

            return v11;
        }

        throw new GdxRuntimeException("Not enough indices");
    }

    public static void clearAllMeshes(Application app) {
        Mesh.meshes.remove(app);
    }

    public Mesh copy(boolean isStatic) {
        return this.copy(isStatic, false, null);
    }

    public Mesh copy(boolean isStatic, boolean removeDuplicates, int[] usage) {
        Mesh v22;
        int v27;
        int v16;
        int v12;
        int v23;
        int v25 = this.getVertexSize() / 4;
        int v21 = this.getNumVertices();
        float[] v26 = new float[v21 * v25];
        this.getVertices(0, v26.length, v26);
        short[] v9 = null;
        VertexAttribute[] v8 = null;
        int v19 = 0;
        if(usage != null) {
            v23 = 0;
            int v7 = 0;
            int v11;
            for(v11 = 0; v11 < usage.length; ++v11) {
                if(this.getVertexAttribute(usage[v11]) != null) {
                    v23 += this.getVertexAttribute(usage[v11]).numComponents;
                    ++v7;
                }
            }

            if(v23 <= 0) {
                goto label_92;
            }

            v8 = new VertexAttribute[v7];
            v9 = new short[v23];
            v12 = -1;
            int v6 = -1;
            for(v11 = 0; v11 < usage.length; ++v11) {
                VertexAttribute v5 = this.getVertexAttribute(usage[v11]);
                if(v5 != null) {
                    v16 = 0;
                    while(v16 < v5.numComponents) {
                        ++v12;
                        v9[v12] = ((short)(v5.offset + v16));
                        ++v16;
                    }

                    ++v6;
                    v8[v6] = new VertexAttribute(v5.usage, v5.numComponents, v5.alias);
                    v19 += v5.numComponents;
                }
            }
        }

    label_92:
        if(v9 == null) {
            v9 = new short[v25];
            short v11_1;
            for(v11_1 = 0; v11_1 < v25; v11_1 = ((short)(v11_1 + 1))) {
                v9[v11_1] = v11_1;
            }

            v19 = v25;
        }

        int v20 = this.getNumIndices();
        short[] v15 = null;
        if(v20 > 0) {
            v15 = new short[v20];
            this.getIndices(v15);
            if(!removeDuplicates && v19 == v25) {
                goto label_185;
            }

            float[] v24 = new float[v26.length];
            v23 = 0;
            for(v11 = 0; v11 < v20; ++v11) {
                int v13 = v15[v11] * v25;
                short v18 = -1;
                if(removeDuplicates) {
                    short v16_1 = 0;
                    while(v16_1 < v23) {
                        if(v18 >= 0) {
                            break;
                        }

                        int v14 = v16_1 * v19;
                        int v10 = 1;
                        int v17 = 0;
                        while(v17 < v9.length) {
                            if(v10 == 0) {
                                break;
                            }

                            if(v24[v14 + v17] != v26[v9[v17] + v13]) {
                                v10 = 0;
                            }

                            ++v17;
                        }

                        if(v10 != 0) {
                            v18 = v16_1;
                        }

                        v16_1 = ((short)(v16_1 + 1));
                    }
                }

                if(v18 > 0) {
                    v15[v11] = v18;
                }
                else {
                    v12 = v23 * v19;
                    v16 = 0;
                    while(v16 < v9.length) {
                        v24[v12 + v16] = v26[v9[v16] + v13];
                        ++v16;
                    }

                    v15[v11] = ((short)v23);
                    ++v23;
                }
            }

            v26 = v24;
            v21 = v23;
        }

    label_185:
        if(v8 == null) {
            if(v15 == null) {
                v27 = 0;
            }
            else {
                v27 = v15.length;
            }

            v22 = new Mesh(isStatic, v21, v27, this.getVertexAttributes());
        }
        else {
            if(v15 == null) {
                v27 = 0;
            }
            else {
                v27 = v15.length;
            }

            v22 = new Mesh(isStatic, v21, v27, v8);
        }

        v22.setVertices(v26, 0, v21 * v19);
        v22.setIndices(v15);
        return v22;
    }

    public void dispose() {
        if(Mesh.meshes.get(Gdx.app) != null) {
            Mesh.meshes.get(Gdx.app).removeValue(this, true);
        }

        this.vertices.dispose();
        this.indices.dispose();
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count) {
        return this.extendBoundingBox(out, offset, count, null);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, int offset, int count, Matrix4 transform) {
        int v3;
        int v5 = this.getNumIndices();
        if(offset >= 0 && count >= 1 && offset + count <= v5) {
            FloatBuffer v9 = this.vertices.getBuffer();
            ShortBuffer v4 = this.indices.getBuffer();
            VertexAttribute v6 = this.getVertexAttribute(1);
            int v7 = v6.offset / 4;
            int v8 = this.vertices.getAttributes().vertexSize / 4;
            int v1 = offset + count;
            switch(v6.numComponents) {
                case 1: {
                    int v2;
                    for(v2 = offset; v2 < v1; ++v2) {
                        this.tmpV.set(v9.get(v4.get(v2) * v8 + v7), 0f, 0f);
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        out.ext(this.tmpV);
                    }
                }
                case 2: {
                    for(v2 = offset; v2 < v1; ++v2) {
                        v3 = v4.get(v2) * v8 + v7;
                        this.tmpV.set(v9.get(v3), v9.get(v3 + 1), 0f);
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        out.ext(this.tmpV);
                    }
                }
                case 3: {
                    for(v2 = offset; v2 < v1; ++v2) {
                        v3 = v4.get(v2) * v8 + v7;
                        this.tmpV.set(v9.get(v3), v9.get(v3 + 1), v9.get(v3 + 2));
                        if(transform != null) {
                            this.tmpV.mul(transform);
                        }

                        out.ext(this.tmpV);
                    }
                }
            }

            return out;
        }

        throw new GdxRuntimeException("Not enough indices ( offset=" + offset + ", count=" + count + ", max=" + v5 + " )");
    }

    public void getIndices(short[] indices) {
        this.getIndices(indices, 0);
    }

    public void getIndices(int srcOffset, int count, short[] indices, int destOffset) {
        int v0 = this.getNumIndices();
        if(count < 0) {
            count = v0 - srcOffset;
        }

        if(srcOffset >= 0 && srcOffset < v0 && srcOffset + count <= v0) {
            if(indices.length - destOffset < count) {
                throw new IllegalArgumentException("not enough room in indices array, has " + indices.length + " shorts, needs " + count);
            }
            else {
                int v1 = this.getIndicesBuffer().position();
                this.getIndicesBuffer().position(srcOffset);
                this.getIndicesBuffer().get(indices, destOffset, count);
                this.getIndicesBuffer().position(v1);
                return;
            }
        }

        throw new IllegalArgumentException("Invalid range specified, offset: " + srcOffset + ", count: " + count + ", max: " + v0);
    }

    public void getIndices(int srcOffset, short[] indices, int destOffset) {
        this.getIndices(srcOffset, -1, indices, destOffset);
    }

    public void getIndices(short[] indices, int destOffset) {
        this.getIndices(0, indices, destOffset);
    }

    public ShortBuffer getIndicesBuffer() {
        return this.indices.getBuffer();
    }

    public static String getManagedStatus() {
        StringBuilder v1 = new StringBuilder();
        v1.append("Managed meshes/app: { ");
        Iterator v3 = Mesh.meshes.keySet().iterator();
        while(v3.hasNext()) {
            v1.append(Mesh.meshes.get(v3.next()).size);
            v1.append(" ");
        }

        v1.append("}");
        return v1.toString();
    }

    public int getMaxIndices() {
        return this.indices.getNumMaxIndices();
    }

    public int getMaxVertices() {
        return this.vertices.getNumMaxVertices();
    }

    public int getNumIndices() {
        return this.indices.getNumIndices();
    }

    public int getNumVertices() {
        return this.vertices.getNumVertices();
    }

    public VertexAttribute getVertexAttribute(int usage) {
        VertexAttribute v3;
        VertexAttributes v0 = this.vertices.getAttributes();
        int v2 = v0.size();
        int v1 = 0;
        while(true) {
            if(v1 >= v2) {
                break;
            }
            else if(v0.get(v1).usage == usage) {
                v3 = v0.get(v1);
            }
            else {
                ++v1;
                continue;
            }

            goto label_9;
        }

        v3 = null;
    label_9:
        return v3;
    }

    public VertexAttributes getVertexAttributes() {
        return this.vertices.getAttributes();
    }

    public int getVertexSize() {
        return this.vertices.getAttributes().vertexSize;
    }

    public float[] getVertices(int srcOffset, int count, float[] vertices) {
        return this.getVertices(srcOffset, count, vertices, 0);
    }

    public float[] getVertices(int srcOffset, int count, float[] vertices, int destOffset) {
        int v0 = this.getNumVertices() * this.getVertexSize() / 4;
        if(count == -1) {
            count = v0 - srcOffset;
            if(count > vertices.length - destOffset) {
                count = vertices.length - destOffset;
            }
        }

        if(srcOffset >= 0 && count > 0 && srcOffset + count <= v0 && destOffset >= 0 && destOffset < vertices.length) {
            if(vertices.length - destOffset < count) {
                throw new IllegalArgumentException("not enough room in vertices array, has " + vertices.length + " floats, needs " + count);
            }
            else {
                int v1 = this.getVerticesBuffer().position();
                this.getVerticesBuffer().position(srcOffset);
                this.getVerticesBuffer().get(vertices, destOffset, count);
                this.getVerticesBuffer().position(v1);
                return vertices;
            }
        }

        throw new IndexOutOfBoundsException();
    }

    public float[] getVertices(int srcOffset, float[] vertices) {
        return this.getVertices(srcOffset, -1, vertices);
    }

    public float[] getVertices(float[] vertices) {
        return this.getVertices(0, -1, vertices);
    }

    public FloatBuffer getVerticesBuffer() {
        return this.vertices.getBuffer();
    }

    public static void invalidateAllMeshes(Application app) {
        Object v1 = Mesh.meshes.get(app);
        if(v1 != null) {
            int v0;
            for(v0 = 0; v0 < ((Array)v1).size; ++v0) {
                ((Array)v1).get(v0).vertices.invalidate();
                ((Array)v1).get(v0).indices.invalidate();
            }
        }
    }

    private VertexData makeVertexBuffer(boolean isStatic, int maxVertices, VertexAttributes vertexAttributes) {
        VertexBufferObject v0_1;
        if(Gdx.gl30 != null) {
            VertexBufferObjectWithVAO v0 = new VertexBufferObjectWithVAO(isStatic, maxVertices, vertexAttributes);
        }
        else {
            v0_1 = new VertexBufferObject(isStatic, maxVertices, vertexAttributes);
        }

        return ((VertexData)v0_1);
    }

    public void render(ShaderProgram shader, int primitiveType) {
        int v4;
        if(this.indices.getNumMaxIndices() > 0) {
            v4 = this.getNumIndices();
        }
        else {
            v4 = this.getNumVertices();
        }

        this.render(shader, primitiveType, 0, v4, this.autoBind);
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count, boolean autoBind) {
        int v5 = 5123;
        if(count != 0) {
            if(autoBind) {
                this.bind(shader);
            }

            if(this.isVertexArray) {
                if(this.indices.getNumIndices() > 0) {
                    ShortBuffer v0 = this.indices.getBuffer();
                    int v2 = v0.position();
                    int v1 = v0.limit();
                    v0.position(offset);
                    v0.limit(offset + count);
                    Gdx.gl20.glDrawElements(primitiveType, count, v5, ((Buffer)v0));
                    v0.position(v2);
                    v0.limit(v1);
                }
                else {
                    Gdx.gl20.glDrawArrays(primitiveType, offset, count);
                }
            }
            else if(this.indices.getNumIndices() > 0) {
                Gdx.gl20.glDrawElements(primitiveType, count, v5, offset * 2);
            }
            else {
                Gdx.gl20.glDrawArrays(primitiveType, offset, count);
            }

            if(!autoBind) {
                return;
            }

            this.unbind(shader);
        }
    }

    public void render(ShaderProgram shader, int primitiveType, int offset, int count) {
        this.render(shader, primitiveType, offset, count, this.autoBind);
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        int v8;
        VertexAttribute v5 = this.getVertexAttribute(1);
        int v4 = v5.offset / 4;
        int v2 = v5.numComponents;
        int v3 = this.getNumVertices();
        int v6 = this.getVertexSize() / 4;
        float[] v7 = new float[v3 * v6];
        this.getVertices(v7);
        int v1 = v4;
        switch(v2) {
            case 1: {
                for(v0 = 0; v0 < v3; ++v0) {
                    v7[v1] *= scaleX;
                    v1 += v6;
                }
            }
            case 2: {
                for(v0 = 0; v0 < v3; ++v0) {
                    v7[v1] *= scaleX;
                    v8 = v1 + 1;
                    v7[v8] *= scaleY;
                    v1 += v6;
                }
            }
            case 3: {
                int v0;
                for(v0 = 0; v0 < v3; ++v0) {
                    v7[v1] *= scaleX;
                    v8 = v1 + 1;
                    v7[v8] *= scaleY;
                    v8 = v1 + 2;
                    v7[v8] *= scaleZ;
                    v1 += v6;
                }
            }
        }

        this.setVertices(v7);
    }

    public void setAutoBind(boolean autoBind) {
        this.autoBind = autoBind;
    }

    public Mesh setIndices(short[] indices) {
        this.indices.setIndices(indices, 0, indices.length);
        return this;
    }

    public Mesh setIndices(short[] indices, int offset, int count) {
        this.indices.setIndices(indices, offset, count);
        return this;
    }

    public Mesh setVertices(float[] vertices, int offset, int count) {
        this.vertices.setVertices(vertices, offset, count);
        return this;
    }

    public Mesh setVertices(float[] vertices) {
        this.vertices.setVertices(vertices, 0, vertices.length);
        return this;
    }

    public static void transform(Matrix4 matrix, float[] vertices, int vertexSize, int offset, int dimensions, int start, int count) {
        if(offset >= 0 && dimensions >= 1 && offset + dimensions <= vertexSize) {
            if(start >= 0 && count >= 1 && (start + count) * vertexSize <= vertices.length) {
                Vector3 v2 = new Vector3();
                int v1 = offset + start * vertexSize;
                switch(dimensions) {
                    case 1: {
                        int v0;
                        for(v0 = 0; v0 < count; ++v0) {
                            v2.set(vertices[v1], 0f, 0f).mul(matrix);
                            vertices[v1] = v2.x;
                            v1 += vertexSize;
                        }
                    }
                    case 2: {
                        for(v0 = 0; v0 < count; ++v0) {
                            v2.set(vertices[v1], vertices[v1 + 1], 0f).mul(matrix);
                            vertices[v1] = v2.x;
                            vertices[v1 + 1] = v2.y;
                            v1 += vertexSize;
                        }
                    }
                    case 3: {
                        for(v0 = 0; v0 < count; ++v0) {
                            v2.set(vertices[v1], vertices[v1 + 1], vertices[v1 + 2]).mul(matrix);
                            vertices[v1] = v2.x;
                            vertices[v1 + 1] = v2.y;
                            vertices[v1 + 2] = v2.z;
                            v1 += vertexSize;
                        }
                    }
                }

                return;
            }

            throw new IndexOutOfBoundsException("start = " + start + ", count = " + count + ", vertexSize = " + vertexSize + ", length = " + vertices.length);
        }

        throw new IndexOutOfBoundsException();
    }

    public void transform(Matrix4 matrix) {
        this.transform(matrix, 0, this.getNumVertices());
    }

    public void transform(Matrix4 matrix, int start, int count) {
        VertexAttribute v8 = this.getVertexAttribute(1);
        int v3 = v8.offset / 4;
        int v2 = this.getVertexSize() / 4;
        int v4 = v8.numComponents;
        this.getNumVertices();
        float[] v1 = new float[count * v2];
        this.getVertices(start * v2, count * v2, v1);
        Mesh.transform(matrix, v1, v2, v3, v4, 0, count);
        this.updateVertices(start * v2, v1);
    }

    public static void transformUV(Matrix3 matrix, float[] vertices, int vertexSize, int offset, int start, int count) {
        if(start >= 0 && count >= 1 && (start + count) * vertexSize <= vertices.length) {
            Vector2 v2 = new Vector2();
            int v1 = offset + start * vertexSize;
            int v0;
            for(v0 = 0; v0 < count; ++v0) {
                v2.set(vertices[v1], vertices[v1 + 1]).mul(matrix);
                vertices[v1] = v2.x;
                vertices[v1 + 1] = v2.y;
                v1 += vertexSize;
            }

            return;
        }

        throw new IndexOutOfBoundsException("start = " + start + ", count = " + count + ", vertexSize = " + vertexSize + ", length = " + vertices.length);
    }

    public void transformUV(Matrix3 matrix) {
        this.transformUV(matrix, 0, this.getNumVertices());
    }

    protected void transformUV(Matrix3 matrix, int start, int count) {
        int v3 = this.getVertexAttribute(16).offset / 4;
        int v2 = this.getVertexSize() / 4;
        float[] v1 = new float[this.getNumVertices() * v2];
        this.getVertices(0, v1.length, v1);
        Mesh.transformUV(matrix, v1, v2, v3, start, count);
        this.setVertices(v1, 0, v1.length);
    }

    public void unbind(ShaderProgram shader) {
        this.unbind(shader, null);
    }

    public void unbind(ShaderProgram shader, int[] locations) {
        this.vertices.unbind(shader, locations);
        if(this.indices.getNumIndices() > 0) {
            this.indices.unbind();
        }
    }

    public Mesh updateVertices(int targetOffset, float[] source) {
        return this.updateVertices(targetOffset, source, 0, source.length);
    }

    public Mesh updateVertices(int targetOffset, float[] source, int sourceOffset, int count) {
        this.vertices.updateVertices(targetOffset, source, sourceOffset, count);
        return this;
    }
}

