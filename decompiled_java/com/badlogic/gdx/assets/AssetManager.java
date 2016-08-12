// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.assets;

import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonRegionLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import java.util.Iterator;
import java.util.Stack;

public class AssetManager implements Disposable {
    final ObjectMap assetDependencies;
    final ObjectMap assetTypes;
    final ObjectMap assets;
    final AsyncExecutor executor;
    final ObjectSet injected;
    AssetErrorListener listener;
    final Array loadQueue;
    int loaded;
    final ObjectMap loaders;
    Logger log;
    final Stack tasks;
    int toLoad;

    public AssetManager() {
        this(new InternalFileHandleResolver());
    }

    public AssetManager(FileHandleResolver resolver) {
        super();
        this.assets = new ObjectMap();
        this.assetTypes = new ObjectMap();
        this.assetDependencies = new ObjectMap();
        this.injected = new ObjectSet();
        this.loaders = new ObjectMap();
        this.loadQueue = new Array();
        this.tasks = new Stack();
        this.listener = null;
        this.loaded = 0;
        this.toLoad = 0;
        this.log = new Logger("AssetManager", 0);
        this.setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
        this.setLoader(Music.class, new MusicLoader(resolver));
        this.setLoader(Pixmap.class, new PixmapLoader(resolver));
        this.setLoader(Sound.class, new SoundLoader(resolver));
        this.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        this.setLoader(Texture.class, new TextureLoader(resolver));
        this.setLoader(Skin.class, new SkinLoader(resolver));
        this.setLoader(ParticleEffect.class, new ParticleEffectLoader(resolver));
        this.setLoader(com.badlogic.gdx.graphics.g3d.particles.ParticleEffect.class, new com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader(resolver));
        this.setLoader(PolygonRegion.class, new PolygonRegionLoader(resolver));
        this.setLoader(I18NBundle.class, new I18NBundleLoader(resolver));
        this.setLoader(Model.class, ".g3dj", new G3dModelLoader(new JsonReader(), resolver));
        this.setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), resolver));
        this.setLoader(Model.class, ".obj", new ObjLoader(resolver));
        this.executor = new AsyncExecutor(1);
    }

    protected void addAsset(String fileName, Class arg4, Object arg5) {
        this.assetTypes.put(fileName, arg4);
        Object v0 = this.assets.get(arg4);
        if(v0 == null) {
            ObjectMap v0_1 = new ObjectMap();
            this.assets.put(arg4, v0_1);
        }

        ((ObjectMap)v0).put(fileName, new RefCountedContainer(arg5));
    }

    private void addTask(AssetDescriptor assetDesc) {
        AssetLoader v0 = this.getLoader(assetDesc.type, assetDesc.fileName);
        if(v0 == null) {
            throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(assetDesc.type));
        }

        this.tasks.push(new AssetLoadingTask(this, assetDesc, v0, this.executor));
    }

    public void clear() {  // has try-catch handlers
        Object v3;
        try {
            this.loadQueue.clear();
            do {
            }
            while(!this.update());

            ObjectIntMap v5 = new ObjectIntMap();
        label_5:
            if(this.assetTypes.size <= 0) {
                goto label_46;
            }

            v5.clear();
            Array v1 = this.assetTypes.keys().toArray();
            Iterator v6 = v1.iterator();
            while(v6.hasNext()) {
                v5.put(v6.next(), 0);
            }

            v6 = v1.iterator();
            do {
            label_22:
                if(!v6.hasNext()) {
                    goto label_37;
                }

                v3 = this.assetDependencies.get(v6.next());
            }
            while(v3 == null);

            Iterator v7 = ((Array)v3).iterator();
            goto label_29;
        label_37:
            v6 = v1.iterator();
            goto label_38;
            while(true) {
            label_29:
                if(!v7.hasNext()) {
                    goto label_22;
                }

                Object v4 = v7.next();
                v5.put(v4, v5.get(v4, 0) + 1);
            }

            while(true) {
            label_38:
                if(!v6.hasNext()) {
                    goto label_5;
                }

                Object v0 = v6.next();
                if(v5.get(v0, 0) != 0) {
                    continue;
                }

                this.unload(((String)v0));
            }

        label_46:
            this.assets.clear();
            this.assetTypes.clear();
            this.assetDependencies.clear();
            this.loaded = 0;
            this.toLoad = 0;
            this.loadQueue.clear();
            this.tasks.clear();
            return;
        }
        catch(Throwable v8) {
            throw v8;
        }
    }

    public boolean containsAsset(Object arg8) {  // has try-catch handlers
        Object v3;
        try {
            v3 = this.assets.get(arg8.getClass());
            if(v3 == null) {
            }
            else {
                goto label_7;
            }
        }
        catch(Throwable v4) {
            goto label_23;
        }

        boolean v4_1 = false;
        goto label_6;
        try {
        label_7:
            Iterator v1 = ((ObjectMap)v3).keys().iterator();
            do {
                if(v1.hasNext()) {
                    Object v2 = ((ObjectMap)v3).get(v1.next()).getObject(Object.class);
                    if(v2 != arg8 && !arg8.equals(v2)) {
                        continue;
                    }

                    break;
                }
                else {
                    goto label_20;
                }
            }
            while(true);
        }
        catch(Throwable v4) {
            goto label_23;
        }

        v4_1 = true;
        goto label_6;
    label_20:
        v4_1 = false;
    label_6:
        return v4_1;
    label_23:
        throw v4;
    }

    public void dispose() {  // has try-catch handlers
        try {
            this.log.debug("Disposing.");
            this.clear();
            this.executor.dispose();
            return;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public void finishLoading() {
        this.log.debug("Waiting for loading to complete...");
        while(!this.update()) {
            ThreadUtils.yield();
        }

        this.log.debug("Loading complete.");
    }

    public void finishLoadingAsset(String fileName) {
        this.log.debug("Waiting for asset to be loaded: " + fileName);
        while(!this.isLoaded(fileName)) {
            this.update();
            ThreadUtils.yield();
        }

        this.log.debug("Asset loaded: " + fileName);
    }

    public Object get(AssetDescriptor arg3) {  // has try-catch handlers
        try {
            return this.get(arg3.fileName, arg3.type);
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public Object get(String fileName, Class arg8) {  // has try-catch handlers
        Object v0;
        try {
            Object v2 = this.assets.get(arg8);
            if(v2 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            Object v1 = ((ObjectMap)v2).get(fileName);
            if(v1 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            v0 = ((RefCountedContainer)v1).getObject(arg8);
            if(v0 != null) {
                goto label_30;
            }

            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        catch(Throwable v3) {
            throw v3;
        }

    label_30:
        return v0;
    }

    public Object get(String fileName) {  // has try-catch handlers
        Object v0;
        try {
            Object v3 = this.assetTypes.get(fileName);
            if(v3 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            Object v2 = this.assets.get(v3);
            if(v2 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            Object v1 = ((ObjectMap)v2).get(fileName);
            if(v1 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            v0 = ((RefCountedContainer)v1).getObject(((Class)v3));
            if(v0 != null) {
                goto label_40;
            }

            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        catch(Throwable v4) {
            throw v4;
        }

    label_40:
        return v0;
    }

    public Array getAll(Class arg5, Array arg6) {  // has try-catch handlers
        try {
            Object v1 = this.assets.get(arg5);
            if(v1 != null) {
                Iterator v2 = ((ObjectMap)v1).entries().iterator();
                while(v2.hasNext()) {
                    arg6.add(v2.next().value.getObject(arg5));
                }
            }
        }
        catch(Throwable v3) {
            throw v3;
        }

        return arg6;
    }

    public String getAssetFileName(Object arg9) {  // has try-catch handlers
        try {
            Iterator v2 = this.assets.keys().iterator();
        label_3:
            if(v2.hasNext()) {
                Object v5 = this.assets.get(v2.next());
                Iterator v3 = ((ObjectMap)v5).keys().iterator();
                do {
                    if(!v3.hasNext()) {
                        goto label_3;
                    }

                    Object v1 = v3.next();
                    Object v4 = ((ObjectMap)v5).get(v1).getObject(Object.class);
                    if(v4 != arg9 && !arg9.equals(v4)) {
                        continue;
                    }

                    goto label_19;
                }
                while(true);
            }
            else {
                goto label_20;
            }

            goto label_19;
        }
        catch(Throwable v6) {
            goto label_23;
        }

    label_20:
        String v1_1 = null;
    label_19:
        return v1_1;
    label_23:
        throw v6;
    }

    public Array getAssetNames() {  // has try-catch handlers
        try {
            return this.assetTypes.keys().toArray();
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public Class getAssetType(String fileName) {  // has try-catch handlers
        try {
            return this.assetTypes.get(fileName);
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public Array getDependencies(String fileName) {  // has try-catch handlers
        try {
            return this.assetDependencies.get(fileName);
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public String getDiagnostics() {  // has try-catch handlers
        try {
            StringBuffer v1 = new StringBuffer();
            Iterator v5 = this.assetTypes.keys().iterator();
            while(v5.hasNext()) {
                Object v4 = v5.next();
                v1.append(((String)v4));
                v1.append(", ");
                Object v7 = this.assetTypes.get(v4);
                Object v0 = this.assets.get(v7).get(v4);
                Object v3 = this.assetDependencies.get(v4);
                v1.append(ClassReflection.getSimpleName(((Class)v7)));
                v1.append(", refs: ");
                v1.append(((RefCountedContainer)v0).getRefCount());
                if(v3 != null) {
                    v1.append(", deps: [");
                    Iterator v6 = ((Array)v3).iterator();
                    while(v6.hasNext()) {
                        v1.append(v6.next());
                        v1.append(",");
                    }

                    v1.append("]");
                }

                v1.append("\n");
            }

            return v1.toString();
        }
        catch(Throwable v8) {
            throw v8;
        }
    }

    public int getLoadedAssets() {  // has try-catch handlers
        try {
            return this.assetTypes.size;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public AssetLoader getLoader(Class arg8, String fileName) {
        Object v5;
        AssetLoader v5_1;
        Object v3 = this.loaders.get(arg8);
        if(v3 == null || ((ObjectMap)v3).size < 1) {
            v5_1 = null;
        }
        else if(fileName == null) {
            v5 = ((ObjectMap)v3).get("");
        }
        else {
            Object v4 = null;
            int v2 = -1;
            Iterator v1 = ((ObjectMap)v3).entries().iterator();
            while(v1.hasNext()) {
                Object v0 = v1.next();
                if(((Entry)v0).key.length() <= v2) {
                    continue;
                }

                if(!fileName.endsWith(((Entry)v0).key)) {
                    continue;
                }

                v4 = ((Entry)v0).value;
                v2 = ((Entry)v0).key.length();
            }

            v5 = v4;
        }

        return v5_1;
    }

    public AssetLoader getLoader(Class arg2) {
        return this.getLoader(arg2, null);
    }

    public Logger getLogger() {
        return this.log;
    }

    public float getProgress() {  // has try-catch handlers
        float v0 = 1f;
        try {
            if(this.toLoad != 0) {
                goto label_4;
            }

            goto label_3;
        }
        catch(Throwable v0_1) {
            goto label_13;
        }

    label_4:
        v0 = 1f;
        try {
            v0 = Math.min(v0, (((float)this.loaded)) / (((float)this.toLoad)));
        }
        catch(Throwable v0_1) {
        label_13:
            throw v0_1;
        }

    label_3:
        return v0;
    }

    public int getQueuedAssets() {  // has try-catch handlers
        try {
            return this.loadQueue.size + this.tasks.size();
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public int getReferenceCount(String fileName) {  // has try-catch handlers
        try {
            Object v0 = this.assetTypes.get(fileName);
            if(v0 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            return this.assets.get(v0).get(fileName).getRefCount();
        }
        catch(Throwable v1) {
            throw v1;
        }
    }

    private void handleTaskError(Throwable t) {
        this.log.error("Error loading asset.", t);
        if(this.tasks.isEmpty()) {
            throw new GdxRuntimeException(t);
        }

        Object v3 = this.tasks.pop();
        AssetDescriptor v0 = ((AssetLoadingTask)v3).assetDesc;
        if((((AssetLoadingTask)v3).dependenciesLoaded) && ((AssetLoadingTask)v3).dependencies != null) {
            Iterator v2 = ((AssetLoadingTask)v3).dependencies.iterator();
            while(v2.hasNext()) {
                this.unload(v2.next().fileName);
            }
        }

        this.tasks.clear();
        if(this.listener != null) {
            this.listener.error(v0, t);
            return;
        }

        throw new GdxRuntimeException(t);
    }

    private void incrementRefCountedDependencies(String parent) {
        Object v1 = this.assetDependencies.get(parent);
        if(v1 != null) {
            Iterator v3 = ((Array)v1).iterator();
            while(v3.hasNext()) {
                Object v2 = v3.next();
                this.assets.get(this.assetTypes.get(v2)).get(v2).incRefCount();
                this.incrementRefCountedDependencies(((String)v2));
            }
        }
    }

    void injectDependencies(String parentAssetFilename, Array arg6) {  // has try-catch handlers
        try {
            ObjectSet v2 = this.injected;
            Iterator v1 = arg6.iterator();
            while(true) {
                if(!v1.hasNext()) {
                    break;
                }

                Object v0 = v1.next();
                if(v2.contains(((AssetDescriptor)v0).fileName)) {
                    continue;
                }

                v2.add(((AssetDescriptor)v0).fileName);
                this.injectDependency(parentAssetFilename, ((AssetDescriptor)v0));
            }

            v2.clear();
            return;
        }
        catch(Throwable v3) {
            throw v3;
        }
    }

    private void injectDependency(String parentAssetFilename, AssetDescriptor dependendAssetDesc) {  // has try-catch handlers
        Array v1_1;
        try {
            Object v1 = this.assetDependencies.get(parentAssetFilename);
            if(v1 == null) {
                v1_1 = new Array();
                this.assetDependencies.put(parentAssetFilename, v1_1);
            }

            v1_1.add(dependendAssetDesc.fileName);
            if(this.isLoaded(dependendAssetDesc.fileName)) {
                this.log.debug("Dependency already loaded: " + dependendAssetDesc);
                this.assets.get(this.assetTypes.get(dependendAssetDesc.fileName)).get(dependendAssetDesc.fileName).incRefCount();
                this.incrementRefCountedDependencies(dependendAssetDesc.fileName);
            }
            else {
                this.log.info("Loading dependency: " + dependendAssetDesc);
                this.addTask(dependendAssetDesc);
            }
        }
        catch(Throwable v3) {
            throw v3;
        }
    }

    public boolean isLoaded(String fileName) {  // has try-catch handlers
        boolean v0;
        if(fileName == null) {
            v0 = false;
        }
        else {
            try {
                v0 = this.assetTypes.containsKey(fileName);
            }
            catch(Throwable v0_1) {
                throw v0_1;
            }
        }

        return v0;
    }

    public boolean isLoaded(String fileName, Class type) {  // has try-catch handlers
        boolean v2 = false;
        try {
            Object v1 = this.assets.get(type);
            if(v1 != null) {
                Object v0 = ((ObjectMap)v1).get(fileName);
                if(v0 != null) {
                    if(((RefCountedContainer)v0).getObject(type) == null) {
                        goto label_4;
                    }

                    goto label_9;
                }
            }

            goto label_4;
        }
        catch(Throwable v2_1) {
            throw v2_1;
        }

    label_9:
        v2 = true;
    label_4:
        return v2;
    }

    public void load(String fileName, Class arg10, AssetLoaderParameters arg11) {  // has try-catch handlers
        try {
            if(this.getLoader(arg10, fileName) == null) {
                throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(arg10));
            }

            if(this.loadQueue.size == 0) {
                this.loaded = 0;
                this.toLoad = 0;
            }

            int v2;
            for(v2 = 0; v2 < this.loadQueue.size; ++v2) {
                Object v1 = this.loadQueue.get(v2);
                if((((AssetDescriptor)v1).fileName.equals(fileName)) && !((AssetDescriptor)v1).type.equals(arg10)) {
                    throw new GdxRuntimeException("Asset with name \'" + fileName + "\' already in preload queue, but has different type (expected: " + ClassReflection.getSimpleName(arg10) + ", found: " + ClassReflection.getSimpleName(((AssetDescriptor)v1).type) + ")");
                }
            }

            for(v2 = 0; v2 < this.tasks.size(); ++v2) {
                AssetDescriptor v1_1 = this.tasks.get(v2).assetDesc;
                if((v1_1.fileName.equals(fileName)) && !v1_1.type.equals(arg10)) {
                    throw new GdxRuntimeException("Asset with name \'" + fileName + "\' already in task list, but has different type (expected: " + ClassReflection.getSimpleName(arg10) + ", found: " + ClassReflection.getSimpleName(v1_1.type) + ")");
                }
            }

            Object v4 = this.assetTypes.get(fileName);
            if(v4 != null && !v4.equals(arg10)) {
                throw new GdxRuntimeException("Asset with name \'" + fileName + "\' already loaded, but has different type (expected: " + ClassReflection.getSimpleName(arg10) + ", found: " + ClassReflection.getSimpleName(((Class)v4)) + ")");
            }

            ++this.toLoad;
            AssetDescriptor v0 = new AssetDescriptor(fileName, arg10, arg11);
            this.loadQueue.add(v0);
            this.log.debug("Queued: " + v0);
            return;
        }
        catch(Throwable v5) {
            throw v5;
        }
    }

    public void load(AssetDescriptor desc) {  // has try-catch handlers
        try {
            this.load(desc.fileName, desc.type, desc.params);
            return;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public void load(String fileName, Class arg3) {  // has try-catch handlers
        AssetLoaderParameters v0 = null;
        try {
            this.load(fileName, arg3, v0);
            return;
        }
        catch(Throwable v0_1) {
            throw v0_1;
        }
    }

    private void nextTask() {
        Object v0 = this.loadQueue.removeIndex(0);
        if(this.isLoaded(((AssetDescriptor)v0).fileName)) {
            this.log.debug("Already loaded: " + v0);
            this.assets.get(this.assetTypes.get(((AssetDescriptor)v0).fileName)).get(((AssetDescriptor)v0).fileName).incRefCount();
            this.incrementRefCountedDependencies(((AssetDescriptor)v0).fileName);
            if(((AssetDescriptor)v0).params != null && ((AssetDescriptor)v0).params.loadedCallback != null) {
                ((AssetDescriptor)v0).params.loadedCallback.finishedLoading(this, ((AssetDescriptor)v0).fileName, ((AssetDescriptor)v0).type);
            }

            ++this.loaded;
        }
        else {
            this.log.info("Loading: " + v0);
            this.addTask(((AssetDescriptor)v0));
        }
    }

    public void setErrorListener(AssetErrorListener listener) {  // has try-catch handlers
        try {
            this.listener = listener;
            return;
        }
        catch(Throwable v0) {
            throw v0;
        }
    }

    public void setLoader(Class arg2, AssetLoader arg3) {  // has try-catch handlers
        String v0 = null;
        try {
            this.setLoader(arg2, v0, arg3);
            return;
        }
        catch(Throwable v0_1) {
            throw v0_1;
        }
    }

    public void setLoader(Class arg5, String suffix, AssetLoader arg7) {  // has try-catch handlers
        ObjectMap v0_1;
        if(arg5 != null) {
            goto label_6;
        }

        try {
            throw new IllegalArgumentException("type cannot be null.");
        }
        catch(Throwable v1) {
            goto label_5;
        }

    label_6:
        if(arg7 != null) {
            goto label_10;
        }

        try {
            throw new IllegalArgumentException("loader cannot be null.");
        label_10:
            this.log.debug("Loader set: " + ClassReflection.getSimpleName(arg5) + " -> " + ClassReflection.getSimpleName(arg7.getClass()));
            Object v0 = this.loaders.get(arg5);
            if(v0 == null) {
                ObjectMap v1_1 = this.loaders;
                v0_1 = new ObjectMap();
                v1_1.put(arg5, v0_1);
            }

            if(suffix == null) {
                suffix = "";
            }

            v0_1.put(suffix, arg7);
            return;
        }
        catch(Throwable v1) {
        label_5:
            throw v1;
        }
    }

    public void setLogger(Logger logger) {
        this.log = logger;
    }

    public void setReferenceCount(String fileName, int refCount) {  // has try-catch handlers
        try {
            Object v0 = this.assetTypes.get(fileName);
            if(v0 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            this.assets.get(v0).get(fileName).setRefCount(refCount);
            return;
        }
        catch(Throwable v1) {
            throw v1;
        }
    }

    public void unload(String fileName) {  // has try-catch handlers
        try {
            if(this.tasks.size() > 0) {
                Object v1 = this.tasks.firstElement();
                if(((AssetLoadingTask)v1).assetDesc.fileName.equals(fileName)) {
                    ((AssetLoadingTask)v1).cancel = true;
                    this.log.debug("Unload (from tasks): " + fileName);
                }
                else {
                    goto label_19;
                }
            }
            else {
                goto label_19;
            }

            return;
        }
        catch(Throwable v8) {
            goto label_46;
        }

    label_19:
        int v4 = -1;
        int v5 = 0;
        try {
            while(true) {
            label_21:
                if(v5 < this.loadQueue.size) {
                    if(this.loadQueue.get(v5).fileName.equals(fileName)) {
                        v4 = v5;
                    }
                    else {
                        break;
                    }
                }

                goto label_30;
            }
        }
        catch(Throwable v8) {
            goto label_46;
        }

        ++v5;
        goto label_21;
        try {
        label_30:
            if(v4 == -1) {
                goto label_49;
            }

            --this.toLoad;
            this.loadQueue.removeIndex(v4);
            this.log.debug("Unload (from queue): " + fileName);
            return;
        label_49:
            Object v7 = this.assetTypes.get(fileName);
            if(v7 == null) {
                throw new GdxRuntimeException("Asset not loaded: " + fileName);
            }

            Object v0 = this.assets.get(v7).get(fileName);
            ((RefCountedContainer)v0).decRefCount();
            if(((RefCountedContainer)v0).getRefCount() <= 0) {
                this.log.debug("Unload (dispose): " + fileName);
                if((((RefCountedContainer)v0).getObject(Object.class) instanceof Disposable)) {
                    ((RefCountedContainer)v0).getObject(Object.class).dispose();
                }

                this.assetTypes.remove(fileName);
                this.assets.get(v7).remove(fileName);
            }
            else {
                this.log.debug("Unload (decrement): " + fileName);
            }

            Object v2 = this.assetDependencies.get(fileName);
            if(v2 != null) {
                Iterator v6 = ((Array)v2).iterator();
                while(v6.hasNext()) {
                    Object v3 = v6.next();
                    if(!this.isLoaded(((String)v3))) {
                        continue;
                    }

                    this.unload(((String)v3));
                }
            }

            if(((RefCountedContainer)v0).getRefCount() > 0) {
                return;
            }

            this.assetDependencies.remove(fileName);
        }
        catch(Throwable v8) {
            goto label_46;
        }

        return;
    label_46:
        throw v8;
    }

    public boolean update() {  // has try-catch handlers
        boolean v1 = true;
        try {
            if(this.tasks.size() == 0) {
                while(this.loadQueue.size != 0) {
                    if(this.tasks.size() != 0) {
                        break;
                    }

                    this.nextTask();
                }

                if(this.tasks.size() == 0) {
                    goto label_18;
                }
            }

            if((this.updateTask()) && this.loadQueue.size == 0 && this.tasks.size() == 0) {
                goto label_18;
            }
        }
        catch(Throwable v1_1) {
        label_35:
            throw v1_1;
        }
        catch(Throwable v0) {
            try {
                this.handleTaskError(v0);
                if(this.loadQueue.size != 0) {
                }
                else {
                    goto label_18;
                }
            }
            catch(Throwable v1_1) {
                goto label_35;
            }

            v1 = false;
            goto label_18;
        }

        v1 = false;
    label_18:
        return v1;
    }

    public boolean update(int millis) {
        boolean v0;
        long v2 = TimeUtils.millis() + (((long)millis));
        while(true) {
            v0 = this.update();
            if(!v0 && TimeUtils.millis() <= v2) {
                ThreadUtils.yield();
                continue;
            }

            break;
        }

        return v0;
    }

    private boolean updateTask() {
        boolean v3 = true;
        Object v2 = this.tasks.peek();
        if((((AssetLoadingTask)v2).cancel) || (((AssetLoadingTask)v2).update())) {
            if(this.tasks.size() == 1) {
                ++this.loaded;
            }

            this.tasks.pop();
            if(!((AssetLoadingTask)v2).cancel) {
                goto label_18;
            }

            goto label_17;
        label_18:
            this.addAsset(((AssetLoadingTask)v2).assetDesc.fileName, ((AssetLoadingTask)v2).assetDesc.type, ((AssetLoadingTask)v2).getAsset());
            if(((AssetLoadingTask)v2).assetDesc.params != null && ((AssetLoadingTask)v2).assetDesc.params.loadedCallback != null) {
                ((AssetLoadingTask)v2).assetDesc.params.loadedCallback.finishedLoading(this, ((AssetLoadingTask)v2).assetDesc.fileName, ((AssetLoadingTask)v2).assetDesc.type);
            }

            this.log.debug("Loaded: " + (((float)(TimeUtils.nanoTime() - ((AssetLoadingTask)v2).startTime))) / 1000000f + "ms " + ((AssetLoadingTask)v2).assetDesc);
        }
        else {
            v3 = false;
        }

    label_17:
        return v3;
    }
}

