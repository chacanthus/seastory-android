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

import com.badlogic.gdx.Application$ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.FloatBuffer;

public class FloatTextureData implements TextureData {
    FloatBuffer buffer;
    int height;
    boolean isPrepared;
    int width;

    public FloatTextureData(int w, int h) {
        super();
        this.width = 0;
        this.height = 0;
        this.isPrepared = false;
        this.width = w;
        this.height = h;
    }

    public void consumeCustomData(int target) {
        if(!Gdx.graphics.supportsExtension("texture_float")) {
            throw new GdxRuntimeException("Extension OES_TEXTURE_FLOAT not supported!");
        }

        if(Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS || Gdx.app.getType() == ApplicationType.WebGL) {
            Gdx.gl.glTexImage2D(target, 0, 6408, this.width, this.height, 0, 6408, 5126, this.buffer);
        }
        else {
            Gdx.gl.glTexImage2D(target, 0, 34836, this.width, this.height, 0, 6408, 5126, this.buffer);
        }
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public Format getFormat() {
        return Format.RGBA8888;
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

        this.buffer = BufferUtils.newFloatBuffer(this.width * this.height * 4);
        this.isPrepared = true;
    }

    public boolean useMipMaps() {
        return 0;
    }
}

