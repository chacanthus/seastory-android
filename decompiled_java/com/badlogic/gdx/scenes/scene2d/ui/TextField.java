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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont$BitmapFontData;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer$Task;

public class TextField extends Widget implements Disableable {
    public class DefaultOnscreenKeyboard implements OnscreenKeyboard {
        public DefaultOnscreenKeyboard() {
            super();
        }

        public void show(boolean visible) {
            Gdx.input.setOnscreenKeyboardVisible(visible);
        }
    }

    class KeyRepeatTask extends Task {
        int keycode;

        KeyRepeatTask(TextField arg1) {
            TextField.this = arg1;
            super();
        }

        public void run() {
            TextField.this.inputListener.keyDown(null, this.keycode);
        }
    }

    public abstract interface OnscreenKeyboard {
        public abstract void show(boolean arg0);
    }

    public class TextFieldClickListener extends ClickListener {
        public TextFieldClickListener(TextField arg1) {
            TextField.this = arg1;
            super();
        }

        public void clicked(InputEvent event, float x, float y) {
            int v1 = this.getTapCount() % 4;
            if(v1 == 0) {
                TextField.this.clearSelection();
            }

            if(v1 == 2) {
                int[] v0 = TextField.this.wordUnderCursor(x);
                TextField.this.setSelection(v0[0], v0[1]);
            }

            if(v1 == 3) {
                TextField.this.selectAll();
            }
        }

        protected void goEnd(boolean jump) {
            TextField.this.cursor = TextField.this.text.length();
        }

        protected void goHome(boolean jump) {
            TextField.this.cursor = 0;
        }

        public boolean keyDown(InputEvent event, int keycode) {
            boolean v1;
            int v12 = 22;
            int v11 = 21;
            int v10 = 3;
            boolean v5 = false;
            if(!TextField.this.disabled) {
                TextField.this.lastBlink = 0;
                TextField.this.cursorOn = false;
                Stage v3 = TextField.this.getStage();
                if(v3 != null && v3.getKeyboardFocus() == TextField.this) {
                    int v2 = 0;
                    boolean v0 = UIUtils.ctrl();
                    if(!v0 || (TextField.this.passwordMode)) {
                        v1 = false;
                    }
                    else {
                        v1 = true;
                    }

                    if(v0) {
                        if(keycode == 50) {
                            TextField.this.paste();
                            v2 = 1;
                        }

                        if(keycode != 31 && keycode != 133) {
                            if(keycode != 52 && keycode != 67) {
                                if(keycode == 29) {
                                    TextField.this.selectAll();
                                    v5 = true;
                                    goto label_8;
                                }
                                else {
                                    goto label_57;
                                }
                            }

                            TextField.this.cut();
                            v5 = true;
                            goto label_8;
                        }

                        TextField.this.copy();
                        v5 = true;
                        goto label_8;
                    }

                label_57:
                    if(UIUtils.shift()) {
                        if(keycode == 133) {
                            TextField.this.paste();
                        }

                        if(keycode == 112 && (TextField.this.hasSelection)) {
                            TextField.this.copy();
                            TextField.this.delete();
                        }

                        int v4 = TextField.this.cursor;
                        if(keycode == v11) {
                            TextField.this.moveCursor(false, v1);
                            v2 = 1;
                        }
                        else if(keycode == v12) {
                            TextField.this.moveCursor(true, v1);
                            v2 = 1;
                        }
                        else if(keycode == v10) {
                            this.goHome(v1);
                        }
                        else if(keycode == 132) {
                            this.goEnd(v1);
                        }
                        else {
                            goto label_85;
                        }

                        if(TextField.this.hasSelection) {
                            goto label_85;
                        }

                        TextField.this.selectionStart = v4;
                        TextField.this.hasSelection = true;
                    }
                    else {
                        if(keycode == v11) {
                            TextField.this.moveCursor(false, v1);
                            TextField.this.clearSelection();
                            v2 = 1;
                        }

                        if(keycode == v12) {
                            TextField.this.moveCursor(true, v1);
                            TextField.this.clearSelection();
                            v2 = 1;
                        }

                        if(keycode == v10) {
                            this.goHome(v1);
                            TextField.this.clearSelection();
                        }

                        if(keycode != 132) {
                            goto label_85;
                        }

                        this.goEnd(v1);
                        TextField.this.clearSelection();
                    }

                label_85:
                    TextField.this.cursor = MathUtils.clamp(TextField.this.cursor, 0, TextField.this.text.length());
                    if(v2 != 0) {
                        this.scheduleKeyRepeatTask(keycode);
                    }

                    v5 = true;
                }
            }

        label_8:
            return v5;
        }

