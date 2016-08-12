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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ETC1 {
    public final class ETC1Data implements Disposable {
        public final ByteBuffer compressedData;
        public final int dataOffset;
        public final int height;
        public final int width;

        public ETC1Data(int width, int height, ByteBuffer compressedData, int dataOffset) {
            super();
            this.width = width;
            this.height = height;
            this.compressedData = compressedData;
            this.dataOffset = dataOffset;
            this.checkNPOT();
        }

        public ETC1Data(FileHandle pkmFile) {  // has try-catch handlers
            DataInputStream v3_1;
            DataInputStream v4;
            super();
            byte[] v0 = new byte[10240];
            Closeable v3 = null;
            try {
                v4 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(pkmFile.read())));
                goto label_9;
            }
            catch(Throwable v6) {
            }
            catch(Exception v1) {
                goto label_22;
                try {
                label_9:
                    this.compressedData = BufferUtils.newUnsafeByteBuffer(v4.readInt());
                    while(true) {
                        int v5 = v4.read(v0);
                        if(v5 == -1) {
                            break;
                        }

                        this.compressedData.put(v0, 0, v5);
                    }

                    this.compressedData.position(0);
                    this.compressedData.limit(this.compressedData.capacity());
                }
                catch(Exception v1) {
                    goto label_21;
                }
                catch(Throwable v6) {
                    goto label_56;
                }

                StreamUtils.closeQuietly(((Closeable)v4));
                this.width = ETC1.getWidthPKM(this.compressedData, 0);
                this.height = ETC1.getHeightPKM(this.compressedData, 0);
                this.dataOffset = ETC1.PKM_HEADER_SIZE;
                this.compressedData.position(this.dataOffset);
                this.checkNPOT();
                return;
            label_21:
                v3_1 = v4;
                try {
                label_22:
                    throw new GdxRuntimeException("Couldn\'t load pkm file \'" + pkmFile + "\'", ((Throwable)v1));
                }
                catch(Throwable v6) {
                    goto label_32;
                }
            }

        label_56:
            v3_1 = v4;
        label_32:
            StreamUtils.closeQuietly(v3);
            throw v6;
        }

        private void checkNPOT() {
            if(!MathUtils.isPowerOfTwo(this.width) || !MathUtils.isPowerOfTwo(this.height)) {
                Gdx.app.debug("ETC1Data", "warning: non-power-of-two ETC1 textures may crash the driver of PowerVR GPUs");
            }
        }

        public void dispose() {
            BufferUtils.disposeUnsafeByteBuffer(this.compressedData);
        }

        public boolean hasPKMHeader() {
            boolean v0;
            if(this.dataOffset == 16) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public String toString() {
            String v0;
            if(this.hasPKMHeader()) {
                StringBuilder v1 = new StringBuilder();
                if(ETC1.isValidPKM(this.compressedData, 0)) {
                    v0 = "valid";
                }
                else {
                    v0 = "invalid";
                }

                v0 = v1.append(v0).append(" pkm [").append(ETC1.getWidthPKM(this.compressedData, 0)).append("x").append(ETC1.getHeightPKM(this.compressedData, 0)).append("], compressed: ").append(this.compressedData.capacity() - ETC1.PKM_HEADER_SIZE).toString();
            }
            else {
                v0 = "raw [" + this.width + "x" + this.height + "], compressed: " + (this.compressedData.capacity() - ETC1.PKM_HEADER_SIZE);
            }

            return v0;
        }

        public void write(FileHandle file) {  // has try-catch handlers
            DataOutputStream v3_1;
            DataOutputStream v4;
            Closeable v3 = null;
            byte[] v0 = new byte[10240];
            int v5 = 0;
            this.compressedData.position(0);
            this.compressedData.limit(this.compressedData.capacity());
            try {
                v4 = new DataOutputStream(new GZIPOutputStream(file.write(false)));
                goto label_15;
            }
            catch(Throwable v6) {
            }
            catch(Exception v2) {
                goto label_42;
                try {
                label_15:
                    v4.writeInt(this.compressedData.capacity());
                    while(v5 != this.compressedData.capacity()) {
                        int v1 = Math.min(this.compressedData.remaining(), v0.length);
                        this.compressedData.get(v0, 0, v1);
                        v4.write(v0, 0, v1);
                        v5 += v1;
                    }
                }
                catch(Throwable v6) {
                    goto label_55;
                }
                catch(Exception v2) {
                    goto label_58;
                }

                StreamUtils.closeQuietly(((Closeable)v4));
                this.compressedData.position(this.dataOffset);
                this.compressedData.limit(this.compressedData.capacity());
                return;
            label_58:
                v3_1 = v4;
                try {
                label_42:
                    throw new GdxRuntimeException("Couldn\'t write PKM file to \'" + file + "\'", ((Throwable)v2));
                }
                catch(Throwable v6) {
                    goto label_52;
                }
            }

        label_55:
            v3_1 = v4;
        label_52:
            StreamUtils.closeQuietly(((Closeable)v3_1));
            throw v6;
        }
    }

    public static int ETC1_RGB8_OES;
    public static int PKM_HEADER_SIZE;

    static  {
        ETC1.PKM_HEADER_SIZE = 16;
        ETC1.ETC1_RGB8_OES = 36196;
    }

    public ETC1() {
        super();
    }

    public static Pixmap decodeImage(ETC1Data etc1Data, Format format) {
        int v5;
        int v4;
        int v1;
        if(etc1Data.hasPKMHeader()) {
            v1 = 16;
            v4 = ETC1.getWidthPKM(etc1Data.compressedData, 0);
            v5 = ETC1.getHeightPKM(etc1Data.compressedData, 0);
        }
        else {
            v1 = 0;
            v4 = etc1Data.width;
            v5 = etc1Data.height;
        }

        int v6 = ETC1.getPixelSize(format);
        Pixmap v7 = new Pixmap(v4, v5, format);
        ETC1.decodeImage(etc1Data.compressedData, v1, v7.getPixels(), 0, v4, v5, v6);
        return v7;
    }

    private static native void decodeImage(ByteBuffer arg0, int arg1, ByteBuffer arg2, int arg3, int arg4, int arg5, int arg6) {
    }

    public static ETC1Data encodeImage(Pixmap pixmap) {
        ByteBuffer v0 = ETC1.encodeImage(pixmap.getPixels(), 0, pixmap.getWidth(), pixmap.getHeight(), ETC1.getPixelSize(pixmap.getFormat()));
        BufferUtils.newUnsafeByteBuffer(v0);
        return new ETC1Data(pixmap.getWidth(), pixmap.getHeight(), v0, 0);
    }

    private static native ByteBuffer encodeImage(ByteBuffer arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public static ETC1Data encodeImagePKM(Pixmap pixmap) {
        ByteBuffer v0 = ETC1.encodeImagePKM(pixmap.getPixels(), 0, pixmap.getWidth(), pixmap.getHeight(), ETC1.getPixelSize(pixmap.getFormat()));
        BufferUtils.newUnsafeByteBuffer(v0);
        return new ETC1Data(pixmap.getWidth(), pixmap.getHeight(), v0, 16);
    }

    private static native ByteBuffer encodeImagePKM(ByteBuffer arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    public static native void formatHeader(ByteBuffer arg0, int arg1, int arg2, int arg3) {
    }

    public static native int getCompressedDataSize(int arg0, int arg1) {
    }

    static native int getHeightPKM(ByteBuffer arg0, int arg1) {
    }

    private static int getPixelSize(Format format) {
        int v0;
        if(format == Format.RGB565) {
            v0 = 2;
        }
        else if(format == Format.RGB888) {
            v0 = 3;
        }
        else {
            goto label_8;
        }

        return v0;
    label_8:
        throw new GdxRuntimeException("Can only handle RGB565 or RGB888 images");
    }

    static native int getWidthPKM(ByteBuffer arg0, int arg1) {
    }

    static native boolean isValidPKM(ByteBuffer arg0, int arg1) {
    }
}

