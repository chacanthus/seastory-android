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

import com.badlogic.gdx.graphics.GL20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class AndroidGL20 implements GL20 {
    static  {
        System.loadLibrary("gdx");
        AndroidGL20.init();
    }

    public AndroidGL20() {
        super();
    }

    public native void glActiveTexture() {
    }

    public native void glAttachShader(int arg1) {
    }

    public native void glBindAttribLocation(int arg1, String arg2) {
    }

    public native void glBindBuffer(int arg1) {
    }

    public native void glBindFramebuffer(int arg1) {
    }

    public native void glBindRenderbuffer(int arg1) {
    }

    public native void glBindTexture(int arg1) {
    }

    public native void glBlendColor(float arg1, float arg2, float arg3) {
    }

    public native void glBlendEquation() {
    }

    public native void glBlendEquationSeparate(int arg1) {
    }

    public native void glBlendFunc(int arg1) {
    }

    public native void glBlendFuncSeparate(int arg1, int arg2, int arg3) {
    }

    public native void glBufferData(int arg1, Buffer arg2, int arg3) {
    }

    public native void glBufferSubData(int arg1, int arg2, Buffer arg3) {
    }

    public native int glCheckFramebufferStatus() {
    }

    public native void glClear() {
    }

    public native void glClearColor(float arg1, float arg2, float arg3) {
    }

    public native void glClearDepthf() {
    }

    public native void glClearStencil() {
    }

    public native void glColorMask(boolean arg1, boolean arg2, boolean arg3) {
    }

    public native void glCompileShader() {
    }

    public native void glCompressedTexImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, Buffer arg7) {
    }

    public native void glCompressedTexSubImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, Buffer arg8) {
    }

    public native void glCopyTexImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
    }

    public native void glCopyTexSubImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
    }

    public native int glCreateProgram() {
    }

    public native int glCreateShader() {
    }

    public native void glCullFace() {
    }

    public native void glDeleteBuffer() {
    }

    public native void glDeleteBuffers(IntBuffer arg1) {
    }

    public native void glDeleteFramebuffer() {
    }

    public native void glDeleteFramebuffers(IntBuffer arg1) {
    }

    public native void glDeleteProgram() {
    }

    public native void glDeleteRenderbuffer() {
    }

    public native void glDeleteRenderbuffers(IntBuffer arg1) {
    }

    public native void glDeleteShader() {
    }

    public native void glDeleteTexture() {
    }

    public native void glDeleteTextures(IntBuffer arg1) {
    }

    public native void glDepthFunc() {
    }

    public native void glDepthMask() {
    }

    public native void glDepthRangef(float arg1) {
    }

    public native void glDetachShader(int arg1) {
    }

    public native void glDisable() {
    }

    public native void glDisableVertexAttribArray() {
    }

    public native void glDrawArrays(int arg1, int arg2) {
    }

    public native void glDrawElements(int arg1, int arg2, int arg3) {
    }

    public native void glDrawElements(int arg1, int arg2, Buffer arg3) {
    }

    public native void glEnable() {
    }

    public native void glEnableVertexAttribArray() {
    }

    public native void glFinish() {
    }

    public native void glFlush() {
    }

    public native void glFramebufferRenderbuffer(int arg1, int arg2, int arg3) {
    }

    public native void glFramebufferTexture2D(int arg1, int arg2, int arg3, int arg4) {
    }

    public native void glFrontFace() {
    }

    public native int glGenBuffer() {
    }

    public native void glGenBuffers(IntBuffer arg1) {
    }

    public native int glGenFramebuffer() {
    }

    public native void glGenFramebuffers(IntBuffer arg1) {
    }

    public native int glGenRenderbuffer() {
    }

    public native void glGenRenderbuffers(IntBuffer arg1) {
    }

    public native int glGenTexture() {
    }

    public native void glGenTextures(IntBuffer arg1) {
    }

    public native void glGenerateMipmap() {
    }

    public native String glGetActiveAttrib(int arg1, IntBuffer arg2, Buffer arg3) {
    }

    public native String glGetActiveUniform(int arg1, IntBuffer arg2, Buffer arg3) {
    }

    public native void glGetAttachedShaders(int arg1, Buffer arg2, IntBuffer arg3) {
    }

    public native int glGetAttribLocation(String arg1) {
    }

    public native void glGetBooleanv(Buffer arg1) {
    }

    public native void glGetBufferParameteriv(int arg1, IntBuffer arg2) {
    }

    public native int glGetError() {
    }

    public native void glGetFloatv(FloatBuffer arg1) {
    }

    public native void glGetFramebufferAttachmentParameteriv(int arg1, int arg2, IntBuffer arg3) {
    }

    public native void glGetIntegerv(IntBuffer arg1) {
    }

    public native String glGetProgramInfoLog() {
    }

    public native void glGetProgramiv(int arg1, IntBuffer arg2) {
    }

    public native void glGetRenderbufferParameteriv(int arg1, IntBuffer arg2) {
    }

    public native String glGetShaderInfoLog() {
    }

    public native void glGetShaderPrecisionFormat(int arg1, IntBuffer arg2, IntBuffer arg3) {
    }

    public native void glGetShaderSource(int arg1, Buffer arg2, String arg3) {
    }

    public native void glGetShaderiv(int arg1, IntBuffer arg2) {
    }

    public native String glGetString() {
    }

    public native void glGetTexParameterfv(int arg1, FloatBuffer arg2) {
    }

    public native void glGetTexParameteriv(int arg1, IntBuffer arg2) {
    }

    public native int glGetUniformLocation(String arg1) {
    }

    public native void glGetUniformfv(int arg1, FloatBuffer arg2) {
    }

    public native void glGetUniformiv(int arg1, IntBuffer arg2) {
    }

    public native void glGetVertexAttribPointerv(int arg1, Buffer arg2) {
    }

    public native void glGetVertexAttribfv(int arg1, FloatBuffer arg2) {
    }

    public native void glGetVertexAttribiv(int arg1, IntBuffer arg2) {
    }

    public native void glHint(int arg1) {
    }

    public native boolean glIsBuffer() {
    }

    public native boolean glIsEnabled() {
    }

    public native boolean glIsFramebuffer() {
    }

    public native boolean glIsProgram() {
    }

    public native boolean glIsRenderbuffer() {
    }

    public native boolean glIsShader() {
    }

    public native boolean glIsTexture() {
    }

    public native void glLineWidth() {
    }

    public native void glLinkProgram() {
    }

    public native void glPixelStorei(int arg1) {
    }

    public native void glPolygonOffset(float arg1) {
    }

    public native void glReadPixels(int arg1, int arg2, int arg3, int arg4, int arg5, Buffer arg6) {
    }

    public native void glReleaseShaderCompiler() {
    }

    public native void glRenderbufferStorage(int arg1, int arg2, int arg3) {
    }

    public native void glSampleCoverage(boolean arg1) {
    }

    public native void glScissor(int arg1, int arg2, int arg3) {
    }

    public native void glShaderBinary(IntBuffer arg1, int arg2, Buffer arg3, int arg4) {
    }

    public native void glShaderSource(String arg1) {
    }

    public native void glStencilFunc(int arg1, int arg2) {
    }

    public native void glStencilFuncSeparate(int arg1, int arg2, int arg3) {
    }

    public native void glStencilMask() {
    }

    public native void glStencilMaskSeparate(int arg1) {
    }

    public native void glStencilOp(int arg1, int arg2) {
    }

    public native void glStencilOpSeparate(int arg1, int arg2, int arg3) {
    }

    public native void glTexImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, Buffer arg8) {
    }

    public native void glTexParameterf(int arg1, float arg2) {
    }

    public native void glTexParameterfv(int arg1, FloatBuffer arg2) {
    }

    public native void glTexParameteri(int arg1, int arg2) {
    }

    public native void glTexParameteriv(int arg1, IntBuffer arg2) {
    }

    public native void glTexSubImage2D(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, Buffer arg8) {
    }

    public native void glUniform1f(float arg1) {
    }

    public native void glUniform1fv(int arg1, FloatBuffer arg2) {
    }

    public native void glUniform1fv(int arg1, float[] arg2, int arg3) {
    }

    public native void glUniform1i(int arg1) {
    }

    public native void glUniform1iv(int arg1, IntBuffer arg2) {
    }

    public native void glUniform1iv(int arg1, int[] arg2, int arg3) {
    }

    public native void glUniform2f(float arg1, float arg2) {
    }

    public native void glUniform2fv(int arg1, FloatBuffer arg2) {
    }

    public native void glUniform2fv(int arg1, float[] arg2, int arg3) {
    }

    public native void glUniform2i(int arg1, int arg2) {
    }

    public native void glUniform2iv(int arg1, IntBuffer arg2) {
    }

    public native void glUniform2iv(int arg1, int[] arg2, int arg3) {
    }

    public native void glUniform3f(float arg1, float arg2, float arg3) {
    }

    public native void glUniform3fv(int arg1, FloatBuffer arg2) {
    }

    public native void glUniform3fv(int arg1, float[] arg2, int arg3) {
    }

    public native void glUniform3i(int arg1, int arg2, int arg3) {
    }

    public native void glUniform3iv(int arg1, IntBuffer arg2) {
    }

    public native void glUniform3iv(int arg1, int[] arg2, int arg3) {
    }

    public native void glUniform4f(float arg1, float arg2, float arg3, float arg4) {
    }

    public native void glUniform4fv(int arg1, FloatBuffer arg2) {
    }

    public native void glUniform4fv(int arg1, float[] arg2, int arg3) {
    }

    public native void glUniform4i(int arg1, int arg2, int arg3, int arg4) {
    }

    public native void glUniform4iv(int arg1, IntBuffer arg2) {
    }

    public native void glUniform4iv(int arg1, int[] arg2, int arg3) {
    }

    public native void glUniformMatrix2fv(int arg1, boolean arg2, FloatBuffer arg3) {
    }

    public native void glUniformMatrix2fv(int arg1, boolean arg2, float[] arg3, int arg4) {
    }

    public native void glUniformMatrix3fv(int arg1, boolean arg2, FloatBuffer arg3) {
    }

    public native void glUniformMatrix3fv(int arg1, boolean arg2, float[] arg3, int arg4) {
    }

    public native void glUniformMatrix4fv(int arg1, boolean arg2, FloatBuffer arg3) {
    }

    public native void glUniformMatrix4fv(int arg1, boolean arg2, float[] arg3, int arg4) {
    }

    public native void glUseProgram() {
    }

    public native void glValidateProgram() {
    }

    public native void glVertexAttrib1f(float arg1) {
    }

    public native void glVertexAttrib1fv(FloatBuffer arg1) {
    }

    public native void glVertexAttrib2f(float arg1, float arg2) {
    }

    public native void glVertexAttrib2fv(FloatBuffer arg1) {
    }

    public native void glVertexAttrib3f(float arg1, float arg2, float arg3) {
    }

    public native void glVertexAttrib3fv(FloatBuffer arg1) {
    }

    public native void glVertexAttrib4f(float arg1, float arg2, float arg3, float arg4) {
    }

    public native void glVertexAttrib4fv(FloatBuffer arg1) {
    }

    public native void glVertexAttribPointer(int arg1, int arg2, boolean arg3, int arg4, int arg5) {
    }

    public native void glVertexAttribPointer(int arg1, int arg2, boolean arg3, int arg4, Buffer arg5) {
    }

    public native void glViewport(int arg1, int arg2, int arg3) {
    }

    private static native void init() {
    }
}

