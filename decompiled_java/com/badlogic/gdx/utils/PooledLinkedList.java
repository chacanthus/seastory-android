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

public class PooledLinkedList {
    final class Item {
        Item() {
            super();
        }
    }

    private Item curr;
    private Item head;
    private Item iter;
    private final Pool pool;
    private int size;
    private Item tail;

    public PooledLinkedList(int maxPoolSize) {
        super();
        this.size = 0;
        this.pool = new Pool() {
            protected Item newObject() {
                return new Item();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
    }

    public void add(Object arg4) {
        Object v0 = this.pool.obtain();
        ((Item)v0).payload = arg4;
        ((Item)v0).next = null;
        ((Item)v0).prev = null;
        if(this.head == null) {
            this.head = ((Item)v0);
            this.tail = ((Item)v0);
            ++this.size;
        }
        else {
            ((Item)v0).prev = this.tail;
            this.tail.next = ((Item)v0);
            this.tail = ((Item)v0);
            ++this.size;
        }
    }

    public void clear() {
        this.iter();
        while(this.next() != null) {
            this.remove();
        }
    }

    public void iter() {
        this.iter = this.head;
    }

    public void iterReverse() {
        this.iter = this.tail;
    }

    public Object next() {
        Object v0;
        if(this.iter == null) {
            v0 = null;
        }
        else {
            v0 = this.iter.payload;
            this.curr = this.iter;
            this.iter = this.iter.next;
        }

        return v0;
    }

    public Object previous() {
        Object v0;
        if(this.iter == null) {
            v0 = null;
        }
        else {
            v0 = this.iter.payload;
            this.curr = this.iter;
            this.iter = this.iter.prev;
        }

        return v0;
    }

    public void remove() {
        Item v5 = null;
        if(this.curr != null) {
            --this.size;
            this.pool.free(this.curr);
            Item v0 = this.curr;
            Item v1 = this.curr.next;
            Item v2 = this.curr.prev;
            this.curr = v5;
            if(this.size == 0) {
                this.head = v5;
                this.tail = v5;
            }
            else if(v0 == this.head) {
                v1.prev = v5;
                this.head = v1;
            }
            else if(v0 == this.tail) {
                v2.next = v5;
                this.tail = v2;
            }
            else {
                v2.next = v1;
                v1.prev = v2;
            }
        }
    }
}

