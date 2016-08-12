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
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MipMapGenerator {
    private static boolean useHWMipMap;

    static  {
        MipMapGenerator.useHWMipMap = true;
    }

    private MipMapGenerator() {
        super();
    }

    public static void generateMipMap(int target, Pixmap pixmap, int textureWidth, int textureHeight) {
        if(!MipMapGenerator.useHWMipMap) {
            MipMapGenerator.generateMipMapCPU(target, pixmap, textureWidth, textureHeight);
        }
        else {
            if(Gdx.app.getType() != ApplicationType.Android && Gdx.app.getType() != ApplicationType.WebGL && Gdx.app.getType() != ApplicationType.iOS) {
                MipMapGenerator.generateMipMapDesktop(target, pixmap, textureWidth, textureHeight);
                return;
            }

            MipMapGenerator.generateMipMapGLES20(target, pixmap);
        }
    }

    public static void generateMipMap(Pixmap pixmap, int textureWidth, int textureHeight) {
        MipMapGenerator.generateMipMap(3553, pixmap, textureWidth, textureHeight);
    }

    private static void generateMipMapCPU(int target, Pixmap pixmap, int textureWidth, int textureHeight) {
        Gdx.gl.glTexImage2D(target, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
        if(Gdx.gl20 == null && textureWidth != textureHeight) {
            throw new GdxRuntimeException("texture width and height must be square when using mipmapping.");
        }

        int v10 = pixmap.getWidth() / 2;
        int v11 = pixmap.getHeight() / 2;
        int v14 = 1;
        Blending v22 = Pixmap.getBlending();
        Pixmap.setBlending(Blending.None);
        while(v10 > 0) {
            if(v11 <= 0) {
                break;
            }

            Pixmap v2 = new Pixmap(v10, v11, pixmap.getFormat());
            v2.drawPixmap(pixmap, 0, 0, pixmap.getWidth(), pixmap.getHeight(), 0, 0, v10, v11);
            if(v14 > 1) {
                pixmap.dispose();
            }

            pixmap = v2;
            Gdx.gl.glTexImage2D(target, v14, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            v10 = pixmap.getWidth() / 2;
            v11 = pixmap.getHeight() / 2;
            ++v14;
        }

        Pixmap.setBlending(v22);
    }

    private static void generateMipMapDesktop(int target, Pixmap pixmap, int textureWidth, int textureHeight) {
        if((Gdx.graphics.supportsExtension("GL_ARB_framebuffer_object")) || (Gdx.graphics.supportsExtension("GL_EXT_framebuffer_object")) || Gdx.gl30 != null) {
            Gdx.gl.glTexImage2D(target, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            Gdx.gl20.glGenerateMipmap(target);
        }
        else {
            MipMapGenerator.generateMipMapCPU(target, pixmap, textureWidth, textureHeight);
        }
    }

    private static void generateMipMapGLES20(int target, Pixmap pixmap) {
        Gdx.gl.glTexImage2D(target, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
        Gdx.gl20.glGenerateMipmap(target);
    }

    public static void setUseHardwareMipMap(boolean useHWMipMap) {
        MipMapGenerator.useHWMipMap = useHWMipMap;
    }
}

