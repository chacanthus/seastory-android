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
import com.badlogic.gdx.utils.Scaling;

public class ImageTextButton extends Button {
    public class ImageTextButtonStyle extends TextButtonStyle {
        public ImageTextButtonStyle() {
            super();
        }

        public ImageTextButtonStyle(ImageTextButtonStyle style) {
            super(((TextButtonStyle)style));
            if(style.imageUp != null) {
                this.imageUp = style.imageUp;
            }

            if(style.imageDown != null) {
                this.imageDown = style.imageDown;
            }

            if(style.imageOver != null) {
                this.imageOver = style.imageOver;
            }

            if(style.imageChecked != null) {
                this.imageChecked = style.imageChecked;
            }

            if(style.imageCheckedOver != null) {
                this.imageCheckedOver = style.imageCheckedOver;
            }

            if(style.imageDisabled != null) {
                this.imageDisabled = style.imageDisabled;
            }
        }

        public ImageTextButtonStyle(TextButtonStyle style) {
            super(style);
        }

        public ImageTextButtonStyle(Drawable up, Drawable down, Drawable checked, BitmapFont font) {
            super(up, down, checked, font);
        }
    }

    private final Image image;
    private final Label label;
    private ImageTextButtonStyle style;

    public ImageTextButton(String text, ImageTextButtonStyle style) {
        super(((ButtonStyle)style));
        this.style = style;
        this.defaults().space(3f);
        this.image = new Image();
        this.image.setScaling(Scaling.fit);
        this.add(this.image);
        this.label = new Label(((CharSequence)text), new LabelStyle(style.font, style.fontColor));
        this.label.setAlignment(1);
        this.add(this.label);
        this.setStyle(((ButtonStyle)style));
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public ImageTextButton(String text, Skin skin) {
        this(text, skin.get(ImageTextButtonStyle.class));
        this.setSkin(skin);
    }

    public ImageTextButton(String text, Skin skin, String styleName) {
        this(text, skin.get(styleName, ImageTextButtonStyle.class));
        this.setSkin(skin);
    }

    public void draw(Batch batch, float parentAlpha) {
        Color v0;
        this.updateImage();
        if(!this.isDisabled() || this.style.disabledFontColor == null) {
            if((this.isPressed()) && this.style.downFontColor != null) {
                v0 = this.style.downFontColor;
                goto label_8;
            }

            if((this.isChecked) && this.style.checkedFontColor != null) {
                if((this.isOver()) && this.style.checkedOverFontColor != null) {
                    v0 = this.style.checkedOverFontColor;
                    goto label_8;
                }

                v0 = this.style.checkedFontColor;
                goto label_8;
            }

            if((this.isOver()) && this.style.overFontColor != null) {
                v0 = this.style.overFontColor;
                goto label_8;
            }

            v0 = this.style.fontColor;
        }
        else {
            v0 = this.style.disabledFontColor;
        }

    label_8:
        if(v0 != null) {
            this.label.getStyle().fontColor = v0;
        }

        super.draw(batch, parentAlpha);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return this.getCell(this.image);
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

    public ImageTextButtonStyle getStyle() {
        return this.style;
    }

    public CharSequence getText() {
        return this.label.getText();
    }

    public void setStyle(ButtonStyle style) {
        if(!(style instanceof ImageTextButtonStyle)) {
            throw new IllegalArgumentException("style must be a ImageTextButtonStyle.");
        }

        super.setStyle(style);
        this.style = style;
        if(this.image != null) {
            this.updateImage();
        }

        if(this.label != null) {
            LabelStyle v0 = this.label.getStyle();
            v0.font = style.font;
            v0.fontColor = style.fontColor;
            this.label.setStyle(v0);
        }
    }

    public void setText(CharSequence text) {
        this.label.setText(text);
    }

    public String toString() {
        return super.toString() + ": " + this.label.getText();
    }

    private void updateImage() {
        Drawable v0 = null;
        if(!this.isDisabled() || this.style.imageDisabled == null) {
            if((this.isPressed()) && this.style.imageDown != null) {
                v0 = this.style.imageDown;
                goto label_8;
            }

            if((this.isChecked) && this.style.imageChecked != null) {
                if(this.style.imageCheckedOver != null && (this.isOver())) {
                    v0 = this.style.imageCheckedOver;
                    goto label_8;
                }

                v0 = this.style.imageChecked;
                goto label_8;
            }

            if((this.isOver()) && this.style.imageOver != null) {
                v0 = this.style.imageOver;
                goto label_8;
            }

            if(this.style.imageUp == null) {
                goto label_8;
            }

            v0 = this.style.imageUp;
        }
        else {
            v0 = this.style.imageDisabled;
        }

    label_8:
        this.image.setDrawable(v0);
    }
}

