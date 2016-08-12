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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.IntBuffer;

public final class DefaultTextureBinder implements TextureBinder {
    public static final int MAX_GLES_UNITS = 32;
    public static final int ROUNDROBIN = 0;
    public static final int WEIGHTED = 1;
    private int bindCount;
    private final int count;
    private int currentTexture;
    private final int method;
    private final int offset;
    private int reuseCount;
    private final int reuseWeight;
    private boolean reused;
    private final TextureDescriptor tempDesc;
    private final GLTexture[] textures;
    private final int[] weights;

    public DefaultTextureBinder(int method, int offset) {
        this(method, offset, -1);
    }

    public DefaultTextureBinder(int method) {
        this(method, 0);
    }

    public DefaultTextureBinder(int method, int offset, int count) {
        this(method, offset, count, 10);
    }

    public DefaultTextureBinder(int method, int offset, int count, int reuseWeight) {
        int[] v1;
        super();
        this.reuseCount = 0;
        this.bindCount = 0;
        this.tempDesc = new TextureDescriptor();
        this.currentTexture = 0;
        int v0 = Math.min(DefaultTextureBinder.getMaxTextureUnits(), 32);
        if(count < 0) {
            count = v0 - offset;
        }

        if(offset >= 0 && count >= 0 && offset + count <= v0 && reuseWeight >= 1) {
            this.method = method;
            this.offset = offset;
            this.count = count;
            this.textures = new GLTexture[count];
            this.reuseWeight = reuseWeight;
            if(method == 1) {
                v1 = new int[count];
            }
            else {
                v1 = null;
            }

            this.weights = v1;
            return;
        }

        throw new GdxRuntimeException("Illegal arguments");
    }

    public void begin() {
        int v0;
        for(v0 = 0; v0 < this.count; ++v0) {
            this.textures[v0] = null;
            if(this.weights != null) {
                this.weights[v0] = 0;
            }
        }
    }

    public final int bind(GLTexture texture) {
        this.tempDesc.set(texture, null, null, null, null);
        return this.bindTexture(this.tempDesc, false);
    }

    public final int bind(TextureDescriptor textureDesc) {
        return this.bindTexture(textureDesc, false);
    }

    private final int bindTexture(TextureDescriptor textureDesc, boolean rebind) {
        GLTexture v2 = textureDesc.texture;
        this.reused = false;
        switch(this.method) {
            case 0: {
                goto label_7;
            }
            case 1: {
                goto label_24;
            }
        }

        int v1 = -1;
        goto label_6;
    label_7:
        v1 = this.offset + this.bindTextureRoundRobin(v2);
        goto label_10;
    label_24:
        v1 = this.offset + this.bindTextureWeighted(v2);
    label_10:
        if(this.reused) {
            ++this.reuseCount;
            if(rebind) {
                v2.bind(v1);
            }
            else {
                Gdx.gl.glActiveTexture(33984 + v1);
            }
        }
        else {
            ++this.bindCount;
        }

        v2.unsafeSetWrap(textureDesc.uWrap, textureDesc.vWrap);
        v2.unsafeSetFilter(textureDesc.minFilter, textureDesc.magFilter);
    label_6:
        return v1;
    }

    private final int bindTextureRoundRobin(GLTexture texture) {
        int v1;
        int v0 = 0;
        while(true) {
            if(v0 < this.count) {
                v1 = (this.currentTexture + v0) % this.count;
                if(this.textures[v1] == texture) {
                    this.reused = true;
                }
                else {
                    ++v0;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_12;
        }

        this.currentTexture = (this.currentTexture + 1) % this.count;
        this.textures[this.currentTexture] = texture;
        texture.bind(this.offset + this.currentTexture);
        v1 = this.currentTexture;
    label_12:
        return v1;
    }

    private final int bindTextureWeighted(GLTexture texture) {
        int v1 = -1;
        int v2 = this.weights[0];
        int v3 = 0;
        int v0;
        for(v0 = 0; v0 < this.count; ++v0) {
            if(this.textures[v0] == texture) {
                v1 = v0;
                this.weights[v0] += this.reuseWeight;
            }
            else {
                if(this.weights[v0] >= 0) {
                    int[] v4 = this.weights;
                    int v5 = v4[v0] - 1;
                    v4[v0] = v5;
                    if(v5 < v2) {
                        goto label_27;
                    }

                    goto label_17;
                }

            label_27:
                v2 = this.weights[v0];
                v3 = v0;
            }

        label_17:
        }

        if(v1 < 0) {
            this.textures[v3] = texture;
            this.weights[v3] = 100;
            v1 = v3;
            texture.bind(this.offset + v3);
        }
        else {
            this.reused = true;
        }

        return v1;
    }

    public void end() {
        Gdx.gl.glActiveTexture(33984);
    }

    public final int getBindCount() {
        return this.bindCount;
    }

    private static int getMaxTextureUnits() {
        IntBuffer v0 = BufferUtils.newIntBuffer(16);
        Gdx.gl.glGetIntegerv(34930, v0);
        return v0.get(0);
    }

    public final int getReuseCount() {
        return this.reuseCount;
    }

    public final void resetCounts() {
        this.reuseCount = 0;
        this.bindCount = 0;
    }
}