        public boolean keyTyped(InputEvent event, char character) {
            String v3;
            int v4;
            TextField v7_1;
            int v5;
            int v0;
            int v1;
            int v2;
            boolean v7;
            if(TextField.this.disabled) {
                v7 = false;
            }
            else {
                switch(character) {
                    case 8: 
                    case 9: 
                    case 10: 
                    case 13: {
                        break;
                    }
                    default: {
                        if(character < 32) {
                            v7 = false;
                            goto label_4;
                        }

                        break;
                    }
                }

                Stage v6 = TextField.this.getStage();
                if(v6 != null && v6.getKeyboardFocus() == TextField.this) {
                    if(character != 9 && character != 10) {
                        goto label_37;
                    }
                    else if(TextField.this.focusTraversal) {
                        TextField.this.next(UIUtils.shift());
                    }
                    else {
                    label_37:
                        if(character == 127) {
                            v2 = 1;
                        }
                        else {
                            v2 = 0;
                        }

                        if(character == 8) {
                            v1 = 1;
                        }
                        else {
                            v1 = 0;
                        }

                        if(!TextField.this.onlyFontChars || (TextField.this.style.font.getData().hasGlyph(character))) {
                        label_59:
                            v0 = 1;
                        }
                        else {
                            if(TextField.this.writeEnters) {
                                if(character != 10) {
                                    if(character != 13) {
                                        goto label_95;
                                    }

                                    goto label_59;
                                }
                                else {
                                    goto label_59;
                                }
                            }

                        label_95:
                            v0 = 0;
                        }

                        if(v1 != 0 || v2 != 0) {
                            v5 = 1;
                        }
                        else {
                            v5 = 0;
                        }

                        if(v0 == 0 && v5 == 0) {
                            goto label_28;
                        }

                        if(TextField.this.hasSelection) {
                            TextField.this.cursor = TextField.this.delete(false);
                        }
                        else {
                            if(v1 != 0 && TextField.this.cursor > 0) {
                                v7_1 = TextField.this;
                                StringBuilder v8 = new StringBuilder().append(TextField.this.text.substring(0, TextField.this.cursor - 1));
                                String v9 = TextField.this.text;
                                TextField v10 = TextField.this;
                                int v11 = v10.cursor;
                                v10.cursor = v11 - 1;
                                v7_1.text = v8.append(v9.substring(v11)).toString();
                                TextField.this.renderOffset = 0f;
                            }

                            if(v2 == 0) {
                                goto label_73;
                            }

                            if(TextField.this.cursor >= TextField.this.text.length()) {
                                goto label_73;
                            }

                            TextField.this.text = TextField.this.text.substring(0, TextField.this.cursor) + TextField.this.text.substring(TextField.this.cursor + 1);
                        }

                    label_73:
                        if(v0 != 0 && v5 == 0) {
                            if(character == 13 || character == 10) {
                                v4 = 1;
                            }
                            else {
                                v4 = 0;
                            }

                            if(v4 == 0 && TextField.this.filter != null && !TextField.this.filter.acceptChar(TextField.this, character)) {
                                v7 = true;
                                goto label_4;
                            }

                            if(TextField.this.withinMaxLength(TextField.this.text.length())) {
                                goto label_162;
                            }

                            v7 = true;
                            goto label_4;
                        label_162:
                            if(v4 != 0) {
                                v3 = "\n";
                            }
                            else {
                                v3 = String.valueOf(character);
                            }

                            v7_1 = TextField.this;
                            TextField v8_1 = TextField.this;
                            TextField v9_1 = TextField.this;
                            int v10_1 = v9_1.cursor;
                            v9_1.cursor = v10_1 + 1;
                            v7_1.text = v8_1.insert(v10_1, ((CharSequence)v3), TextField.this.text);
                        }

                        TextField.this.updateDisplayText();
                    }

                label_28:
                    if(TextField.this.listener != null) {
                        TextField.this.listener.keyTyped(TextField.this, character);
                    }

                    v7 = true;
                    goto label_4;
                }

                v7 = false;
            }

        label_4:
            return v7;
        }

        public boolean keyUp(InputEvent event, int keycode) {
            boolean v0;
            if(TextField.this.disabled) {
                v0 = false;
            }
            else {
                TextField.this.keyRepeatTask.cancel();
                v0 = true;
            }

            return v0;
        }

