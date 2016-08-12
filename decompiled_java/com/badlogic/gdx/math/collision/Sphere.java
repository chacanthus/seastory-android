// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Sphere implements Serializable {
    private static final float PI_4_3 = 4.18879f;
    public final Vector3 center;
    public float radius;
    private static final long serialVersionUID = -6487336868908521596L;

    public Sphere(Vector3 center, float radius) {
        super();
        this.center = new Vector3(center);
        this.radius = radius;
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if(this != (((Sphere)o))) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if(this.radius == ((Sphere)v0).radius && (this.center.equals(((Sphere)v0).center))) {
                    goto label_3;
                }

                v1 = false;
                goto label_3;
            }

            v1 = false;
        }

    label_3:
        return v1;
    }

    public int hashCode() {
        return (this.center.hashCode() + 71) * 71 + NumberUtils.floatToRawIntBits(this.radius);
    }

    public boolean overlaps(Sphere sphere) {
        boolean v0;
        if(this.center.dst2(sphere.center) < (this.radius + sphere.radius) * (this.radius + sphere.radius)) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public float surfaceArea() {
        return 12.566371f * this.radius * this.radius;
    }

    public float volume() {
        return 4.18879f * this.radius * this.radius * this.radius;
    }
}

