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
import java.util.Iterator;

public class XmlReader {
    public class Element {
        private ObjectMap attributes;
        private Array children;
        private final String name;
        private Element parent;
        private String text;

        public Element(String name, Element parent) {
            super();
            this.name = name;
            this.parent = parent;
        }

        public void addChild(Element element) {
            if(this.children == null) {
                this.children = new Array(8);
            }

            this.children.add(element);
        }

        public String get(String name, String defaultValue) {
            String v1_1;
            Object v1;
            if(this.attributes != null) {
                v1 = this.attributes.get(name);
                if(v1 == null) {
                    goto label_6;
                }
            }
            else {
            label_6:
                Element v0 = this.getChildByName(name);
                if(v0 == null) {
                    v1_1 = defaultValue;
                }
                else {
                    v1_1 = v0.getText();
                    if(v1_1 == null) {
                        v1_1 = defaultValue;
                    }
                }
            }

            return ((String)v1);
        }

        public String get(String name) {
            String v0 = this.get(name, null);
            if(v0 == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn\'t have attribute or child: " + name);
            }

            return v0;
        }

        public String getAttribute(String name) {
            if(this.attributes == null) {
                throw new GdxRuntimeException("Element " + name + " doesn\'t have attribute: " + name);
            }

            Object v0 = this.attributes.get(name);
            if(v0 == null) {
                throw new GdxRuntimeException("Element " + name + " doesn\'t have attribute: " + name);
            }

            return ((String)v0);
        }

        public String getAttribute(String name, String defaultValue) {
            Object v4_1;
            if(this.attributes != null) {
                Object v0 = this.attributes.get(name);
                if(v0 != null) {
                    v4_1 = v0;
                }
            }

            return ((String)v4_1);
        }

        public ObjectMap getAttributes() {
            return this.attributes;
        }

        public boolean getBoolean(String name) {
            String v0 = this.get(name, null);
            if(v0 == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn\'t have attribute or child: " + name);
            }

            return Boolean.parseBoolean(v0);
        }

        public boolean getBoolean(String name, boolean defaultValue) {
            String v0 = this.get(name, null);
            if(v0 != null) {
                defaultValue = Boolean.parseBoolean(v0);
            }

            return defaultValue;
        }

        public boolean getBooleanAttribute(String name) {
            return Boolean.parseBoolean(this.getAttribute(name));
        }

        public boolean getBooleanAttribute(String name, boolean defaultValue) {
            String v0 = this.getAttribute(name, null);
            if(v0 != null) {
                defaultValue = Boolean.parseBoolean(v0);
            }

            return defaultValue;
        }

        public Element getChild(int index) {
            if(this.children == null) {
                throw new GdxRuntimeException("Element has no children: " + this.name);
            }

            return this.children.get(index);
        }

        public Element getChildByName(String name) {
            Object v0_1;
            Element v0;
            Element v2 = null;
            if(this.children == null) {
                v0 = v2;
            }
            else {
                int v1 = 0;
                while(true) {
                    if(v1 < this.children.size) {
                        v0_1 = this.children.get(v1);
                        if(!((Element)v0_1).name.equals(name)) {
                            ++v1;
                            continue;
                        }
                    }
                    else {
                        break;
                    }

                    goto label_4;
                }

                v0 = v2;
            }

        label_4:
            return ((Element)v0_1);
        }

        public Element getChildByNameRecursive(String name) {
            Element v0;
            Element v3 = null;
            if(this.children == null) {
                v0 = v3;
            }
            else {
                int v2 = 0;
                while(true) {
                    if(v2 < this.children.size) {
                        Object v0_1 = this.children.get(v2);
                        if(!((Element)v0_1).name.equals(name)) {
                            Element v1 = ((Element)v0_1).getChildByNameRecursive(name);
                            if(v1 != null) {
                                v0 = v1;
                            }
                            else {
                                ++v2;
                                continue;
                            }
                        }
                    }
                    else {
                        break;
                    }

                    goto label_4;
                }

                v0 = v3;
            }

        label_4:
            return v0;
        }

