// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public abstract interface ImageResolver {
    public class AssetManagerImageResolver implements ImageResolver {
        private final AssetManager assetManager;

        public AssetManagerImageResolver(AssetManager assetManager) {
            super();
            this.assetManager = assetManager;
        }

        public TextureRegion getImage(String name) {
            return new TextureRegion(this.assetManager.get(name, Texture.class));
        }
    }

    public class DirectImageResolver implements ImageResolver {
        private final ObjectMap images;

        public DirectImageResolver(ObjectMap arg1) {
            super();
            this.images = arg1;
        }

        public TextureRegion getImage(String name) {
            return new TextureRegion(this.images.get(name));
        }
    }

    public class TextureAtlasImageResolver implements ImageResolver {
        private final TextureAtlas atlas;

        public TextureAtlasImageResolver(TextureAtlas atlas) {
            super();
            this.atlas = atlas;
        }

        public TextureRegion getImage(String name) {
            return this.atlas.findRegion(name);
        }
    }

    public abstract TextureRegion getImage(String arg0);
}

