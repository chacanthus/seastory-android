// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression.lzma;

public class Base {
    public static final int kAlignMask = 15;
    public static final int kAlignTableSize = 16;
    public static final int kDicLogSizeMin = 0;
    public static final int kEndPosModelIndex = 14;
    public static final int kMatchMaxLen = 273;
    public static final int kMatchMinLen = 2;
    public static final int kNumAlignBits = 4;
    public static final int kNumFullDistances = 128;
    public static final int kNumHighLenBits = 8;
    public static final int kNumLenSymbols = 272;
    public static final int kNumLenToPosStates = 4;
    public static final int kNumLenToPosStatesBits = 2;
    public static final int kNumLitContextBitsMax = 8;
    public static final int kNumLitPosStatesBitsEncodingMax = 4;
    public static final int kNumLowLenBits = 3;
    public static final int kNumLowLenSymbols = 8;
    public static final int kNumMidLenBits = 3;
    public static final int kNumMidLenSymbols = 8;
    public static final int kNumPosModels = 10;
    public static final int kNumPosSlotBits = 6;
    public static final int kNumPosStatesBitsEncodingMax = 4;
    public static final int kNumPosStatesBitsMax = 4;
    public static final int kNumPosStatesEncodingMax = 16;
    public static final int kNumPosStatesMax = 16;
    public static final int kNumRepDistances = 4;
    public static final int kNumStates = 12;
    public static final int kStartPosModelIndex = 4;

    public Base() {
        super();
    }

    public static final int GetLenToPosState(int len) {
        len += -2;
        if(len >= 4) {
            len = 3;
        }

        return len;
    }

    public static final int StateInit() {
        return 0;
    }

    public static final boolean StateIsCharState(int index) {
        boolean v0;
        if(index < 7) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public static final int StateUpdateChar(int index) {
        int v0;
        if(index < 4) {
            v0 = 0;
        }
        else if(index < 10) {
            v0 = index - 3;
        }
        else {
            v0 = index - 6;
        }

        return v0;
    }

    public static final int StateUpdateMatch(int index) {
        int v0 = 7;
        if(index >= v0) {
            v0 = 10;
        }

        return v0;
    }

    public static final int StateUpdateRep(int index) {
        int v0;
        if(index < 7) {
            v0 = 8;
        }
        else {
            v0 = 11;
        }

        return v0;
    }

    public static final int StateUpdateShortRep(int index) {
        int v0;
        if(index < 7) {
            v0 = 9;
        }
        else {
            v0 = 11;
        }

        return v0;
    }
}

