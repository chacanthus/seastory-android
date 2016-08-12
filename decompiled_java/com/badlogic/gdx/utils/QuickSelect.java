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

public class QuickSelect {
    private Object[] array;
    private Comparator comp;

    public QuickSelect() {
        super();
    }

    private int medianOfThreePivot(int leftIdx, int rightIdx) {
        Object v0 = this.array[leftIdx];
        int v2 = (leftIdx + rightIdx) / 2;
        Object v1 = this.array[v2];
        Object v3 = this.array[rightIdx];
        if(this.comp.compare(v0, v1) > 0) {
            if(this.comp.compare(v1, v3) <= 0) {
                if(this.comp.compare(v0, v3) > 0) {
                    v2 = rightIdx;
                }
                else {
                    v2 = leftIdx;
                }
            }
        }
        else if(this.comp.compare(v0, v3) > 0) {
            v2 = leftIdx;
        }
        else if(this.comp.compare(v1, v3) > 0) {
            v2 = rightIdx;
        }

        return v2;
    }

    private int partition(int left, int right, int pivot) {
        Object v1 = this.array[pivot];
        this.swap(right, pivot);
        int v2 = left;
        int v0;
        for(v0 = left; v0 < right; ++v0) {
            if(this.comp.compare(this.array[v0], v1) < 0) {
                this.swap(v2, v0);
                ++v2;
            }
        }

        this.swap(right, v2);
        return v2;
    }

    private int recursiveSelect(int left, int right, int k) {
        int v3;
        if(left != right) {
            int v2 = this.partition(left, right, this.medianOfThreePivot(left, right));
            int v0 = v2 - left + 1;
            if(v0 == k) {
                v3 = v2;
            }
            else if(k < v0) {
                v3 = this.recursiveSelect(left, v2 - 1, k);
            }
            else {
                v3 = this.recursiveSelect(v2 + 1, right, k - v0);
            }

            left = v3;
        }

        return left;
    }

    public int select(Object[] arg3, Comparator arg4, int n, int size) {
        this.array = arg3;
        this.comp = arg4;
        return this.recursiveSelect(0, size - 1, n);
    }

    private void swap(int left, int right) {
        Object v0 = this.array[left];
        this.array[left] = this.array[right];
        this.array[right] = v0;
    }
}

