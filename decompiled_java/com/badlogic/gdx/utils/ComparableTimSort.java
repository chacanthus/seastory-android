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

class ComparableTimSort {
    private static final boolean DEBUG = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    private Object[] a;
    private int minGallop;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize;
    private Object[] tmp;

    ComparableTimSort() {
        super();
        this.minGallop = 7;
        this.stackSize = 0;
        this.tmp = new Object[256];
        this.runBase = new int[40];
        this.runLen = new int[40];
    }

    private ComparableTimSort(Object[] a) {
        int v2;
        int v3;
        super();
        this.minGallop = 7;
        this.stackSize = 0;
        this.a = a;
        int v0 = a.length;
        if(v0 < 512) {
            v3 = v0 >>> 1;
        }
        else {
            v3 = 256;
        }

        this.tmp = new Object[v3];
        if(v0 < 120) {
            v2 = 5;
        }
        else if(v0 < 1542) {
            v2 = 10;
        }
        else if(v0 < 119151) {
            v2 = 19;
        }
        else {
            v2 = 40;
        }

        this.runBase = new int[v2];
        this.runLen = new int[v2];
    }

    private static void binarySort(Object[] a, int lo, int hi, int start) {
        if(start == lo) {
            ++start;
        }

        while(start < hi) {
            Object v3 = a[start];
            int v0 = lo;
            int v4 = start;
            while(true) {
                if(v0 >= v4) {
                    break;
                }

                int v1 = v0 + v4 >>> 1;
                if(((Comparable)v3).compareTo(a[v1]) >= 0) {
                    goto label_14;
                }

                v4 = v1;
                continue;
            label_14:
                v0 = v1 + 1;
            }

            int v2 = start - v0;
            switch(v2) {
                case 1: {
                    goto label_27;
                }
                case 2: {
                    goto label_23;
                }
            }

            System.arraycopy(a, v0, a, v0 + 1, v2);
            goto label_20;
        label_23:
            a[v0 + 2] = a[v0 + 1];
        label_27:
            a[v0 + 1] = a[v0];
        label_20:
            a[v0] = v3;
            ++start;
        }
    }

    private static int countRunAndMakeAscending(Object[] a, int lo, int hi) {
        int v2;
        int v0 = lo + 1;
        if(v0 == hi) {
            v2 = 1;
        }
        else {
            int v1 = v0 + 1;
            if(a[v0].compareTo(a[lo]) < 0) {
                v0 = v1;
                while(v0 < hi) {
                    if(a[v0].compareTo(a[v0 - 1]) >= 0) {
                        break;
                    }

                    ++v0;
                }

                ComparableTimSort.reverseRange(a, lo, v0);
            }
            else {
                for(v0 = v1; v0 < hi; ++v0) {
                    if(a[v0].compareTo(a[v0 - 1]) < 0) {
                        break;
                    }
                }
            }

            v2 = v0 - lo;
        }

        return v2;
    }

    public void doSort(Object[] a, int lo, int hi) {
        int v0;
        this.stackSize = 0;
        ComparableTimSort.rangeCheck(a.length, lo, hi);
        int v3 = hi - lo;
        if(v3 >= 2) {
            if(v3 < 32) {
                ComparableTimSort.binarySort(a, lo, hi, lo + ComparableTimSort.countRunAndMakeAscending(a, lo, hi));
            }
            else {
                this.a = a;
                int v2 = ComparableTimSort.minRunLength(v3);
                do {
                    int v4 = ComparableTimSort.countRunAndMakeAscending(a, lo, hi);
                    if(v4 < v2) {
                        if(v3 <= v2) {
                            v0 = v3;
                        }
                        else {
                            v0 = v2;
                        }

                        ComparableTimSort.binarySort(a, lo, lo + v0, lo + v4);
                        v4 = v0;
                    }

                    this.pushRun(lo, v4);
                    this.mergeCollapse();
                    lo += v4;
                    v3 -= v4;
                }
                while(v3 != 0);

                this.mergeForceCollapse();
            }
        }
    }

