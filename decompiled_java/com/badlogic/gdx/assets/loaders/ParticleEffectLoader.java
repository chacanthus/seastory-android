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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectLoader extends SynchronousAssetLoader {
    public class ParticleEffectParameter extends AssetLoaderParameters {
        public ParticleEffectParameter() {
            super();
        }
    }

    public ParticleEffectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((ParticleEffectParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, ParticleEffectParameter param) {
        Array v0 = null;
        if(param != null && param.atlasFile != null) {
            v0 = new Array();
            v0.add(new AssetDescriptor(param.atlasFile, TextureAtlas.class));
        }

        return v0;
    }

    public ParticleEffect load(AssetManager am, String fileName, FileHandle file, ParticleEffectParameter param) {
        ParticleEffect v0 = new ParticleEffect();
        if(param == null || param.atlasFile == null) {
            if(param != null && param.imagesDir != null) {
                v0.load(file, param.imagesDir);
                goto label_9;
            }

            v0.load(file, file.parent());
        }
        else {
            v0.load(file, am.get(param.atlasFile, TextureAtlas.class), param.atlasPrefix);
        }

    label_9:
        return v0;
    }

    public Object load(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.load(x0, x1, x2, ((ParticleEffectParameter)x3));
    }
}

