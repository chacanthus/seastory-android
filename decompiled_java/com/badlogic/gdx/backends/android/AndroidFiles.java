// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.content.res.AssetManager;
import android.os.Environment;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files$FileType;
import com.badlogic.gdx.files.FileHandle;

public class AndroidFiles implements Files {
    protected final AssetManager assets;
    protected final String localpath;
    protected final String sdcard;

    public AndroidFiles(AssetManager assets, String localpath) {
        super();
        this.sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        this.assets = assets;
        if(!localpath.endsWith("/")) {
            localpath = localpath + "/";
        }

        this.localpath = localpath;
    }

    public AndroidFiles(AssetManager assets) {
        super();
        this.sdcard = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        this.assets = assets;
        this.localpath = this.sdcard;
    }

    public FileHandle absolute(String path) {
        return new AndroidFileHandle(null, path, FileType.Absolute);
    }

    public FileHandle classpath(String path) {
        return new AndroidFileHandle(null, path, FileType.Classpath);
    }

    public FileHandle external(String path) {
        return new AndroidFileHandle(null, path, FileType.External);
    }

    public String getExternalStoragePath() {
        return this.sdcard;
    }

    public FileHandle getFileHandle(String path, FileType type) {
        AssetManager v0;
        if(type == FileType.Internal) {
            v0 = this.assets;
        }
        else {
            v0 = null;
        }

        return new AndroidFileHandle(v0, path, type);
    }

    public String getLocalStoragePath() {
        return this.localpath;
    }

    public FileHandle internal(String path) {
        return new AndroidFileHandle(this.assets, path, FileType.Internal);
    }

    public boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public boolean isLocalStorageAvailable() {
        return 1;
    }

    public FileHandle local(String path) {
        return new AndroidFileHandle(null, path, FileType.Local);
    }
}

