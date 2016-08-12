﻿// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

public class GridPoint3 {
    public int x;
    public int y;
    public int z;

    public GridPoint3() {
        super();
    }

    public GridPoint3(int x, int y, int z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GridPoint3(GridPoint3 point) {
        super();
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if(this != (((GridPoint3)o))) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if(this.x == ((GridPoint3)v0).x && this.y == ((GridPoint3)v0).y && this.z == ((GridPoint3)v0).z) {
                    goto label_3;
                }

                v1 = false;
                goto label_3;
            }

            v1 = false;
        }

    label_3:
        return v1;
    }

    public int hashCode() {
        return ((this.x + 17) * 17 + this.y) * 17 + this.z;
    }

    public GridPoint3 set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public GridPoint3 set(GridPoint3 point) {
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
        return this;
    }
}

