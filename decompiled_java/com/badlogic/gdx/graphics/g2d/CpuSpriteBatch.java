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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class CpuSpriteBatch extends SpriteBatch {
    private final Affine2 adjustAffine;
    private boolean adjustNeeded;
    private boolean haveIdentityRealMatrix;
    private final Affine2 tmpAffine;
    private final Matrix4 virtualMatrix;

    public CpuSpriteBatch() {
        this(1000);
    }

    public CpuSpriteBatch(int size) {
        this(size, null);
    }

    public CpuSpriteBatch(int size, ShaderProgram defaultShader) {
        super(size, defaultShader);
        this.virtualMatrix = new Matrix4();
        this.adjustAffine = new Affine2();
        this.haveIdentityRealMatrix = true;
        this.tmpAffine = new Affine2();
    }

    private static boolean checkEqual(Matrix4 matrix, Affine2 affine) {
        boolean v1 = true;
        float[] v0 = matrix.getValues();
        if(v0[0] != affine.m00 || v0[1] != affine.m10 || v0[4] != affine.m01 || v0[5] != affine.m11 || v0[12] != affine.m02 || v0[13] != affine.m12) {
            v1 = false;
        }

        return v1;
    }

    private static boolean checkEqual(Matrix4 a, Matrix4 b) {
        int v6 = 12;
        int v5 = 5;
        int v4 = 4;
        boolean v0 = true;
        if(a != b && (a.val[0] != b.val[0] || a.val[1] != b.val[1] || a.val[v4] != b.val[v4] || a.val[v5] != b.val[v5] || a.val[v6] != b.val[v6] || a.val[13] != b.val[13])) {
            v0 = false;
        }

        return v0;
    }

    private static boolean checkIdt(Matrix4 matrix) {
        boolean v1 = true;
        float v5 = 1f;
        float[] v0 = matrix.getValues();
        if(v0[0] != v5 || v0[1] != 0f || v0[4] != 0f || v0[5] != v5 || v0[12] != 0f || v0[13] != 0f) {
            v1 = false;
        }

        return v1;
    }

    public void draw(Texture texture, float x, float y) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y);
        }
        else {
            this.drawAdjusted(texture, x, y, 0f, 0f, ((float)texture.getWidth()), ((float)texture.getHeight()), 1f, 1f, 0f, 0, 1, 1, 0, false, false);
        }
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y, width, height);
        }
        else {
            this.drawAdjusted(texture, x, y, 0f, 0f, width, height, 1f, 1f, 0f, 0, 1, 1, 0, false, false);
        }
    }

    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y, width, height, u, v, u2, v2);
        }
        else {
            this.drawAdjustedUV(texture, x, y, 0f, 0f, ((float)texture.getWidth()), ((float)texture.getHeight()), 1f, 1f, 0f, u, v, u2, v2, false, false);
        }
    }

    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        }
        else {
            this.drawAdjusted(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        }
    }

    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        }
        else {
            this.drawAdjusted(texture, x, y, 0f, 0f, width, height, 1f, 1f, 0f, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
        }
    }

    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        if(!this.adjustNeeded) {
            super.draw(texture, x, y, srcX, srcY, srcWidth, srcHeight);
        }
        else {
            this.drawAdjusted(texture, x, y, 0f, 0f, ((float)texture.getWidth()), ((float)texture.getHeight()), 1f, 1f, 0f, srcX, srcY, srcWidth, srcHeight, false, false);
        }
    }

    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if(count % 20 != 0) {
            throw new GdxRuntimeException("invalid vertex count");
        }

        if(!this.adjustNeeded) {
            super.draw(texture, spriteVertices, offset, count);
        }
        else {
            this.drawAdjusted(texture, spriteVertices, offset, count);
        }
    }

    public void draw(TextureRegion region, float x, float y) {
        float v8 = 1f;
        if(!this.adjustNeeded) {
            super.draw(region, x, y);
        }
        else {
            this.drawAdjusted(region, x, y, 0f, 0f, ((float)region.getRegionWidth()), ((float)region.getRegionHeight()), v8, v8, 0f);
        }
    }

    public void draw(TextureRegion region, float x, float y, float width, float height) {
        if(!this.adjustNeeded) {
            super.draw(region, x, y, width, height);
        }
        else {
            this.drawAdjusted(region, x, y, 0f, 0f, width, height, 1f, 1f, 0f);
        }
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if(!this.adjustNeeded) {
            super.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
        else {
            this.drawAdjusted(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        if(!this.adjustNeeded) {
            super.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation, clockwise);
        }
        else {
            this.drawAdjusted(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation, clockwise);
        }
    }

    public void draw(TextureRegion region, float width, float height, Affine2 transform) {
        if(!this.adjustNeeded) {
            super.draw(region, width, height, transform);
        }
        else {
            this.drawAdjusted(region, width, height, transform);
        }
    }

    private void drawAdjusted(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        float v19 = 1f / (((float)texture.getWidth()));
        float v18 = 1f / (((float)texture.getHeight()));
        this.drawAdjustedUV(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, (((float)srcX)) * v19, (((float)(srcY + srcHeight))) * v18, (((float)(srcX + srcWidth))) * v19, (((float)srcY)) * v18, flipX, flipY);
    }

    private void drawAdjusted(Texture texture, float[] spriteVertices, int offset, int count) {
        if(!this.drawing) {
            throw new IllegalStateException("CpuSpriteBatch.begin must be called before draw.");
        }

        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }

        Affine2 v1 = this.adjustAffine;
        int v0 = Math.min(this.vertices.length - this.idx, count);
        do {
            count -= v0;
            while(v0 > 0) {
                float v2 = spriteVertices[offset];
                float v3 = spriteVertices[offset + 1];
                this.vertices[this.idx] = v1.m00 * v2 + v1.m01 * v3 + v1.m02;
                this.vertices[this.idx + 1] = v1.m10 * v2 + v1.m11 * v3 + v1.m12;
                this.vertices[this.idx + 2] = spriteVertices[offset + 2];
                this.vertices[this.idx + 3] = spriteVertices[offset + 3];
                this.vertices[this.idx + 4] = spriteVertices[offset + 4];
                this.idx += 5;
                offset += 5;
                v0 += -5;
            }

            if(count > 0) {
                super.flush();
                v0 = Math.min(this.vertices.length, count);
            }
        }
        while(count > 0);
    }

    private void drawAdjusted(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        this.drawAdjustedUV(region.texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, region.u, region.v2, region.u2, region.v, false, false);
    }

    private void drawAdjusted(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        float v24;
        float v20;
        float v23;
        float v19;
        float v22;
        float v18;
        float v21;
        float v17;
        float v34;
        float v30;
        float v33;
        float v29;
        float v32;
        float v28;
        float v31;
        float v27;
        if(!this.drawing) {
            throw new IllegalStateException("CpuSpriteBatch.begin must be called before draw.");
        }

        if(region.texture != this.lastTexture) {
            this.switchTexture(region.texture);
        }
        else if(this.idx == this.vertices.length) {
            super.flush();
        }

        float v25 = x + originX;
        float v26 = y + originY;
        float v3 = -originX;
        float v5 = -originY;
        float v4 = width - originX;
        float v6 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v3 *= scaleX;
            v5 *= scaleY;
            v4 *= scaleX;
            v6 *= scaleY;
        }

        float v7 = v3;
        float v8 = v5;
        float v9 = v3;
        float v10 = v6;
        float v11 = v4;
        float v12 = v6;
        float v13 = v4;
        float v14 = v5;
        if(rotation != 0f) {
            float v2 = MathUtils.cosDeg(rotation);
            float v15 = MathUtils.sinDeg(rotation);
            v27 = v2 * v7 - v15 * v8;
            v31 = v15 * v7 + v2 * v8;
            v28 = v2 * v9 - v15 * v10;
            v32 = v15 * v9 + v2 * v10;
            v29 = v2 * v11 - v15 * v12;
            v33 = v15 * v11 + v2 * v12;
            v30 = v27 + (v29 - v28);
            v34 = v33 - (v32 - v31);
        }
        else {
            v27 = v7;
            v31 = v8;
            v28 = v9;
            v32 = v10;
            v29 = v11;
            v33 = v12;
            v30 = v13;
            v34 = v14;
        }

        v27 += v25;
        v31 += v26;
        v28 += v25;
        v32 += v26;
        v29 += v25;
        v33 += v26;
        v30 += v25;
        v34 += v26;
        if(clockwise) {
            v17 = region.u2;
            v21 = region.v2;
            v18 = region.u;
            v22 = region.v2;
            v19 = region.u;
            v23 = region.v;
            v20 = region.u2;
            v24 = region.v;
        }
        else {
            v17 = region.u;
            v21 = region.v;
            v18 = region.u2;
            v22 = region.v;
            v19 = region.u2;
            v23 = region.v2;
            v20 = region.u;
            v24 = region.v2;
        }

        Affine2 v16 = this.adjustAffine;
        this.vertices[this.idx] = v16.m00 * v27 + v16.m01 * v31 + v16.m02;
        this.vertices[this.idx + 1] = v16.m10 * v27 + v16.m11 * v31 + v16.m12;
        this.vertices[this.idx + 2] = this.color;
        this.vertices[this.idx + 3] = v17;
        this.vertices[this.idx + 4] = v21;
        this.vertices[this.idx + 5] = v16.m00 * v28 + v16.m01 * v32 + v16.m02;
        this.vertices[this.idx + 6] = v16.m10 * v28 + v16.m11 * v32 + v16.m12;
        this.vertices[this.idx + 7] = this.color;
        this.vertices[this.idx + 8] = v18;
        this.vertices[this.idx + 9] = v22;
        this.vertices[this.idx + 10] = v16.m00 * v29 + v16.m01 * v33 + v16.m02;
        this.vertices[this.idx + 11] = v16.m10 * v29 + v16.m11 * v33 + v16.m12;
        this.vertices[this.idx + 12] = this.color;
        this.vertices[this.idx + 13] = v19;
        this.vertices[this.idx + 14] = v23;
        this.vertices[this.idx + 15] = v16.m00 * v30 + v16.m01 * v34 + v16.m02;
        this.vertices[this.idx + 16] = v16.m10 * v30 + v16.m11 * v34 + v16.m12;
        this.vertices[this.idx + 17] = this.color;
        this.vertices[this.idx + 18] = v20;
        this.vertices[this.idx + 19] = v24;
        this.idx += 20;
    }

    private void drawAdjusted(TextureRegion region, float width, float height, Affine2 transform) {
        if(!this.drawing) {
            throw new IllegalStateException("CpuSpriteBatch.begin must be called before draw.");
        }

        if(region.texture != this.lastTexture) {
            this.switchTexture(region.texture);
        }
        else if(this.idx == this.vertices.length) {
            super.flush();
        }

        float v6 = transform.m02;
        float v10 = transform.m12;
        float v7 = transform.m01 * height + transform.m02;
        float v11 = transform.m11 * height + transform.m12;
        float v8 = transform.m00 * width + transform.m01 * height + transform.m02;
        float v12 = transform.m10 * width + transform.m11 * height + transform.m12;
        float v9 = transform.m00 * width + transform.m02;
        float v13 = transform.m10 * width + transform.m12;
        float v2 = region.u;
        float v4 = region.v2;
        float v3 = region.u2;
        float v5 = region.v;
        Affine2 v1 = this.adjustAffine;
        this.vertices[this.idx] = v1.m00 * v6 + v1.m01 * v10 + v1.m02;
        this.vertices[this.idx + 1] = v1.m10 * v6 + v1.m11 * v10 + v1.m12;
        this.vertices[this.idx + 2] = this.color;
        this.vertices[this.idx + 3] = v2;
        this.vertices[this.idx + 4] = v4;
        this.vertices[this.idx + 5] = v1.m00 * v7 + v1.m01 * v11 + v1.m02;
        this.vertices[this.idx + 6] = v1.m10 * v7 + v1.m11 * v11 + v1.m12;
        this.vertices[this.idx + 7] = this.color;
        this.vertices[this.idx + 8] = v2;
        this.vertices[this.idx + 9] = v5;
        this.vertices[this.idx + 10] = v1.m00 * v8 + v1.m01 * v12 + v1.m02;
        this.vertices[this.idx + 11] = v1.m10 * v8 + v1.m11 * v12 + v1.m12;
        this.vertices[this.idx + 12] = this.color;
        this.vertices[this.idx + 13] = v3;
        this.vertices[this.idx + 14] = v5;
        this.vertices[this.idx + 15] = v1.m00 * v9 + v1.m01 * v13 + v1.m02;
        this.vertices[this.idx + 16] = v1.m10 * v9 + v1.m11 * v13 + v1.m12;
        this.vertices[this.idx + 17] = this.color;
        this.vertices[this.idx + 18] = v3;
        this.vertices[this.idx + 19] = v4;
        this.idx += 20;
    }

    private void drawAdjustedUV(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, float u, float v, float u2, float v2, boolean flipX, boolean flipY) {
        float v17;
        float v27;
        float v23;
        float v26;
        float v22;
        float v25;
        float v21;
        float v24;
        float v20;
        if(!this.drawing) {
            throw new IllegalStateException("CpuSpriteBatch.begin must be called before draw.");
        }

        if(texture != this.lastTexture) {
            this.switchTexture(texture);
        }
        else if(this.idx == this.vertices.length) {
            super.flush();
        }

        float v18 = x + originX;
        float v19 = y + originY;
        float v3 = -originX;
        float v5 = -originY;
        float v4 = width - originX;
        float v6 = height - originY;
        if(scaleX != 1f || scaleY != 1f) {
            v3 *= scaleX;
            v5 *= scaleY;
            v4 *= scaleX;
            v6 *= scaleY;
        }

        float v7 = v3;
        float v8 = v5;
        float v9 = v3;
        float v10 = v6;
        float v11 = v4;
        float v12 = v6;
        float v13 = v4;
        float v14 = v5;
        if(rotation != 0f) {
            float v2 = MathUtils.cosDeg(rotation);
            float v15 = MathUtils.sinDeg(rotation);
            v20 = v2 * v7 - v15 * v8;
            v24 = v15 * v7 + v2 * v8;
            v21 = v2 * v9 - v15 * v10;
            v25 = v15 * v9 + v2 * v10;
            v22 = v2 * v11 - v15 * v12;
            v26 = v15 * v11 + v2 * v12;
            v23 = v20 + (v22 - v21);
            v27 = v26 - (v25 - v24);
        }
        else {
            v20 = v7;
            v24 = v8;
            v21 = v9;
            v25 = v10;
            v22 = v11;
            v26 = v12;
            v23 = v13;
            v27 = v14;
        }

        v20 += v18;
        v24 += v19;
        v21 += v18;
        v25 += v19;
        v22 += v18;
        v26 += v19;
        v23 += v18;
        v27 += v19;
        if(flipX) {
            v17 = u;
            u = u2;
            u2 = v17;
        }

        if(flipY) {
            v17 = v;
            v = v2;
            v2 = v17;
        }

        Affine2 v16 = this.adjustAffine;
        this.vertices[this.idx] = v16.m00 * v20 + v16.m01 * v24 + v16.m02;
        this.vertices[this.idx + 1] = v16.m10 * v20 + v16.m11 * v24 + v16.m12;
        this.vertices[this.idx + 2] = this.color;
        this.vertices[this.idx + 3] = u;
        this.vertices[this.idx + 4] = v;
        this.vertices[this.idx + 5] = v16.m00 * v21 + v16.m01 * v25 + v16.m02;
        this.vertices[this.idx + 6] = v16.m10 * v21 + v16.m11 * v25 + v16.m12;
        this.vertices[this.idx + 7] = this.color;
        this.vertices[this.idx + 8] = u;
        this.vertices[this.idx + 9] = v2;
        this.vertices[this.idx + 10] = v16.m00 * v22 + v16.m01 * v26 + v16.m02;
        this.vertices[this.idx + 11] = v16.m10 * v22 + v16.m11 * v26 + v16.m12;
        this.vertices[this.idx + 12] = this.color;
        this.vertices[this.idx + 13] = u2;
        this.vertices[this.idx + 14] = v2;
        this.vertices[this.idx + 15] = v16.m00 * v23 + v16.m01 * v27 + v16.m02;
        this.vertices[this.idx + 16] = v16.m10 * v23 + v16.m11 * v27 + v16.m12;
        this.vertices[this.idx + 17] = this.color;
        this.vertices[this.idx + 18] = u2;
        this.vertices[this.idx + 19] = v;
        this.idx += 20;
    }

    public void flushAndSyncTransformMatrix() {
        this.flush();
        if(this.adjustNeeded) {
            this.haveIdentityRealMatrix = CpuSpriteBatch.checkIdt(this.virtualMatrix);
            if(!this.haveIdentityRealMatrix && this.virtualMatrix.det() == 0f) {
                throw new GdxRuntimeException("Transform matrix is singular, can\'t sync");
            }

            this.adjustNeeded = false;
            super.setTransformMatrix(this.virtualMatrix);
        }
    }

    public Matrix4 getTransformMatrix() {
        Matrix4 v0;
        if(this.adjustNeeded) {
            v0 = this.virtualMatrix;
        }
        else {
            v0 = super.getTransformMatrix();
        }

        return v0;
    }

    public void setTransformMatrix(Affine2 transform) {
        Matrix4 v0 = super.getTransformMatrix();
        if(CpuSpriteBatch.checkEqual(v0, transform)) {
            this.adjustNeeded = false;
        }
        else {
            this.virtualMatrix.setAsAffine(transform);
            if(this.isDrawing()) {
                this.adjustNeeded = true;
                if(this.haveIdentityRealMatrix) {
                    this.adjustAffine.set(transform);
                }
                else {
                    this.adjustAffine.set(v0).inv().mul(transform);
                }
            }
            else {
                v0.setAsAffine(transform);
                this.haveIdentityRealMatrix = CpuSpriteBatch.checkIdt(v0);
            }
        }
    }

    public void setTransformMatrix(Matrix4 transform) {
        Matrix4 v0 = super.getTransformMatrix();
        if(CpuSpriteBatch.checkEqual(v0, transform)) {
            this.adjustNeeded = false;
        }
        else if(this.isDrawing()) {
            this.virtualMatrix.setAsAffine(transform);
            this.adjustNeeded = true;
            if(this.haveIdentityRealMatrix) {
                this.adjustAffine.set(transform);
            }
            else {
                this.tmpAffine.set(transform);
                this.adjustAffine.set(v0).inv().mul(this.tmpAffine);
            }
        }
        else {
            v0.setAsAffine(transform);
            this.haveIdentityRealMatrix = CpuSpriteBatch.checkIdt(v0);
        }
    }
}

