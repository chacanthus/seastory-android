// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression;

public class CRC {
    public static int[] Table;
    int _value;

    static  {
        int v5 = 256;
        CRC.Table = new int[v5];
        int v0;
        for(v0 = 0; v0 < v5; ++v0) {
            int v2 = v0;
            int v1;
            for(v1 = 0; v1 < 8; ++v1) {
                if((v2 & 1) != 0) {
                    v2 = v2 >>> 1 ^ -306674912;
                }
                else {
                    v2 >>>= 1;
                }
            }

            CRC.Table[v0] = v2;
        }
    }

    public CRC() {
        super();
        this._value = -1;
    }

    public int GetDigest() {
        return this._value ^ -1;
    }

    public void Init() {
        this._value = -1;
    }

    public void Update(byte[] data) {
        int v1 = data.length;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            this._value = CRC.Table[(this._value ^ data[v0]) & 255] ^ this._value >>> 8;
        }
    }

    public void Update(byte[] data, int offset, int size) {
        int v0;
        for(v0 = 0; v0 < size; ++v0) {
            this._value = CRC.Table[(this._value ^ data[offset + v0]) & 255] ^ this._value >>> 8;
        }
    }

    public void UpdateByte(int b) {
        this._value = CRC.Table[(this._value ^ b) & 255] ^ this._value >>> 8;
    }
}

