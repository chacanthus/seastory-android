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
import java.io.OutputStream;

public class OutWindow {
    byte[] _buffer;
    int _pos;
    OutputStream _stream;
    int _streamPos;
    int _windowSize;

    public OutWindow() {
        super();
        this._windowSize = 0;
    }

    public void CopyBlock(int distance, int len) throws IOException {
        int v0 = this._pos - distance - 1;
        if(v0 < 0) {
            v0 += this._windowSize;
        }

        while(len != 0) {
            if(v0 >= this._windowSize) {
                v0 = 0;
            }

            byte[] v2 = this._buffer;
            int v3 = this._pos;
            this._pos = v3 + 1;
            int v1 = v0 + 1;
            v2[v3] = this._buffer[v0];
            if(this._pos >= this._windowSize) {
                this.Flush();
            }

            --len;
            v0 = v1;
        }
    }

    public void Create(int windowSize) {
        if(this._buffer == null || this._windowSize != windowSize) {
            this._buffer = new byte[windowSize];
        }

        this._windowSize = windowSize;
        this._pos = 0;
        this._streamPos = 0;
    }

    public void Flush() throws IOException {
        int v0 = this._pos - this._streamPos;
        if(v0 != 0) {
            this._stream.write(this._buffer, this._streamPos, v0);
            if(this._pos >= this._windowSize) {
                this._pos = 0;
            }

            this._streamPos = this._pos;
        }
    }

    public byte GetByte(int distance) {
        int v0 = this._pos - distance - 1;
        if(v0 < 0) {
            v0 += this._windowSize;
        }

        return this._buffer[v0];
    }

    public void Init(boolean solid) {
        if(!solid) {
            this._streamPos = 0;
            this._pos = 0;
        }
    }

    public void PutByte(byte b) throws IOException {
        byte[] v0 = this._buffer;
        int v1 = this._pos;
        this._pos = v1 + 1;
        v0[v1] = b;
        if(this._pos >= this._windowSize) {
            this.Flush();
        }
    }

    public void ReleaseStream() throws IOException {
        this.Flush();
        this._stream = null;
    }

    public void SetStream(OutputStream stream) throws IOException {
        this.ReleaseStream();
        this._stream = stream;
    }
}

