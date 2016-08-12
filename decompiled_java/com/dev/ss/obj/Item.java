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

public class Item {
    private static final int LOOP = 1;
    private static final int STOP;
    public String ani;
    public static String band;
    private float deltaTime;
    private int frameNumber;
    private float half_height;
    private float half_width;
    public float height;
    private int iCurrentLoopCount;
    private int iPartIdx;
    private int iTargetLoopCount;
    public int idx;
    public int no;
    private static float[] partFrameDuration;
    private static int[][] partFrames;
    private static String[] partName;
    private static int[] partStopFrame;
    private static float[] partTotalDuration;
    private int playMode;
    public boolean visible;
    public float width;
    public float x;
    public float y;

    static  {
        Item.band = "gycbwvrsbfgcyxwrsybfgcyrsvrcbfgycwrvsbf";
    }

    public Item(int _iPartCnt, float _frameDuration, int _iTotalFrameCount, int _no, int _idx) {
        super();
        this.x = 0f;
        this.y = 0f;
        this.visible = true;
        this.ani = "";
        this.width = 0f;
        this.height = 0f;
        this.half_width = 0f;
        this.half_height = 0f;
        this.iTargetLoopCount = 1;
        this.iCurrentLoopCount = 0;
        this.deltaTime = 0f;
        this.playMode = 0;
        this.iPartIdx = 0;
        this.no = _no;
        this.ani = this.getDefaultAni();
        this.idx = _idx;
        if(_iPartCnt > 0) {
            Item.partName = new String[_iPartCnt];
            Item.partFrames = new int[_iPartCnt][];
            Item.partFrameDuration = new float[_iPartCnt];
            Item.partTotalDuration = new float[_iPartCnt];
            Item.partStopFrame = new int[_iPartCnt];
        }
    }

    public void draw(float _deltaTime) {
        this.deltaTime += _deltaTime;
        if(this.visible) {
            GameScreen.batch.draw(Res.rgItem[this.getPartFrameNumber()], this.x - this.half_width, this.y - this.half_height, this.width, this.height);
        }
    }

    private String getDefaultAni() {
        this.no %= 39;
        if(this.no < 0) {
            this.no = 39 - this.no * -1;
        }

        if(this.no == 0) {
            this.no = 39;
        }

        return Item.band.substring(this.no - 1, this.no);
    }

    protected int getPartFrameNumber() {
        if(this.iPartIdx < 0) {
            this.iPartIdx = 0;
        }

        this.frameNumber = ((int)(this.deltaTime / Item.partFrameDuration[this.iPartIdx]));
        if(this.playMode == 0) {
            this.frameNumber = Item.partStopFrame[this.iPartIdx];
        }
        else {
            this.frameNumber = Item.partFrames[this.iPartIdx][this.frameNumber % Item.partFrames[this.iPartIdx].length];
        }

        this.iCurrentLoopCount = ((int)(this.deltaTime / Item.partTotalDuration[this.iPartIdx]));
        if(this.iCurrentLoopCount >= this.iTargetLoopCount) {
            this.frameNumber = Item.partStopFrame[this.iPartIdx];
            this.play(true, this.ani.substring(0, 1));
        }

        return this.frameNumber;
    }

    private int getPartIdx(String _name) {
        int v1 = -1;
        int v0 = 0;
        while(v0 < Item.partName.length) {
            if(_name.equals(Item.partName[v0])) {
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
            }
            else {
                this.playMode = 0;
                this.iTargetLoopCount = 0;
            }
        }
    }

    public void play(boolean bPlay) {
        this.play(true, this.ani);
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
            }
            else {
                this.playMode = 0;
                this.iTargetLoopCount = 0;
            }
        }
    }

    public void setNo(int _no) {
        _no %= 39;
        if(_no < 0) {
            _no = 39 - _no * -1;
        }

        if(_no == 0) {
            _no = 39;
        }

        this.no = _no;
        this.ani = this.getDefaultAni();
        this.play(true, this.ani);
    }

    public void setPart(int _iPartIdx, String _name, int _stopFrame, float _frameDuration, int[] _frame) {
        Item.partName[_iPartIdx] = _name;
        Item.partFrames[_iPartIdx] = new int[_frame.length];
        int v0;
        for(v0 = 0; v0 < _frame.length; ++v0) {
            Item.partFrames[_iPartIdx][v0] = _frame[v0];
        }

        Item.partFrameDuration[_iPartIdx] = _frameDuration;
        Item.partTotalDuration[_iPartIdx] = (((float)_frame.length)) * _frameDuration;
        Item.partStopFrame[_iPartIdx] = _stopFrame;
    }

    public void setPartFrameDuration(float _frameDuration) {
        Item.partTotalDuration[this.iPartIdx] = (((float)Item.partFrames[this.iPartIdx].length)) * _frameDuration;
        Item.partFrameDuration[this.iPartIdx] = _frameDuration;
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
    }
}

