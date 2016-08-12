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

public class Transform {
    public static final int COS = 2;
    public static final int POS_X = 0;
    public static final int POS_Y = 1;
    public static final int SIN = 3;
    private Vector2 position;
    public float[] vals;

    public Transform() {
        super();
        this.vals = new float[4];
        this.position = new Vector2();
    }

    public Transform(Vector2 position, float angle) {
        super();
        this.vals = new float[4];
        this.position = new Vector2();
        this.setPosition(position);
        this.setRotation(angle);
    }

    public Vector2 getPosition() {
        return this.position.set(this.vals[0], this.vals[1]);
    }

    public float getRotation() {
        return ((float)Math.atan2(((double)this.vals[3]), ((double)this.vals[2])));
    }

    public Vector2 mul(Vector2 v) {
        float v0 = this.vals[0] + this.vals[2] * v.x + -this.vals[3] * v.y;
        float v1 = this.vals[1] + this.vals[3] * v.x + this.vals[2] * v.y;
        v.x = v0;
        v.y = v1;
        return v;
    }

    public void setPosition(Vector2 pos) {
        this.vals[0] = pos.x;
        this.vals[1] = pos.y;
    }

    public void setRotation(float angle) {
        float v0 = ((float)Math.cos(((double)angle)));
        float v1 = ((float)Math.sin(((double)angle)));
        this.vals[2] = v0;
        this.vals[3] = v1;
    }
}

