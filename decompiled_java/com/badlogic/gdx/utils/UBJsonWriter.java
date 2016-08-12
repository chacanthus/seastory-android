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

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UBJsonWriter implements Closeable {
    class JsonObject {
        JsonObject(UBJsonWriter arg3, boolean array) throws IOException {
            int v0;
            UBJsonWriter.this = arg3;
            super();
            this.array = array;
            DataOutputStream v1 = arg3.out;
            if(array) {
                v0 = 91;
            }
            else {
                v0 = 123;
            }

            v1.writeByte(v0);
        }

        void close() throws IOException {
            int v0;
            DataOutputStream v1 = UBJsonWriter.this.out;
            if(this.array) {
                v0 = 93;
            }
            else {
                v0 = 125;
            }

            v1.writeByte(v0);
        }
    }

    private JsonObject current;
    private boolean named;
    final DataOutputStream out;
    private final Array stack;

    public UBJsonWriter(OutputStream out) {
        DataOutputStream v3_1;
        super();
        this.stack = new Array();
        if(!(out instanceof DataOutputStream)) {
            v3_1 = new DataOutputStream(out);
        }

        this.out = v3_1;
    }

    public UBJsonWriter array() throws IOException {
        if(this.current != null && !this.current.array) {
            if(!this.named) {
                throw new IllegalStateException("Name must be set.");
            }
            else {
                this.named = false;
            }
        }

        Array v0 = this.stack;
        JsonObject v1 = new JsonObject(this, true);
        this.current = v1;
        v0.add(v1);
        return this;
    }

    public UBJsonWriter array(String name) throws IOException {
        this.name(name).array();
        return this;
    }

    private void checkName() {
        if(this.current != null && !this.current.array) {
            if(!this.named) {
                throw new IllegalStateException("Name must be set.");
            }
            else {
                this.named = false;
            }
        }
    }

    public void close() throws IOException {
        while(this.stack.size > 0) {
            this.pop();
        }

        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public UBJsonWriter name(String name) throws IOException {
        if(this.current != null && !this.current.array) {
            byte[] v0 = name.getBytes("UTF-8");
            if(v0.length <= 127) {
                this.out.writeByte(105);
                this.out.writeByte(v0.length);
            }
            else if(v0.length <= 32767) {
                this.out.writeByte(73);
                this.out.writeShort(v0.length);
            }
            else {
                this.out.writeByte(108);
                this.out.writeInt(v0.length);
            }

            this.out.write(v0);
            this.named = true;
            return this;
        }

        throw new IllegalStateException("Current item must be an object.");
    }

    public UBJsonWriter object() throws IOException {
        if(this.current != null && !this.current.array) {
            if(!this.named) {
                throw new IllegalStateException("Name must be set.");
            }
            else {
                this.named = false;
            }
        }

        Array v0 = this.stack;
        JsonObject v1 = new JsonObject(this, false);
        this.current = v1;
        v0.add(v1);
        return this;
    }

    public UBJsonWriter object(String name) throws IOException {
        this.name(name).object();
        return this;
    }

    public UBJsonWriter pop() throws IOException {
        return this.pop(false);
    }

    protected UBJsonWriter pop(boolean silent) throws IOException {
        Object v0_1;
        if(this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }

        if(silent) {
            this.stack.pop();
        }
        else {
            this.stack.pop().close();
        }

        if(this.stack.size == 0) {
            JsonObject v0 = null;
        }
        else {
            v0_1 = this.stack.peek();
        }

        this.current = ((JsonObject)v0_1);
        return this;
    }

    public UBJsonWriter set(String name) throws IOException {
        return this.name(name).value();
    }

    public UBJsonWriter set(String name, byte value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, char value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, double value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, float value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, int value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, long value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, String value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, short value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, boolean value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, byte[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, char[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, double[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, float[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, int[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, long[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, String[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, short[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter set(String name, boolean[] value) throws IOException {
        return this.name(name).value(value);
    }

    public UBJsonWriter value() throws IOException {
        this.checkName();
        this.out.writeByte(90);
        return this;
    }

    public UBJsonWriter value(byte value) throws IOException {
        this.checkName();
        this.out.writeByte(105);
        this.out.writeByte(value);
        return this;
    }

    public UBJsonWriter value(char value) throws IOException {
        this.checkName();
        this.out.writeByte(73);
        this.out.writeChar(value);
        return this;
    }

    public UBJsonWriter value(double value) throws IOException {
        this.checkName();
        this.out.writeByte(68);
        this.out.writeDouble(value);
        return this;
    }

    public UBJsonWriter value(float value) throws IOException {
        this.checkName();
        this.out.writeByte(100);
        this.out.writeFloat(value);
        return this;
    }

    public UBJsonWriter value(int value) throws IOException {
        this.checkName();
        this.out.writeByte(108);
        this.out.writeInt(value);
        return this;
    }

    public UBJsonWriter value(long value) throws IOException {
        this.checkName();
        this.out.writeByte(76);
        this.out.writeLong(value);
        return this;
    }

    public UBJsonWriter value(String value) throws IOException {
        this.checkName();
        byte[] v0 = value.getBytes("UTF-8");
        this.out.writeByte(83);
        if(v0.length <= 127) {
            this.out.writeByte(105);
            this.out.writeByte(v0.length);
        }
        else if(v0.length <= 32767) {
            this.out.writeByte(73);
            this.out.writeShort(v0.length);
        }
        else {
            this.out.writeByte(108);
            this.out.writeInt(v0.length);
        }

        this.out.write(v0);
        return this;
    }

    public UBJsonWriter value(short value) throws IOException {
        this.checkName();
        this.out.writeByte(73);
        this.out.writeShort(value);
        return this;
    }

    public UBJsonWriter value(boolean value) throws IOException {
        int v0;
        this.checkName();
        DataOutputStream v1 = this.out;
        if(value) {
            v0 = 84;
        }
        else {
            v0 = 70;
        }

        v1.writeByte(v0);
        return this;
    }

    public UBJsonWriter value(byte[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(105);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeByte(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(char[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(73);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeChar(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(double[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(100);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeDouble(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(float[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(100);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeFloat(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(int[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(108);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeInt(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(long[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(73);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeLong(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(String[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(83);
        this.out.writeByte(35);
        this.value(values.length);
        int v1 = 0;
        int v2 = values.length;
        while(v1 < v2) {
            byte[] v0 = values[v1].getBytes("UTF-8");
            if(v0.length <= 127) {
                this.out.writeByte(105);
                this.out.writeByte(v0.length);
            }
            else if(v0.length <= 32767) {
                this.out.writeByte(73);
                this.out.writeShort(v0.length);
            }
            else {
                this.out.writeByte(108);
                this.out.writeInt(v0.length);
            }

            this.out.write(v0);
            ++v1;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(short[] values) throws IOException {
        this.array();
        this.out.writeByte(36);
        this.out.writeByte(73);
        this.out.writeByte(35);
        this.value(values.length);
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            this.out.writeShort(values[v0]);
            ++v0;
        }

        this.pop(true);
        return this;
    }

    public UBJsonWriter value(boolean[] values) throws IOException {
        int v2;
        this.array();
        int v0 = 0;
        int v1 = values.length;
        while(v0 < v1) {
            DataOutputStream v3 = this.out;
            if(values[v0]) {
                v2 = 84;
            }
            else {
                v2 = 70;
            }

            v3.writeByte(v2);
            ++v0;
        }

        this.pop();
        return this;
    }

    public UBJsonWriter value(JsonValue value) throws IOException {
        if(value.isObject()) {
            if(value.name != null) {
                this.object(value.name);
            }
            else {
                this.object();
            }

            JsonValue v0;
            for(v0 = value.child; v0 != null; v0 = v0.next) {
                this.value(v0);
            }

            this.pop();
        }
        else {
            if(!value.isArray()) {
                goto label_30;
            }

            if(value.name != null) {
                this.array(value.name);
            }
            else {
                this.array();
            }

            for(v0 = value.child; v0 != null; v0 = v0.next) {
                this.value(v0);
            }

            this.pop();
            goto label_14;
        label_30:
            if(!value.isBoolean()) {
                goto label_39;
            }

            if(value.name != null) {
                this.name(value.name);
            }

            this.value(value.asBoolean());
            goto label_14;
        label_39:
            if(!value.isDouble()) {
                goto label_48;
            }

            if(value.name != null) {
                this.name(value.name);
            }

            this.value(value.asDouble());
            goto label_14;
        label_48:
            if(!value.isLong()) {
                goto label_57;
            }

            if(value.name != null) {
                this.name(value.name);
            }

            this.value(value.asLong());
            goto label_14;
        label_57:
            if(!value.isString()) {
                goto label_66;
            }

            if(value.name != null) {
                this.name(value.name);
            }

            this.value(value.asString());
            goto label_14;
        label_66:
            if(!value.isNull()) {
                goto label_74;
            }

            if(value.name != null) {
                this.name(value.name);
            }

            this.value();
        }

    label_14:
        return this;
    label_74:
        throw new IOException("Unhandled JsonValue type");
    }

    public UBJsonWriter value(Object object) throws IOException {
        if(object == null) {
            this = this.value();
        }
        else if((object instanceof Number)) {
            Object v0 = object;
            if((object instanceof Byte)) {
                this = this.value(((Number)v0).byteValue());
            }
            else if((object instanceof Short)) {
                this = this.value(((Number)v0).shortValue());
            }
            else if((object instanceof Integer)) {
                this = this.value(((Number)v0).intValue());
            }
            else if((object instanceof Long)) {
                this = this.value(((Number)v0).longValue());
            }
            else if((object instanceof Float)) {
                this = this.value(((Number)v0).floatValue());
            }
            else if((object instanceof Double)) {
                this = this.value(((Number)v0).doubleValue());
            }
        }
        else if((object instanceof Character)) {
            this = this.value(((Character)object).charValue());
        }
        else if((object instanceof CharSequence)) {
            this = this.value(object.toString());
        }
        else {
            goto label_46;
        }

        return this;
    label_46:
        throw new IOException("Unknown object type.");
    }
}

