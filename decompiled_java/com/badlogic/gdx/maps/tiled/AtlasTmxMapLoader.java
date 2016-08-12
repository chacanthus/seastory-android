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

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas$AtlasRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader$Element;
import java.io.IOException;
import java.util.Iterator;

public class AtlasTmxMapLoader extends BaseTmxMapLoader {
    abstract interface AtlasResolver {
        public abstract TextureAtlas getAtlas(String arg0);
    }

    public class AtlasTiledMapLoaderParameters extends Parameters {
        public boolean forceTextureFilters;

        public AtlasTiledMapLoaderParameters() {
            super();
            this.forceTextureFilters = false;
        }
    }

    protected Array trackedTextures;

    public AtlasTmxMapLoader() {
        super(new InternalFileHandleResolver());
        this.trackedTextures = new Array();
    }

    public AtlasTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.trackedTextures = new Array();
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((AtlasTiledMapLoaderParameters)x2));
    }

    public Array getDependencies(String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {  // has try-catch handlers
        Array v1 = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            Element v5 = this.root.getChildByName("properties");
            if(v5 != null) {
                Iterator v3 = v5.getChildrenByName("property").iterator();
                while(v3.hasNext()) {
                    Object v6 = v3.next();
                    String v4 = ((Element)v6).getAttribute("name");
                    String v7 = ((Element)v6).getAttribute("value");
                    if(!v4.startsWith("atlas")) {
                        continue;
                    }

                    v1.add(new AssetDescriptor(AtlasTmxMapLoader.getRelativeFileHandle(tmxFile, v7), TextureAtlas.class));
                }
            }
        }
        catch(IOException v2) {
            throw new GdxRuntimeException("Unable to parse .tmx file.");
        }

        return v1;
    }

    public TiledMap load(String fileName) {
        return this.load(fileName, new AtlasTiledMapLoaderParameters());
    }

    public TiledMap load(String fileName, AtlasTiledMapLoaderParameters parameter) {  // has try-catch handlers
        if(parameter != null) {
            try {
                this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
                this.flipY = parameter.flipY;
                goto label_5;
            label_27:
                this.convertObjectToTileSpace = false;
                this.flipY = true;
            label_5:
                FileHandle v6 = this.resolve(fileName);
                this.root = this.xml.parse(v6);
                ObjectMap v3 = new ObjectMap();
                FileHandle v1 = this.loadAtlas(this.root, v6);
                if(v1 == null) {
                    throw new GdxRuntimeException("Couldn\'t load atlas");
                }

                v3.put(v1.path(), new TextureAtlas(v1));
                TiledMap v5 = this.loadMap(this.root, v6, new DirectAtlasResolver(v3));
                v5.setOwnedResources(v3.values().toArray());
                this.setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
                return v5;
            }
            catch(IOException v4) {
                throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v4));
            }
        }
        else {
            goto label_27;
        }

        goto label_5;
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((AtlasTiledMapLoaderParameters)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {  // has try-catch handlers
        this.map = null;
        if(parameter != null) {
            this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
            this.flipY = parameter.flipY;
        }
        else {
            this.convertObjectToTileSpace = false;
            this.flipY = true;
        }

        try {
            this.map = this.loadMap(this.root, tmxFile, new AssetManagerAtlasResolver(manager));
            return;
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v0));
        }
    }

    protected FileHandle loadAtlas(Element root, FileHandle tmxFile) throws IOException {
        FileHandle v0;
        String v5;
        String v6 = null;
        Element v1 = root.getChildByName("properties");
        if(v1 != null) {
            Iterator v2 = v1.getChildrenByName("property").iterator();
            do {
                if(v2.hasNext()) {
                    Object v4 = v2.next();
                    String v3 = ((Element)v4).getAttribute("name", v6);
                    v5 = ((Element)v4).getAttribute("value", v6);
                    if(!v3.equals("atlas")) {
                        continue;
                    }

                    if(v5 == null) {
                        v5 = ((Element)v4).getText();
                    }

                    if(v5 == null) {
                        continue;
                    }

                    if(v5.length() == 0) {
                        continue;
                    }

                    break;
                }
                else {
                    goto label_24;
                }
            }
            while(true);

            v0 = AtlasTmxMapLoader.getRelativeFileHandle(tmxFile, v5);
        }
        else {
        label_24:
            v0 = tmxFile.sibling(tmxFile.nameWithoutExtension() + ".atlas");
            if(v0.exists()) {
                goto label_23;
            }

            v0 = ((FileHandle)v6);
        }

    label_23:
        return v0;
    }

    protected TiledMap loadMap(Element root, FileHandle tmxFile, AtlasResolver resolver) {
        TiledMap v7 = new TiledMap();
        String v10 = root.getAttribute("orientation", null);
        int v12 = root.getIntAttribute("width", 0);
        int v9 = root.getIntAttribute("height", 0);
        int v14 = root.getIntAttribute("tilewidth", 0);
        int v13 = root.getIntAttribute("tileheight", 0);
        String v8 = root.getAttribute("backgroundcolor", null);
        MapProperties v11 = v7.getProperties();
        if(v10 != null) {
            v11.put("orientation", v10);
        }

        v11.put("width", Integer.valueOf(v12));
        v11.put("height", Integer.valueOf(v9));
        v11.put("tilewidth", Integer.valueOf(v14));
        v11.put("tileheight", Integer.valueOf(v13));
        if(v8 != null) {
            v11.put("backgroundcolor", v8);
        }

        this.mapTileWidth = v14;
        this.mapTileHeight = v13;
        this.mapWidthInPixels = v12 * v14;
        this.mapHeightInPixels = v9 * v13;
        if(v10 != null && ("staggered".equals(v10)) && v9 > 1) {
            this.mapWidthInPixels += v14 / 2;
            this.mapHeightInPixels = this.mapHeightInPixels / 2 + v13 / 2;
        }

        int v5 = 0;
        int v6 = root.getChildCount();
        while(v5 < v6) {
            Element v3 = root.getChild(v5);
            String v4 = v3.getName();
            if(v4.equals("properties")) {
                this.loadProperties(v7.getProperties(), v3);
            }
            else if(v4.equals("tileset")) {
                this.loadTileset(v7, v3, tmxFile, resolver);
            }
            else if(v4.equals("layer")) {
                this.loadTileLayer(v7, v3);
            }
            else if(v4.equals("objectgroup")) {
                this.loadObjectGroup(v7, v3);
            }

            ++v5;
        }

        return v7;
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, AtlasTiledMapLoaderParameters parameter) {
        if(parameter != null) {
            this.setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        }

        return this.map;
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((AtlasTiledMapLoaderParameters)x3));
    }

    protected void loadTileset(TiledMap map, Element element, FileHandle tmxFile, AtlasResolver resolver) {  // has try-catch handlers
        Element v28;
        String v27;
        String v36;
        TiledMapTile v38_1;
        Object v39;
        float v46;
        int v42;
        StaticTiledMapTile v38;
        String v8_1;
        Element v16;
        Element v24;
        if(element.getName().equals("tileset")) {
            String v23 = element.get("name", null);
            int v11 = element.getIntAttribute("firstgid", 1);
            int v44 = element.getIntAttribute("tilewidth", 0);
            int v41 = element.getIntAttribute("tileheight", 0);
            int v34 = element.getIntAttribute("spacing", 0);
            int v22 = element.getIntAttribute("margin", 0);
            String v33 = element.getAttribute("source", null);
            int v25 = 0;
            int v26 = 0;
            String v18 = "";
            int v19 = 0;
            int v17 = 0;
            if(v33 != null) {
                FileHandle v45 = AtlasTmxMapLoader.getRelativeFileHandle(tmxFile, v33);
                try {
                    element = this.xml.parse(v45);
                    v23 = element.get("name", null);
                    v44 = element.getIntAttribute("tilewidth", 0);
                    v41 = element.getIntAttribute("tileheight", 0);
                    v34 = element.getIntAttribute("spacing", 0);
                    v22 = element.getIntAttribute("margin", 0);
                    v24 = element.getChildByName("tileoffset");
                    if(v24 != null) {
                        v25 = v24.getIntAttribute("x", 0);
                        v26 = v24.getIntAttribute("y", 0);
                    }

                    v16 = element.getChildByName("image");
                    if(v16 == null) {
                        goto label_133;
                    }

                    v18 = v16.getAttribute("source");
                    v19 = v16.getIntAttribute("width", 0);
                    v17 = v16.getIntAttribute("height", 0);
                    AtlasTmxMapLoader.getRelativeFileHandle(v45, v18);
                }
                catch(IOException v10) {
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            else {
                v24 = element.getChildByName("tileoffset");
                if(v24 != null) {
                    v25 = v24.getIntAttribute("x", 0);
                    v26 = v24.getIntAttribute("y", 0);
                }

                v16 = element.getChildByName("image");
                if(v16 == null) {
                    goto label_133;
                }

                v18 = v16.getAttribute("source");
                v19 = v16.getIntAttribute("width", 0);
                v17 = v16.getIntAttribute("height", 0);
                AtlasTmxMapLoader.getRelativeFileHandle(tmxFile, v18);
            }

        label_133:
            Object v8 = map.getProperties().get("atlas", String.class);
            if(v8 == null) {
                FileHandle v7 = tmxFile.sibling(tmxFile.nameWithoutExtension() + ".atlas");
                if(v7.exists()) {
                    v8_1 = v7.name();
                }
            }

            if(v8_1 == null) {
                throw new GdxRuntimeException("The map is missing the \'atlas\' property");
            }

            FileHandle v9 = this.resolve(AtlasTmxMapLoader.getRelativeFileHandle(tmxFile, v8_1).path());
            TextureAtlas v6 = resolver.getAtlas(v9.path());
            String v32 = v9.nameWithoutExtension();
            Iterator v13 = v6.getTextures().iterator();
            while(v13.hasNext()) {
                this.trackedTextures.add(v13.next());
            }

            TiledMapTileSet v43 = new TiledMapTileSet();
            MapProperties v29 = v43.getProperties();
            v43.setName(v23);
            v29.put("firstgid", Integer.valueOf(v11));
            v29.put("imagesource", v18);
            v29.put("imagewidth", Integer.valueOf(v19));
            v29.put("imageheight", Integer.valueOf(v17));
            v29.put("tilewidth", Integer.valueOf(v44));
            v29.put("tileheight", Integer.valueOf(v41));
            v29.put("margin", Integer.valueOf(v22));
            v29.put("spacing", Integer.valueOf(v34));
            v13 = v6.findRegions(v32).iterator();
            while(true) {
                if(!v13.hasNext()) {
                    break;
                }

                Object v30 = v13.next();
                if(v30 == null) {
                    continue;
                }

                v38 = new StaticTiledMapTile(v30);
                v42 = v11 + v30.index;
                v38.setId(v42);
                v38.setOffsetX(((float)v25));
                if(this.flipY) {
                    v46 = ((float)(-v26));
                }
                else {
                    v46 = ((float)v26);
                }

                v38.setOffsetY(v46);
                v43.putTile(v42, v38);
            }

            v13 = element.getChildrenByName("tile").iterator();
            while(true) {
                if(!v13.hasNext()) {
                    break;
                }

                v39 = v13.next();
                v42 = v11 + v39.getIntAttribute("id", 0);
                v38_1 = v43.getTile(v42);
                if(v38_1 == null) {
                    v16 = v39.getChildByName("image");
                    if(v16 != null) {
                        String v31 = v16.getAttribute("source");
                        v31 = v31.substring(0, v31.lastIndexOf(46));
                        AtlasRegion v30_1 = v6.findRegion(v31);
                        if(v30_1 == null) {
                            throw new GdxRuntimeException("Tileset region not found: " + v31);
                        }
                        else {
                            v38 = new StaticTiledMapTile(v30_1);
                            v38.setId(v42);
                            v38.setOffsetX(((float)v25));
                            if(this.flipY) {
                                v46 = ((float)(-v26));
                            }
                            else {
                                v46 = ((float)v26);
                            }

                            v38.setOffsetY(v46);
                            v43.putTile(v42, v38);
                        }
                    }
                }

                if((((TiledMapTile)v38)) == null) {
                    continue;
                }

                v36 = v39.getAttribute("terrain", null);
                if(v36 != null) {
                    ((TiledMapTile)v38).getProperties().put("terrain", v36);
                }

                v27 = v39.getAttribute("probability", null);
                if(v27 != null) {
                    ((TiledMapTile)v38).getProperties().put("probability", v27);
                }

                v28 = v39.getChildByName("properties");
                if(v28 == null) {
                    continue;
                }

                this.loadProperties(((TiledMapTile)v38).getProperties(), v28);
            }

            Array v40 = element.getChildrenByName("tile");
            Array v4 = new Array();
            v13 = v40.iterator();
            do {
            label_448:
                if(!v13.hasNext()) {
                    goto label_538;
                }

                v39 = v13.next();
                v38_1 = v43.getTile(v11 + v39.getIntAttribute("id", 0));
            }
            while(v38_1 == null);

            Element v5 = v39.getChildByName("animation");
            if(v5 != null) {
                Array v35 = new Array();
                IntArray v20 = new IntArray();
                Iterator v14 = v5.getChildrenByName("frame").iterator();
                goto label_473;
            label_538:
                v13 = v4.iterator();
                while(v13.hasNext()) {
                    Object v38_2 = v13.next();
                    v43.putTile(((AnimatedTiledMapTile)v38_2).getId(), v38_2);
                }

                v28 = element.getChildByName("properties");
                if(v28 != null) {
                    this.loadProperties(v43.getProperties(), v28);
                }

                map.getTileSets().addTileSet(v43);
                return;
                while(true) {
                label_473:
                    if(!v14.hasNext()) {
                        break;
                    }

                    Object v12 = v14.next();
                    v35.add(v43.getTile(((Element)v12).getIntAttribute("tileid") + v11));
                    v20.add(((Element)v12).getIntAttribute("duration"));
                }

                AnimatedTiledMapTile v3 = new AnimatedTiledMapTile(v20, v35);
                v3.setId(v38_1.getId());
                v4.add(v3);
                AnimatedTiledMapTile v38_3 = v3;
            }

            v36 = v39.getAttribute("terrain", null);
            if(v36 != null) {
                v38_1.getProperties().put("terrain", v36);
            }

            v27 = v39.getAttribute("probability", null);
            if(v27 != null) {
                v38_1.getProperties().put("probability", v27);
            }

            v28 = v39.getChildByName("properties");
            if(v28 == null) {
                goto label_448;
            }

            this.loadProperties(v38_1.getProperties(), v28);
            goto label_448;
        }
    }

    private void setTextureFilters(TextureFilter min, TextureFilter mag) {
        Iterator v0 = this.trackedTextures.iterator();
        while(v0.hasNext()) {
            v0.next().setFilter(min, mag);
        }

        this.trackedTextures.clear();
    }
}

