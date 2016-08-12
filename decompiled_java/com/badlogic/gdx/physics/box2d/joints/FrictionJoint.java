// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

public class FrictionJoint extends Joint {
    private final Vector2 localAnchorA;
    private final Vector2 localAnchorB;
    private final float[] tmp;

    public FrictionJoint(World world, long addr) {
        super(world, addr);
        this.tmp = new float[2];
        this.localAnchorA = new Vector2();
        this.localAnchorB = new Vector2();
    }

    public Vector2 getLocalAnchorA() {
        this.jniGetLocalAnchorA(this.addr, this.tmp);
        this.localAnchorA.set(this.tmp[0], this.tmp[1]);
        return this.localAnchorA;
    }

    public Vector2 getLocalAnchorB() {
        this.jniGetLocalAnchorB(this.addr, this.tmp);
        this.localAnchorB.set(this.tmp[0], this.tmp[1]);
        return this.localAnchorB;
    }

    public float getMaxForce() {
        return this.jniGetMaxForce(this.addr);
    }

    public float getMaxTorque() {
        return this.jniGetMaxTorque(this.addr);
    }

    private native void jniGetLocalAnchorA(float[] arg1) {
    }

    private native void jniGetLocalAnchorB(float[] arg1) {
    }

    private native float jniGetMaxForce() {
    }

    private native float jniGetMaxTorque() {
    }

    private native void jniSetMaxForce(float arg1) {
    }

    private native void jniSetMaxTorque(float arg1) {
    }

    public void setMaxForce(float force) {
        this.jniSetMaxForce(this.addr, force);
    }

    public void setMaxTorque(float torque) {
        this.jniSetMaxTorque(this.addr, torque);
    }
}

