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

public class OrthogonalTiledMapRenderer extends BatchTiledMapRenderer {
    public OrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float v22;
        Color v5 = this.batch.getColor();
        float v10 = Color.toFloatBits(v5.r, v5.g, v5.b, v5.a * layer.getOpacity());
        int v16 = layer.getWidth();
        int v13 = layer.getHeight();
        float v15 = layer.getTileWidth() * this.unitScale;
        float v14 = layer.getTileHeight() * this.unitScale;
        int v8 = Math.max(0, ((int)(this.viewBounds.x / v15)));
        int v9 = Math.min(v16, ((int)((this.viewBounds.x + this.viewBounds.width + v15) / v15)));
        int v20 = Math.max(0, ((int)(this.viewBounds.y / v14)));
        int v21 = Math.min(v13, ((int)((this.viewBounds.y + this.viewBounds.height + v14) / v14)));
        float v35 = (((float)v21)) * v14;
        float v34 = (((float)v8)) * v15;
        float[] v30 = this.vertices;
        int v19;
        for(v19 = v21; v19 >= v20; --v19) {
            float v31 = v34;
            int v7;
            for(v7 = v8; v7 < v9; ++v7) {
                Cell v6 = layer.getCell(v7, v19);
                if(v6 == null) {
                    v31 += v15;
                }
                else {
                    TiledMapTile v25 = v6.getTile();
                    if(v25 != null) {
                        boolean v11 = v6.getFlipHorizontally();
                        boolean v12 = v6.getFlipVertically();
                        int v18 = v6.getRotation();
                        TextureRegion v17 = v25.getTextureRegion();
                        float v32 = v31 + v25.getOffsetX() * this.unitScale;
                        float v36 = v35 + v25.getOffsetY() * this.unitScale;
                        float v33 = v32 + (((float)v17.getRegionWidth())) * this.unitScale;
                        float v37 = v36 + (((float)v17.getRegionHeight())) * this.unitScale;
                        float v26 = v17.getU();
                        float v28 = v17.getV2();
                        float v27 = v17.getU2();
                        float v29 = v17.getV();
                        v30[0] = v32;
                        v30[1] = v36;
                        v30[2] = v10;
                        v30[3] = v26;
                        v30[4] = v28;
                        v30[5] = v32;
                        v30[6] = v37;
                        v30[7] = v10;
                        v30[8] = v26;
                        v30[9] = v29;
                        v30[10] = v33;
                        v30[11] = v37;
                        v30[12] = v10;
                        v30[13] = v27;
                        v30[14] = v29;
                        v30[15] = v33;
                        v30[16] = v36;
                        v30[17] = v10;
                        v30[18] = v27;
                        v30[19] = v28;
                        if(v11) {
                            v22 = v30[3];
                            v30[3] = v30[13];
                            v30[13] = v22;
                            v22 = v30[8];
                            v30[8] = v30[18];
                            v30[18] = v22;
                        }

                        if(v12) {
                            v22 = v30[4];
                            v30[4] = v30[14];
                            v30[14] = v22;
                            v22 = v30[9];
                            v30[9] = v30[19];
                            v30[19] = v22;
                        }

                        if(v18 != 0) {
                            switch(v18) {
                                case 1: {
                                    goto label_246;
                                }
                                case 2: {
                                    goto label_279;
                                }
                                case 3: {
                                    goto label_312;
                                }
                            }

                            goto label_232;
                        label_246:
                            float v24 = v30[4];
                            v30[4] = v30[9];
                            v30[9] = v30[14];
                            v30[14] = v30[19];
                            v30[19] = v24;
                            float v23 = v30[3];
                            v30[3] = v30[8];
                            v30[8] = v30[13];
                            v30[13] = v30[18];
                            v30[18] = v23;
                            goto label_232;
                        label_279:
                            v23 = v30[3];
                            v30[3] = v30[13];
                            v30[13] = v23;
                            v23 = v30[8];
                            v30[8] = v30[18];
                            v30[18] = v23;
                            v24 = v30[4];
                            v30[4] = v30[14];
                            v30[14] = v24;
                            v24 = v30[9];
                            v30[9] = v30[19];
                            v30[19] = v24;
                            goto label_232;
                        label_312:
                            v24 = v30[4];
                            v30[4] = v30[19];
                            v30[19] = v30[14];
                            v30[14] = v30[9];
                            v30[9] = v24;
                            v23 = v30[3];
                            v30[3] = v30[18];
                            v30[18] = v30[13];
                            v30[13] = v30[8];
                            v30[8] = v23;
                        }

                    label_232:
                        this.batch.draw(v17.getTexture(), v30, 0, 20);
                    }

                    v31 += v15;
                }
            }

            v35 -= v14;
        }
    }
}

