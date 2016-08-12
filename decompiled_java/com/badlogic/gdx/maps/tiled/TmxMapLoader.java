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
import com.badlogic.gdx.assets.loaders.TextureLoader$TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver$AssetManagerImageResolver;
import com.badlogic.gdx.maps.ImageResolver$DirectImageResolver;
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

public class TmxMapLoader extends BaseTmxMapLoader {
    public class Parameters extends com.badlogic.gdx.maps.tiled.BaseTmxMapLoader$Parameters {
        public Parameters() {
            super();
        }
    }

    public TmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public TmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((Parameters)x2));
    }

    public Array getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {  // has try-catch handlers
        boolean v2;
        Array v0 = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            if(parameter != null) {
                v2 = parameter.generateMipMaps;
            }
            else {
                goto label_33;
            }

            goto label_6;
        }
        catch(IOException v1) {
            goto label_32;
        }

    label_33:
        v2 = false;
        try {
        label_6:
            TextureParameter v5 = new TextureParameter();
            v5.genMipMaps = v2;
            if(parameter != null) {
                v5.minFilter = parameter.textureMinFilter;
                v5.magFilter = parameter.textureMagFilter;
            }

            Iterator v3 = this.loadTilesets(this.root, tmxFile).iterator();
            while(v3.hasNext()) {
                v0.add(new AssetDescriptor(v3.next(), Texture.class, ((AssetLoaderParameters)v5)));
            }

            v3 = this.loadImages(this.root, tmxFile).iterator();
            while(v3.hasNext()) {
                v0.add(new AssetDescriptor(v3.next(), Texture.class, ((AssetLoaderParameters)v5)));
            }
        }
        catch(IOException v1) {
            goto label_32;
        }

        return v0;
    label_32:
        throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v1));
    }

    public TiledMap load(String fileName) {
        return this.load(fileName, new Parameters());
    }

    public TiledMap load(String fileName, Parameters parameters) {  // has try-catch handlers
        try {
            this.convertObjectToTileSpace = parameters.convertObjectToTileSpace;
            this.flipY = parameters.flipY;
            FileHandle v8 = this.resolve(fileName);
            this.root = this.xml.parse(v8);
            ObjectMap v7 = new ObjectMap();
            Array v6 = this.loadTilesets(this.root, v8);
            v6.addAll(this.loadImages(this.root, v8));
            Iterator v1 = v6.iterator();
            while(v1.hasNext()) {
                Object v5 = v1.next();
                Texture v4 = new Texture(((FileHandle)v5), parameters.generateMipMaps);
                v4.setFilter(parameters.textureMinFilter, parameters.textureMagFilter);
                v7.put(((FileHandle)v5).path(), v4);
            }

            TiledMap v3 = this.loadTilemap(this.root, v8, new DirectImageResolver(v7));
            v3.setOwnedResources(v7.values().toArray());
            return v3;
        }
        catch(IOException v0) {
            throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v0));
        }
    }

    public void loadAsync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        this.loadAsync(x0, x1, x2, ((Parameters)x3));
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {  // has try-catch handlers
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
            this.map = this.loadTilemap(this.root, tmxFile, new AssetManagerImageResolver(manager));
            return;
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v0));
        }
    }

    protected Array loadImages(Element root, FileHandle tmxFile) throws IOException {
        Array v4 = new Array();
        Iterator v1 = root.getChildrenByName("imagelayer").iterator();
        while(true) {
            if(!v1.hasNext()) {
                break;
            }

            String v5 = v1.next().getChildByName("image").getAttribute("source", null);
            if(v5 == null) {
                continue;
            }

            FileHandle v0 = TmxMapLoader.getRelativeFileHandle(tmxFile, v5);
            if(v4.contains(v0, false)) {
                continue;
            }

            v4.add(v0);
        }

        return v4;
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return this.map;
    }

    public Object loadSync(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.loadSync(x0, x1, x2, ((Parameters)x3));
    }

    protected void loadTileSet(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {  // has try-catch handlers
        AnimatedTiledMapTile v40_3;
        Element v32;
        TiledMapTile v40_1;
        Object v41;
        Iterator v15;
        float v45;
        StaticTiledMapTile v40;
        Element v20;
        Element v28;
        if(element.getName().equals("tileset")) {
            String v27 = element.get("name", null);
            int v13 = element.getIntAttribute("firstgid", 1);
            int v7 = element.getIntAttribute("tilewidth", 0);
            int v8 = element.getIntAttribute("tileheight", 0);
            int v35 = element.getIntAttribute("spacing", 0);
            int v26 = element.getIntAttribute("margin", 0);
            String v34 = element.getAttribute("source", null);
            int v29 = 0;
            int v30 = 0;
            String v22 = "";
            int v23 = 0;
            int v21 = 0;
            FileHandle v19 = null;
            if(v34 != null) {
                FileHandle v44 = TmxMapLoader.getRelativeFileHandle(tmxFile, v34);
                try {
                    element = this.xml.parse(v44);
                    v27 = element.get("name", null);
                    v7 = element.getIntAttribute("tilewidth", 0);
                    v8 = element.getIntAttribute("tileheight", 0);
                    v35 = element.getIntAttribute("spacing", 0);
                    v26 = element.getIntAttribute("margin", 0);
                    v28 = element.getChildByName("tileoffset");
                    if(v28 != null) {
                        v29 = v28.getIntAttribute("x", 0);
                        v30 = v28.getIntAttribute("y", 0);
                    }

                    v20 = element.getChildByName("image");
                    if(v20 == null) {
                        goto label_133;
                    }

                    v22 = v20.getAttribute("source");
                    v23 = v20.getIntAttribute("width", 0);
                    v21 = v20.getIntAttribute("height", 0);
                    v19 = TmxMapLoader.getRelativeFileHandle(v44, v22);
                }
                catch(IOException v12) {
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            else {
                v28 = element.getChildByName("tileoffset");
                if(v28 != null) {
                    v29 = v28.getIntAttribute("x", 0);
                    v30 = v28.getIntAttribute("y", 0);
                }

                v20 = element.getChildByName("image");
                if(v20 == null) {
                    goto label_133;
                }

                v22 = v20.getAttribute("source");
                v23 = v20.getIntAttribute("width", 0);
                v21 = v20.getIntAttribute("height", 0);
                v19 = TmxMapLoader.getRelativeFileHandle(tmxFile, v22);
            }

        label_133:
            TiledMapTileSet v43 = new TiledMapTileSet();
            v43.setName(v27);
            v43.getProperties().put("firstgid", Integer.valueOf(v13));
            if(v19 != null) {
                TextureRegion v4 = imageResolver.getImage(v19.path());
                MapProperties v33 = v43.getProperties();
                v33.put("imagesource", v22);
                v33.put("imagewidth", Integer.valueOf(v23));
                v33.put("imageheight", Integer.valueOf(v21));
                v33.put("tilewidth", Integer.valueOf(v7));
                v33.put("tileheight", Integer.valueOf(v8));
                v33.put("margin", Integer.valueOf(v26));
                v33.put("spacing", Integer.valueOf(v35));
                int v38 = v4.getRegionWidth() - v7;
                int v37 = v4.getRegionHeight() - v8;
                int v17 = v13;
                int v6 = v26;
                while(v6 <= v37) {
                    int v5 = v26;
                    int v18;
                    for(v18 = v17; v5 <= v38; ++v18) {
                        v40 = new StaticTiledMapTile(new TextureRegion(v4, v5, v6, v7, v8));
                        v40.setId(v18);
                        v40.setOffsetX(((float)v29));
                        if(this.flipY) {
                            v45 = ((float)(-v30));
                        }
                        else {
                            v45 = ((float)v30);
                        }

                        v40.setOffsetY(v45);
                        v43.putTile(v18, v40);
                        v5 += v7 + v35;
                    }

                    v6 += v8 + v35;
                    v17 = v18;
                }
            }
            else {
                v15 = element.getChildrenByName("tile").iterator();
                while(v15.hasNext()) {
                    v41 = v15.next();
                    v20 = v41.getChildByName("image");
                    if(v20 != null) {
                        v22 = v20.getAttribute("source");
                        v20.getIntAttribute("width", 0);
                        v20.getIntAttribute("height", 0);
                        v19 = TmxMapLoader.getRelativeFileHandle(tmxFile, v22);
                    }

                    v40 = new StaticTiledMapTile(imageResolver.getImage(v19.path()));
                    v40.setId(v41.getIntAttribute("id") + v13);
                    v40.setOffsetX(((float)v29));
                    if(this.flipY) {
                        v45 = ((float)(-v30));
                    }
                    else {
                        v45 = ((float)v30);
                    }

                    v40.setOffsetY(v45);
                    v43.putTile(((TiledMapTile)v40).getId(), v40);
                }
            }

            Array v42 = element.getChildrenByName("tile");
            Array v10 = new Array();
            v15 = v42.iterator();
            do {
            label_371:
                if(!v15.hasNext()) {
                    goto label_461;
                }

                v41 = v15.next();
                v40_1 = v43.getTile(v13 + v41.getIntAttribute("id", 0));
            }
            while(v40_1 == null);

            Element v11 = v41.getChildByName("animation");
            if(v11 != null) {
                Array v36 = new Array();
                IntArray v24 = new IntArray();
                Iterator v16 = v11.getChildrenByName("frame").iterator();
                goto label_396;
            label_461:
                v15 = v10.iterator();
                while(v15.hasNext()) {
                    Object v40_2 = v15.next();
                    v43.putTile(((AnimatedTiledMapTile)v40_2).getId(), v40_2);
                }

                v32 = element.getChildByName("properties");
                if(v32 != null) {
                    this.loadProperties(v43.getProperties(), v32);
                }

                map.getTileSets().addTileSet(v43);
                return;
                while(true) {
                label_396:
                    if(!v16.hasNext()) {
                        break;
                    }

                    Object v14 = v16.next();
                    v36.add(v43.getTile(((Element)v14).getIntAttribute("tileid") + v13));
                    v24.add(((Element)v14).getIntAttribute("duration"));
                }

                AnimatedTiledMapTile v9 = new AnimatedTiledMapTile(v24, v36);
                v9.setId(v40_1.getId());
                v10.add(v9);
                v40_3 = v9;
            }

            String v39 = v41.getAttribute("terrain", null);
            if(v39 != null) {
                ((TiledMapTile)v40_3).getProperties().put("terrain", v39);
            }

            String v31 = v41.getAttribute("probability", null);
            if(v31 != null) {
                ((TiledMapTile)v40_3).getProperties().put("probability", v31);
            }

            v32 = v41.getChildByName("properties");
            if(v32 == null) {
                goto label_371;
            }

            this.loadProperties(((TiledMapTile)v40_3).getProperties(), v32);
            goto label_371;
        }
    }

    protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        TiledMap v7 = new TiledMap();
        String v10 = root.getAttribute("orientation", null);
        int v12 = root.getIntAttribute("width", 0);
        int v9 = root.getIntAttribute("height", 0);
        int v16 = root.getIntAttribute("tilewidth", 0);
        int v15 = root.getIntAttribute("tileheight", 0);
        String v8 = root.getAttribute("backgroundcolor", null);
        MapProperties v11 = v7.getProperties();
        if(v10 != null) {
            v11.put("orientation", v10);
        }

        v11.put("width", Integer.valueOf(v12));
        v11.put("height", Integer.valueOf(v9));
        v11.put("tilewidth", Integer.valueOf(v16));
        v11.put("tileheight", Integer.valueOf(v15));
        if(v8 != null) {
            v11.put("backgroundcolor", v8);
        }

        this.mapTileWidth = v16;
        this.mapTileHeight = v15;
        this.mapWidthInPixels = v12 * v16;
        this.mapHeightInPixels = v9 * v15;
        if(v10 != null && ("staggered".equals(v10)) && v9 > 1) {
            this.mapWidthInPixels += v16 / 2;
            this.mapHeightInPixels = this.mapHeightInPixels / 2 + v15 / 2;
        }

        Element v14 = root.getChildByName("properties");
        if(v14 != null) {
            this.loadProperties(v7.getProperties(), v14);
        }

        Iterator v5 = root.getChildrenByName("tileset").iterator();
        while(v5.hasNext()) {
            Object v3 = v5.next();
            this.loadTileSet(v7, ((Element)v3), tmxFile, imageResolver);
            root.removeChild(((Element)v3));
        }

        int v4 = 0;
        int v6 = root.getChildCount();
        while(v4 < v6) {
            Element v3_1 = root.getChild(v4);
            String v13 = v3_1.getName();
            if(v13.equals("layer")) {
                this.loadTileLayer(v7, v3_1);
            }
            else if(v13.equals("objectgroup")) {
                this.loadObjectGroup(v7, v3_1);
            }
            else if(v13.equals("imagelayer")) {
                this.loadImageLayer(v7, v3_1, tmxFile, imageResolver);
            }

            ++v4;
        }

        return v7;
    }

    protected Array loadTilesets(Element root, FileHandle tmxFile) throws IOException {
        Iterator v1;
        FileHandle v9;
        Object v8;
        Array v5 = new Array();
        Iterator v0 = root.getChildrenByName("tileset").iterator();
        while(true) {
        label_4:
            if(!v0.hasNext()) {
                goto label_60;
            }

            v8 = v0.next();
            String v6 = ((Element)v8).getAttribute("source", null);
            if(v6 == null) {
                goto label_37;
            }

            v9 = TmxMapLoader.getRelativeFileHandle(tmxFile, v6);
            Element v8_1 = this.xml.parse(v9);
            if(v8_1.getChildByName("image") == null) {
                goto label_24;
            }

            v5.add(TmxMapLoader.getRelativeFileHandle(v9, v8_1.getChildByName("image").getAttribute("source")));
            continue;
        label_24:
            v1 = v8_1.getChildrenByName("tile").iterator();
            goto label_27;
        label_37:
            if(((Element)v8).getChildByName("image") == null) {
                break;
            }

            v5.add(TmxMapLoader.getRelativeFileHandle(tmxFile, ((Element)v8).getChildByName("image").getAttribute("source")));
        }

        v1 = ((Element)v8).getChildrenByName("tile").iterator();
        goto label_50;
    label_60:
        return v5;
        while(true) {
        label_50:
            if(!v1.hasNext()) {
                goto label_4;
            }

            v5.add(TmxMapLoader.getRelativeFileHandle(tmxFile, v1.next().getChildByName("image").getAttribute("source")));
        }

        while(true) {
        label_27:
            if(!v1.hasNext()) {
                goto label_4;
            }

            v5.add(TmxMapLoader.getRelativeFileHandle(v9, v1.next().getChildByName("image").getAttribute("source")));
        }
    }
}

