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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataOutput extends DataOutputStream {
    public DataOutput(OutputStream out) {
        super(out);
    }

    public int writeInt(int value, boolean optimizePositive) throws IOException {
        int v0;
        if(!optimizePositive) {
            value = value << 1 ^ value >> 31;
        }

        if(value >>> 7 == 0) {
            this.write(((byte)value));
            v0 = 1;
        }
        else {
            this.write(((byte)(value & 127 | 128)));
            if(value >>> 14 == 0) {
                this.write(((byte)(value >>> 7)));
                v0 = 2;
            }
            else {
                this.write(((byte)(value >>> 7 | 128)));
                if(value >>> 21 == 0) {
                    this.write(((byte)(value >>> 14)));
                    v0 = 3;
                }
                else {
                    this.write(((byte)(value >>> 14 | 128)));
                    if(value >>> 28 == 0) {
                        this.write(((byte)(value >>> 21)));
                        v0 = 4;
                    }
                    else {
                        this.write(((byte)(value >>> 21 | 128)));
                        this.write(((byte)(value >>> 28)));
                        v0 = 5;
                    }
                }
            }
        }

        return v0;
    }

    public void writeString(String value) throws IOException {
        if(value == null) {
            this.write(0);
        }
        else {
            int v1 = value.length();
            if(v1 == 0) {
                this.writeByte(1);
            }
            else {
                this.writeInt(v1 + 1, true);
                int v2 = 0;
                while(v2 < v1) {
                    int v0 = value.charAt(v2);
                    if(v0 > 127) {
                        break;
                    }

                    this.write(((byte)v0));
                    ++v2;
                }

                if(v2 >= v1) {
                    return;
                }

                this.writeString_slow(value, v1, v2);
            }
        }
    }

    private void writeString_slow(String value, int charCount, int charIndex) throws IOException {
        while(charIndex < charCount) {
            int v0 = value.charAt(charIndex);
            if(v0 <= 127) {
                this.write(((byte)v0));
            }
            else if(v0 > 2047) {
                this.write(((byte)(v0 >> 12 & 15 | 224)));
                this.write(((byte)(v0 >> 6 & 63 | 128)));
                this.write(((byte)(v0 & 63 | 128)));
            }
            else {
                this.write(((byte)(v0 >> 6 & 31 | 192)));
                this.write(((byte)(v0 & 63 | 128)));
            }

            ++charIndex;
        }
    }
}

