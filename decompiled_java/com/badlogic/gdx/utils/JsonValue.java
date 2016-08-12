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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JsonValue implements Iterable {
    public class JsonIterator implements Iterable, Iterator {
        JsonValue current;
        JsonValue entry;

        public JsonIterator(JsonValue arg2) {
            JsonValue.this = arg2;
            super();
            this.entry = JsonValue.this.child;
        }

        public boolean hasNext() {
            boolean v0;
            if(this.entry != null) {
                v0 = true;
            }
            else {
                v0 = false;
            }

            return v0;
        }

        public Iterator iterator() {
            return this;
        }

        public JsonValue next() {
            this.current = this.entry;
            if(this.current == null) {
                throw new NoSuchElementException();
            }

            this.entry = this.current.next;
            return this.current;
        }

        public Object next() {
            return this.next();
        }

        public void remove() {
            if(this.current.prev == null) {
                JsonValue.this.child = this.current.next;
                if(JsonValue.this.child != null) {
                    JsonValue.this.child.prev = null;
                }
            }
            else {
                this.current.prev.next = this.current.next;
                if(this.current.next != null) {
                    this.current.next.prev = this.current.prev;
                }
            }

            --JsonValue.this.size;
        }
    }

    public class PrettyPrintSettings {
        public PrettyPrintSettings() {
            super();
        }
    }

    public enum ValueType {
        public static final enum ValueType array;
        public static final enum ValueType booleanValue;
        public static final enum ValueType doubleValue;
        public static final enum ValueType longValue;
        public static final enum ValueType nullValue;
        public static final enum ValueType object;
        public static final enum ValueType stringValue;

        static  {
            ValueType.object = new ValueType("object", 0);
            ValueType.array = new ValueType("array", 1);
            ValueType.stringValue = new ValueType("stringValue", 2);
            ValueType.doubleValue = new ValueType("doubleValue", 3);
            ValueType.longValue = new ValueType("longValue", 4);
            ValueType.booleanValue = new ValueType("booleanValue", 5);
            ValueType.nullValue = new ValueType("nullValue", 6);
            ValueType[] v0 = new ValueType[7];
            v0[0] = ValueType.object;
            v0[1] = ValueType.array;
            v0[2] = ValueType.stringValue;
            v0[3] = ValueType.doubleValue;
            v0[4] = ValueType.longValue;
            v0[5] = ValueType.booleanValue;
            v0[6] = ValueType.nullValue;
            ValueType.$VALUES = v0;
        }

        private ValueType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static ValueType valueOf(String name) {
            return Enum.valueOf(ValueType.class, name);
        }

        public static ValueType[] values() {
            return ValueType.$VALUES.clone();
        }
    }

    public JsonValue child;
    private double doubleValue;
    private long longValue;
    public String name;
    public JsonValue next;
    public JsonValue prev;
    public int size;
    private String stringValue;
    private ValueType type;

    public JsonValue(String value) {
        super();
        this.set(value);
    }

    public JsonValue(double value) {
        super();
        this.set(value, null);
    }

    public JsonValue(double value, String stringValue) {
        super();
        this.set(value, stringValue);
    }

    public JsonValue(long value) {
        super();
        this.set(value, null);
    }

    public JsonValue(long value, String stringValue) {
        super();
        this.set(value, stringValue);
    }

    public JsonValue(ValueType type) {
        super();
        this.type = type;
    }

    public JsonValue(boolean value) {
        super();
        this.set(value);
    }

    public boolean asBoolean() {
        long v4 = 0;
        boolean v0 = true;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = this.stringValue.equalsIgnoreCase("true");
                break;
            }
            case 2: {
                if(this.doubleValue != 0) {
                    goto label_19;
                }

                v0 = false;
                break;
            }
            case 3: {
                if(this.longValue != v4) {
                    goto label_19;
                }

                v0 = false;
                break;
            }
            case 4: {
                if(this.longValue != v4) {
                    goto label_19;
                }

                v0 = false;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to boolean: " + this.type);
            }
        }

    label_19:
        return v0;
    }

    public boolean[] asBooleanArray() {
        boolean v2;
        long v10 = 0;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        boolean[] v0 = new boolean[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = Boolean.parseBoolean(v3.stringValue);
                    break;
                }
                case 2: {
                    if(v3.doubleValue != 0) {
                        goto label_43;
                    }

                    v2 = true;
                    goto label_34;
                label_43:
                    v2 = false;
                    break;
                }
                case 3: {
                    if(v3.longValue != v10) {
                        goto label_49;
                    }

                    v2 = true;
                    goto label_34;
                label_49:
                    v2 = false;
                    break;
                }
                case 4: {
                    if(v3.longValue == v10) {
                        goto label_55;
                    }

                    v2 = true;
                    goto label_34;
                label_55:
                    v2 = false;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to boolean: " + v3.type);
                }
            }

        label_34:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public byte asByte() {
        byte v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Byte.parseByte(this.stringValue);
                break;
            }
            case 2: {
                v0 = ((byte)(((int)this.doubleValue)));
                break;
            }
            case 3: {
                v0 = ((byte)(((int)this.longValue)));
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_29;
                }

                v0 = 1;
                goto label_15;
            label_29:
                v0 = 0;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to byte: " + this.type);
            }
        }

    label_15:
        return v0;
    }

    public byte[] asByteArray() {
        byte v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        byte[] v0 = new byte[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = Byte.parseByte(v3.stringValue);
                    break;
                }
                case 2: {
                    v2 = ((byte)(((int)v3.doubleValue)));
                    break;
                }
                case 3: {
                    v2 = ((byte)(((int)v3.longValue)));
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_48;
                    }

                    v2 = 1;
                    goto label_31;
                label_48:
                    v2 = 0;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to byte: " + v3.type);
                }
            }

        label_31:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public char asChar() {
        char v0 = '\u0000';
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                if(this.stringValue.length() != 0) {
                    goto label_18;
                }

                goto label_17;
            label_18:
                v0 = this.stringValue.charAt(0);
                break;
            }
            case 2: {
                v0 = ((char)(((int)this.doubleValue)));
                break;
            }
            case 3: {
                v0 = ((char)(((int)this.longValue)));
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_17;
                }

                v0 = '\u0001';
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to char: " + this.type);
            }
        }

    label_17:
        return v0;
    }

    public char[] asCharArray() {
        char v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        char[] v0 = new char[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    if(v3.stringValue.length() != 0) {
                        goto label_38;
                    }

                    v2 = '\u0000';
                    goto label_34;
                label_38:
                    v2 = v3.stringValue.charAt(0);
                    break;
                }
                case 2: {
                    v2 = ((char)(((int)v3.doubleValue)));
                    break;
                }
                case 3: {
                    v2 = ((char)(((int)v3.longValue)));
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_54;
                    }

                    v2 = '\u0001';
                    goto label_34;
                label_54:
                    v2 = '\u0000';
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to char: " + v3.type);
                }
            }

        label_34:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public double asDouble() {
        double v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Double.parseDouble(this.stringValue);
                break;
            }
            case 2: {
                v0 = this.doubleValue;
                break;
            }
            case 3: {
                v0 = ((double)this.longValue);
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_26;
                }

                v0 = 1;
                goto label_15;
            label_26:
                v0 = 0;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to double: " + this.type);
            }
        }

    label_15:
        return v0;
    }

    public double[] asDoubleArray() {
        double v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        double[] v0 = new double[this.size];
        int v1 = 0;
        JsonValue v4 = this.child;
        while(v4 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v4.type.ordinal()]) {
                case 1: {
                    v2 = Double.parseDouble(v4.stringValue);
                    break;
                }
                case 2: {
                    v2 = v4.doubleValue;
                    break;
                }
                case 3: {
                    v2 = ((double)v4.longValue);
                    break;
                }
                case 4: {
                    if(v4.longValue == 0) {
                        goto label_45;
                    }

                    v2 = 1;
                    goto label_31;
                label_45:
                    v2 = 0;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to double: " + v4.type);
                }
            }

        label_31:
            v0[v1] = v2;
            v4 = v4.next;
            ++v1;
        }

        return v0;
    }

    public float asFloat() {
        float v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Float.parseFloat(this.stringValue);
                break;
            }
            case 2: {
                v0 = ((float)this.doubleValue);
                break;
            }
            case 3: {
                v0 = ((float)this.longValue);
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_27;
                }

                v0 = 1f;
                goto label_15;
            label_27:
                v0 = 0f;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to float: " + this.type);
            }
        }

    label_15:
        return v0;
    }

    public float[] asFloatArray() {
        float v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        float[] v0 = new float[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = Float.parseFloat(v3.stringValue);
                    break;
                }
                case 2: {
                    v2 = ((float)v3.doubleValue);
                    break;
                }
                case 3: {
                    v2 = ((float)v3.longValue);
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_46;
                    }

                    v2 = 1f;
                    goto label_31;
                label_46:
                    v2 = 0f;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to float: " + v3.type);
                }
            }

        label_31:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public int asInt() {
        int v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Integer.parseInt(this.stringValue);
                break;
            }
            case 2: {
                v0 = ((int)this.doubleValue);
                break;
            }
            case 3: {
                v0 = ((int)this.longValue);
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_27;
                }

                v0 = 1;
                goto label_15;
            label_27:
                v0 = 0;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to int: " + this.type);
            }
        }

    label_15:
        return v0;
    }

    public int[] asIntArray() {
        int v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        int[] v0 = new int[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = Integer.parseInt(v3.stringValue);
                    break;
                }
                case 2: {
                    v2 = ((int)v3.doubleValue);
                    break;
                }
                case 3: {
                    v2 = ((int)v3.longValue);
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_46;
                    }

                    v2 = 1;
                    goto label_31;
                label_46:
                    v2 = 0;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to int: " + v3.type);
                }
            }

        label_31:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public long asLong() {
        long v0 = 0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Long.parseLong(this.stringValue);
                break;
            }
            case 2: {
                v0 = ((long)this.doubleValue);
                break;
            }
            case 3: {
                v0 = this.longValue;
                break;
            }
            case 4: {
                if(this.longValue == v0) {
                    goto label_16;
                }

                v0 = 1;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to long: " + this.type);
            }
        }

    label_16:
        return v0;
    }

    public long[] asLongArray() {
        long v2;
        long v6 = 0;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        long[] v0 = new long[this.size];
        int v1 = 0;
        JsonValue v4 = this.child;
        while(v4 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v4.type.ordinal()]) {
                case 1: {
                    v2 = Long.parseLong(v4.stringValue);
                    break;
                }
                case 2: {
                    v2 = ((long)v4.doubleValue);
                    break;
                }
                case 3: {
                    v2 = v4.longValue;
                    break;
                }
                case 4: {
                    if(v4.longValue == v6) {
                        goto label_45;
                    }

                    v2 = 1;
                    goto label_32;
                label_45:
                    v2 = v6;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to long: " + v4.type);
                }
            }

        label_32:
            v0[v1] = v2;
            v4 = v4.next;
            ++v1;
        }

        return v0;
    }

    public short asShort() {
        short v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = Short.parseShort(this.stringValue);
                break;
            }
            case 2: {
                v0 = ((short)(((int)this.doubleValue)));
                break;
            }
            case 3: {
                v0 = ((short)(((int)this.longValue)));
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_29;
                }

                v0 = 1;
                goto label_15;
            label_29:
                v0 = 0;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to short: " + this.type);
            }
        }

    label_15:
        return v0;
    }

    public short[] asShortArray() {
        short v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        short[] v0 = new short[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = Short.parseShort(v3.stringValue);
                    break;
                }
                case 2: {
                    v2 = ((short)(((int)v3.doubleValue)));
                    break;
                }
                case 3: {
                    v2 = ((short)(((int)v3.longValue)));
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_48;
                    }

                    v2 = 1;
                    goto label_31;
                label_48:
                    v2 = 0;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to short: " + v3.type);
                }
            }

        label_31:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public String asString() {
        String v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: {
                v0 = this.stringValue;
                break;
            }
            case 2: {
                if(this.stringValue == null) {
                    goto label_19;
                }

                v0 = this.stringValue;
                goto label_14;
            label_19:
                v0 = Double.toString(this.doubleValue);
                break;
            }
            case 3: {
                if(this.stringValue == null) {
                    goto label_26;
                }

                v0 = this.stringValue;
                goto label_14;
            label_26:
                v0 = Long.toString(this.longValue);
                break;
            }
            case 4: {
                if(this.longValue == 0) {
                    goto label_34;
                }

                v0 = "true";
                goto label_14;
            label_34:
                v0 = "false";
                break;
            }
            case 5: {
                v0 = null;
                break;
            }
            default: {
                throw new IllegalStateException("Value cannot be converted to string: " + this.type);
            }
        }

    label_14:
        return v0;
    }

    public String[] asStringArray() {
        String v2;
        if(this.type != ValueType.array) {
            throw new IllegalStateException("Value is not an array: " + this.type);
        }

        String[] v0 = new String[this.size];
        int v1 = 0;
        JsonValue v3 = this.child;
        while(v3 != null) {
            switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[v3.type.ordinal()]) {
                case 1: {
                    v2 = v3.stringValue;
                    break;
                }
                case 2: {
                    if(this.stringValue == null) {
                        goto label_38;
                    }

                    v2 = this.stringValue;
                    goto label_30;
                label_38:
                    v2 = Double.toString(v3.doubleValue);
                    break;
                }
                case 3: {
                    if(this.stringValue == null) {
                        goto label_45;
                    }

                    v2 = this.stringValue;
                    goto label_30;
                label_45:
                    v2 = Long.toString(v3.longValue);
                    break;
                }
                case 4: {
                    if(v3.longValue == 0) {
                        goto label_53;
                    }

                    v2 = "true";
                    goto label_30;
                label_53:
                    v2 = "false";
                    break;
                }
                case 5: {
                    v2 = null;
                    break;
                }
                default: {
                    throw new IllegalStateException("Value cannot be converted to string: " + v3.type);
                }
            }

        label_30:
            v0[v1] = v2;
            v3 = v3.next;
            ++v1;
        }

        return v0;
    }

    public JsonValue child() {
        return this.child;
    }

    public JsonValue get(String name) {
        JsonValue v0 = this.child;
        while(v0 != null) {
            if(v0.name.equalsIgnoreCase(name)) {
                break;
            }

            v0 = v0.next;
        }

        return v0;
    }

    public JsonValue get(int index) {
        JsonValue v0 = this.child;
        while(v0 != null) {
            if(index <= 0) {
                break;
            }

            --index;
            v0 = v0.next;
        }

        return v0;
    }

    public boolean getBoolean(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asBoolean();
    }

    public boolean getBoolean(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asBoolean();
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asBoolean();
        }

        return defaultValue;
    }

    public byte getByte(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asByte();
    }

    public byte getByte(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asByte();
    }

    public byte getByte(String name, byte defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asByte();
        }

        return defaultValue;
    }

    public char getChar(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asChar();
    }

    public char getChar(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asChar();
    }

    public char getChar(String name, char defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asChar();
        }

        return defaultValue;
    }

    public JsonValue getChild(String name) {
        JsonValue v1;
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            v1 = null;
        }
        else {
            v1 = v0.child;
        }

        return v1;
    }

    public double getDouble(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asDouble();
    }

    public double getDouble(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asDouble();
    }

    public double getDouble(String name, double defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asDouble();
        }

        return defaultValue;
    }

    public float getFloat(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asFloat();
    }

    public float getFloat(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asFloat();
    }

    public float getFloat(String name, float defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asFloat();
        }

        return defaultValue;
    }

    public int getInt(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asInt();
    }

    public int getInt(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asInt();
    }

    public int getInt(String name, int defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asInt();
        }

        return defaultValue;
    }

    public long getLong(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asLong();
    }

    public long getLong(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asLong();
    }

    public long getLong(String name, long defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asLong();
        }

        return defaultValue;
    }

    public short getShort(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asShort();
    }

    public short getShort(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asShort();
    }

    public short getShort(String name, short defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue())) {
            defaultValue = v0.asShort();
        }

        return defaultValue;
    }

    public String getString(String name, String defaultValue) {
        JsonValue v0 = this.get(name);
        if(v0 != null && (v0.isValue()) && !v0.isNull()) {
            defaultValue = v0.asString();
        }

        return defaultValue;
    }

    public String getString(int index) {
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            throw new IllegalArgumentException("Indexed value not found: " + this.name);
        }

        return v0.asString();
    }

    public String getString(String name) {
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            throw new IllegalArgumentException("Named value not found: " + name);
        }

        return v0.asString();
    }

    public boolean has(String name) {
        boolean v0;
        if(this.get(name) != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean hasChild(String name) {
        boolean v0;
        if(this.getChild(name) != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private static void indent(int count, StringBuilder buffer) {
        int v0;
        for(v0 = 0; v0 < count; ++v0) {
            buffer.append('\t');
        }
    }

    public boolean isArray() {
        boolean v0;
        if(this.type == ValueType.array) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isBoolean() {
        boolean v0;
        if(this.type == ValueType.booleanValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isDouble() {
        boolean v0;
        if(this.type == ValueType.doubleValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private static boolean isFlat(JsonValue object) {
        JsonValue v0 = object.child;
        while(true) {
            if(v0 != null) {
                if(!v0.isObject() && !v0.isArray()) {
                    v0 = v0.next;
                    continue;
                }

                break;
            }
            else {
                goto label_10;
            }
        }

        boolean v1 = false;
        goto label_7;
    label_10:
        v1 = true;
    label_7:
        return v1;
    }

    public boolean isLong() {
        boolean v0;
        if(this.type == ValueType.longValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isNull() {
        boolean v0;
        if(this.type == ValueType.nullValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isNumber() {
        boolean v0;
        if(this.type == ValueType.doubleValue || this.type == ValueType.longValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private static boolean isNumeric(JsonValue object) {
        boolean v1;
        JsonValue v0 = object.child;
        while(true) {
            if(v0 == null) {
                break;
            }
            else if(!v0.isNumber()) {
                v1 = false;
            }
            else {
                v0 = v0.next;
                continue;
            }

            goto label_5;
        }

        v1 = true;
    label_5:
        return v1;
    }

    public boolean isObject() {
        boolean v0;
        if(this.type == ValueType.object) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isString() {
        boolean v0;
        if(this.type == ValueType.stringValue) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isValue() {
        boolean v0;
        switch(com.badlogic.gdx.utils.JsonValue$1.$SwitchMap$com$badlogic$gdx$utils$JsonValue$ValueType[this.type.ordinal()]) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                v0 = true;
                break;
            }
            default: {
                v0 = false;
                break;
            }
        }

        return v0;
    }

    public JsonIterator iterator() {
        return new JsonIterator(this);
    }

    public Iterator iterator() {
        return this.iterator();
    }

    public String name() {
        return this.name;
    }

    public JsonValue next() {
        return this.next;
    }

    public String prettyPrint(OutputType outputType, int singleLineColumns) {
        PrettyPrintSettings v0 = new PrettyPrintSettings();
        v0.outputType = outputType;
        v0.singleLineColumns = singleLineColumns;
        return this.prettyPrint(v0);
    }

    public String prettyPrint(PrettyPrintSettings settings) {
        StringBuilder v0 = new StringBuilder(512);
        this.prettyPrint(this, v0, 0, settings);
        return v0.toString();
    }

    private void prettyPrint(JsonValue object, StringBuilder buffer, int indent, PrettyPrintSettings settings) {
        char v12_1;
        JsonValue v2;
        String v12;
        int v10;
        int v8;
        int v11;
        OutputType v9 = settings.outputType;
        if(!object.isObject()) {
            if(!object.isArray()) {
                goto label_151;
            }

            if(object.child != null) {
                goto label_85;
            }

            buffer.append("[]");
            return;
        label_85:
            if(!JsonValue.isFlat(object)) {
                v8 = 1;
            }
            else {
                v8 = 0;
            }

            if((settings.wrapNumericArrays) || !JsonValue.isNumeric(object)) {
                v11 = 1;
            }
            else {
                v11 = 0;
            }

            v10 = buffer.length();
            while(true) {
            label_95:
                if(v8 != 0) {
                    v12 = "[\n";
                }
                else {
                    v12 = "[ ";
                }

                buffer.append(v12);
                v2 = object.child;
                while(true) {
                label_101:
                    if(v2 == null) {
                        goto label_143;
                    }

                    if(v8 != 0) {
                        JsonValue.indent(indent, buffer);
                    }

                    this.prettyPrint(v2, buffer, indent + 1, settings);
                    if((v8 == 0 || v9 != OutputType.minimal) && v2.next != null) {
                        buffer.append(',');
                    }

                    if(v8 != 0) {
                        v12_1 = '\n';
                    }
                    else {
                        v12_1 = ' ';
                    }

                    buffer.append(v12_1);
                    if(v11 != 0 && v8 == 0 && buffer.length() - v10 > settings.singleLineColumns) {
                        buffer.setLength(v10);
                        v8 = 1;
                        goto label_95;
                    }

                    break;
                }
            }

            v2 = v2.next;
            goto label_101;
        label_143:
            if(v8 != 0) {
                JsonValue.indent(indent - 1, buffer);
            }

            buffer.append(']');
            return;
        label_151:
            if(!object.isString()) {
                goto label_158;
            }

            buffer.append(v9.quoteValue(object.asString()));
            return;
        label_158:
            if(!object.isDouble()) {
                goto label_168;
            }

            double v4 = object.asDouble();
            long v6 = object.asLong();
            if(v4 == (((double)v6))) {
                v4 = ((double)v6);
            }

            buffer.append(v4);
            return;
        label_168:
            if(!object.isLong()) {
                goto label_174;
            }

            buffer.append(object.asLong());
            return;
        label_174:
            if(!object.isBoolean()) {
                goto label_180;
            }

            buffer.append(object.asBoolean());
            return;
        label_180:
            if(!object.isNull()) {
                goto label_186;
            }

            buffer.append("null");
        }
        else if(object.child == null) {
            buffer.append("{}");
        }
        else {
            if(!JsonValue.isFlat(object)) {
                v8 = 1;
            }
            else {
                v8 = 0;
            }

            v10 = buffer.length();
            while(true) {
            label_15:
                if(v8 != 0) {
                    v12 = "{\n";
                }
                else {
                    v12 = "{ ";
                }

                buffer.append(v12);
                v2 = object.child;
                while(true) {
                label_22:
                    if(v2 == null) {
                        goto label_68;
                    }

                    if(v8 != 0) {
                        JsonValue.indent(indent, buffer);
                    }

                    buffer.append(v9.quoteName(v2.name));
                    buffer.append(": ");
                    this.prettyPrint(v2, buffer, indent + 1, settings);
                    if((v8 == 0 || v9 != OutputType.minimal) && v2.next != null) {
                        buffer.append(',');
                    }

                    if(v8 != 0) {
                        v12_1 = '\n';
                    }
                    else {
                        v12_1 = ' ';
                    }

                    buffer.append(v12_1);
                    if(v8 == 0 && buffer.length() - v10 > settings.singleLineColumns) {
                        buffer.setLength(v10);
                        v8 = 1;
                        goto label_15;
                    }

                    break;
                }
            }

            v2 = v2.next;
            goto label_22;
        label_68:
            if(v8 != 0) {
                JsonValue.indent(indent - 1, buffer);
            }

            buffer.append('}');
        }

        return;
    label_186:
        throw new SerializationException("Unknown object type: " + object);
    }

    public JsonValue prev() {
        return this.prev;
    }

    public JsonValue remove(String name) {
        JsonValue v1 = null;
        JsonValue v0 = this.get(name);
        if(v0 == null) {
            v0 = v1;
        }
        else {
            if(v0.prev == null) {
                this.child = v0.next;
                if(this.child != null) {
                    this.child.prev = v1;
                }
            }
            else {
                v0.prev.next = v0.next;
                if(v0.next != null) {
                    v0.next.prev = v0.prev;
                }
            }

            --this.size;
        }

        return v0;
    }

    public JsonValue remove(int index) {
        JsonValue v1 = null;
        JsonValue v0 = this.get(index);
        if(v0 == null) {
            v0 = v1;
        }
        else {
            if(v0.prev == null) {
                this.child = v0.next;
                if(this.child != null) {
                    this.child.prev = v1;
                }
            }
            else {
                v0.prev.next = v0.next;
                if(v0.next != null) {
                    v0.next.prev = v0.prev;
                }
            }

            --this.size;
        }

        return v0;
    }

    public JsonValue require(int index) {
        JsonValue v0 = this.child;
        while(v0 != null) {
            if(index <= 0) {
                break;
            }

            --index;
            v0 = v0.next;
        }

        if(v0 == null) {
            throw new IllegalArgumentException("Child not found with index: " + index);
        }

        return v0;
    }

    public JsonValue require(String name) {
        JsonValue v0 = this.child;
        while(v0 != null) {
            if(v0.name.equalsIgnoreCase(name)) {
                break;
            }

            v0 = v0.next;
        }

        if(v0 == null) {
            throw new IllegalArgumentException("Child not found with name: " + name);
        }

        return v0;
    }

    public void set(double value, String stringValue) {
        this.doubleValue = value;
        this.longValue = ((long)value);
        this.stringValue = stringValue;
        this.type = ValueType.doubleValue;
    }

    public void set(long value, String stringValue) {
        this.longValue = value;
        this.doubleValue = ((double)value);
        this.stringValue = stringValue;
        this.type = ValueType.longValue;
    }

    public void set(String value) {
        ValueType v0;
        this.stringValue = value;
        if(value == null) {
            v0 = ValueType.nullValue;
        }
        else {
            v0 = ValueType.stringValue;
        }

        this.type = v0;
    }

    public void set(boolean value) {
        long v0;
        if(value) {
            v0 = 1;
        }
        else {
            v0 = 0;
        }

        this.longValue = v0;
        this.type = ValueType.booleanValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNext(JsonValue next) {
        this.next = next;
    }

    public void setPrev(JsonValue prev) {
        this.prev = prev;
    }

    public void setType(ValueType type) {
        if(type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }

        this.type = type;
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        String v0;
        if(!this.isValue()) {
            java.lang.StringBuilder v1 = new java.lang.StringBuilder();
            if(this.name == null) {
                v0 = "";
            }
            else {
                v0 = this.name + ": ";
            }

            v0 = v1.append(v0).append(this.prettyPrint(OutputType.minimal, 0)).toString();
        }
        else if(this.name == null) {
            v0 = this.asString();
        }
        else {
            v0 = this.name + ": " + this.asString();
        }

        return v0;
    }

    public ValueType type() {
        return this.type;
    }
}

