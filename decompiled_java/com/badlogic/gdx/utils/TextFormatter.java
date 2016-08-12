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

import java.text.MessageFormat;
import java.util.Locale;

class TextFormatter {
    private StringBuilder buffer;
    private MessageFormat messageFormat;

    public TextFormatter(Locale locale, boolean useMessageFormat) {
        super();
        this.buffer = new StringBuilder();
        if(useMessageFormat) {
            this.messageFormat = new MessageFormat("", locale);
        }
    }

    public String format(String pattern, Object[] args) {
        String v0;
        if(this.messageFormat != null) {
            this.messageFormat.applyPattern(this.replaceEscapeChars(pattern));
            v0 = this.messageFormat.format(args);
        }
        else {
            v0 = this.simpleFormat(pattern, args);
        }

        return v0;
    }

    private String replaceEscapeChars(String pattern) {
        char v9 = '\'';
        char v8 = '{';
        this.buffer.setLength(0);
        int v1 = 0;
        int v5 = pattern.length();
        int v3;
        for(v3 = 0; v3 < v5; ++v3) {
            char v0 = pattern.charAt(v3);
            if(v0 == v9) {
                v1 = 1;
                this.buffer.append("\'\'");
            }
            else if(v0 == v8) {
                int v4 = v3 + 1;
                while(v4 < v5) {
                    if(pattern.charAt(v4) != v8) {
                        break;
                    }

                    ++v4;
                }

                int v2 = (v4 - v3) / 2;
                if(v2 > 0) {
                    v1 = 1;
                    this.buffer.append(v9);
                    do {
                        this.buffer.append(v8);
                        --v2;
                    }
                    while(v2 > 0);

                    this.buffer.append(v9);
                }

                if((v4 - v3) % 2 != 0) {
                    this.buffer.append(v8);
                }

                v3 = v4 - 1;
            }
            else {
                this.buffer.append(v0);
            }
        }

        if(v1 != 0) {
            pattern = this.buffer.toString();
        }

        return pattern;
    }

    private String simpleFormat(String pattern, Object[] args) {
        char v0;
        int v7 = 123;
        this.buffer.setLength(0);
        int v1 = 0;
        int v4 = -1;
        int v3 = pattern.length();
        int v2;
        for(v2 = 0; true; ++v2) {
            if(v2 >= v3) {
                goto label_74;
            }

            v0 = pattern.charAt(v2);
            if(v4 >= 0) {
                if(v0 != 125) {
                    goto label_57;
                }

                if(v4 >= args.length) {
                    throw new IllegalArgumentException("Argument index out of bounds: " + v4);
                }

                if(pattern.charAt(v2 - 1) == v7) {
                    throw new IllegalArgumentException("Missing argument index after a left curly brace");
                }

                if(args[v4] == null) {
                    this.buffer.append("null");
                }
                else {
                    this.buffer.append(args[v4].toString());
                }

                v4 = -1;
                goto label_21;
            label_57:
                if(v0 >= 48 && v0 <= 57) {
                    v4 = v4 * 10 + (v0 - 48);
                    goto label_21;
                }

                break;
            }
            else if(v0 == v7) {
                v1 = 1;
                if(v2 + 1 < v3 && pattern.charAt(v2 + 1) == v7) {
                    this.buffer.append(v0);
                    ++v2;
                    goto label_21;
                }

                v4 = 0;
            }
            else {
                this.buffer.append(v0);
            }

        label_21:
        }

        throw new IllegalArgumentException("Unexpected \'" + v0 + "\' while parsing argument index");
    label_74:
        if(v4 >= 0) {
            throw new IllegalArgumentException("Unmatched braces in the pattern.");
        }

        if(v1 != 0) {
            pattern = this.buffer.toString();
        }

        return pattern;
    }
}

