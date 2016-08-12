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

public class SortedIntList implements Iterable {
    class Iterator implements java.util.Iterator {
        private Node position;
        private Node previousPosition;

        Iterator(SortedIntList arg1) {
            SortedIntList.this = arg1;
            super();
        }

        public boolean hasNext() {
            boolean v0;
            if(this.position != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public Node next() {
            this.previousPosition = this.position;
            this.position = this.position.n;
            return this.previousPosition;
        }

        public Object next() {
            return this.next();
        }

        public void remove() {
            if(this.previousPosition != null) {
                if(this.previousPosition == SortedIntList.this.first) {
                    SortedIntList.this.first = this.position;
                }
                else {
                    this.previousPosition.p.n = this.position;
                    if(this.position != null) {
                        this.position.p = this.previousPosition.p;
                    }
                }

                --SortedIntList.this.size;
            }
        }

        public Iterator reset() {
            this.position = SortedIntList.this.first;
            this.previousPosition = null;
            return this;
        }
    }

    public class Node {
        public Node() {
            super();
        }
    }

    class NodePool extends Pool {
        NodePool() {
            super();
        }

        protected Node newObject() {
            return new Node();
        }

        protected Object newObject() {
            return this.newObject();
        }

        public Node obtain(Node arg2, Node arg3, Object arg4, int index) {
            Object v0 = super.obtain();
            ((Node)v0).p = arg2;
            ((Node)v0).n = arg3;
            ((Node)v0).value = arg4;
            ((Node)v0).index = index;
            return ((Node)v0);
        }
    }

    Node first;
    private Iterator iterator;
    private NodePool nodePool;
    int size;

    public SortedIntList() {
        super();
        this.nodePool = new NodePool();
        this.size = 0;
    }

    public void clear() {
        while(this.first != null) {
            this.nodePool.free(this.first);
            this.first = this.first.n;
        }

        this.size = 0;
    }

    public Object get(int index) {
        Object v1 = null;
        if(this.first != null) {
            Node v0 = this.first;
            while(v0.n != null) {
                if(v0.index >= index) {
                    break;
                }

                v0 = v0.n;
            }

            if(v0.index != index) {
                goto label_13;
            }

            v1 = v0.value;
        }

    label_13:
        return v1;
    }

    public Object insert(int index, Object arg7) {
        Object v4 = null;
        if(this.first != null) {
            Node v0 = this.first;
            while(v0.n != null) {
                if(v0.n.index > index) {
                    break;
                }

                v0 = v0.n;
            }

            if(index <= v0.index) {
                goto label_28;
            }

            v0.n = this.nodePool.obtain(v0, v0.n, arg7, index);
            if(v0.n.n != null) {
                v0.n.n.p = v0.n;
            }

            ++this.size;
            goto label_27;
        label_28:
            if(index >= v0.index) {
                goto label_40;
            }

            Node v1 = this.nodePool.obtain(((Node)v4), this.first, arg7, index);
            this.first.p = v1;
            this.first = v1;
            ++this.size;
            goto label_27;
        label_40:
            v0.value = arg7;
        }
        else {
            this.first = this.nodePool.obtain(((Node)v4), ((Node)v4), arg7, index);
            ++this.size;
        }

    label_27:
        return v4;
    }

    public java.util.Iterator iterator() {
        if(this.iterator == null) {
            this.iterator = new Iterator(this);
        }

        return this.iterator.reset();
    }

    public int size() {
        return this.size;
    }
}

