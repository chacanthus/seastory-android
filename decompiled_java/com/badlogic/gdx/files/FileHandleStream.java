// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.files;

import com.badlogic.gdx.Files$FileType;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class FileHandleStream extends FileHandle {
    public FileHandleStream(String path) {
        super(new File(path), FileType.Absolute);
    }

    public FileHandle child(String name) {
        throw new UnsupportedOperationException();
    }

    public void copyTo(FileHandle dest) {
        throw new UnsupportedOperationException();
    }

    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    public boolean deleteDirectory() {
        throw new UnsupportedOperationException();
    }

    public boolean exists() {
        return 1;
    }

    public boolean isDirectory() {
        return 0;
    }

    public long length() {
        return 0;
    }

    public FileHandle[] list() {
        throw new UnsupportedOperationException();
    }

    public void mkdirs() {
        throw new UnsupportedOperationException();
    }

    public void moveTo(FileHandle dest) {
        throw new UnsupportedOperationException();
    }

    public FileHandle parent() {
        throw new UnsupportedOperationException();
    }

    public InputStream read() {
        throw new UnsupportedOperationException();
    }

    public FileHandle sibling(String name) {
        throw new UnsupportedOperationException();
    }

    public OutputStream write(boolean overwrite) {
        throw new UnsupportedOperationException();
    }
}

