// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.DefaultRenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public class ModelBatch implements Disposable {
    public class RenderablePool extends Pool {
        protected Array obtained;

        protected RenderablePool() {
            super();
            this.obtained = new Array();
        }

        public void flush() {
            super.freeAll(this.obtained);
            this.obtained.clear();
        }

        protected Renderable newObject() {
            return new Renderable();
        }

        protected Object newObject() {
            return this.newObject();
        }

        public Renderable obtain() {
            Object v0 = super.obtain();
            ((Renderable)v0).environment = null;
            ((Renderable)v0).material = null;
            ((Renderable)v0).mesh = null;
            ((Renderable)v0).shader = null;
            this.obtained.add(v0);
            return ((Renderable)v0);
        }

        public Object obtain() {
            return this.obtain();
        }
    }

    protected Camera camera;
    protected final RenderContext context;
    private final boolean ownContext;
    protected final Array renderables;
    protected final RenderablePool renderablesPool;
    protected final ShaderProvider shaderProvider;
    protected final RenderableSorter sorter;

    public ModelBatch() {
        this(null, null, null);
    }

    public ModelBatch(RenderContext context, ShaderProvider shaderProvider, RenderableSorter sorter) {
        DefaultShaderProvider v4_1;
        boolean v0;
        DefaultRenderableSorter v5_1;
        super();
        this.renderablesPool = new RenderablePool();
        this.renderables = new Array();
        if(sorter == null) {
            v5_1 = new DefaultRenderableSorter();
        }

        this.sorter = ((RenderableSorter)v5_1);
        if(context == null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.ownContext = v0;
        if(context == null) {
            context = new RenderContext(new DefaultTextureBinder(1, 1));
        }

        this.context = context;
        if(shaderProvider == null) {
            v4_1 = new DefaultShaderProvider();
        }

        this.shaderProvider = ((ShaderProvider)v4_1);
    }

    public ModelBatch(FileHandle vertexShader, FileHandle fragmentShader) {
        this(null, new DefaultShaderProvider(vertexShader, fragmentShader), null);
    }

    public ModelBatch(RenderContext context) {
        this(context, null, null);
    }

    public ModelBatch(RenderContext context, RenderableSorter sorter) {
        this(context, null, sorter);
    }

    public ModelBatch(RenderContext context, ShaderProvider shaderProvider) {
        this(context, shaderProvider, null);
    }

    public ModelBatch(RenderableSorter sorter) {
        this(null, null, sorter);
    }

    public ModelBatch(ShaderProvider shaderProvider) {
        this(null, shaderProvider, null);
    }

    public ModelBatch(ShaderProvider shaderProvider, RenderableSorter sorter) {
        this(null, shaderProvider, sorter);
    }

    public ModelBatch(String vertexShader, String fragmentShader) {
        this(null, new DefaultShaderProvider(vertexShader, fragmentShader), null);
    }

    public void begin(Camera cam) {
        if(this.camera != null) {
            throw new GdxRuntimeException("Call end() first.");
        }

        this.camera = cam;
        if(this.ownContext) {
            this.context.begin();
        }
    }

    public void dispose() {
        this.shaderProvider.dispose();
    }

    public void end() {
        this.flush();
        if(this.ownContext) {
            this.context.end();
        }

        this.camera = null;
    }

    public void flush() {
        this.sorter.sort(this.camera, this.renderables);
        Shader v0 = null;
        int v1;
        for(v1 = 0; v1 < this.renderables.size; ++v1) {
            Object v2 = this.renderables.get(v1);
            if(v0 != ((Renderable)v2).shader) {
                if(v0 != null) {
                    v0.end();
                }

                v0 = ((Renderable)v2).shader;
                v0.begin(this.camera, this.context);
            }

            v0.render(((Renderable)v2));
        }

        if(v0 != null) {
            v0.end();
        }

        this.renderablesPool.flush();
        this.renderables.clear();
    }

    public Camera getCamera() {
        return this.camera;
    }

    public RenderContext getRenderContext() {
        return this.context;
    }

    public RenderableSorter getRenderableSorter() {
        return this.sorter;
    }

    public ShaderProvider getShaderProvider() {
        return this.shaderProvider;
    }

    public boolean ownsRenderContext() {
        return this.ownContext;
    }

    public void render(Renderable renderable) {
        renderable.shader = this.shaderProvider.getShader(renderable);
        renderable.mesh.setAutoBind(false);
        this.renderables.add(renderable);
    }

    public void render(RenderableProvider renderableProvider) {
        int v1 = this.renderables.size;
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        int v0;
        for(v0 = v1; v0 < this.renderables.size; ++v0) {
            Object v2 = this.renderables.get(v0);
            ((Renderable)v2).shader = this.shaderProvider.getShader(((Renderable)v2));
        }
    }

    public void render(RenderableProvider renderableProvider, Environment environment) {
        int v1 = this.renderables.size;
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        int v0;
        for(v0 = v1; v0 < this.renderables.size; ++v0) {
            Object v2 = this.renderables.get(v0);
            ((Renderable)v2).environment = environment;
            ((Renderable)v2).shader = this.shaderProvider.getShader(((Renderable)v2));
        }
    }

    public void render(RenderableProvider renderableProvider, Environment environment, Shader shader) {
        int v1 = this.renderables.size;
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        int v0;
        for(v0 = v1; v0 < this.renderables.size; ++v0) {
            Object v2 = this.renderables.get(v0);
            ((Renderable)v2).environment = environment;
            ((Renderable)v2).shader = shader;
            ((Renderable)v2).shader = this.shaderProvider.getShader(((Renderable)v2));
        }
    }

    public void render(RenderableProvider renderableProvider, Shader shader) {
        int v1 = this.renderables.size;
        renderableProvider.getRenderables(this.renderables, this.renderablesPool);
        int v0;
        for(v0 = v1; v0 < this.renderables.size; ++v0) {
            Object v2 = this.renderables.get(v0);
            ((Renderable)v2).shader = shader;
            ((Renderable)v2).shader = this.shaderProvider.getShader(((Renderable)v2));
        }
    }

    public void render(Iterable arg4) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.render(v0.next());
        }
    }

    public void render(Iterable arg4, Environment environment) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.render(v0.next(), environment);
        }
    }

    public void render(Iterable arg4, Environment environment, Shader shader) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.render(v0.next(), environment, shader);
        }
    }

    public void render(Iterable arg4, Shader shader) {
        Iterator v0 = arg4.iterator();
        while(v0.hasNext()) {
            this.render(v0.next(), shader);
        }
    }

    public void setCamera(Camera cam) {
        if(this.camera == null) {
            throw new GdxRuntimeException("Call begin() first.");
        }

        if(this.renderables.size > 0) {
            this.flush();
        }

        this.camera = cam;
    }
}

