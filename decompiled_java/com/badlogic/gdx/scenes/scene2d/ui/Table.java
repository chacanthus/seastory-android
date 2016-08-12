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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer$ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;

public class Table extends WidgetGroup {
    public enum Debug {
        static  {
            Debug.none = new Debug("none", 0);
            Debug.all = new Debug("all", 1);
            Debug.table = new Debug("table", 2);
            Debug.cell = new Debug("cell", 3);
            Debug.actor = new Debug("actor", 4);
            Debug[] v0 = new Debug[5];
            v0[0] = Debug.none;
            v0[1] = Debug.all;
            v0[2] = Debug.table;
            v0[3] = Debug.cell;
            v0[4] = Debug.actor;
            Debug.$VALUES = v0;
        }

        private Debug(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Debug valueOf(String name) {
            return Enum.valueOf(Debug.class, name);
        }

        public static Debug[] values() {
            return Debug.$VALUES.clone();
        }
    }

    public class DebugRect extends Rectangle {
        static  {
            DebugRect.pool = Pools.get(DebugRect.class);
        }

        public DebugRect() {
            super();
        }
    }

    int align;
    Drawable background;
    public static Value backgroundBottom;
    public static Value backgroundLeft;
    public static Value backgroundRight;
    public static Value backgroundTop;
    private final Cell cellDefaults;
    static final Pool cellPool;
    private final Array cells;
    private boolean clip;
    private final Array columnDefaults;
    private float[] columnMinWidth;
    private float[] columnPrefWidth;
    private static float[] columnWeightedWidth;
    private float[] columnWidth;
    private int columns;
    Debug debug;
    public static Color debugActorColor;
    public static Color debugCellColor;
    Array debugRects;
    public static Color debugTableColor;
    private float[] expandHeight;
    private float[] expandWidth;
    Value padBottom;
    Value padLeft;
    Value padRight;
    Value padTop;
    boolean round;
    private Cell rowDefaults;
    private float[] rowHeight;
    private float[] rowMinHeight;
    private float[] rowPrefHeight;
    private static float[] rowWeightedHeight;
    private int rows;
    private boolean sizeInvalid;
    private Skin skin;
    private float tableMinHeight;
    private float tableMinWidth;
    private float tablePrefHeight;
    private float tablePrefWidth;

