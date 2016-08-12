// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.assets.loaders.resolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class ResolutionFileResolver implements FileHandleResolver {
    public class Resolution {
        public Resolution(int portraitWidth, int portraitHeight, String folder) {
            super();
            this.portraitWidth = portraitWidth;
            this.portraitHeight = portraitHeight;
            this.folder = folder;
        }
    }

    protected final FileHandleResolver baseResolver;
    protected final Resolution[] descriptors;

    public ResolutionFileResolver(FileHandleResolver baseResolver, Resolution[] descriptors) {
        super();
        if(descriptors.length == 0) {
            throw new IllegalArgumentException("At least one Resolution needs to be supplied.");
        }

        this.baseResolver = baseResolver;
        this.descriptors = descriptors;
    }

    public static Resolution choose(Resolution[] descriptors) {
        Resolution v4;
        int v3;
        int v2;
        int v5 = Gdx.graphics.getWidth();
        int v1 = Gdx.graphics.getHeight();
        Resolution v0 = descriptors[0];
        if(v5 < v1) {
            v2 = 0;
            v3 = descriptors.length;
            while(v2 < v3) {
                v4 = descriptors[v2];
                if(v5 >= v4.portraitWidth && v4.portraitWidth >= v0.portraitWidth && v1 >= v4.portraitHeight && v4.portraitHeight >= v0.portraitHeight) {
                    v0 = descriptors[v2];
                }

                ++v2;
            }
        }
        else {
            v2 = 0;
            v3 = descriptors.length;
            while(v2 < v3) {
                v4 = descriptors[v2];
                if(v5 >= v4.portraitHeight && v4.portraitHeight >= v0.portraitHeight && v1 >= v4.portraitWidth && v4.portraitWidth >= v0.portraitWidth) {
                    v0 = descriptors[v2];
                }

                ++v2;
            }
        }

        return v0;
    }

    public FileHandle resolve(String fileName) {
        FileHandle v1 = this.baseResolver.resolve(this.resolve(new FileHandle(fileName), ResolutionFileResolver.choose(this.descriptors).folder));
        if(!v1.exists()) {
            v1 = this.baseResolver.resolve(fileName);
        }

        return v1;
    }

    protected String resolve(FileHandle originalHandle, String suffix) {
        String v1 = "";
        FileHandle v0 = originalHandle.parent();
        if(v0 != null && !v0.name().equals("")) {
            v1 = v0 + "/";
        }

        return v1 + suffix + "/" + originalHandle.name();
    }
}

