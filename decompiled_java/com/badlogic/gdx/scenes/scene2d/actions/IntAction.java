// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.actions;

public class IntAction extends TemporalAction {
    private int end;
    private int start;
    private int value;

    public IntAction() {
        super();
        this.start = 0;
        this.end = 1;
    }

    public IntAction(int start, int end) {
        super();
        this.start = start;
        this.end = end;
    }

    protected void begin() {
        this.value = this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getStart() {
        return this.start;
    }

    public int getValue() {
        return this.value;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected void update(float percent) {
        this.value = ((int)((((float)this.start)) + (((float)(this.end - this.start))) * percent));
    }
}

