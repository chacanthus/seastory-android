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

public class Sort {
    private ComparableTimSort comparableTimSort;
    private static Sort instance;
    private TimSort timSort;

    public Sort() {
        super();
    }

    public static Sort instance() {
        if(Sort.instance == null) {
            Sort.instance = new Sort();
        }

        return Sort.instance;
    }

    public void sort(Array arg5, Comparator arg6) {
        if(this.timSort == null) {
            this.timSort = new TimSort();
        }

        this.timSort.doSort(arg5.items, arg6, 0, arg5.size);
    }

    public void sort(Object[] arg2, int fromIndex, int toIndex) {
        if(this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }

        this.comparableTimSort.doSort(arg2, fromIndex, toIndex);
    }

    public void sort(Object[] arg2, Comparator arg3, int fromIndex, int toIndex) {
        if(this.timSort == null) {
            this.timSort = new TimSort();
        }

        this.timSort.doSort(arg2, arg3, fromIndex, toIndex);
    }

    public void sort(Array arg5) {
        if(this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }

        this.comparableTimSort.doSort(arg5.items, 0, arg5.size);
    }

    public void sort(Object[] arg4) {
        if(this.comparableTimSort == null) {
            this.comparableTimSort = new ComparableTimSort();
        }

        this.comparableTimSort.doSort(arg4, 0, arg4.length);
    }

    public void sort(Object[] arg4, Comparator arg5) {
        if(this.timSort == null) {
            this.timSort = new TimSort();
        }

        this.timSort.doSort(arg4, arg5, 0, arg4.length);
    }
}

