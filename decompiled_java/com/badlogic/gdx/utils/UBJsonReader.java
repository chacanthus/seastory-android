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

import com.badlogic.gdx.files.FileHandle;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UBJsonReader implements BaseJsonReader {
    public boolean oldFormat;

    public UBJsonReader() {
        super();
        this.oldFormat = true;
    }

    public JsonValue parse(FileHandle file) {  // has try-catch handlers
        int v1 = 8192;
        try {
            return this.parse(file.read(v1));
        }
        catch(Exception v0) {
            throw new SerializationException("Error parsing file: " + file, ((Throwable)v0));
        }
    }

    public JsonValue parse(InputStream input) {  // has try-catch handlers
        DataInputStream v0_1;
        JsonValue v3_1;
        DataInputStream v1;
        Closeable v0 = null;
        try {
            v1 = new DataInputStream(input);
            goto label_2;
        }
        catch(Throwable v3) {
        }
        catch(IOException v2) {
            goto label_6;
            try {
            label_2:
                v3_1 = this.parse(v1);
                goto label_3;
            }
            catch(Throwable v3) {
                v0_1 = v1;
            }
            catch(IOException v2) {
                v0_1 = v1;
                try {
                label_6:
                    throw new SerializationException(((Throwable)v2));
                }
                catch(Throwable v3) {
                }
            }
        }

        StreamUtils.closeQuietly(((Closeable)v0_1));
        throw v3;
    label_3:
        StreamUtils.closeQuietly(((Closeable)v1));
        return v3_1;
    }

    public JsonValue parse(DataInputStream din) throws IOException {  // has try-catch handlers
        JsonValue v0_1;
        try {
            v0_1 = this.parse(din, din.readByte());
        }
        catch(Throwable v0) {
            StreamUtils.closeQuietly(((Closeable)din));
            throw v0;
        }

        StreamUtils.closeQuietly(((Closeable)din));
        return v0_1;
    }

    protected JsonValue parse(DataInputStream din, byte type) throws IOException {
        long v0_1;
        JsonValue v0;
        if(type == 91) {
            v0 = this.parseArray(din);
        }
        else if(type == 123) {
            v0 = this.parseObject(din);
        }
        else if(type == 90) {
            v0 = new JsonValue(ValueType.nullValue);
        }
        else if(type == 84) {
            v0 = new JsonValue(true);
        }
        else if(type == 70) {
            v0 = new JsonValue(false);
        }
        else if(type == 66) {
            v0 = new JsonValue(((long)this.readUChar(din)));
        }
        else if(type == 85) {
            v0 = new JsonValue(((long)this.readUChar(din)));
        }
        else if(type == 105) {
            if(this.oldFormat) {
                v0_1 = ((long)din.readShort());
            }
            else {
                v0_1 = ((long)din.readByte());
            }

            v0 = new JsonValue(v0_1);
        }
        else {
            if(type != 73) {
                goto label_59;
            }

            if(this.oldFormat) {
                v0_1 = ((long)din.readInt());
            }
            else {
                v0_1 = ((long)din.readShort());
            }

            v0 = new JsonValue(v0_1);
            goto label_3;
        label_59:
            if(type != 108) {
                goto label_65;
            }

            v0 = new JsonValue(((long)din.readInt()));
            goto label_3;
        label_65:
            if(type != 76) {
                goto label_70;
            }

            v0 = new JsonValue(din.readLong());
            goto label_3;
        label_70:
            if(type != 100) {
                goto label_76;
            }

            v0 = new JsonValue(((double)din.readFloat()));
            goto label_3;
        label_76:
            if(type != 68) {
                goto label_81;
            }

            v0 = new JsonValue(din.readDouble());
            goto label_3;
        label_81:
            if(type != 115 && type != 83) {
                if(type != 97 && type != 65) {
                    throw new GdxRuntimeException("Unrecognized data type");
                }

                v0 = this.parseData(din, type);
                goto label_3;
            }

            v0 = new JsonValue(this.parseString(din, type));
        }

    label_3:
        return v0;
    }

    protected JsonValue parseArray(DataInputStream din) throws IOException {
        long v12 = 0;
        JsonValue v3 = new JsonValue(ValueType.array);
        byte v6 = din.readByte();
        byte v8 = 0;
        if(v6 == 36) {
            v8 = din.readByte();
            v6 = din.readByte();
        }

        long v4 = -1;
        if(v6 == 35) {
            v4 = this.parseSize(din, false, -1);
            if(v4 < v12) {
                throw new GdxRuntimeException("Unrecognized data type");
            }
            else if(v4 != v12) {
                if(v8 == 0) {
                    v6 = din.readByte();
                    goto label_23;
                }
                else {
                    v6 = v8;
                    goto label_23;
                }
            }
        }
        else {
        label_23:
            JsonValue v2 = null;
            long v0 = 0;
            while(din.available() > 0) {
                if(v6 == 93) {
                    break;
                }

                JsonValue v7 = this.parse(din, v6);
                if(v2 != null) {
                    v7.prev = v2;
                    v2.next = v7;
                    ++v3.size;
                }
                else {
                    v3.child = v7;
                    v3.size = 1;
                }

                v2 = v7;
                if(v4 > v12) {
                    ++v0;
                    if(v0 < v4) {
                        goto label_41;
                    }

                    break;
                }

            label_41:
                if(v8 != 0) {
                    goto label_50;
                }

                v6 = din.readByte();
                continue;
            label_50:
                v6 = v8;
            }
        }

        return v3;
    }

    protected JsonValue parseData(DataInputStream din, byte blockType) throws IOException {
        long v6;
        byte v0 = din.readByte();
        if(blockType == 65) {
            v6 = this.readUInt(din);
        }
        else {
            v6 = ((long)this.readUChar(din));
        }

        JsonValue v4 = new JsonValue(ValueType.array);
        JsonValue v1 = null;
        long v2;
        for(v2 = 0; v2 < v6; ++v2) {
            JsonValue v5 = this.parse(din, v0);
            if(v1 != null) {
                v1.next = v5;
                ++v4.size;
            }
            else {
                v4.child = v5;
                v4.size = 1;
            }

            v1 = v5;
        }

        return v4;
    }

    protected JsonValue parseObject(DataInputStream din) throws IOException {
        byte v10;
        JsonValue v5 = new JsonValue(ValueType.object);
        byte v8 = din.readByte();
        byte v9 = 0;
        if(v8 == 36) {
            v9 = din.readByte();
            v8 = din.readByte();
        }

        long v6 = -1;
        if(v8 == 35) {
            v6 = this.parseSize(din, false, -1);
            if(v6 < 0) {
                throw new GdxRuntimeException("Unrecognized data type");
            }
            else if(v6 != 0) {
                v8 = din.readByte();
                goto label_23;
            }
        }
        else {
        label_23:
            JsonValue v4 = null;
            long v0 = 0;
            while(din.available() > 0) {
                if(v8 == 125) {
                    break;
                }

                String v3 = this.parseString(din, true, v8);
                if(v9 == 0) {
                    v10 = din.readByte();
                }
                else {
                    v10 = v9;
                }

                JsonValue v2 = this.parse(din, v10);
                v2.setName(v3);
                if(v4 != null) {
                    v2.prev = v4;
                    v4.next = v2;
                    ++v5.size;
                }
                else {
                    v5.child = v2;
                    v5.size = 1;
                }

                v4 = v2;
                if(v6 > 0) {
                    ++v0;
                    if(v0 < v6) {
                        goto label_47;
                    }

                    break;
                }

            label_47:
                v8 = din.readByte();
            }
        }

        return v5;
    }

    protected long parseSize(DataInputStream din, boolean useIntOnError, long defaultValue) throws IOException {
        return this.parseSize(din, din.readByte(), useIntOnError, defaultValue);
    }

    protected long parseSize(DataInputStream din, byte type, boolean useIntOnError, long defaultValue) throws IOException {
        long v0;
        if(type == 105) {
            v0 = ((long)this.readUChar(din));
        }
        else if(type == 73) {
            v0 = ((long)this.readUShort(din));
        }
        else if(type == 108) {
            v0 = this.readUInt(din);
        }
        else if(type == 76) {
            v0 = din.readLong();
        }
        else if(useIntOnError) {
            v0 = (((long)((((short)type)) & 255))) << 24 | (((long)((((short)din.readByte())) & 255))) << 16 | (((long)((((short)din.readByte())) & 255))) << 8 | (((long)((((short)din.readByte())) & 255)));
        }
        else {
            v0 = defaultValue;
        }

        return v0;
    }

    protected String parseString(DataInputStream din, byte type) throws IOException {
        return this.parseString(din, false, type);
    }

    protected String parseString(DataInputStream din, boolean sOptional, byte type) throws IOException {
        String v0;
        long v8 = 0;
        long v4 = -1;
        long v6 = -1;
        if(type == 83) {
            v6 = this.parseSize(din, true, v4);
        }
        else if(type == 115) {
            v6 = ((long)this.readUChar(din));
        }
        else if(sOptional) {
            v6 = this.parseSize(din, type, false, v4);
        }

        if(v6 < v8) {
            throw new GdxRuntimeException("Unrecognized data type, string expected");
        }

        if(v6 > v8) {
            v0 = this.readString(din, v6);
        }
        else {
            v0 = "";
        }

        return v0;
    }

    protected String readString(DataInputStream din, long size) throws IOException {
        byte[] v0 = new byte[((int)size)];
        din.readFully(v0);
        return new String(v0, "UTF-8");
    }

    protected short readUChar(DataInputStream din) throws IOException {
        return ((short)((((short)din.readByte())) & 255));
    }

    protected long readUInt(DataInputStream din) throws IOException {
        return (((long)din.readInt())) & -1;
    }

    protected int readUShort(DataInputStream din) throws IOException {
        return din.readShort() & 65535;
    }
}

