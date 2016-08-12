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

public class LongMap implements Iterable {
    public class Entries extends MapIterator implements Iterable, Iterator {
        private Entry entry;

        public Entries(LongMap map) {
            super(map);
            this.entry = new Entry();
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public Iterator iterator() {
            return this;
        }

        public Entry next() {
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            long[] v0 = this.map.keyTable;
            if(this.nextIndex == -1) {
                this.entry.key = 0;
                this.entry.value = this.map.zeroValue;
            }
            else {
                this.entry.key = v0[this.nextIndex];
                this.entry.value = this.map.valueTable[this.nextIndex];
            }

            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return this.entry;
        }

        public Object next() {
            return this.next();
        }

        public void remove() {
            super.remove();
        }

        public void reset() {
            super.reset();
        }
    }

    public class Entry {
        public Entry() {
            super();
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

    public class Keys extends MapIterator {
        public Keys(LongMap map) {
            super(map);
        }

        public long next() {
            long v0;
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
                v0 = this.map.keyTable[this.nextIndex];
            }

            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return v0;
        }

        public void remove() {
            super.remove();
        }

        public void reset() {
            super.reset();
        }

        public LongArray toArray() {
            LongArray v0 = new LongArray(true, this.map.size);
            while(this.hasNext) {
                v0.add(this.next());
            }

            return v0;
        }
    }

    class MapIterator {
        static final int INDEX_ILLEGAL = -2;
        static final int INDEX_ZERO = -1;
        int currentIndex;
        public boolean hasNext;
        final LongMap map;
        int nextIndex;
        boolean valid;

        public MapIterator(LongMap arg2) {
            super();
            this.valid = true;
            this.map = arg2;
            this.reset();
        }

        void findNextIndex() {
            this.hasNext = false;
            long[] v0 = this.map.keyTable;
            int v1 = this.map.capacity + this.map.stashSize;
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

        public void remove() {
            Object v4 = null;
            if(this.currentIndex == -1 && (this.map.hasZeroValue)) {
                this.map.zeroValue = v4;
                this.map.hasZeroValue = false;
            }
            else if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }
            else if(this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                this.findNextIndex();
            }
            else {
                this.map.keyTable[this.currentIndex] = 0;
                this.map.valueTable[this.currentIndex] = v4;
            }

            this.currentIndex = -2;
            --this.map.size;
        }

        public void reset() {
            this.currentIndex = -2;
            this.nextIndex = -1;
            if(this.map.hasZeroValue) {
                this.hasNext = true;
            }
            else {
                this.findNextIndex();
            }
        }
    }

    public class Values extends MapIterator implements Iterable, Iterator {
        public Values(LongMap arg1) {
            super(arg1);
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public Iterator iterator() {
            return this;
        }

        public Object next() {
            Object v0;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.nextIndex == -1) {
                v0 = this.map.zeroValue;
            }
            else {
                v0 = this.map.valueTable[this.nextIndex];
            }

            this.currentIndex = this.nextIndex;
            this.findNextIndex();
            return v0;
        }

        public void remove() {
            super.remove();
        }

        public void reset() {
            super.reset();
        }

