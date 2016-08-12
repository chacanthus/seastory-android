// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

public class ExtendViewport extends Viewport {
    private float maxWorldHeight;
    private float maxWorldWidth;
    private float minWorldHeight;
    private float minWorldWidth;

    public ExtendViewport(float minWorldWidth, float minWorldHeight) {
        this(minWorldWidth, minWorldHeight, 0f, 0f, new OrthographicCamera());
    }

    public ExtendViewport(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight, Camera camera) {
        super();
        this.minWorldWidth = minWorldWidth;
        this.minWorldHeight = minWorldHeight;
        this.maxWorldWidth = maxWorldWidth;
        this.maxWorldHeight = maxWorldHeight;
        this.setCamera(camera);
    }

    public ExtendViewport(float minWorldWidth, float minWorldHeight, float maxWorldWidth, float maxWorldHeight) {
        this(minWorldWidth, minWorldHeight, maxWorldWidth, maxWorldHeight, new OrthographicCamera());
    }

    public ExtendViewport(float minWorldWidth, float minWorldHeight, Camera camera) {
        this(minWorldWidth, minWorldHeight, 0f, 0f, camera);
    }

    public float getMaxWorldHeight() {
        return this.maxWorldHeight;
    }

    public float getMaxWorldWidth() {
        return this.maxWorldWidth;
    }

    public float getMinWorldHeight() {
        return this.minWorldHeight;
    }

    public float getMinWorldWidth() {
        return this.minWorldWidth;
    }

    public void setMaxWorldHeight(float maxWorldHeight) {
        this.maxWorldHeight = maxWorldHeight;
    }

    public void setMaxWorldWidth(float maxWorldWidth) {
        this.maxWorldWidth = maxWorldWidth;
    }

    public void setMinWorldHeight(float minWorldHeight) {
        this.minWorldHeight = minWorldHeight;
    }

    public void setMinWorldWidth(float minWorldWidth) {
        this.minWorldWidth = minWorldWidth;
    }

    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        float v0;
        float v2;
        float v7 = this.minWorldWidth;
        float v6 = this.minWorldHeight;
        Vector2 v1 = Scaling.fit.apply(v7, v6, ((float)screenWidth), ((float)screenHeight));
        int v5 = Math.round(v1.x);
        int v4 = Math.round(v1.y);
        if(v5 < screenWidth) {
            v2 = (((float)v4)) / v6;
            v0 = (((float)(screenWidth - v5))) * (v6 / (((float)v4)));
            if(this.maxWorldWidth > 0f) {
                v0 = Math.min(v0, this.maxWorldWidth - this.minWorldWidth);
            }

            v7 += v0;
            v5 += Math.round(v0 * v2);
        }
        else {
            if(v4 >= screenHeight) {
                goto label_29;
            }

            v2 = (((float)v5)) / v7;
            v0 = (((float)(screenHeight - v4))) * (v7 / (((float)v5)));
            if(this.maxWorldHeight > 0f) {
                v0 = Math.min(v0, this.maxWorldHeight - this.minWorldHeight);
            }

            v6 += v0;
            v4 += Math.round(v0 * v2);
        }

    label_29:
        this.setWorldSize(v7, v6);
        this.setScreenBounds((screenWidth - v5) / 2, (screenHeight - v4) / 2, v5, v4);
        this.apply(centerCamera);
    }
}

