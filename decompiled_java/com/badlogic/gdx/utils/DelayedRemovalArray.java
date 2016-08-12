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

import java.util.Comparator;

public class DelayedRemovalArray extends Array {
    private int iterating;
    private IntArray remove;

    public DelayedRemovalArray(int capacity) {
        super(capacity);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray() {
        super();
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(Array array) {
        super(array);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(Class arrayType) {
        super(arrayType);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(boolean ordered, int capacity) {
        super(ordered, capacity);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(boolean ordered, int capacity, Class arrayType) {
        super(ordered, capacity, arrayType);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(boolean ordered, Object[] arg4, int startIndex, int count) {
        super(ordered, arg4, startIndex, count);
        this.remove = new IntArray(0);
    }

    public DelayedRemovalArray(Object[] arg3) {
        super(arg3);
        this.remove = new IntArray(0);
    }

    public void begin() {
        ++this.iterating;
    }

    public void clear() {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.clear();
    }

    public void end() {
        if(this.iterating == 0) {
            throw new IllegalStateException("begin must be called before end.");
        }

        --this.iterating;
        if(this.iterating == 0) {
            int v0 = 0;
            int v1 = this.remove.size;
            while(v0 < v1) {
                this.removeIndex(this.remove.pop());
                ++v0;
            }
        }
    }

    public void insert(int index, Object arg4) {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.insert(index, arg4);
    }

    public Object pop() {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        return super.pop();
    }

    private void remove(int index) {
        int v0 = 0;
        int v1 = this.remove.size;
        while(true) {
            if(v0 < v1) {
                int v2 = this.remove.get(v0);
                if(index != v2) {
                    if(index < v2) {
                        this.remove.insert(v0, index);
                    }
                    else {
                        ++v0;
                        continue;
                    }
                }
            }
            else {
                break;
            }

            return;
        }

        this.remove.add(index);
    }

    public Object removeIndex(int index) {
        Object v0;
        if(this.iterating > 0) {
            this.remove(index);
            v0 = this.get(index);
        }
        else {
            v0 = super.removeIndex(index);
        }

        return v0;
    }

    public void removeRange(int start, int end) {
        if(this.iterating > 0) {
            int v0;
            for(v0 = end; v0 >= start; --v0) {
                this.remove(v0);
            }
        }
        else {
            super.removeRange(start, end);
        }
    }

    public boolean removeValue(Object arg3, boolean identity) {
        boolean v1;
        if(this.iterating > 0) {
            int v0 = this.indexOf(arg3, identity);
            if(v0 == -1) {
                v1 = false;
            }
            else {
                this.remove(v0);
                v1 = true;
            }
        }
        else {
            v1 = super.removeValue(arg3, identity);
        }

        return v1;
    }

    public void reverse() {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.reverse();
    }

    public void set(int index, Object arg4) {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.set(index, arg4);
    }

    public void shuffle() {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.shuffle();
    }

    public void sort() {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.sort();
    }

    public void sort(Comparator arg3) {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.sort(arg3);
    }

    public void swap(int first, int second) {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.swap(first, second);
    }

    public void truncate(int newSize) {
        if(this.iterating > 0) {
            throw new IllegalStateException("Invalid between begin/end.");
        }

        super.truncate(newSize);
    }

    public static DelayedRemovalArray with(Object[] arg1) {
        return new DelayedRemovalArray(arg1);
    }
}

