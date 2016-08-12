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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class ImageButton extends Button {
    public class ImageButtonStyle extends ButtonStyle {
        public Drawable imageChecked;
        public Drawable imageCheckedOver;
        public Drawable imageDisabled;
        public Drawable imageDown;
        public Drawable imageOver;
        public Drawable imageUp;

        public ImageButtonStyle(Drawable up, Drawable down, Drawable checked, Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
            super(up, down, checked);
            this.imageUp = imageUp;
            this.imageDown = imageDown;
            this.imageChecked = imageChecked;
        }

        public ImageButtonStyle() {
            super();
        }

        public ImageButtonStyle(ButtonStyle style) {
            super(style);
        }

        public ImageButtonStyle(ImageButtonStyle style) {
            super(((ButtonStyle)style));
            this.imageUp = style.imageUp;
            this.imageDown = style.imageDown;
            this.imageOver = style.imageOver;
            this.imageChecked = style.imageChecked;
            this.imageCheckedOver = style.imageCheckedOver;
            this.imageDisabled = style.imageDisabled;
        }
    }

    private final Image image;
    private ImageButtonStyle style;

    public ImageButton(ImageButtonStyle style) {
        super(((ButtonStyle)style));
        this.image = new Image();
        this.image.setScaling(Scaling.fit);
        this.add(this.image);
        this.setStyle(((ButtonStyle)style));
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
    }

    public ImageButton(Skin skin) {
        this(skin.get(ImageButtonStyle.class));
    }

    public ImageButton(Skin skin, String styleName) {
        this(skin.get(styleName, ImageButtonStyle.class));
    }

    public ImageButton(Drawable imageUp) {
        this(new ImageButtonStyle(null, null, null, imageUp, null, null));
    }

    public ImageButton(Drawable imageUp, Drawable imageDown) {
        this(new ImageButtonStyle(null, null, null, imageUp, imageDown, null));
    }

    public ImageButton(Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
        this(new ImageButtonStyle(null, null, null, imageUp, imageDown, imageChecked));
    }

    public void draw(Batch batch, float parentAlpha) {
        this.updateImage();
        super.draw(batch, parentAlpha);
    }

    public Image getImage() {
        return this.image;
    }

    public Cell getImageCell() {
        return this.getCell(this.image);
    }

    public ButtonStyle getStyle() {
        return this.getStyle();
    }

    public ImageButtonStyle getStyle() {
        return this.style;
    }

    public void setStyle(ButtonStyle style) {
        if(!(style instanceof ImageButtonStyle)) {
            throw new IllegalArgumentException("style must be an ImageButtonStyle.");
        }

        super.setStyle(style);
        this.style = ((ImageButtonStyle)style);
        if(this.image != null) {
            this.updateImage();
        }
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

