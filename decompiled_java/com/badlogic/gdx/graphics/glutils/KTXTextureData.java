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
import com.badlogic.gdx.graphics.CubemapData;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.TextureData$TextureDataType;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.zip.GZIPInputStream;

public class KTXTextureData implements CubemapData, TextureData {
    private static final int GL_TEXTURE_1D = 4660;
    private static final int GL_TEXTURE_1D_ARRAY_EXT = 4660;
    private static final int GL_TEXTURE_2D_ARRAY_EXT = 4660;
    private static final int GL_TEXTURE_3D = 4660;
    private ByteBuffer compressedData;
    private FileHandle file;
    private int glBaseInternalFormat;
    private int glFormat;
    private int glInternalFormat;
    private int glType;
    private int glTypeSize;
    private int imagePos;
    private int numberOfArrayElements;
    private int numberOfFaces;
    private int numberOfMipmapLevels;
    private int pixelDepth;
    private int pixelHeight;
    private int pixelWidth;
    private boolean useMipMaps;

    public KTXTextureData(FileHandle file, boolean genMipMaps) {
        super();
        this.pixelWidth = -1;
        this.pixelHeight = -1;
        this.pixelDepth = -1;
        this.file = file;
        this.useMipMaps = genMipMaps;
    }

    public void consumeCubemapData() {
        this.consumeCustomData(34067);
    }

