// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class BaseDrawable implements Drawable {
    private float bottomHeight;
    private float leftWidth;
    private float minHeight;
    private float minWidth;
    private String name;
    private float rightWidth;
    private float topHeight;

    public BaseDrawable() {
        super();
    }

    public BaseDrawable(Drawable drawable) {
        super();
        if((drawable instanceof BaseDrawable)) {
            this.name = drawable.getName();
        }

        this.leftWidth = drawable.getLeftWidth();
        this.rightWidth = drawable.getRightWidth();
        this.topHeight = drawable.getTopHeight();
        this.bottomHeight = drawable.getBottomHeight();
        this.minWidth = drawable.getMinWidth();
        this.minHeight = drawable.getMinHeight();
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
    }

    public float getBottomHeight() {
        return this.bottomHeight;
    }

    public float getLeftWidth() {
        return this.leftWidth;
    }

    public float getMinHeight() {
        return this.minHeight;
    }

    public float getMinWidth() {
        return this.minWidth;
    }

    public String getName() {
        return this.name;
    }

    public float getRightWidth() {
        return this.rightWidth;
    }

    public float getTopHeight() {
        return this.topHeight;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }

    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }

    public String toString() {
        String v0;
        if(this.name == null) {
            v0 = ClassReflection.getSimpleName(this.getClass());
        }
        else {
            v0 = this.name;
        }

        return v0;
    }
}