    static  {
        Table.debugTableColor = new Color(0f, 0f, 1f, 1f);
        Table.debugCellColor = new Color(1f, 0f, 0f, 1f);
        Table.debugActorColor = new Color(0f, 1f, 0f, 1f);
        Table.cellPool = new Pool() {
            protected Cell newObject() {
                return new Cell();
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        Table.backgroundTop = new Value() {
            public float get(Actor context) {
                float v1;
                Drawable v0 = ((Table)context).background;
                if(v0 == null) {
                    v1 = 0f;
                }
                else {
                    v1 = v0.getTopHeight();
                }

                return v1;
            }
        };
        Table.backgroundLeft = new Value() {
            public float get(Actor context) {
                float v1;
                Drawable v0 = ((Table)context).background;
                if(v0 == null) {
                    v1 = 0f;
                }
                else {
                    v1 = v0.getLeftWidth();
                }

                return v1;
            }
        };
        Table.backgroundBottom = new Value() {
            public float get(Actor context) {
                float v1;
                Drawable v0 = ((Table)context).background;
                if(v0 == null) {
                    v1 = 0f;
                }
                else {
                    v1 = v0.getBottomHeight();
                }

                return v1;
            }
        };
        Table.backgroundRight = new Value() {
            public float get(Actor context) {
                float v1;
                Drawable v0 = ((Table)context).background;
                if(v0 == null) {
                    v1 = 0f;
                }
                else {
                    v1 = v0.getRightWidth();
                }

                return v1;
            }
        };
    }

    public Table() {
        this(null);
    }

    public Table(Skin skin) {
        super();
        this.cells = new Array(4);
        this.columnDefaults = new Array(2);
        this.sizeInvalid = true;
        this.padTop = Table.backgroundTop;
        this.padLeft = Table.backgroundLeft;
        this.padBottom = Table.backgroundBottom;
        this.padRight = Table.backgroundRight;
        this.align = 1;
        this.debug = Debug.none;
        this.round = true;
        this.skin = skin;
        this.cellDefaults = this.obtainCell();
        this.setTransform(false);
        this.setTouchable(Touchable.childrenOnly);
    }

    public Cell add() {
        return this.add(null);
    }

    public Cell add(Actor arg12) {
        Cell v0 = this.obtainCell();
        v0.actor = arg12;
        Array v2 = this.cells;
        int v1 = v2.size;
        if(v1 > 0) {
            Object v6 = v2.peek();
            if(!((Cell)v6).endRow) {
                v0.column = ((Cell)v6).column + ((Cell)v6).colspan.intValue();
                v0.row = ((Cell)v6).row;
            }
            else {
                v0.column = 0;
                v0.row = ((Cell)v6).row + 1;
            }

            if(v0.row <= 0) {
                goto label_29;
            }

            int v5;
            for(v5 = v1 - 1; v5 >= 0; --v5) {
                Object v8 = v2.get(v5);
                int v3 = ((Cell)v8).column;
                int v7 = v3 + ((Cell)v8).colspan.intValue();
                while(v3 < v7) {
                    if(v3 != v0.column) {
                        goto label_51;
                    }

                    v0.cellAboveIndex = v5;
                    goto label_29;
                label_51:
                    ++v3;
                }
            }
        }
        else {
            v0.column = 0;
            v0.row = 0;
        }

    label_29:
        v2.add(v0);
        v0.set(this.cellDefaults);
        if(v0.column < this.columnDefaults.size) {
            Object v4 = this.columnDefaults.get(v0.column);
            if(v4 != null) {
                v0.merge(((Cell)v4));
            }
        }

        v0.merge(this.rowDefaults);
        if(arg12 != null) {
            this.addActor(arg12);
        }

        return v0;
    }

    public Cell add(String text) {
        if(this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }

        return this.add(new Label(((CharSequence)text), this.skin));
    }

    public Cell add(String text, String labelStyleName) {
        if(this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }

        return this.add(new Label(((CharSequence)text), this.skin.get(labelStyleName, LabelStyle.class)));
    }

    public Cell add(String text, String fontName, Color color) {
        if(this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }

        return this.add(new Label(((CharSequence)text), new LabelStyle(this.skin.getFont(fontName), color)));
    }

    public Cell add(String text, String fontName, String colorName) {
        if(this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }

        return this.add(new Label(((CharSequence)text), new LabelStyle(this.skin.getFont(fontName), this.skin.getColor(colorName))));
    }

    public void add(Actor[] actors) {
        int v0 = 0;
        int v1 = actors.length;
        while(v0 < v1) {
            this.add(actors[v0]);
            ++v0;
        }
    }

    private void addDebugRect(float x, float y, float w, float h, Color color) {
        if(this.debugRects == null) {
            this.debugRects = new Array();
        }

        Object v0 = DebugRect.pool.obtain();
        ((DebugRect)v0).color = color;
        ((DebugRect)v0).set(x, this.getHeight() - y - h, w, h);
        this.debugRects.add(v0);
    }

    public Table align(int align) {
        this.align = align;
        return this;
    }

    public Table background(Drawable background) {
        this.setBackground(background);
        return this;
    }

    public Table background(String drawableName) {
        this.setBackground(drawableName);
        return this;
    }

    public Table bottom() {
        this.align |= 4;
        this.align &= -3;
        return this;
    }

    public Table center() {
        this.align = 1;
        return this;
    }

    public void clearChildren() {
        Array v2 = this.cells;
        int v3;
        for(v3 = v2.size - 1; v3 >= 0; --v3) {
            Actor v0 = v2.get(v3).actor;
            if(v0 != null) {
                v0.remove();
            }
        }

        Table.cellPool.freeAll(v2);
        v2.clear();
        this.rows = 0;
        this.columns = 0;
        if(this.rowDefaults != null) {
            Table.cellPool.free(this.rowDefaults);
        }

        this.rowDefaults = null;
        super.clearChildren();
    }

    private void clearDebugRects() {
        if(this.debugRects != null) {
            DebugRect.pool.freeAll(this.debugRects);
            this.debugRects.clear();
        }
    }

    public Cell columnDefaults(int column) {
        Cell v0_1;
        Object v0;
        Object v3 = null;
        if(this.columnDefaults.size > column) {
            v0 = this.columnDefaults.get(column);
        }
        else {
            v0 = v3;
        }

        if(v0 == null) {
            v0_1 = this.obtainCell();
            v0_1.clear();
            if(column >= this.columnDefaults.size) {
                int v1;
                for(v1 = this.columnDefaults.size; v1 < column; ++v1) {
                    this.columnDefaults.add(v3);
                }

                this.columnDefaults.add(v0_1);
            }
            else {
                this.columnDefaults.set(column, v0_1);
            }
        }

        return v0_1;
    }

    private void computeSize() {
        float v29;
        int v21;
        int v26;
        float v44;
        float v19;
        float v23;
        float v25;
        float v28;
        float v45;
        Actor v3;
        int v8;
        int v9;
        Object v5;
        this.sizeInvalid = false;
        Array v7 = this.cells;
        int v6 = v7.size;
        if(v6 > 0 && !v7.peek().endRow) {
            this.endRow();
        }

        int v13 = this.columns;
        int v34 = this.rows;
        float[] v10 = this.ensureSize(this.columnMinWidth, v13);
        this.columnMinWidth = v10;
        float[] v32 = this.ensureSize(this.rowMinHeight, v34);
        this.rowMinHeight = v32;
        float[] v11 = this.ensureSize(this.columnPrefWidth, v13);
        this.columnPrefWidth = v11;
        float[] v33 = this.ensureSize(this.rowPrefHeight, v34);
        this.rowPrefHeight = v33;
        this.columnWidth = this.ensureSize(this.columnWidth, v13);
        this.rowHeight = this.ensureSize(this.rowHeight, v34);
        float[] v15 = this.ensureSize(this.expandWidth, v13);
        this.expandWidth = v15;
        float[] v14 = this.ensureSize(this.expandHeight, v34);
        this.expandHeight = v14;
        float v36 = 0f;
        int v20;
        for(v20 = 0; v20 < v6; ++v20) {
            v5 = v7.get(v20);
            v9 = ((Cell)v5).column;
            int v30 = ((Cell)v5).row;
            v8 = ((Cell)v5).colspan.intValue();
            v3 = ((Cell)v5).actor;
            if(((Cell)v5).expandY.intValue() != 0 && v14[v30] == 0f) {
                v14[v30] = ((float)((Cell)v5).expandY.intValue());
            }

            if(v8 == 1 && ((Cell)v5).expandX.intValue() != 0 && v15[v9] == 0f) {
                v15[v9] = ((float)((Cell)v5).expandX.intValue());
            }

            float v46 = ((Cell)v5).padLeft.get(v3);
            if(v9 == 0) {
                v45 = 0f;
            }
            else {
                v45 = Math.max(0f, ((Cell)v5).spaceLeft.get(v3) - v36);
            }

            ((Cell)v5).computedPadLeft = v45 + v46;
            ((Cell)v5).computedPadTop = ((Cell)v5).padTop.get(v3);
            if(((Cell)v5).cellAboveIndex != -1) {
                ((Cell)v5).computedPadTop += Math.max(0f, ((Cell)v5).spaceTop.get(v3) - v7.get(((Cell)v5).cellAboveIndex).spaceBottom.get(v3));
            }

            float v35 = ((Cell)v5).spaceRight.get(v3);
            v46 = ((Cell)v5).padRight.get(v3);
            if(v9 + v8 == v13) {
                v45 = 0f;
            }
            else {
                v45 = v35;
            }

            ((Cell)v5).computedPadRight = v45 + v46;
            v46 = ((Cell)v5).padBottom.get(v3);
            if(v30 == v34 - 1) {
                v45 = 0f;
            }
            else {
                v45 = ((Cell)v5).spaceBottom.get(v3);
            }

            ((Cell)v5).computedPadBottom = v45 + v46;
            v36 = v35;
            v28 = ((Cell)v5).prefWidth.get(v3);
            float v27 = ((Cell)v5).prefHeight.get(v3);
            v25 = ((Cell)v5).minWidth.get(v3);
            float v24 = ((Cell)v5).minHeight.get(v3);
            v23 = ((Cell)v5).maxWidth.get(v3);
            float v22 = ((Cell)v5).maxHeight.get(v3);
            if(v28 < v25) {
                v28 = v25;
            }

            if(v27 < v24) {
                v27 = v24;
            }

            if(v23 > 0f && v28 > v23) {
                v28 = v23;
            }

            if(v22 > 0f && v27 > v22) {
                v27 = v22;
            }

            if(v8 == 1) {
                v19 = ((Cell)v5).computedPadLeft + ((Cell)v5).computedPadRight;
                v11[v9] = Math.max(v11[v9], v28 + v19);
                v10[v9] = Math.max(v10[v9], v25 + v19);
            }

            v44 = ((Cell)v5).computedPadTop + ((Cell)v5).computedPadBottom;
            v33[v30] = Math.max(v33[v30], v27 + v44);
            v32[v30] = Math.max(v32[v30], v24 + v44);
        }

        for(v20 = 0; v20 < v6; ++v20) {
            v5 = v7.get(v20);
            int v16 = ((Cell)v5).expandX.intValue();
            if(v16 != 0) {
                v9 = ((Cell)v5).column;
                v26 = v9 + ((Cell)v5).colspan.intValue();
                v21 = v9;
                while(true) {
                    if(v21 >= v26) {
                        break;
                    }
                    else if(v15[v21] == 0f) {
                        ++v21;
                        continue;
                    }

                    goto label_296;
                }

                v21 = v9;
                while(v21 < v26) {
                    v15[v21] = ((float)v16);
                    ++v21;
                }
            }

        label_296:
        }

        for(v20 = 0; v20 < v6; ++v20) {
            v5 = v7.get(v20);
            v8 = ((Cell)v5).colspan.intValue();
            if(v8 != 1) {
                v9 = ((Cell)v5).column;
                v3 = ((Cell)v5).actor;
                v25 = ((Cell)v5).minWidth.get(v3);
                v28 = ((Cell)v5).prefWidth.get(v3);
                v23 = ((Cell)v5).maxWidth.get(v3);
                if(v28 < v25) {
                    v28 = v25;
                }

                if(v23 > 0f && v28 > v23) {
                    v28 = v23;
                }

                float v37 = -(((Cell)v5).computedPadLeft + ((Cell)v5).computedPadRight);
                float v38 = v37;
                v21 = v9;
                v26 = v21 + v8;
                while(v21 < v26) {
                    v37 += v10[v21];
                    v38 += v11[v21];
                    ++v21;
                }

                float v39 = 0f;
                v21 = v9;
                v26 = v21 + v8;
                while(v21 < v26) {
                    v39 += v15[v21];
                    ++v21;
                }

                float v17 = Math.max(0f, v25 - v37);
                float v18 = Math.max(0f, v28 - v38);
                v21 = v9;
                v26 = v21 + v8;
                while(v21 < v26) {
                    if(v39 == 0f) {
                        v29 = 1f / (((float)v8));
                    }
                    else {
                        v29 = v15[v21] / v39;
                    }

                    v10[v21] += v17 * v29;
                    v11[v21] += v18 * v29;
                    ++v21;
                }
            }
        }

        float v41 = 0f;
        float v40 = 0f;
        float v43 = 0f;
        float v42 = 0f;
        for(v20 = 0; v20 < v6; ++v20) {
            v5 = v7.get(v20);
            if(((Cell)v5).uniformX == Boolean.TRUE && ((Cell)v5).colspan.intValue() == 1) {
                v19 = ((Cell)v5).computedPadLeft + ((Cell)v5).computedPadRight;
                v41 = Math.max(v41, v10[((Cell)v5).column] - v19);
                v43 = Math.max(v43, v11[((Cell)v5).column] - v19);
            }

            if(((Cell)v5).uniformY == Boolean.TRUE) {
                v44 = ((Cell)v5).computedPadTop + ((Cell)v5).computedPadBottom;
                v40 = Math.max(v40, v32[((Cell)v5).row] - v44);
                v42 = Math.max(v42, v33[((Cell)v5).row] - v44);
            }
        }

        if(v43 > 0f || v42 > 0f) {
            for(v20 = 0; v20 < v6; ++v20) {
                v5 = v7.get(v20);
                if(v43 > 0f && ((Cell)v5).uniformX == Boolean.TRUE && ((Cell)v5).colspan.intValue() == 1) {
                    v19 = ((Cell)v5).computedPadLeft + ((Cell)v5).computedPadRight;
                    v10[((Cell)v5).column] = v41 + v19;
                    v11[((Cell)v5).column] = v43 + v19;
                }

                if(v42 > 0f && ((Cell)v5).uniformY == Boolean.TRUE) {
                    v44 = ((Cell)v5).computedPadTop + ((Cell)v5).computedPadBottom;
                    v32[((Cell)v5).row] = v40 + v44;
                    v33[((Cell)v5).row] = v42 + v44;
                }
            }
        }

        this.tableMinWidth = 0f;
        this.tableMinHeight = 0f;
        this.tablePrefWidth = 0f;
        this.tablePrefHeight = 0f;
        for(v20 = 0; v20 < v13; ++v20) {
            this.tableMinWidth += v10[v20];
            this.tablePrefWidth += v11[v20];
        }

        for(v20 = 0; v20 < v34; ++v20) {
            this.tableMinHeight += v32[v20];
            this.tablePrefHeight += Math.max(v32[v20], v33[v20]);
        }

        v19 = this.padLeft.get(this) + this.padRight.get(this);
        v44 = this.padTop.get(this) + this.padBottom.get(this);
        this.tableMinWidth += v19;
        this.tableMinHeight += v44;
        this.tablePrefWidth = Math.max(this.tablePrefWidth + v19, this.tableMinWidth);
        this.tablePrefHeight = Math.max(this.tablePrefHeight + v44, this.tableMinHeight);
    }

    public Actor debug() {
        return this.debug();
    }

    public Table debug() {
        super.debug();
        return this;
    }

    public Table debug(Debug debug) {
        boolean v0;
        if(debug != Debug.none) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        super.setDebug(v0);
        if(this.debug != debug) {
            this.debug = debug;
            if(debug == Debug.none) {
                this.clearDebugRects();
            }
            else {
                this.invalidate();
            }
        }

        return this;
    }

    public Table debugActor() {
        super.setDebug(true);
        if(this.debug != Debug.actor) {
            this.debug = Debug.actor;
            this.invalidate();
        }

        return this;
    }

    public Group debugAll() {
        return this.debugAll();
    }

    public Table debugAll() {
        super.debugAll();
        return this;
    }

    public Table debugCell() {
        super.setDebug(true);
        if(this.debug != Debug.cell) {
            this.debug = Debug.cell;
            this.invalidate();
        }

        return this;
    }

    public Table debugTable() {
        super.setDebug(true);
        if(this.debug != Debug.table) {
            this.debug = Debug.table;
            this.invalidate();
        }

        return this;
    }

    public Cell defaults() {
        return this.cellDefaults;
    }

    public void draw(Batch batch, float parentAlpha) {
        this.validate();
        if(this.isTransform()) {
            this.applyTransform(batch, this.computeTransform());
            this.drawBackground(batch, parentAlpha, 0f, 0f);
            if(this.clip) {
                batch.flush();
                float v1 = this.padLeft.get(((Actor)this));
                float v0 = this.padBottom.get(((Actor)this));
                if(this.clipBegin(v1, v0, this.getWidth() - v1 - this.padRight.get(((Actor)this)), this.getHeight() - v0 - this.padTop.get(((Actor)this)))) {
                    this.drawChildren(batch, parentAlpha);
                    batch.flush();
                    this.clipEnd();
                }
            }
            else {
                this.drawChildren(batch, parentAlpha);
            }

            this.resetTransform(batch);
        }
        else {
            this.drawBackground(batch, parentAlpha, this.getX(), this.getY());
            super.draw(batch, parentAlpha);
        }
    }

    protected void drawBackground(Batch batch, float parentAlpha, float x, float y) {
        if(this.background != null) {
            Color v6 = this.getColor();
            batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
            this.background.draw(batch, x, y, this.getWidth(), this.getHeight());
        }
    }

    public void drawDebug(ShapeRenderer shapes) {
        if(this.isTransform()) {
            this.applyTransform(shapes, this.computeTransform());
            this.drawDebugRects(shapes);
            if(this.clip) {
                shapes.flush();
                float v2 = 0f;
                float v3 = 0f;
                float v1 = this.getWidth();
                float v0 = this.getHeight();
                if(this.background != null) {
                    v2 = this.padLeft.get(((Actor)this));
                    v3 = this.padBottom.get(((Actor)this));
                    v1 -= this.padRight.get(((Actor)this)) + v2;
                    v0 -= this.padTop.get(((Actor)this)) + v3;
                }

                if(!this.clipBegin(v2, v3, v1, v0)) {
                    goto label_30;
                }

                this.drawDebugChildren(shapes);
                this.clipEnd();
            }
            else {
                this.drawDebugChildren(shapes);
            }

        label_30:
            this.resetTransform(shapes);
        }
        else {
            this.drawDebugRects(shapes);
            super.drawDebug(shapes);
        }
    }

    protected void drawDebugBounds(ShapeRenderer shapes) {
    }

    private void drawDebugRects(ShapeRenderer shapes) {
        if(this.debugRects != null && (this.getDebug())) {
            shapes.set(ShapeType.Line);
            shapes.setColor(this.getStage().getDebugColor());
            float v3 = 0f;
            float v4 = 0f;
            if(!this.isTransform()) {
                v3 = this.getX();
                v4 = this.getY();
            }

            int v1 = 0;
            int v2 = this.debugRects.size;
            while(v1 < v2) {
                Object v0 = this.debugRects.get(v1);
                shapes.setColor(((DebugRect)v0).color);
                shapes.rect(((DebugRect)v0).x + v3, ((DebugRect)v0).y + v4, ((DebugRect)v0).width, ((DebugRect)v0).height);
                ++v1;
            }
        }
    }

    private void endRow() {
        Array v1 = this.cells;
        int v3 = 0;
        int v2 = v1.size - 1;
        while(v2 >= 0) {
            Object v0 = v1.get(v2);
            if(((Cell)v0).endRow) {
                break;
            }

            v3 += ((Cell)v0).colspan.intValue();
            --v2;
        }

        this.columns = Math.max(this.columns, v3);
        ++this.rows;
        v1.peek().endRow = true;
    }

    private float[] ensureSize(float[] array, int size) {
        if(array == null || array.length < size) {
            array = new float[size];
        }
        else {
            int v0 = 0;
            int v1 = array.length;
            while(v0 < v1) {
                array[v0] = 0f;
                ++v0;
            }
        }

        return array;
    }

    public int getAlign() {
        return this.align;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public Cell getCell(Actor arg6) {
        Object v0;
        Array v1 = this.cells;
        int v2 = 0;
        int v3 = v1.size;
        while(true) {
            if(v2 < v3) {
                v0 = v1.get(v2);
                if(((Cell)v0).actor != arg6) {
                    ++v2;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_7;
        }

        Cell v0_1 = null;
    label_7:
        return ((Cell)v0);
    }

    public Array getCells() {
        return this.cells;
    }

    public boolean getClip() {
        return this.clip;
    }

    public int getColumns() {
        return this.columns;
    }

    public float getMinHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.tableMinHeight;
    }

    public float getMinWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.tableMinWidth;
    }

    public float getPadBottom() {
        return this.padBottom.get(((Actor)this));
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadLeft() {
        return this.padLeft.get(((Actor)this));
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadRight() {
        return this.padRight.get(((Actor)this));
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadTop() {
        return this.padTop.get(((Actor)this));
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadX() {
        return this.padLeft.get(((Actor)this)) + this.padRight.get(((Actor)this));
    }

    public float getPadY() {
        return this.padTop.get(((Actor)this)) + this.padBottom.get(((Actor)this));
    }

    public float getPrefHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        float v0 = this.tablePrefHeight;
        if(this.background != null) {
            v0 = Math.max(v0, this.background.getMinHeight());
        }

        return v0;
    }

    public float getPrefWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        float v0 = this.tablePrefWidth;
        if(this.background != null) {
            v0 = Math.max(v0, this.background.getMinWidth());
        }

        return v0;
    }

    public int getRow(float y) {
        int v6;
        Array v1 = this.cells;
        int v5 = 0;
        y += this.getPadTop();
        int v4 = v1.size;
        if(v4 == 0) {
            v6 = -1;
        }
        else if(v4 == 1) {
            v6 = 0;
        }
        else {
            int v3 = 0;
            while(v3 < v4) {
                int v2 = v3 + 1;
                Object v0 = v1.get(v3);
                if(((Cell)v0).actorY + ((Cell)v0).computedPadTop < y) {
                    break;
                }

                if(((Cell)v0).endRow) {
                    ++v5;
                }

                v3 = v2;
            }

            v6 = v5;
        }

        return v6;
    }

    public int getRows() {
        return this.rows;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public Debug getTableDebug() {
        return this.debug;
    }

    public Actor hit(float x, float y, boolean touchable) {
        Actor v0 = null;
        if(this.clip) {
            if((touchable) && this.getTouchable() == Touchable.disabled) {
                goto label_8;
            }

            if(x < 0f) {
                goto label_8;
            }

            if(x >= this.getWidth()) {
                goto label_8;
            }

            if(y < 0f) {
                goto label_8;
            }

            if(y >= this.getHeight()) {
                goto label_8;
            }

            goto label_15;
        }
        else {
        label_15:
            v0 = super.hit(x, y, touchable);
        }

    label_8:
        return v0;
    }

    public void invalidate() {
        this.sizeInvalid = true;
        super.invalidate();
    }

    private void layout(float layoutX, float layoutY, float layoutWidth, float layoutHeight) {
        float v5;
        float v10;
        int v36;
        float v59;
        float v25;
        float v37;
        float v38;
        int v41;
        int v14;
        int v15;
        Object v11;
        float[] v50;
        float v27;
        float[] v18;
        Array v13 = this.cells;
        int v12 = v13.size;
        if(this.sizeInvalid) {
            this.computeSize();
        }

        float v42 = this.padLeft.get(this);
        float v33 = v42 + this.padRight.get(this);
        float v43 = this.padTop.get(this);
        float v60 = v43 + this.padBottom.get(this);
        int v20 = this.columns;
        int v51 = this.rows;
        float[] v24 = this.expandWidth;
        float[] v23 = this.expandHeight;
        float[] v19 = this.columnWidth;
        float[] v47 = this.rowHeight;
        float v56 = 0f;
        float v55 = 0f;
        int v34;
        for(v34 = 0; v34 < v20; ++v34) {
            v56 += v24[v34];
        }

        for(v34 = 0; v34 < v51; ++v34) {
            v55 += v23[v34];
        }

        float v58 = this.tablePrefWidth - this.tableMinWidth;
        if(v58 == 0f) {
            v18 = this.columnMinWidth;
        }
        else {
            v27 = Math.min(v58, Math.max(0f, layoutWidth - this.tableMinWidth));
            v18 = this.ensureSize(Table.columnWeightedWidth, v20);
            Table.columnWeightedWidth = v18;
            float[] v16 = this.columnMinWidth;
            float[] v17 = this.columnPrefWidth;
            for(v34 = 0; v34 < v20; ++v34) {
                v18[v34] = v16[v34] + v27 * ((v17[v34] - v16[v34]) / v58);
            }
        }

        float v57 = this.tablePrefHeight - this.tableMinHeight;
        if(v57 == 0f) {
            v50 = this.rowMinHeight;
        }
        else {
            v50 = this.ensureSize(Table.rowWeightedHeight, v51);
            Table.rowWeightedHeight = v50;
            float v26 = Math.min(v57, Math.max(0f, layoutHeight - this.tableMinHeight));
            float[] v48 = this.rowMinHeight;
            float[] v49 = this.rowPrefHeight;
            for(v34 = 0; v34 < v51; ++v34) {
                v50[v34] = v48[v34] + v26 * ((v49[v34] - v48[v34]) / v57);
            }
        }

        for(v34 = 0; v34 < v12; ++v34) {
            v11 = v13.get(v34);
            v15 = ((Cell)v11).column;
            int v46 = ((Cell)v11).row;
            Actor v8 = ((Cell)v11).actor;
            float v52 = 0f;
            v14 = ((Cell)v11).colspan.intValue();
            int v35 = v15;
            v41 = v35 + v14;
            while(v35 < v41) {
                v52 += v18[v35];
                ++v35;
            }

            float v61 = v50[v46];
            float v45 = ((Cell)v11).prefWidth.get(v8);
            float v44 = ((Cell)v11).prefHeight.get(v8);
            float v40 = ((Cell)v11).minWidth.get(v8);
            float v39 = ((Cell)v11).minHeight.get(v8);
            v38 = ((Cell)v11).maxWidth.get(v8);
            v37 = ((Cell)v11).maxHeight.get(v8);
            if(v45 < v40) {
                v45 = v40;
            }

            if(v44 < v39) {
                v44 = v39;
            }

            if(v38 > 0f && v45 > v38) {
                v45 = v38;
            }

            if(v37 > 0f && v44 > v37) {
                v44 = v37;
            }

            ((Cell)v11).actorWidth = Math.min(v52 - ((Cell)v11).computedPadLeft - ((Cell)v11).computedPadRight, v45);
            ((Cell)v11).actorHeight = Math.min(v61 - ((Cell)v11).computedPadTop - ((Cell)v11).computedPadBottom, v44);
            if(v14 == 1) {
                v19[v15] = Math.max(v19[v15], v52);
            }

            v47[v46] = Math.max(v47[v46], v61);
        }

        if(v56 > 0f) {
            v25 = layoutWidth - v33;
            for(v34 = 0; v34 < v20; ++v34) {
                v25 -= v19[v34];
            }

            v59 = 0f;
            v36 = 0;
            for(v34 = 0; v34 < v20; ++v34) {
                if(v24[v34] != 0f) {
                    v10 = v24[v34] * v25 / v56;
                    v19[v34] += v10;
                    v59 += v10;
                    v36 = v34;
                }
            }

            v19[v36] += v25 - v59;
        }

        if(v55 > 0f) {
            v25 = layoutHeight - v60;
            for(v34 = 0; v34 < v51; ++v34) {
                v25 -= v47[v34];
            }

            v59 = 0f;
            v36 = 0;
            for(v34 = 0; v34 < v51; ++v34) {
                if(v23[v34] != 0f) {
                    v10 = v23[v34] * v25 / v55;
                    v47[v34] += v10;
                    v59 += v10;
                    v36 = v34;
                }
            }

            v47[v36] += v25 - v59;
        }

        for(v34 = 0; v34 < v12; ++v34) {
            v11 = v13.get(v34);
            v14 = ((Cell)v11).colspan.intValue();
            if(v14 != 1) {
                v27 = 0f;
                v15 = ((Cell)v11).column;
                v41 = v15 + v14;
                while(v15 < v41) {
                    v27 += v18[v15] - v19[v15];
                    ++v15;
                }

                v27 = (v27 - Math.max(0f, ((Cell)v11).computedPadLeft + ((Cell)v11).computedPadRight)) / (((float)v14));
                if(v27 > 0f) {
                    v15 = ((Cell)v11).column;
                    v41 = v15 + v14;
                    while(v15 < v41) {
                        v19[v15] += v27;
                        ++v15;
                    }
                }
            }
        }

        float v54 = v33;
        float v53 = v60;
        for(v34 = 0; v34 < v20; ++v34) {
            v54 += v19[v34];
        }

        for(v34 = 0; v34 < v51; ++v34) {
            v53 += v47[v34];
        }

        int v9 = this.align;
        float v62 = layoutX + v42;
        if((v9 & 16) != 0) {
            v62 += layoutWidth - v54;
        }
        else if((v9 & 8) == 0) {
            v62 += (layoutWidth - v54) / 2f;
        }

        float v63 = layoutY + v43;
        if((v9 & 4) != 0) {
            v63 += layoutHeight - v53;
        }
        else if((v9 & 2) == 0) {
            v63 += (layoutHeight - v53) / 2f;
        }

        float v3 = v62;
        float v22 = v63;
        for(v34 = 0; v34 < v12; ++v34) {
            v11 = v13.get(v34);
            v5 = 0f;
            v15 = ((Cell)v11).column;
            v41 = v15 + ((Cell)v11).colspan.intValue();
            while(v15 < v41) {
                v5 += v19[v15];
                ++v15;
            }

            v5 -= ((Cell)v11).computedPadLeft + ((Cell)v11).computedPadRight;
            v3 += ((Cell)v11).computedPadLeft;
            float v28 = ((Cell)v11).fillX.floatValue();
            float v29 = ((Cell)v11).fillY.floatValue();
            if(v28 > 0f) {
                ((Cell)v11).actorWidth = Math.max(v5 * v28, ((Cell)v11).minWidth.get(((Cell)v11).actor));
                v38 = ((Cell)v11).maxWidth.get(((Cell)v11).actor);
                if(v38 > 0f) {
                    ((Cell)v11).actorWidth = Math.min(((Cell)v11).actorWidth, v38);
                }
            }

            if(v29 > 0f) {
                ((Cell)v11).actorHeight = Math.max(v47[((Cell)v11).row] * v29 - ((Cell)v11).computedPadTop - ((Cell)v11).computedPadBottom, ((Cell)v11).minHeight.get(((Cell)v11).actor));
                v37 = ((Cell)v11).maxHeight.get(((Cell)v11).actor);
                if(v37 > 0f) {
                    ((Cell)v11).actorHeight = Math.min(((Cell)v11).actorHeight, v37);
                }
            }

            v9 = ((Cell)v11).align.intValue();
            if((v9 & 8) != 0) {
                ((Cell)v11).actorX = v3;
            }
            else if((v9 & 16) != 0) {
                ((Cell)v11).actorX = v3 + v5 - ((Cell)v11).actorWidth;
            }
            else {
                ((Cell)v11).actorX = (v5 - ((Cell)v11).actorWidth) / 2f + v3;
            }

            if((v9 & 2) != 0) {
                ((Cell)v11).actorY = ((Cell)v11).computedPadTop + v22;
            }
            else if((v9 & 4) != 0) {
                ((Cell)v11).actorY = v47[((Cell)v11).row] + v22 - ((Cell)v11).actorHeight - ((Cell)v11).computedPadBottom;
            }
            else {
                ((Cell)v11).actorY = (v47[((Cell)v11).row] - ((Cell)v11).actorHeight + ((Cell)v11).computedPadTop - ((Cell)v11).computedPadBottom) / 2f + v22;
            }

            if(((Cell)v11).endRow) {
                v3 = v62;
                v22 += v47[((Cell)v11).row];
            }
            else {
                v3 += ((Cell)v11).computedPadRight + v5;
            }
        }

        if(this.debug != Debug.none) {
            this.clearDebugRects();
            float v21 = v62;
            v22 = v63;
            if(this.debug == Debug.table || this.debug == Debug.all) {
                this.addDebugRect(layoutX, layoutY, layoutWidth, layoutHeight, Table.debugTableColor);
                this.addDebugRect(v62, v63, v54 - v33, v53 - v60, Table.debugTableColor);
            }

            v34 = 0;
            while(v34 < v12) {
                v11 = v13.get(v34);
                if(this.debug == Debug.actor || this.debug == Debug.all) {
                    this.addDebugRect(((Cell)v11).actorX, ((Cell)v11).actorY, ((Cell)v11).actorWidth, ((Cell)v11).actorHeight, Table.debugActorColor);
                }

                v5 = 0f;
                v15 = ((Cell)v11).column;
                v41 = v15 + ((Cell)v11).colspan.intValue();
                while(v15 < v41) {
                    v5 += v19[v15];
                    ++v15;
                }

                v5 -= ((Cell)v11).computedPadLeft + ((Cell)v11).computedPadRight;
                v3 = v21 + ((Cell)v11).computedPadLeft;
                if(this.debug == Debug.cell || this.debug == Debug.all) {
                    this.addDebugRect(v3, v22 + ((Cell)v11).computedPadTop, v5, v47[((Cell)v11).row] - ((Cell)v11).computedPadTop - ((Cell)v11).computedPadBottom, Table.debugCellColor);
                }

                if(((Cell)v11).endRow) {
                    v3 = v62;
                    v22 += v47[((Cell)v11).row];
                }
                else {
                    v3 += ((Cell)v11).computedPadRight + v5;
                }

                ++v34;
                v21 = v3;
            }
        }
    }

    public void layout() {
        Actor v0;
        float v4;
        float v1;
        Object v5;
        int v11;
        int v10;
        float v12 = this.getWidth();
        float v9 = this.getHeight();
        this.layout(0f, 0f, v12, v9);
        Array v6 = this.cells;
        if(this.round) {
            v10 = 0;
            v11 = v6.size;
            while(v10 < v11) {
                v5 = v6.get(v10);
                float v2 = ((float)Math.round(((Cell)v5).actorWidth));
                v1 = ((float)Math.round(((Cell)v5).actorHeight));
                float v3 = ((float)Math.round(((Cell)v5).actorX));
                v4 = v9 - (((float)Math.round(((Cell)v5).actorY))) - v1;
                ((Cell)v5).setActorBounds(v3, v4, v2, v1);
                v0 = ((Cell)v5).actor;
                if(v0 != null) {
                    v0.setBounds(v3, v4, v2, v1);
                }

                ++v10;
            }
        }
        else {
            v10 = 0;
            v11 = v6.size;
            while(v10 < v11) {
                v5 = v6.get(v10);
                v1 = ((Cell)v5).actorHeight;
                v4 = v9 - ((Cell)v5).actorY - v1;
                ((Cell)v5).setActorY(v4);
                v0 = ((Cell)v5).actor;
                if(v0 != null) {
                    v0.setBounds(((Cell)v5).actorX, v4, ((Cell)v5).actorWidth, v1);
                }

                ++v10;
            }
        }

        SnapshotArray v8 = this.getChildren();
        v10 = 0;
        v11 = ((Array)v8).size;
        while(v10 < v11) {
            Object v7 = ((Array)v8).get(v10);
            if((v7 instanceof Layout)) {
                ((Layout)v7).validate();
            }

            ++v10;
        }
    }

    public Table left() {
        this.align |= 8;
        this.align &= -17;
        return this;
    }

    private Cell obtainCell() {
        Object v0 = Table.cellPool.obtain();
        ((Cell)v0).setLayout(this);
        return ((Cell)v0);
    }

    public Table pad(float pad) {
        this.pad(new Fixed(pad));
        return this;
    }

    public Table pad(Value pad) {
        if(pad == null) {
            throw new IllegalArgumentException("pad cannot be null.");
        }

        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(float top, float left, float bottom, float right) {
        this.padTop = new Fixed(top);
        this.padLeft = new Fixed(left);
        this.padBottom = new Fixed(bottom);
        this.padRight = new Fixed(right);
        this.sizeInvalid = true;
        return this;
    }

    public Table pad(Value top, Value left, Value bottom, Value right) {
        if(top == null) {
            throw new IllegalArgumentException("top cannot be null.");
        }

        if(left == null) {
            throw new IllegalArgumentException("left cannot be null.");
        }

        if(bottom == null) {
            throw new IllegalArgumentException("bottom cannot be null.");
        }

        if(right == null) {
            throw new IllegalArgumentException("right cannot be null.");
        }

        this.padTop = top;
        this.padLeft = left;
        this.padBottom = bottom;
        this.padRight = right;
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(float padBottom) {
        this.padBottom = new Fixed(padBottom);
        this.sizeInvalid = true;
        return this;
    }

    public Table padBottom(Value padBottom) {
        if(padBottom == null) {
            throw new IllegalArgumentException("padBottom cannot be null.");
        }

        this.padBottom = padBottom;
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(float padLeft) {
        this.padLeft = new Fixed(padLeft);
        this.sizeInvalid = true;
        return this;
    }

    public Table padLeft(Value padLeft) {
        if(padLeft == null) {
            throw new IllegalArgumentException("padLeft cannot be null.");
        }

        this.padLeft = padLeft;
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(float padRight) {
        this.padRight = new Fixed(padRight);
        this.sizeInvalid = true;
        return this;
    }

    public Table padRight(Value padRight) {
        if(padRight == null) {
            throw new IllegalArgumentException("padRight cannot be null.");
        }

        this.padRight = padRight;
        this.sizeInvalid = true;
        return this;
    }

    public Table padTop(float padTop) {
        this.padTop = new Fixed(padTop);
        this.sizeInvalid = true;
        return this;
    }

    public Table padTop(Value padTop) {
        if(padTop == null) {
            throw new IllegalArgumentException("padTop cannot be null.");
        }

        this.padTop = padTop;
        this.sizeInvalid = true;
        return this;
    }

    public boolean removeActor(Actor actor) {
        return this.removeActor(actor, true);
    }

    public boolean removeActor(Actor actor, boolean unfocus) {
        boolean v1;
        if(!super.removeActor(actor, unfocus)) {
            v1 = false;
        }
        else {
            Cell v0 = this.getCell(actor);
            if(v0 != null) {
                v0.actor = null;
            }

            v1 = true;
        }

        return v1;
    }

    public void reset() {
        this.clear();
        this.padTop = Table.backgroundTop;
        this.padLeft = Table.backgroundLeft;
        this.padBottom = Table.backgroundBottom;
        this.padRight = Table.backgroundRight;
        this.align = 1;
        this.debug(Debug.none);
        this.cellDefaults.reset();
        int v1 = 0;
        int v2 = this.columnDefaults.size;
        while(v1 < v2) {
            Object v0 = this.columnDefaults.get(v1);
            if(v0 != null) {
                Table.cellPool.free(v0);
            }

            ++v1;
        }

        this.columnDefaults.clear();
    }

    public Table right() {
        this.align |= 16;
        this.align &= -9;
        return this;
    }

    public Cell row() {
        if(this.cells.size > 0) {
            this.endRow();
            this.invalidate();
        }

        if(this.rowDefaults != null) {
            Table.cellPool.free(this.rowDefaults);
        }

        this.rowDefaults = this.obtainCell();
        this.rowDefaults.clear();
        return this.rowDefaults;
    }

    public void setBackground(Drawable background) {
        if(this.background != background) {
            float v7 = this.getPadTop();
            float v3 = this.getPadLeft();
            float v1 = this.getPadBottom();
            float v5 = this.getPadRight();
            this.background = background;
            float v6 = this.getPadTop();
            float v2 = this.getPadLeft();
            float v0 = this.getPadBottom();
            float v4 = this.getPadRight();
            if(v7 + v1 == v6 + v0 && v3 + v5 == v2 + v4) {
                if(v7 == v6 && v3 == v2 && v1 == v0 && v5 == v4) {
                    return;
                }

                this.invalidate();
                return;
            }

            this.invalidateHierarchy();
        }
    }

    public void setBackground(String drawableName) {
        if(this.skin == null) {
            throw new IllegalStateException("Table must have a skin set to use this method.");
        }

        this.setBackground(this.skin.getDrawable(drawableName));
    }

    public void setClip(boolean enabled) {
        this.clip = enabled;
        this.setTransform(enabled);
        this.invalidate();
    }

    public void setDebug(boolean enabled) {
        Debug v0;
        if(enabled) {
            v0 = Debug.all;
        }
        else {
            v0 = Debug.none;
        }

        this.debug(v0);
    }

    public void setRound(boolean round) {
        this.round = round;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Cell stack(Actor[] actors) {
        Stack v2 = new Stack();
        if(actors != null) {
            int v0 = 0;
            int v1 = actors.length;
            while(v0 < v1) {
                v2.addActor(actors[v0]);
                ++v0;
            }
        }

        return this.add(((Actor)v2));
    }

    public Table top() {
        this.align |= 2;
        this.align &= -5;
        return this;
    }
}

