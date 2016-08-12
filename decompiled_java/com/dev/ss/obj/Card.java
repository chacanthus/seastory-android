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
import com.dev.ss.lib.Util;
import com.dev.ss.screen.GameScreen;

public class Card {
    private static final int LOOP = 1;
    private static final int STOP;
    public String ani;
    private boolean bStretch;
    private static String[] band;
    public int bandPosNo;
    private float big_half_height;
    private float big_half_width;
    private float big_height;
    private float big_width;
    public int containerIdx;
    private float deltaTime;
    private float fStretch;
    private int frameNumber;
    private float half_height;
    private float half_width;
    private float height;
    private int iCurrentLoopCount;
    private int iPartIdx;
    private int iTargetLoopCount;
    public float mileage;
    private static float[] partFrameDuration;
    private static int[][] partFrames;
    private static String[] partName;
    private static int[] partStopFrame;
    private static float[] partTotalDuration;
    private int playMode;
    public int reel;
    public float startY;
    public float targetY;
    public boolean visible;
    private float width;
    public float x;
    public float y;

    static  {
        String[] v0 = new String[4];
        v0[0] = "gfofhjbnstcmhgofso";
        v0[1] = "nmjcbmftsogtcfgnho";
        v0[2] = "ctjgfbhnomsghfomto";
        v0[3] = "chfnfmgschgtojfmob";
        Card.band = v0;
    }

    public Card(int _iPartCnt, float _frameDuration, int _iTotalFrameCount, int _reel, int _bandPosNo, int _containerIdx) {
        super();
        this.x = 0f;
        this.y = 0f;
        this.targetY = 0f;
        this.startY = 0f;
        this.mileage = 0f;
        this.visible = true;
        this.ani = "";
        this.fStretch = 0f;
        this.bStretch = false;
        this.width = 0f;
        this.height = 0f;
        this.half_width = 0f;
        this.half_height = 0f;
        this.big_width = 0f;
        this.big_height = 0f;
        this.big_half_width = 0f;
        this.big_half_height = 0f;
        this.iTargetLoopCount = 1;
        this.iCurrentLoopCount = 0;
        this.deltaTime = 0f;
        this.playMode = 0;
        this.iPartIdx = 0;
        this.reel = _reel;
        this.bandPosNo = _bandPosNo;
        this.ani = this.getDefaultAni();
        this.containerIdx = _containerIdx;
        if(_iPartCnt > 0) {
            Card.partName = new String[_iPartCnt];
            Card.partFrames = new int[_iPartCnt][];
            Card.partFrameDuration = new float[_iPartCnt];
            Card.partTotalDuration = new float[_iPartCnt];
            Card.partStopFrame = new int[_iPartCnt];
        }
    }

    public void draw(float _deltaTime) {
        float v7 = 2f;
        this.deltaTime += _deltaTime;
        if(this.visible) {
            int v6 = this.getPartFrameNumber();
            if(this.bStretch) {
                this.fStretch += _deltaTime;
                this.big_width = ((float)((((double)(this.width + Util.getWidth(10f)))) + Math.abs(Math.cos(((double)(this.fStretch * v7))) * (((double)Util.getWidth(15f))))));
                this.big_height = this.big_width;
                this.big_half_width = this.big_width / v7;
                this.big_half_height = this.big_half_width;
                GameScreen.batch.draw(Res.rgCard[v6], this.x - this.big_half_width, this.y - this.big_half_height, this.big_width, this.big_height);
            }
            else {
                GameScreen.batch.draw(Res.rgCard[v6], this.x - this.half_width, this.y - this.half_height, this.width, this.height);
            }
        }
    }

    private String getDefaultAni() {
        this.bandPosNo %= 18;
        if(this.bandPosNo < 0) {
            this.bandPosNo = 18 - this.bandPosNo * -1;
        }

        if(this.bandPosNo == 0) {
            this.bandPosNo = 18;
        }

        return Card.band[this.reel].substring(this.bandPosNo - 1, this.bandPosNo);
    }

