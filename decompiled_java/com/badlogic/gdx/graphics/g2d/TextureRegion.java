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

public class TextureRegion {
    int regionHeight;
    int regionWidth;
    Texture texture;
    float u;
    float u2;
    float v;
    float v2;

    public TextureRegion(Texture texture) {
        super();
        if(texture == null) {
            throw new IllegalArgumentException("texture cannot be null.");
        }

        this.texture = texture;
        this.setRegion(0, 0, texture.getWidth(), texture.getHeight());
    }

    public TextureRegion(TextureRegion region, int x, int y, int width, int height) {
        super();
        this.setRegion(region, x, y, width, height);
    }

    public TextureRegion(Texture texture, int x, int y, int width, int height) {
        super();
        this.texture = texture;
        this.setRegion(x, y, width, height);
    }

    public TextureRegion() {
        super();
    }

    public TextureRegion(Texture texture, float u, float v, float u2, float v2) {
        super();
        this.texture = texture;
        this.setRegion(u, v, u2, v2);
    }

    public TextureRegion(Texture texture, int width, int height) {
        super();
        this.texture = texture;
        this.setRegion(0, 0, width, height);
    }

    public TextureRegion(TextureRegion region) {
        super();
        this.setRegion(region);
    }

    public void flip(boolean x, boolean y) {
        float v0;
        if(x) {
            v0 = this.u;
            this.u = this.u2;
            this.u2 = v0;
        }

        if(y) {
            v0 = this.v;
            this.v = this.v2;
            this.v2 = v0;
        }
    }

    public int getRegionHeight() {
        return this.regionHeight;
    }

    public int getRegionWidth() {
        return this.regionWidth;
    }

    public int getRegionX() {
        return Math.round(this.u * (((float)this.texture.getWidth())));
    }

    public int getRegionY() {
        return Math.round(this.v * (((float)this.texture.getHeight())));
    }

    public Texture getTexture() {
        return this.texture;
    }

    public float getU() {
        return this.u;
    }

    public float getU2() {
        return this.u2;
    }

    public float getV() {
        return this.v;
    }

    public float getV2() {
        return this.v2;
    }

    public boolean isFlipX() {
        boolean v0;
        if(this.u > this.u2) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isFlipY() {
        boolean v0;
        if(this.v > this.v2) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void scroll(float xAmount, float yAmount) {
        float v5 = 1f;
        if(xAmount != 0f) {
            float v1 = (this.u2 - this.u) * (((float)this.texture.getWidth()));
            this.u = (this.u + xAmount) % v5;
            this.u2 = this.u + v1 / (((float)this.texture.getWidth()));
        }

        if(yAmount != 0f) {
            float v0 = (this.v2 - this.v) * (((float)this.texture.getHeight()));
            this.v = (this.v + yAmount) % v5;
            this.v2 = this.v + v0 / (((float)this.texture.getHeight()));
        }
    }

    public void setRegion(int x, int y, int width, int height) {
        float v1 = 1f / (((float)this.texture.getWidth()));
        float v0 = 1f / (((float)this.texture.getHeight()));
        this.setRegion((((float)x)) * v1, (((float)y)) * v0, (((float)(x + width))) * v1, (((float)(y + height))) * v0);
        this.regionWidth = Math.abs(width);
        this.regionHeight = Math.abs(height);
    }

    public void setRegion(float u, float v, float u2, float v2) {
        float v6 = 0.25f;
        int v3 = this.texture.getWidth();
        int v2 = this.texture.getHeight();
        this.regionWidth = Math.round(Math.abs(u2 - u) * (((float)v3)));
        this.regionHeight = Math.round(Math.abs(v2 - v) * (((float)v2)));
        if(this.regionWidth == 1 && this.regionHeight == 1) {
            float v0 = v6 / (((float)v3));
            u += v0;
            u2 -= v0;
            float v1 = v6 / (((float)v2));
            v += v1;
            v2 -= v1;
        }

        this.u = u;
        this.v = v;
        this.u2 = u2;
        this.v2 = v2;
    }

    public void setRegion(TextureRegion region) {
        this.texture = region.texture;
        this.setRegion(region.u, region.v, region.u2, region.v2);
    }

    public void setRegion(TextureRegion region, int x, int y, int width, int height) {
        this.texture = region.texture;
        this.setRegion(region.getRegionX() + x, region.getRegionY() + y, width, height);
    }

    public void setRegion(Texture texture) {
        this.texture = texture;
        this.setRegion(0, 0, texture.getWidth(), texture.getHeight());
    }

    public void setRegionHeight(int height) {
        if(this.isFlipY()) {
            this.setV(this.v2 + (((float)height)) / (((float)this.texture.getHeight())));
        }
        else {
            this.setV2(this.v + (((float)height)) / (((float)this.texture.getHeight())));
        }
    }

    public void setRegionWidth(int width) {
        if(this.isFlipX()) {
            this.setU(this.u2 + (((float)width)) / (((float)this.texture.getWidth())));
        }
        else {
            this.setU2(this.u + (((float)width)) / (((float)this.texture.getWidth())));
        }
    }

    public void setRegionX(int x) {
        this.setU((((float)x)) / (((float)this.texture.getWidth())));
    }

    public void setRegionY(int y) {
        this.setV((((float)y)) / (((float)this.texture.getHeight())));
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setU(float u) {
        this.u = u;
        this.regionWidth = Math.round(Math.abs(this.u2 - u) * (((float)this.texture.getWidth())));
    }

    public void setU2(float u2) {
        this.u2 = u2;
        this.regionWidth = Math.round(Math.abs(u2 - this.u) * (((float)this.texture.getWidth())));
    }

    public void setV(float v) {
        this.v = v;
        this.regionHeight = Math.round(Math.abs(this.v2 - v) * (((float)this.texture.getHeight())));
    }

    public void setV2(float v2) {
        this.v2 = v2;
        this.regionHeight = Math.round(Math.abs(v2 - this.v) * (((float)this.texture.getHeight())));
    }

    public static TextureRegion[][] split(Texture texture, int tileWidth, int tileHeight) {
        return new TextureRegion(texture).split(tileWidth, tileHeight);
    }

    public TextureRegion[][] split(int tileWidth, int tileHeight) {
        int v2 = this.getRegionX();
        int v3 = this.getRegionY();
        int v13 = this.regionWidth;
        int v10 = this.regionHeight / tileHeight;
        int v7 = v13 / tileWidth;
        int v11 = v2;
        Object v12 = new TextureRegion[v10][v7];
        int v9 = 0;
        while(v9 < v10) {
            v2 = v11;
            int v6 = 0;
            while(v6 < v7) {
                v12[v9][v6] = new TextureRegion(this.texture, v2, v3, tileWidth, tileHeight);
                ++v6;
                v2 += tileWidth;
            }

            ++v9;
            v3 += tileHeight;
        }

        return ((TextureRegion[][])v12);
    }
}

