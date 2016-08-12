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

public class ObjectMap implements Iterable {
    public class Entries extends MapIterator {
        Entry entry;

        public Entries(ObjectMap arg2) {
            super(arg2);
            this.entry = new Entry();
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public Iterator iterator() {
            return this.iterator();
        }

        public Entries iterator() {
            return this;
        }

        public Entry next() {
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            this.entry.key = this.map.keyTable[this.nextIndex];
            this.entry.value = this.map.valueTable[this.nextIndex];
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
        public Keys(ObjectMap arg1) {
            super(arg1);
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public Iterator iterator() {
            return this.iterator();
        }

        public Keys iterator() {
            return this;
        }

        public Object next() {
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.map.keyTable[this.nextIndex];
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
            return this.toArray(new Array(true, this.map.size));
        }

        public Array toArray(Array arg2) {
            while(this.hasNext) {
                arg2.add(this.next());
            }

            return arg2;
        }
    }

    abstract class MapIterator implements Iterable, Iterator {
        int currentIndex;
        public boolean hasNext;
        final ObjectMap map;
        int nextIndex;
        boolean valid;

        public MapIterator(ObjectMap arg2) {
            super();
            this.valid = true;
            this.map = arg2;
            this.reset();
        }

        void findNextIndex() {
            this.hasNext = false;
            Object[] v0 = this.map.keyTable;
            int v1 = this.map.capacity + this.map.stashSize;
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

        public void remove() {
            Object v2 = null;
            if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            if(this.currentIndex >= this.map.capacity) {
                this.map.removeStashIndex(this.currentIndex);
                this.nextIndex = this.currentIndex - 1;
                this.findNextIndex();
            }
            else {
                this.map.keyTable[this.currentIndex] = v2;
                this.map.valueTable[this.currentIndex] = v2;
            }

            this.currentIndex = -1;
            --this.map.size;
        }

        public void reset() {
            this.currentIndex = -1;
            this.nextIndex = -1;
            this.findNextIndex();
        }
    }

    public class Values extends MapIterator {
        public Values(ObjectMap arg1) {
            super(arg1);
        }

        public boolean hasNext() {
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            return this.hasNext;
        }

        public Iterator iterator() {
            return this.iterator();
        }

        public Values iterator() {
            return this;
        }

        public Object next() {
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.map.valueTable[this.nextIndex];
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
            return this.toArray(new Array(true, this.map.size));
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
    private Entries entries1;
    private Entries entries2;
    private int hashShift;
    Object[] keyTable;
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

    public ObjectMap() {
        this(32, 0.8f);
    }

    public ObjectMap(int initialCapacity, float loadFactor) {
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
        this.valueTable = new Object[this.keyTable.length];
    }

    public ObjectMap(int initialCapacity) {
        this(initialCapacity, 0.8f);
    }

    public ObjectMap(ObjectMap arg5) {
        this(arg5.capacity, arg5.loadFactor);
        this.stashSize = arg5.stashSize;
        System.arraycopy(arg5.keyTable, 0, this.keyTable, 0, arg5.keyTable.length);
        System.arraycopy(arg5.valueTable, 0, this.valueTable, 0, arg5.valueTable.length);
        this.size = arg5.size;
    }

    public void clear() {
        Object v7 = null;
        if(this.size != 0) {
            Object[] v2 = this.keyTable;
            Object[] v3 = this.valueTable;
            int v1;
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                int v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                v2[v0] = v7;
                v3[v0] = v7;
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

    public boolean containsKey(Object arg4) {
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

    public boolean containsValue(Object value, boolean identity) {
        int v0;
        int v1;
        boolean v4 = true;
        Object[] v3 = this.valueTable;
        if(value == null) {
            Object[] v2 = this.keyTable;
            v1 = this.capacity + this.stashSize;
            while(true) {
                v0 = v1 - 1;
                if(v1 > 0) {
                    if(v2[v0] != null && v3[v0] == null) {
                        break;
                    }

                    v1 = v0;
                    continue;
                }
                else {
                    goto label_37;
                }
            }
        }
        else {
            if(!identity) {
                goto label_26;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_37;
                }

                if(v3[v0] == value) {
                    goto label_14;
                }
            }

        label_26:
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(value.equals(v3[v0])) {
                    goto label_14;
                }
            }

        label_37:
            v4 = false;
        }

    label_14:
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

    public Object findKey(Object value, boolean identity) {
        Object v4;
        int v0;
        int v1;
        Object[] v3 = this.valueTable;
        if(value == null) {
            Object[] v2 = this.keyTable;
            v1 = this.capacity + this.stashSize;
            while(true) {
                v0 = v1 - 1;
                if(v1 > 0) {
                    if(v2[v0] != null && v3[v0] == null) {
                        v4 = v2[v0];
                        break;
                    }

                    v1 = v0;
                    continue;
                }
                else {
                    goto label_39;
                }
            }
        }
        else {
            if(!identity) {
                goto label_27;
            }

            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    goto label_39;
                }

                if(v3[v0] != value) {
                    goto label_43;
                }

                v4 = this.keyTable[v0];
                goto label_14;
            label_43:
            }

        label_27:
            for(v1 = this.capacity + this.stashSize; true; v1 = v0) {
                v0 = v1 - 1;
                if(v1 <= 0) {
                    break;
                }

                if(!value.equals(v3[v0])) {
                    goto label_41;
                }

                v4 = this.keyTable[v0];
                goto label_14;
            label_41:
            }

        label_39:
            v4 = null;
        }

    label_14:
        return v4;
    }

    public Object get(Object arg4) {
        Object v2;
        int v0 = arg4.hashCode();
        int v1 = v0 & this.mask;
        if(!arg4.equals(this.keyTable[v1])) {
            v1 = this.hash2(v0);
            if(!arg4.equals(this.keyTable[v1])) {
                v1 = this.hash3(v0);
                if(!arg4.equals(this.keyTable[v1])) {
                    v2 = this.getStash(arg4);
                }
                else {
                    goto label_19;
                }
            }
            else {
                goto label_19;
            }
        }
        else {
        label_19:
            v2 = this.valueTable[v1];
        }

        return v2;
    }

    public Object get(Object arg4, Object arg5) {
        Object v2;
        int v0 = arg4.hashCode();
        int v1 = v0 & this.mask;
        if(!arg4.equals(this.keyTable[v1])) {
            v1 = this.hash2(v0);
            if(!arg4.equals(this.keyTable[v1])) {
                v1 = this.hash3(v0);
                if(!arg4.equals(this.keyTable[v1])) {
                    v2 = this.getStash(arg4, arg5);
                }
                else {
                    goto label_19;
                }
            }
            else {
                goto label_19;
            }
        }
        else {
        label_19:
            v2 = this.valueTable[v1];
        }

        return v2;
    }

    private Object getStash(Object arg5) {
        Object v3;
        Object[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(arg5.equals(v1[v0])) {
                v3 = this.valueTable[v0];
            }
            else {
                ++v0;
                continue;
            }

            goto label_10;
        }

        v3 = null;
    label_10:
        return v3;
    }

    private Object getStash(Object arg5, Object arg6) {
        Object[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(v0 < v2) {
            if(arg5.equals(v1[v0])) {
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

    public Entries iterator() {
        return this.entries();
    }

    public Iterator iterator() {
        return this.iterator();
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

    private void push(Object arg11, Object arg12, int index1, Object arg14, int index2, Object arg16, int index3, Object arg18) {
        int v8;
        Object v1;
        Object v0;
        Object[] v4 = this.keyTable;
        Object[] v7 = this.valueTable;
        int v5 = this.mask;
        int v3 = 0;
        int v6 = this.pushIterations;
    label_5:
        switch(MathUtils.random(2)) {
            case 0: {
                v0 = arg14;
                v1 = v7[index1];
                v4[index1] = arg11;
                v7[index1] = arg12;
                break;
            }
            case 1: {
                v0 = arg16;
                v1 = v7[index2];
                v4[index2] = arg11;
                v7[index2] = arg12;
                break;
            }
            default: {
                v0 = arg18;
                v1 = v7[index3];
                v4[index3] = arg11;
                v7[index3] = arg12;
                break;
            }
        }

        int v2 = v0.hashCode();
        index1 = v2 & v5;
        arg14 = v4[index1];
        if(arg14 == null) {
            v4[index1] = v0;
            v7[index1] = v1;
            v8 = this.size;
            this.size = v8 + 1;
            if(v8 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            index2 = this.hash2(v2);
            arg16 = v4[index2];
            if(arg16 == null) {
                v4[index2] = v0;
                v7[index2] = v1;
                v8 = this.size;
                this.size = v8 + 1;
                if(v8 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                index3 = this.hash3(v2);
                arg18 = v4[index3];
                if(arg18 == null) {
                    v4[index3] = v0;
                    v7[index3] = v1;
                    v8 = this.size;
                    this.size = v8 + 1;
                    if(v8 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    ++v3;
                    if(v3 == v6) {
                        this.putStash(v0, v1);
                    }
                    else {
                        goto label_69;
                    }
                }
            }
        }

        return;
    label_69:
        arg11 = v0;
        arg12 = v1;
        goto label_5;
    }

    public Object put(Object arg3, Object arg4) {
        if(arg3 == null) {
            throw new IllegalArgumentException("key cannot be null.");
        }

        return this.put_internal(arg3, arg4);
    }

    public void putAll(ObjectMap arg5) {
        this.ensureCapacity(arg5.size);
        Iterator v1 = arg5.iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            this.put(((Entry)v0).key, ((Entry)v0).value);
        }
    }

    private void putResize(Object arg11, Object arg12) {
        int v0;
        int v9 = arg11.hashCode();
        int v3 = v9 & this.mask;
        Object v4 = this.keyTable[v3];
        if(v4 == null) {
            this.keyTable[v3] = arg11;
            this.valueTable[v3] = arg12;
            v0 = this.size;
            this.size = v0 + 1;
            if(v0 >= this.threshold) {
                this.resize(this.capacity << 1);
            }
        }
        else {
            int v5 = this.hash2(v9);
            Object v6 = this.keyTable[v5];
            if(v6 == null) {
                this.keyTable[v5] = arg11;
                this.valueTable[v5] = arg12;
                v0 = this.size;
                this.size = v0 + 1;
                if(v0 >= this.threshold) {
                    this.resize(this.capacity << 1);
                }
            }
            else {
                int v7 = this.hash3(v9);
                Object v8 = this.keyTable[v7];
                if(v8 == null) {
                    this.keyTable[v7] = arg11;
                    this.valueTable[v7] = arg12;
                    v0 = this.size;
                    this.size = v0 + 1;
                    if(v0 >= this.threshold) {
                        this.resize(this.capacity << 1);
                    }
                }
                else {
                    this.push(arg11, arg12, v3, v4, v5, v6, v7, v8);
                }
            }
        }
    }

    private void putStash(Object arg4, Object arg5) {
        if(this.stashSize == this.stashCapacity) {
            this.resize(this.capacity << 1);
            this.put_internal(arg4, arg5);
        }
        else {
            int v0 = this.capacity + this.stashSize;
            this.keyTable[v0] = arg4;
            this.valueTable[v0] = arg5;
            ++this.stashSize;
            ++this.size;
        }
    }

    private Object put_internal(Object arg15, Object arg16) {
        int v0;
        Object v13;
        Object[] v11 = this.keyTable;
        int v9 = arg15.hashCode();
        int v3 = v9 & this.mask;
        Object v4 = v11[v3];
        if(arg15.equals(v4)) {
            v13 = this.valueTable[v3];
            this.valueTable[v3] = arg16;
        }
        else {
            int v5 = this.hash2(v9);
            Object v6 = v11[v5];
            if(arg15.equals(v6)) {
                v13 = this.valueTable[v5];
                this.valueTable[v5] = arg16;
            }
            else {
                int v7 = this.hash3(v9);
                Object v8 = v11[v7];
                if(arg15.equals(v8)) {
                    v13 = this.valueTable[v7];
                    this.valueTable[v7] = arg16;
                }
                else {
                    int v10 = this.capacity;
                    int v12 = v10 + this.stashSize;
                    while(true) {
                        if(v10 >= v12) {
                            break;
                        }
                        else if(arg15.equals(v11[v10])) {
                            v13 = this.valueTable[v10];
                            this.valueTable[v10] = arg16;
                        }
                        else {
                            ++v10;
                            continue;
                        }

                        goto label_11;
                    }

                    if(v4 == null) {
                        v11[v3] = arg15;
                        this.valueTable[v3] = arg16;
                        v0 = this.size;
                        this.size = v0 + 1;
                        if(v0 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v13 = null;
                    }
                    else {
                        if(v6 != null) {
                            goto label_72;
                        }

                        v11[v5] = arg15;
                        this.valueTable[v5] = arg16;
                        v0 = this.size;
                        this.size = v0 + 1;
                        if(v0 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v13 = null;
                        goto label_11;
                    label_72:
                        if(v8 != null) {
                            goto label_86;
                        }

                        v11[v7] = arg15;
                        this.valueTable[v7] = arg16;
                        v0 = this.size;
                        this.size = v0 + 1;
                        if(v0 >= this.threshold) {
                            this.resize(this.capacity << 1);
                        }

                        v13 = null;
                        goto label_11;
                    label_86:
                        this.push(arg15, arg16, v3, v4, v5, v6, v7, v8);
                        v13 = null;
                    }
                }
            }
        }

    label_11:
        return v13;
    }

    public Object remove(Object arg6) {
        Object v2;
        Object v4 = null;
        int v0 = arg6.hashCode();
        int v1 = v0 & this.mask;
        if(arg6.equals(this.keyTable[v1])) {
            this.keyTable[v1] = v4;
            v2 = this.valueTable[v1];
            this.valueTable[v1] = v4;
            --this.size;
        }
        else {
            v1 = this.hash2(v0);
            if(arg6.equals(this.keyTable[v1])) {
                this.keyTable[v1] = v4;
                v2 = this.valueTable[v1];
                this.valueTable[v1] = v4;
                --this.size;
            }
            else {
                v1 = this.hash3(v0);
                if(arg6.equals(this.keyTable[v1])) {
                    this.keyTable[v1] = v4;
                    v2 = this.valueTable[v1];
                    this.valueTable[v1] = v4;
                    --this.size;
                }
                else {
                    v2 = this.removeStash(arg6);
                }
            }
        }

        return v2;
    }

    Object removeStash(Object arg6) {
        Object v3;
        Object[] v1 = this.keyTable;
        int v0 = this.capacity;
        int v2 = v0 + this.stashSize;
        while(true) {
            if(v0 >= v2) {
                break;
            }
            else if(arg6.equals(v1[v0])) {
                v3 = this.valueTable[v0];
                this.removeStashIndex(v0);
                --this.size;
            }
            else {
                ++v0;
                continue;
            }

            goto label_14;
        }

        v3 = null;
    label_14:
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
        int v2 = this.capacity + this.stashSize;
        this.capacity = newSize;
        this.threshold = ((int)((((float)newSize)) * this.loadFactor));
        this.mask = newSize - 1;
        this.hashShift = 31 - Integer.numberOfTrailingZeros(newSize);
        this.stashCapacity = Math.max(3, (((int)Math.ceil(Math.log(((double)newSize))))) * 2);
        this.pushIterations = Math.max(Math.min(newSize, 8), (((int)Math.sqrt(((double)newSize)))) / 8);
        Object[] v3 = this.keyTable;
        Object[] v5 = this.valueTable;
        this.keyTable = new Object[this.stashCapacity + newSize];
        this.valueTable = new Object[this.stashCapacity + newSize];
        int v4 = this.size;
        this.size = 0;
        this.stashSize = 0;
        if(v4 > 0) {
            int v0;
            for(v0 = 0; v0 < v2; ++v0) {
                Object v1 = v3[v0];
                if(v1 != null) {
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

    private String toString(String separator, boolean braces) {
        String v6;
        Object v3;
        int v1;
        char v7 = '=';
        if(this.size != 0) {
            StringBuilder v0 = new StringBuilder(32);
            if(braces) {
                v0.append('{');
            }

            Object[] v4 = this.keyTable;
            Object[] v5 = this.valueTable;
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
                    goto label_46;
                }

                goto label_28;
            }

            v0.append(v3);
            v0.append(v7);
            v0.append(v5[v1]);
            v2 = v1;
            goto label_28;
        label_46:
            for(v2 = v1; true; v2 = v1) {
            label_28:
                v1 = v2 - 1;
                if(v2 <= 0) {
                    break;
                }

                v3 = v4[v1];
                if(v3 != null) {
                    goto label_34;
                }

                v2 = v1;
                continue;
            label_34:
                v0.append(separator);
                v0.append(v3);
                v0.append(v7);
                v0.append(v5[v1]);
            }

            if(braces) {
                v0.append('}');
            }

            v6 = v0.toString();
        }
        else if(braces) {
            v6 = "{}";
        }
        else {
            v6 = "";
        }

        return v6;
    }

    public String toString() {
        return this.toString(", ", true);
    }

    public String toString(String separator) {
        return this.toString(separator, false);
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

