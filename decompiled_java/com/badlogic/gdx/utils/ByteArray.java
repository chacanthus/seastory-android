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

public class ByteArray {
    public byte[] items;
    public boolean ordered;
    public int size;

    public ByteArray(int capacity) {
        this(true, capacity);
    }

    public ByteArray() {
        this(true, 16);
    }

    public ByteArray(boolean ordered, int capacity) {
        super();
        this.ordered = ordered;
        this.items = new byte[capacity];
    }

    public ByteArray(ByteArray array) {
        super();
        this.ordered = array.ordered;
        this.size = array.size;
        this.items = new byte[this.size];
        System.arraycopy(array.items, 0, this.items, 0, this.size);
    }

    public ByteArray(boolean ordered, byte[] array, int startIndex, int count) {
        this(ordered, count);
        this.size = count;
        System.arraycopy(array, startIndex, this.items, 0, count);
    }

    public ByteArray(byte[] array) {
        this(true, array, 0, array.length);
    }

    public void add(byte value) {
        byte[] v0 = this.items;
        if(this.size == v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        int v1 = this.size;
        this.size = v1 + 1;
        v0[v1] = value;
    }

    public void addAll(ByteArray array) {
        this.addAll(array, 0, array.size);
    }

    public void addAll(ByteArray array, int offset, int length) {
        if(offset + length > array.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size);
        }

        this.addAll(array.items, offset, length);
    }

    public void addAll(byte[] array, int offset, int length) {
        byte[] v0 = this.items;
        int v1 = this.size + length;
        if(v1 > v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)v1)) * 1.75f))));
        }

        System.arraycopy(array, offset, v0, this.size, length);
        this.size += length;
    }

    public void addAll(byte[] array) {
        this.addAll(array, 0, array.length);
    }

    public void clear() {
        this.size = 0;
    }

    public boolean contains(byte value) {
        boolean v3;
        int v0 = this.size - 1;
        byte[] v2 = this.items;
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

    public byte[] ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 > this.items.length) {
            this.resize(Math.max(8, v0));
        }

        return this.items;
    }

    public boolean equals(Object object) {
        boolean v3 = true;
        if((((ByteArray)object)) != this) {
            if(!(object instanceof ByteArray)) {
                v3 = false;
            }
            else {
                Object v0 = object;
                int v2 = this.size;
                if(v2 != ((ByteArray)v0).size) {
                    v3 = false;
                }
                else {
                    int v1 = 0;
                    while(v1 < v2) {
                        if(this.items[v1] != ((ByteArray)v0).items[v1]) {
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

    public byte first() {
        if(this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }

        return this.items[0];
    }

    public byte get(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        return this.items[index];
    }

    public void incr(int index, byte value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] = ((byte)(this.items[index] + value));
    }

    public int indexOf(byte value) {
        byte[] v1 = this.items;
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

    public void insert(int index, byte value) {
        if(index > this.size) {
            throw new IndexOutOfBoundsException("index can\'t be > size: " + index + " > " + this.size);
        }

        byte[] v0 = this.items;
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

    public int lastIndexOf(byte value) {
        byte[] v1 = this.items;
        int v0 = this.size - 1;
        while(true) {
            if(v0 < 0) {
                break;
            }
            else if(v1[v0] != value) {
                --v0;
                continue;
            }

            goto label_6;
        }

        v0 = -1;
    label_6:
        return v0;
    }

    public void mul(int index, byte value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] = ((byte)(this.items[index] * value));
    }

    public byte peek() {
        return this.items[this.size - 1];
    }

    public byte pop() {
        byte[] v0 = this.items;
        int v1 = this.size - 1;
        this.size = v1;
        return v0[v1];
    }

    public byte random() {
        byte v0 = 0;
        if(this.size != 0) {
            v0 = this.items[MathUtils.random(0, this.size - 1)];
        }

        return v0;
    }

    public boolean removeAll(ByteArray array) {
        boolean v7;
        int v5 = this.size;
        int v6 = v5;
        byte[] v3 = this.items;
        int v0 = 0;
        int v4 = array.size;
        while(v0 < v4) {
            int v2 = array.get(v0);
            int v1 = 0;
            while(v1 < v5) {
                if(v2 == v3[v1]) {
                    this.removeIndex(v1);
                    --v5;
                }
                else {
                    ++v1;
                    continue;
                }

                break;
            }

            ++v0;
        }

        if(v5 != v6) {
            v7 = true;
        }
        else {
            v7 = false;
        }

        return v7;
    }

    public int removeIndex(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        byte[] v0 = this.items;
        int v1 = v0[index];
        --this.size;
        if(this.ordered) {
            System.arraycopy(v0, index + 1, v0, index, this.size - index);
        }
        else {
            v0[index] = v0[this.size];
        }

        return v1;
    }

    public void removeRange(int start, int end) {
        if(end >= this.size) {
            throw new IndexOutOfBoundsException("end can\'t be >= size: " + end + " >= " + this.size);
        }

        if(start > end) {
            throw new IndexOutOfBoundsException("start can\'t be > end: " + start + " > " + end);
        }

        byte[] v2 = this.items;
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

    public boolean removeValue(byte value) {
        boolean v3;
        byte[] v1 = this.items;
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

    protected byte[] resize(int newSize) {
        byte[] v1 = new byte[newSize];
        System.arraycopy(this.items, 0, v1, 0, Math.min(this.size, v1.length));
        this.items = v1;
        return v1;
    }

    public void reverse() {
        byte[] v2 = this.items;
        int v0 = 0;
        int v3 = this.size - 1;
        int v4 = this.size / 2;
        while(v0 < v4) {
            int v1 = v3 - v0;
            byte v5 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v5;
            ++v0;
        }
    }

    public void set(int index, byte value) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] = value;
    }

    public byte[] shrink() {
        if(this.items.length != this.size) {
            this.resize(this.size);
        }

        return this.items;
    }

    public void shuffle() {
        byte[] v2 = this.items;
        int v0;
        for(v0 = this.size - 1; v0 >= 0; --v0) {
            int v1 = MathUtils.random(v0);
            byte v3 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v3;
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

        byte[] v1 = this.items;
        byte v0 = v1[first];
        v1[first] = v1[second];
        v1[second] = v0;
    }

    public byte[] toArray() {
        byte[] v0 = new byte[this.size];
        System.arraycopy(this.items, 0, v0, 0, this.size);
        return v0;
    }

    public String toString() {
        String v3;
        if(this.size == 0) {
            v3 = "[]";
        }
        else {
            byte[] v2 = this.items;
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
            byte[] v2 = this.items;
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

    public static ByteArray with(byte[] array) {
        return new ByteArray(array);
    }
}

