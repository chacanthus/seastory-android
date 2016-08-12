// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class TiledMapTileSets implements Iterable {
    private Array tilesets;

    public TiledMapTileSets() {
        super();
        this.tilesets = new Array();
    }

    public void addTileSet(TiledMapTileSet tileset) {
        this.tilesets.add(tileset);
    }

    public TiledMapTile getTile(int id) {
        TiledMapTile v1;
        int v0 = this.tilesets.size - 1;
        while(true) {
            if(v0 >= 0) {
                v1 = this.tilesets.get(v0).getTile(id);
                if(v1 == null) {
                    --v0;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }

        v1 = null;
    label_8:
        return v1;
    }

    public TiledMapTileSet getTileSet(int index) {
        return this.tilesets.get(index);
    }

    public TiledMapTileSet getTileSet(String name) {
        Object v1;
        Iterator v0 = this.tilesets.iterator();
        do {
            if(v0.hasNext()) {
                v1 = v0.next();
                if(!name.equals(((TiledMapTileSet)v1).getName())) {
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }
        while(true);

        TiledMapTileSet v1_1 = null;
    label_8:
        return ((TiledMapTileSet)v1);
    }

    public Iterator iterator() {
        return this.tilesets.iterator();
    }

    public void removeTileSet(int index) {
        this.tilesets.removeIndex(index);
    }

    public void removeTileSet(TiledMapTileSet tileset) {
        this.tilesets.removeValue(tileset, true);
    }
}

