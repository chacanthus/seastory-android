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

public class Select {
    private static Select instance;
    private QuickSelect quickSelect;

    public Select() {
        super();
    }

    private int fastMax(Object[] arg6, Comparator arg7, int size) {
        int v1 = 0;
        int v2;
        for(v2 = 1; v2 < size; ++v2) {
            if(arg7.compare(arg6[v2], arg6[v1]) > 0) {
                v1 = v2;
            }
        }

        return v1;
    }

    private int fastMin(Object[] arg6, Comparator arg7, int size) {
        int v2 = 0;
        int v1;
        for(v1 = 1; v1 < size; ++v1) {
            if(arg7.compare(arg6[v1], arg6[v2]) < 0) {
                v2 = v1;
            }
        }

        return v2;
    }

    public static Select instance() {
        if(Select.instance == null) {
            Select.instance = new Select();
        }

        return Select.instance;
    }

    public Object select(Object[] arg3, Comparator arg4, int kthLowest, int size) {
        return arg3[this.selectIndex(arg3, arg4, kthLowest, size)];
    }

    public int selectIndex(Object[] arg5, Comparator arg6, int kthLowest, int size) {
        int v0;
        if(size < 1) {
            throw new GdxRuntimeException("cannot select from empty array (size < 1)");
        }

        if(kthLowest > size) {
            throw new GdxRuntimeException("Kth rank is larger than size. k: " + kthLowest + ", size: " + size);
        }

        if(kthLowest == 1) {
            v0 = this.fastMin(arg5, arg6, size);
        }
        else if(kthLowest == size) {
            v0 = this.fastMax(arg5, arg6, size);
        }
        else {
            if(this.quickSelect == null) {
                this.quickSelect = new QuickSelect();
            }

            v0 = this.quickSelect.select(arg5, arg6, kthLowest, size);
        }

        return v0;
    }
}

