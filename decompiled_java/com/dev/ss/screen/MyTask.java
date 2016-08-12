// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.screen;

import com.dev.ss.Global;
import java.util.TimerTask;

class MyTask extends TimerTask {
    int iHour;
    int iMin;
    int iSec;
    String sMin;
    String sRtn;
    String sSec;

    MyTask() {
        super();
        this.iMin = 0;
        this.iHour = 0;
        this.iSec = 0;
        this.sMin = "";
        this.sSec = "";
        this.sRtn = "";
    }

    public void run() {
        ++Global.iLiveTick;
        if(Global.reserve > 1) {
            --Global.reserve;
        }
        else if(Global.reserve == 1) {
            ++GameScreen.iReserveConfirmTick;
            if(GameScreen.iReserveConfirmTick >= 7) {
                GameScreen.iReserveConfirmTick = -60;
                GameScreen.socketWrite("G110\t" + Global.id + "\t" + "0" + "\t" + String.valueOf(Global.machineNo));
            }
        }

        if(Global.reserve <= 1) {
            GameScreen.lbReserve.setText(this.toTime(0));
        }
        else {
            GameScreen.lbReserve.setText(this.toTime(Global.reserve));
        }

        if(Global.iLiveTick >= 20) {
            Global.iLiveTick = 0;
            if(Global.mode == 3) {
                GameScreen.socketWrite("G000\t" + Global.id + "\t");
            }
        }
    }

    private String toTime(int iTime) {
        int v2 = 10;
        this.iSec = iTime % 60;
        this.iMin = ((int)((((float)iTime)) / 60f));
        this.iMin %= 60;
        this.iHour = ((int)((((float)iTime)) / 3600f));
        if(this.iMin < v2) {
            this.sMin = "0" + this.iMin;
        }
        else {
            this.sMin = String.valueOf(this.iMin);
        }

        if(this.iSec < v2) {
            this.sSec = "0" + this.iSec;
        }
        else {
            this.sSec = String.valueOf(this.iSec);
        }

        if(this.iHour > 0) {
            this.sRtn = String.valueOf(String.valueOf(this.iHour)) + "�ð� " + this.sMin + "�� " + this.sSec + "��";
        }
        else {
            this.sRtn = String.valueOf(this.sMin) + "�� " + this.sSec + "��";
        }

        return this.sRtn;
    }
}

