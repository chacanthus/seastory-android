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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

public final class PropertiesUtils {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;

    private PropertiesUtils() {
        super();
    }

    private static void dumpString(StringBuilder outBuffer, String string, boolean escapeSpace, boolean escapeUnicode) {
        Character v5_1;
        int v5_2;
        char v6 = '\\';
        int v4 = string.length();
        int v2;
        for(v2 = 0; v2 < v4; ++v2) {
            char v0 = string.charAt(v2);
            if(v0 <= 61 || v0 >= 127) {
                switch(v0) {
                    case 9: {
                        goto label_46;
                    }
                    case 10: {
                        goto label_40;
                    }
                    case 12: {
                        goto label_49;
                    }
                    case 13: {
                        goto label_43;
                    }
                    case 32: {
                        goto label_35;
                    }
                    case 33: 
                    case 35: 
                    case 58: 
                    case 61: {
                        goto label_52;
                    }
                }

                if(v0 < 32 || v0 > 126) {
                    v5_2 = 1;
                }
                else {
                    v5_2 = 0;
                }

                if((v5_2 & (((int)escapeUnicode))) == 0) {
                    goto label_59;
                }

                String v1 = Integer.toHexString(v0);
                outBuffer.append("\\u");
                int v3;
                for(v3 = 0; v3 < 4 - v1.length(); ++v3) {
                    outBuffer.append('0');
                }

                outBuffer.append(v1);
                goto label_12;
            label_59:
                outBuffer.append(v0);
                goto label_12;
            label_49:
                outBuffer.append("\\f");
                goto label_12;
            label_35:
                if(v2 != 0 && !escapeSpace) {
                    goto label_12;
                }

                outBuffer.append("\\ ");
                goto label_12;
            label_52:
                outBuffer.append(v6).append(v0);
                goto label_12;
            label_40:
                outBuffer.append("\\n");
                goto label_12;
            label_43:
                outBuffer.append("\\r");
                goto label_12;
            label_46:
                outBuffer.append("\\t");
            }
            else {
                if(v0 == v6) {
                    String v5 = "\\\\";
                }
                else {
                    v5_1 = Character.valueOf(v0);
                }

                outBuffer.append(v5_1);
            }

        label_12:
        }
    }

    public static void load(ObjectMap arg21, Reader reader) throws IOException {
        String v16;
        int v15;
        char v13;
        int v8;
        if(arg21 == null) {
            throw new NullPointerException("ObjectMap cannot be null");
        }

        if(reader == null) {
            throw new NullPointerException("Reader cannot be null");
        }

        int v11 = 0;
        int v17 = 0;
        int v5 = 0;
        char[] v4 = new char[40];
        int v14 = 0;
        int v10 = -1;
        int v7 = 1;
        BufferedReader v3 = new BufferedReader(reader);
        while(true) {
        label_19:
            v8 = v3.read();
            if(v8 != -1) {
                goto label_32;
            }

            break;
        label_32:
            v13 = ((char)v8);
            if(v14 == v4.length) {
                char[] v12 = new char[v4.length * 2];
                System.arraycopy(v4, 0, v12, 0, v14);
                v4 = v12;
            }

            if(v11 == 2) {
                int v6 = Character.digit(v13, 16);
                if(v6 >= 0) {
                    v17 = (v17 << 4) + v6;
                    ++v5;
                    if(v5 < 4) {
                        continue;
                    }
                }
                else if(v5 <= 4) {
                    throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
                }

                v11 = 0;
                v15 = v14 + 1;
                v4[v14] = ((char)v17);
                if(v13 == 10) {
                    goto label_79;
                }

                v14 = v15;
                continue;
            }
            else {
                v15 = v14;
            }

        label_79:
            if(v11 == 1) {
                v11 = 0;
                switch(v13) {
                    case 10: {
                        goto label_96;
                    }
                    case 13: {
                        goto label_93;
                    }
                    case 98: {
                        goto label_99;
                    }
                    case 102: {
                        goto label_101;
                    }
                    case 110: {
                        goto label_103;
                    }
                    case 114: {
                        goto label_105;
                    }
                    case 116: {
                        goto label_107;
                    }
                    case 117: {
                        goto label_109;
                    }
                }

                goto label_84;
            label_99:
                v13 = '\b';
                goto label_84;
            label_101:
                v13 = '\f';
                goto label_84;
            label_103:
                v13 = '\n';
                goto label_84;
            label_105:
                v13 = '\r';
                goto label_84;
            label_107:
                v13 = '\t';
                goto label_84;
            label_109:
                v11 = 2;
                v5 = 0;
                v17 = 0;
                v14 = v15;
                continue;
            label_93:
                v11 = 3;
                v14 = v15;
                continue;
            label_96:
                v11 = 5;
                v14 = v15;
                continue;
            }
            else {
                switch(v13) {
                    case 10: {
                        goto label_144;
                    }
                    case 13: {
                        goto label_150;
                    }
                    case 33: 
                    case 35: {
                        goto label_128;
                    }
                    case 58: 
                    case 61: {
                        goto label_184;
                    }
                    case 92: {
                        goto label_177;
                    }
                }

                goto label_115;
            label_177:
                if(v11 == 4) {
                    v10 = v15;
                }

                v11 = 1;
                v14 = v15;
                continue;
            label_184:
                if(v10 != -1) {
                    goto label_115;
                }

                v11 = 0;
                v10 = v15;
                v14 = v15;
                continue;
            label_128:
                if(v7 == 0) {
                label_115:
                    if(Character.isSpace(v13)) {
                        if(v11 == 3) {
                            v11 = 5;
                        }

                        if(v15 == 0) {
                            goto label_236;
                        }

                        if(v15 == v10) {
                            goto label_236;
                        }

                        if(v11 != 5) {
                            goto label_191;
                        }

                        v14 = v15;
                        continue;
                    label_191:
                        if(v10 != -1) {
                            goto label_197;
                        }

                        v11 = 4;
                        v14 = v15;
                        continue;
                    }

                label_197:
                    if(v11 != 5 && v11 != 3) {
                        goto label_84;
                    }

                    v11 = 0;
                    goto label_84;
                }

                goto label_129;
            }

        label_84:
            v7 = 0;
            if(v11 == 4) {
                v10 = v15;
                v11 = 0;
            }

            v14 = v15 + 1;
            v4[v15] = v13;
            continue;
        label_144:
            if(v11 != 3) {
                goto label_150;
            }

            v11 = 5;
            v14 = v15;
            continue;
        label_150:
            v11 = 0;
            v7 = 1;
            if(v15 > 0 || v15 == 0 && v10 == 0) {
                if(v10 == -1) {
                    v10 = v15;
                }

                v16 = new String(v4, 0, v15);
                arg21.put(v16.substring(0, v10), v16.substring(v10));
            }

            v10 = -1;
            v14 = 0;
        }

        if(v11 == 2 && v5 <= 4) {
            throw new IllegalArgumentException("Invalid Unicode sequence: expected format \\uxxxx");
        }

        if(v10 == -1 && v14 > 0) {
            v10 = v14;
        }

        if(v10 >= 0) {
            v16 = new String(v4, 0, v14);
            String v9 = v16.substring(0, v10);
            String v18 = v16.substring(v10);
            if(v11 == 1) {
                v18 = v18 + "\u0000";
            }

            arg21.put(v9, v18);
        }

        return;
        do {
        label_129:
            v8 = v3.read();
            if(v8 != -1) {
                goto label_135;
            }

            goto label_133;
        label_135:
            v13 = ((char)v8);
            if(v13 == 13) {
                goto label_236;
            }
        }
        while(v13 != 10);

        v14 = v15;
        goto label_19;
    label_133:
        v14 = v15;
        goto label_19;
    label_236:
        v14 = v15;
        goto label_19;
    }

