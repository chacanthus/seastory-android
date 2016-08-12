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

import com.badlogic.gdx.utils.compression.ICodeProgress;
import com.badlogic.gdx.utils.compression.lz.BinTree;
import com.badlogic.gdx.utils.compression.rangecoder.BitTreeEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Encoder {
    class LenEncoder {
        short[] _choice;
        BitTreeEncoder _highCoder;
        BitTreeEncoder[] _lowCoder;
        BitTreeEncoder[] _midCoder;

        public LenEncoder(Encoder arg6) {
            int v4 = 3;
            int v3 = 16;
            Encoder.this = arg6;
            super();
            this._choice = new short[2];
            this._lowCoder = new BitTreeEncoder[v3];
            this._midCoder = new BitTreeEncoder[v3];
            this._highCoder = new BitTreeEncoder(8);
            int v0;
            for(v0 = 0; v0 < v3; ++v0) {
                this._lowCoder[v0] = new BitTreeEncoder(v4);
                this._midCoder[v0] = new BitTreeEncoder(v4);
            }
        }

        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
            int v3 = 8;
            if(symbol < v3) {
                rangeEncoder.Encode(this._choice, 0, 0);
                this._lowCoder[posState].Encode(rangeEncoder, symbol);
            }
            else {
                symbol += -8;
                rangeEncoder.Encode(this._choice, 0, 1);
                if(symbol < v3) {
                    rangeEncoder.Encode(this._choice, 1, 0);
                    this._midCoder[posState].Encode(rangeEncoder, symbol);
                }
                else {
                    rangeEncoder.Encode(this._choice, 1, 1);
                    this._highCoder.Encode(rangeEncoder, symbol - 8);
                }
            }
        }

        public void Init(int numPosStates) {
            com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._choice);
            int v0;
            for(v0 = 0; v0 < numPosStates; ++v0) {
                this._lowCoder[v0].Init();
                this._midCoder[v0].Init();
            }

            this._highCoder.Init();
        }

        public void SetPrices(int posState, int numSymbols, int[] prices, int st) {
            int v0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[0]);
            int v1 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[0]);
            int v2 = v1 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._choice[1]);
            int v3 = v1 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._choice[1]);
            int v4 = 0;
            while(true) {
                if(v4 >= 8) {
                    break;
                }
                else if(v4 < numSymbols) {
                    prices[st + v4] = this._lowCoder[posState].GetPrice(v4) + v0;
                    ++v4;
                    continue;
                }

                return;
            }

            while(v4 < 16) {
                if(v4 >= numSymbols) {
                    return;
                }
                else {
                    prices[st + v4] = this._midCoder[posState].GetPrice(v4 - 8) + v2;
                    ++v4;
                    continue;
                }
            }

            while(v4 < numSymbols) {
                prices[st + v4] = this._highCoder.GetPrice(v4 - 16) + v3;
                ++v4;
            }
        }
    }

    class LenPriceTableEncoder extends LenEncoder {
        int[] _counters;
        int[] _prices;
        int _tableSize;

        LenPriceTableEncoder(Encoder arg2) {
            Encoder.this = arg2;
            super(arg2);
            this._prices = new int[4352];
            this._counters = new int[16];
        }

        public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, int symbol, int posState) throws IOException {
            super.Encode(rangeEncoder, symbol, posState);
            int[] v0 = this._counters;
            int v1 = v0[posState] - 1;
            v0[posState] = v1;
            if(v1 == 0) {
                this.UpdateTable(posState);
            }
        }

        public int GetPrice(int symbol, int posState) {
            return this._prices[posState * 272 + symbol];
        }

        public void SetTableSize(int tableSize) {
            this._tableSize = tableSize;
        }

        void UpdateTable(int posState) {
            this.SetPrices(posState, this._tableSize, this._prices, posState * 272);
            this._counters[posState] = this._tableSize;
        }

        public void UpdateTables(int numPosStates) {
            int v0;
            for(v0 = 0; v0 < numPosStates; ++v0) {
                this.UpdateTable(v0);
            }
        }
    }

    class LiteralEncoder {
        class Encoder2 {
            short[] m_Encoders;

            Encoder2(LiteralEncoder arg2) {
                this.this$1 = arg2;
                super();
                this.m_Encoders = new short[768];
            }

            public void Encode(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, byte symbol) throws IOException {
                int v1 = 1;
                int v2;
                for(v2 = 7; v2 >= 0; --v2) {
                    int v0 = symbol >> v2 & 1;
                    rangeEncoder.Encode(this.m_Encoders, v1, v0);
                    v1 = v1 << 1 | v0;
                }
            }

            public void EncodeMatched(com.badlogic.gdx.utils.compression.rangecoder.Encoder rangeEncoder, byte matchByte, byte symbol) throws IOException {
                int v1 = 1;
                int v4 = 1;
                int v2;
                for(v2 = 7; v2 >= 0; --v2) {
                    int v0 = symbol >> v2 & 1;
                    int v5 = v1;
                    if(v4 != 0) {
                        int v3 = matchByte >> v2 & 1;
                        v5 += v3 + 1 << 8;
                        if(v3 == v0) {
                            v4 = 1;
                        }
                        else {
                            v4 = 0;
                        }
                    }

                    rangeEncoder.Encode(this.m_Encoders, v5, v0);
                    v1 = v1 << 1 | v0;
                }
            }

            public int GetPrice(boolean matchMode, byte matchByte, byte symbol) {
                int v0;
                int v4 = 0;
                int v1 = 1;
                int v2 = 7;
                if(matchMode) {
                    while(v2 >= 0) {
                        int v3 = matchByte >> v2 & 1;
                        v0 = symbol >> v2 & 1;
                        v4 += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this.m_Encoders[(v3 + 1 << 8) + v1], v0);
                        v1 = v1 << 1 | v0;
                        if(v3 != v0) {
                            --v2;
                        }
                        else {
                            --v2;
                            continue;
                        }

                        break;
                    }
                }

                while(true) {
                    if(v2 < 0) {
                        break;
                    }

                    v0 = symbol >> v2 & 1;
                    v4 += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this.m_Encoders[v1], v0);
                    v1 = v1 << 1 | v0;
                    --v2;
                }

                return v4;
            }

            public void Init() {
                com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this.m_Encoders);
            }
        }

        Encoder2[] m_Coders;
        int m_NumPosBits;
        int m_NumPrevBits;
        int m_PosMask;

        LiteralEncoder(Encoder arg1) {
            Encoder.this = arg1;
            super();
        }

        public void Create(int numPosBits, int numPrevBits) {
            if(this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
                this.m_NumPosBits = numPosBits;
                this.m_PosMask = (1 << numPosBits) - 1;
                this.m_NumPrevBits = numPrevBits;
                int v1 = 1 << this.m_NumPrevBits + this.m_NumPosBits;
                this.m_Coders = new Encoder2[v1];
                int v0;
                for(v0 = 0; v0 < v1; ++v0) {
                    this.m_Coders[v0] = new Encoder2(this);
                }
            }
        }

        public Encoder2 GetSubCoder(int pos, byte prevByte) {
            return this.m_Coders[((this.m_PosMask & pos) << this.m_NumPrevBits) + ((prevByte & 255) >>> 8 - this.m_NumPrevBits)];
        }

        public void Init() {
            int v1 = 1 << this.m_NumPrevBits + this.m_NumPosBits;
            int v0;
            for(v0 = 0; v0 < v1; ++v0) {
                this.m_Coders[v0].Init();
            }
        }
    }

    class Optimal {
        public int BackPrev;
        public int BackPrev2;
        public int Backs0;
        public int Backs1;
        public int Backs2;
        public int Backs3;
        public int PosPrev;
        public int PosPrev2;
        public boolean Prev1IsChar;
        public boolean Prev2;
        public int Price;
        public int State;

        Optimal(Encoder arg1) {
            Encoder.this = arg1;
            super();
        }

        public boolean IsShortRep() {
            boolean v0;
            if(this.BackPrev == 0) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public void MakeAsChar() {
            this.BackPrev = -1;
            this.Prev1IsChar = false;
        }

        public void MakeAsShortRep() {
            this.BackPrev = 0;
            this.Prev1IsChar = false;
        }
    }

    public static final int EMatchFinderTypeBT2 = 0;
    public static final int EMatchFinderTypeBT4 = 1;
    int _additionalOffset;
    int _alignPriceCount;
    int[] _alignPrices;
    int _dictionarySize;
    int _dictionarySizePrev;
    int _distTableSize;
    int[] _distancesPrices;
    boolean _finished;
    InputStream _inStream;
    short[] _isMatch;
    short[] _isRep;
    short[] _isRep0Long;
    short[] _isRepG0;
    short[] _isRepG1;
    short[] _isRepG2;
    LenPriceTableEncoder _lenEncoder;
    LiteralEncoder _literalEncoder;
    int _longestMatchLength;
    boolean _longestMatchWasFound;
    int[] _matchDistances;
    BinTree _matchFinder;
    int _matchFinderType;
    int _matchPriceCount;
    boolean _needReleaseMFStream;
    int _numDistancePairs;
    int _numFastBytes;
    int _numFastBytesPrev;
    int _numLiteralContextBits;
    int _numLiteralPosStateBits;
    Optimal[] _optimum;
    int _optimumCurrentIndex;
    int _optimumEndIndex;
    BitTreeEncoder _posAlignEncoder;
    short[] _posEncoders;
    BitTreeEncoder[] _posSlotEncoder;
    int[] _posSlotPrices;
    int _posStateBits;
    int _posStateMask;
    byte _previousByte;
    com.badlogic.gdx.utils.compression.rangecoder.Encoder _rangeEncoder;
    int[] _repDistances;
    LenPriceTableEncoder _repMatchLenEncoder;
    int _state;
    boolean _writeEndMark;
    int backRes;
    boolean[] finished;
    static byte[] g_FastPos = null;
    static final int kDefaultDictionaryLogSize = 22;
    static final int kIfinityPrice = 268435455;
    static final int kNumFastBytesDefault = 32;
    public static final int kNumLenSpecSymbols = 16;
    static final int kNumOpts = 4096;
    public static final int kPropSize = 5;
    long nowPos64;
    long[] processedInSize;
    long[] processedOutSize;
    byte[] properties;
    int[] repLens;
    int[] reps;
    int[] tempPrices;

    static  {
        Encoder.g_FastPos = new byte[2048];
        int v3 = 22;
        int v0 = 2;
        Encoder.g_FastPos[0] = 0;
        Encoder.g_FastPos[1] = 1;
        int v4;
        for(v4 = 2; v4 < v3; ++v4) {
            int v2 = 1 << (v4 >> 1) - 1;
            int v1 = 0;
            while(v1 < v2) {
                Encoder.g_FastPos[v0] = ((byte)v4);
                ++v1;
                ++v0;
            }
        }
    }

    public Encoder() {
        int v4 = 4;
        super();
        this._state = Base.StateInit();
        this._repDistances = new int[v4];
        this._optimum = new Optimal[4096];
        this._matchFinder = null;
        this._rangeEncoder = new com.badlogic.gdx.utils.compression.rangecoder.Encoder();
        this._isMatch = new short[192];
        this._isRep = new short[12];
        this._isRepG0 = new short[12];
        this._isRepG1 = new short[12];
        this._isRepG2 = new short[12];
        this._isRep0Long = new short[192];
        this._posSlotEncoder = new BitTreeEncoder[v4];
        this._posEncoders = new short[114];
        this._posAlignEncoder = new BitTreeEncoder(v4);
        this._lenEncoder = new LenPriceTableEncoder(this);
        this._repMatchLenEncoder = new LenPriceTableEncoder(this);
        this._literalEncoder = new LiteralEncoder(this);
        this._matchDistances = new int[548];
        this._numFastBytes = 32;
        this._posSlotPrices = new int[256];
        this._distancesPrices = new int[512];
        this._alignPrices = new int[16];
        this._distTableSize = 44;
        this._posStateBits = 2;
        this._posStateMask = 3;
        this._numLiteralPosStateBits = 0;
        this._numLiteralContextBits = 3;
        this._dictionarySize = 4194304;
        this._dictionarySizePrev = -1;
        this._numFastBytesPrev = -1;
        this._matchFinderType = 1;
        this._writeEndMark = false;
        this._needReleaseMFStream = false;
        this.reps = new int[v4];
        this.repLens = new int[v4];
        this.processedInSize = new long[1];
        this.processedOutSize = new long[1];
        this.finished = new boolean[1];
        this.properties = new byte[5];
        this.tempPrices = new int[128];
        int v0;
        for(v0 = 0; v0 < 4096; ++v0) {
            this._optimum[v0] = new Optimal(this);
        }

        for(v0 = 0; v0 < v4; ++v0) {
            this._posSlotEncoder[v0] = new BitTreeEncoder(6);
        }
    }

    int Backward(int cur) {
        this._optimumEndIndex = cur;
        int v2 = this._optimum[cur].PosPrev;
        int v1 = this._optimum[cur].BackPrev;
        do {
            if(this._optimum[cur].Prev1IsChar) {
                this._optimum[v2].MakeAsChar();
                this._optimum[v2].PosPrev = v2 - 1;
                if(this._optimum[cur].Prev2) {
                    this._optimum[v2 - 1].Prev1IsChar = false;
                    this._optimum[v2 - 1].PosPrev = this._optimum[cur].PosPrev2;
                    this._optimum[v2 - 1].BackPrev = this._optimum[cur].BackPrev2;
                }
            }

            int v3 = v2;
            int v0 = v1;
            v1 = this._optimum[v3].BackPrev;
            v2 = this._optimum[v3].PosPrev;
            this._optimum[v3].BackPrev = v0;
            this._optimum[v3].PosPrev = cur;
            cur = v3;
        }
        while(cur > 0);

        this.backRes = this._optimum[0].BackPrev;
        this._optimumCurrentIndex = this._optimum[0].PosPrev;
        return this._optimumCurrentIndex;
    }

    void BaseInit() {
        this._state = Base.StateInit();
        this._previousByte = 0;
        int v0;
        for(v0 = 0; v0 < 4; ++v0) {
            this._repDistances[v0] = 0;
        }
    }

    boolean ChangePair(int smallDist, int bigDist) {
        boolean v1;
        int v0 = 7;
        if(smallDist >= 33554432 || bigDist < smallDist << v0) {
            v1 = false;
        }
        else {
            v1 = true;
        }

        return v1;
    }

    public void Code(InputStream inStream, OutputStream outStream, long inSize, long outSize, ICodeProgress progress) throws IOException {  // has try-catch handlers
        this._needReleaseMFStream = false;
        try {
            this.SetStreams(inStream, outStream, inSize, outSize);
            while(true) {
            label_3:
                this.CodeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
                if(!this.finished[0]) {
                    break;
                }

                goto label_11;
            }
        }
        catch(Throwable v0) {
            goto label_23;
        }

        if(progress == null) {
            goto label_3;
        }

        try {
            progress.SetProgress(this.processedInSize[0], this.processedOutSize[0]);
            goto label_3;
        }
        catch(Throwable v0) {
            goto label_23;
        }

    label_11:
        this.ReleaseStreams();
        return;
    label_23:
        this.ReleaseStreams();
        throw v0;
    }

    public void CodeOneBlock(long[] inSize, long[] outSize, boolean[] finished) throws IOException {
        int v7;
        byte v6;
        inSize[0] = 0;
        outSize[0] = 0;
        finished[0] = true;
        if(this._inStream != null) {
            this._matchFinder.SetStream(this._inStream);
            this._matchFinder.Init();
            this._needReleaseMFStream = true;
            this._inStream = null;
        }

        if(!this._finished) {
            this._finished = true;
            long v18 = this.nowPos64;
            if(this.nowPos64 == 0) {
                if(this._matchFinder.GetNumAvailableBytes() == 0) {
                    this.Flush(((int)this.nowPos64));
                    return;
                }
                else {
                    this.ReadMatchDistances();
                    this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + ((((int)this.nowPos64)) & this._posStateMask), 0);
                    this._state = Base.StateUpdateChar(this._state);
                    v6 = this._matchFinder.GetIndexByte(-this._additionalOffset);
                    this._literalEncoder.GetSubCoder(((int)this.nowPos64), this._previousByte).Encode(this._rangeEncoder, v6);
                    this._previousByte = v6;
                    --this._additionalOffset;
                    ++this.nowPos64;
                }
            }

            if(this._matchFinder.GetNumAvailableBytes() == 0) {
                this.Flush(((int)this.nowPos64));
                return;
            }

            do {
            label_154:
                int v10 = this.GetOptimum(((int)this.nowPos64));
                int v13 = this.backRes;
                int v16 = (((int)this.nowPos64)) & this._posStateMask;
                int v5 = (this._state << 4) + v16;
                if(v10 != 1 || v13 != -1) {
                    this._rangeEncoder.Encode(this._isMatch, v5, 1);
                    if(v13 < 4) {
                        this._rangeEncoder.Encode(this._isRep, this._state, 1);
                        if(v13 == 0) {
                            this._rangeEncoder.Encode(this._isRepG0, this._state, 0);
                            if(v10 == 1) {
                                this._rangeEncoder.Encode(this._isRep0Long, v5, 0);
                            }
                            else {
                                this._rangeEncoder.Encode(this._isRep0Long, v5, 1);
                            }
                        }
                        else {
                            this._rangeEncoder.Encode(this._isRepG0, this._state, 1);
                            if(v13 == 1) {
                                this._rangeEncoder.Encode(this._isRepG1, this._state, 0);
                            }
                            else {
                                this._rangeEncoder.Encode(this._isRepG1, this._state, 1);
                                this._rangeEncoder.Encode(this._isRepG2, this._state, v13 - 2);
                            }
                        }

                        if(v10 == 1) {
                            this._state = Base.StateUpdateShortRep(this._state);
                        }
                        else {
                            this._repMatchLenEncoder.Encode(this._rangeEncoder, v10 - 2, v16);
                            this._state = Base.StateUpdateRep(this._state);
                        }

                        v7 = this._repDistances[v13];
                        if(v13 == 0) {
                            goto label_489;
                        }

                        int v9;
                        for(v9 = v13; v9 >= 1; --v9) {
                            this._repDistances[v9] = this._repDistances[v9 - 1];
                        }

                        this._repDistances[0] = v7;
                    }
                    else {
                        this._rangeEncoder.Encode(this._isRep, this._state, 0);
                        this._state = Base.StateUpdateMatch(this._state);
                        this._lenEncoder.Encode(this._rangeEncoder, v10 - 2, v16);
                        v13 += -4;
                        int v15 = Encoder.GetPosSlot(v13);
                        this._posSlotEncoder[Base.GetLenToPosState(v10)].Encode(this._rangeEncoder, v15);
                        if(v15 >= 4) {
                            int v8 = (v15 >> 1) - 1;
                            int v4 = (v15 & 1 | 2) << v8;
                            int v14 = v13 - v4;
                            if(v15 < 14) {
                                BitTreeEncoder.ReverseEncode(this._posEncoders, v4 - v15 - 1, this._rangeEncoder, v8, v14);
                            }
                            else {
                                this._rangeEncoder.EncodeDirectBits(v14 >> 4, v8 - 4);
                                this._posAlignEncoder.ReverseEncode(this._rangeEncoder, v14 & 15);
                                ++this._alignPriceCount;
                            }
                        }

                        v7 = v13;
                        for(v9 = 3; v9 >= 1; --v9) {
                            this._repDistances[v9] = this._repDistances[v9 - 1];
                        }

                        this._repDistances[0] = v7;
                        ++this._matchPriceCount;
                    }

                label_489:
                    this._previousByte = this._matchFinder.GetIndexByte(v10 - 1 - this._additionalOffset);
                }
                else {
                    this._rangeEncoder.Encode(this._isMatch, v5, 0);
                    v6 = this._matchFinder.GetIndexByte(-this._additionalOffset);
                    Encoder2 v17 = this._literalEncoder.GetSubCoder(((int)this.nowPos64), this._previousByte);
                    if(!Base.StateIsCharState(this._state)) {
                        v17.EncodeMatched(this._rangeEncoder, this._matchFinder.GetIndexByte(-this._repDistances[0] - 1 - this._additionalOffset), v6);
                    }
                    else {
                        v17.Encode(this._rangeEncoder, v6);
                    }

                    this._previousByte = v6;
                    this._state = Base.StateUpdateChar(this._state);
                }

                this._additionalOffset -= v10;
                this.nowPos64 += ((long)v10);
                if(this._additionalOffset != 0) {
                    goto label_154;
                }

                if(this._matchPriceCount >= 128) {
                    this.FillDistancesPrices();
                }

                if(this._alignPriceCount >= 16) {
                    this.FillAlignPrices();
                }

                inSize[0] = this.nowPos64;
                outSize[0] = this._rangeEncoder.GetProcessedSizeAdd();
                if(this._matchFinder.GetNumAvailableBytes() != 0) {
                    continue;
                }

                this.Flush(((int)this.nowPos64));
                return;
            }
            while(this.nowPos64 - v18 < 4096);

            this._finished = false;
            finished[0] = false;
        }
    }

    void Create() {
        if(this._matchFinder == null) {
            BinTree v0 = new BinTree();
            int v1 = 4;
            if(this._matchFinderType == 0) {
                v1 = 2;
            }

            v0.SetType(v1);
            this._matchFinder = v0;
        }

        this._literalEncoder.Create(this._numLiteralPosStateBits, this._numLiteralContextBits);
        if(this._dictionarySize != this._dictionarySizePrev || this._numFastBytesPrev != this._numFastBytes) {
            this._matchFinder.Create(this._dictionarySize, 4096, this._numFastBytes, 274);
            this._dictionarySizePrev = this._dictionarySize;
            this._numFastBytesPrev = this._numFastBytes;
        }
    }

    void FillAlignPrices() {
        int v0;
        for(v0 = 0; v0 < 16; ++v0) {
            this._alignPrices[v0] = this._posAlignEncoder.ReverseGetPrice(v0);
        }

        this._alignPriceCount = 0;
    }

    void FillDistancesPrices() {
        int v5;
        int v13 = 128;
        int v12 = 4;
        int v3;
        for(v3 = 4; v3 < v13; ++v3) {
            v5 = Encoder.GetPosSlot(v3);
            int v2 = (v5 >> 1) - 1;
            int v0 = (v5 & 1 | 2) << v2;
            this.tempPrices[v3] = BitTreeEncoder.ReverseGetPrice(this._posEncoders, v0 - v5 - 1, v2, v3 - v0);
        }

        int v4;
        for(v4 = 0; v4 < v12; ++v4) {
            BitTreeEncoder v1 = this._posSlotEncoder[v4];
            int v6 = v4 << 6;
            for(v5 = 0; v5 < this._distTableSize; ++v5) {
                this._posSlotPrices[v6 + v5] = v1.GetPrice(v5);
            }

            for(v5 = 14; v5 < this._distTableSize; ++v5) {
                int v9 = v6 + v5;
                this._posSlotPrices[v9] += (v5 >> 1) - 5 << 6;
            }

            int v7 = v4 * 128;
            for(v3 = 0; v3 < v12; ++v3) {
                this._distancesPrices[v7 + v3] = this._posSlotPrices[v6 + v3];
            }

            while(v3 < v13) {
                this._distancesPrices[v7 + v3] = this._posSlotPrices[Encoder.GetPosSlot(v3) + v6] + this.tempPrices[v3];
                ++v3;
            }
        }

        this._matchPriceCount = 0;
    }

    void Flush(int nowPos) throws IOException {
        this.ReleaseMFStream();
        this.WriteEndMarker(this._posStateMask & nowPos);
        this._rangeEncoder.FlushData();
        this._rangeEncoder.FlushStream();
    }

    int GetOptimum(int position) throws IOException {
        int v7;
        int v19;
        int v34;
        int v28;
        int v40;
        int v49;
        int v20;
        int v30;
        int v31;
        int v6;
        int v37;
        int v48;
        int v24;
        Optimal v36;
        int v8;
        int v16;
        int v46;
        boolean v51;
        int v17;
        int v18;
        if(this._optimumEndIndex != this._optimumCurrentIndex) {
            v18 = this._optimum[this._optimumCurrentIndex].PosPrev - this._optimumCurrentIndex;
            this.backRes = this._optimum[this._optimumCurrentIndex].BackPrev;
            this._optimumCurrentIndex = this._optimum[this._optimumCurrentIndex].PosPrev;
        }
        else {
            this._optimumEndIndex = 0;
            this._optimumCurrentIndex = 0;
            if(!this._longestMatchWasFound) {
                v17 = this.ReadMatchDistances();
            }
            else {
                v17 = this._longestMatchLength;
                this._longestMatchWasFound = false;
            }

            int v32 = this._numDistancePairs;
            if(this._matchFinder.GetNumAvailableBytes() + 1 >= 2) {
                goto label_93;
            }

            this.backRes = -1;
            v18 = 1;
            goto label_49;
        label_93:
            int v45 = 0;
            int v13;
            for(v13 = 0; v13 < 4; ++v13) {
                this.reps[v13] = this._repDistances[v13];
                this.repLens[v13] = this._matchFinder.GetMatchLen(-1, this.reps[v13], 273);
                if(this.repLens[v13] > this.repLens[v45]) {
                    v45 = v13;
                }
            }

            if(this.repLens[v45] < this._numFastBytes) {
                goto label_156;
            }

            this.backRes = v45;
            v18 = this.repLens[v45];
            this.MovePos(v18 - 1);
            goto label_49;
        label_156:
            if(v17 < this._numFastBytes) {
                goto label_177;
            }

            this.backRes = this._matchDistances[v32 - 1] + 4;
            this.MovePos(v17 - 1);
            v18 = v17;
            goto label_49;
        label_177:
            byte v11 = this._matchFinder.GetIndexByte(-1);
            byte v22 = this._matchFinder.GetIndexByte(-this._repDistances[0] - 2);
            if(v17 < 2 && v11 != v22 && this.repLens[v45] < 2) {
                this.backRes = -1;
                v18 = 1;
                goto label_49;
            }

            this._optimum[0].State = this._state;
            int v39 = position & this._posStateMask;
            Optimal v52 = this._optimum[1];
            int v53 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(this._state << 4) + v39]);
            Encoder2 v54 = this._literalEncoder.GetSubCoder(position, this._previousByte);
            if(!Base.StateIsCharState(this._state)) {
                v51 = true;
            }
            else {
                v51 = false;
            }

            v52.Price = v54.GetPrice(v51, v22, v11) + v53;
            this._optimum[1].MakeAsChar();
            int v23 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(this._state << 4) + v39]);
            int v44 = v23 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[this._state]);
            if(v22 == v11) {
                v46 = v44 + this.GetRepLen1Price(this._state, v39);
                if(v46 < this._optimum[1].Price) {
                    this._optimum[1].Price = v46;
                    this._optimum[1].MakeAsShortRep();
                }
            }

            if(v17 >= this.repLens[v45]) {
                v16 = v17;
            }
            else {
                v16 = this.repLens[v45];
            }

            if(v16 >= 2) {
                goto label_360;
            }

            this.backRes = this._optimum[1].BackPrev;
            v18 = 1;
            goto label_49;
        label_360:
            this._optimum[1].PosPrev = 0;
            this._optimum[0].Backs0 = this.reps[0];
            this._optimum[0].Backs1 = this.reps[1];
            this._optimum[0].Backs2 = this.reps[2];
            this._optimum[0].Backs3 = this.reps[3];
            int v14;
            for(v14 = v16; true; v14 = v15) {
                int v15 = v14 - 1;
                this._optimum[v14].Price = 268435455;
                if(v15 >= 2) {
                    goto label_1670;
                }

                break;
            label_1670:
            }

            for(v13 = 0; v13 < 4; ++v13) {
                int v43 = this.repLens[v13];
                if(v43 >= 2) {
                    int v41 = v44 + this.GetPureRepPrice(v13, this._state, v39);
                    do {
                        v8 = v41 + this._repMatchLenEncoder.GetPrice(v43 - 2, v39);
                        v36 = this._optimum[v43];
                        if(v8 < v36.Price) {
                            v36.Price = v8;
                            v36.PosPrev = 0;
                            v36.BackPrev = v13;
                            v36.Prev1IsChar = false;
                        }

                        --v43;
                    }
                    while(v43 >= 2);
                }
            }

            int v29 = v23 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep[this._state]);
            if(this.repLens[0] >= 2) {
                v14 = this.repLens[0] + 1;
            }
            else {
                v14 = 2;
            }

            if(v14 <= v17) {
                int v33;
                for(v33 = 0; v14 > this._matchDistances[v33]; v33 += 2) {
                }

                while(true) {
                    int v12 = this._matchDistances[v33 + 1];
                    v8 = v29 + this.GetPosLenPrice(v12, v14, v39);
                    v36 = this._optimum[v14];
                    if(v8 < v36.Price) {
                        v36.Price = v8;
                        v36.PosPrev = 0;
                        v36.BackPrev = v12 + 4;
                        v36.Prev1IsChar = false;
                    }

                    if(v14 == this._matchDistances[v33]) {
                        v33 += 2;
                        if(v33 != v32) {
                            goto label_529;
                        }

                        break;
                    }

                label_529:
                    ++v14;
                }
            }

            int v5 = 0;
            do {
            label_573:
                ++v5;
                if(v5 != v16) {
                    goto label_579;
                }

                goto label_576;
            label_579:
                v24 = this.ReadMatchDistances();
                v32 = this._numDistancePairs;
                if(v24 < this._numFastBytes) {
                    goto label_599;
                }

                this._longestMatchLength = v24;
                this._longestMatchWasFound = true;
                v18 = this.Backward(v5);
                goto label_49;
            label_599:
                ++position;
                int v38 = this._optimum[v5].PosPrev;
                if(this._optimum[v5].Prev1IsChar) {
                    --v38;
                    if(this._optimum[v5].Prev2) {
                        v48 = this._optimum[this._optimum[v5].PosPrev2].State;
                        if(this._optimum[v5].BackPrev2 < 4) {
                            v48 = Base.StateUpdateRep(v48);
                        }
                        else {
                            v48 = Base.StateUpdateMatch(v48);
                        }
                    }
                    else {
                        v48 = this._optimum[v38].State;
                    }

                    v48 = Base.StateUpdateChar(v48);
                }
                else {
                    v48 = this._optimum[v38].State;
                }

                if(v38 != v5 - 1) {
                    if(!this._optimum[v5].Prev1IsChar || !this._optimum[v5].Prev2) {
                        v37 = this._optimum[v5].BackPrev;
                        if(v37 < 4) {
                            v48 = Base.StateUpdateRep(v48);
                        }
                        else {
                            v48 = Base.StateUpdateMatch(v48);
                        }
                    }
                    else {
                        v38 = this._optimum[v5].PosPrev2;
                        v37 = this._optimum[v5].BackPrev2;
                        v48 = Base.StateUpdateRep(v48);
                    }

                    Optimal v35 = this._optimum[v38];
                    if(v37 >= 4) {
                        goto label_1132;
                    }

                    if(v37 != 0) {
                        goto label_1025;
                    }

                    this.reps[0] = v35.Backs0;
                    this.reps[1] = v35.Backs1;
                    this.reps[2] = v35.Backs2;
                    this.reps[3] = v35.Backs3;
                    goto label_662;
                label_1025:
                    if(v37 != 1) {
                        goto label_1062;
                    }

                    this.reps[0] = v35.Backs1;
                    this.reps[1] = v35.Backs0;
                    this.reps[2] = v35.Backs2;
                    this.reps[3] = v35.Backs3;
                    goto label_662;
                label_1062:
                    if(v37 != 2) {
                        goto label_1099;
                    }

                    this.reps[0] = v35.Backs2;
                    this.reps[1] = v35.Backs0;
                    this.reps[2] = v35.Backs1;
                    this.reps[3] = v35.Backs3;
                    goto label_662;
                label_1099:
                    this.reps[0] = v35.Backs3;
                    this.reps[1] = v35.Backs0;
                    this.reps[2] = v35.Backs1;
                    this.reps[3] = v35.Backs2;
                    goto label_662;
                label_1132:
                    this.reps[0] = v37 - 4;
                    this.reps[1] = v35.Backs0;
                    this.reps[2] = v35.Backs1;
                    this.reps[3] = v35.Backs2;
                }
                else if(this._optimum[v5].IsShortRep()) {
                    v48 = Base.StateUpdateShortRep(v48);
                }
                else {
                    v48 = Base.StateUpdateChar(v48);
                }

            label_662:
                this._optimum[v5].State = v48;
                this._optimum[v5].Backs0 = this.reps[0];
                this._optimum[v5].Backs1 = this.reps[1];
                this._optimum[v5].Backs2 = this.reps[2];
                this._optimum[v5].Backs3 = this.reps[3];
                int v10 = this._optimum[v5].Price;
                v11 = this._matchFinder.GetIndexByte(-1);
                v22 = this._matchFinder.GetIndexByte(-this.reps[0] - 2);
                v39 = position & this._posStateMask;
                int v52_1 = v10 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(v48 << 4) + v39]);
                Encoder2 v53_1 = this._literalEncoder.GetSubCoder(position, this._matchFinder.GetIndexByte(-2));
                if(!Base.StateIsCharState(v48)) {
                    v51 = true;
                }
                else {
                    v51 = false;
                }

                v6 = v52_1 + v53_1.GetPrice(v51, v22, v11);
                Optimal v27 = this._optimum[v5 + 1];
                int v25 = 0;
                if(v6 < v27.Price) {
                    v27.Price = v6;
                    v27.PosPrev = v5;
                    v27.MakeAsChar();
                    v25 = 1;
                }

                v23 = v10 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(v48 << 4) + v39]);
                v44 = v23 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[v48]);
                if(v22 == v11 && (v27.PosPrev >= v5 || v27.BackPrev != 0)) {
                    v46 = v44 + this.GetRepLen1Price(v48, v39);
                    if(v46 > v27.Price) {
                        goto label_832;
                    }

                    v27.Price = v46;
                    v27.PosPrev = v5;
                    v27.MakeAsShortRep();
                    v25 = 1;
                }

            label_832:
                v31 = Math.min(4095 - v5, this._matchFinder.GetNumAvailableBytes() + 1);
                v30 = v31;
            }
            while(v30 < 2);

            if(v30 > this._numFastBytes) {
                v30 = this._numFastBytes;
            }

            if(v25 == 0 && v22 != v11) {
                v20 = this._matchFinder.GetMatchLen(0, this.reps[0], Math.min(v31 - 1, this._numFastBytes));
                if(v20 >= 2) {
                    v49 = Base.StateUpdateChar(v48);
                    v40 = position + 1 & this._posStateMask;
                    v28 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(v49 << 4) + v40]) + v6 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[v49]);
                    v34 = v5 + 1 + v20;
                    goto label_904;
                label_576:
                    v18 = this.Backward(v5);
                    goto label_49;
                label_904:
                    while(v16 < v34) {
                        ++v16;
                        this._optimum[v16].Price = 268435455;
                    }

                    v8 = v28 + this.GetRepPrice(0, v20, v49, v40);
                    v36 = this._optimum[v34];
                    if(v8 < v36.Price) {
                        v36.Price = v8;
                        v36.PosPrev = v5 + 1;
                        v36.BackPrev = 0;
                        v36.Prev1IsChar = true;
                        v36.Prev2 = false;
                    }
                }
            }

            int v47 = 2;
            int v42;
            for(v42 = 0; v42 < 4; ++v42) {
                v19 = this._matchFinder.GetMatchLen(-1, this.reps[v42], v30);
                if(v19 >= 2) {
                    int v21 = v19;
                    do {
                        if(v16 < v5 + v19) {
                            ++v16;
                            this._optimum[v16].Price = 268435455;
                            continue;
                        }
                        else {
                            v8 = v44 + this.GetRepPrice(v42, v19, v48, v39);
                            v36 = this._optimum[v5 + v19];
                            if(v8 < v36.Price) {
                                v36.Price = v8;
                                v36.PosPrev = v5;
                                v36.BackPrev = v42;
                                v36.Prev1IsChar = false;
                            }

                            --v19;
                            if(v19 >= 2) {
                                continue;
                            }

                            break;
                        }
                    }
                    while(true);

                    v19 = v21;
                    if(v42 == 0) {
                        v47 = v19 + 1;
                    }

                    if(v19 >= v31) {
                        goto label_1223;
                    }

                    v20 = this._matchFinder.GetMatchLen(v19, this.reps[v42], Math.min(v31 - 1 - v19, this._numFastBytes));
                    if(v20 < 2) {
                        goto label_1223;
                    }

                    v49 = Base.StateUpdateRep(v48);
                    v7 = this.GetRepPrice(v42, v19, v48, v39) + v44 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(v49 << 4) + (position + v19 & this._posStateMask)]) + this._literalEncoder.GetSubCoder(position + v19, this._matchFinder.GetIndexByte(v19 - 2)).GetPrice(true, this._matchFinder.GetIndexByte(v19 - 1 - (this.reps[v42] + 1)), this._matchFinder.GetIndexByte(v19 - 1));
                    v49 = Base.StateUpdateChar(v49);
                    v40 = position + v19 + 1 & this._posStateMask;
                    v28 = v7 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(v49 << 4) + v40]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[v49]);
                    v34 = v19 + 1 + v20;
                    while(v16 < v5 + v34) {
                        ++v16;
                        this._optimum[v16].Price = 268435455;
                    }

                    v8 = v28 + this.GetRepPrice(0, v20, v49, v40);
                    v36 = this._optimum[v5 + v34];
                    if(v8 >= v36.Price) {
                        goto label_1223;
                    }

                    v36.Price = v8;
                    v36.PosPrev = v5 + v19 + 1;
                    v36.BackPrev = 0;
                    v36.Prev1IsChar = true;
                    v36.Prev2 = true;
                    v36.PosPrev2 = v5;
                    v36.BackPrev2 = v42;
                }

            label_1223:
            }

            if(v24 > v30) {
                v24 = v30;
                for(v32 = 0; v24 > this._matchDistances[v32]; v32 += 2) {
                }

                this._matchDistances[v32] = v24;
                v32 += 2;
            }

            if(v24 < v47) {
                goto label_573;
            }

            v29 = v23 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep[v48]);
            while(v16 < v5 + v24) {
                ++v16;
                this._optimum[v16].Price = 268435455;
            }

            for(v33 = 0; v47 > this._matchDistances[v33]; v33 += 2) {
            }

            for(v19 = v47; true; ++v19) {
                int v9 = this._matchDistances[v33 + 1];
                v8 = v29 + this.GetPosLenPrice(v9, v19, v39);
                v36 = this._optimum[v5 + v19];
                if(v8 < v36.Price) {
                    v36.Price = v8;
                    v36.PosPrev = v5;
                    v36.BackPrev = v9 + 4;
                    v36.Prev1IsChar = false;
                }

                if(v19 == this._matchDistances[v33]) {
                    if(v19 < v31) {
                        v20 = this._matchFinder.GetMatchLen(v19, v9, Math.min(v31 - 1 - v19, this._numFastBytes));
                        if(v20 >= 2) {
                            v49 = Base.StateUpdateMatch(v48);
                            v7 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isMatch[(v49 << 4) + (position + v19 & this._posStateMask)]) + v8 + this._literalEncoder.GetSubCoder(position + v19, this._matchFinder.GetIndexByte(v19 - 2)).GetPrice(true, this._matchFinder.GetIndexByte(v19 - (v9 + 1) - 1), this._matchFinder.GetIndexByte(v19 - 1));
                            v49 = Base.StateUpdateChar(v49);
                            v40 = position + v19 + 1 & this._posStateMask;
                            v28 = v7 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isMatch[(v49 << 4) + v40]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep[v49]);
                            v34 = v19 + 1 + v20;
                            while(v16 < v5 + v34) {
                                ++v16;
                                this._optimum[v16].Price = 268435455;
                            }

                            v8 = v28 + this.GetRepPrice(0, v20, v49, v40);
                            v36 = this._optimum[v5 + v34];
                            if(v8 < v36.Price) {
                                v36.Price = v8;
                                v36.PosPrev = v5 + v19 + 1;
                                v36.BackPrev = 0;
                                v36.Prev1IsChar = true;
                                v36.Prev2 = true;
                                v36.PosPrev2 = v5;
                                v36.BackPrev2 = v9 + 4;
                            }
                        }
                    }

                    v33 += 2;
                    if(v33 == v32) {
                        goto label_573;
                    }
                }
            }
        }

    label_49:
        return v18;
    }

    int GetPosLenPrice(int pos, int len, int posState) {
        int v1;
        int v0 = Base.GetLenToPosState(len);
        if(pos < 128) {
            v1 = this._distancesPrices[v0 * 128 + pos];
        }
        else {
            v1 = this._posSlotPrices[(v0 << 6) + Encoder.GetPosSlot2(pos)] + this._alignPrices[pos & 15];
        }

        return this._lenEncoder.GetPrice(len - 2, posState) + v1;
    }

    static int GetPosSlot(int pos) {
        int v0;
        if(pos < 2048) {
            v0 = Encoder.g_FastPos[pos];
        }
        else if(pos < 2097152) {
            v0 = Encoder.g_FastPos[pos >> 10] + 20;
        }
        else {
            v0 = Encoder.g_FastPos[pos >> 20] + 40;
        }

        return v0;
    }

    static int GetPosSlot2(int pos) {
        int v0;
        if(pos < 131072) {
            v0 = Encoder.g_FastPos[pos >> 6] + 12;
        }
        else if(pos < 134217728) {
            v0 = Encoder.g_FastPos[pos >> 16] + 32;
        }
        else {
            v0 = Encoder.g_FastPos[pos >> 26] + 52;
        }

        return v0;
    }

    int GetPureRepPrice(int repIndex, int state, int posState) {
        int v0;
        if(repIndex == 0) {
            v0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[state]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRep0Long[(state << 4) + posState]);
        }
        else {
            v0 = com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG0[state]);
            if(repIndex == 1) {
                v0 += com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG1[state]);
            }
            else {
                v0 = v0 + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice1(this._isRepG1[state]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice(this._isRepG2[state], repIndex - 2);
            }
        }

        return v0;
    }

    int GetRepLen1Price(int state, int posState) {
        return com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRepG0[state]) + com.badlogic.gdx.utils.compression.rangecoder.Encoder.GetPrice0(this._isRep0Long[(state << 4) + posState]);
    }

    int GetRepPrice(int repIndex, int len, int state, int posState) {
        return this.GetPureRepPrice(repIndex, state, posState) + this._repMatchLenEncoder.GetPrice(len - 2, posState);
    }

    void Init() {
        this.BaseInit();
        this._rangeEncoder.Init();
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isMatch);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep0Long);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRep);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG0);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG1);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._isRepG2);
        com.badlogic.gdx.utils.compression.rangecoder.Encoder.InitBitModels(this._posEncoders);
        this._literalEncoder.Init();
        int v0;
        for(v0 = 0; v0 < 4; ++v0) {
            this._posSlotEncoder[v0].Init();
        }

        this._lenEncoder.Init(1 << this._posStateBits);
        this._repMatchLenEncoder.Init(1 << this._posStateBits);
        this._posAlignEncoder.Init();
        this._longestMatchWasFound = false;
        this._optimumEndIndex = 0;
        this._optimumCurrentIndex = 0;
        this._additionalOffset = 0;
    }

    void MovePos(int num) throws IOException {
        if(num > 0) {
            this._matchFinder.Skip(num);
            this._additionalOffset += num;
        }
    }

    int ReadMatchDistances() throws IOException {
        int v0 = 0;
        this._numDistancePairs = this._matchFinder.GetMatches(this._matchDistances);
        if(this._numDistancePairs > 0) {
            v0 = this._matchDistances[this._numDistancePairs - 2];
            if(v0 == this._numFastBytes) {
                v0 += this._matchFinder.GetMatchLen(v0 - 1, this._matchDistances[this._numDistancePairs - 1], 273 - v0);
            }
        }

        ++this._additionalOffset;
        return v0;
    }

    void ReleaseMFStream() {
        if(this._matchFinder != null && (this._needReleaseMFStream)) {
            this._matchFinder.ReleaseStream();
            this._needReleaseMFStream = false;
        }
    }

    void ReleaseOutStream() {
        this._rangeEncoder.ReleaseStream();
    }

    void ReleaseStreams() {
        this.ReleaseMFStream();
        this.ReleaseOutStream();
    }

    public boolean SetAlgorithm(int algorithm) {
        return 1;
    }

    public boolean SetDictionarySize(int dictionarySize) {
        boolean v2 = true;
        if(dictionarySize < 1 || dictionarySize > 536870912) {
            v2 = false;
        }
        else {
            this._dictionarySize = dictionarySize;
            int v0;
            for(v0 = 0; dictionarySize > 1 << v0; ++v0) {
            }

            this._distTableSize = v0 * 2;
        }

        return v2;
    }

    public void SetEndMarkerMode(boolean endMarkerMode) {
        this._writeEndMark = endMarkerMode;
    }

    public boolean SetLcLpPb(int lc, int lp, int pb) {
        int v2 = 4;
        boolean v0 = true;
        if(lp < 0 || lp > v2 || lc < 0 || lc > 8 || pb < 0 || pb > v2) {
            v0 = false;
        }
        else {
            this._numLiteralPosStateBits = lp;
            this._numLiteralContextBits = lc;
            this._posStateBits = pb;
            this._posStateMask = (1 << this._posStateBits) - 1;
        }

        return v0;
    }

    public boolean SetMatchFinder(int matchFinderIndex) {
        boolean v1;
        if(matchFinderIndex < 0 || matchFinderIndex > 2) {
            v1 = false;
        }
        else {
            int v0 = this._matchFinderType;
            this._matchFinderType = matchFinderIndex;
            if(this._matchFinder != null && v0 != this._matchFinderType) {
                this._dictionarySizePrev = -1;
                this._matchFinder = null;
            }

            v1 = true;
        }

        return v1;
    }

    public boolean SetNumFastBytes(int numFastBytes) {
        boolean v0;
        if(numFastBytes < 5 || numFastBytes > 273) {
            v0 = false;
        }
        else {
            this._numFastBytes = numFastBytes;
            v0 = true;
        }

        return v0;
    }

    void SetOutStream(OutputStream outStream) {
        this._rangeEncoder.SetStream(outStream);
    }

    void SetStreams(InputStream inStream, OutputStream outStream, long inSize, long outSize) {
        this._inStream = inStream;
        this._finished = false;
        this.Create();
        this.SetOutStream(outStream);
        this.Init();
        this.FillDistancesPrices();
        this.FillAlignPrices();
        this._lenEncoder.SetTableSize(this._numFastBytes - 1);
        this._lenEncoder.UpdateTables(1 << this._posStateBits);
        this._repMatchLenEncoder.SetTableSize(this._numFastBytes - 1);
        this._repMatchLenEncoder.UpdateTables(1 << this._posStateBits);
        this.nowPos64 = 0;
    }

    void SetWriteEndMarkerMode(boolean writeEndMarker) {
        this._writeEndMark = writeEndMarker;
    }

    public void WriteCoderProperties(OutputStream outStream) throws IOException {
        this.properties[0] = ((byte)((this._posStateBits * 5 + this._numLiteralPosStateBits) * 9 + this._numLiteralContextBits));
        int v0;
        for(v0 = 0; v0 < 4; ++v0) {
            this.properties[v0 + 1] = ((byte)(this._dictionarySize >> v0 * 8));
        }

        outStream.write(this.properties, 0, 5);
    }

    void WriteEndMarker(int posState) throws IOException {
        if(this._writeEndMark) {
            this._rangeEncoder.Encode(this._isMatch, (this._state << 4) + posState, 1);
            this._rangeEncoder.Encode(this._isRep, this._state, 0);
            this._state = Base.StateUpdateMatch(this._state);
            this._lenEncoder.Encode(this._rangeEncoder, 0, posState);
            this._posSlotEncoder[Base.GetLenToPosState(2)].Encode(this._rangeEncoder, 63);
            this._rangeEncoder.EncodeDirectBits(67108863, 26);
            this._posAlignEncoder.ReverseEncode(this._rangeEncoder, 15);
        }
    }
}

