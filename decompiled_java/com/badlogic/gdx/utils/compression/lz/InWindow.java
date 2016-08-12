// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils.compression.lz;

import java.io.IOException;
import java.io.InputStream;

public class InWindow {
    public int _blockSize;
    public byte[] _bufferBase;
    public int _bufferOffset;
    int _keepSizeAfter;
    int _keepSizeBefore;
    int _pointerToLastSafePosition;
    public int _pos;
    int _posLimit;
    InputStream _stream;
    boolean _streamEndWasReached;
    public int _streamPos;

    public InWindow() {
        super();
    }

    public void Create(int keepSizeBefore, int keepSizeAfter, int keepSizeReserv) {
        this._keepSizeBefore = keepSizeBefore;
        this._keepSizeAfter = keepSizeAfter;
        int v0 = keepSizeBefore + keepSizeAfter + keepSizeReserv;
        if(this._bufferBase == null || this._blockSize != v0) {
            this.Free();
            this._blockSize = v0;
            this._bufferBase = new byte[this._blockSize];
        }

        this._pointerToLastSafePosition = this._blockSize - keepSizeAfter;
    }

    void Free() {
        this._bufferBase = null;
    }

    public byte GetIndexByte(int index) {
        return this._bufferBase[this._bufferOffset + this._pos + index];
    }

    public int GetMatchLen(int index, int distance, int limit) {
        if((this._streamEndWasReached) && this._pos + index + limit > this._streamPos) {
            limit = this._streamPos - (this._pos + index);
        }

        ++distance;
        int v1 = this._bufferOffset + this._pos + index;
        int v0 = 0;
        while(v0 < limit) {
            if(this._bufferBase[v1 + v0] != this._bufferBase[v1 + v0 - distance]) {
                break;
            }

            ++v0;
        }

        return v0;
    }

    public int GetNumAvailableBytes() {
        return this._streamPos - this._pos;
    }

    public void Init() throws IOException {
        this._bufferOffset = 0;
        this._pos = 0;
        this._streamPos = 0;
        this._streamEndWasReached = false;
        this.ReadBlock();
    }

    public void MoveBlock() {
        int v2 = this._bufferOffset + this._pos - this._keepSizeBefore;
        if(v2 > 0) {
            --v2;
        }

        int v1 = this._bufferOffset + this._streamPos - v2;
        int v0;
        for(v0 = 0; v0 < v1; ++v0) {
            this._bufferBase[v0] = this._bufferBase[v2 + v0];
        }

        this._bufferOffset -= v2;
    }

    public void MovePos() throws IOException {
        ++this._pos;
        if(this._pos > this._posLimit) {
            if(this._bufferOffset + this._pos > this._pointerToLastSafePosition) {
                this.MoveBlock();
            }

            this.ReadBlock();
        }
    }

    public void ReadBlock() throws IOException {
        if(!this._streamEndWasReached) {
            while(true) {
                int v2 = -this._bufferOffset + this._blockSize - this._streamPos;
                if(v2 != 0) {
                    int v0 = this._stream.read(this._bufferBase, this._bufferOffset + this._streamPos, v2);
                    if(v0 == -1) {
                        this._posLimit = this._streamPos;
                        if(this._bufferOffset + this._posLimit > this._pointerToLastSafePosition) {
                            this._posLimit = this._pointerToLastSafePosition - this._bufferOffset;
                        }

                        this._streamEndWasReached = true;
                    }
                    else {
                        this._streamPos += v0;
                        if(this._streamPos < this._pos + this._keepSizeAfter) {
                            continue;
                        }

                        this._posLimit = this._streamPos - this._keepSizeAfter;
                        continue;
                    }
                }

                return;
            }
        }
    }

    public void ReduceOffsets(int subValue) {
        this._bufferOffset += subValue;
        this._posLimit -= subValue;
        this._pos -= subValue;
        this._streamPos -= subValue;
    }

    public void ReleaseStream() {
        this._stream = null;
    }

    public void SetStream(InputStream stream) {
        this._stream = stream;
    }
}

