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

public class Bits {
    long[] bits;

    public Bits() {
        super();
        long[] v0 = new long[1];
        v0[0] = 0;
        this.bits = v0;
    }

    public Bits(int nbits) {
        super();
        long[] v0 = new long[1];
        v0[0] = 0;
        this.bits = v0;
        this.checkCapacity(nbits >>> 6);
    }

    public void and(Bits other) {
        int v0 = Math.min(this.bits.length, other.bits.length);
        int v1;
        for(v1 = 0; v0 > v1; ++v1) {
            this.bits[v1] &= other.bits[v1];
        }

        if(this.bits.length > v0) {
            v1 = v0;
            int v2 = this.bits.length;
            while(v2 > v1) {
                this.bits[v1] = 0;
                ++v1;
            }
        }
    }

    public void andNot(Bits other) {
        int v0 = 0;
        int v1 = this.bits.length;
        int v2 = other.bits.length;
        while(v0 < v1) {
            if(v0 >= v2) {
                return;
            }

            this.bits[v0] &= other.bits[v0] ^ -1;
            ++v0;
        }
    }

    private void checkCapacity(int len) {
        if(len >= this.bits.length) {
            long[] v0 = new long[len + 1];
            System.arraycopy(this.bits, 0, v0, 0, this.bits.length);
            this.bits = v0;
        }
    }

    public void clear() {
        long[] v0 = this.bits;
        int v2 = v0.length;
        int v1;
        for(v1 = 0; v1 < v2; ++v1) {
            v0[v1] = 0;
        }
    }

    public void clear(int index) {
        int v0 = index >>> 6;
        if(v0 < this.bits.length) {
            this.bits[v0] &= 1 << (index & 63) ^ -1;
        }
    }

    public boolean containsAll(Bits other) {
        boolean v5 = false;
        long[] v0 = this.bits;
        long[] v3 = other.bits;
        int v4 = v3.length;
        int v1 = v0.length;
        int v2 = v1;
        while(true) {
            if(v2 >= v4) {
                break;
            }
            else if(v3[v2] == 0) {
                ++v2;
                continue;
            }

            goto label_10;
        }

        v2 = Math.min(v1, v4) - 1;
        while(true) {
            if(v2 < 0) {
                break;
            }
            else if((v0[v2] & v3[v2]) == v3[v2]) {
                --v2;
                continue;
            }

            goto label_10;
        }

        v5 = true;
    label_10:
        return v5;
    }

    public boolean equals(Object obj) {
        boolean v4 = true;
        if(this != (((Bits)obj))) {
            if(obj == null) {
                v4 = false;
            }
            else if(this.getClass() != obj.getClass()) {
                v4 = false;
            }
            else {
                Object v2 = obj;
                long[] v3 = ((Bits)v2).bits;
                int v0 = Math.min(this.bits.length, v3.length);
                int v1 = 0;
                while(true) {
                    if(v0 <= v1) {
                        break;
                    }
                    else if(this.bits[v1] != v3[v1]) {
                        v4 = false;
                    }
                    else {
                        ++v1;
                        continue;
                    }

                    goto label_3;
                }

                if(this.bits.length != v3.length && this.length() != ((Bits)v2).length()) {
                    v4 = false;
                }
            }
        }

    label_3:
        return v4;
    }

    public void flip(int index) {
        int v0 = index >>> 6;
        this.checkCapacity(v0);
        this.bits[v0] ^= 1 << (index & 63);
    }

    public boolean get(int index) {
        boolean v1 = false;
        int v0 = index >>> 6;
        if(v0 < this.bits.length && (this.bits[v0] & 1 << (index & 63)) != 0) {
            v1 = true;
        }

        return v1;
    }

    public boolean getAndClear(int index) {
        boolean v3 = false;
        int v2 = index >>> 6;
        if(v2 < this.bits.length) {
            long v0 = this.bits[v2];
            this.bits[v2] &= 1 << (index & 63) ^ -1;
            if(this.bits[v2] != v0) {
                v3 = true;
            }
        }

        return v3;
    }

    public boolean getAndSet(int index) {
        boolean v3;
        int v2 = index >>> 6;
        this.checkCapacity(v2);
        long v0 = this.bits[v2];
        this.bits[v2] |= 1 << (index & 63);
        if(this.bits[v2] == v0) {
            v3 = true;
        }
        else {
            v3 = false;
        }

        return v3;
    }

    public int hashCode() {
        int v2 = this.length() >>> 6;
        int v0 = 0;
        int v1;
        for(v1 = 0; v2 >= v1; ++v1) {
            v0 = v0 * 127 + (((int)(this.bits[v1] ^ this.bits[v1] >>> 32)));
        }

        return v0;
    }

    public boolean intersects(Bits other) {
        boolean v3;
        long[] v0 = this.bits;
        long[] v2 = other.bits;
        int v1 = Math.min(v0.length, v2.length) - 1;
        while(true) {
            if(v1 < 0) {
                break;
            }
            else if((v0[v1] & v2[v1]) != 0) {
                v3 = true;
            }
            else {
                --v1;
                continue;
            }

            goto label_13;
        }

        v3 = false;
    label_13:
        return v3;
    }

