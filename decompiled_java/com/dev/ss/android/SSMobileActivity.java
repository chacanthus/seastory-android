// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.android;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer$OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View$OnClickListener;
import android.view.ViewGroup$LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout$LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer$Task;
import com.dev.ss.BridgeToLibGdx;
import com.dev.ss.Global;
import com.dev.ss.SSMobile;
import com.dev.ss.libgdx.Dialog;
import java.util.ArrayList;
import java.util.List;

public class SSMobileActivity extends AndroidApplication implements BridgeToLibGdx {
    class CheckGameMsg implements Runnable {
        CheckGameMsg(SSMobileActivity arg1) {
            SSMobileActivity.this = arg1;
            super();
        }

        public void run() {  // has try-catch handlers
            Message v3;
            String[] v0;
            Object v4;
            while(true) {
                if(!Thread.currentThread().isInterrupted()) {
                    goto label_22;
                }

                return;
                try {
                label_22:
                    while(SSMobileActivity.lsGameMsg.size() > 0) {
                        v4 = SSMobileActivity.lsGameMsg.get(0);
                        SSMobileActivity.lsGameMsg.remove(0);
                        v0 = ((String)v4).split("\t");
                        String v1 = v0[0];
                        if(!"hide_loading".equals(v1)) {
                            goto label_30;
                        }

                        v3 = new Message();
                        v3.what = 150;
                        SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                        continue;
                    label_30:
                        if(!"play_sound".equals(v1)) {
                            goto label_45;
                        }

                        SSMobileActivity.sSound = v0[1];
                        v3 = new Message();
                        v3.obj = v4;
                        v3.what = 155;
                        SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                        continue;
                    label_45:
                        if(!"hide_ui".equals(v1)) {
                            goto label_54;
                        }

                        v3 = new Message();
                        v3.what = 170;
                        SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                        continue;
                    label_54:
                        if(!"show_ui".equals(v1)) {
                            goto label_63;
                        }

                        v3 = new Message();
                        v3.what = 160;
                        SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                        continue;
                    label_63:
                        if(!"saved_id".equals(v1)) {
                            goto label_73;
                        }

                        v3 = new Message();
                        v3.obj = v4;
                        v3.what = 180;
                        SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                        continue;
                    label_73:
                        if(!"update_url".equals(v1)) {
                            goto label_87;
                        }

                        goto label_76;
                    }
                }
                catch(Exception v6) {
                    goto label_44;
                }

                try {
                label_76:
                    Intent v2 = new Intent("android.intent.action.VIEW");
                    v2.setData(Uri.parse(v0[1]));
                    SSMobileActivity.this.startActivity(v2);
                    goto label_22;
                }
                catch(Exception v6) {
                    goto label_22;
                }

                try {
                label_87:
                    if(!"regist_success".equals(v1)) {
                        goto label_22;
                    }

                    v3 = new Message();
                    v3.obj = v4;
                    v3.what = 200;
                    SSMobileActivity.ProcessGameMsg.sendMessage(v3);
                    goto label_22;
                }
                catch(Exception v6) {
                    goto label_44;
                }

                long v6_1 = 20;
                try {
                    Thread.sleep(v6_1);
                    continue;
                }
                catch(Exception v6) {
                label_44:
                    continue;
                }
                catch(InterruptedException v6_2) {
                    continue;
                }
            }
        }
    }

    static Handler ProcessGameMsg;
    public static Context context;
    private static EditText etAgent;
    private static EditText etId;
    private static EditText etPwd;
    private static EditText etRegistFormId;
    private static EditText etRegistFormPwd;
    private static EditText etRegistFormTel;
    private static float fFontSize;
    private static Method gameScreenMethod;
    private static Object gameScreenObj;
    private static ImageButton ibLogin;
    private static ImageButton ibLoginForm;
    private static ImageButton ibRegist;
    private static ImageButton ibRegistForm;
    private static ImageView ivLoading;
    private static ImageView ivLoadingBack;
    private static Method loginScreenMethod;
    private static Object loginScreenObj;
    private static List lsGameMsg;
    public static MediaPlayer mediaPlayer;
    public static String sSound;
    private static int screen_pixel_height;
    private static int screen_pixel_width;
    private static int standard_screen_pixel_height;
    private static int standard_screen_pixel_width;

