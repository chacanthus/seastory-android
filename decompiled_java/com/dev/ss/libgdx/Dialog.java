// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.libgdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label$LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton$TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window$WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener$ChangeEvent;
import com.badlogic.gdx.utils.ObjectMap;
import com.dev.ss.Global;
import com.dev.ss.Snd;
import com.dev.ss.screen.LoginScreen;

public class Dialog extends Window {
    Table buttonTable;
    Table contentTable;
    public static float fadeDuration;
    private Skin skin;
    ObjectMap values;

    static  {
        Dialog.fadeDuration = 0f;
        Dialog.count = 0;
    }

    public Dialog(String title, Skin skin, String windowStyleName) {
        super(title, skin.get(windowStyleName, WindowStyle.class));
        this.values = new ObjectMap();
        this.skin = skin;
        this.initialize();
    }

    public Dialog(String title, Skin skin) {
        super(title, skin.get(WindowStyle.class));
        this.values = new ObjectMap();
        this.skin = skin;
        this.initialize();
    }

    public Dialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
        this.values = new ObjectMap();
        this.initialize();
    }

    public Dialog button(String text, Object object) {
        if(this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }

        return this.button(text, object, this.skin.get(TextButtonStyle.class));
    }

    public Dialog button(String text) {
        return this.button(text, null);
    }

    public Dialog button(String text, Object object, TextButtonStyle buttonStyle) {
        TextButton v0 = new TextButton(text, buttonStyle);
        this.buttonTable.add(((Actor)v0));
        this.setObject(((Actor)v0), object);
        return this;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Stage v0 = this.getStage();
        if(v0.getKeyboardFocus() == null) {
            v0.setKeyboardFocus(((Actor)this));
        }

        super.draw(((Batch)batch), parentAlpha);
    }

    public Table getButtonTable() {
        return this.buttonTable;
    }

    public Table getContentTable() {
        return this.contentTable;
    }

    public void hide() {
        if(Dialog.count > 0) {
            --Dialog.count;
        }

        this.remove();
        if(Global.mode != 3 && (Global.mode == 1 || Global.mode == 2) && Dialog.count <= 0) {
            LoginScreen.sendMsgToOuter("show_ui");
        }
    }

    private void initialize() {
        this.setModal(true);
        this.defaults().space(6f);
        Table v0 = new Table(this.skin);
        this.contentTable = v0;
        this.add(((Actor)v0)).expand();
        this.row();
        v0 = new Table(this.skin);
        this.buttonTable = v0;
        this.add(((Actor)v0));
        this.contentTable.pad(12f);
        this.buttonTable.defaults().space(12f);
        this.buttonTable.padBottom(16f);
        this.buttonTable.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Group v4_1;
                while(actor.getParent() != Dialog.this.buttonTable) {
                    v4_1 = ((Actor)v4_1).getParent();
                }

                Dialog.this.result(Dialog.this.values.get(v4_1));
                Dialog.this.hide();
            }
        });
        ++Dialog.count;
        Snd.sndAlram.play();
        if(Global.mode != 3 && (Global.mode == 1 || Global.mode == 2) && Dialog.count > 0) {
            LoginScreen.sendMsgToOuter("hide_ui");
        }
    }

    public Dialog key(int keycode, Object object) {
        this.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode2) {
                if(this.val$keycode == keycode2) {
                    Dialog.this.result(this.val$object);
                    Dialog.this.hide();
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

    public Dialog show(Stage stage) {
        stage.setKeyboardFocus(((Actor)this));
        stage.setScrollFocus(((Actor)this));
        this.pack();
        this.setPosition(((float)Math.round((stage.getWidth() - this.getWidth()) / 2f)), ((float)Math.round((stage.getHeight() - this.getHeight()) / 2f)));
        stage.addActor(((Actor)this));
        if(Dialog.fadeDuration > 0f) {
            this.getColor().a = 0f;
            this.addAction(Actions.fadeIn(Dialog.fadeDuration, Interpolation.fade));
        }

        return this;
    }

    public Dialog text(String text) {
        if(this.skin == null) {
            throw new IllegalStateException("This method may only be used if the dialog was constructed with a Skin.");
        }

        return this.text(text, this.skin.get(LabelStyle.class));
    }

    public Dialog text(String text, LabelStyle labelStyle) {
        this.contentTable.add(new Label(((CharSequence)text), labelStyle));
        return this;
    }
}

