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

public class CircleShape extends Shape {
    private final Vector2 position;
    private final float[] tmp;

    protected CircleShape(long addr) {
        super();
        this.tmp = new float[2];
        this.position = new Vector2();
        this.addr = addr;
    }

    public CircleShape() {
        super();
        this.tmp = new float[2];
        this.position = new Vector2();
        this.addr = this.newCircleShape();
    }

    public Vector2 getPosition() {
        this.jniGetPosition(this.addr, this.tmp);
        this.position.x = this.tmp[0];
        this.position.y = this.tmp[1];
        return this.position;
    }

    public Type getType() {
        return Type.Circle;
    }

    private native void jniGetPosition(float[] arg1) {
    }

    private native void jniSetPosition(float arg1, float arg2) {
    }

    private native long newCircleShape() {
    }

    public void setPosition(Vector2 position) {
        this.jniSetPosition(this.addr, position.x, position.y);
    }
}

