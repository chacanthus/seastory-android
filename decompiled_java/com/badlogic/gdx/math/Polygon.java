// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

public class Polygon implements Shape2D {
    private Rectangle bounds;
    private boolean dirty;
    private float[] localVertices;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float[] worldVertices;
    private float x;
    private float y;

    public Polygon(float[] vertices) {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        if(vertices.length < 6) {
            throw new IllegalArgumentException("polygons must contain at least 3 points.");
        }

        this.localVertices = vertices;
    }

    public Polygon() {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.dirty = true;
        this.localVertices = new float[0];
    }

    public float area() {
        float[] v0 = this.getTransformedVertices();
        return GeometryUtils.polygonArea(v0, 0, v0.length);
    }

    public boolean contains(float x, float y) {
        boolean v8 = true;
        float[] v3 = this.getTransformedVertices();
        int v2 = v3.length;
        int v1 = 0;
        int v0;
        for(v0 = 0; v0 < v2; v0 += 2) {
            float v4 = v3[v0];
            float v6 = v3[v0 + 1];
            float v5 = v3[(v0 + 2) % v2];
            float v7 = v3[(v0 + 3) % v2];
            if(v6 <= y && y < v7) {
                goto label_19;
            }
            else if(v7 <= y && y < v6) {
            label_19:
                if(x < (v5 - v4) / (v7 - v6) * (y - v6) + v4) {
                    ++v1;
                }
            }
        }

        if((v1 & 1) != 1) {
            v8 = false;
        }

        return v8;
    }

    public void dirty() {
        this.dirty = true;
    }

    public Rectangle getBoundingRectangle() {
        float[] v6 = this.getTransformedVertices();
        float v3 = v6[0];
        float v4 = v6[1];
        float v1 = v6[0];
        float v2 = v6[1];
        int v5 = v6.length;
        int v0;
        for(v0 = 2; v0 < v5; v0 += 2) {
            if(v3 > v6[v0]) {
                v3 = v6[v0];
            }

            if(v4 > v6[v0 + 1]) {
                v4 = v6[v0 + 1];
            }

            if(v1 < v6[v0]) {
                v1 = v6[v0];
            }

            if(v2 < v6[v0 + 1]) {
                v2 = v6[v0 + 1];
            }
        }

        if(this.bounds == null) {
            this.bounds = new Rectangle();
        }

        this.bounds.x = v3;
        this.bounds.y = v4;
        this.bounds.width = v1 - v3;
        this.bounds.height = v2 - v4;
        return this.bounds;
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

    public float[] getTransformedVertices() {
        int v12;
        float[] v16;
        if(!this.dirty) {
            v16 = this.worldVertices;
        }
        else {
            this.dirty = false;
            float[] v4 = this.localVertices;
            if(this.worldVertices == null || this.worldVertices.length != v4.length) {
                this.worldVertices = new float[v4.length];
            }

            v16 = this.worldVertices;
            float v9 = this.x;
            float v10 = this.y;
            float v7 = this.originX;
            float v8 = this.originY;
            float v13 = this.scaleX;
            float v14 = this.scaleY;
            if(v13 != 1f || v14 != 1f) {
                v12 = 1;
            }
            else {
                v12 = 0;
            }

            float v11 = this.rotation;
            float v2 = MathUtils.cosDeg(v11);
            float v15 = MathUtils.sinDeg(v11);
            int v3 = 0;
            int v5 = v4.length;
            while(v3 < v5) {
                float v17 = v4[v3] - v7;
                float v18 = v4[v3 + 1] - v8;
                if(v12 != 0) {
                    v17 *= v13;
                    v18 *= v14;
                }

                if(v11 != 0f) {
                    float v6 = v17;
                    v17 = v2 * v17 - v15 * v18;
                    v18 = v15 * v6 + v2 * v18;
                }

                v16[v3] = v9 + v17 + v7;
                v16[v3 + 1] = v10 + v18 + v8;
                v3 += 2;
            }
        }

        return v16;
    }

    public float[] getVertices() {
        return this.localVertices;
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

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.dirty = true;
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

    public void setVertices(float[] vertices) {
        if(vertices.length < 6) {
            throw new IllegalArgumentException("polygons must contain at least 3 points.");
        }

        this.localVertices = vertices;
        this.dirty = true;
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
        this.dirty = true;
    }
}

