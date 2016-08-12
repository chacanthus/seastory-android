// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class TextArea extends TextField {
    public class TextAreaListener extends TextFieldClickListener {
        public TextAreaListener(TextArea arg1) {
            TextArea.this = arg1;
            super(((TextField)arg1));
        }

        protected void goEnd(boolean jump) {
            if((jump) || TextArea.this.cursorLine >= TextArea.this.getLines()) {
                TextArea.this.cursor = TextArea.this.text.length();
            }
            else if(TextArea.this.cursorLine * 2 + 1 < TextArea.this.linesBreak.size) {
                TextArea.this.cursor = TextArea.this.linesBreak.get(TextArea.this.cursorLine * 2 + 1);
            }
        }

        protected void goHome(boolean jump) {
            if(jump) {
                TextArea.this.cursor = 0;
            }
            else if(TextArea.this.cursorLine * 2 < TextArea.this.linesBreak.size) {
                TextArea.this.cursor = TextArea.this.linesBreak.get(TextArea.this.cursorLine * 2);
            }
        }

        public boolean keyDown(InputEvent event, int keycode) {
            int v1;
            boolean v3 = true;
            super.keyDown(event, keycode);
            Stage v2 = TextArea.this.getStage();
            if(v2 == null || v2.getKeyboardFocus() != TextArea.this) {
                v3 = false;
            }
            else {
                int v0 = 0;
                if((Gdx.input.isKeyPressed(59)) || (Gdx.input.isKeyPressed(60))) {
                    v1 = 1;
                }
                else {
                    v1 = 0;
                }

                if(keycode == 20) {
                    if(v1 == 0) {
                        TextArea.this.clearSelection();
                    }
                    else if(!TextArea.this.hasSelection) {
                        TextArea.this.selectionStart = TextArea.this.cursor;
                        TextArea.this.hasSelection = true;
                    }

                    TextArea.this.moveCursorLine(TextArea.this.cursorLine + 1);
                    v0 = 1;
                }
                else {
                    if(keycode != 19) {
                        goto label_69;
                    }

                    if(v1 == 0) {
                        TextArea.this.clearSelection();
                    }
                    else if(!TextArea.this.hasSelection) {
                        TextArea.this.selectionStart = TextArea.this.cursor;
                        TextArea.this.hasSelection = true;
                    }

                    TextArea.this.moveCursorLine(TextArea.this.cursorLine - 1);
                    v0 = 1;
                    goto label_37;
                label_69:
                    TextArea.this.moveOffset = -1f;
                }

            label_37:
                if(v0 != 0) {
                    this.scheduleKeyRepeatTask(keycode);
                }

                TextArea.this.showCursor();
            }

            return v3;
        }

        public boolean keyTyped(InputEvent event, char character) {
            boolean v0 = super.keyTyped(event, character);
            TextArea.this.showCursor();
            return v0;
        }

        protected void setCursorPosition(float x, float y) {
            TextArea.this.moveOffset = -1f;
            Drawable v0 = TextArea.this.style.background;
            BitmapFont v1 = TextArea.this.style.font;
            float v2 = TextArea.this.getHeight();
            if(v0 != null) {
                v2 -= v0.getTopHeight();
                x -= v0.getLeftWidth();
            }

            x = Math.max(0f, x);
            if(v0 != null) {
                y -= v0.getTopHeight();
            }

            TextArea.this.cursorLine = (((int)Math.floor(((double)((v2 - y) / v1.getLineHeight()))))) + TextArea.this.firstLineShowing;
            TextArea.this.cursorLine = Math.max(0, Math.min(TextArea.this.cursorLine, TextArea.this.getLines() - 1));
            super.setCursorPosition(x, y);
            TextArea.this.updateCurrentLine();
        }
    }

    int cursorLine;
    int firstLineShowing;
    private String lastText;
    IntArray linesBreak;
    private int linesShowing;
    float moveOffset;
    private float prefRows;

    public TextArea(String text, Skin skin) {
        super(text, skin);
    }

    public TextArea(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public TextArea(String text, TextFieldStyle style) {
        super(text, style);
    }

    private int calculateCurrentLineIndex(int cursor) {
        int v0 = 0;
        while(v0 < this.linesBreak.size) {
            if(cursor <= this.linesBreak.items[v0]) {
                break;
            }

            ++v0;
        }

        return v0;
    }

    protected void calculateOffsets() {
        float v8;
        super.calculateOffsets();
        if(!this.text.equals(this.lastText)) {
            this.lastText = this.text;
            BitmapFont v0 = this.style.font;
            float v9 = this.getWidth();
            if(this.style.background != null) {
                v8 = this.style.background.getLeftWidth() + this.style.background.getRightWidth();
            }
            else {
                v8 = 0f;
            }

            float v7 = v9 - v8;
            this.linesBreak.clear();
            int v6 = 0;
            int v3 = 0;
            Pool v5 = Pools.get(GlyphLayout.class);
            Object v4 = v5.obtain();
            int v1;
            for(v1 = 0; v1 < this.text.length(); ++v1) {
                int v2 = this.text.charAt(v1);
                if(v2 == 13 || v2 == 10) {
                    this.linesBreak.add(v6);
                    this.linesBreak.add(v1);
                    v6 = v1 + 1;
                }
                else {
                    if(!this.continueCursor(v1, 0)) {
                        v3 = v1;
                    }

                    ((GlyphLayout)v4).setText(v0, this.text.subSequence(v6, v1 + 1));
                    if(((GlyphLayout)v4).width <= v7) {
                        goto label_43;
                    }

                    if(v6 >= v3) {
                        v3 = v1 - 1;
                    }

                    this.linesBreak.add(v6);
                    this.linesBreak.add(v3 + 1);
                    v6 = v3 + 1;
                    v3 = v6;
                }

            label_43:
            }

            v5.free(v4);
            if(v6 < this.text.length()) {
                this.linesBreak.add(v6);
                this.linesBreak.add(this.text.length());
            }

            this.showCursor();
        }
    }

    protected boolean continueCursor(int index, int offset) {
        boolean v1;
        int v0 = this.calculateCurrentLineIndex(index + offset);
        if(super.continueCursor(index, offset)) {
            if(v0 >= 0 && v0 < this.linesBreak.size - 2 && this.linesBreak.items[v0 + 1] == index && this.linesBreak.items[v0 + 1] != this.linesBreak.items[v0 + 2]) {
                goto label_25;
            }

            v1 = true;
        }
        else {
        label_25:
            v1 = false;
        }

        return v1;
    }

    protected InputListener createInputListener() {
        return new TextAreaListener(this);
    }

    protected void drawCursor(Drawable cursorPatch, Batch batch, BitmapFont font, float x, float y) {
        float v6;
        if(this.cursor >= this.glyphPositions.size || this.cursorLine * 2 >= this.linesBreak.size) {
            v6 = 0f;
        }
        else {
            v6 = this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.linesBreak.items[this.cursorLine * 2]);
        }

        cursorPatch.draw(batch, x + v6, y - font.getDescent() / 2f - (((float)(this.cursorLine - this.firstLineShowing + 1))) * font.getLineHeight(), cursorPatch.getMinWidth(), font.getLineHeight());
    }

    protected void drawSelection(Drawable selection, Batch batch, BitmapFont font, float x, float y) {
        int v7 = this.firstLineShowing * 2;
        float v12 = 0f;
        int v11 = Math.min(this.cursor, this.selectionStart);
        int v10 = Math.max(this.cursor, this.selectionStart);
        while(v7 + 1 < this.linesBreak.size) {
            if(v7 >= (this.firstLineShowing + this.linesShowing) * 2) {
                return;
            }

            int v9 = this.linesBreak.get(v7);
            int v8 = this.linesBreak.get(v7 + 1);
            if((v11 >= v9 || v11 >= v8 || v10 >= v9 || v10 >= v8) && (v11 <= v9 || v11 <= v8 || v10 <= v9 || v10 <= v8)) {
                int v14 = Math.max(this.linesBreak.get(v7), v11);
                selection.draw(batch, x + (this.glyphPositions.get(v14) - this.glyphPositions.get(this.linesBreak.get(v7))), y - this.textHeight - font.getDescent() - v12, this.glyphPositions.get(Math.min(this.linesBreak.get(v7 + 1), v10)) - this.glyphPositions.get(v14), font.getLineHeight());
            }

            v12 += font.getLineHeight();
            v7 += 2;
        }
    }

    protected void drawText(Batch batch, BitmapFont font, float x, float y) {
        float v11 = 0f;
        int v10 = this.firstLineShowing * 2;
        while(v10 < (this.firstLineShowing + this.linesShowing) * 2) {
            if(v10 >= this.linesBreak.size) {
                return;
            }

            font.draw(batch, this.displayText, x, y + v11, this.linesBreak.items[v10], this.linesBreak.items[v10 + 1], 0f, 8, false);
            v11 -= font.getLineHeight();
            v10 += 2;
        }
    }

    public int getCursorLine() {
        return this.cursorLine;
    }

    public int getFirstLineShowing() {
        return this.firstLineShowing;
    }

    public int getLines() {
        int v0;
        int v1 = this.linesBreak.size / 2;
        if(this.newLineAtEnd()) {
            v0 = 1;
        }
        else {
            v0 = 0;
        }

        return v0 + v1;
    }

    public int getLinesShowing() {
        return this.linesShowing;
    }

    public float getPrefHeight() {
        float v0;
        if(this.prefRows <= 0f) {
            v0 = super.getPrefHeight();
        }
        else {
            v0 = this.textHeight * this.prefRows;
            if(this.style.background != null) {
                v0 = Math.max(this.style.background.getBottomHeight() + v0 + this.style.background.getTopHeight(), this.style.background.getMinHeight());
            }
        }

        return v0;
    }

    protected float getTextY(BitmapFont font, Drawable background) {
        float v0 = this.getHeight();
        if(background != null) {
            v0 = ((float)(((int)(v0 - background.getTopHeight()))));
        }

        return v0;
    }

    protected void initialize() {
        super.initialize();
        this.writeEnters = true;
        this.linesBreak = new IntArray();
        this.cursorLine = 0;
        this.firstLineShowing = 0;
        this.moveOffset = -1f;
        this.linesShowing = 0;
    }

    protected int letterUnderCursor(float x) {
        int v4 = 0;
        if(this.linesBreak.size > 0) {
            if(this.cursorLine * 2 >= this.linesBreak.size) {
                v4 = this.text.length();
            }
            else {
                int v3 = this.linesBreak.items[this.cursorLine * 2];
                int v0 = this.linesBreak.items[this.cursorLine * 2 + 1];
                int v2 = v3;
                int v1 = 0;
                while(v2 <= v0) {
                    if(v1 != 0) {
                        break;
                    }

                    if(this.glyphPositions.items[v2] - this.glyphPositions.items[v3] > x) {
                        v1 = 1;
                        continue;
                    }
                    else {
                        ++v2;
                        continue;
                    }
                }

                v4 = Math.max(0, v2 - 1);
            }
        }

        return v4;
    }

    protected void moveCursor(boolean forward, boolean jump) {
        int v0;
        if(forward) {
            v0 = 1;
        }
        else {
            v0 = -1;
        }

        int v1 = this.cursorLine * 2 + v0;
        if(v1 < 0 || v1 + 1 >= this.linesBreak.size || this.linesBreak.items[v1] != this.cursor || this.linesBreak.items[v1 + 1] != this.cursor) {
            super.moveCursor(forward, jump);
        }
        else {
            this.cursorLine += v0;
            if(jump) {
                super.moveCursor(forward, jump);
            }

            this.showCursor();
        }

        this.updateCurrentLine();
    }

    public void moveCursorLine(int line) {
        int v1_1;
        float v1 = 0f;
        float v3 = -1f;
        if(line < 0) {
            this.cursorLine = 0;
            this.cursor = 0;
            this.moveOffset = v3;
        }
        else if(line >= this.getLines()) {
            int v0 = this.getLines() - 1;
            this.cursor = this.text.length();
            if(line > this.getLines() || v0 == this.cursorLine) {
                this.moveOffset = v3;
            }

            this.cursorLine = v0;
        }
        else {
            if(line == this.cursorLine) {
                return;
            }

            if(this.moveOffset < 0f) {
                if(this.linesBreak.size > this.cursorLine * 2) {
                    v1 = this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.linesBreak.get(this.cursorLine * 2));
                }

                this.moveOffset = v1;
            }

            this.cursorLine = line;
            if(this.cursorLine * 2 >= this.linesBreak.size) {
                v1_1 = this.text.length();
            }
            else {
                v1_1 = this.linesBreak.get(this.cursorLine * 2);
            }

            this.cursor = v1_1;
            while(this.cursor < this.text.length()) {
                if(this.cursor > this.linesBreak.get(this.cursorLine * 2 + 1) - 1) {
                    break;
                }

                if(this.glyphPositions.get(this.cursor) - this.glyphPositions.get(this.linesBreak.get(this.cursorLine * 2)) >= this.moveOffset) {
                    break;
                }

                ++this.cursor;
            }

            this.showCursor();
        }
    }

    public boolean newLineAtEnd() {
        boolean v0;
        if(this.text.length() != 0) {
            if(this.text.charAt(this.text.length() - 1) != 10 && this.text.charAt(this.text.length() - 1) != 13) {
                goto label_19;
            }

            v0 = true;
        }
        else {
        label_19:
            v0 = false;
        }

        return v0;
    }

    public void setPrefRows(float prefRows) {
        this.prefRows = prefRows;
    }

    public void setSelection(int selectionStart, int selectionEnd) {
        super.setSelection(selectionStart, selectionEnd);
        this.updateCurrentLine();
    }

    void showCursor() {
        int v0;
        this.updateCurrentLine();
        if(this.cursorLine != this.firstLineShowing) {
            if(this.cursorLine >= this.firstLineShowing) {
                v0 = 1;
            }
            else {
                v0 = -1;
            }

            while(this.firstLineShowing > this.cursorLine || this.firstLineShowing + this.linesShowing - 1 < this.cursorLine) {
                this.firstLineShowing += v0;
            }
        }
    }

    protected void sizeChanged() {
        float v3;
        this.lastText = null;
        BitmapFont v2 = this.style.font;
        Drawable v1 = this.style.background;
        float v4 = this.getHeight();
        if(v1 == null) {
            v3 = 0f;
        }
        else {
            v3 = v1.getBottomHeight() + v1.getTopHeight();
        }

        this.linesShowing = ((int)Math.floor(((double)((v4 - v3) / v2.getLineHeight()))));
    }

    void updateCurrentLine() {
        int v0 = this.calculateCurrentLineIndex(this.cursor);
        int v1 = v0 / 2;
        if((v0 % 2 == 0 || v0 + 1 >= this.linesBreak.size || this.cursor != this.linesBreak.items[v0] || this.linesBreak.items[v0 + 1] != this.linesBreak.items[v0]) && (v1 < this.linesBreak.size / 2 || this.text.length() == 0 || this.text.charAt(this.text.length() - 1) == 10 || this.text.charAt(this.text.length() - 1) == 13)) {
            this.cursorLine = v1;
        }
    }
}

