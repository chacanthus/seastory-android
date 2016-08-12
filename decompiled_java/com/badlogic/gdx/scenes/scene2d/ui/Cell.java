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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool$Poolable;

public class Cell implements Poolable {
    private static final Integer bottomi;
    private static final Integer centeri;
    private static final Integer lefti;
    private static final Float onef;
    private static final Integer onei;
    private static final Integer righti;
    private Table table;
    private static final Integer topi;
    private static final Float zerof;
    private static final Integer zeroi;

    static  {
        Cell.zerof = Float.valueOf(0f);
        Cell.onef = Float.valueOf(1f);
        Cell.zeroi = Integer.valueOf(0);
        Cell.onei = Integer.valueOf(1);
        Cell.centeri = Cell.onei;
        Cell.topi = Integer.valueOf(2);
        Cell.bottomi = Integer.valueOf(4);
        Cell.lefti = Integer.valueOf(8);
        Cell.righti = Integer.valueOf(16);
    }

    public Cell() {
        super();
        this.reset();
    }

    public Cell align(int align) {
        this.align = Integer.valueOf(align);
        return this;
    }

    public Cell bottom() {
        if(this.align == null) {
            this.align = Cell.bottomi;
        }
        else {
            this.align = Integer.valueOf((this.align.intValue() | 4) & -3);
        }

        return this;
    }

    public Cell center() {
        this.align = Cell.centeri;
        return this;
    }

    void clear() {
        this.minWidth = null;
        this.minHeight = null;
        this.prefWidth = null;
        this.prefHeight = null;
        this.maxWidth = null;
        this.maxHeight = null;
        this.spaceTop = null;
        this.spaceLeft = null;
        this.spaceBottom = null;
        this.spaceRight = null;
        this.padTop = null;
        this.padLeft = null;
        this.padBottom = null;
        this.padRight = null;
        this.fillX = null;
        this.fillY = null;
        this.align = null;
        this.expandX = null;
        this.expandY = null;
        this.colspan = null;
        this.uniformX = null;
        this.uniformY = null;
    }

    public Cell clearActor() {
        this.setActor(null);
        return this;
    }

    public Cell colspan(int colspan) {
        this.colspan = Integer.valueOf(colspan);
        return this;
    }

    public Cell expand() {
        this.expandX = Cell.onei;
        this.expandY = Cell.onei;
        return this;
    }

    public Cell expand(int x, int y) {
        this.expandX = Integer.valueOf(x);
        this.expandY = Integer.valueOf(y);
        return this;
    }

    public Cell expand(boolean x, boolean y) {
        Integer v0;
        if(x) {
            v0 = Cell.onei;
        }
        else {
            v0 = Cell.zeroi;
        }

        this.expandX = v0;
        if(y) {
            v0 = Cell.onei;
        }
        else {
            v0 = Cell.zeroi;
        }

        this.expandY = v0;
        return this;
    }

    public Cell expandX() {
        this.expandX = Cell.onei;
        return this;
    }

    public Cell expandY() {
        this.expandY = Cell.onei;
        return this;
    }

    public Cell fill() {
        this.fillX = Cell.onef;
        this.fillY = Cell.onef;
        return this;
    }

    public Cell fill(float x, float y) {
        this.fillX = Float.valueOf(x);
        this.fillY = Float.valueOf(y);
        return this;
    }

    public Cell fill(boolean fill) {
        Float v0;
        if(fill) {
            v0 = Cell.onef;
        }
        else {
            v0 = Cell.zerof;
        }

        this.fillX = v0;
        if(fill) {
            v0 = Cell.onef;
        }
        else {
            v0 = Cell.zerof;
        }

        this.fillY = v0;
        return this;
    }

    public Cell fill(boolean x, boolean y) {
        Float v0;
        if(x) {
            v0 = Cell.onef;
        }
        else {
            v0 = Cell.zerof;
        }

        this.fillX = v0;
        if(y) {
            v0 = Cell.onef;
        }
        else {
            v0 = Cell.zerof;
        }

        this.fillY = v0;
        return this;
    }

