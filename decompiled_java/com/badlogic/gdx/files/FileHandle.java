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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class FileHandle {
    protected File file;
    protected FileType type;

    public FileHandle(String fileName) {
        super();
        this.file = new File(fileName);
        this.type = FileType.Absolute;
    }

    protected FileHandle() {
        super();
    }

    public FileHandle(File file) {
        super();
        this.file = file;
        this.type = FileType.Absolute;
    }

    protected FileHandle(File file, FileType type) {
        super();
        this.file = file;
        this.type = type;
    }

    protected FileHandle(String fileName, FileType type) {
        super();
        this.type = type;
        this.file = new File(fileName);
    }

    public FileHandle child(String name) {
        FileHandle v0;
        if(this.file.getPath().length() == 0) {
            v0 = new FileHandle(new File(name), this.type);
        }
        else {
            v0 = new FileHandle(new File(this.file, name), this.type);
        }

        return v0;
    }

    private static void copyDirectory(FileHandle sourceDir, FileHandle destDir) {
        destDir.mkdirs();
        FileHandle[] v1 = sourceDir.list();
        int v2 = 0;
        int v3 = v1.length;
        while(v2 < v3) {
            FileHandle v4 = v1[v2];
            FileHandle v0 = destDir.child(v4.name());
            if(v4.isDirectory()) {
                FileHandle.copyDirectory(v4, v0);
            }
            else {
                FileHandle.copyFile(v4, v0);
            }

            ++v2;
        }
    }

    private static void copyFile(FileHandle source, FileHandle dest) {  // has try-catch handlers
        try {
            dest.write(source.read(), false);
            return;
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Error copying source file: " + source.file + " (" + source.type + ")\n" + "To destination: " + dest.file + " (" + dest.type + ")", ((Throwable)v0));
        }
    }

    public void copyTo(FileHandle dest) {
        boolean v0 = this.isDirectory();
        if(!v0) {
            if(dest.isDirectory()) {
                dest = dest.child(this.name());
            }

            FileHandle.copyFile(this, dest);
        }
        else {
            if(!dest.exists()) {
                dest.mkdirs();
                if(!dest.isDirectory()) {
                    throw new GdxRuntimeException("Destination directory cannot be created: " + dest);
                }
            }
            else if(!dest.isDirectory()) {
                throw new GdxRuntimeException("Destination exists but is not a directory: " + dest);
            }

            if(!v0) {
                dest = dest.child(this.name());
            }

            FileHandle.copyDirectory(this, dest);
        }
    }

    public boolean delete() {
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        }

        return this.file().delete();
    }

    private static boolean deleteDirectory(File file) {
        FileHandle.emptyDirectory(file, false);
        return file.delete();
    }

    public boolean deleteDirectory() {
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        }

        return FileHandle.deleteDirectory(this.file());
    }

    private static void emptyDirectory(File file, boolean preserveTree) {
        if(file.exists()) {
            File[] v0 = file.listFiles();
            if(v0 != null) {
                int v1 = 0;
                int v2 = v0.length;
                while(v1 < v2) {
                    if(!v0[v1].isDirectory()) {
                        v0[v1].delete();
                    }
                    else if(preserveTree) {
                        FileHandle.emptyDirectory(v0[v1], true);
                    }
                    else {
                        FileHandle.deleteDirectory(v0[v1]);
                    }

                    ++v1;
                }
            }
        }
    }

    public void emptyDirectory() {
        this.emptyDirectory(false);
    }

    public void emptyDirectory(boolean preserveTree) {
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot delete a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot delete an internal file: " + this.file);
        }

        FileHandle.emptyDirectory(this.file(), preserveTree);
    }

    public boolean equals(Object obj) {
        boolean v1 = false;
        if((obj instanceof FileHandle)) {
            Object v0 = obj;
            if(this.type == ((FileHandle)v0).type && (this.path().equals(((FileHandle)v0).path()))) {
                v1 = true;
            }
        }

        return v1;
    }

    private int estimateLength() {
        int v0 = ((int)this.length());
        if(v0 == 0) {
            v0 = 512;
        }

        return v0;
    }

    public boolean exists() {
        boolean v0 = true;
        switch(com.badlogic.gdx.files.FileHandle$1.$SwitchMap$com$badlogic$gdx$Files$FileType[this.type.ordinal()]) {
            case 1: {
                if(this.file().exists()) {
                    goto label_8;
                }

                goto label_12;
            }
            case 2: {
            label_12:
                if(FileHandle.class.getResource("/" + this.file.getPath().replace('\\', '/')) != null) {
                    goto label_8;
                }

                v0 = false;
                break;
            }
            default: {
                v0 = this.file().exists();
                break;
            }
        }

    label_8:
        return v0;
    }

    public String extension() {
        String v2;
        String v1 = this.file.getName();
        int v0 = v1.lastIndexOf(46);
        if(v0 == -1) {
            v2 = "";
        }
        else {
            v2 = v1.substring(v0 + 1);
        }

        return v2;
    }

    public File file() {
        File v0;
        if(this.type == FileType.External) {
            v0 = new File(Gdx.files.getExternalStoragePath(), this.file.getPath());
        }
        else {
            v0 = this.file;
        }

        return v0;
    }

    public int hashCode() {
        return (this.type.hashCode() + 37) * 67 + this.path().hashCode();
    }

    public boolean isDirectory() {
        boolean v0;
        if(this.type == FileType.Classpath) {
            v0 = false;
        }
        else {
            v0 = this.file().isDirectory();
        }

        return v0;
    }

    public long lastModified() {
        return this.file().lastModified();
    }

    public long length() {  // has try-catch handlers
        int v1_2;
        long v2;
        if(this.type != FileType.Classpath && (this.type != FileType.Internal || (this.file.exists()))) {
            v2 = this.file().length();
            goto label_13;
        }

        InputStream v0 = this.read();
        try {
            v1_2 = v0.available();
        }
        catch(Throwable v1) {
            StreamUtils.closeQuietly(((Closeable)v0));
            throw v1;
        }
        catch(Exception v1_1) {
            StreamUtils.closeQuietly(((Closeable)v0));
            v2 = 0;
            goto label_13;
        }

        v2 = ((long)v1_2);
        StreamUtils.closeQuietly(((Closeable)v0));
    label_13:
        return v2;
    }

    public FileHandle[] list() {
        FileHandle[] v0;
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }

        String[] v3 = this.file().list();
        if(v3 == null) {
            v0 = new FileHandle[0];
        }
        else {
            v0 = new FileHandle[v3.length];
            int v1 = 0;
            int v2 = v3.length;
            while(v1 < v2) {
                v0[v1] = this.child(v3[v1]);
                ++v1;
            }
        }

        return v0;
    }

    public FileHandle[] list(FileFilter filter) {
        FileHandle[] v3;
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }

        String[] v8 = this.file().list();
        if(v8 == null) {
            v3 = new FileHandle[0];
        }
        else {
            v3 = new FileHandle[v8.length];
            int v1 = 0;
            int v4 = 0;
            int v5 = v8.length;
            while(v4 < v5) {
                FileHandle v0 = this.child(v8[v4]);
                if(filter.accept(v0.file())) {
                    v3[v1] = v0;
                    ++v1;
                }

                ++v4;
            }

            if(v1 >= v8.length) {
                goto label_16;
            }

            FileHandle[] v6 = new FileHandle[v1];
            System.arraycopy(v3, 0, v6, 0, v1);
            v3 = v6;
        }

    label_16:
        return v3;
    }

    public FileHandle[] list(FilenameFilter filter) {
        FileHandle[] v2;
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }

        File v1 = this.file();
        String[] v7 = v1.list();
        if(v7 == null) {
            v2 = new FileHandle[0];
        }
        else {
            v2 = new FileHandle[v7.length];
            int v0 = 0;
            int v3 = 0;
            int v4 = v7.length;
            while(v3 < v4) {
                String v6 = v7[v3];
                if(filter.accept(v1, v6)) {
                    v2[v0] = this.child(v6);
                    ++v0;
                }

                ++v3;
            }

            if(v0 >= v7.length) {
                goto label_16;
            }

            FileHandle[] v5 = new FileHandle[v0];
            System.arraycopy(v2, 0, v5, 0, v0);
            v2 = v5;
        }

    label_16:
        return v2;
    }

    public FileHandle[] list(String suffix) {
        FileHandle[] v1;
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot list a classpath directory: " + this.file);
        }

        String[] v6 = this.file().list();
        if(v6 == null) {
            v1 = new FileHandle[0];
        }
        else {
            v1 = new FileHandle[v6.length];
            int v0 = 0;
            int v2 = 0;
            int v3 = v6.length;
            while(v2 < v3) {
                String v5 = v6[v2];
                if(v5.endsWith(suffix)) {
                    v1[v0] = this.child(v5);
                    ++v0;
                }

                ++v2;
            }

            if(v0 >= v6.length) {
                goto label_16;
            }

            FileHandle[] v4 = new FileHandle[v0];
            System.arraycopy(v1, 0, v4, 0, v0);
            v1 = v4;
        }

    label_16:
        return v1;
    }

    public void mkdirs() {
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot mkdirs with a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot mkdirs with an internal file: " + this.file);
        }

        this.file().mkdirs();
    }

    public void moveTo(FileHandle dest) {
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot move a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot move an internal file: " + this.file);
        }

        this.copyTo(dest);
        this.delete();
        if((this.exists()) && (this.isDirectory())) {
            this.deleteDirectory();
        }
    }

    public String name() {
        return this.file.getName();
    }

    public String nameWithoutExtension() {
        String v1 = this.file.getName();
        int v0 = v1.lastIndexOf(46);
        if(v0 != -1) {
            v1 = v1.substring(0, v0);
        }

        return v1;
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

        return new FileHandle(v0, this.type);
    }

    public String path() {
        return this.file.getPath().replace('\\', '/');
    }

    public String pathWithoutExtension() {
        String v1 = this.file.getPath().replace('\\', '/');
        int v0 = v1.lastIndexOf(46);
        if(v0 != -1) {
            v1 = v1.substring(0, v0);
        }

        return v1;
    }

    public InputStream read() {  // has try-catch handlers
        InputStream v1_1;
        if(this.type != FileType.Classpath) {
            if(this.type == FileType.Internal && !this.file().exists()) {
                goto label_15;
            }

            if(this.type == FileType.Local) {
                if(this.file().exists()) {
                    goto label_42;
                }

                goto label_15;
            }

            try {
            label_42:
                FileInputStream v1 = new FileInputStream(this.file());
            }
            catch(Exception v0) {
                if(this.file().isDirectory()) {
                    throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ((Throwable)v0));
                }

                throw new GdxRuntimeException("Error reading file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
        }
        else {
        label_15:
            v1_1 = FileHandle.class.getResourceAsStream("/" + this.file.getPath().replace('\\', '/'));
            if(v1_1 != null) {
                goto label_44;
            }

            throw new GdxRuntimeException("File not found: " + this.file + " (" + this.type + ")");
        }

    label_44:
        return v1_1;
    }

    public BufferedInputStream read(int bufferSize) {
        return new BufferedInputStream(this.read(), bufferSize);
    }

    public int readBytes(byte[] bytes, int offset, int size) {  // has try-catch handlers
        InputStream v2 = this.read();
        int v3;
        for(v3 = 0; true; v3 += v0) {
            int v4 = offset + v3;
            int v5 = size - v3;
            try {
                int v0 = v2.read(bytes, v4, v5);
                if(v0 > 0) {
                    goto label_9;
                }
            }
            catch(Throwable v4_1) {
            }
            catch(IOException v1) {
                try {
                    throw new GdxRuntimeException("Error reading file: " + this, ((Throwable)v1));
                }
                catch(Throwable v4_1) {
                    StreamUtils.closeQuietly(((Closeable)v2));
                    throw v4_1;
                }
            }

            StreamUtils.closeQuietly(((Closeable)v2));
            return v3 - offset;
        label_9:
        }
    }

    public byte[] readBytes() {  // has try-catch handlers
        byte[] v2_1;
        InputStream v1 = this.read();
        try {
            v2_1 = StreamUtils.copyStreamToByteArray(v1, this.estimateLength());
        }
        catch(Throwable v2) {
        }
        catch(IOException v0) {
            try {
                throw new GdxRuntimeException("Error reading file: " + this, ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)v1));
        return v2_1;
    }

    public String readString() {
        return this.readString(null);
    }

    public String readString(String charset) {  // has try-catch handlers
        InputStreamReader v4;
        StringBuilder v3 = new StringBuilder(this.estimateLength());
        if(charset == null) {
            try {
                v4 = new InputStreamReader(this.read());
                goto label_7;
            label_15:
                v4 = new InputStreamReader(this.read(), charset);
            label_7:
                char[] v0 = new char[256];
                while(true) {
                    int v2 = v4.read(v0);
                    if(v2 != -1) {
                        goto label_19;
                    }

                    goto label_12;
                label_19:
                    v3.append(v0, 0, v2);
                }
            }
            catch(IOException v1) {
                goto label_23;
            }
            catch(Throwable v6) {
                goto label_31;
            }
        }
        else {
            goto label_15;
        }

        goto label_7;
    label_12:
        StreamUtils.closeQuietly(((Closeable)v4));
        return v3.toString();
        try {
        label_23:
            throw new GdxRuntimeException("Error reading layout file: " + this, ((Throwable)v1));
        }
        catch(Throwable v6) {
        label_31:
            StreamUtils.closeQuietly(((Closeable)v4));
            throw v6;
        }
    }

    public BufferedReader reader(int bufferSize) {
        return new BufferedReader(new InputStreamReader(this.read()), bufferSize);
    }

    public BufferedReader reader(int bufferSize, String charset) {  // has try-catch handlers
        try {
            return new BufferedReader(new InputStreamReader(this.read(), charset), bufferSize);
        }
        catch(UnsupportedEncodingException v0) {
            throw new GdxRuntimeException("Error reading file: " + this, ((Throwable)v0));
        }
    }

    public Reader reader() {
        return new InputStreamReader(this.read());
    }

    public Reader reader(String charset) {  // has try-catch handlers
        InputStream v1 = this.read();
        try {
            return new InputStreamReader(v1, charset);
        }
        catch(UnsupportedEncodingException v0) {
            StreamUtils.closeQuietly(((Closeable)v1));
            throw new GdxRuntimeException("Error reading file: " + this, ((Throwable)v0));
        }
    }

    public FileHandle sibling(String name) {
        if(this.file.getPath().length() == 0) {
            throw new GdxRuntimeException("Cannot get the sibling of the root.");
        }

        return new FileHandle(new File(this.file.getParent(), name), this.type);
    }

    public static FileHandle tempDirectory(String prefix) {  // has try-catch handlers
        String v2 = null;
        try {
            File v1 = File.createTempFile(prefix, v2);
            if(!v1.delete()) {
                throw new IOException("Unable to delete temp file: " + v1);
            }

            if(!v1.mkdir()) {
                throw new IOException("Unable to create temp directory: " + v1);
            }

            return new FileHandle(v1);
        }
        catch(IOException v0) {
            throw new GdxRuntimeException("Unable to create temp file.", ((Throwable)v0));
        }
    }

    public static FileHandle tempFile(String prefix) {  // has try-catch handlers
        try {
            return new FileHandle(File.createTempFile(prefix, null));
        }
        catch(IOException v0) {
            throw new GdxRuntimeException("Unable to create temp file.", ((Throwable)v0));
        }
    }

    public String toString() {
        return this.file.getPath().replace('\\', '/');
    }

    public FileType type() {
        return this.type;
    }

    public void write(InputStream input, boolean append) {  // has try-catch handlers
        OutputStream v1;
        try {
            v1 = this.write(append);
            StreamUtils.copyStream(input, v1);
        }
        catch(Throwable v2) {
        }
        catch(Exception v0) {
            try {
                throw new GdxRuntimeException("Error stream writing to file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)input));
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)input));
        StreamUtils.closeQuietly(((Closeable)v1));
    }

    public OutputStream write(boolean append) {  // has try-catch handlers
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + this.file);
        }

        this.parent().mkdirs();
        try {
            return new FileOutputStream(this.file(), append);
        }
        catch(Exception v0) {
            if(this.file().isDirectory()) {
                throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }

            throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
        }
    }

    public OutputStream write(boolean append, int bufferSize) {
        return new BufferedOutputStream(this.write(append), bufferSize);
    }

    public void writeBytes(byte[] bytes, int offset, int length, boolean append) {  // has try-catch handlers
        OutputStream v1 = this.write(append);
        try {
            v1.write(bytes, offset, length);
        }
        catch(Throwable v2) {
        }
        catch(IOException v0) {
            try {
                throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)v1));
    }

    public void writeBytes(byte[] bytes, boolean append) {  // has try-catch handlers
        OutputStream v1 = this.write(append);
        try {
            v1.write(bytes);
        }
        catch(Throwable v2) {
        }
        catch(IOException v0) {
            try {
                throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)v1));
    }

    public void writeString(String string, boolean append) {
        this.writeString(string, append, null);
    }

    public void writeString(String string, boolean append, String charset) {  // has try-catch handlers
        Writer v1;
        try {
            v1 = this.writer(append, charset);
            v1.write(string);
        }
        catch(Throwable v2) {
        }
        catch(Exception v0) {
            try {
                throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }
            catch(Throwable v2) {
                StreamUtils.closeQuietly(((Closeable)v1));
                throw v2;
            }
        }

        StreamUtils.closeQuietly(((Closeable)v1));
    }

    public Writer writer(boolean append, String charset) {  // has try-catch handlers
        OutputStreamWriter v2;
        if(this.type == FileType.Classpath) {
            throw new GdxRuntimeException("Cannot write to a classpath file: " + this.file);
        }

        if(this.type == FileType.Internal) {
            throw new GdxRuntimeException("Cannot write to an internal file: " + this.file);
        }

        this.parent().mkdirs();
        try {
            FileOutputStream v1 = new FileOutputStream(this.file(), append);
            if(charset == null) {
                v2 = new OutputStreamWriter(((OutputStream)v1));
            }
            else {
                v2 = new OutputStreamWriter(((OutputStream)v1), charset);
            }

            return ((Writer)v2);
        }
        catch(IOException v0) {
            if(this.file().isDirectory()) {
                throw new GdxRuntimeException("Cannot open a stream to a directory: " + this.file + " (" + this.type + ")", ((Throwable)v0));
            }

            throw new GdxRuntimeException("Error writing file: " + this.file + " (" + this.type + ")", ((Throwable)v0));
        }
    }

    public Writer writer(boolean append) {
        return this.writer(append, null);
    }
}

