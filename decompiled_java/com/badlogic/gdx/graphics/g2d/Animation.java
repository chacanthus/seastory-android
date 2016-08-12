// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Animation {
    public enum PlayMode {
        public static final enum PlayMode LOOP_PINGPONG;
        public static final enum PlayMode LOOP_RANDOM;

        static  {
            PlayMode.NORMAL = new PlayMode("NORMAL", 0);
            PlayMode.REVERSED = new PlayMode("REVERSED", 1);
            PlayMode.LOOP = new PlayMode("LOOP", 2);
            PlayMode.LOOP_REVERSED = new PlayMode("LOOP_REVERSED", 3);
            PlayMode.LOOP_PINGPONG = new PlayMode("LOOP_PINGPONG", 4);
            PlayMode.LOOP_RANDOM = new PlayMode("LOOP_RANDOM", 5);
            PlayMode[] v0 = new PlayMode[6];
            v0[0] = PlayMode.NORMAL;
            v0[1] = PlayMode.REVERSED;
            v0[2] = PlayMode.LOOP;
            v0[3] = PlayMode.LOOP_REVERSED;
            v0[4] = PlayMode.LOOP_PINGPONG;
            v0[5] = PlayMode.LOOP_RANDOM;
            PlayMode.$VALUES = v0;
        }

        private PlayMode(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static PlayMode valueOf(String name) {
            return Enum.valueOf(PlayMode.class, name);
        }

        public static PlayMode[] values() {
            return PlayMode.$VALUES.clone();
        }
    }

    private float animationDuration;
    private float frameDuration;
    final TextureRegion[] keyFrames;
    private int lastFrameNumber;
    private float lastStateTime;
    private PlayMode playMode;

    public Animation(float frameDuration, Array arg6) {
        super();
        this.playMode = PlayMode.NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = (((float)arg6.size)) * frameDuration;
        this.keyFrames = new TextureRegion[arg6.size];
        int v0 = 0;
        int v1 = arg6.size;
        while(v0 < v1) {
            this.keyFrames[v0] = arg6.get(v0);
            ++v0;
        }

        this.playMode = PlayMode.NORMAL;
    }

    public Animation(float frameDuration, Array arg6, PlayMode playMode) {
        super();
        this.playMode = PlayMode.NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = (((float)arg6.size)) * frameDuration;
        this.keyFrames = new TextureRegion[arg6.size];
        int v0 = 0;
        int v1 = arg6.size;
        while(v0 < v1) {
            this.keyFrames[v0] = arg6.get(v0);
            ++v0;
        }

        this.playMode = playMode;
    }

    public Animation(float frameDuration, TextureRegion[] keyFrames) {
        super();
        this.playMode = PlayMode.NORMAL;
        this.frameDuration = frameDuration;
        this.animationDuration = (((float)keyFrames.length)) * frameDuration;
        this.keyFrames = keyFrames;
        this.playMode = PlayMode.NORMAL;
    }

    public float getAnimationDuration() {
        return this.animationDuration;
    }

    public float getFrameDuration() {
        return this.frameDuration;
    }

    public TextureRegion getKeyFrame(float stateTime) {
        return this.keyFrames[this.getKeyFrameIndex(stateTime)];
    }

    public TextureRegion getKeyFrame(float stateTime, boolean looping) {
        PlayMode v1 = this.playMode;
        if(looping) {
            if(this.playMode != PlayMode.NORMAL && this.playMode != PlayMode.REVERSED) {
                goto label_19;
            }

            if(this.playMode != PlayMode.NORMAL) {
                goto label_16;
            }

            this.playMode = PlayMode.LOOP;
            goto label_13;
        label_16:
            this.playMode = PlayMode.LOOP_REVERSED;
        }
        else {
        label_19:
            if(looping) {
                goto label_13;
            }

            if(this.playMode == PlayMode.NORMAL) {
                goto label_13;
            }

            if(this.playMode == PlayMode.REVERSED) {
                goto label_13;
            }

            if(this.playMode != PlayMode.LOOP_REVERSED) {
                goto label_32;
            }

            this.playMode = PlayMode.REVERSED;
            goto label_13;
        label_32:
            this.playMode = PlayMode.LOOP;
        }

    label_13:
        TextureRegion v0 = this.getKeyFrame(stateTime);
        this.playMode = v1;
        return v0;
    }

    public int getKeyFrameIndex(float stateTime) {
        int v0;
        if(this.keyFrames.length == 1) {
            v0 = 0;
        }
        else {
            v0 = ((int)(stateTime / this.frameDuration));
            switch(com.badlogic.gdx.graphics.g2d.Animation$1.$SwitchMap$com$badlogic$gdx$graphics$g2d$Animation$PlayMode[this.playMode.ordinal()]) {
                case 1: {
                    v0 = Math.min(this.keyFrames.length - 1, v0);
                    break;
                }
                case 2: {
                    v0 %= this.keyFrames.length;
                    break;
                }
                case 3: {
                    v0 %= this.keyFrames.length * 2 - 2;
                    if(v0 >= this.keyFrames.length) {
                        v0 = this.keyFrames.length - 2 - (v0 - this.keyFrames.length);
                        goto label_15;
                    }
                    else {
                        goto label_15;
                    }
                }
                case 4: {
                    if((((int)(this.lastStateTime / this.frameDuration))) != v0) {
                        v0 = MathUtils.random(this.keyFrames.length - 1);
                        goto label_15;
                    }
                    else {
                        v0 = this.lastFrameNumber;
                        goto label_15;
                    }
                }
                case 5: {
                    v0 = Math.max(this.keyFrames.length - v0 - 1, 0);
                    break;
                }
                case 6: {
                    v0 = this.keyFrames.length - v0 % this.keyFrames.length - 1;
                    break;
                }
            }

        label_15:
            this.lastFrameNumber = v0;
            this.lastStateTime = stateTime;
        }

        return v0;
    }

    public TextureRegion[] getKeyFrames() {
        return this.keyFrames;
    }

    public PlayMode getPlayMode() {
        return this.playMode;
    }

    public boolean isAnimationFinished(float stateTime) {
        boolean v1;
        if(this.keyFrames.length - 1 < (((int)(stateTime / this.frameDuration)))) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        return v1;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
        this.animationDuration = (((float)this.keyFrames.length)) * frameDuration;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }
}