        public int getChildCount() {
            int v0;
            if(this.children == null) {
                v0 = 0;
            }
            else {
                v0 = this.children.size;
            }

            return v0;
        }

        public Array getChildrenByName(String name) {
            Array v2 = new Array();
            if(this.children != null) {
                int v1;
                for(v1 = 0; v1 < this.children.size; ++v1) {
                    Object v0 = this.children.get(v1);
                    if(((Element)v0).name.equals(name)) {
                        v2.add(v0);
                    }
                }
            }

            return v2;
        }

        private void getChildrenByNameRecursively(String name, Array arg5) {
            if(this.children != null) {
                int v1;
                for(v1 = 0; v1 < this.children.size; ++v1) {
                    Object v0 = this.children.get(v1);
                    if(((Element)v0).name.equals(name)) {
                        arg5.add(v0);
                    }

                    ((Element)v0).getChildrenByNameRecursively(name, arg5);
                }
            }
        }

        public Array getChildrenByNameRecursively(String name) {
            Array v0 = new Array();
            this.getChildrenByNameRecursively(name, v0);
            return v0;
        }

        public float getFloat(String name) {
            String v0 = this.get(name, null);
            if(v0 == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn\'t have attribute or child: " + name);
            }

            return Float.parseFloat(v0);
        }

        public float getFloat(String name, float defaultValue) {
            String v0 = this.get(name, null);
            if(v0 != null) {
                defaultValue = Float.parseFloat(v0);
            }

            return defaultValue;
        }

        public float getFloatAttribute(String name) {
            return Float.parseFloat(this.getAttribute(name));
        }

        public float getFloatAttribute(String name, float defaultValue) {
            String v0 = this.getAttribute(name, null);
            if(v0 != null) {
                defaultValue = Float.parseFloat(v0);
            }

            return defaultValue;
        }

        public int getInt(String name) {
            String v0 = this.get(name, null);
            if(v0 == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn\'t have attribute or child: " + name);
            }

            return Integer.parseInt(v0);
        }

        public int getInt(String name, int defaultValue) {
            String v0 = this.get(name, null);
            if(v0 != null) {
                defaultValue = Integer.parseInt(v0);
            }

            return defaultValue;
        }

        public int getIntAttribute(String name, int defaultValue) {
            String v0 = this.getAttribute(name, null);
            if(v0 != null) {
                defaultValue = Integer.parseInt(v0);
            }

            return defaultValue;
        }

        public int getIntAttribute(String name) {
            return Integer.parseInt(this.getAttribute(name));
        }

        public String getName() {
            return this.name;
        }

        public Element getParent() {
            return this.parent;
        }

        public String getText() {
            return this.text;
        }

        public void remove() {
            this.parent.removeChild(this);
        }

        public void removeChild(Element child) {
            if(this.children != null) {
                this.children.removeValue(child, true);
            }
        }

        public void removeChild(int index) {
            if(this.children != null) {
                this.children.removeIndex(index);
            }
        }

        public void setAttribute(String name, String value) {
            if(this.attributes == null) {
                this.attributes = new ObjectMap(8);
            }

            this.attributes.put(name, value);
        }

        public void setText(String text) {
            this.text = text;
        }

        public String toString() {
            return this.toString("");
        }

        public String toString(String indent) {
            Iterator v4;
            char v7 = '\n';
            StringBuilder v0 = new StringBuilder(128);
            v0.append(indent);
            v0.append('<');
            v0.append(this.name);
            if(this.attributes != null) {
                v4 = this.attributes.entries().iterator();
                while(v4.hasNext()) {
                    Object v3 = v4.next();
                    v0.append(' ');
                    v0.append(((Entry)v3).key);
                    v0.append("=\"");
                    v0.append(((Entry)v3).value);
                    v0.append('\"');
                }
            }

            if(this.children == null) {
                if(this.text != null && this.text.length() != 0) {
                    goto label_38;
                }

                v0.append("/>");
            }
            else {
            label_38:
                v0.append(">\n");
                String v2 = indent + '\t';
                if(this.text != null && this.text.length() > 0) {
                    v0.append(v2);
                    v0.append(this.text);
                    v0.append(v7);
                }

                if(this.children != null) {
                    v4 = this.children.iterator();
                    while(v4.hasNext()) {
                        v0.append(v4.next().toString(v2));
                        v0.append(v7);
                    }
                }

                v0.append(indent);
                v0.append("</");
                v0.append(this.name);
                v0.append('>');
            }

            return v0.toString();
        }
    }

