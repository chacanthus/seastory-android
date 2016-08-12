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

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PixmapTextureData implements TextureData {
    final boolean disposePixmap;
    final Format format;
    final boolean managed;
    final Pixmap pixmap;
    final boolean useMipMaps;

    public PixmapTextureData(Pixmap pixmap, Format format, boolean useMipMaps, boolean disposePixmap) {
        this(pixmap, format, useMipMaps, disposePixmap, false);
    }

    public PixmapTextureData(Pixmap pixmap, Format format, boolean useMipMaps, boolean disposePixmap, boolean managed) {
        super();
        this.pixmap = pixmap;
        if(format == null) {
            format = pixmap.getFormat();
        }

        this.format = format;
        this.useMipMaps = useMipMaps;
        this.disposePixmap = disposePixmap;
        this.managed = managed;
    }

    public void consumeCustomData(int target) {
        throw new GdxRuntimeException("This TextureData implementation does not upload data itself");
    }

    public Pixmap consumePixmap() {
        return this.pixmap;
    }

    public boolean disposePixmap() {
        return this.disposePixmap;
    }

    public Format getFormat() {
        return this.format;
    }

    public int getHeight() {
        return this.pixmap.getHeight();
    }

    public TextureDataType getType() {
        return TextureDataType.Pixmap;
    }

    public int getWidth() {
        return this.pixmap.getWidth();
    }

    public boolean isManaged() {
        return this.managed;
    }

    public boolean isPrepared() {
        return 1;
    }

    public void prepare() {
        throw new GdxRuntimeException("prepare() must not be called on a PixmapTextureData instance as it is already prepared.");
    }

    public boolean useMipMaps() {
        return this.useMipMaps;
    }
}

