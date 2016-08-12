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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class SpotLight extends BaseLight {
    public float cutoffAngle;
    public final Vector3 direction;
    public float intensity;
    public final Vector3 position;

    public SpotLight() {
        super();
        this.position = new Vector3();
        this.direction = new Vector3();
    }

    public boolean equals(SpotLight other) {
        boolean v0;
        if(other != null) {
            if(other != this) {
                if(!this.color.equals(other.color)) {
                    goto label_24;
                }
                else if(!this.position.equals(other.position)) {
                    goto label_24;
                }
                else if(!this.direction.equals(other.direction)) {
                    goto label_24;
                }
                else if(MathUtils.isEqual(this.intensity, other.intensity)) {
                    if(MathUtils.isEqual(this.cutoffAngle, other.cutoffAngle)) {
                        goto label_22;
                    }

                    goto label_24;
                }
                else {
                    goto label_24;
                }
            }

        label_22:
            v0 = true;
        }
        else {
        label_24:
            v0 = false;
        }

        return v0;
    }

    public boolean equals(Object obj) {
        boolean v0;
        if((obj instanceof SpotLight)) {
            v0 = this.equals(((SpotLight)obj));
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public SpotLight set(float r, float g, float b, float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float intensity, float cutoffAngle) {
        this.color.set(r, g, b, 1f);
        this.position.set(posX, posY, posZ);
        this.direction.set(dirX, dirY, dirZ).nor();
        this.intensity = intensity;
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public SpotLight set(float r, float g, float b, Vector3 position, Vector3 direction, float intensity, float cutoffAngle) {
        this.color.set(r, g, b, 1f);
        if(position != null) {
            this.position.set(position);
        }

        if(direction != null) {
            this.direction.set(direction).nor();
        }

        this.intensity = intensity;
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public SpotLight set(Color color, float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float intensity, float cutoffAngle) {
        if(color != null) {
            this.color.set(color);
        }

        this.position.set(posX, posY, posZ);
        this.direction.set(dirX, dirY, dirZ).nor();
        this.intensity = intensity;
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public SpotLight set(Color color, Vector3 position, Vector3 direction, float intensity, float cutoffAngle) {
        if(color != null) {
            this.color.set(color);
        }

        if(position != null) {
            this.position.set(position);
        }

        if(direction != null) {
            this.direction.set(direction).nor();
        }

        this.intensity = intensity;
        this.cutoffAngle = cutoffAngle;
        return this;
    }

    public SpotLight set(SpotLight copyFrom) {
        return this.set(copyFrom.color, copyFrom.position, copyFrom.direction, copyFrom.intensity, copyFrom.cutoffAngle);
    }

    public SpotLight setTarget(Vector3 target) {
        this.direction.set(target).sub(this.position).nor();
        return this;
    }
}

