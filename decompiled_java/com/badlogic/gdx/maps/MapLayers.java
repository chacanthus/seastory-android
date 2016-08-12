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

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import java.util.Iterator;

public class MapLayers implements Iterable {
    private Array layers;

    public MapLayers() {
        super();
        this.layers = new Array();
    }

    public void add(MapLayer layer) {
        this.layers.add(layer);
    }

    public MapLayer get(int index) {
        return this.layers.get(index);
    }

    public MapLayer get(String name) {
        int v0 = 0;
        int v2 = this.layers.size;
        while(true) {
            if(v0 < v2) {
                Object v1 = this.layers.get(v0);
                if(!name.equals(((MapLayer)v1).getName())) {
                    ++v0;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_9;
        }

        MapLayer v1_1 = null;
    label_9:
        return v1_1;
    }

    public Array getByType(Class arg2) {
        return this.getByType(arg2, new Array());
    }

    public Array getByType(Class arg5, Array arg6) {
        arg6.clear();
        int v0 = 0;
        int v2 = this.layers.size;
        while(v0 < v2) {
            Object v1 = this.layers.get(v0);
            if(ClassReflection.isInstance(arg5, v1)) {
                arg6.add(v1);
            }

            ++v0;
        }

        return arg6;
    }

    public int getCount() {
        return this.layers.size;
    }

    public Iterator iterator() {
        return this.layers.iterator();
    }

    public void remove(int index) {
        this.layers.removeIndex(index);
    }

    public void remove(MapLayer layer) {
        this.layers.removeValue(layer, true);
    }
}

