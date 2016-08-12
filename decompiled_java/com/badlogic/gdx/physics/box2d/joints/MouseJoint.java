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

public class MouseJoint extends Joint {
    private final Vector2 target;
    final float[] tmp;

    public MouseJoint(World world, long addr) {
        super(world, addr);
        this.tmp = new float[2];
        this.target = new Vector2();
    }

    public float getDampingRatio() {
        return this.jniGetDampingRatio(this.addr);
    }

    public float getFrequency() {
        return this.jniGetFrequency(this.addr);
    }

    public float getMaxForce() {
        return this.jniGetMaxForce(this.addr);
    }

    public Vector2 getTarget() {
        this.jniGetTarget(this.addr, this.tmp);
        this.target.x = this.tmp[0];
        this.target.y = this.tmp[1];
        return this.target;
    }

    private native float jniGetDampingRatio() {
    }

    private native float jniGetFrequency() {
    }

    private native float jniGetMaxForce() {
    }

    private native void jniGetTarget(float[] arg1) {
    }

    private native void jniSetDampingRatio(float arg1) {
    }

    private native void jniSetFrequency(float arg1) {
    }

    private native void jniSetMaxForce(float arg1) {
    }

    private native void jniSetTarget(float arg1, float arg2) {
    }

    public void setDampingRatio(float ratio) {
        this.jniSetDampingRatio(this.addr, ratio);
    }

    public void setFrequency(float hz) {
        this.jniSetFrequency(this.addr, hz);
    }

    public void setMaxForce(float force) {
        this.jniSetMaxForce(this.addr, force);
    }

    public void setTarget(Vector2 target) {
        this.jniSetTarget(this.addr, target.x, target.y);
    }
}

