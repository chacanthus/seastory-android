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
import com.badlogic.gdx.utils.SnapshotArray;

public class HorizontalGroup extends WidgetGroup {
    private int align;
    private float fill;
    private float padBottom;
    private float padLeft;
    private float padRight;
    private float padTop;
    private float prefHeight;
    private float prefWidth;
    private boolean reverse;
    private boolean round;
    private boolean sizeInvalid;
    private float spacing;

    public HorizontalGroup() {
        super();
        this.sizeInvalid = true;
        this.round = true;
        this.setTouchable(Touchable.childrenOnly);
    }

    public HorizontalGroup align(int align) {
        this.align = align;
        return this;
    }

    public HorizontalGroup bottom() {
        this.align |= 4;
        this.align &= -3;
        return this;
    }

    public HorizontalGroup center() {
        this.align = 1;
        return this;
    }

    private void computeSize() {
        this.sizeInvalid = false;
        SnapshotArray v1 = this.getChildren();
        int v4 = v1.size;
        this.prefWidth = this.padLeft + this.padRight + this.spacing * (((float)(v4 - 1)));
        this.prefHeight = 0f;
        int v2;
        for(v2 = 0; v2 < v4; ++v2) {
            Object v0 = v1.get(v2);
            if((v0 instanceof Layout)) {
                this.prefWidth += v0.getPrefWidth();
                this.prefHeight = Math.max(this.prefHeight, v0.getPrefHeight());
            }
            else {
                this.prefWidth += ((Actor)v0).getWidth();
                this.prefHeight = Math.max(this.prefHeight, ((Actor)v0).getHeight());
            }
        }

        this.prefHeight += this.padTop + this.padBottom;
        if(this.round) {
            this.prefWidth = ((float)Math.round(this.prefWidth));
            this.prefHeight = ((float)Math.round(this.prefHeight));
        }
    }

    public HorizontalGroup fill() {
        this.fill = 1f;
        return this;
    }

    public HorizontalGroup fill(float fill) {
        this.fill = fill;
        return this;
    }

    public int getAlign() {
        return this.align;
    }

    public float getFill() {
        return this.fill;
    }

    public float getPadBottom() {
        return this.padBottom;
    }

    public float getPadLeft() {
        return this.padLeft;
    }

    public float getPadRight() {
        return this.padRight;
    }

    public float getPadTop() {
        return this.padTop;
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

    public boolean getReverse() {
        return this.reverse;
    }

    public float getSpace() {
        return this.spacing;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    public void layout() {
        float v17;
        float v8;
        float v18;
        float v16 = this.spacing;
        float v13 = this.padBottom;
        int v4 = this.align;
        boolean v14 = this.reverse;
        boolean v15 = this.round;
        float v7 = this.getHeight() - this.padTop - v13;
        if(!v14) {
            v18 = this.padLeft;
        }
        else {
            v18 = this.getWidth() - this.padRight + v16;
        }

        SnapshotArray v6 = this.getChildren();
        int v9 = 0;
        int v12 = v6.size;
        while(v9 < v12) {
            Object v5 = v6.get(v9);
            if((v5 instanceof Layout)) {
                Object v10 = v5;
                if(this.fill > 0f) {
                    v8 = v7 * this.fill;
                }
                else {
                    v8 = Math.min(((Layout)v10).getPrefHeight(), v7);
                }

                v8 = Math.max(v8, ((Layout)v10).getMinHeight());
                float v11 = ((Layout)v10).getMaxHeight();
                if(v11 > 0f && v8 > v11) {
                    v8 = v11;
                }

                v17 = ((Layout)v10).getPrefWidth();
            }
            else {
                v17 = ((Actor)v5).getWidth();
                v8 = ((Actor)v5).getHeight();
                if(this.fill <= 0f) {
                    goto label_48;
                }

                v8 *= this.fill;
            }

        label_48:
            float v19 = v13;
            if((v4 & 2) != 0) {
                v19 += v7 - v8;
            }
            else if((v4 & 4) == 0) {
                v19 += (v7 - v8) / 2f;
            }

            if(v14) {
                v18 -= v17 + v16;
            }

            if(v15) {
                ((Actor)v5).setBounds(((float)Math.round(v18)), ((float)Math.round(v19)), ((float)Math.round(v17)), ((float)Math.round(v8)));
            }
            else {
                ((Actor)v5).setBounds(v18, v19, v17, v8);
            }

            if(!v14) {
                v18 += v17 + v16;
            }

            ++v9;
        }
    }

    public HorizontalGroup pad(float pad) {
        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        return this;
    }

    public HorizontalGroup pad(float top, float left, float bottom, float right) {
        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        return this;
    }

    public HorizontalGroup padBottom(float padBottom) {
        this.padBottom = padBottom;
        return this;
    }

    public HorizontalGroup padLeft(float padLeft) {
        this.padLeft = padLeft;
        return this;
    }

    public HorizontalGroup padRight(float padRight) {
        this.padRight = padRight;
        return this;
    }

    public HorizontalGroup padTop(float padTop) {
        this.padTop = padTop;
        return this;
    }

    public HorizontalGroup reverse() {
        this.reverse(true);
        return this;
    }

    public HorizontalGroup reverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public HorizontalGroup space(float spacing) {
        this.spacing = spacing;
        return this;
    }

    public HorizontalGroup top() {
        this.align |= 2;
        this.align &= -5;
        return this;
    }
}

