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

public class XmlWriter extends Writer {
    private String currentElement;
    public int indent;
    private boolean indentNextClose;
    private final Array stack;
    private final Writer writer;

    public XmlWriter(Writer writer) {
        super();
        this.stack = new Array();
        this.writer = writer;
    }

    public XmlWriter attribute(String name, Object value) throws IOException {
        String v0;
        if(this.currentElement == null) {
            throw new IllegalStateException();
        }

        this.writer.write(32);
        this.writer.write(name);
        this.writer.write("=\"");
        Writer v1 = this.writer;
        if(value == null) {
            v0 = "null";
        }
        else {
            v0 = value.toString();
        }

        v1.write(v0);
        this.writer.write(34);
        return this;
    }

    public void close() throws IOException {
        while(this.stack.size != 0) {
            this.pop();
        }

        this.writer.close();
    }

    public XmlWriter element(String name) throws IOException {
        if(this.startElementContent()) {
            this.writer.write(10);
        }

        this.indent();
        this.writer.write(60);
        this.writer.write(name);
        this.currentElement = name;
        return this;
    }

    public XmlWriter element(String name, Object text) throws IOException {
        return this.element(name).text(text).pop();
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    private void indent() throws IOException {
        int v0 = this.indent;
        if(this.currentElement != null) {
            ++v0;
        }

        int v1;
        for(v1 = 0; v1 < v0; ++v1) {
            this.writer.write(9);
        }
    }

    public XmlWriter pop() throws IOException {
        if(this.currentElement != null) {
            this.writer.write("/>\n");
            this.currentElement = null;
        }
        else {
            this.indent = Math.max(this.indent - 1, 0);
            if(this.indentNextClose) {
                this.indent();
            }

            this.writer.write("</");
            this.writer.write(this.stack.pop());
            this.writer.write(">\n");
        }

        this.indentNextClose = true;
        return this;
    }

    private boolean startElementContent() throws IOException {
        boolean v0;
        if(this.currentElement == null) {
            v0 = false;
        }
        else {
            ++this.indent;
            this.stack.add(this.currentElement);
            this.currentElement = null;
            this.writer.write(">");
            v0 = true;
        }

        return v0;
    }

    public XmlWriter text(Object text) throws IOException {
        boolean v1;
        String v0;
        int v3 = 10;
        this.startElementContent();
        if(text == null) {
            v0 = "null";
        }
        else {
            v0 = text.toString();
        }

        if(v0.length() > 64) {
            v1 = true;
        }
        else {
            v1 = false;
        }

        this.indentNextClose = v1;
        if(this.indentNextClose) {
            this.writer.write(v3);
            this.indent();
        }

        this.writer.write(v0);
        if(this.indentNextClose) {
            this.writer.write(v3);
        }

        return this;
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        this.startElementContent();
        this.writer.write(cbuf, off, len);
    }
}