    static  {
        SSMobileActivity.standard_screen_pixel_width = 768;
        SSMobileActivity.standard_screen_pixel_height = 1280;
        SSMobileActivity.mediaPlayer = null;
        SSMobileActivity.sSound = "";
        SSMobileActivity.lsGameMsg = new ArrayList();
        SSMobileActivity.gameScreenObj = null;
        SSMobileActivity.gameScreenMethod = null;
        SSMobileActivity.loginScreenObj = null;
        SSMobileActivity.loginScreenMethod = null;
        SSMobileActivity.ProcessGameMsg = new Handler() {
            public void handleMessage(Message msg) {  // has try-catch handlers
                String v3;
                String v1;
                String[] v0;
                switch(msg.what) {
                    case 150: {
                        goto label_88;
                    }
                    case 155: {
                        goto label_3;
                    }
                    case 160: {
                        goto label_103;
                    }
                    case 170: {
                        goto label_99;
                    }
                    case 180: {
                        goto label_107;
                    }
                    case 200: {
                        goto label_123;
                    }
                }

                return;
                try {
                label_99:
                    SSMobileActivity.hideUI();
                    return;
                }
                catch(Exception v4) {
                    return;
                }

                try {
                label_3:
                    if(SSMobileActivity.mediaPlayer != null) {
                        SSMobileActivity.mediaPlayer.release();
                        SSMobileActivity.mediaPlayer = null;
                    }

                    if(SSMobileActivity.sSound.equals("bar1")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968576);
                    }
                    else if(SSMobileActivity.sSound.equals("cele_0001")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968577);
                    }
                    else if(SSMobileActivity.sSound.equals("jellyfish")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968578);
                    }
                    else if(SSMobileActivity.sSound.equals("jocker1")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968579);
                    }
                    else if(SSMobileActivity.sSound.equals("seven1")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968580);
                    }
                    else if(SSMobileActivity.sSound.equals("shark")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968581);
                    }
                    else if(SSMobileActivity.sSound.equals("turtle")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968582);
                    }
                    else if(SSMobileActivity.sSound.equals("whale")) {
                        SSMobileActivity.mediaPlayer = MediaPlayer.create(SSMobileActivity.context, 2130968583);
                    }
                    else {
                        return;
                    }

                    SSMobileActivity.mediaPlayer.setOnPreparedListener(new MediaPlayer$OnPreparedListener() {
                        public void onPrepared(MediaPlayer mp) {
                            SSMobileActivity.mediaPlayer.start();
                        }
                    });
                    SSMobileActivity.mediaPlayer.prepareAsync();
                    return;
                    return;
                }
                catch(Exception v4) {
                    return;
                }

                try {
                label_103:
                    SSMobileActivity.showUI();
                    return;
                }
                catch(Exception v4) {
                    return;
                }

                try {
                label_88:
                    SSMobileActivity.ivLoading.setVisibility(4);
                    SSMobileActivity.ivLoading.clearAnimation();
                    SSMobileActivity.ivLoadingBack.setVisibility(4);
                    return;
                }
                catch(Exception v4) {
                    return;
                }

                try {
                label_107:
                    v0 = msg.obj.split("\t");
                    goto label_113;
                }
                catch(Exception v4) {
                    goto label_122;
                }

                try {
                label_113:
                    v1 = v0[1];
                }
                catch(Exception v4) {
                }

                int v4_1 = 2;
                try {
                    v3 = v0[v4_1];
                    goto label_116;
                }
                catch(Exception v4) {
                    goto label_116;
                }

                try {
                label_116:
                    SSMobileActivity.etId.setText(((CharSequence)v1));
                    SSMobileActivity.etPwd.setText(((CharSequence)v3));
                    return;
                }
                catch(Exception v4) {
                label_122:
                    return;
                }

                try {
                label_123:
                    v0 = msg.obj.split("\t");
                    goto label_129;
                }
                catch(Exception v4) {
                    goto label_147;
                }

                try {
                label_129:
                    v1 = v0[1];
                }
                catch(Exception v4) {
                }

                v4_1 = 2;
                try {
                    v3 = v0[v4_1];
                    goto label_132;
                }
                catch(Exception v4) {
                    goto label_132;
                }

                try {
                label_132:
                    SSMobileActivity.etId.setText(((CharSequence)v1));
                    SSMobileActivity.etPwd.setText(((CharSequence)v3));
                    SSMobileActivity.etRegistFormId.setText("");
                    SSMobileActivity.etRegistFormPwd.setText("");
                    SSMobileActivity.etRegistFormTel.setText("");
                }
                catch(Exception v4) {
                label_147:
                }
            }
        };
    }

    public SSMobileActivity() {
        super();
    }

    private static float XCenter(float pixel_x, float _width) {
        return (pixel_x - (((float)(SSMobileActivity.standard_screen_pixel_width / 2)))) / (((float)SSMobileActivity.standard_screen_pixel_width)) - _width / 2f;
    }

    private static float YCenter(float pixel_y, float _height) {
        return (pixel_y - (((float)(SSMobileActivity.standard_screen_pixel_height / 2)))) / (((float)SSMobileActivity.standard_screen_pixel_height)) * (((float)(-SSMobileActivity.screen_pixel_height))) / (((float)SSMobileActivity.screen_pixel_width)) - _height / 2f;
    }

    static ImageView access$0() {
        return SSMobileActivity.ivLoading;
    }

    static ImageView access$1() {
        return SSMobileActivity.ivLoadingBack;
    }

    static void access$10(SSMobileActivity arg0) {
        arg0.hideSofttKeyboard();
    }

    static EditText access$11() {
        return SSMobileActivity.etAgent;
    }

    static void access$2() {
        SSMobileActivity.hideUI();
    }

    static void access$3() {
        SSMobileActivity.showUI();
    }

    static EditText access$4() {
        return SSMobileActivity.etId;
    }

    static EditText access$5() {
        return SSMobileActivity.etPwd;
    }

    static EditText access$6() {
        return SSMobileActivity.etRegistFormId;
    }

    static EditText access$7() {
        return SSMobileActivity.etRegistFormPwd;
    }

    static EditText access$8() {
        return SSMobileActivity.etRegistFormTel;
    }

    static List access$9() {
        return SSMobileActivity.lsGameMsg;
    }

    private static int getX(int pixel_width) {
        return SSMobileActivity.screen_pixel_width * pixel_width / SSMobileActivity.standard_screen_pixel_width;
    }

    private static int getY(int pixel_height) {
        return SSMobileActivity.screen_pixel_height * pixel_height / SSMobileActivity.standard_screen_pixel_height;
    }

    private void hideSofttKeyboard() {  // has try-catch handlers
        try {
            Gdx.input.setOnscreenKeyboardVisible(false);
        }
        catch(Exception v0) {
        }
    }

    private static void hideUI() {
        SSMobileActivity.etId.setVisibility(4);
        SSMobileActivity.etPwd.setVisibility(4);
        SSMobileActivity.ibLogin.setVisibility(4);
        SSMobileActivity.ibRegistForm.setVisibility(4);
        SSMobileActivity.etRegistFormId.setVisibility(4);
        SSMobileActivity.etRegistFormPwd.setVisibility(4);
        SSMobileActivity.etRegistFormTel.setVisibility(4);
        SSMobileActivity.etAgent.setVisibility(4);
        SSMobileActivity.ibRegist.setVisibility(4);
        SSMobileActivity.ibLoginForm.setVisibility(4);
    }

    private void iniLoadingUI() {
        SSMobileActivity.ivLoadingBack = this.findViewById(2131165185);
        FrameLayout$LayoutParams v8 = new FrameLayout$LayoutParams(SSMobileActivity.getX(332), SSMobileActivity.getY(332));
        v8.leftMargin = SSMobileActivity.getX(218);
        v8.topMargin = SSMobileActivity.getY(474);
        SSMobileActivity.ivLoadingBack.setLayoutParams(((ViewGroup$LayoutParams)v8));
        SSMobileActivity.ivLoadingBack.setVisibility(0);
        SSMobileActivity.ivLoadingBack.bringToFront();
        SSMobileActivity.ivLoading = this.findViewById(2131165186);
        FrameLayout$LayoutParams v7 = new FrameLayout$LayoutParams(SSMobileActivity.getX(202), SSMobileActivity.getY(202));
        v7.leftMargin = SSMobileActivity.getX(283);
        v7.topMargin = SSMobileActivity.getY(559);
        SSMobileActivity.ivLoading.setLayoutParams(((ViewGroup$LayoutParams)v7));
        RotateAnimation v0 = new RotateAnimation(0f, 359f, 1, 0.5f, 1, 0.5f);
        ((Animation)v0).setDuration(6000);
        ((Animation)v0).setRepeatCount(-1);
        SSMobileActivity.ivLoading.startAnimation(((Animation)v0));
        SSMobileActivity.ivLoading.setVisibility(0);
        SSMobileActivity.ivLoading.bringToFront();
    }

    private void iniLoginFormUI() {
        SSMobileActivity.etId = this.findViewById(2131165188);
        SSMobileActivity.etId.setText("");
        SSMobileActivity.etId.setTextSize(SSMobileActivity.fFontSize);
        FrameLayout$LayoutParams v0 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v0.leftMargin = SSMobileActivity.getX(327);
        v0.topMargin = SSMobileActivity.getY(582);
        SSMobileActivity.etId.setLayoutParams(((ViewGroup$LayoutParams)v0));
        SSMobileActivity.etId.setVisibility(4);
        SSMobileActivity.etId.bringToFront();
        SSMobileActivity.etId.clearFocus();
        SSMobileActivity.etPwd = this.findViewById(2131165190);
        SSMobileActivity.etPwd.setText("");
        SSMobileActivity.etPwd.setTextSize(SSMobileActivity.fFontSize);
        FrameLayout$LayoutParams v1 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v1.leftMargin = SSMobileActivity.getX(327);
        v1.topMargin = SSMobileActivity.getY(690);
        SSMobileActivity.etPwd.setLayoutParams(((ViewGroup$LayoutParams)v1));
        SSMobileActivity.etPwd.setVisibility(4);
        SSMobileActivity.etPwd.bringToFront();
        SSMobileActivity.etPwd.clearFocus();
        SSMobileActivity.ibLogin = this.findViewById(2131165191);
        FrameLayout$LayoutParams v2 = new FrameLayout$LayoutParams(SSMobileActivity.getX(253), SSMobileActivity.getY(90));
        v2.leftMargin = SSMobileActivity.getX(396);
        v2.topMargin = SSMobileActivity.getY(812);
        SSMobileActivity.ibLogin.setLayoutParams(((ViewGroup$LayoutParams)v2));
        SSMobileActivity.ibLogin.setVisibility(4);
        SSMobileActivity.ibLogin.bringToFront();
        SSMobileActivity.ibLogin.setOnClickListener(new View$OnClickListener() {
            static SSMobileActivity access$0(com.dev.ss.android.SSMobileActivity$2 arg1) {
                return arg1.this$0;
            }

            public void onClick(View v) {
                SSMobileActivity.hideUI();
                SSMobileActivity.this.hideSofttKeyboard();
                Timer.schedule(new Task() {
                    public void run() {
                        this.this$1.this$0.sendMsgToLoginScreen("try_login\t" + SSMobileActivity.etId.getText() + "\t" + SSMobileActivity.etPwd.getText() + "\t" + "x2.5");
                    }
                }, 0.1f);
            }
        });
        SSMobileActivity.ibRegistForm = this.findViewById(2131165192);
        FrameLayout$LayoutParams v3 = new FrameLayout$LayoutParams(SSMobileActivity.getX(253), SSMobileActivity.getY(90));
        v3.leftMargin = SSMobileActivity.getX(123);
        v3.topMargin = SSMobileActivity.getY(812);
        SSMobileActivity.ibRegistForm.setLayoutParams(((ViewGroup$LayoutParams)v3));
        SSMobileActivity.ibRegistForm.setVisibility(4);
        SSMobileActivity.ibRegistForm.bringToFront();
        SSMobileActivity.ibRegistForm.setOnClickListener(new View$OnClickListener() {
            public void onClick(View v) {
                Global.mode = 2;
                SSMobileActivity.showUI();
                SSMobileActivity.this.hideSofttKeyboard();
            }
        });
    }

    private void iniRegistFormUI() {
        SSMobileActivity.etRegistFormId = this.findViewById(2131165194);
        SSMobileActivity.etRegistFormId.setText("");
        SSMobileActivity.etRegistFormId.setTextSize(SSMobileActivity.fFontSize);
        FrameLayout$LayoutParams v1 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v1.leftMargin = SSMobileActivity.getX(321);
        v1.topMargin = SSMobileActivity.getY(544);
        SSMobileActivity.etRegistFormId.setLayoutParams(((ViewGroup$LayoutParams)v1));
        SSMobileActivity.etRegistFormId.setVisibility(4);
        SSMobileActivity.etRegistFormId.bringToFront();
        SSMobileActivity.etRegistFormId.clearFocus();
        SSMobileActivity.etRegistFormPwd = this.findViewById(2131165196);
        SSMobileActivity.etRegistFormPwd.setText("");
        SSMobileActivity.etRegistFormPwd.setTextSize(SSMobileActivity.fFontSize);
        FrameLayout$LayoutParams v2 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v2.leftMargin = SSMobileActivity.getX(321);
        v2.topMargin = SSMobileActivity.getY(642);
        SSMobileActivity.etRegistFormPwd.setLayoutParams(((ViewGroup$LayoutParams)v2));
        SSMobileActivity.etRegistFormPwd.setVisibility(4);
        SSMobileActivity.etRegistFormPwd.bringToFront();
        SSMobileActivity.etRegistFormPwd.clearFocus();
        SSMobileActivity.etRegistFormTel = this.findViewById(2131165198);
        SSMobileActivity.etRegistFormTel.setText("");
        SSMobileActivity.etRegistFormTel.setTextSize(SSMobileActivity.fFontSize);
        SSMobileActivity.etRegistFormTel.setInputType(12290);
        FrameLayout$LayoutParams v3 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v3.leftMargin = SSMobileActivity.getX(321);
        v3.topMargin = SSMobileActivity.getY(741);
        SSMobileActivity.etRegistFormTel.setLayoutParams(((ViewGroup$LayoutParams)v3));
        SSMobileActivity.etRegistFormTel.setVisibility(4);
        SSMobileActivity.etRegistFormTel.bringToFront();
        SSMobileActivity.etRegistFormTel.clearFocus();
        SSMobileActivity.etAgent = this.findViewById(2131165200);
        SSMobileActivity.etAgent.setText("");
        SSMobileActivity.etAgent.setTextSize(SSMobileActivity.fFontSize);
        FrameLayout$LayoutParams v0 = new FrameLayout$LayoutParams(SSMobileActivity.getX(310), SSMobileActivity.getY(70));
        v0.leftMargin = SSMobileActivity.getX(321);
        v0.topMargin = SSMobileActivity.getY(842);
        SSMobileActivity.etAgent.setLayoutParams(((ViewGroup$LayoutParams)v0));
        SSMobileActivity.etAgent.setVisibility(4);
        SSMobileActivity.etAgent.bringToFront();
        SSMobileActivity.etAgent.clearFocus();
        SSMobileActivity.ibRegist = this.findViewById(2131165202);
        FrameLayout$LayoutParams v5 = new FrameLayout$LayoutParams(SSMobileActivity.getX(253), SSMobileActivity.getY(90));
        v5.leftMargin = SSMobileActivity.getX(119);
        v5.topMargin = SSMobileActivity.getY(958);
        SSMobileActivity.ibRegist.setLayoutParams(((ViewGroup$LayoutParams)v5));
        SSMobileActivity.ibRegist.setVisibility(4);
        SSMobileActivity.ibRegist.bringToFront();
        SSMobileActivity.ibRegist.setOnClickListener(new View$OnClickListener() {
            static SSMobileActivity access$0(com.dev.ss.android.SSMobileActivity$4 arg1) {
                return arg1.this$0;
            }

            public void onClick(View v) {
                SSMobileActivity.hideUI();
                SSMobileActivity.this.hideSofttKeyboard();
                Timer.schedule(new Task() {
                    public void run() {
                        this.this$1.this$0.sendMsgToLoginScreen("try_regist\t" + SSMobileActivity.etRegistFormId.getText() + "\t" + SSMobileActivity.etRegistFormPwd.getText() + "\t" + SSMobileActivity.etRegistFormTel.getText() + "\t" + SSMobileActivity.etAgent.getText() + "\t" + "x2.5");
                    }
                }, 0.1f);
            }
        });
        SSMobileActivity.ibLoginForm = this.findViewById(2131165201);
        FrameLayout$LayoutParams v4 = new FrameLayout$LayoutParams(SSMobileActivity.getX(253), SSMobileActivity.getY(90));
        v4.leftMargin = SSMobileActivity.getX(395);
        v4.topMargin = SSMobileActivity.getY(958);
        SSMobileActivity.ibLoginForm.setLayoutParams(((ViewGroup$LayoutParams)v4));
        SSMobileActivity.ibLoginForm.setVisibility(4);
        SSMobileActivity.ibLoginForm.bringToFront();
        SSMobileActivity.ibLoginForm.setOnClickListener(new View$OnClickListener() {
            public void onClick(View v) {
                Global.mode = 1;
                SSMobileActivity.showUI();
                SSMobileActivity.this.hideSofttKeyboard();
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        int v5 = 800;
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(1024, 1024);
        this.getWindow().addFlags(128);
        this.getWindow().setSoftInputMode(3);
        this.requestWindowFeature(1);
        this.setContentView(2130903040);
        SSMobileActivity.context = ((Context)this);
        DisplayMetrics v0 = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(v0);
        SSMobileActivity.screen_pixel_width = v0.widthPixels;
        SSMobileActivity.screen_pixel_height = v0.heightPixels;
        if(SSMobileActivity.screen_pixel_width <= v5) {
            SSMobileActivity.fFontSize = 9.5f;
        }
        else {
            if(SSMobileActivity.screen_pixel_width > v5 && SSMobileActivity.screen_pixel_width <= 1080) {
                SSMobileActivity.fFontSize = 16f;
                goto label_28;
            }

            SSMobileActivity.fFontSize = 18f;
        }

    label_28:
        new Thread(new CheckGameMsg(this)).start();
        this.findViewById(2131165184).addView(this.initializeForView(new SSMobile(((BridgeToLibGdx)this))));
        this.iniLoadingUI();
        this.iniLoginFormUI();
        this.iniRegistFormUI();
    }

    public void onDestroy() {  // has try-catch handlers
        try {
            if(SSMobileActivity.mediaPlayer == null) {
                goto label_6;
            }

            SSMobileActivity.mediaPlayer.release();
            SSMobileActivity.mediaPlayer = null;
        }
        catch(Exception v0) {
        }

    label_6:
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void sendMsgToGameScreen(String sMsg) {  // has try-catch handlers
        try {
            if(SSMobileActivity.gameScreenMethod == null) {
                return;
            }

            if(SSMobileActivity.gameScreenObj == null) {
                return;
            }

            SSMobileActivity.gameScreenMethod.invoke(SSMobileActivity.gameScreenObj, sMsg);
        }
        catch(Exception v1) {
        }
    }

    public void sendMsgToLoginScreen(String sMsg) {  // has try-catch handlers
        try {
            if(SSMobileActivity.loginScreenMethod == null) {
                return;
            }

            if(SSMobileActivity.loginScreenObj == null) {
                return;
            }

            SSMobileActivity.loginScreenMethod.invoke(SSMobileActivity.loginScreenObj, sMsg);
        }
        catch(Exception v1) {
        }
    }

    public void sendMsgToOuter(String _cmd) {  // has try-catch handlers
        try {
            SSMobileActivity.lsGameMsg.add(_cmd);
        }
        catch(Exception v0) {
        }
    }

    public void setGameScreenCallback(Object _obj, Method _method) {  // has try-catch handlers
        try {
            SSMobileActivity.gameScreenObj = _obj;
            SSMobileActivity.gameScreenMethod = _method;
        }
        catch(Exception v0) {
        }
    }

    public void setLoginScreenCallback(Object _obj, Method _method) {  // has try-catch handlers
        try {
            SSMobileActivity.loginScreenObj = _obj;
            SSMobileActivity.loginScreenMethod = _method;
        }
        catch(Exception v0) {
        }
    }

    private static void showUI() {
        int v3 = 4;
        if(Global.mode != 2 || Dialog.count > 0) {
            if(Global.mode == 1 && Dialog.count <= 0) {
                SSMobileActivity.etId.setVisibility(0);
                SSMobileActivity.etPwd.setVisibility(0);
                SSMobileActivity.ibLogin.setVisibility(0);
                SSMobileActivity.ibRegistForm.setVisibility(0);
                SSMobileActivity.etId.bringToFront();
                SSMobileActivity.etPwd.bringToFront();
                SSMobileActivity.ibLogin.bringToFront();
                SSMobileActivity.ibRegistForm.bringToFront();
                SSMobileActivity.etRegistFormId.setVisibility(v3);
                SSMobileActivity.etRegistFormPwd.setVisibility(v3);
                SSMobileActivity.etRegistFormTel.setVisibility(v3);
                SSMobileActivity.etAgent.setVisibility(v3);
                SSMobileActivity.ibRegist.setVisibility(v3);
                SSMobileActivity.ibLoginForm.setVisibility(v3);
                return;
            }

            SSMobileActivity.hideUI();
        }
        else {
            SSMobileActivity.etId.setVisibility(v3);
            SSMobileActivity.etPwd.setVisibility(v3);
            SSMobileActivity.ibLogin.setVisibility(v3);
            SSMobileActivity.ibRegistForm.setVisibility(v3);
            SSMobileActivity.etRegistFormId.setVisibility(0);
            SSMobileActivity.etRegistFormPwd.setVisibility(0);
            SSMobileActivity.etRegistFormTel.setVisibility(0);
            SSMobileActivity.etAgent.setVisibility(0);
            SSMobileActivity.ibRegist.setVisibility(0);
            SSMobileActivity.ibLoginForm.setVisibility(0);
            SSMobileActivity.etRegistFormId.bringToFront();
            SSMobileActivity.etRegistFormPwd.bringToFront();
            SSMobileActivity.etRegistFormTel.bringToFront();
            SSMobileActivity.etAgent.bringToFront();
            SSMobileActivity.ibRegist.bringToFront();
            SSMobileActivity.ibLoginForm.bringToFront();
        }
    }
}