    public boolean isEmpty() {
        boolean v3;
        long[] v0 = this.bits;
        int v2 = v0.length;
        int v1 = 0;
        while(true) {
            if(v1 >= v2) {
                break;
            }
            else if(v0[v1] != 0) {
                v3 = false;
            }
            else {
                ++v1;
                continue;
            }

            goto label_8;
        }

        v3 = true;
    label_8:
        return v3;
    }

    public int length() {
        int v5;
        long v8 = 0;
        long[] v1 = this.bits;
        int v4;
        for(v4 = v1.length - 1; v4 >= 0; --v4) {
            long v2 = v1[v4];
            if(v2 != v8) {
                int v0 = 63;
                while(v0 >= 0) {
                    if((1 << (v0 & 63) & v2) != v8) {
                        v5 = (v4 << 6) + v0;
                        goto label_16;
                    }
                    else {
                        --v0;
                        continue;
                    }
                }
            }
        }

        v5 = 0;
    label_16:
        return v5;
    }

    public int nextClearBit(int fromIndex) {
        int v6;
        long[] v0 = this.bits;
        int v5 = fromIndex >>> 6;
        int v1 = v0.length;
        if(v5 >= v1) {
            v6 = -1;
        }
        else {
            long v2 = v0[v5];
            int v4 = fromIndex & 63;
            while(true) {
                if(v4 >= 64) {
                    break;
                }
                else if((1 << (v4 & 63) & v2) == 0) {
                    v6 = (v5 << 6) + v4;
                }
                else {
                    ++v4;
                    continue;
                }

                goto label_5;
            }

            ++v5;
            while(true) {
                if(v5 >= v1) {
                    break;
                }
                else if(v5 == 0) {
                    v6 = v5 << 6;
                }
                else {
                    v2 = v0[v5];
                    v4 = 0;
                    while(true) {
                        if(v4 >= 64) {
                            break;
                        }
                        else if((1 << (v4 & 63) & v2) == 0) {
                            v6 = (v5 << 6) + v4;
                        }
                        else {
                            ++v4;
                            continue;
                        }

                        goto label_5;
                    }

                    ++v5;
                    continue;
                }

                goto label_5;
            }

            v6 = -1;
        }

    label_5:
        return v6;
    }

    public int nextSetBit(int fromIndex) {
        int v4;
        int v6;
        long[] v0 = this.bits;
        int v5 = fromIndex >>> 6;
        int v1 = v0.length;
        if(v5 >= v1) {
            v6 = -1;
        }
        else {
            long v2 = v0[v5];
            if(v2 != 0) {
                v4 = fromIndex & 63;
                while(v4 < 64) {
                    if((1 << (v4 & 63) & v2) != 0) {
                        v6 = (v5 << 6) + v4;
                        goto label_5;
                    }
                    else {
                        ++v4;
                        continue;
                    }
                }
            }

            ++v5;
            while(v5 < v1) {
                if(v5 != 0) {
                    v2 = v0[v5];
                    if(v2 != 0) {
                        v4 = 0;
                        while(v4 < 64) {
                            if((1 << (v4 & 63) & v2) != 0) {
                                v6 = (v5 << 6) + v4;
                                goto label_5;
                            }
                            else {
                                ++v4;
                                continue;
                            }
                        }
                    }
                }

                ++v5;
            }

            v6 = -1;
        }

    label_5:
        return v6;
    }

    public int numBits() {
        return this.bits.length << 6;
    }

    public void or(Bits other) {
        int v0 = Math.min(this.bits.length, other.bits.length);
        int v1;
        for(v1 = 0; v0 > v1; ++v1) {
            this.bits[v1] |= other.bits[v1];
        }

        if(v0 < other.bits.length) {
            this.checkCapacity(other.bits.length);
            v1 = v0;
            int v2 = other.bits.length;
            while(v2 > v1) {
                this.bits[v1] = other.bits[v1];
                ++v1;
            }
        }
    }

    public void set(int index) {
        int v0 = index >>> 6;
        this.checkCapacity(v0);
        this.bits[v0] |= 1 << (index & 63);
    }

    public void xor(Bits other) {
        int v2;
        int v0 = Math.min(this.bits.length, other.bits.length);
        int v1;
        for(v1 = 0; v0 > v1; ++v1) {
            this.bits[v1] ^= other.bits[v1];
        }

        if(this.bits.length > v0) {
            v1 = other.bits.length;
            v2 = this.bits.length;
            goto label_22;
        }
        else if(v0 < other.bits.length) {
            this.checkCapacity(other.bits.length);
            v1 = v0;
            v2 = other.bits.length;
            while(true) {
                if(v2 > v1) {
                    this.bits[v1] = other.bits[v1];
                    ++v1;
                    continue;
                }

                return;
            }

        label_22:
            while(v2 > v1) {
                this.bits[v1] = 0;
                ++v1;
            }
        }
    }
}

