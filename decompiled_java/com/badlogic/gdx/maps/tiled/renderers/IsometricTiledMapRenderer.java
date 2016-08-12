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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class IsometricTiledMapRenderer extends BatchTiledMapRenderer {
    private Vector2 bottomLeft;
    private Vector2 bottomRight;
    private Matrix4 invIsotransform;
    private Matrix4 isoTransform;
    private Vector3 screenPos;
    private Vector2 topLeft;
    private Vector2 topRight;

    public IsometricTiledMapRenderer(TiledMap map) {
        super(map);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        this.init();
    }

    public IsometricTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        this.init();
    }

    public IsometricTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        this.init();
    }

    public IsometricTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        this.screenPos = new Vector3();
        this.topRight = new Vector2();
        this.bottomLeft = new Vector2();
        this.topLeft = new Vector2();
        this.bottomRight = new Vector2();
        this.init();
    }

    private void init() {
        this.isoTransform = new Matrix4();
        this.isoTransform.idt();
        this.isoTransform.scale(((float)(Math.sqrt(2) / 2)), ((float)(Math.sqrt(2) / 4)), 1f);
        this.isoTransform.rotate(0f, 0f, 1f, -45f);
        this.invIsotransform = new Matrix4(this.isoTransform);
        this.invIsotransform.inv();
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float v17;
        Color v2 = this.batch.getColor();
        float v7 = Color.toFloatBits(v2.r, v2.g, v2.b, v2.a * layer.getOpacity());
        float v22 = layer.getTileWidth() * this.unitScale;
        float v11 = v22 * 0.5f;
        float v10 = layer.getTileHeight() * this.unitScale * 0.5f;
        this.topRight.set(this.viewBounds.x + this.viewBounds.width, this.viewBounds.y);
        this.bottomLeft.set(this.viewBounds.x, this.viewBounds.y + this.viewBounds.height);
        this.topLeft.set(this.viewBounds.x, this.viewBounds.y);
        this.bottomRight.set(this.viewBounds.x + this.viewBounds.width, this.viewBounds.y + this.viewBounds.height);
        int v15 = (((int)(this.translateScreenToIso(this.topLeft).y / v22))) - 2;
        int v16 = (((int)(this.translateScreenToIso(this.bottomRight).y / v22))) + 2;
        int v5 = (((int)(this.translateScreenToIso(this.bottomLeft).x / v22))) - 2;
        int v6 = (((int)(this.translateScreenToIso(this.topRight).x / v22))) + 2;
        int v14;
        for(v14 = v16; v14 >= v15; --v14) {
            int v4;
            for(v4 = v5; v4 <= v6; ++v4) {
                float v27 = (((float)v4)) * v11 + (((float)v14)) * v11;
                float v30 = (((float)v14)) * v10 - (((float)v4)) * v10;
                Cell v3 = layer.getCell(v4, v14);
                if(v3 != null) {
                    TiledMapTile v20 = v3.getTile();
                    if(v20 != null) {
                        boolean v8 = v3.getFlipHorizontally();
                        boolean v9 = v3.getFlipVertically();
                        int v13 = v3.getRotation();
                        TextureRegion v12 = v20.getTextureRegion();
                        float v28 = v27 + v20.getOffsetX() * this.unitScale;
                        float v31 = v30 + v20.getOffsetY() * this.unitScale;
                        float v29 = v28 + (((float)v12.getRegionWidth())) * this.unitScale;
                        float v32 = v31 + (((float)v12.getRegionHeight())) * this.unitScale;
                        float v23 = v12.getU();
                        float v25 = v12.getV2();
                        float v24 = v12.getU2();
                        float v26 = v12.getV();
                        this.vertices[0] = v28;
                        this.vertices[1] = v31;
                        this.vertices[2] = v7;
                        this.vertices[3] = v23;
                        this.vertices[4] = v25;
                        this.vertices[5] = v28;
                        this.vertices[6] = v32;
                        this.vertices[7] = v7;
                        this.vertices[8] = v23;
                        this.vertices[9] = v26;
                        this.vertices[10] = v29;
                        this.vertices[11] = v32;
                        this.vertices[12] = v7;
                        this.vertices[13] = v24;
                        this.vertices[14] = v26;
                        this.vertices[15] = v29;
                        this.vertices[16] = v31;
                        this.vertices[17] = v7;
                        this.vertices[18] = v24;
                        this.vertices[19] = v25;
                        if(v8) {
                            v17 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = v17;
                            v17 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = v17;
                        }

                        if(v9) {
                            v17 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = v17;
                            v17 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = v17;
                        }

                        if(v13 != 0) {
                            switch(v13) {
                                case 1: {
                                    goto label_435;
                                }
                                case 2: {
                                    goto label_516;
                                }
                                case 3: {
                                    goto label_597;
                                }
                            }

                            goto label_424;
                        label_435:
                            float v19 = this.vertices[4];
                            this.vertices[4] = this.vertices[9];
                            this.vertices[9] = this.vertices[14];
                            this.vertices[14] = this.vertices[19];
                            this.vertices[19] = v19;
                            float v18 = this.vertices[3];
                            this.vertices[3] = this.vertices[8];
                            this.vertices[8] = this.vertices[13];
                            this.vertices[13] = this.vertices[18];
                            this.vertices[18] = v18;
                            goto label_424;
                        label_516:
                            v18 = this.vertices[3];
                            this.vertices[3] = this.vertices[13];
                            this.vertices[13] = v18;
                            v18 = this.vertices[8];
                            this.vertices[8] = this.vertices[18];
                            this.vertices[18] = v18;
                            v19 = this.vertices[4];
                            this.vertices[4] = this.vertices[14];
                            this.vertices[14] = v19;
                            v19 = this.vertices[9];
                            this.vertices[9] = this.vertices[19];
                            this.vertices[19] = v19;
                            goto label_424;
                        label_597:
                            v19 = this.vertices[4];
                            this.vertices[4] = this.vertices[19];
                            this.vertices[19] = this.vertices[14];
                            this.vertices[14] = this.vertices[9];
                            this.vertices[9] = v19;
                            v18 = this.vertices[3];
                            this.vertices[3] = this.vertices[18];
                            this.vertices[18] = this.vertices[13];
                            this.vertices[13] = this.vertices[8];
                            this.vertices[8] = v18;
                        }

                    label_424:
                        this.batch.draw(v12.getTexture(), this.vertices, 0, 20);
                    }
                }
            }
        }
    }

    private Vector3 translateScreenToIso(Vector2 vec) {
        this.screenPos.set(vec.x, vec.y, 0f);
        this.screenPos.mul(this.invIsotransform);
        return this.screenPos;
    }
}