        protected void scheduleKeyRepeatTask(int keycode) {
            if(!TextField.this.keyRepeatTask.isScheduled() || TextField.this.keyRepeatTask.keycode != keycode) {
                TextField.this.keyRepeatTask.keycode = keycode;
                TextField.this.keyRepeatTask.cancel();
                Timer.schedule(TextField.this.keyRepeatTask, TextField.keyRepeatInitialTime, TextField.keyRepeatTime);
            }
        }

        protected void setCursorPosition(float x, float y) {
            TextField.this.lastBlink = 0;
            TextField.this.cursorOn = false;
            TextField.this.cursor = TextField.this.letterUnderCursor(x);
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            boolean v1 = false;
            if((super.touchDown(event, x, y, pointer, button)) && (pointer != 0 || button == 0)) {
                if(!TextField.this.disabled) {
                    goto label_12;
                }

                v1 = true;
                goto label_4;
            label_12:
                this.setCursorPosition(x, y);
                TextField.this.selectionStart = TextField.this.cursor;
                Stage v0 = TextField.this.getStage();
                if(v0 != null) {
                    v0.setKeyboardFocus(TextField.this);
                }

                TextField.this.keyboard.show(true);
                TextField.this.hasSelection = true;
                v1 = true;
            }

        label_4:
            return v1;
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            super.touchDragged(event, x, y, pointer);
            this.setCursorPosition(x, y);
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if(TextField.this.selectionStart == TextField.this.cursor) {
                TextField.this.hasSelection = false;
            }

            super.touchUp(event, x, y, pointer, button);
        }
    }

    public abstract interface TextFieldFilter {
        public class DigitsOnlyFilter implements TextFieldFilter {
            public DigitsOnlyFilter() {
                super();
            }

            public boolean acceptChar(TextField textField, char c) {
                return Character.isDigit(c);
            }
        }

        public abstract boolean acceptChar(TextField arg0, char arg1);
    }

    public abstract interface TextFieldListener {
        public abstract void keyTyped(TextField arg0, char arg1);
    }

    public class TextFieldStyle {
        public TextFieldStyle() {
            super();
        }

        public TextFieldStyle(BitmapFont font, Color fontColor, Drawable cursor, Drawable selection, Drawable background) {
            super();
            this.background = background;
            this.cursor = cursor;
            this.font = font;
            this.fontColor = fontColor;
            this.selection = selection;
        }

        public TextFieldStyle(TextFieldStyle style) {
            super();
            this.messageFont = style.messageFont;
            if(style.messageFontColor != null) {
                this.messageFontColor = new Color(style.messageFontColor);
            }

            this.background = style.background;
            this.focusedBackground = style.focusedBackground;
            this.disabledBackground = style.disabledBackground;
            this.cursor = style.cursor;
            this.font = style.font;
            if(style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }

            if(style.focusedFontColor != null) {
                this.focusedFontColor = new Color(style.focusedFontColor);
            }

            if(style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }

            this.selection = style.selection;
        }
    }

    private static final char BACKSPACE = '\b';
    private static final char BULLET = '\u0095';
    private static final char DELETE = '\u007F';
    protected static final char ENTER_ANDROID = '\n';
    protected static final char ENTER_DESKTOP = '\r';
    private static final char TAB = '\t';
    private float blinkTime;
    private Clipboard clipboard;
    protected int cursor;
    boolean cursorOn;
    boolean disabled;
    protected CharSequence displayText;
    TextFieldFilter filter;
    boolean focusTraversal;
    protected final FloatArray glyphPositions;
    protected boolean hasSelection;
    InputListener inputListener;
    public static float keyRepeatInitialTime;
    KeyRepeatTask keyRepeatTask;
    public static float keyRepeatTime;
    OnscreenKeyboard keyboard;
    long lastBlink;
    protected final GlyphLayout layout;
    TextFieldListener listener;
    private int maxLength;
    private String messageText;
    boolean onlyFontChars;
    private StringBuilder passwordBuffer;
    private char passwordCharacter;
    boolean passwordMode;
    float renderOffset;
    protected int selectionStart;
    private float selectionWidth;
    private float selectionX;
    TextFieldStyle style;
    protected String text;
    private int textHAlign;
    protected float textHeight;
    protected float textOffset;
    private static final Vector2 tmp1;
    private static final Vector2 tmp2;
    private static final Vector2 tmp3;
    private int visibleTextEnd;
    private int visibleTextStart;
    protected boolean writeEnters;

