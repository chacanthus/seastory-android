// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

public abstract interface Vector {
    public abstract Vector add(Vector arg0);

    public abstract Vector clamp(float arg0, float arg1);

    public abstract Vector cpy();

    public abstract float dot(Vector arg0);

    public abstract float dst(Vector arg0);

    public abstract float dst2(Vector arg0);

    public abstract boolean epsilonEquals(Vector arg0, float arg1);

    public abstract boolean hasOppositeDirection(Vector arg0);

    public abstract boolean hasSameDirection(Vector arg0);

    public abstract Vector interpolate(Vector arg0, float arg1, Interpolation arg2);

    public abstract boolean isCollinear(Vector arg0);

    public abstract boolean isCollinear(Vector arg0, float arg1);

    public abstract boolean isCollinearOpposite(Vector arg0);

    public abstract boolean isCollinearOpposite(Vector arg0, float arg1);

    public abstract boolean isOnLine(Vector arg0);

    public abstract boolean isOnLine(Vector arg0, float arg1);

    public abstract boolean isPerpendicular(Vector arg0);

    public abstract boolean isPerpendicular(Vector arg0, float arg1);

    public abstract boolean isUnit();

    public abstract boolean isUnit(float arg0);

    public abstract boolean isZero();

    public abstract boolean isZero(float arg0);

    public abstract float len();

    public abstract float len2();

    public abstract Vector lerp(Vector arg0, float arg1);

    public abstract Vector limit(float arg0);

    public abstract Vector limit2(float arg0);

    public abstract Vector mulAdd(Vector arg0, float arg1);

    public abstract Vector mulAdd(Vector arg0, Vector arg1);

    public abstract Vector nor();

    public abstract Vector scl(float arg0);

    public abstract Vector scl(Vector arg0);

    public abstract Vector set(Vector arg0);

    public abstract Vector setLength(float arg0);

    public abstract Vector setLength2(float arg0);

    public abstract Vector setZero();

    public abstract Vector sub(Vector arg0);
}

