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
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
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
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader$Element;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TideMapLoader extends SynchronousAssetLoader {
    public class Parameters extends AssetLoaderParameters {
        public Parameters() {
            super();
        }
    }

    private Element root;
    private XmlReader xml;

    public TideMapLoader() {
        super(new InternalFileHandleResolver());
        this.xml = new XmlReader();
    }

    public TideMapLoader(FileHandleResolver resolver) {
        super(resolver);
        this.xml = new XmlReader();
    }

    public Array getDependencies(String x0, FileHandle x1, AssetLoaderParameters x2) {
        return this.getDependencies(x0, x1, ((Parameters)x2));
    }

    public Array getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {  // has try-catch handlers
        Array v0 = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            Iterator v2 = this.loadTileSheets(this.root, tmxFile).iterator();
            while(v2.hasNext()) {
                v0.add(new AssetDescriptor(v2.next().path(), Texture.class));
            }
        }
        catch(IOException v1) {
            goto label_24;
        }

        return v0;
    label_24:
        throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v1));
    }

    private static FileHandle getRelativeFileHandle(FileHandle file, String path) {
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

    public TiledMap load(AssetManager assetManager, String fileName, FileHandle tideFile, Parameters parameter) {  // has try-catch handlers
        try {
            return this.loadMap(this.root, tideFile, new AssetManagerImageResolver(assetManager));
        }
        catch(Exception v0) {
            throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v0));
        }
    }

    public TiledMap load(String fileName) {  // has try-catch handlers
        try {
            FileHandle v6 = this.resolve(fileName);
            this.root = this.xml.parse(v6);
            ObjectMap v5 = new ObjectMap();
            Iterator v1 = this.loadTileSheets(this.root, v6).iterator();
            while(v1.hasNext()) {
                Object v4 = v1.next();
                v5.put(((FileHandle)v4).path(), new Texture(((FileHandle)v4)));
            }

            TiledMap v3 = this.loadMap(this.root, v6, new DirectImageResolver(v5));
            v3.setOwnedResources(v5.values().toArray());
            return v3;
        }
        catch(IOException v0) {
            throw new GdxRuntimeException("Couldn\'t load tilemap \'" + fileName + "\'", ((Throwable)v0));
        }
    }

    public Object load(AssetManager x0, String x1, FileHandle x2, AssetLoaderParameters x3) {
        return this.load(x0, x1, x2, ((Parameters)x3));
    }

    private void loadLayer(TiledMap map, Element element) {
        Cell v5;
        int v38;
        if(element.getName().equals("Layer")) {
            String v19 = element.getAttribute("Id");
            String v37 = element.getAttribute("Visible");
            Element v11 = element.getChildByName("Dimensions");
            String v22 = v11.getAttribute("LayerSize");
            String v32 = v11.getAttribute("TileSize");
            String[] v23 = v22.split(" x ");
            int v24 = Integer.parseInt(v23[0]);
            int v25 = Integer.parseInt(v23[1]);
            String[] v33 = v32.split(" x ");
            TiledMapTileLayer v21 = new TiledMapTileLayer(v24, v25, Integer.parseInt(v33[0]), Integer.parseInt(v33[1]));
            v21.setName(v19);
            v21.setVisible(v37.equalsIgnoreCase("True"));
            Array v30 = element.getChildByName("TileArray").getChildrenByName("Row");
            TiledMapTileSets v36 = map.getTileSets();
            TiledMapTileSet v10 = null;
            int v12 = 0;
            int v28 = 0;
            int v29 = v30.size;
            while(v28 < v29) {
                Object v9 = v30.get(v28);
                int v40 = v29 - 1 - v28;
                int v6 = 0;
                int v7 = ((Element)v9).getChildCount();
                int v39;
                for(v39 = 0; v6 < v7; v39 = v38) {
                    Element v8 = ((Element)v9).getChild(v6);
                    String v26 = v8.getName();
                    if(v26.equals("TileSheet")) {
                        v10 = v36.getTileSet(v8.getAttribute("Ref"));
                        v12 = v10.getProperties().get("firstgid", Integer.class).intValue();
                        v38 = v39;
                    }
                    else if(v26.equals("Null")) {
                        v38 = v39 + v8.getIntAttribute("Count");
                    }
                    else if(v26.equals("Static")) {
                        v5 = new Cell();
                        v5.setTile(v10.getTile(v8.getIntAttribute("Index") + v12));
                        v38 = v39 + 1;
                        v21.setCell(v39, v40, v5);
                    }
                    else if(v26.equals("Animated")) {
                        int v20 = v8.getInt("Interval");
                        String v41 = "Frames";
                        Element v18 = v8.getChildByName(v41);
                        Array v17 = new Array();
                        int v14 = 0;
                        int v15 = v18.getChildCount();
                        while(v14 < v15) {
                            Element v13 = v18.getChild(v14);
                            String v16 = v13.getName();
                            if(v16.equals("TileSheet")) {
                                v10 = v36.getTileSet(v13.getAttribute("Ref"));
                                Object v41_1 = v10.getProperties().get("firstgid", Integer.class);
                                v12 = ((Integer)v41_1).intValue();
                            }
                            else {
                                boolean v41_2 = v16.equals("Static");
                                if(v41_2) {
                                    TiledMapTile v41_3 = v10.getTile(v13.getIntAttribute("Index") + v12);
                                    v17.add(v41_3);
                                }
                            }

                            ++v14;
                        }

                        v5 = new Cell();
                        v5.setTile(new AnimatedTiledMapTile((((float)v20)) / 1000f, v17));
                        v38 = v39 + 1;
                        v21.setCell(v39, v40, v5);
                    }
                    else {
                        v38 = v39;
                    }

                    ++v6;
                }

                ++v28;
            }

            Element v27 = element.getChildByName("Properties");
            if(v27 != null) {
                this.loadProperties(v21.getProperties(), v27);
            }

            map.getLayers().add(v21);
        }
    }

    private TiledMap loadMap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        TiledMap v3 = new TiledMap();
        Element v4 = root.getChildByName("Properties");
        if(v4 != null) {
            this.loadProperties(v3.getProperties(), v4);
        }

        Iterator v0 = root.getChildByName("TileSheets").getChildrenByName("TileSheet").iterator();
        while(v0.hasNext()) {
            this.loadTileSheet(v3, v0.next(), tmxFile, imageResolver);
        }

        v0 = root.getChildByName("Layers").getChildrenByName("Layer").iterator();
        while(v0.hasNext()) {
            this.loadLayer(v3, v0.next());
        }

        return v3;
    }

    private void loadProperties(MapProperties properties, Element element) {
        String v7 = null;
        if(element.getName().equals("Properties")) {
            Iterator v0 = element.getChildrenByName("Property").iterator();
            while(v0.hasNext()) {
                Object v2 = v0.next();
                String v1 = ((Element)v2).getAttribute("Key", v7);
                String v3 = ((Element)v2).getAttribute("Type", v7);
                String v4 = ((Element)v2).getText();
                if(v3.equals("Int32")) {
                    properties.put(v1, Integer.valueOf(Integer.parseInt(v4)));
                    continue;
                }
                else if(v3.equals("String")) {
                    properties.put(v1, v4);
                    continue;
                }
                else if(v3.equals("Boolean")) {
                    properties.put(v1, Boolean.valueOf(v4.equalsIgnoreCase("true")));
                    continue;
                }
                else {
                    properties.put(v1, v4);
                    continue;
                }
            }
        }
    }

    private void loadTileSheet(TiledMap map, Element element, FileHandle tideFile, ImageResolver imageResolver) {
        if(element.getName().equals("TileSheet")) {
            String v14 = element.getAttribute("Id");
            element.getChildByName("Description").getText();
            String v16 = element.getChildByName("ImageSource").getText();
            Element v8 = element.getChildByName("Alignment");
            String v22 = v8.getAttribute("SheetSize");
            String v33 = v8.getAttribute("TileSize");
            String v17 = v8.getAttribute("Margin");
            v8.getAttribute("Spacing");
            String[] v23 = v22.split(" x ");
            Integer.parseInt(v23[0]);
            Integer.parseInt(v23[1]);
            String[] v34 = v33.split(" x ");
            int v6 = Integer.parseInt(v34[0]);
            int v7 = Integer.parseInt(v34[1]);
            String[] v18 = v17.split(" x ");
            int v19 = Integer.parseInt(v18[0]);
            int v20 = Integer.parseInt(v18[1]);
            String[] v27 = v17.split(" x ");
            int v28 = Integer.parseInt(v27[0]);
            int v29 = Integer.parseInt(v27[1]);
            TextureRegion v3 = imageResolver.getImage(TideMapLoader.getRelativeFileHandle(tideFile, v16).path());
            TiledMapTileSets v36 = map.getTileSets();
            int v10 = 1;
            Iterator v13 = v36.iterator();
            while(v13.hasNext()) {
                v10 += v13.next().size();
            }

            TiledMapTileSet v35 = new TiledMapTileSet();
            v35.setName(v14);
            v35.getProperties().put("firstgid", Integer.valueOf(v10));
            int v11 = v10;
            int v31 = v3.getRegionWidth() - v6;
            int v30 = v3.getRegionHeight() - v7;
            int v5 = v20;
            while(v5 <= v30) {
                int v4 = v19;
                int v12;
                for(v12 = v11; v4 <= v31; ++v12) {
                    StaticTiledMapTile v32 = new StaticTiledMapTile(new TextureRegion(v3, v4, v5, v6, v7));
                    v32.setId(v12);
                    v35.putTile(v12, v32);
                    v4 += v6 + v28;
                }

                v5 += v7 + v29;
                v11 = v12;
            }

            Element v21 = element.getChildByName("Properties");
            if(v21 != null) {
                this.loadProperties(v35.getProperties(), v21);
            }

            v36.addTileSet(v35);
        }
    }

    private Array loadTileSheets(Element root, FileHandle tideFile) throws IOException {
        Array v3 = new Array();
        Iterator v0 = root.getChildByName("TileSheets").getChildrenByName("TileSheet").iterator();
        while(v0.hasNext()) {
            v3.add(TideMapLoader.getRelativeFileHandle(tideFile, v0.next().getChildByName("ImageSource").getText()));
        }

        return v3;
    }
}

