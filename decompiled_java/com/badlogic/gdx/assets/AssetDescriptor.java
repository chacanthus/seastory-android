// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.assets;

import com.badlogic.gdx.files.FileHandle;

public class AssetDescriptor {
    public FileHandle file;
    public final String fileName;
    public final AssetLoaderParameters params;
    public final Class type;

    public AssetDescriptor(FileHandle file, Class arg3) {
        this(file, arg3, null);
    }

    public AssetDescriptor(FileHandle file, Class arg5, AssetLoaderParameters arg6) {
        super();
        this.fileName = file.path().replaceAll("\\\\", "/");
        this.file = file;
        this.type = arg5;
        this.params = arg6;
    }

    public AssetDescriptor(String fileName, Class arg3) {
        this(fileName, arg3, null);
    }

    public AssetDescriptor(String fileName, Class arg4, AssetLoaderParameters arg5) {
        super();
        this.fileName = fileName.replaceAll("\\\\", "/");
        this.type = arg4;
        this.params = arg5;
    }

    public String toString() {
        StringBuffer v0 = new StringBuffer();
        v0.append(this.fileName);
        v0.append(", ");
        v0.append(this.type.getName());
        return v0.toString();
    }
}

