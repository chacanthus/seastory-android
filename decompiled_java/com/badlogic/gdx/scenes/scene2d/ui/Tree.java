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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;

public class Tree extends WidgetGroup {
    public class Node {
        Actor actor;
        final Array children;
        boolean expanded;
        float height;
        Drawable icon;
        Object object;
        Node parent;
        boolean selectable;

        public Node(Actor actor) {
            super();
            this.children = new Array(0);
            this.selectable = true;
            if(actor == null) {
                throw new IllegalArgumentException("actor cannot be null.");
            }

            this.actor = actor;
        }

        public void add(Node node) {
            this.insert(this.children.size, node);
        }

        public void addAll(Array arg5) {
            int v0 = 0;
            int v1 = arg5.size;
            while(v0 < v1) {
                this.insert(this.children.size, arg5.get(v0));
                ++v0;
            }
        }

        protected void addToTree(Tree tree) {
            tree.addActor(this.actor);
            if(this.expanded) {
                int v0 = 0;
                int v1 = this.children.size;
                while(v0 < v1) {
                    this.children.get(v0).addToTree(tree);
                    ++v0;
                }
            }
        }

        public void collapseAll() {
            this.setExpanded(false);
            Tree.collapseAll(this.children);
        }

        public void expandAll() {
            this.setExpanded(true);
            if(this.children.size > 0) {
                Tree.expandAll(this.children);
            }
        }

        public void expandTo() {
            Node v0;
            for(v0 = this.parent; v0 != null; v0 = v0.parent) {
                v0.setExpanded(true);
            }
        }

        public void findExpandedObjects(Array objects) {
            if((this.expanded) && !Tree.findExpandedObjects(this.children, objects)) {
                objects.add(this.object);
            }
        }

        public Node findNode(Object object) {
            if(object == null) {
                throw new IllegalArgumentException("object cannot be null.");
            }

            if(!object.equals(this.object)) {
                this = Tree.findNode(this.children, object);
            }

            return this;
        }

        public Actor getActor() {
            return this.actor;
        }