    private static final byte[] _xml_actions = null;
    private static final short[] _xml_index_offsets = null;
    private static final byte[] _xml_indicies = null;
    private static final byte[] _xml_key_offsets = null;
    private static final byte[] _xml_range_lengths = null;
    private static final byte[] _xml_single_lengths = null;
    private static final byte[] _xml_trans_actions = null;
    private static final char[] _xml_trans_keys = null;
    private static final byte[] _xml_trans_targs = null;
    private Element current;
    private final Array elements;
    private Element root;
    private final StringBuilder textBuffer;
    static final int xml_en_elementBody = 15;
    static final int xml_en_main = 1;
    static final int xml_error = 0;
    static final int xml_first_final = 34;
    static final int xml_start = 1;

    static  {
        XmlReader._xml_actions = XmlReader.init__xml_actions_0();
        XmlReader._xml_key_offsets = XmlReader.init__xml_key_offsets_0();
        XmlReader._xml_trans_keys = XmlReader.init__xml_trans_keys_0();
        XmlReader._xml_single_lengths = XmlReader.init__xml_single_lengths_0();
        XmlReader._xml_range_lengths = XmlReader.init__xml_range_lengths_0();
        XmlReader._xml_index_offsets = XmlReader.init__xml_index_offsets_0();
        XmlReader._xml_indicies = XmlReader.init__xml_indicies_0();
        XmlReader._xml_trans_targs = XmlReader.init__xml_trans_targs_0();
        XmlReader._xml_trans_actions = XmlReader.init__xml_trans_actions_0();
    }

    public XmlReader() {
        super();
        this.elements = new Array(8);
        this.textBuffer = new StringBuilder(64);
    }

    protected void attribute(String name, String value) {
        this.current.setAttribute(name, value);
    }

    protected void close() {
        Object v0;
        this.root = this.elements.pop();
        if(this.elements.size > 0) {
            v0 = this.elements.peek();
        }
        else {
            Element v0_1 = null;
        }

        this.current = ((Element)v0);
    }

    protected String entity(String name) {
        String v0;
        if(name.equals("lt")) {
            v0 = "<";
        }
        else if(name.equals("gt")) {
            v0 = ">";
        }
        else if(name.equals("amp")) {
            v0 = "&";
        }
        else if(name.equals("apos")) {
            v0 = "\'";
        }
        else if(name.equals("quot")) {
            v0 = "\"";
        }
        else if(name.startsWith("#x")) {
            v0 = Character.toString(((char)Integer.parseInt(name.substring(2), 16)));
        }
        else {
            v0 = null;
        }

        return v0;
    }

    private static byte[] init__xml_actions_0() {
        return new byte[]{0, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 2, 0, 6, 2, 1, 4, 2, 2, 4};
    }

    private static short[] init__xml_index_offsets_0() {
        return new short[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 37, 39, 44, 48, 52, 54, 56, 60, 62, 67, 72, 78, 84, 88, 93, 95, 97, 102, 106, 110, 112, 116, 118, 120, 122, 124, 127};
    }

