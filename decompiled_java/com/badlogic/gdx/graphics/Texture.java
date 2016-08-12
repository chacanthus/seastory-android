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
import com.badlogic.gdx.assets.loaders.TextureLoader$TextureParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Texture extends GLTexture {
    public enum TextureFilter {
        public static final enum TextureFilter Linear;
        public static final enum TextureFilter MipMap;
        public static final enum TextureFilter MipMapLinearLinear;
        public static final enum TextureFilter MipMapLinearNearest;
        public static final enum TextureFilter MipMapNearestLinear;
        public static final enum TextureFilter MipMapNearestNearest;
        final int glEnum;

        static  {
            TextureFilter.Nearest = new TextureFilter("Nearest", 0, 9728);
            TextureFilter.Linear = new TextureFilter("Linear", 1, 9729);
            TextureFilter.MipMap = new TextureFilter("MipMap", 2, 9987);
            TextureFilter.MipMapNearestNearest = new TextureFilter("MipMapNearestNearest", 3, 9984);
            TextureFilter.MipMapLinearNearest = new TextureFilter("MipMapLinearNearest", 4, 9985);
            TextureFilter.MipMapNearestLinear = new TextureFilter("MipMapNearestLinear", 5, 9986);
            TextureFilter.MipMapLinearLinear = new TextureFilter("MipMapLinearLinear", 6, 9987);
            TextureFilter[] v0 = new TextureFilter[7];
            v0[0] = TextureFilter.Nearest;
            v0[1] = TextureFilter.Linear;
            v0[2] = TextureFilter.MipMap;
            v0[3] = TextureFilter.MipMapNearestNearest;
            v0[4] = TextureFilter.MipMapLinearNearest;
            v0[5] = TextureFilter.MipMapNearestLinear;
            v0[6] = TextureFilter.MipMapLinearLinear;
            TextureFilter.$VALUES = v0;
        }

        private TextureFilter(String arg1, int arg2, int glEnum) {
            super(arg1, arg2);
            this.glEnum = glEnum;
        }

        public int getGLEnum() {
            return this.glEnum;
        }

        public boolean isMipMap() {
            boolean v0;
            if(this.glEnum == 9728 || this.glEnum == 9729) {
                v0 = false;
            }
            else {
                v0 = true;
            }

            return v0;
        }

        public static TextureFilter valueOf(String name) {
            return Enum.valueOf(TextureFilter.class, name);
        }

        public static TextureFilter[] values() {
            return TextureFilter.$VALUES.clone();
        }
    }

    public enum TextureWrap {
        public static final enum TextureWrap MirroredRepeat;
        public static final enum TextureWrap Repeat;
        final int glEnum;

        static  {
            TextureWrap.MirroredRepeat = new TextureWrap("MirroredRepeat", 0, 33648);
            TextureWrap.ClampToEdge = new TextureWrap("ClampToEdge", 1, 33071);
            TextureWrap.Repeat = new TextureWrap("Repeat", 2, 10497);
            TextureWrap[] v0 = new TextureWrap[3];
            v0[0] = TextureWrap.MirroredRepeat;
            v0[1] = TextureWrap.ClampToEdge;
            v0[2] = TextureWrap.Repeat;
            TextureWrap.$VALUES = v0;
        }

        private TextureWrap(String arg1, int arg2, int glEnum) {
            super(arg1, arg2);
            this.glEnum = glEnum;
        }

        public int getGLEnum() {
            return this.glEnum;
        }

        public static TextureWrap valueOf(String name) {
            return Enum.valueOf(TextureWrap.class, name);
        }

        public static TextureWrap[] values() {
            return TextureWrap.$VALUES.clone();
        }
    }

    private static AssetManager assetManager;
    TextureData data;
    static final Map managedTextures;

    static  {
        Texture.managedTextures = new HashMap();
    }

    public Texture(TextureData data) {
        super(3553, Gdx.gl.glGenTexture());
        this.load(data);
        if(data.isManaged()) {
            Texture.addManagedTexture(Gdx.app, this);
        }
    }

    public Texture(int width, int height, Format format) {
        this(new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
    }

    public Texture(FileHandle file) {
        this(file, null, false);
    }

    public Texture(FileHandle file, Format format, boolean useMipMaps) {
        this(Factory.loadFromFile(file, format, useMipMaps));
    }

    public Texture(FileHandle file, boolean useMipMaps) {
        this(file, null, useMipMaps);
    }

    public Texture(Pixmap pixmap) {
        this(new PixmapTextureData(pixmap, null, false, false));
    }

    public Texture(Pixmap pixmap, Format format, boolean useMipMaps) {
        this(new PixmapTextureData(pixmap, format, useMipMaps, false));
    }

    public Texture(Pixmap pixmap, boolean useMipMaps) {
        this(new PixmapTextureData(pixmap, null, useMipMaps, false));
    }

    public Texture(String internalPath) {
        this(Gdx.files.internal(internalPath));
    }

    private static void addManagedTexture(Application app, Texture texture) {
        Object v0 = Texture.managedTextures.get(app);
        if(v0 == null) {
            Array v0_1 = new Array();
        }

        ((Array)v0).add(texture);
        Texture.managedTextures.put(app, v0);
    }

    public static void clearAllTextures(Application app) {
        Texture.managedTextures.remove(app);
    }

    public void dispose() {
        if(this.glHandle != 0) {
            this.delete();
            if((this.data.isManaged()) && Texture.managedTextures.get(Gdx.app) != null) {
                Texture.managedTextures.get(Gdx.app).removeValue(this, true);
            }
        }
    }

    public void draw(Pixmap pixmap, int x, int y) {
        if(this.data.isManaged()) {
            throw new GdxRuntimeException("can\'t draw to a managed texture");
        }

        this.bind();
        Gdx.gl.glTexSubImage2D(this.glTarget, 0, x, y, pixmap.getWidth(), pixmap.getHeight(), pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
    }

    public int getDepth() {
        return 0;
    }

    public int getHeight() {
        return this.data.getHeight();
    }

    public static String getManagedStatus() {
        StringBuilder v1 = new StringBuilder();
        v1.append("Managed textures/app: { ");
        Iterator v2 = Texture.managedTextures.keySet().iterator();
        while(v2.hasNext()) {
            v1.append(Texture.managedTextures.get(v2.next()).size);
            v1.append(" ");
        }

        v1.append("}");
        return v1.toString();
    }

    public static int getNumManagedTextures() {
        return Texture.managedTextures.get(Gdx.app).size;
    }

    public TextureData getTextureData() {
        return this.data;
    }

    public int getWidth() {
        return this.data.getWidth();
    }

    public static void invalidateAllTextures(Application app) {
        Object v3 = Texture.managedTextures.get(app);
        if(v3 != null) {
            if(Texture.assetManager == null) {
                int v1;
                for(v1 = 0; v1 < ((Array)v3).size; ++v1) {
                    ((Array)v3).get(v1).reload();
                }
            }
            else {
                Texture.assetManager.finishLoading();
                Array v7 = new Array(((Array)v3));
                Iterator v2 = v7.iterator();
                while(true) {
                    if(v2.hasNext()) {
                        Object v6 = v2.next();
                        String v0 = Texture.assetManager.getAssetFileName(v6);
                        if(v0 == null) {
                            ((Texture)v6).reload();
                            continue;
                        }
                        else {
                            int v5 = Texture.assetManager.getReferenceCount(v0);
                            Texture.assetManager.setReferenceCount(v0, 0);
                            ((Texture)v6).glHandle = 0;
                            TextureParameter v4 = new TextureParameter();
                            v4.textureData = ((Texture)v6).getTextureData();
                            v4.minFilter = ((Texture)v6).getMinFilter();
                            v4.magFilter = ((Texture)v6).getMagFilter();
                            v4.wrapU = ((Texture)v6).getUWrap();
                            v4.wrapV = ((Texture)v6).getVWrap();
                            v4.genMipMaps = ((Texture)v6).data.useMipMaps();
                            v4.texture = ((Texture)v6);
                            v4.loadedCallback = new LoadedCallback() {
                                public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
                                    assetManager.setReferenceCount(fileName, this.val$refCount);
                                }
                            };
                            Texture.assetManager.unload(v0);
                            ((Texture)v6).glHandle = Gdx.gl.glGenTexture();
                            Texture.assetManager.load(v0, Texture.class, ((AssetLoaderParameters)v4));
                            continue;
                        }
                    }
                    else {
                        break;
                    }

                    return;
                }

                ((Array)v3).clear();
                ((Array)v3).addAll(v7);
            }
        }
    }

    public boolean isManaged() {
        return this.data.isManaged();
    }

    public void load(TextureData data) {
        if(this.data != null && data.isManaged() != this.data.isManaged()) {
            throw new GdxRuntimeException("New data must have the same managed status as the old data");
        }

        this.data = data;
        if(!data.isPrepared()) {
            data.prepare();
        }

        this.bind();
        Texture.uploadImageData(3553, data);
        this.setFilter(this.minFilter, this.magFilter);
        this.setWrap(this.uWrap, this.vWrap);
        Gdx.gl.glBindTexture(this.glTarget, 0);
    }

    protected void reload() {
        if(!this.isManaged()) {
            throw new GdxRuntimeException("Tried to reload unmanaged Texture");
        }

        this.glHandle = Gdx.gl.glGenTexture();
        this.load(this.data);
    }

    public static void setAssetManager(AssetManager manager) {
        Texture.assetManager = manager;
    }
}