    public Cell fillX() {
        this.fillX = Cell.onef;
        return this;
    }

    public Cell fillY() {
        this.fillY = Cell.onef;
        return this;
    }

    public Actor getActor() {
        return this.actor;
    }

    public float getActorHeight() {
        return this.actorHeight;
    }

    public float getActorWidth() {
        return this.actorWidth;
    }

    public float getActorX() {
        return this.actorX;
    }

    public float getActorY() {
        return this.actorY;
    }

    public int getAlign() {
        return this.align.intValue();
    }

    public int getColspan() {
        return this.colspan.intValue();
    }

    public int getColumn() {
        return this.column;
    }

    public float getComputedPadBottom() {
        return this.computedPadBottom;
    }

    public float getComputedPadLeft() {
        return this.computedPadLeft;
    }

    public float getComputedPadRight() {
        return this.computedPadRight;
    }

    public float getComputedPadTop() {
        return this.computedPadTop;
    }

    public int getExpandX() {
        return this.expandX.intValue();
    }

    public int getExpandY() {
        return this.expandY.intValue();
    }

    public float getFillX() {
        return this.fillX.floatValue();
    }

    public float getFillY() {
        return this.fillY.floatValue();
    }

    public float getMaxHeight() {
        return this.maxHeight.get(this.actor);
    }

    public Value getMaxHeightValue() {
        return this.maxHeight;
    }

    public float getMaxWidth() {
        return this.maxWidth.get(this.actor);
    }

    public Value getMaxWidthValue() {
        return this.maxWidth;
    }

    public float getMinHeight() {
        return this.minHeight.get(this.actor);
    }

    public Value getMinHeightValue() {
        return this.minHeight;
    }

    public float getMinWidth() {
        return this.minWidth.get(this.actor);
    }

    public Value getMinWidthValue() {
        return this.minWidth;
    }

    public float getPadBottom() {
        return this.padBottom.get(this.actor);
    }

    public Value getPadBottomValue() {
        return this.padBottom;
    }

    public float getPadLeft() {
        return this.padLeft.get(this.actor);
    }

    public Value getPadLeftValue() {
        return this.padLeft;
    }

    public float getPadRight() {
        return this.padRight.get(this.actor);
    }

    public Value getPadRightValue() {
        return this.padRight;
    }

    public float getPadTop() {
        return this.padTop.get(this.actor);
    }

    public Value getPadTopValue() {
        return this.padTop;
    }

    public float getPadX() {
        return this.padLeft.get(this.actor) + this.padRight.get(this.actor);
    }

    public float getPadY() {
        return this.padTop.get(this.actor) + this.padBottom.get(this.actor);
    }

    public float getPrefHeight() {
        return this.prefHeight.get(this.actor);
    }

    public Value getPrefHeightValue() {
        return this.prefHeight;
    }

    public float getPrefWidth() {
        return this.prefWidth.get(this.actor);
    }

    public Value getPrefWidthValue() {
        return this.prefWidth;
    }

    public int getRow() {
        return this.row;
    }

    public float getSpaceBottom() {
        return this.spaceBottom.get(this.actor);
    }

    public Value getSpaceBottomValue() {
        return this.spaceBottom;
    }

    public float getSpaceLeft() {
        return this.spaceLeft.get(this.actor);
    }

    public Value getSpaceLeftValue() {
        return this.spaceLeft;
    }

    public float getSpaceRight() {
        return this.spaceRight.get(this.actor);
    }

    public Value getSpaceRightValue() {
        return this.spaceRight;
    }

    public float getSpaceTop() {
        return this.spaceTop.get(this.actor);
    }

    public Value getSpaceTopValue() {
        return this.spaceTop;
    }

    public Table getTable() {
        return this.table;
    }

    public boolean getUniformX() {
        return this.uniformX.booleanValue();
    }

    public boolean getUniformY() {
        return this.uniformY.booleanValue();
    }

