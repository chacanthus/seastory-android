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

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.badlogic.gdx.Files$FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

public class AndroidFileHandle extends FileHandle {
    final AssetManager assets;

    AndroidFileHandle(AssetManager assets, File file, FileType type) {
        super(file, type);
        this.assets = assets;
    }

    AndroidFileHandle(AssetManager assets, String fileName, FileType type) {
        super(fileName.replace('\\', '/'), type);
        this.assets = assets;
    }

    public FileHandle child(String name) {
        AndroidFileHandle v0;
        name = name.replace('\\', '/');
        if(this.file.getPath().length() == 0) {
            v0 = new AndroidFileHandle(this.assets, new File(name), this.type);
        }
        else {
            v0 = new AndroidFileHandle(this.assets, new File(this.file, name), this.type);
        }

        return ((FileHandle)v0);
    }

    public boolean exists() {  // has try-catch handlers
        boolean v2 = true;
        if(this.type != FileType.Internal) {
            goto label_21;
        }

        String v1 = this.file.getPath();
        try {
            this.assets.open(v1).close();
            goto label_10;
        }
        catch(Exception v0) {
            try {
                if(this.assets.list(v1).length > 0) {
                    goto label_10;
                }
            }
            catch(Exception v2_1) {
                v2 = false;
                goto label_10;
            }

            v2 = false;
            goto label_10;
        }

    label_21:
        v2 = super.exists();
    label_10:
        return v2;
    }

    public File file() {
        File v0;
        if(this.type == FileType.Local) {
            v0 = new File(Gdx.files.getLocalStoragePath(), this.file.getPath());
        }
        else {
            v0 = super.file();
        }

        return v0;
    }

    public boolean isDirectory() {  // has try-catch handlers
        boolean v1 = false;
        if(this.type != FileType.Internal) {
            goto label_14;
        }

        try {
            if(this.assets.list(this.file.getPath()).length <= 0) {
                goto label_11;
            }
        }
        catch(IOException v0) {
            goto label_11;
        }

        v1 = true;
        goto label_11;
    label_14:
        v1 = super.isDirectory();
    label_11:
        return v1;
    }

    public long lastModified() {
        return super.lastModified();
    }

    public long length() {  // has try-catch handlers
        long v2;
        AssetFileDescriptor v0;
        if(this.type != FileType.Internal) {
            goto label_15;
        }

        try {
            v0 = this.assets.openFd(this.file.getPath());
            v2 = v0.getLength();
            if(v0 == null) {
                goto label_11;
            }

            goto label_10;
        }
        catch(Throwable v1) {
            if(v0 == null) {
                goto label_20;
            }

            try {
                v0.close();
            }
            catch(IOException v2_1) {
            }

        label_20:
            throw v1;
        }
        catch(IOException v1_1) {
            if(v0 == null) {
                goto label_15;
            }

            try {
                v0.close();
                goto label_15;
            }
            catch(IOException v1_1) {
                goto label_15;
            }
        }

        try {
        label_10:
            v0.close();
            goto label_11;
        }
        catch(IOException v1_1) {
            goto label_11;
        }

    label_15:
        v2 = super.length();
    label_11:
        return v2;
    }

