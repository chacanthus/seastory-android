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
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.NumberUtils;
import java.nio.FloatBuffer;

public class SpriteCache implements Disposable {
    class Cache {
        int[] counts;
        final int id;
        int maxCount;
        final int offset;
        int textureCount;
        Texture[] textures;

        public Cache(int id, int offset) {
            super();
            this.id = id;
            this.offset = offset;
        }
    }

    private Array caches;
    private float color;
    private final Matrix4 combinedMatrix;
    private final IntArray counts;
    private Cache currentCache;
    private ShaderProgram customShader;
    private boolean drawing;
    private final Mesh mesh;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    private static final float[] tempVertices;
    private final Array textures;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;

    static  {
        SpriteCache.tempVertices = new float[30];
    }

    public SpriteCache() {
        this(1000, false);
    }

    public SpriteCache(int size, boolean useIndices) {
        this(size, SpriteCache.createDefaultShader(), useIndices);
    }

    public SpriteCache(int size, ShaderProgram shader, boolean useIndices) {
        int v5;
        super();
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.caches = new Array();
        this.combinedMatrix = new Matrix4();
        this.textures = new Array(8);
        this.counts = new IntArray(8);
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(1f, 1f, 1f, 1f);
        this.customShader = null;
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.shader = shader;
        if((useIndices) && size > 5460) {
            throw new IllegalArgumentException("Can\'t have more than 5460 sprites per batch: " + size);
        }

        if(useIndices) {
            v5 = 4;
        }
        else {
            v5 = 6;
        }

        int v8 = size * v5;
        if(useIndices) {
            v5 = size * 6;
        }
        else {
            v5 = 0;
        }

        VertexAttribute[] v9 = new VertexAttribute[3];
        v9[0] = new VertexAttribute(1, 2, "a_position");
        v9[1] = new VertexAttribute(4, 4, "a_color");
        v9[2] = new VertexAttribute(16, 2, "a_texCoord0");
        this.mesh = new Mesh(true, v8, v5, v9);
        this.mesh.setAutoBind(false);
        if(useIndices) {
            int v4 = size * 6;
            short[] v2 = new short[v4];
            short v3 = 0;
            int v1 = 0;
            while(v1 < v4) {
                v2[v1] = v3;
                v2[v1 + 1] = ((short)(v3 + 1));
                v2[v1 + 2] = ((short)(v3 + 2));
                v2[v1 + 3] = ((short)(v3 + 2));
                v2[v1 + 4] = ((short)(v3 + 3));
                v2[v1 + 5] = v3;
                v1 += 6;
                v3 = ((short)(v3 + 4));
            }

            this.mesh.setIndices(v2);
        }

        this.projectionMatrix.setToOrtho2D(0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
    }

    public void add(Texture texture, float x, float y) {
        int v9 = 16;
        int v8 = 15;
        float v5 = 1f;
        float v0 = x + (((float)texture.getWidth()));
        float v1 = y + (((float)texture.getHeight()));
        SpriteCache.tempVertices[0] = x;
        SpriteCache.tempVertices[1] = y;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = 0f;
        SpriteCache.tempVertices[4] = v5;
        SpriteCache.tempVertices[5] = x;
        SpriteCache.tempVertices[6] = v1;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = 0f;
        SpriteCache.tempVertices[9] = 0f;
        SpriteCache.tempVertices[10] = v0;
        SpriteCache.tempVertices[11] = v1;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v5;
        SpriteCache.tempVertices[14] = 0f;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[v8] = v0;
            SpriteCache.tempVertices[v9] = y;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v5;
            SpriteCache.tempVertices[19] = v5;
            this.add(texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[v8] = v0;
            SpriteCache.tempVertices[v9] = v1;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v5;
            SpriteCache.tempVertices[19] = 0f;
            SpriteCache.tempVertices[20] = v0;
            SpriteCache.tempVertices[21] = y;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v5;
            SpriteCache.tempVertices[24] = v5;
            SpriteCache.tempVertices[25] = x;
            SpriteCache.tempVertices[26] = y;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = 0f;
            SpriteCache.tempVertices[29] = v5;
            this.add(texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(Texture texture, float[] vertices, int offset, int length) {
        int v2;
        if(this.currentCache == null) {
            throw new IllegalStateException("beginCache must be called before add.");
        }

        if(this.mesh.getNumIndices() > 0) {
            v2 = 4;
        }
        else {
            v2 = 6;
        }

        int v0 = length / (v2 * 5) * 6;
        int v1 = this.textures.size - 1;
        if(v1 < 0 || this.textures.get(v1) != texture) {
            this.textures.add(texture);
            this.counts.add(v0);
        }
        else {
            this.counts.incr(v1, v0);
        }

        this.mesh.getVerticesBuffer().put(vertices, offset, length);
    }

    public void add(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v21;
        float v35;
        float v31;
        float v34;
        float v30;
        float v33;
        float v29;
        float v32;
        float v28;
        float v26 = x + originX;
        float v27 = y + originY;
        float v6 = -originX;
        float v8 = -originY;
        float v7 = width - originX;
        float v9 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v6 *= scaleX;
            v8 *= scaleY;
            v7 *= scaleX;
            v9 *= scaleY;
        }

        float v12 = v6;
        float v13 = v8;
        float v14 = v6;
        float v15 = v9;
        float v16 = v7;
        float v17 = v9;
        float v18 = v7;
        float v19 = v8;
        if(rotation != 0f) {
            float v5 = MathUtils.cosDeg(rotation);
            float v20 = MathUtils.sinDeg(rotation);
            v28 = v5 * v12 - v20 * v13;
            v32 = v20 * v12 + v5 * v13;
            v29 = v5 * v14 - v20 * v15;
            v33 = v20 * v14 + v5 * v15;
            v30 = v5 * v16 - v20 * v17;
            v34 = v20 * v16 + v5 * v17;
            v31 = v28 + (v30 - v29);
            v35 = v34 - (v33 - v32);
        }
        else {
            v28 = v12;
            v32 = v13;
            v29 = v14;
            v33 = v15;
            v30 = v16;
            v34 = v17;
            v31 = v18;
            v35 = v19;
        }

        v28 += v26;
        v32 += v27;
        v29 += v26;
        v33 += v27;
        v30 += v26;
        v34 += v27;
        v31 += v26;
        v35 += v27;
        float v11 = 1f / (((float)texture.getWidth()));
        float v10 = 1f / (((float)texture.getHeight()));
        float v22 = (((float)srcX)) * v11;
        float v24 = (((float)(srcY + srcHeight))) * v10;
        float v23 = (((float)(srcX + srcWidth))) * v11;
        float v25 = (((float)srcY)) * v10;
        if(flipX) {
            v21 = v22;
            v22 = v23;
            v23 = v21;
        }

        if(flipY) {
            v21 = v24;
            v24 = v25;
            v25 = v21;
        }

        SpriteCache.tempVertices[0] = v28;
        SpriteCache.tempVertices[1] = v32;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = v22;
        SpriteCache.tempVertices[4] = v24;
        SpriteCache.tempVertices[5] = v29;
        SpriteCache.tempVertices[6] = v33;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = v22;
        SpriteCache.tempVertices[9] = v25;
        SpriteCache.tempVertices[10] = v30;
        SpriteCache.tempVertices[11] = v34;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v23;
        SpriteCache.tempVertices[14] = v25;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v31;
            SpriteCache.tempVertices[16] = v35;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v23;
            SpriteCache.tempVertices[19] = v24;
            this.add(texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v30;
            SpriteCache.tempVertices[16] = v34;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v23;
            SpriteCache.tempVertices[19] = v25;
            SpriteCache.tempVertices[20] = v31;
            SpriteCache.tempVertices[21] = v35;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v23;
            SpriteCache.tempVertices[24] = v24;
            SpriteCache.tempVertices[25] = v28;
            SpriteCache.tempVertices[26] = v32;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = v22;
            SpriteCache.tempVertices[29] = v24;
            this.add(texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v5;
        float v4 = 1f / (((float)texture.getWidth()));
        float v3 = 1f / (((float)texture.getHeight()));
        float v6 = (((float)srcX)) * v4;
        float v8 = (((float)(srcY + srcHeight))) * v3;
        float v7 = (((float)(srcX + srcWidth))) * v4;
        float v9 = (((float)srcY)) * v3;
        float v1 = x + width;
        float v2 = y + height;
        if(flipX) {
            v5 = v6;
            v6 = v7;
            v7 = v5;
        }

        if(flipY) {
            v5 = v8;
            v8 = v9;
            v9 = v5;
        }

        SpriteCache.tempVertices[0] = x;
        SpriteCache.tempVertices[1] = y;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = v6;
        SpriteCache.tempVertices[4] = v8;
        SpriteCache.tempVertices[5] = x;
        SpriteCache.tempVertices[6] = v2;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = v6;
        SpriteCache.tempVertices[9] = v9;
        SpriteCache.tempVertices[10] = v1;
        SpriteCache.tempVertices[11] = v2;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v7;
        SpriteCache.tempVertices[14] = v9;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v1;
            SpriteCache.tempVertices[16] = y;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v7;
            SpriteCache.tempVertices[19] = v8;
            this.add(texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v1;
            SpriteCache.tempVertices[16] = v2;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v7;
            SpriteCache.tempVertices[19] = v9;
            SpriteCache.tempVertices[20] = v1;
            SpriteCache.tempVertices[21] = y;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v7;
            SpriteCache.tempVertices[24] = v8;
            SpriteCache.tempVertices[25] = x;
            SpriteCache.tempVertices[26] = y;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = v6;
            SpriteCache.tempVertices[29] = v8;
            this.add(texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(Texture texture, float x, float y, int srcWidth, int srcHeight, float u, float v, float u2, float v2, float color) {
        float v0 = x + (((float)srcWidth));
        float v1 = y + (((float)srcHeight));
        SpriteCache.tempVertices[0] = x;
        SpriteCache.tempVertices[1] = y;
        SpriteCache.tempVertices[2] = color;
        SpriteCache.tempVertices[3] = u;
        SpriteCache.tempVertices[4] = v;
        SpriteCache.tempVertices[5] = x;
        SpriteCache.tempVertices[6] = v1;
        SpriteCache.tempVertices[7] = color;
        SpriteCache.tempVertices[8] = u;
        SpriteCache.tempVertices[9] = v2;
        SpriteCache.tempVertices[10] = v0;
        SpriteCache.tempVertices[11] = v1;
        SpriteCache.tempVertices[12] = color;
        SpriteCache.tempVertices[13] = u2;
        SpriteCache.tempVertices[14] = v2;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v0;
            SpriteCache.tempVertices[16] = y;
            SpriteCache.tempVertices[17] = color;
            SpriteCache.tempVertices[18] = u2;
            SpriteCache.tempVertices[19] = v;
            this.add(texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v0;
            SpriteCache.tempVertices[16] = v1;
            SpriteCache.tempVertices[17] = color;
            SpriteCache.tempVertices[18] = u2;
            SpriteCache.tempVertices[19] = v2;
            SpriteCache.tempVertices[20] = v0;
            SpriteCache.tempVertices[21] = y;
            SpriteCache.tempVertices[22] = color;
            SpriteCache.tempVertices[23] = u2;
            SpriteCache.tempVertices[24] = v;
            SpriteCache.tempVertices[25] = x;
            SpriteCache.tempVertices[26] = y;
            SpriteCache.tempVertices[27] = color;
            SpriteCache.tempVertices[28] = u;
            SpriteCache.tempVertices[29] = v;
            this.add(texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        float v4 = 1f / (((float)texture.getWidth()));
        float v3 = 1f / (((float)texture.getHeight()));
        float v5 = (((float)srcX)) * v4;
        float v7 = (((float)(srcY + srcHeight))) * v3;
        float v6 = (((float)(srcX + srcWidth))) * v4;
        float v8 = (((float)srcY)) * v3;
        float v1 = x + (((float)srcWidth));
        float v2 = y + (((float)srcHeight));
        SpriteCache.tempVertices[0] = x;
        SpriteCache.tempVertices[1] = y;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = v5;
        SpriteCache.tempVertices[4] = v7;
        SpriteCache.tempVertices[5] = x;
        SpriteCache.tempVertices[6] = v2;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = v5;
        SpriteCache.tempVertices[9] = v8;
        SpriteCache.tempVertices[10] = v1;
        SpriteCache.tempVertices[11] = v2;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v6;
        SpriteCache.tempVertices[14] = v8;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v1;
            SpriteCache.tempVertices[16] = y;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v6;
            SpriteCache.tempVertices[19] = v7;
            this.add(texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v1;
            SpriteCache.tempVertices[16] = v2;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v6;
            SpriteCache.tempVertices[19] = v8;
            SpriteCache.tempVertices[20] = v1;
            SpriteCache.tempVertices[21] = y;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v6;
            SpriteCache.tempVertices[24] = v7;
            SpriteCache.tempVertices[25] = x;
            SpriteCache.tempVertices[26] = y;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = v5;
            SpriteCache.tempVertices[29] = v7;
            this.add(texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(Sprite sprite) {
        int v6 = 20;
        int v5 = 15;
        int v3 = 5;
        if(this.mesh.getNumIndices() > 0) {
            this.add(sprite.getTexture(), sprite.getVertices(), 0, v6);
        }
        else {
            float[] v0 = sprite.getVertices();
            System.arraycopy(v0, 0, SpriteCache.tempVertices, 0, v5);
            System.arraycopy(v0, 10, SpriteCache.tempVertices, v5, v3);
            System.arraycopy(v0, v5, SpriteCache.tempVertices, v6, v3);
            System.arraycopy(v0, 0, SpriteCache.tempVertices, 25, v3);
            this.add(sprite.getTexture(), SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(TextureRegion region, float x, float y) {
        this.add(region, x, y, ((float)region.getRegionWidth()), ((float)region.getRegionHeight()));
    }

    public void add(TextureRegion region, float x, float y, float width, float height) {
        float v0 = x + width;
        float v1 = y + height;
        float v2 = region.u;
        float v4 = region.v2;
        float v3 = region.u2;
        float v5 = region.v;
        SpriteCache.tempVertices[0] = x;
        SpriteCache.tempVertices[1] = y;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = v2;
        SpriteCache.tempVertices[4] = v4;
        SpriteCache.tempVertices[5] = x;
        SpriteCache.tempVertices[6] = v1;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = v2;
        SpriteCache.tempVertices[9] = v5;
        SpriteCache.tempVertices[10] = v0;
        SpriteCache.tempVertices[11] = v1;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v3;
        SpriteCache.tempVertices[14] = v5;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v0;
            SpriteCache.tempVertices[16] = y;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v3;
            SpriteCache.tempVertices[19] = v4;
            this.add(region.texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v0;
            SpriteCache.tempVertices[16] = v1;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v3;
            SpriteCache.tempVertices[19] = v5;
            SpriteCache.tempVertices[20] = v0;
            SpriteCache.tempVertices[21] = y;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v3;
            SpriteCache.tempVertices[24] = v4;
            SpriteCache.tempVertices[25] = x;
            SpriteCache.tempVertices[26] = y;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = v2;
            SpriteCache.tempVertices[29] = v4;
            this.add(region.texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void add(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        float v32;
        float v28;
        float v31;
        float v27;
        float v30;
        float v26;
        float v29;
        float v25;
        float v23 = x + originX;
        float v24 = y + originY;
        float v6 = -originX;
        float v8 = -originY;
        float v7 = width - originX;
        float v9 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v6 *= scaleX;
            v8 *= scaleY;
            v7 *= scaleX;
            v9 *= scaleY;
        }

        float v10 = v6;
        float v11 = v8;
        float v12 = v6;
        float v13 = v9;
        float v14 = v7;
        float v15 = v9;
        float v16 = v7;
        float v17 = v8;
        if(rotation != 0f) {
            float v5 = MathUtils.cosDeg(rotation);
            float v18 = MathUtils.sinDeg(rotation);
            v25 = v5 * v10 - v18 * v11;
            v29 = v18 * v10 + v5 * v11;
            v26 = v5 * v12 - v18 * v13;
            v30 = v18 * v12 + v5 * v13;
            v27 = v5 * v14 - v18 * v15;
            v31 = v18 * v14 + v5 * v15;
            v28 = v25 + (v27 - v26);
            v32 = v31 - (v30 - v29);
        }
        else {
            v25 = v10;
            v29 = v11;
            v26 = v12;
            v30 = v13;
            v27 = v14;
            v31 = v15;
            v28 = v16;
            v32 = v17;
        }

        v25 += v23;
        v29 += v24;
        v27 += v23;
        v31 += v24;
        v28 += v23;
        v32 += v24;
        float v19 = region.u;
        float v21 = region.v2;
        float v20 = region.u2;
        float v22 = region.v;
        SpriteCache.tempVertices[0] = v25;
        SpriteCache.tempVertices[1] = v29;
        SpriteCache.tempVertices[2] = this.color;
        SpriteCache.tempVertices[3] = v19;
        SpriteCache.tempVertices[4] = v21;
        SpriteCache.tempVertices[5] = v26 + v23;
        SpriteCache.tempVertices[6] = v30 + v24;
        SpriteCache.tempVertices[7] = this.color;
        SpriteCache.tempVertices[8] = v19;
        SpriteCache.tempVertices[9] = v22;
        SpriteCache.tempVertices[10] = v27;
        SpriteCache.tempVertices[11] = v31;
        SpriteCache.tempVertices[12] = this.color;
        SpriteCache.tempVertices[13] = v20;
        SpriteCache.tempVertices[14] = v22;
        if(this.mesh.getNumIndices() > 0) {
            SpriteCache.tempVertices[15] = v28;
            SpriteCache.tempVertices[16] = v32;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v20;
            SpriteCache.tempVertices[19] = v21;
            this.add(region.texture, SpriteCache.tempVertices, 0, 20);
        }
        else {
            SpriteCache.tempVertices[15] = v27;
            SpriteCache.tempVertices[16] = v31;
            SpriteCache.tempVertices[17] = this.color;
            SpriteCache.tempVertices[18] = v20;
            SpriteCache.tempVertices[19] = v22;
            SpriteCache.tempVertices[20] = v28;
            SpriteCache.tempVertices[21] = v32;
            SpriteCache.tempVertices[22] = this.color;
            SpriteCache.tempVertices[23] = v20;
            SpriteCache.tempVertices[24] = v21;
            SpriteCache.tempVertices[25] = v25;
            SpriteCache.tempVertices[26] = v29;
            SpriteCache.tempVertices[27] = this.color;
            SpriteCache.tempVertices[28] = v19;
            SpriteCache.tempVertices[29] = v21;
            this.add(region.texture, SpriteCache.tempVertices, 0, 30);
        }
    }

    public void begin() {
        if(this.drawing) {
            throw new IllegalStateException("end must be called before begin.");
        }

        this.renderCalls = 0;
        this.combinedMatrix.set(this.projectionMatrix).mul(this.transformMatrix);
        Gdx.gl20.glDepthMask(false);
        if(this.customShader != null) {
            this.customShader.begin();
            this.customShader.setUniformMatrix("u_proj", this.projectionMatrix);
            this.customShader.setUniformMatrix("u_trans", this.transformMatrix);
            this.customShader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.customShader.setUniformi("u_texture", 0);
            this.mesh.bind(this.customShader);
        }
        else {
            this.shader.begin();
            this.shader.setUniformMatrix("u_projectionViewMatrix", this.combinedMatrix);
            this.shader.setUniformi("u_texture", 0);
            this.mesh.bind(this.shader);
        }

        this.drawing = true;
    }

    public void beginCache() {
        if(this.currentCache != null) {
            throw new IllegalStateException("endCache must be called before begin.");
        }

        this.currentCache = new Cache(this.caches.size, this.mesh.getVerticesBuffer().limit());
        this.caches.add(this.currentCache);
        this.mesh.getVerticesBuffer().compact();
    }

    public void beginCache(int cacheID) {
        if(this.currentCache != null) {
            throw new IllegalStateException("endCache must be called before begin.");
        }

        if(cacheID == this.caches.size - 1) {
            this.mesh.getVerticesBuffer().limit(this.caches.removeIndex(cacheID).offset);
            this.beginCache();
        }
        else {
            this.currentCache = this.caches.get(cacheID);
            this.mesh.getVerticesBuffer().position(this.currentCache.offset);
        }
    }

    public void clear() {
        this.caches.clear();
        this.mesh.getVerticesBuffer().clear().flip();
    }

    static ShaderProgram createDefaultShader() {
        ShaderProgram v1 = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projectionViewMatrix;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_color.a = v_color.a * (255.0/254.0);\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projectionViewMatrix * a_position;\n}\n", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if(!v1.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + v1.getLog());
        }

        return v1;
    }

    public void dispose() {
        this.mesh.dispose();
        if(this.shader != null) {
            this.shader.dispose();
        }
    }

    public void draw(int cacheID) {
        int v7;
        int v8 = 4;
        if(!this.drawing) {
            throw new IllegalStateException("SpriteCache.begin must be called before draw.");
        }

        Object v0 = this.caches.get(cacheID);
        if(this.mesh.getNumIndices() > 0) {
            v7 = v8;
        }
        else {
            v7 = 6;
        }

        int v4 = ((Cache)v0).offset / (v7 * 5) * 6;
        Texture[] v6 = ((Cache)v0).textures;
        int[] v2 = ((Cache)v0).counts;
        int v5 = ((Cache)v0).textureCount;
        int v3;
        for(v3 = 0; v3 < v5; ++v3) {
            int v1 = v2[v3];
            v6[v3].bind();
            if(this.customShader != null) {
                this.mesh.render(this.customShader, v8, v4, v1);
            }
            else {
                this.mesh.render(this.shader, v8, v4, v1);
            }

            v4 += v1;
        }

        this.renderCalls += v5;
        this.totalRenderCalls += v5;
    }

    public void draw(int cacheID, int offset, int length) {
        int v8 = 4;
        if(!this.drawing) {
            throw new IllegalStateException("SpriteCache.begin must be called before draw.");
        }

        Object v0 = this.caches.get(cacheID);
        offset = offset * 6 + ((Cache)v0).offset;
        length *= 6;
        Texture[] v5 = ((Cache)v0).textures;
        int[] v2 = ((Cache)v0).counts;
        int v4 = ((Cache)v0).textureCount;
        int v3;
        for(v3 = 0; v3 < v4; ++v3) {
            v5[v3].bind();
            int v1 = v2[v3];
            if(v1 > length) {
                v3 = v4;
                v1 = length;
            }
            else {
                length -= v1;
            }

            if(this.customShader != null) {
                this.mesh.render(this.customShader, v8, offset, v1);
            }
            else {
                this.mesh.render(this.shader, v8, offset, v1);
            }

            offset += v1;
        }

        this.renderCalls += ((Cache)v0).textureCount;
        this.totalRenderCalls += v4;
    }

    public void end() {
        if(!this.drawing) {
            throw new IllegalStateException("begin must be called before end.");
        }

        this.drawing = false;
        this.shader.end();
        Gdx.gl20.glDepthMask(true);
        if(this.customShader != null) {
            this.mesh.unbind(this.customShader);
        }
        else {
            this.mesh.unbind(this.shader);
        }
    }

    public int endCache() {
        int v4;
        int v2;
        if(this.currentCache == null) {
            throw new IllegalStateException("beginCache must be called before endCache.");
        }

        Cache v0 = this.currentCache;
        int v1 = this.mesh.getVerticesBuffer().position() - v0.offset;
        if(v0.textures == null) {
            v0.maxCount = v1;
            v0.textureCount = this.textures.size;
            v0.textures = this.textures.toArray(Texture.class);
            v0.counts = new int[v0.textureCount];
            v2 = 0;
            v4 = this.counts.size;
            while(v2 < v4) {
                v0.counts[v2] = this.counts.get(v2);
                ++v2;
            }

            this.mesh.getVerticesBuffer().flip();
        }
        else if(v1 > v0.maxCount) {
            throw new GdxRuntimeException("If a cache is not the last created, it cannot be redefined with more entries than when it was first created: " + v1 + " (" + v0.maxCount + " max)");
        }
        else {
            v0.textureCount = this.textures.size;
            if(v0.textures.length < v0.textureCount) {
                v0.textures = new Texture[v0.textureCount];
            }

            v2 = 0;
            v4 = v0.textureCount;
            while(v2 < v4) {
                v0.textures[v2] = this.textures.get(v2);
                ++v2;
            }

            if(v0.counts.length < v0.textureCount) {
                v0.counts = new int[v0.textureCount];
            }

            v2 = 0;
            v4 = v0.textureCount;
            while(v2 < v4) {
                v0.counts[v2] = this.counts.get(v2);
                ++v2;
            }

            FloatBuffer v5 = this.mesh.getVerticesBuffer();
            v5.position(0);
            Object v3 = this.caches.get(this.caches.size - 1);
            v5.limit(((Cache)v3).offset + ((Cache)v3).maxCount);
        }

        this.currentCache = null;
        this.textures.clear();
        this.counts.clear();
        return v0.id;
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

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
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
            throw new IllegalStateException("Can\'t set the matrix within begin/end.");
        }

        this.projectionMatrix.set(projection);
    }

    public void setShader(ShaderProgram shader) {
        this.customShader = shader;
    }

    public void setTransformMatrix(Matrix4 transform) {
        if(this.drawing) {
            throw new IllegalStateException("Can\'t set the matrix within begin/end.");
        }

        this.transformMatrix.set(transform);
    }
}