    private Object[] ensureCapacity(int minCapacity) {
        if(this.tmp.length < minCapacity) {
            int v1 = minCapacity | minCapacity >> 1;
            v1 |= v1 >> 2;
            v1 |= v1 >> 4;
            v1 |= v1 >> 8;
            v1 = (v1 | v1 >> 16) + 1;
            if(v1 < 0) {
                v1 = minCapacity;
            }
            else {
                v1 = Math.min(v1, this.a.length >>> 1);
            }

            this.tmp = new Object[v1];
        }

        return this.tmp;
    }

    private static int gallopLeft(Comparable arg6, Object[] a, int base, int len, int hint) {
        int v2;
        int v0 = 0;
        int v3 = 1;
        if(arg6.compareTo(a[base + hint]) > 0) {
            v2 = len - hint;
            while(v3 < v2) {
                if(arg6.compareTo(a[base + hint + v3]) <= 0) {
                    break;
                }

                v0 = v3;
                v3 = (v3 << 1) + 1;
                if(v3 > 0) {
                    continue;
                }

                v3 = v2;
            }

            if(v3 > v2) {
                v3 = v2;
            }

            v0 += hint;
            v3 += hint;
        }
        else {
            v2 = hint + 1;
            while(v3 < v2) {
                if(arg6.compareTo(a[base + hint - v3]) > 0) {
                    break;
                }

                v0 = v3;
                v3 = (v3 << 1) + 1;
                if(v3 > 0) {
                    continue;
                }

                v3 = v2;
            }

            if(v3 > v2) {
                v3 = v2;
            }

            int v4 = v0;
            v0 = hint - v3;
            v3 = hint - v4;
        }

        ++v0;
        while(true) {
            if(v0 >= v3) {
                break;
            }

            int v1 = v0 + (v3 - v0 >>> 1);
            if(arg6.compareTo(a[base + v1]) <= 0) {
                goto label_53;
            }

            v0 = v1 + 1;
            continue;
        label_53:
            v3 = v1;
        }

        return v3;
    }

    private static int gallopRight(Comparable arg6, Object[] a, int base, int len, int hint) {
        int v2;
        int v3 = 1;
        int v0 = 0;
        if(arg6.compareTo(a[base + hint]) < 0) {
            v2 = hint + 1;
            while(v3 < v2) {
                if(arg6.compareTo(a[base + hint - v3]) >= 0) {
                    break;
                }

                v0 = v3;
                v3 = (v3 << 1) + 1;
                if(v3 > 0) {
                    continue;
                }

                v3 = v2;
            }

            if(v3 > v2) {
                v3 = v2;
            }

            int v4 = v0;
            v0 = hint - v3;
            v3 = hint - v4;
        }
        else {
            v2 = len - hint;
            while(v3 < v2) {
                if(arg6.compareTo(a[base + hint + v3]) < 0) {
                    break;
                }

                v0 = v3;
                v3 = (v3 << 1) + 1;
                if(v3 > 0) {
                    continue;
                }

                v3 = v2;
            }

            if(v3 > v2) {
                v3 = v2;
            }

            v0 += hint;
            v3 += hint;
        }

        ++v0;
        while(true) {
            if(v0 >= v3) {
                break;
            }

            int v1 = v0 + (v3 - v0 >>> 1);
            if(arg6.compareTo(a[base + v1]) >= 0) {
                goto label_53;
            }

            v3 = v1;
            continue;
        label_53:
            v0 = v1 + 1;
        }

        return v3;
    }

    private void mergeAt(int i) {
        int v0 = this.runBase[i];
        int v3 = this.runLen[i];
        int v1 = this.runBase[i + 1];
        int v4 = this.runLen[i + 1];
        this.runLen[i] = v3 + v4;
        if(i == this.stackSize - 3) {
            this.runBase[i + 1] = this.runBase[i + 2];
            this.runLen[i + 1] = this.runLen[i + 2];
        }

        --this.stackSize;
        int v2 = ComparableTimSort.gallopRight(this.a[v1], this.a, v0, v3, 0);
        v0 += v2;
        v3 -= v2;
        if(v3 != 0) {
            v4 = ComparableTimSort.gallopLeft(this.a[v0 + v3 - 1], this.a, v1, v4, v4 - 1);
            if(v4 != 0) {
                if(v3 <= v4) {
                    this.mergeLo(v0, v3, v1, v4);
                }
                else {
                    this.mergeHi(v0, v3, v1, v4);
                }
            }
        }
    }

