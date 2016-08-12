// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.lib;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Nine {
    private float fBottom;
    private float fLeft;
    private float fMiddleHeight;
    private float fMiddleWidth;
    private float fRight;
    private float fTop;
    private float height;
    private TextureRegion[] patches;
    private float width;
    public float x;
    public float y;

    public Nine(TextureRegion region, int left, int right, int top, int bottom, int width, int height) {
        super();
        this.fLeft = 0f;
        this.fRight = 0f;
        this.fTop = 0f;
        this.fBottom = 0f;
        this.fMiddleWidth = 0f;
        this.fMiddleHeight = 0f;
        this.patches = new TextureRegion[9];
        this.x = 0f;
        this.y = 0f;
        this.width = 0f;
        this.height = 0f;
        this.fLeft = ((float)left);
        this.fRight = ((float)right);
        this.fTop = ((float)top);
        this.fBottom = ((float)bottom);
        this.width = ((float)width);
        this.height = ((float)height);
        int v8 = region.getRegionWidth() - left - right;
        int v7 = region.getRegionHeight() - top - bottom;
        this.fMiddleWidth = ((float)(width - left - right));
        this.fMiddleHeight = ((float)(height - top - bottom));
        if(top > 0) {
            if(left > 0) {
                this.patches[0] = new TextureRegion(region, 0, 0, left, top);
            }

            if(v8 > 0) {
                this.patches[1] = new TextureRegion(region, left, 0, v8, top);
            }

            if(right <= 0) {
                goto label_84;
            }

            this.patches[2] = new TextureRegion(region, left + v8, 0, right, top);
        }

    label_84:
        if(v7 > 0) {
            if(left > 0) {
                this.patches[3] = new TextureRegion(region, 0, top, left, v7);
            }

            if(v8 > 0) {
                this.patches[4] = new TextureRegion(region, left, top, v8, v7);
            }

            if(right <= 0) {
                goto label_115;
            }

            this.patches[5] = new TextureRegion(region, left + v8, top, right, v7);
        }

    label_115:
        if(bottom > 0) {
            if(left > 0) {
                this.patches[6] = new TextureRegion(region, 0, top + v7, left, bottom);
            }

            if(v8 > 0) {
                this.patches[7] = new TextureRegion(region, left, top + v7, v8, bottom);
            }

            if(right <= 0) {
                return;
            }

            this.patches[8] = new TextureRegion(region, left + v8, top + v7, right, bottom);
        }
    }

    public void draw(SpriteBatch batch) {
        int v9 = 4;
        int v8 = 3;
        int v7 = 2;
        if(this.patches[0] != null) {
            batch.draw(this.patches[0], Util.getLeft(this.x), Util.getTop(this.y, Util.getHeight(this.fTop)), Util.getWidth(this.fLeft), Util.getHeight(this.fTop));
        }

        if(this.patches[1] != null) {
            batch.draw(this.patches[1], Util.getLeft(this.x + this.fLeft), Util.getTop(this.y, Util.getHeight(this.fTop)), Util.getWidth(this.fMiddleWidth), Util.getHeight(this.fTop));
        }

        if(this.patches[v7] != null) {
            batch.draw(this.patches[v7], Util.getLeft(this.x + this.fLeft + this.fMiddleWidth), Util.getTop(this.y, Util.getHeight(this.fTop)), Util.getWidth(this.fRight), Util.getHeight(this.fTop));
        }

        if(this.patches[v8] != null) {
            batch.draw(this.patches[v8], Util.getLeft(this.x), Util.getTop(this.y + this.fTop, Util.getHeight(this.fMiddleHeight)), Util.getWidth(this.fLeft), Util.getHeight(this.fMiddleHeight));
        }

        if(this.patches[v9] != null) {
            batch.draw(this.patches[v9], Util.getLeft(this.x + this.fLeft), Util.getTop(this.y + this.fTop, Util.getHeight(this.fMiddleHeight)), Util.getWidth(this.fMiddleWidth), Util.getHeight(this.fMiddleHeight));
        }

        if(this.patches[5] != null) {
            batch.draw(this.patches[5], Util.getLeft(this.x + this.fLeft + this.fMiddleWidth), Util.getTop(this.y + this.fTop, Util.getHeight(this.fMiddleHeight)), Util.getWidth(this.fRight), Util.getHeight(this.fMiddleHeight));
        }

        if(this.patches[6] != null) {
            batch.draw(this.patches[6], Util.getLeft(this.x), Util.getTop(this.y + this.fTop + this.fMiddleHeight, Util.getHeight(this.fBottom)), Util.getWidth(this.fLeft), Util.getHeight(this.fBottom));
        }

        if(this.patches[7] != null) {
            batch.draw(this.patches[7], Util.getLeft(this.x + this.fLeft), Util.getTop(this.y + this.fTop + this.fMiddleHeight, Util.getHeight(this.fBottom)), Util.getWidth(this.fMiddleWidth), Util.getHeight(this.fBottom));
        }

        if(this.patches[8] != null) {
            batch.draw(this.patches[8], Util.getLeft(this.x + this.fLeft + this.fMiddleWidth), Util.getTop(this.y + this.fTop + this.fMiddleHeight, Util.getHeight(this.fBottom)), Util.getWidth(this.fRight), Util.getHeight(this.fBottom));
        }
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

    public void setPosition(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }
}

