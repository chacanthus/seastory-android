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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedMap extends ObjectMap {
    public class OrderedMapEntries extends Entries {
        private Array keys;

        public OrderedMapEntries(OrderedMap arg2) {
            super(((ObjectMap)arg2));
            this.keys = arg2.keys;
        }

        public Entry next() {
            boolean v0;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            this.entry.key = this.keys.get(this.nextIndex);
            this.entry.value = this.map.get(this.entry.key);
            ++this.nextIndex;
            if(this.nextIndex < this.map.size) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            this.hasNext = v0;
            return this.entry;
        }

        public Object next() {
            return this.next();
        }

        public void remove() {
            if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            this.map.remove(this.entry.key);
        }

        public void reset() {
            boolean v0 = false;
            this.nextIndex = 0;
            if(this.map.size > 0) {
                v0 = true;
            }

            this.hasNext = v0;
        }
    }

    public class OrderedMapKeys extends Keys {
        private Array keys;

        public OrderedMapKeys(OrderedMap arg2) {
            super(((ObjectMap)arg2));
            this.keys = arg2.keys;
        }

        public Object next() {
            boolean v1;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.keys.get(this.nextIndex);
            ++this.nextIndex;
            if(this.nextIndex < this.map.size) {
                v1 = true;
            }
            else {
                v1 = false;
            }

            this.hasNext = v1;
            return v0;
        }

        public void remove() {
            if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            this.map.remove(this.keys.get(this.nextIndex - 1));
        }

        public void reset() {
            boolean v0 = false;
            this.nextIndex = 0;
            if(this.map.size > 0) {
                v0 = true;
            }

            this.hasNext = v0;
        }
    }

    public class OrderedMapValues extends Values {
        private Array keys;

        public OrderedMapValues(OrderedMap arg2) {
            super(((ObjectMap)arg2));
            this.keys = arg2.keys;
        }

        public Object next() {
            boolean v1;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.map.get(this.keys.get(this.nextIndex));
            ++this.nextIndex;
            if(this.nextIndex < this.map.size) {
                v1 = true;
            }
            else {
                v1 = false;
            }

            this.hasNext = v1;
            return v0;
        }

        public void remove() {
            if(this.currentIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            this.map.remove(this.keys.get(this.nextIndex - 1));
        }

        public void reset() {
            boolean v0 = false;
            this.nextIndex = 0;
            if(this.map.size > 0) {
                v0 = true;
            }

            this.hasNext = v0;
        }
    }

    private Entries entries1;
    private Entries entries2;
    final Array keys;
    private Keys keys1;
    private Keys keys2;
    private Values values1;
    private Values values2;

    public OrderedMap() {
        super();
        this.keys = new Array();
    }

    public OrderedMap(int initialCapacity) {
        super(initialCapacity);
        this.keys = new Array(this.capacity);
    }

    public OrderedMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.keys = new Array(this.capacity);
    }

    public OrderedMap(ObjectMap arg3) {
        super(arg3);
        this.keys = new Array(this.capacity);
    }

    public void clear() {
        this.keys.clear();
        super.clear();
    }

    public void clear(int maximumCapacity) {
        this.keys.clear();
        super.clear(maximumCapacity);
    }

    public Entries entries() {
        Entries v0;
        if(this.entries1 == null) {
            this.entries1 = new OrderedMapEntries(this);
            this.entries2 = new OrderedMapEntries(this);
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

    public Entries iterator() {
        return this.entries();
    }

    public Iterator iterator() {
        return this.iterator();
    }

    public Keys keys() {
        Keys v0;
        if(this.keys1 == null) {
            this.keys1 = new OrderedMapKeys(this);
            this.keys2 = new OrderedMapKeys(this);
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

    public Array orderedKeys() {
        return this.keys;
    }

    public Object put(Object arg2, Object arg3) {
        if(!this.containsKey(arg2)) {
            this.keys.add(arg2);
        }

        return super.put(arg2, arg3);
    }

    public Object remove(Object arg3) {
        this.keys.removeValue(arg3, false);
        return super.remove(arg3);
    }

    public String toString() {
        String v5;
        if(this.size == 0) {
            v5 = "{}";
        }
        else {
            StringBuilder v0 = new StringBuilder(32);
            v0.append('{');
            Array v3 = this.keys;
            int v1 = 0;
            int v4 = v3.size;
            while(v1 < v4) {
                Object v2 = v3.get(v1);
                if(v1 > 0) {
                    v0.append(", ");
                }

                v0.append(v2);
                v0.append('=');
                v0.append(this.get(v2));
                ++v1;
            }

            v0.append('}');
            v5 = v0.toString();
        }

        return v5;
    }

    public Values values() {
        Values v0;
        if(this.values1 == null) {
            this.values1 = new OrderedMapValues(this);
            this.values2 = new OrderedMapValues(this);
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

