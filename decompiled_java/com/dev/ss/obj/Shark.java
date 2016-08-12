// 도박중독 예방 캠페인
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

public class Shark extends Ani {
    public int iMoveType;

    public Shark(int _iPartCnt, float _frameDuration, int _iTotalFrameCount) {
        super(_iPartCnt, _frameDuration, _iTotalFrameCount);
        this.iMoveType = 0;
    }

    public void draw(float _deltaTime) {
        float v7 = 50f;
        int v4 = 9;
        float v3 = 150f;
        float v2 = 100f;
        if(this.visible) {
            this.deltaTime += _deltaTime;
            int v6 = this.getPartFrameNumber();
            if(this.iMoveType == 1) {
                if(v6 < 14) {
                    this.x -= Util.getWidth(400f) * _deltaTime;
                    this.y -= Util.getHeight(80f) * _deltaTime;
                }
                else {
                    if(v6 >= 14 && v6 < 25) {
                        this.x += Util.getWidth(250f) * _deltaTime;
                        this.y -= Util.getHeight(90f) * _deltaTime;
                        goto label_28;
                    }

                    if(v6 < 25) {
                        goto label_28;
                    }

                    this.x -= Util.getWidth(400f) * _deltaTime;
                    this.y -= Util.getHeight(120f) * _deltaTime;
                }

            label_28:
                this.width += Util.getWidth(90f) * _deltaTime;
                this.height += Util.getHeight(45f) * _deltaTime;
                if(v6 >= 37) {
                    this.visible = false;
                }

                GameScreen.batch.draw(Res.rgSharkl[v6], this.x, this.y, this.width, this.height);
            }
            else {
                if(this.iMoveType != 2) {
                    goto label_132;
                }

                if(v6 < v4) {
                    this.x += Util.getWidth(v2) * _deltaTime;
                    this.y -= Util.getHeight(v3) * _deltaTime;
                }
                else if(v6 >= v4) {
                    this.x -= Util.getWidth(v2) * _deltaTime;
                    this.y -= Util.getHeight(v3) * _deltaTime;
                }

                this.width += Util.getWidth(v2) * _deltaTime;
                this.height += Util.getHeight(v7) * _deltaTime;
                if(v6 >= 37) {
                    this.visible = false;
                }

                GameScreen.batch.draw(Res.rgSharkl[v6], this.x, this.y, this.width, this.height);
                return;
            label_132:
                if(this.iMoveType != 4) {
                    return;
                }

                if(v6 < v4) {
                    this.x -= Util.getWidth(v2) * _deltaTime;
                    this.y -= Util.getHeight(v3) * _deltaTime;
                }
                else if(v6 >= v4) {
                    this.x += Util.getWidth(v3) * _deltaTime;
                    this.y -= Util.getHeight(v3) * _deltaTime;
                }

                this.width += Util.getWidth(v2) * _deltaTime;
                this.height += Util.getHeight(v7) * _deltaTime;
                if(v6 >= 16) {
                    this.visible = false;
                }

                GameScreen.batch.draw(Res.rgSharkr[v6], this.x, this.y, this.width, this.height);
            }
        }
    }
}

