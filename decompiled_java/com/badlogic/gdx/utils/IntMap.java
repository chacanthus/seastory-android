﻿// 도박중독 예방 캠페인
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

public class IntMap implements Iterable {
    public class Entries extends MapIterator implements Iterable, Iterator {
        private Entry entry;

        public Entries(IntMap map) {
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

            int[] v0 = this.map.keyTable;
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
        public Keys(IntMap map) {
            super(map);
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

        public IntArray toArray() {
            IntArray v0 = new IntArray(true, this.map.size);
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
        final IntMap map;
        int nextIndex;
        boolean valid;

        public MapIterator(IntMap arg2) {
            super();
            this.valid = true;
            this.map = arg2;
            this.reset();
        }

        void findNextIndex() {
            this.hasNext = false;
            int[] v0 = this.map.keyTable;
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
            Object v3 = null;
            if(this.currentIndex == -1 && (this.map.hasZeroValue)) {
                this.map.zeroValue = v3;
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
                this.map.valueTable[this.currentIndex] = v3;
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
        public Values(IntMap arg1) {
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
    int[] keyTable;
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

    public IntMap() {
        this(32, 0.8f);
    }

    public IntMap(int initialCapacity, float loadFactor) {
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
        this.valueTable = new Object[this.keyTable.length];
    }

    public IntMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public IntMap(IntMap arg5) {
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
            int[] v2 = this.keyTable;
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

    public boolean containsKey(int key) {
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

    public boolean containsValue(Object value, boolean identity) {
        int v0;
        boolean v4 = true;
        Object[] v3 = this.valueTable;
        if(value == null) {
            if((this.hasZeroValue) && this.zeroValue == null) {
                goto label_7;
            }

            int[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_50;
                }

                if(v2[v0] != 0 && v3[v0] == null) {
                    break;
                }
            }
        }
        else {
            if(!identity) {
                goto label_34;
            }

            if(value == this.zeroValue) {
                goto label_7;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_50;
                }

                if(v3[v0] == value) {
                    goto label_7;
                }
            }

        label_34:
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

        label_50:
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

    public int findKey(Object value, boolean identity, int notFound) {
        int v0;
        Object[] v3 = this.valueTable;
        if(value == null) {
            if((this.hasZeroValue) && this.zeroValue == null) {
                notFound = 0;
                goto label_8;
            }

            int[] v2 = this.keyTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(v2[v0] != 0 && v3[v0] == null) {
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

            notFound = 0;
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
                notFound = 0;
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

    public Object get(int key) {
        Object v1 = null;
        if(key != 0) {
            int v0 = key & this.mask;
            if(this.keyTable[v0] != key) {
                v0 = this.hash2(key);
                if(this.keyTable[v0] != key) {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] != key) {
                        v1 = this.getStash(key, v1);
                        goto label_4;
                    }
                }
            }

            v1 = this.valueTable[v0];
        }
        else if(this.hasZeroValue) {
            v1 = this.zeroValue;
        }

    label_4:
        return v1;
    }

    public Object get(int key, Object arg4) {
        if(key != 0) {
            int v0 = key & this.mask;
            if(this.keyTable[v0] != key) {
                v0 = this.hash2(key);
                if(this.keyTable[v0] != key) {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] != key) {
                        arg4 = this.getStash(key, arg4);
                        goto label_3;
                    }
                }
            }

            arg4 = this.valueTable[v0];
        }
        else if(this.hasZeroValue) {
            arg4 = this.zeroValue;
        }

    label_3:
        return arg4;
    }

    private Object getStash(int key, Object arg6) {
        int[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(v0 < v2) {
            if(v1[v0] == key) {
                arg6 = this.valueTable[v0];
            }
            else {
                ++v0;
                continue;
            }

            break;
        }

        return arg6;
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

    private void push(int insertKey, Object arg11, int index1, int key1, int index2, int key2, int index3, int key3) {
        int v7;
        Object v1;
        int v0;
        int[] v3 = this.keyTable;
        Object[] v6 = this.valueTable;
        int v4 = this.mask;
        int v2 = 0;
        int v5 = this.pushIterations;
    label_5:
        switch(MathUtils.random(2)) {
            case 0: {
                v0 = key1;
                v1 = v6[index1];
                v3[index1] = insertKey;
                v6[index1] = arg11;
                break;
            }
            case 1: {
                v0 = key2;
                v1 = v6[index2];
                v3[index2] = insertKey;
                v6[index2] = arg11;
                break;
            }
            default: {
                v0 = key3;
                v1 = v6[index3];
                v3[index3] = insertKey;
                v6[index3] = arg11;
                break;
            }
        }

        index1 = v0 & v4;
        key1 = v3[index1];
        if(key1 == 0) {
            v3[index1] = v0;
            v6[index1] = v1;
            v7 = this.size;
            this.size = v7 + 1;
            if(v7 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            index2 = this.hash2(v0);
            key2 = v3[index2];
            if(key2 == 0) {
                v3[index2] = v0;
                v6[index2] = v1;
                v7 = this.size;
                this.size = v7 + 1;
                if(v7 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                index3 = this.hash3(v0);
                key3 = v3[index3];
                if(key3 == 0) {
                    v3[index3] = v0;
                    v6[index3] = v1;
                    v7 = this.size;
                    this.size = v7 + 1;
                    if(v7 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    ++v2;
                    if(v2 == v5) {
                        this.putStash(v0, v1);
                    }
                    else {
                        goto label_68;
                    }
                }
            }
        }

        return;
    label_68:
        insertKey = v0;
        arg11 = v1;
        goto label_5;
    }

    public Object put(int key, Object arg15) {
        int v0;
        Object v12 = null;
        if(key == 0) {
            v12 = this.zeroValue;
            this.zeroValue = arg15;
            if(!this.hasZeroValue) {
                this.hasZeroValue = true;
                ++this.size;
            }
        }
        else {
            int[] v10 = this.keyTable;
            int v3 = key & this.mask;
            int v4 = v10[v3];
            if(v4 == key) {
                v12 = this.valueTable[v3];
                this.valueTable[v3] = arg15;
            }
            else {
                int v5 = this.hash2(key);
                int v6 = v10[v5];
                if(v6 == key) {
                    v12 = this.valueTable[v5];
                    this.valueTable[v5] = arg15;
                }
                else {
                    int v7 = this.hash3(key);
                    int v8 = v10[v7];
                    if(v8 == key) {
                        v12 = this.valueTable[v7];
                        this.valueTable[v7] = arg15;
                    }
                    else {
                        int v9 = this.capacity;
                        int v11 = v9 + this.stashSize;
                        while(true) {
                            if(v9 >= v11) {
                                break;
                            }
                            else if(v10[v9] == key) {
                                v12 = this.valueTable[v9];
                                this.valueTable[v9] = arg15;
                            }
                            else {
                                ++v9;
                                continue;
                            }

                            goto label_11;
                        }

                        if(v4 == 0) {
                            v10[v3] = key;
                            this.valueTable[v3] = arg15;
                            v0 = this.size;
                            this.size = v0 + 1;
                            if(v0 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }
                        }
                        else if(v6 == 0) {
                            v10[v5] = key;
                            this.valueTable[v5] = arg15;
                            v0 = this.size;
                            this.size = v0 + 1;
                            if(v0 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }
                        }
                        else if(v8 == 0) {
                            v10[v7] = key;
                            this.valueTable[v7] = arg15;
                            v0 = this.size;
                            this.size = v0 + 1;
                            if(v0 >= this.threshold) {
                                this.resize(this.capacity << 1);
                            }
                        }
                        else {
                            this.push(key, arg15, v3, v4, v5, v6, v7, v8);
                        }
                    }
                }
            }
        }

    label_11:
        return v12;
    }

    public void putAll(IntMap arg5) {
        Iterator v1 = arg5.entries().iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            this.put(((Entry)v0).key, ((Entry)v0).value);
        }
    }

    private void putResize(int key, Object arg11) {
        int v0;
        if(key == 0) {
            this.zeroValue = arg11;
            this.hasZeroValue = true;
        }
        else {
            int v3 = key & this.mask;
            int v4 = this.keyTable[v3];
            if(v4 == 0) {
                this.keyTable[v3] = key;
                this.valueTable[v3] = arg11;
                v0 = this.size;
                this.size = v0 + 1;
                if(v0 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                int v5 = this.hash2(key);
                int v6 = this.keyTable[v5];
                if(v6 == 0) {
                    this.keyTable[v5] = key;
                    this.valueTable[v5] = arg11;
                    v0 = this.size;
                    this.size = v0 + 1;
                    if(v0 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    int v7 = this.hash3(key);
                    int v8 = this.keyTable[v7];
                    if(v8 == 0) {
                        this.keyTable[v7] = key;
                        this.valueTable[v7] = arg11;
                        v0 = this.size;
                        this.size = v0 + 1;
                        if(v0 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }
                    }
                    else {
                        this.push(key, arg11, v3, v4, v5, v6, v7, v8);
                    }
                }
            }
        }
    }

    private void putStash(int key, Object arg5) {
        if(this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.put(key, arg5);
        }
        else {
            int v0 = this.capacity + this.stashSize;
            this.keyTable[v0] = key;
            this.valueTable[v0] = arg5;
            ++this.stashSize;
            ++this.size;
        }
    }

    public Object remove(int key) {
        Object v1;
        Object v2 = null;
        if(key != 0) {
            int v0 = key & this.mask;
            if(this.keyTable[v0] == key) {
                this.keyTable[v0] = 0;
                v1 = this.valueTable[v0];
                this.valueTable[v0] = v2;
                --this.size;
            }
            else {
                v0 = this.hash2(key);
                if(this.keyTable[v0] == key) {
                    this.keyTable[v0] = 0;
                    v1 = this.valueTable[v0];
                    this.valueTable[v0] = v2;
                    --this.size;
                }
                else {
                    v0 = this.hash3(key);
                    if(this.keyTable[v0] == key) {
                        this.keyTable[v0] = 0;
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

    Object removeStash(int key) {
        Object v3;
        int[] v1 = this.keyTable;
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
        Object v3 = null;
        --this.stashSize;
        int v0 = this.capacity + this.stashSize;
        if(index < v0) {
            this.keyTable[index] = this.keyTable[v0];
            this.valueTable[index] = this.valueTable[v0];
            this.valueTable[v0] = v3;
        }
        else {
            this.valueTable[index] = v3;
        }
    }

    private void resize(int newSize) {
        int v6;
        int v2 = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = ((int)((((float)newSize)) * this.loadFactor));
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)newSize))))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (((int)Math.sqrt(((double)newSize)))) / 8);
        int[] v3 = this.keyTable;
        Object[] v5 = this.valueTable;
        this.keyTable = new int[this.stashCapacity + newSize];
        this.valueTable = new Object[this.stashCapacity + newSize];
        int v4 = this.size;
        if(this.hasZeroValue) {
            v6 = 1;
        }
        else {
            v6 = 0;
        }

        this.size = v6;
        this.stashSize = 0;
        if(v4 > 0) {
            int v0;
            for(v0 = 0; v0 < v2; ++v0) {
                int v1 = v3[v0];
                if(v1 != 0) {
                    this.putResize(v1, v5[v0]);
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
        String v6;
        char v7 = '=';
        if(this.size == 0) {
            v6 = "[]";
        }
        else {
            StringBuilder v0 = new StringBuilder(32);
            v0.append('[');
            int[] v4 = this.keyTable;
            Object[] v5 = this.valueTable;
            int v1 = v4.length;
            if(this.hasZeroValue) {
                v0.append("0=");
                v0.append(this.zeroValue);
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
                        goto label_49;
                    }

                    goto label_19;
                }

                v0.append(v3);
                v0.append(v7);
                v0.append(v5[v1]);
                v2 = v1;
                goto label_19;
            label_49:
                v2 = v1;
            }

            while(true) {
            label_19:
                v1 = v2 - 1;
                if(v2 <= 0) {
                    break;
                }

                v3 = v4[v1];
                if(v3 != 0) {
                    goto label_37;
                }

                v2 = v1;
                continue;
            label_37:
                v0.append(", ");
                v0.append(v3);
                v0.append(v7);
                v0.append(v5[v1]);
                v2 = v1;
            }

            v0.append(']');
            v6 = v0.toString();
        }

        return v6;
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

