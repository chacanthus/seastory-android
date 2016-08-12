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
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;
import com.badlogic.gdx.graphics.glutils.KTXTextureData;
import com.badlogic.gdx.utils.Array;

public class CubemapLoader extends AsynchronousAssetLoader {
    public class CubemapLoaderInfo {
        Cubemap cubemap;
        CubemapData data;
        String filename;

        public CubemapLoaderInfo() {
            super();
        }
    }

    public class CubemapParameter extends AssetLoaderParameters {
        public Format format;

        public CubemapParameter() {
            super();
            this.format = null;
            this.cubemap = null;
            this.cubemapData = null;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
            this.wrapU = TextureWrap.ClampToEdge;
            this.wrapV = TextureWrap.ClampToEdge;
        }
    }

    CubemapLoaderInfo info;

    public CubemapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.info = new CubemapLoaderInfo();
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((CubemapParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, CubemapParameter parameter) {
        return null;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((CubemapParameter)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, CubemapParameter parameter) {
        this.info.filename = fileName;
        if(parameter == null || parameter.cubemapData == null) {
            this.info.cubemap = null;
            if(parameter != null) {
                this.info.cubemap = parameter.cubemap;
            }

            if(!fileName.contains(".ktx") && !fileName.contains(".zktx")) {
                goto label_25;
            }

            this.info.data = new KTXTextureData(file, false);
        }
        else {
            this.info.data = parameter.cubemapData;
            this.info.cubemap = parameter.cubemap;
        }

    label_25:
        if(!this.info.data.isPrepared()) {
            this.info.data.prepare();
        }
    }

    public Cubemap loadSync(AssetManager manager, String fileName, FileHandle file, CubemapParameter parameter) {
        Cubemap v0;
        if(this.info == null) {
            v0 = null;
        }
        else {
            v0 = this.info.cubemap;
            if(v0 != null) {
                v0.load(this.info.data);
            }
            else {
                v0 = new Cubemap(this.info.data);
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
        return this.loadSync(x0, x1, x2, ((CubemapParameter)x3));
    }
}

