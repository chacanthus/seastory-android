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

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class VertexArray implements VertexData {
    final VertexAttributes attributes;
    final FloatBuffer buffer;
    final ByteBuffer byteBuffer;
    boolean isBound;

    public VertexArray(int numVertices, VertexAttribute[] attributes) {
        this(numVertices, new VertexAttributes(attributes));
    }

    public VertexArray(int numVertices, VertexAttributes attributes) {
        super();
        this.isBound = false;
        this.attributes = attributes;
        this.byteBuffer = BufferUtils.newUnsafeByteBuffer(this.attributes.vertexSize * numVertices);
        this.buffer = this.byteBuffer.asFloatBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
    }

    public void bind(ShaderProgram shader) {
        this.bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        int v1;
        VertexAttribute v7;
        int v10 = 5126;
        int v9 = this.attributes.size();
        this.byteBuffer.limit(this.buffer.limit() * 4);
        if(locations == null) {
            int v8;
            for(v8 = 0; v8 < v9; ++v8) {
                v7 = this.attributes.get(v8);
                v1 = shader.getAttributeLocation(v7.alias);
                if(v1 >= 0) {
                    shader.enableVertexAttribute(v1);
                    if(v7.type == v10) {
                        this.buffer.position(v7.offset / 4);
                        shader.setVertexAttribute(v1, v7.numComponents, v7.type, v7.normalized, this.attributes.vertexSize, this.buffer);
                    }
                    else {
                        this.byteBuffer.position(v7.offset);
                        shader.setVertexAttribute(v1, v7.numComponents, v7.type, v7.normalized, this.attributes.vertexSize, this.byteBuffer);
                    }
                }
            }
        }
        else {
            for(v8 = 0; v8 < v9; ++v8) {
                v7 = this.attributes.get(v8);
                v1 = locations[v8];
                if(v1 >= 0) {
                    shader.enableVertexAttribute(v1);
                    if(v7.type == v10) {
                        this.buffer.position(v7.offset / 4);
                        shader.setVertexAttribute(v1, v7.numComponents, v7.type, v7.normalized, this.attributes.vertexSize, this.buffer);
                    }
                    else {
                        this.byteBuffer.position(v7.offset);
                        shader.setVertexAttribute(v1, v7.numComponents, v7.type, v7.normalized, this.attributes.vertexSize, this.byteBuffer);
                    }
                }
            }
        }

        this.isBound = true;
    }

    public void dispose() {
        BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer);
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }

    public FloatBuffer getBuffer() {
        return this.buffer;
    }

    public int getNumMaxVertices() {
        return this.byteBuffer.capacity() / this.attributes.vertexSize;
    }

    public int getNumVertices() {
        return this.buffer.limit() * 4 / this.attributes.vertexSize;
    }

    public void invalidate() {
    }

    public void setVertices(float[] vertices, int offset, int count) {
        BufferUtils.copy(vertices, this.byteBuffer, count, offset);
        this.buffer.position(0);
        this.buffer.limit(count);
    }

    public void unbind(ShaderProgram shader) {
        this.unbind(shader, null);
    }

    public void unbind(ShaderProgram shader, int[] locations) {
        int v2 = this.attributes.size();
        if(locations == null) {
            int v0;
            for(v0 = 0; v0 < v2; ++v0) {
                shader.disableVertexAttribute(this.attributes.get(v0).alias);
            }
        }
        else {
            for(v0 = 0; v0 < v2; ++v0) {
                int v1 = locations[v0];
                if(v1 >= 0) {
                    shader.disableVertexAttribute(v1);
                }
            }
        }

        this.isBound = false;
    }

    public void updateVertices(int targetOffset, float[] vertices, int sourceOffset, int count) {
        int v0 = this.byteBuffer.position();
        this.byteBuffer.position(targetOffset * 4);
        BufferUtils.copy(vertices, sourceOffset, count, this.byteBuffer);
        this.byteBuffer.position(v0);
    }
}

