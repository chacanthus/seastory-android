// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class NinePatch {
    public static final int BOTTOM_CENTER = 7;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_RIGHT = 8;
    public static final int MIDDLE_CENTER = 4;
    public static final int MIDDLE_LEFT = 3;
    public static final int MIDDLE_RIGHT = 5;
    public static final int TOP_CENTER = 1;
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 2;
    private int bottomCenter;
    private float bottomHeight;
    private int bottomLeft;
    private int bottomRight;
    private final Color color;
    private int idx;
    private float leftWidth;
    private int middleCenter;
    private float middleHeight;
    private int middleLeft;
    private int middleRight;
    private float middleWidth;
    private int padBottom;
    private int padLeft;
    private int padRight;
    private int padTop;
    private float rightWidth;
    private Texture texture;
    private static final Color tmpDrawColor;
    private int topCenter;
    private float topHeight;
    private int topLeft;
    private int topRight;
    private float[] vertices;

    static  {
        NinePatch.tmpDrawColor = new Color();
    }

    public NinePatch(Texture texture) {
        this(new TextureRegion(texture));
    }

    public NinePatch(TextureRegion region) {
        super();
        this.bottomLeft = -1;
        this.bottomCenter = -1;
        this.bottomRight = -1;
        this.middleLeft = -1;
        this.middleCenter = -1;
        this.middleRight = -1;
        this.topLeft = -1;
        this.topCenter = -1;
        this.topRight = -1;
        this.vertices = new float[180];
        this.color = new Color(Color.WHITE);
        this.padLeft = -1;
        this.padRight = -1;
        this.padTop = -1;
        this.padBottom = -1;
        TextureRegion[] v0 = new TextureRegion[9];
        v0[0] = null;
        v0[1] = null;
        v0[2] = null;
        v0[3] = null;
        v0[4] = region;
        v0[5] = null;
        v0[6] = null;
        v0[7] = null;
        v0[8] = null;
        this.load(v0);
    }

    public NinePatch(Texture texture, int left, int right, int top, int bottom) {
        this(new TextureRegion(texture), left, right, top, bottom);
    }

    public NinePatch(TextureRegion region, int left, int right, int top, int bottom) {
        super();
        this.bottomLeft = -1;
        this.bottomCenter = -1;
        this.bottomRight = -1;
        this.middleLeft = -1;
        this.middleCenter = -1;
        this.middleRight = -1;
        this.topLeft = -1;
        this.topCenter = -1;
        this.topRight = -1;
        this.vertices = new float[180];
        this.color = new Color(Color.WHITE);
        this.padLeft = -1;
        this.padRight = -1;
        this.padTop = -1;
        this.padBottom = -1;
        if(region == null) {
            throw new IllegalArgumentException("region cannot be null.");
        }

        int v7 = region.getRegionWidth() - left - right;
        int v6 = region.getRegionHeight() - top - bottom;
        TextureRegion[] v8 = new TextureRegion[9];
        if(top > 0) {
            if(left > 0) {
                v8[0] = new TextureRegion(region, 0, 0, left, top);
            }

            if(v7 > 0) {
                v8[1] = new TextureRegion(region, left, 0, v7, top);
            }

            if(right <= 0) {
                goto label_73;
            }

            v8[2] = new TextureRegion(region, left + v7, 0, right, top);
        }

    label_73:
        if(v6 > 0) {
            if(left > 0) {
                v8[3] = new TextureRegion(region, 0, top, left, v6);
            }

            if(v7 > 0) {
                v8[4] = new TextureRegion(region, left, top, v7, v6);
            }

            if(right <= 0) {
                goto label_101;
            }

            v8[5] = new TextureRegion(region, left + v7, top, right, v6);
        }

    label_101:
        if(bottom > 0) {
            if(left > 0) {
                v8[6] = new TextureRegion(region, 0, top + v6, left, bottom);
            }

            if(v7 > 0) {
                v8[7] = new TextureRegion(region, left, top + v6, v7, bottom);
            }

            if(right <= 0) {
                goto label_129;
            }

            v8[8] = new TextureRegion(region, left + v7, top + v6, right, bottom);
        }

    label_129:
        if(left == 0 && v7 == 0) {
            v8[1] = v8[2];
            v8[4] = v8[5];
            v8[7] = v8[8];
            v8[2] = null;
            v8[5] = null;
            v8[8] = null;
        }

        if(top == 0 && v6 == 0) {
            v8[3] = v8[6];
            v8[4] = v8[7];
            v8[5] = v8[8];
            v8[6] = null;
            v8[7] = null;
            v8[8] = null;
        }

        this.load(v8);
    }

    public NinePatch(Texture texture, Color color) {
        this(texture);
        this.setColor(color);
    }

    public NinePatch(NinePatch ninePatch) {
        this(ninePatch, ninePatch.color);
    }

    public NinePatch(NinePatch ninePatch, Color color) {
        super();
        this.bottomLeft = -1;
        this.bottomCenter = -1;
        this.bottomRight = -1;
        this.middleLeft = -1;
        this.middleCenter = -1;
        this.middleRight = -1;
        this.topLeft = -1;
        this.topCenter = -1;
        this.topRight = -1;
        this.vertices = new float[180];
        this.color = new Color(Color.WHITE);
        this.padLeft = -1;
        this.padRight = -1;
        this.padTop = -1;
        this.padBottom = -1;
        this.texture = ninePatch.texture;
        this.bottomLeft = ninePatch.bottomLeft;
        this.bottomCenter = ninePatch.bottomCenter;
        this.bottomRight = ninePatch.bottomRight;
        this.middleLeft = ninePatch.middleLeft;
        this.middleCenter = ninePatch.middleCenter;
        this.middleRight = ninePatch.middleRight;
        this.topLeft = ninePatch.topLeft;
        this.topCenter = ninePatch.topCenter;
        this.topRight = ninePatch.topRight;
        this.leftWidth = ninePatch.leftWidth;
        this.rightWidth = ninePatch.rightWidth;
        this.middleWidth = ninePatch.middleWidth;
        this.middleHeight = ninePatch.middleHeight;
        this.topHeight = ninePatch.topHeight;
        this.bottomHeight = ninePatch.bottomHeight;
        this.padLeft = ninePatch.padLeft;
        this.padTop = ninePatch.padTop;
        this.padBottom = ninePatch.padBottom;
        this.padRight = ninePatch.padRight;
        this.vertices = new float[ninePatch.vertices.length];
        System.arraycopy(ninePatch.vertices, 0, this.vertices, 0, ninePatch.vertices.length);
        this.idx = ninePatch.idx;
        this.color.set(color);
    }

    public NinePatch(TextureRegion region, Color color) {
        this(region);
        this.setColor(color);
    }

    public NinePatch(TextureRegion[] patches) {
        int v10 = 8;
        int v9 = 6;
        int v8 = 2;
        super();
        this.bottomLeft = -1;
        this.bottomCenter = -1;
        this.bottomRight = -1;
        this.middleLeft = -1;
        this.middleCenter = -1;
        this.middleRight = -1;
        this.topLeft = -1;
        this.topCenter = -1;
        this.topRight = -1;
        this.vertices = new float[180];
        this.color = new Color(Color.WHITE);
        this.padLeft = -1;
        this.padRight = -1;
        this.padTop = -1;
        this.padBottom = -1;
        if(patches != null && patches.length == 9) {
            this.load(patches);
            float v1 = this.getLeftWidth();
            if(patches[0] != null && (((float)patches[0].getRegionWidth())) != v1 || patches[3] != null && (((float)patches[3].getRegionWidth())) != v1 || patches[v9] != null && (((float)patches[v9].getRegionWidth())) != v1) {
                throw new GdxRuntimeException("Left side patches must have the same width");
            }

            float v2 = this.getRightWidth();
            if(patches[v8] != null && (((float)patches[v8].getRegionWidth())) != v2 || patches[5] != null && (((float)patches[5].getRegionWidth())) != v2 || patches[v10] != null && (((float)patches[v10].getRegionWidth())) != v2) {
                throw new GdxRuntimeException("Right side patches must have the same width");
            }

            float v0 = this.getBottomHeight();
            if(patches[v9] != null && (((float)patches[v9].getRegionHeight())) != v0 || patches[7] != null && (((float)patches[7].getRegionHeight())) != v0 || patches[v10] != null && (((float)patches[v10].getRegionHeight())) != v0) {
                throw new GdxRuntimeException("Bottom side patches must have the same height");
            }

            float v3 = this.getTopHeight();
            if(patches[0] != null && (((float)patches[0].getRegionHeight())) != v3 || patches[1] != null && (((float)patches[1].getRegionHeight())) != v3 || patches[v8] != null && (((float)patches[v8].getRegionHeight())) != v3) {
                throw new GdxRuntimeException("Top side patches must have the same height");
            }

            return;
        }

        throw new IllegalArgumentException("NinePatch needs nine TextureRegions");
    }

    private int add(TextureRegion region, float color, boolean isStretchW, boolean isStretchH) {
        float v9 = 0.5f;
        if(this.texture == null) {
            this.texture = region.getTexture();
        }
        else if(this.texture != region.getTexture()) {
            throw new IllegalArgumentException("All regions must be from the same texture.");
        }

        float v2 = region.u;
        float v4 = region.v2;
        float v3 = region.u2;
        float v5 = region.v;
        if(isStretchW) {
            float v1 = v9 / (((float)this.texture.getWidth()));
            v2 += v1;
            v3 -= v1;
        }

        if(isStretchH) {
            float v0 = v9 / (((float)this.texture.getHeight()));
            v4 -= v0;
            v5 += v0;
        }

        float[] v6 = this.vertices;
        this.idx += 2;
        int v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = color;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = v2;
        v6[this.idx] = v4;
        this.idx += 3;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = color;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = v2;
        v6[this.idx] = v5;
        this.idx += 3;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = color;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = v3;
        v6[this.idx] = v5;
        this.idx += 3;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = color;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = v3;
        v7 = this.idx;
        this.idx = v7 + 1;
        v6[v7] = v4;
        return this.idx - 20;
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        float v7 = x + this.leftWidth;
        float v9 = x + width - this.rightWidth;
        float v8 = y + this.bottomHeight;
        float v10 = y + height - this.topHeight;
        float v6 = NinePatch.tmpDrawColor.set(this.color).mul(batch.getColor()).toFloatBits();
        if(this.bottomLeft != -1) {
            this.set(this.bottomLeft, x, y, v7 - x, v8 - y, v6);
        }

        if(this.bottomCenter != -1) {
            this.set(this.bottomCenter, v7, y, v9 - v7, v8 - y, v6);
        }

        if(this.bottomRight != -1) {
            this.set(this.bottomRight, v9, y, x + width - v9, v8 - y, v6);
        }

        if(this.middleLeft != -1) {
            this.set(this.middleLeft, x, v8, v7 - x, v10 - v8, v6);
        }

        if(this.middleCenter != -1) {
            this.set(this.middleCenter, v7, v8, v9 - v7, v10 - v8, v6);
        }

        if(this.middleRight != -1) {
            this.set(this.middleRight, v9, v8, x + width - v9, v10 - v8, v6);
        }

        if(this.topLeft != -1) {
            this.set(this.topLeft, x, v10, v7 - x, y + height - v10, v6);
        }

        if(this.topCenter != -1) {
            this.set(this.topCenter, v7, v10, v9 - v7, y + height - v10, v6);
        }

        if(this.topRight != -1) {
            this.set(this.topRight, v9, v10, x + width - v9, y + height - v10, v6);
        }

        batch.draw(this.texture, this.vertices, 0, this.idx);
    }

    public float getBottomHeight() {
        return this.bottomHeight;
    }

    public Color getColor() {
        return this.color;
    }

    public float getLeftWidth() {
        return this.leftWidth;
    }

    public float getMiddleHeight() {
        return this.middleHeight;
    }

    public float getMiddleWidth() {
        return this.middleWidth;
    }

    public float getPadBottom() {
        float v0;
        if(this.padBottom == -1) {
            v0 = this.getBottomHeight();
        }
        else {
            v0 = ((float)this.padBottom);
        }

        return v0;
    }

    public float getPadLeft() {
        float v0;
        if(this.padLeft == -1) {
            v0 = this.getLeftWidth();
        }
        else {
            v0 = ((float)this.padLeft);
        }

        return v0;
    }

    public float getPadRight() {
        float v0;
        if(this.padRight == -1) {
            v0 = this.getRightWidth();
        }
        else {
            v0 = ((float)this.padRight);
        }

        return v0;
    }

    public float getPadTop() {
        float v0;
        if(this.padTop == -1) {
            v0 = this.getTopHeight();
        }
        else {
            v0 = ((float)this.padTop);
        }

        return v0;
    }

    public float getRightWidth() {
        return this.rightWidth;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public float getTopHeight() {
        return this.topHeight;
    }

    public float getTotalHeight() {
        return this.topHeight + this.middleHeight + this.bottomHeight;
    }

    public float getTotalWidth() {
        return this.leftWidth + this.middleWidth + this.rightWidth;
    }

    private void load(TextureRegion[] patches) {
        int v8 = 4;
        int v7 = 3;
        int v6 = 2;
        float v0 = Color.WHITE.toFloatBits();
        if(patches[6] != null) {
            this.bottomLeft = this.add(patches[6], v0, false, false);
            this.leftWidth = ((float)patches[6].getRegionWidth());
            this.bottomHeight = ((float)patches[6].getRegionHeight());
        }

        if(patches[7] != null) {
            this.bottomCenter = this.add(patches[7], v0, true, false);
            this.middleWidth = Math.max(this.middleWidth, ((float)patches[7].getRegionWidth()));
            this.bottomHeight = Math.max(this.bottomHeight, ((float)patches[7].getRegionHeight()));
        }

        if(patches[8] != null) {
            this.bottomRight = this.add(patches[8], v0, false, false);
            this.rightWidth = Math.max(this.rightWidth, ((float)patches[8].getRegionWidth()));
            this.bottomHeight = Math.max(this.bottomHeight, ((float)patches[8].getRegionHeight()));
        }

        if(patches[v7] != null) {
            this.middleLeft = this.add(patches[v7], v0, false, true);
            this.leftWidth = Math.max(this.leftWidth, ((float)patches[v7].getRegionWidth()));
            this.middleHeight = Math.max(this.middleHeight, ((float)patches[v7].getRegionHeight()));
        }

        if(patches[v8] != null) {
            this.middleCenter = this.add(patches[v8], v0, true, true);
            this.middleWidth = Math.max(this.middleWidth, ((float)patches[v8].getRegionWidth()));
            this.middleHeight = Math.max(this.middleHeight, ((float)patches[v8].getRegionHeight()));
        }

        if(patches[5] != null) {
            this.middleRight = this.add(patches[5], v0, false, true);
            this.rightWidth = Math.max(this.rightWidth, ((float)patches[5].getRegionWidth()));
            this.middleHeight = Math.max(this.middleHeight, ((float)patches[5].getRegionHeight()));
        }

        if(patches[0] != null) {
            this.topLeft = this.add(patches[0], v0, false, false);
            this.leftWidth = Math.max(this.leftWidth, ((float)patches[0].getRegionWidth()));
            this.topHeight = Math.max(this.topHeight, ((float)patches[0].getRegionHeight()));
        }

        if(patches[1] != null) {
            this.topCenter = this.add(patches[1], v0, true, false);
            this.middleWidth = Math.max(this.middleWidth, ((float)patches[1].getRegionWidth()));
            this.topHeight = Math.max(this.topHeight, ((float)patches[1].getRegionHeight()));
        }

        if(patches[v6] != null) {
            this.topRight = this.add(patches[v6], v0, false, false);
            this.rightWidth = Math.max(this.rightWidth, ((float)patches[v6].getRegionWidth()));
            this.topHeight = Math.max(this.topHeight, ((float)patches[v6].getRegionHeight()));
        }

        if(this.idx < this.vertices.length) {
            float[] v1 = new float[this.idx];
            System.arraycopy(this.vertices, 0, v1, 0, this.idx);
            this.vertices = v1;
        }
    }

    public void scale(float scaleX, float scaleY) {
        this.leftWidth *= scaleX;
        this.rightWidth *= scaleX;
        this.topHeight *= scaleY;
        this.bottomHeight *= scaleY;
        this.padLeft = ((int)((((float)this.padLeft)) * scaleX));
        this.padRight = ((int)((((float)this.padRight)) * scaleX));
        this.padTop = ((int)((((float)this.padTop)) * scaleY));
        this.padBottom = ((int)((((float)this.padBottom)) * scaleY));
    }

    private void set(int idx, float x, float y, float width, float height, float color) {
        float v0 = x + width;
        float v1 = y + height;
        float[] v3 = this.vertices;
        int v2 = idx + 1;
        v3[idx] = x;
        idx = v2 + 1;
        v3[v2] = y;
        v3[idx] = color;
        idx += 3;
        v2 = idx + 1;
        v3[idx] = x;
        idx = v2 + 1;
        v3[v2] = v1;
        v3[idx] = color;
        idx += 3;
        v2 = idx + 1;
        v3[idx] = v0;
        idx = v2 + 1;
        v3[v2] = v1;
        v3[idx] = color;
        idx += 3;
        v2 = idx + 1;
        v3[idx] = v0;
        v3[v2] = y;
        v3[v2 + 1] = color;
    }

    public void setBottomHeight(float bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setLeftWidth(float leftWidth) {
        this.leftWidth = leftWidth;
    }

    public void setMiddleHeight(float middleHeight) {
        this.middleHeight = middleHeight;
    }

    public void setMiddleWidth(float middleWidth) {
        this.middleWidth = middleWidth;
    }

    public void setPadBottom(int bottom) {
        this.padBottom = bottom;
    }

    public void setPadLeft(int left) {
        this.padLeft = left;
    }

    public void setPadRight(int right) {
        this.padRight = right;
    }

    public void setPadTop(int top) {
        this.padTop = top;
    }

    public void setPadding(int left, int right, int top, int bottom) {
        this.padLeft = left;
        this.padRight = right;
        this.padTop = top;
        this.padBottom = bottom;
    }

    public void setRightWidth(float rightWidth) {
        this.rightWidth = rightWidth;
    }

    public void setTopHeight(float topHeight) {
        this.topHeight = topHeight;
    }
}

