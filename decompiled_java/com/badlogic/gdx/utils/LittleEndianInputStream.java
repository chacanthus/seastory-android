// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianInputStream extends FilterInputStream implements DataInput {
    private DataInputStream din;

    public LittleEndianInputStream(InputStream in) {
        super(in);
        this.din = new DataInputStream(in);
    }

    public boolean readBoolean() throws IOException {
        return this.din.readBoolean();
    }

    public byte readByte() throws IOException {
        return this.din.readByte();
    }

    public char readChar() throws IOException {
        return this.din.readChar();
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    public void readFully(byte[] b) throws IOException {
        this.din.readFully(b);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        this.din.readFully(b, off, len);
    }

    public int readInt() throws IOException {
        int[] v1 = new int[4];
        int v0;
        for(v0 = 3; v0 >= 0; --v0) {
            v1[v0] = this.din.read();
        }

        return (v1[0] & 255) << 24 | (v1[1] & 255) << 16 | (v1[2] & 255) << 8 | v1[3] & 255;
    }

    public final String readLine() throws IOException {
        return this.din.readLine();
    }

    public long readLong() throws IOException {
        int v7 = 8;
        int[] v1 = new int[v7];
        int v0;
        for(v0 = 7; v0 >= 0; --v0) {
            v1[v0] = this.din.read();
        }

        return (((long)(v1[0] & 255))) << 56 | (((long)(v1[1] & 255))) << 48 | (((long)(v1[2] & 255))) << 40 | (((long)(v1[3] & 255))) << 32 | (((long)(v1[4] & 255))) << 24 | (((long)(v1[5] & 255))) << 16 | (((long)(v1[6] & 255))) << v7 | (((long)(v1[7] & 255)));
    }

    public short readShort() throws IOException {
        return ((short)(this.din.read() << 8 | this.din.read() & 255));
    }

    public String readUTF() throws IOException {
        return this.din.readUTF();
    }

    public int readUnsignedByte() throws IOException {
        return this.din.readUnsignedByte();
    }

    public int readUnsignedShort() throws IOException {
        return (this.din.read() & 255) << 8 | this.din.read() & 255;
    }

    public int skipBytes(int n) throws IOException {
        return this.din.skipBytes(n);
    }
}

