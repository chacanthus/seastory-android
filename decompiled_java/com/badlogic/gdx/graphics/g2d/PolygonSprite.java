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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;

public class PolygonSprite {
    private Rectangle bounds;
    private final Color color;
    private boolean dirty;
    private float height;
    private float originX;
    private float originY;
    PolygonRegion region;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float[] vertices;
    private float width;
    private float x;
    private float y;

    public PolygonSprite(PolygonRegion region) {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.bounds = new Rectangle();
        this.color = new Color(1f, 1f, 1f, 1f);
        this.setRegion(region);
        this.setColor(1f, 1f, 1f, 1f);
        this.setSize(((float)region.region.regionWidth), ((float)region.region.regionHeight));
        this.setOrigin(this.width / 2f, this.height / 2f);
    }

    public PolygonSprite(PolygonSprite sprite) {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.bounds = new Rectangle();
        this.color = new Color(1f, 1f, 1f, 1f);
        this.set(sprite);
    }

    public void draw(PolygonSpriteBatch spriteBatch) {
        spriteBatch.draw(this.region.region.texture, this.getVertices(), 0, this.vertices.length, this.region.triangles, 0, this.region.triangles.length);
    }

    public void draw(PolygonSpriteBatch spriteBatch, float alphaModulation) {
        Color v0 = this.getColor();
        float v1 = v0.a;
        v0.a *= alphaModulation;
        this.setColor(v0);
        this.draw(spriteBatch);
        v0.a = v1;
        this.setColor(v0);
    }

    public Rectangle getBoundingRectangle() {
        float[] v5 = this.getVertices();
        float v3 = v5[0];
        float v4 = v5[1];
        float v1 = v5[0];
        float v2 = v5[1];
        int v0;
        for(v0 = 5; v0 < v5.length; v0 += 5) {
            float v6 = v5[v0];
            float v7 = v5[v0 + 1];
            if(v3 > v6) {
                v3 = v6;
            }

            if(v1 < v6) {
                v1 = v6;
            }

            if(v4 > v7) {
                v4 = v7;
            }

            if(v2 < v7) {
                v2 = v7;
            }
        }

        this.bounds.x = v3;
        this.bounds.y = v4;
        this.bounds.width = v1 - v3;
        this.bounds.height = v2 - v4;
        return this.bounds;
    }

    public Color getColor() {
        return this.color;
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

    public PolygonRegion getRegion() {
        return this.region;
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

    public Color getVertexColor() {
        int v1 = NumberUtils.floatToIntColor(this.vertices[2]);
        Color v0 = this.color;
        v0.r = (((float)(v1 & 255))) / 255f;
        v0.g = (((float)(v1 >>> 8 & 255))) / 255f;
        v0.b = (((float)(v1 >>> 16 & 255))) / 255f;
        v0.a = (((float)(v1 >>> 24 & 255))) / 255f;
        return v0;
    }

    public float[] getVertices() {
        float[] v17;
        if(!this.dirty) {
            v17 = this.vertices;
        }
        else {
            this.dirty = false;
            float v7 = this.originX;
            float v8 = this.originY;
            float v13 = this.scaleX;
            float v14 = this.scaleY;
            PolygonRegion v9 = this.region;
            v17 = this.vertices;
            float[] v10 = v9.vertices;
            float v18 = this.x + v7;
            float v19 = this.y + v8;
            float v11 = this.width / (((float)v9.region.getRegionWidth()));
            float v12 = this.height / (((float)v9.region.getRegionHeight()));
            float v2 = MathUtils.cosDeg(this.rotation);
            float v15 = MathUtils.sinDeg(this.rotation);
            int v5 = 0;
            int v16 = 0;
            int v6 = v10.length;
            while(v5 < v6) {
                float v3 = (v10[v5] * v11 - v7) * v13;
                float v4 = (v10[v5 + 1] * v12 - v8) * v14;
                v17[v16] = v2 * v3 - v15 * v4 + v18;
                v17[v16 + 1] = v15 * v3 + v2 * v4 + v19;
                v5 += 2;
                v16 += 5;
            }
        }

        return v17;
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
        this.rotation += degrees;
        this.dirty = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
    }

    public void set(PolygonSprite sprite) {
        if(sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }

        this.setRegion(sprite.region);
        this.x = sprite.x;
        this.y = sprite.y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dirty = true;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
        float v0 = NumberUtils.intToFloatColor((((int)(255f * a))) << 24 | (((int)(255f * b))) << 16 | (((int)(255f * g))) << 8 | (((int)(255f * r))));
        float[] v3 = this.vertices;
        int v1;
        for(v1 = 2; v1 < v3.length; v1 += 5) {
            v3[v1] = v0;
        }
    }

    public void setColor(Color tint) {
        this.color.set(tint);
        float v0 = tint.toFloatBits();
        float[] v2 = this.vertices;
        int v1;
        for(v1 = 2; v1 < v2.length; v1 += 5) {
            v2[v1] = v0;
        }
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.translate(x - this.x, y - this.y);
    }

    public void setRegion(PolygonRegion region) {
        this.region = region;
        float[] v2 = region.vertices;
        float[] v3 = region.textureCoords;
        if(this.vertices == null || v2.length != this.vertices.length) {
            this.vertices = new float[v2.length / 2 * 5];
        }

        float[] v5 = this.vertices;
        int v0 = 0;
        int v4 = 2;
        int v1 = v2.length;
        while(v0 < v1) {
            v5[v4] = this.color.toFloatBits();
            v5[v4 + 1] = v3[v0];
            v5[v4 + 2] = v3[v0 + 1];
            v0 += 2;
            v4 += 5;
        }

        this.dirty = true;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.dirty = true;
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
            float[] v1 = this.vertices;
            int v0;
            for(v0 = 0; v0 < v1.length; v0 += 5) {
                v1[v0] += xAmount;
                int v2 = v0 + 1;
                v1[v2] += yAmount;
            }
        }
    }

    public void translateX(float xAmount) {
        this.x += xAmount;
        if(!this.dirty) {
            float[] v1 = this.vertices;
            int v0;
            for(v0 = 0; v0 < v1.length; v0 += 5) {
                v1[v0] += xAmount;
            }
        }
    }

    public void translateY(float yAmount) {
        this.y += yAmount;
        if(!this.dirty) {
            float[] v1 = this.vertices;
            int v0;
            for(v0 = 1; v0 < v1.length; v0 += 5) {
                v1[v0] += yAmount;
            }
        }
    }
}

