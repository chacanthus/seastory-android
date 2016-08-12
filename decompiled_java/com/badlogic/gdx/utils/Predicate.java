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

public abstract interface Predicate {
    public class PredicateIterator implements Iterator {
        public boolean end;
        public Iterator iterator;
        public Object next;
        public boolean peeked;
        public Predicate predicate;

        public PredicateIterator(Iterator arg2, Predicate arg3) {
            super();
            this.end = false;
            this.peeked = false;
            this.next = null;
            this.set(arg2, arg3);
        }

        public PredicateIterator(Iterable arg2, Predicate arg3) {
            super(arg2.iterator(), arg3);
        }

        public boolean hasNext() {
            Object v0;
            boolean v1 = false;
            if(!this.end) {
                if(this.next != null) {
                    v1 = true;
                }
                else {
                    this.peeked = true;
                    do {
                        if(this.iterator.hasNext()) {
                            v0 = this.iterator.next();
                            if(!this.predicate.evaluate(v0)) {
                                continue;
                            }

                            break;
                        }
                        else {
                            goto label_21;
                        }
                    }
                    while(true);

                    this.next = v0;
                    v1 = true;
                    goto label_4;
                label_21:
                    this.end = true;
                }
            }

        label_4:
            return v1;
        }

        public Object next() {
            Object v0;
            Object v1 = null;
            if(this.next != null || (this.hasNext())) {
                v0 = this.next;
                this.next = v1;
                this.peeked = false;
            }
            else {
                v0 = v1;
            }

            return v0;
        }

        public void remove() {
            if(this.peeked) {
                throw new GdxRuntimeException("Cannot remove between a call to hasNext() and next().");
            }

            this.iterator.remove();
        }

        public void set(Iterator arg2, Predicate arg3) {
            this.iterator = arg2;
            this.predicate = arg3;
            this.peeked = false;
            this.end = false;
            this.next = null;
        }

        public void set(Iterable arg2, Predicate arg3) {
            this.set(arg2.iterator(), arg3);
        }
    }

    public abstract boolean evaluate(Object arg0);
}

