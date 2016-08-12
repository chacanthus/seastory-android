// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh$VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.NumberUtils;

public class PolygonSpriteBatch implements Batch {
    private int blendDstFunc;
    private int blendSrcFunc;
    private boolean blendingDisabled;
    float color;
    private final Matrix4 combinedMatrix;
    private ShaderProgram customShader;
    private boolean drawing;
    private float invTexHeight;
    private float invTexWidth;
    private Texture lastTexture;
    public int maxTrianglesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    private int triangleIndex;
    private final short[] triangles;
    private int vertexIndex;
    private final float[] vertices;

    public PolygonSpriteBatch() {
        this(2000, null);
    }

    public PolygonSpriteBatch(int size, ShaderProgram defaultShader) {
        int v11 = 2;
        super();
        this.invTexWidth = 0f;
        this.invTexHeight = 0f;
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.blendSrcFunc = 770;
        this.blendDstFunc = 771;
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(1f, 1f, 1f, 1f);
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.maxTrianglesInBatch = 0;
        if(size > 10920) {
            throw new IllegalArgumentException("Can\'t have more than 10920 triangles per batch: " + size);
        }

        VertexDataType v1 = VertexDataType.VertexArray;
        if(Gdx.gl30 != null) {
            v1 = VertexDataType.VertexBufferObjectWithVAO;
        }

        VertexAttribute[] v5 = new VertexAttribute[3];
        v5[0] = new VertexAttribute(1, v11, "a_position");
        v5[1] = new VertexAttribute(4, 4, "a_color");
        v5[v11] = new VertexAttribute(16, v11, "a_texCoord0");
        this.mesh = new Mesh(v1, false, size, size * 3, v5);
        this.vertices = new float[size * 5];
        this.triangles = new short[size * 3];
        if(defaultShader == null) {
            this.shader = SpriteBatch.createDefaultShader();
            this.ownsShader = true;
        }
        else {
            this.shader = defaultShader;
        }

        this.projectionMatrix.setToOrtho2D(0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
    }

    public PolygonSpriteBatch(int size) {
        this(size, null);
    }

    public void begin() {
        if(this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.end must be called before begin.");
        }

        this.renderCalls = 0;
        Gdx.gl.glDepthMask(false);
        if(this.customShader != null) {
            this.customShader.begin();
        }
        else {
            this.shader.begin();
        }

        this.setupMatrices();
        this.drawing = true;
    }

    public void disableBlending() {
        this.flush();
        this.blendingDisabled = true;
    }

    public void dispose() {
        this.mesh.dispose();
        if((this.ownsShader) && this.shader != null) {
            this.shader.dispose();
        }
    }

    public void draw(Texture texture, float[] polygonVertices, int verticesOffset, int verticesCount, short[] polygonTriangles, int trianglesOffset, int trianglesCount) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v5 = this.triangles;
        float[] v7 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + trianglesCount <= v5.length && this.vertexIndex + verticesCount <= v7.length) {
                goto label_10;
            }

