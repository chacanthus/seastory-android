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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class SplitPane extends WidgetGroup {
    public class SplitPaneStyle {
        public SplitPaneStyle() {
            super();
        }

        public SplitPaneStyle(SplitPaneStyle style) {
            super();
            this.handle = style.handle;
        }

        public SplitPaneStyle(Drawable handle) {
            super();
            this.handle = handle;
        }
    }

    private Rectangle firstScissors;
    private Actor firstWidget;
    private Rectangle firstWidgetBounds;
    Rectangle handleBounds;
    Vector2 handlePosition;
    Vector2 lastPoint;
    float maxAmount;
    float minAmount;
    private float oldSplitAmount;
    private Rectangle secondScissors;
    private Actor secondWidget;
    private Rectangle secondWidgetBounds;
    float splitAmount;
    SplitPaneStyle style;
    boolean vertical;

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, Skin skin) {
        String v0;
        StringBuilder v1 = new StringBuilder().append("default-");
        if(vertical) {
            v0 = "vertical";
        }
        else {
            v0 = "horizontal";
        }

        this(firstWidget, secondWidget, vertical, skin, v1.append(v0).toString());
    }

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, Skin skin, String styleName) {
        this(firstWidget, secondWidget, vertical, skin.get(styleName, SplitPaneStyle.class));
    }

    public SplitPane(Actor firstWidget, Actor secondWidget, boolean vertical, SplitPaneStyle style) {
        super();
        this.splitAmount = 0.5f;
        this.maxAmount = 1f;
        this.firstWidgetBounds = new Rectangle();
        this.secondWidgetBounds = new Rectangle();
        this.handleBounds = new Rectangle();
        this.firstScissors = new Rectangle();
        this.secondScissors = new Rectangle();
        this.lastPoint = new Vector2();
        this.handlePosition = new Vector2();
        this.firstWidget = firstWidget;
        this.secondWidget = secondWidget;
        this.vertical = vertical;
        this.setStyle(style);
        this.setFirstWidget(firstWidget);
        this.setSecondWidget(secondWidget);
        this.setSize(this.getPrefWidth(), this.getPrefHeight());
        this.initialize();
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    private void calculateHorizBoundsAndPositions() {
        Drawable v1 = this.style.handle;
        float v3 = this.getHeight();
        float v0 = this.getWidth() - v1.getMinWidth();
        float v4 = ((float)(((int)(this.splitAmount * v0))));
        float v2 = v1.getMinWidth();
        this.firstWidgetBounds.set(0f, 0f, v4, v3);
        this.secondWidgetBounds.set(v4 + v2, 0f, v0 - v4, v3);
        this.handleBounds.set(v4, 0f, v2, v3);
    }

    private void calculateVertBoundsAndPositions() {
        Drawable v2 = this.style.handle;
        float v6 = this.getWidth();
        float v4 = this.getHeight();
        float v0 = v4 - v2.getMinHeight();
        float v5 = ((float)(((int)(this.splitAmount * v0))));
        float v1 = v0 - v5;
        float v3 = v2.getMinHeight();
        this.firstWidgetBounds.set(0f, v4 - v5, v6, v5);
        this.secondWidgetBounds.set(0f, 0f, v6, v1);
        this.handleBounds.set(0f, v1, v6, v3);
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        Color v6 = this.getColor();
        Drawable v0 = this.style.handle;
        this.applyTransform(batch, this.computeTransform());
        batch.getTransformMatrix();
        if(this.firstWidget != null) {
            this.getStage().calculateScissors(this.firstWidgetBounds, this.firstScissors);
            if(ScissorStack.pushScissors(this.firstScissors)) {
                if(this.firstWidget.isVisible()) {
                    this.firstWidget.draw(batch, v6.a * parentAlpha);
                }

                batch.flush();
                ScissorStack.popScissors();
            }
        }

        if(this.secondWidget != null) {
            this.getStage().calculateScissors(this.secondWidgetBounds, this.secondScissors);
            if(ScissorStack.pushScissors(this.secondScissors)) {
                if(this.secondWidget.isVisible()) {
                    this.secondWidget.draw(batch, v6.a * parentAlpha);
                }

                batch.flush();
                ScissorStack.popScissors();
            }
        }

        batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
        v0.draw(batch, this.handleBounds.x, this.handleBounds.y, this.handleBounds.width, this.handleBounds.height);
        this.resetTransform(batch);
    }

    public float getMinHeight() {
        return 0;
    }

    public float getMinWidth() {
        return 0;
    }

    public float getPrefHeight() {
        float v2;
        float v1;
        float v0;
        if(this.firstWidget == null) {
            v0 = 0f;
        }
        else if((this.firstWidget instanceof Layout)) {
            v0 = this.firstWidget.getPrefHeight();
        }
        else {
            v0 = this.firstWidget.getHeight();
        }

        if(this.secondWidget == null) {
            v1 = 0f;
        }
        else if((this.secondWidget instanceof Layout)) {
            v1 = this.secondWidget.getPrefHeight();
        }
        else {
            v1 = this.secondWidget.getHeight();
        }

        if(!this.vertical) {
            v2 = Math.max(v0, v1);
        }
        else {
            v2 = this.style.handle.getMinHeight() + v0 + v1;
        }

        return v2;
    }

    public float getPrefWidth() {
        float v2;
        float v1;
        float v0;
        if(this.firstWidget == null) {
            v0 = 0f;
        }
        else if((this.firstWidget instanceof Layout)) {
            v0 = this.firstWidget.getPrefWidth();
        }
        else {
            v0 = this.firstWidget.getWidth();
        }

        if(this.secondWidget == null) {
            v1 = 0f;
        }
        else if((this.secondWidget instanceof Layout)) {
            v1 = this.secondWidget.getPrefWidth();
        }
        else {
            v1 = this.secondWidget.getWidth();
        }

        if(this.vertical) {
            v2 = Math.max(v0, v1);
        }
        else {
            v2 = this.style.handle.getMinWidth() + v0 + v1;
        }

        return v2;
    }

    public float getSplit() {
        return this.splitAmount;
    }

    public SplitPaneStyle getStyle() {
        return this.style;
    }

    private void initialize() {
        this.addListener(new InputListener() {
            int draggingPointer;

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0 = false;
                if(this.draggingPointer == -1 && (pointer != 0 || button == 0) && (SplitPane.this.handleBounds.contains(x, y))) {
                    this.draggingPointer = pointer;
                    SplitPane.this.lastPoint.set(x, y);
                    SplitPane.this.handlePosition.set(SplitPane.this.handleBounds.x, SplitPane.this.handleBounds.y);
                    v0 = true;
                }

                return v0;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float v2;
                if(pointer == this.draggingPointer) {
                    Drawable v5 = SplitPane.this.style.handle;
                    if(!SplitPane.this.vertical) {
                        v2 = x - SplitPane.this.lastPoint.x;
                        float v1 = SplitPane.this.getWidth() - v5.getMinWidth();
                        float v3 = SplitPane.this.handlePosition.x + v2;
                        SplitPane.this.handlePosition.x = v3;
                        SplitPane.this.splitAmount = Math.min(v1, Math.max(0f, v3)) / v1;
                        if(SplitPane.this.splitAmount < SplitPane.this.minAmount) {
                            SplitPane.this.splitAmount = SplitPane.this.minAmount;
                        }

                        if(SplitPane.this.splitAmount > SplitPane.this.maxAmount) {
                            SplitPane.this.splitAmount = SplitPane.this.maxAmount;
                        }

                        SplitPane.this.lastPoint.set(x, y);
                    }
                    else {
                        v2 = y - SplitPane.this.lastPoint.y;
                        float v0 = SplitPane.this.getHeight() - v5.getMinHeight();
                        float v4 = SplitPane.this.handlePosition.y + v2;
                        SplitPane.this.handlePosition.y = v4;
                        SplitPane.this.splitAmount = 1f - Math.min(v0, Math.max(0f, v4)) / v0;
                        if(SplitPane.this.splitAmount < SplitPane.this.minAmount) {
                            SplitPane.this.splitAmount = SplitPane.this.minAmount;
                        }

                        if(SplitPane.this.splitAmount > SplitPane.this.maxAmount) {
                            SplitPane.this.splitAmount = SplitPane.this.maxAmount;
                        }

                        SplitPane.this.lastPoint.set(x, y);
                    }

                    SplitPane.this.invalidate();
                }
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(pointer == this.draggingPointer) {
                    this.draggingPointer = -1;
                }
            }
        });
    }

    public void layout() {
        if(!this.vertical) {
            this.calculateHorizBoundsAndPositions();
        }
        else {
            this.calculateVertBoundsAndPositions();
        }

        Actor v0 = this.firstWidget;
        if(v0 != null) {
            v0.setBounds(this.firstWidgetBounds.x, this.firstWidgetBounds.y, this.firstWidgetBounds.width, this.firstWidgetBounds.height);
            if((v0 instanceof Layout)) {
                ((Layout)v0).validate();
            }
        }

        Actor v2 = this.secondWidget;
        if(v2 != null) {
            v2.setBounds(this.secondWidgetBounds.x, this.secondWidgetBounds.y, this.secondWidgetBounds.width, this.secondWidgetBounds.height);
            if((v2 instanceof Layout)) {
                ((Layout)v2).validate();
            }
        }
    }

    public boolean removeActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget(null).");
    }

    public void setFirstWidget(Actor widget) {
        if(this.firstWidget != null) {
            super.removeActor(this.firstWidget);
        }

        this.firstWidget = widget;
        if(widget != null) {
            super.addActor(widget);
        }

        this.invalidate();
    }

    public void setMaxSplitAmount(float maxAmount) {
        if(maxAmount > 1f) {
            throw new GdxRuntimeException("maxAmount has to be >= 0");
        }

        if(maxAmount <= this.minAmount) {
            throw new GdxRuntimeException("maxAmount has to be > minAmount");
        }

        this.maxAmount = maxAmount;
    }

    public void setMinSplitAmount(float minAmount) {
        if(minAmount < 0f) {
            throw new GdxRuntimeException("minAmount has to be >= 0");
        }

        if(minAmount >= this.maxAmount) {
            throw new GdxRuntimeException("minAmount has to be < maxAmount");
        }

        this.minAmount = minAmount;
    }

    public void setSecondWidget(Actor widget) {
        if(this.secondWidget != null) {
            super.removeActor(this.secondWidget);
        }

        this.secondWidget = widget;
        if(widget != null) {
            super.addActor(widget);
        }

        this.invalidate();
    }

    public void setSplitAmount(float split) {
        this.splitAmount = Math.max(Math.min(this.maxAmount, split), this.minAmount);
        this.invalidate();
    }

    public void setStyle(SplitPaneStyle style) {
        this.style = style;
        this.invalidateHierarchy();
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
}

