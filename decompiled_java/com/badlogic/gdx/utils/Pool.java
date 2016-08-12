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

public abstract class Pool {
    public abstract interface Poolable {
        public abstract void reset();
    }

    private final Array freeObjects;
    public final int max;
    public int peak;

    public Pool(int initialCapacity, int max) {
        super();
        this.freeObjects = new Array(false, initialCapacity);
        this.max = max;
    }

    public Pool(int initialCapacity) {
        this(initialCapacity, 2147483647);
    }

    public Pool() {
        this(16, 2147483647);
    }

    public void clear() {
        this.freeObjects.clear();
    }

    public void free(Object arg3) {
        if(arg3 == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }

        if(this.freeObjects.size < this.max) {
            this.freeObjects.add(arg3);
            this.peak = Math.max(this.peak, this.freeObjects.size);
        }

        if((arg3 instanceof Poolable)) {
            ((Poolable)arg3).reset();
        }
    }

    public void freeAll(Array arg7) {
        if(arg7 == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }

        Array v0 = this.freeObjects;
        int v2 = this.max;
        int v1;
        for(v1 = 0; v1 < arg7.size; ++v1) {
            Object v3 = arg7.get(v1);
            if(v3 != null) {
                if(v0.size < v2) {
                    v0.add(v3);
                }

                if(!(v3 instanceof Poolable)) {
                    goto label_11;
                }

                ((Poolable)v3).reset();
            }

        label_11:
        }

        this.peak = Math.max(this.peak, v0.size);
    }

    public int getFree() {
        return this.freeObjects.size;
    }

    protected abstract Object newObject();

    public Object obtain() {
        Object v0;
        if(this.freeObjects.size == 0) {
            v0 = this.newObject();
        }
        else {
            v0 = this.freeObjects.pop();
        }

        return v0;
    }
}