    private void mergeCollapse() {
        while(this.stackSize > 1) {
            int v0 = this.stackSize - 2;
            if(v0 > 0 && this.runLen[v0 - 1] <= this.runLen[v0] + this.runLen[v0 + 1]) {
                if(this.runLen[v0 - 1] < this.runLen[v0 + 1]) {
                    --v0;
                }

                this.mergeAt(v0);
                continue;
            }

            if(this.runLen[v0] > this.runLen[v0 + 1]) {
                return;
            }

            this.mergeAt(v0);
        }
    }

    private void mergeForceCollapse() {
        while(this.stackSize > 1) {
            int v0 = this.stackSize - 2;
            if(v0 > 0 && this.runLen[v0 - 1] < this.runLen[v0 + 1]) {
                --v0;
            }

            this.mergeAt(v0);
        }
    }

    private void mergeHi(int base1, int len1, int base2, int len2) {
        int v13;
        int v14;
        int v8;
        Object[] v2 = this.a;
        Object[] v12 = this.ensureCapacity(len2);
        System.arraycopy(v2, base2, v12, 0, len2);
        int v5 = base1 + len1 - 1;
        int v7 = len2 - 1;
        int v9 = base2 + len2 - 1;
        int v10 = v9 - 1;
        int v6 = v5 - 1;
        v2[v9] = v2[v5];
        --len1;
        if(len1 == 0) {
            System.arraycopy(v12, 0, v2, v10 - (len2 - 1), len2);
        }
        else if(len2 == 1) {
            v9 = v10 - len1;
            System.arraycopy(v2, v6 - len1 + 1, v2, v9 + 1, len1);
            v2[v9] = v12[v7];
        }
        else {
            int v11 = this.minGallop;
            v9 = v10;
            v5 = v6;
            while(true) {
            label_44:
                int v3 = 0;
                int v4 = 0;
                do {
                    if(v12[v7].compareTo(v2[v5]) < 0) {
                        v10 = v9 - 1;
                        v6 = v5 - 1;
                        v2[v9] = v2[v5];
                        ++v3;
                        v4 = 0;
                        --len1;
                        if(len1 == 0) {
                            v9 = v10;
                            v5 = v6;
                        }
                        else {
                            v9 = v10;
                            v5 = v6;
                            goto label_92;
                        }
                    }
                    else {
                        v10 = v9 - 1;
                        v8 = v7 - 1;
                        v2[v9] = v12[v7];
                        ++v4;
                        v3 = 0;
                        --len2;
                        if(len2 == 1) {
                            v9 = v10;
                            v7 = v8;
                        }
                        else {
                            v9 = v10;
                            v7 = v8;
                        label_92:
                            if((v3 | v4) < v11) {
                                continue;
                            }

                            break;
                        }
                    }

                    goto label_60;
                }
                while(true);

                while(true) {
                label_94:
                    v3 = len1 - ComparableTimSort.gallopRight(v12[v7], v2, base1, len1, len1 - 1);
                    if(v3 != 0) {
                        v9 -= v3;
                        v5 -= v3;
                        len1 -= v3;
                        System.arraycopy(v2, v5 + 1, v2, v9 + 1, v3);
                        if(len1 != 0) {
                            goto label_108;
                        }

                        goto label_60;
                    }

                label_108:
                    v10 = v9 - 1;
                    v8 = v7 - 1;
                    v2[v9] = v12[v7];
                    --len2;
                    if(len2 != 1) {
                        goto label_119;
                    }

                    v9 = v10;
                    v7 = v8;
                    goto label_60;
                label_119:
                    v4 = len2 - ComparableTimSort.gallopLeft(v2[v5], v12, 0, len2, len2 - 1);
                    if(v4 != 0) {
                        v9 = v10 - v4;
                        v7 = v8 - v4;
                        len2 -= v4;
                        System.arraycopy(v12, v7 + 1, v2, v9 + 1, v4);
                        if(len2 <= 1) {
                            goto label_60;
                        }
                    }
                    else {
                        v9 = v10;
                        v7 = v8;
                    }

                    v10 = v9 - 1;
                    v6 = v5 - 1;
                    v2[v9] = v2[v5];
                    --len1;
                    if(len1 != 0) {
                        goto label_144;
                    }

                    v9 = v10;
                    v5 = v6;
                    goto label_60;
                label_144:
                    --v11;
                    if(v3 >= 7) {
                        v14 = 1;
                    }
                    else {
                        v14 = 0;
                    }

                    if(v4 >= 7) {
                        v13 = 1;
                    }
                    else {
                        v13 = 0;
                    }

                    if((v13 | v14) != 0) {
                        break;
                    }

                    if(v11 < 0) {
                        v11 = 0;
                    }

                    v11 += 2;
                    v9 = v10;
                    v5 = v6;
                    goto label_44;
                }
            }

            v9 = v10;
            v5 = v6;
            goto label_94;
        label_60:
            if(v11 < 1) {
                v11 = 1;
            }

            this.minGallop = v11;
            if(len2 != 1) {
                goto label_165;
            }

            v9 -= len1;
            System.arraycopy(v2, v5 - len1 + 1, v2, v9 + 1, len1);
            v2[v9] = v12[v7];
            return;
        label_165:
            if(len2 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }

            System.arraycopy(v12, 0, v2, v9 - (len2 - 1), len2);
        }
    }

