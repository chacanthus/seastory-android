// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener$FocusEvent;
import com.badlogic.gdx.utils.ObjectMap;

public class Dialog extends Window {
    Table buttonTable;
    boolean cancelHide;
    Table contentTable;
    FocusListener focusListener;
    protected InputListener ignoreTouchDown;
    Actor previousKeyboardFocus;
    Actor previousScrollFocus;
    private Skin skin;
    ObjectMap values;

    public Dialog(String title, Skin skin) {
        super(title, skin.get(WindowStyle.class));
        this.values = new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.cancel();
                return 0;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }

    public Dialog(String title, Skin skin, String windowStyleName) {
        super(title, skin.get(windowStyleName, WindowStyle.class));
        this.values = new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.cancel();
                return 0;
            }
        };
        this.setSkin(skin);
        this.skin = skin;
        this.initialize();
    }

    public Dialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        this.values = new ObjectMap();
        this.ignoreTouchDown = new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.cancel();
                return 0;
            }
        };
        this.initialize();
    }

    public Dialog button(Button button) {
        return this.button(button, null);
    }

    public Dialog button(Button button, Object object) {
        this.buttonTable.add(((Actor)button));
        this.setObject(((Actor)button), object);
        return this;
    }

    public Dialog button(String text) {
        return this.button(text, null);
    }

    public Dialog button(String text, Object object) {
        if(this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }

        return this.button(text, object, this.skin.get(TextButtonStyle.class));
    }

    public Dialog button(String text, Object object, TextButtonStyle buttonStyle) {
        return this.button(new TextButton(text, buttonStyle), object);
    }

    public void cancel() {
        this.cancelHide = true;
    }

    public Table getButtonTable() {
        return this.buttonTable;
    }

    public Table getContentTable() {
        return this.contentTable;
    }

    public void hide() {
        this.hide(Actions.sequence(Actions.fadeOut(0.4f, Interpolation.fade), Actions.removeListener(this.ignoreTouchDown, true), Actions.removeActor()));
    }

    public void hide(Action action) {
        Actor v3 = null;
        Stage v1 = this.getStage();
        if(v1 != null) {
            this.removeListener(this.focusListener);
            if(this.previousKeyboardFocus != null && this.previousKeyboardFocus.getStage() == null) {
                this.previousKeyboardFocus = v3;
            }

            Actor v0 = v1.getKeyboardFocus();
            if(v0 == null || (v0.isDescendantOf(((Actor)this)))) {
                v1.setKeyboardFocus(this.previousKeyboardFocus);
            }

            if(this.previousScrollFocus != null && this.previousScrollFocus.getStage() == null) {
                this.previousScrollFocus = v3;
            }

            v0 = v1.getScrollFocus();
            if(v0 != null && !v0.isDescendantOf(((Actor)this))) {
                goto label_29;
            }

            v1.setScrollFocus(this.previousScrollFocus);
        }

    label_29:
        if(action != null) {
            this.addCaptureListener(this.ignoreTouchDown);
            this.addAction(Actions.sequence(action, Actions.removeListener(this.ignoreTouchDown, true), Actions.removeActor()));
        }
        else {
            this.remove();
        }
    }

    private void initialize() {
        this.setModal(true);
        this.defaults().space(6f);
        Table v0 = new Table(this.skin);
        this.contentTable = v0;
        this.add(((Actor)v0)).expand().fill();
        this.row();
        v0 = new Table(this.skin);
        this.buttonTable = v0;
        this.add(((Actor)v0));
        this.contentTable.defaults().space(6f);
        this.buttonTable.defaults().space(6f);
        this.buttonTable.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Group v4_1;
                if(Dialog.this.values.containsKey(actor)) {
                    while(actor.getParent() != Dialog.this.buttonTable) {
                        v4_1 = ((Actor)v4_1).getParent();
                    }

                    Dialog.this.result(Dialog.this.values.get(v4_1));
                    if(!Dialog.this.cancelHide) {
                        Dialog.this.hide();
                    }

                    Dialog.this.cancelHide = false;
                }
            }
        });
        this.focusListener = new FocusListener() {
            private void focusChanged(FocusEvent event) {
                Stage v1 = Dialog.this.getStage();
                if((Dialog.this.isModal) && v1 != null && v1.getRoot().getChildren().size > 0 && v1.getRoot().getChildren().peek() == Dialog.this) {
                    Actor v0 = event.getRelatedActor();
                    if(v0 != null && !v0.isDescendantOf(Dialog.this) && !v0.equals(Dialog.this.previousKeyboardFocus) && !v0.equals(Dialog.this.previousScrollFocus)) {
                        event.cancel();
                    }
                }
            }

            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if(!focused) {
                    this.focusChanged(event);
                }
            }

            public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if(!focused) {
                    this.focusChanged(event);
                }
            }
        };
    }

    public Dialog key(int keycode, Object object) {
        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode2) {
                if(this.val$keycode == keycode2) {
                    Dialog.this.result(this.val$object);
                    if(!Dialog.this.cancelHide) {
                        Dialog.this.hide();
                    }

                    Dialog.this.cancelHide = false;
                }

                return 0;
            }
        });
        return this;
    }

    protected void result(Object object) {
    }

    public void setObject(Actor actor, Object object) {
        this.values.put(actor, object);
    }

    protected void setStage(Stage stage) {
        if(stage == null) {
            this.addListener(this.focusListener);
        }
        else {
            this.removeListener(this.focusListener);
        }

        super.setStage(stage);
    }

    public Dialog show(Stage stage) {
        this.show(stage, Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.4f, Interpolation.fade)));
        this.setPosition(((float)Math.round((stage.getWidth() - this.getWidth()) / 2f)), ((float)Math.round((stage.getHeight() - this.getHeight()) / 2f)));
        return this;
    }

    public Dialog show(Stage stage, Action action) {
        Actor v2 = null;
        this.clearActions();
        this.removeCaptureListener(this.ignoreTouchDown);
        this.previousKeyboardFocus = v2;
        Actor v0 = stage.getKeyboardFocus();
        if(v0 != null && !v0.isDescendantOf(((Actor)this))) {
            this.previousKeyboardFocus = v0;
        }

        this.previousScrollFocus = v2;
        v0 = stage.getScrollFocus();
        if(v0 != null && !v0.isDescendantOf(((Actor)this))) {
            this.previousScrollFocus = v0;
        }

        this.pack();
        stage.addActor(((Actor)this));
        stage.setKeyboardFocus(((Actor)this));
        stage.setScrollFocus(((Actor)this));
        if(action != null) {
            this.addAction(action);
        }

        return this;
    }

    public Dialog text(Label label) {
        this.contentTable.add(((Actor)label));
        return this;
    }

    public Dialog text(String text) {
        if(this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }

        return this.text(text, this.skin.get(LabelStyle.class));
    }

    public Dialog text(String text, LabelStyle labelStyle) {
        return this.text(new Label(((CharSequence)text), labelStyle));
    }
}

