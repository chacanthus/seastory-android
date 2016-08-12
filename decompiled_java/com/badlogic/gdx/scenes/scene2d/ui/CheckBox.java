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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CheckBox extends TextButton {
    public class CheckBoxStyle extends TextButtonStyle {
        public CheckBoxStyle() {
            super();
        }

        public CheckBoxStyle(CheckBoxStyle style) {
            super();
            this.checkboxOff = style.checkboxOff;
            this.checkboxOn = style.checkboxOn;
            this.checkboxOver = style.checkboxOver;
            this.checkboxOffDisabled = style.checkboxOffDisabled;
            this.checkboxOnDisabled = style.checkboxOnDisabled;
            this.font = style.font;
            this.fontColor = new Color(style.fontColor);
        }

        public CheckBoxStyle(Drawable checkboxOff, Drawable checkboxOn, BitmapFont font, Color fontColor) {
            super();
            this.checkboxOff = checkboxOff;
            this.checkboxOn = checkboxOn;
            this.font = font;
            this.fontColor = fontColor;
        }
    }

    private Image image;
    private Cell imageCell;
    private CheckBoxStyle style;

    public CheckBox(String text, CheckBoxStyle style) {
        super(text, ((TextButtonStyle)style));
        this.clearChildren();
        Image v1 = new Image(style.checkboxOff);
        this.image = v1;
        this.imageCell = this.add(((Actor)v1));
        Label v0 = this.getLabel();
        this.add(((Actor)v0));
        v0.setAlignment(8);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public CheckBox(String text, Skin skin) {
        this(text, skin.get(CheckBoxStyle.class));
    }

    public CheckBox(String text, Skin skin, String styleName) {
        this(text, skin.get(styleName, CheckBoxStyle.class));
    }

    public void draw(Batch batch, float parentAlpha) {
        Drawable v0 = null;
        if(this.isDisabled()) {
            if((this.isChecked) && this.style.checkboxOnDisabled != null) {
                v0 = this.style.checkboxOnDisabled;
                goto label_10;
            }

            v0 = this.style.checkboxOffDisabled;
        }

    label_10:
        if(v0 == null) {
            if((this.isChecked) && this.style.checkboxOn != null) {
                v0 = this.style.checkboxOn;
                goto label_18;
            }

            if((this.isOver()) && this.style.checkboxOver != null && !this.isDisabled()) {
                v0 = this.style.checkboxOver;
                goto label_18;
            }

            v0 = this.style.checkboxOff;
        }

    label_18:
        this.image.setDrawable(v0);
        super.draw(batch, parentAlpha);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return this.imageCell;
    }

    public ButtonStyle getStyle() {
        return this.getStyle();
    }

    public CheckBoxStyle getStyle() {
        return this.style;
    }

    public TextButtonStyle getStyle() {
        return this.getStyle();
    }

    public void setStyle(ButtonStyle style) {
        if(!(style instanceof CheckBoxStyle)) {
            throw new IllegalArgumentException("style must be a CheckBoxStyle.");
        }

        super.setStyle(style);
        this.style = ((CheckBoxStyle)style);
    }
}

