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

public abstract class Shape {
    public enum Type {
        static  {
            Type.Circle = new Type("Circle", 0);
            Type.Edge = new Type("Edge", 1);
            Type.Polygon = new Type("Polygon", 2);
            Type.Chain = new Type("Chain", 3);
            Type[] v0 = new Type[4];
            v0[0] = Type.Circle;
            v0[1] = Type.Edge;
            v0[2] = Type.Polygon;
            v0[3] = Type.Chain;
            Type.$VALUES = v0;
        }

        private Type(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        public static Type[] values() {
            return Type.$VALUES.clone();
        }
    }

    public Shape() {
        super();
    }

    public void dispose() {
        this.jniDispose(this.addr);
    }

    public int getChildCount() {
        return this.jniGetChildCount(this.addr);
    }

    public float getRadius() {
        return this.jniGetRadius(this.addr);
    }

    public abstract Type getType();

    private native void jniDispose() {
    }

    private native int jniGetChildCount() {
    }

    private native float jniGetRadius() {
    }

    protected static native int jniGetType(long arg0) {
    }

    private native void jniSetRadius(float arg1) {
    }

    public void setRadius(float radius) {
        this.jniSetRadius(this.addr, radius);
    }
}

