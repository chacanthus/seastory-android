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

import com.badlogic.gdx.maps.MapLayer;

public class TiledMapTileLayer extends MapLayer {
    public class Cell {
        public static final int ROTATE_0 = 0;
        public static final int ROTATE_180 = 2;
        public static final int ROTATE_270 = 3;
        public static final int ROTATE_90 = 1;
        private boolean flipHorizontally;
        private boolean flipVertically;
        private int rotation;
        private TiledMapTile tile;

        public Cell() {
            super();
        }

        public boolean getFlipHorizontally() {
            return this.flipHorizontally;
        }

        public boolean getFlipVertically() {
            return this.flipVertically;
        }

        public int getRotation() {
            return this.rotation;
        }

        public TiledMapTile getTile() {
            return this.tile;
        }

        public void setFlipHorizontally(boolean flipHorizontally) {
            this.flipHorizontally = flipHorizontally;
        }

        public void setFlipVertically(boolean flipVertically) {
            this.flipVertically = flipVertically;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
        }

        public void setTile(TiledMapTile tile) {
            this.tile = tile;
        }
    }

    private Cell[][] cells;
    private int height;
    private float tileHeight;
    private float tileWidth;
    private int width;

    public TiledMapTileLayer(int width, int height, int tileWidth, int tileHeight) {
        super();
        this.width = width;
        this.height = height;
        this.tileWidth = ((float)tileWidth);
        this.tileHeight = ((float)tileHeight);
        this.cells = new Cell[width][height];
    }

    public Cell getCell(int x, int y) {
        Cell v0 = null;
        if(x >= 0 && x < this.width && y >= 0 && y < this.height) {
            v0 = this.cells[x][y];
        }

        return v0;
    }

    public int getHeight() {
        return this.height;
    }

    public float getTileHeight() {
        return this.tileHeight;
    }

    public float getTileWidth() {
        return this.tileWidth;
    }

    public int getWidth() {
        return this.width;
    }

    public void setCell(int x, int y, Cell cell) {
        if(x >= 0 && x < this.width && y >= 0 && y < this.height) {
            this.cells[x][y] = cell;
        }
    }
}

