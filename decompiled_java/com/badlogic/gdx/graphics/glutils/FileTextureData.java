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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class FileTextureData implements TextureData {
    public static boolean copyToPOT;
    final FileHandle file;
    Format format;
    int height;
    boolean isPrepared;
    Pixmap pixmap;
    boolean useMipMaps;
    int width;

    public FileTextureData(FileHandle file, Pixmap preloadedPixmap, Format format, boolean useMipMaps) {
        super();
        this.width = 0;
        this.height = 0;
        this.isPrepared = false;
        this.file = file;
        this.pixmap = preloadedPixmap;
        this.format = format;
        this.useMipMaps = useMipMaps;
        if(this.pixmap != null) {
            this.pixmap = this.ensurePot(this.pixmap);
            this.width = this.pixmap.getWidth();
            this.height = this.pixmap.getHeight();
            if(format == null) {
                this.format = this.pixmap.getFormat();
            }
        }
    }

    public void consumeCustomData(int target) {
        throw new GdxRuntimeException("This TextureData implementation does not upload data itself");
    }

    public Pixmap consumePixmap() {
        if(!this.isPrepared) {
            throw new GdxRuntimeException("Call prepare() before calling getPixmap()");
        }

        this.isPrepared = false;
        Pixmap v0 = this.pixmap;
        this.pixmap = null;
        return v0;
    }

    public boolean disposePixmap() {
        return 1;
    }

    private Pixmap ensurePot(Pixmap pixmap) {
        Pixmap v0;
        if(Gdx.gl20 != null || !FileTextureData.copyToPOT) {
        label_20:
            v0 = pixmap;
        }
        else {
            int v6 = pixmap.getWidth();
            int v7 = pixmap.getHeight();
            int v9 = MathUtils.nextPowerOfTwo(v6);
            int v8 = MathUtils.nextPowerOfTwo(v7);
            if(v6 == v9 && v7 == v8) {
                goto label_20;
            }

            v0 = new Pixmap(v9, v8, pixmap.getFormat());
            v0.drawPixmap(pixmap, 0, 0, 0, 0, v6, v7);
            pixmap.dispose();
        }

        return v0;
    }

    public FileHandle getFileHandle() {
        return this.file;
    }

    public Format getFormat() {
        return this.format;
    }

    public int getHeight() {
        return this.height;
    }

    public TextureDataType getType() {
        return TextureDataType.Pixmap;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isManaged() {
        return 1;
    }

    public boolean isPrepared() {
        return this.isPrepared;
    }

    public void prepare() {
        if(this.isPrepared) {
            throw new GdxRuntimeException("Already prepared");
        }

        if(this.pixmap == null) {
            if(this.file.extension().equals("cim")) {
                this.pixmap = PixmapIO.readCIM(this.file);
            }
            else {
                this.pixmap = this.ensurePot(new Pixmap(this.file));
            }

            this.width = this.pixmap.getWidth();
            this.height = this.pixmap.getHeight();
            if(this.format != null) {
                goto label_26;
            }

            this.format = this.pixmap.getFormat();
        }

    label_26:
        this.isPrepared = true;
    }

    public boolean useMipMaps() {
        return this.useMipMaps;
    }
}

