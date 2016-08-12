﻿// 도박중독 예방 캠페인
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

public class BitTreeDecoder {
    short[] Models;
    int NumBitLevels;

    public BitTreeDecoder(int numBitLevels) {
        super();
        this.NumBitLevels = numBitLevels;
        this.Models = new short[1 << numBitLevels];
    }

    public int Decode(Decoder rangeDecoder) throws IOException {
        int v1 = 1;
        int v0;
        for(v0 = this.NumBitLevels; v0 != 0; --v0) {
            v1 = (v1 << 1) + rangeDecoder.DecodeBit(this.Models, v1);
        }

        return v1 - (1 << this.NumBitLevels);
    }

    public void Init() {
        Decoder.InitBitModels(this.Models);
    }

    public static int ReverseDecode(short[] Models, int startIndex, Decoder rangeDecoder, int NumBitLevels) throws IOException {
        int v2 = 1;
        int v3 = 0;
        int v1;
        for(v1 = 0; v1 < NumBitLevels; ++v1) {
            int v0 = rangeDecoder.DecodeBit(Models, startIndex + v2);
            v2 = (v2 << 1) + v0;
            v3 |= v0 << v1;
        }

        return v3;
    }

    public int ReverseDecode(Decoder rangeDecoder) throws IOException {
        int v2 = 1;
        int v3 = 0;
        int v1;
        for(v1 = 0; v1 < this.NumBitLevels; ++v1) {
            int v0 = rangeDecoder.DecodeBit(this.Models, v2);
            v2 = (v2 << 1) + v0;
            v3 |= v0 << v1;
        }

        return v3;
    }
}

