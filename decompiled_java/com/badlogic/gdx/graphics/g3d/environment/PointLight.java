// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class PointLight extends BaseLight {
    public float intensity;
    public final Vector3 position;

    public PointLight() {
        super();
        this.position = new Vector3();
    }

    public boolean equals(PointLight other) {
        boolean v0;
        if(other != null) {
            if(other != this) {
                if(!this.color.equals(other.color)) {
                    goto label_15;
                }
                else if(this.position.equals(other.position)) {
                    if(this.intensity == other.intensity) {
                        goto label_13;
                    }

                    goto label_15;
                }
                else {
                    goto label_15;
                }
            }

        label_13:
            v0 = true;
        }
        else {
        label_15:
            v0 = false;
        }

        return v0;
    }

    public boolean equals(Object obj) {
        boolean v0;
        if((obj instanceof PointLight)) {
            v0 = this.equals(((PointLight)obj));
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public PointLight set(float r, float g, float b, float x, float y, float z, float intensity) {
        this.color.set(r, g, b, 1f);
        this.position.set(x, y, z);
        this.intensity = intensity;
        return this;
    }

    public PointLight set(PointLight copyFrom) {
        return this.set(copyFrom.color, copyFrom.position, copyFrom.intensity);
    }

    public PointLight set(float r, float g, float b, Vector3 position, float intensity) {
        this.color.set(r, g, b, 1f);
        if(position != null) {
            this.position.set(position);
        }

        this.intensity = intensity;
        return this;
    }

    public PointLight set(Color color, float x, float y, float z, float intensity) {
        if(color != null) {
            this.color.set(color);
        }

        this.position.set(x, y, z);
        this.intensity = intensity;
        return this;
    }

    public PointLight set(Color color, Vector3 position, float intensity) {
        if(color != null) {
            this.color.set(color);
        }

        if(position != null) {
            this.position.set(position);
        }

        this.intensity = intensity;
        return this;
    }
}

