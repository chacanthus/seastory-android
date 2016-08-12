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

public abstract class Joint {
    protected long addr;
    private final Vector2 anchorA;
    private final Vector2 anchorB;
    protected JointEdge jointEdgeA;
    protected JointEdge jointEdgeB;
    private final Vector2 reactionForce;
    private final float[] tmp;
    private Object userData;
    private final World world;

    protected Joint(World world, long addr) {
        super();
        this.tmp = new float[2];
        this.anchorA = new Vector2();
        this.anchorB = new Vector2();
        this.reactionForce = new Vector2();
        this.world = world;
        this.addr = addr;
    }

    public Vector2 getAnchorA() {
        this.jniGetAnchorA(this.addr, this.tmp);
        this.anchorA.x = this.tmp[0];
        this.anchorA.y = this.tmp[1];
        return this.anchorA;
    }

    public Vector2 getAnchorB() {
        this.jniGetAnchorB(this.addr, this.tmp);
        this.anchorB.x = this.tmp[0];
        this.anchorB.y = this.tmp[1];
        return this.anchorB;
    }

    public Body getBodyA() {
        return this.world.bodies.get(this.jniGetBodyA(this.addr));
    }

    public Body getBodyB() {
        return this.world.bodies.get(this.jniGetBodyB(this.addr));
    }

    public boolean getCollideConnected() {
        return this.jniGetCollideConnected(this.addr);
    }

    public Vector2 getReactionForce(float inv_dt) {
        this.jniGetReactionForce(this.addr, inv_dt, this.tmp);
        this.reactionForce.x = this.tmp[0];
        this.reactionForce.y = this.tmp[1];
        return this.reactionForce;
    }

    public float getReactionTorque(float inv_dt) {
        return this.jniGetReactionTorque(this.addr, inv_dt);
    }

    public JointType getType() {
        JointType v1;
        int v0 = this.jniGetType(this.addr);
        if(v0 <= 0 || v0 >= JointType.valueTypes.length) {
            v1 = JointType.Unknown;
        }
        else {
            v1 = JointType.valueTypes[v0];
        }

        return v1;
    }

    public Object getUserData() {
        return this.userData;
    }

    public boolean isActive() {
        return this.jniIsActive(this.addr);
    }

    private native void jniGetAnchorA(float[] arg1) {
    }

    private native void jniGetAnchorB(float[] arg1) {
    }

    private native long jniGetBodyA() {
    }

    private native long jniGetBodyB() {
    }

    private native boolean jniGetCollideConnected() {
    }

    private native void jniGetReactionForce(float arg1, float[] arg2) {
    }

    private native float jniGetReactionTorque(float arg1) {
    }

    private native int jniGetType() {
    }

    private native boolean jniIsActive() {
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}

