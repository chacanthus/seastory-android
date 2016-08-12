// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.opengl.GLES30;
import com.badlogic.gdx.graphics.GL30;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public class AndroidGL30 extends AndroidGL20 implements GL30 {
    public AndroidGL30() {
        super();
    }

    public void glBeginQuery(int target, int id) {
        GLES30.glBeginQuery(target, id);
    }

    public void glBeginTransformFeedback(int primitiveMode) {
        GLES30.glBeginTransformFeedback(primitiveMode);
    }

    public void glBindBufferBase(int target, int index, int buffer) {
        GLES30.glBindBufferBase(target, index, buffer);
    }

    public void glBindBufferRange(int target, int index, int buffer, int offset, int size) {
        GLES30.glBindBufferRange(target, index, buffer, offset, size);
    }

    public void glBindSampler(int unit, int sampler) {
        GLES30.glBindSampler(unit, sampler);
    }

    public void glBindTransformFeedback(int target, int id) {
        GLES30.glBindTransformFeedback(target, id);
    }

    public void glBindVertexArray(int array) {
        GLES30.glBindVertexArray(array);
    }

    public void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        GLES30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }

    public void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) {
        GLES30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
    }

    public void glClearBufferfv(int buffer, int drawbuffer, FloatBuffer value) {
        GLES30.glClearBufferfv(buffer, drawbuffer, value);
    }

    public void glClearBufferiv(int buffer, int drawbuffer, IntBuffer value) {
        GLES30.glClearBufferiv(buffer, drawbuffer, value);
    }

    public void glClearBufferuiv(int buffer, int drawbuffer, IntBuffer value) {
        GLES30.glClearBufferuiv(buffer, drawbuffer, value);
    }

    public void glCopyBufferSubData(int readTarget, int writeTarget, int readOffset, int writeOffset, int size) {
        GLES30.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
    }

    public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) {
        GLES30.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
    }

    public void glDeleteQueries(int n, IntBuffer ids) {
        GLES30.glDeleteQueries(n, ids);
    }

    public void glDeleteQueries(int n, int[] ids, int offset) {
        GLES30.glDeleteQueries(n, ids, offset);
    }

    public void glDeleteSamplers(int count, IntBuffer samplers) {
        GLES30.glDeleteSamplers(count, samplers);
    }

    public void glDeleteSamplers(int count, int[] samplers, int offset) {
        GLES30.glDeleteSamplers(count, samplers, offset);
    }

    public void glDeleteTransformFeedbacks(int n, IntBuffer ids) {
        GLES30.glDeleteTransformFeedbacks(n, ids);
    }

    public void glDeleteTransformFeedbacks(int n, int[] ids, int offset) {
        GLES30.glDeleteTransformFeedbacks(n, ids, offset);
    }

    public void glDeleteVertexArrays(int n, IntBuffer arrays) {
        GLES30.glDeleteVertexArrays(n, arrays);
    }

    public void glDeleteVertexArrays(int n, int[] arrays, int offset) {
        GLES30.glDeleteVertexArrays(n, arrays, offset);
    }

    public void glDrawArraysInstanced(int mode, int first, int count, int instanceCount) {
        GLES30.glDrawArraysInstanced(mode, first, count, instanceCount);
    }

    public void glDrawBuffers(int n, IntBuffer bufs) {
        GLES30.glDrawBuffers(n, bufs);
    }

    public void glDrawElementsInstanced(int mode, int count, int type, int indicesOffset, int instanceCount) {
        GLES30.glDrawElementsInstanced(mode, count, type, indicesOffset, instanceCount);
    }

    public void glDrawRangeElements(int mode, int start, int end, int count, int type, int offset) {
        GLES30.glDrawRangeElements(mode, start, end, count, type, offset);
    }

    public void glDrawRangeElements(int mode, int start, int end, int count, int type, Buffer indices) {
        GLES30.glDrawRangeElements(mode, start, end, count, type, indices);
    }

    public void glEndQuery(int target) {
        GLES30.glEndQuery(target);
    }

    public void glEndTransformFeedback() {
        GLES30.glEndTransformFeedback();
    }

    public void glFlushMappedBufferRange(int target, int offset, int length) {
        GLES30.glFlushMappedBufferRange(target, offset, length);
    }

    public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) {
        GLES30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
    }

    public void glGenQueries(int n, IntBuffer ids) {
        GLES30.glGenQueries(n, ids);
    }

    public void glGenQueries(int n, int[] ids, int offset) {
        GLES30.glGenQueries(n, ids, offset);
    }

    public void glGenSamplers(int count, IntBuffer samplers) {
        GLES30.glGenSamplers(count, samplers);
    }

    public void glGenSamplers(int count, int[] samplers, int offset) {
        GLES30.glGenSamplers(count, samplers, offset);
    }

    public void glGenTransformFeedbacks(int n, IntBuffer ids) {
        GLES30.glGenTransformFeedbacks(n, ids);
    }

    public void glGenTransformFeedbacks(int n, int[] ids, int offset) {
        GLES30.glGenTransformFeedbacks(n, ids, offset);
    }

    public void glGenVertexArrays(int n, IntBuffer arrays) {
        GLES30.glGenVertexArrays(n, arrays);
    }

    public void glGenVertexArrays(int n, int[] arrays, int offset) {
        GLES30.glGenVertexArrays(n, arrays, offset);
    }

    public String glGetActiveUniformBlockName(int program, int uniformBlockIndex) {
        return GLES30.glGetActiveUniformBlockName(program, uniformBlockIndex);
    }

    public void glGetActiveUniformBlockName(int program, int uniformBlockIndex, Buffer length, Buffer uniformBlockName) {
        GLES30.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
    }

    public void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, IntBuffer params) {
        GLES30.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
    }

    public void glGetActiveUniformsiv(int program, int uniformCount, IntBuffer uniformIndices, int pname, IntBuffer params) {
        GLES30.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname, params);
    }

    public void glGetBufferParameteri64v(int target, int pname, LongBuffer params) {
        GLES30.glGetBufferParameteri64v(target, pname, params);
    }

    public Buffer glGetBufferPointerv(int target, int pname) {
        return GLES30.glGetBufferPointerv(target, pname);
    }

    public int glGetFragDataLocation(int program, String name) {
        return GLES30.glGetFragDataLocation(program, name);
    }

    public void glGetInteger64v(int pname, LongBuffer params) {
        GLES30.glGetInteger64v(pname, params);
    }

    public void glGetQueryObjectuiv(int id, int pname, IntBuffer params) {
        GLES30.glGetQueryObjectuiv(id, pname, params);
    }

    public void glGetQueryiv(int target, int pname, IntBuffer params) {
        GLES30.glGetQueryiv(target, pname, params);
    }

    public void glGetSamplerParameterfv(int sampler, int pname, FloatBuffer params) {
        GLES30.glGetSamplerParameterfv(sampler, pname, params);
    }

    public void glGetSamplerParameteriv(int sampler, int pname, IntBuffer params) {
        GLES30.glGetSamplerParameteriv(sampler, pname, params);
    }

    public String glGetStringi(int name, int index) {
        return GLES30.glGetStringi(name, index);
    }

    public int glGetUniformBlockIndex(int program, String uniformBlockName) {
        return GLES30.glGetUniformBlockIndex(program, uniformBlockName);
    }

    public void glGetUniformIndices(int program, String[] uniformNames, IntBuffer uniformIndices) {
        GLES30.glGetUniformIndices(program, uniformNames, uniformIndices);
    }

    public void glGetUniformuiv(int program, int location, IntBuffer params) {
        GLES30.glGetUniformuiv(program, location, params);
    }

    public void glGetVertexAttribIiv(int index, int pname, IntBuffer params) {
        GLES30.glGetVertexAttribIiv(index, pname, params);
    }

    public void glGetVertexAttribIuiv(int index, int pname, IntBuffer params) {
        GLES30.glGetVertexAttribIuiv(index, pname, params);
    }

    public void glInvalidateFramebuffer(int target, int numAttachments, IntBuffer attachments) {
        GLES30.glInvalidateFramebuffer(target, numAttachments, attachments);
    }

    public void glInvalidateSubFramebuffer(int target, int numAttachments, IntBuffer attachments, int x, int y, int width, int height) {
        GLES30.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width, height);
    }

    public boolean glIsQuery(int id) {
        return GLES30.glIsQuery(id);
    }

    public boolean glIsSampler(int sampler) {
        return GLES30.glIsSampler(sampler);
    }

    public boolean glIsTransformFeedback(int id) {
        return GLES30.glIsTransformFeedback(id);
    }

    public boolean glIsVertexArray(int array) {
        return GLES30.glIsVertexArray(array);
    }

    public void glPauseTransformFeedback() {
        GLES30.glPauseTransformFeedback();
    }

    public void glProgramParameteri(int program, int pname, int value) {
        GLES30.glProgramParameteri(program, pname, value);
    }

    public void glReadBuffer(int mode) {
        GLES30.glReadBuffer(mode);
    }

    public void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) {
        GLES30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
    }

    public void glResumeTransformFeedback() {
        GLES30.glResumeTransformFeedback();
    }

    public void glSamplerParameterf(int sampler, int pname, float param) {
        GLES30.glSamplerParameterf(sampler, pname, param);
    }

    public void glSamplerParameterfv(int sampler, int pname, FloatBuffer param) {
        GLES30.glSamplerParameterfv(sampler, pname, param);
    }

    public void glSamplerParameteri(int sampler, int pname, int param) {
        GLES30.glSamplerParameteri(sampler, pname, param);
    }

    public void glSamplerParameteriv(int sampler, int pname, IntBuffer param) {
        GLES30.glSamplerParameteriv(sampler, pname, param);
    }

    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, int offset) {
        GLES30.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, offset);
    }

    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, Buffer pixels) {
        GLES30.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int offset) {
        GLES30.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, offset);
    }

    public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, Buffer pixels) {
        GLES30.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
    }

    public void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) {
        GLES30.glTransformFeedbackVaryings(program, varyings, bufferMode);
    }

    public void glUniform1uiv(int location, int count, IntBuffer value) {
        GLES30.glUniform1uiv(location, count, value);
    }

    public void glUniform3uiv(int location, int count, IntBuffer value) {
        GLES30.glUniform3uiv(location, count, value);
    }

    public void glUniform4uiv(int location, int count, IntBuffer value) {
        GLES30.glUniform4uiv(location, count, value);
    }

    public void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) {
        GLES30.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
    }

    public void glUniformMatrix2x3fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix2x3fv(location, count, transpose, value);
    }

    public void glUniformMatrix2x4fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix2x4fv(location, count, transpose, value);
    }

    public void glUniformMatrix3x2fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix3x2fv(location, count, transpose, value);
    }

    public void glUniformMatrix3x4fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix3x4fv(location, count, transpose, value);
    }

    public void glUniformMatrix4x2fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix4x2fv(location, count, transpose, value);
    }

    public void glUniformMatrix4x3fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix4x3fv(location, count, transpose, value);
    }

    public boolean glUnmapBuffer(int target) {
        return GLES30.glUnmapBuffer(target);
    }

    public void glVertexAttribDivisor(int index, int divisor) {
        GLES30.glVertexAttribDivisor(index, divisor);
    }

    public void glVertexAttribI4i(int index, int x, int y, int z, int w) {
        GLES30.glVertexAttribI4i(index, x, y, z, w);
    }

    public void glVertexAttribI4ui(int index, int x, int y, int z, int w) {
        GLES30.glVertexAttribI4ui(index, x, y, z, w);
    }

    public void glVertexAttribIPointer(int index, int size, int type, int stride, int offset) {
        GLES30.glVertexAttribIPointer(index, size, type, stride, offset);
    }
}

