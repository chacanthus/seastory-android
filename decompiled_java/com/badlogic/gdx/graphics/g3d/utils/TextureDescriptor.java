// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;

public class TextureDescriptor implements Comparable {
    public TextureFilter magFilter;
    public TextureFilter minFilter;
    public TextureWrap uWrap;
    public TextureWrap vWrap;

    public TextureDescriptor() {
        super();
        this.texture = null;
    }

    public TextureDescriptor(GLTexture arg7) {
        this(arg7, null, null, null, null);
    }

    public TextureDescriptor(GLTexture arg2, TextureFilter minFilter, TextureFilter magFilter, TextureWrap uWrap, TextureWrap vWrap) {
        super();
        this.texture = null;
        this.set(arg2, minFilter, magFilter, uWrap, vWrap);
    }

    public int compareTo(TextureDescriptor arg4) {
        int v0 = 0;
        if(arg4 != this) {
            if(this.texture.glTarget != arg4.texture.glTarget) {
                v0 = this.texture.glTarget - arg4.texture.glTarget;
            }
            else if(this.texture.getTextureObjectHandle() != arg4.texture.getTextureObjectHandle()) {
                v0 = this.texture.getTextureObjectHandle() - arg4.texture.getTextureObjectHandle();
            }
            else if(this.minFilter != arg4.minFilter) {
                v0 = this.minFilter.getGLEnum() - arg4.minFilter.getGLEnum();
            }
            else if(this.magFilter != arg4.magFilter) {
                v0 = this.magFilter.getGLEnum() - arg4.magFilter.getGLEnum();
            }
            else if(this.uWrap != arg4.uWrap) {
                v0 = this.uWrap.getGLEnum() - arg4.uWrap.getGLEnum();
            }
            else if(this.vWrap != arg4.vWrap) {
                v0 = this.vWrap.getGLEnum() - arg4.vWrap.getGLEnum();
            }
        }

        return v0;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((TextureDescriptor)x0));
    }

    public boolean equals(Object obj) {
        boolean v1 = true;
        boolean v2 = false;
        if(obj != null) {
            if((((TextureDescriptor)obj)) == this) {
                v2 = true;
            }
            else if((obj instanceof TextureDescriptor)) {
                Object v0 = obj;
                if(((TextureDescriptor)v0).texture != this.texture || ((TextureDescriptor)v0).minFilter != this.minFilter || ((TextureDescriptor)v0).magFilter != this.magFilter || ((TextureDescriptor)v0).uWrap != this.uWrap || ((TextureDescriptor)v0).vWrap != this.vWrap) {
                    v1 = false;
                }

                v2 = v1;
            }
        }

        return v2;
    }

    public int hashCode() {
        long v0 = 811 * (811 * (811 * (811 * (811 * (((long)this.texture.glTarget)) + (((long)this.texture.getTextureObjectHandle()))) + (((long)this.minFilter.getGLEnum()))) + (((long)this.magFilter.getGLEnum()))) + (((long)this.uWrap.getGLEnum()))) + (((long)this.vWrap.getGLEnum()));
        return ((int)(v0 >> 32 ^ v0));
    }

    public void set(GLTexture arg1, TextureFilter minFilter, TextureFilter magFilter, TextureWrap uWrap, TextureWrap vWrap) {
        this.texture = arg1;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.uWrap = uWrap;
        this.vWrap = vWrap;
    }

    public void set(TextureDescriptor arg2) {
        this.texture = arg2.texture;
        this.minFilter = arg2.minFilter;
        this.magFilter = arg2.magFilter;
        this.uWrap = arg2.uWrap;
        this.vWrap = arg2.vWrap;
    }
}

