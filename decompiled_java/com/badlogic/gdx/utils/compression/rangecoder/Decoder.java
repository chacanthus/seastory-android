// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression.rangecoder;

import java.io.IOException;
import java.io.InputStream;

public class Decoder {
    int Code;
    int Range;
    InputStream Stream;
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    static final int kNumMoveBits = 5;
    static final int kTopMask = -16777216;

    public Decoder() {
        super();
    }

    public int DecodeBit(short[] probs, int index) throws IOException {
        int v2;
        int v4 = -16777216;
        int v1 = probs[index];
        int v0 = (this.Range >>> 11) * v1;
        if((this.Code ^ -2147483648) < (-2147483648 ^ v0)) {
            this.Range = v0;
            probs[index] = ((short)((2048 - v1 >>> 5) + v1));
            if((this.Range & v4) == 0) {
                this.Code = this.Code << 8 | this.Stream.read();
                this.Range <<= 8;
            }

            v2 = 0;
        }
        else {
            this.Range -= v0;
            this.Code -= v0;
            probs[index] = ((short)(v1 - (v1 >>> 5)));
            if((this.Range & v4) == 0) {
                this.Code = this.Code << 8 | this.Stream.read();
                this.Range <<= 8;
            }

            v2 = 1;
        }

        return v2;
    }

    public final int DecodeDirectBits(int numTotalBits) throws IOException {
        int v1 = 0;
        int v0;
        for(v0 = numTotalBits; v0 != 0; --v0) {
            this.Range >>>= 1;
            int v2 = this.Code - this.Range >>> 31;
            this.Code -= this.Range & v2 - 1;
            v1 = v1 << 1 | 1 - v2;
            if((this.Range & -16777216) == 0) {
                this.Code = this.Code << 8 | this.Stream.read();
                this.Range <<= 8;
            }
        }

        return v1;
    }

    public final void Init() throws IOException {
        this.Code = 0;
        this.Range = -1;
        int v0;
        for(v0 = 0; v0 < 5; ++v0) {
            this.Code = this.Code << 8 | this.Stream.read();
        }
    }

    public static void InitBitModels(short[] probs) {
        int v0;
        for(v0 = 0; v0 < probs.length; ++v0) {
            probs[v0] = 1024;
        }
    }

    public final void ReleaseStream() {
        this.Stream = null;
    }

    public final void SetStream(InputStream stream) {
        this.Stream = stream;
    }
}

