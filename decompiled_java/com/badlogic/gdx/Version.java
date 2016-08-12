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

import com.badlogic.gdx.utils.GdxRuntimeException;

public class Version {
    public static final int MAJOR = 0;
    public static final int MINOR = 0;
    public static final int REVISION = 0;
    public static final String VERSION = "1.5.6";

    static  {  // has try-catch handlers
        int v3;
        int v6 = 2;
        int v2 = 0;
        try {
            String[] v1 = "1.5.6".split("\\.");
            if(v1.length < 1) {
                v3 = 0;
            }
            else {
                v3 = Integer.valueOf(v1[0]).intValue();
            }

            Version.MAJOR = v3;
            if(v1.length < v6) {
                v3 = 0;
            }
            else {
                v3 = Integer.valueOf(v1[1]).intValue();
            }

            Version.MINOR = v3;
            if(v1.length >= 3) {
                v2 = Integer.valueOf(v1[2]).intValue();
            }

            Version.REVISION = v2;
            return;
        }
        catch(Throwable v0) {
            throw new GdxRuntimeException("Invalid version 1.5.6", v0);
        }
    }

    public Version() {
        super();
    }

    public static boolean isHigher(int major, int minor, int revision) {
        return Version.isHigherEqual(major, minor, revision + 1);
    }

    public static boolean isHigherEqual(int major, int minor, int revision) {
        boolean v0 = true;
        if(Version.MAJOR != major) {
            if(Version.MAJOR <= major) {
                v0 = false;
            }
        }
        else if(Version.MINOR != minor) {
            if(Version.MINOR <= minor) {
                v0 = false;
            }
        }
        else if(Version.REVISION < revision) {
            v0 = false;
        }

        return v0;
    }

    public static boolean isLower(int major, int minor, int revision) {
        return Version.isLowerEqual(major, minor, revision - 1);
    }

    public static boolean isLowerEqual(int major, int minor, int revision) {
        boolean v0 = true;
        if(Version.MAJOR != major) {
            if(Version.MAJOR >= major) {
                v0 = false;
            }
        }
        else if(Version.MINOR != minor) {
            if(Version.MINOR >= minor) {
                v0 = false;
            }
        }
        else if(Version.REVISION > revision) {
            v0 = false;
        }

        return v0;
    }
}

