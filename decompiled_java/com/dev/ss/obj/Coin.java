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

import com.dev.ss.Global;
import com.dev.ss.Res;
import com.dev.ss.Snd;
import com.dev.ss.lib.Ani;
import com.dev.ss.lib.Util;
import com.dev.ss.screen.GameScreen;

public class Coin extends Ani {
    public float ax;
    public float ay;
    public float col_margin_height;
    public float col_margin_width;
    public float half_height;
    public float half_width;
    public int iNowPin;

    public Coin(int _iPartCnt, float _frameDuration, int _iTotalFrameCount) {
        super(_iPartCnt, _frameDuration, _iTotalFrameCount);
        this.ax = 0f;
        this.ay = 0f;
        this.half_width = 0f;
        this.half_height = 0f;
        this.col_margin_height = 0f;
        this.col_margin_width = 0f;
        this.iNowPin = -1;
    }

    private void doCollision(float _deltaTime) {
        float v10 = 1.1f;
        float v9 = 0.9f;
        int v8 = 50;
        float v7 = -1f;
        this.ay -= 0.35f * _deltaTime;
        this.y += this.ay * _deltaTime;
        this.x += this.ax * _deltaTime;
        if(this.x < 0f && this.ax < 0f) {
            this.ax *= v7;
        }

        if(this.x > 1f && this.ax > 0f) {
            this.ax *= v7;
        }

        int v1;
        for(v1 = 0; v1 < 33; ++v1) {
            if(this.iNowPin != v1 && Res.pin[v1].x < this.x + this.width - this.col_margin_width && Res.pin[v1].x + Res.pin[v1].width > this.x + this.col_margin_width && Res.pin[v1].y + Res.pin[v1].height > this.y + this.col_margin_height && Res.pin[v1].y < this.y + this.height - this.col_margin_height) {
                this.iNowPin = v1;
                Res.pin[v1].play(true, "hit", 1);
                this.ay *= v7;
                if(this.ay > 0f) {
                    float v0 = (((float)Util.randomInt(0, 10))) * 0.01f;
                    if(Util.randomTrue(33)) {
                        this.ay *= 0.6f + v0;
                    }
                    else if(Util.randomTrue(v8)) {
                        this.ay *= 0.7f + v0;
                    }
                    else {
                        this.ay *= 0.8f + v0;
                    }
                }

                if(Res.pin[v1].x + Res.pin[v1].half_width >= this.x + this.half_width) {
                    goto label_151;
                }

                if(this.ax >= 0f) {
                    return;
                }

                if(!Util.randomTrue(v8)) {
                    goto label_140;
                }

                this.ax *= v7;
                return;
            label_140:
                if(!Util.randomTrue(70)) {
                    goto label_147;
                }

                this.ax *= v9;
                return;
            label_147:
                this.ax *= v10;
                return;
            label_151:
                if(this.ax <= 0f) {
                    return;
                }

                if(!Util.randomTrue(v8)) {
                    goto label_159;
                }

                this.ax *= v7;
                return;
            label_159:
                if(!Util.randomTrue(70)) {
                    goto label_166;
                }

                this.ax *= v9;
                return;
            label_166:
                this.ax *= v10;
                return;
            }
        }
    }

