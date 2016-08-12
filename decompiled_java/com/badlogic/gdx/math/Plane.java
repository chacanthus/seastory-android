// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

import java.io.Serializable;

public class Plane implements Serializable {
    public enum PlaneSide {
        static  {
            PlaneSide.OnPlane = new PlaneSide("OnPlane", 0);
            PlaneSide.Back = new PlaneSide("Back", 1);
            PlaneSide.Front = new PlaneSide("Front", 2);
            PlaneSide[] v0 = new PlaneSide[3];
            v0[0] = PlaneSide.OnPlane;
            v0[1] = PlaneSide.Back;
            v0[2] = PlaneSide.Front;
            PlaneSide.$VALUES = v0;
        }

        private PlaneSide(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static PlaneSide valueOf(String name) {
            return Enum.valueOf(PlaneSide.class, name);
        }

        public static PlaneSide[] values() {
            return PlaneSide.$VALUES.clone();
        }
    }

    public float d;
    public final Vector3 normal;
    private static final long serialVersionUID = -1240652082930747866L;

    public Plane(Vector3 normal, float d) {
        super();
        this.normal = new Vector3();
        this.d = 0f;
        this.normal.set(normal).nor();
        this.d = d;
    }

    public Plane(Vector3 normal, Vector3 point) {
        super();
        this.normal = new Vector3();
        this.d = 0f;
        this.normal.set(normal).nor();
        this.d = -this.normal.dot(point);
    }

    public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
        super();
        this.normal = new Vector3();
        this.d = 0f;
        this.set(point1, point2, point3);
    }

    public float distance(Vector3 point) {
        return this.normal.dot(point) + this.d;
    }

    public float getD() {
        return this.d;
    }

    public Vector3 getNormal() {
        return this.normal;
    }

    public boolean isFrontFacing(Vector3 direction) {
        boolean v1;
        if(this.normal.dot(direction) <= 0f) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        return v1;
    }

    public void set(Vector3 point1, Vector3 point2, Vector3 point3) {
        this.normal.set(point1).sub(point2).crs(point2.x - point3.x, point2.y - point3.y, point2.z - point3.z).nor();
        this.d = -point1.dot(this.normal);
    }

    public void set(float nx, float ny, float nz, float d) {
        this.normal.set(nx, ny, nz);
        this.d = d;
    }

    public void set(float pointX, float pointY, float pointZ, float norX, float norY, float norZ) {
        this.normal.set(norX, norY, norZ);
        this.d = -(pointX * norX + pointY * norY + pointZ * norZ);
    }

    public void set(Plane plane) {
        this.normal.set(plane.normal);
        this.d = plane.d;
    }

    public void set(Vector3 point, Vector3 normal) {
        this.normal.set(normal);
        this.d = -point.dot(normal);
    }

    public PlaneSide testPoint(float x, float y, float z) {
        PlaneSide v1;
        float v0 = this.normal.dot(x, y, z) + this.d;
        if(v0 == 0f) {
            v1 = PlaneSide.OnPlane;
        }
        else if(v0 < 0f) {
            v1 = PlaneSide.Back;
        }
        else {
            v1 = PlaneSide.Front;
        }

        return v1;
    }

    public PlaneSide testPoint(Vector3 point) {
        PlaneSide v1;
        float v0 = this.normal.dot(point) + this.d;
        if(v0 == 0f) {
            v1 = PlaneSide.OnPlane;
        }
        else if(v0 < 0f) {
            v1 = PlaneSide.Back;
        }
        else {
            v1 = PlaneSide.Front;
        }

        return v1;
    }

    public String toString() {
        return this.normal.toString() + ", " + this.d;
    }
}

