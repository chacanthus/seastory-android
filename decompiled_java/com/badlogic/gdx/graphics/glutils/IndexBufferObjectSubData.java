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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class IndexBufferObjectSubData implements IndexData {
    ShortBuffer buffer;
    int bufferHandle;
    ByteBuffer byteBuffer;
    boolean isBound;
    final boolean isDirect;
    boolean isDirty;
    final int usage;

    public IndexBufferObjectSubData(boolean isStatic, int maxIndices) {
        int v0;
        super();
        this.isDirty = true;
        this.isBound = false;
        this.byteBuffer = BufferUtils.newByteBuffer(maxIndices * 2);
        this.isDirect = true;
        if(isStatic) {
            v0 = 35044;
        }
        else {
            v0 = 35048;
        }

        this.usage = v0;
        this.buffer = this.byteBuffer.asShortBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
        this.bufferHandle = this.createBufferObject();
    }

    public IndexBufferObjectSubData(int maxIndices) {
        super();
        this.isDirty = true;
        this.isBound = false;
        this.byteBuffer = BufferUtils.newByteBuffer(maxIndices * 2);
        this.isDirect = true;
        this.usage = 35044;
        this.buffer = this.byteBuffer.asShortBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
        this.bufferHandle = this.createBufferObject();
    }

    public void bind() {
        int v4 = 34963;
        if(this.bufferHandle == 0) {
            throw new GdxRuntimeException("buuh");
        }

        Gdx.gl20.glBindBuffer(v4, this.bufferHandle);
        if(this.isDirty) {
            this.byteBuffer.limit(this.buffer.limit() * 2);
            Gdx.gl20.glBufferSubData(v4, 0, this.byteBuffer.limit(), this.byteBuffer);
            this.isDirty = false;
        }

        this.isBound = true;
    }

    private int createBufferObject() {
        int v0 = Gdx.gl20.glGenBuffer();
        Gdx.gl20.glBindBuffer(34963, v0);
        Gdx.gl20.glBufferData(34963, this.byteBuffer.capacity(), null, this.usage);
        Gdx.gl20.glBindBuffer(34963, 0);
        return v0;
    }

    public void dispose() {
        GL20 v0 = Gdx.gl20;
        v0.glBindBuffer(34963, 0);
        v0.glDeleteBuffer(this.bufferHandle);
        this.bufferHandle = 0;
    }

    public ShortBuffer getBuffer() {
        this.isDirty = true;
        return this.buffer;
    }

    public int getNumIndices() {
        return this.buffer.limit();
    }

    public int getNumMaxIndices() {
        return this.buffer.capacity();
    }

    public void invalidate() {
        this.bufferHandle = this.createBufferObject();
        this.isDirty = true;
    }

    public void setIndices(ShortBuffer indices) {
        int v0 = indices.position();
        this.isDirty = true;
        this.buffer.clear();
        this.buffer.put(indices);
        this.buffer.flip();
        indices.position(v0);
        this.byteBuffer.position(0);
        this.byteBuffer.limit(this.buffer.limit() << 1);
        if(this.isBound) {
            Gdx.gl20.glBufferSubData(34963, 0, this.byteBuffer.limit(), this.byteBuffer);
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
            Gdx.gl20.glBufferSubData(34963, 0, this.byteBuffer.limit(), this.byteBuffer);
            this.isDirty = false;
        }
    }

    public void unbind() {
        Gdx.gl20.glBindBuffer(34963, 0);
        this.isBound = false;
    }
}

