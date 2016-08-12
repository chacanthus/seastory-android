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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$TextureAtlasData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$TextureAtlasData$Page;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class TextureAtlasLoader extends SynchronousAssetLoader {
    public class TextureAtlasParameter extends AssetLoaderParameters {
        public TextureAtlasParameter() {
            super();
            this.flip = false;
        }

        public TextureAtlasParameter(boolean flip) {
            super();
            this.flip = false;
            this.flip = flip;
        }
    }

    TextureAtlasData data;

    public TextureAtlasLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((TextureAtlasParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle atlasFile, TextureAtlasParameter parameter) {
        FileHandle v2 = atlasFile.parent();
        if(parameter != null) {
            this.data = new TextureAtlasData(atlasFile, v2, parameter.flip);
        }
        else {
            this.data = new TextureAtlasData(atlasFile, v2, false);
        }

        Array v0 = new Array();
        Iterator v1 = this.data.getPages().iterator();
        while(v1.hasNext()) {
            Object v3 = v1.next();
            TextureParameter v4 = new TextureParameter();
            v4.format = ((Page)v3).format;
            v4.genMipMaps = ((Page)v3).useMipMaps;
            v4.minFilter = ((Page)v3).minFilter;
            v4.magFilter = ((Page)v3).magFilter;
            v0.add(new AssetDescriptor(((Page)v3).textureFile, Texture.class, ((AssetLoaderParameters)v4)));
        }

        return v0;
    }

    public TextureAtlas load(AssetManager assetManager, String fileName, FileHandle file, TextureAtlasParameter parameter) {
        Iterator v0 = this.data.getPages().iterator();
        while(v0.hasNext()) {
            Object v1 = v0.next();
            ((Page)v1).texture = assetManager.get(((Page)v1).textureFile.path().replaceAll("\\\\", "/"), Texture.class);
        }

        return new TextureAtlas(this.data);
    }

    public Object load(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.load(x0, x1, x2, ((TextureAtlasParameter)x3));
    }
}

