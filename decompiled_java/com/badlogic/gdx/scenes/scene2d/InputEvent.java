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

import com.badlogic.gdx.math.Vector2;

public class InputEvent extends Event {
    public enum Type {
        public static final enum Type enter;
        public static final enum Type exit;
        public static final enum Type keyDown;
        public static final enum Type keyTyped;
        public static final enum Type keyUp;
        public static final enum Type mouseMoved;
        public static final enum Type scrolled;
        public static final enum Type touchDragged;
        public static final enum Type touchUp;

        static  {
            Type.touchDown = new Type("touchDown", 0);
            Type.touchUp = new Type("touchUp", 1);
            Type.touchDragged = new Type("touchDragged", 2);
            Type.mouseMoved = new Type("mouseMoved", 3);
            Type.enter = new Type("enter", 4);
            Type.exit = new Type("exit", 5);
            Type.scrolled = new Type("scrolled", 6);
            Type.keyDown = new Type("keyDown", 7);
            Type.keyUp = new Type("keyUp", 8);
            Type.keyTyped = new Type("keyTyped", 9);
            Type[] v0 = new Type[10];
            v0[0] = Type.touchDown;
            v0[1] = Type.touchUp;
            v0[2] = Type.touchDragged;
            v0[3] = Type.mouseMoved;
            v0[4] = Type.enter;
            v0[5] = Type.exit;
            v0[6] = Type.scrolled;
            v0[7] = Type.keyDown;
            v0[8] = Type.keyUp;
            v0[9] = Type.keyTyped;
            Type.$VALUES = v0;
        }

        private Type(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Type valueOf(String name) {
            return Enum.valueOf(Type.class, name);
        }

        public static Type[] values() {
            return Type.$VALUES.clone();
        }
    }

    private int button;
    private char character;
    private int keyCode;
    private int pointer;
    private Actor relatedActor;
    private int scrollAmount;
    private float stageX;
    private float stageY;
    private Type type;

    public InputEvent() {
        super();
    }

    public int getButton() {
        return this.button;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public int getPointer() {
        return this.pointer;
    }

    public Actor getRelatedActor() {
        return this.relatedActor;
    }

    public int getScrollAmount() {
        return this.scrollAmount;
    }

    public float getStageX() {
        return this.stageX;
    }

    public float getStageY() {
        return this.stageY;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isTouchFocusCancel() {
        boolean v0;
        float v1 = -2147483648f;
        if(this.stageX == v1 || this.stageY == v1) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public void reset() {
        super.reset();
        this.relatedActor = null;
        this.button = -1;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public void setRelatedActor(Actor relatedActor) {
        this.relatedActor = relatedActor;
    }

    public void setScrollAmount(int scrollAmount) {
        this.scrollAmount = scrollAmount;
    }

    public void setStageX(float stageX) {
        this.stageX = stageX;
    }

    public void setStageY(float stageY) {
        this.stageY = stageY;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Vector2 toCoordinates(Actor actor, Vector2 actorCoords) {
        actorCoords.set(this.stageX, this.stageY);
        actor.stageToLocalCoordinates(actorCoords);
        return actorCoords;
    }

    public String toString() {
        return this.type.toString();
    }
}

