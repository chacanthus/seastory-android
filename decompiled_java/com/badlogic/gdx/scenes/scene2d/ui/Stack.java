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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

public class Stack extends WidgetGroup {
    private float maxHeight;
    private float maxWidth;
    private float minHeight;
    private float minWidth;
    private float prefHeight;
    private float prefWidth;
    private boolean sizeInvalid;

    public Stack() {
        super();
        this.sizeInvalid = true;
        this.setTransform(false);
        this.setWidth(150f);
        this.setHeight(150f);
        this.setTouchable(Touchable.childrenOnly);
    }

    public void add(Actor actor) {
        this.addActor(actor);
    }

    private void computeSize() {
        float v1;
        float v2;
        this.sizeInvalid = false;
        this.prefWidth = 0f;
        this.prefHeight = 0f;
        this.minWidth = 0f;
        this.minHeight = 0f;
        this.maxWidth = 0f;
        this.maxHeight = 0f;
        SnapshotArray v3 = this.getChildren();
        int v4 = 0;
        int v6 = v3.size;
        while(v4 < v6) {
            Object v0 = v3.get(v4);
            if((v0 instanceof Layout)) {
                this.prefWidth = Math.max(this.prefWidth, v0.getPrefWidth());
                this.prefHeight = Math.max(this.prefHeight, v0.getPrefHeight());
                this.minWidth = Math.max(this.minWidth, v0.getMinWidth());
                this.minHeight = Math.max(this.minHeight, v0.getMinHeight());
                v2 = v0.getMaxWidth();
                v1 = v0.getMaxHeight();
            }
            else {
                this.prefWidth = Math.max(this.prefWidth, ((Actor)v0).getWidth());
                this.prefHeight = Math.max(this.prefHeight, ((Actor)v0).getHeight());
                this.minWidth = Math.max(this.minWidth, ((Actor)v0).getWidth());
                this.minHeight = Math.max(this.minHeight, ((Actor)v0).getHeight());
                v2 = 0f;
                v1 = 0f;
            }

            if(v2 > 0f) {
                if(this.maxWidth != 0f) {
                    v2 = Math.min(this.maxWidth, v2);
                }

                this.maxWidth = v2;
            }

            if(v1 > 0f) {
                if(this.maxHeight != 0f) {
                    v1 = Math.min(this.maxHeight, v1);
                }

                this.maxHeight = v1;
            }

            ++v4;
        }
    }

    public float getMaxHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.maxHeight;
    }

    public float getMaxWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.maxWidth;
    }

    public float getMinHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.minHeight;
    }

    public float getMinWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.minWidth;
    }

    public float getPrefHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.prefHeight;
    }

    public float getPrefWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.prefWidth;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    public void layout() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        float v5 = this.getWidth();
        float v2 = this.getHeight();
        SnapshotArray v1 = this.getChildren();
        int v3 = 0;
        int v4 = ((Array)v1).size;
        while(v3 < v4) {
            Object v0 = ((Array)v1).get(v3);
            ((Actor)v0).setBounds(0f, 0f, v5, v2);
            if((v0 instanceof Layout)) {
                ((Layout)v0).validate();
            }

            ++v3;
        }
    }
}

