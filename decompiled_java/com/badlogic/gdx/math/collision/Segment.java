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
import java.io.Serializable;

public class Segment implements Serializable {
    public final Vector3 a;
    public final Vector3 b;
    private static final long serialVersionUID = 2739667069736519602L;

    public Segment(float aX, float aY, float aZ, float bX, float bY, float bZ) {
        super();
        this.a = new Vector3();
        this.b = new Vector3();
        this.a.set(aX, aY, aZ);
        this.b.set(bX, bY, bZ);
    }

    public Segment(Vector3 a, Vector3 b) {
        super();
        this.a = new Vector3();
        this.b = new Vector3();
        this.a.set(a);
        this.b.set(b);
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if((((Segment)o)) != this) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if((this.a.equals(((Segment)v0).a)) && (this.b.equals(((Segment)v0).b))) {
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
        return (this.a.hashCode() + 71) * 71 + this.b.hashCode();
    }

    public float len() {
        return this.a.dst(this.b);
    }

    public float len2() {
        return this.a.dst2(this.b);
    }
}

