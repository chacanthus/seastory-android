// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$Factory;
import com.badlogic.gdx.utils.Array;

public class TextureLoader extends AsynchronousAssetLoader {
    public class TextureLoaderInfo {
        TextureData data;
        String filename;
        Texture texture;

        public TextureLoaderInfo() {
            super();
        }
    }

    public class TextureParameter extends AssetLoaderParameters {
        public TextureParameter() {
            super();
            this.format = null;
            this.genMipMaps = false;
            this.texture = null;
            this.textureData = null;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
            this.wrapU = TextureWrap.ClampToEdge;
            this.wrapV = TextureWrap.ClampToEdge;
        }
    }

    TextureLoaderInfo info;

    public TextureLoader(FileHandleResolver resolver) {
        super(resolver);
        this.info = new TextureLoaderInfo();
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((TextureParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, TextureParameter parameter) {
        return null;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((TextureParameter)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
        this.info.filename = fileName;
        if(parameter == null || parameter.textureData == null) {
            Format v0 = null;
            boolean v1 = false;
            this.info.texture = null;
            if(parameter != null) {
                v0 = parameter.format;
                v1 = parameter.genMipMaps;
                this.info.texture = parameter.texture;
            }

            this.info.data = Factory.loadFromFile(file, v0, v1);
        }
        else {
            this.info.data = parameter.textureData;
            this.info.texture = parameter.texture;
        }

        if(!this.info.data.isPrepared()) {
            this.info.data.prepare();
        }
    }

    public Texture loadSync(AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
        Texture v0;
        if(this.info == null) {
            v0 = null;
        }
        else {
            v0 = this.info.texture;
            if(v0 != null) {
                v0.load(this.info.data);
            }
            else {
                v0 = new Texture(this.info.data);
            }

            if(parameter == null) {
                goto label_3;
            }

            v0.setFilter(parameter.minFilter, parameter.magFilter);
            v0.setWrap(parameter.wrapU, parameter.wrapV);
        }

    label_3:
        return v0;
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((TextureParameter)x3));
    }
}

