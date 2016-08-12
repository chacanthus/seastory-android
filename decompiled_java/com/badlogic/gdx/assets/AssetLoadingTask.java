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
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

class AssetLoadingTask implements AsyncTask {
    volatile Object asset;
    final AssetDescriptor assetDesc;
    volatile boolean asyncDone;
    volatile boolean cancel;
    volatile Array dependencies;
    volatile boolean dependenciesLoaded;
    volatile AsyncResult depsFuture;
    final AsyncExecutor executor;
    volatile AsyncResult loadFuture;
    final AssetLoader loader;
    AssetManager manager;
    final long startTime;
    int ticks;

    public AssetLoadingTask(AssetManager manager, AssetDescriptor assetDesc, AssetLoader loader, AsyncExecutor threadPool) {
        long v0;
        super();
        this.asyncDone = false;
        this.dependenciesLoaded = false;
        this.depsFuture = null;
        this.loadFuture = null;
        this.asset = null;
        this.ticks = 0;
        this.cancel = false;
        this.manager = manager;
        this.assetDesc = assetDesc;
        this.loader = loader;
        this.executor = threadPool;
        if(manager.log.getLevel() == 3) {
            v0 = TimeUtils.nanoTime();
        }
        else {
            v0 = 0;
        }

        this.startTime = v0;
    }

    public Object call() throws Exception {
        return this.call();
    }

    public Void call() throws Exception {
        AssetLoader v0 = this.loader;
        if(!this.dependenciesLoaded) {
            this.dependencies = ((AsynchronousAssetLoader)v0).getDependencies(this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
            if(this.dependencies != null) {
                this.manager.injectDependencies(this.assetDesc.fileName, this.dependencies);
            }
            else {
                ((AsynchronousAssetLoader)v0).loadAsync(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
                this.asyncDone = true;
            }
        }
        else {
            ((AsynchronousAssetLoader)v0).loadAsync(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
        }

        return null;
    }

    public Object getAsset() {
        return this.asset;
    }

    private void handleAsyncLoader() {  // has try-catch handlers
        AssetLoader v0 = this.loader;
        if(this.dependenciesLoaded) {
            if(this.loadFuture == null && !this.asyncDone) {
                this.loadFuture = this.executor.submit(((AsyncTask)this));
                return;
            }

            if(!this.asyncDone) {
                goto label_60;
            }

            this.asset = ((AsynchronousAssetLoader)v0).loadSync(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
            return;
        label_60:
            if(!this.loadFuture.isDone()) {
                return;
            }

            try {
                this.loadFuture.get();
            }
            catch(Exception v1) {
                throw new GdxRuntimeException("Couldn\'t load asset: " + this.assetDesc.fileName, ((Throwable)v1));
            }

            this.asset = ((AsynchronousAssetLoader)v0).loadSync(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
        }
        else if(this.depsFuture == null) {
            this.depsFuture = this.executor.submit(((AsyncTask)this));
        }
        else if(this.depsFuture.isDone()) {
            try {
                this.depsFuture.get();
            }
            catch(Exception v1) {
                throw new GdxRuntimeException("Couldn\'t load dependencies of asset: " + this.assetDesc.fileName, ((Throwable)v1));
            }

            this.dependenciesLoaded = true;
            if(this.asyncDone) {
                this.asset = ((AsynchronousAssetLoader)v0).loadSync(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
            }
        }
    }

    private void handleSyncLoader() {
        AssetLoader v0 = this.loader;
        if(!this.dependenciesLoaded) {
            this.dependenciesLoaded = true;
            this.dependencies = ((SynchronousAssetLoader)v0).getDependencies(this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
            if(this.dependencies == null) {
                this.asset = ((SynchronousAssetLoader)v0).load(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
            }
            else {
                this.manager.injectDependencies(this.assetDesc.fileName, this.dependencies);
            }
        }
        else {
            this.asset = ((SynchronousAssetLoader)v0).load(this.manager, this.assetDesc.fileName, this.resolve(this.loader, this.assetDesc), this.assetDesc.params);
        }
    }

    private FileHandle resolve(AssetLoader loader, AssetDescriptor assetDesc) {
        if(assetDesc.file == null) {
            assetDesc.file = loader.resolve(assetDesc.fileName);
        }

        return assetDesc.file;
    }

    public boolean update() {
        boolean v0;
        ++this.ticks;
        if((this.loader instanceof SynchronousAssetLoader)) {
            this.handleSyncLoader();
        }
        else {
            this.handleAsyncLoader();
        }

        if(this.asset != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }
}

