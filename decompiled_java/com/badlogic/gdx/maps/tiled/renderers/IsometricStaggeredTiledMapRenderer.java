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

public class IsometricStaggeredTiledMapRenderer extends BatchTiledMapRenderer {
    public IsometricStaggeredTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float v21;
        float v18;
        Color v3 = this.batch.getColor();
        float v5 = Color.toFloatBits(v3.r, v3.g, v3.b, v3.a * layer.getOpacity());
        int v13 = layer.getWidth();
        int v8 = layer.getHeight();
        float v11 = layer.getTileWidth() * this.unitScale;
        float v9 = layer.getTileHeight() * this.unitScale;
        float v12 = v11 * 0.5f;
        float v10 = v9 * 0.5f;
        int v16 = Math.max(0, ((int)((this.viewBounds.x - v12) / v11)));
        int v14 = Math.min(v13, ((int)((this.viewBounds.x + this.viewBounds.width + v11 + v12) / v11)));
        int v17 = Math.max(0, ((int)((this.viewBounds.y - v9) / v9)));
        int v32;
        for(v32 = Math.min(v8, ((int)((this.viewBounds.y + this.viewBounds.height + v9) / v10))) - 1; v32 >= v17; --v32) {
            if(v32 % 2 == 1) {
                v18 = v12;
            }
            else {
                v18 = 0f;
            }

            int v29;
            for(v29 = v14 - 1; v29 >= v16; --v29) {
                Cell v4 = layer.getCell(v29, v32);
                if(v4 != null) {
                    TiledMapTile v24 = v4.getTile();
                    if(v24 != null) {
                        boolean v6 = v4.getFlipHorizontally();
                        boolean v7 = v4.getFlipVertically();
                        int v20 = v4.getRotation();
                        TextureRegion v19 = v24.getTextureRegion();
                        float v30 = (((float)v29)) * v11 - v18 + v24.getOffsetX() * this.unitScale;
                        float v33 = (((float)v32)) * v10 + v24.getOffsetY() * this.unitScale;
                        float v31 = v30 + (((float)v19.getRegionWidth())) * this.unitScale;
                        float v34 = v33 + (((float)v19.getRegionHeight())) * this.unitScale;
                        float v25 = v19.getU();
                        float v27 = v19.getV2();
                        float v26 = v19.getU2();
                        float v28 = v19.getV();
                        this.vertices[0] = v30;
                        this.vertices[1] = v33;
                        this.vertices[2] = v5;
                        this.vertices[3] = v25;
                        this.vertices[4] = v27;
                        this.vertices[5] = v30;
                        this.vertices[6] = v34;
                        this.vertices[7] = v5;
                        this.vertices[8] = v25;
                        this.vertices[9] = v28;
                        this.vertices[10] = v31;
                        this.vertices[11] = v34;
                        this.vertices[12] = v5;
                        this.vertices[13] = v26;
                        this.vertices[14] = v28;
                        this.vertices[15] = v31;
                        this.vertices[16] = v33;
                        this.vertices[17] = v5;
                        this.vertices[18] = v26;
                        this.vertices[19] = v27;
                        if(v6) {
                            v21 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = v21;
                            v21 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = v21;
                        }

                        if(v7) {
                            v21 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = v21;
                            v21 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = v21;
                        }

                        if(v20 != 0) {
                            switch(v20) {
                                case 1: {
                                    goto label_365;
                                }
                                case 2: {
                                    goto label_446;
                                }
                                case 3: {
                                    goto label_527;
                                }
                            }

                            goto label_354;
                        label_365:
                            float v23 = this.vertices[4];
                            this.vertices[4] = this.vertices[9];
                            this.vertices[9] = this.vertices[14];
                            this.vertices[14] = this.vertices[19];
                            this.vertices[19] = v23;
                            float v22 = this.vertices[3];
                            this.vertices[3] = this.vertices[8];
                            this.vertices[8] = this.vertices[13];
                            this.vertices[13] = this.vertices[18];
                            this.vertices[18] = v22;
                            goto label_354;
                        label_446:
                            v22 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = v22;
                            v22 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = v22;
                            v23 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = v23;
                            v23 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = v23;
                            goto label_354;
                        label_527:
                            v23 = this.vertices[4];
                            this.vertices[4] = this.vertices[19];
                            this.vertices[19] = this.vertices[14];
                            this.vertices[14] = this.vertices[9];
                            this.vertices[9] = v23;
                            v22 = this.vertices[3];
                            this.vertices[3] = this.vertices[18];
                            this.vertices[18] = this.vertices[13];
                            this.vertices[13] = this.vertices[8];
                            this.vertices[8] = v22;
                        }

                    label_354:
                        this.batch.draw(v19.getTexture(), this.vertices, 0, 20);
                    }
                }
            }
        }
    }
}