    public static void store(ObjectMap arg1, Writer writer, String comment) throws IOException {
        PropertiesUtils.storeImpl(arg1, writer, comment, false);
    }

    private static void storeImpl(ObjectMap arg6, Writer writer, String comment, boolean escapeUnicode) throws IOException {
        if(comment != null) {
            PropertiesUtils.writeComment(writer, comment);
        }

        writer.write("#");
        writer.write(new Date().toString());
        writer.write("\n");
        StringBuilder v2 = new StringBuilder(200);
        Iterator v1 = arg6.entries().iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            PropertiesUtils.dumpString(v2, ((Entry)v0).key, true, escapeUnicode);
            v2.append('=');
            PropertiesUtils.dumpString(v2, ((Entry)v0).value, false, escapeUnicode);
            writer.write("\n");
            writer.write(v2.toString());
            v2.setLength(0);
        }

        writer.flush();
    }

    private static void writeComment(Writer writer, String comment) throws IOException {
        int v10 = 255;
        int v9 = 13;
        int v8 = 10;
        writer.write("#");
        int v5 = comment.length();
        int v1 = 0;
        int v4 = 0;
        while(v1 < v5) {
            int v0 = comment.charAt(v1);
            if(v0 > v10 || v0 == v8 || v0 == v9) {
                if(v4 != v1) {
                    writer.write(comment.substring(v4, v1));
                }

                if(v0 > v10) {
                    String v2 = Integer.toHexString(v0);
                    writer.write("\\u");
                    int v3;
                    for(v3 = 0; v3 < 4 - v2.length(); ++v3) {
                        writer.write(48);
                    }

                    writer.write(v2);
                }
                else {
                    writer.write("\n");
                    if(v0 == v9 && v1 != v5 - 1 && comment.charAt(v1 + 1) == v8) {
                        ++v1;
                    }

                    if(v1 != v5 - 1) {
                        if(comment.charAt(v1 + 1) != 35) {
                            if(comment.charAt(v1 + 1) != 33) {
                                goto label_51;
                            }

                            goto label_29;
                        }
                        else {
                            goto label_29;
                        }
                    }

                label_51:
                    writer.write("#");
                }

            label_29:
                v4 = v1 + 1;
            }

            ++v1;
        }

        if(v4 != v1) {
            writer.write(comment.substring(v4, v1));
        }

        writer.write("\n");
    }
}

