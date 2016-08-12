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

import java.util.Arrays;
import java.util.Comparator;

class TimSort {
    private static final boolean DEBUG = false;
    private static final int INITIAL_TMP_STORAGE_LENGTH = 256;
    private static final int MIN_GALLOP = 7;
    private static final int MIN_MERGE = 32;
    private Object[] a;
    private Comparator c;
    private int minGallop;
    private final int[] runBase;
    private final int[] runLen;
    private int stackSize;
    private Object[] tmp;
    private int tmpCount;

    TimSort() {
        super();
        this.minGallop = 7;
        this.stackSize = 0;
        this.tmp = new Object[256];
        this.runBase = new int[40];
        this.runLen = new int[40];
    }

    private TimSort(Object[] arg5, Comparator arg6) {
        int v2;
        int v3;
        super();
        this.minGallop = 7;
        this.stackSize = 0;
        this.a = arg5;
        this.c = arg6;
        int v0 = arg5.length;
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

    private static void binarySort(Object[] arg7, int lo, int hi, int start, Comparator arg11) {
        if(start == lo) {
            ++start;
        }

        while(start < hi) {
            Object v3 = arg7[start];
            int v0 = lo;
            int v4 = start;
            while(true) {
                if(v0 >= v4) {
                    break;
                }

                int v1 = v0 + v4 >>> 1;
                if(arg11.compare(v3, arg7[v1]) >= 0) {
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

            System.arraycopy(arg7, v0, arg7, v0 + 1, v2);
            goto label_20;
        label_23:
            arg7[v0 + 2] = arg7[v0 + 1];
        label_27:
            arg7[v0 + 1] = arg7[v0];
        label_20:
            arg7[v0] = v3;
            ++start;
        }
    }

    private static int countRunAndMakeAscending(Object[] arg4, int lo, int hi, Comparator arg7) {
        int v2;
        int v0 = lo + 1;
        if(v0 == hi) {
            v2 = 1;
        }
        else {
            int v1 = v0 + 1;
            if(arg7.compare(arg4[v0], arg4[lo]) < 0) {
                v0 = v1;
                while(v0 < hi) {
                    if(arg7.compare(arg4[v0], arg4[v0 - 1]) >= 0) {
                        break;
                    }

                    ++v0;
                }

                TimSort.reverseRange(arg4, lo, v0);
            }
            else {
                for(v0 = v1; v0 < hi; ++v0) {
                    if(arg7.compare(arg4[v0], arg4[v0 - 1]) < 0) {
                        break;
                    }
                }
            }

            v2 = v0 - lo;
        }

        return v2;
    }

    public void doSort(Object[] arg12, Comparator arg13, int lo, int hi) {
        int v0;
        Object[] v10 = null;
        this.stackSize = 0;
        TimSort.rangeCheck(arg12.length, lo, hi);
        int v5 = hi - lo;
        if(v5 >= 2) {
            if(v5 < 32) {
                TimSort.binarySort(arg12, lo, hi, lo + TimSort.countRunAndMakeAscending(arg12, lo, hi, arg13), arg13);
            }
            else {
                this.a = arg12;
                this.c = arg13;
                this.tmpCount = 0;
                int v3 = TimSort.minRunLength(v5);
                do {
                    int v6 = TimSort.countRunAndMakeAscending(arg12, lo, hi, arg13);
                    if(v6 < v3) {
                        if(v5 <= v3) {
                            v0 = v5;
                        }
                        else {
                            v0 = v3;
                        }

                        TimSort.binarySort(arg12, lo, lo + v0, lo + v6, arg13);
                        v6 = v0;
                    }

                    this.pushRun(lo, v6);
                    this.mergeCollapse();
                    lo += v6;
                    v5 -= v6;
                }
                while(v5 != 0);

                this.mergeForceCollapse();
                this.a = v10;
                this.c = ((Comparator)v10);
                Object[] v7 = this.tmp;
                int v1 = 0;
                int v4 = this.tmpCount;
                while(v1 < v4) {
                    v7[v1] = v10;
                    ++v1;
                }
            }
        }
    }

    private Object[] ensureCapacity(int minCapacity) {
        this.tmpCount = Math.max(this.tmpCount, minCapacity);
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

    private static int gallopLeft(Object arg6, Object[] arg7, int base, int len, int hint, Comparator arg11) {
        int v2;
        int v0 = 0;
        int v3 = 1;
        if(arg11.compare(arg6, arg7[base + hint]) > 0) {
            v2 = len - hint;
            while(v3 < v2) {
                if(arg11.compare(arg6, arg7[base + hint + v3]) <= 0) {
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
                if(arg11.compare(arg6, arg7[base + hint - v3]) > 0) {
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
            if(arg11.compare(arg6, arg7[base + v1]) <= 0) {
                goto label_53;
            }

            v0 = v1 + 1;
            continue;
        label_53:
            v3 = v1;
        }

        return v3;
    }

    private static int gallopRight(Object arg6, Object[] arg7, int base, int len, int hint, Comparator arg11) {
        int v2;
        int v3 = 1;
        int v0 = 0;
        if(arg11.compare(arg6, arg7[base + hint]) < 0) {
            v2 = hint + 1;
            while(v3 < v2) {
                if(arg11.compare(arg6, arg7[base + hint - v3]) >= 0) {
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
                if(arg11.compare(arg6, arg7[base + hint + v3]) < 0) {
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
            if(arg11.compare(arg6, arg7[base + v1]) >= 0) {
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
        int v2 = this.runBase[i];
        int v3 = this.runLen[i];
        int v6 = this.runBase[i + 1];
        int v7 = this.runLen[i + 1];
        this.runLen[i] = v3 + v7;
        if(i == this.stackSize - 3) {
            this.runBase[i + 1] = this.runBase[i + 2];
            this.runLen[i + 1] = this.runLen[i + 2];
        }

        --this.stackSize;
        int v10 = TimSort.gallopRight(this.a[v6], this.a, v2, v3, 0, this.c);
        v2 += v10;
        v3 -= v10;
        if(v3 != 0) {
            v7 = TimSort.gallopLeft(this.a[v2 + v3 - 1], this.a, v6, v7, v7 - 1, this.c);
            if(v7 != 0) {
                if(v3 <= v7) {
                    this.mergeLo(v2, v3, v6, v7);
                }
                else {
                    this.mergeHi(v2, v3, v6, v7);
                }
            }
        }
    }

    private void mergeCollapse() {
        while(this.stackSize > 1) {
            int v0 = this.stackSize - 2;
            if(v0 < 1 || this.runLen[v0 - 1] > this.runLen[v0] + this.runLen[v0 + 1]) {
                if(v0 >= 2 && this.runLen[v0 - 2] <= this.runLen[v0] + this.runLen[v0 - 1]) {
                label_28:
                    if(this.runLen[v0 - 1] < this.runLen[v0 + 1]) {
                        --v0;
                        goto label_36;
                    }
                    else {
                        goto label_36;
                    }
                }

                if(this.runLen[v0] <= this.runLen[v0 + 1]) {
                    goto label_36;
                }

                return;
            }
            else {
                goto label_28;
            }

        label_36:
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
        int v2;
        int v4;
        int v19;
        Object[] v3 = this.a;
        Object[] v9 = this.ensureCapacity(len2);
        System.arraycopy(v3, base2, v9, 0, len2);
        int v16 = base1 + len1 - 1;
        int v18 = len2 - 1;
        int v20 = base2 + len2 - 1;
        int v21 = v20 - 1;
        int v17 = v16 - 1;
        v3[v20] = v3[v16];
        --len1;
        if(len1 == 0) {
            System.arraycopy(v9, 0, v3, v21 - (len2 - 1), len2);
        }
        else if(len2 == 1) {
            v20 = v21 - len1;
            System.arraycopy(v3, v17 - len1 + 1, v3, v20 + 1, len1);
            v3[v20] = v9[v18];
        }
        else {
            Comparator v7 = this.c;
            int v22 = this.minGallop;
            v20 = v21;
            v16 = v17;
            while(true) {
            label_47:
                int v14 = 0;
                int v15 = 0;
                do {
                    if(v7.compare(v9[v18], v3[v16]) < 0) {
                        v21 = v20 - 1;
                        v17 = v16 - 1;
                        v3[v20] = v3[v16];
                        ++v14;
                        v15 = 0;
                        --len1;
                        if(len1 == 0) {
                            v20 = v21;
                            v16 = v17;
                        }
                        else {
                            v20 = v21;
                            v16 = v17;
                            goto label_97;
                        }
                    }
                    else {
                        v21 = v20 - 1;
                        v19 = v18 - 1;
                        v3[v20] = v9[v18];
                        ++v15;
                        v14 = 0;
                        --len2;
                        if(len2 == 1) {
                            v20 = v21;
                            v18 = v19;
                        }
                        else {
                            v20 = v21;
                            v18 = v19;
                        label_97:
                            if((v14 | v15) < v22) {
                                continue;
                            }

                            break;
                        }
                    }

                    goto label_63;
                }
                while(true);

                while(true) {
                label_100:
                    v14 = len1 - TimSort.gallopRight(v9[v18], v3, base1, len1, len1 - 1, v7);
                    if(v14 != 0) {
                        v20 -= v14;
                        v16 -= v14;
                        len1 -= v14;
                        System.arraycopy(v3, v16 + 1, v3, v20 + 1, v14);
                        if(len1 != 0) {
                            goto label_114;
                        }

                        goto label_63;
                    }

                label_114:
                    v21 = v20 - 1;
                    v19 = v18 - 1;
                    v3[v20] = v9[v18];
                    --len2;
                    if(len2 != 1) {
                        goto label_125;
                    }

                    v20 = v21;
                    v18 = v19;
                    goto label_63;
                label_125:
                    v15 = len2 - TimSort.gallopLeft(v3[v16], v9, 0, len2, len2 - 1, v7);
                    if(v15 != 0) {
                        v20 = v21 - v15;
                        v18 = v19 - v15;
                        len2 -= v15;
                        System.arraycopy(v9, v18 + 1, v3, v20 + 1, v15);
                        if(len2 <= 1) {
                            goto label_63;
                        }
                    }
                    else {
                        v20 = v21;
                        v18 = v19;
                    }

                    v21 = v20 - 1;
                    v17 = v16 - 1;
                    v3[v20] = v3[v16];
                    --len1;
                    if(len1 != 0) {
                        goto label_151;
                    }

                    v20 = v21;
                    v16 = v17;
                    goto label_63;
                label_151:
                    --v22;
                    if(v14 >= 7) {
                        v4 = 1;
                    }
                    else {
                        v4 = 0;
                    }

                    if(v15 >= 7) {
                        v2 = 1;
                    }
                    else {
                        v2 = 0;
                    }

                    if((v2 | v4) != 0) {
                        break;
                    }

                    if(v22 < 0) {
                        v22 = 0;
                    }

                    v22 += 2;
                    v20 = v21;
                    v16 = v17;
                    goto label_47;
                }
            }

            v20 = v21;
            v16 = v17;
            goto label_100;
        label_63:
            if(v22 < 1) {
                v22 = 1;
            }

            this.minGallop = v22;
            if(len2 != 1) {
                goto label_172;
            }

            v20 -= len1;
            System.arraycopy(v3, v16 - len1 + 1, v3, v20 + 1, len1);
            v3[v20] = v9[v18];
            return;
        label_172:
            if(len2 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }

            System.arraycopy(v9, 0, v3, v20 - (len2 - 1), len2);
        }
    }

    private void mergeLo(int base1, int len1, int base2, int len2) {
        int v3;
        int v6;
        int v17;
        Object[] v10 = this.a;
        Object[] v4 = this.ensureCapacity(len1);
        System.arraycopy(v10, base1, v4, 0, len1);
        int v5 = 0;
        int v20 = base1 + 1;
        int v18 = base2 + 1;
        v10[base1] = v10[base2];
        --len2;
        if(len2 == 0) {
            System.arraycopy(v4, 0, v10, v20, len1);
        }
        else if(len1 == 1) {
            System.arraycopy(v10, v18, v10, v20, len2);
            v10[v20 + len2] = v4[0];
        }
        else {
            Comparator v8 = this.c;
            int v21 = this.minGallop;
            int v19 = v20;
            int v11 = v18;
            while(true) {
            label_44:
                int v15 = 0;
                int v16 = 0;
                do {
                    if(v8.compare(v10[v11], v4[v5]) < 0) {
                        v20 = v19 + 1;
                        v18 = v11 + 1;
                        v10[v19] = v10[v11];
                        ++v16;
                        v15 = 0;
                        --len2;
                        if(len2 == 0) {
                            v19 = v20;
                            v11 = v18;
                        }
                        else {
                            v19 = v20;
                            v11 = v18;
                            goto label_92;
                        }
                    }
                    else {
                        v20 = v19 + 1;
                        v17 = v5 + 1;
                        v10[v19] = v4[v5];
                        ++v15;
                        v16 = 0;
                        --len1;
                        if(len1 == 1) {
                            v19 = v20;
                            v5 = v17;
                        }
                        else {
                            v19 = v20;
                            v5 = v17;
                        label_92:
                            if((v15 | v16) < v21) {
                                continue;
                            }

                            break;
                        }
                    }

                    goto label_60;
                }
                while(true);

                v18 = v11;
                while(true) {
                label_96:
                    v15 = TimSort.gallopRight(v10[v18], v4, v5, len1, 0, v8);
                    if(v15 != 0) {
                        System.arraycopy(v4, v5, v10, v19, v15);
                        v19 += v15;
                        v5 += v15;
                        len1 -= v15;
                        if(len1 <= 1) {
                            v11 = v18;
                            goto label_60;
                        }
                    }

                    v20 = v19 + 1;
                    v11 = v18 + 1;
                    v10[v19] = v10[v18];
                    --len2;
                    if(len2 != 0) {
                        goto label_119;
                    }

                    v19 = v20;
                    goto label_60;
                label_119:
                    v16 = TimSort.gallopLeft(v4[v5], v10, v11, len2, 0, v8);
                    if(v16 != 0) {
                        System.arraycopy(v10, v11, v10, v20, v16);
                        v19 = v20 + v16;
                        v11 += v16;
                        len2 -= v16;
                        if(len2 == 0) {
                            goto label_60;
                        }
                    }
                    else {
                        v19 = v20;
                    }

                    v20 = v19 + 1;
                    v17 = v5 + 1;
                    v10[v19] = v4[v5];
                    --len1;
                    if(len1 != 1) {
                        goto label_143;
                    }

                    v19 = v20;
                    v5 = v17;
                    goto label_60;
                label_143:
                    --v21;
                    if(v15 >= 7) {
                        v6 = 1;
                    }
                    else {
                        v6 = 0;
                    }

                    if(v16 >= 7) {
                        v3 = 1;
                    }
                    else {
                        v3 = 0;
                    }

                    if((v3 | v6) != 0) {
                        break;
                    }

                    if(v21 < 0) {
                        v21 = 0;
                    }

                    v21 += 2;
                    v19 = v20;
                    v5 = v17;
                    goto label_44;
                }
            }

            v19 = v20;
            v18 = v11;
            v5 = v17;
            goto label_96;
        label_60:
            if(v21 < 1) {
                v21 = 1;
            }

            this.minGallop = v21;
            if(len1 != 1) {
                goto label_165;
            }

            System.arraycopy(v10, v11, v10, v19, len2);
            v10[v19 + len2] = v4[v5];
            return;
        label_165:
            if(len1 == 0) {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }

            System.arraycopy(v4, v5, v10, v19, len1);
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

    static void sort(Object[] arg8, int lo, int hi, Comparator arg11) {
        int v0;
        if(arg11 == null) {
            Arrays.sort(arg8, lo, hi);
        }
        else {
            TimSort.rangeCheck(arg8.length, lo, hi);
            int v3 = hi - lo;
            if(v3 >= 2) {
                if(v3 < 32) {
                    TimSort.binarySort(arg8, lo, hi, lo + TimSort.countRunAndMakeAscending(arg8, lo, hi, arg11), arg11);
                }
                else {
                    TimSort v5 = new TimSort(arg8, arg11);
                    int v2 = TimSort.minRunLength(v3);
                    do {
                        int v4 = TimSort.countRunAndMakeAscending(arg8, lo, hi, arg11);
                        if(v4 < v2) {
                            if(v3 <= v2) {
                                v0 = v3;
                            }
                            else {
                                v0 = v2;
                            }

                            TimSort.binarySort(arg8, lo, lo + v0, lo + v4, arg11);
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

    static void sort(Object[] arg2, Comparator arg3) {
        TimSort.sort(arg2, 0, arg2.length, arg3);
    }
}

