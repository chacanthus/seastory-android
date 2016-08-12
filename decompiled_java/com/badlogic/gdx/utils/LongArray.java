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

import com.badlogic.gdx.math.MathUtils;
import java.util.Arrays;

public class LongArray {
    public long[] items;
    public boolean ordered;
    public int size;

    public LongArray() {
        this(true, 16);
    }

    public LongArray(boolean ordered, int capacity) {
        super();
        this.ordered = ordered;
        this.items = new long[capacity];
    }

    public LongArray(int capacity) {
        this(true, capacity);
    }

    public LongArray(LongArray array) {
        super();
        this.ordered = array.ordered;
        this.size = array.size;
        this.items = new long[this.size];
        System.arraycopy(array.items, 0, this.items, 0, this.size);
    }

    public LongArray(boolean ordered, long[] array, int startIndex, int count) {
        this(ordered, count);
        this.size = count;
        System.arraycopy(array, startIndex, this.items, 0, count);
    }

    public LongArray(long[] array) {
        this(true, array, 0, array.length);
    }

    public void add(long value) {
        long[] v0 = this.items;
        if(this.size == v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        int v1 = this.size;
        this.size = v1 + 1;
        v0[v1] = value;
    }

    public void addAll(LongArray array) {
        this.addAll(array, 0, array.size);
    }

    public void addAll(LongArray array, int offset, int length) {
        if(offset + length > array.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size);
        }

        this.addAll(array.items, offset, length);
    }

