// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import java.util.Iterator;

public class PixmapPacker implements Disposable {
    final class Node {
        public Node() {
            super();
            this.rect = new Rectangle();
        }

        public Node(int x, int y, int width, int height, Node leftChild, Node rightChild, String leaveName) {
            super();
            this.rect = new Rectangle(((float)x), ((float)y), ((float)width), ((float)height));
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.leaveName = leaveName;
        }
    }

    public class Page {
        final Array addedRects;
        Pixmap image;
        OrderedMap rects;
        Node root;
        Texture texture;

        public Page(PixmapPacker arg2) {
            PixmapPacker.this = arg2;
            super();
            this.addedRects = new Array();
        }

        public Pixmap getPixmap() {
            return this.image;
        }

        public OrderedMap getRects() {
            return this.rects;
        }
    }

    Page currPage;
    boolean disposed;
    final boolean duplicateBorder;
    final int padding;
    final Format pageFormat;
    final int pageHeight;
    final int pageWidth;
    final Array pages;

    public PixmapPacker(int width, int height, Format format, int padding, boolean duplicateBorder) {
        super();
        this.pages = new Array();
        this.pageWidth = width;
        this.pageHeight = height;
        this.pageFormat = format;
        this.padding = padding;
        this.duplicateBorder = duplicateBorder;
        this.newPage();
    }

    public void dispose() {  // has try-catch handlers
        try {
            Iterator v0 = this.pages.iterator();
            while(v0.hasNext()) {
                v0.next().image.dispose();
            }

            this.disposed = true;
            return;
        }
        catch(Throwable v2) {
            throw v2;
        }
    }

    public boolean duplicateBorder() {
        return this.duplicateBorder;
    }

    public TextureAtlas generateTextureAtlas(TextureFilter minFilter, TextureFilter magFilter, boolean useMipMaps) {  // has try-catch handlers
        Iterator v10;
        com.badlogic.gdx.graphics.g2d.PixmapPacker$1 v15;
        Object v13;
        TextureAtlas v8;
        try {
            v8 = new TextureAtlas();
            Iterator v9 = this.pages.iterator();
            do {
            label_4:
                if(!v9.hasNext()) {
                    goto label_44;
                }

                v13 = v9.next();
            }
            while(((Page)v13).rects.size == 0);

            v15 = new Texture() {
                public void dispose() {
                    super.dispose();
                    this.getTextureData().consumePixmap().dispose();
                }
            };
            ((Texture)v15).setFilter(minFilter, magFilter);
            v10 = ((Page)v13).rects.keys().iterator();
            goto label_25;
        }
        catch(Throwable v3) {
            goto label_43;
        }

    label_44:
        return v8;
        try {
            while(true) {
            label_25:
                if(!v10.hasNext()) {
                    goto label_4;
                }

                Object v11 = v10.next();
                Object v14 = ((Page)v13).rects.get(v11);
                v8.addRegion(((String)v11), new TextureRegion(v15, ((int)((Rectangle)v14).x), ((int)((Rectangle)v14).y), ((int)((Rectangle)v14).width), ((int)((Rectangle)v14).height)));
            }
        }
        catch(Throwable v3) {
        label_43:
            throw v3;
        }
    }

    public int getPadding() {
        return this.padding;
    }