    private void mergeLo(int base1, int len1, int base2, int len2) {
        int v12;
        int v13;
        int v5;
        Object[] v1 = this.a;
        Object[] v11 = this.ensureCapacity(len1);
        System.arraycopy(v1, base1, v11, 0, len1);
        int v4 = 0;
        int v9 = base1 + 1;
        int v7 = base2 + 1;
        v1[base1] = v1[base2];
        --len2;
        if(len2 == 0) {
            System.arraycopy(v11, 0, v1, v9, len1);
        }
        else if(len1 == 1) {
            System.arraycopy(v1, v7, v1, v9, len2);
            v1[v9 + len2] = v11[0];
        }
        else {
            int v10 = this.minGallop;
            int v8 = v9;
            int v6 = v7;
            while(true) {
            label_34:
                int v2 = 0;
                int v3 = 0;
                do {
                    if(v1[v6].compareTo(v11[v4]) < 0) {
                        v9 = v8 + 1;
                        v7 = v6 + 1;
                        v1[v8] = v1[v6];
                        ++v3;
                        v2 = 0;
                        --len2;
                        if(len2 == 0) {
                            v8 = v9;
                            v6 = v7;
                        }
                        else {
                            v8 = v9;
                            v6 = v7;
                            goto label_78;
                        }
                    }
                    else {
                        v9 = v8 + 1;
                        v5 = v4 + 1;
                        v1[v8] = v11[v4];
                        ++v2;
                        v3 = 0;
                        --len1;
                        if(len1 == 1) {
                            v8 = v9;
                            v4 = v5;
                        }
                        else {
                            v8 = v9;
                            v4 = v5;
                        label_78:
                            if((v2 | v3) < v10) {
                                continue;
                            }

                            break;
                        }
                    }

                    goto label_50;
                }
                while(true);

                while(true) {
                label_80:
                    v2 = ComparableTimSort.gallopRight(v1[v6], v11, v4, len1, 0);
                    if(v2 != 0) {
                        System.arraycopy(v11, v4, v1, v8, v2);
                        v8 += v2;
                        v4 += v2;
                        len1 -= v2;
                        if(len1 > 1) {
                            goto label_92;
                        }

                        goto label_50;
                    }

                label_92:
                    v9 = v8 + 1;
                    v7 = v6 + 1;
                    v1[v8] = v1[v6];
                    --len2;
                    if(len2 != 0) {
                        goto label_101;
                    }

                    v8 = v9;
                    v6 = v7;
                    goto label_50;
                label_101:
                    v3 = ComparableTimSort.gallopLeft(v11[v4], v1, v7, len2, 0);
                    if(v3 != 0) {
                        System.arraycopy(v1, v7, v1, v9, v3);
                        v8 = v9 + v3;
                        v6 = v7 + v3;
                        len2 -= v3;
                        if(len2 == 0) {
                            goto label_50;
                        }
                    }
                    else {
                        v8 = v9;
                        v6 = v7;
                    }

                    v9 = v8 + 1;
                    v5 = v4 + 1;
                    v1[v8] = v11[v4];
                    --len1;
                    if(len1 != 1) {
                        goto label_122;
                    }

                    v8 = v9;
                    v4 = v5;
                    goto label_50;
                label_122:
                    --v10;
                    if(v2 >= 7) {
                        v13 = 1;
                    }
                    else {
                        v13 = 0;
                    }

                    if(v3 >= 7) {
                        v12 = 1;
                    }
                    else {
                        v12 = 0;
                    }

                    if((v12 | v13) != 0) {
                        break;
                    }

                    if(v10 < 0) {
                        v10 = 0;
                    }

                    v10 += 2;
                    v8 = v9;
                    v4 = v5;
                    goto label_34;
                }
            }

            v8 = v9;
            v4 = v5;
            goto label_80;
        label_50:
            if(v10 < 1) {
                v10 = 1;
            }

            this.minGallop = v10;
            if(len1 != 1) {
                goto label_143;
            }

            System.arraycopy(v1, v6, v1, v8, len2);
            v1[v8 + len2] = v11[v4];
            return;
        label_143:
            if(len1 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }

            System.arraycopy(v11, v4, v1, v8, len1);
        }
    }

