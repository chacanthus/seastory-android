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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dev.ss.screen.GameScreen;

public class Ani {
    public static final int LOOP = 1;
    public static final int STOP;
    public boolean bHideWhenStop;
    protected boolean bPartPlay;
    public float deltaTime;
    protected float frameDuration;
    protected int frameNumber;
    public float height;
    protected int iCurrentLoopCount;
    protected int iIdx;
    protected int iPartIdx;
    public int iStopFrame;
    protected int iTargetLoopCount;
    protected int iTotalFrameCount;
    public String name;
    protected float[] partFrameDuration;
    protected int[][] partFrames;
    protected String[] partName;
    protected int[] partStopFrame;
    protected float[] partTotalDuration;
    protected int playMode;
    private Rectangle rect;
    protected TextureRegion[] textureRegionArray;
    protected float totalDuration;
    public boolean visible;
    public float width;
    public float x;
    public float y;

    public Ani(int _iPartCnt, float _frameDuration, int _iTotalFrameCount) {
        super();
        this.width = 0f;
        this.height = 0f;
        this.x = 0f;
        this.y = 0f;
        this.name = "";
        this.iTargetLoopCount = 1;
        this.iCurrentLoopCount = 0;
        this.deltaTime = 0f;
        this.playMode = 0;
        this.iStopFrame = 0;
        this.visible = true;
        this.bHideWhenStop = false;
        this.bPartPlay = false;
        this.iPartIdx = 0;
        this.rect = new Rectangle();
        this.frameDuration = _frameDuration;
        this.iTotalFrameCount = _iTotalFrameCount;
        this.totalDuration = (((float)_iTotalFrameCount)) * _frameDuration;
        if(_iPartCnt > 0) {
            this.partName = new String[_iPartCnt];
            this.partFrames = new int[_iPartCnt][];
            this.partFrameDuration = new float[_iPartCnt];
            this.partTotalDuration = new float[_iPartCnt];
            this.partStopFrame = new int[_iPartCnt];
        }
    }

    public Ani(int _iPartCnt, float frameDuration, TextureRegion[] _textureRegionArray) {
        super();
        this.width = 0f;
        this.height = 0f;
        this.x = 0f;
        this.y = 0f;
        this.name = "";
        this.iTargetLoopCount = 1;
        this.iCurrentLoopCount = 0;
        this.deltaTime = 0f;
        this.playMode = 0;
        this.iStopFrame = 0;
        this.visible = true;
        this.bHideWhenStop = false;
        this.bPartPlay = false;
        this.iPartIdx = 0;
        this.rect = new Rectangle();
        this.frameDuration = frameDuration;
        this.iTotalFrameCount = _textureRegionArray.length;
        this.totalDuration = (((float)this.iTotalFrameCount)) * frameDuration;
        this.textureRegionArray = _textureRegionArray;
        if(_iPartCnt > 0) {
            this.partName = new String[_iPartCnt];
            this.partFrames = new int[_iPartCnt][];
            this.partFrameDuration = new float[_iPartCnt];
            this.partTotalDuration = new float[_iPartCnt];
            this.partStopFrame = new int[_iPartCnt];
        }
    }

    public void draw(float _deltaTime) {
        this.deltaTime += _deltaTime;
        this.iIdx = 0;
        if(this.bPartPlay) {
            if(this.bHideWhenStop) {
                this.iIdx = this.getPartFrameNumber();
                if((this.visible) && this.iIdx >= 0) {
                    GameScreen.batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
                }
            }
            else if(this.visible) {
                this.iIdx = this.getPartFrameNumber();
                if(this.iIdx >= 0) {
                    GameScreen.batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
                }
            }
        }
        else if(this.bHideWhenStop) {
            this.iIdx = this.getFrameNumber();
            if((this.visible) && this.iIdx >= 0) {
                GameScreen.batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
            }
        }
        else if(this.visible) {
            this.iIdx = this.getFrameNumber();
            if(this.iIdx >= 0) {
                GameScreen.batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
            }
        }
    }