    public void consumeCustomData(int target) {
        if(this.compressedData == null) {
            throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");
        }

        IntBuffer v21 = BufferUtils.newIntBuffer(16);
        int v22 = 0;
        if(this.glType == 0 || this.glFormat == 0) {
            if(this.glType + this.glFormat != 0) {
                throw new GdxRuntimeException("either both or none of glType, glFormat must be zero");
            }
            else {
                v22 = 1;
            }
        }

        int v33 = 1;
        int v27 = 4660;
        if(this.pixelHeight > 0) {
            v33 = 2;
            v27 = 3553;
        }

        if(this.pixelDepth > 0) {
            v33 = 3;
            v27 = 4660;
        }

        if(this.numberOfFaces == 6) {
            if(v33 == 2) {
                v27 = 34067;
            }
            else {
                throw new GdxRuntimeException("cube map needs 2D faces");
            }
        }
        else if(this.numberOfFaces != 1) {
            throw new GdxRuntimeException("numberOfFaces must be either 1 or 6");
        }

        if(this.numberOfArrayElements > 0) {
            if(v27 == 4660) {
                v27 = 4660;
            }
            else if(v27 == 3553) {
                v27 = 4660;
            }
            else {
                goto label_74;
            }

            ++v33;
            goto label_53;
        label_74:
            throw new GdxRuntimeException("No API for 3D and cube arrays yet");
        }

    label_53:
        if(v27 == 4660) {
            throw new GdxRuntimeException("Unsupported texture format (only 2D texture are supported in LibGdx for the time being)");
        }

        int v32 = -1;
        if(this.numberOfFaces != 6 || target == 34067) {
            if(this.numberOfFaces == 6 && target == 34067) {
                target = 34069;
                goto label_97;
            }

            if(target == v27) {
                goto label_97;
            }

            if(34069 <= target && target <= 34074 && target == 3553) {
                goto label_97;
            }

            throw new GdxRuntimeException("Invalid target requested : 0x" + Integer.toHexString(target) + ", expecting : 0x" + Integer.toHexString(v27));
        }
        else {
            if(34069 <= target && target <= 34074) {
                v32 = target - 34069;
                target = 34069;
                goto label_97;
            }

            throw new GdxRuntimeException("You must specify either GL_TEXTURE_CUBE_MAP to bind all 6 faces of the cube or the requested face GL_TEXTURE_CUBE_MAP_POSITIVE_X and followings.");
        }

    label_97:
        Gdx.gl.glGetIntegerv(3317, v21);
        int v31 = v21.get(0);
        if(v31 != 4) {
            Gdx.gl.glPixelStorei(3317, 4);
        }

        int v14 = this.glInternalFormat;
        int v18 = this.glFormat;
        int v30 = this.imagePos;
        int v4;
        for(v4 = 0; v4 < this.numberOfMipmapLevels; ++v4) {
            int v15 = Math.max(1, this.pixelWidth >> v4);
            int v16 = Math.max(1, this.pixelHeight >> v4);
            Math.max(1, this.pixelDepth >> v4);
            this.compressedData.position(v30);
            int v25 = this.compressedData.getInt();
            int v26 = v25 + 3 & -4;
            v30 += 4;
            int v24;
            for(v24 = 0; v24 < this.numberOfFaces; ++v24) {
                this.compressedData.position(v30);
                v30 += v26;
                if(v32 == -1 || v32 == v24) {
                    ByteBuffer v10 = this.compressedData.slice();
                    v10.limit(v26);
                    if(v33 != 1 && v33 == 2) {
                        if(this.numberOfArrayElements > 0) {
                            v16 = this.numberOfArrayElements;
                        }

                        if(v22 == 0) {
                            goto label_261;
                        }

                        if(v14 != ETC1.ETC1_RGB8_OES) {
                            goto label_252;
                        }

                        if(Gdx.graphics.supportsExtension("GL_OES_compressed_ETC1_RGB8_texture")) {
                            goto label_243;
                        }

                        Pixmap v29 = ETC1.decodeImage(new ETC1Data(v15, v16, v10, 0), Format.RGB888);
                        Gdx.gl.glTexImage2D(target + v24, v4, v29.getGLInternalFormat(), v29.getWidth(), v29.getHeight(), 0, v29.getGLFormat(), v29.getGLType(), v29.getPixels());
                        v29.dispose();
                        goto label_164;
                    label_243:
                        Gdx.gl.glCompressedTexImage2D(target + v24, v4, v14, v15, v16, 0, v25, ((Buffer)v10));
                        goto label_164;
                    label_252:
                        Gdx.gl.glCompressedTexImage2D(target + v24, v4, v14, v15, v16, 0, v25, ((Buffer)v10));
                        goto label_164;
                    label_261:
                        Gdx.gl.glTexImage2D(target + v24, v4, v14, v15, v16, 0, v18, this.glType, v10);
                    }
                }

            label_164:
            }
        }

        if(v31 != 4) {
            Gdx.gl.glPixelStorei(3317, v31);
        }

        if(this.useMipMaps()) {
            Gdx.gl.glGenerateMipmap(target);
        }

        this.disposePreparedData();
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public void disposePreparedData() {
        if(this.compressedData != null) {
            BufferUtils.disposeUnsafeByteBuffer(this.compressedData);
        }

        this.compressedData = null;
    }

    public ByteBuffer getData(int requestedLevel, int requestedFace) {
        ByteBuffer v0;
        int v5 = this.imagePos;
        int v4;
        for(v4 = 0; v4 < this.numberOfMipmapLevels; ++v4) {
            int v3 = this.compressedData.getInt(v5) + 3 & -4;
            v5 += 4;
            if(v4 == requestedLevel) {
                int v1 = 0;
                while(v1 < this.numberOfFaces) {
                    if(v1 == requestedFace) {
                        this.compressedData.position(v5);
                        v0 = this.compressedData.slice();
                        v0.limit(v3);
                        goto label_19;
                    }
                    else {
                        v5 += v3;
                        ++v1;
                        continue;
                    }
                }
            }
            else {
                v5 += this.numberOfFaces * v3;
            }
        }

        v0 = null;
    label_19:
        return v0;
    }

    public Format getFormat() {
        throw new GdxRuntimeException("This TextureData implementation directly handles texture formats.");
    }

    public int getGlInternalFormat() {
        return this.glInternalFormat;
    }

    public int getHeight() {
        return this.pixelHeight;
    }

    public int getNumberOfFaces() {
        return this.numberOfFaces;
    }

    public int getNumberOfMipMapLevels() {
        return this.numberOfMipmapLevels;
    }

    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    public int getWidth() {
        return this.pixelWidth;
    }

    public boolean isManaged() {
        return 1;
    }

    public boolean isPrepared() {
        boolean v0;
        if(this.compressedData != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void prepare() {  // has try-catch handlers
        ByteOrder v14_1;
        DataInputStream v9_1;
        DataInputStream v10;
        if(this.compressedData != null) {
            throw new GdxRuntimeException("Already prepared");
        }

        if(this.file == null) {
            throw new GdxRuntimeException("Need a file to load from");
        }

        if(this.file.name().endsWith(".zktx")) {
            byte[] v1 = new byte[10240];
            Closeable v9 = null;
            try {
                v10 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(this.file.read())));
                goto label_28;
            }
            catch(Throwable v14) {
            }
            catch(Exception v4) {
                goto label_43;
                try {
                label_28:
                    this.compressedData = BufferUtils.newUnsafeByteBuffer(v10.readInt());
                    while(true) {
                        int v13 = v10.read(v1);
                        if(v13 == -1) {
                            break;
                        }

                        this.compressedData.put(v1, 0, v13);
                    }

                    this.compressedData.position(0);
                    this.compressedData.limit(this.compressedData.capacity());
                }
                catch(Throwable v14) {
                    goto label_310;
                }
                catch(Exception v4) {
                    goto label_42;
                }

                StreamUtils.closeQuietly(((Closeable)v10));
                goto label_69;
            label_42:
                v9_1 = v10;
                try {
                label_43:
                    throw new GdxRuntimeException("Couldn\'t load zktx file \'" + this.file + "\'", ((Throwable)v4));
                }
                catch(Throwable v14) {
                    goto label_56;
                }
            }

        label_310:
            v9_1 = v10;
        label_56:
            StreamUtils.closeQuietly(v9);
            throw v14;
        }
        else {
            this.compressedData = ByteBuffer.wrap(this.file.readBytes());
        }

    label_69:
        if(this.compressedData.get() != -85) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 75) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 84) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 88) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 32) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 49) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 49) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != -69) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 13) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 10) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 26) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(this.compressedData.get() != 10) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        int v5 = this.compressedData.getInt();
        if(v5 != 67305985 && v5 != 16909060) {
            throw new GdxRuntimeException("Invalid KTX Header");
        }

        if(v5 != 67305985) {
            ByteBuffer v15 = this.compressedData;
            if(this.compressedData.order() == ByteOrder.BIG_ENDIAN) {
                v14_1 = ByteOrder.LITTLE_ENDIAN;
            }
            else {
                v14_1 = ByteOrder.BIG_ENDIAN;
            }

            v15.order(v14_1);
        }

        this.glType = this.compressedData.getInt();
        this.glTypeSize = this.compressedData.getInt();
        this.glFormat = this.compressedData.getInt();
        this.glInternalFormat = this.compressedData.getInt();
        this.glBaseInternalFormat = this.compressedData.getInt();
        this.pixelWidth = this.compressedData.getInt();
        this.pixelHeight = this.compressedData.getInt();
        this.pixelDepth = this.compressedData.getInt();
        this.numberOfArrayElements = this.compressedData.getInt();
        this.numberOfFaces = this.compressedData.getInt();
        this.numberOfMipmapLevels = this.compressedData.getInt();
        if(this.numberOfMipmapLevels == 0) {
            this.numberOfMipmapLevels = 1;
            this.useMipMaps = true;
        }

        this.imagePos = this.compressedData.position() + this.compressedData.getInt();
        if(!this.compressedData.isDirect()) {
            int v12 = this.imagePos;
            int v11;
            for(v11 = 0; v11 < this.numberOfMipmapLevels; ++v11) {
                v12 += this.numberOfFaces * (this.compressedData.getInt(v12) + 3 & -4) + 4;
            }

            this.compressedData.limit(v12);
            this.compressedData.position(0);
            ByteBuffer v3 = BufferUtils.newUnsafeByteBuffer(v12);
            v3.order(this.compressedData.order());
            v3.put(this.compressedData);
            this.compressedData = v3;
        }
    }

    public boolean useMipMaps() {
        return this.useMipMaps;
    }
}

