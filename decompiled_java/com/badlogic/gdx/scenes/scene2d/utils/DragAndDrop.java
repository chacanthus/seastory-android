// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap$Entry;
import java.util.Iterator;

public class DragAndDrop {
    public class Payload {
        Actor dragActor;
        Actor invalidDragActor;
        Object object;
        Actor validDragActor;

        public Payload() {
            super();
        }

        public Actor getDragActor() {
            return this.dragActor;
        }

        public Actor getInvalidDragActor() {
            return this.invalidDragActor;
        }

        public Object getObject() {
            return this.object;
        }

        public Actor getValidDragActor() {
            return this.validDragActor;
        }

        public void setDragActor(Actor dragActor) {
            this.dragActor = dragActor;
        }

        public void setInvalidDragActor(Actor invalidDragActor) {
            this.invalidDragActor = invalidDragActor;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public void setValidDragActor(Actor validDragActor) {
            this.validDragActor = validDragActor;
        }
    }

    public abstract class Source {
        public Source(Actor actor) {
            super();
            if(actor == null) {
                throw new IllegalArgumentException("actor cannot be null.");
            }

            this.actor = actor;
        }

        public abstract Payload dragStart(InputEvent arg0, float arg1, float arg2, int arg3);

        public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
        }

        public Actor getActor() {
            return this.actor;
        }
    }

    public abstract class Target {
        final Actor actor;

        public Target(Actor actor) {
            super();
            if(actor == null) {
                throw new IllegalArgumentException("actor cannot be null.");
            }

            this.actor = actor;
            Stage v0 = actor.getStage();
            if(v0 != null && actor == v0.getRoot()) {
                throw new IllegalArgumentException("The stage root cannot be a drag and drop target.");
            }
        }

        public abstract boolean drag(Source arg0, Payload arg1, float arg2, float arg3, int arg4);

        public abstract void drop(Source arg0, Payload arg1, float arg2, float arg3, int arg4);

        public Actor getActor() {
            return this.actor;
        }

        public void reset(Source source, Payload payload) {
        }
    }

    int activePointer;
    private int button;
    boolean cancelTouchFocus;
    Actor dragActor;
    float dragActorX;
    float dragActorY;
    long dragStartTime;
    int dragTime;
    boolean isValidTarget;
    boolean keepWithinStage;
    Payload payload;
    ObjectMap sourceListeners;
    private float tapSquareSize;
    Target target;
    Array targets;
    static final Vector2 tmpVector;
    float touchOffsetX;
    float touchOffsetY;

    static  {
        DragAndDrop.tmpVector = new Vector2();
    }

    public DragAndDrop() {
        super();
        this.targets = new Array();
        this.sourceListeners = new ObjectMap();
        this.tapSquareSize = 8f;
        this.dragActorX = 14f;
        this.dragActorY = -20f;
        this.dragTime = 250;
        this.activePointer = -1;
        this.cancelTouchFocus = true;
        this.keepWithinStage = true;
    }

    public void addSource(Source source) {
        com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop$1 v0 = new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                Object v2;
                if(DragAndDrop.this.payload != null && pointer == DragAndDrop.this.activePointer) {
                    Stage v16 = event.getStage();
                    Touchable v11 = null;
                    if(DragAndDrop.this.dragActor != null) {
                        v11 = DragAndDrop.this.dragActor.getTouchable();
                        DragAndDrop.this.dragActor.setTouchable(Touchable.disabled);
                    }

                    Object v15 = null;
                    DragAndDrop.this.isValidTarget = false;
                    float v17 = event.getStageX() + DragAndDrop.this.touchOffsetX;
                    float v18 = event.getStageY() + DragAndDrop.this.touchOffsetY;
                    Actor v12 = event.getStage().hit(v17, v18, true);
                    if(v12 == null) {
                        v12 = event.getStage().hit(v17, v18, false);
                    }

                    if(v12 != null) {
                        int v13 = 0;
                        int v14 = DragAndDrop.this.targets.size;
                        while(true) {
                            if(v13 < v14) {
                                v2 = DragAndDrop.this.targets.get(v13);
                                if(!((Target)v2).actor.isAscendantOf(v12)) {
                                    ++v13;
                                    continue;
                                }
                                else {
                                    break;
                                }
                            }

                            goto label_90;
                        }

                        v15 = v2;
                        ((Target)v2).actor.stageToLocalCoordinates(DragAndDrop.tmpVector.set(v17, v18));
                        DragAndDrop.this.isValidTarget = ((Target)v2).drag(this.val$source, DragAndDrop.this.payload, DragAndDrop.tmpVector.x, DragAndDrop.tmpVector.y, pointer);
                    }

                label_90:
                    if(v15 != DragAndDrop.this.target) {
                        if(DragAndDrop.this.target != null) {
                            DragAndDrop.this.target.reset(this.val$source, DragAndDrop.this.payload);
                        }

                        DragAndDrop.this.target = ((Target)v15);
                    }

                    if(DragAndDrop.this.dragActor != null) {
                        DragAndDrop.this.dragActor.setTouchable(v11);
                    }

                    Actor v8 = null;
                    if(DragAndDrop.this.target != null) {
                        if(DragAndDrop.this.isValidTarget) {
                            v8 = DragAndDrop.this.payload.validDragActor;
                        }
                        else {
                            v8 = DragAndDrop.this.payload.invalidDragActor;
                        }
                    }

                    if(v8 == null) {
                        v8 = DragAndDrop.this.payload.dragActor;
                    }

                    if(v8 == null) {
                        return;
                    }

                    if(DragAndDrop.this.dragActor != v8) {
                        if(DragAndDrop.this.dragActor != null) {
                            DragAndDrop.this.dragActor.remove();
                        }

                        DragAndDrop.this.dragActor = v8;
                        v16.addActor(v8);
                    }

                    float v9 = event.getStageX() + DragAndDrop.this.dragActorX;
                    float v10 = event.getStageY() + DragAndDrop.this.dragActorY - v8.getHeight();
                    if(DragAndDrop.this.keepWithinStage) {
                        if(v9 < 0f) {
                            v9 = 0f;
                        }

                        if(v10 < 0f) {
                            v10 = 0f;
                        }

                        if(v8.getWidth() + v9 > v16.getWidth()) {
                            v9 = v16.getWidth() - v8.getWidth();
                        }

                        if(v8.getHeight() + v10 <= v16.getHeight()) {
                            goto label_190;
                        }

                        v10 = v16.getHeight() - v8.getHeight();
                    }

                label_190:
                    v8.setPosition(v9, v10);
                }
            }

