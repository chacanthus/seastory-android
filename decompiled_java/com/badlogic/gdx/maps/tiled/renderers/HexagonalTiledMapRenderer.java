// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer$Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;

public class HexagonalTiledMapRenderer extends BatchTiledMapRenderer {
    public HexagonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public HexagonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public HexagonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public HexagonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float v26;
        float v41;
        Color v5 = this.batch.getColor();
        float v10 = Color.toFloatBits(v5.r, v5.g, v5.b, v5.a * layer.getOpacity());
        int v20 = layer.getWidth();
        int v13 = layer.getHeight();
        float v17 = layer.getTileWidth() * this.unitScale;
        float v14 = layer.getTileHeight() * this.unitScale;
        float v19 = v17 * 0.75f;
        float v16 = v14 * 0.5f;
        float v15 = v14 * 1.5f;
        int v8 = Math.max(0, ((int)((this.viewBounds.x - v17 * 0.25f) / v19)));
        int v9 = Math.min(v20, ((int)((this.viewBounds.x + this.viewBounds.width + v19) / v19)));
        int v24 = Math.max(0, ((int)(this.viewBounds.y / v15)));
        int v25 = Math.min(v13, ((int)((this.viewBounds.y + this.viewBounds.height + v15) / v14)));
        float[] v34 = this.vertices;
        int v23;
        for(v23 = v24; v23 < v25; ++v23) {
            int v7;
            for(v7 = v8; v7 < v9; ++v7) {
                float v35 = v19 * (((float)v7));
                if(v7 % 2 == 1) {
                    v41 = 0f;
                }
                else {
                    v41 = v16;
                }

                float v38 = v41 + (((float)v23)) * v14;
                Cell v6 = layer.getCell(v7, v23);
                if(v6 == null) {
                }
                else {
                    TiledMapTile v29 = v6.getTile();
                    if(v29 != null && !(v29 instanceof AnimatedTiledMapTile)) {
                        boolean v11 = v6.getFlipHorizontally();
                        boolean v12 = v6.getFlipVertically();
                        int v22 = v6.getRotation();
                        TextureRegion v21 = v29.getTextureRegion();
                        float v36 = v35 + v29.getOffsetX() * this.unitScale;
                        float v39 = v38 + v29.getOffsetY() * this.unitScale;
                        float v37 = v36 + (((float)v21.getRegionWidth())) * this.unitScale;
                        float v40 = v39 + (((float)v21.getRegionHeight())) * this.unitScale;
                        float v30 = v21.getU();
                        float v32 = v21.getV2();
                        float v31 = v21.getU2();
                        float v33 = v21.getV();
                        v34[0] = v36;
                        v34[1] = v39;
                        v34[2] = v10;
                        v34[3] = v30;
                        v34[4] = v32;
                        v34[5] = v36;
                        v34[6] = v40;
                        v34[7] = v10;
                        v34[8] = v30;
                        v34[9] = v33;
                        v34[10] = v37;
                        v34[11] = v40;
                        v34[12] = v10;
                        v34[13] = v31;
                        v34[14] = v33;
                        v34[15] = v37;
                        v34[16] = v39;
                        v34[17] = v10;
                        v34[18] = v31;
                        v34[19] = v32;
                        if(v11) {
                            v26 = v34[3];
                            v34[3] = v34[13];
                            v34[13] = v26;
                            v26 = v34[8];
                            v34[8] = v34[18];
                            v34[18] = v26;
                        }

                        if(v12) {
                            v26 = v34[4];
                            v34[4] = v34[14];
                            v34[14] = v26;
                            v26 = v34[9];
                            v34[9] = v34[19];
                            v34[19] = v26;
                        }

                        if(v22 != 2) {
                            goto label_289;
                        }

                        float v27 = v34[3];
                        v34[3] = v34[13];
                        v34[13] = v27;
                        v27 = v34[8];
                        v34[8] = v34[18];
                        v34[18] = v27;
                        float v28 = v34[4];
                        v34[4] = v34[14];
                        v34[14] = v28;
                        v28 = v34[9];
                        v34[9] = v34[19];
                        v34[19] = v28;
                        break;
                    label_289:
                        this.batch.draw(v21.getTexture(), v34, 0, 20);
                    }
                }
            }
        }
    }
}