        public Array getChildren() {
            return this.children;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public int getLevel() {
            int v1 = 0;
            Node v0 = this;
            do {
                ++v1;
                v0 = v0.getParent();
            }
            while(v0 != null);

            return v1;
        }

        public Object getObject() {
            return this.object;
        }

        public Node getParent() {
            return this.parent;
        }

        public Tree getTree() {
            Tree v0_1;
            Group v0 = this.actor.getParent();
            if(!(v0 instanceof Tree)) {
                v0_1 = null;
            }

            return v0_1;
        }

        public void insert(int index, Node node) {
            node.parent = this;
            this.children.insert(index, node);
            this.updateChildren();
        }

        public boolean isExpanded() {
            return this.expanded;
        }

        public boolean isSelectable() {
            return this.selectable;
        }

        public void remove(Node node) {
            this.children.removeValue(node, true);
            if(this.expanded) {
                Tree v0 = this.getTree();
                if(v0 != null) {
                    node.removeFromTree(v0);
                    if(this.children.size == 0) {
                        this.expanded = false;
                    }
                }
            }
        }

        public void remove() {
            Tree v0 = this.getTree();
            if(v0 != null) {
                v0.remove(this);
            }
            else if(this.parent != null) {
                this.parent.remove(this);
            }
        }

        public void removeAll() {
            Tree v2 = this.getTree();
            if(v2 != null) {
                int v0 = 0;
                int v1 = this.children.size;
                while(v0 < v1) {
                    this.children.get(v0).removeFromTree(v2);
                    ++v0;
                }
            }

            this.children.clear();
        }

        protected void removeFromTree(Tree tree) {
            tree.removeActor(this.actor);
            if(this.expanded) {
                int v0 = 0;
                int v1 = this.children.size;
                while(v0 < v1) {
                    this.children.get(v0).removeFromTree(tree);
                    ++v0;
                }
            }
        }

        public void restoreExpandedObjects(Array objects) {
            int v0 = 0;
            int v1 = objects.size;
            while(v0 < v1) {
                Node v2 = this.findNode(objects.get(v0));
                if(v2 != null) {
                    v2.setExpanded(true);
                    v2.expandTo();
                }

                ++v0;
            }
        }

        public void setExpanded(boolean expanded) {
            int v1;
            int v0;
            if(expanded != this.expanded) {
                this.expanded = expanded;
                if(this.children.size != 0) {
                    Tree v2 = this.getTree();
                    if(v2 != null) {
                        if(expanded) {
                            v0 = 0;
                            v1 = this.children.size;
                            while(v0 < v1) {
                                this.children.get(v0).addToTree(v2);
                                ++v0;
                            }
                        }
                        else {
                            v0 = 0;
                            v1 = this.children.size;
                            while(v0 < v1) {
                                this.children.get(v0).removeFromTree(v2);
                                ++v0;
                            }
                        }

                        v2.invalidateHierarchy();
                    }
                }
            }
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        public void updateChildren() {
            if(this.expanded) {
                Tree v2 = this.getTree();
                if(v2 != null) {
                    int v0 = 0;
                    int v1 = this.children.size;
                    while(v0 < v1) {
                        this.children.get(v0).addToTree(v2);
                        ++v0;
                    }
                }
            }
        }
    }

    public class TreeStyle {
        public TreeStyle() {
            super();
        }

        public TreeStyle(TreeStyle style) {
            super();
            this.plus = style.plus;
            this.minus = style.minus;
            this.selection = style.selection;
        }

        public TreeStyle(Drawable plus, Drawable minus, Drawable selection) {
            super();
            this.plus = plus;
            this.minus = minus;
            this.selection = selection;
        }
    }

    private ClickListener clickListener;
    private Node foundNode;
    float iconSpacingLeft;
    float iconSpacingRight;
    float indentSpacing;
    private float leftColumnWidth;
    Node overNode;
    float padding;
    private float prefHeight;
    private float prefWidth;
    final Array rootNodes;
    final Selection selection;
    private boolean sizeInvalid;
    TreeStyle style;
    float ySpacing;

    public Tree(Skin skin) {
        this(skin.get(TreeStyle.class));
    }

    public Tree(TreeStyle style) {
        super();
        this.rootNodes = new Array();
        this.ySpacing = 4f;
        this.iconSpacingLeft = 2f;
        this.iconSpacingRight = 2f;
        this.padding = 0f;
        this.sizeInvalid = true;
        this.selection = new Selection();
        this.selection.setActor(((Actor)this));
        this.selection.setMultiple(true);
        this.setStyle(style);
        this.initialize();
    }

    public Tree(Skin skin, String styleName) {
        this(skin.get(styleName, TreeStyle.class));
    }

    public void add(Node node) {
        this.insert(this.rootNodes.size, node);
    }

    public void clearChildren() {
        super.clearChildren();
        this.setOverNode(null);
        this.rootNodes.clear();
        this.selection.clear();
    }

    static void collapseAll(Array arg4) {
        int v0 = 0;
        int v1 = arg4.size;
        while(v0 < v1) {
            Object v2 = arg4.get(v0);
            ((Node)v2).setExpanded(false);
            Tree.collapseAll(((Node)v2).children);
            ++v0;
        }
    }

    public void collapseAll() {
        Tree.collapseAll(this.rootNodes);
    }

    private void computeSize() {
        this.sizeInvalid = false;
        this.prefWidth = this.style.plus.getMinWidth();
        this.prefWidth = Math.max(this.prefWidth, this.style.minus.getMinWidth());
        this.prefHeight = this.getHeight();
        this.leftColumnWidth = 0f;
        this.computeSize(this.rootNodes, this.indentSpacing);
        this.leftColumnWidth += this.iconSpacingLeft + this.padding;
        this.prefWidth += this.leftColumnWidth + this.padding;
        this.prefHeight -= this.getHeight();
    }

    private void computeSize(Array arg11, float indent) {
        float v7 = this.ySpacing;
        float v6 = this.iconSpacingLeft + this.iconSpacingRight;
        int v1 = 0;
        int v3 = arg11.size;
        while(v1 < v3) {
            Object v4 = arg11.get(v1);
            float v5 = indent + this.iconSpacingRight;
            Actor v0 = ((Node)v4).actor;
            if((v0 instanceof Layout)) {
                v5 += v0.getPrefWidth();
                ((Node)v4).height = v0.getPrefHeight();
                v0.pack();
            }
            else {
                v5 += v0.getWidth();
                ((Node)v4).height = v0.getHeight();
            }

            if(((Node)v4).icon != null) {
                v5 += ((Node)v4).icon.getMinWidth() + v6;
                ((Node)v4).height = Math.max(((Node)v4).height, ((Node)v4).icon.getMinHeight());
            }

            this.prefWidth = Math.max(this.prefWidth, v5);
            this.prefHeight -= ((Node)v4).height + v7;
            if(((Node)v4).expanded) {
                this.computeSize(((Node)v4).children, this.indentSpacing + indent);
            }

            ++v1;
        }
    }

    private void draw(Batch batch, Array arg21, float indent) {
        Drawable v5;
        Drawable v17 = this.style.plus;
        Drawable v14 = this.style.minus;
        float v4 = this.getX();
        float v18 = this.getY();
        int v12 = 0;
        int v15 = arg21.size;
        while(v12 < v15) {
            Object v16 = arg21.get(v12);
            Actor v11 = v16.actor;
            if((this.selection.contains(v16)) && this.style.selection != null) {
                this.style.selection.draw(batch, v4, v11.getY() + v18 - this.ySpacing / 2f, this.getWidth(), this.ySpacing + v16.height);
            }
            else if(v16 == this.overNode && this.style.over != null) {
                this.style.over.draw(batch, v4, v11.getY() + v18 - this.ySpacing / 2f, this.getWidth(), this.ySpacing + v16.height);
            }

            if(v16.icon != null) {
                float v13 = v11.getY() + (((float)Math.round((v16.height - v16.icon.getMinHeight()) / 2f)));
                batch.setColor(v11.getColor());
                v16.icon.draw(batch, v16.actor.getX() + v4 - this.iconSpacingRight - v16.icon.getMinWidth(), v18 + v13, v16.icon.getMinWidth(), v16.icon.getMinHeight());
                batch.setColor(Color.WHITE);
            }

            if(v16.children.size != 0) {
                if(v16.expanded) {
                    v5 = v14;
                }
                else {
                    v5 = v17;
                }

                v5.draw(batch, v4 + indent - this.iconSpacingLeft, v18 + (v11.getY() + (((float)Math.round((v16.height - v5.getMinHeight()) / 2f)))), v5.getMinWidth(), v5.getMinHeight());
                if(!v16.expanded) {
                    goto label_91;
                }

                this.draw(batch, v16.children, this.indentSpacing + indent);
            }

        label_91:
            ++v12;
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        Color v6 = this.getColor();
        batch.setColor(v6.r, v6.g, v6.b, v6.a * parentAlpha);
        if(this.style.background != null) {
            this.style.background.draw(batch, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }

        this.draw(batch, this.rootNodes, this.leftColumnWidth);
        super.draw(batch, parentAlpha);
    }

    static void expandAll(Array arg3) {
        int v0 = 0;
        int v1 = arg3.size;
        while(v0 < v1) {
            arg3.get(v0).expandAll();
            ++v0;
        }
    }

    public void expandAll() {
        Tree.expandAll(this.rootNodes);
    }

    static boolean findExpandedObjects(Array arg5, Array objects) {
        int v1 = 0;
        int v2 = arg5.size;
        while(v1 < v2) {
            Object v3 = arg5.get(v1);
            if((((Node)v3).expanded) && !Tree.findExpandedObjects(((Node)v3).children, objects)) {
                objects.add(((Node)v3).object);
            }

            ++v1;
        }

        return 0;
    }

    public void findExpandedObjects(Array objects) {
        Tree.findExpandedObjects(this.rootNodes, objects);
    }

    static Node findNode(Array arg5, Object object) {
        Node v3_1;
        int v1 = 0;
        int v2 = arg5.size;
        while(true) {
            if(v1 < v2) {
                Object v3 = arg5.get(v1);
                if(!object.equals(((Node)v3).object)) {
                    ++v1;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_7;
        }

        v1 = 0;
        v2 = arg5.size;
        while(true) {
            if(v1 < v2) {
                Node v0 = Tree.findNode(arg5.get(v1).children, object);
                if(v0 != null) {
                    v3_1 = v0;
                }
                else {
                    ++v1;
                    continue;
                }
            }
            else {
                break;
            }

            goto label_7;
        }

        v3_1 = null;
    label_7:
        return v3_1;
    }

    public Node findNode(Object object) {
        if(object == null) {
            throw new IllegalArgumentException("object cannot be null.");
        }

        return Tree.findNode(this.rootNodes, object);
    }

    public ClickListener getClickListener() {
        return this.clickListener;
    }

    public float getIndentSpacing() {
        return this.indentSpacing;
    }

    private float getNodeAt(Array arg7, float y, float rowY) {
        float v3 = -1f;
        int v0 = 0;
        int v1 = arg7.size;
        while(v0 < v1) {
            Object v2 = arg7.get(v0);
            if(y >= rowY - ((Node)v2).height - this.ySpacing && y < rowY) {
                this.foundNode = ((Node)v2);
                goto label_12;
            }

            rowY -= ((Node)v2).height + this.ySpacing;
            if(((Node)v2).expanded) {
                rowY = this.getNodeAt(((Node)v2).children, y, rowY);
                if(rowY != v3) {
                    goto label_22;
                }

                goto label_12;
            }

        label_22:
            ++v0;
        }

        v3 = rowY;
    label_12:
        return v3;
    }

    public Node getNodeAt(float y) {
        this.foundNode = null;
        this.getNodeAt(this.rootNodes, y, this.getHeight());
        return this.foundNode;
    }

    public Array getNodes() {
        return this.rootNodes;
    }

    public Node getOverNode() {
        return this.overNode;
    }

    public float getPrefHeight() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.prefHeight;
    }

    public float getPrefWidth() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        return this.prefWidth;
    }

    public Array getRootNodes() {
        return this.rootNodes;
    }

    public Selection getSelection() {
        return this.selection;
    }

    public TreeStyle getStyle() {
        return this.style;
    }

    private void initialize() {
        com.badlogic.gdx.scenes.scene2d.ui.Tree$1 v0 = new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                boolean v4;
                Node v2 = Tree.this.getNodeAt(y);
                if(v2 != null && v2 == Tree.this.getNodeAt(this.getTouchDownY())) {
                    if((Tree.this.selection.getMultiple()) && (Tree.this.selection.hasItems()) && (UIUtils.shift())) {
                        float v1 = Tree.this.selection.getLastSelected().actor.getY();
                        float v0 = v2.actor.getY();
                        if(!UIUtils.ctrl()) {
                            Tree.this.selection.clear();
                        }

                        if(v1 > v0) {
                            Tree.this.selectNodes(Tree.this.rootNodes, v0, v1);
                        }
                        else {
                            Tree.this.selectNodes(Tree.this.rootNodes, v1, v0);
                        }

                        Tree.this.selection.fireChangeEvent();
                        return;
                    }

                    if(v2.children.size > 0 && (!Tree.this.selection.getMultiple() || !UIUtils.ctrl())) {
                        float v3 = v2.actor.getX();
                        if(v2.icon != null) {
                            v3 -= Tree.this.iconSpacingRight + v2.icon.getMinWidth();
                        }

                        if(x >= v3) {
                            goto label_71;
                        }

                        if(!v2.expanded) {
                            v4 = true;
                        }
                        else {
                            v4 = false;
                        }

                        v2.setExpanded(v4);
                        return;
                    }

                label_71:
                    if(!v2.isSelectable()) {
                        return;
                    }

                    Tree.this.selection.choose(v2);
                }
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if(toActor == null || !toActor.isDescendantOf(Tree.this)) {
                    Tree.this.setOverNode(null);
                }
            }

            public boolean mouseMoved(InputEvent event, float x, float y) {
                Tree.this.setOverNode(Tree.this.getNodeAt(y));
                return 0;
            }
        };
        this.clickListener = ((ClickListener)v0);
        this.addListener(((EventListener)v0));
    }

    public void insert(int index, Node node) {
        this.remove(node);
        node.parent = null;
        this.rootNodes.insert(index, node);
        node.addToTree(this);
        this.invalidateHierarchy();
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    private float layout(Array arg9, float indent, float y) {
        float v5 = this.ySpacing;
        int v1 = 0;
        int v2 = arg9.size;
        while(v1 < v2) {
            Object v3 = arg9.get(v1);
            float v4 = indent;
            if(((Node)v3).icon != null) {
                v4 += ((Node)v3).icon.getMinWidth();
            }

            y -= ((Node)v3).height;
            ((Node)v3).actor.setPosition(v4, y);
            y -= v5;
            if(((Node)v3).expanded) {
                y = this.layout(((Node)v3).children, this.indentSpacing + indent, y);
            }

            ++v1;
        }

        return y;
    }

    public void layout() {
        if(this.sizeInvalid) {
            this.computeSize();
        }

        this.layout(this.rootNodes, this.leftColumnWidth + this.indentSpacing + this.iconSpacingRight, this.getHeight() - this.ySpacing / 2f);
    }

    public void remove(Node node) {
        if(node.parent != null) {
            node.parent.remove(node);
        }
        else {
            this.rootNodes.removeValue(node, true);
            node.removeFromTree(this);
            this.invalidateHierarchy();
        }
    }

    public void restoreExpandedObjects(Array objects) {
        int v0 = 0;
        int v1 = objects.size;
        while(v0 < v1) {
            Node v2 = this.findNode(objects.get(v0));
            if(v2 != null) {
                v2.setExpanded(true);
                v2.expandTo();
            }

            ++v0;
        }
    }

    void selectNodes(Array arg5, float low, float high) {
        int v0 = 0;
        int v1 = arg5.size;
        while(v0 < v1) {
            Object v2 = arg5.get(v0);
            if(((Node)v2).actor.getY() < low) {
                return;
            }

            if(((Node)v2).isSelectable()) {
                if(((Node)v2).actor.getY() <= high) {
                    this.selection.add(v2);
                }

                if(!((Node)v2).expanded) {
                    goto label_10;
                }

                this.selectNodes(((Node)v2).children, low, high);
            }

        label_10:
            ++v0;
        }
    }

    public void setIconSpacing(float left, float right) {
        this.iconSpacingLeft = left;
        this.iconSpacingRight = right;
    }

    public void setOverNode(Node overNode) {
        this.overNode = overNode;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public void setStyle(TreeStyle style) {
        this.style = style;
        this.indentSpacing = Math.max(style.plus.getMinWidth(), style.minus.getMinWidth()) + this.iconSpacingLeft;
    }

    public void setYSpacing(float ySpacing) {
        this.ySpacing = ySpacing;
    }
}

