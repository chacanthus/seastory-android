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

public class ScalingViewport extends Viewport {
    private Scaling scaling;

    public ScalingViewport(Scaling scaling, float worldWidth, float worldHeight, Camera camera) {
        super();
        this.scaling = scaling;
        this.setWorldSize(worldWidth, worldHeight);
        this.setCamera(camera);
    }

    public ScalingViewport(Scaling scaling, float worldWidth, float worldHeight) {
        this(scaling, worldWidth, worldHeight, new OrthographicCamera());
    }

    public Scaling getScaling() {
        return this.scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }

    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        Vector2 v0 = this.scaling.apply(this.getWorldWidth(), this.getWorldHeight(), ((float)screenWidth), ((float)screenHeight));
        int v2 = Math.round(v0.x);
        int v1 = Math.round(v0.y);
        this.setScreenBounds((screenWidth - v2) / 2, (screenHeight - v1) / 2, v2, v1);
        this.apply(centerCamera);
    }
}