        public Array toArray() {
            Array v0 = new Array(true, this.map.size);
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
    private Entries entries1;
    private Entries entries2;
    boolean hasZeroValue;
    private int hashShift;
    long[] keyTable;
    private Keys keys1;
    private Keys keys2;
    private float loadFactor;
    private int mask;
    private int pushIterations;
    public int size;
    private int stashCapacity;
    int stashSize;
    private int threshold;
    Object[] valueTable;
    private Values values1;
    private Values values2;
    Object zeroValue;

    public LongMap() {
        this(32, 0.8f);
    }

    public LongMap(int initialCapacity, float loadFactor) {
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
        this.hashShift = 63 - Long.numberOfTrailingZeros(((long)this.capacity));
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)this.capacity))))) * 2);
        this.pushIterations = Math.max(Math.min(this.capacity, 8), (((int)Math.sqrt(((double)this.capacity)))) / 8);
        this.keyTable = new long[this.capacity + this.stashCapacity];
        this.valueTable = new Object[this.keyTable.length];
    }

    public LongMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public LongMap(LongMap arg5) {
        this(arg5.capacity, arg5.loadFactor);
        this.stashSize = arg5.stashSize;
        System.arraycopy(arg5.keyTable, 0, this.keyTable, 0, arg5.keyTable.length);
        System.arraycopy(arg5.valueTable, 0, this.valueTable, 0, arg5.valueTable.length);
        this.size = arg5.size;
        this.zeroValue = arg5.zeroValue;
        this.hasZeroValue = arg5.hasZeroValue;
    }

    public void clear() {
        Object v7 = null;
        if(this.size != 0) {
            long[] v2 = this.keyTable;
            Object[] v3 = this.valueTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                int v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                v2[v0] = 0;
                v3[v0] = v7;
            }

            this.size = 0;
            this.stashSize = 0;
            this.zeroValue = v7;
            this.hasZeroValue = false;
        }
    }

    public void clear(int maximumCapacity) {
        if(this.capacity <= maximumCapacity) {
            this.clear();
        }
        else {
            this.zeroValue = null;
            this.hasZeroValue = false;
            this.size = 0;
            this.resize(maximumCapacity);
        }
    }

    public boolean containsKey(long key) {
        boolean v1;
        if(key == 0) {
            v1 = this.hasZeroValue;
        }
        else {
            if(this.keyTable[((int)((((long)this.mask)) & key))] != key && this.keyTable[this.hash2(key)] != key && this.keyTable[this.hash3(key)] != key) {
                v1 = this.containsKeyStash(key);
                goto label_3;
            }

            v1 = true;
        }

    label_3:
        return v1;
    }

    private boolean containsKeyStash(long key) {
        boolean v3;
        long[] v1 = this.keyTable;
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

    public boolean containsValue(Object value, boolean identity) {
        int v0;
        boolean v4 = true;
        Object[] v3 = this.valueTable;
        if(value == null) {
            if((this.hasZeroValue) && this.zeroValue == null) {
                goto label_7;
            }

            long[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_51;
                }

                if(v2[v0] != 0 && v3[v0] == null) {
                    break;
                }
            }
        }
        else {
            if(!identity) {
                goto label_35;
            }

            if(value == this.zeroValue) {
                goto label_7;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_51;
                }

                if(v3[v0] == value) {
                    goto label_7;
                }
            }

        label_35:
            if((this.hasZeroValue) && (value.equals(this.zeroValue))) {
                goto label_7;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(value.equals(v3[v0])) {
                    goto label_7;
                }
            }

        label_51:
            v4 = false;
        }

    label_7:
        return v4;
    }

    public void ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 >= this.threshold) {
            this.resize(MathUtils.nextPowerOfTwo(((int)((((float)v0)) / this.loadFactor))));
        }
    }

    public Entries entries() {
        Entries v0;
        if(this.entries1 == null) {
            this.entries1 = new Entries(this);
            this.entries2 = new Entries(this);
        }

        if(!this.entries1.valid) {
            this.entries1.reset();
            this.entries1.valid = true;
            this.entries2.valid = false;
            v0 = this.entries1;
        }
        else {
            this.entries2.reset();
            this.entries2.valid = true;
            this.entries1.valid = false;
            v0 = this.entries2;
        }

        return v0;
    }

    public long findKey(Object value, boolean identity, long notFound) {
        int v0;
        long v4 = 0;
        Object[] v3 = this.valueTable;
        if(value == null) {
            if((this.hasZeroValue) && this.zeroValue == null) {
                notFound = v4;
                goto label_8;
            }

            long[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(v2[v0] != v4 && v3[v0] == null) {
                    notFound = v2[v0];
                    break;
                }
            }
        }
        else {
            if(!identity) {
                goto label_38;
            }

            if(value != this.zeroValue) {
                goto label_27;
            }

            notFound = v4;
            goto label_8;
        label_27:
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_8;
                }

                if(v3[v0] != value) {
                    goto label_59;
                }

                notFound = this.keyTable[v0];
                goto label_8;
            label_59:
            }

        label_38:
            if((this.hasZeroValue) && (value.equals(this.zeroValue))) {
                notFound = v4;
                goto label_8;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(!value.equals(v3[v0])) {
                    goto label_57;
                }

                notFound = this.keyTable[v0];
                break;
            label_57:
            }
        }

    label_8:
        return notFound;
    }

    public Object get(long key) {
        Object v1 = null;
        if(key != 0) {
            int v0 = ((int)((((long)this.mask)) & key));
            if(this.keyTable[v0] != key) {
                v0 = this.hash2(key);
                if(this.keyTable[v0] != key) {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] != key) {
                        v1 = this.getStash(key, v1);
                        goto label_5;
                    }
                }
            }

            v1 = this.valueTable[v0];
        }
        else if(this.hasZeroValue) {
            v1 = this.zeroValue;
        }

    label_5:
        return v1;
    }

    public Object get(long key, Object arg8) {
        if(key != 0) {
            int v0 = ((int)((((long)this.mask)) & key));
            if(this.keyTable[v0] != key) {
                v0 = this.hash2(key);
                if(this.keyTable[v0] != key) {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] != key) {
                        arg8 = this.getStash(key, arg8);
                        goto label_4;
                    }
                }
            }

            arg8 = this.valueTable[v0];
        }
        else if(this.hasZeroValue) {
            arg8 = this.zeroValue;
        }

    label_4:
        return arg8;
    }

    private Object getStash(long key, Object arg10) {
        long[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(v0 < v2) {
            if(v1[v0] == key) {
                arg10 = this.valueTable[v0];
            }
            else {
                ++v0;
                continue;
            }

            break;
        }

        return arg10;
    }

    private int hash2(long h) {
        h *= -1262997959;
        return ((int)((h >>> this.hashShift ^ h) & (((long)this.mask))));
    }

    private int hash3(long h) {
        h *= -825114047;
        return ((int)((h >>> this.hashShift ^ h) & (((long)this.mask))));
    }

    public Iterator iterator() {
        return this.entries();
    }

    public Keys keys() {
        Keys v0;
        if(this.keys1 == null) {
            this.keys1 = new Keys(this);
            this.keys2 = new Keys(this);
        }

        if(!this.keys1.valid) {
            this.keys1.reset();
            this.keys1.valid = true;
            this.keys2.valid = false;
            v0 = this.keys1;
        }
        else {
            this.keys2.reset();
            this.keys2.valid = true;
            this.keys1.valid = false;
            v0 = this.keys2;
        }

        return v0;
    }

    private void push(long insertKey, Object arg14, int index1, long key1, int index2, long key2, int index3, long key3) {
        int v8;
        Object v2;
        long v0;
        long[] v4 = this.keyTable;
        Object[] v7 = this.valueTable;
        int v5 = this.mask;
        int v3 = 0;
        int v6 = this.pushIterations;
    label_5:
        switch(MathUtils.random(2)) {
            case 0: {
                v0 = key1;
                v2 = v7[index1];
                v4[index1] = insertKey;
                v7[index1] = arg14;
                break;
            }
            case 1: {
                v0 = key2;
                v2 = v7[index2];
                v4[index2] = insertKey;
                v7[index2] = arg14;
                break;
            }
            default: {
                v0 = key3;
                v2 = v7[index3];
                v4[index3] = insertKey;
                v7[index3] = arg14;
                break;
            }
        }

        index1 = ((int)((((long)v5)) & v0));
        key1 = v4[index1];
        if(key1 == 0) {
            v4[index1] = v0;
            v7[index1] = v2;
            v8 = this.size;
            this.size = v8 + 1;
            if(v8 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            index2 = this.hash2(v0);
            key2 = v4[index2];
            if(key2 == 0) {
                v4[index2] = v0;
                v7[index2] = v2;
                v8 = this.size;
                this.size = v8 + 1;
                if(v8 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                index3 = this.hash3(v0);
                key3 = v4[index3];
                if(key3 == 0) {
                    v4[index3] = v0;
                    v7[index3] = v2;
                    v8 = this.size;
                    this.size = v8 + 1;
                    if(v8 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    ++v3;
                    if(v3 == v6) {
                        this.putStash(v0, v2);
                    }
                    else {
                        goto label_73;
                    }
                }
            }
        }

        return;
    label_73:
        insertKey = v0;
        arg14 = v2;
        goto label_5;
    }

    public Object put(long key, Object arg22) {
        int v3;
        Object v18;
        if(key == 0) {
            v18 = this.zeroValue;
            this.zeroValue = arg22;
            if(!this.hasZeroValue) {
                this.hasZeroValue = true;
                ++this.size;
            }
        }
        else {
            long[] v16 = this.keyTable;
            int v7 = ((int)((((long)this.mask)) & key));
            long v8 = v16[v7];
            if(v8 == key) {
                v18 = this.valueTable[v7];
                this.valueTable[v7] = arg22;
            }
            else {
                int v10 = this.hash2(key);
                long v11 = v16[v10];
                if(v11 == key) {
                    v18 = this.valueTable[v10];
                    this.valueTable[v10] = arg22;
                }
                else {
                    int v13 = this.hash3(key);
                    long v14 = v16[v13];
                    if(v14 == key) {
                        v18 = this.valueTable[v13];
                        this.valueTable[v13] = arg22;
                    }
                    else {
                        int v2 = this.capacity;
                        int v17 = v2 + this.stashSize;
                        while(true) {
                            if(v2 >= v17) {
                                break;
                            }
                            else if(v16[v2] == key) {
                                v18 = this.valueTable[v2];
                                this.valueTable[v2] = arg22;
                            }
                            else {
                                ++v2;
                                continue;
                            }

                            goto label_19;
                        }

                        if(v8 == 0) {
                            v16[v7] = key;
                            this.valueTable[v7] = arg22;
                            v3 = this.size;
                            this.size = v3 + 1;
                            if(v3 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v18 = null;
                        }
                        else {
                            if(v11 != 0) {
                                goto label_117;
                            }

                            v16[v10] = key;
                            this.valueTable[v10] = arg22;
                            v3 = this.size;
                            this.size = v3 + 1;
                            if(v3 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v18 = null;
                            goto label_19;
                        label_117:
                            if(v14 != 0) {
                                goto label_138;
                            }

                            v16[v13] = key;
                            this.valueTable[v13] = arg22;
                            v3 = this.size;
                            this.size = v3 + 1;
                            if(v3 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }

                            v18 = null;
                            goto label_19;
                        label_138:
                            this.push(key, arg22, v7, v8, v10, v11, v13, v14);
                            v18 = null;
                        }
                    }
                }
            }
        }

    label_19:
        return v18;
    }

    public void putAll(LongMap arg6) {
        Iterator v1 = arg6.entries().iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            this.put(((Entry)v0).key, ((Entry)v0).value);
        }
    }

    private void putResize(long key, Object arg20) {
        int v2;
        if(key == 0) {
            this.zeroValue = arg20;
            this.hasZeroValue = true;
        }
        else {
            int v7 = ((int)((((long)this.mask)) & key));
            long v8 = this.keyTable[v7];
            if(v8 == 0) {
                this.keyTable[v7] = key;
                this.valueTable[v7] = arg20;
                v2 = this.size;
                this.size = v2 + 1;
                if(v2 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                int v10 = this.hash2(key);
                long v11 = this.keyTable[v10];
                if(v11 == 0) {
                    this.keyTable[v10] = key;
                    this.valueTable[v10] = arg20;
                    v2 = this.size;
                    this.size = v2 + 1;
                    if(v2 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    int v13 = this.hash3(key);
                    long v14 = this.keyTable[v13];
                    if(v14 == 0) {
                        this.keyTable[v13] = key;
                        this.valueTable[v13] = arg20;
                        v2 = this.size;
                        this.size = v2 + 1;
                        if(v2 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }
                    }
                    else {
                        this.push(key, arg20, v7, v8, v10, v11, v13, v14);
                    }
                }
            }
        }
    }

    private void putStash(long key, Object arg6) {
        if(this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.put(key, arg6);
        }
        else {
            int v0 = this.capacity + this.stashSize;
            this.keyTable[v0] = key;
            this.valueTable[v0] = arg6;
            ++this.stashSize;
            ++this.size;
        }
    }

    public Object remove(long key) {
        Object v1;
        long v6 = 0;
        Object v2 = null;
        if(key != v6) {
            int v0 = ((int)((((long)this.mask)) & key));
            if(this.keyTable[v0] == key) {
                this.keyTable[v0] = v6;
                v1 = this.valueTable[v0];
                this.valueTable[v0] = v2;
                --this.size;
            }
            else {
                v0 = this.hash2(key);
                if(this.keyTable[v0] == key) {
                    this.keyTable[v0] = v6;
                    v1 = this.valueTable[v0];
                    this.valueTable[v0] = v2;
                    --this.size;
                }
                else {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] == key) {
                        this.keyTable[v0] = v6;
                        v1 = this.valueTable[v0];
                        this.valueTable[v0] = v2;
                        --this.size;
                    }
                    else {
                        v1 = this.removeStash(key);
                    }
                }
            }
        }
        else if(!this.hasZeroValue) {
            v1 = v2;
        }
        else {
            v1 = this.zeroValue;
            this.zeroValue = v2;
            this.hasZeroValue = false;
            --this.size;
        }

        return v1;
    }

    Object removeStash(long key) {
        Object v3;
        long[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(v1[v0] == key) {
                v3 = this.valueTable[v0];
                this.removeStashIndex(v0);
                --this.size;
            }
            else {
                ++v0;
                continue;
            }

            goto label_13;
        }

        v3 = null;
    label_13:
        return v3;
    }

    void removeStashIndex(int index) {
        Object v4 = null;
        --this.stashSize;
        int v0 = this.capacity + this.stashSize;
        if(index < v0) {
            this.keyTable[index] = this.keyTable[v0];
            this.valueTable[index] = this.valueTable[v0];
            this.valueTable[v0] = v4;
        }
        else {
            this.valueTable[index] = v4;
        }
    }

    private void resize(int newSize) {
        int v7;
        int v1 = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = ((int)((((float)newSize)) * this.loadFactor));
        this.mask = newSize - 1;
        this.hashShift = 63 - Long.numberOfTrailingZeros(((long)newSize));
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)newSize))))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (((int)Math.sqrt(((double)newSize)))) / 8);
        long[] v4 = this.keyTable;
        Object[] v6 = this.valueTable;
        this.keyTable = new long[this.stashCapacity + newSize];
        this.valueTable = new Object[this.stashCapacity + newSize];
        int v5 = this.size;
        if(this.hasZeroValue) {
            v7 = 1;
        }
        else {
            v7 = 0;
        }

        this.size = v7;
        this.stashSize = 0;
        if(v5 > 0) {
            int v0;
            for(v0 = 0; v0 < v1; ++v0) {
                long v2 = v4[v0];
                if(v2 != 0) {
                    this.putResize(v2, v6[v0]);
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
        long v4;
        int v1;
        String v7;
        long v10 = 0;
        char v8 = '=';
        if(this.size == 0) {
            v7 = "[]";
        }
        else {
            StringBuilder v0 = new StringBuilder(32);
            v0.append('[');
            long[] v3 = this.keyTable;
            Object[] v6 = this.valueTable;
            int v2 = v3.length;
            while(true) {
                v1 = v2 - 1;
                if(v2 > 0) {
                    v4 = v3[v1];
                    if(v4 == v10) {
                        v2 = v1;
                        continue;
                    }
                    else {
                        break;
                    }
                }
                else {
                    goto label_43;
                }

                goto label_25;
            }

            v0.append(v4);
            v0.append(v8);
            v0.append(v6[v1]);
            v2 = v1;
            goto label_25;
        label_43:
            for(v2 = v1; true; v2 = v1) {
            label_25:
                v1 = v2 - 1;
                if(v2 <= 0) {
                    break;
                }

                v4 = v3[v1];
                if(v4 != v10) {
                    goto label_31;
                }

                v2 = v1;
                continue;
            label_31:
                v0.append(", ");
                v0.append(v4);
                v0.append(v8);
                v0.append(v6[v1]);
            }

            v0.append(']');
            v7 = v0.toString();
        }

        return v7;
    }

    public Values values() {
        Values v0;
        if(this.values1 == null) {
            this.values1 = new Values(this);
            this.values2 = new Values(this);
        }

        if(!this.values1.valid) {
            this.values1.reset();
            this.values1.valid = true;
            this.values2.valid = false;
            v0 = this.values1;
        }
        else {
            this.values2.reset();
            this.values2.valid = true;
            this.values1.valid = false;
            v0 = this.values2;
        }

        return v0;
    }
}

