// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.lib;

import com.badlogic.gdx.graphics.Camera;
import java.util.Random;

public class ScreenShaker {
    private boolean backToOrigin;
    private float deltaTime;
    private float exDeltaX;
    private float exDeltaY;
    private float interval;
    private Random rad;
    private float scale;
    private int shakeCount;
    private boolean shakeScreen;
    private int targetShakeCount;

    public ScreenShaker() {
        super();
        this.rad = new Random();
        this.shakeScreen = false;
        this.backToOrigin = false;
        this.shakeCount = 0;
        this.targetShakeCount = 0;
        this.scale = 0.03f;
        this.interval = 0.03f;
        this.exDeltaX = 0f;
        this.exDeltaY = 0f;
        this.deltaTime = 0f;
    }

    public void shakeScreen(int _targetShakeCount, float _scale, float _interval) {
        if(!this.shakeScreen) {
            this.shakeScreen = true;
            this.backToOrigin = false;
            this.deltaTime = 0f;
            this.shakeCount = 0;
            this.targetShakeCount = _targetShakeCount;
            this.scale = _scale;
            this.interval = _interval;
        }
    }

    public void update(Camera camera, float _deltaTime) {
        float v1;
        float v0;
        int v7 = 2;
        float v4 = -1f;
        if(this.shakeScreen) {
            this.deltaTime += _deltaTime;
            if(this.deltaTime > this.interval) {
                this.deltaTime = 0f;
                if(this.rad.nextInt(v7) == 0) {
                    v0 = this.rad.nextFloat() * this.scale;
                }
                else {
                    v0 = this.rad.nextFloat() * this.scale * v4;
                }

                if(this.rad.nextInt(v7) == 0) {
                    v1 = this.rad.nextFloat() * this.scale;
                }
                else {
                    v1 = this.rad.nextFloat() * this.scale * v4;
                }

                if(this.backToOrigin) {
                    this.backToOrigin = false;
                    camera.translate(this.exDeltaX, this.exDeltaY, 0f);
                }
                else {
                    this.backToOrigin = true;
                    camera.translate(v0, v1, 0f);
                    this.exDeltaX = v0 * v4;
                    this.exDeltaY = v1 * v4;
                }

                if(this.shakeCount > this.targetShakeCount) {
                    this.shakeScreen = false;
                    if(this.backToOrigin) {
                        camera.translate(this.exDeltaX, this.exDeltaY, 0f);
                    }
                }

                ++this.shakeCount;
            }
        }
    }
}

