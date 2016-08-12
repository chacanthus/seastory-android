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
import java.util.NoSuchElementException;

public class IntSet {
    public class IntSetIterator {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        int nextIndex;
        final IntSet set;
        boolean valid;

        public IntSetIterator(IntSet set) {
            super();
            this.valid = true;
            this.set = set;
            this.reset();
        }

        void findNextIndex() {
            this.hasNext = false;
            int[] v0 = this.set.keyTable;
            int v1 = this.set.capacity + this.set.stashSize;
            do {
                int v2 = this.nextIndex + 1;
                this.nextIndex = v2;
                if(v2 < v1) {
                    if(v0[this.nextIndex] == 0) {
                        continue;
                    }

                    break;
                }

                return;
            }
            while(true);

            this.hasNext = true;
        }

        public int next() {
            int v0;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.nextIndex == -1) {
                v0 = 0;
            }
            else {
                v0 = this.set.keyTable[this.nextIndex];
            }

            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return v0;
        }

        public void remove() {
            if(this.currentIndex == -1 && (this.set.hasZeroValue)) {
                this.set.hasZeroValue = false;
            }
            else if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            else if(this.currentIndex >= this.set.capacity) {
                this.set.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                this.findNextIndex();
            }
            else {
                this.set.keyTable[this.currentIndex] = 0;
            }

            this.currentIndex = -2;
            --this.set.size;
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if(this.set.hasZeroValue) {
                this.hasNext = true;
            }
            else {
                this.findNextIndex();
            }
        }

