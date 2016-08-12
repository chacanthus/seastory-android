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
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class List extends Widget implements Cullable {
    public class ListStyle {
        public ListStyle() {
            super();
            this.fontColorSelected = new Color(1f, 1f, 1f, 1f);
            this.fontColorUnselected = new Color(1f, 1f, 1f, 1f);
        }

        public ListStyle(BitmapFont font, Color fontColorSelected, Color fontColorUnselected, Drawable selection) {
            super();
            this.fontColorSelected = new Color(1f, 1f, 1f, 1f);
            this.fontColorUnselected = new Color(1f, 1f, 1f, 1f);
            this.font = font;
            this.fontColorSelected.set(fontColorSelected);
            this.fontColorUnselected.set(fontColorUnselected);
            this.selection = selection;
        }

        public ListStyle(ListStyle style) {
            super();
            this.fontColorSelected = new Color(1f, 1f, 1f, 1f);
            this.fontColorUnselected = new Color(1f, 1f, 1f, 1f);
            this.font = style.font;
            this.fontColorSelected.set(style.fontColorSelected);
            this.fontColorUnselected.set(style.fontColorUnselected);
            this.selection = style.selection;
        }
    }

    private Rectangle cullingArea;
    private float itemHeight;
    private final Array items;
    private float prefHeight;
    private float prefWidth;
    final ArraySelection selection;
    private ListStyle style;
    private float textOffsetX;
    private float textOffsetY;

    public List(ListStyle style) {
        super();
        this.items = new Array();
        this.selection = new ArraySelection(this.items);
        this.selection.setActor(((Actor)this));
        this.selection.setRequired(true);
        this.setStyle(style);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0 = false;
                if((pointer != 0 || button == 0) && !List.this.selection.isDisabled()) {
                    List.this.touchDown(y);
                    v0 = true;
                }

                return v0;
            }
        });
    }

    public List(Skin skin) {
        this(skin.get(ListStyle.class));
    }

    public List(Skin skin, String styleName) {
        this(skin.get(styleName, ListStyle.class));
    }

    public void clearItems() {
        if(this.items.size != 0) {
            this.items.clear();
            this.selection.clear();
            this.invalidateHierarchy();
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        BitmapFont v14 = this.style.font;
        Drawable v7 = this.style.selection;
        Color v15 = this.style.fontColorSelected;
        Color v16 = this.style.fontColorUnselected;
        Color v13 = this.getColor();
        batch.setColor(v13.r, v13.g, v13.b, v13.a * parentAlpha);
        float v3 = this.getX();
        float v4 = this.getY();
        float v5 = this.getWidth();
        float v6 = this.getHeight();
        float v19 = v6;
        Drawable v1 = this.style.background;
        if(v1 != null) {
            v1.draw(batch, v3, v4, v5, v6);
            float v20 = v1.getLeftWidth();
            v3 += v20;
            v19 -= v1.getTopHeight();
            v5 -= v1.getRightWidth() + v20;
        }

        v14.setColor(v16.r, v16.g, v16.b, v16.a * parentAlpha);
        int v17;
        for(v17 = 0; v17 < this.items.size; ++v17) {
            if(this.cullingArea != null) {
                if(v19 - this.itemHeight <= this.cullingArea.y + this.cullingArea.height && v19 >= this.cullingArea.y) {
                    goto label_74;
                }

                if(v19 >= this.cullingArea.y) {
                    goto label_120;
                }

                return;
            }
            else {
            label_74:
                Object v18 = this.items.get(v17);
                boolean v21 = this.selection.contains(v18);
                if(v21) {
                    v7.draw(batch, v3, v4 + v19 - this.itemHeight, v5, this.itemHeight);
                    v14.setColor(v15.r, v15.g, v15.b, v15.a * parentAlpha);
                }

                v14.draw(batch, v18.toString(), this.textOffsetX + v3, v4 + v19 - this.textOffsetY);
                if(!v21) {
                    goto label_120;
                }

                v14.setColor(v16.r, v16.g, v16.b, v16.a * parentAlpha);
            }

        label_120:
            v19 -= this.itemHeight;
        }
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public Array getItems() {
        return this.items;
    }

    public float getPrefHeight() {
        this.validate();
        return this.prefHeight;
    }

    public float getPrefWidth() {
        this.validate();
        return this.prefWidth;
    }

    public Object getSelected() {
        return this.selection.first();
    }

    public int getSelectedIndex() {
        int v1;
        OrderedSet v0 = this.selection.items();
        if(((ObjectSet)v0).size == 0) {
            v1 = -1;
        }
        else {
            v1 = this.items.indexOf(((ObjectSet)v0).first(), false);
        }

        return v1;
    }

    public ArraySelection getSelection() {
        return this.selection;
    }

    public ListStyle getStyle() {
        return this.style;
    }

    public void layout() {
        BitmapFont v1 = this.style.font;
        Drawable v5 = this.style.selection;
        this.itemHeight = v1.getCapHeight() - v1.getDescent() * 2f;
        this.itemHeight += v5.getTopHeight() + v5.getBottomHeight();
        this.textOffsetX = v5.getLeftWidth();
        this.textOffsetY = v5.getTopHeight() - v1.getDescent();
        this.prefWidth = 0f;
        Pool v4 = Pools.get(GlyphLayout.class);
        Object v3 = v4.obtain();
        int v2;
        for(v2 = 0; v2 < this.items.size; ++v2) {
            ((GlyphLayout)v3).setText(v1, this.items.get(v2).toString());
            this.prefWidth = Math.max(((GlyphLayout)v3).width, this.prefWidth);
        }

        v4.free(v3);
        this.prefWidth += v5.getLeftWidth() + v5.getRightWidth();
        this.prefHeight = (((float)this.items.size)) * this.itemHeight;
        Drawable v0 = this.style.background;
        if(v0 != null) {
            this.prefWidth += v0.getLeftWidth() + v0.getRightWidth();
            this.prefHeight += v0.getTopHeight() + v0.getBottomHeight();
        }
    }

    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    public void setItems(Array newItems) {
        if(newItems == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }

        float v1 = this.getPrefWidth();
        float v0 = this.getPrefHeight();
        this.items.clear();
        this.items.addAll(newItems);
        this.selection.validate();
        this.invalidate();
        if(v1 != this.getPrefWidth() || v0 != this.getPrefHeight()) {
            this.invalidateHierarchy();
        }
    }

    public void setItems(Object[] arg5) {
        if(arg5 == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }

        float v1 = this.getPrefWidth();
        float v0 = this.getPrefHeight();
        this.items.clear();
        this.items.addAll(arg5);
        this.selection.validate();
        this.invalidate();
        if(v1 != this.getPrefWidth() || v0 != this.getPrefHeight()) {
            this.invalidateHierarchy();
        }
    }

    public void setSelected(Object arg3) {
        if(this.items.contains(arg3, false)) {
            this.selection.set(arg3);
        }
        else {
            if((this.selection.getRequired()) && this.items.size > 0) {
                this.selection.set(this.items.first());
                return;
            }

            this.selection.clear();
        }
    }

    public void setSelectedIndex(int index) {
        int v1 = -1;
        if(index >= v1 && index < this.items.size) {
            if(index == v1) {
                this.selection.clear();
            }
            else {
                this.selection.set(this.items.get(index));
            }

            return;
        }

        throw new IllegalArgumentException("index must be >= -1 and < " + this.items.size + ": " + index);
    }

    public void setStyle(ListStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.invalidateHierarchy();
    }

    void touchDown(float y) {
        if(this.items.size != 0) {
            float v0 = this.getHeight();
            if(this.style.background != null) {
                v0 -= this.style.background.getTopHeight() + this.style.background.getBottomHeight();
                y -= this.style.background.getBottomHeight();
            }

            this.selection.choose(this.items.get(Math.min(this.items.size - 1, Math.max(0, ((int)((v0 - y) / this.itemHeight))))));
        }
    }
}