    public void addAll(long[] array, int offset, int length) {
        long[] v0 = this.items;
        int v1 = this.size + length;
        if(v1 > v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)v1)) * 1.75f))));
        }

        System.arraycopy(array, offset, v0, this.size, length);
        this.size += length;
    }

    public void addAll(long[] array) {
        this.addAll(array, 0, array.length);
    }

    public void clear() {
        this.size = 0;
    }

    public boolean contains(long value) {
        boolean v3;
        int v0 = this.size - 1;
        long[] v2 = this.items;
        int v1 = v0;
        while(true) {
            if(v1 >= 0) {
                v0 = v1 - 1;
                if(v2[v1] == value) {
                    v3 = true;
                }
                else {
                    v1 = v0;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_9;
        }

        v3 = false;
    label_9:
        return v3;
    }

    public long[] ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 > this.items.length) {
            this.resize(Math.max(8, v0));
        }

        return this.items;
    }

    public boolean equals(Object object) {
        boolean v3 = true;
        if((((LongArray)object)) != this) {
            if(!(object instanceof LongArray)) {
                v3 = false;
            }
            else {
                Object v0 = object;
                int v2 = this.size;
                if(v2 != ((LongArray)v0).size) {
                    v3 = false;
                }
                else {
                    int v1 = 0;
                    while(v1 < v2) {
                        if(this.items[v1] != ((LongArray)v0).items[v1]) {
                            v3 = false;
                        }
                        else {
                            ++v1;
                            continue;
                        }

                        break;
                    }
                }
            }
        }

        return v3;
    }

    public long first() {
        if(this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }

        return this.items[0];
    }

    public long get(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        return this.items[index];
    }

    public void incr(int index, long value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] += value;
    }

    public int indexOf(long value) {
        long[] v1 = this.items;
        int v0 = 0;
        int v2 = this.size;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(v1[v0] != value) {
                ++v0;
                continue;
            }

            goto label_6;
        }

        v0 = -1;
    label_6:
        return v0;
    }

    public void insert(int index, long value) {
        if(index > this.size) {
            throw new IndexOutOfBoundsException("index can\'t be > size: " + index + " > " + this.size);
        }

        long[] v0 = this.items;
        if(this.size == v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        if(this.ordered) {
            System.arraycopy(v0, index, v0, index + 1, this.size - index);
        }
        else {
            v0[this.size] = v0[index];
        }

        ++this.size;
        v0[index] = value;
    }

    public int lastIndexOf(char value) {
        long[] v1 = this.items;
        int v0 = this.size - 1;
        while(true) {
            if(v0 < 0) {
                break;
            }
            else if(v1[v0] != (((long)value))) {
                --v0;
                continue;
            }

            goto label_7;
        }

        v0 = -1;
    label_7:
        return v0;
    }

    public void mul(int index, long value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] *= value;
    }

    public long peek() {
        return this.items[this.size - 1];
    }

    public long pop() {
        long[] v0 = this.items;
        int v1 = this.size - 1;
        this.size = v1;
        return v0[v1];
    }

    public long random() {
        long v0;
        if(this.size == 0) {
            v0 = 0;
        }
        else {
            v0 = this.items[MathUtils.random(0, this.size - 1)];
        }

        return v0;
    }

    public boolean removeAll(LongArray array) {
        boolean v8;
        int v6 = this.size;
        int v7 = v6;
        long[] v4 = this.items;
        int v0 = 0;
        int v5 = array.size;
        while(v0 < v5) {
            long v2 = array.get(v0);
            int v1 = 0;
            while(v1 < v6) {
                if(v2 == v4[v1]) {
                    this.removeIndex(v1);
                    --v6;
                }
                else {
                    ++v1;
                    continue;
                }

                break;
            }

            ++v0;
        }

        if(v6 != v7) {
            v8 = true;
        }
        else {
            v8 = false;
        }

        return v8;
    }

    public long removeIndex(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        long[] v0 = this.items;
        long v2 = v0[index];
        --this.size;
        if(this.ordered) {
            System.arraycopy(v0, index + 1, v0, index, this.size - index);
        }
        else {
            v0[index] = v0[this.size];
        }

        return v2;
    }

    public void removeRange(int start, int end) {
        if(end >= this.size) {
            throw new IndexOutOfBoundsException("end can\'t be >= size: " + end + " >= " + this.size);
        }

        if(start > end) {
            throw new IndexOutOfBoundsException("start can\'t be > end: " + start + " > " + end);
        }

        long[] v2 = this.items;
        int v0 = end - start + 1;
        if(this.ordered) {
            System.arraycopy(v2, start + v0, v2, start, this.size - (start + v0));
        }
        else {
            int v3 = this.size - 1;
            int v1;
            for(v1 = 0; v1 < v0; ++v1) {
                v2[start + v1] = v2[v3 - v1];
            }
        }

        this.size -= v0;
    }

    public boolean removeValue(long value) {
        boolean v3;
        long[] v1 = this.items;
        int v0 = 0;
        int v2 = this.size;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(v1[v0] == value) {
                this.removeIndex(v0);
                v3 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_8;
        }

        v3 = false;
    label_8:
        return v3;
    }

    protected long[] resize(int newSize) {
        long[] v1 = new long[newSize];
        System.arraycopy(this.items, 0, v1, 0, Math.min(this.size, v1.length));
        this.items = v1;
        return v1;
    }

    public void reverse() {
        long[] v2 = this.items;
        int v0 = 0;
        int v3 = this.size - 1;
        int v4 = this.size / 2;
        while(v0 < v4) {
            int v1 = v3 - v0;
            long v6 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v6;
            ++v0;
        }
    }

    public void set(int index, long value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] = value;
    }

    public long[] shrink() {
        if(this.items.length != this.size) {
            this.resize(this.size);
        }

        return this.items;
    }

    public void shuffle() {
        long[] v2 = this.items;
        int v0;
        for(v0 = this.size - 1; v0 >= 0; --v0) {
            int v1 = MathUtils.random(v0);
            long v4 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v4;
        }
    }

    public void sort() {
        Arrays.sort(this.items, 0, this.size);
    }

    public void swap(int first, int second) {
        if(first >= this.size) {
            throw new IndexOutOfBoundsException("first can\'t be >= size: " + first + " >= " + this.size);
        }

        if(second >= this.size) {
            throw new IndexOutOfBoundsException("second can\'t be >= size: " + second + " >= " + this.size);
        }

        long[] v2 = this.items;
        long v0 = v2[first];
        v2[first] = v2[second];
        v2[second] = v0;
    }

    public long[] toArray() {
        long[] v0 = new long[this.size];
        System.arraycopy(this.items, 0, v0, 0, this.size);
        return v0;
    }

    public String toString() {
        String v3;
        if(this.size == 0) {
            v3 = "[]";
        }
        else {
            long[] v2 = this.items;
            StringBuilder v0 = new StringBuilder(32);
            v0.append('[');
            v0.append(v2[0]);
            int v1;
            for(v1 = 1; v1 < this.size; ++v1) {
                v0.append(", ");
                v0.append(v2[v1]);
            }

            v0.append(']');
            v3 = v0.toString();
        }

        return v3;
    }

    public String toString(String separator) {
        String v3;
        if(this.size == 0) {
            v3 = "";
        }
        else {
            long[] v2 = this.items;
            StringBuilder v0 = new StringBuilder(32);
            v0.append(v2[0]);
            int v1;
            for(v1 = 1; v1 < this.size; ++v1) {
                v0.append(separator);
                v0.append(v2[v1]);
            }

            v3 = v0.toString();
        }

        return v3;
    }

    public void truncate(int newSize) {
        if(this.size > newSize) {
            this.size = newSize;
        }
    }

    public static LongArray with(long[] array) {
        return new LongArray(array);
    }
}

