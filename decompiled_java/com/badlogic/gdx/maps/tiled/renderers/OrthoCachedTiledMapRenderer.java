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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer$Cell;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public class OrthoCachedTiledMapRenderer implements TiledMapRenderer, Disposable {
    protected static final int NUM_VERTICES = 20;
    protected boolean blending;
    protected final Rectangle cacheBounds;
    protected boolean cached;
    protected boolean canCacheMoreE;
    protected boolean canCacheMoreN;
    protected boolean canCacheMoreS;
    protected boolean canCacheMoreW;
    protected int count;
    protected final TiledMap map;
    protected float maxTileHeight;
    protected float maxTileWidth;
    protected float overCache;
    protected final SpriteCache spriteCache;
    private static final float tolerance = 0.00001f;
    protected float unitScale;
    protected final float[] vertices;
    protected final Rectangle viewBounds;

    public OrthoCachedTiledMapRenderer(TiledMap map) {
        this(map, 1f, 2000);
    }

    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale, int cacheSize) {
        super();
        this.vertices = new float[20];
        this.viewBounds = new Rectangle();
        this.cacheBounds = new Rectangle();
        this.overCache = 0.5f;
        this.map = map;
        this.unitScale = unitScale;
        this.spriteCache = new SpriteCache(cacheSize, true);
    }

    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale) {
        this(map, unitScale, 2000);
    }

    public void dispose() {
        this.spriteCache.dispose();
    }

    public SpriteCache getSpriteCache() {
        return this.spriteCache;
    }

    public void invalidateCache() {
        this.cached = false;
    }

    public boolean isCached() {
        return this.cached;
    }

    public void render() {
        int v11 = 3042;
        float v10 = 2f;
        if(!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float v1 = this.viewBounds.width * this.overCache;
            float v0 = this.viewBounds.height * this.overCache;
            this.cacheBounds.x = this.viewBounds.x - v1;
            this.cacheBounds.y = this.viewBounds.y - v0;
            this.cacheBounds.width = this.viewBounds.width + v1 * v10;
            this.cacheBounds.height = this.viewBounds.height + v0 * v10;
            Iterator v3 = this.map.getLayers().iterator();
            while(v3.hasNext()) {
                Object v5 = v3.next();
                this.spriteCache.beginCache();
                if((v5 instanceof TiledMapTileLayer)) {
                    this.renderTileLayer(((TiledMapTileLayer)v5));
                }
                else if((v5 instanceof TiledMapImageLayer)) {
                    this.renderImageLayer(((TiledMapImageLayer)v5));
                }

                this.spriteCache.endCache();
            }
        }

        if(this.blending) {
            Gdx.gl.glEnable(v11);
            Gdx.gl.glBlendFunc(770, 771);
        }

        this.spriteCache.begin();
        MapLayers v6 = this.map.getLayers();
        int v2 = 0;
        int v4 = v6.getCount();
        while(v2 < v4) {
            MapLayer v5_1 = v6.get(v2);
            if(v5_1.isVisible()) {
                this.spriteCache.draw(v2);
                this.renderObjects(v5_1);
            }

            ++v2;
        }

        this.spriteCache.end();
        if(this.blending) {
            Gdx.gl.glDisable(v11);
        }
    }

    public void render(int[] layers) {
        int v12 = 3042;
        float v11 = 2f;
        if(!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float v2 = this.viewBounds.width * this.overCache;
            float v1 = this.viewBounds.height * this.overCache;
            this.cacheBounds.x = this.viewBounds.x - v2;
            this.cacheBounds.y = this.viewBounds.y - v1;
            this.cacheBounds.width = this.viewBounds.width + v2 * v11;
            this.cacheBounds.height = this.viewBounds.height + v1 * v11;
            Iterator v4 = this.map.getLayers().iterator();
            while(v4.hasNext()) {
                Object v5 = v4.next();
                this.spriteCache.beginCache();
                if((v5 instanceof TiledMapTileLayer)) {
                    this.renderTileLayer(((TiledMapTileLayer)v5));
                }
                else if((v5 instanceof TiledMapImageLayer)) {
                    this.renderImageLayer(((TiledMapImageLayer)v5));
                }

                this.spriteCache.endCache();
            }
        }

        if(this.blending) {
            Gdx.gl.glEnable(v12);
            Gdx.gl.glBlendFunc(770, 771);
        }

        this.spriteCache.begin();
        MapLayers v7 = this.map.getLayers();
        int[] v0 = layers;
        int v6 = v0.length;
        int v4_1;
        for(v4_1 = 0; v4_1 < v6; ++v4_1) {
            int v3 = v0[v4_1];
            MapLayer v5_1 = v7.get(v3);
            if(v5_1.isVisible()) {
                this.spriteCache.draw(v3);
                this.renderObjects(v5_1);
            }
        }

        this.spriteCache.end();
        if(this.blending) {
            Gdx.gl.glDisable(v12);
        }
    }

    public void renderImageLayer(TiledMapImageLayer layer) {
        float v4 = Color.toFloatBits(1f, 1f, 1f, layer.getOpacity());
        float[] v10 = this.vertices;
        TextureRegion v5 = layer.getTextureRegion();
        if(v5 != null) {
            float v11 = ((float)layer.getX());
            float v14 = ((float)layer.getY());
            float v12 = v11 * this.unitScale;
            float v15 = v14 * this.unitScale;
            float v13 = v12 + (((float)v5.getRegionWidth())) * this.unitScale;
            float v16 = v15 + (((float)v5.getRegionHeight())) * this.unitScale;
            float v6 = v5.getU();
            float v8 = v5.getV2();
            float v7 = v5.getU2();
            float v9 = v5.getV();
            v10[0] = v12;
            v10[1] = v15;
            v10[2] = v4;
            v10[3] = v6;
            v10[4] = v8;
            v10[5] = v12;
            v10[6] = v16;
            v10[7] = v4;
            v10[8] = v6;
            v10[9] = v9;
            v10[10] = v13;
            v10[11] = v16;
            v10[12] = v4;
            v10[13] = v7;
            v10[14] = v9;
            v10[15] = v13;
            v10[16] = v15;
            v10[17] = v4;
            v10[18] = v7;
            v10[19] = v8;
            this.spriteCache.add(v5.getTexture(), v10, 0, 20);
        }
    }

    public void renderObject(MapObject object) {
    }

    public void renderObjects(MapLayer layer) {
        Iterator v0 = layer.getObjects().iterator();
        while(v0.hasNext()) {
            this.renderObject(v0.next());
        }
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float v23;
        boolean v37;
        float v11 = Color.toFloatBits(1f, 1f, 1f, layer.getOpacity());
        int v17 = layer.getWidth();
        int v14 = layer.getHeight();
        float v16 = layer.getTileWidth() * this.unitScale;
        float v15 = layer.getTileHeight() * this.unitScale;
        int v9 = Math.max(0, ((int)(this.cacheBounds.x / v16)));
        int v10 = Math.min(v17, ((int)((this.cacheBounds.x + this.cacheBounds.width + v16) / v16)));
        int v21 = Math.max(0, ((int)(this.cacheBounds.y / v15)));
        int v22 = Math.min(v14, ((int)((this.cacheBounds.y + this.cacheBounds.height + v15) / v15)));
        if(v22 < v14) {
            v37 = true;
        }
        else {
            v37 = false;
        }

        this.canCacheMoreN = v37;
        if(v10 < v17) {
            v37 = true;
        }
        else {
            v37 = false;
        }

        this.canCacheMoreE = v37;
        if(v9 > 0) {
            v37 = true;
        }
        else {
            v37 = false;
        }

        this.canCacheMoreW = v37;
        if(v21 > 0) {
            v37 = true;
        }
        else {
            v37 = false;
        }

        this.canCacheMoreS = v37;
        float[] v32 = this.vertices;
        int v20;
        for(v20 = v22; v20 >= v21; --v20) {
            int v8;
            for(v8 = v9; v8 < v10; ++v8) {
                Cell v7 = layer.getCell(v8, v20);
                if(v7 != null) {
                    TiledMapTile v27 = v7.getTile();
                    if(v27 != null) {
                        ++this.count;
                        boolean v12 = v7.getFlipHorizontally();
                        boolean v13 = v7.getFlipVertically();
                        int v19 = v7.getRotation();
                        TextureRegion v18 = v27.getTextureRegion();
                        Texture v26 = v18.getTexture();
                        float v33 = (((float)v8)) * v16 + v27.getOffsetX() * this.unitScale;
                        float v35 = (((float)v20)) * v15 + v27.getOffsetY() * this.unitScale;
                        float v34 = v33 + (((float)v18.getRegionWidth())) * this.unitScale;
                        float v36 = v35 + (((float)v18.getRegionHeight())) * this.unitScale;
                        float v5 = 0.5f / (((float)v26.getWidth()));
                        float v6 = 0.5f / (((float)v26.getHeight()));
                        float v28 = v18.getU() + v5;
                        float v30 = v18.getV2() - v6;
                        float v29 = v18.getU2() - v5;
                        float v31 = v18.getV() + v6;
                        v32[0] = v33;
                        v32[1] = v35;
                        v32[2] = v11;
                        v32[3] = v28;
                        v32[4] = v30;
                        v32[5] = v33;
                        v32[6] = v36;
                        v32[7] = v11;
                        v32[8] = v28;
                        v32[9] = v31;
                        v32[10] = v34;
                        v32[11] = v36;
                        v32[12] = v11;
                        v32[13] = v29;
                        v32[14] = v31;
                        v32[15] = v34;
                        v32[16] = v35;
                        v32[17] = v11;
                        v32[18] = v29;
                        v32[19] = v30;
                        if(v12) {
                            v23 = v32[3];
                            v32[3] = v32[13];
                            v32[13] = v23;
                            v23 = v32[8];
                            v32[8] = v32[18];
                            v32[18] = v23;
                        }

                        if(v13) {
                            v23 = v32[4];
                            v32[4] = v32[14];
                            v32[14] = v23;
                            v23 = v32[9];
                            v32[9] = v32[19];
                            v32[19] = v23;
                        }

                        if(v19 != 0) {
                            switch(v19) {
                                case 1: {
                                    goto label_286;
                                }
                                case 2: {
                                    goto label_319;
                                }
                                case 3: {
                                    goto label_352;
                                }
                            }

                            goto label_274;
                        label_286:
                            float v25 = v32[4];
                            v32[4] = v32[9];
                            v32[9] = v32[14];
                            v32[14] = v32[19];
                            v32[19] = v25;
                            float v24 = v32[3];
                            v32[3] = v32[8];
                            v32[8] = v32[13];
                            v32[13] = v32[18];
                            v32[18] = v24;
                            goto label_274;
                        label_319:
                            v24 = v32[3];
                            v32[3] = v32[13];
                            v32[13] = v24;
                            v24 = v32[8];
                            v32[8] = v32[18];
                            v32[18] = v24;
                            v25 = v32[4];
                            v32[4] = v32[14];
                            v32[14] = v25;
                            v25 = v32[9];
                            v32[9] = v32[19];
                            v32[19] = v25;
                            goto label_274;
                        label_352:
                            v25 = v32[4];
                            v32[4] = v32[19];
                            v32[19] = v32[14];
                            v32[14] = v32[9];
                            v32[9] = v25;
                            v24 = v32[3];
                            v32[3] = v32[18];
                            v32[18] = v32[13];
                            v32[13] = v32[8];
                            v32[8] = v24;
                        }

                    label_274:
                        this.spriteCache.add(v26, v32, 0, 20);
                    }
                }
            }
        }
    }

    public void setBlending(boolean blending) {
        this.blending = blending;
    }

    public void setMaxTileSize(float maxPixelWidth, float maxPixelHeight) {
        this.maxTileWidth = maxPixelWidth;
        this.maxTileHeight = maxPixelHeight;
    }

    public void setOverCache(float overCache) {
        this.overCache = overCache;
    }

    public void setView(OrthographicCamera camera) {
        float v6 = 0.00001f;
        this.spriteCache.setProjectionMatrix(camera.combined);
        float v1 = camera.viewportWidth * camera.zoom + this.maxTileWidth * 2f * this.unitScale;
        float v0 = camera.viewportHeight * camera.zoom + this.maxTileHeight * 2f * this.unitScale;
        this.viewBounds.set(camera.position.x - v1 / 2f, camera.position.y - v0 / 2f, v1, v0);
        if(!this.canCacheMoreW || this.viewBounds.x >= this.cacheBounds.x - v6) {
            if((this.canCacheMoreS) && this.viewBounds.y < this.cacheBounds.y - v6) {
                goto label_75;
            }

            if((this.canCacheMoreE) && this.viewBounds.x + this.viewBounds.width > this.cacheBounds.x + this.cacheBounds.width + v6) {
                goto label_75;
            }

            if(!this.canCacheMoreN) {
                return;
            }

            if(this.viewBounds.y + this.viewBounds.height <= this.cacheBounds.y + this.cacheBounds.height + v6) {
                return;
            }

        label_75:
            this.cached = false;
        }
        else {
            goto label_75;
        }
    }

    public void setView(Matrix4 projection, float x, float y, float width, float height) {
        float v3 = 0.00001f;
        this.spriteCache.setProjectionMatrix(projection);
        this.viewBounds.set(x - this.maxTileWidth * this.unitScale, y - this.maxTileHeight * this.unitScale, width + this.maxTileWidth * 2f * this.unitScale, height + this.maxTileHeight * 2f * this.unitScale);
        if(!this.canCacheMoreW || this.viewBounds.x >= this.cacheBounds.x - v3) {
            if((this.canCacheMoreS) && this.viewBounds.y < this.cacheBounds.y - v3) {
                goto label_68;
            }

            if((this.canCacheMoreE) && this.viewBounds.x + this.viewBounds.width > this.cacheBounds.x + this.cacheBounds.width + v3) {
                goto label_68;
            }

            if(!this.canCacheMoreN) {
                return;
            }

            if(this.viewBounds.y + this.viewBounds.height <= this.cacheBounds.y + this.cacheBounds.height + v3) {
                return;
            }

        label_68:
            this.cached = false;
        }
        else {
            goto label_68;
        }
    }
}

