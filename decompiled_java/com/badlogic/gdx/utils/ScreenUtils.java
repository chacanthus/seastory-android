// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class ScreenUtils {
    public ScreenUtils() {
        super();
    }

    public static byte[] getFrameBufferPixels(int x, int y, int w, int h, boolean flipY) {
        Gdx.gl.glPixelStorei(3333, 1);
        ByteBuffer v7 = BufferUtils.newByteBuffer(w * h * 4);
        Gdx.gl.glReadPixels(x, y, w, h, 6408, 5121, ((Buffer)v7));
        byte[] v9 = new byte[w * h * 4];
        if(flipY) {
            int v11 = w * 4;
            int v8;
            for(v8 = 0; v8 < h; ++v8) {
                v7.position((h - v8 - 1) * v11);
                v7.get(v9, v8 * v11, v11);
            }
        }
        else {
            v7.clear();
            v7.get(v9);
        }

        return v9;
    }

    public static byte[] getFrameBufferPixels(boolean flipY) {
        return ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), flipY);
    }

    public static Pixmap getFrameBufferPixmap(int x, int y, int w, int h) {
        Gdx.gl.glPixelStorei(3333, 1);
        Pixmap v8 = new Pixmap(w, h, Format.RGBA8888);
        Gdx.gl.glReadPixels(x, y, w, h, 6408, 5121, v8.getPixels());
        return v8;
    }

    public static TextureRegion getFrameBufferTexture() {
        return ScreenUtils.getFrameBufferTexture(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public static TextureRegion getFrameBufferTexture(int x, int y, int w, int h) {
        int v9 = MathUtils.nextPowerOfTwo(w);
        int v7 = MathUtils.nextPowerOfTwo(h);
        Pixmap v6 = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
        Pixmap v8 = new Pixmap(v9, v7, Format.RGBA8888);
        v8.drawPixmap(v6, 0, 0);
        TextureRegion v0 = new TextureRegion(new Texture(v8), 0, h, w, -h);
        v8.dispose();
        v6.dispose();
        return v0;
    }
}