    private static byte[] init__xml_indicies_0() {
        return new byte[]{0, 2, 0, 1, 2, 1, 1, 2, 3, 5, 6, 7, 5, 4, 9, 10, 1, 11, 9, 8, 13, 1, 14, 1, 13, 12, 15, 16, 15, 1, 16, 17, 18, 16, 1, 20, 19, 22, 21, 9, 10, 11, 9, 1, 23, 24, 23, 1, 25, 11, 25, 1, 20, 26, 22, 27, 29, 30, 29, 28, 32, 31, 30, 34, 1, 30, 33, 36, 37, 38, 36, 35, 40, 41, 1, 42, 40, 39, 44, 1, 45, 1, 44, 43, 46, 47, 46, 1, 47, 48, 49, 47, 1, 51, 50, 53, 52, 40, 41, 42, 40, 1, 54, 55, 54, 1, 56, 42, 56, 1, 57, 1, 57, 34, 57, 1, 1, 58, 59, 58, 51, 60, 53, 61, 62, 62, 1, 1, 0};
    }

    private static byte[] init__xml_key_offsets_0() {
        return new byte[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 36, 37, 42, 46, 50, 51, 52, 56, 57, 62, 67, 73, 79, 83, 88, 89, 90, 95, 99, 103, 104, 108, 109, 110, 111, 112, 115};
    }

