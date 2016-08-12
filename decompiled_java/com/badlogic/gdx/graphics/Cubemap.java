// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetLoaderParameters$LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.CubemapLoader$CubemapParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.FacedCubemapData;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cubemap extends GLTexture {
    public enum CubemapSide {
        public static final enum CubemapSide NegativeX;
        public static final enum CubemapSide NegativeY;
        public static final enum CubemapSide NegativeZ;
        public static final enum CubemapSide PositiveX;
        public static final enum CubemapSide PositiveY;
        public static final enum CubemapSide PositiveZ;
        public final int glEnum;
        public final int index;

        static  {
            CubemapSide.PositiveX = new CubemapSide("PositiveX", 0, 0, 34069);
            CubemapSide.NegativeX = new CubemapSide("NegativeX", 1, 1, 34070);
            CubemapSide.PositiveY = new CubemapSide("PositiveY", 2, 2, 34071);
            CubemapSide.NegativeY = new CubemapSide("NegativeY", 3, 3, 34072);
            CubemapSide.PositiveZ = new CubemapSide("PositiveZ", 4, 4, 34073);
            CubemapSide.NegativeZ = new CubemapSide("NegativeZ", 5, 5, 34074);
            CubemapSide[] v0 = new CubemapSide[6];
            v0[0] = CubemapSide.PositiveX;
            v0[1] = CubemapSide.NegativeX;
            v0[2] = CubemapSide.PositiveY;
            v0[3] = CubemapSide.NegativeY;
            v0[4] = CubemapSide.PositiveZ;
            v0[5] = CubemapSide.NegativeZ;
            CubemapSide.$VALUES = v0;
        }

        private CubemapSide(String arg1, int arg2, int index, int glEnum) {
            super(arg1, arg2);
            this.index = index;
            this.glEnum = glEnum;
        }

        public int getGLEnum() {
            return this.glEnum;
        }

        public static CubemapSide valueOf(String name) {
            return Enum.valueOf(CubemapSide.class, name);
        }

        public static CubemapSide[] values() {
            return CubemapSide.$VALUES.clone();
        }
    }

    private static AssetManager assetManager;
    protected CubemapData data;
    static final Map managedCubemaps;

    static  {
        Cubemap.managedCubemaps = new HashMap();
    }

    public Cubemap(CubemapData data) {
        super(34067);
        this.data = data;
        this.load(data);
    }

    public Cubemap(int width, int height, int depth, Format format) {
        this(new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
    }

    public Cubemap(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
        super(34067);
        this.minFilter = TextureFilter.Nearest;
        this.magFilter = TextureFilter.Nearest;
        this.uWrap = TextureWrap.ClampToEdge;
        this.vWrap = TextureWrap.ClampToEdge;
        this.data = new FacedCubemapData(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ);
        this.load(this.data);
    }

    public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ, boolean useMipMaps) {
        this(Factory.loadFromFile(positiveX, useMipMaps), Factory.loadFromFile(negativeX, useMipMaps), Factory.loadFromFile(positiveY, useMipMaps), Factory.loadFromFile(negativeY, useMipMaps), Factory.loadFromFile(positiveZ, useMipMaps), Factory.loadFromFile(negativeZ, useMipMaps));
    }

    public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ, boolean useMipMaps) {
        PixmapTextureData v6_1;
        PixmapTextureData v5_1;
        TextureData v4;
        TextureData v3;
        PixmapTextureData v2_1;
        PixmapTextureData v1_1;
        TextureData v6 = null;
        if(positiveX == null) {
            TextureData v1 = v6;
        }
        else {
            v1_1 = new PixmapTextureData(positiveX, ((Format)v6), useMipMaps, false);
        }

        if(negativeX == null) {
            TextureData v2 = v6;
        }
        else {
            v2_1 = new PixmapTextureData(negativeX, ((Format)v6), useMipMaps, false);
        }

        if(positiveY == null) {
            v3 = v6;
        }
        else {
            PixmapTextureData v3_1 = new PixmapTextureData(positiveY, ((Format)v6), useMipMaps, false);
        }

        if(negativeY == null) {
            v4 = v6;
        }
        else {
            PixmapTextureData v4_1 = new PixmapTextureData(negativeY, ((Format)v6), useMipMaps, false);
        }

        if(positiveZ == null) {
            TextureData v5 = v6;
        }
        else {
            v5_1 = new PixmapTextureData(positiveZ, ((Format)v6), useMipMaps, false);
        }

        if(negativeZ != null) {
            v6_1 = new PixmapTextureData(negativeZ, ((Format)v6), useMipMaps, false);
        }

        this(((TextureData)v1_1), ((TextureData)v2_1), v3, v4, ((TextureData)v5_1), ((TextureData)v6_1));
    }

    private static void addManagedCubemap(Application app, Cubemap cubemap) {
        Array v0_1;
        Object v0 = Cubemap.managedCubemaps.get(app);
        if(v0 == null) {
            v0_1 = new Array();
        }

        v0_1.add(cubemap);
        Cubemap.managedCubemaps.put(app, v0_1);
    }

    public static void clearAllCubemaps(Application app) {
        Cubemap.managedCubemaps.remove(app);
    }

    public void dispose() {
        if(this.glHandle != 0) {
            this.delete();
            if((this.data.isManaged()) && Cubemap.managedCubemaps.get(Gdx.app) != null) {
                Cubemap.managedCubemaps.get(Gdx.app).removeValue(this, true);
            }
        }
    }

    public CubemapData getCubemapData() {
        return this.data;
    }

    public int getDepth() {
        return 0;
    }

    public int getHeight() {
        return this.data.getHeight();
    }

    public static String getManagedStatus() {
        StringBuilder v1 = new StringBuilder();
        v1.append("Managed cubemap/app: { ");
        Iterator v2 = Cubemap.managedCubemaps.keySet().iterator();
        while(v2.hasNext()) {
            v1.append(Cubemap.managedCubemaps.get(v2.next()).size);
            v1.append(" ");
        }

        v1.append("}");
        return v1.toString();
    }

    public static int getNumManagedCubemaps() {
        return Cubemap.managedCubemaps.get(Gdx.app).size;
    }

    public int getWidth() {
        return this.data.getWidth();
    }

    public static void invalidateAllCubemaps(Application app) {
        Object v5 = Cubemap.managedCubemaps.get(app);
        if(v5 != null) {
            if(Cubemap.assetManager == null) {
                int v3;
                for(v3 = 0; v3 < ((Array)v5).size; ++v3) {
                    ((Array)v5).get(v3).reload();
                }
            }
            else {
                Cubemap.assetManager.finishLoading();
                Array v1 = new Array(((Array)v5));
                Iterator v4 = v1.iterator();
                while(true) {
                    if(v4.hasNext()) {
                        Object v0 = v4.next();
                        String v2 = Cubemap.assetManager.getAssetFileName(v0);
                        if(v2 == null) {
                            ((Cubemap)v0).reload();
                            continue;
                        }
                        else {
                            int v7 = Cubemap.assetManager.getReferenceCount(v2);
                            Cubemap.assetManager.setReferenceCount(v2, 0);
                            ((Cubemap)v0).glHandle = 0;
                            CubemapParameter v6 = new CubemapParameter();
                            v6.cubemapData = ((Cubemap)v0).getCubemapData();
                            v6.minFilter = ((Cubemap)v0).getMinFilter();
                            v6.magFilter = ((Cubemap)v0).getMagFilter();
                            v6.wrapU = ((Cubemap)v0).getUWrap();
                            v6.wrapV = ((Cubemap)v0).getVWrap();
                            v6.cubemap = ((Cubemap)v0);
                            v6.loadedCallback = new LoadedCallback() {
                                public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
                                    assetManager.setReferenceCount(fileName, this.val$refCount);
                                }
                            };
                            Cubemap.assetManager.unload(v2);
                            ((Cubemap)v0).glHandle = Gdx.gl.glGenTexture();
                            Cubemap.assetManager.load(v2, Cubemap.class, ((AssetLoaderParameters)v6));
                            continue;
                        }
                    }
                    else {
                        break;
                    }

                    return;
                }

                ((Array)v5).clear();
                ((Array)v5).addAll(v1);
            }
        }
    }

    public boolean isManaged() {
        return this.data.isManaged();
    }

    public void load(CubemapData data) {
        if(!data.isPrepared()) {
            data.prepare();
        }

        this.bind();
        this.unsafeSetFilter(this.minFilter, this.magFilter, true);
        this.unsafeSetWrap(this.uWrap, this.vWrap, true);
        data.consumeCubemapData();
        Gdx.gl.glBindTexture(this.glTarget, 0);
    }

    protected void reload() {
        if(!this.isManaged()) {
            throw new GdxRuntimeException("Tried to reload an unmanaged Cubemap");
        }

        this.glHandle = Gdx.gl.glGenTexture();
        this.load(this.data);
    }

    public static void setAssetManager(AssetManager manager) {
        Cubemap.assetManager = manager;
    }
}

