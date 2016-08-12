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

public class SpriteBatch implements Batch {
    private int blendDstFunc;
    private int blendSrcFunc;
    private boolean blendingDisabled;
    float color;
    private final Matrix4 combinedMatrix;
    private ShaderProgram customShader;
    boolean drawing;
    int idx;
    float invTexHeight;
    float invTexWidth;
    Texture lastTexture;
    public int maxSpritesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    final float[] vertices;

    public SpriteBatch() {
        this(1000, null);
    }

    public SpriteBatch(int size, ShaderProgram defaultShader) {
        super();
        this.idx = 0;
        this.lastTexture = null;
        this.invTexWidth = 0f;
        this.invTexHeight = 0f;
        this.drawing = false;
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.blendingDisabled = false;
        this.blendSrcFunc = 770;
        this.blendDstFunc = 771;
        this.customShader = null;
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(1f, 1f, 1f, 1f);
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.maxSpritesInBatch = 0;
        if(size > 5460) {
            throw new IllegalArgumentException("Can\'t have more than 5460 sprites per batch: " + size);
        }

        VertexDataType v3 = VertexDataType.VertexArray;
        if(Gdx.gl30 != null) {
            v3 = VertexDataType.VertexBufferObjectWithVAO;
        }

        VertexAttribute[] v7 = new VertexAttribute[3];
        v7[0] = new VertexAttribute(1, 2, "a_position");
        v7[1] = new VertexAttribute(4, 4, "a_color");
        v7[2] = new VertexAttribute(16, 2, "a_texCoord0");
        this.mesh = new Mesh(v3, false, size * 4, size * 6, v7);
        this.projectionMatrix.setToOrtho2D(0f, 0f, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
        this.vertices = new float[size * 20];
        int v11 = size * 6;
        short[] v9 = new short[v11];
        short v10 = 0;
        int v8 = 0;
        while(v8 < v11) {
            v9[v8] = v10;
            v9[v8 + 1] = ((short)(v10 + 1));
            v9[v8 + 2] = ((short)(v10 + 2));
            v9[v8 + 3] = ((short)(v10 + 2));
            v9[v8 + 4] = ((short)(v10 + 3));
            v9[v8 + 5] = v10;
            v8 += 6;
            v10 = ((short)(v10 + 4));
        }

        this.mesh.setIndices(v9);
        if(defaultShader == null) {
            this.shader = SpriteBatch.createDefaultShader();
            this.ownsShader = true;
        }
        else {
            this.shader = defaultShader;
        }
    }

    public SpriteBatch(int size) {
        this(size, null);
    }

    public void begin() {
        if(this.drawing) {
            throw new IllegalStateException("SpriteBatch.end must be called before begin.");
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

    public static ShaderProgram createDefaultShader() {
        ShaderProgram v1 = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_color.a = v_color.a * (255.0/254.0);\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if(!v1.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + v1.getLog());
        }

        return v1;
    }

    public void disableBlending() {
        if(!this.blendingDisabled) {
            this.flush();
            this.blendingDisabled = true;
        }
    }

    public void dispose() {
        this.mesh.dispose();
        if((this.ownsShader) && this.shader != null) {
            this.shader.dispose();
        }
    }

    public void draw(TextureRegion region, float x, float y, float width, float height) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v10 = this.vertices;
        Texture v5 = region.texture;
        if(v5 != this.lastTexture) {
            this.switchTexture(v5);
        }
        else if(this.idx == v10.length) {
            this.flush();
        }

        float v1 = x + width;
        float v2 = y + height;
        float v6 = region.u;
        float v8 = region.v2;
        float v7 = region.u2;
        float v9 = region.v;
        float v0 = this.color;
        int v4 = this.idx + 1;
        v10[this.idx] = x;
        int v3 = v4 + 1;
        v10[v4] = y;
        v4 = v3 + 1;
        v10[v3] = v0;
        v3 = v4 + 1;
        v10[v4] = v6;
        v4 = v3 + 1;
        v10[v3] = v8;
        v3 = v4 + 1;
        v10[v4] = x;
        v4 = v3 + 1;
        v10[v3] = v2;
        v3 = v4 + 1;
        v10[v4] = v0;
        v4 = v3 + 1;
        v10[v3] = v6;
        v3 = v4 + 1;
        v10[v4] = v9;
        v4 = v3 + 1;
        v10[v3] = v1;
        v3 = v4 + 1;
        v10[v4] = v2;
        v4 = v3 + 1;
        v10[v3] = v0;
        v3 = v4 + 1;
        v10[v4] = v7;
        v4 = v3 + 1;
        v10[v3] = v9;
        v3 = v4 + 1;
        v10[v4] = v1;
        v4 = v3 + 1;
        v10[v3] = y;
        v3 = v4 + 1;
        v10[v4] = v0;
        v4 = v3 + 1;
        v10[v3] = v7;
        v10[v4] = v8;
        this.idx = v4 + 1;
    }

    public void draw(Texture texture, float x, float y) {
        this.draw(texture, x, y, ((float)texture.getWidth()), ((float)texture.getHeight()));
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v9 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == v9.length) {
            this.flush();
        }

        float v1 = x + width;
        float v2 = y + height;
        float v0 = this.color;
        int v4 = this.idx + 1;
        v9[this.idx] = x;
        int v3 = v4 + 1;
        v9[v4] = y;
        v4 = v3 + 1;
        v9[v3] = v0;
        v3 = v4 + 1;
        v9[v4] = 0f;
        v4 = v3 + 1;
        v9[v3] = 1f;
        v3 = v4 + 1;
        v9[v4] = x;
        v4 = v3 + 1;
        v9[v3] = v2;
        v3 = v4 + 1;
        v9[v4] = v0;
        v4 = v3 + 1;
        v9[v3] = 0f;
        v3 = v4 + 1;
        v9[v4] = 0f;
        v4 = v3 + 1;
        v9[v3] = v1;
        v3 = v4 + 1;
        v9[v4] = v2;
        v4 = v3 + 1;
        v9[v3] = v0;
        v3 = v4 + 1;
        v9[v4] = 1f;
        v4 = v3 + 1;
        v9[v3] = 0f;
        v3 = v4 + 1;
        v9[v4] = v1;
        v4 = v3 + 1;
        v9[v3] = y;
        v3 = v4 + 1;
        v9[v4] = v0;
        v4 = v3 + 1;
        v9[v3] = 1f;
        v9[v4] = 1f;
        this.idx = v4 + 1;
    }

    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v5 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == v5.length) {
            this.flush();
        }

        float v1 = x + width;
        float v2 = y + height;
        float v0 = this.color;
        int v4 = this.idx + 1;
        v5[this.idx] = x;
        int v3 = v4 + 1;
        v5[v4] = y;
        v4 = v3 + 1;
        v5[v3] = v0;
        v3 = v4 + 1;
        v5[v4] = u;
        v4 = v3 + 1;
        v5[v3] = v;
        v3 = v4 + 1;
        v5[v4] = x;
        v4 = v3 + 1;
        v5[v3] = v2;
        v3 = v4 + 1;
        v5[v4] = v0;
        v4 = v3 + 1;
        v5[v3] = u;
        v3 = v4 + 1;
        v5[v4] = v2;
        v4 = v3 + 1;
        v5[v3] = v1;
        v3 = v4 + 1;
        v5[v4] = v2;
        v4 = v3 + 1;
        v5[v3] = v0;
        v3 = v4 + 1;
        v5[v4] = u2;
        v4 = v3 + 1;
        v5[v3] = v2;
        v3 = v4 + 1;
        v5[v4] = v1;
        v4 = v3 + 1;
        v5[v3] = y;
        v3 = v4 + 1;
        v5[v4] = v0;
        v4 = v3 + 1;
        v5[v3] = u2;
        v5[v4] = v;
        this.idx = v4 + 1;
    }

    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v19;
        float v34;
        float v30;
        float v33;
        float v29;
        float v32;
        float v28;
        float v31;
        float v27;
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v24 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == v24.length) {
            this.flush();
        }

        float v25 = x + originX;
        float v26 = y + originY;
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
            v27 = v3 * v10 - v18 * v11;
            v31 = v18 * v10 + v3 * v11;
            v28 = v3 * v12 - v18 * v13;
            v32 = v18 * v12 + v3 * v13;
            v29 = v3 * v14 - v18 * v15;
            v33 = v18 * v14 + v3 * v15;
            v30 = v27 + (v29 - v28);
            v34 = v33 - (v32 - v31);
        }
        else {
            v27 = v10;
            v31 = v11;
            v28 = v12;
            v32 = v13;
            v29 = v14;
            v33 = v15;
            v30 = v16;
            v34 = v17;
        }

        v27 += v25;
        v31 += v26;
        v28 += v25;
        v32 += v26;
        v29 += v25;
        v33 += v26;
        v30 += v25;
        v34 += v26;
        float v20 = (((float)srcX)) * this.invTexWidth;
        float v22 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v21 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v23 = (((float)srcY)) * this.invTexHeight;
        if(flipX) {
            v19 = v20;
            v20 = v21;
            v21 = v19;
        }

        if(flipY) {
            v19 = v22;
            v22 = v23;
            v23 = v19;
        }

        float v2 = this.color;
        int v9 = this.idx + 1;
        v24[this.idx] = v27;
        int v8 = v9 + 1;
        v24[v9] = v31;
        v9 = v8 + 1;
        v24[v8] = v2;
        v8 = v9 + 1;
        v24[v9] = v20;
        v9 = v8 + 1;
        v24[v8] = v22;
        v8 = v9 + 1;
        v24[v9] = v28;
        v9 = v8 + 1;
        v24[v8] = v32;
        v8 = v9 + 1;
        v24[v9] = v2;
        v9 = v8 + 1;
        v24[v8] = v20;
        v8 = v9 + 1;
        v24[v9] = v23;
        v9 = v8 + 1;
        v24[v8] = v29;
        v8 = v9 + 1;
        v24[v9] = v33;
        v9 = v8 + 1;
        v24[v8] = v2;
        v8 = v9 + 1;
        v24[v9] = v21;
        v9 = v8 + 1;
        v24[v8] = v23;
        v8 = v9 + 1;
        v24[v9] = v30;
        v9 = v8 + 1;
        v24[v8] = v34;
        v8 = v9 + 1;
        v24[v9] = v2;
        v9 = v8 + 1;
        v24[v8] = v21;
        v24[v9] = v22;
        this.idx = v9 + 1;
    }

    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v6;
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v11 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == v11.length) {
            this.flush();
        }

        float v7 = (((float)srcX)) * this.invTexWidth;
        float v9 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v8 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v10 = (((float)srcY)) * this.invTexHeight;
        float v2 = x + width;
        float v3 = y + height;
        if(flipX) {
            v6 = v7;
            v7 = v8;
            v8 = v6;
        }

        if(flipY) {
            v6 = v9;
            v9 = v10;
            v10 = v6;
        }

        float v1 = this.color;
        int v5 = this.idx + 1;
        v11[this.idx] = x;
        int v4 = v5 + 1;
        v11[v5] = y;
        v5 = v4 + 1;
        v11[v4] = v1;
        v4 = v5 + 1;
        v11[v5] = v7;
        v5 = v4 + 1;
        v11[v4] = v9;
        v4 = v5 + 1;
        v11[v5] = x;
        v5 = v4 + 1;
        v11[v4] = v3;
        v4 = v5 + 1;
        v11[v5] = v1;
        v5 = v4 + 1;
        v11[v4] = v7;
        v4 = v5 + 1;
        v11[v5] = v10;
        v5 = v4 + 1;
        v11[v4] = v2;
        v4 = v5 + 1;
        v11[v5] = v3;
        v5 = v4 + 1;
        v11[v4] = v1;
        v4 = v5 + 1;
        v11[v5] = v8;
        v5 = v4 + 1;
        v11[v4] = v10;
        v4 = v5 + 1;
        v11[v5] = v2;
        v5 = v4 + 1;
        v11[v4] = y;
        v4 = v5 + 1;
        v11[v5] = v1;
        v5 = v4 + 1;
        v11[v4] = v8;
        v11[v5] = v9;
        this.idx = v5 + 1;
    }

    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v10 = this.vertices;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == v10.length) {
            this.flush();
        }

        float v6 = (((float)srcX)) * this.invTexWidth;
        float v8 = (((float)(srcY + srcHeight))) * this.invTexHeight;
        float v7 = (((float)(srcX + srcWidth))) * this.invTexWidth;
        float v9 = (((float)srcY)) * this.invTexHeight;
        float v2 = x + (((float)srcWidth));
        float v3 = y + (((float)srcHeight));
        float v1 = this.color;
        int v5 = this.idx + 1;
        v10[this.idx] = x;
        int v4 = v5 + 1;
        v10[v5] = y;
        v5 = v4 + 1;
        v10[v4] = v1;
        v4 = v5 + 1;
        v10[v5] = v6;
        v5 = v4 + 1;
        v10[v4] = v8;
        v4 = v5 + 1;
        v10[v5] = x;
        v5 = v4 + 1;
        v10[v4] = v3;
        v4 = v5 + 1;
        v10[v5] = v1;
        v5 = v4 + 1;
        v10[v4] = v6;
        v4 = v5 + 1;
        v10[v5] = v9;
        v5 = v4 + 1;
        v10[v4] = v2;
        v4 = v5 + 1;
        v10[v5] = v3;
        v5 = v4 + 1;
        v10[v4] = v1;
        v4 = v5 + 1;
        v10[v5] = v7;
        v5 = v4 + 1;
        v10[v4] = v9;
        v4 = v5 + 1;
        v10[v5] = v2;
        v5 = v4 + 1;
        v10[v4] = y;
        v4 = v5 + 1;
        v10[v5] = v1;
        v5 = v4 + 1;
        v10[v4] = v7;
        v10[v5] = v8;
        this.idx = v5 + 1;
    }

    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        int v2 = this.vertices.length;
        int v1 = v2;
        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else {
            v1 -= this.idx;
            if(v1 == 0) {
                this.flush();
                v1 = v2;
            }
        }

        int v0 = Math.min(v1, count);
        System.arraycopy(spriteVertices, offset, this.vertices, this.idx, v0);
        this.idx += v0;
        count -= v0;
        while(count > 0) {
            offset += v0;
            this.flush();
            v0 = Math.min(v2, count);
            System.arraycopy(spriteVertices, offset, this.vertices, 0, v0);
            this.idx += v0;
            count -= v0;
        }
    }

    public void draw(TextureRegion region, float x, float y) {
        this.draw(region, x, y, ((float)region.getRegionWidth()), ((float)region.getRegionHeight()));
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        float v34;
        float v30;
        float v33;
        float v29;
        float v32;
        float v28;
        float v31;
        float v27;
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v24 = this.vertices;
        Texture v19 = region.texture;
        if(v19 != this.lastTexture) {
            this.switchTexture(v19);
        }
        else if(this.idx == v24.length) {
            this.flush();
        }

        float v25 = x + originX;
        float v26 = y + originY;
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
            v27 = v3 * v10 - v18 * v11;
            v31 = v18 * v10 + v3 * v11;
            v28 = v3 * v12 - v18 * v13;
            v32 = v18 * v12 + v3 * v13;
            v29 = v3 * v14 - v18 * v15;
            v33 = v18 * v14 + v3 * v15;
            v30 = v27 + (v29 - v28);
            v34 = v33 - (v32 - v31);
        }
        else {
            v27 = v10;
            v31 = v11;
            v28 = v12;
            v32 = v13;
            v29 = v14;
            v33 = v15;
            v30 = v16;
            v34 = v17;
        }

        float v20 = region.u;
        float v22 = region.v2;
        float v21 = region.u2;
        float v23 = region.v;
        float v2 = this.color;
        int v9 = this.idx + 1;
        v24[this.idx] = v27 + v25;
        int v8 = v9 + 1;
        v24[v9] = v31 + v26;
        v9 = v8 + 1;
        v24[v8] = v2;
        v8 = v9 + 1;
        v24[v9] = v20;
        v9 = v8 + 1;
        v24[v8] = v22;
        v8 = v9 + 1;
        v24[v9] = v28 + v25;
        v9 = v8 + 1;
        v24[v8] = v32 + v26;
        v8 = v9 + 1;
        v24[v9] = v2;
        v9 = v8 + 1;
        v24[v8] = v20;
        v8 = v9 + 1;
        v24[v9] = v23;
        v9 = v8 + 1;
        v24[v8] = v29 + v25;
        v8 = v9 + 1;
        v24[v9] = v33 + v26;
        v9 = v8 + 1;
        v24[v8] = v2;
        v8 = v9 + 1;
        v24[v9] = v21;
        v9 = v8 + 1;
        v24[v8] = v23;
        v8 = v9 + 1;
        v24[v9] = v30 + v25;
        v9 = v8 + 1;
        v24[v8] = v34 + v26;
        v8 = v9 + 1;
        v24[v9] = v2;
        v9 = v8 + 1;
        v24[v8] = v21;
        v24[v9] = v22;
        this.idx = v9 + 1;
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        float v27;
        float v23;
        float v26;
        float v22;
        float v25;
        float v21;
        float v24;
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
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v28 = this.vertices;
        Texture v19 = region.texture;
        if(v19 != this.lastTexture) {
            this.switchTexture(v19);
        }
        else if(this.idx == v28.length) {
            this.flush();
        }

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
        if(clockwise) {
            v20 = region.u2;
            v24 = region.v2;
            v21 = region.u;
            v25 = region.v2;
            v22 = region.u;
            v26 = region.v;
            v23 = region.u2;
            v27 = region.v;
        }
        else {
            v20 = region.u;
            v24 = region.v;
            v21 = region.u2;
            v25 = region.v;
            v22 = region.u2;
            v26 = region.v2;
            v23 = region.u;
            v27 = region.v2;
        }

        float v2 = this.color;
        int v9 = this.idx + 1;
        v28[this.idx] = v31;
        int v8 = v9 + 1;
        v28[v9] = v35;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v20;
        v9 = v8 + 1;
        v28[v8] = v24;
        v8 = v9 + 1;
        v28[v9] = v32;
        v9 = v8 + 1;
        v28[v8] = v36;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v21;
        v8 = v9 + 1;
        v28[v9] = v25;
        v9 = v8 + 1;
        v28[v8] = v33;
        v8 = v9 + 1;
        v28[v9] = v37;
        v9 = v8 + 1;
        v28[v8] = v2;
        v8 = v9 + 1;
        v28[v9] = v22;
        v9 = v8 + 1;
        v28[v8] = v26;
        v8 = v9 + 1;
        v28[v9] = v34;
        v9 = v8 + 1;
        v28[v8] = v38;
        v8 = v9 + 1;
        v28[v9] = v2;
        v9 = v8 + 1;
        v28[v8] = v23;
        v28[v9] = v27;
        this.idx = v9 + 1;
    }

    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
        }

        float[] v10 = this.vertices;
        Texture v5 = region.texture;
        if(v5 != this.lastTexture) {
            this.switchTexture(v5);
        }
        else if(this.idx == v10.length) {
            this.flush();
        }

        float v11 = transform.m02;
        float v15 = transform.m12;
        float v12 = transform.m01 * height + transform.m02;
        float v16 = transform.m11 * height + transform.m12;
        float v13 = transform.m00 * width + transform.m01 * height + transform.m02;
        float v17 = transform.m10 * width + transform.m11 * height + transform.m12;
        float v14 = transform.m00 * width + transform.m02;
        float v18 = transform.m10 * width + transform.m12;
        float v6 = region.u;
        float v8 = region.v2;
        float v7 = region.u2;
        float v9 = region.v;
        float v2 = this.color;
        int v4 = this.idx + 1;
        v10[this.idx] = v11;
        int v3 = v4 + 1;
        v10[v4] = v15;
        v4 = v3 + 1;
        v10[v3] = v2;
        v3 = v4 + 1;
        v10[v4] = v6;
        v4 = v3 + 1;
        v10[v3] = v8;
        v3 = v4 + 1;
        v10[v4] = v12;
        v4 = v3 + 1;
        v10[v3] = v16;
        v3 = v4 + 1;
        v10[v4] = v2;
        v4 = v3 + 1;
        v10[v3] = v6;
        v3 = v4 + 1;
        v10[v4] = v9;
        v4 = v3 + 1;
        v10[v3] = v13;
        v3 = v4 + 1;
        v10[v4] = v17;
        v4 = v3 + 1;
        v10[v3] = v2;
        v3 = v4 + 1;
        v10[v4] = v7;
        v4 = v3 + 1;
        v10[v3] = v9;
        v3 = v4 + 1;
        v10[v4] = v14;
        v4 = v3 + 1;
        v10[v3] = v18;
        v3 = v4 + 1;
        v10[v4] = v2;
        v4 = v3 + 1;
        v10[v3] = v7;
        v10[v4] = v8;
        this.idx = v4 + 1;
    }

    public void enableBlending() {
        if(this.blendingDisabled) {
            this.flush();
            this.blendingDisabled = false;
        }
    }

    public void end() {
        if(!this.drawing) {
            throw new IllegalStateException("SpriteBatch.begin must be called before end.");
        }

        if(this.idx > 0) {
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
        ShaderProgram v3;
        int v5 = 3042;
        if(this.idx != 0) {
            ++this.renderCalls;
            ++this.totalRenderCalls;
            int v2 = this.idx / 20;
            if(v2 > this.maxSpritesInBatch) {
                this.maxSpritesInBatch = v2;
            }

            int v0 = v2 * 6;
            this.lastTexture.bind();
            Mesh v1 = this.mesh;
            v1.setVertices(this.vertices, 0, this.idx);
            v1.getIndicesBuffer().position(0);
            v1.getIndicesBuffer().limit(v0);
            if(this.blendingDisabled) {
                Gdx.gl.glDisable(v5);
            }
            else {
                Gdx.gl.glEnable(v5);
                if(this.blendSrcFunc != -1) {
                    Gdx.gl.glBlendFunc(this.blendSrcFunc, this.blendDstFunc);
                }
            }

            if(this.customShader != null) {
                v3 = this.customShader;
            }
            else {
                v3 = this.shader;
            }

            v1.render(v3, 4, 0, v0);
            this.idx = 0;
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

    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
    }

    public void setColor(float color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
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

    protected void switchTexture(Texture texture) {
        this.flush();
        this.lastTexture = texture;
        this.invTexWidth = 1f / (((float)texture.getWidth()));
        this.invTexHeight = 1f / (((float)texture.getHeight()));
    }
}

