// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.utils.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class IndexArray implements IndexData {
    ShortBuffer buffer;
    ByteBuffer byteBuffer;
    private final boolean empty;
    static final IntBuffer tmpHandle;

    static  {
        IndexArray.tmpHandle = BufferUtils.newIntBuffer(1);
    }

    public IndexArray(int maxIndices) {
        boolean v0;
        super();
        if(maxIndices == 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.empty = v0;
        this.byteBuffer = BufferUtils.newUnsafeByteBuffer(2);
        this.buffer = this.byteBuffer.asShortBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
    }

    public void bind() {
    }

    public void dispose() {
        BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer);
    }

    public ShortBuffer getBuffer() {
        return this.buffer;
    }

    public int getNumIndices() {
        int v0;
        if(this.empty) {
            v0 = 0;
        }
        else {
            v0 = this.buffer.limit();
        }

        return v0;
    }

    public int getNumMaxIndices() {
        int v0;
        if(this.empty) {
            v0 = 0;
        }
        else {
            v0 = this.buffer.capacity();
        }

        return v0;
    }

    public void invalidate() {
    }

    public void setIndices(ShortBuffer indices) {
        int v0 = indices.position();
        this.buffer.clear();
        this.buffer.limit(indices.remaining());
        this.buffer.put(indices);
        this.buffer.flip();
        indices.position(v0);
        this.byteBuffer.position(0);
        this.byteBuffer.limit(this.buffer.limit() << 1);
    }

    public void setIndices(short[] indices, int offset, int count) {
        this.buffer.clear();
        this.buffer.put(indices, offset, count);
        this.buffer.flip();
        this.byteBuffer.position(0);
        this.byteBuffer.limit(count << 1);
    }

    public void unbind() {
    }
}