            this.flush();
        }

    label_10:
        int v3 = this.triangleIndex;
        int v6 = this.vertexIndex;
        int v2 = v6 / 5;
        int v0 = trianglesOffset;
        int v1 = v0 + trianglesCount;
        int v4;
        for(v4 = v3; v0 < v1; ++v4) {
            v5[v4] = ((short)(polygonTriangles[v0] + v2));
            ++v0;
        }

        this.triangleIndex = v4;
        System.arraycopy(polygonVertices, verticesOffset, v7, v6, verticesCount);
        this.vertexIndex += verticesCount;
    }

    public void draw(Texture texture, float x, float y) {
        this.draw(texture, x, y, ((float)texture.getWidth()), ((float)texture.getHeight()));
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v9 = this.triangles;
        float[] v14 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + 6 <= v9.length && this.vertexIndex + 20 <= v14.length) {
                goto label_15;
            }

            this.flush();
        }

    label_15:
        int v7 = this.triangleIndex;
        int v6 = this.vertexIndex / 5;
        int v8 = v7 + 1;
        v9[v7] = ((short)v6);
        v7 = v8 + 1;
        v9[v8] = ((short)(v6 + 1));
        v8 = v7 + 1;
        v9[v7] = ((short)(v6 + 2));
        v7 = v8 + 1;
        v9[v8] = ((short)(v6 + 2));
        v8 = v7 + 1;
        v9[v7] = ((short)(v6 + 3));
        v9[v8] = ((short)v6);
        this.triangleIndex = v8 + 1;
        float v2 = x + width;
        float v3 = y + height;
        float v1 = this.color;
        int v5 = this.vertexIndex + 1;
        v14[this.vertexIndex] = x;
        int v4 = v5 + 1;
        v14[v5] = y;
        v5 = v4 + 1;
        v14[v4] = v1;
        v4 = v5 + 1;
        v14[v5] = 0f;
        v5 = v4 + 1;
        v14[v4] = 1f;
        v4 = v5 + 1;
        v14[v5] = x;
        v5 = v4 + 1;
        v14[v4] = v3;
        v4 = v5 + 1;
        v14[v5] = v1;
        v5 = v4 + 1;
        v14[v4] = 0f;
        v4 = v5 + 1;
        v14[v5] = 0f;
        v5 = v4 + 1;
        v14[v4] = v2;
        v4 = v5 + 1;
        v14[v5] = v3;
        v5 = v4 + 1;
        v14[v4] = v1;
        v4 = v5 + 1;
        v14[v5] = 1f;
        v5 = v4 + 1;
        v14[v4] = 0f;
        v4 = v5 + 1;
        v14[v5] = v2;
        v5 = v4 + 1;
        v14[v4] = y;
        v4 = v5 + 1;
        v14[v5] = v1;
        v5 = v4 + 1;
        v14[v4] = 1f;
        v14[v5] = 1f;
        this.vertexIndex = v5 + 1;
    }

    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v8 = this.triangles;
        float[] v9 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + 6 <= v8.length && this.vertexIndex + 20 <= v9.length) {
                goto label_10;
            }

            this.flush();
        }

    label_10:
        int v6 = this.triangleIndex;
        int v5 = this.vertexIndex / 5;
        int v7 = v6 + 1;
        v8[v6] = ((short)v5);
        v6 = v7 + 1;
        v8[v7] = ((short)(v5 + 1));
        v7 = v6 + 1;
        v8[v6] = ((short)(v5 + 2));
        v6 = v7 + 1;
        v8[v7] = ((short)(v5 + 2));
        v7 = v6 + 1;
        v8[v6] = ((short)(v5 + 3));
        v8[v7] = ((short)v5);
        this.triangleIndex = v7 + 1;
        float v1 = x + width;
        float v2 = y + height;
        float v0 = this.color;
        int v4 = this.vertexIndex + 1;
        v9[this.vertexIndex] = x;
        int v3 = v4 + 1;
        v9[v4] = y;
        v4 = v3 + 1;
        v9[v3] = v0;
        v3 = v4 + 1;
        v9[v4] = u;
        v4 = v3 + 1;
        v9[v3] = v;
        v3 = v4 + 1;
        v9[v4] = x;
        v4 = v3 + 1;
        v9[v3] = v2;
        v3 = v4 + 1;
        v9[v4] = v0;
        v4 = v3 + 1;
        v9[v3] = u;
        v3 = v4 + 1;
        v9[v4] = v2;
        v4 = v3 + 1;
        v9[v3] = v1;
        v3 = v4 + 1;
        v9[v4] = v2;
        v4 = v3 + 1;
        v9[v3] = v0;
        v3 = v4 + 1;
        v9[v4] = u2;
        v4 = v3 + 1;
        v9[v3] = v2;
        v3 = v4 + 1;
        v9[v4] = v1;
        v4 = v3 + 1;
        v9[v3] = y;
        v3 = v4 + 1;
        v9[v4] = v0;
        v4 = v3 + 1;
        v9[v3] = u2;
        v9[v4] = v;
        this.vertexIndex = v4 + 1;
    }

    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v20;
        float v38;
        float v34;
        float v37;
        float v33;
        float v36;
        float v32;
        float v35;
        float v31;
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v23 = this.triangles;
        float[] v28 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + 6 <= v23.length && this.vertexIndex + 20 <= v28.length) {
                goto label_20;
            }

            this.flush();
        }

    label_20:
        int v21 = this.triangleIndex;
        int v19 = this.vertexIndex / 5;
        int v22 = v21 + 1;
        v23[v21] = ((short)v19);
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 1));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 2));
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 2));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 3));
        v23[v22] = ((short)v19);
        this.triangleIndex = v22 + 1;
        float v29 = x + originX;
        float v30 = y + originY;
        float v4 = -originX;
        float v6 = -originY;
        float v5 = width - originX;
        float v7 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v4 *= scaleX;
            v6 *= scaleY;
            v5 *= scaleX;
            v7 *= scaleY;
        }

        float v10 = v4;
        float v11 = v6;
        float v12 = v4;
        float v13 = v7;
        float v14 = v5;
        float v15 = v7;
        float v16 = v5;
        float v17 = v6;
        if(rotation != 0f) {
            float v3 = MathUtils.cosDeg(rotation);
            float v18 = MathUtils.sinDeg(rotation);
            v31 = v3 * v10 - v18 * v11;
            v35 = v18 * v10 + v3 * v11;
            v32 = v3 * v12 - v18 * v13;
            v36 = v18 * v12 + v3 * v13;
            v33 = v3 * v14 - v18 * v15;
            v37 = v18 * v14 + v3 * v15;
            v34 = v31 + (v33 - v32);
            v38 = v37 - (v36 - v35);
        }
        else {
            v31 = v10;
            v35 = v11;
            v32 = v12;
            v36 = v13;
            v33 = v14;
            v37 = v15;
            v34 = v16;
            v38 = v17;
        }

        v31 += v29;
        v35 += v30;
        v32 += v29;
        v36 += v30;
        v33 += v29;
        v37 += v30;
        v34 += v29;
        v38 += v30;
        float v24 = (((float)srcX)) * this.invTexWidth;
        float v26 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v25 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v27 = (((float)srcY)) * this.invTexHeight;
        if(flipX) {
            v20 = v24;
            v24 = v25;
            v25 = v20;
        }

        if(flipY) {
            v20 = v26;
            v26 = v27;
            v27 = v20;
        }

        float v2 = this.color;
        int v9 = this.vertexIndex + 1;
        v28[this.vertexIndex] = v31;
        int v8 = v9 + 1;
        v28[v9] = v35;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v24;
        v9 = v8 + 1;
        v28[v8] = v26;
        v8 = v9 + 1;
        v28[v9] = v32;
        v9 = v8 + 1;
        v28[v8] = v36;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v24;
        v8 = v9 + 1;
        v28[v9] = v27;
        v9 = v8 + 1;
        v28[v8] = v33;
        v8 = v9 + 1;
        v28[v9] = v37;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v25;
        v9 = v8 + 1;
        v28[v8] = v27;
        v8 = v9 + 1;
        v28[v9] = v34;
        v9 = v8 + 1;
        v28[v8] = v38;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v25;
        v28[v9] = v26;
        this.vertexIndex = v9 + 1;
    }

    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v8;
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v11 = this.triangles;
        float[] v16 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + 6 <= v11.length && this.vertexIndex + 20 <= v16.length) {
                goto label_19;
            }

            this.flush();
        }

    label_19:
        int v9 = this.triangleIndex;
        int v7 = this.vertexIndex / 5;
        int v10 = v9 + 1;
        v11[v9] = ((short)v7);
        v9 = v10 + 1;
        v11[v10] = ((short)(v7 + 1));
        v10 = v9 + 1;
        v11[v9] = ((short)(v7 + 2));
        v9 = v10 + 1;
        v11[v10] = ((short)(v7 + 2));
        v10 = v9 + 1;
        v11[v9] = ((short)(v7 + 3));
        v11[v10] = ((short)v7);
        this.triangleIndex = v10 + 1;
        float v12 = (((float)srcX)) * this.invTexWidth;
        float v14 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v13 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v15 = (((float)srcY)) * this.invTexHeight;
        float v3 = x + width;
        float v4 = y + height;
        if(flipX) {
            v8 = v12;
            v12 = v13;
            v13 = v8;
        }

        if(flipY) {
            v8 = v14;
            v14 = v15;
            v15 = v8;
        }

        float v2 = this.color;
        int v6 = this.vertexIndex + 1;
        v16[this.vertexIndex] = x;
        int v5 = v6 + 1;
        v16[v6] = y;
        v6 = v5 + 1;
        v16[v5] = v2;
        v5 = v6 + 1;
        v16[v6] = v12;
        v6 = v5 + 1;
        v16[v5] = v14;
        v5 = v6 + 1;
        v16[v6] = x;
        v6 = v5 + 1;
        v16[v5] = v4;
        v5 = v6 + 1;
        v16[v6] = v2;
        v6 = v5 + 1;
        v16[v5] = v12;
        v5 = v6 + 1;
        v16[v6] = v15;
        v6 = v5 + 1;
        v16[v5] = v3;
        v5 = v6 + 1;
        v16[v6] = v4;
        v6 = v5 + 1;
        v16[v5] = v2;
        v5 = v6 + 1;
        v16[v6] = v13;
        v6 = v5 + 1;
        v16[v5] = v15;
        v5 = v6 + 1;
        v16[v6] = v3;
        v6 = v5 + 1;
        v16[v5] = y;
        v5 = v6 + 1;
        v16[v6] = v2;
        v6 = v5 + 1;
        v16[v5] = v13;
        v16[v6] = v14;
        this.vertexIndex = v6 + 1;
    }

    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v9 = this.triangles;
        float[] v14 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + 6 <= v9.length && this.vertexIndex + 20 <= v14.length) {
                goto label_15;
            }

            this.flush();
        }

    label_15:
        int v7 = this.triangleIndex;
        int v6 = this.vertexIndex / 5;
        int v8 = v7 + 1;
        v9[v7] = ((short)v6);
        v7 = v8 + 1;
        v9[v8] = ((short)(v6 + 1));
        v8 = v7 + 1;
        v9[v7] = ((short)(v6 + 2));
        v7 = v8 + 1;
        v9[v8] = ((short)(v6 + 2));
        v8 = v7 + 1;
        v9[v7] = ((short)(v6 + 3));
        v9[v8] = ((short)v6);
        this.triangleIndex = v8 + 1;
        float v10 = (((float)srcX)) * this.invTexWidth;
        float v12 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v11 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v13 = (((float)srcY)) * this.invTexHeight;
        float v2 = x + (((float)srcWidth));
        float v3 = y + (((float)srcHeight));
        float v1 = this.color;
        int v5 = this.vertexIndex + 1;
        v14[this.vertexIndex] = x;
        int v4 = v5 + 1;
        v14[v5] = y;
        v5 = v4 + 1;
        v14[v4] = v1;
        v4 = v5 + 1;
        v14[v5] = v10;
        v5 = v4 + 1;
        v14[v4] = v12;
        v4 = v5 + 1;
        v14[v5] = x;
        v5 = v4 + 1;
        v14[v4] = v3;
        v4 = v5 + 1;
        v14[v5] = v1;
        v5 = v4 + 1;
        v14[v4] = v10;
        v4 = v5 + 1;
        v14[v5] = v13;
        v5 = v4 + 1;
        v14[v4] = v2;
        v4 = v5 + 1;
        v14[v5] = v3;
        v5 = v4 + 1;
        v14[v4] = v1;
        v4 = v5 + 1;
        v14[v5] = v11;
        v5 = v4 + 1;
        v14[v4] = v13;
        v4 = v5 + 1;
        v14[v5] = v2;
        v5 = v4 + 1;
        v14[v4] = y;
        v4 = v5 + 1;
        v14[v5] = v1;
        v5 = v4 + 1;
        v14[v4] = v11;
        v14[v5] = v12;
        this.vertexIndex = v5 + 1;
    }

    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v3 = this.triangles;
        float[] v6 = this.vertices;
        int v1 = count / 20 * 6;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            if(this.triangleIndex + v1 <= v3.length && this.vertexIndex + count <= v6.length) {
                goto label_12;
            }

            this.flush();
        }

    label_12:
        int v5 = this.vertexIndex;
        int v2 = this.triangleIndex;
        short v4 = ((short)(v5 / 5));
        int v0 = v2 + v1;
        while(v2 < v0) {
            v3[v2] = v4;
            v3[v2 + 1] = ((short)(v4 + 1));
            v3[v2 + 2] = ((short)(v4 + 2));
            v3[v2 + 3] = ((short)(v4 + 2));
            v3[v2 + 4] = ((short)(v4 + 3));
            v3[v2 + 5] = v4;
            v2 += 6;
            v4 = ((short)(v4 + 4));
        }

        this.triangleIndex = v2;
        System.arraycopy(spriteVertices, offset, v6, v5, count);
        this.vertexIndex += count;
    }

    public void draw(PolygonRegion region, float x, float y) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v13 = this.triangles;
        short[] v4 = region.triangles;
        int v5 = v4.length;
        float[] v6 = region.vertices;
        int v7 = v6.length;
        Texture v9 = region.region.texture;
        if(v9 != this.lastTexture) {
            this.switchTexture(v9);
        }
        else {
            if(this.triangleIndex + v5 <= v13.length && this.vertexIndex + v7 <= this.vertices.length) {
                goto label_27;
            }

            this.flush();
        }

    label_27:
        int v11 = this.triangleIndex;
        int v14 = this.vertexIndex;
        int v8 = v14 / 5;
        int v3 = 0;
        int v12;
        for(v12 = v11; v3 < v5; ++v12) {
            v13[v12] = ((short)(v4[v3] + v8));
            ++v3;
        }

        this.triangleIndex = v12;
        float[] v16 = this.vertices;
        float v2 = this.color;
        float[] v10 = region.textureCoords;
        v3 = 0;
        int v15;
        for(v15 = v14; v3 < v7; ++v15) {
            v14 = v15 + 1;
            v16[v15] = v6[v3] + x;
            v15 = v14 + 1;
            v16[v14] = v6[v3 + 1] + y;
            v14 = v15 + 1;
            v16[v15] = v2;
            v15 = v14 + 1;
            v16[v14] = v10[v3];
            v16[v15] = v10[v3 + 1];
            v3 += 2;
        }

        this.vertexIndex = v15;
    }

    public void draw(PolygonRegion region, float x, float y, float width, float height) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v17 = this.triangles;
        short[] v5 = region.triangles;
        int v6 = v5.length;
        float[] v7 = region.vertices;
        int v8 = v7.length;
        TextureRegion v14 = region.region;
        Texture v12 = v14.texture;
        if(v12 != this.lastTexture) {
            this.switchTexture(v12);
        }
        else {
            if(this.triangleIndex + v6 <= v17.length && this.vertexIndex + v8 <= this.vertices.length) {
                goto label_26;
            }

            this.flush();
        }

    label_26:
        int v15 = this.triangleIndex;
        int v18 = this.vertexIndex;
        int v11 = v18 / 5;
        int v3 = 0;
        int v4 = v5.length;
        int v16;
        for(v16 = v15; v3 < v4; ++v16) {
            v17[v16] = ((short)(v5[v3] + v11));
            ++v3;
        }

        this.triangleIndex = v16;
        float[] v20 = this.vertices;
        float v2 = this.color;
        float[] v13 = region.textureCoords;
        float v9 = width / (((float)v14.regionWidth));
        float v10 = height / (((float)v14.regionHeight));
        v3 = 0;
        int v19;
        for(v19 = v18; v3 < v8; ++v19) {
            v18 = v19 + 1;
            v20[v19] = v7[v3] * v9 + x;
            v19 = v18 + 1;
            v20[v18] = v7[v3 + 1] * v10 + y;
            v18 = v19 + 1;
            v20[v19] = v2;
            v19 = v18 + 1;
            v20[v18] = v13[v3];
            v20[v19] = v13[v3 + 1];
            v3 += 2;
        }

        this.vertexIndex = v19;
    }

    public void draw(PolygonRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v20 = this.triangles;
        short[] v7 = region.triangles;
        int v8 = v7.length;
        float[] v9 = region.vertices;
        int v10 = v9.length;
        TextureRegion v17 = region.region;
        Texture v15 = v17.texture;
        if(v15 != this.lastTexture) {
            this.switchTexture(v15);
        }
        else {
            if(this.triangleIndex + v8 <= v20.length && this.vertexIndex + v10 <= this.vertices.length) {
                goto label_28;
            }

            this.flush();
        }

    label_28:
        int v18 = this.triangleIndex;
        int v21 = this.vertexIndex;
        int v14 = v21 / 5;
        int v6 = 0;
        int v19;
        for(v19 = v18; v6 < v8; ++v19) {
            v20[v19] = ((short)(v7[v6] + v14));
            ++v6;
        }

        this.triangleIndex = v19;
        float[] v23 = this.vertices;
        float v2 = this.color;
        float[] v16 = region.textureCoords;
        float v24 = x + originX;
        float v25 = y + originY;
        float v11 = width / (((float)v17.regionWidth));
        float v12 = height / (((float)v17.regionHeight));
        float v3 = MathUtils.cosDeg(rotation);
        float v13 = MathUtils.sinDeg(rotation);
        v6 = 0;
        int v22;
        for(v22 = v21; v6 < v10; ++v22) {
            float v4 = (v9[v6] * v11 - originX) * scaleX;
            float v5 = (v9[v6 + 1] * v12 - originY) * scaleY;
            v21 = v22 + 1;
            v23[v22] = v3 * v4 - v13 * v5 + v24;
            v22 = v21 + 1;
            v23[v21] = v13 * v4 + v3 * v5 + v25;
            v21 = v22 + 1;
            v23[v22] = v2;
            v22 = v21 + 1;
            v23[v21] = v16[v6];
            v23[v22] = v16[v6 + 1];
            v6 += 2;
        }

        this.vertexIndex = v22;
    }

    public void draw(TextureRegion region, float x, float y) {
        this.draw(region, x, y, ((float)region.getRegionWidth()), ((float)region.getRegionHeight()));
    }

    public void draw(TextureRegion region, float x, float y, float width, float height) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v11 = this.triangles;
        float[] v16 = this.vertices;
        Texture v8 = region.texture;
        if(v8 != this.lastTexture) {
            this.switchTexture(v8);
        }
        else {
            if(this.triangleIndex + 6 <= v11.length && this.vertexIndex + 20 <= v16.length) {
                goto label_21;
            }

            this.flush();
        }

    label_21:
        int v9 = this.triangleIndex;
        int v7 = this.vertexIndex / 5;
        int v10 = v9 + 1;
        v11[v9] = ((short)v7);
        v9 = v10 + 1;
        v11[v10] = ((short)(v7 + 1));
        v10 = v9 + 1;
        v11[v9] = ((short)(v7 + 2));
        v9 = v10 + 1;
        v11[v10] = ((short)(v7 + 2));
        v10 = v9 + 1;
        v11[v9] = ((short)(v7 + 3));
        v11[v10] = ((short)v7);
        this.triangleIndex = v10 + 1;
        float v3 = x + width;
        float v4 = y + height;
        float v12 = region.u;
        float v14 = region.v2;
        float v13 = region.u2;
        float v15 = region.v;
        float v2 = this.color;
        int v6 = this.vertexIndex + 1;
        v16[this.vertexIndex] = x;
        int v5 = v6 + 1;
        v16[v6] = y;
        v6 = v5 + 1;
        v16[v5] = v2;
        v5 = v6 + 1;
        v16[v6] = v12;
        v6 = v5 + 1;
        v16[v5] = v14;
        v5 = v6 + 1;
        v16[v6] = x;
        v6 = v5 + 1;
        v16[v5] = v4;
        v5 = v6 + 1;
        v16[v6] = v2;
        v6 = v5 + 1;
        v16[v5] = v12;
        v5 = v6 + 1;
        v16[v6] = v15;
        v6 = v5 + 1;
        v16[v5] = v3;
        v5 = v6 + 1;
        v16[v6] = v4;
        v6 = v5 + 1;
        v16[v5] = v2;
        v5 = v6 + 1;
        v16[v6] = v13;
        v6 = v5 + 1;
        v16[v5] = v15;
        v5 = v6 + 1;
        v16[v6] = v3;
        v6 = v5 + 1;
        v16[v5] = y;
        v5 = v6 + 1;
        v16[v6] = v2;
        v6 = v5 + 1;
        v16[v5] = v13;
        v16[v6] = v14;
        this.vertexIndex = v6 + 1;
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        float v38;
        float v34;
        float v37;
        float v33;
        float v36;
        float v32;
        float v35;
        float v31;
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v23 = this.triangles;
        float[] v28 = this.vertices;
        Texture v20 = region.texture;
        if(v20 != this.lastTexture) {
            this.switchTexture(v20);
        }
        else {
            if(this.triangleIndex + 6 <= v23.length && this.vertexIndex + 20 <= v28.length) {
                goto label_25;
            }

            this.flush();
        }

    label_25:
        int v21 = this.triangleIndex;
        int v19 = this.vertexIndex / 5;
        int v22 = v21 + 1;
        v23[v21] = ((short)v19);
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 1));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 2));
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 2));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 3));
        v23[v22] = ((short)v19);
        this.triangleIndex = v22 + 1;
        float v29 = x + originX;
        float v30 = y + originY;
        float v4 = -originX;
        float v6 = -originY;
        float v5 = width - originX;
        float v7 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v4 *= scaleX;
            v6 *= scaleY;
            v5 *= scaleX;
            v7 *= scaleY;
        }

        float v10 = v4;
        float v11 = v6;
        float v12 = v4;
        float v13 = v7;
        float v14 = v5;
        float v15 = v7;
        float v16 = v5;
        float v17 = v6;
        if(rotation != 0f) {
            float v3 = MathUtils.cosDeg(rotation);
            float v18 = MathUtils.sinDeg(rotation);
            v31 = v3 * v10 - v18 * v11;
            v35 = v18 * v10 + v3 * v11;
            v32 = v3 * v12 - v18 * v13;
            v36 = v18 * v12 + v3 * v13;
            v33 = v3 * v14 - v18 * v15;
            v37 = v18 * v14 + v3 * v15;
            v34 = v31 + (v33 - v32);
            v38 = v37 - (v36 - v35);
        }
        else {
            v31 = v10;
            v35 = v11;
            v32 = v12;
            v36 = v13;
            v33 = v14;
            v37 = v15;
            v34 = v16;
            v38 = v17;
        }

        float v24 = region.u;
        float v26 = region.v2;
        float v25 = region.u2;
        float v27 = region.v;
        float v2 = this.color;
        int v9 = this.vertexIndex + 1;
        v28[this.vertexIndex] = v31 + v29;
        int v8 = v9 + 1;
        v28[v9] = v35 + v30;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v24;
        v9 = v8 + 1;
        v28[v8] = v26;
        v8 = v9 + 1;
        v28[v9] = v32 + v29;
        v9 = v8 + 1;
        v28[v8] = v36 + v30;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v24;
        v8 = v9 + 1;
        v28[v9] = v27;
        v9 = v8 + 1;
        v28[v8] = v33 + v29;
        v8 = v9 + 1;
        v28[v9] = v37 + v30;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v25;
        v9 = v8 + 1;
        v28[v8] = v27;
        v8 = v9 + 1;
        v28[v9] = v34 + v29;
        v9 = v8 + 1;
        v28[v8] = v38 + v30;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v25;
        v28[v9] = v26;
        this.vertexIndex = v9 + 1;
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        float v31;
        float v27;
        float v30;
        float v26;
        float v29;
        float v25;
        float v28;
        float v24;
        float v42;
        float v38;
        float v41;
        float v37;
        float v40;
        float v36;
        float v39;
        float v35;
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v23 = this.triangles;
        float[] v32 = this.vertices;
        Texture v20 = region.texture;
        if(v20 != this.lastTexture) {
            this.switchTexture(v20);
        }
        else {
            if(this.triangleIndex + 6 <= v23.length && this.vertexIndex + 20 <= v32.length) {
                goto label_25;
            }

            this.flush();
        }

    label_25:
        int v21 = this.triangleIndex;
        int v19 = this.vertexIndex / 5;
        int v22 = v21 + 1;
        v23[v21] = ((short)v19);
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 1));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 2));
        v21 = v22 + 1;
        v23[v22] = ((short)(v19 + 2));
        v22 = v21 + 1;
        v23[v21] = ((short)(v19 + 3));
        v23[v22] = ((short)v19);
        this.triangleIndex = v22 + 1;
        float v33 = x + originX;
        float v34 = y + originY;
        float v4 = -originX;
        float v6 = -originY;
        float v5 = width - originX;
        float v7 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v4 *= scaleX;
            v6 *= scaleY;
            v5 *= scaleX;
            v7 *= scaleY;
        }

        float v10 = v4;
        float v11 = v6;
        float v12 = v4;
        float v13 = v7;
        float v14 = v5;
        float v15 = v7;
        float v16 = v5;
        float v17 = v6;
        if(rotation != 0f) {
            float v3 = MathUtils.cosDeg(rotation);
            float v18 = MathUtils.sinDeg(rotation);
            v35 = v3 * v10 - v18 * v11;
            v39 = v18 * v10 + v3 * v11;
            v36 = v3 * v12 - v18 * v13;
            v40 = v18 * v12 + v3 * v13;
            v37 = v3 * v14 - v18 * v15;
            v41 = v18 * v14 + v3 * v15;
            v38 = v35 + (v37 - v36);
            v42 = v41 - (v40 - v39);
        }
        else {
            v35 = v10;
            v39 = v11;
            v36 = v12;
            v40 = v13;
            v37 = v14;
            v41 = v15;
            v38 = v16;
            v42 = v17;
        }

        v35 += v33;
        v39 += v34;
        v36 += v33;
        v40 += v34;
        v37 += v33;
        v41 += v34;
        v38 += v33;
        v42 += v34;
        if(clockwise) {
            v24 = region.u2;
            v28 = region.v2;
            v25 = region.u;
            v29 = region.v2;
            v26 = region.u;
            v30 = region.v;
            v27 = region.u2;
            v31 = region.v;
        }
        else {
            v24 = region.u;
            v28 = region.v;
            v25 = region.u2;
            v29 = region.v;
            v26 = region.u2;
            v30 = region.v2;
            v27 = region.u;
            v31 = region.v2;
        }

        float v2 = this.color;
        int v9 = this.vertexIndex + 1;
        v32[this.vertexIndex] = v35;
        int v8 = v9 + 1;
        v32[v9] = v39;
        v9 = v8 + 1;
        v32[v8] = v2;
        v8 = v9 + 1;
        v32[v9] = v24;
        v9 = v8 + 1;
        v32[v8] = v28;
        v8 = v9 + 1;
        v32[v9] = v36;
        v9 = v8 + 1;
        v32[v8] = v40;
        v8 = v9 + 1;
        v32[v9] = v2;
        v9 = v8 + 1;
        v32[v8] = v25;
        v8 = v9 + 1;
        v32[v9] = v29;
        v9 = v8 + 1;
        v32[v8] = v37;
        v8 = v9 + 1;
        v32[v9] = v41;
        v9 = v8 + 1;
        v32[v8] = v2;
        v8 = v9 + 1;
        v32[v9] = v26;
        v9 = v8 + 1;
        v32[v8] = v30;
        v8 = v9 + 1;
        v32[v9] = v38;
        v9 = v8 + 1;
        v32[v8] = v42;
        v8 = v9 + 1;
        v32[v9] = v2;
        v9 = v8 + 1;
        v32[v8] = v27;
        v32[v9] = v31;
        this.vertexIndex = v9 + 1;
    }

    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
        }

        short[] v9 = this.triangles;
        float[] v14 = this.vertices;
        Texture v6 = region.texture;
        if(v6 != this.lastTexture) {
            this.switchTexture(v6);
        }
        else {
            if(this.triangleIndex + 6 <= v9.length && this.vertexIndex + 20 <= v14.length) {
                goto label_20;
            }

            this.flush();
        }

    label_20:
        int v7 = this.triangleIndex;
        int v5 = this.vertexIndex / 5;
        int v8 = v7 + 1;
        v9[v7] = ((short)v5);
        v7 = v8 + 1;
        v9[v8] = ((short)(v5 + 1));
        v8 = v7 + 1;
        v9[v7] = ((short)(v5 + 2));
        v7 = v8 + 1;
        v9[v8] = ((short)(v5 + 2));
        v8 = v7 + 1;
        v9[v7] = ((short)(v5 + 3));
        v9[v8] = ((short)v5);
        this.triangleIndex = v8 + 1;
        float v15 = transform.m02;
        float v19 = transform.m12;
        float v16 = transform.m01 * height + transform.m02;
        float v20 = transform.m11 * height + transform.m12;
        float v17 = transform.m00 * width + transform.m01 * height + transform.m02;
        float v21 = transform.m10 * width + transform.m11 * height + transform.m12;
        float v18 = transform.m00 * width + transform.m02;
        float v22 = transform.m10 * width + transform.m12;
        float v10 = region.u;
        float v12 = region.v2;
        float v11 = region.u2;
        float v13 = region.v;
        float v2 = this.color;
        int v4 = this.vertexIndex + 1;
        v14[this.vertexIndex] = v15;
        int v3 = v4 + 1;
        v14[v4] = v19;
        v4 = v3 + 1;
        v14[v3] = v2;
        v3 = v4 + 1;
        v14[v4] = v10;
        v4 = v3 + 1;
        v14[v3] = v12;
        v3 = v4 + 1;
        v14[v4] = v16;
        v4 = v3 + 1;
        v14[v3] = v20;
        v3 = v4 + 1;
        v14[v4] = v2;
        v4 = v3 + 1;
        v14[v3] = v10;
        v3 = v4 + 1;
        v14[v4] = v13;
        v4 = v3 + 1;
        v14[v3] = v17;
        v3 = v4 + 1;
        v14[v4] = v21;
        v4 = v3 + 1;
        v14[v3] = v2;
        v3 = v4 + 1;
        v14[v4] = v11;
        v4 = v3 + 1;
        v14[v3] = v13;
        v3 = v4 + 1;
        v14[v4] = v18;
        v4 = v3 + 1;
        v14[v3] = v22;
        v3 = v4 + 1;
        v14[v4] = v2;
        v4 = v3 + 1;
        v14[v3] = v11;
        v14[v4] = v12;
        this.vertexIndex = v4 + 1;
    }

    public void enableBlending() {
        this.flush();
        this.blendingDisabled = false;
    }

    public void end() {
        if(!this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.begin must be called before end.");
        }

        if(this.vertexIndex > 0) {
            this.flush();
        }

        this.lastTexture = null;
        this.drawing = false;
        GL20 v0 = Gdx.gl;
        v0.glDepthMask(true);
        if(this.isBlendingEnabled()) {
            v0.glDisable(3042);
        }

        if(this.customShader != null) {
            this.customShader.end();
        }
        else {
            this.shader.end();
        }
    }

    public void flush() {
        ShaderProgram v2;
        int v4 = 3042;
        if(this.vertexIndex != 0) {
            ++this.renderCalls;
            ++this.totalRenderCalls;
            int v1 = this.triangleIndex;
            if(v1 > this.maxTrianglesInBatch) {
                this.maxTrianglesInBatch = v1;
            }

            this.lastTexture.bind();
            Mesh v0 = this.mesh;
            v0.setVertices(this.vertices, 0, this.vertexIndex);
            v0.setIndices(this.triangles, 0, this.triangleIndex);
            if(this.blendingDisabled) {
                Gdx.gl.glDisable(v4);
            }
            else {
                Gdx.gl.glEnable(v4);
                if(this.blendSrcFunc != -1) {
                    Gdx.gl.glBlendFunc(this.blendSrcFunc, this.blendDstFunc);
                }
            }

            if(this.customShader != null) {
                v2 = this.customShader;
            }
            else {
                v2 = this.shader;
            }

            v0.render(v2, 4, 0, v1);
            this.vertexIndex = 0;
            this.triangleIndex = 0;
        }
    }

    public int getBlendDstFunc() {
        return this.blendDstFunc;
    }

    public int getBlendSrcFunc() {
        return this.blendSrcFunc;
    }

    public Color getColor() {
        int v1 = NumberUtils.floatToIntColor(this.color);
        Color v0 = this.tempColor;
        v0.r = (((float)(v1 & 255))) / 255f;
        v0.g = (((float)(v1 >>> 8 & 255))) / 255f;
        v0.b = (((float)(v1 >>> 16 & 255))) / 255f;
        v0.a = (((float)(v1 >>> 24 & 255))) / 255f;
        return v0;
    }

    public float getPackedColor() {
        return this.color;
    }

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    public boolean isBlendingEnabled() {
        boolean v0;
        if(!this.blendingDisabled) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public void setBlendFunction(int srcFunc, int dstFunc) {
        if(this.blendSrcFunc != srcFunc || this.blendDstFunc != dstFunc) {
            this.flush();
            this.blendSrcFunc = srcFunc;
            this.blendDstFunc = dstFunc;
        }
    }

    public void setColor(float color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
    }

    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
    }

    public void setProjectionMatrix(Matrix4 projection) {
        if(this.drawing) {
            this.flush();
        }

        this.projectionMatrix.set(projection);
        if(this.drawing) {
            this.setupMatrices();
        }
    }

    public void setShader(ShaderProgram shader) {
        if(this.drawing) {
            this.flush();
            if(this.customShader != null) {
                this.customShader.end();
            }
            else {
                this.shader.end();
            }
        }

        this.customShader = shader;
        if(this.drawing) {
            if(this.customShader != null) {
                this.customShader.begin();
            }
            else {
                this.shader.begin();
            }

            this.setupMatrices();
        }
    }

    public void setTransformMatrix(Matrix4 transform) {
        if(this.drawing) {
            this.flush();
        }

        this.transformMatrix.set(transform);
        if(this.drawing) {
            this.setupMatrices();
        }
    }

    private void setupMatrices() {
        this.combinedMatrix.set(this.projectionMatrix).mul(this.transformMatrix);
        if(this.customShader != null) {
            this.customShader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.customShader.setUniformi("u_texture", 0);
        }
        else {
            this.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.shader.setUniformi("u_texture", 0);
        }
    }

    private void switchTexture(Texture texture) {
        this.flush();
        this.lastTexture = texture;
        this.invTexWidth = 1f / (((float)texture.getWidth()));
        this.invTexHeight = 1f / (((float)texture.getHeight()));
    }
}

