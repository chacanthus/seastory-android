// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d.freetype;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class FreeTypeFontGeneratorLoader extends SynchronousAssetLoader {
    public class FreeTypeFontGeneratorParameters extends AssetLoaderParameters {
        public FreeTypeFontGeneratorParameters() {
            super();
        }
    }

    public FreeTypeFontGeneratorLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((FreeTypeFontGeneratorParameters)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, FreeTypeFontGeneratorParameters parameter) {
        return null;
    }

    public FreeTypeFontGenerator load(AssetManager assetManager, String fileName, FileHandle file, FreeTypeFontGeneratorParameters parameter) {
        FreeTypeFontGenerator v0;
        if(file.extension().equals("gen")) {
            v0 = new FreeTypeFontGenerator(file.sibling(file.nameWithoutExtension()));
        }
        else {
            v0 = new FreeTypeFontGenerator(file);
        }

        return v0;
    }

    public Object load(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.load(x0, x1, x2, ((FreeTypeFontGeneratorParameters)x3));
    }
}