    public Page getPage(String name) {  // has try-catch handlers
        try {
            Iterator v0 = this.pages.iterator();
            do {
                if(v0.hasNext()) {
                    Object v1 = v0.next();
                    if(((Page)v1).rects.get(name) == null) {
                        continue;
                    }
                }
                else {
                    break;
                }

                goto label_8;
            }
            while(true);
        }
        catch(Throwable v3) {
            goto label_12;
        }

        Page v1_1 = null;
    label_8:
        return v1_1;
    label_12:
        throw v3;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public int getPageIndex(String name) {  // has try-catch handlers
        int v0 = 0;
        try {
            while(true) {
            label_1:
                if(v0 >= this.pages.size) {
                    goto label_12;
                }
                else if(this.pages.get(v0).rects.get(name) == null) {
                    break;
                }

                goto label_9;
            }
        }
        catch(Throwable v2) {
            throw v2;
        }

        ++v0;
        goto label_1;
    label_12:
        v0 = -1;
    label_9:
        return v0;
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public Array getPages() {
        return this.pages;
    }

    public Rectangle getRect(String name) {  // has try-catch handlers
        try {
            Iterator v0 = this.pages.iterator();
            do {
                if(v0.hasNext()) {
                    Object v2 = v0.next().rects.get(name);
                    if(v2 == null) {
                        continue;
                    }
                }
                else {
                    break;
                }

                goto label_8;
            }
            while(true);
        }
        catch(Throwable v3) {
            goto label_12;
        }

        Rectangle v2_1 = null;
    label_8:
        return v2_1;
    label_12:
        throw v3;
    }

    private Node insert(Node node, Rectangle rect) {
        Node v2 = null;
        if(node.leaveName == null && node.leftChild != null && node.rightChild != null) {
            v2 = this.insert(node.leftChild, rect);
            if(v2 == null) {
                v2 = this.insert(node.rightChild, rect);
            }
        }
        else if(node.leaveName == null) {
            if(node.rect.width == rect.width && node.rect.height == rect.height) {
                v2 = node;
                goto label_13;
            }

            if(node.rect.width < rect.width) {
                goto label_13;
            }

            if(node.rect.height < rect.height) {
                goto label_13;
            }

            node.leftChild = new Node();
            node.rightChild = new Node();
            if((((int)node.rect.width)) - (((int)rect.width)) > (((int)node.rect.height)) - (((int)rect.height))) {
                node.leftChild.rect.x = node.rect.x;
                node.leftChild.rect.y = node.rect.y;
                node.leftChild.rect.width = rect.width;
                node.leftChild.rect.height = node.rect.height;
                node.rightChild.rect.x = node.rect.x + rect.width;
                node.rightChild.rect.y = node.rect.y;
                node.rightChild.rect.width = node.rect.width - rect.width;
                node.rightChild.rect.height = node.rect.height;
            }
            else {
                node.leftChild.rect.x = node.rect.x;
                node.leftChild.rect.y = node.rect.y;
                node.leftChild.rect.width = node.rect.width;
                node.leftChild.rect.height = rect.height;
                node.rightChild.rect.x = node.rect.x;
                node.rightChild.rect.y = node.rect.y + rect.height;
                node.rightChild.rect.width = node.rect.width;
                node.rightChild.rect.height = node.rect.height - rect.height;
            }

            v2 = this.insert(node.leftChild, rect);
        }

    label_13:
        return v2;
    }

    private void newPage() {
        Page v8 = new Page(this);
        v8.image = new Pixmap(this.pageWidth, this.pageHeight, this.pageFormat);
        v8.root = new Node(0, 0, this.pageWidth, this.pageHeight, null, null, null);
        v8.rects = new OrderedMap();
        this.pages.add(v8);
        this.currPage = v8;
    }

    public Rectangle pack(String name, Pixmap image) {  // has try-catch handlers
        int v2_1;
        try {
            if(this.disposed) {
            }
            else {
                goto label_5;
            }
        }
        catch(Throwable v2) {
            goto label_18;
        }

        Rectangle v17 = null;
        goto label_4;
        try {
        label_5:
            if(this.getRect(name) != null) {
                throw new RuntimeException("Key with name \'" + name + "\' is already in map");
            }
            else {
                int v3 = this.padding;
                if(this.duplicateBorder) {
                    v2_1 = 1;
                }
                else {
                    v2_1 = 0;
                }

                int v13 = v3 + v2_1 << 1;
                v17 = new Rectangle(0f, 0f, ((float)(image.getWidth() + v13)), ((float)(image.getHeight() + v13)));
                if(v17.getWidth() <= (((float)this.pageWidth)) && v17.getHeight() <= (((float)this.pageHeight))) {
                    Node v16 = this.insert(this.currPage.root, v17);
                    if(v16 == null) {
                        this.newPage();
                        v17 = this.pack(name, image);
                        goto label_4;
                    }
                    else {
                        v16.leaveName = name;
                        v17 = new Rectangle(v16.rect);
                        v17.width -= ((float)v13);
                        v17.height -= ((float)v13);
                        v13 >>= 1;
                        v17.x += ((float)v13);
                        v17.y += ((float)v13);
                        this.currPage.rects.put(name, v17);
                        Blending v12 = Pixmap.getBlending();
                        Pixmap.setBlending(Blending.None);
                        this.currPage.image.drawPixmap(image, ((int)v17.x), ((int)v17.y));
                        if(this.duplicateBorder) {
                            int v15 = image.getWidth();
                            int v14 = image.getHeight();
                            this.currPage.image.drawPixmap(image, 0, 0, 1, 1, (((int)v17.x)) - 1, (((int)v17.y)) - 1, 1, 1);
                            this.currPage.image.drawPixmap(image, v15 - 1, 0, 1, 1, (((int)v17.width)) + (((int)v17.x)), (((int)v17.y)) - 1, 1, 1);
                            this.currPage.image.drawPixmap(image, 0, v14 - 1, 1, 1, (((int)v17.x)) - 1, (((int)v17.height)) + (((int)v17.y)), 1, 1);
                            this.currPage.image.drawPixmap(image, v15 - 1, v14 - 1, 1, 1, (((int)v17.width)) + (((int)v17.x)), (((int)v17.height)) + (((int)v17.y)), 1, 1);
                            this.currPage.image.drawPixmap(image, 0, 0, v15, 1, ((int)v17.x), (((int)v17.y)) - 1, ((int)v17.width), 1);
                            this.currPage.image.drawPixmap(image, 0, v14 - 1, v15, 1, ((int)v17.x), (((int)v17.y)) + (((int)v17.height)), ((int)v17.width), 1);
                            this.currPage.image.drawPixmap(image, 0, 0, 1, v14, (((int)v17.x)) - 1, ((int)v17.y), 1, ((int)v17.height));
                            this.currPage.image.drawPixmap(image, v15 - 1, 0, 1, v14, (((int)v17.x)) + (((int)v17.width)), ((int)v17.y), 1, ((int)v17.height));
                        }

                        Pixmap.setBlending(v12);
                        this.currPage.addedRects.add(name);
                        goto label_4;
                    }
                }

                goto label_48;
            }
        }
        catch(Throwable v2) {
            goto label_18;
        }

    label_4:
        return v17;
        try {
        label_48:
            throw new GdxRuntimeException("page size for \'" + name + "\' to small");
        }
        catch(Throwable v2) {
        label_18:
            throw v2;
        }
    }

    public void updateTextureAtlas(TextureAtlas atlas, TextureFilter minFilter, TextureFilter magFilter, boolean useMipMaps) {  // has try-catch handlers
        Object v10;
        Object v8;
        Iterator v7;
        Object v9;
        try {
            Iterator v6 = this.pages.iterator();
            do {
            label_2:
                if(!v6.hasNext()) {
                    return;
                }

                v9 = v6.next();
                if(((Page)v9).texture != null) {
                    continue;
                }

                if(((Page)v9).rects.size == 0) {
                    goto label_2;
                }

                if(((Page)v9).addedRects.size <= 0) {
                    goto label_2;
                }

                ((Page)v9).texture = new Texture() {
                    public void dispose() {
                        super.dispose();
                        this.getTextureData().consumePixmap().dispose();
                    }
                };
                ((Page)v9).texture.setFilter(minFilter, magFilter);
                v7 = ((Page)v9).addedRects.iterator();
                goto label_26;
            }
            while(((Page)v9).addedRects.size <= 0);

            ((Page)v9).texture.load(((Page)v9).texture.getTextureData());
            v7 = ((Page)v9).addedRects.iterator();
            goto label_57;
        }
        catch(Throwable v1) {
            goto label_44;
        }

        return;
        try {
            while(true) {
            label_57:
                if(!v7.hasNext()) {
                    break;
                }

                v8 = v7.next();
                v10 = ((Page)v9).rects.get(v8);
                atlas.addRegion(((String)v8), new TextureRegion(((Page)v9).texture, ((int)((Rectangle)v10).x), ((int)((Rectangle)v10).y), ((int)((Rectangle)v10).width), ((int)((Rectangle)v10).height)));
            }

            ((Page)v9).addedRects.clear();
            goto label_2;
            while(true) {
            label_26:
                if(!v7.hasNext()) {
                    break;
                }

                v8 = v7.next();
                v10 = ((Page)v9).rects.get(v8);
                atlas.addRegion(((String)v8), new TextureRegion(((Page)v9).texture, ((int)((Rectangle)v10).x), ((int)((Rectangle)v10).y), ((int)((Rectangle)v10).width), ((int)((Rectangle)v10).height)));
            }

            ((Page)v9).addedRects.clear();
            goto label_2;
        }
        catch(Throwable v1) {
        label_44:
            throw v1;
        }
    }
}