    private static int minRunLength(int n) {
        int v0 = 0;
        while(n >= 32) {
            v0 |= n & 1;
            n >>= 1;
        }

        return n + v0;
    }

    private void pushRun(int runBase, int runLen) {
        this.runBase[this.stackSize] = runBase;
        this.runLen[this.stackSize] = runLen;
        ++this.stackSize;
    }

    private static void rangeCheck(int arrayLen, int fromIndex, int toIndex) {
        if(fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }

        if(fromIndex < 0) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        }

        if(toIndex > arrayLen) {
            throw new ArrayIndexOutOfBoundsException(toIndex);
        }
    }

    private static void reverseRange(Object[] a, int lo, int hi) {
        int v0 = hi - 1;
        int v1;
        for(v1 = lo; v1 < v0; ++v1) {
            Object v2 = a[v1];
            a[v1] = a[v0];
            a[v0] = v2;
            --v0;
        }
    }

    static void sort(Object[] a) {
        ComparableTimSort.sort(a, 0, a.length);
    }

    static void sort(Object[] a, int lo, int hi) {
        int v0;
        ComparableTimSort.rangeCheck(a.length, lo, hi);
        int v3 = hi - lo;
        if(v3 >= 2) {
            if(v3 < 32) {
                ComparableTimSort.binarySort(a, lo, hi, lo + ComparableTimSort.countRunAndMakeAscending(a, lo, hi));
            }
            else {
                ComparableTimSort v5 = new ComparableTimSort(a);
                int v2 = ComparableTimSort.minRunLength(v3);
                do {
                    int v4 = ComparableTimSort.countRunAndMakeAscending(a, lo, hi);
                    if(v4 < v2) {
                        if(v3 <= v2) {
                            v0 = v3;
                        }
                        else {
                            v0 = v2;
                        }

                        ComparableTimSort.binarySort(a, lo, lo + v0, lo + v4);
                        v4 = v0;
                    }

                    v5.pushRun(lo, v4);
                    v5.mergeCollapse();
                    lo += v4;
                    v3 -= v4;
                }
                while(v3 != 0);

                v5.mergeForceCollapse();
            }
        }
    }
}

