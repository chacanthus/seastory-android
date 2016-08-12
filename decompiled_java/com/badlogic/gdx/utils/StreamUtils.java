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

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class StreamUtils {
    public class OptimizedByteArrayOutputStream extends ByteArrayOutputStream {
        public OptimizedByteArrayOutputStream(int initialSize) {
            super(initialSize);
        }

        public byte[] getBuffer() {
            return this.buf;
        }

        public byte[] toByteArray() {  // has try-catch handlers
            byte[] v0_1;
            try {
                if(this.count == this.buf.length) {
                    v0_1 = this.buf;
                }
                else {
                    v0_1 = super.toByteArray();
                }
            }
            catch(Throwable v0) {
                throw v0;
            }

            return v0_1;
        }
    }

    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTES;

    static  {
        StreamUtils.EMPTY_BYTES = new byte[0];
    }

    public StreamUtils() {
        super();
    }

    public static void closeQuietly(Closeable c) {  // has try-catch handlers
        if(c == null) {
            return;
        }

        try {
            c.close();
        }
        catch(Exception v0) {
        }
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        StreamUtils.copyStream(input, output, new byte[4096]);
    }

    public static void copyStream(InputStream input, ByteBuffer output) throws IOException {
        StreamUtils.copyStream(input, output, new byte[4096]);
    }

    public static int copyStream(InputStream input, ByteBuffer output, byte[] buffer) throws IOException {
        int v1 = output.position();
        int v2 = 0;
        while(true) {
            int v0 = input.read(buffer);
            if(v0 == -1) {
                break;
            }

            BufferUtils.copy(buffer, 0, ((Buffer)output), v0);
            v2 += v0;
            output.position(v1 + v2);
        }

        output.position(v1);
        return v2;
    }

    public static void copyStream(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        while(true) {
            int v0 = input.read(buffer);
            if(v0 == -1) {
                return;
            }

            output.write(buffer, 0, v0);
        }
    }

    public static void copyStream(InputStream input, OutputStream output, int bufferSize) throws IOException {
        StreamUtils.copyStream(input, output, new byte[bufferSize]);
    }

    public static void copyStream(InputStream input, ByteBuffer output, int bufferSize) throws IOException {
        StreamUtils.copyStream(input, output, new byte[bufferSize]);
    }

    public static byte[] copyStreamToByteArray(InputStream input, int estimatedSize) throws IOException {
        OptimizedByteArrayOutputStream v0 = new OptimizedByteArrayOutputStream(Math.max(0, estimatedSize));
        StreamUtils.copyStream(input, ((OutputStream)v0));
        return ((ByteArrayOutputStream)v0).toByteArray();
    }

    public static byte[] copyStreamToByteArray(InputStream input) throws IOException {
        return StreamUtils.copyStreamToByteArray(input, input.available());
    }

    public static String copyStreamToString(InputStream input, int estimatedSize) throws IOException {
        return StreamUtils.copyStreamToString(input, estimatedSize, null);
    }

    public static String copyStreamToString(InputStream input) throws IOException {
        return StreamUtils.copyStreamToString(input, input.available(), null);
    }

    public static String copyStreamToString(InputStream input, int estimatedSize, String charset) throws IOException {
        InputStreamReader v2;
        if(charset == null) {
            v2 = new InputStreamReader(input);
        }
        else {
            v2 = new InputStreamReader(input, charset);
        }

        StringWriter v3 = new StringWriter(Math.max(0, estimatedSize));
        char[] v0 = new char[4096];
        while(true) {
            int v1 = v2.read(v0);
            if(v1 == -1) {
                break;
            }

            v3.write(v0, 0, v1);
        }

        return v3.toString();
    }
}

