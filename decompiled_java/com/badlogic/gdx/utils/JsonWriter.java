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

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class JsonWriter extends Writer {
    class JsonObject {
        JsonObject(JsonWriter arg3, boolean array) throws IOException {
            int v0;
            JsonWriter.this = arg3;
            super();
            this.array = array;
            Writer v1 = arg3.writer;
            if(array) {
                v0 = 91;
            }
            else {
                v0 = 123;
            }

            v1.write(v0);
        }

        void close() throws IOException {
            int v0;
            Writer v1 = JsonWriter.this.writer;
            if(this.array) {
                v0 = 93;
            }
            else {
                v0 = 125;
            }

            v1.write(v0);
        }
    }

    public enum OutputType {
        public static final enum OutputType javascript;
        private static Pattern javascriptPattern;
        public static final enum OutputType json;
        private static Pattern minimalNamePattern;
        private static Pattern minimalValuePattern;

        static  {
            OutputType.json = new OutputType("json", 0);
            OutputType.javascript = new OutputType("javascript", 1);
            OutputType.minimal = new OutputType("minimal", 2);
            OutputType[] v0 = new OutputType[3];
            v0[0] = OutputType.json;
            v0[1] = OutputType.javascript;
            v0[2] = OutputType.minimal;
            OutputType.$VALUES = v0;
            OutputType.javascriptPattern = Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$");
            OutputType.minimalNamePattern = Pattern.compile("^[^\":,}/ ][^:]*$");
            OutputType.minimalValuePattern = Pattern.compile("^[^\":,{\\[\\]/ ][^}\\],]*$");
        }

        private OutputType(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public String quoteName(String value) {
            char v3 = '\"';
            value = value.replace("\\", "\\\\").replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t");
            switch(com.badlogic.gdx.utils.JsonWriter$1.$SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[this.ordinal()]) {
                case 1: {
                    if(value.contains("//")) {
                        goto label_36;
                    }

                    if(value.contains("/*")) {
                        goto label_36;
                    }

                    if(OutputType.minimalNamePattern.matcher(((CharSequence)value)).matches()) {
                        goto label_25;
                    }

                    goto label_36;
                }
                case 2: {
                label_36:
                    if(!OutputType.javascriptPattern.matcher(((CharSequence)value)).matches()) {
                        goto label_17;
                    }

                    break;
                }
                default: {
                label_17:
                    value = v3 + value.replace("\"", "\\\"") + v3;
                    break;
                }
            }

        label_25:
            return value;
        }

        public String quoteValue(Object value) {
            String v1;
            char v5 = '\"';
            if(value == null) {
                v1 = "null";
            }
            else {
                v1 = value.toString();
                if(!(value instanceof Number) && !(value instanceof Boolean)) {
                    v1 = v1.replace("\\", "\\\\").replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t");
                    if(this == OutputType.minimal && !v1.equals("true") && !v1.equals("false") && !v1.equals("null") && !v1.contains("//") && !v1.contains("/*")) {
                        int v0 = v1.length();
                        if(v0 > 0 && v1.charAt(v0 - 1) != 32) {
                            if(!OutputType.minimalValuePattern.matcher(((CharSequence)v1)).matches()) {
                                goto label_48;
                            }

                            goto label_3;
                        }
                    }

                label_48:
                    v1 = v5 + v1.replace("\"", "\\\"") + v5;
                }
            }

        label_3:
            return v1;
        }

        public static OutputType valueOf(String name) {
            return Enum.valueOf(OutputType.class, name);
        }

        public static OutputType[] values() {
            return OutputType.$VALUES.clone();
        }
    }

    private JsonObject current;
    private boolean named;
    private OutputType outputType;
    private boolean quoteLongValues;
    private final Array stack;
    final Writer writer;

    public JsonWriter(Writer writer) {
        super();
        this.stack = new Array();
        this.outputType = OutputType.json;
        this.quoteLongValues = false;
        this.writer = writer;
    }

    public JsonWriter array() throws IOException {
        this.requireCommaOrName();
        Array v0 = this.stack;
        JsonObject v1 = new JsonObject(this, true);
        this.current = v1;
        v0.add(v1);
        return this;
    }

    public JsonWriter array(String name) throws IOException {
        return this.name(name).array();
    }

    public void close() throws IOException {
        while(this.stack.size > 0) {
            this.pop();
        }

        this.writer.close();
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public Writer getWriter() {
        return this.writer;
    }

    public JsonWriter json(String json) throws IOException {
        this.requireCommaOrName();
        this.writer.write(json);
        return this;
    }

    public JsonWriter json(String name, String json) throws IOException {
        return this.name(name).json(json);
    }

    public JsonWriter name(String name) throws IOException {
        if(this.current != null && !this.current.array) {
            if(!this.current.needsComma) {
                this.current.needsComma = true;
            }
            else {
                this.writer.write(44);
            }

            this.writer.write(this.outputType.quoteName(name));
            this.writer.write(58);
            this.named = true;
            return this;
        }

        throw new IllegalStateException("Current item must be an object.");
    }

    public JsonWriter object() throws IOException {
        this.requireCommaOrName();
        Array v0 = this.stack;
        JsonObject v1 = new JsonObject(this, false);
        this.current = v1;
        v0.add(v1);
        return this;
    }

    public JsonWriter object(String name) throws IOException {
        return this.name(name).object();
    }

    public JsonWriter pop() throws IOException {
        Object v0_1;
        if(this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }

        this.stack.pop().close();
        if(this.stack.size == 0) {
            JsonObject v0 = null;
        }
        else {
            v0_1 = this.stack.peek();
        }

        this.current = ((JsonObject)v0_1);
        return this;
    }

    private void requireCommaOrName() throws IOException {
        if(this.current != null) {
            if(this.current.array) {
                if(!this.current.needsComma) {
                    this.current.needsComma = true;
                }
                else {
                    this.writer.write(44);
                }
            }
            else if(!this.named) {
                throw new IllegalStateException("Name must be set.");
            }
            else {
                this.named = false;
            }
        }
    }

    public JsonWriter set(String name, Object value) throws IOException {
        return this.name(name).value(value);
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean quoteLongValues) {
        this.quoteLongValues = quoteLongValues;
    }

    public JsonWriter value(Object value) throws IOException {
        Long v9_2;
        if(this.quoteLongValues) {
            if(!(value instanceof Long) && !(value instanceof Double) && !(value instanceof BigDecimal) && !(value instanceof BigInteger)) {
                goto label_17;
            }

            String v9_1 = value.toString();
        }
        else {
        label_17:
            if(!(value instanceof Number)) {
                goto label_11;
            }

            long v0 = value.longValue();
            if(value.doubleValue() != (((double)v0))) {
                goto label_11;
            }

            v9_2 = Long.valueOf(v0);
        }

    label_11:
        this.requireCommaOrName();
        this.writer.write(this.outputType.quoteValue(v9_2));
        return this;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.writer.write(cbuf, off, len);
    }
}

