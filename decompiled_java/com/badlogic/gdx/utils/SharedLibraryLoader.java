// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SharedLibraryLoader {
    public static String abi;
    public static boolean is64Bit;
    public static boolean isARM;
    public static boolean isAndroid;
    public static boolean isIos;
    public static boolean isLinux;
    public static boolean isMac;
    public static boolean isWindows;
    private static final HashSet loadedLibraries;
    private String nativesJar;

    static  {
        String v1_1;
        boolean v1;
        SharedLibraryLoader.isWindows = System.getProperty("os.name").contains("Windows");
        SharedLibraryLoader.isLinux = System.getProperty("os.name").contains("Linux");
        SharedLibraryLoader.isMac = System.getProperty("os.name").contains("Mac");
        SharedLibraryLoader.isIos = false;
        SharedLibraryLoader.isAndroid = false;
        SharedLibraryLoader.isARM = System.getProperty("os.arch").startsWith("arm");
        if((System.getProperty("os.arch").equals("amd64")) || (System.getProperty("os.arch").equals("x86_64"))) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        SharedLibraryLoader.is64Bit = v1;
        if(System.getProperty("sun.arch.abi") != null) {
            v1_1 = System.getProperty("sun.arch.abi");
        }
        else {
            v1_1 = "";
        }

        SharedLibraryLoader.abi = v1_1;
        String v0 = System.getProperty("java.runtime.name");
        if(v0 != null && (v0.contains("Android Runtime"))) {
            SharedLibraryLoader.isAndroid = true;
            SharedLibraryLoader.isWindows = false;
            SharedLibraryLoader.isLinux = false;
            SharedLibraryLoader.isMac = false;
            SharedLibraryLoader.is64Bit = false;
        }

        if(!SharedLibraryLoader.isAndroid && !SharedLibraryLoader.isWindows && !SharedLibraryLoader.isLinux && !SharedLibraryLoader.isMac) {
            SharedLibraryLoader.isIos = true;
            SharedLibraryLoader.is64Bit = false;
        }

        SharedLibraryLoader.loadedLibraries = new HashSet();
    }

    public SharedLibraryLoader() {
        super();
    }

    public SharedLibraryLoader(String nativesJar) {
        super();
        this.nativesJar = nativesJar;
    }

    private boolean canExecute(File file) {  // has try-catch handlers
        boolean v2_1;
        try {
            Method v0 = File.class.getMethod("canExecute", new Class[0]);
            if(!v0.invoke(file, new Object[0]).booleanValue()) {
                goto label_14;
            }

            v2_1 = true;
            goto label_13;
        label_14:
            Class v2_2 = File.class;
            Method v1 = v2_2.getMethod("setExecutable", Boolean.TYPE, Boolean.TYPE);
            v1.invoke(file, Boolean.valueOf(true), Boolean.valueOf(false));
            v2_1 = v0.invoke(file, new Object[0]).booleanValue();
        }
        catch(Exception v2) {
            v2_1 = false;
        }

    label_13:
        return v2_1;
    }

    private boolean canWrite(File file) {  // has try-catch handlers
        File v2;
        boolean v3 = false;
        File v1 = file.getParentFile();
        if(!file.exists()) {
            v1.mkdirs();
            if(v1.isDirectory()) {
                v2 = file;
            }
            else {
                goto label_8;
            }
        }
        else if(!file.canWrite()) {
            goto label_8;
        }
        else if(this.canExecute(file)) {
            v2 = new File(v1, UUID.randomUUID().toString());
        }
        else {
            goto label_8;
        }

        try {
            new FileOutputStream(v2).close();
            if(this.canExecute(v2)) {
                goto label_23;
            }
        }
        catch(Throwable v3_1) {
            v2.delete();
            throw v3_1;
        }
        catch(Throwable v0) {
            v2.delete();
            goto label_8;
        }

        v2.delete();
        goto label_8;
    label_23:
        v3 = true;
        v2.delete();
    label_8:
        return v3;
    }

    public String crc(InputStream input) {  // has try-catch handlers
        if(input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }

        CRC32 v1 = new CRC32();
        byte[] v0 = new byte[4096];
        try {
            while(true) {
                int v3 = input.read(v0);
                if(v3 != -1) {
                    goto label_15;
                }

                break;
            label_15:
                v1.update(v0, 0, v3);
            }
        }
        catch(Exception v2) {
            StreamUtils.closeQuietly(((Closeable)input));
        }

        return Long.toString(v1.getValue(), 16);
    }

    private File extractFile(String sourcePath, String sourceCrc, File extractedFile) throws IOException {  // has try-catch handlers
        String v2 = null;
        if(!extractedFile.exists()) {
            goto label_5;
        }

        try {
            v2 = this.crc(new FileInputStream(extractedFile));
        }
        catch(FileNotFoundException v6) {
        }

    label_5:
        if(v2 == null) {
            try {
            label_8:
                InputStream v3 = this.readFile(sourcePath);
                extractedFile.getParentFile().mkdirs();
                FileOutputStream v5 = new FileOutputStream(extractedFile);
                byte[] v0 = new byte[4096];
                while(true) {
                    int v4 = v3.read(v0);
                    if(v4 == -1) {
                        break;
                    }

                    v5.write(v0, 0, v4);
                }

                v3.close();
                v5.close();
            label_19:
                return extractedFile;
            }
            catch(IOException v1) {
                throw new GdxRuntimeException("Error extracting file: " + sourcePath + "\nTo: " + extractedFile.getAbsolutePath(), ((Throwable)v1));
            }
        }
        else if(!v2.equals(sourceCrc)) {
            goto label_8;
        }

        goto label_19;
    }

    public File extractFile(String sourcePath, String dirName) throws IOException {  // has try-catch handlers
        File v2;
        try {
            String v3 = this.crc(this.readFile(sourcePath));
            if(dirName == null) {
                dirName = v3;
            }

            v2 = this.extractFile(sourcePath, v3, this.getExtractedFile(dirName, new File(sourcePath).getName()));
        }
        catch(RuntimeException v0) {
            v2 = new File(System.getProperty("java.library.path"), sourcePath);
            if(v2.exists()) {
                goto label_8;
            }

            throw v0;
        }

    label_8:
        return v2;
    }

    private File getExtractedFile(String dirName, String fileName) {  // has try-catch handlers
        File v1;
        File v0;
        File v2 = new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + dirName, fileName);
        if(!this.canWrite(v2)) {
            goto label_17;
        }

        goto label_16;
    label_17:
        String v3 = null;
        try {
            v0 = File.createTempFile(dirName, v3);
            if(!v0.delete()) {
                goto label_27;
            }

            v1 = new File(v0, fileName);
            if(!this.canWrite(v1)) {
                goto label_27;
            }
        }
        catch(IOException v3_1) {
            goto label_27;
        }

        v2 = v1;
        goto label_16;
    label_27:
        v0 = new File(System.getProperty("user.home") + "/.libgdx/" + dirName, fileName);
        if(!this.canWrite(v0)) {
            goto label_40;
        }

        v2 = v0;
        goto label_16;
    label_40:
        v0 = new File(".temp/" + dirName, fileName);
        if(!this.canWrite(v0)) {
            goto label_16;
        }

        v2 = v0;
    label_16:
        return v2;
    }

    public void load(String libraryName) {  // has try-catch handlers
        String v1_1;
        try {
            if(!SharedLibraryLoader.isIos) {
                libraryName = this.mapLibraryName(libraryName);
                if(!SharedLibraryLoader.loadedLibraries.contains(libraryName)) {
                    try {
                        if(SharedLibraryLoader.isAndroid) {
                            System.loadLibrary(libraryName);
                        }
                        else {
                            goto label_15;
                        }

                        goto label_10;
                    }
                    catch(Throwable v0) {
                        goto label_18;
                    }
                }
            }

            return;
        }
        catch(Throwable v1) {
            goto label_14;
        }

        try {
        label_15:
            this.loadFile(libraryName);
            goto label_10;
        }
        catch(Throwable v1) {
        }
        catch(Throwable v0) {
            try {
            label_18:
                StringBuilder v3 = new StringBuilder().append("Couldn\'t load shared library \'").append(libraryName).append("\' for target: ").append(System.getProperty("os.name"));
                if(SharedLibraryLoader.is64Bit) {
                    v1_1 = ", 64-bit";
                }
                else {
                    v1_1 = ", 32-bit";
                }

                throw new GdxRuntimeException(v3.append(v1_1).toString(), v0);
            label_10:
                SharedLibraryLoader.loadedLibraries.add(libraryName);
            }
            catch(Throwable v1) {
            label_14:
                throw v1;
            }
        }
    }

    private Throwable loadFile(String sourcePath, String sourceCrc, File extractedFile) {  // has try-catch handlers
        try {
            System.load(this.extractFile(sourcePath, sourceCrc, extractedFile).getAbsolutePath());
            v0 = null;
        }
        catch(Throwable v0) {
            v0.printStackTrace();
        }

        return v0;
    }

    private void loadFile(String sourcePath) {  // has try-catch handlers
        File v1;
        String v3 = this.crc(this.readFile(sourcePath));
        String v2 = new File(sourcePath).getName();
        Throwable v0 = this.loadFile(sourcePath, v3, new File(System.getProperty("java.io.tmpdir") + "/libgdx" + System.getProperty("user.name") + "/" + v3, v2));
        if(v0 != null) {
            goto label_21;
        }

        return;
    label_21:
        String v4 = null;
        try {
            v1 = File.createTempFile(v3, v4);
            if(!v1.delete()) {
                goto label_27;
            }

            if(this.loadFile(sourcePath, v3, v1) == null) {
                return;
            }
        }
        catch(Throwable v4_1) {
        }

    label_27:
        if(this.loadFile(sourcePath, v3, new File(System.getProperty("user.home") + "/.libgdx/" + v3, v2)) == null) {
            return;
        }

        if(this.loadFile(sourcePath, v3, new File(".temp/" + v3, v2)) == null) {
            return;
        }

        v1 = new File(System.getProperty("java.library.path"), sourcePath);
        if(!v1.exists()) {
            goto label_54;
        }

        System.load(v1.getAbsolutePath());
        return;
    label_54:
        throw new GdxRuntimeException(v0);
    }

    public String mapLibraryName(String libraryName) {
        String v0;
        StringBuilder v1;
        if(SharedLibraryLoader.isWindows) {
            v1 = new StringBuilder().append(libraryName);
            if(SharedLibraryLoader.is64Bit) {
                v0 = "64.dll";
            }
            else {
                v0 = ".dll";
            }

            libraryName = v1.append(v0).toString();
        }
        else {
            if(!SharedLibraryLoader.isLinux) {
                goto label_37;
            }

            v1 = new StringBuilder().append("lib").append(libraryName);
            if(SharedLibraryLoader.isARM) {
                v0 = "arm" + SharedLibraryLoader.abi;
            }
            else {
                v0 = "";
            }

            v1 = v1.append(v0);
            if(SharedLibraryLoader.is64Bit) {
                v0 = "64.so";
            }
            else {
                v0 = ".so";
            }

            libraryName = v1.append(v0).toString();
            goto label_9;
        label_37:
            if(!SharedLibraryLoader.isMac) {
                goto label_9;
            }

            v1 = new StringBuilder().append("lib").append(libraryName);
            if(SharedLibraryLoader.is64Bit) {
                v0 = "64.dylib";
            }
            else {
                v0 = ".dylib";
            }

            libraryName = v1.append(v0).toString();
        }

    label_9:
        return libraryName;
    }

    private InputStream readFile(String path) {  // has try-catch handlers
        InputStream v3;
        if(this.nativesJar == null) {
            v3 = SharedLibraryLoader.class.getResourceAsStream("/" + path);
            if(v3 == null) {
                throw new GdxRuntimeException("Unable to read file for extraction: " + path);
            }
        }
        else {
            try {
                ZipFile v2 = new ZipFile(this.nativesJar);
                ZipEntry v0 = v2.getEntry(path);
                if(v0 == null) {
                    throw new GdxRuntimeException("Couldn\'t find \'" + path + "\' in JAR: " + this.nativesJar);
                }
                else {
                    v3 = v2.getInputStream(v0);
                }
            }
            catch(IOException v1) {
                throw new GdxRuntimeException("Error reading \'" + path + "\' in JAR: " + this.nativesJar, ((Throwable)v1));
            }
        }

        return v3;
    }
}