    public void draw(SpriteBatch batch, float _deltaTime) {
        this.deltaTime += _deltaTime;
        this.iIdx = 0;
        if(this.bPartPlay) {
            if(this.bHideWhenStop) {
                this.iIdx = this.getPartFrameNumber();
                if(this.visible) {
                    batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
                }
            }
            else if(this.visible) {
                batch.draw(this.textureRegionArray[this.getPartFrameNumber()], this.x, this.y, this.width, this.height);
            }
        }
        else if(this.bHideWhenStop) {
            this.iIdx = this.getFrameNumber();
            if(this.visible) {
                batch.draw(this.textureRegionArray[this.iIdx], this.x, this.y, this.width, this.height);
            }
        }
        else if(this.visible) {
            batch.draw(this.textureRegionArray[this.getFrameNumber()], this.x, this.y, this.width, this.height);
        }
    }

    public Rectangle getBoundingRectangle() {
        this.rect.x = this.x;
        this.rect.y = this.y;
        this.rect.width = this.width;
        this.rect.height = this.height;
        return this.rect;
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

    protected int getPartFrameNumber() {
        if(this.iPartIdx < 0) {
            this.iPartIdx = 0;
        }

        this.frameNumber = ((int)(this.deltaTime / this.partFrameDuration[this.iPartIdx]));
        if(this.playMode == 0) {
            this.frameNumber = this.partStopFrame[this.iPartIdx];
            if(this.bHideWhenStop) {
                this.visible = false;
            }
        }
        else {
            this.frameNumber = this.partFrames[this.iPartIdx][this.frameNumber % this.partFrames[this.iPartIdx].length];
        }

        this.iCurrentLoopCount = ((int)(this.deltaTime / this.partTotalDuration[this.iPartIdx]));
        if(this.iCurrentLoopCount >= this.iTargetLoopCount) {
            this.playMode = 0;
            this.frameNumber = this.partStopFrame[this.iPartIdx];
            if(this.bHideWhenStop) {
                this.visible = false;
            }
        }

        return this.frameNumber;
    }

    protected int getPartIdx(String _name) {
        int v1 = -1;
        int v0 = 0;
        while(v0 < this.partName.length) {
            if(_name.equals(this.partName[v0])) {
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
        this.name = "";
        this.bPartPlay = false;
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

    public void play(boolean bPlay, String _name) {
        this.name = _name;
        this.iPartIdx = this.getPartIdx(_name);
        if(this.iPartIdx < 0) {
            Util.sysout("ERROR", "Ani->play", "Name not found.", _name);
        }
        else {
            this.bPartPlay = true;
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

    public void play(boolean bPlay, String _name, int _iTargetLoopCount) {
        this.name = _name;
        this.iPartIdx = this.getPartIdx(_name);
        if(this.iPartIdx < 0) {
            Util.sysout("ERROR", "Ani->play", "Name not found.", _name);
        }
        else {
            this.bPartPlay = true;
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

    public void play(boolean bPlay, int _iTargetLoopCount) {
        this.name = "";
        this.bPartPlay = false;
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

    public void setFrameDuration(float _frameDuration) {
        this.frameDuration = _frameDuration;
        this.totalDuration = (((float)this.iTotalFrameCount)) * _frameDuration;
    }

    public void setPart(int _iPartIdx, String _name, int _stopFrame, float _frameDuration, int[] _frame) {
        this.partName[_iPartIdx] = _name;
        this.partFrames[_iPartIdx] = new int[_frame.length];
        int v0;
        for(v0 = 0; v0 < _frame.length; ++v0) {
            this.partFrames[_iPartIdx][v0] = _frame[v0];
        }

        this.partFrameDuration[_iPartIdx] = _frameDuration;
        this.partTotalDuration[_iPartIdx] = (((float)_frame.length)) * _frameDuration;
        this.partStopFrame[_iPartIdx] = _stopFrame;
    }

    public void setPartFrameDuration(float _frameDuration) {
        this.partTotalDuration[this.iPartIdx] = (((float)this.partFrames[this.iPartIdx].length)) * _frameDuration;
        this.partFrameDuration[this.iPartIdx] = _frameDuration;
    }

    public void setPosition(float _x, float _y) {
        this.x = _x;
        this.y = _y;
    }

    public void setSize(float _width, float _height) {
        this.width = _width;
        this.height = _height;
    }
}