    public FileHandle[] list() {  // has try-catch handlers
        FileHandle[] v1;
        if(this.type == FileType.Internal) {
            try {
                String[] v4 = this.assets.list(this.file.getPath());
                v1 = new FileHandle[v4.length];
                int v2 = 0;
                int v3 = v1.length;
                while(v2 < v3) {
                    v1[v2] = new AndroidFileHandle(this.assets, new File(this.file, v4[v2]), this.type);
                    ++v2;
                }
            }
            catch(Exception v0) {
                throw new GdxRuntimeException("Error listing children: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
        }
        else {
            v1 = super.list();
        }

        return v1;
    }

    public FileHandle[] list(FileFilter filter) {  // has try-catch handlers
        FileHandle[] v3;
        if(this.type == FileType.Internal) {
            try {
                String[] v8 = this.assets.list(this.file.getPath());
                v3 = new FileHandle[v8.length];
                int v1 = 0;
                int v4 = 0;
                int v5 = v3.length;
                while(v4 < v5) {
                    AndroidFileHandle v0 = new AndroidFileHandle(this.assets, new File(this.file, v8[v4]), this.type);
                    if(filter.accept(((FileHandle)v0).file())) {
                        v3[v1] = ((FileHandle)v0);
                        ++v1;
                    }

                    ++v4;
                }

                if(v1 >= v8.length) {
                    goto label_34;
                }

                FileHandle[] v6 = new FileHandle[v1];
                System.arraycopy(v3, 0, v6, 0, v1);
                v3 = v6;
            }
            catch(Exception v2) {
                throw new GdxRuntimeException("Error listing children: " + this.file + " (" + this.type + ")", ((Throwable)v2));
            }
        }
        else {
            v3 = super.list(filter);
        }

    label_34:
        return v3;
    }

    public FileHandle[] list(FilenameFilter filter) {  // has try-catch handlers
        FileHandle[] v2;
        if(this.type == FileType.Internal) {
            try {
                String[] v7 = this.assets.list(this.file.getPath());
                v2 = new FileHandle[v7.length];
                int v0 = 0;
                int v3 = 0;
                int v4 = v2.length;
                while(v3 < v4) {
                    String v6 = v7[v3];
                    if(filter.accept(this.file, v6)) {
                        v2[v0] = new AndroidFileHandle(this.assets, new File(this.file, v6), this.type);
                        ++v0;
                    }

                    ++v3;
                }

                if(v0 >= v7.length) {
                    goto label_34;
                }

                FileHandle[] v5 = new FileHandle[v0];
                System.arraycopy(v2, 0, v5, 0, v0);
                v2 = v5;
            }
            catch(Exception v1) {
                throw new GdxRuntimeException("Error listing children: " + this.file + " (" + this.type + ")", ((Throwable)v1));
            }
        }
        else {
            v2 = super.list(filter);
        }

    label_34:
        return v2;
    }

    public FileHandle[] list(String suffix) {  // has try-catch handlers
        FileHandle[] v2;
        if(this.type == FileType.Internal) {
            try {
                String[] v7 = this.assets.list(this.file.getPath());
                v2 = new FileHandle[v7.length];
                int v0 = 0;
                int v3 = 0;
                int v4 = v2.length;
                while(v3 < v4) {
                    String v6 = v7[v3];
                    if(v6.endsWith(suffix)) {
                        v2[v0] = new AndroidFileHandle(this.assets, new File(this.file, v6), this.type);
                        ++v0;
                    }

                    ++v3;
                }

                if(v0 >= v7.length) {
                    goto label_33;
                }

                FileHandle[] v5 = new FileHandle[v0];
                System.arraycopy(v2, 0, v5, 0, v0);
                v2 = v5;
            }
            catch(Exception v1) {
                throw new GdxRuntimeException("Error listing children: " + this.file + " (" + this.type + ")", ((Throwable)v1));
            }
        }
        else {
            v2 = super.list(suffix);
        }

    label_33:
        return v2;
    }

    public FileHandle parent() {
        File v0 = this.file.getParentFile();
        if(v0 == null) {
            if(this.type == FileType.Absolute) {
                v0 = new File("/");
            }
            else {
                v0 = new File("");
            }
        }

        return new AndroidFileHandle(this.assets, v0, this.type);
    }

    public InputStream read() {  // has try-catch handlers
        InputStream v1;
        if(this.type == FileType.Internal) {
            try {
                v1 = this.assets.open(this.file.getPath());
            }
            catch(IOException v0) {
                throw new GdxRuntimeException("Error reading file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
        }
        else {
            v1 = super.read();
        }

        return v1;
    }

    public FileHandle sibling(String name) {
        name = name.replace('\\', '/');
        if(this.file.getPath().length() == 0) {
            throw new GdxRuntimeException("Cannot get the sibling of the root.");
        }

        return new AndroidFileHandle(this.assets, new File(this.file.getParent(), name), this.type);
    }
}

