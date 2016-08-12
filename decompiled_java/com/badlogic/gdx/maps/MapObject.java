// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps;

import com.badlogic.gdx.graphics.Color;

public class MapObject {
    private Color color;
    private String name;
    private float opacity;
    private MapProperties properties;
    private boolean visible;

    public MapObject() {
        super();
        this.name = "";
        this.opacity = 1f;
        this.visible = true;
        this.properties = new MapProperties();
        this.color = Color.WHITE.cpy();
    }

    public Color getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public MapProperties getProperties() {
        return this.properties;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

