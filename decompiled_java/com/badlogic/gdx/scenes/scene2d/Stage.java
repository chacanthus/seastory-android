// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.Application$ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Table$Debug;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener$FocusEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool$Poolable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Stage extends InputAdapter implements Disposable {
    public final class TouchFocus implements Poolable {
        public TouchFocus() {
            super();
        }

        public void reset() {
            this.listenerActor = null;
            this.listener = null;
        }
    }

    private boolean actionsRequestRendering;
    private final Batch batch;
    static boolean debug;
    private boolean debugAll;
    private final Color debugColor;
    private boolean debugInvisible;
    private boolean debugParentUnderMouse;
    private ShapeRenderer debugShapes;
    private Debug debugTableUnderMouse;
    private boolean debugUnderMouse;
    private Actor keyboardFocus;
    private Actor mouseOverActor;
    private int mouseScreenX;
    private int mouseScreenY;
    private boolean ownsBatch;
    private final Actor[] pointerOverActors;
    private final int[] pointerScreenX;
    private final int[] pointerScreenY;
    private final boolean[] pointerTouched;
    private final Group root;
    private Actor scrollFocus;
    private final Vector2 tempCoords;
    private final SnapshotArray touchFocuses;
    private Viewport viewport;

    public Stage() {
        this(new ScalingViewport(Scaling.stretch, ((float)Gdx.graphics.getWidth()), ((float)Gdx.graphics.getHeight()), new OrthographicCamera()), new SpriteBatch());
        this.ownsBatch = true;
    }

    public Stage(Viewport viewport, Batch batch) {
        super();
        this.tempCoords = new Vector2();
        this.pointerOverActors = new Actor[20];
        this.pointerTouched = new boolean[20];
        this.pointerScreenX = new int[20];
        this.pointerScreenY = new int[20];
        this.touchFocuses = new SnapshotArray(true, 4, TouchFocus.class);
        this.actionsRequestRendering = true;
        this.debugTableUnderMouse = Debug.none;
        this.debugColor = new Color(0f, 1f, 0f, 0.85f);
        if(viewport == null) {
            throw new IllegalArgumentException("viewport cannot be null.");
        }

        if(batch == null) {
            throw new IllegalArgumentException("batch cannot be null.");
        }

        this.viewport = viewport;
        this.batch = batch;
        this.root = new Group();
        this.root.setStage(this);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public Stage(Viewport viewport) {
        this(viewport, new SpriteBatch());
        this.ownsBatch = true;
    }

    public void act() {
        this.act(Math.min(Gdx.graphics.getDeltaTime(), 0.033333f));
    }

    public void act(float delta) {
        int v3 = 0;
        int v1 = this.pointerOverActors.length;
        while(v3 < v1) {
            Actor v2 = this.pointerOverActors[v3];
            if(this.pointerTouched[v3]) {
                this.pointerOverActors[v3] = this.fireEnterAndExit(v2, this.pointerScreenX[v3], this.pointerScreenY[v3], v3);
            }
            else if(v2 != null) {
                this.pointerOverActors[v3] = null;
                this.screenToStageCoordinates(this.tempCoords.set(((float)this.pointerScreenX[v3]), ((float)this.pointerScreenY[v3])));
                Object v0 = Pools.obtain(InputEvent.class);
                ((InputEvent)v0).setType(Type.exit);
                ((InputEvent)v0).setStage(this);
                ((InputEvent)v0).setStageX(this.tempCoords.x);
                ((InputEvent)v0).setStageY(this.tempCoords.y);
                ((InputEvent)v0).setRelatedActor(v2);
                ((InputEvent)v0).setPointer(v3);
                v2.fire(((Event)v0));
                Pools.free(v0);
            }

            ++v3;
        }

        ApplicationType v4 = Gdx.app.getType();
        if(v4 == ApplicationType.Desktop || v4 == ApplicationType.Applet || v4 == ApplicationType.WebGL) {
            this.mouseOverActor = this.fireEnterAndExit(this.mouseOverActor, this.mouseScreenX, this.mouseScreenY, -1);
        }

        this.root.act(delta);
    }

    public void addAction(Action action) {
        this.root.addAction(action);
    }

    public void addActor(Actor actor) {
        this.root.addActor(actor);
    }

    public boolean addCaptureListener(EventListener listener) {
        return this.root.addCaptureListener(listener);
    }

    public boolean addListener(EventListener listener) {
        return this.root.addListener(listener);
    }

    public void addTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        Object v0 = Pools.obtain(TouchFocus.class);
        ((TouchFocus)v0).listenerActor = listenerActor;
        ((TouchFocus)v0).target = target;
        ((TouchFocus)v0).listener = listener;
        ((TouchFocus)v0).pointer = pointer;
        ((TouchFocus)v0).button = button;
        this.touchFocuses.add(v0);
    }

    public void calculateScissors(Rectangle localRect, Rectangle scissorRect) {
        Matrix4 v0;
        this.viewport.calculateScissors(this.batch.getTransformMatrix(), localRect, scissorRect);
        if(this.debugShapes == null || !this.debugShapes.isDrawing()) {
            v0 = this.batch.getTransformMatrix();
        }
        else {
            v0 = this.debugShapes.getTransformMatrix();
        }

        this.viewport.calculateScissors(v0, localRect, scissorRect);
    }

    public void cancelTouchFocus() {
        this.cancelTouchFocusExcept(null, null);
    }

    public void cancelTouchFocus(Actor actor) {
        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.touchUp);
        ((InputEvent)v0).setStageX(-2147483648f);
        ((InputEvent)v0).setStageY(-2147483648f);
        SnapshotArray v5 = this.touchFocuses;
        Object[] v3 = v5.begin();
        int v2 = 0;
        int v4 = v5.size;
        while(v2 < v4) {
            Object v1 = v3[v2];
            if(((TouchFocus)v1).listenerActor == actor && (v5.removeValue(v1, true))) {
                ((InputEvent)v0).setTarget(((TouchFocus)v1).target);
                ((InputEvent)v0).setListenerActor(((TouchFocus)v1).listenerActor);
                ((InputEvent)v0).setPointer(((TouchFocus)v1).pointer);
                ((InputEvent)v0).setButton(((TouchFocus)v1).button);
                ((TouchFocus)v1).listener.handle(((Event)v0));
            }

            ++v2;
        }

        v5.end();
        Pools.free(v0);
    }

    public void cancelTouchFocusExcept(EventListener exceptListener, Actor exceptActor) {
        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.touchUp);
        ((InputEvent)v0).setStageX(-2147483648f);
        ((InputEvent)v0).setStageY(-2147483648f);
        SnapshotArray v5 = this.touchFocuses;
        Object[] v3 = v5.begin();
        int v2 = 0;
        int v4 = v5.size;
        while(v2 < v4) {
            Object v1 = v3[v2];
            if((((TouchFocus)v1).listener != exceptListener || ((TouchFocus)v1).listenerActor != exceptActor) && (v5.removeValue(v1, true))) {
                ((InputEvent)v0).setTarget(((TouchFocus)v1).target);
                ((InputEvent)v0).setListenerActor(((TouchFocus)v1).listenerActor);
                ((InputEvent)v0).setPointer(((TouchFocus)v1).pointer);
                ((InputEvent)v0).setButton(((TouchFocus)v1).button);
                ((TouchFocus)v1).listener.handle(((Event)v0));
            }

            ++v2;
        }

        v5.end();
        Pools.free(v0);
    }

    public void clear() {
        this.unfocusAll();
        this.root.clear();
    }

    private void disableDebug(Actor actor, Actor except) {
        if(actor != except) {
            actor.setDebug(false);
            if((actor instanceof Group)) {
                SnapshotArray v0 = ((Group)actor).children;
                int v1 = 0;
                int v2 = v0.size;
                while(v1 < v2) {
                    this.disableDebug(v0.get(v1), except);
                    ++v1;
                }
            }
        }
    }

    public void dispose() {
        this.clear();
        if(this.ownsBatch) {
            this.batch.dispose();
        }
    }

    public void draw() {
        Camera v1 = this.viewport.getCamera();
        v1.update();
        if(this.root.isVisible()) {
            Batch v0 = this.batch;
            if(v0 != null) {
                v0.setProjectionMatrix(v1.combined);
                v0.begin();
                this.root.draw(v0, 1f);
                v0.end();
            }

            if(!Stage.debug) {
                return;
            }

            this.drawDebug();
        }
    }

    private void drawDebug() {
        Group v0_1;
        if(this.debugShapes == null) {
            this.debugShapes = new ShapeRenderer();
            this.debugShapes.setAutoShapeType(true);
        }

        if((this.debugUnderMouse) || (this.debugParentUnderMouse) || this.debugTableUnderMouse != Debug.none) {
            this.screenToStageCoordinates(this.tempCoords.set(((float)Gdx.input.getX()), ((float)Gdx.input.getY())));
            Actor v0 = this.hit(this.tempCoords.x, this.tempCoords.y, true);
            if(v0 != null) {
                if((this.debugParentUnderMouse) && v0.parent != null) {
                    v0_1 = v0.parent;
                }

                if(this.debugTableUnderMouse == Debug.none) {
                    v0.setDebug(true);
                }
                else {
                    while(v0 != null) {
                        if((v0 instanceof Table)) {
                            break;
                        }

                        v0_1 = v0.parent;
                    }

                    if(v0 == null) {
                        return;
                    }

                    v0.debug(this.debugTableUnderMouse);
                }

                if((this.debugAll) && ((v0 instanceof Group))) {
                    v0.debugAll();
                }

                this.disableDebug(this.root, v0);
            label_47:
                Gdx.gl.glEnable(3042);
                this.debugShapes.setProjectionMatrix(this.viewport.getCamera().combined);
                this.debugShapes.begin();
                this.root.drawDebug(this.debugShapes);
                this.debugShapes.end();
            }
        }
        else if(this.debugAll) {
            this.root.debugAll();
            goto label_47;
        }
        else {
            goto label_47;
        }
    }

    private Actor fireEnterAndExit(Actor overLast, int screenX, int screenY, int pointer) {
        this.screenToStageCoordinates(this.tempCoords.set(((float)screenX), ((float)screenY)));
        Actor v1 = this.hit(this.tempCoords.x, this.tempCoords.y, true);
        if(v1 != overLast) {
            Object v0 = Pools.obtain(InputEvent.class);
            ((InputEvent)v0).setStage(this);
            ((InputEvent)v0).setStageX(this.tempCoords.x);
            ((InputEvent)v0).setStageY(this.tempCoords.y);
            ((InputEvent)v0).setPointer(pointer);
            if(overLast != null) {
                ((InputEvent)v0).setType(Type.exit);
                ((InputEvent)v0).setRelatedActor(v1);
                overLast.fire(((Event)v0));
            }

            if(v1 != null) {
                ((InputEvent)v0).setType(Type.enter);
                ((InputEvent)v0).setRelatedActor(overLast);
                v1.fire(((Event)v0));
            }

            Pools.free(v0);
            overLast = v1;
        }

        return overLast;
    }

    public boolean getActionsRequestRendering() {
        return this.actionsRequestRendering;
    }

    public Array getActors() {
        return this.root.children;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public Camera getCamera() {
        return this.viewport.getCamera();
    }

    public Color getDebugColor() {
        return this.debugColor;
    }

    public float getHeight() {
        return this.viewport.getWorldHeight();
    }

    public Actor getKeyboardFocus() {
        return this.keyboardFocus;
    }

    public Group getRoot() {
        return this.root;
    }

    public Actor getScrollFocus() {
        return this.scrollFocus;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    public float getWidth() {
        return this.viewport.getWorldWidth();
    }

    public Actor hit(float stageX, float stageY, boolean touchable) {
        this.root.parentToLocalCoordinates(this.tempCoords.set(stageX, stageY));
        return this.root.hit(this.tempCoords.x, this.tempCoords.y, touchable);
    }

    public boolean keyDown(int keyCode) {
        Group v2;
        if(this.keyboardFocus == null) {
            v2 = this.root;
        }
        else {
            Actor v2_1 = this.keyboardFocus;
        }

        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.keyDown);
        ((InputEvent)v0).setKeyCode(keyCode);
        ((Actor)v2).fire(((Event)v0));
        boolean v1 = ((InputEvent)v0).isHandled();
        Pools.free(v0);
        return v1;
    }

    public boolean keyTyped(char character) {
        Actor v2_1;
        if(this.keyboardFocus == null) {
            Group v2 = this.root;
        }
        else {
            v2_1 = this.keyboardFocus;
        }

        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.keyTyped);
        ((InputEvent)v0).setCharacter(character);
        v2_1.fire(((Event)v0));
        boolean v1 = ((InputEvent)v0).isHandled();
        Pools.free(v0);
        return v1;
    }

    public boolean keyUp(int keyCode) {
        Group v2;
        if(this.keyboardFocus == null) {
            v2 = this.root;
        }
        else {
            Actor v2_1 = this.keyboardFocus;
        }

        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.keyUp);
        ((InputEvent)v0).setKeyCode(keyCode);
        ((Actor)v2).fire(((Event)v0));
        boolean v1 = ((InputEvent)v0).isHandled();
        Pools.free(v0);
        return v1;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        Group v2_1;
        boolean v1 = false;
        if(screenX >= this.viewport.getScreenX() && screenX < this.viewport.getScreenX() + this.viewport.getScreenWidth() && Gdx.graphics.getHeight() - screenY >= this.viewport.getScreenY() && Gdx.graphics.getHeight() - screenY < this.viewport.getScreenY() + this.viewport.getScreenHeight()) {
            this.mouseScreenX = screenX;
            this.mouseScreenY = screenY;
            this.screenToStageCoordinates(this.tempCoords.set(((float)screenX), ((float)screenY)));
            Object v0 = Pools.obtain(InputEvent.class);
            ((InputEvent)v0).setStage(this);
            ((InputEvent)v0).setType(Type.mouseMoved);
            ((InputEvent)v0).setStageX(this.tempCoords.x);
            ((InputEvent)v0).setStageY(this.tempCoords.y);
            Actor v2 = this.hit(this.tempCoords.x, this.tempCoords.y, true);
            if(v2 == null) {
                v2_1 = this.root;
            }

            ((Actor)v2_1).fire(((Event)v0));
            v1 = ((InputEvent)v0).isHandled();
            Pools.free(v0);
        }

        return v1;
    }

    public boolean removeCaptureListener(EventListener listener) {
        return this.root.removeCaptureListener(listener);
    }

    public boolean removeListener(EventListener listener) {
        return this.root.removeListener(listener);
    }

    public void removeTouchFocus(EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
        SnapshotArray v2 = this.touchFocuses;
        int v1;
        for(v1 = v2.size - 1; v1 >= 0; --v1) {
            Object v0 = v2.get(v1);
            if(((TouchFocus)v0).listener == listener && ((TouchFocus)v0).listenerActor == listenerActor && ((TouchFocus)v0).target == target && ((TouchFocus)v0).pointer == pointer && ((TouchFocus)v0).button == button) {
                v2.removeIndex(v1);
                Pools.free(v0);
            }
        }
    }

    public Vector2 screenToStageCoordinates(Vector2 screenCoords) {
        this.viewport.unproject(screenCoords);
        return screenCoords;
    }

    public boolean scrolled(int amount) {
        Actor v2_1;
        if(this.scrollFocus == null) {
            Group v2 = this.root;
        }
        else {
            v2_1 = this.scrollFocus;
        }

        this.screenToStageCoordinates(this.tempCoords.set(((float)this.mouseScreenX), ((float)this.mouseScreenY)));
        Object v0 = Pools.obtain(InputEvent.class);
        ((InputEvent)v0).setStage(this);
        ((InputEvent)v0).setType(Type.scrolled);
        ((InputEvent)v0).setScrollAmount(amount);
        ((InputEvent)v0).setStageX(this.tempCoords.x);
        ((InputEvent)v0).setStageY(this.tempCoords.y);
        v2_1.fire(((Event)v0));
        boolean v1 = ((InputEvent)v0).isHandled();
        Pools.free(v0);
        return v1;
    }

    public void setActionsRequestRendering(boolean actionsRequestRendering) {
        this.actionsRequestRendering = actionsRequestRendering;
    }

    public void setDebugAll(boolean debugAll) {
        if(this.debugAll != debugAll) {
            this.debugAll = debugAll;
            if(debugAll) {
                Stage.debug = true;
            }
            else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugInvisible(boolean debugInvisible) {
        this.debugInvisible = debugInvisible;
    }

    public void setDebugParentUnderMouse(boolean debugParentUnderMouse) {
        if(this.debugParentUnderMouse != debugParentUnderMouse) {
            this.debugParentUnderMouse = debugParentUnderMouse;
            if(debugParentUnderMouse) {
                Stage.debug = true;
            }
            else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugTableUnderMouse(Debug debugTableUnderMouse) {
        if(debugTableUnderMouse == null) {
            debugTableUnderMouse = Debug.none;
        }

        if(this.debugTableUnderMouse != debugTableUnderMouse) {
            this.debugTableUnderMouse = debugTableUnderMouse;
            if(debugTableUnderMouse != Debug.none) {
                Stage.debug = true;
            }
            else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setDebugTableUnderMouse(boolean debugTableUnderMouse) {
        Debug v0;
        if(debugTableUnderMouse) {
            v0 = Debug.all;
        }
        else {
            v0 = Debug.none;
        }

        this.setDebugTableUnderMouse(v0);
    }

    public void setDebugUnderMouse(boolean debugUnderMouse) {
        if(this.debugUnderMouse != debugUnderMouse) {
            this.debugUnderMouse = debugUnderMouse;
            if(debugUnderMouse) {
                Stage.debug = true;
            }
            else {
                this.root.setDebug(false, true);
            }
        }
    }

    public void setKeyboardFocus(Actor actor) {
        if(this.keyboardFocus != actor) {
            Object v0 = Pools.obtain(FocusEvent.class);
            ((FocusEvent)v0).setStage(this);
            ((FocusEvent)v0).setType(com.badlogic.gdx.scenes.scene2d.utils.FocusListener$FocusEvent$Type.keyboard);
            Actor v1 = this.keyboardFocus;
            if(v1 != null) {
                ((FocusEvent)v0).setFocused(false);
                ((FocusEvent)v0).setRelatedActor(actor);
                v1.fire(((Event)v0));
            }

            if(!((FocusEvent)v0).isCancelled()) {
                this.keyboardFocus = actor;
                if(actor != null) {
                    ((FocusEvent)v0).setFocused(true);
                    ((FocusEvent)v0).setRelatedActor(v1);
                    actor.fire(((Event)v0));
                    if(((FocusEvent)v0).isCancelled()) {
                        this.setKeyboardFocus(v1);
                    }
                }
            }

            Pools.free(v0);
        }
    }

    public void setScrollFocus(Actor actor) {
        if(this.scrollFocus != actor) {
            Object v0 = Pools.obtain(FocusEvent.class);
            ((FocusEvent)v0).setStage(this);
            ((FocusEvent)v0).setType(com.badlogic.gdx.scenes.scene2d.utils.FocusListener$FocusEvent$Type.scroll);
            Actor v1 = this.scrollFocus;
            if(v1 != null) {
                ((FocusEvent)v0).setFocused(false);
                ((FocusEvent)v0).setRelatedActor(actor);
                v1.fire(((Event)v0));
            }

            if(!((FocusEvent)v0).isCancelled()) {
                this.scrollFocus = actor;
                if(actor != null) {
                    ((FocusEvent)v0).setFocused(true);
                    ((FocusEvent)v0).setRelatedActor(v1);
                    actor.fire(((Event)v0));
                    if(((FocusEvent)v0).isCancelled()) {
                        this.setScrollFocus(v1);
                    }
                }
            }

            Pools.free(v0);
        }
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Vector2 stageToScreenCoordinates(Vector2 stageCoords) {
        this.viewport.project(stageCoords);
        stageCoords.y -= ((float)this.viewport.getScreenHeight());
        return stageCoords;
    }

    public Vector2 toScreenCoordinates(Vector2 coords, Matrix4 transformMatrix) {
        return this.viewport.toScreenCoordinates(coords, transformMatrix);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean v1 = false;
        if(screenX >= this.viewport.getScreenX() && screenX < this.viewport.getScreenX() + this.viewport.getScreenWidth() && Gdx.graphics.getHeight() - screenY >= this.viewport.getScreenY() && Gdx.graphics.getHeight() - screenY < this.viewport.getScreenY() + this.viewport.getScreenHeight()) {
            this.pointerTouched[pointer] = true;
            this.pointerScreenX[pointer] = screenX;
            this.pointerScreenY[pointer] = screenY;
            this.screenToStageCoordinates(this.tempCoords.set(((float)screenX), ((float)screenY)));
            Object v0 = Pools.obtain(InputEvent.class);
            ((InputEvent)v0).setType(Type.touchDown);
            ((InputEvent)v0).setStage(this);
            ((InputEvent)v0).setStageX(this.tempCoords.x);
            ((InputEvent)v0).setStageY(this.tempCoords.y);
            ((InputEvent)v0).setPointer(pointer);
            ((InputEvent)v0).setButton(button);
            Actor v2 = this.hit(this.tempCoords.x, this.tempCoords.y, true);
            if(v2 != null) {
                v2.fire(((Event)v0));
            }
            else if(this.root.getTouchable() == Touchable.enabled) {
                this.root.fire(((Event)v0));
            }

            v1 = ((InputEvent)v0).isHandled();
            Pools.free(v0);
        }

        return v1;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean v3;
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        this.mouseScreenX = screenX;
        this.mouseScreenY = screenY;
        if(this.touchFocuses.size == 0) {
            v3 = false;
        }
        else {
            this.screenToStageCoordinates(this.tempCoords.set(((float)screenX), ((float)screenY)));
            Object v0 = Pools.obtain(InputEvent.class);
            ((InputEvent)v0).setType(Type.touchDragged);
            ((InputEvent)v0).setStage(this);
            ((InputEvent)v0).setStageX(this.tempCoords.x);
            ((InputEvent)v0).setStageY(this.tempCoords.y);
            ((InputEvent)v0).setPointer(pointer);
            SnapshotArray v6 = this.touchFocuses;
            Object[] v2 = v6.begin();
            int v4 = 0;
            int v5 = v6.size;
            while(v4 < v5) {
                Object v1 = v2[v4];
                if(((TouchFocus)v1).pointer == pointer && (v6.contains(v1, true))) {
                    ((InputEvent)v0).setTarget(((TouchFocus)v1).target);
                    ((InputEvent)v0).setListenerActor(((TouchFocus)v1).listenerActor);
                    if(((TouchFocus)v1).listener.handle(((Event)v0))) {
                        ((InputEvent)v0).handle();
                    }
                }

                ++v4;
            }

            v6.end();
            v3 = ((InputEvent)v0).isHandled();
            Pools.free(v0);
        }

        return v3;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean v3 = false;
        this.pointerTouched[pointer] = false;
        this.pointerScreenX[pointer] = screenX;
        this.pointerScreenY[pointer] = screenY;
        if(this.touchFocuses.size != 0) {
            this.screenToStageCoordinates(this.tempCoords.set(((float)screenX), ((float)screenY)));
            Object v0 = Pools.obtain(InputEvent.class);
            ((InputEvent)v0).setType(Type.touchUp);
            ((InputEvent)v0).setStage(this);
            ((InputEvent)v0).setStageX(this.tempCoords.x);
            ((InputEvent)v0).setStageY(this.tempCoords.y);
            ((InputEvent)v0).setPointer(pointer);
            ((InputEvent)v0).setButton(button);
            SnapshotArray v6 = this.touchFocuses;
            Object[] v2 = v6.begin();
            int v4 = 0;
            int v5 = v6.size;
            while(v4 < v5) {
                Object v1 = v2[v4];
                if(((TouchFocus)v1).pointer == pointer && ((TouchFocus)v1).button == button && (v6.removeValue(v1, true))) {
                    ((InputEvent)v0).setTarget(((TouchFocus)v1).target);
                    ((InputEvent)v0).setListenerActor(((TouchFocus)v1).listenerActor);
                    if(((TouchFocus)v1).listener.handle(((Event)v0))) {
                        ((InputEvent)v0).handle();
                    }

                    Pools.free(v1);
                }

                ++v4;
            }

            v6.end();
            v3 = ((InputEvent)v0).isHandled();
            Pools.free(v0);
        }

        return v3;
    }

    public void unfocus(Actor actor) {
        Actor v1 = null;
        this.cancelTouchFocus(actor);
        if(this.scrollFocus != null && (this.scrollFocus.isDescendantOf(actor))) {
            this.scrollFocus = v1;
        }

        if(this.keyboardFocus != null && (this.keyboardFocus.isDescendantOf(actor))) {
            this.keyboardFocus = v1;
        }
    }

    public void unfocusAll() {
        this.scrollFocus = null;
        this.keyboardFocus = null;
        this.cancelTouchFocus();
    }
}

