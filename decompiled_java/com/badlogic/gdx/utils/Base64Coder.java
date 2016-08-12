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

public class Base64Coder {
    private static char[] map1 = null;
    private static byte[] map2 = null;
    private static final String systemLineSeparator = "\n";

    static  {
        int v6 = 64;
        Base64Coder.map1 = new char[v6];
        char v0 = 'A';
        int v2;
        for(v2 = 0; v0 <= 90; ++v2) {
            Base64Coder.map1[v2] = v0;
            v0 = ((char)(v0 + 1));
        }

        v0 = 'a';
        while(v0 <= 122) {
            Base64Coder.map1[v2] = v0;
            v0 = ((char)(v0 + 1));
            ++v2;
        }

        v0 = '0';
        while(v0 <= 57) {
            Base64Coder.map1[v2] = v0;
            v0 = ((char)(v0 + 1));
            ++v2;
        }

        Base64Coder.map1[v2] = '+';
        Base64Coder.map1[v2 + 1] = '/';
        Base64Coder.map2 = new byte[128];
        int v1;
        for(v1 = 0; v1 < Base64Coder.map2.length; ++v1) {
            Base64Coder.map2[v1] = -1;
        }

        for(v1 = 0; v1 < v6; ++v1) {
            Base64Coder.map2[Base64Coder.map1[v1]] = ((byte)v1);
        }
    }

    private Base64Coder() {
        super();
    }

    public static byte[] decode(String s) {
        return Base64Coder.decode(s.toCharArray());
    }

    public static byte[] decode(char[] in) {
        return Base64Coder.decode(in, 0, in.length);
    }

    public static byte[] decode(char[] in, int iOff, int iLen) {
        int v9;
        int v8;
        if(iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }

        while(iLen > 0) {
            if(in[iOff + iLen - 1] != 61) {
                break;
            }

            --iLen;
        }

        int v16 = iLen * 3 / 4;
        byte[] v19 = new byte[v16];
        int v10 = iOff + iLen;
        int v18 = 0;
        int v12 = iOff;
        while(true) {
            if(v12 >= v10) {
                goto label_103;
            }

            int v11 = v12 + 1;
            int v6 = in[v12];
            v12 = v11 + 1;
            int v7 = in[v11];
            if(v12 < v10) {
                v8 = in[v12];
                ++v12;
            }
            else {
                v8 = 65;
            }

            if(v12 < v10) {
                v11 = v12 + 1;
                v9 = in[v12];
            }
            else {
                v9 = 65;
                v11 = v12;
            }

            if(v6 <= 127 && v7 <= 127 && v8 <= 127 && v9 <= 127) {
                int v2 = Base64Coder.map2[v6];
                int v3 = Base64Coder.map2[v7];
                int v4 = Base64Coder.map2[v8];
                int v5 = Base64Coder.map2[v9];
                if(v2 >= 0 && v3 >= 0 && v4 >= 0 && v5 >= 0) {
                    int v14 = (v3 & 15) << 4 | v4 >>> 2;
                    int v15 = (v4 & 3) << 6 | v5;
                    int v17 = v18 + 1;
                    v19[v18] = ((byte)(v2 << 2 | v3 >>> 4));
                    if(v17 < v16) {
                        v18 = v17 + 1;
                        v19[v17] = ((byte)v14);
                    }
                    else {
                        v18 = v17;
                    }

                    if(v18 < v16) {
                        v17 = v18 + 1;
                        v19[v18] = ((byte)v15);
                    }
                    else {
                        v17 = v18;
                    }

                    v18 = v17;
                    v12 = v11;
                    continue;
                }

                break;
            }

            goto label_49;
        }

        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
    label_49:
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
    label_103:
        return v19;
    }

    public static byte[] decodeLines(String s) {
        char[] v0 = new char[s.length()];
        int v3 = 0;
        int v2;
        for(v2 = 0; v2 < s.length(); ++v2) {
            char v1 = s.charAt(v2);
            if(v1 != 32 && v1 != 13 && v1 != 10 && v1 != 9) {
                v0[v3] = v1;
                ++v3;
            }
        }

        return Base64Coder.decode(v0, 0, v3);
    }

    public static String decodeString(String s) {
        return new String(Base64Coder.decode(s));
    }

    public static char[] encode(byte[] in) {
        return Base64Coder.encode(in, 0, in.length);
    }

    public static char[] encode(byte[] in, int iOff, int iLen) {
        char v15;
        int v2;
        int v1;
        int v10 = (iLen * 4 + 2) / 3;
        char[] v14 = new char[(iLen + 2) / 3 * 4];
        int v3 = iOff + iLen;
        int v13 = 0;
        int v5;
        for(v5 = iOff; v5 < v3; v5 = v4) {
            int v4 = v5 + 1;
            int v0 = in[v5] & 255;
            if(v4 < v3) {
                v5 = v4 + 1;
                v1 = in[v4] & 255;
            }
            else {
                v1 = 0;
                v5 = v4;
            }

            if(v5 < v3) {
                v4 = v5 + 1;
                v2 = in[v5] & 255;
            }
            else {
                v2 = 0;
                v4 = v5;
            }

            int v8 = (v1 & 15) << 2 | v2 >>> 6;
            int v9 = v2 & 63;
            int v12 = v13 + 1;
            v14[v13] = Base64Coder.map1[v0 >>> 2];
            v13 = v12 + 1;
            v14[v12] = Base64Coder.map1[(v0 & 3) << 4 | v1 >>> 4];
            if(v13 < v10) {
                v15 = Base64Coder.map1[v8];
            }
            else {
                v15 = '=';
            }

            v14[v13] = v15;
            v12 = v13 + 1;
            if(v12 < v10) {
                v15 = Base64Coder.map1[v9];
            }
            else {
                v15 = '=';
            }

            v14[v12] = v15;
            v13 = v12 + 1;
        }

        return v14;
    }

    public static char[] encode(byte[] in, int iLen) {
        return Base64Coder.encode(in, 0, iLen);
    }

    public static String encodeLines(byte[] in) {
        return Base64Coder.encodeLines(in, 0, in.length, 76, "\n");
    }

    public static String encodeLines(byte[] in, int iOff, int iLen, int lineLen, String lineSeparator) {
        int v0 = lineLen * 3 / 4;
        if(v0 <= 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder v1 = new StringBuilder((iLen + 2) / 3 * 4 + lineSeparator.length() * ((iLen + v0 - 1) / v0));
        int v3;
        for(v3 = 0; v3 < iLen; v3 += v4) {
            int v4 = Math.min(iLen - v3, v0);
            v1.append(Base64Coder.encode(in, iOff + v3, v4));
            v1.append(lineSeparator);
        }

        return v1.toString();
    }

    public static String encodeString(String s) {
        return new String(Base64Coder.encode(s.getBytes()));
    }
}

