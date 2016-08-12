// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.profiling;

import com.badlogic.gdx.graphics.GL30;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

public class GL30Profiler extends GLProfiler implements GL30 {
    protected GL30Profiler(GL30 gl30) {
        super();
        this.gl30 = gl30;
    }

    public void glActiveTexture(int texture) {
        ++GL30Profiler.calls;
        this.gl30.glActiveTexture(texture);
    }

    public void glAttachShader(int program, int shader) {
        ++GL30Profiler.calls;
        this.gl30.glAttachShader(program, shader);
    }

    public void glBeginQuery(int target, int id) {
        ++GL30Profiler.calls;
        this.gl30.glBeginQuery(target, id);
    }

    public void glBeginTransformFeedback(int primitiveMode) {
        ++GL30Profiler.calls;
        this.gl30.glBeginTransformFeedback(primitiveMode);
    }

    public void glBindAttribLocation(int program, int index, String name) {
        ++GL30Profiler.calls;
        this.gl30.glBindAttribLocation(program, index, name);
    }

    public void glBindBuffer(int target, int buffer) {
        ++GL30Profiler.calls;
        this.gl30.glBindBuffer(target, buffer);
    }

    public void glBindBufferBase(int target, int index, int buffer) {
        ++GL30Profiler.calls;
        this.gl30.glBindBufferBase(target, index, buffer);
    }

    public void glBindBufferRange(int target, int index, int buffer, int offset, int size) {
        ++GL30Profiler.calls;
        this.gl30.glBindBufferRange(target, index, buffer, offset, size);
    }

    public void glBindFramebuffer(int target, int framebuffer) {
        ++GL30Profiler.calls;
        this.gl30.glBindFramebuffer(target, framebuffer);
    }

    public void glBindRenderbuffer(int target, int renderbuffer) {
        ++GL30Profiler.calls;
        this.gl30.glBindRenderbuffer(target, renderbuffer);
    }

    public void glBindSampler(int unit, int sampler) {
        ++GL30Profiler.calls;
        this.gl30.glBindSampler(unit, sampler);
    }

    public void glBindTexture(int target, int texture) {
        ++GL30Profiler.textureBindings;
        ++GL30Profiler.calls;
        this.gl30.glBindTexture(target, texture);
    }

    public void glBindTransformFeedback(int target, int id) {
        ++GL30Profiler.calls;
        this.gl30.glBindTransformFeedback(target, id);
    }

    public void glBindVertexArray(int array) {
        ++GL30Profiler.calls;
        this.gl30.glBindVertexArray(array);
    }

    public void glBlendColor(float red, float green, float blue, float alpha) {
        ++GL30Profiler.calls;
        this.gl30.glBlendColor(red, green, blue, alpha);
    }

    public void glBlendEquation(int mode) {
        ++GL30Profiler.calls;
        this.gl30.glBlendEquation(mode);
    }

    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        ++GL30Profiler.calls;
        this.gl30.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    public void glBlendFunc(int sfactor, int dfactor) {
        ++GL30Profiler.calls;
        this.gl30.glBlendFunc(sfactor, dfactor);
    }

