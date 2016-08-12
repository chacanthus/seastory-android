// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d;

public enum Touchable {
    public static final enum Touchable childrenOnly;
    public static final enum Touchable disabled;

    static  {
        Touchable.enabled = new Touchable("enabled", 0);
        Touchable.disabled = new Touchable("disabled", 1);
        Touchable.childrenOnly = new Touchable("childrenOnly", 2);
        Touchable[] v0 = new Touchable[3];
        v0[0] = Touchable.enabled;
        v0[1] = Touchable.disabled;
        v0[2] = Touchable.childrenOnly;
        Touchable.$VALUES = v0;
    }

    private Touchable(String arg1, int arg2) {
        super(arg1, arg2);
    }

    public static Touchable valueOf(String name) {
        return Enum.valueOf(Touchable.class, name);
    }

    public static Touchable[] values() {
        return Touchable.$VALUES.clone();
    }
}

