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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public abstract class BatchTiledMapRenderer implements TiledMapRenderer, Disposable {
    protected static final int NUM_VERTICES = 20;
    protected Batch batch;
    protected Rectangle imageBounds;
    protected TiledMap map;
    protected boolean ownsBatch;
    protected float unitScale;
    protected float[] vertices;
    protected Rectangle viewBounds;

    public BatchTiledMapRenderer(TiledMap map) {
        this(map, 1f);
    }

    public BatchTiledMapRenderer(TiledMap map, float unitScale) {
        super();
        this.imageBounds = new Rectangle();
        this.vertices = new float[20];
        this.map = map;
        this.unitScale = unitScale;
        this.viewBounds = new Rectangle();
        this.batch = new SpriteBatch();
        this.ownsBatch = true;
    }

    public BatchTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super();
        this.imageBounds = new Rectangle();
        this.vertices = new float[20];
        this.map = map;
        this.unitScale = unitScale;
        this.viewBounds = new Rectangle();
        this.batch = batch;
        this.ownsBatch = false;
    }

    public BatchTiledMapRenderer(TiledMap map, Batch batch) {
        this(map, 1f, batch);
    }

    protected void beginRender() {
        AnimatedTiledMapTile.updateAnimationBaseTime();
        this.batch.begin();
    }

    public void dispose() {
        if(this.ownsBatch) {
            this.batch.dispose();
        }
    }

    protected void endRender() {
        this.batch.end();
    }

    public Batch getBatch() {
        return this.batch;
    }

    public TiledMap getMap() {
        return this.map;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public Rectangle getViewBounds() {
        return this.viewBounds;
    }

    public void render() {
        this.beginRender();
        Iterator v0 = this.map.getLayers().iterator();
        while(true) {
            if(!v0.hasNext()) {
                break;
            }

            Object v1 = v0.next();
            if(!((MapLayer)v1).isVisible()) {
                continue;
            }

            if((v1 instanceof TiledMapTileLayer)) {
                this.renderTileLayer(v1);
            }

            if(!(v1 instanceof TiledMapImageLayer)) {
                goto label_17;
            }

            this.renderImageLayer(((TiledMapImageLayer)v1));
            continue;
        label_17:
            this.renderObjects(((MapLayer)v1));
        }

        this.endRender();
    }

    public void render(int[] layers) {
        this.beginRender();
        int[] v0 = layers;
        int v4 = v0.length;
        int v1;
        for(v1 = 0; v1 < v4; ++v1) {
            MapLayer v2 = this.map.getLayers().get(v0[v1]);
            if(v2.isVisible()) {
                if((v2 instanceof TiledMapTileLayer)) {
                    this.renderTileLayer(((TiledMapTileLayer)v2));
                }
                else if((v2 instanceof TiledMapImageLayer)) {
                    this.renderImageLayer(((TiledMapImageLayer)v2));
                }
                else {
                    this.renderObjects(v2);
                }
            }
        }

        this.endRender();
    }

    public void renderImageLayer(TiledMapImageLayer layer) {
        Color v4 = this.batch.getColor();
        float v5 = Color.toFloatBits(v4.r, v4.g, v4.b, v4.a * layer.getOpacity());
        float[] v11 = this.vertices;
        TextureRegion v6 = layer.getTextureRegion();
        if(v6 != null) {
            float v12 = ((float)layer.getX());
            float v15 = ((float)layer.getY());
            float v13 = v12 * this.unitScale;
            float v16 = v15 * this.unitScale;
            float v14 = v13 + (((float)v6.getRegionWidth())) * this.unitScale;
            float v17 = v16 + (((float)v6.getRegionHeight())) * this.unitScale;
            this.imageBounds.set(v13, v16, v14 - v13, v17 - v16);
            if(!this.viewBounds.contains(this.imageBounds) && !this.viewBounds.overlaps(this.imageBounds)) {
                return;
            }

            float v7 = v6.getU();
            float v9 = v6.getV2();
            float v8 = v6.getU2();
            float v10 = v6.getV();
            v11[0] = v13;
            v11[1] = v16;
            v11[2] = v5;
            v11[3] = v7;
            v11[4] = v9;
            v11[5] = v13;
            v11[6] = v17;
            v11[7] = v5;
            v11[8] = v7;
            v11[9] = v10;
            v11[10] = v14;
            v11[11] = v17;
            v11[12] = v5;
            v11[13] = v8;
            v11[14] = v10;
            v11[15] = v14;
            v11[16] = v16;
            v11[17] = v5;
            v11[18] = v8;
            v11[19] = v9;
            this.batch.draw(v6.getTexture(), v11, 0, 20);
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

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public void setView(OrthographicCamera camera) {
        this.batch.setProjectionMatrix(camera.combined);
        float v1 = camera.viewportWidth * camera.zoom;
        float v0 = camera.viewportHeight * camera.zoom;
        this.viewBounds.set(camera.position.x - v1 / 2f, camera.position.y - v0 / 2f, v1, v0);
    }

    public void setView(Matrix4 projection, float x, float y, float width, float height) {
        this.batch.setProjectionMatrix(projection);
        this.viewBounds.set(x, y, width, height);
    }
}