    protected int getPartFrameNumber() {
        if(this.iPartIdx < 0) {
            this.iPartIdx = 0;
        }

        this.frameNumber = ((int)(this.deltaTime / Card.partFrameDuration[this.iPartIdx]));
        if(this.playMode == 0) {
            this.frameNumber = Card.partStopFrame[this.iPartIdx];
        }
        else {
            this.frameNumber = Card.partFrames[this.iPartIdx][this.frameNumber % Card.partFrames[this.iPartIdx].length];
        }

        this.iCurrentLoopCount = ((int)(this.deltaTime / Card.partTotalDuration[this.iPartIdx]));
        if(this.iCurrentLoopCount >= this.iTargetLoopCount) {
            this.playMode = 0;
            this.frameNumber = Card.partStopFrame[this.iPartIdx];
        }

        return this.frameNumber;
    }

    private int getPartIdx(String _name) {
        int v1 = -1;
        int v0 = 0;
        while(v0 < Card.partName.length) {
            if(_name.equals(Card.partName[v0])) {
                v1 = v0;
            }
            else {
                ++v0;
                continue;
            }

            break;
        }

        return v1;
    }

    public void play(boolean bPlay) {
        this.play(true, this.ani);
    }

    public void play(boolean bPlay, String _name) {
        this.iPartIdx = this.getPartIdx(_name);
        if(this.iPartIdx < 0) {
            Util.sysout("ERROR", "Ani->play", "Name not found.", _name);
        }
        else {
            this.deltaTime = 0f;
            this.iCurrentLoopCount = 0;
            if(bPlay) {
                this.playMode = 1;
                this.iTargetLoopCount = 2147483647;
                GameScreen.frontCardContainerIdx[this.reel] = this.containerIdx;
            }
            else {
                this.playMode = 0;
                this.iTargetLoopCount = 0;
            }

            if(this.ani.length() > 1) {
                this.bStretch = true;
            }
            else {
                this.bStretch = false;
            }

            this.fStretch = 0f;
        }
    }

    public void play(boolean bPlay, String _name, int _iTargetLoopCount) {
        this.iPartIdx = this.getPartIdx(_name);
        if(this.iPartIdx < 0) {
            Util.sysout("ERROR", "Ani->play", "Name not found.", _name);
        }
        else {
            this.deltaTime = 0f;
            this.iCurrentLoopCount = 0;
            if(bPlay) {
                this.playMode = 1;
                this.iTargetLoopCount = _iTargetLoopCount;
                GameScreen.frontCardContainerIdx[this.reel] = this.containerIdx;
            }
            else {
                this.playMode = 0;
                this.iTargetLoopCount = 0;
            }

            if(this.ani.length() > 1) {
                this.bStretch = true;
            }
            else {
                this.bStretch = false;
            }

            this.fStretch = 0f;
        }
    }

    public void setNo(int _bandPosNo) {
        _bandPosNo %= 18;
        if(_bandPosNo < 0) {
            _bandPosNo = 18 - _bandPosNo * -1;
        }

        if(_bandPosNo == 0) {
            _bandPosNo = 18;
        }

        this.bandPosNo = _bandPosNo;
        this.ani = this.getDefaultAni();
        this.play(true, this.ani);
    }

    public void setPart(int _iPartIdx, String _name, int _stopFrame, float _frameDuration, int[] _frame) {
        Card.partName[_iPartIdx] = _name;
        Card.partFrames[_iPartIdx] = new int[_frame.length];
        int v0;
        for(v0 = 0; v0 < _frame.length; ++v0) {
            Card.partFrames[_iPartIdx][v0] = _frame[v0];
        }

        Card.partFrameDuration[_iPartIdx] = _frameDuration;
        Card.partTotalDuration[_iPartIdx] = (((float)_frame.length)) * _frameDuration;
        Card.partStopFrame[_iPartIdx] = _stopFrame;
    }

    public void setPartFrameDuration(float _frameDuration) {
        Card.partTotalDuration[this.iPartIdx] = (((float)Card.partFrames[this.iPartIdx].length)) * _frameDuration;
        Card.partFrameDuration[this.iPartIdx] = _frameDuration;
    }

    public void setPosition(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }

    public void setSize(float _width, float _height) {
        this.width = _width;
        this.height = _height;
        this.half_width = this.width / 2f;
        this.half_height = this.height / 2f;
        this.big_width = _width;
        this.big_height = _height;
        this.big_half_width = this.big_width / 2f;
        this.big_half_height = this.big_height / 2f;
    }
}