            public void dragStart(InputEvent event, float x, float y, int pointer) {
                if(DragAndDrop.this.activePointer != -1) {
                    event.stop();
                }
                else {
                    DragAndDrop.this.activePointer = pointer;
                    DragAndDrop.this.dragStartTime = System.currentTimeMillis();
                    DragAndDrop.this.payload = this.val$source.dragStart(event, this.getTouchDownX(), this.getTouchDownY(), pointer);
                    event.stop();
                    if((DragAndDrop.this.cancelTouchFocus) && DragAndDrop.this.payload != null) {
                        this.val$source.getActor().getStage().cancelTouchFocusExcept(((EventListener)this), this.val$source.getActor());
                    }
                }
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {
                Target v6;
                Payload v9 = null;
                if(pointer == DragAndDrop.this.activePointer) {
                    DragAndDrop.this.activePointer = -1;
                    if(DragAndDrop.this.payload != null) {
                        if(System.currentTimeMillis() - DragAndDrop.this.dragStartTime < (((long)DragAndDrop.this.dragTime))) {
                            DragAndDrop.this.isValidTarget = false;
                        }

                        if(DragAndDrop.this.dragActor != null) {
                            DragAndDrop.this.dragActor.remove();
                        }

                        if(DragAndDrop.this.isValidTarget) {
                            DragAndDrop.this.target.actor.stageToLocalCoordinates(DragAndDrop.tmpVector.set(event.getStageX() + DragAndDrop.this.touchOffsetX, event.getStageY() + DragAndDrop.this.touchOffsetY));
                            DragAndDrop.this.target.drop(this.val$source, DragAndDrop.this.payload, DragAndDrop.tmpVector.x, DragAndDrop.tmpVector.y, pointer);
                        }

                        Source v0 = this.val$source;
                        Payload v5 = DragAndDrop.this.payload;
                        if(DragAndDrop.this.isValidTarget) {
                            v6 = DragAndDrop.this.target;
                        }
                        else {
                            v6 = ((Target)v9);
                        }

                        v0.dragStop(event, x, y, pointer, v5, v6);
                        if(DragAndDrop.this.target != null) {
                            DragAndDrop.this.target.reset(this.val$source, DragAndDrop.this.payload);
                        }

                        DragAndDrop.this.payload = v9;
                        DragAndDrop.this.target = ((Target)v9);
                        DragAndDrop.this.isValidTarget = false;
                        DragAndDrop.this.dragActor = ((Actor)v9);
                    }
                }
            }
        };
        ((DragListener)v0).setTapSquareSize(this.tapSquareSize);
        ((DragListener)v0).setButton(this.button);
        source.actor.addCaptureListener(((EventListener)v0));
        this.sourceListeners.put(source, v0);
    }

    public void addTarget(Target target) {
        this.targets.add(target);
    }

    public void clear() {
        this.targets.clear();
        Iterator v1 = this.sourceListeners.entries().iterator();
        while(v1.hasNext()) {
            Object v0 = v1.next();
            ((Entry)v0).key.actor.removeCaptureListener(((Entry)v0).value);
        }

        this.sourceListeners.clear();
    }

    public Actor getDragActor() {
        return this.dragActor;
    }

    public boolean isDragging() {
        boolean v0;
        if(this.payload != null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void removeSource(Source source) {
        source.actor.removeCaptureListener(this.sourceListeners.remove(source));
    }

    public void removeTarget(Target target) {
        this.targets.removeValue(target, true);
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setCancelTouchFocus(boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }

    public void setDragActorPosition(float dragActorX, float dragActorY) {
        this.dragActorX = dragActorX;
        this.dragActorY = dragActorY;
    }

    public void setDragTime(int dragMillis) {
        this.dragTime = dragMillis;
    }

    public void setKeepWithinStage(boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

    public void setTapSquareSize(float halfTapSquareSize) {
        this.tapSquareSize = halfTapSquareSize;
    }

    public void setTouchOffset(float touchOffsetX, float touchOffsetY) {
        this.touchOffsetX = touchOffsetX;
        this.touchOffsetY = touchOffsetY;
    }
}

