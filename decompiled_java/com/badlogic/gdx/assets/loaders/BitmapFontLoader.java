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
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont$BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class BitmapFontLoader extends AsynchronousAssetLoader {
    public class BitmapFontParameter extends AssetLoaderParameters {
        public BitmapFontParameter() {
            super();
            this.flip = false;
            this.genMipMaps = false;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
            this.bitmapFontData = null;
            this.atlasName = null;
        }
    }

    BitmapFontData data;

    public BitmapFontLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((BitmapFontParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, BitmapFontParameter parameter) {
        boolean v6;
        Array v0 = new Array();
        if(parameter == null || parameter.bitmapFontData == null) {
            if(parameter != null) {
                v6 = parameter.flip;
            }
            else {
                v6 = false;
            }

            this.data = new BitmapFontData(file, v6);
            if(parameter != null && parameter.atlasName != null) {
                v0.add(new AssetDescriptor(parameter.atlasName, TextureAtlas.class));
                goto label_6;
            }

            int v2;
            for(v2 = 0; v2 < this.data.getImagePaths().length; ++v2) {
                FileHandle v4 = this.resolve(this.data.getImagePath(v2));
                TextureParameter v5 = new TextureParameter();
                if(parameter != null) {
                    v5.genMipMaps = parameter.genMipMaps;
                    v5.minFilter = parameter.minFilter;
                    v5.magFilter = parameter.magFilter;
                }

                v0.add(new AssetDescriptor(v4, Texture.class, ((AssetLoaderParameters)v5)));
            }
        }
        else {
            this.data = parameter.bitmapFontData;
        }

    label_6:
        return v0;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((BitmapFontParameter)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, BitmapFontParameter parameter) {
    }

    public BitmapFont loadSync(AssetManager manager, String fileName, FileHandle file, BitmapFontParameter parameter) {
        BitmapFont v6;
        if(parameter == null || parameter.atlasName == null) {
            int v2 = this.data.getImagePaths().length;
            Array v5 = new Array(v2);
            int v1;
            for(v1 = 0; v1 < v2; ++v1) {
                v5.add(new TextureRegion(manager.get(this.data.getImagePath(v1), Texture.class)));
            }

            v6 = new BitmapFont(this.data, v5, true);
        }
        else {
            Object v0 = manager.get(parameter.atlasName, TextureAtlas.class);
            String v3 = file.sibling(this.data.imagePaths[0]).nameWithoutExtension().toString();
            AtlasRegion v4 = ((TextureAtlas)v0).findRegion(v3);
            if(v4 == null) {
                throw new GdxRuntimeException("Could not find font region " + v3 + " in atlas " + parameter.atlasName);
            }
            else {
                v6 = new BitmapFont(file, ((TextureRegion)v4));
            }
        }

        return v6;
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((BitmapFontParameter)x3));
    }
}

