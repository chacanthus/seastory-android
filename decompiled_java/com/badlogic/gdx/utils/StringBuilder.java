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
import java.util.Arrays;

public class StringBuilder implements Appendable, CharSequence {
    static final int INITIAL_CAPACITY = 16;
    public char[] chars;
    private static final char[] digits;
    public int length;

    static  {
        StringBuilder.digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    public StringBuilder() {
        super();
        this.chars = new char[16];
    }

    public StringBuilder(int capacity) {
        super();
        if(capacity < 0) {
            throw new NegativeArraySizeException();
        }

        this.chars = new char[capacity];
    }

    public StringBuilder(String string) {
        super();
        this.length = string.length();
        this.chars = new char[this.length + 16];
        string.getChars(0, this.length, this.chars, 0);
    }

    public StringBuilder(StringBuilder builder) {
        super();
        this.length = builder.length;
        this.chars = new char[this.length + 16];
        System.arraycopy(builder.chars, 0, this.chars, 0, this.length);
    }

    public StringBuilder(CharSequence seq) {
        this(seq.toString());
    }

    public StringBuilder append(CharSequence csq) {
        if(csq == null) {
            this.appendNull();
        }
        else {
            this.append0(csq.toString());
        }

        return this;
    }

    public StringBuilder append(StringBuilder builder) {
        if(builder == null) {
            this.appendNull();
        }
        else {
            this.append0(builder.chars, 0, builder.length);
        }

        return this;
    }

    public StringBuilder append(char c) {
        this.append0(c);
        return this;
    }

    public StringBuilder append(Object obj) {
        if(obj == null) {
            this.appendNull();
        }
        else {
            this.append0(obj.toString());
        }

        return this;
    }

    public StringBuilder append(String str) {
        this.append0(str);
        return this;
    }

    public StringBuilder append(char[] ch) {
        this.append0(ch);
        return this;
    }

    public StringBuilder append(float f) {
        this.append0(Float.toString(f));
        return this;
    }

    public StringBuilder append(boolean b) {
        String v0;
        if(b) {
            v0 = "true";
        }
        else {
            v0 = "false";
        }

        this.append0(v0);
        return this;
    }

    public StringBuilder append(int value) {
        return this.append(value, 0);
    }

    public StringBuilder append(double d) {
        this.append0(Double.toString(d));
        return this;
    }

    public StringBuilder append(long value) {
        return this.append(value, 0);
    }

    public StringBuilder append(int value, int minLength) {
        return this.append(value, minLength, '0');
    }

    public StringBuilder append(int value, int minLength, char prefix) {
        int v10 = 10;
        int v9 = 100000000;
        int v8 = 10000000;
        int v7 = 1000000;
        int v6 = 100000;
        if(value == -2147483648) {
            this.append0("-2147483648");
        }
        else {
            if(value < 0) {
                this.append0('-');
                value = -value;
            }

            if(minLength > 1) {
                int v0;
                for(v0 = minLength - StringBuilder.numChars(value, v10); v0 > 0; --v0) {
                    this.append(prefix);
                }
            }

            if(value >= 10000) {
                if(value >= 1000000000) {
                    this.append0(StringBuilder.digits[((int)((((long)value)) % 10000000000L / 1000000000))]);
                }

                if(value >= v9) {
                    this.append0(StringBuilder.digits[value % 1000000000 / v9]);
                }

                if(value >= v8) {
                    this.append0(StringBuilder.digits[value % v9 / v8]);
                }

                if(value >= v7) {
                    this.append0(StringBuilder.digits[value % v8 / v7]);
                }

                if(value >= v6) {
                    this.append0(StringBuilder.digits[value % v7 / v6]);
                }

                this.append0(StringBuilder.digits[value % v6 / 10000]);
            }

            if(value >= 1000) {
                this.append0(StringBuilder.digits[value % 10000 / 1000]);
            }

            if(value >= 100) {
                this.append0(StringBuilder.digits[value % 1000 / 100]);
            }

            if(value >= v10) {
                this.append0(StringBuilder.digits[value % 100 / 10]);
            }

            this.append0(StringBuilder.digits[value % 10]);
        }

        return this;
    }

    public StringBuilder append(long value, int minLength) {
        return this.append(value, minLength, '0');
    }

    public StringBuilder append(long value, int minLength, char prefix) {
        if(value == -9223372036854775808L) {
            this.append0("-9223372036854775808");
        }
        else {
            if(value < 0) {
                this.append0('-');
                value = -value;
            }

            if(minLength > 1) {
                int v0;
                for(v0 = minLength - StringBuilder.numChars(value, 10); v0 > 0; --v0) {
                    this.append(prefix);
                }
            }

            if(value >= 10000) {
                if(value >= 1000000000000000000L) {
                    this.append0(StringBuilder.digits[((int)((((double)value)) % 10000000000000000000 / 1000000000000000000))]);
                }

                if(value >= 100000000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 1000000000000000000L / 100000000000000000L))]);
                }

