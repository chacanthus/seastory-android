// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class PixmapIO {
    class CIM {
        private static final int BUFFER_SIZE = 32000;
        private static final byte[] readBuffer;
        private static final byte[] writeBuffer;

        static  {
            CIM.writeBuffer = new byte[32000];
            CIM.readBuffer = new byte[32000];
        }

        private CIM() {
            super();
        }

        public static Pixmap read(FileHandle file) {  // has try-catch handlers
            DataInputStream v3_1;
            ByteBuffer v5;
            Pixmap v6;
            DataInputStream v4;
            Closeable v3 = null;
            try {
                v4 = new DataInputStream(new InflaterInputStream(new BufferedInputStream(file.read())));
                goto label_5;
            }
            catch(Throwable v9) {
            }
            catch(Exception v0) {
                goto label_28;
                try {
                label_5:
                    v6 = new Pixmap(v4.readInt(), v4.readInt(), Format.fromGdx2DPixmapFormat(v4.readInt()));
                    v5 = v6.getPixels();
                    v5.position(0);
                    v5.limit(v5.capacity());
                    goto label_17;
                }
                catch(Exception v0) {
                    goto label_27;
                }
                catch(Throwable v9) {
                    goto label_47;
                }

                try {
                    while(true) {
                    label_17:
                        int v7 = v4.read(CIM.readBuffer);
                        if(v7 <= 0) {
                            break;
                        }

                        v5.put(CIM.readBuffer, 0, v7);
                    }

                    goto label_41;
                }
                catch(Throwable v9) {
                    goto label_25;
                }

                try {
                label_41:
                    v5.position(0);
                    v5.limit(v5.capacity());
                }
                catch(Exception v0) {
                    goto label_27;
                }
                catch(Throwable v9) {
                    goto label_47;
                }

                StreamUtils.closeQuietly(((Closeable)v4));
                return v6;
                try {
                    try {
                    label_25:
                        throw v9;
                    }
                    catch(Throwable v9) {
                    label_47:
                        v3_1 = v4;
                    }
                    catch(Exception v0) {
                    label_27:
                        v3_1 = v4;
                        try {
                        label_28:
                            throw new GdxRuntimeException("Couldn\'t read Pixmap from file \'" + file + "\'", ((Throwable)v0));
                        }
                        catch(Throwable v9) {
                        }
                    }
                }
                catch(Throwable v9) {
                    goto label_25;
                }
            }

            StreamUtils.closeQuietly(((Closeable)v3_1));
            throw v9;
        }

        public static void write(FileHandle file, Pixmap pixmap) {  // has try-catch handlers
            DataOutputStream v4_1;
            int v2;
            int v7;
            ByteBuffer v6;
            DataOutputStream v5;
            Closeable v4 = null;
            try {
                v5 = new DataOutputStream(new DeflaterOutputStream(file.write(false)));
                goto label_5;
            }
            catch(Throwable v8) {
            }
            catch(Exception v1) {
                goto label_46;
                try {
                label_5:
                    v5.writeInt(pixmap.getWidth());
                    v5.writeInt(pixmap.getHeight());
                    v5.writeInt(Format.toGdx2DPixmapFormat(pixmap.getFormat()));
                    v6 = pixmap.getPixels();
                    v6.position(0);
                    v6.limit(v6.capacity());
                    v7 = v6.capacity() % 32000;
                    int v3 = v6.capacity() / 32000;
                    v2 = 0;
                }
                catch(Throwable v8) {
                    goto label_59;
                }
                catch(Exception v1) {
                    goto label_45;
                }

                while(true) {
                    if(v2 >= v3) {
                        goto label_30;
                    }

                    try {
                        v6.get(CIM.writeBuffer);
                        v5.write(CIM.writeBuffer);
                        ++v2;
                        continue;
                    label_30:
                        v6.get(CIM.writeBuffer, 0, v7);
                        v5.write(CIM.writeBuffer, 0, v7);
                        goto label_37;
                    }
                    catch(Throwable v8) {
                        goto label_43;
                    }
                }

                try {
                label_37:
                    v6.position(0);
                    v6.limit(v6.capacity());
                }
                catch(Throwable v8) {
                    goto label_59;
                }
                catch(Exception v1) {
                    goto label_45;
                }

                StreamUtils.closeQuietly(((Closeable)v5));
                return;
                try {
                label_43:
                    throw v8;
                }
                catch(Throwable v8) {
                    goto label_59;
                }
                catch(Throwable v8) {
                    goto label_43;
                }
                catch(Exception v1) {
                }

            label_45:
                v4_1 = v5;
                try {
                label_46:
                    throw new GdxRuntimeException("Couldn\'t write Pixmap to file \'" + file + "\'", ((Throwable)v1));
                }
                catch(Throwable v8) {
                    goto label_56;
                }
            }

        label_59:
            v4_1 = v5;
        label_56:
            StreamUtils.closeQuietly(((Closeable)v4_1));
            throw v8;
        }
    }

    public class PNG implements Disposable {
        class ChunkBuffer extends DataOutputStream {
            final ByteArrayOutputStream buffer;
            final CRC32 crc;

            ChunkBuffer(int initialSize) {
                super(new ByteArrayOutputStream(initialSize), new CRC32());
            }

            private ChunkBuffer(ByteArrayOutputStream buffer, CRC32 crc) {
                super(new CheckedOutputStream(((OutputStream)buffer), ((Checksum)crc)));
                this.buffer = buffer;
                this.crc = crc;
            }

            public void endChunk(DataOutputStream target) throws IOException {
                this.flush();
                target.writeInt(this.buffer.size() - 4);
                this.buffer.writeTo(((OutputStream)target));
                target.writeInt(((int)this.crc.getValue()));
                this.buffer.reset();
                this.crc.reset();
            }
        }

        private static final byte COLOR_ARGB = 6;
        private static final byte COMPRESSION_DEFLATE = 0;
        private static final byte FILTER_NONE = 0;
        private static final int IDAT = 1229209940;
        private static final int IEND = 1229278788;
        private static final int IHDR = 1229472850;
        private static final byte INTERLACE_NONE = 0;
        private static final byte PAETH = 4;
        private static final byte[] SIGNATURE;
        private final ChunkBuffer buffer;
        private ByteArray curLineBytes;
        private final Deflater deflater;
        private final DeflaterOutputStream deflaterOutput;
        private boolean flipY;
        private int lastLineLen;
        private ByteArray lineOutBytes;
        private ByteArray prevLineBytes;

        static  {
            PNG.SIGNATURE = new byte[]{-119, 80, 78, 71, 13, 10, 26, 10};
        }

        public PNG(int initialBufferSize) {
            super();
            this.flipY = true;
            this.buffer = new ChunkBuffer(initialBufferSize);
            this.deflater = new Deflater();
            this.deflaterOutput = new DeflaterOutputStream(this.buffer, this.deflater);
        }

        public PNG() {
            super(16384);
        }

        public void dispose() {
            this.deflater.end();
        }

        public void setCompression(int level) {
            this.deflater.setLevel(level);
        }

        public void setFlipY(boolean flipY) {
            this.flipY = flipY;
        }

        public void write(FileHandle file, Pixmap pixmap) throws IOException {  // has try-catch handlers
            OutputStream v0 = file.write(false);
            try {
                this.write(v0, pixmap);
            }
            catch(Throwable v1) {
                StreamUtils.closeQuietly(((Closeable)v0));
                throw v1;
            }

            StreamUtils.closeQuietly(((Closeable)v0));
        }

        public void write(OutputStream output, Pixmap pixmap) throws IOException {
            int v25;
            int v22;
            int v23;
            byte[] v20;
            byte[] v6;
            byte[] v11;
            DataOutputStream v7 = new DataOutputStream(output);
            v7.write(PNG.SIGNATURE);
            this.buffer.writeInt(1229472850);
            this.buffer.writeInt(pixmap.getWidth());
            this.buffer.writeInt(pixmap.getHeight());
            this.buffer.writeByte(8);
            this.buffer.writeByte(6);
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.writeByte(0);
            this.buffer.endChunk(v7);
            this.buffer.writeInt(1229209940);
            this.deflater.reset();
            int v10 = pixmap.getWidth() * 4;
            ByteArray v28 = this.lineOutBytes;
            if(v28 == null) {
                v28 = new ByteArray(v10);
                this.lineOutBytes = v28;
                v11 = v28.items;
                v28 = new ByteArray(v10);
                this.curLineBytes = v28;
                v6 = v28.items;
                v28 = new ByteArray(v10);
                this.prevLineBytes = v28;
                v20 = v28.items;
            }
            else {
                v11 = this.lineOutBytes.ensureCapacity(v10);
                v6 = this.curLineBytes.ensureCapacity(v10);
                v20 = this.prevLineBytes.ensureCapacity(v10);
                int v9 = 0;
                int v12 = this.lastLineLen;
                while(v9 < v12) {
                    v20[v9] = 0;
                    ++v9;
                }
            }

            this.lastLineLen = v10;
            ByteBuffer v19 = pixmap.getPixels();
            int v13 = v19.position();
            if(pixmap.getFormat() == Format.RGBA8888) {
                v23 = 1;
            }
            else {
                v23 = 0;
            }

            int v27 = 0;
            int v8 = pixmap.getHeight();
            while(v27 < v8) {
                if(this.flipY) {
                    v22 = v8 - v27 - 1;
                }
                else {
                    v22 = v27;
                }

                if(v23 != 0) {
                    v19.position(v22 * v10);
                    v19.get(v6, 0, v10);
                }
                else {
                    int v21 = 0;
                    v25 = 0;
                    while(v21 < pixmap.getWidth()) {
                        int v18 = pixmap.getPixel(v21, v22);
                        int v26 = v25 + 1;
                        v6[v25] = ((byte)(v18 >> 24 & 255));
                        v25 = v26 + 1;
                        v6[v26] = ((byte)(v18 >> 16 & 255));
                        v26 = v25 + 1;
                        v6[v25] = ((byte)(v18 >> 8 & 255));
                        v25 = v26 + 1;
                        v6[v26] = ((byte)(v18 & 255));
                        ++v21;
                    }
                }

                v11[0] = ((byte)(v6[0] - v20[0]));
                v11[1] = ((byte)(v6[1] - v20[1]));
                v11[2] = ((byte)(v6[2] - v20[2]));
                v11[3] = ((byte)(v6[3] - v20[3]));
                for(v25 = 4; v25 < v10; ++v25) {
                    int v3 = v6[v25 - 4] & 255;
                    int v4 = v20[v25] & 255;
                    int v5 = v20[v25 - 4] & 255;
                    int v14 = v3 + v4 - v5;
                    int v15 = v14 - v3;
                    if(v15 < 0) {
                        v15 = -v15;
                    }

                    int v16 = v14 - v4;
                    if(v16 < 0) {
                        v16 = -v16;
                    }

                    int v17 = v14 - v5;
                    if(v17 < 0) {
                        v17 = -v17;
                    }

                    if(v15 <= v16 && v15 <= v17) {
                        v5 = v3;
                    }
                    else if(v16 <= v17) {
                        v5 = v4;
                    }

                    v11[v25] = ((byte)(v6[v25] - v5));
                }

                this.deflaterOutput.write(4);
                this.deflaterOutput.write(v11, 0, v10);
                byte[] v24 = v6;
                v6 = v20;
                v20 = v24;
                ++v27;
            }

            v19.position(v13);
            this.deflaterOutput.finish();
            this.buffer.endChunk(v7);
            this.buffer.writeInt(1229278788);
            this.buffer.endChunk(v7);
            output.flush();
        }
    }

    public PixmapIO() {
        super();
    }

    public static Pixmap readCIM(FileHandle file) {
        return CIM.read(file);
    }

    public static void writeCIM(FileHandle file, Pixmap pixmap) {
        CIM.write(file, pixmap);
    }

    public static void writePNG(FileHandle file, Pixmap pixmap) {  // has try-catch handlers
        PNG v1;
        try {
            v1 = new PNG(((int)((((float)(pixmap.getWidth() * pixmap.getHeight()))) * 1.5f)));
            goto label_9;
        }
        catch(IOException v0) {
            goto label_23;
        }

        try {
        label_9:
            v1.setFlipY(false);
            v1.write(file, pixmap);
            goto label_11;
        }
        catch(Throwable v2) {
            try {
                v1.dispose();
                throw v2;
            label_11:
                v1.dispose();
                return;
            }
            catch(IOException v0) {
            label_23:
                throw new GdxRuntimeException("Error writing PNG: " + file, ((Throwable)v0));
            }
        }
    }
}

