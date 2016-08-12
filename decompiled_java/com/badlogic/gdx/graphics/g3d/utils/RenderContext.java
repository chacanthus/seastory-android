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
import com.badlogic.gdx.graphics.GL20;

public class RenderContext {
    private int blendDFactor;
    private int blendSFactor;
    private boolean blending;
    private int cullFace;
    private int depthFunc;
    private boolean depthMask;
    private float depthRangeFar;
    private float depthRangeNear;
    public final TextureBinder textureBinder;

    public RenderContext(TextureBinder textures) {
        super();
        this.textureBinder = textures;
    }

    public void begin() {
        Gdx.gl.glDisable(2929);
        this.depthFunc = 0;
        Gdx.gl.glDepthMask(true);
        this.depthMask = true;
        Gdx.gl.glDisable(3042);
        this.blending = false;
        Gdx.gl.glDisable(2884);
        this.blendDFactor = 0;
        this.blendSFactor = 0;
        this.cullFace = 0;
        this.textureBinder.begin();
    }

    public void end() {
        if(this.depthFunc != 0) {
            Gdx.gl.glDisable(2929);
        }

        if(!this.depthMask) {
            Gdx.gl.glDepthMask(true);
        }

        if(this.blending) {
            Gdx.gl.glDisable(3042);
        }

        if(this.cullFace > 0) {
            Gdx.gl.glDisable(2884);
        }

        this.textureBinder.end();
    }

    public void setBlending(boolean enabled, int sFactor, int dFactor) {
        int v1 = 3042;
        if(enabled != this.blending) {
            this.blending = enabled;
            if(enabled) {
                Gdx.gl.glEnable(v1);
            }
            else {
                Gdx.gl.glDisable(v1);
            }
        }

        if((enabled) && (this.blendSFactor != sFactor || this.blendDFactor != dFactor)) {
            Gdx.gl.glBlendFunc(sFactor, dFactor);
            this.blendSFactor = sFactor;
            this.blendDFactor = dFactor;
        }
    }

    public void setCullFace(int face) {
        int v1 = 2884;
        if(face != this.cullFace) {
            this.cullFace = face;
            if(face != 1028 && face != 1029 && face != 1032) {
                Gdx.gl.glDisable(v1);
                return;
            }

            Gdx.gl.glEnable(v1);
            Gdx.gl.glCullFace(face);
        }
    }

    public void setDepthMask(boolean depthMask) {
        if(this.depthMask != depthMask) {
            GL20 v0 = Gdx.gl;
            this.depthMask = depthMask;
            v0.glDepthMask(depthMask);
        }
    }

    public void setDepthTest(int depthFunction, float depthRangeNear, float depthRangeFar) {
        GL20 v2;
        int v1;
        int v4 = 2929;
        int v0 = 1;
        if(this.depthFunc != 0) {
            v1 = 1;
        }
        else {
            v1 = 0;
        }

        if(depthFunction == 0) {
            v0 = 0;
        }

        if(this.depthFunc != depthFunction) {
            this.depthFunc = depthFunction;
            if(v0 != 0) {
                Gdx.gl.glEnable(v4);
                Gdx.gl.glDepthFunc(depthFunction);
            }
            else {
                Gdx.gl.glDisable(v4);
            }
        }

        if(v0 != 0) {
            if(v1 == 0 || this.depthFunc != depthFunction) {
                v2 = Gdx.gl;
                this.depthFunc = depthFunction;
                v2.glDepthFunc(depthFunction);
            }

            if(v1 != 0 && this.depthRangeNear == depthRangeNear && this.depthRangeFar == depthRangeFar) {
                return;
            }

            v2 = Gdx.gl;
            this.depthRangeNear = depthRangeNear;
            this.depthRangeFar = depthRangeFar;
            v2.glDepthRangef(depthRangeNear, depthRangeFar);
        }
    }

    public void setDepthTest(int depthFunction) {
        this.setDepthTest(depthFunction, 0f, 1f);
    }
}

