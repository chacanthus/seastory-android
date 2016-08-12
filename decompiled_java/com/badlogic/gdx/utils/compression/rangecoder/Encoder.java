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
import java.io.OutputStream;

public class Encoder {
    long Low;
    private static int[] ProbPrices = null;
    int Range;
    OutputStream Stream;
    int _cache;
    int _cacheSize;
    long _position;
    static final int kBitModelTotal = 2048;
    static final int kNumBitModelTotalBits = 11;
    public static final int kNumBitPriceShiftBits = 6;
    static final int kNumMoveBits = 5;
    static final int kNumMoveReducingBits = 2;
    static final int kTopMask = -16777216;

    static  {
        Encoder.ProbPrices = new int[512];
        int v3 = 9;
        int v1;
        for(v1 = v3 - 1; v1 >= 0; --v1) {
            int v0 = 1 << v3 - v1;
            int v2;
            for(v2 = 1 << v3 - v1 - 1; v2 < v0; ++v2) {
                Encoder.ProbPrices[v2] = (v1 << 6) + (v0 - v2 << 6 >>> v3 - v1 - 1);
            }
        }
    }

    public Encoder() {
        super();
    }

    public void Encode(short[] probs, int index, int symbol) throws IOException {
        int v1 = probs[index];
        int v0 = (this.Range >>> 11) * v1;
        if(symbol == 0) {
            this.Range = v0;
            probs[index] = ((short)((2048 - v1 >>> 5) + v1));
        }
        else {
            this.Low += (((long)v0)) & 4294967295L;
            this.Range -= v0;
            probs[index] = ((short)(v1 - (v1 >>> 5)));
        }

        if((this.Range & -16777216) == 0) {
            this.Range <<= 8;
            this.ShiftLow();
        }
    }

    public void EncodeDirectBits(int v, int numTotalBits) throws IOException {
        int v0;
        for(v0 = numTotalBits - 1; v0 >= 0; --v0) {
            this.Range >>>= 1;
            if((v >>> v0 & 1) == 1) {
                this.Low += ((long)this.Range);
            }

            if((this.Range & -16777216) == 0) {
                this.Range <<= 8;
                this.ShiftLow();
            }
        }
    }

    public void FlushData() throws IOException {
        int v0;
        for(v0 = 0; v0 < 5; ++v0) {
            this.ShiftLow();
        }
    }

    public void FlushStream() throws IOException {
        this.Stream.flush();
    }

    public static int GetPrice(int Prob, int symbol) {
        return Encoder.ProbPrices[((Prob - symbol ^ -symbol) & 2047) >>> 2];
    }

    public static int GetPrice0(int Prob) {
        return Encoder.ProbPrices[Prob >>> 2];
    }

    public static int GetPrice1(int Prob) {
        return Encoder.ProbPrices[2048 - Prob >>> 2];
    }

    public long GetProcessedSizeAdd() {
        return (((long)this._cacheSize)) + this._position + 4;
    }

    public void Init() {
        this._position = 0;
        this.Low = 0;
        this.Range = -1;
        this._cacheSize = 1;
        this._cache = 0;
    }

    public static void InitBitModels(short[] probs) {
        int v0;
        for(v0 = 0; v0 < probs.length; ++v0) {
            probs[v0] = 1024;
        }
    }

    public void ReleaseStream() {
        this.Stream = null;
    }

    public void SetStream(OutputStream stream) {
        this.Stream = stream;
    }

    public void ShiftLow() throws IOException {
        int v0 = ((int)(this.Low >>> 32));
        if(v0 != 0 || this.Low < 4278190080L) {
            this._position += ((long)this._cacheSize);
            int v1 = this._cache;
            do {
                this.Stream.write(v1 + v0);
                v1 = 255;
                int v2 = this._cacheSize - 1;
                this._cacheSize = v2;
            }
            while(v2 != 0);

            this._cache = (((int)this.Low)) >>> 24;
        }

        ++this._cacheSize;
        this.Low = (this.Low & 16777215) << 8;
    }
}

