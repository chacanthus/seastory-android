// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;

public class TextureMapObject extends MapObject {
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private TextureRegion textureRegion;
    private float x;
    private float y;

    public TextureMapObject() {
        this(null);
    }

    public TextureMapObject(TextureRegion textureRegion) {
        super();
        this.x = 0f;
        this.y = 0f;
        this.originX = 0f;
        this.originY = 0f;
        this.scaleX = 1f;
        this.scaleY = 1f;
        this.rotation = 0f;
        this.textureRegion = null;
        this.textureRegion = textureRegion;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setOriginX(float x) {
        this.originX = x;
    }

    public void setOriginY(float y) {
        this.originY = y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setScaleX(float x) {
        this.scaleX = x;
    }

    public void setScaleY(float y) {
        this.scaleY = y;
    }

    public void setTextureRegion(TextureRegion region) {
        this.textureRegion = region;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

