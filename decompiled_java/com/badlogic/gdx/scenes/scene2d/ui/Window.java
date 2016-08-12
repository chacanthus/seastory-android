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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Window extends Table {
    public class WindowStyle {
        public WindowStyle() {
            super();
            this.titleFontColor = new Color(1f, 1f, 1f, 1f);
        }

        public WindowStyle(BitmapFont titleFont, Color titleFontColor, Drawable background) {
            super();
            this.titleFontColor = new Color(1f, 1f, 1f, 1f);
            this.background = background;
            this.titleFont = titleFont;
            this.titleFontColor.set(titleFontColor);
        }

        public WindowStyle(WindowStyle style) {
            super();
            this.titleFontColor = new Color(1f, 1f, 1f, 1f);
            this.background = style.background;
            this.titleFont = style.titleFont;
            this.titleFontColor = new Color(style.titleFontColor);
        }
    }

    private static final int MOVE = 32;
    boolean dragging;
    boolean drawTitleTable;
    boolean isModal;
    boolean isMovable;
    boolean isResizable;
    boolean keepWithinStage;
    int resizeBorder;
    private WindowStyle style;
    Label titleLabel;
    Table titleTable;
    private static final Vector2 tmpPosition;
    private static final Vector2 tmpSize;

    static  {
        Window.tmpPosition = new Vector2();
        Window.tmpSize = new Vector2();
    }

    public Window(String title, Skin skin) {
        this(title, skin.get(WindowStyle.class));
        this.setSkin(skin);
    }

    public Window(String title, WindowStyle style) {
        float v4 = 150f;
        super();
        this.isMovable = true;
        this.resizeBorder = 8;
        this.keepWithinStage = true;
        if(title == null) {
            throw new IllegalArgumentException("title cannot be null.");
        }

        this.setTouchable(Touchable.enabled);
        this.setClip(true);
        this.titleLabel = new Label(((CharSequence)title), new LabelStyle(style.titleFont, style.titleFontColor));
        this.titleTable = new Table() {
            public void draw(Batch batch, float parentAlpha) {
                if(Window.this.drawTitleTable) {
                    super.draw(batch, parentAlpha);
                }
            }
        };
        this.titleTable.add(this.titleLabel).expandX().fillX();
        this.addActor(this.titleTable);
        this.setStyle(style);
        this.setWidth(v4);
        this.setHeight(v4);
        this.addCaptureListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Window.this.toFront();
                return 0;
            }
        });
        this.addListener(new InputListener() {
            int edge;
            float lastX;
            float lastY;
            float startX;
            float startY;

            public boolean keyDown(InputEvent event, int keycode) {
                return Window.this.isModal;
            }

            public boolean keyTyped(InputEvent event, char character) {
                return Window.this.isModal;
            }

            public boolean keyUp(InputEvent event, int keycode) {
                return Window.this.isModal;
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                return Window.this.isModal;
            }

            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                return Window.this.isModal;
            }

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v3;
                boolean v5 = false;
                if(button == 0) {
                    int v0 = Window.this.resizeBorder;
                    float v2 = Window.this.getWidth();
                    float v1 = Window.this.getHeight();
                    this.edge = 0;
                    if(Window.this.isResizable) {
                        if(x < (((float)v0))) {
                            this.edge |= 8;
                        }

                        if(x > v2 - (((float)v0))) {
                            this.edge |= 16;
                        }

                        if(y < (((float)v0))) {
                            this.edge |= 4;
                        }

                        if(y > v1 - (((float)v0))) {
                            this.edge |= 2;
                        }

                        if(this.edge != 0) {
                            v0 += 25;
                        }

                        if(x < (((float)v0))) {
                            this.edge |= 8;
                        }

                        if(x > v2 - (((float)v0))) {
                            this.edge |= 16;
                        }

                        if(y < (((float)v0))) {
                            this.edge |= 4;
                        }

                        if(y <= v1 - (((float)v0))) {
                            goto label_60;
                        }

                        this.edge |= 2;
                    }

                label_60:
                    if((Window.this.isMovable) && this.edge == 0 && y <= v1 && y >= v1 - Window.this.getPadTop() && x >= 0f && x <= v2) {
                        this.edge = 32;
                    }

                    Window v6 = Window.this;
                    if(this.edge != 0) {
                        v3 = true;
                    }
                    else {
                        v3 = false;
                    }

                    v6.dragging = v3;
                    this.startX = x;
                    this.startY = y;
                    this.lastX = x;
                    this.lastY = y;
                }

                if(this.edge != 0 || (Window.this.isModal)) {
                    v5 = true;
                }

                return v5;
            }

            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float v3;
                float v2;
                int v4;
                if(Window.this.dragging) {
                    float v11 = Window.this.getWidth();
                    float v5 = Window.this.getHeight();
                    float v12 = Window.this.getX();
                    float v13 = Window.this.getY();
                    float v9 = Window.this.getMinWidth();
                    Window.this.getMaxWidth();
                    float v8 = Window.this.getMinHeight();
                    Window.this.getMaxHeight();
                    Stage v10 = Window.this.getStage();
                    if(!Window.this.keepWithinStage || Window.this.getParent() != v10.getRoot()) {
                        v4 = 0;
                    }
                    else {
                        v4 = 1;
                    }

                    if((this.edge & 32) != 0) {
                        v12 += x - this.startX;
                        v13 += y - this.startY;
                    }

                    if((this.edge & 8) != 0) {
                        v2 = x - this.startX;
                        if(v11 - v2 < v9) {
                            v2 = -(v9 - v11);
                        }

                        if(v4 != 0 && v12 + v2 < 0f) {
                            v2 = -v12;
                        }

                        v11 -= v2;
                        v12 += v2;
                    }

                    if((this.edge & 4) != 0) {
                        v3 = y - this.startY;
                        if(v5 - v3 < v8) {
                            v3 = -(v8 - v5);
                        }

                        if(v4 != 0 && v13 + v3 < 0f) {
                            v3 = -v13;
                        }

                        v5 -= v3;
                        v13 += v3;
                    }

                    if((this.edge & 16) != 0) {
                        v2 = x - this.lastX;
                        if(v11 + v2 < v9) {
                            v2 = v9 - v11;
                        }

                        if(v4 != 0 && v12 + v11 + v2 > v10.getWidth()) {
                            v2 = v10.getWidth() - v12 - v11;
                        }

                        v11 += v2;
                    }

                    if((this.edge & 2) != 0) {
                        v3 = y - this.lastY;
                        if(v5 + v3 < v8) {
                            v3 = v8 - v5;
                        }

                        if(v4 != 0 && v13 + v5 + v3 > v10.getHeight()) {
                            v3 = v10.getHeight() - v13 - v5;
                        }

                        v5 += v3;
                    }

                    this.lastX = x;
                    this.lastY = y;
                    Window.this.setBounds(((float)Math.round(v12)), ((float)Math.round(v13)), ((float)Math.round(v11)), ((float)Math.round(v5)));
                }
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Window.this.dragging = false;
            }
        });
    }

    public Window(String title, Skin skin, String styleName) {
        this(title, skin.get(styleName, WindowStyle.class));
        this.setSkin(skin);
    }

    public void draw(Batch batch, float parentAlpha) {
        Stage v7 = this.getStage();
        if(v7.getKeyboardFocus() == null) {
            v7.setKeyboardFocus(((Actor)this));
        }

        this.keepWithinStage();
        if(this.style.stageBackground != null) {
            this.stageToLocalCoordinates(Window.tmpPosition.set(0f, 0f));
            this.stageToLocalCoordinates(Window.tmpSize.set(v7.getWidth(), v7.getHeight()));
            this.drawStageBackground(batch, parentAlpha, this.getX() + Window.tmpPosition.x, this.getY() + Window.tmpPosition.y, this.getX() + Window.tmpSize.x, this.getY() + Window.tmpSize.y);
        }

        super.draw(batch, parentAlpha);
    }

    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, parentAlpha, x, y);
        this.titleTable.getColor().a = this.getColor().a;
        float v1 = this.getPadTop();
        float v0 = this.getPadLeft();
        this.titleTable.setSize(this.getWidth() - v0 - this.getPadRight(), v1);
        this.titleTable.setPosition(v0, this.getHeight() - v1);
        this.drawTitleTable = true;
        this.titleTable.draw(batch, parentAlpha);
        this.drawTitleTable = false;
    }

    protected void drawStageBackground(Batch batch, float parentAlpha, float x, float y, float width, float height) {
        Color v6 = this.getColor();
        batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
        this.style.stageBackground.draw(batch, x, y, width, height);
    }

    public float getPrefWidth() {
        return Math.max(super.getPrefWidth(), this.titleLabel.getPrefWidth() + this.getPadLeft() + this.getPadRight());
    }

    public WindowStyle getStyle() {
        return this.style;
    }

    public Label getTitleLabel() {
        return this.titleLabel;
    }

    public Table getTitleTable() {
        return this.titleTable;
    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor v5_1;
        Actor v2 = super.hit(x, y, touchable);
        if(v2 != null || !this.isModal || (touchable) && this.getTouchable() != Touchable.enabled) {
            float v1 = this.getHeight();
            if(v2 != null && (((Window)v2)) != this) {
                if(y <= v1 && y >= v1 - this.getPadTop() && x >= 0f && x <= this.getWidth()) {
                    Actor v0 = v2;
                    while(v0.getParent() != this) {
                        Group v0_1 = ((Actor)v0_1).getParent();
                    }

                    if(this.getCell(((Actor)v0_1)) == null) {
                        goto label_29;
                    }

                    goto label_8;
                }

            label_29:
                v5_1 = v2;
                goto label_8;
            }

            v5_1 = v2;
        }

    label_8:
        return ((Window)v5_1);
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public boolean isModal() {
        return this.isModal;
    }

    public boolean isMovable() {
        return this.isMovable;
    }

    public boolean isResizable() {
        return this.isResizable;
    }

    void keepWithinStage() {
        float v2;
        float v3;
        int v8 = 8;
        int v11 = 4;
        int v10 = 2;
        float v9 = 2f;
        if(this.keepWithinStage) {
            Stage v4 = this.getStage();
            Camera v0 = v4.getCamera();
            if((v0 instanceof OrthographicCamera)) {
                Camera v1 = v0;
                v3 = v4.getWidth();
                v2 = v4.getHeight();
                if(this.getX(16) - v0.position.x > v3 / v9 / ((OrthographicCamera)v1).zoom) {
                    this.setPosition(v0.position.x + v3 / v9 / ((OrthographicCamera)v1).zoom, this.getY(16), 16);
                }

                if(this.getX(v8) - v0.position.x < -v3 / v9 / ((OrthographicCamera)v1).zoom) {
                    this.setPosition(v0.position.x - v3 / v9 / ((OrthographicCamera)v1).zoom, this.getY(v8), v8);
                }

                if(this.getY(v10) - v0.position.y > v2 / v9 / ((OrthographicCamera)v1).zoom) {
                    this.setPosition(this.getX(v10), v0.position.y + v2 / v9 / ((OrthographicCamera)v1).zoom, v10);
                }

                if(this.getY(v11) - v0.position.y >= -v2 / v9 / ((OrthographicCamera)v1).zoom) {
                    return;
                }

                this.setPosition(this.getX(v11), v0.position.y - v2 / v9 / ((OrthographicCamera)v1).zoom, v11);
            }
            else {
                if(this.getParent() != v4.getRoot()) {
                    return;
                }

                v3 = v4.getWidth();
                v2 = v4.getHeight();
                if(this.getX() < 0f) {
                    this.setX(0f);
                }

                if(this.getRight() > v3) {
                    this.setX(v3 - this.getWidth());
                }

                if(this.getY() < 0f) {
                    this.setY(0f);
                }

                if(this.getTop() <= v2) {
                    return;
                }

                this.setY(v2 - this.getHeight());
            }
        }
    }

    public void setKeepWithinStage(boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

    public void setModal(boolean isModal) {
        this.isModal = isModal;
    }

    public void setMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    public void setResizable(boolean isResizable) {
        this.isResizable = isResizable;
    }

    public void setResizeBorder(int resizeBorder) {
        this.resizeBorder = resizeBorder;
    }

    public void setStyle(WindowStyle style) {
        if(style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }

        this.style = style;
        this.setBackground(style.background);
        this.titleLabel.setStyle(new LabelStyle(style.titleFont, style.titleFontColor));
        this.invalidateHierarchy();
    }
}

