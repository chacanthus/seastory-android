// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class OrthographicCamera extends Camera {
    private final Vector3 tmp;

    public OrthographicCamera() {
        super();
        this.zoom = 1f;
        this.tmp = new Vector3();
        this.near = 0f;
    }

    public OrthographicCamera(float viewportWidth, float viewportHeight) {
        super();
        this.zoom = 1f;
        this.tmp = new Vector3();
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.near = 0f;
        this.update();
    }

    public void rotate(float angle) {
        this.rotate(this.direction, angle);
    }

    public void setToOrtho(boolean yDown) {
        this.setToOrtho(yDown, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()));
    }

    public void setToOrtho(boolean yDown, float viewportWidth, float viewportHeight) {
        float v4 = 2f;
        float v2 = 1f;
        float v1 = -1f;
        if(yDown) {
            this.up.set(0f, v1, 0f);
            this.direction.set(0f, 0f, v2);
        }
        else {
            this.up.set(0f, v2, 0f);
            this.direction.set(0f, 0f, v1);
        }

        this.position.set(this.zoom * viewportWidth / v4, this.zoom * viewportHeight / v4, 0f);
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.update();
    }

    public void translate(float x, float y) {
        this.translate(x, y, 0f);
    }

    public void translate(Vector2 vec) {
        this.translate(vec.x, vec.y, 0f);
    }

    public void update() {
        this.update(true);
    }

    public void update(boolean updateFrustum) {
        this.projection.setToOrtho(this.zoom * -this.viewportWidth / 2f, this.zoom * (this.viewportWidth / 2f), this.zoom * -(this.viewportHeight / 2f), this.zoom * this.viewportHeight / 2f, this.near, this.far);
        this.view.setToLookAt(this.position, this.tmp.set(this.position).add(this.direction), this.up);
        this.combined.set(this.projection);
        Matrix4.mul(this.combined.val, this.view.val);
        if(updateFrustum) {
            this.invProjectionView.set(this.combined);
            Matrix4.inv(this.invProjectionView.val);
            this.frustum.update(this.invProjectionView);
        }
    }
}