                if(value >= 10000000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 100000000000000000L / 10000000000000000L))]);
                }

                if(value >= 1000000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 10000000000000000L / 1000000000000000L))]);
                }

                if(value >= 100000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 1000000000000000L / 100000000000000L))]);
                }

                if(value >= 10000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 100000000000000L / 10000000000000L))]);
                }

                if(value >= 1000000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 10000000000000L / 1000000000000L))]);
                }

                if(value >= 100000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 1000000000000L / 100000000000L))]);
                }

                if(value >= 10000000000L) {
                    this.append0(StringBuilder.digits[((int)(value % 100000000000L / 10000000000L))]);
                }

                if(value >= 1000000000) {
                    this.append0(StringBuilder.digits[((int)(value % 10000000000L / 1000000000))]);
                }

                if(value >= 100000000) {
                    this.append0(StringBuilder.digits[((int)(value % 1000000000 / 100000000))]);
                }

                if(value >= 10000000) {
                    this.append0(StringBuilder.digits[((int)(value % 100000000 / 10000000))]);
                }

                if(value >= 1000000) {
                    this.append0(StringBuilder.digits[((int)(value % 10000000 / 1000000))]);
                }

                if(value >= 100000) {
                    this.append0(StringBuilder.digits[((int)(value % 1000000 / 100000))]);
                }

                this.append0(StringBuilder.digits[((int)(value % 100000 / 10000))]);
            }

            if(value >= 1000) {
                this.append0(StringBuilder.digits[((int)(value % 10000 / 1000))]);
            }

            if(value >= 100) {
                this.append0(StringBuilder.digits[((int)(value % 1000 / 100))]);
            }

            if(value >= 10) {
                this.append0(StringBuilder.digits[((int)(value % 100 / 10))]);
            }

            this.append0(StringBuilder.digits[((int)(value % 10))]);
        }

        return this;
    }

    public StringBuilder append(StringBuilder builder, int start, int end) {
        if(builder == null) {
            this.appendNull();
        }
        else {
            this.append0(builder.chars, start, end);
        }

        return this;
    }

    public StringBuilder append(CharSequence csq, int start, int end) {
        this.append0(csq, start, end);
        return this;
    }

    public StringBuilder append(char[] str, int offset, int len) {
        this.append0(str, offset, len);
        return this;
    }

    public Appendable append(char x0) throws IOException {
        return this.append(x0);
    }

    public Appendable append(CharSequence x0) throws IOException {
        return this.append(x0);
    }

    public Appendable append(CharSequence x0, int x1, int x2) throws IOException {
        return this.append(x0, x1, x2);
    }

    final void append0(char ch) {
        if(this.length == this.chars.length) {
            this.enlargeBuffer(this.length + 1);
        }

        char[] v0 = this.chars;
        int v1 = this.length;
        this.length = v1 + 1;
        v0[v1] = ch;
    }

    final void append0(String string) {
        if(string == null) {
            this.appendNull();
        }
        else {
            int v0 = string.length();
            int v1 = this.length + v0;
            if(v1 > this.chars.length) {
                this.enlargeBuffer(v1);
            }

            string.getChars(0, v0, this.chars, this.length);
            this.length = v1;
        }
    }

    final void append0(char[] value, int offset, int length) {
        if(offset <= value.length && offset >= 0) {
            if(length >= 0 && value.length - offset >= length) {
                int v0 = this.length + length;
                if(v0 > this.chars.length) {
                    this.enlargeBuffer(v0);
                }

                System.arraycopy(value, offset, this.chars, this.length, length);
                this.length = v0;
                return;
            }

            throw new ArrayIndexOutOfBoundsException("Length out of bounds: " + length);
        }

        throw new ArrayIndexOutOfBoundsException("Offset out of bounds: " + offset);
    }

    final void append0(CharSequence s, int start, int end) {
        String v2_1;
        if(s == null) {
            v2_1 = "null";
        }

        if(start >= 0 && end >= 0 && start <= end && end <= ((CharSequence)v2_1).length()) {
            this.append0(((CharSequence)v2_1).subSequence(start, end).toString());
            return;
        }

        throw new IndexOutOfBoundsException();
    }

    final void append0(char[] value) {
        int v0 = this.length + value.length;
        if(v0 > this.chars.length) {
            this.enlargeBuffer(v0);
        }

        System.arraycopy(value, 0, this.chars, this.length, value.length);
        this.length = v0;
    }

    public StringBuilder appendCodePoint(int codePoint) {
        this.append0(Character.toChars(codePoint));
        return this;
    }

    final void appendNull() {
        char v4 = 'l';
        int v0 = this.length + 4;
        if(v0 > this.chars.length) {
            this.enlargeBuffer(v0);
        }

        char[] v1 = this.chars;
        int v2 = this.length;
        this.length = v2 + 1;
        v1[v2] = 'n';
        v1 = this.chars;
        v2 = this.length;
        this.length = v2 + 1;
        v1[v2] = 'u';
        v1 = this.chars;
        v2 = this.length;
        this.length = v2 + 1;
        v1[v2] = v4;
        v1 = this.chars;
        v2 = this.length;
        this.length = v2 + 1;
        v1[v2] = v4;
    }

    public int capacity() {
        return this.chars.length;
    }

    public char charAt(int index) {
        if(index >= 0 && index < this.length) {
            return this.chars[index];
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    public int codePointAt(int index) {
        if(index >= 0 && index < this.length) {
            return Character.codePointAt(this.chars, index, this.length);
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    public int codePointBefore(int index) {
        if(index >= 1 && index <= this.length) {
            return Character.codePointBefore(this.chars, index);
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        if(beginIndex >= 0 && endIndex <= this.length && beginIndex <= endIndex) {
            return Character.codePointCount(this.chars, beginIndex, endIndex - beginIndex);
        }

        throw new StringIndexOutOfBoundsException();
    }

    public StringBuilder delete(int start, int end) {
        this.delete0(start, end);
        return this;
    }

    final void delete0(int start, int end) {
        if(start >= 0) {
            if(end > this.length) {
                end = this.length;
            }

            if(end != start) {
                if(end > start) {
                    int v0 = this.length - end;
                    if(v0 >= 0) {
                        System.arraycopy(this.chars, end, this.chars, start, v0);
                    }

                    this.length -= end - start;
                }
                else {
                    goto label_18;
                }
            }

            return;
        }

    label_18:
        throw new StringIndexOutOfBoundsException();
    }

    public StringBuilder deleteCharAt(int index) {
        this.deleteCharAt0(index);
        return this;
    }

    final void deleteCharAt0(int location) {
        if(location >= 0 && location < this.length) {
            int v0 = this.length - location - 1;
            if(v0 > 0) {
                System.arraycopy(this.chars, location + 1, this.chars, location, v0);
            }

            --this.length;
            return;
        }

        throw new StringIndexOutOfBoundsException(location);
    }

    private void enlargeBuffer(int min) {
        int v1 = (this.chars.length >> 1) + this.chars.length + 2;
        if(min <= v1) {
            min = v1;
        }

        char[] v0 = new char[min];
        System.arraycopy(this.chars, 0, v0, 0, this.length);
        this.chars = v0;
    }

    public void ensureCapacity(int min) {
        if(min > this.chars.length) {
            int v0 = (this.chars.length << 1) + 2;
            if(v0 <= min) {
                v0 = min;
            }

            this.enlargeBuffer(v0);
        }
    }

    public boolean equals(Object obj) {
        boolean v5 = true;
        if(this != (((StringBuilder)obj))) {
            if(obj == null) {
                v5 = false;
            }
            else if(this.getClass() != obj.getClass()) {
                v5 = false;
            }
            else {
                Object v4 = obj;
                int v3 = this.length;
                if(v3 != ((StringBuilder)v4).length) {
                    v5 = false;
                }
                else {
                    char[] v0 = this.chars;
                    char[] v1 = ((StringBuilder)v4).chars;
                    if(v0 != v1) {
                        if(v0 != null && v1 != null) {
                            int v2 = 0;
                            while(true) {
                                if(v2 >= v3) {
                                    goto label_3;
                                }
                                else if(v0[v2] != v1[v2]) {
                                    v5 = false;
                                    goto label_3;
                                }
                                else {
                                    ++v2;
                                    continue;
                                }
                            }
                        }

                        v5 = false;
                    }
                }
            }
        }

    label_3:
        return v5;
    }

    public void getChars(int start, int end, char[] dest, int destStart) {
        if(start <= this.length && end <= this.length && start <= end) {
            System.arraycopy(this.chars, start, dest, destStart, end - start);
            return;
        }

        throw new StringIndexOutOfBoundsException();
    }

    final char[] getValue() {
        return this.chars;
    }

    public int hashCode() {
        return (this.length + 31) * 31 + Arrays.hashCode(this.chars);
    }

    public int indexOf(String string) {
        return this.indexOf(string, 0);
    }

    public int indexOf(String subString, int start) {
        int v4;
        int v3;
        int v2;
        int v6 = -1;
        if(start < 0) {
            start = 0;
        }

        int v5 = subString.length();
        if(v5 <= 0) {
            if(0 < this.length || 0 == 0) {
                v6 = 0;
            }
            else {
                v6 = this.length;
            }

            v2 = v6;
        }
        else if(v5 > this.length) {
            v2 = v6;
        }
        else {
            int v0 = subString.charAt(0);
            while(true) {
                v2 = start;
                int v1 = 0;
                while(v2 < this.length) {
                    if(this.chars[v2] == v0) {
                        v1 = 1;
                    }
                    else {
                        ++v2;
                        continue;
                    }

                    break;
                }

                if(v1 != 0 && v5 + v2 <= this.length) {
                    v3 = v2;
                    v4 = 0;
                    goto label_30;
                }

                v2 = v6;
                break;
                do {
                label_30:
                    ++v4;
                    if(v4 >= v5) {
                        break;
                    }

                    ++v3;
                }
                while(this.chars[v3] == subString.charAt(v4));

                if(v4 == v5) {
                    break;
                }

                start = v2 + 1;
            }
        }

        return v2;
    }

    public StringBuilder insert(int offset, char c) {
        this.insert0(offset, c);
        return this;
    }

    public StringBuilder insert(int offset, double d) {
        this.insert0(offset, Double.toString(d));
        return this;
    }

    public StringBuilder insert(int offset, float f) {
        this.insert0(offset, Float.toString(f));
        return this;
    }

    public StringBuilder insert(int offset, int i) {
        this.insert0(offset, Integer.toString(i));
        return this;
    }

    public StringBuilder insert(int offset, long l) {
        this.insert0(offset, Long.toString(l));
        return this;
    }

    public StringBuilder insert(int offset, CharSequence s) {
        String v0;
        if(s == null) {
            v0 = "null";
        }
        else {
            v0 = s.toString();
        }

        this.insert0(offset, v0);
        return this;
    }

    public StringBuilder insert(int offset, CharSequence s, int start, int end) {
        this.insert0(offset, s, start, end);
        return this;
    }

    public StringBuilder insert(int offset, Object obj) {
        String v0;
        if(obj == null) {
            v0 = "null";
        }
        else {
            v0 = obj.toString();
        }

        this.insert0(offset, v0);
        return this;
    }

    public StringBuilder insert(int offset, String str) {
        this.insert0(offset, str);
        return this;
    }

    public StringBuilder insert(int offset, boolean b) {
        String v0;
        if(b) {
            v0 = "true";
        }
        else {
            v0 = "false";
        }

        this.insert0(offset, v0);
        return this;
    }

    public StringBuilder insert(int offset, char[] ch) {
        this.insert0(offset, ch);
        return this;
    }

    public StringBuilder insert(int offset, char[] str, int strOffset, int strLen) {
        this.insert0(offset, str, strOffset, strLen);
        return this;
    }

    final void insert0(int index, char ch) {
        if(index >= 0 && index <= this.length) {
            this.move(1, index);
            this.chars[index] = ch;
            ++this.length;
            return;
        }

        throw new ArrayIndexOutOfBoundsException(index);
    }

    final void insert0(int index, String string) {
        if(index >= 0 && index <= this.length) {
            if(string == null) {
                string = "null";
            }

            int v0 = string.length();
            if(v0 != 0) {
                this.move(v0, index);
                string.getChars(0, v0, this.chars, index);
                this.length += v0;
            }

            return;
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    final void insert0(int index, CharSequence s, int start, int end) {
        String v3_1;
        if(s == null) {
            v3_1 = "null";
        }

        if(index >= 0 && index <= this.length && start >= 0 && end >= 0 && start <= end && end <= ((CharSequence)v3_1).length()) {
            this.insert0(index, ((CharSequence)v3_1).subSequence(start, end).toString());
            return;
        }

        throw new IndexOutOfBoundsException();
    }

    final void insert0(int index, char[] value) {
        if(index >= 0 && index <= this.length) {
            if(value.length != 0) {
                this.move(value.length, index);
                System.arraycopy(value, 0, value, index, value.length);
                this.length += value.length;
            }

            return;
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    final void insert0(int index, char[] value, int start, int length) {
        if(index >= 0 && index <= length) {
            if(start >= 0 && length >= 0 && length <= value.length - start) {
                if(length != 0) {
                    this.move(length, index);
                    System.arraycopy(value, start, this.chars, index, length);
                    this.length += length;
                }

                return;
            }

            throw new StringIndexOutOfBoundsException("offset " + start + ", length " + length + ", char[].length " + value.length);
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    public int lastIndexOf(String string) {
        return this.lastIndexOf(string, this.length);
    }

    public int lastIndexOf(String subString, int start) {
        int v2;
        int v6 = -1;
        int v5 = subString.length();
        if(v5 > this.length || start < 0) {
            v2 = v6;
        }
        else if(v5 > 0) {
            if(start > this.length - v5) {
                start = this.length - v5;
            }

            int v0 = subString.charAt(0);
            while(true) {
                v2 = start;
                int v1 = 0;
                while(v2 >= 0) {
                    if(this.chars[v2] == v0) {
                        v1 = 1;
                    }
                    else {
                        --v2;
                        continue;
                    }

                    break;
                }

                if(v1 != 0) {
                    goto label_25;
                }

                v2 = v6;
                break;
            label_25:
                int v3 = v2;
                int v4 = 0;
                do {
                    ++v4;
                    if(v4 >= v5) {
                        break;
                    }

                    ++v3;
                }
                while(this.chars[v3] == subString.charAt(v4));

                if(v4 == v5) {
                    break;
                }

                start = v2 - 1;
            }
        }
        else {
            if(start < this.length) {
                v6 = start;
            }
            else {
                v6 = this.length;
            }

            v2 = v6;
        }

        return v2;
    }

    public int length() {
        return this.length;
    }

    private void move(int size, int index) {
        int v3;
        if(this.chars.length - this.length >= size) {
            System.arraycopy(this.chars, index, this.chars, index + size, this.length - index);
        }
        else {
            int v0 = this.length + size;
            int v1 = (this.chars.length << 1) + 2;
            if(v0 > v1) {
                v3 = v0;
            }
            else {
                v3 = v1;
            }

            char[] v2 = new char[v3];
            System.arraycopy(this.chars, 0, v2, 0, index);
            System.arraycopy(this.chars, index, v2, index + size, this.length - index);
            this.chars = v2;
        }
    }

    public static int numChars(int value, int radix) {
        int v0;
        if(value < 0) {
            v0 = 2;
        }
        else {
            v0 = 1;
        }

        while(true) {
            value /= radix;
            if(value == 0) {
                break;
            }

            ++v0;
        }

        return v0;
    }

    public static int numChars(long value, int radix) {
        int v0;
        long v4 = 0;
        if(value < v4) {
            v0 = 2;
        }
        else {
            v0 = 1;
        }

        while(true) {
            value /= ((long)radix);
            if(value == v4) {
                break;
            }

            ++v0;
        }

        return v0;
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return Character.offsetByCodePoints(this.chars, 0, this.length, index, codePointOffset);
    }

    public StringBuilder replace(int start, int end, String str) {
        this.replace0(start, end, str);
        return this;
    }

    final void replace0(int start, int end, String string) {
        if(start >= 0) {
            if(end > this.length) {
                end = this.length;
            }

            if(end > start) {
                int v1 = string.length();
                int v0 = end - start - v1;
                if(v0 > 0) {
                    System.arraycopy(this.chars, end, this.chars, start + v1, this.length - end);
                }
                else if(v0 < 0) {
                    this.move(-v0, end);
                }

                string.getChars(0, v1, this.chars, start);
                this.length -= v0;
            }
            else {
                if(start != end) {
                    goto label_32;
                }

                if(string == null) {
                    throw new NullPointerException();
                }

                this.insert0(start, string);
            }

            return;
        }

    label_32:
        throw new StringIndexOutOfBoundsException();
    }

    public StringBuilder reverse() {
        this.reverse0();
        return this;
    }

    final void reverse0() {
        int v9;
        int v10;
        if(this.length >= 2) {
            int v2 = this.length - 1;
            char v5 = this.chars[0];
            char v4 = this.chars[v2];
            int v1 = 1;
            int v0 = 1;
            int v7 = 0;
            int v8 = this.length / 2;
            while(v7 < v8) {
                char v6 = this.chars[v7 + 1];
                char v3 = this.chars[v2 - 1];
                if(v1 == 0 || v6 < 56320 || v6 > 57343 || v5 < 55296 || v5 > 56319) {
                    v10 = 0;
                }
                else {
                    v10 = 1;
                }

                if(v10 != 0 && this.length < 3) {
                    return;
                }

                if(v0 == 0 || v3 < 55296 || v3 > 56319 || v4 < 56320 || v4 > 57343) {
                    v9 = 0;
                }
                else {
                    v9 = 1;
                }

                v0 = 1;
                v1 = 1;
                if(v10 == v9) {
                    if(v10 != 0) {
                        this.chars[v2] = v6;
                        this.chars[v2 - 1] = v5;
                        this.chars[v7] = v3;
                        this.chars[v7 + 1] = v4;
                        v5 = this.chars[v7 + 2];
                        v4 = this.chars[v2 - 2];
                        ++v7;
                        --v2;
                    }
                    else {
                        this.chars[v2] = v5;
                        this.chars[v7] = v4;
                        v5 = v6;
                        v4 = v3;
                    }
                }
                else if(v10 != 0) {
                    this.chars[v2] = v6;
                    this.chars[v7] = v4;
                    v4 = v3;
                    v1 = 0;
                }
                else {
                    this.chars[v2] = v5;
                    this.chars[v7] = v3;
                    v5 = v6;
                    v0 = 0;
                }

                ++v7;
                --v2;
            }

            if((this.length & 1) != 1) {
                return;
            }

            if(v1 != 0 && v0 != 0) {
                return;
            }

            char[] v11 = this.chars;
            if(v1 == 0) {
                v4 = v5;
            }

            v11[v2] = v4;
        }
    }

    public void setCharAt(int index, char ch) {
        if(index >= 0 && index < this.length) {
            this.chars[index] = ch;
            return;
        }

        throw new StringIndexOutOfBoundsException(index);
    }

    public void setLength(int newLength) {
        if(newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        }

        if(newLength > this.chars.length) {
            this.enlargeBuffer(newLength);
        }
        else if(this.length < newLength) {
            Arrays.fill(this.chars, this.length, newLength, '\u0000');
        }

        this.length = newLength;
    }

    public CharSequence subSequence(int start, int end) {
        return this.substring(start, end);
    }

    public String substring(int start, int end) {
        String v0;
        if(start >= 0 && start <= end && end <= this.length) {
            if(start == end) {
                v0 = "";
            }
            else {
                v0 = new String(this.chars, start, end - start);
            }

            return v0;
        }

        throw new StringIndexOutOfBoundsException();
    }

    public String substring(int start) {
        String v0;
        if(start >= 0 && start <= this.length) {
            if(start == this.length) {
                v0 = "";
            }
            else {
                v0 = new String(this.chars, start, this.length - start);
            }

            return v0;
        }

        throw new StringIndexOutOfBoundsException(start);
    }

    public String toString() {
        String v0;
        if(this.length == 0) {
            v0 = "";
        }
        else {
            v0 = new String(this.chars, 0, this.length);
        }

        return v0;
    }

    public void trimToSize() {
        if(this.length < this.chars.length) {
            char[] v0 = new char[this.length];
            System.arraycopy(this.chars, 0, v0, 0, this.length);
            this.chars = v0;
        }
    }
}

