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

public class SizeToAction extends TemporalAction {
    private float endHeight;
    private float endWidth;
    private float startHeight;
    private float startWidth;

    public SizeToAction() {
        super();
    }

    protected void begin() {
        this.startWidth = this.target.getWidth();
        this.startHeight = this.target.getHeight();
    }

    public float getHeight() {
        return this.endHeight;
    }

    public float getWidth() {
        return this.endWidth;
    }

    public void setHeight(float height) {
        this.endHeight = height;
    }

    public void setSize(float width, float height) {
        this.endWidth = width;
        this.endHeight = height;
    }

    public void setWidth(float width) {
        this.endWidth = width;
    }

    protected void update(float percent) {
        this.target.setSize(this.startWidth + (this.endWidth - this.startWidth) * percent, this.startHeight + (this.endHeight - this.startHeight) * percent);
    }
}

