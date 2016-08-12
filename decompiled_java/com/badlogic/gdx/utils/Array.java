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
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Array implements Iterable {
    public class ArrayIterable implements Iterable {
        private final boolean allowRemove;
        private final Array array;
        private ArrayIterator iterator1;
        private ArrayIterator iterator2;

        public ArrayIterable(Array arg2) {
            super(arg2, true);
        }

        public ArrayIterable(Array arg1, boolean allowRemove) {
            super();
            this.array = arg1;
            this.allowRemove = allowRemove;
        }

        public Iterator iterator() {
            ArrayIterator v0;
            if(this.iterator1 == null) {
                this.iterator1 = new ArrayIterator(this.array, this.allowRemove);
                this.iterator2 = new ArrayIterator(this.array, this.allowRemove);
            }

            if(!this.iterator1.valid) {
                this.iterator1.index = 0;
                this.iterator1.valid = true;
                this.iterator2.valid = false;
                v0 = this.iterator1;
            }
            else {
                this.iterator2.index = 0;
                this.iterator2.valid = true;
                this.iterator1.valid = false;
                v0 = this.iterator2;
            }

            return ((Iterator)v0);
        }
    }

    public class ArrayIterator implements Iterable, Iterator {
        private final boolean allowRemove;
        private final Array array;
        int index;
        boolean valid;

        public ArrayIterator(Array arg2, boolean allowRemove) {
            super();
            this.valid = true;
            this.array = arg2;
            this.allowRemove = allowRemove;
        }

        public ArrayIterator(Array arg2) {
            super(arg2, true);
        }

        public boolean hasNext() {
            boolean v0;
            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            if(this.index < this.array.size) {
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
            if(this.index >= this.array.size) {
                throw new NoSuchElementException(String.valueOf(this.index));
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object[] v0 = this.array.items;
            int v1 = this.index;
            this.index = v1 + 1;
            return v0[v1];
        }

        public void remove() {
            if(!this.allowRemove) {
                throw new GdxRuntimeException("Remove not allowed.");
            }

            --this.index;
            this.array.removeIndex(this.index);
        }

        public void reset() {
            this.index = 0;
        }
    }

    public Object[] items;
    private ArrayIterable iterable;
    public boolean ordered;
    private PredicateIterable predicateIterable;
    public int size;

    public Array() {
        this(true, 16);
    }

    public Array(Array arg5) {
        this(arg5.ordered, arg5.size, arg5.items.getClass().getComponentType());
        this.size = arg5.size;
        System.arraycopy(arg5.items, 0, this.items, 0, this.size);
    }

    public Array(int capacity) {
        this(true, capacity);
    }

    public Array(boolean ordered, int capacity, Class arrayType) {
        super();
        this.ordered = ordered;
        this.items = ArrayReflection.newInstance(arrayType, capacity);
    }

    public Array(boolean ordered, int capacity) {
        super();
        this.ordered = ordered;
        this.items = new Object[capacity];
    }

    public Array(Class arrayType) {
        this(true, 16, arrayType);
    }

    public Array(boolean ordered, Object[] arg5, int start, int count) {
        this(ordered, count, arg5.getClass().getComponentType());
        this.size = count;
        System.arraycopy(arg5, start, this.items, 0, this.size);
    }

    public Array(Object[] arg4) {
        this(true, arg4, 0, arg4.length);
    }

    public void add(Object arg5) {
        Object[] v0 = this.items;
        if(this.size == v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)this.size)) * 1.75f))));
        }

        int v1 = this.size;
        this.size = v1 + 1;
        v0[v1] = arg5;
    }

    public void addAll(Array arg3) {
        this.addAll(arg3, 0, arg3.size);
    }

    public void addAll(Object[] arg3) {
        this.addAll(arg3, 0, arg3.length);
    }

    public void addAll(Array arg4, int start, int count) {
        if(start + count > arg4.size) {
            throw new IllegalArgumentException("start + count must be <= size: " + start + " + " + count + " <= " + arg4.size);
        }

        this.addAll(arg4.items, start, count);
    }

    public void addAll(Object[] arg6, int start, int count) {
        Object[] v0 = this.items;
        int v1 = this.size + count;
        if(v1 > v0.length) {
            v0 = this.resize(Math.max(8, ((int)((((float)v1)) * 1.75f))));
        }

        System.arraycopy(arg6, start, v0, this.size, count);
        this.size += count;
    }

    public void clear() {
        Object[] v1 = this.items;
        int v0 = 0;
        int v2 = this.size;
        while(v0 < v2) {
            v1[v0] = null;
            ++v0;
        }

        this.size = 0;
    }

    public boolean contains(Object arg6, boolean identity) {
        int v1;
        boolean v3 = true;
        Object[] v2 = this.items;
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

    public Object[] ensureCapacity(int additionalCapacity) {
        int v0 = this.size + additionalCapacity;
        if(v0 > this.items.length) {
            this.resize(Math.max(8, v0));
        }

        return this.items;
    }

    public boolean equals(Object object) {
        boolean v7 = true;
        if((((Array)object)) != this) {
            if(!(object instanceof Array)) {
                v7 = false;
            }
            else {
                Object v0 = object;
                int v4 = this.size;
                if(v4 != ((Array)v0).size) {
                    v7 = false;
                }
                else {
                    Object[] v2 = this.items;
                    Object[] v3 = ((Array)v0).items;
                    int v1;
                    for(v1 = 0; v1 < v4; ++v1) {
                        Object v5 = v2[v1];
                        Object v6 = v3[v1];
                        if(v5 == null) {
                            if(v6 != null) {
                                goto label_26;
                            }
                        }
                        else if(!v5.equals(v6)) {
                        label_26:
                            v7 = false;
                            break;
                        }
                    }
                }
            }
        }

        return v7;
    }

    public Object first() {
        if(this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }

        return this.items[0];
    }

    public Object get(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        return this.items[index];
    }

    public int indexOf(Object arg5, boolean identity) {
        int v3;
        int v2;
        int v0;
        Object[] v1 = this.items;
        if((identity) || arg5 == null) {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    break;
                }
                else if(v1[v0] == arg5) {
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
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    goto label_22;
                }
                else if(arg5.equals(v1[v0])) {
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

    public void insert(int index, Object arg6) {
        if(index > this.size) {
            throw new IndexOutOfBoundsException("index can\'t be > size: " + index + " > " + this.size);
        }

        Object[] v0 = this.items;
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
        v0[index] = arg6;
    }

    public Iterator iterator() {
        if(this.iterable == null) {
            this.iterable = new ArrayIterable(this);
        }

        return this.iterable.iterator();
    }

    public int lastIndexOf(Object arg4, boolean identity) {
        int v2;
        int v0;
        Object[] v1 = this.items;
        if((identity) || arg4 == null) {
            v0 = this.size - 1;
            while(true) {
                if(v0 < 0) {
                    break;
                }
                else if(v1[v0] == arg4) {
                    v2 = v0;
                }
                else {
                    --v0;
                    continue;
                }

                goto label_9;
            }

        label_22:
            v2 = -1;
        }
        else {
            v0 = this.size - 1;
            while(true) {
                if(v0 < 0) {
                    goto label_22;
                }
                else if(arg4.equals(v1[v0])) {
                    v2 = v0;
                }
                else {
                    --v0;
                    continue;
                }

                break;
            }
        }

    label_9:
        return v2;
    }

    public static Array of(Class arg1) {
        return new Array(arg1);
    }

    public static Array of(boolean ordered, int capacity, Class arg3) {
        return new Array(ordered, capacity, arg3);
    }

    public Object peek() {
        if(this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }

        return this.items[this.size - 1];
    }

    public Object pop() {
        if(this.size == 0) {
            throw new IllegalStateException("Array is empty.");
        }

        --this.size;
        Object v0 = this.items[this.size];
        this.items[this.size] = null;
        return v0;
    }

    public Object random() {
        Object v0;
        if(this.size == 0) {
            v0 = null;
        }
        else {
            v0 = this.items[MathUtils.random(0, this.size - 1)];
        }

        return v0;
    }

    public boolean removeAll(Array arg9, boolean identity) {
        boolean v7;
        int v1;
        Object v2;
        int v4;
        int v0;
        int v5 = this.size;
        int v6 = v5;
        Object[] v3 = this.items;
        if(identity) {
            v0 = 0;
            v4 = arg9.size;
            while(v0 < v4) {
                v2 = arg9.get(v0);
                v1 = 0;
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
        }
        else {
            v0 = 0;
            v4 = arg9.size;
            while(v0 < v4) {
                v2 = arg9.get(v0);
                v1 = 0;
                while(v1 < v5) {
                    if(v2.equals(v3[v1])) {
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
        }

        if(v5 != v6) {
            v7 = true;
        }
        else {
            v7 = false;
        }

        return v7;
    }

    public Object removeIndex(int index) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        Object[] v0 = this.items;
        Object v1 = v0[index];
        --this.size;
        if(this.ordered) {
            System.arraycopy(v0, index + 1, v0, index, this.size - index);
        }
        else {
            v0[index] = v0[this.size];
        }

        v0[this.size] = null;
        return v1;
    }

    public void removeRange(int start, int end) {
        if(end >= this.size) {
            throw new IndexOutOfBoundsException("end can\'t be >= size: " + end + " >= " + this.size);
        }

        if(start > end) {
            throw new IndexOutOfBoundsException("start can\'t be > end: " + start + " > " + end);
        }

        Object[] v2 = this.items;
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

    public boolean removeValue(Object arg6, boolean identity) {
        int v2;
        int v0;
        boolean v3 = true;
        Object[] v1 = this.items;
        if((identity) || arg6 == null) {
            v0 = 0;
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    break;
                }
                else if(v1[v0] == arg6) {
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
            v2 = this.size;
            while(true) {
                if(v0 >= v2) {
                    goto label_23;
                }
                else if(arg6.equals(v1[v0])) {
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

    protected Object[] resize(int newSize) {
        Object[] v0 = this.items;
        Object v1 = ArrayReflection.newInstance(v0.getClass().getComponentType(), newSize);
        System.arraycopy(v0, 0, v1, 0, Math.min(this.size, v1.length));
        this.items = ((Object[])v1);
        return ((Object[])v1);
    }

    public void reverse() {
        Object[] v2 = this.items;
        int v0 = 0;
        int v3 = this.size - 1;
        int v4 = this.size / 2;
        while(v0 < v4) {
            int v1 = v3 - v0;
            Object v5 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v5;
            ++v0;
        }
    }

    public Iterable select(Predicate arg2) {
        if(this.predicateIterable == null) {
            this.predicateIterable = new PredicateIterable(((Iterable)this), arg2);
        }
        else {
            this.predicateIterable.set(((Iterable)this), arg2);
        }

        return this.predicateIterable;
    }

    public Object selectRanked(Comparator arg4, int kthLowest) {
        if(kthLowest < 1) {
            throw new GdxRuntimeException("nth_lowest must be greater than 0, 1 = first, 2 = second...");
        }

        return Select.instance().select(this.items, arg4, kthLowest, this.size);
    }

    public int selectRankedIndex(Comparator arg4, int kthLowest) {
        if(kthLowest < 1) {
            throw new GdxRuntimeException("nth_lowest must be greater than 0, 1 = first, 2 = second...");
        }

        return Select.instance().selectIndex(this.items, arg4, kthLowest, this.size);
    }

    public void set(int index, Object arg5) {
        if(index >= this.size) {
            throw new IndexOutOfBoundsException("index can\'t be >= size: " + index + " >= " + this.size);
        }

        this.items[index] = arg5;
    }

    public Object[] shrink() {
        if(this.items.length != this.size) {
            this.resize(this.size);
        }

        return this.items;
    }

    public void shuffle() {
        Object[] v2 = this.items;
        int v0;
        for(v0 = this.size - 1; v0 >= 0; --v0) {
            int v1 = MathUtils.random(v0);
            Object v3 = v2[v0];
            v2[v0] = v2[v1];
            v2[v1] = v3;
        }
    }

    public void sort(Comparator arg5) {
        Sort.instance().sort(this.items, arg5, 0, this.size);
    }

    public void sort() {
        Sort.instance().sort(this.items, 0, this.size);
    }

    public void swap(int first, int second) {
        if(first >= this.size) {
            throw new IndexOutOfBoundsException("first can\'t be >= size: " + first + " >= " + this.size);
        }

        if(second >= this.size) {
            throw new IndexOutOfBoundsException("second can\'t be >= size: " + second + " >= " + this.size);
        }

        Object[] v1 = this.items;
        Object v0 = v1[first];
        v1[first] = v1[second];
        v1[second] = v0;
    }

    public Object[] toArray(Class type) {
        Object v0 = ArrayReflection.newInstance(type, this.size);
        System.arraycopy(this.items, 0, v0, 0, this.size);
        return ((Object[])v0);
    }

    public Object[] toArray() {
        return this.toArray(this.items.getClass().getComponentType());
    }

    public String toString() {
        String v3;
        if(this.size == 0) {
            v3 = "[]";
        }
        else {
            Object[] v2 = this.items;
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
            Object[] v2 = this.items;
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
            int v0;
            for(v0 = newSize; v0 < this.size; ++v0) {
                this.items[v0] = null;
            }

            this.size = newSize;
        }
    }

    public static Array with(Object[] arg1) {
        return new Array(arg1);
    }
}

