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
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ETC1TextureData implements TextureData {
    ETC1Data data;
    FileHandle file;
    int height;
    boolean isPrepared;
    boolean useMipMaps;
    int width;

    public ETC1TextureData(FileHandle file, boolean useMipMaps) {
        super();
        this.width = 0;
        this.height = 0;
        this.isPrepared = false;
        this.file = file;
        this.useMipMaps = useMipMaps;
    }

    public ETC1TextureData(FileHandle file) {
        this(file, false);
    }

    public ETC1TextureData(ETC1Data encodedImage, boolean useMipMaps) {
        super();
        this.width = 0;
        this.height = 0;
        this.isPrepared = false;
        this.data = encodedImage;
        this.useMipMaps = useMipMaps;
    }

    public void consumeCustomData(int target) {
        if(!this.isPrepared) {
            throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");
        }

        if(!Gdx.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
            Pixmap v10 = ETC1.decodeImage(this.data, Format.RGB565);
            Gdx.gl.glTexImage2D(target, 0, v10.getGLInternalFormat(), v10.getWidth(), v10.getHeight(), 0, v10.getGLFormat(), v10.getGLType(), v10.getPixels());
            if(this.useMipMaps) {
                MipMapGenerator.generateMipMap(target, v10, v10.getWidth(), v10.getHeight());
            }

            v10.dispose();
            this.useMipMaps = false;
        }
        else {
            Gdx.gl.glCompressedTexImage2D(target, 0, ETC1.ETC1_RGB8_OES, this.width, this.height, 0, this.data.compressedData.capacity() - this.data.dataOffset, this.data.compressedData);
            if(!this.useMipMaps()) {
                goto label_30;
            }

            Gdx.gl20.glGenerateMipmap(3553);
        }

    label_30:
        this.data.dispose();
        this.data = null;
        this.isPrepared = false;
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public Format getFormat() {
        return Format.RGB565;
    }

    public int getHeight() {
        return this.height;
    }

    public TextureDataType getType() {
        return TextureDataType.Custom;
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

        if(this.file == null && this.data == null) {
            throw new GdxRuntimeException("Can only load once from ETC1Data");
        }

        if(this.file != null) {
            this.data = new ETC1Data(this.file);
        }

        this.width = this.data.width;
        this.height = this.data.height;
        this.isPrepared = true;
    }

    public boolean useMipMaps() {
        return this.useMipMaps;
    }
}

