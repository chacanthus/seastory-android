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

public class Polyline implements Shape2D {
    private boolean calculateLength;
    private boolean calculateScaledLength;
    private boolean dirty;
    private float length;
    private final float[] localVertices;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float scaledLength;
    private float[] worldVertices;
    private float x;
    private float y;

    public Polyline(float[] vertices) {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        if(vertices.length < 4) {
            throw new IllegalArgumentException("polylines must contain at least 2 points.");
        }

        this.localVertices = vertices;
    }

    public Polyline() {
        super();
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.calculateScaledLength = true;
        this.calculateLength = true;
        this.dirty = true;
        this.localVertices = new float[0];
    }

    public void calculateLength() {
        this.calculateLength = true;
    }

    public void calculateScaledLength() {
        this.calculateScaledLength = true;
    }

    public void dirty() {
        this.dirty = true;
    }

    public float getLength() {
        float v4;
        if(!this.calculateLength) {
            v4 = this.length;
        }
        else {
            this.calculateLength = false;
            this.length = 0f;
            int v0 = 0;
            int v1 = this.localVertices.length - 2;
            while(v0 < v1) {
                float v2 = this.localVertices[v0 + 2] - this.localVertices[v0];
                float v3 = this.localVertices[v0 + 1] - this.localVertices[v0 + 3];
                this.length += ((float)Math.sqrt(((double)(v2 * v2 + v3 * v3))));
                v0 += 2;
            }

            v4 = this.length;
        }

        return v4;
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

    public float getScaledLength() {
        float v4;
        if(!this.calculateScaledLength) {
            v4 = this.scaledLength;
        }
        else {
            this.calculateScaledLength = false;
            this.scaledLength = 0f;
            int v0 = 0;
            int v1 = this.localVertices.length - 2;
            while(v0 < v1) {
                float v2 = this.localVertices[v0 + 2] * this.scaleX - this.localVertices[v0] * this.scaleX;
                float v3 = this.localVertices[v0 + 1] * this.scaleY - this.localVertices[v0 + 3] * this.scaleY;
                this.scaledLength += ((float)Math.sqrt(((double)(v2 * v2 + v3 * v3))));
                v0 += 2;
            }

            v4 = this.scaledLength;
        }

        return v4;
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
            if(this.worldVertices == null || this.worldVertices.length < v4.length) {
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
        this.calculateScaledLength = true;
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
        this.calculateScaledLength = true;
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
        this.dirty = true;
    }
}