    public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        ++GL30Profiler.calls;
        this.gl30.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        ++GL30Profiler.calls;
        this.gl30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }

    public void glBufferData(int target, int size, Buffer data, int usage) {
        ++GL30Profiler.calls;
        this.gl30.glBufferData(target, size, data, usage);
    }

    public void glBufferSubData(int target, int offset, int size, Buffer data) {
        ++GL30Profiler.calls;
        this.gl30.glBufferSubData(target, offset, size, data);
    }

    public int glCheckFramebufferStatus(int target) {
        ++GL30Profiler.calls;
        return this.gl30.glCheckFramebufferStatus(target);
    }

    public void glClear(int mask) {
        ++GL30Profiler.calls;
        this.gl30.glClear(mask);
    }

    public void glClearBufferfi(int buffer, int drawbuffer, float depth, int stencil) {
        ++GL30Profiler.calls;
        this.gl30.glClearBufferfi(buffer, drawbuffer, depth, stencil);
    }

    public void glClearBufferfv(int buffer, int drawbuffer, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glClearBufferfv(buffer, drawbuffer, value);
    }

    public void glClearBufferiv(int buffer, int drawbuffer, IntBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glClearBufferiv(buffer, drawbuffer, value);
    }

    public void glClearBufferuiv(int buffer, int drawbuffer, IntBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glClearBufferuiv(buffer, drawbuffer, value);
    }

    public void glClearColor(float red, float green, float blue, float alpha) {
        ++GL30Profiler.calls;
        this.gl30.glClearColor(red, green, blue, alpha);
    }

    public void glClearDepthf(float depth) {
        ++GL30Profiler.calls;
        this.gl30.glClearDepthf(depth);
    }

    public void glClearStencil(int s) {
        ++GL30Profiler.calls;
        this.gl30.glClearStencil(s);
    }

    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        ++GL30Profiler.calls;
        this.gl30.glColorMask(red, green, blue, alpha);
    }

    public void glCompileShader(int shader) {
        ++GL30Profiler.calls;
        this.gl30.glCompileShader(shader);
    }

    public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
        ++GL30Profiler.calls;
        this.gl30.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
    }

    public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
        ++GL30Profiler.calls;
        this.gl30.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
    }

    public void glCopyBufferSubData(int readTarget, int writeTarget, int readOffset, int writeOffset, int size) {
        ++GL30Profiler.calls;
        this.gl30.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
    }

    public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        ++GL30Profiler.calls;
        this.gl30.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    public void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glCopyTexSubImage3D(target, level, xoffset, yoffset, zoffset, x, y, width, height);
    }

    public int glCreateProgram() {
        ++GL30Profiler.calls;
        return this.gl30.glCreateProgram();
    }

    public int glCreateShader(int type) {
        ++GL30Profiler.calls;
        return this.gl30.glCreateShader(type);
    }

    public void glCullFace(int mode) {
        ++GL30Profiler.calls;
        this.gl30.glCullFace(mode);
    }

    public void glDeleteBuffer(int buffer) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteBuffer(buffer);
    }

    public void glDeleteBuffers(int n, IntBuffer buffers) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteBuffers(n, buffers);
    }

    public void glDeleteFramebuffer(int framebuffer) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteFramebuffer(framebuffer);
    }

    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteFramebuffers(n, framebuffers);
    }

    public void glDeleteProgram(int program) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteProgram(program);
    }

    public void glDeleteQueries(int n, IntBuffer ids) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteQueries(n, ids);
    }

    public void glDeleteQueries(int n, int[] ids, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteQueries(n, ids, offset);
    }

    public void glDeleteRenderbuffer(int renderbuffer) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteRenderbuffer(renderbuffer);
    }

    public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteRenderbuffers(n, renderbuffers);
    }

    public void glDeleteSamplers(int count, IntBuffer samplers) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteSamplers(count, samplers);
    }

    public void glDeleteSamplers(int count, int[] samplers, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteSamplers(count, samplers, offset);
    }

    public void glDeleteShader(int shader) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteShader(shader);
    }

    public void glDeleteTexture(int texture) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteTexture(texture);
    }

    public void glDeleteTextures(int n, IntBuffer textures) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteTextures(n, textures);
    }

    public void glDeleteTransformFeedbacks(int n, IntBuffer ids) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteTransformFeedbacks(n, ids);
    }

    public void glDeleteTransformFeedbacks(int n, int[] ids, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteTransformFeedbacks(n, ids, offset);
    }

    public void glDeleteVertexArrays(int n, IntBuffer arrays) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteVertexArrays(n, arrays);
    }

    public void glDeleteVertexArrays(int n, int[] arrays, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glDeleteVertexArrays(n, arrays, offset);
    }

    public void glDepthFunc(int func) {
        ++GL30Profiler.calls;
        this.gl30.glDepthFunc(func);
    }

    public void glDepthMask(boolean flag) {
        ++GL30Profiler.calls;
        this.gl30.glDepthMask(flag);
    }

    public void glDepthRangef(float zNear, float zFar) {
        ++GL30Profiler.calls;
        this.gl30.glDepthRangef(zNear, zFar);
    }

    public void glDetachShader(int program, int shader) {
        ++GL30Profiler.calls;
        this.gl30.glDetachShader(program, shader);
    }

    public void glDisable(int cap) {
        ++GL30Profiler.calls;
        this.gl30.glDisable(cap);
    }

    public void glDisableVertexAttribArray(int index) {
        ++GL30Profiler.calls;
        this.gl30.glDisableVertexAttribArray(index);
    }

    public void glDrawArrays(int mode, int first, int count) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawArrays(mode, first, count);
    }

    public void glDrawArraysInstanced(int mode, int first, int count, int instanceCount) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawArraysInstanced(mode, first, count, instanceCount);
    }

    public void glDrawBuffers(int n, IntBuffer bufs) {
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawBuffers(n, bufs);
    }

    public void glDrawElements(int mode, int count, int type, int indices) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawElements(mode, count, type, indices);
    }

    public void glDrawElements(int mode, int count, int type, Buffer indices) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawElements(mode, count, type, indices);
    }

    public void glDrawElementsInstanced(int mode, int count, int type, int indicesOffset, int instanceCount) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawElementsInstanced(mode, count, type, indicesOffset, instanceCount);
    }

    public void glDrawRangeElements(int mode, int start, int end, int count, int type, int offset) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawRangeElements(mode, start, end, count, type, offset);
    }

    public void glDrawRangeElements(int mode, int start, int end, int count, int type, Buffer indices) {
        GL30Profiler.vertexCount.put(((float)count));
        ++GL30Profiler.drawCalls;
        ++GL30Profiler.calls;
        this.gl30.glDrawRangeElements(mode, start, end, count, type, indices);
    }

    public void glEnable(int cap) {
        ++GL30Profiler.calls;
        this.gl30.glEnable(cap);
    }

    public void glEnableVertexAttribArray(int index) {
        ++GL30Profiler.calls;
        this.gl30.glEnableVertexAttribArray(index);
    }

    public void glEndQuery(int target) {
        ++GL30Profiler.calls;
        this.gl30.glEndQuery(target);
    }

    public void glEndTransformFeedback() {
        ++GL30Profiler.calls;
        this.gl30.glEndTransformFeedback();
    }

    public void glFinish() {
        ++GL30Profiler.calls;
        this.gl30.glFinish();
    }

    public void glFlush() {
        ++GL30Profiler.calls;
        this.gl30.glFlush();
    }

    public void glFlushMappedBufferRange(int target, int offset, int length) {
        ++GL30Profiler.calls;
        this.gl30.glFlushMappedBufferRange(target, offset, length);
    }

    public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
        ++GL30Profiler.calls;
        this.gl30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
    }

    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        ++GL30Profiler.calls;
        this.gl30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) {
        ++GL30Profiler.calls;
        this.gl30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
    }

    public void glFrontFace(int mode) {
        ++GL30Profiler.calls;
        this.gl30.glFrontFace(mode);
    }

    public int glGenBuffer() {
        ++GL30Profiler.calls;
        return this.gl30.glGenBuffer();
    }

    public void glGenBuffers(int n, IntBuffer buffers) {
        ++GL30Profiler.calls;
        this.gl30.glGenBuffers(n, buffers);
    }

    public int glGenFramebuffer() {
        ++GL30Profiler.calls;
        return this.gl30.glGenFramebuffer();
    }

    public void glGenFramebuffers(int n, IntBuffer framebuffers) {
        ++GL30Profiler.calls;
        this.gl30.glGenFramebuffers(n, framebuffers);
    }

    public void glGenQueries(int n, IntBuffer ids) {
        ++GL30Profiler.calls;
        this.gl30.glGenQueries(n, ids);
    }

    public void glGenQueries(int n, int[] ids, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glGenQueries(n, ids, offset);
    }

    public int glGenRenderbuffer() {
        ++GL30Profiler.calls;
        return this.gl30.glGenRenderbuffer();
    }

    public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
        ++GL30Profiler.calls;
        this.gl30.glGenRenderbuffers(n, renderbuffers);
    }

    public void glGenSamplers(int count, IntBuffer samplers) {
        ++GL30Profiler.calls;
        this.gl30.glGenSamplers(count, samplers);
    }

    public void glGenSamplers(int count, int[] samplers, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glGenSamplers(count, samplers, offset);
    }

    public int glGenTexture() {
        ++GL30Profiler.calls;
        return this.gl30.glGenTexture();
    }

    public void glGenTextures(int n, IntBuffer textures) {
        ++GL30Profiler.calls;
        this.gl30.glGenTextures(n, textures);
    }

    public void glGenTransformFeedbacks(int n, IntBuffer ids) {
        ++GL30Profiler.calls;
        this.gl30.glGenTransformFeedbacks(n, ids);
    }

    public void glGenTransformFeedbacks(int n, int[] ids, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glGenTransformFeedbacks(n, ids, offset);
    }

    public void glGenVertexArrays(int n, IntBuffer arrays) {
        ++GL30Profiler.calls;
        this.gl30.glGenVertexArrays(n, arrays);
    }

    public void glGenVertexArrays(int n, int[] arrays, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glGenVertexArrays(n, arrays, offset);
    }

    public void glGenerateMipmap(int target) {
        ++GL30Profiler.calls;
        this.gl30.glGenerateMipmap(target);
    }

    public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
        ++GL30Profiler.calls;
        return this.gl30.glGetActiveAttrib(program, index, size, type);
    }

    public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
        ++GL30Profiler.calls;
        return this.gl30.glGetActiveUniform(program, index, size, type);
    }

    public String glGetActiveUniformBlockName(int program, int uniformBlockIndex) {
        ++GL30Profiler.calls;
        return this.gl30.glGetActiveUniformBlockName(program, uniformBlockIndex);
    }

    public void glGetActiveUniformBlockName(int program, int uniformBlockIndex, Buffer length, Buffer uniformBlockName) {
        ++GL30Profiler.calls;
        this.gl30.glGetActiveUniformBlockName(program, uniformBlockIndex, length, uniformBlockName);
    }

    public void glGetActiveUniformBlockiv(int program, int uniformBlockIndex, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params);
    }

    public void glGetActiveUniformsiv(int program, int uniformCount, IntBuffer uniformIndices, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetActiveUniformsiv(program, uniformCount, uniformIndices, pname, params);
    }

    public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
        ++GL30Profiler.calls;
        this.gl30.glGetAttachedShaders(program, maxcount, count, shaders);
    }

    public int glGetAttribLocation(int program, String name) {
        ++GL30Profiler.calls;
        return this.gl30.glGetAttribLocation(program, name);
    }

    public void glGetBooleanv(int pname, Buffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetBooleanv(pname, params);
    }

    public void glGetBufferParameteri64v(int target, int pname, LongBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetBufferParameteri64v(target, pname, params);
    }

    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetBufferParameteriv(target, pname, params);
    }

    public Buffer glGetBufferPointerv(int target, int pname) {
        ++GL30Profiler.calls;
        return this.gl30.glGetBufferPointerv(target, pname);
    }

    public int glGetError() {
        ++GL30Profiler.calls;
        return this.gl30.glGetError();
    }

    public void glGetFloatv(int pname, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetFloatv(pname, params);
    }

    public int glGetFragDataLocation(int program, String name) {
        ++GL30Profiler.calls;
        return this.gl30.glGetFragDataLocation(program, name);
    }

    public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    public void glGetInteger64v(int pname, LongBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetInteger64v(pname, params);
    }

    public void glGetIntegerv(int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetIntegerv(pname, params);
    }

    public String glGetProgramInfoLog(int program) {
        ++GL30Profiler.calls;
        return this.gl30.glGetProgramInfoLog(program);
    }

    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetProgramiv(program, pname, params);
    }

    public void glGetQueryObjectuiv(int id, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetQueryObjectuiv(id, pname, params);
    }

    public void glGetQueryiv(int target, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetQueryiv(target, pname, params);
    }

    public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetRenderbufferParameteriv(target, pname, params);
    }

    public void glGetSamplerParameterfv(int sampler, int pname, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetSamplerParameterfv(sampler, pname, params);
    }

    public void glGetSamplerParameteriv(int sampler, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetSamplerParameteriv(sampler, pname, params);
    }

    public String glGetShaderInfoLog(int shader) {
        ++GL30Profiler.calls;
        return this.gl30.glGetShaderInfoLog(shader);
    }

    public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        ++GL30Profiler.calls;
        this.gl30.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
    }

    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetShaderiv(shader, pname, params);
    }

    public String glGetString(int name) {
        ++GL30Profiler.calls;
        return this.gl30.glGetString(name);
    }

    public String glGetStringi(int name, int index) {
        ++GL30Profiler.calls;
        return this.gl30.glGetStringi(name, index);
    }

    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetTexParameterfv(target, pname, params);
    }

    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetTexParameteriv(target, pname, params);
    }

    public int glGetUniformBlockIndex(int program, String uniformBlockName) {
        ++GL30Profiler.calls;
        return this.gl30.glGetUniformBlockIndex(program, uniformBlockName);
    }

    public void glGetUniformIndices(int program, String[] uniformNames, IntBuffer uniformIndices) {
        ++GL30Profiler.calls;
        this.gl30.glGetUniformIndices(program, uniformNames, uniformIndices);
    }

    public int glGetUniformLocation(int program, String name) {
        ++GL30Profiler.calls;
        return this.gl30.glGetUniformLocation(program, name);
    }

    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetUniformfv(program, location, params);
    }

    public void glGetUniformiv(int program, int location, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetUniformiv(program, location, params);
    }

    public void glGetUniformuiv(int program, int location, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetUniformuiv(program, location, params);
    }

    public void glGetVertexAttribIiv(int index, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetVertexAttribIiv(index, pname, params);
    }

    public void glGetVertexAttribIuiv(int index, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetVertexAttribIuiv(index, pname, params);
    }

    public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
        ++GL30Profiler.calls;
        this.gl30.glGetVertexAttribPointerv(index, pname, pointer);
    }

    public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetVertexAttribfv(index, pname, params);
    }

    public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glGetVertexAttribiv(index, pname, params);
    }

    public void glHint(int target, int mode) {
        ++GL30Profiler.calls;
        this.gl30.glHint(target, mode);
    }

    public void glInvalidateFramebuffer(int target, int numAttachments, IntBuffer attachments) {
        ++GL30Profiler.calls;
        this.gl30.glInvalidateFramebuffer(target, numAttachments, attachments);
    }

    public void glInvalidateSubFramebuffer(int target, int numAttachments, IntBuffer attachments, int x, int y, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glInvalidateSubFramebuffer(target, numAttachments, attachments, x, y, width, height);
    }

    public boolean glIsBuffer(int buffer) {
        ++GL30Profiler.calls;
        return this.gl30.glIsBuffer(buffer);
    }

    public boolean glIsEnabled(int cap) {
        ++GL30Profiler.calls;
        return this.gl30.glIsEnabled(cap);
    }

    public boolean glIsFramebuffer(int framebuffer) {
        ++GL30Profiler.calls;
        return this.gl30.glIsFramebuffer(framebuffer);
    }

    public boolean glIsProgram(int program) {
        ++GL30Profiler.calls;
        return this.gl30.glIsProgram(program);
    }

    public boolean glIsQuery(int id) {
        ++GL30Profiler.calls;
        return this.gl30.glIsQuery(id);
    }

    public boolean glIsRenderbuffer(int renderbuffer) {
        ++GL30Profiler.calls;
        return this.gl30.glIsRenderbuffer(renderbuffer);
    }

    public boolean glIsSampler(int sampler) {
        ++GL30Profiler.calls;
        return this.gl30.glIsSampler(sampler);
    }

    public boolean glIsShader(int shader) {
        ++GL30Profiler.calls;
        return this.gl30.glIsShader(shader);
    }

    public boolean glIsTexture(int texture) {
        ++GL30Profiler.calls;
        return this.gl30.glIsTexture(texture);
    }

    public boolean glIsTransformFeedback(int id) {
        ++GL30Profiler.calls;
        return this.gl30.glIsTransformFeedback(id);
    }

    public boolean glIsVertexArray(int array) {
        ++GL30Profiler.calls;
        return this.gl30.glIsVertexArray(array);
    }

    public void glLineWidth(float width) {
        ++GL30Profiler.calls;
        this.gl30.glLineWidth(width);
    }

    public void glLinkProgram(int program) {
        ++GL30Profiler.calls;
        this.gl30.glLinkProgram(program);
    }

    public void glPauseTransformFeedback() {
        ++GL30Profiler.calls;
        this.gl30.glPauseTransformFeedback();
    }

    public void glPixelStorei(int pname, int param) {
        ++GL30Profiler.calls;
        this.gl30.glPixelStorei(pname, param);
    }

    public void glPolygonOffset(float factor, float units) {
        ++GL30Profiler.calls;
        this.gl30.glPolygonOffset(factor, units);
    }

    public void glProgramParameteri(int program, int pname, int value) {
        ++GL30Profiler.calls;
        this.gl30.glProgramParameteri(program, pname, value);
    }

    public void glReadBuffer(int mode) {
        ++GL30Profiler.calls;
        this.gl30.glReadBuffer(mode);
    }

    public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
        ++GL30Profiler.calls;
        this.gl30.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public void glReleaseShaderCompiler() {
        ++GL30Profiler.calls;
        this.gl30.glReleaseShaderCompiler();
    }

    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glRenderbufferStorage(target, internalformat, width, height);
    }

    public void glRenderbufferStorageMultisample(int target, int samples, int internalformat, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
    }

    public void glResumeTransformFeedback() {
        ++GL30Profiler.calls;
        this.gl30.glResumeTransformFeedback();
    }

    public void glSampleCoverage(float value, boolean invert) {
        ++GL30Profiler.calls;
        this.gl30.glSampleCoverage(value, invert);
    }

    public void glSamplerParameterf(int sampler, int pname, float param) {
        ++GL30Profiler.calls;
        this.gl30.glSamplerParameterf(sampler, pname, param);
    }

    public void glSamplerParameterfv(int sampler, int pname, FloatBuffer param) {
        ++GL30Profiler.calls;
        this.gl30.glSamplerParameterfv(sampler, pname, param);
    }

    public void glSamplerParameteri(int sampler, int pname, int param) {
        ++GL30Profiler.calls;
        this.gl30.glSamplerParameteri(sampler, pname, param);
    }

    public void glSamplerParameteriv(int sampler, int pname, IntBuffer param) {
        ++GL30Profiler.calls;
        this.gl30.glSamplerParameteriv(sampler, pname, param);
    }

    public void glScissor(int x, int y, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glScissor(x, y, width, height);
    }

    public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        ++GL30Profiler.calls;
        this.gl30.glShaderBinary(n, shaders, binaryformat, binary, length);
    }

    public void glShaderSource(int shader, String string) {
        ++GL30Profiler.calls;
        this.gl30.glShaderSource(shader, string);
    }

    public void glStencilFunc(int func, int ref, int mask) {
        ++GL30Profiler.calls;
        this.gl30.glStencilFunc(func, ref, mask);
    }

    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        ++GL30Profiler.calls;
        this.gl30.glStencilFuncSeparate(face, func, ref, mask);
    }

    public void glStencilMask(int mask) {
        ++GL30Profiler.calls;
        this.gl30.glStencilMask(mask);
    }

    public void glStencilMaskSeparate(int face, int mask) {
        ++GL30Profiler.calls;
        this.gl30.glStencilMaskSeparate(face, mask);
    }

    public void glStencilOp(int fail, int zfail, int zpass) {
        ++GL30Profiler.calls;
        this.gl30.glStencilOp(fail, zfail, zpass);
    }

    public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
        ++GL30Profiler.calls;
        this.gl30.glStencilOpSeparate(face, fail, zfail, zpass);
    }

    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        ++GL30Profiler.calls;
        this.gl30.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, offset);
    }

    public void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, Buffer pixels) {
        ++GL30Profiler.calls;
        this.gl30.glTexImage3D(target, level, internalformat, width, height, depth, border, format, type, pixels);
    }

    public void glTexParameterf(int target, int pname, float param) {
        ++GL30Profiler.calls;
        this.gl30.glTexParameterf(target, pname, param);
    }

    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glTexParameterfv(target, pname, params);
    }

    public void glTexParameteri(int target, int pname, int param) {
        ++GL30Profiler.calls;
        this.gl30.glTexParameteri(target, pname, param);
    }

    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        ++GL30Profiler.calls;
        this.gl30.glTexParameteriv(target, pname, params);
    }

    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        ++GL30Profiler.calls;
        this.gl30.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, offset);
    }

    public void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, Buffer pixels) {
        ++GL30Profiler.calls;
        this.gl30.glTexSubImage3D(target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
    }

    public void glTransformFeedbackVaryings(int program, String[] varyings, int bufferMode) {
        ++GL30Profiler.calls;
        this.gl30.glTransformFeedbackVaryings(program, varyings, bufferMode);
    }

    public void glUniform1f(int location, float x) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1f(location, x);
    }

    public void glUniform1fv(int location, int count, FloatBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1fv(location, count, v);
    }

    public void glUniform1fv(int location, int count, float[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1fv(location, count, v, offset);
    }

    public void glUniform1i(int location, int x) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1i(location, x);
    }

    public void glUniform1iv(int location, int count, IntBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1iv(location, count, v);
    }

    public void glUniform1iv(int location, int count, int[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1iv(location, count, v, offset);
    }

    public void glUniform1uiv(int location, int count, IntBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniform1uiv(location, count, value);
    }

    public void glUniform2f(int location, float x, float y) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2f(location, x, y);
    }

    public void glUniform2fv(int location, int count, FloatBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2fv(location, count, v);
    }

    public void glUniform2fv(int location, int count, float[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2fv(location, count, v, offset);
    }

    public void glUniform2i(int location, int x, int y) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2i(location, x, y);
    }

    public void glUniform2iv(int location, int count, IntBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2iv(location, count, v);
    }

    public void glUniform2iv(int location, int count, int[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform2iv(location, count, v, offset);
    }

    public void glUniform3f(int location, float x, float y, float z) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3f(location, x, y, z);
    }

    public void glUniform3fv(int location, int count, FloatBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3fv(location, count, v);
    }

    public void glUniform3fv(int location, int count, float[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3fv(location, count, v, offset);
    }

    public void glUniform3i(int location, int x, int y, int z) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3i(location, x, y, z);
    }

    public void glUniform3iv(int location, int count, IntBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3iv(location, count, v);
    }

    public void glUniform3iv(int location, int count, int[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3iv(location, count, v, offset);
    }

    public void glUniform3uiv(int location, int count, IntBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniform3uiv(location, count, value);
    }

    public void glUniform4f(int location, float x, float y, float z, float w) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4f(location, x, y, z, w);
    }

    public void glUniform4fv(int location, int count, FloatBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4fv(location, count, v);
    }

    public void glUniform4fv(int location, int count, float[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4fv(location, count, v, offset);
    }

    public void glUniform4i(int location, int x, int y, int z, int w) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4i(location, x, y, z, w);
    }

    public void glUniform4iv(int location, int count, IntBuffer v) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4iv(location, count, v);
    }

    public void glUniform4iv(int location, int count, int[] v, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4iv(location, count, v, offset);
    }

    public void glUniform4uiv(int location, int count, IntBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniform4uiv(location, count, value);
    }

    public void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) {
        ++GL30Profiler.calls;
        this.gl30.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
    }

    public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix2fv(location, count, transpose, value);
    }

    public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix2fv(location, count, transpose, value, offset);
    }

    public void glUniformMatrix2x3fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix2x3fv(location, count, transpose, value);
    }

    public void glUniformMatrix2x4fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix2x4fv(location, count, transpose, value);
    }

    public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix3fv(location, count, transpose, value);
    }

    public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix3fv(location, count, transpose, value, offset);
    }

    public void glUniformMatrix3x2fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix3x2fv(location, count, transpose, value);
    }

    public void glUniformMatrix3x4fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix3x4fv(location, count, transpose, value);
    }

    public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix4fv(location, count, transpose, value);
    }

    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix4fv(location, count, transpose, value, offset);
    }

    public void glUniformMatrix4x2fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix4x2fv(location, count, transpose, value);
    }

    public void glUniformMatrix4x3fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL30Profiler.calls;
        this.gl30.glUniformMatrix4x3fv(location, count, transpose, value);
    }

    public boolean glUnmapBuffer(int target) {
        ++GL30Profiler.calls;
        return this.gl30.glUnmapBuffer(target);
    }

    public void glUseProgram(int program) {
        ++GL30Profiler.shaderSwitches;
        ++GL30Profiler.calls;
        this.gl30.glUseProgram(program);
    }

    public void glValidateProgram(int program) {
        ++GL30Profiler.calls;
        this.gl30.glValidateProgram(program);
    }

    public void glVertexAttrib1f(int indx, float x) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib1f(indx, x);
    }

    public void glVertexAttrib1fv(int indx, FloatBuffer values) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib1fv(indx, values);
    }

    public void glVertexAttrib2f(int indx, float x, float y) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib2f(indx, x, y);
    }

    public void glVertexAttrib2fv(int indx, FloatBuffer values) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib2fv(indx, values);
    }

    public void glVertexAttrib3f(int indx, float x, float y, float z) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib3f(indx, x, y, z);
    }

    public void glVertexAttrib3fv(int indx, FloatBuffer values) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib3fv(indx, values);
    }

    public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib4f(indx, x, y, z, w);
    }

    public void glVertexAttrib4fv(int indx, FloatBuffer values) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttrib4fv(indx, values);
    }

    public void glVertexAttribDivisor(int index, int divisor) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribDivisor(index, divisor);
    }

    public void glVertexAttribI4i(int index, int x, int y, int z, int w) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribI4i(index, x, y, z, w);
    }

    public void glVertexAttribI4ui(int index, int x, int y, int z, int w) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribI4ui(index, x, y, z, w);
    }

    public void glVertexAttribIPointer(int index, int size, int type, int stride, int offset) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribIPointer(index, size, type, stride, offset);
    }

    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
        ++GL30Profiler.calls;
        this.gl30.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    public void glViewport(int x, int y, int width, int height) {
        ++GL30Profiler.calls;
        this.gl30.glViewport(x, y, width, height);
    }
}

