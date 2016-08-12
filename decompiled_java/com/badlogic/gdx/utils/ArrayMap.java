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
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayMap implements Iterable {
    public class Entries implements Iterable, Iterator {
        Entry entry;
        int index;
        private final ArrayMap map;
        boolean valid;

        public Entries(ArrayMap arg2) {
            super();
            this.entry = new Entry();
            this.valid = true;
            this.map = arg2;
        }

        public boolean hasNext() {
            boolean v0;
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.index < this.map.size) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public Iterator iterator() {
            return this;
        }

        public Entry next() {
            if(this.index >= this.map.size) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            this.entry.key = this.map.keys[this.index];
            Entry v0 = this.entry;
            Object[] v1 = this.map.values;
            int v2 = this.index;
            this.index = v2 + 1;
            v0.value = v1[v2];
            return this.entry;
        }

        public Object next() {
            return this.next();
        }

        public void remove() {
            --this.index;
            this.map.removeIndex(this.index);
        }

        public void reset() {
            this.index = 0;
        }
    }

    public class Keys implements Iterable, Iterator {
        int index;
        private final ArrayMap map;
        boolean valid;

        public Keys(ArrayMap arg2) {
            super();
            this.valid = true;
            this.map = arg2;
        }

        public boolean hasNext() {
            boolean v0;
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.index < this.map.size) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public Iterator iterator() {
            return this;
        }

        public Object next() {
            if(this.index >= this.map.size) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object[] v0 = this.map.keys;
            int v1 = this.index;
            this.index = v1 + 1;
            return v0[v1];
        }

        public void remove() {
            --this.index;
            this.map.removeIndex(this.index);
        }

        public void reset() {
            this.index = 0;
        }

        public Array toArray() {
            return new Array(true, this.map.keys, this.index, this.map.size - this.index);
        }

        public Array toArray(Array array) {
            array.addAll(this.map.keys, this.index, this.map.size - this.index);
            return array;
        }
    }

    public class Values implements Iterable, Iterator {
        int index;
        private final ArrayMap map;
        boolean valid;

        public Values(ArrayMap arg2) {
            super();
            this.valid = true;
            this.map = arg2;
        }

        public boolean hasNext() {
            boolean v0;
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.index < this.map.size) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public Iterator iterator() {
            return this;
        }

        public Object next() {
            if(this.index >= this.map.size) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object[] v0 = this.map.values;
            int v1 = this.index;
            this.index = v1 + 1;
            return v0[v1];
        }

        public void remove() {
            --this.index;
            this.map.removeIndex(this.index);
        }

        public void reset() {
            this.index = 0;
        }

        public Array toArray() {
            return new Array(true, this.map.values, this.index, this.map.size - this.index);
        }

        public Array toArray(Array array) {
            array.addAll(this.map.values, this.index, this.map.size - this.index);
            return array;
        }
    }

    private Entries entries1;
    private Entries entries2;
    private Keys keysIter1;
    private Keys keysIter2;
    public boolean ordered;
    private Values valuesIter1;
    private Values valuesIter2;

    public ArrayMap() {
        this(true, 16);
    }

    public ArrayMap(boolean ordered, int capacity) {
        super();
        this.ordered = ordered;
        this.keys = new Object[capacity];
        this.values = new Object[capacity];
    }

    public ArrayMap(int capacity) {
        this(true, capacity);
    }

    public ArrayMap(ArrayMap array) {
        this(array.ordered, array.size, array.keys.getClass().getComponentType(), array.values.getClass().getComponentType());
        this.size = array.size;
        System.arraycopy(array.keys, 0, this.keys, 0, this.size);
        System.arraycopy(array.values, 0, this.values, 0, this.size);
    }

    public ArrayMap(boolean ordered, int capacity, Class keyArrayType, Class valueArrayType) {
        super();
        this.ordered = ordered;
        this.keys = ArrayReflection.newInstance(keyArrayType, capacity);
        this.values = ArrayReflection.newInstance(valueArrayType, capacity);
    }

    public ArrayMap(Class keyArrayType, Class valueArrayType) {
        this(false, 16, keyArrayType, valueArrayType);
    }

    public void clear() {
        Object v4 = null;
        Object[] v1 = this.keys;
        Object[] v3 = this.values;
        int v0 = 0;
        int v2 = this.size;
        while(v0 < v2) {
            v1[v0] = v4;
            v3[v0] = v4;
            ++v0;
        }

        this.size = 0;
    }

    public void clear(int maximumCapacity) {
        if(this.keys.length <= maximumCapacity) {
            this.clear();
        }
        else {
            this.size = 0;
            this.resize(maximumCapacity);
        }
    }

    public boolean containsKey(Object arg6) {
        int v1;
        boolean v3 = true;
        Object[] v2 = this.keys;
        int v0 = this.size - 1;
        if(arg6 == null) {
            do {
                v1 = v0;
                if(v1 >= 0) {
                    v0 = v1 - 1;
                    if(v2[v1] != arg6) {
                        continue;
                    }
                }
                else {
                    goto label_18;
                }

                break;
            }
            while(true);
        }
        else {
            do {
                v1 = v0;
                if(v1 >= 0) {
                    v0 = v1 - 1;
                    if(!arg6.equals(v2[v1])) {
                        continue;
                    }

                    break;
                }
                else {
                    goto label_18;
                }
            }
            while(true);

            goto label_10;
        label_18:
            v3 = false;
        }

    label_10:
        return v3;
    }

    public boolean containsValue(Object arg6, boolean identity) {
        int v1;
        boolean v3 = true;
        Object[] v2 = this.values;
        int v0 = this.size - 1;
        if(!identity) {
            if(arg6 != null) {
                goto label_12;
            }

            goto label_6;
            do {
            label_12:
                v1 = v0;
                if(v1 < 0) {
                    goto label_19;
                }

                v0 = v1 - 1;
            }
            while(!arg6.equals(v2[v1]));
        }
        else {
            do {
            label_6:
                v1 = v0;
                if(v1 < 0) {
                    break;
                }

                v0 = v1 - 1;
                if(v2[v1] != arg6) {
                    continue;
                }

                goto label_11;
            }
            while(true);

        label_19:
            v3 = false;
        }

    label_11:
        return v3;
    }

    public void ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 >= this.keys.length) {
            this.resize(Math.max(8, v0));
        }
    }

    public Entries entries() {
        Entries v0;
        if(this.entries1 == null) {
            this.entries1 = new Entries(this);
            this.entries2 = new Entries(this);
        }

        if(!this.entries1.valid) {
            this.entries1.index = 0;
            this.entries1.valid = true;
            this.entries2.valid = false;
            v0 = this.entries1;
        }
        else {
            this.entries2.index = 0;
            this.entries2.valid = true;
            this.entries1.valid = false;
            v0 = this.entries2;
        }

        return v0;
    }

    public Object firstKey() {
        if(this.size == 0) {
            throw new IllegalStateException("Map is empty.");
        }

        return this.keys[0];
    }

    public Object firstValue() {
        if(this.size == 0) {
            throw new IllegalStateException("Map is empty.");
        }

        return this.values[0];
    }

    public Object get(Object arg4) {
        Object v2;
        Object[] v1 = this.keys;
        int v0 = this.size - 1;
        if(arg4 == null) {
            while(true) {
                if(v0 < 0) {
                    goto label_20;
                }
                else if(v1[v0] == arg4) {
                    v2 = this.values[v0];
                }
                else {
                    --v0;
                    continue;
                }

                break;
            }
        }
        else {
            while(true) {
                if(v0 < 0) {
                    break;
                }
                else if(arg4.equals(v1[v0])) {
                    v2 = this.values[v0];
                }
                else {
                    --v0;
                    continue;
                }

                goto label_9;
            }

        label_20:
            v2 = null;
        }

    label_9:
        return v2;
    }

    public Object getKey(Object arg4, boolean identity) {
        Object v2;
        Object[] v1 = this.values;
        int v0 = this.size - 1;
        if(!identity) {
            if(arg4 != null) {
                goto label_14;
            }

            goto label_5;
            while(true) {
            label_14:
                if(v0 < 0) {
                    goto label_21;
                }

                if(!arg4.equals(v1[v0])) {
                    goto label_13;
                }

                v2 = this.keys[v0];
                break;
            label_13:
                --v0;
            }
        }
        else {
            while(true) {
            label_5:
                if(v0 < 0) {
                    break;
                }

                if(v1[v0] != arg4) {
                    goto label_11;
                }

                v2 = this.keys[v0];
                goto label_10;
            label_11:
                --v0;
            }

        label_21:
            v2 = null;
        }

    label_10:
        return v2;
    }

    public Object getKeyAt(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        return this.keys[index];
    }

    public Object getValueAt(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        return this.values[index];
    }

    public int indexOfKey(Object arg5) {
        int v3;
        int v2;
        int v0;
        Object[] v1 = this.keys;
        if(arg5 == null) {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    goto label_21;
                }
                else if(v1[v0] == arg5) {
                    v3 = v0;
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }
        else {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    break;
                }
                else if(arg5.equals(v1[v0])) {
                    v3 = v0;
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_8;
            }

        label_21:
            v3 = -1;
        }

    label_8:
        return v3;
    }

    public int indexOfValue(Object arg5, boolean identity) {
        int v3;
        int v1;
        int v0;
        Object[] v2 = this.values;
        if((identity) || arg5 == null) {
            v0 = 0;
            v1 = this.size;
            while(true) {
                if(v0 >= v1) {
                    break;
                }
                else if(v2[v0] == arg5) {
                    v3 = v0;
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_9;
            }

        label_22:
            v3 = -1;
        }
        else {
            v0 = 0;
            v1 = this.size;
            while(true) {
                if(v0 >= v1) {
                    goto label_22;
                }
                else if(arg5.equals(v2[v0])) {
                    v3 = v0;
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }

    label_9:
        return v3;
    }

    public void insert(int index, Object arg6, Object arg7) {
        if(index > this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        if(this.size == this.keys.length) {
            this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        if(this.ordered) {
            System.arraycopy(this.keys, index, this.keys, index + 1, this.size - index);
            System.arraycopy(this.values, index, this.values, index + 1, this.size - index);
        }
        else {
            this.keys[this.size] = this.keys[index];
            this.values[this.size] = this.values[index];
        }

        ++this.size;
        this.keys[index] = arg6;
        this.values[index] = arg7;
    }

    public Iterator iterator() {
        return this.entries();
    }

    public Keys keys() {
        Keys v0;
        if(this.keysIter1 == null) {
            this.keysIter1 = new Keys(this);
            this.keysIter2 = new Keys(this);
        }

        if(!this.keysIter1.valid) {
            this.keysIter1.index = 0;
            this.keysIter1.valid = true;
            this.keysIter2.valid = false;
            v0 = this.keysIter1;
        }
        else {
            this.keysIter2.index = 0;
            this.keysIter2.valid = true;
            this.keysIter1.valid = false;
            v0 = this.keysIter2;
        }

        return v0;
    }

    public Object peekKey() {
        return this.keys[this.size - 1];
    }

    public Object peekValue() {
        return this.values[this.size - 1];
    }

    public int put(Object arg5, Object arg6) {
        int v0 = this.indexOfKey(arg5);
        if(v0 == -1) {
            if(this.size == this.keys.length) {
                this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
            }

            v0 = this.size;
            this.size = v0 + 1;
        }

        this.keys[v0] = arg5;
        this.values[v0] = arg6;
        return v0;
    }

    public int put(Object arg6, Object arg7, int index) {
        int v0 = this.indexOfKey(arg6);
        if(v0 != -1) {
            this.removeIndex(v0);
        }
        else if(this.size == this.keys.length) {
            this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        System.arraycopy(this.keys, index, this.keys, index + 1, this.size - index);
        System.arraycopy(this.values, index, this.values, index + 1, this.size - index);
        this.keys[index] = arg6;
        this.values[index] = arg7;
        ++this.size;
        return index;
    }

    public void putAll(ArrayMap map) {
        this.putAll(map, 0, map.size);
    }

    public void putAll(ArrayMap map, int offset, int length) {
        if(offset + length > map.size) {
            throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + map.size);
        }

        int v0 = this.size + length - offset;
        if(v0 >= this.keys.length) {
            this.resize(Math.max(8, ((int)((((float)v0)) * 1.75f))));
        }

        System.arraycopy(map.keys, offset, this.keys, this.size, length);
        System.arraycopy(map.values, offset, this.values, this.size, length);
        this.size += length;
    }

    public void removeIndex(int index) {
        Object v5 = null;
        if(index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        Object[] v0 = this.keys;
        --this.size;
        if(this.ordered) {
            System.arraycopy(v0, index + 1, v0, index, this.size - index);
            System.arraycopy(this.values, index + 1, this.values, index, this.size - index);
        }
        else {
            v0[index] = v0[this.size];
            this.values[index] = this.values[this.size];
        }

        v0[this.size] = v5;
        this.values[this.size] = v5;
    }

    public Object removeKey(Object arg6) {
        Object v3;
        int v2;
        int v0;
        Object[] v1 = this.keys;
        if(arg6 == null) {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    goto label_25;
                }
                else if(v1[v0] == arg6) {
                    v3 = this.values[v0];
                    this.removeIndex(v0);
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }
        else {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    break;
                }
                else if(arg6.equals(v1[v0])) {
                    v3 = this.values[v0];
                    this.removeIndex(v0);
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_10;
            }

        label_25:
            v3 = null;
        }

    label_10:
        return v3;
    }

    public boolean removeValue(Object arg6, boolean identity) {
        int v1;
        int v0;
        boolean v3 = true;
        Object[] v2 = this.values;
        if((identity) || arg6 == null) {
            v0 = 0;
            v1 = this.size;
            while(true) {
                if(v0 >= v1) {
                    break;
                }
                else if(v2[v0] == arg6) {
                    this.removeIndex(v0);
                }
                else {
                    ++v0;
                    continue;
                }

                goto label_10;
            }

        label_23:
            v3 = false;
        }
        else {
            v0 = 0;
            v1 = this.size;
            while(true) {
                if(v0 >= v1) {
                    goto label_23;
                }
                else if(arg6.equals(v2[v0])) {
                    this.removeIndex(v0);
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }

    label_10:
        return v3;
    }

    protected void resize(int newSize) {
        Object v0 = ArrayReflection.newInstance(this.keys.getClass().getComponentType(), newSize);
        System.arraycopy(this.keys, 0, v0, 0, Math.min(this.size, v0.length));
        this.keys = ((Object[])v0);
        Object v1 = ArrayReflection.newInstance(this.values.getClass().getComponentType(), newSize);
        System.arraycopy(this.values, 0, v1, 0, Math.min(this.size, v1.length));
        this.values = ((Object[])v1);
    }

    public void reverse() {
        int v0 = 0;
        int v2 = this.size - 1;
        int v3 = this.size / 2;
        while(v0 < v3) {
            int v1 = v2 - v0;
            Object v4 = this.keys[v0];
            this.keys[v0] = this.keys[v1];
            this.keys[v1] = v4;
            Object v5 = this.values[v0];
            this.values[v0] = this.values[v1];
            this.values[v1] = v5;
            ++v0;
        }
    }

    public void setKey(int index, Object arg4) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        this.keys[index] = arg4;
    }

    public void setValue(int index, Object arg4) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        this.values[index] = arg4;
    }

    public void shrink() {
        if(this.keys.length != this.size) {
            this.resize(this.size);
        }
    }

    public void shuffle() {
        int v0;
        for(v0 = this.size - 1; v0 >= 0; --v0) {
            int v1 = MathUtils.random(v0);
            Object v2 = this.keys[v0];
            this.keys[v0] = this.keys[v1];
            this.keys[v1] = v2;
            Object v3 = this.values[v0];
            this.values[v0] = this.values[v1];
            this.values[v1] = v3;
        }
    }

    public String toString() {
        String v4;
        char v6 = '=';
        if(this.size == 0) {
            v4 = "{}";
        }
        else {
            Object[] v2 = this.keys;
            Object[] v3 = this.values;
            StringBuilder v0 = new StringBuilder(32);
            v0.append('{');
            v0.append(v2[0]);
            v0.append(v6);
            v0.append(v3[0]);
            int v1;
            for(v1 = 1; v1 < this.size; ++v1) {
                v0.append(", ");
                v0.append(v2[v1]);
                v0.append(v6);
                v0.append(v3[v1]);
            }

            v0.append('}');
            v4 = v0.toString();
        }

        return v4;
    }

    public void truncate(int newSize) {
        Object v2 = null;
        if(this.size > newSize) {
            int v0;
            for(v0 = newSize; v0 < this.size; ++v0) {
                this.keys[v0] = v2;
                this.values[v0] = v2;
            }

            this.size = newSize;
        }
    }

    public Values values() {
        Values v0;
        if(this.valuesIter1 == null) {
            this.valuesIter1 = new Values(this);
            this.valuesIter2 = new Values(this);
        }

        if(!this.valuesIter1.valid) {
            this.valuesIter1.index = 0;
            this.valuesIter1.valid = true;
            this.valuesIter2.valid = false;
            v0 = this.valuesIter1;
        }
        else {
            this.valuesIter2.index = 0;
            this.valuesIter2.valid = true;
            this.valuesIter1.valid = false;
            v0 = this.valuesIter2;
        }

        return v0;
    }
}