    private static byte[] init__xml_range_lengths_0() {
        return new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0};
    }

    private static byte[] init__xml_single_lengths_0() {
        return new byte[]{0, 2, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 1, 2, 1, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 2, 1, 1, 1, 1, 1, 0};
    }

    private static byte[] init__xml_trans_actions_0() {
        return new byte[]{0, 0, 0, 1, 0, 3, 3, 20, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 17, 0, 13, 5, 23, 0, 1, 0, 1, 0, 0, 0, 15, 1, 0, 0, 3, 3, 20, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 17, 0, 13, 5, 23, 0, 0, 0, 7, 1, 0, 0};
    }

    private static char[] init__xml_trans_keys_0() {
        return new char[]{' ', '<', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '=', '\t', '\r', ' ', '\"', '\'', '\t', '\r', '\"', '\"', ' ', '/', '>', '\t', '\r', ' ', '>', '\t', '\r', ' ', '>', '\t', '\r', '\'', '\'', ' ', '<', '\t', '\r', '<', ' ', '/', '>', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '=', '\t', '\r', ' ', '\"', '\'', '\t', '\r', '\"', '\"', ' ', '/', '>', '\t', '\r', ' ', '>', '\t', '\r', ' ', '>', '\t', '\r', '<', ' ', '/', '\t', '\r', '>', '>', '\'', '\'', ' ', '\t', '\r', '\u0000'};
    }

    private static byte[] init__xml_trans_targs_0() {
        return new byte[]{1, 0, 2, 3, 3, 4, 11, 34, 5, 4, 11, 34, 5, 6, 7, 6, 7, 8, 13, 9, 10, 9, 10, 12, 34, 12, 14, 14, 16, 15, 17, 16, 17, 18, 30, 18, 19, 26, 28, 20, 19, 26, 28, 20, 21, 22, 21, 22, 23, 32, 24, 25, 24, 25, 27, 28, 27, 29, 31, 35, 33, 33, 34};
    }

    protected void open(String name) {
        Element v0 = new Element(name, this.current);
        Element v1 = this.current;
        if(v1 != null) {
            v1.addChild(v0);
        }

        this.elements.add(v0);
        this.current = v0;
    }

    public Element parse(FileHandle file) throws IOException {  // has try-catch handlers
        try {
            return this.parse(file.reader("UTF-8"));
        }
        catch(Exception v0) {
            throw new SerializationException("Error parsing file: " + file, ((Throwable)v0));
        }
    }

    public Element parse(Reader reader) throws IOException {  // has try-catch handlers
        Element v5_2;
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

    public Element parse(InputStream input) throws IOException {  // has try-catch handlers
        Element v1_1;
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

    public Element parse(char[] data, int offset, int length) {
        int v18;
        int v10;
        int v14;
        int v9;
        int v28 = offset;
        int v29 = length;
        int v31 = 0;
        String v15 = null;
        int v24 = 0;
        int v17 = 1;
        int v6;
        for(v6 = 0; true; v6 = 5) {
        label_8:
            switch(v6) {
                case 0: {
                    goto label_25;
                }
                case 1: {
                    goto label_33;
                }
                case 2: {
                    goto label_406;
                }
            }

            goto label_9;
        label_25:
            if(v28 != v29) {
                goto label_30;
            }

            v6 = 4;
            continue;
        label_30:
            if(v17 != 0) {
                break;
            }
        }

    label_33:
        int v7 = XmlReader._xml_key_offsets[v17];
        int v13 = XmlReader._xml_index_offsets[v17];
        int v8 = XmlReader._xml_single_lengths[v17];
        if(v8 > 0) {
            v9 = v7;
            v14 = v7 + v8 - 1;
            while(true) {
                if(v14 >= v9) {
                    v10 = v9 + (v14 - v9 >> 1);
                    if(data[v28] < XmlReader._xml_trans_keys[v10]) {
                        v14 = v10 - 1;
                        continue;
                    }
                    else if(data[v28] > XmlReader._xml_trans_keys[v10]) {
                        v9 = v10 + 1;
                        continue;
                    }
                    else {
                        break;
                    }
                }
                else {
                    goto label_44;
                }

                goto label_55;
            }

            v13 += v10 - v7;
            goto label_55;
        label_44:
            v7 += v8;
            v13 += v8;
            goto label_46;
        }
        else {
        label_46:
            v8 = XmlReader._xml_range_lengths[v17];
            if(v8 > 0) {
                v9 = v7;
                v14 = (v8 << 1) + v7 - 2;
                while(true) {
                    if(v14 >= v9) {
                        v10 = v9 + (v14 - v9 >> 1 & -2);
                        if(data[v28] < XmlReader._xml_trans_keys[v10]) {
                            v14 = v10 - 2;
                            continue;
                        }
                        else if(data[v28] > XmlReader._xml_trans_keys[v10 + 1]) {
                            v9 = v10 + 2;
                            continue;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        goto label_54;
                    }

                    goto label_55;
                }

                v13 += v10 - v7 >> 1;
                goto label_55;
            label_54:
                v13 += v8;
            }
        }

    label_55:
        v13 = XmlReader._xml_indicies[v13];
        v17 = XmlReader._xml_trans_targs[v13];
        if(XmlReader._xml_trans_actions[v13] == 0) {
            goto label_406;
        }

        int v5 = XmlReader._xml_trans_actions[v13] + 1;
        int v12 = XmlReader._xml_actions[XmlReader._xml_trans_actions[v13]];
        while(true) {
            int v11 = v12 - 1;
            if(v12 <= 0) {
                break;
            }

            int v4 = v5 + 1;
            int v33 = XmlReader._xml_actions[v5];
            switch(v33) {
                case 0: {
                    goto label_124;
                }
                case 1: {
                    goto label_126;
                }
                case 2: {
                    goto label_269;
                }
                case 3: {
                    goto label_274;
                }
                case 4: {
                    goto label_278;
                }
                case 5: {
                    goto label_282;
                }
                case 6: {
                    goto label_288;
                }
                case 7: {
                    goto label_299;
                }
            }

            goto label_74;
        label_274:
            this.close();
            v17 = 15;
            v6 = 2;
            goto label_8;
        label_278:
            if(v24 == 0) {
                goto label_74;
            }

            v17 = 15;
            v6 = 2;
            goto label_8;
        label_282:
            v15 = new String(data, v31, v28 - v31);
            goto label_74;
        label_299:
            int v21 = v28;
        label_300:
            while(v21 != v31) {
                v33 = data[v21 - 1];
                switch(v33) {
                    case 9: 
                    case 10: 
                    case 13: 
                    case 32: {
                        --v21;
                        goto label_300;
                    }
                }

                break;
            }

            int v22 = 0;
            int v19;
            for(v19 = v31; true; v19 = v18) {
            label_309:
                if(v19 == v21) {
                    goto label_369;
                }

                v18 = v19 + 1;
                v33 = data[v19];
                if(v33 == 38) {
                    break;
                }
            }

            int v23 = v18;
            v19 = v18;
            goto label_324;
        label_369:
            if(v22 == 0) {
                goto label_395;
            }

            if(v31 < v21) {
                this.textBuffer.append(data, v31, v21 - v31);
            }

            this.text(this.textBuffer.toString());
            this.textBuffer.setLength(0);
            goto label_74;
        label_395:
            this.text(new String(data, v31, v21 - v31));
            goto label_74;
            while(true) {
            label_324:
                if(v19 != v21) {
                    v18 = v19 + 1;
                    v33 = data[v19];
                    if(v33 != 59) {
                        v19 = v18;
                        continue;
                    }
                    else {
                        break;
                    }
                }
                else {
                    goto label_467;
                }

                goto label_365;
            }

            this.textBuffer.append(data, v31, v23 - v31 - 1);
            String v27 = new String(data, v23, v18 - v23 - 1);
            String v32 = this.entity(v27);
            StringBuilder v33_1 = this.textBuffer;
            if(v32 == null) {
                v32 = v27;
            }

            v33_1.append(v32);
            v31 = v18;
            v22 = 1;
            goto label_365;
        label_467:
            v18 = v19;
        label_365:
            v19 = v18;
            goto label_309;
        label_124:
            v31 = v28;
            goto label_74;
        label_269:
            v24 = 0;
            this.close();
            v17 = 15;
            v6 = 2;
            goto label_8;
        label_126:
            int v16 = data[v31];
            if(v16 != 63) {
                Object v33_2 = null;
                if(v16 != (((int)v33_2))) {
                    v24 = 1;
                    this.open(new String(data, v31, v28 - v31));
                    goto label_74;
                }
            }

            if(data[v31 + 1] != 91 || data[v31 + 2] != 67 || data[v31 + 3] != 68 || data[v31 + 4] != 65 || data[v31 + 5] != 84 || data[v31 + 6] != 65 || data[v31 + 7] != 91) {
                if(v16 == 33 && data[v31 + 1] == 45 && data[v31 + 2] == 45) {
                    for(v28 = v31 + 3; true; ++v28) {
                        if(data[v28] == 45 && data[v28 + 1] == 45 && data[v28 + 2] == 62) {
                            v28 += 2;
                            goto label_209;
                        }
                    }
                }

                while(data[v28] != 62) {
                    ++v28;
                }
            }
            else {
                v31 += 8;
                for(v28 = v31 + 2; true; ++v28) {
                    if(data[v28 - 2] == 93 && data[v28 - 1] == 93) {
                        v33 = data[v28];
                        if(v33 == 62) {
                            this.text(new String(data, v31, v28 - v31 - 2));
                            break;
                        }
                    }
                }
            }

        label_209:
            v17 = 15;
            v6 = 2;
            goto label_8;
        label_288:
            this.attribute(v15, new String(data, v31, v28 - v31));
        label_74:
            v12 = v11;
            v5 = v4;
        }

    label_406:
        if(v17 != 0) {
            goto label_409;
        }

        v6 = 5;
        goto label_8;
    label_409:
        ++v28;
        if(v28 == v29) {
            goto label_9;
        }

        v6 = 1;
        goto label_8;
    label_9:
        if(v28 < v29) {
            int v26 = 1;
            int v25;
            for(v25 = 0; v25 < v28; ++v25) {
                if(data[v25] == 10) {
                    ++v26;
                }
            }

            throw new SerializationException("Error parsing XML on line " + v26 + " near: " + new String(data, v28, Math.min(32, v29 - v28)));
        }

        if(this.elements.size != 0) {
            Object v20 = this.elements.peek();
            this.elements.clear();
            throw new SerializationException("Error parsing XML, unclosed element: " + ((Element)v20).getName());
        }

        Element v30 = this.root;
        this.root = null;
        return v30;
    }

    public Element parse(String xml) {
        char[] v0 = xml.toCharArray();
        return this.parse(v0, 0, v0.length);
    }

    protected void text(String text) {
        String v0 = this.current.getText();
        Element v1 = this.current;
        if(v0 != null) {
            text = v0 + text;
        }

        v1.setText(text);
    }
}