    public void draw(float _deltaTime) {
        int v12 = 15;
        int v11 = 3;
        if(this.visible) {
            this.deltaTime += _deltaTime;
            this.doCollision(_deltaTime);
            GameScreen.batch.draw(Res.rgCoin[this.getPartFrameNumber()], this.x, this.y, this.width, this.height);
            int v6;
            for(v6 = 0; v6 < 39; ++v6) {
                if(GameScreen.item[v6].x - Util.getWidth(100f) / 2f < this.x && this.x < GameScreen.item[v6].x + Util.getWidth(100f) / 2f && this.y < Util.getTop(500f, 0f) && this.y > Util.getTop(550f, 0f)) {
                    if(GameScreen.item[v6].ani.indexOf("b") >= 0 || GameScreen.item[v6].ani.indexOf("g") >= 0 || GameScreen.item[v6].ani.indexOf("r") >= 0 || GameScreen.item[v6].ani.indexOf("y") >= 0 || GameScreen.item[v6].ani.indexOf("w") >= 0) {
                        GameScreen.socketWrite("G160\tS\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "0");
                        ++Global.spin;
                        ++GameScreen.iSpinAllIdx;
                        if(GameScreen.iSpinAllIdx >= v11) {
                            GameScreen.iSpinAllIdx = 0;
                        }

                        Res.spinAll[GameScreen.iSpinAllIdx].setSize(Util.getWidth(256f), Util.getHeight(128f));
                        Res.spinAll[GameScreen.iSpinAllIdx].setPosition(this.x - Util.getWidth(130f), this.y - Util.getHeight(64f));
                        Res.spinAll[GameScreen.iSpinAllIdx].visible = true;
                        Res.spinAll[GameScreen.iSpinAllIdx].play(true, "s", 1);
                        Snd.sndSpin.play();
                        GameScreen.effectSpin(v12);
                    }
                    else {
                        if(GameScreen.item[v6].ani.indexOf("c") < 0 && GameScreen.item[v6].ani.indexOf("s") < 0) {
                            if(GameScreen.item[v6].ani.indexOf("f") >= 0) {
                                GameScreen.socketWrite("G160\tG\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "100");
                                Global.gift += 100;
                                ++GameScreen.iGiftFishIdx;
                                if(GameScreen.iGiftFishIdx >= v11) {
                                    GameScreen.iGiftFishIdx = 0;
                                }

                                Res.gfFish[GameScreen.iGiftFishIdx].setPosition(this.x, this.y);
                                Res.gfFish[GameScreen.iGiftFishIdx].visible = true;
                                Res.gfFish[GameScreen.iGiftFishIdx].play(true, "f", v11);
                                Snd.sndBonus2.play();
                                GameScreen.effectGift(v12);
                                goto label_217;
                            }
                            else {
                                if(GameScreen.item[v6].ani.indexOf("v") < 0) {
                                    goto label_529;
                                }

                                GameScreen.socketWrite("G160\tG\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "50");
                                Global.gift += 50;
                                ++GameScreen.iGiftFishIdx;
                                if(GameScreen.iGiftFishIdx >= v11) {
                                    GameScreen.iGiftFishIdx = 0;
                                }

                                Res.gfFish[GameScreen.iGiftFishIdx].setPosition(this.x, this.y);
                                Res.gfFish[GameScreen.iGiftFishIdx].visible = true;
                                Res.gfFish[GameScreen.iGiftFishIdx].play(true, "v", v11);
                                Snd.sndBonus3.play();
                                GameScreen.effectGift(v12);
                                goto label_217;
                            label_529:
                                if(GameScreen.item[v6].ani.indexOf("x") < 0) {
                                    goto label_721;
                                }

                                if(Util.randomTrue(99)) {
                                    Res.imgPrize.setImage("p1000");
                                    Res.imgPrize.setPosition(GameScreen.item[v6].x - Util.getWidth(150f), Util.getTop(520f, 0f));
                                    Res.imgPrize.show();
                                    GameScreen.socketWrite("G160\tG\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "1000");
                                    Global.gift += 1000;
                                }
                                else if(Util.randomTrue(99)) {
                                    Res.imgPrize.setImage("p2000");
                                    Res.imgPrize.setPosition(GameScreen.item[v6].x - Util.getWidth(150f), Util.getTop(520f, 0f));
                                    Res.imgPrize.show();
                                    GameScreen.socketWrite("G160\tG\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "2000");
                                    Global.gift += 2000;
                                }
                                else {
                                    Res.imgPrize.setImage("p3000");
                                    Res.imgPrize.setPosition(GameScreen.item[v6].x - Util.getWidth(150f), Util.getTop(520f, 0f));
                                    Res.imgPrize.show();
                                    GameScreen.socketWrite("G160\tG\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "3000");
                                    Global.gift += 3000;
                                }

                                Snd.sndBonus1.play();
                                GameScreen.effectGift(v12);
                                goto label_217;
                            label_721:
                                GameScreen.exitApp("?????????? ?????? ?? ????????(BET1).\n?????????? ??????????.");
                                goto label_217;
                            }
                        }

                        if(GameScreen.item[v6].ani.indexOf("c") < 0) {
                            goto label_338;
                        }

                        GameScreen.socketWrite("G160\tD\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "0");
                        ++GameScreen.iSpinAllIdx;
                        if(GameScreen.iSpinAllIdx >= v11) {
                            GameScreen.iSpinAllIdx = 0;
                        }

                        Res.spinAll[GameScreen.iSpinAllIdx].setSize(Util.getWidth(256f), Util.getHeight(256f));
                        Res.spinAll[GameScreen.iSpinAllIdx].setPosition(this.x - Util.getWidth(128f), this.y - Util.getHeight(64f));
                        Res.spinAll[GameScreen.iSpinAllIdx].visible = true;
                        Res.spinAll[GameScreen.iSpinAllIdx].play(true, "k", 1);
                        Snd.sndSoojo.play();
                        goto label_217;
                    label_338:
                        GameScreen.socketWrite("G160\tF\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "0");
                        Snd.sndBlank.play();
                    }

                label_217:
                    --Global.betWait;
                    if(Global.betWait < 0 || Global.bet < 0) {
                        GameScreen.exitApp("?????????? ?????? ?? ????????(BET).\n?????????? ??????????.");
                    }

                    GameScreen.item[v6].play(true, String.valueOf(GameScreen.item[v6].ani) + GameScreen.item[v6].ani, 1);
                    this.visible = false;
                    GameScreen.updateLabel();
                    break;
                }
            }

            if(this.y >= Util.getTop(600f, 0f)) {
                return;
            }

            if(!this.visible) {
                return;
            }

            this.visible = false;
            GameScreen.socketWrite("G160\tX\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(Global.gift) + "\t" + String.valueOf(Global.spin) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait) + "\t" + "0");
            --Global.betWait;
            if(Global.betWait >= 0) {
                return;
            }

            GameScreen.exitApp("?????????? ?????? ?? ????????(BET2).\n?????????? ??????????.");
        }
    }
}

