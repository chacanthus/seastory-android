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

import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MipMapTextureData implements TextureData {
    TextureData[] mips;

    public MipMapTextureData(TextureData[] mipMapData) {
        super();
        this.mips = new TextureData[mipMapData.length];
        System.arraycopy(mipMapData, 0, this.mips, 0, mipMapData.length);
    }

    public void consumeCustomData(int target) {
        int v0;
        for(v0 = 0; v0 < this.mips.length; ++v0) {
            GLTexture.uploadImageData(target, this.mips[v0], v0);
        }
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("It\'s compressed, use the compressed method");
    }

    public boolean disposePixmap() {
        return 0;
    }

    public Format getFormat() {
        return this.mips[0].getFormat();
    }

    public int getHeight() {
        return this.mips[0].getHeight();
    }

    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    public int getWidth() {
        return this.mips[0].getWidth();
    }

    public boolean isManaged() {
        return 1;
    }

    public boolean isPrepared() {
        return 1;
    }

    public void prepare() {
    }

    public boolean useMipMaps() {
        return 0;
    }
}

