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
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectSet implements Iterable {
    public class ObjectSetIterator implements Iterable, Iterator {
        int currentIndex;
        public boolean hasNext;
        int nextIndex;
        final ObjectSet set;
        boolean valid;

        public ObjectSetIterator(ObjectSet arg2) {
            super();
            this.valid = true;
            this.set = arg2;
            this.reset();
        }

        void findNextIndex() {
            this.hasNext = false;
            Object[] v0 = this.set.keyTable;
            int v1 = this.set.capacity + this.set.stashSize;
            do {
                int v2 = this.nextIndex + 1;
                this.nextIndex = v2;
                if(v2 < v1) {
                    if(v0[this.nextIndex] == null) {
                        continue;
                    }

                    break;
                }

                return;
            }
            while(true);

            this.hasNext = true;
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public ObjectSetIterator iterator() {
            return this;
        }

        public Iterator iterator() {
            return this.iterator();
        }

        public Object next() {
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.set.keyTable[this.nextIndex];
            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return v0;
        }

        public void remove() {
            if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            if(this.currentIndex >= this.set.capacity) {
                this.set.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                this.findNextIndex();
            }
            else {
                this.set.keyTable[this.currentIndex] = null;
            }

            this.currentIndex = -1;
            --this.set.size;
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            this.findNextIndex();
        }

        public Array toArray() {
            return this.toArray(new Array(true, this.set.size));
        }

        public Array toArray(Array arg2) {
            while(this.hasNext) {
                arg2.add(this.next());
            }

            return arg2;
        }
    }

    private static final int PRIME1 = -1105259343;
    private static final int PRIME2 = -1262997959;
    private static final int PRIME3 = -825114047;
    int capacity;
    private int hashShift;
    private ObjectSetIterator iterator1;
    private ObjectSetIterator iterator2;
    Object[] keyTable;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;

    public ObjectSet(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public ObjectSet() {
        this(32, 0.8f);
    }

    public ObjectSet(int initialCapacity, float loadFactor) {
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
        this.keyTable = new Object[this.capacity + this.stashCapacity];
    }

    public ObjectSet(ObjectSet set) {
        this(set.capacity, set.loadFactor);
        this.stashSize = set.stashSize;
        System.arraycopy(set.keyTable, 0, this.keyTable, 0, set.keyTable.length);
        this.size = set.size;
    }

    public boolean add(Object arg14) {
        int v0_1;
        boolean v0 = false;
        if(arg14 == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }

        Object[] v10 = this.keyTable;
        int v8 = arg14.hashCode();
        int v2 = v8 & this.mask;
        Object v3 = v10[v2];
        if(!arg14.equals(v3)) {
            int v4 = this.hash2(v8);
            Object v5 = v10[v4];
            if(!arg14.equals(v5)) {
                int v6 = this.hash3(v8);
                Object v7 = v10[v6];
                if(!arg14.equals(v7)) {
                    int v9 = this.capacity;
                    int v11 = v9 + this.stashSize;
                    while(true) {
                        if(v9 >= v11) {
                            break;
                        }
                        else if(!arg14.equals(v10[v9])) {
                            ++v9;
                            continue;
                        }

                        goto label_13;
                    }

                    if(v3 == null) {
                        v10[v2] = arg14;
                        v0_1 = this.size;
                        this.size = v0_1 + 1;
                        if(v0_1 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v0 = true;
                    }
                    else {
                        if(v5 != null) {
                            goto label_55;
                        }

                        v10[v4] = arg14;
                        v0_1 = this.size;
                        this.size = v0_1 + 1;
                        if(v0_1 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v0 = true;
                        goto label_13;
                    label_55:
                        if(v7 != null) {
                            goto label_67;
                        }

                        v10[v6] = arg14;
                        v0_1 = this.size;
                        this.size = v0_1 + 1;
                        if(v0_1 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v0 = true;
                        goto label_13;
                    label_67:
                        this.push(arg14, v2, v3, v4, v5, v6, v7);
                        v0 = true;
                    }
                }
            }
        }

    label_13:
        return v0;
    }

    public void addAll(Object[] arg3) {
        this.addAll(arg3, 0, arg3.length);
    }

    public void addAll(Array arg3) {
        this.addAll(arg3, 0, arg3.size);
    }

    public void addAll(Array arg4, int offset, int length) {
        if(offset + length > arg4.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + arg4.size);
        }

        this.addAll(arg4.items, offset, length);
    }

    public void addAll(Object[] arg4, int offset, int length) {
        this.ensureCapacity(length);
        int v0 = offset;
        int v1 = v0 + length;
        while(v0 < v1) {
            this.add(arg4[v0]);
            ++v0;
        }
    }

    public void addAll(ObjectSet arg4) {
        this.ensureCapacity(arg4.size);
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.add(v0.next());
        }
    }

    private void addResize(Object arg10) {
        int v0;
        int v8 = arg10.hashCode();
        int v2 = v8 & this.mask;
        Object v3 = this.keyTable[v2];
        if(v3 == null) {
            this.keyTable[v2] = arg10;
            v0 = this.size;
            this.size = v0 + 1;
            if(v0 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            int v4 = this.hash2(v8);
            Object v5 = this.keyTable[v4];
            if(v5 == null) {
                this.keyTable[v4] = arg10;
                v0 = this.size;
                this.size = v0 + 1;
                if(v0 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                int v6 = this.hash3(v8);
                Object v7 = this.keyTable[v6];
                if(v7 == null) {
                    this.keyTable[v6] = arg10;
                    v0 = this.size;
                    this.size = v0 + 1;
                    if(v0 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    this.push(arg10, v2, v3, v4, v5, v6, v7);
                }
            }
        }
    }

    private void addStash(Object arg4) {
        if(this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.add(arg4);
        }
        else {
            this.keyTable[this.capacity + this.stashSize] = arg4;
            ++this.stashSize;
            ++this.size;
        }
    }

    public void clear() {
        if(this.size != 0) {
            Object[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                int v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                v2[v0] = null;
            }

            this.size = 0;
            this.stashSize = 0;
        }
    }

    public void clear(int maximumCapacity) {
        if(this.capacity <= maximumCapacity) {
            this.clear();
        }
        else {
            this.size = 0;
            this.resize(maximumCapacity);
        }
    }

    public boolean contains(Object arg4) {
        boolean v2;
        int v0 = arg4.hashCode();
        if((arg4.equals(this.keyTable[v0 & this.mask])) || (arg4.equals(this.keyTable[this.hash2(v0)])) || (arg4.equals(this.keyTable[this.hash3(v0)]))) {
            v2 = true;
        }
        else {
            v2 = this.containsKeyStash(arg4);
        }

        return v2;
    }

    private boolean containsKeyStash(Object arg5) {
        boolean v3;
        Object[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(arg5.equals(v1[v0])) {
                v3 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_9;
        }

        v3 = false;
    label_9:
        return v3;
    }

    public void ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 >= this.threshold) {
            this.resize(MathUtils.nextPowerOfTwo(((int)((((float)v0)) / this.loadFactor))));
        }
    }

    public Object first() {
        Object[] v1 = this.keyTable;
        int v0 = 0;
        int v2 = this.capacity + this.stashSize;
        while(v0 < v2) {
            if(v1[v0] != null) {
                return v1[v0];
            }

            ++v0;
        }

        throw new IllegalStateException("IntSet is empty.");
    }

    private int hash2(int h) {
        h *= -1262997959;
        return (h >>> this.hashShift ^ h) & this.mask;
    }

    private int hash3(int h) {
        h *= -825114047;
        return (h >>> this.hashShift ^ h) & this.mask;
    }

    public Iterator iterator() {
        return this.iterator();
    }

    public ObjectSetIterator iterator() {
        ObjectSetIterator v0;
        if(this.iterator1 == null) {
            this.iterator1 = new ObjectSetIterator(this);
            this.iterator2 = new ObjectSetIterator(this);
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

    private void push(Object arg9, int index1, Object arg11, int index2, Object arg13, int index3, Object arg15) {
        int v6;
        Object v0;
        Object[] v3 = this.keyTable;
        int v4 = this.mask;
        int v2 = 0;
        int v5 = this.pushIterations;
    label_4:
        switch(MathUtils.random(2)) {
            case 0: {
                v0 = arg11;
                v3[index1] = arg9;
                break;
            }
            case 1: {
                v0 = arg13;
                v3[index2] = arg9;
                break;
            }
            default: {
                v0 = arg15;
                v3[index3] = arg9;
                break;
            }
        }

        int v1 = v0.hashCode();
        index1 = v1 & v4;
        arg11 = v3[index1];
        if(arg11 == null) {
            v3[index1] = v0;
            v6 = this.size;
            this.size = v6 + 1;
            if(v6 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            index2 = this.hash2(v1);
            arg13 = v3[index2];
            if(arg13 == null) {
                v3[index2] = v0;
                v6 = this.size;
                this.size = v6 + 1;
                if(v6 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                index3 = this.hash3(v1);
                arg15 = v3[index3];
                if(arg15 == null) {
                    v3[index3] = v0;
                    v6 = this.size;
                    this.size = v6 + 1;
                    if(v6 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    ++v2;
                    if(v2 == v5) {
                        this.addStash(v0);
                    }
                    else {
                        goto label_59;
                    }
                }
            }
        }

        return;
    label_59:
        arg9 = v0;
        goto label_4;
    }

    public boolean remove(Object arg6) {
        Object v4 = null;
        boolean v2 = true;
        int v0 = arg6.hashCode();
        int v1 = v0 & this.mask;
        if(arg6.equals(this.keyTable[v1])) {
            this.keyTable[v1] = v4;
            --this.size;
        }
        else {
            v1 = this.hash2(v0);
            if(arg6.equals(this.keyTable[v1])) {
                this.keyTable[v1] = v4;
                --this.size;
            }
            else {
                v1 = this.hash3(v0);
                if(arg6.equals(this.keyTable[v1])) {
                    this.keyTable[v1] = v4;
                    --this.size;
                }
                else {
                    v2 = this.removeStash(arg6);
                }
            }
        }

        return v2;
    }

    boolean removeStash(Object arg5) {
        boolean v3;
        Object[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(arg5.equals(v1[v0])) {
                this.removeStashIndex(v0);
                --this.size;
                v3 = true;
            }
            else {
                ++v0;
                continue;
            }

            goto label_13;
        }

        v3 = false;
    label_13:
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
        int v2 = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = ((int)((((float)newSize)) * this.loadFactor));
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)newSize))))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (((int)Math.sqrt(((double)newSize)))) / 8);
        Object[] v3 = this.keyTable;
        this.keyTable = new Object[this.stashCapacity + newSize];
        int v4 = this.size;
        this.size = 0;
        this.stashSize = 0;
        if(v4 > 0) {
            int v0;
            for(v0 = 0; v0 < v2; ++v0) {
                Object v1 = v3[v0];
                if(v1 != null) {
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
        return '{' + this.toString(", ") + '}';
    }

    public String toString(String separator) {
        Object v3;
        int v1;
        String v5;
        if(this.size == 0) {
            v5 = "";
        }
        else {
            StringBuilder v0 = new StringBuilder(32);
            Object[] v4 = this.keyTable;
            int v2 = v4.length;
            while(true) {
                v1 = v2 - 1;
                if(v2 > 0) {
                    v3 = v4[v1];
                    if(v3 == null) {
                        v2 = v1;
                        continue;
                    }
                    else {
                        break;
                    }
                }
                else {
                    goto label_29;
                }

                goto label_17;
            }

            v0.append(v3);
            v2 = v1;
            goto label_17;
        label_29:
            for(v2 = v1; true; v2 = v1) {
            label_17:
                v1 = v2 - 1;
                if(v2 <= 0) {
                    break;
                }

                v3 = v4[v1];
                if(v3 != null) {
                    goto label_23;
                }

                v2 = v1;
                continue;
            label_23:
                v0.append(separator);
                v0.append(v3);
            }

            v5 = v0.toString();
        }

        return v5;
    }

    public static ObjectSet with(Object[] arg1) {
        ObjectSet v0 = new ObjectSet();
        v0.addAll(arg1);
        return v0;
    }
}

