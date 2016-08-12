// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

public class Manifold {
    public class ManifoldPoint {
        public int contactID;
        public final Vector2 localPoint;
        public float normalImpulse;
        public float tangentImpulse;

        public ManifoldPoint(Manifold arg2) {
            Manifold.this = arg2;
            super();
            this.localPoint = new Vector2();
            this.contactID = 0;
        }

        public String toString() {
            return "id: " + this.contactID + ", " + this.localPoint + ", " + this.normalImpulse + ", " + this.tangentImpulse;
        }
    }

    public enum ManifoldType {
        static  {
            ManifoldType.Circle = new ManifoldType("Circle", 0);
            ManifoldType.FaceA = new ManifoldType("FaceA", 1);
            ManifoldType.FaceB = new ManifoldType("FaceB", 2);
            ManifoldType[] v0 = new ManifoldType[3];
            v0[0] = ManifoldType.Circle;
            v0[1] = ManifoldType.FaceA;
            v0[2] = ManifoldType.FaceB;
            ManifoldType.$VALUES = v0;
        }

        private ManifoldType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static ManifoldType valueOf(String name) {
            return Enum.valueOf(ManifoldType.class, name);
        }

        public static ManifoldType[] values() {
            return ManifoldType.$VALUES.clone();
        }
    }

    long addr;
    final Vector2 localNormal;
    final Vector2 localPoint;
    final ManifoldPoint[] points;
    final float[] tmpFloat;
    final int[] tmpInt;

    protected Manifold(long addr) {
        super();
        ManifoldPoint[] v0 = new ManifoldPoint[2];
        v0[0] = new ManifoldPoint(this);
        v0[1] = new ManifoldPoint(this);
        this.points = v0;
        this.localNormal = new Vector2();
        this.localPoint = new Vector2();
        this.tmpInt = new int[2];
        this.tmpFloat = new float[4];
        this.addr = addr;
    }

    public Vector2 getLocalNormal() {
        this.jniGetLocalNormal(this.addr, this.tmpFloat);
        this.localNormal.set(this.tmpFloat[0], this.tmpFloat[1]);
        return this.localNormal;
    }

    public Vector2 getLocalPoint() {
        this.jniGetLocalPoint(this.addr, this.tmpFloat);
        this.localPoint.set(this.tmpFloat[0], this.tmpFloat[1]);
        return this.localPoint;
    }

    public int getPointCount() {
        return this.jniGetPointCount(this.addr);
    }

    public ManifoldPoint[] getPoints() {
        int v1 = this.jniGetPointCount(this.addr);
        int v2;
        for(v2 = 0; v2 < v1; ++v2) {
            int v0 = this.jniGetPoint(this.addr, this.tmpFloat, v2);
            ManifoldPoint v3 = this.points[v2];
            v3.contactID = v0;
            v3.localPoint.set(this.tmpFloat[0], this.tmpFloat[1]);
            v3.normalImpulse = this.tmpFloat[2];
            v3.tangentImpulse = this.tmpFloat[3];
        }

        return this.points;
    }

    public ManifoldType getType() {
        ManifoldType v1;
        int v0 = this.jniGetType(this.addr);
        if(v0 == 0) {
            v1 = ManifoldType.Circle;
        }
        else if(v0 == 1) {
            v1 = ManifoldType.FaceA;
        }
        else if(v0 == 2) {
            v1 = ManifoldType.FaceB;
        }
        else {
            v1 = ManifoldType.Circle;
        }

        return v1;
    }

    private native void jniGetLocalNormal(float[] arg1) {
    }

    private native void jniGetLocalPoint(float[] arg1) {
    }

    private native int jniGetPoint(float[] arg1, int arg2) {
    }

    private native int jniGetPointCount() {
    }

    private native int jniGetType() {
    }
}

