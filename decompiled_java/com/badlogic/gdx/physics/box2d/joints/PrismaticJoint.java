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

public class PrismaticJoint extends Joint {
    private final Vector2 localAnchorA;
    private final Vector2 localAnchorB;
    private final Vector2 localAxisA;
    private final float[] tmp;

    public PrismaticJoint(World world, long addr) {
        super(world, addr);
        this.tmp = new float[2];
        this.localAnchorA = new Vector2();
        this.localAnchorB = new Vector2();
        this.localAxisA = new Vector2();
    }

    public void enableLimit(boolean flag) {
        this.jniEnableLimit(this.addr, flag);
    }

    public void enableMotor(boolean flag) {
        this.jniEnableMotor(this.addr, flag);
    }

    public float getJointSpeed() {
        return this.jniGetJointSpeed(this.addr);
    }

    public float getJointTranslation() {
        return this.jniGetJointTranslation(this.addr);
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

    public Vector2 getLocalAxisA() {
        this.jniGetLocalAxisA(this.addr, this.tmp);
        this.localAxisA.set(this.tmp[0], this.tmp[1]);
        return this.localAxisA;
    }

    public float getLowerLimit() {
        return this.jniGetLowerLimit(this.addr);
    }

    public float getMaxMotorForce() {
        return this.jniGetMaxMotorForce(this.addr);
    }

    public float getMotorForce(float invDt) {
        return this.jniGetMotorForce(this.addr, invDt);
    }

    public float getMotorSpeed() {
        return this.jniGetMotorSpeed(this.addr);
    }

    public float getReferenceAngle() {
        return this.jniGetReferenceAngle(this.addr);
    }

    public float getUpperLimit() {
        return this.jniGetUpperLimit(this.addr);
    }

    public boolean isLimitEnabled() {
        return this.jniIsLimitEnabled(this.addr);
    }

    public boolean isMotorEnabled() {
        return this.jniIsMotorEnabled(this.addr);
    }

    private native void jniEnableLimit(boolean arg1) {
    }

    private native void jniEnableMotor(boolean arg1) {
    }

    private native float jniGetJointSpeed() {
    }

    private native float jniGetJointTranslation() {
    }

    private native void jniGetLocalAnchorA(float[] arg1) {
    }

    private native void jniGetLocalAnchorB(float[] arg1) {
    }

    private native void jniGetLocalAxisA(float[] arg1) {
    }

    private native float jniGetLowerLimit() {
    }

    private native float jniGetMaxMotorForce() {
    }

    private native float jniGetMotorForce(float arg1) {
    }

    private native float jniGetMotorSpeed() {
    }

    private native float jniGetReferenceAngle() {
    }

    private native float jniGetUpperLimit() {
    }

    private native boolean jniIsLimitEnabled() {
    }

    private native boolean jniIsMotorEnabled() {
    }

    private native void jniSetLimits(float arg1, float arg2) {
    }

    private native void jniSetMaxMotorForce(float arg1) {
    }

    private native void jniSetMotorSpeed(float arg1) {
    }

    public void setLimits(float lower, float upper) {
        this.jniSetLimits(this.addr, lower, upper);
    }

    public void setMaxMotorForce(float force) {
        this.jniSetMaxMotorForce(this.addr, force);
    }

    public void setMotorSpeed(float speed) {
        this.jniSetMotorSpeed(this.addr, speed);
    }
}

