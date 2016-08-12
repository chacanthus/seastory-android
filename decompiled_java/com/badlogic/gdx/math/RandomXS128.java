// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

import java.util.Random;

public class RandomXS128 extends Random {
    private static final double NORM_DOUBLE = 0;
    private static final double NORM_FLOAT = 0;
    private long seed0;
    private long seed1;

    public RandomXS128() {
        super();
        this.setSeed(new Random().nextLong());
    }

    public RandomXS128(long seed) {
        super();
        this.setSeed(seed);
    }

    public RandomXS128(long seed0, long seed1) {
        super();
        this.setState(seed0, seed1);
    }

    public long getState(int seed) {
        long v0;
        if(seed == 0) {
            v0 = this.seed0;
        }
        else {
            v0 = this.seed1;
        }

        return v0;
    }

    private static final long murmurHash3(long x) {
        x = (x ^ x >>> 33) * -49064778989728563L;
        x = (x ^ x >>> 33) * -4265267296055464877L;
        return x ^ x >>> 33;
    }

    protected final int next(int bits) {
        return ((int)(this.nextLong() & (1 << bits) - 1));
    }

    public boolean nextBoolean() {
        boolean v0;
        if((this.nextLong() & 1) != 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void nextBytes(byte[] bytes) {
        int v3;
        int v5 = 8;
        int v2 = bytes.length;
    label_3:
        if(v2 == 0) {
            return;
        }

        if(v2 < v5) {
            v3 = v2;
        }
        else {
            v3 = v5;
        }

        long v0 = this.nextLong();
        int v4;
        for(v4 = v3; true; v4 = v3) {
            v3 = v4 - 1;
            if(v4 == 0) {
                goto label_3;
            }

            --v2;
            bytes[v2] = ((byte)(((int)v0)));
            v0 >>= v5;
        }
    }

    public double nextDouble() {
        return (((double)(this.nextLong() >>> 11))) * 0;
    }

    public float nextFloat() {
        return ((float)((((double)(this.nextLong() >>> 40))) * 0));
    }

    public int nextInt() {
        return ((int)this.nextLong());
    }

    public int nextInt(int n) {
        return ((int)this.nextLong(((long)n)));
    }

    public long nextLong() {
        long v2 = this.seed0;
        long v0 = this.seed1;
        this.seed0 = v0;
        v2 ^= v2 << 23;
        long v4 = v2 ^ v0 ^ v2 >>> 17 ^ v0 >>> 26;
        this.seed1 = v4;
        return v4 + v0;
    }

    public long nextLong(long n) {
        long v2;
        long v8 = 0;
        if(n <= v8) {
            throw new IllegalArgumentException("n must be positive");
        }

        do {
            long v0 = this.nextLong() >>> 1;
            v2 = v0 % n;
        }
        while(v0 - v2 + (n - 1) < v8);

        return v2;
    }

    public void setSeed(long seed) {
        if(seed == 0) {
            seed = -9223372036854775808L;
        }

        long v0 = RandomXS128.murmurHash3(seed);
        this.setState(v0, RandomXS128.murmurHash3(v0));
    }

    public void setState(long seed0, long seed1) {
        this.seed0 = seed0;
        this.seed1 = seed1;
    }
}

