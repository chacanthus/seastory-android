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

public class Contact {
    protected long addr;
    private final float[] tmp;
    protected World world;
    protected final WorldManifold worldManifold;

    protected Contact(World world, long addr) {
        super();
        this.worldManifold = new WorldManifold();
        this.tmp = new float[8];
        this.addr = addr;
        this.world = world;
    }

    public void ResetRestitution() {
        this.jniResetRestitution(this.addr);
    }

    public int getChildIndexA() {
        return this.jniGetChildIndexA(this.addr);
    }

    public int getChildIndexB() {
        return this.jniGetChildIndexB(this.addr);
    }

    public Fixture getFixtureA() {
        return this.world.fixtures.get(this.jniGetFixtureA(this.addr));
    }

    public Fixture getFixtureB() {
        return this.world.fixtures.get(this.jniGetFixtureB(this.addr));
    }

    public float getFriction() {
        return this.jniGetFriction(this.addr);
    }

    public float getRestitution() {
        return this.jniGetRestitution(this.addr);
    }

    public float getTangentSpeed() {
        return this.jniGetTangentSpeed(this.addr);
    }

    public WorldManifold getWorldManifold() {
        int v1 = this.jniGetWorldManifold(this.addr, this.tmp);
        this.worldManifold.numContactPoints = v1;
        this.worldManifold.normal.set(this.tmp[0], this.tmp[1]);
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            Vector2 v2 = this.worldManifold.points[v0];
            v2.x = this.tmp[v0 * 2 + 2];
            v2.y = this.tmp[v0 * 2 + 3];
        }

        this.worldManifold.separations[0] = this.tmp[6];
        this.worldManifold.separations[1] = this.tmp[7];
        return this.worldManifold;
    }

    public boolean isEnabled() {
        return this.jniIsEnabled(this.addr);
    }

    public boolean isTouching() {
        return this.jniIsTouching(this.addr);
    }

    private native int jniGetChildIndexA() {
    }

    private native int jniGetChildIndexB() {
    }

    private native long jniGetFixtureA() {
    }

    private native long jniGetFixtureB() {
    }

    private native float jniGetFriction() {
    }

    private native float jniGetRestitution() {
    }

    private native float jniGetTangentSpeed() {
    }

    private native int jniGetWorldManifold(float[] arg1) {
    }

    private native boolean jniIsEnabled() {
    }

    private native boolean jniIsTouching() {
    }

    private native void jniResetFriction() {
    }

    private native void jniResetRestitution() {
    }

    private native void jniSetEnabled(boolean arg1) {
    }

    private native void jniSetFriction(float arg1) {
    }

    private native void jniSetRestitution(float arg1) {
    }

    private native void jniSetTangentSpeed(float arg1) {
    }

    public void resetFriction() {
        this.jniResetFriction(this.addr);
    }

    public void setEnabled(boolean flag) {
        this.jniSetEnabled(this.addr, flag);
    }

    public void setFriction(float friction) {
        this.jniSetFriction(this.addr, friction);
    }

    public void setRestitution(float restitution) {
        this.jniSetRestitution(this.addr, restitution);
    }

    public void setTangentSpeed(float speed) {
        this.jniSetTangentSpeed(this.addr, speed);
    }
}

