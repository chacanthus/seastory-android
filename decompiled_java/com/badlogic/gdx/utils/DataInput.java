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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInput extends DataInputStream {
    private char[] chars;

    public DataInput(InputStream in) {
        super(in);
        this.chars = new char[32];
    }

    public int readInt(boolean optimizePositive) throws IOException {
        int v0 = this.read();
        int v1 = v0 & 127;
        if((v0 & 128) != 0) {
            v0 = this.read();
            v1 |= (v0 & 127) << 7;
            if((v0 & 128) != 0) {
                v0 = this.read();
                v1 |= (v0 & 127) << 14;
                if((v0 & 128) != 0) {
                    v0 = this.read();
                    v1 |= (v0 & 127) << 21;
                    if((v0 & 128) != 0) {
                        v1 |= (this.read() & 127) << 28;
                    }
                }
            }
        }

        if(!optimizePositive) {
            v1 = v1 >>> 1 ^ -(v1 & 1);
        }

        return v1;
    }

    public String readString() throws IOException {
        String v5;
        int v1 = this.readInt(true);
        switch(v1) {
            case 0: {
                v5 = null;
                break;
            }
            case 1: {
                v5 = "";
                break;
            }
            default: {
                --v1;
                if(this.chars.length < v1) {
                    this.chars = new char[v1];
                }

                char[] v4 = this.chars;
                int v0 = 0;
                int v3 = 0;
                while(v3 < v1) {
                    v0 = this.read();
                    if(v0 > 127) {
                        break;
                    }

                    v4[v3] = ((char)v0);
                    ++v3;
                }

                if(v3 < v1) {
                    this.readUtf8_slow(v1, v3, v0);
                }

                v5 = new String(v4, 0, v1);
                break;
            }
        }

        return v5;
    }

    private void readUtf8_slow(int charCount, int charIndex, int b) throws IOException {
        char[] v0 = this.chars;
    label_1:
        switch(b >> 4) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                v0[charIndex] = ((char)b);
                break;
            }
            case 12: 
            case 13: {
                v0[charIndex] = ((char)((b & 31) << 6 | this.read() & 63));
                break;
            }
            case 14: {
                v0[charIndex] = ((char)((b & 15) << 12 | (this.read() & 63) << 6 | this.read() & 63));
                break;
            }
        }

        ++charIndex;
        if(charIndex >= charCount) {
            return;
        }

        b = this.read() & 255;
        goto label_1;
    }
}

