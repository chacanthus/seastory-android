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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TextButton extends Button {
    public class TextButtonStyle extends ButtonStyle {
        public TextButtonStyle() {
            super();
        }

        public TextButtonStyle(TextButtonStyle style) {
            super(((ButtonStyle)style));
            this.font = style.font;
            if(style.fontColor != null) {
                this.fontColor = new Color(style.fontColor);
            }

            if(style.downFontColor != null) {
                this.downFontColor = new Color(style.downFontColor);
            }

            if(style.overFontColor != null) {
                this.overFontColor = new Color(style.overFontColor);
            }

            if(style.checkedFontColor != null) {
                this.checkedFontColor = new Color(style.checkedFontColor);
            }

            if(style.checkedOverFontColor != null) {
                this.checkedFontColor = new Color(style.checkedOverFontColor);
            }

            if(style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }
        }

        public TextButtonStyle(Drawable up, Drawable down, Drawable checked, BitmapFont font) {
            super(up, down, checked);
            this.font = font;
        }
    }

    private final Label label;
    private TextButtonStyle style;

    public TextButton(String text, Skin skin) {
        this(text, skin.get(TextButtonStyle.class));
        this.setSkin(skin);
    }

    public TextButton(String text, TextButtonStyle style) {
        super();
        this.setStyle(((ButtonStyle)style));
        this.style = style;
        this.label = new Label(((CharSequence)text), new LabelStyle(style.font, style.fontColor));
        this.label.setAlignment(1);
        this.add(this.label).expand().fill();
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public TextButton(String text, Skin skin, String styleName) {
        this(text, skin.get(styleName, TextButtonStyle.class));
        this.setSkin(skin);
    }

    public void draw(Batch batch, float parentAlpha) {
        Color v0;
        if(!this.isDisabled() || this.style.disabledFontColor == null) {
            if((this.isPressed()) && this.style.downFontColor != null) {
                v0 = this.style.downFontColor;
                goto label_7;
            }

            if((this.isChecked) && this.style.checkedFontColor != null) {
                if((this.isOver()) && this.style.checkedOverFontColor != null) {
                    v0 = this.style.checkedOverFontColor;
                    goto label_7;
                }

                v0 = this.style.checkedFontColor;
                goto label_7;
            }

            if((this.isOver()) && this.style.overFontColor != null) {
                v0 = this.style.overFontColor;
                goto label_7;
            }

            v0 = this.style.fontColor;
        }
        else {
            v0 = this.style.disabledFontColor;
        }

    label_7:
        if(v0 != null) {
            this.label.getStyle().fontColor = v0;
        }

        super.draw(batch, parentAlpha);
    }

    public Label getLabel() {
        return this.label;
    }

    public Cell getLabelCell() {
        return this.getCell(this.label);
    }

    public ButtonStyle getStyle() {
        return this.getStyle();
    }

    public TextButtonStyle getStyle() {
        return this.style;
    }

    public CharSequence getText() {
        return this.label.getText();
    }

    public void setStyle(ButtonStyle style) {
        if(style == null) {
            throw new NullPointerException("style cannot be null");
        }

        if(!(style instanceof TextButtonStyle)) {
            throw new IllegalArgumentException("style must be a TextButtonStyle.");
        }

        super.setStyle(style);
        this.style = style;
        if(this.label != null) {
            LabelStyle v0 = this.label.getStyle();
            v0.font = style.font;
            v0.fontColor = style.fontColor;
            this.label.setStyle(v0);
        }
    }

    public void setText(String text) {
        this.label.setText(((CharSequence)text));
    }

    public String toString() {
        return super.toString() + ": " + this.label.getText();
    }
}

