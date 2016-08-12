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

public class PulleyJoint extends Joint {
    private final Vector2 groundAnchorA;
    private final Vector2 groundAnchorB;
    private final float[] tmp;

    public PulleyJoint(World world, long addr) {
        super(world, addr);
        this.tmp = new float[2];
        this.groundAnchorA = new Vector2();
        this.groundAnchorB = new Vector2();
    }

    public Vector2 getGroundAnchorA() {
        this.jniGetGroundAnchorA(this.addr, this.tmp);
        this.groundAnchorA.set(this.tmp[0], this.tmp[1]);
        return this.groundAnchorA;
    }

    public Vector2 getGroundAnchorB() {
        this.jniGetGroundAnchorB(this.addr, this.tmp);
        this.groundAnchorB.set(this.tmp[0], this.tmp[1]);
        return this.groundAnchorB;
    }

    public float getLength1() {
        return this.jniGetLength1(this.addr);
    }

    public float getLength2() {
        return this.jniGetLength2(this.addr);
    }

    public float getRatio() {
        return this.jniGetRatio(this.addr);
    }

    private native void jniGetGroundAnchorA(float[] arg1) {
    }

    private native void jniGetGroundAnchorB(float[] arg1) {
    }

    private native float jniGetLength1() {
    }

    private native float jniGetLength2() {
    }

    private native float jniGetRatio() {
    }
}

