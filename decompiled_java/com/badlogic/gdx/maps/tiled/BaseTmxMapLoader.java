// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader$Element;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public abstract class BaseTmxMapLoader extends AsynchronousAssetLoader {
    public class Parameters extends AssetLoaderParameters {
        public boolean convertObjectToTileSpace;
        public boolean flipY;
        public boolean generateMipMaps;
        public TextureFilter textureMagFilter;
        public TextureFilter textureMinFilter;

        public Parameters() {
            super();
            this.generateMipMaps = false;
            this.textureMinFilter = TextureFilter.Nearest;
            this.textureMagFilter = TextureFilter.Nearest;
            this.convertObjectToTileSpace = false;
            this.flipY = true;
        }
    }

    protected static final int FLAG_FLIP_DIAGONALLY = 536870912;
    protected static final int FLAG_FLIP_HORIZONTALLY = -2147483648;
    protected static final int FLAG_FLIP_VERTICALLY = 1073741824;
    protected static final int MASK_CLEAR = -536870912;
    protected boolean convertObjectToTileSpace;
    protected boolean flipY;
    protected TiledMap map;
    protected int mapHeightInPixels;
    protected int mapTileHeight;
    protected int mapTileWidth;
    protected int mapWidthInPixels;
    protected Element root;
    protected XmlReader xml;

    public BaseTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.xml = new XmlReader();
        this.flipY = true;
    }

    protected Cell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
        int v2 = 3;
        Cell v0 = new Cell();
        if(flipDiagonally) {
            if((flipHorizontally) && (flipVertically)) {
                v0.setFlipHorizontally(true);
                v0.setRotation(v2);
                goto label_8;
            }

            if(!flipHorizontally) {
                goto label_12;
            }

            v0.setRotation(v2);
            goto label_8;
        label_12:
            if(!flipVertically) {
                goto label_15;
            }

            v0.setRotation(1);
            goto label_8;
        label_15:
            v0.setFlipVertically(true);
            v0.setRotation(v2);
        }
        else {
            v0.setFlipHorizontally(flipHorizontally);
            v0.setFlipVertically(flipVertically);
        }

    label_8:
        return v0;
    }

    protected static FileHandle getRelativeFileHandle(FileHandle file, String path) {
        StringTokenizer v2 = new StringTokenizer(path, "\\/");
        FileHandle v0;
        for(v0 = file.parent(); true; v0 = v0.child(v1)) {
            if(!v2.hasMoreElements()) {
                break;
            }

            String v1 = v2.nextToken();
            if(!v1.equals("..")) {
                goto label_11;
            }

            v0 = v0.parent();
            continue;
        label_11:
        }

        return v0;
    }

    public static int[] getTileIds(Element element, int width, int height) {  // has try-catch handlers
        int v16;
        InflaterInputStream v11_3;
        String v4;
        Element v6 = element.getChildByName("data");
        String v8 = v6.getAttribute("encoding", null);
        if(v8 == null) {
            throw new GdxRuntimeException("Unsupported encoding (XML) for TMX Layer Data");
        }

        int[] v10 = new int[width * height];
        if(v8.equals("csv")) {
            String[] v2 = v6.getText().split(",");
            int v9;
            for(v9 = 0; v9 < v2.length; ++v9) {
                v10[v9] = ((int)Long.parseLong(v2[v9].trim()));
            }
        }
        else if(v8.equals("base64")) {
            Closeable v11 = null;
            try {
                v4 = v6.getAttribute("compression", null);
                byte[] v3 = Base64Coder.decode(v6.getText());
                if(v4 == null) {
                    ByteArrayInputStream v11_1 = new ByteArrayInputStream(v3);
                }
                else {
                    boolean v17_1 = v4.equals("gzip");
                    if(v17_1) {
                        GZIPInputStream v11_2 = new GZIPInputStream(new ByteArrayInputStream(v3), v3.length);
                    }
                    else {
                        v17_1 = v4.equals("zlib");
                        if(v17_1) {
                            v11_3 = new InflaterInputStream(new ByteArrayInputStream(v3));
                        }
                        else {
                            goto label_119;
                        }
                    }
                }

                byte[] v14 = new byte[4];
                v16 = 0;
                while(true) {
                label_56:
                    if(v16 >= height) {
                        goto label_156;
                    }

                    int v15;
                    for(v15 = 0; v15 < width; ++v15) {
                        int v13 = ((InputStream)v11_3).read(v14);
                        while(v13 < v14.length) {
                            int v5 = ((InputStream)v11_3).read(v14, v13, v14.length - v13);
                            if(v5 == -1) {
                                break;
                            }

                            v13 += v5;
                        }

                        if(v13 != v14.length) {
                            throw new GdxRuntimeException("Error Reading TMX Layer Data: Premature end of tile data");
                        }

                        v10[v16 * width + v15] = BaseTmxMapLoader.unsignedByteToInt(v14[0]) | BaseTmxMapLoader.unsignedByteToInt(v14[1]) << 8 | BaseTmxMapLoader.unsignedByteToInt(v14[2]) << 16 | BaseTmxMapLoader.unsignedByteToInt(v14[3]) << 24;
                    }
                }
            }
            catch(IOException v7) {
                goto label_83;
            }
            catch(Throwable v17) {
                goto label_92;
            }

            ++v16;
            goto label_56;
        label_156:
            StreamUtils.closeQuietly(((Closeable)v11_3));
        }
        else {
            goto label_158;
        }

        return v10;
        try {
        label_119:
            throw new GdxRuntimeException("Unrecognised compression (" + v4 + ") for TMX Layer Data");
        }
        catch(Throwable v17) {
        }
        catch(IOException v7) {
            try {
            label_83:
                throw new GdxRuntimeException("Error Reading TMX Layer Data - IOException: " + v7.getMessage());
            }
            catch(Throwable v17) {
            label_92:
                StreamUtils.closeQuietly(((Closeable)v11_3));
                throw v17;
            }
        }

    label_158:
        throw new GdxRuntimeException("Unrecognised encoding (" + v8 + ") for TMX Layer Data");
    }

    protected void loadBasicLayerInfo(MapLayer layer, Element element) {
        boolean v2 = true;
        String v0 = element.getAttribute("name", null);
        float v1 = Float.parseFloat(element.getAttribute("opacity", "1.0"));
        if(element.getIntAttribute("visible", 1) != 1) {
            v2 = false;
        }

        layer.setName(v0);
        layer.setOpacity(v1);
        layer.setVisible(v2);
    }

    protected void loadImageLayer(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        if(element.getName().equals("imagelayer")) {
            int v6 = Integer.parseInt(element.getAttribute("x", "0"));
            int v7 = Integer.parseInt(element.getAttribute("y", "0"));
            if(this.flipY) {
                v7 -= this.mapHeightInPixels;
            }

            TextureRegion v5 = null;
            Element v1 = element.getChildByName("image");
            if(v1 != null) {
                v5 = imageResolver.getImage(BaseTmxMapLoader.getRelativeFileHandle(tmxFile, v1.getAttribute("source")).path());
                v7 -= v5.getRegionHeight();
            }

            TiledMapImageLayer v2 = new TiledMapImageLayer(v5, v6, v7);
            this.loadBasicLayerInfo(((MapLayer)v2), element);
            Element v3 = element.getChildByName("properties");
            if(v3 != null) {
                this.loadProperties(v2.getProperties(), v3);
            }

            map.getLayers().add(((MapLayer)v2));
        }
    }

    protected void loadObject(TiledMap map, MapLayer layer, Element element) {
        boolean v28_3;
        boolean v6;
        boolean v5;
        int v10;
        PolylineMapObject v11_1;
        int v28_1;
        float v30;
        int v29;
        String[] v12;
        float[] v24;
        String[] v13;
        float v28;
        float v19;
        float v18;
        if(element.getName().equals("object")) {
            PolygonMapObject v11 = null;
            if(this.convertObjectToTileSpace) {
                v18 = 1f / (((float)this.mapTileWidth));
            }
            else {
                v18 = 1f;
            }

            if(this.convertObjectToTileSpace) {
                v19 = 1f / (((float)this.mapTileHeight));
            }
            else {
                v19 = 1f;
            }

            float v26 = element.getFloatAttribute("x", 0f) * v18;
            if(this.flipY) {
                v28 = (((float)this.mapHeightInPixels)) - element.getFloatAttribute("y", 0f);
            }
            else {
                v28 = element.getFloatAttribute("y", 0f);
            }

            float v27 = v28 * v19;
            float v25 = element.getFloatAttribute("width", 0f) * v18;
            float v8 = element.getFloatAttribute("height", 0f) * v19;
            if(element.getChildCount() > 0) {
                Element v4 = element.getChildByName("polygon");
                if(v4 != null) {
                    v13 = v4.getAttribute("points").split(" ");
                    v24 = new float[v13.length * 2];
                    int v9;
                    for(v9 = 0; v9 < v13.length; ++v9) {
                        v12 = v13[v9].split(",");
                        v24[v9 * 2] = Float.parseFloat(v12[0]) * v18;
                        v29 = v9 * 2 + 1;
                        v30 = Float.parseFloat(v12[1]) * v19;
                        if(this.flipY) {
                            v28_1 = -1;
                        }
                        else {
                            v28_1 = 1;
                        }

                        v24[v29] = (((float)v28_1)) * v30;
                    }

                    Polygon v14 = new Polygon(v24);
                    v14.setPosition(v26, v27);
                    v11 = new PolygonMapObject(v14);
                }
                else {
                    v4 = element.getChildByName("polyline");
                    if(v4 == null) {
                        goto label_352;
                    }

                    v13 = v4.getAttribute("points").split(" ");
                    v24 = new float[v13.length * 2];
                    for(v9 = 0; v9 < v13.length; ++v9) {
                        v12 = v13[v9].split(",");
                        v24[v9 * 2] = Float.parseFloat(v12[0]) * v18;
                        v29 = v9 * 2 + 1;
                        v30 = Float.parseFloat(v12[1]) * v19;
                        if(this.flipY) {
                            v28_1 = -1;
                        }
                        else {
                            v28_1 = 1;
                        }

                        v24[v29] = (((float)v28_1)) * v30;
                    }

                    Polyline v15 = new Polyline(v24);
                    v15.setPosition(v26, v27);
                    v11_1 = new PolylineMapObject(v15);
                    goto label_138;
                label_352:
                    if(element.getChildByName("ellipse") == null) {
                        goto label_138;
                    }

                    if(this.flipY) {
                        v28 = v27 - v8;
                    }
                    else {
                        v28 = v27;
                    }

                    EllipseMapObject v11_2 = new EllipseMapObject(v26, v28, v25, v8);
                }
            }

        label_138:
            if((((PolygonMapObject)v11_1)) == null) {
                String v7 = element.getAttribute("gid", null);
                if(v7 != null) {
                    v10 = ((int)Long.parseLong(v7));
                    if((-2147483648 & v10) != 0) {
                        v5 = true;
                    }
                    else {
                        v5 = false;
                    }

                    if((1073741824 & v10) != 0) {
                        v6 = true;
                    }
                    else {
                        v6 = false;
                    }

                    TextureRegion v21 = new TextureRegion(map.getTileSets().getTile(536870911 & v10).getTextureRegion());
                    v21.flip(v5, v6);
                    TextureMapObject v20 = new TextureMapObject(v21);
                    v20.getProperties().put("gid", Integer.valueOf(v10));
                    v20.setX(v26);
                    if(this.flipY) {
                        v28 = v27 - v8;
                    }
                    else {
                        v28 = v27;
                    }

                    v20.setY(v28);
                    v20.setScaleX(v18);
                    v20.setScaleY(v19);
                    v20.setRotation(element.getFloatAttribute("rotation", 0f));
                    TextureMapObject v11_3 = v20;
                }
                else {
                    if(this.flipY) {
                        v28 = v27 - v8;
                    }
                    else {
                        v28 = v27;
                    }

                    RectangleMapObject v11_4 = new RectangleMapObject(v26, v28, v25, v8);
                }
            }

            ((MapObject)v11_1).setName(element.getAttribute("name", null));
            String v17 = element.getAttribute("rotation", null);
            if(v17 != null) {
                ((MapObject)v11_1).getProperties().put("rotation", Float.valueOf(Float.parseFloat(v17)));
            }

            String v23 = element.getAttribute("type", null);
            if(v23 != null) {
                ((MapObject)v11_1).getProperties().put("type", v23);
            }

            v10 = element.getIntAttribute("id", 0);
            if(v10 != 0) {
                ((MapObject)v11_1).getProperties().put("id", Integer.valueOf(v10));
            }

            ((MapObject)v11_1).getProperties().put("x", Float.valueOf(v26 * v18));
            MapProperties v28_2 = ((MapObject)v11_1).getProperties();
            String v29_1 = "y";
            if(this.flipY) {
                v27 -= v8;
            }

            v28_2.put(v29_1, Float.valueOf(v27 * v19));
            ((MapObject)v11_1).getProperties().put("width", Float.valueOf(v25));
            ((MapObject)v11_1).getProperties().put("height", Float.valueOf(v8));
            if(element.getIntAttribute("visible", 1) == 1) {
                v28_3 = true;
            }
            else {
                v28_3 = false;
            }

            ((MapObject)v11_1).setVisible(v28_3);
            Element v16 = element.getChildByName("properties");
            if(v16 != null) {
                this.loadProperties(((MapObject)v11_1).getProperties(), v16);
            }

            layer.getObjects().add(((MapObject)v11_1));
        }
    }

    protected void loadObjectGroup(TiledMap map, Element element) {
        if(element.getName().equals("objectgroup")) {
            String v2 = element.getAttribute("name", null);
            MapLayer v1 = new MapLayer();
            v1.setName(v2);
            Element v4 = element.getChildByName("properties");
            if(v4 != null) {
                this.loadProperties(v1.getProperties(), v4);
            }

            Iterator v0 = element.getChildrenByName("object").iterator();
            while(v0.hasNext()) {
                this.loadObject(map, v1, v0.next());
            }

            map.getLayers().add(v1);
        }
    }

    protected void loadProperties(MapProperties properties, Element element) {
        String v6 = null;
        if(element != null && (element.getName().equals("properties"))) {
            Iterator v0 = element.getChildrenByName("property").iterator();
            while(v0.hasNext()) {
                Object v2 = v0.next();
                String v1 = ((Element)v2).getAttribute("name", v6);
                String v3 = ((Element)v2).getAttribute("value", v6);
                if(v3 == null) {
                    v3 = ((Element)v2).getText();
                }

                properties.put(v1, v3);
            }
        }
    }

    protected void loadTileLayer(TiledMap map, Element element) {
        int v19;
        boolean v4;
        boolean v6;
        boolean v5;
        if(element.getName().equals("layer")) {
            int v16 = element.getIntAttribute("width", 0);
            int v7 = element.getIntAttribute("height", 0);
            TiledMapTileLayer v10 = new TiledMapTileLayer(v16, v7, element.getParent().getIntAttribute("tilewidth", 0), element.getParent().getIntAttribute("tileheight", 0));
            this.loadBasicLayerInfo(((MapLayer)v10), element);
            int[] v9 = BaseTmxMapLoader.getTileIds(element, v16, v7);
            TiledMapTileSets v15 = map.getTileSets();
            int v18;
            for(v18 = 0; v18 < v7; ++v18) {
                int v17;
                for(v17 = 0; v17 < v16; ++v17) {
                    int v8 = v9[v18 * v16 + v17];
                    if((-2147483648 & v8) != 0) {
                        v5 = true;
                    }
                    else {
                        v5 = false;
                    }

                    if((1073741824 & v8) != 0) {
                        v6 = true;
                    }
                    else {
                        v6 = false;
                    }

                    if((536870912 & v8) != 0) {
                        v4 = true;
                    }
                    else {
                        v4 = false;
                    }

                    TiledMapTile v12 = v15.getTile(536870911 & v8);
                    if(v12 != null) {
                        Cell v3 = this.createTileLayerCell(v5, v6, v4);
                        v3.setTile(v12);
                        if(this.flipY) {
                            v19 = v7 - 1 - v18;
                        }
                        else {
                            v19 = v18;
                        }

                        v10.setCell(v17, v19, v3);
                    }
                }
            }

            Element v11 = element.getChildByName("properties");
            if(v11 != null) {
                this.loadProperties(v10.getProperties(), v11);
            }

            map.getLayers().add(((MapLayer)v10));
        }
    }

    protected static int unsignedByteToInt(byte b) {
        return b & 255;
    }
}

