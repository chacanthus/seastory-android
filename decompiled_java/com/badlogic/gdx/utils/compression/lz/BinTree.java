// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression.lz;

import java.io.IOException;

public class BinTree extends InWindow {
    private static final int[] CrcTable = null;
    boolean HASH_ARRAY;
    int _cutValue;
    int _cyclicBufferPos;
    int _cyclicBufferSize;
    int[] _hash;
    int _hashMask;
    int _hashSizeSum;
    int _matchMaxLen;
    int[] _son;
    static final int kBT2HashSize = 65536;
    static final int kEmptyHashValue = 0;
    int kFixHashSize;
    static final int kHash2Size = 1024;
    static final int kHash3Offset = 1024;
    static final int kHash3Size = 65536;
    static final int kMaxValForNormalize = 1073741823;
    int kMinMatchCheck;
    int kNumHashDirectBytes;
    static final int kStartMaxLen = 1;

    static  {
        int v5 = 256;
        BinTree.CrcTable = new int[v5];
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

            BinTree.CrcTable[v0] = v2;
        }
    }

    public BinTree() {
        super();
        this._cyclicBufferSize = 0;
        this._cutValue = 255;
        this._hashSizeSum = 0;
        this.HASH_ARRAY = true;
        this.kNumHashDirectBytes = 0;
        this.kMinMatchCheck = 4;
        this.kFixHashSize = 66560;
    }

    public boolean Create(int historySize, int keepAddBufferBefore, int matchMaxLen, int keepAddBufferAfter) {
        boolean v3;
        if(historySize > 1073741567) {
            v3 = false;
        }
        else {
            this._cutValue = (matchMaxLen >> 1) + 16;
            super.Create(historySize + keepAddBufferBefore, matchMaxLen + keepAddBufferAfter, (historySize + keepAddBufferBefore + matchMaxLen + keepAddBufferAfter) / 2 + 256);
            this._matchMaxLen = matchMaxLen;
            int v0 = historySize + 1;
            if(this._cyclicBufferSize != v0) {
                this._cyclicBufferSize = v0;
                this._son = new int[v0 * 2];
            }

            int v1 = 65536;
            if(this.HASH_ARRAY) {
                v1 = historySize - 1;
                v1 |= v1 >> 1;
                v1 |= v1 >> 2;
                v1 |= v1 >> 4;
                v1 = (v1 | v1 >> 8) >> 1 | 65535;
                if(v1 > 16777216) {
                    v1 >>= 1;
                }

                this._hashMask = v1;
                v1 = v1 + 1 + this.kFixHashSize;
            }

            if(v1 != this._hashSizeSum) {
                this._hashSizeSum = v1;
                this._hash = new int[v1];
            }

            v3 = true;
        }

        return v3;
    }

    public int GetMatches(int[] distances) throws IOException {
        int v25;
        int v20;
        int v12;
        int v17;
        int v19;
        int v16;
        if(this._pos + this._matchMaxLen <= this._streamPos) {
            v16 = this._matchMaxLen;
            goto label_16;
        }
        else {
            v16 = this._streamPos - this._pos;
            if(v16 < this.kMinMatchCheck) {
                this.MovePos();
                v19 = 0;
            }
            else {
            label_16:
                v19 = 0;
                if(this._pos > this._cyclicBufferSize) {
                    v17 = this._pos - this._cyclicBufferSize;
                }
                else {
                    v17 = 0;
                }

                int v4 = this._bufferOffset + this._pos;
                int v18 = 1;
                int v10 = 0;
                int v11 = 0;
                if(this.HASH_ARRAY) {
                    int v24 = BinTree.CrcTable[this._bufferBase[v4] & 255] ^ this._bufferBase[v4 + 1] & 255;
                    v10 = v24 & 1023;
                    v24 ^= (this._bufferBase[v4 + 2] & 255) << 8;
                    v11 = v24 & 65535;
                    v12 = (BinTree.CrcTable[this._bufferBase[v4 + 3] & 255] << 5 ^ v24) & this._hashMask;
                }
                else {
                    v12 = this._bufferBase[v4] & 255 ^ (this._bufferBase[v4 + 1] & 255) << 8;
                }

                int v5 = this._hash[this.kFixHashSize + v12];
                if(this.HASH_ARRAY) {
                    int v6 = this._hash[v10];
                    int v7 = this._hash[v11 + 1024];
                    this._hash[v10] = this._pos;
                    this._hash[v11 + 1024] = this._pos;
                    if(v6 > v17 && this._bufferBase[this._bufferOffset + v6] == this._bufferBase[v4]) {
                        v18 = 2;
                        distances[0] = v18;
                        v19 = 2;
                        distances[1] = this._pos - v6 - 1;
                    }

                    if(v7 > v17 && this._bufferBase[this._bufferOffset + v7] == this._bufferBase[v4]) {
                        if(v7 == v6) {
                            v19 += -2;
                        }

                        v20 = v19 + 1;
                        v18 = 3;
                        distances[v19] = v18;
                        v19 = v20 + 1;
                        distances[v20] = this._pos - v7 - 1;
                        v6 = v7;
                    }

                    if(v19 == 0) {
                        goto label_194;
                    }

                    if(v6 != v5) {
                        goto label_194;
                    }

                    v19 += -2;
                    v18 = 1;
                }

            label_194:
                this._hash[this.kFixHashSize + v12] = this._pos;
                int v22 = (this._cyclicBufferPos << 1) + 1;
                int v23 = this._cyclicBufferPos << 1;
                int v15 = this.kNumHashDirectBytes;
                int v14 = v15;
                if(this.kNumHashDirectBytes != 0 && v5 > v17 && this._bufferBase[this._bufferOffset + v5 + this.kNumHashDirectBytes] != this._bufferBase[this.kNumHashDirectBytes + v4]) {
                    v20 = v19 + 1;
                    v18 = this.kNumHashDirectBytes;
                    distances[v19] = v18;
                    v19 = v20 + 1;
                    distances[v20] = this._pos - v5 - 1;
                }

                int v3 = this._cutValue;
                v20 = v19;
                while(true) {
                    if(v5 > v17) {
                        int v2 = v3 - 1;
                        if(v3 != 0) {
                            int v9 = this._pos - v5;
                            if(v9 <= this._cyclicBufferPos) {
                                v25 = this._cyclicBufferPos - v9;
                            }
                            else {
                                v25 = this._cyclicBufferPos - v9 + this._cyclicBufferSize;
                            }

                            int v8 = v25 << 1;
                            int v21 = this._bufferOffset + v5;
                            int v13 = Math.min(v14, v15);
                            if(this._bufferBase[v21 + v13] == this._bufferBase[v4 + v13]) {
                                do {
                                    ++v13;
                                    if(v13 == v16) {
                                        break;
                                    }
                                }
                                while(this._bufferBase[v21 + v13] == this._bufferBase[v4 + v13]);

                                if(v18 >= v13) {
                                    goto label_400;
                                }

                                v19 = v20 + 1;
                                v18 = v13;
                                distances[v20] = v13;
                                v20 = v19 + 1;
                                distances[v19] = v9 - 1;
                                if(v13 != v16) {
                                    goto label_400;
                                }

                                this._son[v23] = this._son[v8];
                                this._son[v22] = this._son[v8 + 1];
                                v19 = v20;
                                goto label_276;
                            }

                        label_400:
                            v19 = v20;
                            if((this._bufferBase[v21 + v13] & 255) < (this._bufferBase[v4 + v13] & 255)) {
                                this._son[v23] = v5;
                                v23 = v8 + 1;
                                v5 = this._son[v23];
                                v15 = v13;
                            }
                            else {
                                this._son[v22] = v5;
                                v22 = v8;
                                v5 = this._son[v22];
                                v14 = v13;
                            }

                            v3 = v2;
                            v20 = v19;
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }

                    goto label_276;
                }

                int[] v25_1 = this._son;
                this._son[v23] = 0;
                v25_1[v22] = 0;
                v19 = v20;
            label_276:
                this.MovePos();
            }
        }

        return v19;
    }

    public void Init() throws IOException {
        super.Init();
        int v0;
        for(v0 = 0; v0 < this._hashSizeSum; ++v0) {
            this._hash[v0] = 0;
        }

        this._cyclicBufferPos = 0;
        this.ReduceOffsets(-1);
    }

    public void MovePos() throws IOException {
        int v0 = this._cyclicBufferPos + 1;
        this._cyclicBufferPos = v0;
        if(v0 >= this._cyclicBufferSize) {
            this._cyclicBufferPos = 0;
        }

        super.MovePos();
        if(this._pos == 1073741823) {
            this.Normalize();
        }
    }

    void Normalize() {
        int v0 = this._pos - this._cyclicBufferSize;
        this.NormalizeLinks(this._son, this._cyclicBufferSize * 2, v0);
        this.NormalizeLinks(this._hash, this._hashSizeSum, v0);
        this.ReduceOffsets(v0);
    }

    void NormalizeLinks(int[] items, int numItems, int subValue) {
        int v0;
        for(v0 = 0; v0 < numItems; ++v0) {
            int v1 = items[v0];
            if(v1 <= subValue) {
                v1 = 0;
            }
            else {
                v1 -= subValue;
            }

            items[v0] = v1;
        }
    }

    public void SetCutValue(int cutValue) {
        this._cutValue = cutValue;
    }

    public void SetType(int numHashBytes) {
        boolean v0;
        int v2 = 2;
        if(numHashBytes > v2) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.HASH_ARRAY = v0;
        if(this.HASH_ARRAY) {
            this.kNumHashDirectBytes = 0;
            this.kMinMatchCheck = 4;
            this.kFixHashSize = 66560;
        }
        else {
            this.kNumHashDirectBytes = v2;
            this.kMinMatchCheck = 3;
            this.kFixHashSize = 0;
        }
    }

    public void Skip(int num) throws IOException {
        int v20;
        int v10;
        int v15;
        int v14;
        do {
            if(this._pos + this._matchMaxLen <= this._streamPos) {
                v14 = this._matchMaxLen;
                goto label_15;
            }
            else {
                v14 = this._streamPos - this._pos;
                if(v14 < this.kMinMatchCheck) {
                    this.MovePos();
                }
                else {
                label_15:
                    if(this._pos > this._cyclicBufferSize) {
                        v15 = this._pos - this._cyclicBufferSize;
                    }
                    else {
                        v15 = 0;
                    }

                    int v4 = this._bufferOffset + this._pos;
                    if(this.HASH_ARRAY) {
                        int v19 = BinTree.CrcTable[this._bufferBase[v4] & 255] ^ this._bufferBase[v4 + 1] & 255;
                        this._hash[v19 & 1023] = this._pos;
                        v19 ^= (this._bufferBase[v4 + 2] & 255) << 8;
                        this._hash[(v19 & 65535) + 1024] = this._pos;
                        v10 = (BinTree.CrcTable[this._bufferBase[v4 + 3] & 255] << 5 ^ v19) & this._hashMask;
                    }
                    else {
                        v10 = this._bufferBase[v4] & 255 ^ (this._bufferBase[v4 + 1] & 255) << 8;
                    }

                    int v5 = this._hash[this.kFixHashSize + v10];
                    this._hash[this.kFixHashSize + v10] = this._pos;
                    int v17 = (this._cyclicBufferPos << 1) + 1;
                    int v18 = this._cyclicBufferPos << 1;
                    int v13 = this.kNumHashDirectBytes;
                    int v12 = v13;
                    int v3 = this._cutValue;
                    while(true) {
                        if(v5 > v15) {
                            int v2 = v3 - 1;
                            if(v3 != 0) {
                                int v7 = this._pos - v5;
                                if(v7 <= this._cyclicBufferPos) {
                                    v20 = this._cyclicBufferPos - v7;
                                }
                                else {
                                    v20 = this._cyclicBufferPos - v7 + this._cyclicBufferSize;
                                }

                                int v6 = v20 << 1;
                                int v16 = this._bufferOffset + v5;
                                int v11 = Math.min(v12, v13);
                                if(this._bufferBase[v16 + v11] == this._bufferBase[v4 + v11]) {
                                    do {
                                        ++v11;
                                        if(v11 == v14) {
                                            break;
                                        }
                                    }
                                    while(this._bufferBase[v16 + v11] == this._bufferBase[v4 + v11]);

                                    if(v11 != v14) {
                                        goto label_265;
                                    }

                                    this._son[v18] = this._son[v6];
                                    this._son[v17] = this._son[v6 + 1];
                                    goto label_152;
                                }

                            label_265:
                                if((this._bufferBase[v16 + v11] & 255) < (this._bufferBase[v4 + v11] & 255)) {
                                    this._son[v18] = v5;
                                    v18 = v6 + 1;
                                    v5 = this._son[v18];
                                    v13 = v11;
                                }
                                else {
                                    this._son[v17] = v5;
                                    v17 = v6;
                                    v5 = this._son[v17];
                                    v12 = v11;
                                }

                                v3 = v2;
                                continue;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }

                        goto label_152;
                    }

                    int[] v20_1 = this._son;
                    this._son[v18] = 0;
                    v20_1[v17] = 0;
                label_152:
                    this.MovePos();
                }
            }

            --num;
        }
        while(num != 0);
    }
}

