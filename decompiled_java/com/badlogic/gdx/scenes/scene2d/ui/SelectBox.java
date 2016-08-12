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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class SelectBox extends Widget implements Disableable {
    class SelectBoxList extends ScrollPane {
        private InputListener hideListener;
        final List list;
        int maxListCount;
        private Actor previousScrollFocus;
        private final Vector2 screenPosition;
        private final SelectBox selectBox;

        public SelectBoxList(SelectBox arg4) {
            super(null, arg4.style.scrollStyle);
            this.screenPosition = new Vector2();
            this.selectBox = arg4;
            this.setOverscroll(false, false);
            this.setFadeScrollBars(false);
            this.setScrollingDisabled(true, false);
            this.list = new List(arg4.style.listStyle);
            this.list.setTouchable(Touchable.disabled);
            this.setWidget(this.list);
            this.list.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    this.val$selectBox.selection.choose(SelectBoxList.this.list.getSelected());
                    SelectBoxList.this.hide();
                }

                public boolean mouseMoved(InputEvent event, float x, float y) {
                    SelectBoxList.this.list.setSelectedIndex(Math.min(this.val$selectBox.items.size - 1, ((int)((SelectBoxList.this.list.getHeight() - y) / SelectBoxList.this.list.getItemHeight()))));
                    return 1;
                }
            });
            this.addListener(new InputListener() {
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if(toActor == null || !SelectBoxList.this.isAscendantOf(toActor)) {
                        SelectBoxList.this.list.selection.set(this.val$selectBox.getSelected());
                    }
                }
            });
            this.hideListener = new InputListener() {
                public boolean keyDown(InputEvent event, int keycode) {
                    if(keycode == 131) {
                        SelectBoxList.this.hide();
                    }

                    return 0;
                }

                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(!SelectBoxList.this.isAscendantOf(event.getTarget())) {
                        SelectBoxList.this.list.selection.set(this.val$selectBox.getSelected());
                        SelectBoxList.this.hide();
                    }

                    return 0;
                }
            };
        }

        public void act(float delta) {
            super.act(delta);
            this.toFront();
        }

        public void draw(Batch batch, float parentAlpha) {
            this.selectBox.localToStageCoordinates(SelectBox.temp.set(0f, 0f));
            if(!SelectBox.temp.equals(this.screenPosition)) {
                this.hide();
            }

            super.draw(batch, parentAlpha);
        }

        public void hide() {
            if((this.list.isTouchable()) && (this.hasParent())) {
                this.list.setTouchable(Touchable.disabled);
                Stage v1 = this.getStage();
                if(v1 != null) {
                    v1.removeCaptureListener(this.hideListener);
                    if(this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null) {
                        this.previousScrollFocus = null;
                    }

                    Actor v0 = v1.getScrollFocus();
                    if(v0 != null && !this.isAscendantOf(v0)) {
                        goto label_26;
                    }

                    v1.setScrollFocus(this.previousScrollFocus);
                }

            label_26:
                this.clearActions();
                this.selectBox.onHide(((Actor)this));
            }
        }

        public void show(Stage stage) {
            int v2;
            if(!this.list.isTouchable()) {
                stage.removeCaptureListener(this.hideListener);
                stage.addCaptureListener(this.hideListener);
                stage.addActor(this);
                this.selectBox.localToStageCoordinates(this.screenPosition.set(0f, 0f));
                float v14 = this.list.getItemHeight();
                if(this.maxListCount <= 0) {
                    v2 = this.selectBox.items.size;
                }
                else {
                    v2 = Math.min(this.maxListCount, this.selectBox.items.size);
                }

                float v11 = v14 * (((float)v2));
                Drawable v16 = this.getStyle().background;
                if(v16 != null) {
                    v11 += v16.getTopHeight() + v16.getBottomHeight();
                }

                Drawable v15 = this.list.getStyle().background;
                if(v15 != null) {
                    v11 += v15.getTopHeight() + v15.getBottomHeight();
                }

                float v13 = this.screenPosition.y;
                float v12 = stage.getCamera().viewportHeight - this.screenPosition.y - this.selectBox.getHeight();
                boolean v10 = true;
                if(v11 > v13) {
                    if(v12 > v13) {
                        v10 = false;
                        v11 = Math.min(v11, v12);
                    }
                    else {
                        v11 = v13;
                    }
                }

                if(v10) {
                    this.setY(this.screenPosition.y - v11);
                }
                else {
                    this.setY(this.screenPosition.y + this.selectBox.getHeight());
                }

                this.setX(this.screenPosition.x);
                this.setHeight(v11);
                this.validate();
                float v17 = Math.max(this.getPrefWidth(), this.selectBox.getWidth());
                if(this.getPrefHeight() > v11) {
                    v17 += this.getScrollBarWidth();
                }

                this.setWidth(v17);
                this.validate();
                this.scrollTo(0f, this.list.getHeight() - (((float)this.selectBox.getSelectedIndex())) * v14 - v14 / 2f, 0f, 0f, true, true);
                this.updateVisualScroll();
                this.previousScrollFocus = null;
                Actor v9 = stage.getScrollFocus();
                if(v9 != null && !v9.isDescendantOf(this)) {
                    this.previousScrollFocus = v9;
                }

                stage.setScrollFocus(this);
                this.list.selection.set(this.selectBox.getSelected());
                this.list.setTouchable(Touchable.enabled);
                this.clearActions();
                this.selectBox.onShow(this, v10);
            }
        }
    }

    public class SelectBoxStyle {
        public SelectBoxStyle() {
            super();
            this.fontColor = new Color(1f, 1f, 1f, 1f);
        }

        public SelectBoxStyle(BitmapFont font, Color fontColor, Drawable background, ScrollPaneStyle scrollStyle, ListStyle listStyle) {
            super();
            this.fontColor = new Color(1f, 1f, 1f, 1f);
            this.font = font;
            this.fontColor.set(fontColor);
            this.background = background;
            this.scrollStyle = scrollStyle;
            this.listStyle = listStyle;
        }

        public SelectBoxStyle(SelectBoxStyle style) {
            super();
            this.fontColor = new Color(1f, 1f, 1f, 1f);
            this.font = style.font;
            this.fontColor.set(style.fontColor);
            if(style.disabledFontColor != null) {
                this.disabledFontColor = new Color(style.disabledFontColor);
            }

            this.background = style.background;
            this.backgroundOver = style.backgroundOver;
            this.backgroundOpen = style.backgroundOpen;
            this.backgroundDisabled = style.backgroundDisabled;
            this.scrollStyle = new ScrollPaneStyle(style.scrollStyle);
            this.listStyle = new ListStyle(style.listStyle);
        }
    }

    private ClickListener clickListener;
    boolean disabled;
    final Array items;
    private GlyphLayout layout;
    private float prefHeight;
    private float prefWidth;
    SelectBoxList selectBoxList;
    final ArraySelection selection;
    SelectBoxStyle style;
    static final Vector2 temp;

    static  {
        SelectBox.temp = new Vector2();
    }

    public SelectBox(SelectBoxStyle style) {
        super();
        this.items = new Array();
        this.selection = new ArraySelection(this.items);
        this.layout = new GlyphLayout();
        this.setStyle(style);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.selection.setActor(((Actor)this));
        this.selection.setRequired(true);
        this.selectBoxList = new SelectBoxList(this);
        com.badlogic.gdx.scenes.scene2d.ui.SelectBox$1 v0 = new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0 = false;
                if((pointer != 0 || button == 0) && !SelectBox.this.disabled) {
                    if(SelectBox.this.selectBoxList.hasParent()) {
                        SelectBox.this.hideList();
                    }
                    else {
                        SelectBox.this.showList();
                    }

                    v0 = true;
                }

                return v0;
            }
        };
        this.clickListener = ((ClickListener)v0);
        this.addListener(((EventListener)v0));
    }

    public SelectBox(Skin skin) {
        this(skin.get(SelectBoxStyle.class));
    }

    public SelectBox(Skin skin, String styleName) {
        this(skin.get(styleName, SelectBoxStyle.class));
    }

    public void clearItems() {
        if(this.items.size != 0) {
            this.items.clear();
            this.selection.clear();
            this.invalidateHierarchy();
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        Color v18;
        Drawable v1;
        this.validate();
        if(!this.disabled || this.style.backgroundDisabled == null) {
            if((this.selectBoxList.hasParent()) && this.style.backgroundOpen != null) {
                v1 = this.style.backgroundOpen;
                goto label_11;
            }

            if((this.clickListener.isOver()) && this.style.backgroundOver != null) {
                v1 = this.style.backgroundOver;
                goto label_11;
            }

            if(this.style.background == null) {
                goto label_129;
            }

            v1 = this.style.background;
            goto label_11;
        label_129:
            v1 = null;
        }
        else {
            v1 = this.style.backgroundDisabled;
        }

    label_11:
        BitmapFont v8 = this.style.font;
        if(!this.disabled || this.style.disabledFontColor == null) {
            v18 = this.style.fontColor;
        }
        else {
            v18 = this.style.disabledFontColor;
        }

        Color v17 = this.getColor();
        float v3 = this.getX();
        float v4 = this.getY();
        float v5 = this.getWidth();
        float v6 = this.getHeight();
        batch.setColor(v17.r, v17.g, v17.b, v17.a * parentAlpha);
        if(v1 != null) {
            v1.draw(batch, v3, v4, v5, v6);
        }

        Object v19 = this.selection.first();
        if(v19 != null) {
            String v9 = v19.toString();
            if(v1 != null) {
                v5 -= v1.getLeftWidth() + v1.getRightWidth();
                v6 -= v1.getBottomHeight() + v1.getTopHeight();
                v3 += v1.getLeftWidth();
                v4 += ((float)(((int)(v6 / 2f + v1.getBottomHeight() + v8.getData().capHeight / 2f))));
            }
            else {
                v4 += ((float)(((int)(v6 / 2f + v8.getData().capHeight / 2f))));
            }

            v8.setColor(v18.r, v18.g, v18.b, v18.a * parentAlpha);
            this.layout.setText(v8, ((CharSequence)v9), 0, v9.length(), Color.WHITE, v5, 8, false, "...");
            v8.draw(batch, this.layout, v3, v4);
        }
    }

    public Array getItems() {
        return this.items;
    }

    public List getList() {
        return this.selectBoxList.list;
    }

    public int getMaxListCount() {
        return this.selectBoxList.maxListCount;
    }

    public float getPrefHeight() {
        this.validate();
        return this.prefHeight;
    }

    public float getPrefWidth() {
        this.validate();
        return this.prefWidth;
    }

    public ScrollPane getScrollPane() {
        return this.selectBoxList;
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

    public SelectBoxStyle getStyle() {
        return this.style;
    }

    public void hideList() {
        this.selectBoxList.hide();
    }

    public void layout() {
        float v8;
        float v11 = 2f;
        float v9 = 0f;
        Drawable v0 = this.style.background;
        BitmapFont v1 = this.style.font;
        if(v0 != null) {
            this.prefHeight = Math.max(v0.getTopHeight() + v0.getBottomHeight() + v1.getCapHeight() - v1.getDescent() * v11, v0.getMinHeight());
        }
        else {
            this.prefHeight = v1.getCapHeight() - v1.getDescent() * v11;
        }

        float v6 = 0f;
        Pool v4 = Pools.get(GlyphLayout.class);
        Object v3 = v4.obtain();
        int v2;
        for(v2 = 0; v2 < this.items.size; ++v2) {
            ((GlyphLayout)v3).setText(v1, this.items.get(v2).toString());
            v6 = Math.max(((GlyphLayout)v3).width, v6);
        }

        v4.free(v3);
        this.prefWidth = v6;
        if(v0 != null) {
            this.prefWidth += v0.getLeftWidth() + v0.getRightWidth();
        }

        ListStyle v5 = this.style.listStyle;
        ScrollPaneStyle v7 = this.style.scrollStyle;
        float v10 = this.prefWidth;
        if(v7.background == null) {
            v8 = 0f;
        }
        else {
            v8 = v7.background.getLeftWidth() + v7.background.getRightWidth();
        }

        v11 = v5.selection.getRightWidth() + (v8 + v6 + v5.selection.getLeftWidth());
        if(this.style.scrollStyle.vScroll != null) {
            v8 = this.style.scrollStyle.vScroll.getMinWidth();
        }
        else {
            v8 = 0f;
        }

        if(this.style.scrollStyle.vScrollKnob != null) {
            v9 = this.style.scrollStyle.vScrollKnob.getMinWidth();
        }

        this.prefWidth = Math.max(v10, Math.max(v8, v9) + v11);
    }

    protected void onHide(Actor selectBoxList) {
        selectBoxList.getColor().a = 1f;
        selectBoxList.addAction(Actions.sequence(Actions.fadeOut(0.15f, Interpolation.fade), Actions.removeActor()));
    }

    protected void onShow(Actor selectBoxList, boolean below) {
        selectBoxList.getColor().a = 0f;
        selectBoxList.addAction(Actions.fadeIn(0.3f, Interpolation.fade));
    }

    public void setDisabled(boolean disabled) {
        if((disabled) && !this.disabled) {
            this.hideList();
        }

        this.disabled = disabled;
    }

    public void setItems(Array arg4) {
        if(arg4 == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }

        float v0 = this.getPrefWidth();
        this.items.clear();
        this.items.addAll(arg4);
        this.selection.validate();
        this.selectBoxList.list.setItems(this.items);
        this.invalidate();
        if(v0 != this.getPrefWidth()) {
            this.invalidateHierarchy();
        }
    }

    public void setItems(Object[] arg4) {
        if(arg4 == null) {
            throw new IllegalArgumentException("newItems cannot be null.");
        }

        float v0 = this.getPrefWidth();
        this.items.clear();
        this.items.addAll(arg4);
        this.selection.validate();
        this.selectBoxList.list.setItems(this.items);
        this.invalidate();
        if(v0 != this.getPrefWidth()) {
            this.invalidateHierarchy();
        }
    }

    public void setMaxListCount(int maxListCount) {
        this.selectBoxList.maxListCount = maxListCount;
    }

    public void setSelected(Object arg3) {
        if(this.items.contains(arg3, false)) {
            this.selection.set(arg3);
        }
        else if(this.items.size > 0) {
            this.selection.set(this.items.first());
        }
        else {
            this.selection.clear();
        }
    }

    public void setSelectedIndex(int index) {
        this.selection.set(this.items.get(index));
    }

    protected void setStage(Stage stage) {
        if(stage == null) {
            this.selectBoxList.hide();
        }

        super.setStage(stage);
    }

    public void setStyle(SelectBoxStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.invalidateHierarchy();
    }

    public void showList() {
        if(this.items.size != 0) {
            this.selectBoxList.show(this.getStage());
        }
    }
}

