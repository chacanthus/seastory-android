// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTile$BlendMode;

public class StaticTiledMapTile implements TiledMapTile {
    private BlendMode blendMode;
    private int id;
    private float offsetX;
    private float offsetY;
    private MapProperties properties;
    private TextureRegion textureRegion;

    public StaticTiledMapTile(TextureRegion textureRegion) {
        super();
        this.blendMode = BlendMode.ALPHA;
        this.textureRegion = textureRegion;
    }

    public StaticTiledMapTile(StaticTiledMapTile copy) {
        super();
        this.blendMode = BlendMode.ALPHA;
        if(copy.properties != null) {
            this.getProperties().putAll(copy.properties);
        }

        this.textureRegion = copy.textureRegion;
        this.id = copy.id;
    }

    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    public int getId() {
        return this.id;
    }

    public float getOffsetX() {
        return this.offsetX;
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public MapProperties getProperties() {
        if(this.properties == null) {
            this.properties = new MapProperties();
        }

        return this.properties;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}

