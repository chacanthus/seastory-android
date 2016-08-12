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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.Locale;

public class I18NBundleLoader extends AsynchronousAssetLoader {
    public class I18NBundleParameter extends AssetLoaderParameters {
        public I18NBundleParameter() {
            super(null, null);
        }

        public I18NBundleParameter(Locale locale, String encoding) {
            super();
            this.locale = locale;
            this.encoding = encoding;
        }

        public I18NBundleParameter(Locale locale) {
            super(locale, null);
        }
    }

    I18NBundle bundle;

    public I18NBundleLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((I18NBundleParameter)x2));
    }

    public Array getDependencies(String fileName, FileHandle file, I18NBundleParameter parameter) {
        return null;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((I18NBundleParameter)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, I18NBundleParameter parameter) {
        String v0;
        Locale v1;
        this.bundle = null;
        if(parameter == null) {
            v1 = Locale.getDefault();
            v0 = null;
        }
        else {
            if(parameter.locale == null) {
                v1 = Locale.getDefault();
            }
            else {
                v1 = parameter.locale;
            }

            v0 = parameter.encoding;
        }

        if(v0 == null) {
            this.bundle = I18NBundle.createBundle(file, v1);
        }
        else {
            this.bundle = I18NBundle.createBundle(file, v1, v0);
        }
    }

    public I18NBundle loadSync(AssetManager manager, String fileName, FileHandle file, I18NBundleParameter parameter) {
        I18NBundle v0 = this.bundle;
        this.bundle = null;
        return v0;
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((I18NBundleParameter)x3));
    }
}

