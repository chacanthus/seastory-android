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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonReader implements BaseJsonReader {
    private static final byte[] _json_actions = null;
    private static final byte[] _json_eof_actions = null;
    private static final short[] _json_index_offsets = null;
    private static final byte[] _json_indicies = null;
    private static final short[] _json_key_offsets = null;
    private static final byte[] _json_range_lengths = null;
    private static final byte[] _json_single_lengths = null;
    private static final byte[] _json_trans_actions = null;
    private static final char[] _json_trans_keys = null;
    private static final byte[] _json_trans_targs = null;
    private JsonValue current;
    private final Array elements;
    static final int json_en_array = 23;
    static final int json_en_main = 1;
    static final int json_en_object = 5;
    static final int json_error = 0;
    static final int json_first_final = 35;
    static final int json_start = 1;
    private final Array lastChild;
    private JsonValue root;

    static  {
        JsonReader._json_actions = JsonReader.init__json_actions_0();
        JsonReader._json_key_offsets = JsonReader.init__json_key_offsets_0();
        JsonReader._json_trans_keys = JsonReader.init__json_trans_keys_0();
        JsonReader._json_single_lengths = JsonReader.init__json_single_lengths_0();
        JsonReader._json_range_lengths = JsonReader.init__json_range_lengths_0();
        JsonReader._json_index_offsets = JsonReader.init__json_index_offsets_0();
        JsonReader._json_indicies = JsonReader.init__json_indicies_0();
        JsonReader._json_trans_targs = JsonReader.init__json_trans_targs_0();
        JsonReader._json_trans_actions = JsonReader.init__json_trans_actions_0();
        JsonReader._json_eof_actions = JsonReader.init__json_eof_actions_0();
    }

    public JsonReader() {
        super();
        this.elements = new Array(8);
        this.lastChild = new Array(8);
    }

    private void addChild(String name, JsonValue child) {
        child.setName(name);
        if(this.current == null) {
            this.current = child;
            this.root = child;
        }
        else {
            if(!this.current.isArray() && !this.current.isObject()) {
                this.root = this.current;
                return;
            }

            if(this.current.size == 0) {
                this.current.child = child;
            }
            else {
                Object v0 = this.lastChild.pop();
                ((JsonValue)v0).next = child;
                child.prev = ((JsonValue)v0);
            }

            this.lastChild.add(child);
            ++this.current.size;
        }
    }

    protected void bool(String name, boolean value) {
        this.addChild(name, new JsonValue(value));
    }

    private static byte[] init__json_actions_0() {
        return new byte[]{0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 1, 8, 2, 0, 7, 2, 0, 8, 2, 1, 3, 2, 1, 5};
    }

    private static byte[] init__json_eof_actions_0() {
        return new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
    }

    private static short[] init__json_index_offsets_0() {
        return new short[]{0, 0, 11, 14, 16, 19, 28, 34, 40, 43, 54, 62, 70, 79, 81, 90, 93, 96, 105, 108, 111, 113, 116, 119, 130, 138, 146, 157, 159, 170, 173, 176, 187, 190, 193, 196, 201, 206, 207};
    }

    private static byte[] init__json_indicies_0() {
        return new byte[]{1, 1, 2, 3, 4, 3, 5, 3, 6, 1, 0, 7, 7, 3, 8, 3, 9, 9, 3, 11, 11, 12, 13, 14, 3, 15, 11, 10, 16, 16, 17, 18, 16, 3, 19, 19, 20, 21, 19, 3, 22, 22, 3, 21, 21, 24, 3, 25, 3, 26, 3, 27, 21, 23, 28, 29, 28, 28, 30, 31, 32, 3, 33, 34, 33, 33, 13, 35, 15, 3, 34, 34, 12, 36, 37, 3, 15, 34, 10, 16, 3, 36, 36, 12, 3, 38, 3, 3, 36, 10, 39, 39, 3, 40, 40, 3, 13, 13, 12, 3, 41, 3, 15, 13, 10, 42, 42, 3, 43, 43, 3, 28, 3, 44, 44, 3, 45, 45, 3, 47, 47, 48, 49, 50, 3, 51, 52, 53, 47, 46, 54, 55, 54, 54, 56, 57, 58, 3, 59, 60, 59, 59, 49, 61, 52, 3, 60, 60, 48, 62, 63, 3, 51, 52, 53, 60, 46, 54, 3, 62, 62, 48, 3, 64, 3, 51, 3, 53, 62, 46, 65, 65, 3, 66, 66, 3, 49, 49, 48, 3, 67, 3, 51, 52, 53, 49, 46, 68, 68, 3, 69, 69, 3, 70, 70, 3, 8, 8, 71, 8, 3, 72, 72, 73, 72, 3, 3, 3, 0};
    }

    private static short[] init__json_key_offsets_0() {
        return new short[]{0, 0, 11, 13, 14, 16, 25, 31, 37, 39, 50, 57, 64, 73, 74, 83, 85, 87, 96, 98, 100, 101, 103, 105, 116, 123, 130, 141, 142, 153, 155, 157, 168, 170, 172, 174, 179, 184, 184};
    }

    private static byte[] init__json_range_lengths_0() {
        return new byte[]{0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0};
    }

    private static byte[] init__json_single_lengths_0() {
        return new byte[]{0, 9, 2, 1, 2, 7, 4, 4, 2, 9, 7, 7, 7, 1, 7, 2, 2, 7, 2, 2, 1, 2, 2, 9, 7, 7, 9, 1, 9, 2, 2, 9, 2, 2, 2, 3, 3, 0, 0};
    }

    private static byte[] init__json_trans_actions_0() {
        return new byte[]{13, 0, 15, 0, 0, 7, 3, 11, 1, 11, 17, 0, 20, 0, 0, 5, 1, 1, 1, 0, 0, 0, 11, 13, 15, 0, 7, 3, 1, 1, 1, 1, 23, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 11, 13, 0, 15, 0, 0, 7, 9, 3, 1, 1, 1, 1, 26, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 1, 0, 0};
    }

    private static char[] init__json_trans_keys_0() {
        return new char[]{'\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '\"', '*', '/', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', ' ', ',', '/', '}', '\t', '\n', '\r', ' ', ',', '/', '}', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '\"', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\"', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', ' ', ',', '/', ']', '\t', '\n', '\r', ' ', ',', '/', ']', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\"', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '*', '/', '\r', ' ', '/', '\t', '\n', '\r', ' ', '/', '\t', '\n', '\u0000'};
    }

    private static byte[] init__json_trans_targs_0() {
        return new byte[]{35, 1, 3, 0, 4, 36, 36, 36, 36, 1, 6, 5, 13, 17, 22, 37, 7, 8, 9, 7, 8, 9, 7, 10, 20, 21, 11, 11, 11, 12, 17, 19, 37, 11, 12, 19, 14, 16, 15, 14, 12, 18, 17, 11, 9, 5, 24, 23, 27, 31, 34, 25, 38, 25, 25, 26, 31, 33, 38, 25, 26, 33, 28, 30, 29, 28, 26, 32, 31, 25, 23, 2, 36, 2};
    }

    protected void number(String name, double value, String stringValue) {
        this.addChild(name, new JsonValue(value, stringValue));
    }

    protected void number(String name, long value, String stringValue) {
        this.addChild(name, new JsonValue(value, stringValue));
    }

    public JsonValue parse(FileHandle file) {  // has try-catch handlers
        try {
            return this.parse(file.reader("UTF-8"));
        }
        catch(Exception v0) {
            throw new SerializationException("Error parsing file: " + file, ((Throwable)v0));
        }
    }

    public JsonValue parse(InputStream input) {  // has try-catch handlers
        JsonValue v1_1;
        try {
            v1_1 = this.parse(new InputStreamReader(input, "UTF-8"));
        }
        catch(Throwable v1) {
        }
        catch(IOException v0) {
            try {
                throw new SerializationException(((Throwable)v0));
            }
            catch(Throwable v1) {
                StreamUtils.closeQuietly(((Closeable)input));
                throw v1;
            }
        }

        StreamUtils.closeQuietly(((Closeable)input));
        return v1_1;
    }

    public JsonValue parse(Reader reader) {  // has try-catch handlers
        JsonValue v5_2;
        int v2;
        int v4;
        char[] v0;
        int v5 = 1024;
        try {
            v0 = new char[v5];
            v4 = 0;
            while(true) {
            label_3:
                v2 = reader.read(v0, v4, v0.length - v4);
                if(v2 != -1) {
                    break;
                }

                goto label_8;
            }
        }
        catch(IOException v1) {
            goto label_25;
        }
        catch(Throwable v5_1) {
            goto label_28;
        }

        if(v2 != 0) {
            goto label_22;
        }

        try {
            char[] v3 = new char[v0.length * 2];
            System.arraycopy(v0, 0, v3, 0, v0.length);
            v0 = v3;
            goto label_3;
        }
        catch(IOException v1) {
            goto label_25;
        }
        catch(Throwable v5_1) {
            goto label_28;
        }

    label_22:
        v4 += v2;
        goto label_3;
        try {
        label_8:
            v5_2 = this.parse(v0, 0, v4);
        }
        catch(IOException v1) {
            goto label_25;
        }
        catch(Throwable v5_1) {
            goto label_28;
        }

        StreamUtils.closeQuietly(((Closeable)reader));
        return v5_2;
        try {
        label_25:
            throw new SerializationException(((Throwable)v1));
        }
        catch(Throwable v5_1) {
        label_28:
            StreamUtils.closeQuietly(((Closeable)reader));
            throw v5_1;
        }
    }

    public JsonValue parse(String json) {
        char[] v0 = json.toCharArray();
        return this.parse(v0, 0, v0.length);
    }

    public JsonValue parse(char[] data, int offset, int length) {  // has try-catch handlers
        int v6;
        int v8;
        int v9;
        int v7;
        int v21;
        int[] v34;
        int v29;
        int v23;
        int v22;
        Object v31;
        String v47;
        int v10;
        int v36;
        int v18;
        int v11;
        int v20;
        int v15;
        int v14;
        int v19;
        int v13;
        int v35 = offset;
        int v38 = length;
        int v27 = v38;
        int[] v41 = new int[4];
        int v40 = 0;
        Array v32 = new Array(8);
        int v33 = 0;
        int v43 = 0;
        int v44 = 0;
        RuntimeException v37 = null;
        if(0 != 0) {
            System.out.println();
        }

        int v24 = 1;
        int v12 = 0;
        int v46 = 0;
        while(true) {
        label_27:
            switch(v12) {
                case 0: {
                    goto label_60;
                }
                case 1: {
                    goto label_68;
                }
                case 2: {
                    goto label_633;
                }
                case 4: {
                    goto label_642;
                }
            }

            goto label_29;
        label_60:
            if(v35 != v38) {
                goto label_65;
            }

            v12 = 4;
            continue;
        label_65:
            if(v24 != 0) {
                goto label_68;
            }

            v12 = 5;
        }

        try {
        label_68:
            v13 = JsonReader._json_key_offsets[v24];
            v19 = JsonReader._json_index_offsets[v24];
            v14 = JsonReader._json_single_lengths[v24];
            if(v14 > 0) {
                v15 = v13;
                v20 = v13 + v14 - 1;
                while(true) {
                label_78:
                    if(v20 >= v15) {
                        goto label_117;
                    }
                    else {
                        goto label_80;
                    }
                }
            }
            else {
                goto label_82;
            }

            goto label_92;
        }
        catch(RuntimeException v28) {
            goto label_507;
        }

    label_117:
        int v16 = v15 + (v20 - v15 >> 1);
        try {
            if(data[v35] < JsonReader._json_trans_keys[v16]) {
                v20 = v16 - 1;
                goto label_78;
            }
            else if(data[v35] > JsonReader._json_trans_keys[v16]) {
                v15 = v16 + 1;
                goto label_78;
            }
            else {
                v19 += v16 - v13;
                goto label_92;
            label_80:
                v13 += v14;
                v19 += v14;
            label_82:
                v14 = JsonReader._json_range_lengths[v24];
                if(v14 > 0) {
                    v15 = v13;
                    v20 = (v14 << 1) + v13 - 2;
                    while(true) {
                    label_89:
                        if(v20 >= v15) {
                            v16 = v15 + (v20 - v15 >> 1 & -2);
                            if(data[v35] < JsonReader._json_trans_keys[v16]) {
                                v20 = v16 - 2;
                                continue;
                            }
                            else if(data[v35] > JsonReader._json_trans_keys[v16 + 1]) {
                                goto label_158;
                            }
                            else {
                                goto label_160;
                            }
                        }
                        else {
                            goto label_91;
                        }

                        goto label_92;
                    }
                }
            }

            goto label_92;
        }
        catch(RuntimeException v28) {
            goto label_507;
        }

    label_158:
        v15 = v16 + 2;
        goto label_89;
    label_160:
        v19 += v16 - v13 >> 1;
        goto label_92;
        try {
        label_91:
            v19 += v14;
        label_92:
            v19 = JsonReader._json_indicies[v19];
            v24 = JsonReader._json_trans_targs[v19];
            if(JsonReader._json_trans_actions[v19] == 0) {
                goto label_633;
            }

            v11 = JsonReader._json_trans_actions[v19] + 1;
            v18 = JsonReader._json_actions[JsonReader._json_trans_actions[v19]];
            v36 = v35;
        }
        catch(RuntimeException v28) {
            goto label_507;
        }

        while(true) {
            int v17 = v18 - 1;
            if(v18 <= 0) {
                goto label_632;
            }

            try {
                v10 = v11 + 1;
                switch(JsonReader._json_actions[v11]) {
                    case 0: {
                        goto label_164;
                    }
                    case 1: {
                        goto label_167;
                    }
                    case 2: {
                        goto label_343;
                    }
                    case 3: {
                        goto label_396;
                    }
                    case 4: {
                        goto label_407;
                    }
                    case 5: {
                        goto label_460;
                    }
                    case 6: {
                        goto label_471;
                    }
                    case 7: {
                        goto label_527;
                    }
                    case 8: {
                        goto label_614;
                    }
                }
            }
            catch(RuntimeException v28) {
                goto label_227;
            }

            v35 = v36;
            goto label_113;
        label_164:
            v43 = 1;
            v35 = v36;
            goto label_113;
        label_614:
            if(0 != 0) {
                try {
                    System.out.println("quotedChars");
                }
                catch(RuntimeException v28) {
                    goto label_227;
                }
            }

            v35 = v36 + 1;
            v40 = v35;
            v33 = 0;
            try {
                do {
                    switch(data[v35]) {
                        case 34: {
                            goto label_627;
                        }
                        case 92: {
                            goto label_629;
                        }
                    }

                    goto label_623;
                label_629:
                    v33 = 1;
                    ++v35;
                label_623:
                    ++v35;
                }
                while(v35 != v27);

            label_627:
                --v35;
                goto label_113;
            }
            catch(RuntimeException v28) {
                goto label_507;
            }

            try {
            label_167:
                v47 = new String(data, v40, v36 - v40);
                if(v33 != 0) {
                    v47 = this.unescape(v47);
                }

                if(v43 == 0) {
                    goto label_197;
                }

                v43 = 0;
                if(0 != 0) {
                    System.out.println("name: " + v47);
                }

                v32.add(v47);
                goto label_193;
            label_197:
                if(v32.size > 0) {
                    v31 = v32.pop();
                }
                else {
                    v31 = null;
                }

                if(v44 == 0) {
                    goto label_295;
                }

                if(!v47.equals("true")) {
                    goto label_232;
                }

                if(0 != 0) {
                    System.out.println("boolean: " + v31 + "=true");
                }

                this.bool(v31, true);
                goto label_193;
            label_232:
                if(!v47.equals("false")) {
                    goto label_253;
                }

                if(0 != 0) {
                    System.out.println("boolean: " + v31 + "=false");
                }

                this.bool(v31, false);
                goto label_193;
            label_253:
                if(!v47.equals("null")) {
                    goto label_262;
                }

                this.string(v31, null);
                goto label_193;
            label_262:
                v22 = 0;
                v23 = 1;
                v29 = v40;
                while(true) {
                label_265:
                    if(v29 < v36) {
                        switch(data[v29]) {
                            case 43: 
                            case 45: 
                            case 48: 
                            case 49: 
                            case 50: 
                            case 51: 
                            case 52: 
                            case 53: 
                            case 54: 
                            case 55: 
                            case 56: 
                            case 57: {
                                goto label_317;
                            }
                            case 46: 
                            case 69: 
                            case 101: {
                                goto label_315;
                            }
                        }

                        break;
                    }

                    goto label_272;
                }
            }
            catch(RuntimeException v28) {
                goto label_227;
            }

            v22 = 0;
            v23 = 0;
            goto label_272;
        label_315:
            v22 = 1;
            v23 = 0;
        label_317:
            ++v29;
            goto label_265;
        label_272:
            if(v22 == 0) {
                goto label_319;
            }

            if(0 != 0) {
                try {
                    System.out.println("double: " + v31 + "=" + Double.parseDouble(v47));
                label_287:
                    this.number(v31, Double.parseDouble(v47), v47);
                    goto label_193;
                }
                catch(RuntimeException v28) {
                label_227:
                    v35 = v36;
                    break;
                }
                catch(NumberFormatException v48) {
                    goto label_295;
                    try {
                    label_319:
                        if(v23 == 0) {
                            goto label_295;
                        }

                        if(0 != 0) {
                            System.out.println("double: " + v31 + "=" + Double.parseDouble(v47));
                        }

                        try {
                            this.number(v31, Long.parseLong(v47), v47);
                            goto label_193;
                        }
                        catch(NumberFormatException v48) {
                        }
                    }
                    catch(RuntimeException v28) {
                        goto label_227;
                    }

                label_295:
                    if(0 != 0) {
                        try {
                            System.out.println("string: " + v31 + "=" + v47);
                        label_310:
                            this.string(v31, v47);
                        label_193:
                            v44 = 0;
                            v40 = v36;
                            v35 = v36;
                            goto label_113;
                        label_343:
                            if(v32.size > 0) {
                                v31 = v32.pop();
                            }
                            else {
                                goto label_394;
                            }

                            goto label_349;
                        }
                        catch(RuntimeException v28) {
                            goto label_227;
                        }
                    }

                    goto label_310;
                label_394:
                    v31 = null;
                    try {
                    label_349:
                        if(0 != 0) {
                            System.out.println("startObject: " + v31);
                        }

                        this.startObject(v31);
                        if(v46 == v41.length) {
                            v34 = new int[v41.length * 2];
                            System.arraycopy(v41, 0, v34, 0, v41.length);
                            v41 = v34;
                        }
                    }
                    catch(RuntimeException v28) {
                        goto label_227;
                    }

                    int v45 = v46 + 1;
                    try {
                        v41[v46] = v24;
                        v24 = 5;
                        v12 = 2;
                        v46 = v45;
                        v35 = v36;
                        goto label_27;
                    }
                    catch(RuntimeException v28) {
                        goto label_897;
                    }

                    try {
                    label_407:
                        if(v32.size > 0) {
                            v31 = v32.pop();
                        }
                        else {
                            goto label_458;
                        }

                        goto label_413;
                    }
                    catch(RuntimeException v28) {
                        goto label_227;
                    }

                label_458:
                    v31 = null;
                    try {
                    label_413:
                        if(0 != 0) {
                            System.out.println("startArray: " + v31);
                        }

                        this.startArray(v31);
                        if(v46 == v41.length) {
                            v34 = new int[v41.length * 2];
                            System.arraycopy(v41, 0, v34, 0, v41.length);
                            v41 = v34;
                        }
                    }
                    catch(RuntimeException v28) {
                        goto label_227;
                    }

                    v45 = v46 + 1;
                    try {
                        v41[v46] = v24;
                        v24 = 23;
                        v12 = 2;
                        v46 = v45;
                        v35 = v36;
                        goto label_27;
                    }
                    catch(RuntimeException v28) {
                        goto label_897;
                    }

                label_471:
                    int v42 = v36 - 1;
                    v35 = v36 + 1;
                    try {
                        if(data[v36] == 47) {
                            while(v35 != v27) {
                                if(data[v35] == 10) {
                                    break;
                                }

                                ++v35;
                            }

                            --v35;
                        }
                        else {
                            while(true) {
                            label_508:
                                if(v35 + 1 >= v27 || data[v35] == 42) {
                                    if(data[v35 + 1] != 47) {
                                        goto label_523;
                                    }

                                    goto label_525;
                                }

                                goto label_523;
                            }
                        }

                        goto label_489;
                    }
                    catch(RuntimeException v28) {
                        goto label_507;
                    }

                label_523:
                    ++v35;
                    goto label_508;
                label_525:
                    ++v35;
                    try {
                    label_489:
                        if(0 == 0) {
                            goto label_113;
                        }

                        System.out.println("comment " + new String(data, v42, v35 - v42));
                        goto label_113;
                    }
                    catch(RuntimeException v28) {
                        goto label_507;
                    }

                label_396:
                    if(0 != 0) {
                        try {
                            System.out.println("endObject");
                        label_400:
                            this.pop();
                            v45 = v46 - 1;
                            goto label_402;
                        }
                        catch(RuntimeException v28) {
                            goto label_227;
                        }
                    }

                    goto label_400;
                    try {
                    label_402:
                        v24 = v41[v45];
                        v12 = 2;
                        v46 = v45;
                        v35 = v36;
                        goto label_27;
                    }
                    catch(RuntimeException v28) {
                        goto label_897;
                    }

                label_460:
                    if(0 != 0) {
                        try {
                            System.out.println("endArray");
                        label_464:
                            this.pop();
                            v45 = v46 - 1;
                            goto label_466;
                        }
                        catch(RuntimeException v28) {
                            goto label_227;
                        }
                    }

                    goto label_464;
                    try {
                    label_466:
                        v24 = v41[v45];
                        v12 = 2;
                        v46 = v45;
                        v35 = v36;
                        goto label_27;
                    }
                    catch(RuntimeException v28) {
                    label_897:
                        v35 = v36;
                        break;
                    }

                label_527:
                    if(0 != 0) {
                        try {
                            System.out.println("unquotedChars");
                        }
                        catch(RuntimeException v28) {
                            goto label_227;
                        }
                    }

                    v40 = v36;
                    v33 = 0;
                    v44 = 1;
                    if(v43 != 0) {
                        v35 = v36;
                        try {
                        label_536:
                            switch(data[v35]) {
                                case 47: {
                                    if(v35 + 1 != v27) {
                                        v21 = data[v35 + 1];
                                        if(v21 != 47) {
                                            if(v21 == 42) {
                                            }
                                            else {
                                                goto label_538;
                                            }
                                        }
                                    }
                                    else {
                                        goto label_538;
                                    }

                                    goto label_553;
                                }
                                case 10: 
                                case 13: 
                                case 58: {
                                    goto label_553;
                                }
                                case 92: {
                                    v33 = 1;
                                    break;
                                }
                            }

                        label_538:
                            if(0 != 0) {
                                System.out.println("unquotedChar (name): \'" + data[v35] + "\'");
                            }

                            ++v35;
                            if(v35 != v27) {
                                goto label_536;
                            }

                            goto label_553;
                        label_578:
                            v35 = v36;
                        label_579:
                            switch(data[v35]) {
                                case 47: {
                                    if(v35 + 1 == v27) {
                                        goto label_581;
                                    }

                                    v21 = data[v35 + 1];
                                    if(v21 == 47) {
                                        goto label_553;
                                    }

                                    if(v21 != 42) {
                                        goto label_581;
                                    }

                                    goto label_553;
                                }
                                case 92: {
                                    v33 = 1;
                                    goto label_581;
                                }
                                case 10: 
                                case 13: 
                                case 44: 
                                case 93: 
                                case 125: {
                                    goto label_553;
                                }
                            }
                        }
                        catch(RuntimeException v28) {
                            goto label_507;
                        }
                    }
                    else {
                        goto label_578;
                        try {
                        label_581:
                            if(0 != 0) {
                                System.out.println("unquotedChar (value): \'" + data[v35] + "\'");
                            }

                            ++v35;
                            if(v35 != v27) {
                                goto label_579;
                            }

                        label_553:
                            --v35;
                            while(true) {
                                if(data[v35] != 32) {
                                    goto label_113;
                                }

                                --v35;
                            }
                        }
                        catch(RuntimeException v28) {
                            goto label_507;
                        }
                    }

                    goto label_553;
                label_113:
                    v18 = v17;
                    v11 = v10;
                    v36 = v35;
                    continue;
                    try {
                    label_632:
                        v35 = v36;
                    label_633:
                        if(v24 != 0) {
                            goto label_636;
                        }

                        v12 = 5;
                        goto label_27;
                    label_636:
                        ++v35;
                        if(v35 == v38) {
                            goto label_642;
                        }

                        v12 = 1;
                        goto label_27;
                    label_642:
                        if(v35 != v27) {
                            goto label_29;
                        }

                        v7 = JsonReader._json_eof_actions[v24] + 1;
                        v9 = JsonReader._json_actions[JsonReader._json_eof_actions[v24]];
                        while(true) {
                        label_651:
                            v8 = v9 - 1;
                            if(v9 <= 0) {
                                goto label_29;
                            }

                            v6 = v7 + 1;
                            switch(JsonReader._json_actions[v7]) {
                                case 1: {
                                    goto label_660;
                                }
                            }

                            goto label_657;
                        label_660:
                            v47 = new String(data, v40, v35 - v40);
                            if(v33 != 0) {
                                v47 = this.unescape(v47);
                            }

                            if(v43 == 0) {
                                goto label_689;
                            }

                            v43 = 0;
                            if(0 != 0) {
                                System.out.println("name: " + v47);
                            }

                            v32.add(v47);
                            goto label_686;
                        label_689:
                            if(v32.size > 0) {
                                v31 = v32.pop();
                            }
                            else {
                                v31 = null;
                            }

                            if(v44 == 0) {
                                goto label_782;
                            }

                            if(!v47.equals("true")) {
                                goto label_719;
                            }

                            if(0 != 0) {
                                System.out.println("boolean: " + v31 + "=true");
                            }

                            this.bool(v31, true);
                            goto label_686;
                        label_719:
                            if(!v47.equals("false")) {
                                goto label_740;
                            }

                            if(0 != 0) {
                                System.out.println("boolean: " + v31 + "=false");
                            }

                            this.bool(v31, false);
                            goto label_686;
                        label_740:
                            if(!v47.equals("null")) {
                                goto label_749;
                            }

                            this.string(v31, null);
                            goto label_686;
                        label_749:
                            v22 = 0;
                            v23 = 1;
                            v29 = v40;
                            while(true) {
                            label_752:
                                if(v29 < v35) {
                                    switch(data[v29]) {
                                        case 43: 
                                        case 45: 
                                        case 48: 
                                        case 49: 
                                        case 50: 
                                        case 51: 
                                        case 52: 
                                        case 53: 
                                        case 54: 
                                        case 55: 
                                        case 56: 
                                        case 57: {
                                            goto label_804;
                                        }
                                        case 46: 
                                        case 69: 
                                        case 101: {
                                            goto label_802;
                                        }
                                    }

                                    break;
                                }

                                goto label_759;
                            }
                        }
                    }
                    catch(RuntimeException v28) {
                        goto label_507;
                    }

                    v22 = 0;
                    v23 = 0;
                    goto label_759;
                label_802:
                    v22 = 1;
                    v23 = 0;
                label_804:
                    ++v29;
                    goto label_752;
                label_759:
                    if(v22 == 0) {
                        goto label_806;
                    }

                    if(0 != 0) {
                        try {
                            System.out.println("double: " + v31 + "=" + Double.parseDouble(v47));
                        label_774:
                            this.number(v31, Double.parseDouble(v47), v47);
                            goto label_686;
                        }
                        catch(RuntimeException v28) {
                        }
                        catch(NumberFormatException v48) {
                            goto label_782;
                            try {
                            label_806:
                                if(v23 == 0) {
                                    goto label_782;
                                }

                                if(0 != 0) {
                                    System.out.println("double: " + v31 + "=" + Double.parseDouble(v47));
                                }

                                try {
                                    this.number(v31, Long.parseLong(v47), v47);
                                    goto label_686;
                                }
                                catch(NumberFormatException v48) {
                                }
                            }
                            catch(RuntimeException v28) {
                                goto label_507;
                            }

                        label_782:
                            if(0 != 0) {
                                try {
                                    System.out.println("string: " + v31 + "=" + v47);
                                label_797:
                                    this.string(v31, v47);
                                label_686:
                                    v44 = 0;
                                    v40 = v35;
                                label_657:
                                    v9 = v8;
                                    v7 = v6;
                                    goto label_651;
                                label_507:
                                    break;
                                }
                                catch(RuntimeException v28) {
                                    goto label_507;
                                }
                            }

                            goto label_797;
                        }
                    }

                    goto label_774;
                label_29:
                    JsonValue v39 = this.root;
                    this.root = null;
                    this.current = null;
                    this.lastChild.clear();
                    if(v35 >= v38) {
                        goto label_855;
                    }

                    int v30 = 1;
                    v29 = 0;
                    goto label_49;
                label_855:
                    if(this.elements.size != 0) {
                        Object v26 = this.elements.peek();
                        this.elements.clear();
                        if(v26 != null && (((JsonValue)v26).isObject())) {
                            throw new SerializationException("Error parsing JSON, unmatched brace.");
                        }

                        throw new SerializationException("Error parsing JSON, unmatched bracket.");
                    }

                    if(v37 != null) {
                        throw new SerializationException("Error parsing JSON: " + new String(data), v37);
                    }

                    return v39;
                    while(true) {
                    label_49:
                        if(v29 >= v35) {
                            break;
                        }

                        if(data[v29] == 10) {
                            ++v30;
                        }

                        ++v29;
                    }

                    throw new SerializationException("Error parsing JSON on line " + v30 + " near: " + new String(data, v35, Math.min(256, v38 - v35)), v37);
                }
            }

            goto label_287;
        }

        v37 = v28;
        goto label_29;
    }

    protected void pop() {
        Object v0;
        this.root = this.elements.pop();
        if(this.current.size > 0) {
            this.lastChild.pop();
        }

        if(this.elements.size > 0) {
            v0 = this.elements.peek();
        }
        else {
            JsonValue v0_1 = null;
        }

        this.current = ((JsonValue)v0);
    }

    protected void startArray(String name) {
        JsonValue v0 = new JsonValue(ValueType.array);
        if(this.current != null) {
            this.addChild(name, v0);
        }

        this.elements.add(v0);
        this.current = v0;
    }

    protected void startObject(String name) {
        JsonValue v0 = new JsonValue(ValueType.object);
        if(this.current != null) {
            this.addChild(name, v0);
        }

        this.elements.add(v0);
        this.current = v0;
    }

    protected void string(String name, String value) {
        this.addChild(name, new JsonValue(value));
    }

    private String unescape(String value) {
        int v4 = value.length();
        StringBuilder v0 = new StringBuilder(v4 + 16);
        int v3 = 0;
        while(v3 < v4) {
            int v2 = v3 + 1;
            char v1 = value.charAt(v3);
            if(v1 != 92) {
                v0.append(v1);
                v3 = v2;
                continue;
            }
            else if(v2 != v4) {
                v3 = v2 + 1;
                v1 = value.charAt(v2);
                if(v1 == 117) {
                    v0.append(Character.toChars(Integer.parseInt(value.substring(v3, v3 + 4), 16)));
                    v3 += 4;
                    continue;
                }
                else {
                    switch(v1) {
                        case 34: 
                        case 47: 
                        case 92: {
                            goto label_38;
                        }
                        case 98: {
                            goto label_37;
                        }
                        case 102: {
                            goto label_40;
                        }
                        case 110: {
                            goto label_42;
                        }
                        case 114: {
                            goto label_44;
                        }
                        case 116: {
                            goto label_46;
                        }
                    }

                    throw new SerializationException("Illegal escaped character: \\" + v1);
                label_37:
                    v1 = '\b';
                    goto label_38;
                label_40:
                    v1 = '\f';
                    goto label_38;
                label_42:
                    v1 = '\n';
                    goto label_38;
                label_44:
                    v1 = '\r';
                    goto label_38;
                label_46:
                    v1 = '\t';
                label_38:
                    v0.append(v1);
                    continue;
                }
            }

            break;
        }

        return v0.toString();
    }
}

