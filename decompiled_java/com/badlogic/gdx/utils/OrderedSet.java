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

public class OrderedSet extends ObjectSet {
    public class OrderedSetIterator extends ObjectSetIterator {
        private Array items;

        public OrderedSetIterator(OrderedSet arg2) {
            super(((ObjectSet)arg2));
            this.items = arg2.items;
        }

        public Object next() {
            boolean v1;
            if(!this.hasNext) {
                throw new NoSuchElementException();
            }

            if(!this.valid) {
                throw new GdxRuntimeException("#iterator() cannot be used nested.");
            }

            Object v0 = this.items.get(this.nextIndex);
            ++this.nextIndex;
            if(this.nextIndex < this.set.size) {
                v1 = true;
            }
            else {
                v1 = false;
            }

            this.hasNext = v1;
            return v0;
        }

        public void remove() {
            if(this.nextIndex < 0) {
                throw new IllegalStateException("next must be called before remove.");
            }

            --this.nextIndex;
            this.set.remove(this.items.get(this.nextIndex));
        }

        public void reset() {
            boolean v0 = false;
            this.nextIndex = 0;
            if(this.set.size > 0) {
                v0 = true;
            }

            this.hasNext = v0;
        }
    }

    final Array items;
    OrderedSetIterator iterator1;
    OrderedSetIterator iterator2;

    public OrderedSet() {
        super();
        this.items = new Array();
    }

    public OrderedSet(int initialCapacity) {
        super(initialCapacity);
        this.items = new Array(this.capacity);
    }

    public OrderedSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.items = new Array(this.capacity);
    }

    public OrderedSet(OrderedSet set) {
        super(((ObjectSet)set));
        this.items = new Array(this.capacity);
        this.items.addAll(set.items);
    }

    public boolean add(Object arg2) {
        if(!this.contains(arg2)) {
            this.items.add(arg2);
        }

        return super.add(arg2);
    }

    public void clear() {
        this.items.clear();
        super.clear();
    }

    public void clear(int maximumCapacity) {
        this.items.clear();
        super.clear(maximumCapacity);
    }

    public OrderedSetIterator iterator() {
        OrderedSetIterator v0;
        if(this.iterator1 == null) {
            this.iterator1 = new OrderedSetIterator(this);
            this.iterator2 = new OrderedSetIterator(this);
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

    public ObjectSetIterator iterator() {
        return this.iterator();
    }

    public Iterator iterator() {
        return this.iterator();
    }

    public Array orderedItems() {
        return this.items;
    }

    public boolean remove(Object arg3) {
        this.items.removeValue(arg3, false);
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
            Array v3 = this.items;
            int v1 = 0;
            int v4 = v3.size;
            while(v1 < v4) {
                Object v2 = v3.get(v1);
                if(v1 > 0) {
                    v0.append(", ");
                }

                v0.append(v2);
                ++v1;
            }

            v0.append('}');
            v5 = v0.toString();
        }

        return v5;
    }
}

