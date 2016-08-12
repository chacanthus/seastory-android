// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.MipMapGenerator;
import com.badlogic.gdx.utils.Disposable;

public abstract class GLTexture implements Disposable {
    protected int glHandle;
    public final int glTarget;
    protected TextureFilter magFilter;
    protected TextureFilter minFilter;
    protected TextureWrap uWrap;
    protected TextureWrap vWrap;

    public GLTexture(int glTarget) {
        this(glTarget, Gdx.gl.glGenTexture());
    }

    public GLTexture(int glTarget, int glHandle) {
        super();
        this.minFilter = TextureFilter.Nearest;
        this.magFilter = TextureFilter.Nearest;
        this.uWrap = TextureWrap.ClampToEdge;
        this.vWrap = TextureWrap.ClampToEdge;
        this.glTarget = glTarget;
        this.glHandle = glHandle;
    }

    public void bind() {
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    public void bind(int unit) {
        Gdx.gl.glActiveTexture(33984 + unit);
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    protected void delete() {
        if(this.glHandle != 0) {
            Gdx.gl.glDeleteTexture(this.glHandle);
            this.glHandle = 0;
        }
    }

    public void dispose() {
        this.delete();
    }

    public abstract int getDepth();

    public abstract int getHeight();

    public TextureFilter getMagFilter() {
        return this.magFilter;
    }

    public TextureFilter getMinFilter() {
        return this.minFilter;
    }

    public int getTextureObjectHandle() {
        return this.glHandle;
    }

    public TextureWrap getUWrap() {
        return this.uWrap;
    }

    public TextureWrap getVWrap() {
        return this.vWrap;
    }

    public abstract int getWidth();

    public abstract boolean isManaged();

    protected abstract void reload();

    public void setFilter(TextureFilter minFilter, TextureFilter magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.bind();
        Gdx.gl.glTexParameterf(this.glTarget, 10241, ((float)minFilter.getGLEnum()));
        Gdx.gl.glTexParameterf(this.glTarget, 10240, ((float)magFilter.getGLEnum()));
    }

    public void setWrap(TextureWrap u, TextureWrap v) {
        this.uWrap = u;
        this.vWrap = v;
        this.bind();
        Gdx.gl.glTexParameterf(this.glTarget, 10242, ((float)u.getGLEnum()));
        Gdx.gl.glTexParameterf(this.glTarget, 10243, ((float)v.getGLEnum()));
    }

    public void unsafeSetFilter(TextureFilter minFilter, TextureFilter magFilter) {
        this.unsafeSetFilter(minFilter, magFilter, false);
    }

    public void unsafeSetFilter(TextureFilter minFilter, TextureFilter magFilter, boolean force) {
        if(minFilter != null && ((force) || this.minFilter != minFilter)) {
            Gdx.gl.glTexParameterf(this.glTarget, 10241, ((float)minFilter.getGLEnum()));
            this.minFilter = minFilter;
        }

        if(magFilter != null && ((force) || this.magFilter != magFilter)) {
            Gdx.gl.glTexParameterf(this.glTarget, 10240, ((float)magFilter.getGLEnum()));
            this.magFilter = magFilter;
        }
    }

    public void unsafeSetWrap(TextureWrap u, TextureWrap v) {
        this.unsafeSetWrap(u, v, false);
    }

    public void unsafeSetWrap(TextureWrap u, TextureWrap v, boolean force) {
        if(u != null && ((force) || this.uWrap != u)) {
            Gdx.gl.glTexParameterf(this.glTarget, 10242, ((float)u.getGLEnum()));
            this.uWrap = u;
        }

        if(v != null && ((force) || this.vWrap != v)) {
            Gdx.gl.glTexParameterf(this.glTarget, 10243, ((float)v.getGLEnum()));
            this.vWrap = v;
        }
    }

    protected static void uploadImageData(int target, TextureData data) {
        GLTexture.uploadImageData(target, data, 0);
    }

    public static void uploadImageData(int target, TextureData data, int miplevel) {
        if(data != null) {
            if(!data.isPrepared()) {
                data.prepare();
            }

            if(data.getType() != TextureDataType.Custom) {
                goto label_13;
            }

            data.consumeCustomData(target);
            return;
        label_13:
            Pixmap v3 = data.consumePixmap();
            boolean v15 = data.disposePixmap();
            if(data.getFormat() != v3.getFormat()) {
                Pixmap v2 = new Pixmap(v3.getWidth(), v3.getHeight(), data.getFormat());
                Blending v14 = Pixmap.getBlending();
                Pixmap.setBlending(Blending.None);
                v2.drawPixmap(v3, 0, 0, 0, 0, v3.getWidth(), v3.getHeight());
                Pixmap.setBlending(v14);
                if(data.disposePixmap()) {
                    v3.dispose();
                }

                v3 = v2;
                v15 = true;
            }

            Gdx.gl.glPixelStorei(3317, 1);
            if(data.useMipMaps()) {
                MipMapGenerator.generateMipMap(target, v3, v3.getWidth(), v3.getHeight());
            }
            else {
                Gdx.gl.glTexImage2D(target, miplevel, v3.getGLInternalFormat(), v3.getWidth(), v3.getHeight(), 0, v3.getGLFormat(), v3.getGLType(), v3.getPixels());
            }

            if(!v15) {
                return;
            }

            v3.dispose();
        }
    }
}

