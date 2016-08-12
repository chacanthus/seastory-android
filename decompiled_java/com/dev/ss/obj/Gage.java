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
import com.dev.ss.lib.Img;
import com.dev.ss.lib.Util;
import com.dev.ss.screen.GameScreen;

public class Gage extends Img {
    public Gage() {
        super();
    }

    public void draw() {
        GameScreen.batch.draw(Res.rgGage[this.nowIdx], this.x, this.y, this.width, this.height);
    }

    public void setImage(String _name) {
        float v1 = 5f;
        this.nowIdx = this.getImageIdx(_name);
        if(this.nowIdx < 0) {
            this.nowIdx = 0;
        }

        if("1".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(8f));
        }
        else if("2".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(12f));
        }
        else if("3".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(16f));
        }
        else if("4".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(20f));
        }
        else if("5".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(24f));
        }
        else if("6".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(28f));
        }
        else if("7".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(32f));
        }
        else if("8".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(36f));
        }
        else if("9".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(40f));
        }
        else if("A".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(44f));
        }
        else if("B".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(48f));
        }
        else if("C".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(52f));
        }
        else if("D".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(56f));
        }
        else if("E".equals(_name)) {
            this.setSize(Util.getWidth(v1), Util.getHeight(60f));
        }
        else {
            this.setSize(Util.getWidth(v1), Util.getHeight(4f));
        }
    }
}

