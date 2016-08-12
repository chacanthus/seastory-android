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

public class SnapshotArray extends Array {
    private Object[] recycled;
    private Object[] snapshot;
    private int snapshots;

    public SnapshotArray(boolean ordered, int capacity, Class arrayType) {
        super(ordered, capacity, arrayType);
    }

    public SnapshotArray() {
        super();
    }

    public SnapshotArray(int capacity) {
        super(capacity);
    }

    public SnapshotArray(Array array) {
        super(array);
    }

    public SnapshotArray(Class arrayType) {
        super(arrayType);
    }

    public SnapshotArray(boolean ordered, int capacity) {
        super(ordered, capacity);
    }

    public SnapshotArray(boolean ordered, Object[] arg2, int startIndex, int count) {
        super(ordered, arg2, startIndex, count);
    }

    public SnapshotArray(Object[] arg1) {
        super(arg1);
    }

    public Object[] begin() {
        this.modified();
        this.snapshot = this.items;
        ++this.snapshots;
        return this.items;
    }

    public void clear() {
        this.modified();
        super.clear();
    }

    public void end() {
        Object v4 = null;
        this.snapshots = Math.max(0, this.snapshots - 1);
        if(this.snapshot != null) {
            if(this.snapshot != this.items && this.snapshots == 0) {
                this.recycled = this.snapshot;
                int v0 = 0;
                int v1 = this.recycled.length;
                while(v0 < v1) {
                    this.recycled[v0] = v4;
                    ++v0;
                }
            }

            this.snapshot = ((Object[])v4);
        }
    }

    public void insert(int index, Object arg2) {
        this.modified();
        super.insert(index, arg2);
    }

    private void modified() {
        if(this.snapshot != null && this.snapshot == this.items) {
            if(this.recycled != null && this.recycled.length >= this.size) {
                System.arraycopy(this.items, 0, this.recycled, 0, this.size);
                this.items = this.recycled;
                this.recycled = null;
                return;
            }

            this.resize(this.items.length);
        }
    }

    public Object pop() {
        this.modified();
        return super.pop();
    }

    public boolean removeAll(Array arg2, boolean identity) {
        this.modified();
        return super.removeAll(arg2, identity);
    }

    public Object removeIndex(int index) {
        this.modified();
        return super.removeIndex(index);
    }

    public void removeRange(int start, int end) {
        this.modified();
        super.removeRange(start, end);
    }

    public boolean removeValue(Object arg2, boolean identity) {
        this.modified();
        return super.removeValue(arg2, identity);
    }

    public void reverse() {
        this.modified();
        super.reverse();
    }

    public void set(int index, Object arg2) {
        this.modified();
        super.set(index, arg2);
    }

    public void shuffle() {
        this.modified();
        super.shuffle();
    }

    public void sort() {
        this.modified();
        super.sort();
    }

    public void sort(Comparator arg1) {
        this.modified();
        super.sort(arg1);
    }

    public void swap(int first, int second) {
        this.modified();
        super.swap(first, second);
    }

    public void truncate(int newSize) {
        this.modified();
        super.truncate(newSize);
    }

    public static SnapshotArray with(Object[] arg1) {
        return new SnapshotArray(arg1);
    }
}

