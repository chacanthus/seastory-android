// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.ShadowMap;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Iterator;

public class Environment extends Attributes {
    public final Array directionalLights;
    public final Array pointLights;
    public ShadowMap shadowMap;
    public final Array spotLights;

    public Environment() {
        super();
        this.directionalLights = new Array();
        this.pointLights = new Array();
        this.spotLights = new Array();
    }

    public Environment add(BaseLight light) {
        if((light instanceof DirectionalLight)) {
            this.directionalLights.add(light);
        }
        else if((light instanceof PointLight)) {
            this.pointLights.add(light);
        }
        else if((light instanceof SpotLight)) {
            this.spotLights.add(light);
        }
        else {
            goto label_15;
        }

        return this;
    label_15:
        throw new GdxRuntimeException("Unknown light type");
    }

    public Environment add(Array arg4) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.add(v0.next());
        }

        return this;
    }

    public Environment add(BaseLight[] lights) {
        BaseLight[] v0 = lights;
        int v2 = v0.length;
        int v1;
        for(v1 = 0; v1 < v2; ++v1) {
            this.add(v0[v1]);
        }

        return this;
    }

    public void clear() {
        super.clear();
        this.directionalLights.clear();
        this.pointLights.clear();
        this.spotLights.clear();
    }

    public Environment remove(BaseLight light) {
        if((light instanceof DirectionalLight)) {
            this.directionalLights.removeValue(light, false);
        }
        else if((light instanceof PointLight)) {
            this.pointLights.removeValue(light, false);
        }
        else if((light instanceof SpotLight)) {
            this.spotLights.removeValue(light, false);
        }
        else {
            goto label_16;
        }

        return this;
    label_16:
        throw new GdxRuntimeException("Unknown light type");
    }

    public Environment remove(Array arg4) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.remove(v0.next());
        }

        return this;
    }

    public Environment remove(BaseLight[] lights) {
        BaseLight[] v0 = lights;
        int v2 = v0.length;
        int v1;
        for(v1 = 0; v1 < v2; ++v1) {
            this.remove(v0[v1]);
        }

        return this;
    }
}