        public IntArray toArray() {
            IntArray v0 = new IntArray(true, this.set.size);
            while(this.hasNext) {
                v0.add(this.next());
            }

            return v0;
        }
    }

    private static final int EMPTY = 0;
    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    boolean hasZeroValue;
    private int hashShift;
    private IntSetIterator iterator1;
    private IntSetIterator iterator2;
    int[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;

    public IntSet() {
        this(32, 0.8f);
    }

    public IntSet(int initialCapacity, float loadFactor) {
        super();
        if(initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be >= 0: " + initialCapacity);
        }

        if(initialCapacity > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large: " + initialCapacity);
        }

        this.capacity = MathUtils.nextPowerOfTwo(initialCapacity);
        if(loadFactor <= 0f) {
            throw new IllegalArgumentException("loadFactor must be > 0: " + loadFactor);
        }

        this.loadFactor = loadFactor;
        this.threshold = ((int)((((float)this.capacity)) * loadFactor));
        this.mask = this.capacity - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(this.capacity);
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)this.capacity))))) * 2);
        this.pushIterations = Math.max(Math.min(this.capacity, 8), (((int)Math.sqrt(((double)this.capacity)))) / 8);
        this.keyTable = new int[this.capacity + this.stashCapacity];
    }

    public IntSet(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public IntSet(IntSet map) {
        this(map.capacity, map.loadFactor);
        this.stashSize = map.stashSize;
        System.arraycopy(map.keyTable, 0, this.keyTable, 0, map.keyTable.length);
        this.size = map.size;
        this.hasZeroValue = map.hasZeroValue;
    }

    public boolean add(int key) {
        int v0_1;
        boolean v0 = false;
        if(key != 0) {
            int[] v9 = this.keyTable;
            int v2 = key & this.mask;
            int v3 = v9[v2];
            if(v3 != key) {
                int v4 = this.hash2(key);
                int v5 = v9[v4];
                if(v5 != key) {
                    int v6 = this.hash3(key);
                    int v7 = v9[v6];
                    if(v7 != key) {
                        int v8 = this.capacity;
                        int v10 = v8 + this.stashSize;
                        while(true) {
                            if(v8 >= v10) {
                                break;
                            }
                            else if(v9[v8] != key) {
                                ++v8;
                                continue;
                            }

                            goto label_5;
                        }

                        if(v3 == 0) {
                            v9[v2] = key;
                            v0_1 = this.size;
                            this.size = v0_1 + 1;
                            if(v0_1 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v0 = true;
                        }
                        else {
                            if(v5 != 0) {
                                goto label_55;
                            }

                            v9[v4] = key;
                            v0_1 = this.size;
                            this.size = v0_1 + 1;
                            if(v0_1 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v0 = true;
                            goto label_5;
                        label_55:
                            if(v7 != 0) {
                                goto label_67;
                            }

                            v9[v6] = key;
                            v0_1 = this.size;
                            this.size = v0_1 + 1;
                            if(v0_1 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v0 = true;
                            goto label_5;
                        label_67:
                            this.push(key, v2, v3, v4, v5, v6, v7);
                            v0 = true;
                        }
                    }
                }
            }
        }
        else if(!this.hasZeroValue) {
            this.hasZeroValue = true;
            ++this.size;
            v0 = true;
        }

    label_5:
        return v0;
    }

    public void addAll(int[] array) {
        this.addAll(array, 0, array.length);
    }

    public void addAll(IntArray array) {
        this.addAll(array, 0, array.size);
    }

    public void addAll(IntArray array, int offset, int length) {
        if(offset + length > array.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + array.size);
        }

        this.addAll(array.items, offset, length);
    }

    public void addAll(int[] array, int offset, int length) {
        this.ensureCapacity(length);
        int v0 = offset;
        int v1 = v0 + length;
        while(v0 < v1) {
            this.add(array[v0]);
            ++v0;
        }
    }

    public void addAll(IntSet set) {
        this.ensureCapacity(set.size);
        IntSetIterator v0 = set.iterator();
        while(v0.hasNext) {
            this.add(v0.next());
        }
    }

    private void addResize(int key) {
        int v0;
        if(key == 0) {
            this.hasZeroValue = true;
        }
        else {
            int v2 = key & this.mask;
            int v3 = this.keyTable[v2];
            if(v3 == 0) {
                this.keyTable[v2] = key;
                v0 = this.size;
                this.size = v0 + 1;
                if(v0 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                int v4 = this.hash2(key);
                int v5 = this.keyTable[v4];
                if(v5 == 0) {
                    this.keyTable[v4] = key;
                    v0 = this.size;
                    this.size = v0 + 1;
                    if(v0 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    int v6 = this.hash3(key);
                    int v7 = this.keyTable[v6];
                    if(v7 == 0) {
                        this.keyTable[v6] = key;
                        v0 = this.size;
                        this.size = v0 + 1;
                        if(v0 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }
                    }
                    else {
                        this.push(key, v2, v3, v4, v5, v6, v7);
                    }
                }
            }
        }
    }

    private void addStash(int key) {
        if(this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.add(key);
        }
        else {
            this.keyTable[this.capacity + this.stashSize] = key;
            ++this.stashSize;
            ++this.size;
        }
    }

    public void clear() {
        if(this.size != 0) {
            int[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                int v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                v2[v0] = 0;
            }

            this.size = 0;
            this.stashSize = 0;
            this.hasZeroValue = false;
        }
    }

    public void clear(int maximumCapacity) {
        if(this.capacity <= maximumCapacity) {
            this.clear();
        }
        else {
            this.hasZeroValue = false;
            this.size = 0;
            this.resize(maximumCapacity);
        }
    }

    public boolean contains(int key) {
        boolean v1;
        if(key == 0) {
            v1 = this.hasZeroValue;
        }
        else {
            if(this.keyTable[key & this.mask] != key && this.keyTable[this.hash2(key)] != key && this.keyTable[this.hash3(key)] != key) {
                v1 = this.containsKeyStash(key);
                goto label_2;
            }

            v1 = true;
        }

    label_2:
        return v1;
    }

    private boolean containsKeyStash(int key) {
        boolean v3;
        int[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(v1[v0] == key) {
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

    public void ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 >= this.threshold) {
            this.resize(MathUtils.nextPowerOfTwo(((int)((((float)v0)) / this.loadFactor))));
        }
    }

    public int first() {
        int v3;
        if(this.hasZeroValue) {
            v3 = 0;
        }
        else {
            int[] v1 = this.keyTable;
            int v0 = 0;
            int v2 = this.capacity + this.stashSize;
            while(true) {
                if(v0 >= v2) {
                    break;
                }
                else if(v1[v0] != 0) {
                    v3 = v1[v0];
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_3;
            }

            throw new IllegalStateException("IntSet is empty.");
        }

    label_3:
        return v3;
    }

    private int hash2(int h) {
        h *= -1262997959;
        return (h >>> this.hashShift ^ h) & this.mask;
    }

    private int hash3(int h) {
        h *= -825114047;
        return (h >>> this.hashShift ^ h) & this.mask;
    }

    public IntSetIterator iterator() {
        IntSetIterator v0;
        if(this.iterator1 == null) {
            this.iterator1 = new IntSetIterator(this);
            this.iterator2 = new IntSetIterator(this);
        }

        if(!this.iterator1.valid) {
            this.iterator1.reset();
            this.iterator1.valid = true;
            this.iterator2.valid = false;
            v0 = this.iterator1;
        }
        else {
            this.iterator2.reset();
            this.iterator2.valid = true;
            this.iterator1.valid = false;
            v0 = this.iterator2;
        }

        return v0;
    }

    private void push(int insertKey, int index1, int key1, int index2, int key2, int index3, int key3) {
        int v5;
        int v0;
        int[] v2 = this.keyTable;
        int v3 = this.mask;
        int v1 = 0;
        int v4 = this.pushIterations;
    label_4:
        switch(MathUtils.random(2)) {
            case 0: {
                v0 = key1;
                v2[index1] = insertKey;
                break;
            }
            case 1: {
                v0 = key2;
                v2[index2] = insertKey;
                break;
            }
            default: {
                v0 = key3;
                v2[index3] = insertKey;
                break;
            }
        }

        index1 = v0 & v3;
        key1 = v2[index1];
        if(key1 == 0) {
            v2[index1] = v0;
            v5 = this.size;
            this.size = v5 + 1;
            if(v5 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            index2 = this.hash2(v0);
            key2 = v2[index2];
            if(key2 == 0) {
                v2[index2] = v0;
                v5 = this.size;
                this.size = v5 + 1;
                if(v5 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                index3 = this.hash3(v0);
                key3 = v2[index3];
                if(key3 == 0) {
                    v2[index3] = v0;
                    v5 = this.size;
                    this.size = v5 + 1;
                    if(v5 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    ++v1;
                    if(v1 == v4) {
                        this.addStash(v0);
                    }
                    else {
                        goto label_58;
                    }
                }
            }
        }

        return;
    label_58:
        insertKey = v0;
        goto label_4;
    }

    public boolean remove(int key) {
        boolean v1 = false;
        if(key != 0) {
            int v0 = key & this.mask;
            if(this.keyTable[v0] == key) {
                this.keyTable[v0] = 0;
                --this.size;
                v1 = true;
            }
            else {
                v0 = this.hash2(key);
                if(this.keyTable[v0] == key) {
                    this.keyTable[v0] = 0;
                    --this.size;
                    v1 = true;
                }
                else {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] == key) {
                        this.keyTable[v0] = 0;
                        --this.size;
                        v1 = true;
                    }
                    else {
                        v1 = this.removeStash(key);
                    }
                }
            }
        }
        else if(this.hasZeroValue) {
            this.hasZeroValue = false;
            --this.size;
            v1 = true;
        }

        return v1;
    }

    boolean removeStash(int key) {
        boolean v3;
        int[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(v1[v0] == key) {
                this.removeStashIndex(v0);
                --this.size;
                v3 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_12;
        }

        v3 = false;
    label_12:
        return v3;
    }

    void removeStashIndex(int index) {
        --this.stashSize;
        int v0 = this.capacity + this.stashSize;
        if(index < v0) {
            this.keyTable[index] = this.keyTable[v0];
        }
    }

    private void resize(int newSize) {
        int v5;
        int v2 = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = ((int)((((float)newSize)) * this.loadFactor));
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)newSize))))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (((int)Math.sqrt(((double)newSize)))) / 8);
        int[] v3 = this.keyTable;
        this.keyTable = new int[this.stashCapacity + newSize];
        int v4 = this.size;
        if(this.hasZeroValue) {
            v5 = 1;
        }
        else {
            v5 = 0;
        }

        this.size = v5;
        this.stashSize = 0;
        if(v4 > 0) {
            int v0;
            for(v0 = 0; v0 < v2; ++v0) {
                int v1 = v3[v0];
                if(v1 != 0) {
                    this.addResize(v1);
                }
            }
        }
    }

    public void shrink(int maximumCapacity) {
        if(maximumCapacity < 0) {
            throw new IllegalArgumentException("maximumCapacity must be >= 0: " + maximumCapacity);
        }

        if(this.size > maximumCapacity) {
            maximumCapacity = this.size;
        }

        if(this.capacity > maximumCapacity) {
            this.resize(MathUtils.nextPowerOfTwo(maximumCapacity));
        }
    }

    public String toString() {
        int v3;
        int v2;
        String v5;
        if(this.size == 0) {
            v5 = "[]";
        }
        else {
            StringBuilder v0 = new StringBuilder(32);
            v0.append('[');
            int[] v4 = this.keyTable;
            int v1 = v4.length;
            if(this.hasZeroValue) {
                v0.append("0");
                v2 = v1;
            }
            else {
                v2 = v1;
                while(true) {
                    v1 = v2 - 1;
                    if(v2 > 0) {
                        v3 = v4[v1];
                        if(v3 == 0) {
                            v2 = v1;
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        goto label_39;
                    }

                    goto label_15;
                }

                v0.append(v3);
                v2 = v1;
                goto label_15;
            label_39:
                v2 = v1;
            }

            while(true) {
            label_15:
                v1 = v2 - 1;
                if(v2 <= 0) {
                    break;
                }

                v3 = v4[v1];
                if(v3 != 0) {
                    goto label_30;
                }

                v2 = v1;
                continue;
            label_30:
                v0.append(", ");
                v0.append(v3);
                v2 = v1;
            }

            v0.append(']');
            v5 = v0.toString();
        }

        return v5;
    }

    public static IntSet with(int[] array) {
        IntSet v0 = new IntSet();
        v0.addAll(array);
        return v0;
    }
}

