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

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public final class BufferUtils {
    static int allocatedUnsafe;
    static Array unsafeBuffers;

    static  {
        BufferUtils.unsafeBuffers = new Array();
        BufferUtils.allocatedUnsafe = 0;
    }

    public BufferUtils() {
        super();
    }

    private static int bytesToElements(Buffer dst, int bytes) {
        if(!(dst instanceof ByteBuffer)) {
            if((dst instanceof ShortBuffer)) {
                bytes >>>= 1;
            }
            else if((dst instanceof CharBuffer)) {
                bytes >>>= 1;
            }
            else if((dst instanceof IntBuffer)) {
                bytes >>>= 2;
            }
            else if((dst instanceof LongBuffer)) {
                bytes >>>= 3;
            }
            else if((dst instanceof FloatBuffer)) {
                bytes >>>= 2;
            }
            else if((dst instanceof DoubleBuffer)) {
                bytes >>>= 3;
            }
            else {
                goto label_27;
            }
        }

        return bytes;
    label_27:
        throw new GdxRuntimeException("Can\'t copy to a " + dst.getClass().getName() + " instance");
    }

    public static native void clear(ByteBuffer arg0, int arg1) {
    }

    public static void copy(Buffer src, Buffer dst, int numElements) {
        int v0 = BufferUtils.elementsToBytes(src, numElements);
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, v0));
        BufferUtils.copyJni(src, BufferUtils.positionInBytes(src), dst, BufferUtils.positionInBytes(dst), v0);
    }

    public static void copy(byte[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements);
    }

    public static void copy(char[] src, int srcOffset, int numElements, Buffer dst) {
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 1);
    }

    public static void copy(char[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 1));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 1);
    }

    public static void copy(double[] src, int srcOffset, int numElements, Buffer dst) {
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 3);
    }

    public static void copy(double[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 3));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 3);
    }

    public static void copy(float[] src, int srcOffset, int numElements, Buffer dst) {
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 2);
    }

    public static void copy(float[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 2));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 2);
    }

    public static void copy(float[] src, Buffer dst, int numFloats, int offset) {
        if((dst instanceof ByteBuffer)) {
            dst.limit(numFloats << 2);
        }
        else if((dst instanceof FloatBuffer)) {
            dst.limit(numFloats);
        }

        BufferUtils.copyJni(src, dst, numFloats, offset);
        dst.position(0);
    }

    public static void copy(int[] src, int srcOffset, int numElements, Buffer dst) {
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 2);
    }

    public static void copy(int[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 2));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 2);
    }

    public static void copy(long[] src, int srcOffset, int numElements, Buffer dst) {
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 3);
    }

    public static void copy(long[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 3));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 3);
    }

    public static void copy(short[] src, int srcOffset, Buffer dst, int numElements) {
        dst.limit(dst.position() + BufferUtils.bytesToElements(dst, numElements << 1));
        BufferUtils.copyJni(src, srcOffset, dst, BufferUtils.positionInBytes(dst), numElements << 1);
    }

    private static native void copyJni(Buffer arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(byte[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(char[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(double[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(float[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(float[] arg0, Buffer arg1, int arg2, int arg3) {
    }

    private static native void copyJni(int[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(long[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    private static native void copyJni(short[] arg0, int arg1, Buffer arg2, int arg3, int arg4) {
    }

    public static void disposeUnsafeByteBuffer(ByteBuffer buffer) {  // has try-catch handlers
        int v1_1;
        int v0 = buffer.capacity();
        try {
            if(!BufferUtils.unsafeBuffers.removeValue(buffer, true)) {
                throw new IllegalArgumentException("buffer not allocated with newUnsafeByteBuffer or already disposed");
            }

            v1_1 = BufferUtils.allocatedUnsafe;
            goto label_12;
        label_10:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_10;
        }

    label_12:
        BufferUtils.allocatedUnsafe = v1_1 - v0;
        BufferUtils.freeMemory(buffer);
    }

    private static int elementsToBytes(Buffer dst, int elements) {
        if(!(dst instanceof ByteBuffer)) {
            if((dst instanceof ShortBuffer)) {
                elements <<= 1;
            }
            else if((dst instanceof CharBuffer)) {
                elements <<= 1;
            }
            else if((dst instanceof IntBuffer)) {
                elements <<= 2;
            }
            else if((dst instanceof LongBuffer)) {
                elements <<= 3;
            }
            else if((dst instanceof FloatBuffer)) {
                elements <<= 2;
            }
            else if((dst instanceof DoubleBuffer)) {
                elements <<= 3;
            }
            else {
                goto label_27;
            }
        }

        return elements;
    label_27:
        throw new GdxRuntimeException("Can\'t copy to a " + dst.getClass().getName() + " instance");
    }

    private static native long find(Buffer arg0, int arg1, int arg2, Buffer arg3, int arg4, int arg5) {
    }

    private static native long find(Buffer arg0, int arg1, int arg2, Buffer arg3, int arg4, int arg5, float arg6) {
    }

    private static native long find(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4, int arg5) {
    }

    private static native long find(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4, int arg5, float arg6) {
    }

    private static native long find(float[] arg0, int arg1, int arg2, Buffer arg3, int arg4, int arg5) {
    }

    private static native long find(float[] arg0, int arg1, int arg2, Buffer arg3, int arg4, int arg5, float arg6) {
    }

    private static native long find(float[] arg0, int arg1, int arg2, float[] arg3, int arg4, int arg5) {
    }

    private static native long find(float[] arg0, int arg1, int arg2, float[] arg3, int arg4, int arg5, float arg6) {
    }

    public static long findFloats(Buffer vertex, int strideInBytes, Buffer vertices, int numVertices) {
        return BufferUtils.find(vertex, BufferUtils.positionInBytes(vertex), strideInBytes, vertices, BufferUtils.positionInBytes(vertices), numVertices);
    }

    public static long findFloats(Buffer vertex, int strideInBytes, Buffer vertices, int numVertices, float epsilon) {
        return BufferUtils.find(vertex, BufferUtils.positionInBytes(vertex), strideInBytes, vertices, BufferUtils.positionInBytes(vertices), numVertices, epsilon);
    }

    public static long findFloats(Buffer vertex, int strideInBytes, float[] vertices, int numVertices) {
        return BufferUtils.find(vertex, BufferUtils.positionInBytes(vertex), strideInBytes, vertices, 0, numVertices);
    }

    public static long findFloats(Buffer vertex, int strideInBytes, float[] vertices, int numVertices, float epsilon) {
        return BufferUtils.find(vertex, BufferUtils.positionInBytes(vertex), strideInBytes, vertices, 0, numVertices, epsilon);
    }

    public static long findFloats(float[] vertex, int strideInBytes, Buffer vertices, int numVertices) {
        return BufferUtils.find(vertex, 0, strideInBytes, vertices, BufferUtils.positionInBytes(vertices), numVertices);
    }

    public static long findFloats(float[] vertex, int strideInBytes, Buffer vertices, int numVertices, float epsilon) {
        return BufferUtils.find(vertex, 0, strideInBytes, vertices, BufferUtils.positionInBytes(vertices), numVertices, epsilon);
    }

    public static long findFloats(float[] vertex, int strideInBytes, float[] vertices, int numVertices) {
        return BufferUtils.find(vertex, 0, strideInBytes, vertices, 0, numVertices);
    }

    public static long findFloats(float[] vertex, int strideInBytes, float[] vertices, int numVertices, float epsilon) {
        return BufferUtils.find(vertex, 0, strideInBytes, vertices, 0, numVertices, epsilon);
    }

    private static native void freeMemory(ByteBuffer arg0) {
    }

    public static int getAllocatedBytesUnsafe() {
        return BufferUtils.allocatedUnsafe;
    }

    private static native long getBufferAddress(Buffer arg0) {
    }

    public static long getUnsafeBufferAddress(Buffer buffer) {
        return BufferUtils.getBufferAddress(buffer) + (((long)buffer.position()));
    }

    public static ByteBuffer newByteBuffer(int numBytes) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numBytes);
        v0.order(ByteOrder.nativeOrder());
        return v0;
    }

    public static CharBuffer newCharBuffer(int numChars) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numChars * 2);
        v0.order(ByteOrder.nativeOrder());
        return v0.asCharBuffer();
    }

    private static native ByteBuffer newDisposableByteBuffer(int arg0) {
    }

    public static DoubleBuffer newDoubleBuffer(int numDoubles) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numDoubles * 8);
        v0.order(ByteOrder.nativeOrder());
        return v0.asDoubleBuffer();
    }

    public static FloatBuffer newFloatBuffer(int numFloats) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numFloats * 4);
        v0.order(ByteOrder.nativeOrder());
        return v0.asFloatBuffer();
    }

    public static IntBuffer newIntBuffer(int numInts) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numInts * 4);
        v0.order(ByteOrder.nativeOrder());
        return v0.asIntBuffer();
    }

    public static LongBuffer newLongBuffer(int numLongs) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numLongs * 8);
        v0.order(ByteOrder.nativeOrder());
        return v0.asLongBuffer();
    }

    public static ShortBuffer newShortBuffer(int numShorts) {
        ByteBuffer v0 = ByteBuffer.allocateDirect(numShorts * 2);
        v0.order(ByteOrder.nativeOrder());
        return v0.asShortBuffer();
    }

    public static ByteBuffer newUnsafeByteBuffer(int numBytes) {  // has try-catch handlers
        ByteBuffer v0 = BufferUtils.newDisposableByteBuffer(numBytes);
        v0.order(ByteOrder.nativeOrder());
        BufferUtils.allocatedUnsafe += numBytes;
        try {
            BufferUtils.unsafeBuffers.add(v0);
            return v0;
        label_11:
            throw v1;
        }
        catch(Throwable v1) {
            goto label_11;
        }
    }

    public static ByteBuffer newUnsafeByteBuffer(ByteBuffer buffer) {  // has try-catch handlers
        BufferUtils.allocatedUnsafe += buffer.capacity();
        try {
            BufferUtils.unsafeBuffers.add(buffer);
            return buffer;
        label_9:
            throw v0;
        }
        catch(Throwable v0) {
            goto label_9;
        }
    }

    private static int positionInBytes(Buffer dst) {
        int v0;
        if((dst instanceof ByteBuffer)) {
            v0 = dst.position();
        }
        else if((dst instanceof ShortBuffer)) {
            v0 = dst.position() << 1;
        }
        else if((dst instanceof CharBuffer)) {
            v0 = dst.position() << 1;
        }
        else if((dst instanceof IntBuffer)) {
            v0 = dst.position() << 2;
        }
        else if((dst instanceof LongBuffer)) {
            v0 = dst.position() << 3;
        }
        else if((dst instanceof FloatBuffer)) {
            v0 = dst.position() << 2;
        }
        else if((dst instanceof DoubleBuffer)) {
            v0 = dst.position() << 3;
        }
        else {
            goto label_34;
        }

        return v0;
    label_34:
        throw new GdxRuntimeException("Can\'t copy to a " + dst.getClass().getName() + " instance");
    }

    public static void transform(Buffer data, int dimensions, int strideInBytes, int count, Matrix3 matrix) {
        BufferUtils.transform(data, dimensions, strideInBytes, count, matrix, 0);
    }

    public static void transform(Buffer data, int dimensions, int strideInBytes, int count, Matrix3 matrix, int offset) {
        switch(dimensions) {
            case 2: {
                goto label_8;
            }
            case 3: {
                goto label_3;
            }
        }

        throw new IllegalArgumentException();
    label_3:
        BufferUtils.transformV3M3Jni(data, strideInBytes, count, matrix.val, BufferUtils.positionInBytes(data) + offset);
        return;
    label_8:
        BufferUtils.transformV2M3Jni(data, strideInBytes, count, matrix.val, BufferUtils.positionInBytes(data) + offset);
    }

    public static void transform(Buffer data, int dimensions, int strideInBytes, int count, Matrix4 matrix) {
        BufferUtils.transform(data, dimensions, strideInBytes, count, matrix, 0);
    }

    public static void transform(Buffer data, int dimensions, int strideInBytes, int count, Matrix4 matrix, int offset) {
        switch(dimensions) {
            case 2: {
                goto label_13;
            }
            case 3: {
                goto label_8;
            }
            case 4: {
                goto label_3;
            }
        }

        throw new IllegalArgumentException();
    label_3:
        BufferUtils.transformV4M4Jni(data, strideInBytes, count, matrix.val, BufferUtils.positionInBytes(data) + offset);
        return;
    label_8:
        BufferUtils.transformV3M4Jni(data, strideInBytes, count, matrix.val, BufferUtils.positionInBytes(data) + offset);
        return;
    label_13:
        BufferUtils.transformV2M4Jni(data, strideInBytes, count, matrix.val, BufferUtils.positionInBytes(data) + offset);
    }

    public static void transform(float[] data, int dimensions, int strideInBytes, int count, Matrix3 matrix) {
        BufferUtils.transform(data, dimensions, strideInBytes, count, matrix, 0);
    }

    public static void transform(float[] data, int dimensions, int strideInBytes, int count, Matrix3 matrix, int offset) {
        switch(dimensions) {
            case 2: {
                goto label_6;
            }
            case 3: {
                goto label_3;
            }
        }

        throw new IllegalArgumentException();
    label_3:
        BufferUtils.transformV3M3Jni(data, strideInBytes, count, matrix.val, offset);
        return;
    label_6:
        BufferUtils.transformV2M3Jni(data, strideInBytes, count, matrix.val, offset);
    }

    public static void transform(float[] data, int dimensions, int strideInBytes, int count, Matrix4 matrix) {
        BufferUtils.transform(data, dimensions, strideInBytes, count, matrix, 0);
    }

    public static void transform(float[] data, int dimensions, int strideInBytes, int count, Matrix4 matrix, int offset) {
        switch(dimensions) {
            case 2: {
                goto label_9;
            }
            case 3: {
                goto label_6;
            }
            case 4: {
                goto label_3;
            }
        }

        throw new IllegalArgumentException();
    label_3:
        BufferUtils.transformV4M4Jni(data, strideInBytes, count, matrix.val, offset);
        return;
    label_6:
        BufferUtils.transformV3M4Jni(data, strideInBytes, count, matrix.val, offset);
        return;
    label_9:
        BufferUtils.transformV2M4Jni(data, strideInBytes, count, matrix.val, offset);
    }

    private static native void transformV2M3Jni(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV2M3Jni(float[] arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV2M4Jni(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV2M4Jni(float[] arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV3M3Jni(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV3M3Jni(float[] arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV3M4Jni(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV3M4Jni(float[] arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV4M4Jni(Buffer arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }

    private static native void transformV4M4Jni(float[] arg0, int arg1, int arg2, float[] arg3, int arg4) {
    }
}

