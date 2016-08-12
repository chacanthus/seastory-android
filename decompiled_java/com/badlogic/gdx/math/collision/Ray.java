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

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;

public class Ray implements Serializable {
    public final Vector3 direction;
    public final Vector3 origin;
    private static final long serialVersionUID = -620692054835390878L;
    static Vector3 tmp;

    static  {
        Ray.tmp = new Vector3();
    }

    public Ray(Vector3 origin, Vector3 direction) {
        super();
        this.origin = new Vector3();
        this.direction = new Vector3();
        this.origin.set(origin);
        this.direction.set(direction).nor();
    }

    public Ray cpy() {
        return new Ray(this.origin, this.direction);
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if((((Ray)o)) != this) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if((this.direction.equals(((Ray)v0).direction)) && (this.origin.equals(((Ray)v0).origin))) {
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

    public Vector3 getEndPoint(Vector3 out, float distance) {
        return out.set(this.direction).scl(distance).add(this.origin);
    }

    public int hashCode() {
        return (this.direction.hashCode() + 73) * 73 + this.origin.hashCode();
    }

    public Ray mul(Matrix4 matrix) {
        Ray.tmp.set(this.origin).add(this.direction);
        Ray.tmp.mul(matrix);
        this.origin.mul(matrix);
        this.direction.set(Ray.tmp.sub(this.origin));
        return this;
    }

    public Ray set(float x, float y, float z, float dx, float dy, float dz) {
        this.origin.set(x, y, z);
        this.direction.set(dx, dy, dz);
        return this;
    }

    public Ray set(Vector3 origin, Vector3 direction) {
        this.origin.set(origin);
        this.direction.set(direction);
        return this;
    }

    public Ray set(Ray ray) {
        this.origin.set(ray.origin);
        this.direction.set(ray.direction);
        return this;
    }

    public String toString() {
        return "ray [" + this.origin + ":" + this.direction + "]";
    }
}

