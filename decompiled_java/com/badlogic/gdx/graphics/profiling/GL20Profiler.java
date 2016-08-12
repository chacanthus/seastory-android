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

import com.badlogic.gdx.graphics.GL20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GL20Profiler extends GLProfiler implements GL20 {
    protected GL20Profiler(GL20 gl20) {
        super();
        this.gl20 = gl20;
    }

    public void glActiveTexture(int texture) {
        ++GL20Profiler.calls;
        this.gl20.glActiveTexture(texture);
    }

    public void glAttachShader(int program, int shader) {
        ++GL20Profiler.calls;
        this.gl20.glAttachShader(program, shader);
    }

    public void glBindAttribLocation(int program, int index, String name) {
        ++GL20Profiler.calls;
        this.gl20.glBindAttribLocation(program, index, name);
    }

    public void glBindBuffer(int target, int buffer) {
        ++GL20Profiler.calls;
        this.gl20.glBindBuffer(target, buffer);
    }

    public void glBindFramebuffer(int target, int framebuffer) {
        ++GL20Profiler.calls;
        this.gl20.glBindFramebuffer(target, framebuffer);
    }

    public void glBindRenderbuffer(int target, int renderbuffer) {
        ++GL20Profiler.calls;
        this.gl20.glBindRenderbuffer(target, renderbuffer);
    }

    public void glBindTexture(int target, int texture) {
        ++GL20Profiler.textureBindings;
        ++GL20Profiler.calls;
        this.gl20.glBindTexture(target, texture);
    }

    public void glBlendColor(float red, float green, float blue, float alpha) {
        ++GL20Profiler.calls;
        this.gl20.glBlendColor(red, green, blue, alpha);
    }

    public void glBlendEquation(int mode) {
        ++GL20Profiler.calls;
        this.gl20.glBlendEquation(mode);
    }

    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        ++GL20Profiler.calls;
        this.gl20.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    public void glBlendFunc(int sfactor, int dfactor) {
        ++GL20Profiler.calls;
        this.gl20.glBlendFunc(sfactor, dfactor);
    }

    public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        ++GL20Profiler.calls;
        this.gl20.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    public void glBufferData(int target, int size, Buffer data, int usage) {
        ++GL20Profiler.calls;
        this.gl20.glBufferData(target, size, data, usage);
    }

    public void glBufferSubData(int target, int offset, int size, Buffer data) {
        ++GL20Profiler.calls;
        this.gl20.glBufferSubData(target, offset, size, data);
    }

    public int glCheckFramebufferStatus(int target) {
        ++GL20Profiler.calls;
        return this.gl20.glCheckFramebufferStatus(target);
    }

    public void glClear(int mask) {
        ++GL20Profiler.calls;
        this.gl20.glClear(mask);
    }

    public void glClearColor(float red, float green, float blue, float alpha) {
        ++GL20Profiler.calls;
        this.gl20.glClearColor(red, green, blue, alpha);
    }

    public void glClearDepthf(float depth) {
        ++GL20Profiler.calls;
        this.gl20.glClearDepthf(depth);
    }

    public void glClearStencil(int s) {
        ++GL20Profiler.calls;
        this.gl20.glClearStencil(s);
    }

    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        ++GL20Profiler.calls;
        this.gl20.glColorMask(red, green, blue, alpha);
    }

    public void glCompileShader(int shader) {
        ++GL20Profiler.calls;
        this.gl20.glCompileShader(shader);
    }

    public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
        ++GL20Profiler.calls;
        this.gl20.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
    }

    public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
        ++GL20Profiler.calls;
        this.gl20.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
    }

    public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        ++GL20Profiler.calls;
        this.gl20.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        ++GL20Profiler.calls;
        this.gl20.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    public int glCreateProgram() {
        ++GL20Profiler.calls;
        return this.gl20.glCreateProgram();
    }

    public int glCreateShader(int type) {
        ++GL20Profiler.calls;
        return this.gl20.glCreateShader(type);
    }

    public void glCullFace(int mode) {
        ++GL20Profiler.calls;
        this.gl20.glCullFace(mode);
    }

    public void glDeleteBuffer(int buffer) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteBuffer(buffer);
    }

    public void glDeleteBuffers(int n, IntBuffer buffers) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteBuffers(n, buffers);
    }

    public void glDeleteFramebuffer(int framebuffer) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteFramebuffer(framebuffer);
    }

    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteFramebuffers(n, framebuffers);
    }

    public void glDeleteProgram(int program) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteProgram(program);
    }

    public void glDeleteRenderbuffer(int renderbuffer) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteRenderbuffer(renderbuffer);
    }

    public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteRenderbuffers(n, renderbuffers);
    }

    public void glDeleteShader(int shader) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteShader(shader);
    }

    public void glDeleteTexture(int texture) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteTexture(texture);
    }

    public void glDeleteTextures(int n, IntBuffer textures) {
        ++GL20Profiler.calls;
        this.gl20.glDeleteTextures(n, textures);
    }

    public void glDepthFunc(int func) {
        ++GL20Profiler.calls;
        this.gl20.glDepthFunc(func);
    }

    public void glDepthMask(boolean flag) {
        ++GL20Profiler.calls;
        this.gl20.glDepthMask(flag);
    }

    public void glDepthRangef(float zNear, float zFar) {
        ++GL20Profiler.calls;
        this.gl20.glDepthRangef(zNear, zFar);
    }

    public void glDetachShader(int program, int shader) {
        ++GL20Profiler.calls;
        this.gl20.glDetachShader(program, shader);
    }

    public void glDisable(int cap) {
        ++GL20Profiler.calls;
        this.gl20.glDisable(cap);
    }

    public void glDisableVertexAttribArray(int index) {
        ++GL20Profiler.calls;
        this.gl20.glDisableVertexAttribArray(index);
    }

    public void glDrawArrays(int mode, int first, int count) {
        GL20Profiler.vertexCount.put(((float)count));
        ++GL20Profiler.drawCalls;
        ++GL20Profiler.calls;
        this.gl20.glDrawArrays(mode, first, count);
    }

    public void glDrawElements(int mode, int count, int type, int indices) {
        GL20Profiler.vertexCount.put(((float)count));
        ++GL20Profiler.drawCalls;
        ++GL20Profiler.calls;
        this.gl20.glDrawElements(mode, count, type, indices);
    }

    public void glDrawElements(int mode, int count, int type, Buffer indices) {
        GL20Profiler.vertexCount.put(((float)count));
        ++GL20Profiler.drawCalls;
        ++GL20Profiler.calls;
        this.gl20.glDrawElements(mode, count, type, indices);
    }

    public void glEnable(int cap) {
        ++GL20Profiler.calls;
        this.gl20.glEnable(cap);
    }

    public void glEnableVertexAttribArray(int index) {
        ++GL20Profiler.calls;
        this.gl20.glEnableVertexAttribArray(index);
    }

    public void glFinish() {
        ++GL20Profiler.calls;
        this.gl20.glFinish();
    }

    public void glFlush() {
        ++GL20Profiler.calls;
        this.gl20.glFlush();
    }

    public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
        ++GL20Profiler.calls;
        this.gl20.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
    }

    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        ++GL20Profiler.calls;
        this.gl20.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    public void glFrontFace(int mode) {
        ++GL20Profiler.calls;
        this.gl20.glFrontFace(mode);
    }

    public int glGenBuffer() {
        ++GL20Profiler.calls;
        return this.gl20.glGenBuffer();
    }

    public void glGenBuffers(int n, IntBuffer buffers) {
        ++GL20Profiler.calls;
        this.gl20.glGenBuffers(n, buffers);
    }

    public int glGenFramebuffer() {
        ++GL20Profiler.calls;
        return this.gl20.glGenFramebuffer();
    }

    public void glGenFramebuffers(int n, IntBuffer framebuffers) {
        ++GL20Profiler.calls;
        this.gl20.glGenFramebuffers(n, framebuffers);
    }

    public int glGenRenderbuffer() {
        ++GL20Profiler.calls;
        return this.gl20.glGenRenderbuffer();
    }

    public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
        ++GL20Profiler.calls;
        this.gl20.glGenRenderbuffers(n, renderbuffers);
    }

    public int glGenTexture() {
        ++GL20Profiler.calls;
        return this.gl20.glGenTexture();
    }

    public void glGenTextures(int n, IntBuffer textures) {
        ++GL20Profiler.calls;
        this.gl20.glGenTextures(n, textures);
    }

    public void glGenerateMipmap(int target) {
        ++GL20Profiler.calls;
        this.gl20.glGenerateMipmap(target);
    }

    public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
        ++GL20Profiler.calls;
        return this.gl20.glGetActiveAttrib(program, index, size, type);
    }

    public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
        ++GL20Profiler.calls;
        return this.gl20.glGetActiveUniform(program, index, size, type);
    }

    public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
        ++GL20Profiler.calls;
        this.gl20.glGetAttachedShaders(program, maxcount, count, shaders);
    }

    public int glGetAttribLocation(int program, String name) {
        ++GL20Profiler.calls;
        return this.gl20.glGetAttribLocation(program, name);
    }

    public void glGetBooleanv(int pname, Buffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetBooleanv(pname, params);
    }

    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetBufferParameteriv(target, pname, params);
    }

    public int glGetError() {
        ++GL20Profiler.calls;
        return this.gl20.glGetError();
    }

    public void glGetFloatv(int pname, FloatBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetFloatv(pname, params);
    }

    public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    public void glGetIntegerv(int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetIntegerv(pname, params);
    }

    public String glGetProgramInfoLog(int program) {
        ++GL20Profiler.calls;
        return this.gl20.glGetProgramInfoLog(program);
    }

    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetProgramiv(program, pname, params);
    }

    public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetRenderbufferParameteriv(target, pname, params);
    }

    public String glGetShaderInfoLog(int shader) {
        ++GL20Profiler.calls;
        return this.gl20.glGetShaderInfoLog(shader);
    }

    public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        ++GL20Profiler.calls;
        this.gl20.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
    }

    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetShaderiv(shader, pname, params);
    }

    public String glGetString(int name) {
        ++GL20Profiler.calls;
        return this.gl20.glGetString(name);
    }

    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetTexParameterfv(target, pname, params);
    }

    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetTexParameteriv(target, pname, params);
    }

    public int glGetUniformLocation(int program, String name) {
        ++GL20Profiler.calls;
        return this.gl20.glGetUniformLocation(program, name);
    }

    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetUniformfv(program, location, params);
    }

    public void glGetUniformiv(int program, int location, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetUniformiv(program, location, params);
    }

    public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
        ++GL20Profiler.calls;
        this.gl20.glGetVertexAttribPointerv(index, pname, pointer);
    }

    public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetVertexAttribfv(index, pname, params);
    }

    public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glGetVertexAttribiv(index, pname, params);
    }

    public void glHint(int target, int mode) {
        ++GL20Profiler.calls;
        this.gl20.glHint(target, mode);
    }

    public boolean glIsBuffer(int buffer) {
        ++GL20Profiler.calls;
        return this.gl20.glIsBuffer(buffer);
    }

    public boolean glIsEnabled(int cap) {
        ++GL20Profiler.calls;
        return this.gl20.glIsEnabled(cap);
    }

    public boolean glIsFramebuffer(int framebuffer) {
        ++GL20Profiler.calls;
        return this.gl20.glIsFramebuffer(framebuffer);
    }

    public boolean glIsProgram(int program) {
        ++GL20Profiler.calls;
        return this.gl20.glIsProgram(program);
    }

    public boolean glIsRenderbuffer(int renderbuffer) {
        ++GL20Profiler.calls;
        return this.gl20.glIsRenderbuffer(renderbuffer);
    }

    public boolean glIsShader(int shader) {
        ++GL20Profiler.calls;
        return this.gl20.glIsShader(shader);
    }

    public boolean glIsTexture(int texture) {
        ++GL20Profiler.calls;
        return this.gl20.glIsTexture(texture);
    }

    public void glLineWidth(float width) {
        ++GL20Profiler.calls;
        this.gl20.glLineWidth(width);
    }

    public void glLinkProgram(int program) {
        ++GL20Profiler.calls;
        this.gl20.glLinkProgram(program);
    }

    public void glPixelStorei(int pname, int param) {
        ++GL20Profiler.calls;
        this.gl20.glPixelStorei(pname, param);
    }

    public void glPolygonOffset(float factor, float units) {
        ++GL20Profiler.calls;
        this.gl20.glPolygonOffset(factor, units);
    }

    public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
        ++GL20Profiler.calls;
        this.gl20.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public void glReleaseShaderCompiler() {
        ++GL20Profiler.calls;
        this.gl20.glReleaseShaderCompiler();
    }

    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        ++GL20Profiler.calls;
        this.gl20.glRenderbufferStorage(target, internalformat, width, height);
    }

    public void glSampleCoverage(float value, boolean invert) {
        ++GL20Profiler.calls;
        this.gl20.glSampleCoverage(value, invert);
    }

    public void glScissor(int x, int y, int width, int height) {
        ++GL20Profiler.calls;
        this.gl20.glScissor(x, y, width, height);
    }

    public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        ++GL20Profiler.calls;
        this.gl20.glShaderBinary(n, shaders, binaryformat, binary, length);
    }

    public void glShaderSource(int shader, String string) {
        ++GL20Profiler.calls;
        this.gl20.glShaderSource(shader, string);
    }

    public void glStencilFunc(int func, int ref, int mask) {
        ++GL20Profiler.calls;
        this.gl20.glStencilFunc(func, ref, mask);
    }

    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        ++GL20Profiler.calls;
        this.gl20.glStencilFuncSeparate(face, func, ref, mask);
    }

    public void glStencilMask(int mask) {
        ++GL20Profiler.calls;
        this.gl20.glStencilMask(mask);
    }

    public void glStencilMaskSeparate(int face, int mask) {
        ++GL20Profiler.calls;
        this.gl20.glStencilMaskSeparate(face, mask);
    }

    public void glStencilOp(int fail, int zfail, int zpass) {
        ++GL20Profiler.calls;
        this.gl20.glStencilOp(fail, zfail, zpass);
    }

    public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
        ++GL20Profiler.calls;
        this.gl20.glStencilOpSeparate(face, fail, zfail, zpass);
    }

    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        ++GL20Profiler.calls;
        this.gl20.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public void glTexParameterf(int target, int pname, float param) {
        ++GL20Profiler.calls;
        this.gl20.glTexParameterf(target, pname, param);
    }

    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glTexParameterfv(target, pname, params);
    }

    public void glTexParameteri(int target, int pname, int param) {
        ++GL20Profiler.calls;
        this.gl20.glTexParameteri(target, pname, param);
    }

    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        ++GL20Profiler.calls;
        this.gl20.glTexParameteriv(target, pname, params);
    }

    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        ++GL20Profiler.calls;
        this.gl20.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    public void glUniform1f(int location, float x) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1f(location, x);
    }

    public void glUniform1fv(int location, int count, FloatBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1fv(location, count, v);
    }

    public void glUniform1fv(int location, int count, float[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1fv(location, count, v, offset);
    }

    public void glUniform1i(int location, int x) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1i(location, x);
    }

    public void glUniform1iv(int location, int count, IntBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1iv(location, count, v);
    }

    public void glUniform1iv(int location, int count, int[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform1iv(location, count, v, offset);
    }

    public void glUniform2f(int location, float x, float y) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2f(location, x, y);
    }

    public void glUniform2fv(int location, int count, FloatBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2fv(location, count, v);
    }

    public void glUniform2fv(int location, int count, float[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2fv(location, count, v, offset);
    }

    public void glUniform2i(int location, int x, int y) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2i(location, x, y);
    }

    public void glUniform2iv(int location, int count, IntBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2iv(location, count, v);
    }

    public void glUniform2iv(int location, int count, int[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform2iv(location, count, v, offset);
    }

    public void glUniform3f(int location, float x, float y, float z) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3f(location, x, y, z);
    }

    public void glUniform3fv(int location, int count, FloatBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3fv(location, count, v);
    }

    public void glUniform3fv(int location, int count, float[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3fv(location, count, v, offset);
    }

    public void glUniform3i(int location, int x, int y, int z) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3i(location, x, y, z);
    }

    public void glUniform3iv(int location, int count, IntBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3iv(location, count, v);
    }

    public void glUniform3iv(int location, int count, int[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform3iv(location, count, v, offset);
    }

    public void glUniform4f(int location, float x, float y, float z, float w) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4f(location, x, y, z, w);
    }

    public void glUniform4fv(int location, int count, FloatBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4fv(location, count, v);
    }

    public void glUniform4fv(int location, int count, float[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4fv(location, count, v, offset);
    }

    public void glUniform4i(int location, int x, int y, int z, int w) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4i(location, x, y, z, w);
    }

    public void glUniform4iv(int location, int count, IntBuffer v) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4iv(location, count, v);
    }

    public void glUniform4iv(int location, int count, int[] v, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniform4iv(location, count, v, offset);
    }

    public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix2fv(location, count, transpose, value);
    }

    public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix2fv(location, count, transpose, value, offset);
    }

    public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix3fv(location, count, transpose, value);
    }

    public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix3fv(location, count, transpose, value, offset);
    }

    public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix4fv(location, count, transpose, value);
    }

    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
        ++GL20Profiler.calls;
        this.gl20.glUniformMatrix4fv(location, count, transpose, value, offset);
    }

    public void glUseProgram(int program) {
        ++GL20Profiler.shaderSwitches;
        ++GL20Profiler.calls;
        this.gl20.glUseProgram(program);
    }

    public void glValidateProgram(int program) {
        ++GL20Profiler.calls;
        this.gl20.glValidateProgram(program);
    }

    public void glVertexAttrib1f(int indx, float x) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib1f(indx, x);
    }

    public void glVertexAttrib1fv(int indx, FloatBuffer values) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib1fv(indx, values);
    }

    public void glVertexAttrib2f(int indx, float x, float y) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib2f(indx, x, y);
    }

    public void glVertexAttrib2fv(int indx, FloatBuffer values) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib2fv(indx, values);
    }

    public void glVertexAttrib3f(int indx, float x, float y, float z) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib3f(indx, x, y, z);
    }

    public void glVertexAttrib3fv(int indx, FloatBuffer values) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib3fv(indx, values);
    }

    public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib4f(indx, x, y, z, w);
    }

    public void glVertexAttrib4fv(int indx, FloatBuffer values) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttrib4fv(indx, values);
    }

    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer ptr) {
        ++GL20Profiler.calls;
        this.gl20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    public void glViewport(int x, int y, int width, int height) {
        ++GL20Profiler.calls;
        this.gl20.glViewport(x, y, width, height);
    }
}

