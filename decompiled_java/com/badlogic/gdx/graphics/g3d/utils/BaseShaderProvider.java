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

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public abstract class BaseShaderProvider implements ShaderProvider {
    protected Array shaders;

    public BaseShaderProvider() {
        super();
        this.shaders = new Array();
    }

    protected abstract Shader createShader(Renderable arg0);

    public void dispose() {
        Iterator v0 = this.shaders.iterator();
        while(v0.hasNext()) {
            v0.next().dispose();
        }

        this.shaders.clear();
    }

    public Shader getShader(Renderable renderable) {
        Shader v1;
        Object v1_1;
        Shader v2 = renderable.shader;
        if(v2 == null || !v2.canRender(renderable)) {
            Iterator v0 = this.shaders.iterator();
            do {
                if(v0.hasNext()) {
                    v1_1 = v0.next();
                    if(!((Shader)v1_1).canRender(renderable)) {
                        continue;
                    }

                    break;
                }
                else {
                    goto label_14;
                }
            }
            while(true);

            goto label_5;
        label_14:
            v1 = this.createShader(renderable);
            v1.init();
            this.shaders.add(v1);
        }
        else {
            v1 = v2;
        }

    label_5:
        return ((Shader)v1_1);
    }
}