    public boolean hasActor() {
        boolean v0;
        if(this.actor != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public Cell height(float height) {
        this.height(new Fixed(height));
        return this;
    }

    public Cell height(Value height) {
        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minHeight = height;
        this.prefHeight = height;
        this.maxHeight = height;
        return this;
    }

    public boolean isEndRow() {
        return this.endRow;
    }

    public Cell left() {
        if(this.align == null) {
            this.align = Cell.lefti;
        }
        else {
            this.align = Integer.valueOf((this.align.intValue() | 8) & -17);
        }

        return this;
    }

    public Cell maxHeight(float maxHeight) {
        this.maxHeight = new Fixed(maxHeight);
        return this;
    }

    public Cell maxHeight(Value maxHeight) {
        if(maxHeight == null) {
            throw new IllegalArgumentException("maxHeight cannot be null.");
        }

        this.maxHeight = maxHeight;
        return this;
    }

    public Cell maxSize(float size) {
        this.maxSize(new Fixed(size));
        return this;
    }

    public Cell maxSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.maxWidth = size;
        this.maxHeight = size;
        return this;
    }

    public Cell maxSize(float width, float height) {
        this.maxSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Cell maxSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.maxWidth = width;
        this.maxHeight = height;
        return this;
    }

    public Cell maxWidth(float maxWidth) {
        this.maxWidth = new Fixed(maxWidth);
        return this;
    }

    public Cell maxWidth(Value maxWidth) {
        if(maxWidth == null) {
            throw new IllegalArgumentException("maxWidth cannot be null.");
        }

        this.maxWidth = maxWidth;
        return this;
    }

    void merge(Cell cell) {
        if(cell != null) {
            if(cell.minWidth != null) {
                this.minWidth = cell.minWidth;
            }

            if(cell.minHeight != null) {
                this.minHeight = cell.minHeight;
            }

            if(cell.prefWidth != null) {
                this.prefWidth = cell.prefWidth;
            }

            if(cell.prefHeight != null) {
                this.prefHeight = cell.prefHeight;
            }

            if(cell.maxWidth != null) {
                this.maxWidth = cell.maxWidth;
            }

            if(cell.maxHeight != null) {
                this.maxHeight = cell.maxHeight;
            }

            if(cell.spaceTop != null) {
                this.spaceTop = cell.spaceTop;
            }

            if(cell.spaceLeft != null) {
                this.spaceLeft = cell.spaceLeft;
            }

            if(cell.spaceBottom != null) {
                this.spaceBottom = cell.spaceBottom;
            }

            if(cell.spaceRight != null) {
                this.spaceRight = cell.spaceRight;
            }

            if(cell.padTop != null) {
                this.padTop = cell.padTop;
            }

            if(cell.padLeft != null) {
                this.padLeft = cell.padLeft;
            }

            if(cell.padBottom != null) {
                this.padBottom = cell.padBottom;
            }

            if(cell.padRight != null) {
                this.padRight = cell.padRight;
            }

            if(cell.fillX != null) {
                this.fillX = cell.fillX;
            }

            if(cell.fillY != null) {
                this.fillY = cell.fillY;
            }

            if(cell.align != null) {
                this.align = cell.align;
            }

            if(cell.expandX != null) {
                this.expandX = cell.expandX;
            }

            if(cell.expandY != null) {
                this.expandY = cell.expandY;
            }

            if(cell.colspan != null) {
                this.colspan = cell.colspan;
            }

            if(cell.uniformX != null) {
                this.uniformX = cell.uniformX;
            }

            if(cell.uniformY == null) {
                return;
            }

            this.uniformY = cell.uniformY;
        }
    }

    public Cell minHeight(float minHeight) {
        this.minHeight = new Fixed(minHeight);
        return this;
    }

    public Cell minHeight(Value minHeight) {
        if(minHeight == null) {
            throw new IllegalArgumentException("minHeight cannot be null.");
        }

        this.minHeight = minHeight;
        return this;
    }

    public Cell minSize(float size) {
        this.minSize(new Fixed(size));
        return this;
    }

    public Cell minSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.minWidth = size;
        this.minHeight = size;
        return this;
    }

