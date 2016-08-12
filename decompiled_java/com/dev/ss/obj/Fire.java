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
import com.dev.ss.screen.GameScreen;

public class Fire extends Ani {
    public float presetTime;

    public Fire(int _iPartCnt, float _frameDuration, int _iTotalFrameCount) {
        super(_iPartCnt, _frameDuration, _iTotalFrameCount);
        this.presetTime = 0f;
    }

    public void draw(float _deltaTime) {
        this.deltaTime += _deltaTime;
        this.iIdx = 0;
        if(this.bPartPlay) {
            if(this.bHideWhenStop) {
                this.iIdx = this.getPartFrameNumber();
                if(this.visible) {
                    GameScreen.batch.draw(Res.rgFire[this.iIdx], this.x, this.y, this.width, this.height);
                }
            }
            else if(this.visible) {
                GameScreen.batch.draw(Res.rgFire[this.getPartFrameNumber()], this.x, this.y, this.width, this.height);
            }
        }
        else if(this.bHideWhenStop) {
            this.iIdx = this.getFrameNumber();
            if(this.visible) {
                GameScreen.batch.draw(Res.rgFire[this.iIdx], this.x, this.y, this.width, this.height);
            }
        }
        else if(this.visible) {
            GameScreen.batch.draw(Res.rgFire[this.getFrameNumber()], this.x, this.y, this.width, this.height);
        }
    }

    protected int getFrameNumber() {
        this.frameNumber = ((int)(this.deltaTime / this.frameDuration));
        if(this.playMode == 0) {
            this.frameNumber = this.iStopFrame;
            if(this.bHideWhenStop) {
                this.visible = false;
            }
        }
        else {
            this.frameNumber %= this.iTotalFrameCount;
        }

        this.iCurrentLoopCount = ((int)(this.deltaTime / this.totalDuration));
        if(this.iCurrentLoopCount >= this.iTargetLoopCount) {
            this.playMode = 0;
            this.frameNumber = this.iStopFrame;
            if(this.bHideWhenStop) {
                this.visible = false;
            }
        }

        return this.frameNumber;
    }
}

