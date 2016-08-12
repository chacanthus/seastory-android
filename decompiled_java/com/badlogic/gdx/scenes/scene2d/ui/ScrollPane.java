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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent$Type;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class ScrollPane extends WidgetGroup {
    public class ScrollPaneStyle {
        public Drawable background;
        public Drawable corner;
        public Drawable hScroll;
        public Drawable hScrollKnob;
        public Drawable vScroll;
        public Drawable vScrollKnob;

        public ScrollPaneStyle() {
            super();
        }

        public ScrollPaneStyle(ScrollPaneStyle style) {
            super();
            this.background = style.background;
            this.hScroll = style.hScroll;
            this.hScrollKnob = style.hScrollKnob;
            this.vScroll = style.vScroll;
            this.vScrollKnob = style.vScrollKnob;
        }

        public ScrollPaneStyle(Drawable background, Drawable hScroll, Drawable hScrollKnob, Drawable vScroll, Drawable vScrollKnob) {
            super();
            this.background = background;
            this.hScroll = hScroll;
            this.hScrollKnob = hScrollKnob;
            this.vScroll = vScroll;
            this.vScrollKnob = vScrollKnob;
        }
    }

    float amountX;
    float amountY;
    float areaHeight;
    float areaWidth;
    boolean cancelTouchFocus;
    private boolean clamp;
    private boolean disableX;
    private boolean disableY;
    int draggingPointer;
    float fadeAlpha;
    float fadeAlphaSeconds;
    float fadeDelay;
    float fadeDelaySeconds;
    private boolean fadeScrollBars;
    boolean flickScroll;
    private ActorGestureListener flickScrollListener;
    float flingTime;
    float flingTimer;
    private boolean forceScrollX;
    private boolean forceScrollY;
    final Rectangle hKnobBounds;
    final Rectangle hScrollBounds;
    boolean hScrollOnBottom;
    final Vector2 lastPoint;
    float maxX;
    float maxY;
    private float overscrollDistance;
    private float overscrollSpeedMax;
    private float overscrollSpeedMin;
    private boolean overscrollX;
    private boolean overscrollY;
    private final Rectangle scissorBounds;
    boolean scrollX;
    boolean scrollY;
    private boolean scrollbarsOnTop;
    private boolean smoothScrolling;
    private ScrollPaneStyle style;
    boolean touchScrollH;
    boolean touchScrollV;
    final Rectangle vKnobBounds;
    final Rectangle vScrollBounds;
    boolean vScrollOnRight;
    private boolean variableSizeKnobs;
    float velocityX;
    float velocityY;
    float visualAmountX;
    float visualAmountY;
    private Actor widget;
    private final Rectangle widgetAreaBounds;
    private final Rectangle widgetCullingArea;

    public ScrollPane(Actor widget) {
        this(widget, new ScrollPaneStyle());
    }

    public ScrollPane(Actor widget, ScrollPaneStyle style) {
        float v3 = 150f;
        super();
        this.hScrollBounds = new Rectangle();
        this.vScrollBounds = new Rectangle();
        this.hKnobBounds = new Rectangle();
        this.vKnobBounds = new Rectangle();
        this.widgetAreaBounds = new Rectangle();
        this.widgetCullingArea = new Rectangle();
        this.scissorBounds = new Rectangle();
        this.vScrollOnRight = true;
        this.hScrollOnBottom = true;
        this.lastPoint = new Vector2();
        this.fadeScrollBars = true;
        this.smoothScrolling = true;
        this.fadeAlphaSeconds = 1f;
        this.fadeDelaySeconds = 1f;
        this.cancelTouchFocus = true;
        this.flickScroll = true;
        this.overscrollX = true;
        this.overscrollY = true;
        this.flingTime = 1f;
        this.overscrollDistance = 50f;
        this.overscrollSpeedMin = 30f;
        this.overscrollSpeedMax = 200f;
        this.clamp = true;
        this.variableSizeKnobs = true;
        this.draggingPointer = -1;
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.setWidget(widget);
        this.setSize(v3, v3);
        this.addCaptureListener(new InputListener() {
            private float handlePosition;

            public boolean mouseMoved(InputEvent event, float x, float y) {
                if(!ScrollPane.this.flickScroll) {
                    ScrollPane.this.resetFade();
                }

                return 0;
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float v4;
                float v3;
                ScrollPane v2;
                int v0 = -1;
                boolean v1 = true;
                if(ScrollPane.this.draggingPointer != v0) {
                    v1 = false;
                }
                else {
                    if(pointer == 0 && button != 0) {
                        v1 = false;
                        goto label_7;
                    }

                    ScrollPane.this.getStage().setScrollFocus(ScrollPane.this);
                    if(!ScrollPane.this.flickScroll) {
                        ScrollPane.this.resetFade();
                    }

                    if(ScrollPane.this.fadeAlpha != 0f) {
                        goto label_27;
                    }

                    v1 = false;
                    goto label_7;
                label_27:
                    if((ScrollPane.this.scrollX) && (ScrollPane.this.hScrollBounds.contains(x, y))) {
                        event.stop();
                        ScrollPane.this.resetFade();
                        if(ScrollPane.this.hKnobBounds.contains(x, y)) {
                            ScrollPane.this.lastPoint.set(x, y);
                            this.handlePosition = ScrollPane.this.hKnobBounds.x;
                            ScrollPane.this.touchScrollH = true;
                            ScrollPane.this.draggingPointer = pointer;
                            goto label_7;
                        }
                        else {
                            v2 = ScrollPane.this;
                            v3 = ScrollPane.this.amountX;
                            v4 = ScrollPane.this.areaWidth;
                            if(x >= ScrollPane.this.hKnobBounds.x) {
                                v0 = 1;
                            }

                            v2.setScrollX((((float)v0)) * v4 + v3);
                            goto label_7;
                        }
                    }

                    if((ScrollPane.this.scrollY) && (ScrollPane.this.vScrollBounds.contains(x, y))) {
                        event.stop();
                        ScrollPane.this.resetFade();
                        if(ScrollPane.this.vKnobBounds.contains(x, y)) {
                            ScrollPane.this.lastPoint.set(x, y);
                            this.handlePosition = ScrollPane.this.vKnobBounds.y;
                            ScrollPane.this.touchScrollV = true;
                            ScrollPane.this.draggingPointer = pointer;
                            goto label_7;
                        }
                        else {
                            v2 = ScrollPane.this;
                            v3 = ScrollPane.this.amountY;
                            v4 = ScrollPane.this.areaHeight;
                            if(y < ScrollPane.this.vKnobBounds.y) {
                                v0 = 1;
                            }

                            v2.setScrollY((((float)v0)) * v4 + v3);
                            goto label_7;
                        }
                    }

                    v1 = false;
                }

            label_7:
                return v1;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float v3;
                if(pointer == ScrollPane.this.draggingPointer) {
                    if(ScrollPane.this.touchScrollH) {
                        float v1 = this.handlePosition + (x - ScrollPane.this.lastPoint.x);
                        this.handlePosition = v1;
                        v1 = Math.min(ScrollPane.this.hScrollBounds.x + ScrollPane.this.hScrollBounds.width - ScrollPane.this.hKnobBounds.width, Math.max(ScrollPane.this.hScrollBounds.x, v1));
                        v3 = ScrollPane.this.hScrollBounds.width - ScrollPane.this.hKnobBounds.width;
                        if(v3 != 0f) {
                            ScrollPane.this.setScrollPercentX((v1 - ScrollPane.this.hScrollBounds.x) / v3);
                        }

                        ScrollPane.this.lastPoint.set(x, y);
                    }
                    else {
                        if(!ScrollPane.this.touchScrollV) {
                            return;
                        }

                        float v2 = this.handlePosition + (y - ScrollPane.this.lastPoint.y);
                        this.handlePosition = v2;
                        v2 = Math.min(ScrollPane.this.vScrollBounds.y + ScrollPane.this.vScrollBounds.height - ScrollPane.this.vKnobBounds.height, Math.max(ScrollPane.this.vScrollBounds.y, v2));
                        v3 = ScrollPane.this.vScrollBounds.height - ScrollPane.this.vKnobBounds.height;
                        if(v3 != 0f) {
                            ScrollPane.this.setScrollPercentY(1f - (v2 - ScrollPane.this.vScrollBounds.y) / v3);
                        }

                        ScrollPane.this.lastPoint.set(x, y);
                    }
                }
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(pointer == ScrollPane.this.draggingPointer) {
                    ScrollPane.this.cancel();
                }
            }
        });
        this.flickScrollListener = new ActorGestureListener() {
            public void fling(InputEvent event, float x, float y, int button) {
                float v2 = 150f;
                if(Math.abs(x) > v2) {
                    ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                    ScrollPane.this.velocityX = x;
                    ScrollPane.this.cancelTouchFocusedChild(event);
                }

                if(Math.abs(y) > v2) {
                    ScrollPane.this.flingTimer = ScrollPane.this.flingTime;
                    ScrollPane.this.velocityY = -y;
                    ScrollPane.this.cancelTouchFocusedChild(event);
                }
            }

            public boolean handle(Event event) {
                boolean v0;
                if(super.handle(event)) {
                    if(((InputEvent)event).getType() == Type.touchDown) {
                        ScrollPane.this.flingTimer = 0f;
                    }

                    v0 = true;
                }
                else {
                    v0 = false;
                }

                return v0;
            }

            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                ScrollPane.this.resetFade();
                ScrollPane.this.amountX -= deltaX;
                ScrollPane.this.amountY += deltaY;
                ScrollPane.this.clamp();
                ScrollPane.this.cancelTouchFocusedChild(event);
            }
        };
        this.addListener(this.flickScrollListener);
        this.addListener(new InputListener() {
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                boolean v0;
                ScrollPane.this.resetFade();
                if(ScrollPane.this.scrollY) {
                    ScrollPane.this.setScrollY(ScrollPane.this.amountY + ScrollPane.this.getMouseWheelY() * (((float)amount)));
                    goto label_14;
                }
                else if(ScrollPane.this.scrollX) {
                    ScrollPane.this.setScrollX(ScrollPane.this.amountX + ScrollPane.this.getMouseWheelX() * (((float)amount)));
                label_14:
                    v0 = true;
                }
                else {
                    v0 = false;
                }

                return v0;
            }
        });
    }

    public ScrollPane(Actor widget, Skin skin) {
        this(widget, skin.get(ScrollPaneStyle.class));
    }

    public ScrollPane(Actor widget, Skin skin, String styleName) {
        this(widget, skin.get(styleName, ScrollPaneStyle.class));
    }

    public void act(float delta) {
        float v11 = 200f;
        float v10 = 7f;
        super.act(delta);
        boolean v2 = this.flickScrollListener.getGestureDetector().isPanning();
        int v1 = 0;
        if(this.fadeAlpha > 0f && (this.fadeScrollBars) && !v2 && !this.touchScrollH && !this.touchScrollV) {
            this.fadeDelay -= delta;
            if(this.fadeDelay <= 0f) {
                this.fadeAlpha = Math.max(0f, this.fadeAlpha - delta);
            }

            v1 = 1;
        }

        if(this.flingTimer > 0f) {
            this.resetFade();
            float v0 = this.flingTimer / this.flingTime;
            this.amountX -= this.velocityX * v0 * delta;
            this.amountY -= this.velocityY * v0 * delta;
            this.clamp();
            if(this.amountX == -this.overscrollDistance) {
                this.velocityX = 0f;
            }

            if(this.amountX >= this.maxX + this.overscrollDistance) {
                this.velocityX = 0f;
            }

            if(this.amountY == -this.overscrollDistance) {
                this.velocityY = 0f;
            }

            if(this.amountY >= this.maxY + this.overscrollDistance) {
                this.velocityY = 0f;
            }

            this.flingTimer -= delta;
            if(this.flingTimer <= 0f) {
                this.velocityX = 0f;
                this.velocityY = 0f;
            }

            v1 = 1;
        }

        if(!this.smoothScrolling || this.flingTimer > 0f || (this.touchScrollH) || (this.touchScrollV) || (v2)) {
            if(this.visualAmountX != this.amountX) {
                this.visualScrollX(this.amountX);
            }

            if(this.visualAmountY == this.amountY) {
                goto label_123;
            }

            this.visualScrollY(this.amountY);
        }
        else {
            if(this.visualAmountX != this.amountX) {
                if(this.visualAmountX < this.amountX) {
                    this.visualScrollX(Math.min(this.amountX, this.visualAmountX + Math.max(v11 * delta, (this.amountX - this.visualAmountX) * v10 * delta)));
                }
                else {
                    this.visualScrollX(Math.max(this.amountX, this.visualAmountX - Math.max(v11 * delta, (this.visualAmountX - this.amountX) * v10 * delta)));
                }

                v1 = 1;
            }

            if(this.visualAmountY == this.amountY) {
                goto label_123;
            }

            if(this.visualAmountY < this.amountY) {
                this.visualScrollY(Math.min(this.amountY, this.visualAmountY + Math.max(v11 * delta, (this.amountY - this.visualAmountY) * v10 * delta)));
            }
            else {
                this.visualScrollY(Math.max(this.amountY, this.visualAmountY - Math.max(v11 * delta, (this.visualAmountY - this.amountY) * v10 * delta)));
            }

            v1 = 1;
        }

    label_123:
        if(!v2) {
            if((this.overscrollX) && (this.scrollX)) {
                if(this.amountX < 0f) {
                    this.resetFade();
                    this.amountX += (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -this.amountX / this.overscrollDistance) * delta;
                    if(this.amountX > 0f) {
                        this.scrollX(0f);
                    }

                    v1 = 1;
                }
                else {
                    if(this.amountX <= this.maxX) {
                        goto label_149;
                    }

                    this.resetFade();
                    this.amountX -= (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -(this.maxX - this.amountX) / this.overscrollDistance) * delta;
                    if(this.amountX < this.maxX) {
                        this.scrollX(this.maxX);
                    }

                    v1 = 1;
                }
            }

        label_149:
            if(!this.overscrollY) {
                goto label_174;
            }

            if(!this.scrollY) {
                goto label_174;
            }

            if(this.amountY >= 0f) {
                goto label_246;
            }

            this.resetFade();
            this.amountY += (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -this.amountY / this.overscrollDistance) * delta;
            if(this.amountY > 0f) {
                this.scrollY(0f);
            }

            v1 = 1;
            goto label_174;
        label_246:
            if(this.amountY <= this.maxY) {
                goto label_174;
            }

            this.resetFade();
            this.amountY -= (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -(this.maxY - this.amountY) / this.overscrollDistance) * delta;
            if(this.amountY < this.maxY) {
                this.scrollY(this.maxY);
            }

            v1 = 1;
        }

    label_174:
        if(v1 != 0) {
            Stage v3 = this.getStage();
            if(v3 != null && (v3.getActionsRequestRendering())) {
                Gdx.graphics.requestRendering();
            }
        }
    }

    public void addActor(Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorAt(int index, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }

    public void cancel() {
        this.draggingPointer = -1;
        this.touchScrollH = false;
        this.touchScrollV = false;
        this.flickScrollListener.getGestureDetector().cancel();
    }

    void cancelTouchFocusedChild(InputEvent event) {
        if(this.cancelTouchFocus) {
            Stage v0 = this.getStage();
            if(v0 != null) {
                v0.cancelTouchFocusExcept(this.flickScrollListener, ((Actor)this));
            }
        }
    }

    void clamp() {
        float v0;
        if(this.clamp) {
            if(this.overscrollX) {
                v0 = MathUtils.clamp(this.amountX, -this.overscrollDistance, this.maxX + this.overscrollDistance);
            }
            else {
                v0 = MathUtils.clamp(this.amountX, 0f, this.maxX);
            }

            this.scrollX(v0);
            if(this.overscrollY) {
                v0 = MathUtils.clamp(this.amountY, -this.overscrollDistance, this.maxY + this.overscrollDistance);
            }
            else {
                v0 = MathUtils.clamp(this.amountY, 0f, this.maxY);
            }

            this.scrollY(v0);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        if(this.widget != null) {
            this.validate();
            this.applyTransform(batch, this.computeTransform());
            if(this.scrollX) {
                this.hKnobBounds.x = this.hScrollBounds.x + (((float)(((int)((this.hScrollBounds.width - this.hKnobBounds.width) * this.getVisualScrollPercentX())))));
            }

            if(this.scrollY) {
                this.vKnobBounds.y = this.vScrollBounds.y + (((float)(((int)((this.vScrollBounds.height - this.vKnobBounds.height) * (1f - this.getVisualScrollPercentY()))))));
            }

            float v10 = this.widgetAreaBounds.y;
            if(!this.scrollY) {
                v10 -= ((float)(((int)this.maxY)));
            }
            else {
                v10 -= ((float)(((int)(this.maxY - this.visualAmountY))));
            }

            float v9 = this.widgetAreaBounds.x;
            if(this.scrollX) {
                v9 -= ((float)(((int)this.visualAmountX)));
            }

            if(!this.fadeScrollBars && (this.scrollbarsOnTop)) {
                if((this.scrollX) && (this.hScrollOnBottom)) {
                    float v7 = 0f;
                    if(this.style.hScrollKnob != null) {
                        v7 = this.style.hScrollKnob.getMinHeight();
                    }

                    if(this.style.hScroll != null) {
                        v7 = Math.max(v7, this.style.hScroll.getMinHeight());
                    }

                    v10 += v7;
                }

                if(!this.scrollY) {
                    goto label_99;
                }

                if(this.vScrollOnRight) {
                    goto label_99;
                }

                float v8 = 0f;
                if(this.style.hScrollKnob != null) {
                    v8 = this.style.hScrollKnob.getMinWidth();
                }

                if(this.style.hScroll != null) {
                    v8 = Math.max(v8, this.style.hScroll.getMinWidth());
                }

                v9 += v8;
            }

        label_99:
            this.widget.setPosition(v9, v10);
            if((this.widget instanceof Cullable)) {
                this.widgetCullingArea.x = -this.widget.getX() + this.widgetAreaBounds.x;
                this.widgetCullingArea.y = -this.widget.getY() + this.widgetAreaBounds.y;
                this.widgetCullingArea.width = this.widgetAreaBounds.width;
                this.widgetCullingArea.height = this.widgetAreaBounds.height;
                this.widget.setCullingArea(this.widgetCullingArea);
            }

            Color v6 = this.getColor();
            batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
            if(this.style.background != null) {
                this.style.background.draw(batch, 0f, 0f, this.getWidth(), this.getHeight());
                batch.flush();
            }

            this.getStage().calculateScissors(this.widgetAreaBounds, this.scissorBounds);
            if(ScissorStack.pushScissors(this.scissorBounds)) {
                this.drawChildren(batch, parentAlpha);
                batch.flush();
                ScissorStack.popScissors();
            }

            batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha * Interpolation.fade.apply(this.fadeAlpha / this.fadeAlphaSeconds));
            if((this.scrollX) && (this.scrollY) && this.style.corner != null) {
                this.style.corner.draw(batch, this.hScrollBounds.width + this.hScrollBounds.x, this.hScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.y);
            }

            if(this.scrollX) {
                if(this.style.hScroll != null) {
                    this.style.hScroll.draw(batch, this.hScrollBounds.x, this.hScrollBounds.y, this.hScrollBounds.width, this.hScrollBounds.height);
                }

                if(this.style.hScrollKnob == null) {
                    goto label_225;
                }

                this.style.hScrollKnob.draw(batch, this.hKnobBounds.x, this.hKnobBounds.y, this.hKnobBounds.width, this.hKnobBounds.height);
            }

        label_225:
            if(this.scrollY) {
                if(this.style.vScroll != null) {
                    this.style.vScroll.draw(batch, this.vScrollBounds.x, this.vScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.height);
                }

                if(this.style.vScrollKnob == null) {
                    goto label_257;
                }

                this.style.vScrollKnob.draw(batch, this.vKnobBounds.x, this.vKnobBounds.y, this.vKnobBounds.width, this.vKnobBounds.height);
            }

        label_257:
            this.resetTransform(batch);
        }
    }

    public void drawDebug(ShapeRenderer shapes) {
        shapes.flush();
        this.applyTransform(shapes, this.computeTransform());
        if(ScissorStack.pushScissors(this.scissorBounds)) {
            this.drawDebugChildren(shapes);
            ScissorStack.popScissors();
        }

        this.resetTransform(shapes);
    }

    public void fling(float flingTime, float velocityX, float velocityY) {
        this.flingTimer = flingTime;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getMaxX() {
        return this.maxX;
    }

    public float getMaxY() {
        return this.maxY;
    }

    public float getMinHeight() {
        return 0;
    }

    public float getMinWidth() {
        return 0;
    }

    protected float getMouseWheelX() {
        return Math.max(this.areaWidth * 0.9f, this.maxX * 0.1f) / 4f;
    }

    protected float getMouseWheelY() {
        return Math.max(this.areaHeight * 0.9f, this.maxY * 0.1f) / 4f;
    }

    public float getPrefHeight() {
        float v0;
        if((this.widget instanceof Layout)) {
            v0 = this.widget.getPrefHeight();
            if(this.style.background != null) {
                v0 += this.style.background.getTopHeight() + this.style.background.getBottomHeight();
            }

            if(!this.forceScrollX) {
                goto label_33;
            }

            float v1 = 0f;
            if(this.style.hScrollKnob != null) {
                v1 = this.style.hScrollKnob.getMinHeight();
            }

            if(this.style.hScroll != null) {
                v1 = Math.max(v1, this.style.hScroll.getMinHeight());
            }

            v0 += v1;
        }
        else {
            v0 = 150f;
        }

    label_33:
        return v0;
    }

    public float getPrefWidth() {
        float v1;
        if((this.widget instanceof Layout)) {
            v1 = this.widget.getPrefWidth();
            if(this.style.background != null) {
                v1 += this.style.background.getLeftWidth() + this.style.background.getRightWidth();
            }

            if(!this.forceScrollY) {
                goto label_33;
            }

            float v0 = 0f;
            if(this.style.vScrollKnob != null) {
                v0 = this.style.vScrollKnob.getMinWidth();
            }

            if(this.style.vScroll != null) {
                v0 = Math.max(v0, this.style.vScroll.getMinWidth());
            }

            v1 += v0;
        }
        else {
            v1 = 150f;
        }

    label_33:
        return v1;
    }

    public float getScrollBarHeight() {
        float v0;
        if(!this.scrollX) {
            v0 = 0f;
        }
        else {
            v0 = 0f;
            if(this.style.hScrollKnob != null) {
                v0 = this.style.hScrollKnob.getMinHeight();
            }

            if(this.style.hScroll == null) {
                goto label_3;
            }

            v0 = Math.max(v0, this.style.hScroll.getMinHeight());
        }

    label_3:
        return v0;
    }

    public float getScrollBarWidth() {
        float v0;
        if(!this.scrollY) {
            v0 = 0f;
        }
        else {
            v0 = 0f;
            if(this.style.vScrollKnob != null) {
                v0 = this.style.vScrollKnob.getMinWidth();
            }

            if(this.style.vScroll == null) {
                goto label_3;
            }

            v0 = Math.max(v0, this.style.vScroll.getMinWidth());
        }

    label_3:
        return v0;
    }

    public float getScrollHeight() {
        return this.areaHeight;
    }

    public float getScrollPercentX() {
        return MathUtils.clamp(this.amountX / this.maxX, 0f, 1f);
    }

    public float getScrollPercentY() {
        return MathUtils.clamp(this.amountY / this.maxY, 0f, 1f);
    }

    public float getScrollWidth() {
        return this.areaWidth;
    }

    public float getScrollX() {
        return this.amountX;
    }

    public float getScrollY() {
        return this.amountY;
    }

    public ScrollPaneStyle getStyle() {
        return this.style;
    }

    public boolean getVariableSizeKnobs() {
        return this.variableSizeKnobs;
    }

    public float getVelocityX() {
        float v1 = 0f;
        if(this.flingTimer > 0f) {
            float v0 = this.flingTimer / this.flingTime;
            v0 *= v0 * v0;
            v1 = this.velocityX * v0 * v0 * v0;
        }

        return v1;
    }

    public float getVelocityY() {
        return this.velocityY;
    }

    public float getVisualScrollPercentX() {
        return MathUtils.clamp(this.visualAmountX / this.maxX, 0f, 1f);
    }

    public float getVisualScrollPercentY() {
        return MathUtils.clamp(this.visualAmountY / this.maxY, 0f, 1f);
    }

    public float getVisualScrollX() {
        float v0;
        if(!this.scrollX) {
            v0 = 0f;
        }
        else {
            v0 = this.visualAmountX;
        }

        return v0;
    }

    public float getVisualScrollY() {
        float v0;
        if(!this.scrollY) {
            v0 = 0f;
        }
        else {
            v0 = this.visualAmountY;
        }

        return v0;
    }

    public Actor getWidget() {
        return this.widget;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if(x < 0f || x >= this.getWidth() || y < 0f || y >= this.getHeight()) {
            this = null;
        }
        else {
            if((this.scrollX) && (this.hScrollBounds.contains(x, y))) {
                goto label_8;
            }

            if((this.scrollY) && (this.vScrollBounds.contains(x, y))) {
                goto label_8;
            }

            Actor v2_1 = super.hit(x, y, touchable);
        }

    label_8:
        return this;
    }

    public boolean isBottomEdge() {
        boolean v0;
        if(!this.scrollY || this.amountY >= this.maxY) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isDragging() {
        boolean v0;
        if(this.draggingPointer != -1) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isFlinging() {
        boolean v0;
        if(this.flingTimer > 0f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isForceScrollX() {
        return this.forceScrollX;
    }

    public boolean isForceScrollY() {
        return this.forceScrollY;
    }

    public boolean isLeftEdge() {
        boolean v0;
        if(!this.scrollX || this.amountX <= 0f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isPanning() {
        return this.flickScrollListener.getGestureDetector().isPanning();
    }

    public boolean isRightEdge() {
        boolean v0;
        if(!this.scrollX || this.amountX >= this.maxX) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isScrollX() {
        return this.scrollX;
    }

    public boolean isScrollY() {
        return this.scrollY;
    }

    public boolean isTopEdge() {
        boolean v0;
        if(!this.scrollY || this.amountY <= 0f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void layout() {
        float v18;
        float v9;
        float v8;
        float v11;
        boolean v22;
        float v19;
        float v20;
        Drawable v3 = this.style.background;
        Drawable v12 = this.style.hScrollKnob;
        Drawable v17 = this.style.vScrollKnob;
        float v5 = 0f;
        float v6 = 0f;
        float v7 = 0f;
        float v4 = 0f;
        if(v3 != null) {
            v5 = v3.getLeftWidth();
            v6 = v3.getRightWidth();
            v7 = v3.getTopHeight();
            v4 = v3.getBottomHeight();
        }

        float v21 = this.getWidth();
        float v13 = this.getHeight();
        float v15 = 0f;
        if(v12 != null) {
            v15 = v12.getMinHeight();
        }

        if(this.style.hScroll != null) {
            v15 = Math.max(v15, this.style.hScroll.getMinHeight());
        }

        float v16 = 0f;
        if(v17 != null) {
            v16 = v17.getMinWidth();
        }

        if(this.style.vScroll != null) {
            v16 = Math.max(v16, this.style.vScroll.getMinWidth());
        }

        this.areaWidth = v21 - v5 - v6;
        this.areaHeight = v13 - v7 - v4;
        if(this.widget != null) {
            if((this.widget instanceof Layout)) {
                Actor v14 = this.widget;
                v20 = ((Layout)v14).getPrefWidth();
                v19 = ((Layout)v14).getPrefHeight();
            }
            else {
                v20 = this.widget.getWidth();
                v19 = this.widget.getHeight();
            }

            if(!this.forceScrollX) {
                if(v20 > this.areaWidth && !this.disableX) {
                    goto label_104;
                }

                v22 = false;
            }
            else {
            label_104:
                v22 = true;
            }

            this.scrollX = v22;
            if(!this.forceScrollY) {
                if(v19 > this.areaHeight && !this.disableY) {
                    goto label_120;
                }

                v22 = false;
            }
            else {
            label_120:
                v22 = true;
            }

            this.scrollY = v22;
            boolean v10 = this.fadeScrollBars;
            if(!v10) {
                if(this.scrollY) {
                    this.areaWidth -= v16;
                    if(!this.scrollX && v20 > this.areaWidth && !this.disableX) {
                        this.scrollX = true;
                    }
                }

                if(!this.scrollX) {
                    goto label_188;
                }

                this.areaHeight -= v15;
                if(this.scrollY) {
                    goto label_188;
                }

                if(v19 <= this.areaHeight) {
                    goto label_188;
                }

                if(this.disableY) {
                    goto label_188;
                }

                this.scrollY = true;
                this.areaWidth -= v16;
            }

        label_188:
            this.widgetAreaBounds.set(v5, v4, this.areaWidth, this.areaHeight);
            if(v10) {
                if((this.scrollX) && (this.scrollY)) {
                    this.areaHeight -= v15;
                    this.areaWidth -= v16;
                }
            }
            else if(this.scrollbarsOnTop) {
                if(this.scrollX) {
                    this.widgetAreaBounds.height += v15;
                }

                if(!this.scrollY) {
                    goto label_224;
                }

                this.widgetAreaBounds.width += v16;
            }
            else {
                if((this.scrollX) && (this.hScrollOnBottom)) {
                    this.widgetAreaBounds.y += v15;
                }

                if(!this.scrollY) {
                    goto label_224;
                }

                if(this.vScrollOnRight) {
                    goto label_224;
                }

                this.widgetAreaBounds.x += v16;
            }

        label_224:
            if(this.disableX) {
                v20 = this.areaWidth;
            }
            else {
                v20 = Math.max(this.areaWidth, v20);
            }

            if(this.disableY) {
                v19 = this.areaHeight;
            }
            else {
                v19 = Math.max(this.areaHeight, v19);
            }

            this.maxX = v20 - this.areaWidth;
            this.maxY = v19 - this.areaHeight;
            if((v10) && (this.scrollX) && (this.scrollY)) {
                this.maxY -= v15;
                this.maxX -= v16;
            }

            this.scrollX(MathUtils.clamp(this.amountX, 0f, this.maxX));
            this.scrollY(MathUtils.clamp(this.amountY, 0f, this.maxY));
            if(this.scrollX) {
                if(v12 != null) {
                    if(this.style.hScroll != null) {
                        v11 = this.style.hScroll.getMinHeight();
                    }
                    else {
                        v11 = v12.getMinHeight();
                    }

                    if(this.vScrollOnRight) {
                        v8 = v5;
                    }
                    else {
                        v8 = v5 + v16;
                    }

                    if(this.hScrollOnBottom) {
                        v9 = v4;
                    }
                    else {
                        v9 = v13 - v7 - v11;
                    }

                    this.hScrollBounds.set(v8, v9, this.areaWidth, v11);
                    if(this.variableSizeKnobs) {
                        this.hKnobBounds.width = Math.max(v12.getMinWidth(), ((float)(((int)(this.hScrollBounds.width * this.areaWidth / v20)))));
                    }
                    else {
                        this.hKnobBounds.width = v12.getMinWidth();
                    }

                    this.hKnobBounds.height = v12.getMinHeight();
                    this.hKnobBounds.x = this.hScrollBounds.x + (((float)(((int)((this.hScrollBounds.width - this.hKnobBounds.width) * this.getScrollPercentX())))));
                    this.hKnobBounds.y = this.hScrollBounds.y;
                }
                else {
                    this.hScrollBounds.set(0f, 0f, 0f, 0f);
                    this.hKnobBounds.set(0f, 0f, 0f, 0f);
                }
            }

            if(this.scrollY) {
                if(v17 != null) {
                    if(this.style.vScroll != null) {
                        v18 = this.style.vScroll.getMinWidth();
                    }
                    else {
                        v18 = v17.getMinWidth();
                    }

                    if(this.hScrollOnBottom) {
                        v9 = v13 - v7 - this.areaHeight;
                    }
                    else {
                        v9 = v4;
                    }

                    if(this.vScrollOnRight) {
                        v8 = v21 - v6 - v18;
                    }
                    else {
                        v8 = v5;
                    }

                    this.vScrollBounds.set(v8, v9, v18, this.areaHeight);
                    this.vKnobBounds.width = v17.getMinWidth();
                    if(this.variableSizeKnobs) {
                        this.vKnobBounds.height = Math.max(v17.getMinHeight(), ((float)(((int)(this.vScrollBounds.height * this.areaHeight / v19)))));
                    }
                    else {
                        this.vKnobBounds.height = v17.getMinHeight();
                    }

                    if(this.vScrollOnRight) {
                        this.vKnobBounds.x = v21 - v6 - v17.getMinWidth();
                    }
                    else {
                        this.vKnobBounds.x = v5;
                    }

                    this.vKnobBounds.y = this.vScrollBounds.y + (((float)(((int)((this.vScrollBounds.height - this.vKnobBounds.height) * (1f - this.getScrollPercentY()))))));
                }
                else {
                    this.vScrollBounds.set(0f, 0f, 0f, 0f);
                    this.vKnobBounds.set(0f, 0f, 0f, 0f);
                }
            }

            this.widget.setSize(v20, v19);
            if(!(this.widget instanceof Layout)) {
                return;
            }

            this.widget.validate();
        }
    }

    public boolean removeActor(Actor actor) {
        boolean v0;
        if(actor != this.widget) {
            v0 = false;
        }
        else {
            this.setWidget(null);
            v0 = true;
        }

        return v0;
    }

    void resetFade() {
        this.fadeAlpha = this.fadeAlphaSeconds;
        this.fadeDelay = this.fadeDelaySeconds;
    }

    public void scrollTo(float x, float y, float width, float height) {
        this.scrollTo(x, y, width, height, false, false);
    }

    public void scrollTo(float x, float y, float width, float height, boolean centerHorizontal, boolean centerVertical) {
        float v4 = 2f;
        float v0 = this.amountX;
        if(centerHorizontal) {
            v0 = x - this.areaWidth / v4 + width / v4;
        }
        else {
            if(x + width > this.areaWidth + v0) {
                v0 = x + width - this.areaWidth;
            }

            if(x >= v0) {
                goto label_9;
            }

            v0 = x;
        }

    label_9:
        this.scrollX(MathUtils.clamp(v0, 0f, this.maxX));
        float v1 = this.amountY;
        if(centerVertical) {
            v1 = this.maxY - y + this.areaHeight / v4 - height / v4;
        }
        else {
            if(v1 > this.maxY - y - height + this.areaHeight) {
                v1 = this.maxY - y - height + this.areaHeight;
            }

            if(v1 >= this.maxY - y) {
                goto label_21;
            }

            v1 = this.maxY - y;
        }

    label_21:
        this.scrollY(MathUtils.clamp(v1, 0f, this.maxY));
    }

    protected void scrollX(float pixelsX) {
        this.amountX = pixelsX;
    }

    protected void scrollY(float pixelsY) {
        this.amountY = pixelsY;
    }

    public void setCancelTouchFocus(boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }

    public void setClamp(boolean clamp) {
        this.clamp = clamp;
    }

    public void setFadeScrollBars(boolean fadeScrollBars) {
        if(this.fadeScrollBars != fadeScrollBars) {
            this.fadeScrollBars = fadeScrollBars;
            if(!fadeScrollBars) {
                this.fadeAlpha = this.fadeAlphaSeconds;
            }

            this.invalidate();
        }
    }

    public void setFlickScroll(boolean flickScroll) {
        if(this.flickScroll != flickScroll) {
            this.flickScroll = flickScroll;
            if(flickScroll) {
                this.addListener(this.flickScrollListener);
            }
            else {
                this.removeListener(this.flickScrollListener);
            }

            this.invalidate();
        }
    }

    public void setFlickScrollTapSquareSize(float halfTapSquareSize) {
        this.flickScrollListener.getGestureDetector().setTapSquareSize(halfTapSquareSize);
    }

    public void setFlingTime(float flingTime) {
        this.flingTime = flingTime;
    }

    public void setForceScroll(boolean x, boolean y) {
        this.forceScrollX = x;
        this.forceScrollY = y;
    }

    public void setOverscroll(boolean overscrollX, boolean overscrollY) {
        this.overscrollX = overscrollX;
        this.overscrollY = overscrollY;
    }

    public void setScrollBarPositions(boolean bottom, boolean right) {
        this.hScrollOnBottom = bottom;
        this.vScrollOnRight = right;
    }

    public void setScrollPercentX(float percentX) {
        this.scrollX(this.maxX * MathUtils.clamp(percentX, 0f, 1f));
    }

    public void setScrollPercentY(float percentY) {
        this.scrollY(this.maxY * MathUtils.clamp(percentY, 0f, 1f));
    }

    public void setScrollX(float pixels) {
        this.scrollX(MathUtils.clamp(pixels, 0f, this.maxX));
    }

    public void setScrollY(float pixels) {
        this.scrollY(MathUtils.clamp(pixels, 0f, this.maxY));
    }

    public void setScrollbarsOnTop(boolean scrollbarsOnTop) {
        this.scrollbarsOnTop = scrollbarsOnTop;
        this.invalidate();
    }

    public void setScrollingDisabled(boolean x, boolean y) {
        this.disableX = x;
        this.disableY = y;
    }

    public void setSmoothScrolling(boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
    }

    public void setStyle(ScrollPaneStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.invalidateHierarchy();
    }

    public void setVariableSizeKnobs(boolean variableSizeKnobs) {
        this.variableSizeKnobs = variableSizeKnobs;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setWidget(Actor widget) {
        if((((ScrollPane)widget)) == this) {
            throw new IllegalArgumentException("widget cannot be the ScrollPane.");
        }

        if(this.widget != null) {
            super.removeActor(this.widget);
        }

        this.widget = widget;
        if(widget != null) {
            super.addActor(widget);
        }
    }

    public void setupFadeScrollBars(float fadeAlphaSeconds, float fadeDelaySeconds) {
        this.fadeAlphaSeconds = fadeAlphaSeconds;
        this.fadeDelaySeconds = fadeDelaySeconds;
    }

    public void setupOverscroll(float distance, float speedMin, float speedMax) {
        this.overscrollDistance = distance;
        this.overscrollSpeedMin = speedMin;
        this.overscrollSpeedMax = speedMax;
    }

    public String toString() {
        String v0;
        if(this.widget == null) {
            v0 = super.toString();
        }
        else {
            v0 = super.toString() + ": " + this.widget.toString();
        }

        return v0;
    }

    public void updateVisualScroll() {
        this.visualAmountX = this.amountX;
        this.visualAmountY = this.amountY;
    }

    protected void visualScrollX(float pixelsX) {
        this.visualAmountX = pixelsX;
    }

    protected void visualScrollY(float pixelsY) {
        this.visualAmountY = pixelsY;
    }
}

