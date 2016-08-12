// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cubemap$CubemapSide;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$Factory;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FacedCubemapData implements CubemapData {
    protected final TextureData[] data;

    public FacedCubemapData(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
        super();
        this.data = new TextureData[6];
        this.data[0] = positiveX;
        this.data[1] = negativeX;
        this.data[2] = positiveY;
        this.data[3] = negativeY;
        this.data[4] = positiveZ;
        this.data[5] = negativeZ;
    }

    public FacedCubemapData() {
        this(null, null, null, null, null, null);
    }

    public FacedCubemapData(int width, int height, int depth, Format format) {
        this(new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
    }

    public FacedCubemapData(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ) {
        this(Factory.loadFromFile(positiveX, false), Factory.loadFromFile(negativeX, false), Factory.loadFromFile(positiveY, false), Factory.loadFromFile(negativeY, false), Factory.loadFromFile(positiveZ, false), Factory.loadFromFile(negativeZ, false));
    }

    public FacedCubemapData(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ, boolean useMipMaps) {
        this(Factory.loadFromFile(positiveX, useMipMaps), Factory.loadFromFile(negativeX, useMipMaps), Factory.loadFromFile(positiveY, useMipMaps), Factory.loadFromFile(negativeY, useMipMaps), Factory.loadFromFile(positiveZ, useMipMaps), Factory.loadFromFile(negativeZ, useMipMaps));
    }

    public FacedCubemapData(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public FacedCubemapData(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ, boolean useMipMaps) {
        TextureData v5;
        PixmapTextureData v4_1;
        PixmapTextureData v3_1;
        TextureData v2;
        PixmapTextureData v1_1;
        TextureData v6 = null;
        if(positiveX == null) {
            TextureData v1 = v6;
        }
        else {
            v1_1 = new PixmapTextureData(positiveX, ((Format)v6), useMipMaps, false);
        }

        if(negativeX == null) {
            v2 = v6;
        }
        else {
            PixmapTextureData v2_1 = new PixmapTextureData(negativeX, ((Format)v6), useMipMaps, false);
        }

        if(positiveY == null) {
            TextureData v3 = v6;
        }
        else {
            v3_1 = new PixmapTextureData(positiveY, ((Format)v6), useMipMaps, false);
        }

        if(negativeY == null) {
            TextureData v4 = v6;
        }
        else {
            v4_1 = new PixmapTextureData(negativeY, ((Format)v6), useMipMaps, false);
        }

        if(positiveZ == null) {
            v5 = v6;
        }
        else {
            PixmapTextureData v5_1 = new PixmapTextureData(positiveZ, ((Format)v6), useMipMaps, false);
        }

        if(negativeZ != null) {
            PixmapTextureData v6_1 = new PixmapTextureData(negativeZ, ((Format)v6), useMipMaps, false);
        }

        this(((TextureData)v1_1), v2, ((TextureData)v3_1), ((TextureData)v4_1), v5, v6);
    }

    public void consumeCubemapData() {
        int v14;
        for(v14 = 0; v14 < this.data.length; ++v14) {
            if(this.data[v14].getType() == TextureDataType.Custom) {
                this.data[v14].consumeCustomData(34069 + v14);
            }
            else {
                Pixmap v1 = this.data[v14].consumePixmap();
                this.data[v14].disposePixmap();
                if(this.data[v14].getFormat() != v1.getFormat()) {
                    Pixmap v0 = new Pixmap(v1.getWidth(), v1.getHeight(), this.data[v14].getFormat());
                    Blending v12 = Pixmap.getBlending();
                    Pixmap.setBlending(Blending.None);
                    v0.drawPixmap(v1, 0, 0, 0, 0, v1.getWidth(), v1.getHeight());
                    Pixmap.setBlending(v12);
                    if(this.data[v14].disposePixmap()) {
                        v1.dispose();
                    }

                    v1 = v0;
                }

                Gdx.gl.glPixelStorei(3317, 1);
                Gdx.gl.glTexImage2D(34069 + v14, 0, v1.getGLInternalFormat(), v1.getWidth(), v1.getHeight(), 0, v1.getGLFormat(), v1.getGLType(), v1.getPixels());
            }
        }
    }

    public int getHeight() {
        int v1;
        int v0 = 0;
        if(this.data[CubemapSide.PositiveZ.index] != null) {
            v1 = this.data[CubemapSide.PositiveZ.index].getHeight();
            if(v1 > 0) {
                v0 = v1;
            }
        }

        if(this.data[CubemapSide.NegativeZ.index] != null) {
            v1 = this.data[CubemapSide.NegativeZ.index].getHeight();
            if(v1 > v0) {
                v0 = v1;
            }
        }

        if(this.data[CubemapSide.PositiveX.index] != null) {
            v1 = this.data[CubemapSide.PositiveX.index].getHeight();
            if(v1 > v0) {
                v0 = v1;
            }
        }

        if(this.data[CubemapSide.NegativeX.index] != null) {
            v1 = this.data[CubemapSide.NegativeX.index].getHeight();
            if(v1 > v0) {
                v0 = v1;
            }
        }

        return v0;
    }

    public TextureData getTextureData(CubemapSide side) {
        return this.data[side.index];
    }

    public int getWidth() {
        int v0;
        int v1 = 0;
        if(this.data[CubemapSide.PositiveZ.index] != null) {
            v0 = this.data[CubemapSide.PositiveZ.index].getWidth();
            if(v0 > 0) {
                v1 = v0;
            }
        }

        if(this.data[CubemapSide.NegativeZ.index] != null) {
            v0 = this.data[CubemapSide.NegativeZ.index].getWidth();
            if(v0 > v1) {
                v1 = v0;
            }
        }

        if(this.data[CubemapSide.PositiveY.index] != null) {
            v0 = this.data[CubemapSide.PositiveY.index].getWidth();
            if(v0 > v1) {
                v1 = v0;
            }
        }

        if(this.data[CubemapSide.NegativeY.index] != null) {
            v0 = this.data[CubemapSide.NegativeY.index].getWidth();
            if(v0 > v1) {
                v1 = v0;
            }
        }

        return v1;
    }

    public boolean isComplete() {
        boolean v1;
        int v0 = 0;
        while(true) {
            if(v0 >= this.data.length) {
                break;
            }
            else if(this.data[v0] == null) {
                v1 = false;
            }
            else {
                ++v0;
                continue;
            }

            goto label_8;
        }

        v1 = true;
    label_8:
        return v1;
    }

    public boolean isManaged() {
        boolean v4;
        TextureData[] v0 = this.data;
        int v3 = v0.length;
        int v2 = 0;
        while(true) {
            if(v2 >= v3) {
                break;
            }
            else if(!v0[v2].isManaged()) {
                v4 = false;
            }
            else {
                ++v2;
                continue;
            }

            goto label_8;
        }

        v4 = true;
    label_8:
        return v4;
    }

    public boolean isPrepared() {
        return 0;
    }

    public void load(CubemapSide side, FileHandle file) {
        this.data[side.index] = Factory.loadFromFile(file, false);
    }

    public void load(CubemapSide side, Pixmap pixmap) {
        TextureData v0 = null;
        TextureData[] v2 = this.data;
        int v3 = side.index;
        if(pixmap != null) {
            PixmapTextureData v0_1 = new PixmapTextureData(pixmap, ((Format)v0), false, false);
        }

        v2[v3] = v0;
    }

    public void prepare() {
        if(!this.isComplete()) {
            throw new GdxRuntimeException("You need to complete your cubemap data before using it");
        }

        int v0;
        for(v0 = 0; v0 < this.data.length; ++v0) {
            if(!this.data[v0].isPrepared()) {
                this.data[v0].prepare();
            }
        }
    }
}