    public Cell minSize(float width, float height) {
        this.minSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Cell minSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minWidth = width;
        this.minHeight = height;
        return this;
    }

    public Cell minWidth(float minWidth) {
        this.minWidth = new Fixed(minWidth);
        return this;
    }

    public Cell minWidth(Value minWidth) {
        if(minWidth == null) {
            throw new IllegalArgumentException("minWidth cannot be null.");
        }

        this.minWidth = minWidth;
        return this;
    }

    public Cell pad(float pad) {
        this.pad(new Fixed(pad));
        return this;
    }

    public Cell pad(Value pad) {
        if(pad == null) {
            throw new IllegalArgumentException("pad cannot be null.");
        }

        this.padTop = pad;
        this.padLeft = pad;
        this.padBottom = pad;
        this.padRight = pad;
        return this;
    }

    public Cell pad(float top, float left, float bottom, float right) {
        this.pad(new Fixed(top), new Fixed(left), new Fixed(bottom), new Fixed(right));
        return this;
    }

    public Cell pad(Value top, Value left, Value bottom, Value right) {
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
        return this;
    }

    public Cell padBottom(float padBottom) {
        this.padBottom = new Fixed(padBottom);
        return this;
    }

    public Cell padBottom(Value padBottom) {
        if(padBottom == null) {
            throw new IllegalArgumentException("padBottom cannot be null.");
        }

        this.padBottom = padBottom;
        return this;
    }

    public Cell padLeft(float padLeft) {
        this.padLeft = new Fixed(padLeft);
        return this;
    }

    public Cell padLeft(Value padLeft) {
        if(padLeft == null) {
            throw new IllegalArgumentException("padLeft cannot be null.");
        }

        this.padLeft = padLeft;
        return this;
    }

    public Cell padRight(float padRight) {
        this.padRight = new Fixed(padRight);
        return this;
    }

    public Cell padRight(Value padRight) {
        if(padRight == null) {
            throw new IllegalArgumentException("padRight cannot be null.");
        }

        this.padRight = padRight;
        return this;
    }

    public Cell padTop(float padTop) {
        this.padTop = new Fixed(padTop);
        return this;
    }

    public Cell padTop(Value padTop) {
        if(padTop == null) {
            throw new IllegalArgumentException("padTop cannot be null.");
        }

        this.padTop = padTop;
        return this;
    }

    public Cell prefHeight(float prefHeight) {
        this.prefHeight = new Fixed(prefHeight);
        return this;
    }

    public Cell prefHeight(Value prefHeight) {
        if(prefHeight == null) {
            throw new IllegalArgumentException("prefHeight cannot be null.");
        }

        this.prefHeight = prefHeight;
        return this;
    }

    public Cell prefSize(float size) {
        this.prefSize(new Fixed(size));
        return this;
    }

    public Cell prefSize(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.prefWidth = size;
        this.prefHeight = size;
        return this;
    }

    public Cell prefSize(float width, float height) {
        this.prefSize(new Fixed(width), new Fixed(height));
        return this;
    }

    public Cell prefSize(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.prefWidth = width;
        this.prefHeight = height;
        return this;
    }

    public Cell prefWidth(float prefWidth) {
        this.prefWidth = new Fixed(prefWidth);
        return this;
    }

    public Cell prefWidth(Value prefWidth) {
        if(prefWidth == null) {
            throw new IllegalArgumentException("prefWidth cannot be null.");
        }

        this.prefWidth = prefWidth;
        return this;
    }