    static  {
        TextField.tmp1 = new Vector2();
        TextField.tmp2 = new Vector2();
        TextField.tmp3 = new Vector2();
        TextField.keyRepeatInitialTime = 0.4f;
        TextField.keyRepeatTime = 0.1f;
    }

    public TextField(String text, Skin skin) {
        this(text, skin.get(TextFieldStyle.class));
    }

    public TextField(String text, TextFieldStyle style) {
        super();
        this.layout = new GlyphLayout();
        this.glyphPositions = new FloatArray();
        this.keyboard = new DefaultOnscreenKeyboard();
        this.focusTraversal = true;
        this.onlyFontChars = true;
        this.textHAlign = 8;
        this.passwordCharacter = '\u0095';
        this.maxLength = 0;
        this.blinkTime = 0.32f;
        this.cursorOn = true;
        this.keyRepeatTask = new KeyRepeatTask(this);
        this.setStyle(style);
        this.clipboard = Gdx.app.getClipboard();
        this.initialize();
        this.setText(text);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public TextField(String text, Skin skin, String styleName) {
        this(text, skin.get(styleName, TextFieldStyle.class));
    }

    public void appendText(String str) {
        if(str == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }

        this.clearSelection();
        this.cursor = this.text.length();
        this.paste(str);
    }

    private void blink() {
        boolean v2 = true;
        if(!Gdx.graphics.isContinuousRendering()) {
            this.cursorOn = true;
        }
        else {
            long v0 = TimeUtils.nanoTime();
            if((((float)(v0 - this.lastBlink))) / 1000000000f > this.blinkTime) {
                if(this.cursorOn) {
                    v2 = false;
                }

                this.cursorOn = v2;
                this.lastBlink = v0;
            }
        }
    }

    protected void calculateOffsets() {
        float v11 = this.getWidth();
        if(this.style.background != null) {
            v11 -= this.style.background.getLeftWidth() + this.style.background.getRightWidth();
        }

        float v0 = this.glyphPositions.get(this.cursor) - Math.abs(this.renderOffset);
        if(v0 <= 0f) {
            if(this.cursor > 0) {
                this.renderOffset = -this.glyphPositions.get(this.cursor - 1);
            }
            else {
                this.renderOffset = 0f;
            }
        }
        else if(v0 > v11) {
            this.renderOffset -= v0 - v11;
        }

        this.visibleTextStart = 0;
        this.textOffset = 0f;
        float v9 = Math.abs(this.renderOffset);
        int v1 = this.glyphPositions.size;
        float[] v2 = this.glyphPositions.items;
        float v10 = 0f;
        int v3 = 0;
        while(v3 < v1) {
            if(v2[v3] >= v9) {
                this.visibleTextStart = v3;
                v10 = v2[v3];
                this.textOffset = v10 - v9;
            }
            else {
                ++v3;
                continue;
            }

            break;
        }

        this.visibleTextEnd = Math.min(this.displayText.length(), this.cursor + 1);
        while(this.visibleTextEnd <= this.displayText.length()) {
            if(v2[this.visibleTextEnd] - v10 > v11) {
                break;
            }

            ++this.visibleTextEnd;
        }

        this.visibleTextEnd = Math.max(0, this.visibleTextEnd - 1);
        if(this.hasSelection) {
            int v6 = Math.min(this.cursor, this.selectionStart);
            int v4 = Math.max(this.cursor, this.selectionStart);
            float v7 = Math.max(v2[v6], v10);
            float v5 = Math.min(v2[v4], v2[this.visibleTextEnd]);
            this.selectionX = v7;
            this.selectionWidth = v5 - v7;
        }

        if(this.textHAlign == 1 || this.textHAlign == 16) {
            this.textOffset = v11 - (v2[this.visibleTextEnd] - v10);
            if(this.textHAlign == 1) {
                this.textOffset = ((float)Math.round(this.textOffset * 0.5f));
            }

            if(!this.hasSelection) {
                return;
            }

            this.selectionX += this.textOffset;
        }
    }

    public void clearSelection() {
        this.hasSelection = false;
    }

    protected boolean continueCursor(int index, int offset) {
        return this.isWordCharacter(this.text.charAt(index + offset));
    }

    public void copy() {
        if((this.hasSelection) && !this.passwordMode) {
            this.clipboard.setContents(this.text.substring(Math.min(this.cursor, this.selectionStart), Math.max(this.cursor, this.selectionStart)));
        }
    }

    protected InputListener createInputListener() {
        return new TextFieldClickListener(this);
    }

    public void cut() {
        if((this.hasSelection) && !this.passwordMode) {
            this.copy();
            this.cursor = this.delete();
        }
    }

    int delete() {
        return this.delete(true);
    }

    int delete(boolean updateText) {
        return this.delete(this.selectionStart, this.cursor, updateText);
    }

    int delete(int from, int to, boolean updateText) {
        String v2;
        int v1 = Math.min(from, to);
        int v0 = Math.max(from, to);
        StringBuilder v3 = new StringBuilder();
        if(v1 > 0) {
            v2 = this.text.substring(0, v1);
        }
        else {
            v2 = "";
        }

        v3 = v3.append(v2);
        if(v0 < this.text.length()) {
            v2 = this.text.substring(v0, this.text.length());
        }
        else {
            v2 = "";
        }

        this.text = v3.append(v2).toString();
        if(updateText) {
            this.updateDisplayText();
        }

        this.clearSelection();
        return v1;
    }

    public void draw(Batch batch, float parentAlpha) {
        BitmapFont v23;
        float v26;
        Drawable v2;
        Color v22;
        int v21;
        Stage v24 = this.getStage();
        if(v24 == null || v24.getKeyboardFocus() != this) {
            v21 = 0;
        }
        else {
            v21 = 1;
        }

        BitmapFont v11 = this.style.font;
        if(!this.disabled || this.style.disabledFontColor == null) {
            if(v21 != 0 && this.style.focusedFontColor != null) {
                v22 = this.style.focusedFontColor;
                goto label_20;
            }

            v22 = this.style.fontColor;
        }
        else {
            v22 = this.style.disabledFontColor;
        }

    label_20:
        Drawable v9 = this.style.selection;
        Drawable v20 = this.style.cursor;
        if(!this.disabled || this.style.disabledBackground == null) {
            if(v21 != 0 && this.style.focusedBackground != null) {
                v2 = this.style.focusedBackground;
                goto label_37;
            }

            v2 = this.style.background;
        }
        else {
            v2 = this.style.disabledBackground;
        }

    label_37:
        Color v19 = this.getColor();
        float v4 = this.getX();
        float v5 = this.getY();
        float v6 = this.getWidth();
        float v7 = this.getHeight();
        batch.setColor(v19.r, v19.g, v19.b, v19.a * parentAlpha);
        float v18 = 0f;
        if(v2 != null) {
            v2.draw(batch, v4, v5, v6, v7);
            v18 = v2.getLeftWidth();
        }

        float v25 = this.getTextY(v11, v2);
        this.calculateOffsets();
        if(v21 != 0 && (this.hasSelection) && v9 != null) {
            this.drawSelection(v9, batch, v11, v4 + v18, v5 + v25);
        }

        if(v11.isFlipped()) {
            v26 = -this.textHeight;
        }
        else {
            v26 = 0f;
        }

        if(this.displayText.length() != 0) {
            v11.setColor(v22.r, v22.g, v22.b, v22.a * parentAlpha);
            this.drawText(batch, v11, v4 + v18, v5 + v25 + v26);
        }
        else if(v21 == 0 && this.messageText != null) {
            if(this.style.messageFontColor != null) {
                v11.setColor(this.style.messageFontColor.r, this.style.messageFontColor.g, this.style.messageFontColor.b, this.style.messageFontColor.a * parentAlpha);
            }
            else {
                v11.setColor(0.7f, 0.7f, 0.7f, parentAlpha);
            }

            if(this.style.messageFont != null) {
                v23 = this.style.messageFont;
            }
            else {
                v23 = v11;
            }

            v23.draw(batch, this.messageText, v4 + v18, v5 + v25 + v26);
        }

        if(v21 != 0 && !this.disabled) {
            this.blink();
            if((this.cursorOn) && v20 != null) {
                this.drawCursor(v20, batch, v11, v4 + v18, v5 + v25);
            }
        }
    }

    protected void drawCursor(Drawable cursorPatch, Batch batch, BitmapFont font, float x, float y) {
        cursorPatch.draw(batch, this.textOffset + x + this.glyphPositions.get(this.cursor) - this.glyphPositions.items[this.visibleTextStart] - 1f, y - this.textHeight - font.getDescent(), cursorPatch.getMinWidth(), this.textHeight + font.getDescent() / 2f);
    }

    protected void drawSelection(Drawable selection, Batch batch, BitmapFont font, float x, float y) {
        selection.draw(batch, this.selectionX + x + this.renderOffset, y - this.textHeight - font.getDescent(), this.selectionWidth, this.textHeight + font.getDescent() / 2f);
    }

    protected void drawText(Batch batch, BitmapFont font, float x, float y) {
        font.draw(batch, this.displayText, x + this.textOffset, y, this.visibleTextStart, this.visibleTextEnd, 0f, 8, false);
    }

    private TextField findNextTextField(Array arg12, TextField best, Vector2 bestCoords, Vector2 currentCoords, boolean up) {
        int v0;
        int v8 = 0;
        int v9 = arg12.size;
        while(v8 < v9) {
            Object v6 = arg12.get(v8);
            if((((TextField)v6)) != this) {
                if((v6 instanceof TextField)) {
                    Object v10 = v6;
                    if(!((TextField)v10).isDisabled() && (((TextField)v10).focusTraversal)) {
                        Vector2 v7 = ((Actor)v6).getParent().localToStageCoordinates(TextField.tmp3.set(((Actor)v6).getX(), ((Actor)v6).getY()));
                        if(v7.y >= currentCoords.y) {
                            if(v7.y == currentCoords.y && v7.x > currentCoords.x) {
                                goto label_29;
                            }

                            v0 = 0;
                        }
                        else {
                        label_29:
                            v0 = 1;
                        }

                        if((v0 ^ (((int)up))) == 0) {
                            goto label_5;
                        }

                        if(best != null) {
                            if(v7.y <= bestCoords.y) {
                                if(v7.y == bestCoords.y && v7.x < bestCoords.x) {
                                    goto label_42;
                                }

                                v0 = 0;
                            }
                            else {
                            label_42:
                                v0 = 1;
                            }

                            if((v0 ^ (((int)up))) == 0) {
                                goto label_5;
                            }
                        }

                        Object v13_1 = v6;
                        bestCoords.set(v7);
                    }
                }
                else {
                    if(!(v6 instanceof Group)) {
                        goto label_5;
                    }

                    best = this.findNextTextField(((Group)v6).getChildren(), best, bestCoords, currentCoords, up);
                }
            }

        label_5:
            ++v8;
        }

        return best;
    }

    public int getCursorPosition() {
        return this.cursor;
    }

    public InputListener getDefaultInputListener() {
        return this.inputListener;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public OnscreenKeyboard getOnscreenKeyboard() {
        return this.keyboard;
    }

    public float getPrefHeight() {
        float v0 = this.textHeight;
        if(this.style.background != null) {
            v0 = Math.max(this.style.background.getBottomHeight() + v0 + this.style.background.getTopHeight(), this.style.background.getMinHeight());
        }

        return v0;
    }

    public float getPrefWidth() {
        return 150f;
    }

    public String getSelection() {
        String v0;
        if(this.hasSelection) {
            v0 = this.text.substring(Math.min(this.selectionStart, this.cursor), Math.max(this.selectionStart, this.cursor));
        }
        else {
            v0 = "";
        }

        return v0;
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public TextFieldStyle getStyle() {
        return this.style;
    }

    public String getText() {
        return this.text;
    }

    public TextFieldFilter getTextFieldFilter() {
        return this.filter;
    }

    protected float getTextY(BitmapFont font, Drawable background) {
        float v5 = 2f;
        float v1 = this.getHeight();
        float v2 = this.textHeight / v5 + font.getDescent();
        if(background != null) {
            float v0 = background.getBottomHeight();
            v2 = ((float)(((int)((v1 - background.getTopHeight() - v0) / v5 + v2 + v0))));
        }
        else {
            v2 = ((float)(((int)(v1 / v5 + v2))));
        }

        return v2;
    }

    protected void initialize() {
        this.writeEnters = false;
        InputListener v0 = this.createInputListener();
        this.inputListener = v0;
        this.addListener(((EventListener)v0));
    }

    String insert(int position, CharSequence text, String to) {
        String v0;
        if(to.length() == 0) {
            v0 = text.toString();
        }
        else {
            v0 = to.substring(0, position) + text + to.substring(position, to.length());
        }

        return v0;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public boolean isPasswordMode() {
        return this.passwordMode;
    }

    protected boolean isWordCharacter(char c) {
        boolean v0;
        if(c < 65 || c > 90) {
            if(c >= 97 && c <= 122) {
                goto label_12;
            }

            if(c >= 48 && c <= 57) {
            label_12:
                v0 = true;
                goto label_13;
            }

            v0 = false;
        }
        else {
            goto label_12;
        }

    label_13:
        return v0;
    }

    protected int letterUnderCursor(float x) {
        x -= this.renderOffset + this.textOffset;
        int v2 = this.glyphPositions.size - 1;
        float[] v0 = this.glyphPositions.items;
        int v1 = 0;
        int v3 = this.glyphPositions.size;
        while(v1 < v3) {
            if(v0[v1] > x) {
                v2 = v1 - 1;
            }
            else {
                ++v1;
                continue;
            }

            break;
        }

        return Math.max(0, v2);
    }

    protected void moveCursor(boolean forward, boolean jump) {
        int v2;
        int v1;
        int v0 = 0;
        if(forward) {
            v1 = this.text.length();
        }
        else {
            v1 = 0;
        }

        if(!forward) {
            v0 = -1;
        }

        do {
            if(forward) {
                v2 = this.cursor + 1;
                this.cursor = v2;
                if(v2 >= v1) {
                    return;
                }

                goto label_10;
            }
            else {
                v2 = this.cursor - 1;
                this.cursor = v2;
                if(v2 > v1) {
                label_10:
                    if((jump) && (this.continueCursor(this.cursor, v0))) {
                        continue;
                    }
                }
            }

            return;
        }
        while(true);
    }

    public void next(boolean up) {
        TextField v2 = null;
        float v9 = 340282346638528860000000000000000000000f;
        Stage v6 = this.getStage();
        if(v6 != null) {
            this.getParent().localToStageCoordinates(TextField.tmp1.set(this.getX(), this.getY()));
            TextField v7 = this.findNextTextField(v6.getActors(), v2, TextField.tmp2, TextField.tmp1, up);
            if(v7 == null) {
                if(up) {
                    TextField.tmp1.set(0f, 0f);
                }
                else {
                    TextField.tmp1.set(v9, v9);
                }

                v7 = this.findNextTextField(this.getStage().getActors(), v2, TextField.tmp2, TextField.tmp1, up);
            }

            if(v7 == null) {
                goto label_35;
            }

            v6.setKeyboardFocus(((Actor)v7));
            return;
        label_35:
            Gdx.input.setOnscreenKeyboardVisible(false);
        }
    }

    void paste(String content) {
        if(content != null) {
            StringBuilder v0 = new StringBuilder();
            int v5 = this.text.length();
            BitmapFontData v2 = this.style.font.getData();
            int v3 = 0;
            int v4 = content.length();
            while(v3 < v4) {
                if(!this.withinMaxLength(v0.length() + v5)) {
                    break;
                }

                char v1 = content.charAt(v3);
                if(!this.writeEnters || v1 != 13) {
                    if((this.onlyFontChars) && !v2.hasGlyph(v1)) {
                        goto label_42;
                    }

                    if(this.filter == null) {
                        goto label_49;
                    }

                    if(!this.filter.acceptChar(this, v1)) {
                        goto label_42;
                    }

                label_49:
                    v0.append(v1);
                }
                else if(v1 == 10) {
                    goto label_49;
                }
                else {
                    goto label_49;
                }

            label_42:
                ++v3;
            }

            content = v0.toString();
            if(this.hasSelection) {
                this.cursor = this.delete(false);
            }

            this.text = this.insert(this.cursor, ((CharSequence)content), this.text);
            this.updateDisplayText();
            this.cursor += content.length();
        }
    }

    void paste() {
        this.paste(this.clipboard.getContents());
    }

    public void selectAll() {
        this.setSelection(0, this.text.length());
    }

    public void setAlignment(int alignment) {
        if(alignment == 8 || alignment == 1 || alignment == 16) {
            this.textHAlign = alignment;
        }
    }

    public void setBlinkTime(float blinkTime) {
        this.blinkTime = blinkTime;
    }

    public void setClipboard(Clipboard clipboard) {
        this.clipboard = clipboard;
    }

    public void setCursorPosition(int cursorPosition) {
        if(cursorPosition < 0) {
            throw new IllegalArgumentException("cursorPosition must be >= 0");
        }

        this.clearSelection();
        this.cursor = Math.min(cursorPosition, this.text.length());
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setFocusTraversal(boolean focusTraversal) {
        this.focusTraversal = focusTraversal;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setOnlyFontChars(boolean onlyFontChars) {
        this.onlyFontChars = onlyFontChars;
    }

    public void setOnscreenKeyboard(OnscreenKeyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void setPasswordCharacter(char passwordCharacter) {
        this.passwordCharacter = passwordCharacter;
        if(this.passwordMode) {
            this.updateDisplayText();
        }
    }

    public void setPasswordMode(boolean passwordMode) {
        this.passwordMode = passwordMode;
        this.updateDisplayText();
    }

    public void setSelection(int selectionStart, int selectionEnd) {
        if(selectionStart < 0) {
            throw new IllegalArgumentException("selectionStart must be >= 0");
        }

        if(selectionEnd < 0) {
            throw new IllegalArgumentException("selectionEnd must be >= 0");
        }

        selectionStart = Math.min(this.text.length(), selectionStart);
        selectionEnd = Math.min(this.text.length(), selectionEnd);
        if(selectionEnd == selectionStart) {
            this.clearSelection();
        }
        else {
            if(selectionEnd < selectionStart) {
                int v0 = selectionEnd;
                selectionEnd = selectionStart;
                selectionStart = v0;
            }

            this.hasSelection = true;
            this.selectionStart = selectionStart;
            this.cursor = selectionEnd;
        }
    }

    public void setStyle(TextFieldStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.textHeight = style.font.getCapHeight() - style.font.getDescent() * 2f;
        this.invalidateHierarchy();
    }

    public void setText(String str) {
        if(str == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }

        if(!str.equals(this.text)) {
            this.clearSelection();
            this.text = "";
            this.paste(str);
            this.cursor = 0;
        }
    }

    public void setTextFieldFilter(TextFieldFilter filter) {
        this.filter = filter;
    }

    public void setTextFieldListener(TextFieldListener listener) {
        this.listener = listener;
    }

    void updateDisplayText() {
        BitmapFont v3 = this.style.font;
        BitmapFontData v2 = v3.getData();
        String v8 = this.text;
        int v9 = v8.length();
        StringBuilder v0 = new StringBuilder();
        int v4;
        for(v4 = 0; v4 < v9; ++v4) {
            char v1 = v8.charAt(v4);
            if(!v2.hasGlyph(v1)) {
                v1 = ' ';
            }

            v0.append(v1);
        }

        String v6 = v0.toString();
        if(!this.passwordMode || !v2.hasGlyph(this.passwordCharacter)) {
            this.displayText = ((CharSequence)v6);
            goto label_34;
        label_59:
            while(v4 < v9) {
                this.passwordBuffer.append(this.passwordCharacter);
                ++v4;
            }

        label_32:
            this.displayText = this.passwordBuffer;
        }
        else {
            if(this.passwordBuffer == null) {
                this.passwordBuffer = new StringBuilder(v6.length());
            }

            if(this.passwordBuffer.length() > v9) {
                this.passwordBuffer.setLength(v9);
            }
            else {
                v4 = this.passwordBuffer.length();
                goto label_59;
            }

            goto label_32;
        }

    label_34:
        this.layout.setText(v3, this.displayText);
        this.glyphPositions.clear();
        float v10 = 0f;
        if(this.layout.runs.size > 0) {
            FloatArray v11 = this.layout.runs.first().xAdvances;
            v4 = 0;
            int v5 = v11.size;
            while(v4 < v5) {
                this.glyphPositions.add(v10);
                v10 += v11.get(v4);
                ++v4;
            }
        }

        this.glyphPositions.add(v10);
        if(this.selectionStart > v6.length()) {
            this.selectionStart = v9;
        }
    }

    boolean withinMaxLength(int size) {
        boolean v0;
        if(this.maxLength <= 0 || size < this.maxLength) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    int[] wordUnderCursor(float x) {
        return this.wordUnderCursor(this.letterUnderCursor(x));
    }

    protected int[] wordUnderCursor(int at) {
        String v4 = this.text;
        int v3 = at;
        int v2 = v4.length();
        int v1 = 0;
        int v0 = v3;
        while(v0 < v2) {
            if(!this.isWordCharacter(v4.charAt(v0))) {
                v2 = v0;
            }
            else {
                ++v0;
                continue;
            }

            break;
        }

        v0 = v3 - 1;
        while(v0 > -1) {
            if(!this.isWordCharacter(v4.charAt(v0))) {
                v1 = v0 + 1;
            }
            else {
                --v0;
                continue;
            }

            break;
        }

        int[] v5 = new int[2];
        v5[0] = v1;
        v5[1] = v2;
        return v5;
    }
}

