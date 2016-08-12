// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTile$BlendMode;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.TimeUtils;

public class AnimatedTiledMapTile implements TiledMapTile {
    private int[] animationIntervals;
    private BlendMode blendMode;
    private int frameCount;
    private StaticTiledMapTile[] frameTiles;
    private int id;
    private static final long initialTimeOffset;
    private static long lastTiledMapRenderTime;
    private int loopDuration;
    private MapProperties properties;

    static  {
        AnimatedTiledMapTile.lastTiledMapRenderTime = 0;
        AnimatedTiledMapTile.initialTimeOffset = TimeUtils.millis();
    }

    public AnimatedTiledMapTile(IntArray intervals, Array arg5) {
        super();
        this.blendMode = BlendMode.ALPHA;
        this.frameCount = 0;
        this.frameTiles = new StaticTiledMapTile[arg5.size];
        this.frameCount = arg5.size;
        this.animationIntervals = intervals.toArray();
        this.loopDuration = 0;
        int v0;
        for(v0 = 0; v0 < intervals.size; ++v0) {
            this.frameTiles[v0] = arg5.get(v0);
            this.loopDuration += intervals.get(v0);
        }
    }

    public AnimatedTiledMapTile(float interval, Array arg6) {
        float v3 = 1000f;
        super();
        this.blendMode = BlendMode.ALPHA;
        this.frameCount = 0;
        this.frameTiles = new StaticTiledMapTile[arg6.size];
        this.frameCount = arg6.size;
        this.loopDuration = arg6.size * (((int)(interval * v3)));
        this.animationIntervals = new int[arg6.size];
        int v0;
        for(v0 = 0; v0 < arg6.size; ++v0) {
            this.frameTiles[v0] = arg6.get(v0);
            this.animationIntervals[v0] = ((int)(interval * v3));
        }
    }

    public int[] getAnimationIntervals() {
        return this.animationIntervals;
    }

    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    public TiledMapTile getCurrentFrame() {
        return this.frameTiles[this.getCurrentFrameIndex()];
    }

    public int getCurrentFrameIndex() {
        int v1 = ((int)(AnimatedTiledMapTile.lastTiledMapRenderTime % (((long)this.loopDuration))));
        int v2;
        for(v2 = 0; v2 < this.animationIntervals.length; ++v2) {
            int v0 = this.animationIntervals[v2];
            if(v1 <= v0) {
                return v2;
            }

            v1 -= v0;
        }

        throw new GdxRuntimeException("Could not determine current animation frame in AnimatedTiledMapTile.  This should never happen.");
    }

    public int getId() {
        return this.id;
    }

    public float getOffsetX() {
        return this.getCurrentFrame().getOffsetX();
    }

    public float getOffsetY() {
        return this.getCurrentFrame().getOffsetY();
    }

    public MapProperties getProperties() {
        if(this.properties == null) {
            this.properties = new MapProperties();
        }

        return this.properties;
    }

    public TextureRegion getTextureRegion() {
        return this.getCurrentFrame().getTextureRegion();
    }

    public void setAnimationIntervals(int[] intervals) {
        if(intervals.length == this.animationIntervals.length) {
            this.animationIntervals = intervals;
            this.loopDuration = 0;
            int v0;
            for(v0 = 0; v0 < intervals.length; ++v0) {
                this.loopDuration += intervals[v0];
            }

            return;
        }

        throw new GdxRuntimeException("Cannot set " + intervals.length + " frame intervals. The given int[] must have a size of " + this.animationIntervals.length + ".");
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOffsetX(float offsetX) {
        throw new GdxRuntimeException("Cannot set offset of AnimatedTiledMapTile.");
    }

    public void setOffsetY(float offsetY) {
        throw new GdxRuntimeException("Cannot set offset of AnimatedTiledMapTile.");
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        throw new GdxRuntimeException("Cannot set the texture region of AnimatedTiledMapTile.");
    }

    public static void updateAnimationBaseTime() {
        AnimatedTiledMapTile.lastTiledMapRenderTime = TimeUtils.millis() - AnimatedTiledMapTile.initialTimeOffset;
    }
}

