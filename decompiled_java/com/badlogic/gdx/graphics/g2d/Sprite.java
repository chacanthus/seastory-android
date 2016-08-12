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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class Sprite extends TextureRegion {
    static final int SPRITE_SIZE = 20;
    static final int VERTEX_SIZE = 5;
    private Rectangle bounds;
    private final Color color;
    private boolean dirty;
    float height;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    final float[] vertices;
    float width;
    private float x;
    private float y;

    public Sprite(TextureRegion region) {
        super();
        this.vertices = new float[20];
        this.color = new Color(1f, 1f, 1f, 1f);
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        this.setRegion(region);
        this.setColor(1f, 1f, 1f, 1f);
        this.setSize(((float)region.getRegionWidth()), ((float)region.getRegionHeight()));
        this.setOrigin(this.width / 2f, this.height / 2f);
    }

    public Sprite(Sprite sprite) {
        super();
        this.vertices = new float[20];
        this.color = new Color(1f, 1f, 1f, 1f);
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        this.set(sprite);
    }

    public Sprite() {
        super();
        this.vertices = new float[20];
        this.color = new Color(1f, 1f, 1f, 1f);
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        this.setColor(1f, 1f, 1f, 1f);
    }

    public Sprite(Texture texture) {
        this(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public Sprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        float v2 = 2f;
        float v1 = 1f;
        super();
        this.vertices = new float[20];
        this.color = new Color(v1, v1, v1, v1);
        this.scaleX = v1;
        this.scaleY = v1;
        this.dirty = true;
        if(texture == null) {
            throw new IllegalArgumentException("texture cannot be null.");
        }

        this.texture = texture;
        this.setRegion(srcX, srcY, srcWidth, srcHeight);
        this.setColor(v1, v1, v1, v1);
        this.setSize(((float)Math.abs(srcWidth)), ((float)Math.abs(srcHeight)));
        this.setOrigin(this.width / v2, this.height / v2);
    }

    public Sprite(Texture texture, int srcWidth, int srcHeight) {
        this(texture, 0, 0, srcWidth, srcHeight);
    }

    public Sprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super();
        this.vertices = new float[20];
        this.color = new Color(1f, 1f, 1f, 1f);
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        this.setRegion(region, srcX, srcY, srcWidth, srcHeight);
        this.setColor(1f, 1f, 1f, 1f);
        this.setSize(((float)Math.abs(srcWidth)), ((float)Math.abs(srcHeight)));
        this.setOrigin(this.width / 2f, this.height / 2f);
    }

    public void draw(Batch batch) {
        batch.draw(this.texture, this.getVertices(), 0, 20);
    }

    public void draw(Batch batch, float alphaModulation) {
        float v0 = this.getColor().a;
        this.setAlpha(v0 * alphaModulation);
        this.draw(batch);
        this.setAlpha(v0);
    }

    public void flip(boolean x, boolean y) {
        float v0;
        int v7 = 13;
        int v6 = 9;
        int v5 = 8;
        int v4 = 4;
        int v3 = 3;
        super.flip(x, y);
        float[] v1 = this.vertices;
        if(x) {
            v0 = v1[v3];
            v1[v3] = v1[v7];
            v1[v7] = v0;
            v0 = v1[v5];
            v1[v5] = v1[18];
            v1[18] = v0;
        }

        if(y) {
            v0 = v1[v4];
            v1[v4] = v1[14];
            v1[14] = v0;
            v0 = v1[v6];
            v1[v6] = v1[19];
            v1[19] = v0;
        }
    }

    public Rectangle getBoundingRectangle() {
        int v10 = 15;
        int v9 = 11;
        int v8 = 10;
        int v7 = 6;
        int v6 = 5;
        float[] v4 = this.getVertices();
        float v2 = v4[0];
        float v3 = v4[1];
        float v0 = v4[0];
        float v1 = v4[1];
        if(v2 > v4[v6]) {
            v2 = v4[v6];
        }

        if(v2 > v4[v8]) {
            v2 = v4[v8];
        }

        if(v2 > v4[v10]) {
            v2 = v4[v10];
        }

        if(v0 < v4[v6]) {
            v0 = v4[v6];
        }

        if(v0 < v4[v8]) {
            v0 = v4[v8];
        }

        if(v0 < v4[v10]) {
            v0 = v4[v10];
        }

        if(v3 > v4[v7]) {
            v3 = v4[v7];
        }

        if(v3 > v4[v9]) {
            v3 = v4[v9];
        }

        if(v3 > v4[16]) {
            v3 = v4[16];
        }

        if(v1 < v4[v7]) {
            v1 = v4[v7];
        }

        if(v1 < v4[v9]) {
            v1 = v4[v9];
        }

        if(v1 < v4[16]) {
            v1 = v4[16];
        }

        if(this.bounds == null) {
            this.bounds = new Rectangle();
        }

        this.bounds.x = v2;
        this.bounds.y = v3;
        this.bounds.width = v0 - v2;
        this.bounds.height = v1 - v3;
        return this.bounds;
    }

    public Color getColor() {
        int v1 = NumberUtils.floatToIntColor(this.vertices[2]);
        Color v0 = this.color;
        v0.r = (((float)(v1 & 255))) / 255f;
        v0.g = (((float)(v1 >>> 8 & 255))) / 255f;
        v0.b = (((float)(v1 >>> 16 & 255))) / 255f;
        v0.a = (((float)(v1 >>> 24 & 255))) / 255f;
        return v0;
    }

    public float getHeight() {
        return this.height;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public float[] getVertices() {
        if(this.dirty) {
            this.dirty = false;
            float[] v16 = this.vertices;
            float v3 = -this.originX;
            float v9 = -this.originY;
            float v4 = v3 + this.width;
            float v10 = v9 + this.height;
            float v17 = this.x - v3;
            float v18 = this.y - v9;
            if(this.scaleX != 1f || this.scaleY != 1f) {
                v3 *= this.scaleX;
                v9 *= this.scaleY;
                v4 *= this.scaleX;
                v10 *= this.scaleY;
            }

            if(this.rotation == 0f) {
                goto label_120;
            }

            float v2 = MathUtils.cosDeg(this.rotation);
            float v15 = MathUtils.sinDeg(this.rotation);
            float v7 = v3 * v2;
            float v8 = v3 * v15;
            float v11 = v10 * v2;
            float v12 = v10 * v15;
            float v19 = v7 - v9 * v15 + v17;
            float v22 = v9 * v2 + v8 + v18;
            v16[0] = v19;
            v16[1] = v22;
            float v20 = v7 - v12 + v17;
            float v23 = v11 + v8 + v18;
            v16[5] = v20;
            v16[6] = v23;
            float v21 = v4 * v2 - v12 + v17;
            float v24 = v11 + v4 * v15 + v18;
            v16[10] = v21;
            v16[11] = v24;
            v16[15] = v21 - v20 + v19;
            v16[16] = v24 - (v23 - v22);
            goto label_116;
        label_120:
            v19 = v3 + v17;
            v22 = v9 + v18;
            v20 = v4 + v17;
            v23 = v10 + v18;
            v16[0] = v19;
            v16[1] = v22;
            v16[5] = v19;
            v16[6] = v23;
            v16[10] = v20;
            v16[11] = v23;
            v16[15] = v20;
            v16[16] = v22;
        }

    label_116:
        return this.vertices;
    }

    public float getWidth() {
        return this.width;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void rotate(float degrees) {
        if(degrees != 0f) {
            this.rotation += degrees;
            this.dirty = true;
        }
    }

    public void rotate90(boolean clockwise) {
        float v0;
        int v7 = 13;
        int v6 = 9;
        int v5 = 8;
        int v3 = 4;
        int v4 = 3;
        float[] v1 = this.vertices;
        if(clockwise) {
            v0 = v1[v3];
            v1[v3] = v1[19];
            v1[19] = v1[14];
            v1[14] = v1[v6];
            v1[v6] = v0;
            v0 = v1[v4];
            v1[v4] = v1[18];
            v1[18] = v1[v7];
            v1[v7] = v1[v5];
            v1[v5] = v0;
        }
        else {
            v0 = v1[v3];
            v1[v3] = v1[v6];
            v1[v6] = v1[14];
            v1[14] = v1[19];
            v1[19] = v0;
            v0 = v1[v4];
            v1[v4] = v1[v5];
            v1[v5] = v1[v7];
            v1[v7] = v1[18];
            v1[18] = v0;
        }
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
    }

    public void scroll(float xAmount, float yAmount) {
        int v10 = 9;
        int v9 = 3;
        float v8 = 1f;
        float[] v4 = this.vertices;
        if(xAmount != 0f) {
            float v0 = (v4[v9] + xAmount) % v8;
            float v1 = v0 + this.width / (((float)this.texture.getWidth()));
            this.u = v0;
            this.u2 = v1;
            v4[v9] = v0;
            v4[8] = v0;
            v4[13] = v1;
            v4[18] = v1;
        }

        if(yAmount != 0f) {
            float v2 = (v4[v10] + yAmount) % v8;
            float v3 = v2 + this.height / (((float)this.texture.getHeight()));
            this.v = v2;
            this.v2 = v3;
            v4[4] = v3;
            v4[v10] = v2;
            v4[14] = v2;
            v4[19] = v3;
        }
    }

    public void set(Sprite sprite) {
        if(sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }

        System.arraycopy(sprite.vertices, 0, this.vertices, 0, 20);
        this.texture = sprite.texture;
        this.u = sprite.u;
        this.v = sprite.v;
        this.u2 = sprite.u2;
        this.v2 = sprite.v2;
        this.x = sprite.x;
        this.y = sprite.y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.regionWidth = sprite.regionWidth;
        this.regionHeight = sprite.regionHeight;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setAlpha(float a) {
        float v1 = NumberUtils.intToFloatColor(NumberUtils.floatToIntColor(this.vertices[2]) & 16777215 | (((int)(255f * a))) << 24);
        this.vertices[2] = v1;
        this.vertices[7] = v1;
        this.vertices[12] = v1;
        this.vertices[17] = v1;
    }

    public void setBounds(float x, float y, float width, float height) {
        float v5 = 1f;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(!this.dirty) {
            float v1 = x + width;
            float v2 = y + height;
            float[] v0 = this.vertices;
            v0[0] = x;
            v0[1] = y;
            v0[5] = x;
            v0[6] = v2;
            v0[10] = v1;
            v0[11] = v2;
            v0[15] = v1;
            v0[16] = y;
            if(this.rotation == 0f && this.scaleX == v5 && this.scaleY == v5) {
                return;
            }

            this.dirty = true;
        }
    }

    public void setCenter(float x, float y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public void setCenterX(float x) {
        this.setX(x - this.width / 2f);
    }

    public void setCenterY(float y) {
        this.setY(y - this.height / 2f);
    }

    public void setColor(Color tint) {
        float v0 = tint.toFloatBits();
        float[] v1 = this.vertices;
        v1[2] = v0;
        v1[7] = v0;
        v1[12] = v0;
        v1[17] = v0;
    }

    public void setColor(float r, float g, float b, float a) {
        float v0 = NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
        float[] v2 = this.vertices;
        v2[2] = v0;
        v2[7] = v0;
        v2[12] = v0;
        v2[17] = v0;
    }

    public void setColor(float color) {
        float[] v0 = this.vertices;
        v0[2] = color;
        v0[7] = color;
        v0[12] = color;
        v0[17] = color;
    }

    public void setFlip(boolean x, boolean y) {
        boolean v0 = false;
        boolean v1 = false;
        if(this.isFlipX() != x) {
            v0 = true;
        }

        if(this.isFlipY() != y) {
            v1 = true;
        }

        this.flip(v0, v1);
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setOriginCenter() {
        this.originX = this.width / 2f;
        this.originY = this.height / 2f;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.translate(x - this.x, y - this.y);
    }

    public void setRegion(float u, float v, float u2, float v2) {
        super.setRegion(u, v, u2, v2);
        float[] v0 = this.vertices;
        v0[3] = u;
        v0[4] = v2;
        v0[8] = u;
        v0[9] = v;
        v0[13] = u2;
        v0[14] = v;
        v0[18] = u2;
        v0[19] = v2;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.dirty = true;
    }

    public void setSize(float width, float height) {
        float v5 = 1f;
        this.width = width;
        this.height = height;
        if(!this.dirty) {
            float v1 = this.x + width;
            float v2 = this.y + height;
            float[] v0 = this.vertices;
            v0[0] = this.x;
            v0[1] = this.y;
            v0[5] = this.x;
            v0[6] = v2;
            v0[10] = v1;
            v0[11] = v2;
            v0[15] = v1;
            v0[16] = this.y;
            if(this.rotation == 0f && this.scaleX == v5 && this.scaleY == v5) {
                return;
            }

            this.dirty = true;
        }
    }

    public void setU(float u) {
        super.setU(u);
        this.vertices[3] = u;
        this.vertices[8] = u;
    }

    public void setU2(float u2) {
        super.setU2(u2);
        this.vertices[13] = u2;
        this.vertices[18] = u2;
    }

    public void setV(float v) {
        super.setV(v);
        this.vertices[9] = v;
        this.vertices[14] = v;
    }

    public void setV2(float v2) {
        super.setV2(v2);
        this.vertices[4] = v2;
        this.vertices[19] = v2;
    }

    public void setX(float x) {
        this.translateX(x - this.x);
    }

    public void setY(float y) {
        this.translateY(y - this.y);
    }

    public void translate(float xAmount, float yAmount) {
        this.x += xAmount;
        this.y += yAmount;
        if(!this.dirty) {
            float[] v0 = this.vertices;
            v0[0] += xAmount;
            v0[1] += yAmount;
            v0[5] += xAmount;
            v0[6] += yAmount;
            v0[10] += xAmount;
            v0[11] += yAmount;
            v0[15] += xAmount;
            v0[16] += yAmount;
        }
    }

    public void translateX(float xAmount) {
        this.x += xAmount;
        if(!this.dirty) {
            float[] v0 = this.vertices;
            v0[0] += xAmount;
            v0[5] += xAmount;
            v0[10] += xAmount;
            v0[15] += xAmount;
        }
    }

    public void translateY(float yAmount) {
        this.y += yAmount;
        if(!this.dirty) {
            float[] v0 = this.vertices;
            v0[1] += yAmount;
            v0[6] += yAmount;
            v0[11] += yAmount;
            v0[16] += yAmount;
        }
    }
}

