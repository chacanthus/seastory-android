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

import com.badlogic.gdx.utils.ObjectMap;
import java.util.Iterator;

public class MapProperties {
    private ObjectMap properties;

    public MapProperties() {
        super();
        this.properties = new ObjectMap();
    }

    public void clear() {
        this.properties.clear();
    }

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public Object get(String key) {
        return this.properties.get(key);
    }

    public Object get(String key, Class arg3) {
        return this.get(key);
    }

    public Object get(String key, Object arg3, Class arg4) {
        Object v0 = this.get(key);
        if(v0 != null) {
            arg3 = v0;
        }

        return arg3;
    }

    public Iterator getKeys() {
        return this.properties.keys();
    }

    public Iterator getValues() {
        return this.properties.values();
    }

    public void put(String key, Object value) {
        this.properties.put(key, value);
    }

    public void putAll(MapProperties properties) {
        this.properties.putAll(properties.properties);
    }

    public void remove(String key) {
        this.properties.remove(key);
    }
}

