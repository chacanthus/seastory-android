// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public class Widget extends Actor implements Layout {
    private boolean fillParent;
    private boolean layoutEnabled;
    private boolean needsLayout;

    public Widget() {
        super();
        this.needsLayout = true;
        this.layoutEnabled = true;
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
    }

    public float getMaxHeight() {
        return 0;
    }

    public float getMaxWidth() {
        return 0;
    }

    public float getMinHeight() {
        return this.getPrefHeight();
    }

    public float getMinWidth() {
        return this.getPrefWidth();
    }

    public float getPrefHeight() {
        return 0;
    }

    public float getPrefWidth() {
        return 0;
    }

    public void invalidate() {
        this.needsLayout = true;
    }

    public void invalidateHierarchy() {
        if(this.layoutEnabled) {
            this.invalidate();
            Group v0 = this.getParent();
            if((v0 instanceof Layout)) {
                ((Layout)v0).invalidateHierarchy();
            }
        }
    }

    public void layout() {
    }

    public boolean needsLayout() {
        return this.needsLayout;
    }

    public void pack() {
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.validate();
    }

    public void setFillParent(boolean fillParent) {
        this.fillParent = fillParent;
    }

    public void setLayoutEnabled(boolean enabled) {
        this.layoutEnabled = enabled;
        if(enabled) {
            this.invalidateHierarchy();
        }
    }

    protected void sizeChanged() {
        this.invalidate();
    }

    public void validate() {
        float v1;
        float v2;
        if(this.layoutEnabled) {
            Group v0 = this.getParent();
            if((this.fillParent) && v0 != null) {
                Stage v3 = this.getStage();
                if(v3 == null || v0 != v3.getRoot()) {
                    v2 = v0.getWidth();
                    v1 = v0.getHeight();
                }
                else {
                    v2 = v3.getWidth();
                    v1 = v3.getHeight();
                }

                this.setSize(v2, v1);
            }

            if(!this.needsLayout) {
                return;
            }

            this.needsLayout = false;
            this.layout();
        }
    }
}

