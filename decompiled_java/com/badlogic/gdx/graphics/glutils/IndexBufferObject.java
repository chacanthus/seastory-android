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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class IndexBufferObject implements IndexData {
    ShortBuffer buffer;
    int bufferHandle;
    ByteBuffer byteBuffer;
    private final boolean empty;
    boolean isBound;
    final boolean isDirect;
    boolean isDirty;
    final int usage;

    public IndexBufferObject(boolean isStatic, int maxIndices) {
        int v0_1;
        boolean v0 = false;
        super();
        this.isDirty = true;
        this.isBound = false;
        if(maxIndices == 0) {
            v0 = true;
        }

        this.empty = v0;
        this.byteBuffer = BufferUtils.newUnsafeByteBuffer(2);
        this.isDirect = true;
        this.buffer = this.byteBuffer.asShortBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
        this.bufferHandle = Gdx.gl20.glGenBuffer();
        if(isStatic) {
            v0_1 = 35044;
        }
        else {
            v0_1 = 35048;
        }

        this.usage = v0_1;
    }

    public IndexBufferObject(int maxIndices) {
        this(true, maxIndices);
    }

    public void bind() {
        int v4 = 34963;
        if(this.bufferHandle == 0) {
            throw new GdxRuntimeException("No buffer allocated!");
        }

        Gdx.gl20.glBindBuffer(v4, this.bufferHandle);
        if(this.isDirty) {
            this.byteBuffer.limit(this.buffer.limit() * 2);
            Gdx.gl20.glBufferData(v4, this.byteBuffer.limit(), this.byteBuffer, this.usage);
            this.isDirty = false;
        }

        this.isBound = true;
    }

    public void dispose() {
        Gdx.gl20.glBindBuffer(34963, 0);
        Gdx.gl20.glDeleteBuffer(this.bufferHandle);
        this.bufferHandle = 0;
        BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer);
    }

    public ShortBuffer getBuffer() {
        this.isDirty = true;
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
        this.bufferHandle = Gdx.gl20.glGenBuffer();
        this.isDirty = true;
    }

    public void setIndices(ShortBuffer indices) {
        this.isDirty = true;
        int v0 = indices.position();
        this.buffer.clear();
        this.buffer.put(indices);
        this.buffer.flip();
        indices.position(v0);
        this.byteBuffer.position(0);
        this.byteBuffer.limit(this.buffer.limit() << 1);
        if(this.isBound) {
            Gdx.gl20.glBufferData(34963, this.byteBuffer.limit(), this.byteBuffer, this.usage);
            this.isDirty = false;
        }
    }

    public void setIndices(short[] indices, int offset, int count) {
        this.isDirty = true;
        this.buffer.clear();
        this.buffer.put(indices, offset, count);
        this.buffer.flip();
        this.byteBuffer.position(0);
        this.byteBuffer.limit(count << 1);
        if(this.isBound) {
            Gdx.gl20.glBufferData(34963, this.byteBuffer.limit(), this.byteBuffer, this.usage);
            this.isDirty = false;
        }
    }

    public void unbind() {
        Gdx.gl20.glBindBuffer(34963, 0);
        this.isBound = false;
    }
}

