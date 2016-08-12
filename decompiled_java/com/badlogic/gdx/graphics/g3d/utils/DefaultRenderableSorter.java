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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Comparator;

public class DefaultRenderableSorter implements RenderableSorter, Comparator {
    private Camera camera;
    private final Vector3 tmpV1;
    private final Vector3 tmpV2;

    public DefaultRenderableSorter() {
        super();
        this.tmpV1 = new Vector3();
        this.tmpV2 = new Vector3();
    }

    public int compare(Renderable o1, Renderable o2) {
        int v3;
        int v1;
        int v0;
        int v7 = -1;
        float v11 = 1000f;
        int v5 = 1;
        if(!o1.material.has(BlendingAttribute.Type) || !o1.material.get(BlendingAttribute.Type).blended) {
            v0 = 0;
        }
        else {
            v0 = 1;
        }

        if(!o2.material.has(BlendingAttribute.Type) || !o2.material.get(BlendingAttribute.Type).blended) {
            v1 = 0;
        }
        else {
            v1 = 1;
        }

        if(v0 == v1) {
            o1.worldTransform.getTranslation(this.tmpV1);
            o2.worldTransform.getTranslation(this.tmpV2);
            float v2 = ((float)((((int)(this.camera.position.dst2(this.tmpV1) * v11))) - (((int)(this.camera.position.dst2(this.tmpV2) * v11)))));
            if(v2 < 0f) {
                v3 = v7;
            }
            else if(v2 > 0f) {
                v3 = 1;
            }
            else {
                v3 = 0;
            }

            if(v0 != 0) {
                v3 = -v3;
            }

            v5 = v3;
        }
        else if(v0 == 0) {
            v5 = v7;
        }

        return v5;
    }

    public int compare(Object x0, Object x1) {
        return this.compare(((Renderable)x0), ((Renderable)x1));
    }

    public void sort(Camera camera, Array arg2) {
        this.camera = camera;
        arg2.sort(((Comparator)this));
    }
}