    public void reset() {
        this.actor = null;
        this.table = null;
        this.endRow = false;
        this.cellAboveIndex = -1;
        this.minWidth = Value.minWidth;
        this.minHeight = Value.minHeight;
        this.prefWidth = Value.prefWidth;
        this.prefHeight = Value.prefHeight;
        this.maxWidth = Value.maxWidth;
        this.maxHeight = Value.maxHeight;
        this.spaceTop = Value.zero;
        this.spaceLeft = Value.zero;
        this.spaceBottom = Value.zero;
        this.spaceRight = Value.zero;
        this.padTop = Value.zero;
        this.padLeft = Value.zero;
        this.padBottom = Value.zero;
        this.padRight = Value.zero;
        this.fillX = Cell.zerof;
        this.fillY = Cell.zerof;
        this.align = Cell.centeri;
        this.expandX = Cell.zeroi;
        this.expandY = Cell.zeroi;
        this.colspan = Cell.onei;
        this.uniformX = null;
        this.uniformY = null;
    }

    public Cell right() {
        if(this.align == null) {
            this.align = Cell.righti;
        }
        else {
            this.align = Integer.valueOf((this.align.intValue() | 16) & -9);
        }

        return this;
    }

    public void row() {
        this.table.row();
    }

    void set(Cell defaults) {
        this.minWidth = defaults.minWidth;
        this.minHeight = defaults.minHeight;
        this.prefWidth = defaults.prefWidth;
        this.prefHeight = defaults.prefHeight;
        this.maxWidth = defaults.maxWidth;
        this.maxHeight = defaults.maxHeight;
        this.spaceTop = defaults.spaceTop;
        this.spaceLeft = defaults.spaceLeft;
        this.spaceBottom = defaults.spaceBottom;
        this.spaceRight = defaults.spaceRight;
        this.padTop = defaults.padTop;
        this.padLeft = defaults.padLeft;
        this.padBottom = defaults.padBottom;
        this.padRight = defaults.padRight;
        this.fillX = defaults.fillX;
        this.fillY = defaults.fillY;
        this.align = defaults.align;
        this.expandX = defaults.expandX;
        this.expandY = defaults.expandY;
        this.colspan = defaults.colspan;
        this.uniformX = defaults.uniformX;
        this.uniformY = defaults.uniformY;
    }

    public Cell setActor(Actor arg2) {
        if(this.actor != arg2) {
            if(this.actor != null) {
                this.actor.remove();
            }

            this.actor = arg2;
            if(arg2 == null) {
                goto label_10;
            }

            this.table.addActor(arg2);
        }

    label_10:
        return this;
    }

    public void setActorBounds(float x, float y, float width, float height) {
        this.actorX = x;
        this.actorY = y;
        this.actorWidth = width;
        this.actorHeight = height;
    }

    public void setActorHeight(float actorHeight) {
        this.actorHeight = actorHeight;
    }

    public void setActorWidth(float actorWidth) {
        this.actorWidth = actorWidth;
    }

    public void setActorX(float actorX) {
        this.actorX = actorX;
    }

    public void setActorY(float actorY) {
        this.actorY = actorY;
    }

    public void setLayout(Table table) {
        this.table = table;
    }

    public Cell size(float size) {
        this.size(new Fixed(size));
        return this;
    }

    public Cell size(Value size) {
        if(size == null) {
            throw new IllegalArgumentException("size cannot be null.");
        }

        this.minWidth = size;
        this.minHeight = size;
        this.prefWidth = size;
        this.prefHeight = size;
        this.maxWidth = size;
        this.maxHeight = size;
        return this;
    }

    public Cell size(float width, float height) {
        this.size(new Fixed(width), new Fixed(height));
        return this;
    }

    public Cell size(Value width, Value height) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        if(height == null) {
            throw new IllegalArgumentException("height cannot be null.");
        }

