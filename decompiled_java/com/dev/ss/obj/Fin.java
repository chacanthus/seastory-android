﻿// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.obj;

import com.dev.ss.Res;
import com.dev.ss.lib.Ani;
import com.dev.ss.lib.Util;
import com.dev.ss.screen.GameScreen;

public class Fin extends Ani {
    public float speed;

    public Fin(int _iPartCnt, float _frameDuration, int _iTotalFrameCount) {
        super(_iPartCnt, _frameDuration, _iTotalFrameCount);
        this.speed = 0f;
    }

    public void draw(float _deltaTime) {
        if(this.visible) {
            this.deltaTime += _deltaTime;
            this.x += this.speed;
            int v6 = this.getPartFrameNumber();
            if(this.speed > 0f) {
                GameScreen.batch.draw(Res.rgWhale, this.x - Util.getWidth(800f), this.y - Util.getHeight(72f), Util.getWidth(1600f), Util.getHeight(400f));
                GameScreen.batch.draw(Res.rgFin[v6], this.x, this.y, this.width, this.height);
                if(this.x > Util.getLeft(1800f)) {
                    this.visible = false;
                }
            }
        }
    }
}

