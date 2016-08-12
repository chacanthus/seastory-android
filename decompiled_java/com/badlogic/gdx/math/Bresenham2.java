// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Bresenham2 {
    private final Array points;
    private final Pool pool;

    public Bresenham2() {
        super();
        this.points = new Array();
        this.pool = new Pool() {
            protected GridPoint2 newObject() {
                return new GridPoint2();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
    }

    public Array line(int startX, int startY, int endX, int endY) {
        this.pool.freeAll(this.points);
        this.points.clear();
        return this.line(startX, startY, endX, endY, this.pool, this.points);
    }

    public Array line(int startX, int startY, int endX, int endY, Pool arg17, Array arg18) {
        int v11 = endX - startX;
        int v5 = endY - startY;
        int v1 = 0;
        int v3 = 0;
        int v2 = 0;
        int v4 = 0;
        if(v11 < 0) {
            v1 = -1;
            v2 = -1;
        }
        else if(v11 > 0) {
            v1 = 1;
            v2 = 1;
        }

        if(v5 < 0) {
            v3 = -1;
        }
        else if(v5 > 0) {
            v3 = 1;
        }

        int v7 = Math.abs(v11);
        int v10 = Math.abs(v5);
        if(v7 <= v10) {
            v7 = Math.abs(v5);
            v10 = Math.abs(v11);
            if(v5 < 0) {
                v4 = -1;
            }
            else if(v5 > 0) {
                v4 = 1;
            }

            v2 = 0;
        }

        int v8 = v7 >> 1;
        int v6;
        for(v6 = 0; v6 <= v7; ++v6) {
            Object v9 = arg17.obtain();
            ((GridPoint2)v9).set(startX, startY);
            arg18.add(v9);
            v8 += v10;
            if(v8 > v7) {
                v8 -= v7;
                startX += v1;
                startY += v3;
            }
            else {
                startX += v2;
                startY += v4;
            }
        }

        return arg18;
    }

    public Array line(GridPoint2 start, GridPoint2 end) {
        return this.line(start.x, start.y, end.x, end.y);
    }
}

