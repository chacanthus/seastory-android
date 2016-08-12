// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DecalMaterial {
    public static final int NO_BLEND = -1;
    protected int dstBlendFactor;
    protected int srcBlendFactor;
    protected TextureRegion textureRegion;

    public DecalMaterial() {
        super();
    }

    public boolean equals(Object o) {
        boolean v1 = false;
        if(o != null) {
            Object v0 = o;
            if(this.dstBlendFactor == ((DecalMaterial)v0).dstBlendFactor && this.srcBlendFactor == ((DecalMaterial)v0).srcBlendFactor && this.textureRegion.getTexture() == ((DecalMaterial)v0).textureRegion.getTexture()) {
                v1 = true;
            }
        }

        return v1;
    }

    public int getDstBlendFactor() {
        return this.dstBlendFactor;
    }

    public int getSrcBlendFactor() {
        return this.srcBlendFactor;
    }

    public int hashCode() {
        int v0;
        if(this.textureRegion.getTexture() != null) {
            v0 = this.textureRegion.getTexture().hashCode();
        }
        else {
            v0 = 0;
        }

        return (v0 * 31 + this.srcBlendFactor) * 31 + this.dstBlendFactor;
    }

    public boolean isOpaque() {
        boolean v0;
        if(this.srcBlendFactor == -1) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void set() {
        this.textureRegion.getTexture().bind();
        if(!this.isOpaque()) {
            Gdx.gl.glBlendFunc(this.srcBlendFactor, this.dstBlendFactor);
        }
    }
}

