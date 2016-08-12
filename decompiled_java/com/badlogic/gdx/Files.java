// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx;

import com.badlogic.gdx.files.FileHandle;

public abstract interface Files {
    public enum FileType {
        public static final enum FileType Absolute;
        public static final enum FileType Classpath;
        public static final enum FileType External;
        public static final enum FileType Internal;
        public static final enum FileType Local;

        static  {
            FileType.Classpath = new FileType("Classpath", 0);
            FileType.Internal = new FileType("Internal", 1);
            FileType.External = new FileType("External", 2);
            FileType.Absolute = new FileType("Absolute", 3);
            FileType.Local = new FileType("Local", 4);
            FileType[] v0 = new FileType[5];
            v0[0] = FileType.Classpath;
            v0[1] = FileType.Internal;
            v0[2] = FileType.External;
            v0[3] = FileType.Absolute;
            v0[4] = FileType.Local;
            FileType.$VALUES = v0;
        }

        private FileType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static FileType valueOf(String name) {
            return Enum.valueOf(FileType.class, name);
        }

        public static FileType[] values() {
            return FileType.$VALUES.clone();
        }
    }

    public abstract FileHandle absolute(String arg0);

    public abstract FileHandle classpath(String arg0);

    public abstract FileHandle external(String arg0);

    public abstract String getExternalStoragePath();

    public abstract FileHandle getFileHandle(String arg0, FileType arg1);

    public abstract String getLocalStoragePath();

    public abstract FileHandle internal(String arg0);

    public abstract boolean isExternalStorageAvailable();

    public abstract boolean isLocalStorageAvailable();

    public abstract FileHandle local(String arg0);
}