        this.minWidth = width;
        this.minHeight = height;
        this.prefWidth = width;
        this.prefHeight = height;
        this.maxWidth = width;
        this.maxHeight = height;
        return this;
    }

    public Cell space(float space) {
        if(space < 0f) {
            throw new IllegalArgumentException("space cannot be < 0.");
        }

        this.space(new Fixed(space));
        return this;
    }

    public Cell space(Value space) {
        if(space == null) {
            throw new IllegalArgumentException("space cannot be null.");
        }

        this.spaceTop = space;
        this.spaceLeft = space;
        this.spaceBottom = space;
        this.spaceRight = space;
        return this;
    }

    public Cell space(float top, float left, float bottom, float right) {
        if(top < 0f) {
            throw new IllegalArgumentException("top cannot be < 0.");
        }

        if(left < 0f) {
            throw new IllegalArgumentException("left cannot be < 0.");
        }

        if(bottom < 0f) {
            throw new IllegalArgumentException("bottom cannot be < 0.");
        }

        if(right < 0f) {
            throw new IllegalArgumentException("right cannot be < 0.");
        }

        this.space(new Fixed(top), new Fixed(left), new Fixed(bottom), new Fixed(right));
        return this;
    }

    public Cell space(Value top, Value left, Value bottom, Value right) {
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

        this.spaceTop = top;
        this.spaceLeft = left;
        this.spaceBottom = bottom;
        this.spaceRight = right;
        return this;
    }

    public Cell spaceBottom(float spaceBottom) {
        if(spaceBottom < 0f) {
            throw new IllegalArgumentException("spaceBottom cannot be < 0.");
        }

        this.spaceBottom = new Fixed(spaceBottom);
        return this;
    }

    public Cell spaceBottom(Value spaceBottom) {
        if(spaceBottom == null) {
            throw new IllegalArgumentException("spaceBottom cannot be null.");
        }

        this.spaceBottom = spaceBottom;
        return this;
    }

    public Cell spaceLeft(float spaceLeft) {
        if(spaceLeft < 0f) {
            throw new IllegalArgumentException("spaceLeft cannot be < 0.");
        }

        this.spaceLeft = new Fixed(spaceLeft);
        return this;
    }

    public Cell spaceLeft(Value spaceLeft) {
        if(spaceLeft == null) {
            throw new IllegalArgumentException("spaceLeft cannot be null.");
        }

        this.spaceLeft = spaceLeft;
        return this;
    }

    public Cell spaceRight(float spaceRight) {
        if(spaceRight < 0f) {
            throw new IllegalArgumentException("spaceRight cannot be < 0.");
        }

        this.spaceRight = new Fixed(spaceRight);
        return this;
    }

    public Cell spaceRight(Value spaceRight) {
        if(spaceRight == null) {
            throw new IllegalArgumentException("spaceRight cannot be null.");
        }

        this.spaceRight = spaceRight;
        return this;
    }

    public Cell spaceTop(float spaceTop) {
        if(spaceTop < 0f) {
            throw new IllegalArgumentException("spaceTop cannot be < 0.");
        }

        this.spaceTop = new Fixed(spaceTop);
        return this;
    }

    public Cell spaceTop(Value spaceTop) {
        if(spaceTop == null) {
            throw new IllegalArgumentException("spaceTop cannot be null.");
        }

        this.spaceTop = spaceTop;
        return this;
    }

    public Cell top() {
        if(this.align == null) {
            this.align = Cell.topi;
        }
        else {
            this.align = Integer.valueOf((this.align.intValue() | 2) & -5);
        }

        return this;
    }

    public Cell uniform() {
        this.uniformX = Boolean.TRUE;
        this.uniformY = Boolean.TRUE;
        return this;
    }

    public Cell uniform(boolean x, boolean y) {
        this.uniformX = Boolean.valueOf(x);
        this.uniformY = Boolean.valueOf(y);
        return this;
    }

    public Cell uniformX() {
        this.uniformX = Boolean.TRUE;
        return this;
    }

    public Cell uniformY() {
        this.uniformY = Boolean.TRUE;
        return this;
    }

    public Cell width(float width) {
        this.width(new Fixed(width));
        return this;
    }

    public Cell width(Value width) {
        if(width == null) {
            throw new IllegalArgumentException("width cannot be null.");
        }

        this.minWidth = width;
        this.prefWidth = width;
        this.maxWidth = width;
        return this;
    }
}

