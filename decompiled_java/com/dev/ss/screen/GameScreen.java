// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input$TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane$ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField$TextFieldListener;
import com.dev.ss.Global;
import com.dev.ss.MySocket;
import com.dev.ss.Res;
import com.dev.ss.SSMobile;
import com.dev.ss.Snd;
import com.dev.ss.lib.ScreenShaker;
import com.dev.ss.lib.Util;
import com.dev.ss.libgdx.Dialog;
import com.dev.ss.obj.Card;
import com.dev.ss.obj.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class GameScreen implements Screen {
    private final int ALLOT;
    private final int ASK;
    private final int BOTTOM;
    private final int CENTER;
    private final int CHARGE;
    private final int EXCHANGE;
    private final int FIRE_NONE;
    private final int HELP;
    private final int HIDE;
    private final int IDLE;
    private final int MACHINE;
    private final int TOP;
    private float[] accelerationSpeed;
    private boolean bAddHurryUp1;
    private boolean bAddHurryUp2;
    private boolean bAddSexySnd1;
    private boolean bAddSexySnd2;
    private boolean bAddSexySnd3;
    private boolean bAdditionalGame;
    private boolean bAdditionalNeedNewGame;
    private boolean bAdditionalWinCeremony;
    private boolean bCanInsert10000;
    private boolean bCanRequestScenario;
    private boolean bCanReserveMachine;
    private boolean bCanSelectMachine;
    private boolean bCanSwitchMoney;
    private boolean bCanUpDownChannel;
    private boolean bEnding;
    private boolean bGameScreenReady;
    private boolean[] bReelAccelerating;
    private boolean[] bReelDecelerating;
    private boolean[] bReelDeceleratingStarted;
    private boolean[] bReelMinSpeedStarted;
    private boolean[] bReelPoppingMinSpeedStarted;
    private boolean[] bReelPoppingStarted;
    private boolean[] bReelRunning;
    private static boolean bReserving;
    public static boolean bShowMenu;
    private boolean bShowToast;
    private boolean bStopScenarioForTest;
    private boolean bWaitingAdditionalGame;
    private boolean bWaitingScenario;
    public static boolean bWelcomeCelemony;
    private TextButton btnTest1;
    private TextButton btnTest10;
    private TextButton btnTest11;
    private TextButton btnTest12;
    private TextButton btnTest13;
    private TextButton btnTest14;
    private TextButton btnTest15;
    private TextButton btnTest16;
    private TextButton btnTest17;
    private TextButton btnTest18;
    private TextButton btnTest19;
    private TextButton btnTest2;
    private TextButton btnTest20;
    private TextButton btnTest21;
    private TextButton btnTest22;
    private TextButton btnTest23;
    private TextButton btnTest24;
    private TextButton btnTest25;
    private TextButton btnTest26;
    private TextButton btnTest27;
    private TextButton btnTest28;
    private TextButton btnTest29;
    private TextButton btnTest3;
    private TextButton btnTest30;
    private TextButton btnTest31;
    private TextButton btnTest32;
    private TextButton btnTest4;
    private TextButton btnTest5;
    private TextButton btnTest6;
    private TextButton btnTest7;
    private TextButton btnTest8;
    private TextButton btnTest9;
    private OrthographicCamera camera;
    private Card[][] card;
    private Color[] clAsk;
    private Color[] clHelp;
    private Color[] clMsg;
    public static Color color;
    private float[] decelerationSpeed;
    private float deltaTime;
    private float elapseTime;
    private float fAdditionalBaseBetTimer;
    private float fAdditionalBeep;
    private float fAdditionalNewGameBtnDelay;
    private float fAdditionalTimer;
    private float fAdditionalWinCeremonyMoneyTimer;
    private float fBackLightAlpha;
    private float fBackLightTimer;
    private float fBackgroundMusic;
    private float fDayNightAlpha;
    private float fDropCoinTimer;
    private float fEndAdditionalGameDelay;
    private float fEndScenarioDelay;
    private float fFireworksTime;
    private float fFishInterval;
    private float fNextScenarioDelay;
    private float fPlusWinMoneyShowDelay;
    private float[] fReelDeceleratingStartTime;
    private float[] fReelDeltaMileage;
    private float[] fReelElapseTime;
    private float[] fReelPoppingStartTime;
    private float fScenarioInterval;
    private float fSharkTime;
    private float[] fStartReelDelayTime;
    private float fToastTime;
    public static float fTotalReelElapseTime;
    public static float fWelcomeCelemony;
    private float fWhaleTime;
    private float fWinCelemonyTime;
    private int fiBackLight;
    int fiBaseCoin;
    int fiCTB;
    private int fiDayNightFlag;
    int fiMultiple;
    int fiPlusWinMoney;
    private String fsBackgroundDayNight;
    String fsFire;
    String fsMask;
    String fsPos;
    String fsTargetObj;
    private static SSMobile game;
    private float height;
    private int iAdditionalBaseBet;
    private int iAdditionalBetTimeLimit;
    private int iAdditionalLady;
    private int iAdditionalMultiple;
    private int iAdditionalStep;
    private int iAdditionalTimer;
    private int iAdditionalWinMoney;
    private int iBackgroundDirection;
    private int iBackgroundMusic;
    private int iChatFlag;
    private int iCurAsk;
    private int iCurHelp;
    private int iCurMsg;
    private int iDropperDirection;
    private int iExMenuMode;
    private int iExZeroIdx;
    private int iFireType;
    private int iFireworksFlag;
    private int iFishLeftIdx;
    private int iFishRightIdx;
    public static int iGiftFishIdx;
    private int[] iMachineNo;
    private int[] iMachineState;
    private int[] iMachineToday;
    private int[] iMachineYesterday;
    private int iMaxAsk;
    private int iMaxHelp;
    private int iMaxMsg;
    private int[] iMaxPopCount;
    private int iMenuMode;
    private int[] iNextBandPos;
    private int[] iPopCount;
    private int[] iPostTargetCardNo;
    private int[] iReelTensioningStep;
    private int iSharkFlag;
    public static int iSpinAllIdx;
    private int[] iTargetCardNo;
    private int iWhaleFlag;
    private int iWinTableBottomContainer;
    private int iWinTableTopContainer;
    private Image imgToast;
    private Image imgToastBack;
    public static Item[] item;
    private static Label lbAdditionalMoney;
    private static Label lbAdditionalWin;
    private static Label lbBet;
    private Label lbChargeAccount;
    private Label lbChargeBank;
    private Label lbChargeMan;
    private Label lbChargeOwner;
    private Label lbExchangeBank;
    private Label lbExchangeOwner;
    private Label lbFps;
    private static Label lbGift;
    private static Label lbMachine;
    private Label[] lbMachineNo;
    private static Label lbMoney;
    private Label lbPlusWinMoney;
    public static Label lbReserve;
    private static Label lbSpin;
    private Label lbToast;
    private ArrayList lsScenario;
    private Method mCallbackFromOuter;
    private float[] reelMaxSpeed;
    private float[] reelMinPoppingSpeed;
    private float[] reelMinSpeed;
    private int reelMode;
    private String[] sZero1;
    private String[] sZero10;
    private String[] sZero11;
    private String[] sZero12;
    private String[] sZero13;
    private String[] sZero14;
    private String[] sZero15;
    private String[] sZero2;
    private String[] sZero3;
    private String[] sZero4;
    private String[] sZero5;
    private String[] sZero6;
    private String[] sZero7;
    private String[] sZero8;
    private String[] sZero9;
    private String[] saAsk;
    private String[] saHelp;
    private String[] saMsg;
    private ScrollPane scpAsk;
    private ScrollPane scpHelp;
    private ScrollPane scpMsg;
    private ScreenShaker screenShaker;
    private Skin skin;
    private static Skin skin_window;
    private ScrollPaneStyle spsAsk;
    private ScrollPaneStyle spsHelp;
    private ScrollPaneStyle spsMsg;
    private static Stage stage;
    private Table tblAsk;
    private Table tblHelp;
    private Table tblMsg;
    private Table tblTest;
    private TextField tfChargeMoney;
    private TextField tfExchangeAccount;
    private TextField tfExchangeMoney;
    private TextField tfExchangeTel;
    private static Thread thdGameWrite;
    private Timer timer;
    private float width;

    static  {
        GameScreen.lsGameWrite = new ArrayList();
        GameScreen.lsGameRead = new ArrayList();
        GameScreen.iReserveConfirmTick = 0;
        GameScreen.fTotalReelElapseTime = 0f;
        GameScreen.bWelcomeCelemony = true;
        GameScreen.fWelcomeCelemony = 1000000000f;
        GameScreen.frontCardContainerIdx = new int[4];
        GameScreen.item = new Item[39];
        GameScreen.iGiftFishIdx = 0;
        GameScreen.iSpinAllIdx = 0;
        GameScreen.bShowMenu = false;
        GameScreen.bReserving = false;
    }

    public GameScreen(SSMobile _game) {
        // Decompilation failed
    }

    static boolean[] access$0(GameScreen arg1) {
        return arg1.bReelRunning;
    }

    static void access$1(GameScreen arg0, boolean arg1) {
        arg0.bEnding = arg1;
    }

    static void access$10(GameScreen arg0) {
        arg0.startTurtle();
    }

    static void access$11(GameScreen arg0) {
        arg0.startWhale();
    }

    static void access$12(GameScreen arg0) {
        arg0.startJellyfish();
    }

    static void access$13(GameScreen arg0) {
        arg0.startShark();
    }

    static void access$14(GameScreen arg0) {
        arg0.startMermaid();
    }

    static void access$15(GameScreen arg0, boolean arg1) {
        arg0.startBarEvent(arg1);
    }

    static void access$16(GameScreen arg0, boolean arg1) {
        arg0.startSevenEvent(arg1);
    }

    static void access$17(GameScreen arg0, boolean arg1) {
        arg0.startJokerEvent(arg1);
    }

    static void access$18(GameScreen arg0, boolean arg1, String arg2) {
        arg0.startMiddleEvent(arg1, arg2);
    }

    static String access$19(GameScreen arg1) {
        return arg1.fsBackgroundDayNight;
    }

    static void access$2(GameScreen arg0, float arg1) {
        arg0.fNextScenarioDelay = arg1;
    }

    static void access$20(GameScreen arg0, String arg1) {
        arg0.setBackGround(arg1);
    }

    static void access$21(GameScreen arg0) {
        arg0.addTestScenario();
    }

    static void access$22(GameScreen arg0) {
        arg0.showBackLight();
    }

    static TextField access$23(GameScreen arg1) {
        return arg1.tfChargeMoney;
    }

    static TextField access$24(GameScreen arg1) {
        return arg1.tfExchangeAccount;
    }

    static TextField access$25(GameScreen arg1) {
        return arg1.tfExchangeMoney;
    }

    static TextField access$26(GameScreen arg1) {
        return arg1.tfExchangeTel;
    }

    static void access$27(GameScreen arg0, boolean arg1) {
        arg0.showMenu(arg1);
    }

    static void access$28(GameScreen arg0, boolean arg1) {
        arg0.bCanSelectMachine = arg1;
    }

    static Label access$29(GameScreen arg1) {
        return arg1.lbChargeMan;
    }

    static boolean access$3(GameScreen arg1) {
        return arg1.reelIdle();
    }

    static void access$30(GameScreen arg0, String arg1, float arg2) {
        arg0.showToast(arg1, arg2);
    }

    static Label access$31(GameScreen arg1) {
        return arg1.lbExchangeBank;
    }

    static Label access$32(GameScreen arg1) {
        return arg1.lbExchangeOwner;
    }

    static void access$33(GameScreen arg0) {
        arg0.showReserve();
    }

    static void access$34(GameScreen arg0, boolean arg1) {
        arg0.bCanSwitchMoney = arg1;
    }

    static void access$35(GameScreen arg0, boolean arg1) {
        arg0.bCanReserveMachine = arg1;
    }

    static void access$36(boolean arg0) {
        GameScreen.bReserving = arg0;
    }

    static void access$37(GameScreen arg0, boolean arg1) {
        arg0.bCanInsert10000 = arg1;
    }

    static void access$38(GameScreen arg0, String arg1) {
        arg0.sendAsk(arg1);
    }

    static boolean access$4(GameScreen arg1, String arg2, int arg3, String arg4, String arg5, String arg6, int arg7) {
        return arg1.reelAllot(arg2, arg3, arg4, arg5, arg6, arg7);
    }

    static void access$5(GameScreen arg0) {
        arg0.startFish();
    }

    static void access$6(String arg0) {
        GameScreen.playAndroidSound(arg0);
    }

    static boolean access$7(GameScreen arg1) {
        return arg1.bStopScenarioForTest;
    }

    static void access$8(GameScreen arg0, boolean arg1) {
        arg0.bStopScenarioForTest = arg1;
    }

    static ScreenShaker access$9(GameScreen arg1) {
        return arg1.screenShaker;
    }

    private void addAsk(String _sAsk, Color _color) {  // has try-catch handlers
        try {
            if(this.iCurAsk >= this.iMaxAsk) {
                --this.iCurAsk;
                int v1;
                for(v1 = 0; v1 < this.iMaxAsk - 1; ++v1) {
                    this.saAsk[v1] = this.saAsk[v1 + 1];
                    this.clAsk[v1] = this.clAsk[v1 + 1];
                }
            }

            this.saAsk[this.iCurAsk] = _sAsk;
            this.clAsk[this.iCurAsk] = _color;
            ++this.iCurAsk;
            this.tblAsk.clear();
            int v2 = this.iCurAsk + 1;
            if(v2 > this.iMaxAsk) {
                v2 = this.iMaxAsk;
            }

            for(v1 = 0; v1 < v2; ++v1) {
                if(this.width >= 1080f) {
                    this.tblAsk.row().height(50f);
                }
                else {
                    this.tblAsk.row().height(30f);
                }

                this.tblAsk.add(new Label(this.saAsk[v1], this.skin, "hangul-font", this.clAsk[v1])).expandX().fillX();
            }

            this.scpAsk.scrollTo(0f, this.scpAsk.getMaxHeight(), 0f, this.scpAsk.getMaxHeight());
            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->addAsk", Util.getExceptionMessage(v0));
            return;
        }
    }

    private void addHelp(String _sHelp, Color _color) {  // has try-catch handlers
        try {
            if(this.iCurHelp >= this.iMaxHelp) {
                --this.iCurHelp;
                int v1;
                for(v1 = 0; v1 < this.iMaxHelp - 1; ++v1) {
                    this.saHelp[v1] = this.saHelp[v1 + 1];
                    this.clHelp[v1] = this.clHelp[v1 + 1];
                }
            }

            this.saHelp[this.iCurHelp] = _sHelp;
            this.clHelp[this.iCurHelp] = _color;
            ++this.iCurHelp;
            this.tblHelp.clear();
            int v2 = this.iCurHelp + 1;
            if(v2 > this.iMaxHelp) {
                v2 = this.iMaxHelp;
            }

            for(v1 = 0; v1 < v2; ++v1) {
                if(this.width >= 1080f) {
                    this.tblHelp.row().height(50f);
                }
                else {
                    this.tblHelp.row().height(30f);
                }

                this.tblHelp.add(new Label(this.saHelp[v1], this.skin, "hangul-font", this.clHelp[v1])).expandX().fillX();
            }

            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->addHelp", Util.getExceptionMessage(v0));
            return;
        }
    }

    private void addMsg(String _sMsg, Color _color) {  // has try-catch handlers
        try {
            _sMsg = _sMsg.trim();
            int v2 = 0;
            if(_sMsg.indexOf(System.getProperty("line.separator")) >= 0) {
                v2 = 1;
            }

            if(_sMsg.indexOf("<br/>") >= 0) {
                ++v2;
            }

            if(_sMsg.indexOf("<BR/>") >= 0) {
            }

            _sMsg = _sMsg.replaceAll("<br/>", System.getProperty("line.separator")).replaceAll("<BR/>", System.getProperty("line.separator"));
            if(this.iCurMsg >= this.iMaxMsg) {
                --this.iCurMsg;
                int v1;
                for(v1 = 0; v1 < this.iMaxMsg - 1; ++v1) {
                    this.saMsg[v1] = this.saMsg[v1 + 1];
                    this.clMsg[v1] = this.clMsg[v1 + 1];
                }
            }

            this.saMsg[this.iCurMsg] = _sMsg;
            this.clMsg[this.iCurMsg] = _color;
            ++this.iCurMsg;
            this.tblMsg.clear();
            int v3 = this.iCurMsg + 1;
            if(v3 > this.iMaxMsg) {
                v3 = this.iMaxMsg;
            }

            for(v1 = 0; v1 < v3; ++v1) {
                if(this.width >= 1080f) {
                    this.tblMsg.row().height(50f);
                }
                else {
                    this.tblMsg.row().height(30f);
                }

                this.tblMsg.add(new Label(this.saMsg[v1], this.skin, "hangul-font", this.clMsg[v1])).expandX().fillX();
            }

            this.scpMsg.layout();
            this.scpMsg.scrollTo(0f, this.scpMsg.getMaxHeight(), 0f, this.scpMsg.getMaxHeight());
            this.scpMsg.layout();
            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->addMsg", Util.getExceptionMessage(v0));
            return;
        }
    }

    private void addMsg(String _sMsg, Color _color, int iFlag) {
        String[] v0 = _sMsg.replaceAll("<br/>", System.getProperty("line.separator")).replaceAll("<BR/>", System.getProperty("line.separator")).replaceAll("\n", System.getProperty("line.separator")).split(System.getProperty("line.separator"));
        int v1;
        for(v1 = 0; v1 < v0.length; ++v1) {
            this.addMsg(v0[v1], _color);
        }
    }

    private void addTestScenario() {
        this.bReelRunning[0] = false;
        this.bReelRunning[1] = false;
        this.bReelRunning[2] = false;
        this.bReelRunning[3] = false;
        this.bEnding = false;
        this.fNextScenarioDelay = 0f;
        Global.spin += 100;
        Global.machineNo = 1;
        Global.id = "TESTID";
        this.lsScenario.add("@T:I@A:1@B:1@D:D@");
        this.lsScenario.add("@T:O@A:1@B:1@X:M@");
        this.lsScenario.add("@T:S@A:1@B:1@M:10181414,M,MMMM,2,50@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:O@A:1@B:1@X:T@");
        this.lsScenario.add("@T:S@A:1@B:1@M:06040617,B,BB*B,1,60@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:O@A:1@B:1@X:S@");
        this.lsScenario.add("@T:S@A:1@B:1@M:08061518,T,TTT*,2,15@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:O@A:1@B:1@X:W@");
        this.lsScenario.add("@T:S@A:1@B:1@M:07180703,N,NNNN,1,50@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:O@A:1@B:1@X:J@");
        this.lsScenario.add("@T:S@A:1@B:1@M:07070907,S,SSS*,2,20@D:D@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:S@A:1@B:1@M:05020213,Z,ZZZZ,1,20@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@D:N@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
        this.lsScenario.add("@T:I@A:1@B:1@");
    }

    private void additionalTimeOver() {
        if(!this.bWaitingAdditionalGame) {
            this.bWaitingAdditionalGame = true;
            Res.btnLow.play(true, "x");
            Res.btnHigh.play(true, "x");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            Snd.sndAddBack1.stop();
            Snd.sndAddBack2.stop();
            Snd.sndAddBack3.stop();
            Snd.sndAddTimeOver.play();
            GameScreen.socketWrite("G180\t" + Global.id + "\t" + String.valueOf(this.iAdditionalWinMoney) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(this.iAdditionalBaseBet));
        }
    }

    private void askExitGame() {
        if(!Global.bAskingGameExit) {
            Global.bAskingGameExit = true;
            new Dialog() {
                protected void result(Object object) {
                    if(((Boolean)object).booleanValue()) {
                        Global.mode = 5;
                        Global.fTerminating = 0f;
                        Global.bTerminating = true;
                        GameScreen.this.showReserve();
                        GameScreen.socketWrite("G020\t" + Global.id);
                    }
                    else {
                        Global.bAskingGameExit = false;
                    }
                }
            }.text("?????? ?????????????????").button("   ??   ", Boolean.valueOf(true)).button("??????", Boolean.valueOf(false)).key(66, Boolean.valueOf(false)).key(131, Boolean.valueOf(false)).show(GameScreen.stage);
        }
    }

    private void changeMenuItem(int _iMenuMode) {
        int v5 = 12;
        this.iMenuMode = _iMenuMode;
        int v0;
        for(v0 = 0; v0 < v5; ++v0) {
            this.lbMachineNo[v0].setVisible(false);
        }

        this.scpAsk.setVisible(false);
        this.scpHelp.setVisible(false);
        this.lbChargeBank.setVisible(false);
        this.lbChargeAccount.setVisible(false);
        this.lbChargeOwner.setVisible(false);
        this.tfChargeMoney.setVisible(false);
        this.lbChargeMan.setVisible(false);
        this.lbExchangeBank.setVisible(false);
        this.lbExchangeOwner.setVisible(false);
        this.tfExchangeAccount.setVisible(false);
        this.tfExchangeMoney.setVisible(false);
        this.tfExchangeTel.setVisible(false);
        if(this.iMenuMode == 5) {
            v0 = 0;
            goto label_34;
        }
        else if(this.iMenuMode == 1) {
            this.lbChargeBank.setVisible(true);
            this.lbChargeAccount.setVisible(true);
            this.lbChargeOwner.setVisible(true);
            this.tfChargeMoney.setVisible(true);
            this.lbChargeMan.setVisible(true);
        }
        else if(this.iMenuMode == 2) {
            this.lbExchangeBank.setVisible(true);
            this.lbExchangeOwner.setVisible(true);
            this.tfExchangeAccount.setVisible(true);
            this.tfExchangeMoney.setVisible(true);
            this.tfExchangeTel.setVisible(true);
        }
        else if(this.iMenuMode == 3) {
            this.scpHelp.setVisible(true);
        }
        else if(this.iMenuMode == 4) {
            this.scpAsk.setVisible(true);
            return;
        label_34:
            while(v0 < v5) {
                this.lbMachineNo[v0].setVisible(true);
                ++v0;
            }

            this.machineListRequest(0);
        }
    }

    private void charge() {
        if(!"member".equals(Global.cls)) {
            this.showMessage("?????? ?????? ?????? ???? ?? ?? ????????.");
        }
        else {
            this.tfChargeMoney.setText(String.valueOf(this.tfChargeMoney.getText()) + " ");
            this.tfChargeMoney.setText(this.tfChargeMoney.getText().trim());
            int v0 = Util.strToint(this.tfChargeMoney.getText(), 0);
            if(v0 <= 0) {
                this.showMessage("???? ?????? ??????????.");
            }
            else if(v0 < 10000) {
                this.showMessage("???? ???? ?????? 10,000????????.\n???? ?????? ??????????.");
            }
            else if(v0 >= 10000000) {
                this.showMessage("???? ?????? ??????????.\n???? ???? ?????? ??????????????.");
            }
            else if("".equals(this.lbChargeMan.getText().toString().trim())) {
                this.showMessage("?????????? ??????????.");
            }
            else if(this.lbChargeMan.getText().toString().length() > 20) {
                this.showMessage("?????????? ??????????.\n?????? ?????? ??????????????.");
            }
            else {
                new Dialog() {
                    protected void result(Object object) {
                        int v0 = ((Integer)object).intValue();
                        if(v0 > 0) {
                            GameScreen.socketWrite("G120\t" + Global.id + "\t" + GameScreen.this.lbChargeMan.getText().toString().trim() + "\t" + String.valueOf(v0) + "\t" + Global.bank + "\t" + Global.account);
                            GameScreen.this.showToast("??????????????????.\n???? ???? ?? ??????????.", 1.5f);
                            if(GameScreen.bShowMenu) {
                                GameScreen.this.showMenu(false);
                            }

                            GameScreen.this.lbChargeMan.setText("");
                            GameScreen.this.tfChargeMoney.setText("");
                        }
                    }
                }.text("[ " + Util.withComma(((long)v0)) + " ]???? ???? ?????????????????").button("??", Integer.valueOf(v0)).button("??????", Integer.valueOf(0)).key(66, Integer.valueOf(0)).key(131, Integer.valueOf(0)).show(GameScreen.stage);
            }
        }
    }

    private void clearGameScreen() {  // has try-catch handlers
        int v1;
        int v5 = 4;
        try {
            if(GameScreen.thdGameWrite == null) {
                goto label_7;
            }

            GameScreen.thdGameWrite.interrupt();
            GameScreen.thdGameWrite = null;
            goto label_7;
        }
        catch(Exception v0) {
            v0.printStackTrace();
            goto label_7;
        }

        try {
        label_7:
            GameScreen.lsGameWrite.clear();
            goto label_9;
        }
        catch(Exception v0) {
            v0.printStackTrace();
            goto label_9;
        }

        try {
        label_9:
            GameScreen.lsGameRead.clear();
            goto label_11;
        }
        catch(Exception v0) {
            v0.printStackTrace();
            goto label_11;
        }

        try {
        label_11:
            this.lsScenario.clear();
        }
        catch(Exception v0) {
            v0.printStackTrace();
            goto label_14;
        }

        try {
        label_14:
            this.bCanInsert10000 = true;
            this.bCanUpDownChannel = true;
            this.bCanSwitchMoney = true;
            this.bCanReserveMachine = true;
            this.bWaitingScenario = true;
            this.bCanRequestScenario = false;
            this.bEnding = false;
            GameScreen.bReserving = false;
            this.fiCTB = 2;
            this.reelMode = 0;
            this.fScenarioInterval = 0f;
            this.fPlusWinMoneyShowDelay = 0f;
            GameScreen.fTotalReelElapseTime = 0f;
            this.bGameScreenReady = false;
            this.bCanSelectMachine = true;
            v1 = 0;
        }
        catch(Exception v0) {
            goto label_121;
        }

        while(v1 < v5) {
            int v2 = 0;
            while(true) {
                if(v2 < 5) {
                    goto label_65;
                }

                break;
                try {
                label_65:
                    this.card[v1][v2].startY = this.card[v1][v2].y;
                    this.card[v1][v2].visible = true;
                    this.card[v1][v2].mileage = 0f;
                    ++v2;
                    continue;
                }
                catch(Exception v0) {
                    goto label_121;
                }
            }

            ++v1;
        }

        v1 = 0;
        while(true) {
            if(v1 < v5) {
                goto label_85;
            }

            return;
            try {
            label_85:
                this.bReelRunning[v1] = false;
                this.bReelPoppingStarted[v1] = false;
                this.bReelDeceleratingStarted[v1] = false;
                this.bReelAccelerating[v1] = false;
                this.bReelDecelerating[v1] = false;
                this.bReelMinSpeedStarted[v1] = false;
                this.bReelPoppingMinSpeedStarted[v1] = false;
                this.iReelTensioningStep[v1] = 0;
                this.fReelDeltaMileage[v1] = 0f;
                this.fReelElapseTime[v1] = 0f;
                this.iPopCount[v1] = 0;
                ++v1;
                continue;
            }
            catch(Exception v0) {
            label_121:
                v0.printStackTrace();
                return;
            }
        }
    }

    private static void clearGameSocket() {  // has try-catch handlers
        try {
            if(GameScreen.thdGameWrite == null) {
                goto label_6;
            }

            GameScreen.thdGameWrite.interrupt();
            GameScreen.thdGameWrite = null;
            goto label_6;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clearGameSocket(1)", Util.getExceptionMessage(v0));
            goto label_6;
        }

        try {
        label_6:
            GameScreen.lsGameWrite.clear();
            goto label_8;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clearGameSocket(2)", Util.getExceptionMessage(v0));
            goto label_8;
        }

        try {
        label_8:
            GameScreen.lsGameRead.clear();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clearGameSocket(3)", Util.getExceptionMessage(v0));
        }
    }

    private void clickBaseBet(int _iBaseBet) {
        this.bWaitingAdditionalGame = true;
        Snd.sndAddChoice.play();
        Snd.sndAddChoiceBack.stop();
        Res.btnAdditionalChoice1000.visible = false;
        Res.btnAdditionalChoice2000.visible = false;
        GameScreen.socketWrite("G170\t" + Global.id + "\t" + String.valueOf(_iBaseBet) + "\t" + String.valueOf(Global.money));
    }

    private void clickEffect(float _x, float _y, float _width, float _height, int _size) {
        this.clickEffect(_x, _y, _width, _height, _size, true);
    }

    private void clickEffect(float _x, float _y, float _width, float _height, int _size, boolean bPlaySound) {  // has try-catch handlers
        float v5 = 2f;
        try {
            Res.aniClick.setSize(Util.getWidth(((float)_size)), Util.getHeight(((float)_size)));
            Res.aniClick.setPosition(_width / v5 + _x - Util.getWidth(((float)_size)) / v5, _height / v5 + _y - Util.getHeight(((float)_size)) / v5);
            Res.aniClick.visible = true;
            Res.aniClick.play(true, 1);
            if(!bPlaySound) {
                return;
            }

            Snd.sndButton.play();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clickEffect", Util.getExceptionMessage(v0));
        }
    }

    private void clickEffect(int _x, int _y, int _size) {
        this.clickEffect(_x, _y, _size, true);
    }

    private void clickEffect(int _x, int _y, int _size, boolean bPlaySound) {  // has try-catch handlers
        try {
            Res.aniClick.setSize(Util.getWidth(((float)_size)), Util.getHeight(((float)_size)));
            Res.aniClick.setPosition(Util.XCenter(((float)_x), Res.aniClick.width), Util.YCenter(((float)_y), Res.aniClick.height));
            Res.aniClick.visible = true;
            Res.aniClick.play(true, 1);
            if(!bPlaySound) {
                return;
            }

            Snd.sndButton.play();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clickEffect", Util.getExceptionMessage(v0));
        }
    }

    private void clickGo() {  // has try-catch handlers
        int v7 = 7;
        int v4 = 6;
        int v6 = 5;
        try {
            if(!this.bWaitingAdditionalGame) {
                goto label_7;
            }

            return;
        label_7:
            this.bAddSexySnd1 = true;
            this.bAddSexySnd2 = true;
            this.bAddSexySnd3 = true;
            if(Util.randomTrue(100)) {
                int v1 = Util.randomInt(1, 13);
                if(v1 == 1) {
                    Snd.sndSexy1.play();
                }

                if(v1 == 2) {
                    Snd.sndSexy2.play();
                }

                if(v1 == 3) {
                    Snd.sndSexy3.play();
                }

                if(v1 == 4) {
                    Snd.sndSexy4.play();
                }

                if(v1 == v6) {
                    Snd.sndSexy5.play();
                }

                if(v1 == v4) {
                    Snd.sndSexy6.play();
                }

                if(v1 == v7) {
                    Snd.sndSexy7.play();
                }

                if(v1 == 8) {
                    Snd.sndSexy8.play();
                }

                if(v1 == 9) {
                    Snd.sndSexy9.play();
                }

                if(v1 == 10) {
                    Snd.sndSexy10.play();
                }

                if(v1 == 11) {
                    Snd.sndSexy11.play();
                }

                if(v1 == 12) {
                    Snd.sndSexy12.play();
                }

                if(v1 != 13) {
                    goto label_67;
                }

                Snd.sndSexy13.play();
            }

        label_67:
            Snd.stopAddSound();
            Snd.sndAddBack1.setLooping(true);
            Snd.sndAddBack1.play();
            this.iAdditionalTimer = this.iAdditionalBetTimeLimit;
            this.fAdditionalTimer = ((float)this.iAdditionalBetTimeLimit);
            this.bAddHurryUp1 = false;
            this.bAddHurryUp2 = false;
            this.setAdditionalTimer();
            Res.aniAdditionalWLT.visible = false;
            Res.btnLow.play(true, "o");
            Res.btnHigh.play(true, "o");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            if(this.iAdditionalStep != 1) {
                goto label_118;
            }

            Res.addCard[5].visible = false;
            return;
        label_118:
            if(this.iAdditionalStep != 2) {
                goto label_132;
            }

            Res.addCard[5].visible = false;
            Res.addCard[6].visible = false;
            return;
        label_132:
            if(this.iAdditionalStep != 3) {
                goto label_151;
            }

            Res.addCard[5].visible = false;
            Res.addCard[6].visible = false;
            Res.addCard[7].visible = false;
            return;
        label_151:
            if(this.iAdditionalStep != 4) {
                goto label_175;
            }

            Res.addCard[5].visible = false;
            Res.addCard[6].visible = false;
            Res.addCard[7].visible = false;
            Res.addCard[8].visible = false;
            return;
        label_175:
            if(this.iAdditionalStep != v6) {
                return;
            }

            Res.addCard[5].visible = false;
            Res.addCard[6].visible = false;
            Res.addCard[7].visible = false;
            Res.addCard[8].visible = false;
            Res.addCard[9].visible = false;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clickGo(CGO0)", Util.getExceptionMessage(v0));
            GameScreen.exitApp("???????? ?????? ?? ????????(CGO0).\n?????????? ??????????.");
        }
    }

    private void clickHighLow(String sHighLow) {  // has try-catch handlers
        try {
            if(!this.bWaitingAdditionalGame) {
                goto label_3;
            }

            return;
        label_3:
            this.bWaitingAdditionalGame = true;
            this.bAddSexySnd1 = true;
            this.bAddSexySnd2 = true;
            this.bAddSexySnd3 = true;
            this.iAdditionalTimer = 999999999;
            this.fAdditionalTimer = 1000000000f;
            this.bAddHurryUp1 = false;
            this.bAddHurryUp2 = false;
            Res.btnLow.play(true, "x");
            Res.btnHigh.play(true, "x");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            Snd.sndAddClick.play();
            GameScreen.socketWrite("G200\t" + Global.id + "\t" + String.valueOf(this.iAdditionalWinMoney) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(this.iAdditionalMultiple) + "\t" + sHighLow + "\t" + String.valueOf(this.iAdditionalBaseBet) + "\t" + String.valueOf(this.iAdditionalStep));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clickHighLow(0)", Util.getExceptionMessage(v0), "sHighLowsHighLow=" + sHighLow);
            GameScreen.exitApp("???????? ?????? ?? ????????(CLH0).\n?????????? ??????????.");
        }
    }

    private void clickStop() {  // has try-catch handlers
        try {
            if(!Res.btnNewGame.visible) {
                goto label_6;
            }

            this.fEndAdditionalGameDelay = 0f;
            return;
        label_6:
            if(this.bWaitingAdditionalGame) {
                return;
            }

            this.bWaitingAdditionalGame = true;
            Snd.stopAddSound();
            this.iAdditionalTimer = 999999999;
            this.fAdditionalTimer = 1000000000f;
            this.bAddHurryUp1 = false;
            this.bAddHurryUp2 = false;
            Res.btnLow.play(true, "x");
            Res.btnHigh.play(true, "x");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            GameScreen.socketWrite("G190\t" + Global.id + "\t" + String.valueOf(this.iAdditionalWinMoney) + "\t" + String.valueOf(Global.money) + "\t" + String.valueOf(this.iAdditionalMultiple) + "\t" + String.valueOf(this.iAdditionalBaseBet));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->clickStop(CST0)", Util.getExceptionMessage(v0));
            GameScreen.exitApp("???????? ?????? ?? ????????(CST0).\n?????????? ??????????.");
        }
    }

    private void controlReelSpeed(int iReelIndex) {
        float v1 = 0.25f;
        float v0 = -0.03f;
        if(this.bReelAccelerating[iReelIndex]) {
            if(this.fReelElapseTime[iReelIndex] <= v1) {
                this.fReelDeltaMileage[iReelIndex] = this.deltaTime * v0;
            }
            else {
                this.fReelDeltaMileage[iReelIndex] += this.accelerationSpeed[iReelIndex] * this.deltaTime;
                if(this.fReelDeltaMileage[iReelIndex] > this.reelMaxSpeed[iReelIndex] * this.deltaTime) {
                    this.fReelDeltaMileage[iReelIndex] = this.reelMaxSpeed[iReelIndex] * this.deltaTime;
                }
            }
        }

        if(this.bReelDecelerating[iReelIndex]) {
            if(this.bReelPoppingStarted[iReelIndex]) {
                if(this.bReelPoppingMinSpeedStarted[iReelIndex]) {
                    this.fReelDeltaMileage[iReelIndex] = this.reelMinPoppingSpeed[iReelIndex] * this.deltaTime;
                }
                else {
                    this.fReelDeltaMileage[iReelIndex] -= this.decelerationSpeed[iReelIndex] * this.deltaTime;
                    if(this.fReelDeltaMileage[iReelIndex] < this.reelMinPoppingSpeed[iReelIndex] * this.deltaTime) {
                        this.fReelDeltaMileage[iReelIndex] = this.reelMinPoppingSpeed[iReelIndex] * this.deltaTime;
                        this.bReelPoppingMinSpeedStarted[iReelIndex] = true;
                    }
                }
            }
            else if(this.bReelMinSpeedStarted[iReelIndex]) {
                this.fReelDeltaMileage[iReelIndex] = this.reelMinSpeed[iReelIndex] * this.deltaTime;
            }
            else {
                this.fReelDeltaMileage[iReelIndex] -= this.decelerationSpeed[iReelIndex] * this.deltaTime;
                if(this.fReelDeltaMileage[iReelIndex] < this.reelMinSpeed[iReelIndex] * this.deltaTime) {
                    this.fReelDeltaMileage[iReelIndex] = this.reelMinSpeed[iReelIndex] * this.deltaTime;
                    this.bReelMinSpeedStarted[iReelIndex] = true;
                }
            }
        }
    }

    private int countElement(String _sSrc, String _sElement) {
        if(_sSrc == null) {
            _sSrc = "";
        }

        int v1 = 0;
        int v2 = _sSrc.length();
        int v0;
        for(v0 = 0; v0 < v2; ++v0) {
            if(_sSrc.substring(v0, v0 + 1).equals(_sElement)) {
                ++v1;
            }
        }

        return v1;
    }

    public void dispose() {
        this.disposeGameScreen();
    }

    private void disposeGameScreen() {  // has try-catch handlers
        try {
            this.timer.cancel();
            goto label_2;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(1)", Util.getExceptionMessage(v0));
            goto label_2;
        }

        try {
        label_2:
            MySocket.clear();
            goto label_3;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(2)", Util.getExceptionMessage(v0));
            goto label_3;
        }

        try {
        label_3:
            GameScreen.clearGameSocket();
            goto label_4;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(3)", Util.getExceptionMessage(v0));
            goto label_4;
        }

        try {
        label_4:
            this.clearGameScreen();
            goto label_5;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(4)", Util.getExceptionMessage(v0));
            goto label_5;
        }

        try {
        label_5:
            GameScreen.batch.dispose();
            goto label_7;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(5)", Util.getExceptionMessage(v0));
            goto label_7;
        }

        try {
        label_7:
            this.skin.dispose();
            goto label_9;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(6)", Util.getExceptionMessage(v0));
            goto label_9;
        }

        try {
        label_9:
            GameScreen.skin_window.dispose();
            goto label_11;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(7)", Util.getExceptionMessage(v0));
            goto label_11;
        }

        try {
        label_11:
            GameScreen.stage.dispose();
            goto label_13;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(8)", Util.getExceptionMessage(v0));
            goto label_13;
        }

        try {
        label_13:
            Res.dispose();
            goto label_14;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(9)", Util.getExceptionMessage(v0));
            goto label_14;
        }

        try {
        label_14:
            Global.ini();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->disposeGameScreen(10)", Util.getExceptionMessage(v0));
            goto label_16;
        }

        try {
        label_16:
            Dialog.count = 0;
            goto label_17;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "LoginScreen->disposeGameScreen(11)", Util.getExceptionMessage(v0));
            goto label_17;
        }

        try {
        label_17:
            Snd.dispose();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "LoginScreen->disposeGameScreen(12)", Util.getExceptionMessage(v0));
        }
    }

    private void drawBackground() {
        float v3 = 0.02f;
        if(this.iBackgroundDirection > 0) {
            if(Res.bgGameTop1.x <= 0f - Util.getWidth(2048f) + Util.getWidth(this.width)) {
                this.iBackgroundDirection = -1;
            }
            else {
                Res.bgGameTop1.x -= this.deltaTime * v3;
            }
        }
        else if(this.iBackgroundDirection <= 0) {
            if(Res.bgGameTop1.x >= Util.getLeft(0f)) {
                this.iBackgroundDirection = 1;
            }
            else {
                Res.bgGameTop1.x += this.deltaTime * v3;
            }
        }

        Res.bgGameTop2.x = Res.bgGameTop1.x + Util.getWidth(1024f);
        Res.bgGameTop1.draw();
        Res.bgGameTop2.draw();
        Res.bgGameBottom1.x = Res.bgGameTop1.x;
        Res.bgGameBottom2.x = Res.bgGameTop2.x;
        Res.bgGameBottom1.draw();
        Res.bgGameBottom2.draw();
    }

    private void drawCards() {
        this.card[0][0].draw(this.deltaTime);
        this.card[0][1].draw(this.deltaTime);
        this.card[0][2].draw(this.deltaTime);
        this.card[0][3].draw(this.deltaTime);
        this.card[0][4].draw(this.deltaTime);
        this.card[1][0].draw(this.deltaTime);
        this.card[1][1].draw(this.deltaTime);
        this.card[1][2].draw(this.deltaTime);
        this.card[1][3].draw(this.deltaTime);
        this.card[1][4].draw(this.deltaTime);
        this.card[2][0].draw(this.deltaTime);
        this.card[2][1].draw(this.deltaTime);
        this.card[2][2].draw(this.deltaTime);
        this.card[2][3].draw(this.deltaTime);
        this.card[2][4].draw(this.deltaTime);
        this.card[3][0].draw(this.deltaTime);
        this.card[3][1].draw(this.deltaTime);
        this.card[3][2].draw(this.deltaTime);
        this.card[3][3].draw(this.deltaTime);
        this.card[3][4].draw(this.deltaTime);
        this.card[0][GameScreen.frontCardContainerIdx[0]].draw(this.deltaTime);
        this.card[1][GameScreen.frontCardContainerIdx[1]].draw(this.deltaTime);
        this.card[2][GameScreen.frontCardContainerIdx[2]].draw(this.deltaTime);
        this.card[3][GameScreen.frontCardContainerIdx[3]].draw(this.deltaTime);
    }

    private void drawInter() {
        float v7 = 1536f;
        float v6 = 0.07f;
        float v3 = 1024f;
        if(this.iWinTableTopContainer == 1) {
            Res.imgWinTableTop1.x -= this.deltaTime * v6;
            Res.imgWinTableTop2.x = Res.imgWinTableTop1.x + Util.getWidth(v3);
            if(Res.imgWinTableTop1.x <= 0f - Util.getWidth(v7)) {
                Res.imgWinTableTop1.x = Res.imgWinTableTop2.x + Util.getWidth(v3);
                this.iWinTableTopContainer = 2;
            }
        }
        else {
            Res.imgWinTableTop2.x -= this.deltaTime * v6;
            Res.imgWinTableTop1.x = Res.imgWinTableTop2.x + Util.getWidth(v3);
            if(Res.imgWinTableTop2.x <= 0f - Util.getWidth(v7)) {
                Res.imgWinTableTop2.x = Res.imgWinTableTop1.x + Util.getWidth(v3);
                this.iWinTableTopContainer = 1;
            }
        }

        if(this.iWinTableBottomContainer == 1) {
            Res.imgWinTableBottom1.x -= this.deltaTime * v6;
            Res.imgWinTableBottom2.x = Res.imgWinTableBottom1.x + Util.getWidth(v3);
            if(Res.imgWinTableBottom1.x <= 0f - Util.getWidth(v7)) {
                Res.imgWinTableBottom1.x = Res.imgWinTableBottom2.x + Util.getWidth(v3);
                this.iWinTableBottomContainer = 2;
            }
        }
        else {
            Res.imgWinTableBottom2.x -= this.deltaTime * v6;
            Res.imgWinTableBottom1.x = Res.imgWinTableBottom2.x + Util.getWidth(v3);
            if(Res.imgWinTableBottom2.x <= 0f - Util.getWidth(v7)) {
                Res.imgWinTableBottom2.x = Res.imgWinTableBottom1.x + Util.getWidth(v3);
                this.iWinTableBottomContainer = 1;
            }
        }

        if(this.iChatFlag != 1) {
            Res.imgWinTableTop1.draw();
            Res.imgWinTableTop2.draw();
            Res.imgWinTableBottom1.draw();
            Res.imgWinTableBottom2.draw();
        }

        Res.imgInter.draw();
        Res.imgInterLeft.draw();
        Res.imgInterRight.draw();
        Res.imgInterBottom.draw();
    }

    private void drawItem() {
        int v0;
        for(v0 = 0; v0 < 39; ++v0) {
            GameScreen.item[v0].x -= this.deltaTime * 0.2f;
            GameScreen.item[v0].draw(this.deltaTime);
            if(GameScreen.item[v0].x <= Util.getLeft(-500f)) {
                GameScreen.item[v0].x = GameScreen.item[(v0 + 38) % 39].x + Util.getWidth(100f);
            }
        }
    }

    private void effectBet(int iCnt) {
        Res.aniDropper.play(true, "2", 30);
    }

    public static void effectGift(int iCnt) {
        Res.aniBackGift.play(true, iCnt);
    }

    private void effectMoney(int iCnt) {
        Res.aniBackMoney.play(true, iCnt);
    }

    private void effectNo(int iCnt) {
        Res.aniBackNo.play(true, iCnt);
    }

    public static void effectSpin(int iCnt) {
        Res.aniBackSpin.play(true, iCnt);
    }

    private void endScenario() {  // has try-catch handlers
        try {
            if(!Global.bTerminating) {
                goto label_3;
            }

            return;
        label_3:
            if(this.lsScenario.size() > 0) {
                goto label_21;
            }

            Util.sysout("ERROR", "GameScreen->endScenario(1)", "Invalid Scenario Count.");
            GameScreen.exitApp("?????????? ???? ?? ?? ????????(E1).\n?????????? ??????????.");
            return;
        label_21:
            Object v3 = this.lsScenario.get(0);
            String v5 = this.getValue("A", ((String)v3));
            String v4 = this.getValue("B", ((String)v3));
            int v1 = Util.strToint(v4, 0);
            if(Global.machineNo == v1 && v1 > 0) {
                goto label_44;
            }

            Util.sysout("ERROR", "GameScreen->endScenario(2)", "Invalid MachineNo.", "sMachineNo=" + v4);
            GameScreen.exitApp("?????????? ?????? ?? ????????(E2).\n?????????? ??????????.");
            return;
        label_44:
            this.lsScenario.remove(0);
            this.bReelRunning[0] = false;
            this.bReelRunning[1] = false;
            this.bReelRunning[2] = false;
            this.bReelRunning[3] = false;
            this.bEnding = true;
            GameScreen.socketWrite("G100\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + v5 + "\t" + (String.valueOf(this.findCardPos(0)) + this.findCardPos(1) + this.findCardPos(2) + this.findCardPos(3)));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->endScenario(0)", Util.getExceptionMessage(v0));
            GameScreen.exitApp("?????????? ???? ?? ?? ????????(E).\n?????????? ??????????.");
        }
    }

    private void exchange() {
        int v4 = 20;
        this.tfExchangeAccount.setText(String.valueOf(this.tfExchangeAccount.getText()) + " ");
        this.tfExchangeAccount.setText(this.tfExchangeAccount.getText().trim());
        if("".equals(this.tfExchangeAccount.getText())) {
            this.showMessage("?????????? ??????????.");
        }
        else {
            this.tfExchangeMoney.setText(String.valueOf(this.tfExchangeMoney.getText()) + " ");
            this.tfExchangeMoney.setText(this.tfExchangeMoney.getText().trim());
            int v0 = Util.strToint(this.tfExchangeMoney.getText(), 0);
            if(v0 <= 0) {
                this.showMessage("???? ?????? ??????????.");
            }
            else if(v0 < 1000) {
                this.showMessage("???? ???? ?????? 1,000????????.");
            }
            else if(v0 > Global.money) {
                this.showMessage("???? ?????? ????????\n?????? ?? ????????.");
            }
            else {
                this.tfExchangeTel.setText(String.valueOf(this.tfExchangeTel.getText()) + " ");
                this.tfExchangeTel.setText(this.tfExchangeTel.getText().trim());
                if("".equals(this.tfExchangeTel.getText())) {
                    this.showMessage("?????????? ??????????.");
                }
                else {
                    this.lbExchangeBank.setText(this.lbExchangeBank.getText().toString().trim());
                    if("".equals(this.lbExchangeBank.getText().toString())) {
                        this.showMessage("???????? ??????????.");
                    }
                    else {
                        this.lbExchangeOwner.setText(this.lbExchangeOwner.getText().toString().trim());
                        if("".equals(this.lbExchangeOwner.getText().toString())) {
                            this.showMessage("???????? ??????????.");
                        }
                        else if(this.lbExchangeBank.getText().toString().length() > v4) {
                            this.showMessage("???????? ??????????.\n?????? ?????? ??????????????.");
                        }
                        else if(this.lbExchangeOwner.getText().toString().length() > v4) {
                            this.showMessage("???????? ??????????.\n?????? ?????? ??????????????.");
                        }
                        else if(this.tfExchangeAccount.getText().length() > v4) {
                            this.showMessage("?????????? ??????????.\n?????? ?????? ??????????????.");
                        }
                        else if(this.tfExchangeTel.getText().length() > v4) {
                            this.showMessage("?????????? ??????????.\n?????? ?????? ??????????????.");
                        }
                        else {
                            new Dialog() {
                                protected void result(Object object) {
                                    if(((Integer)object).intValue() > 0) {
                                        GameScreen.socketWrite("G130\t" + Global.id + "\t" + GameScreen.this.lbExchangeBank.getText().toString().trim() + "\t" + GameScreen.this.lbExchangeOwner.getText().toString().trim() + "\t" + GameScreen.this.tfExchangeAccount.getText().trim() + "\t" + GameScreen.this.tfExchangeMoney.getText().trim() + "\t" + GameScreen.this.tfExchangeTel.getText().trim());
                                        GameScreen.this.showToast("??????????????????.\n ???? ?? ????????????????.", 1.5f);
                                        if(GameScreen.bShowMenu) {
                                            GameScreen.this.showMenu(false);
                                        }

                                        GameScreen.this.tfExchangeAccount.setText("");
                                        GameScreen.this.tfExchangeMoney.setText("");
                                        GameScreen.this.tfExchangeTel.setText("");
                                        GameScreen.this.lbExchangeBank.setText("");
                                        GameScreen.this.lbExchangeOwner.setText("");
                                    }
                                }
                            }.text("???????? ????????\n ???? ?????? ?????????????").button("??", Integer.valueOf(1)).button("??????", Integer.valueOf(0)).key(66, Integer.valueOf(0)).key(131, Integer.valueOf(0)).show(GameScreen.stage);
                        }
                    }
                }
            }
        }
    }

    public static void exitApp(String sMsg) {  // has try-catch handlers
        try {
            Global.bAskingGameExit = true;
            Global.mode = 5;
            GameScreen.sendMsgToOuter("hide_ui");
            GameScreen.playAndroidSound("");
            MySocket.clear();
            GameScreen.clearGameSocket();
            GameScreen.lbReserve.setVisible(false);
            Res.aniReserveBack.visible = false;
            goto label_16;
        }
        catch(Exception v0) {
            goto label_51;
        }

        try {
        label_16:
            Snd.stopAddSound();
            Snd.stopSeaSound();
            goto label_18;
        }
        catch(Exception v0) {
            try {
                Util.sysout("ERROR", "GameScreen->exitApp(1)", Util.getExceptionMessage(v0));
            label_18:
                if("".equals(sMsg)) {
                    goto label_54;
                }

                new Dialog() {
                    protected void result(Object object) {
                        Global.fTerminating = 0f;
                        Global.bTerminating = true;
                    }
                }.text(sMsg).button("   ????   ", Boolean.valueOf(true)).key(66, Boolean.valueOf(true)).key(131, Boolean.valueOf(true)).show(GameScreen.stage);
                return;
            label_54:
                Global.fTerminating = 0f;
                Global.bTerminating = true;
            }
            catch(Exception v0) {
            label_51:
                Util.sysout("ERROR", "GameScreen->exitApp(0)", Util.getExceptionMessage(v0));
            }
        }
    }

    private String findCardPos(int iReelIndex) {  // has try-catch handlers
        String v4 = "";
        int v2 = 0;
        try {
            float v1 = Res.fBaseLineY3;
            float[] v3 = new float[5];
            v3[0] = Math.abs(this.card[iReelIndex][0].y - v1);
            v3[1] = Math.abs(this.card[iReelIndex][1].y - v1);
            v3[2] = Math.abs(this.card[iReelIndex][2].y - v1);
            v3[3] = Math.abs(this.card[iReelIndex][3].y - v1);
            v3[4] = Math.abs(this.card[iReelIndex][4].y - v1);
            float[] v5 = new float[5];
            v5[0] = v3[0];
            v5[1] = v3[1];
            v5[2] = v3[2];
            v5[3] = v3[3];
            v5[4] = v3[4];
            Arrays.sort(v3);
            if(v5[0] == v3[0]) {
                v2 = 0;
            }
            else if(v5[1] == v3[0]) {
                v2 = 1;
            }
            else if(v5[2] == v3[0]) {
                v2 = 2;
            }
            else if(v5[3] == v3[0]) {
                v2 = 3;
            }
            else if(v5[4] == v3[0]) {
                v2 = 4;
            }
            else {
                Util.sysout("ERROR", "GameScreen->findCardPos(1)", "Can\'t find the card");
            }

            v4 = "T" + this.noToPos(this.card[iReelIndex][v2].bandPosNo);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->findCardPos(0)", Util.getExceptionMessage(v0), "");
        }

        return v4;
    }

    private void fn_additional_game_info(String[] aMsg) {  // has try-catch handlers
        try {
            this.startFireworks(false);
            this.setAdditionalOpenCard(aMsg[1]);
            this.setAdditionalHiddenCard(aMsg[2]);
            int v1 = Integer.parseInt(aMsg[5]);
            if(v1 >= 0) {
                goto label_20;
            }

            Util.sysout("ERROR", "GameScreen->fn_additional_game_info(1)", "Invalid Money.", Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(Add1).\n?????????? ??????????.");
            return;
        label_20:
            Global.money = v1;
            this.iAdditionalBaseBet = Integer.parseInt(aMsg[6]);
            if(this.iAdditionalBaseBet != 2000 && this.iAdditionalBaseBet != 10000) {
                Util.sysout("ERROR", "GameScreen->fn_additional_game_info(2)", "Invalid BaseBet.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(Add2).\n?????????? ??????????.");
                return;
            }
        }
        catch(Exception v0) {
            goto label_44;
        }

        int v2 = 4;
        try {
            this.iAdditionalWinMoney = Integer.parseInt(aMsg[v2]);
            if(this.iAdditionalWinMoney == 0) {
                goto label_62;
            }

            Util.sysout("ERROR", "GameScreen->fn_additional_game_info(3)", "Invalid BaseBet.", Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(Add3).\n?????????? ??????????.");
            return;
        label_62:
            this.iAdditionalMultiple = Integer.parseInt(aMsg[3]);
            if(this.iAdditionalMultiple == 2) {
                goto label_77;
            }

            Util.sysout("ERROR", "GameScreen->fn_additional_game_info(4)", "Invalid Multiple.", Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(Add4).\n?????????? ??????????.");
            return;
        label_77:
            this.iAdditionalStep = 0;
            Res.btnLow.play(true, "o");
            Res.btnHigh.play(true, "o");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            Res.addCard[5].visible = true;
            Res.addCard[6].visible = true;
            Res.addCard[7].visible = true;
            Res.addCard[8].visible = true;
            Res.addCard[9].visible = true;
            this.iAdditionalTimer = this.iAdditionalBetTimeLimit;
            this.fAdditionalTimer = ((float)this.iAdditionalBetTimeLimit);
            this.bAddHurryUp1 = false;
            this.bAddHurryUp2 = false;
            this.setAdditionalTimer();
            this.bAddSexySnd1 = Util.randomTrue(70);
            this.bAddSexySnd2 = Util.randomTrue(70);
            this.bAddSexySnd3 = Util.randomTrue(70);
            Res.aniAdditionalWLT.visible = false;
            this.bAdditionalWinCeremony = false;
            this.iAdditionalWinMoney = 0;
            this.fAdditionalWinCeremonyMoneyTimer = -1000000000f;
            this.fAdditionalBeep = -1000000000f;
            this.fEndAdditionalGameDelay = 1000000000f;
            this.fAdditionalNewGameBtnDelay = 1000000000f;
            GameScreen.lbAdditionalMoney.setText(Util.withComma(((long)Global.money)));
            GameScreen.lbAdditionalWin.setText("0");
            GameScreen.lbAdditionalMoney.setVisible(true);
            GameScreen.lbAdditionalWin.setVisible(true);
            Res.bigSmall.play(true, "bigsmall");
            Res.aniBonus.play(true, String.valueOf(String.valueOf(this.iAdditionalBaseBet)) + "x" + String.valueOf(this.iAdditionalMultiple));
            this.iAdditionalLady = Util.randomInt(0, 24);
            Res.aniLady.play(true, "a" + String.valueOf(this.iAdditionalLady));
            this.fAdditionalBaseBetTimer = 1000000000f;
            this.bAdditionalNeedNewGame = false;
            Res.btnNewGame.visible = false;
            Res.imgBackNewGame.visible = false;
            this.bWaitingAdditionalGame = false;
            Snd.sndAddBack1.setLooping(true);
            Snd.sndAddBack1.play();
        }
        catch(Exception v0) {
        label_44:
            Util.sysout("ERROR", "GameScreen->fn_additional_game_info(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(Add0).\n?????????? ??????????.");
        }
    }

    private void fn_additional_high_low(String[] aMsg) {  // has try-catch handlers
        int v1;
        int v6;
        String v8;
        int v2;
        int v5;
        int v4;
        int v3;
        try {
            if(!Global.id.equals(aMsg[1])) {
                Util.sysout("ERROR", "GameScreen->fn_additional_high_low(1)", "Invalid Id.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CLH1).\n?????????? ??????????.");
            }

            String v9 = aMsg[2];
            v3 = Integer.parseInt(aMsg[3]);
            v4 = Integer.parseInt(aMsg[4]);
            v5 = Integer.parseInt(aMsg[5]);
            v2 = Integer.parseInt(aMsg[6]);
            v8 = aMsg[7];
            v6 = Integer.parseInt(aMsg[8]);
            if(v5 == 1) {
                Res.addCard[5].setImage(v8);
            }
            else if(v5 == 2) {
                Res.addCard[6].setImage(v8);
            }
            else {
                goto label_155;
            }

            goto label_37;
        }
        catch(Exception v0) {
            goto label_151;
        }

    label_155:
        if(v5 == 3) {
            try {
                Res.addCard[7].setImage(v8);
                goto label_37;
            label_162:
                if(v5 == 4) {
                    Res.addCard[8].setImage(v8);
                }
                else if(v5 == 5) {
                    Res.addCard[9].setImage(v8);
                }
                else {
                    Util.sysout("ERROR", "GameScreen->fn_additional_high_low(2)", "Invalid OpenCard.", Util.toString(aMsg));
                    GameScreen.exitApp("???????? ?????? ?? ????????(CLH2).\n?????????? ??????????.");
                }

            label_37:
                this.iAdditionalWinMoney = v3;
                this.iAdditionalMultiple = v4;
                this.iAdditionalStep = v5;
                Global.money = v2 + v6;
                GameScreen.lbAdditionalMoney.setText(String.valueOf(v2));
                GameScreen.lbAdditionalWin.setText(Util.withComma(((long)this.iAdditionalWinMoney)));
                Res.bigSmall.play(true, "bigsmall");
                this.bWaitingAdditionalGame = false;
                this.setAdditionalTimer();
                Snd.stopAddSound();
                if(!"W".equals(v9)) {
                    goto label_274;
                }

                Res.aniBonus.play(true, String.valueOf(String.valueOf(this.iAdditionalBaseBet)) + "x" + String.valueOf(this.iAdditionalMultiple));
                if(this.iAdditionalMultiple == 2 || this.iAdditionalMultiple == 4) {
                    int v7 = 1;
                    goto label_81;
                label_193:
                    if(this.iAdditionalMultiple != 8 && this.iAdditionalMultiple != 16) {
                        goto label_221;
                    }

                    v7 = 1;
                    goto label_200;
                label_221:
                    v7 = 1;
                    goto label_222;
                label_274:
                    if(!"T".equals(v9)) {
                        goto label_353;
                    }

                    if(v5 >= 5) {
                        Res.btnLow.play(true, "x");
                        Res.btnHigh.play(true, "x");
                        Res.btnGo.play(true, "x");
                        Res.btnStop.play(true, "x");
                        this.bAdditionalWinCeremony = true;
                        this.fAdditionalWinCeremonyMoneyTimer = 0f;
                        this.fAdditionalBeep = 0f;
                        this.fEndAdditionalGameDelay = 1000000000f;
                        if(Global.money >= this.iAdditionalBaseBet) {
                            this.bAdditionalNeedNewGame = true;
                        }

                        Snd.sndAddCheer.play();
                    }
                    else {
                        Res.btnLow.play(true, "x");
                        Res.btnHigh.play(true, "x");
                        Res.btnGo.play(true, "o");
                        Res.btnStop.play(true, "o");
                        this.iAdditionalTimer = this.iAdditionalBetTimeLimit;
                        this.fAdditionalTimer = ((float)this.iAdditionalBetTimeLimit);
                        this.bAddHurryUp1 = false;
                        this.bAddHurryUp2 = false;
                        Snd.sndAddBack1.setLooping(true);
                        Snd.sndAddBack1.play();
                        goto label_310;
                    label_353:
                        if(!"L".equals(v9)) {
                            goto label_397;
                        }

                        Res.btnLow.play(true, "x");
                        Res.btnHigh.play(true, "x");
                        Res.btnGo.play(true, "x");
                        Res.btnStop.play(true, "x");
                        this.bAdditionalWinCeremony = true;
                        this.fAdditionalWinCeremonyMoneyTimer = 0f;
                        this.fAdditionalBeep = 0f;
                        this.fEndAdditionalGameDelay = 1000000000f;
                        if(Global.money >= this.iAdditionalBaseBet) {
                            this.bAdditionalNeedNewGame = true;
                        }

                        this.setAdditionalTimer();
                        Res.aniAdditionalWLT.play(true, "L", 4);
                        Res.aniAdditionalWLT.visible = true;
                        Snd.sndAddLose.play();
                        return;
                    label_397:
                        Util.sysout("ERROR", "GameScreen->fn_additional_high_low(3)", "Invalid Win/Lose.", Util.toString(aMsg));
                        GameScreen.exitApp("???????? ?????? ?? ????????(CLH3).\n?????????? ??????????.");
                        return;
                    }

                label_310:
                    this.setAdditionalTimer();
                    Res.aniAdditionalWLT.play(true, "T", 4);
                    Res.aniAdditionalWLT.visible = true;
                    Snd.sndAddTie.play();
                    return;
                label_222:
                    while(v7 < 100) {
                        v1 = Util.randomInt(0, 32);
                        if(v1 != this.iAdditionalLady) {
                            this.iAdditionalLady = v1;
                        }
                        else {
                            ++v7;
                            continue;
                        }

                        break;
                    }

                    Res.aniLady.play(true, "c" + String.valueOf(this.iAdditionalLady));
                    goto label_92;
                label_200:
                    while(v7 < 100) {
                        v1 = Util.randomInt(0, 35);
                        if(v1 != this.iAdditionalLady) {
                            this.iAdditionalLady = v1;
                        }
                        else {
                            ++v7;
                            continue;
                        }

                        break;
                    }

                    Res.aniLady.play(true, "b" + String.valueOf(this.iAdditionalLady));
                    goto label_92;
                label_81:
                    while(v7 < 100) {
                        v1 = Util.randomInt(0, 24);
                        if(v1 != this.iAdditionalLady) {
                            this.iAdditionalLady = v1;
                        }
                        else {
                            ++v7;
                            continue;
                        }

                        break;
                    }

                    Res.aniLady.play(true, "a" + String.valueOf(this.iAdditionalLady));
                }
                else {
                    goto label_193;
                }

            label_92:
                if(v5 >= 5) {
                    Res.btnLow.play(true, "x");
                    Res.btnHigh.play(true, "x");
                    Res.btnGo.play(true, "x");
                    Res.btnStop.play(true, "x");
                    this.bAdditionalWinCeremony = true;
                    this.fAdditionalWinCeremonyMoneyTimer = 0f;
                    this.fAdditionalBeep = 0f;
                    this.fEndAdditionalGameDelay = 1000000000f;
                    if(Global.money >= this.iAdditionalBaseBet) {
                        this.bAdditionalNeedNewGame = true;
                    }

                    this.startFireworks(true);
                    Snd.sndAddCheer.play();
                }
                else {
                    Res.btnLow.play(true, "x");
                    Res.btnHigh.play(true, "x");
                    Res.btnGo.play(true, "o");
                    Res.btnStop.play(true, "o");
                    this.iAdditionalTimer = this.iAdditionalBetTimeLimit;
                    this.fAdditionalTimer = ((float)this.iAdditionalBetTimeLimit);
                    this.bAddHurryUp1 = false;
                    this.bAddHurryUp2 = false;
                    Snd.sndAddBack1.setLooping(true);
                    Snd.sndAddBack1.play();
                }

                this.setAdditionalTimer();
                Res.aniAdditionalWLT.play(true, "W", 4);
                Res.aniAdditionalWLT.visible = true;
                Snd.sndAddWin.play();
                return;
            }
            catch(Exception v0) {
            label_151:
                Util.sysout("ERROR", "GameScreen->fn_additional_high_low(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CLH0).\n?????????? ??????????.");
                return;
            }
        }
        else {
            goto label_162;
        }

        goto label_37;
    }

    private void fn_additional_stop(String[] aMsg) {  // has try-catch handlers
        try {
            if(!Global.id.equals(aMsg[1])) {
                Util.sysout("ERROR", "GameScreen->fn_additional_stop(1)", "Invalid Id.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CST1).\n?????????? ??????????.");
            }

            int v2 = Integer.parseInt(aMsg[2]);
            int v1 = Integer.parseInt(aMsg[3]);
            GameScreen.lbAdditionalMoney.setText(Util.withComma(((long)v1)));
            GameScreen.lbAdditionalWin.setText(String.valueOf(v2));
            Global.money = v1 + v2;
            this.bAdditionalWinCeremony = true;
            this.iAdditionalWinMoney = v2;
            this.fAdditionalWinCeremonyMoneyTimer = 0f;
            this.fAdditionalBeep = 0f;
            this.fEndAdditionalGameDelay = 1000000000f;
            if(v2 > 0) {
                Snd.sndAddCheer.play();
            }

            if(Global.money >= this.iAdditionalBaseBet) {
                this.bAdditionalNeedNewGame = true;
            }

            this.bWaitingAdditionalGame = false;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_additional_stop(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(CST0).\n?????????? ??????????.");
        }
    }

    private void fn_additional_time_over(String[] aMsg) {  // has try-catch handlers
        try {
            if(!Global.id.equals(aMsg[1])) {
                Util.sysout("ERROR", "GameScreen->fn_additional_time_over(1)", "Invalid Id.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CT1).\n?????????? ??????????.");
            }

            int v2 = Integer.parseInt(aMsg[2]);
            int v1 = Integer.parseInt(aMsg[3]);
            Global.money = v1 + v2;
            GameScreen.lbAdditionalMoney.setText(String.valueOf(v1));
            GameScreen.lbAdditionalWin.setText(String.valueOf(v2));
            Res.aniAdditionalWLT.play(true, "O", 4);
            Res.aniAdditionalWLT.visible = true;
            this.bAdditionalWinCeremony = true;
            this.iAdditionalWinMoney = v2;
            this.fAdditionalWinCeremonyMoneyTimer = 0f;
            this.fAdditionalBeep = 0f;
            this.fEndAdditionalGameDelay = 1000000000f;
            if(Global.money >= this.iAdditionalBaseBet) {
                this.bAdditionalNeedNewGame = true;
            }

            this.bWaitingAdditionalGame = false;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_additional_time_over(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?? ????????(CT0).\n?????????? ??????????.");
        }
    }

    private void fn_announce(String[] aMsg) {  // has try-catch handlers
        String v1;
        String v4;
        String v2;
        int v7 = 4;
        try {
            v2 = "";
            if(aMsg != null) {
                goto label_3;
            }

            goto label_8;
        }
        catch(Exception v0) {
            goto label_27;
        }

        try {
        label_3:
            if(aMsg.length == 5) {
                v2 = aMsg[4];
            }

        label_8:
            if(!Global.id.equals(v2)) {
                goto label_14;
            }

            return;
        }
        catch(Exception v5) {
            goto label_14;
        }

        try {
        label_14:
            String v3 = aMsg[1];
            v4 = aMsg[2];
            if(!"M".equals(v3)) {
                goto label_29;
            }

            this.showMessage(v4);
            return;
        label_29:
            if(!"T".equals(v3)) {
                goto label_35;
            }

            this.showToast(v4, 2f);
            return;
        label_35:
            if(!"C".equals(v3)) {
                goto label_82;
            }

            v1 = "";
            goto label_39;
        }
        catch(Exception v0) {
            goto label_27;
        }

        try {
        label_39:
            if(aMsg.length < v7) {
                goto label_43;
            }

            v1 = aMsg[3];
            goto label_43;
        }
        catch(Exception v5) {
            goto label_43;
        }

        try {
        label_43:
            if(!"orange".equals(v1)) {
                goto label_50;
            }

            this.addMsg(v4, Color.ORANGE, 0);
            return;
        label_50:
            if(!"white".equals(v1)) {
                goto label_57;
            }

            this.addMsg(v4, Color.WHITE, 0);
            return;
        label_57:
            if(!"green".equals(v1)) {
                goto label_64;
            }

            this.addMsg(v4, Color.GREEN, 0);
            return;
        label_64:
            if(!"blue".equals(v1)) {
                goto label_71;
            }

            this.addMsg(v4, Color.BLUE, 0);
            return;
        label_71:
            if(!"red".equals(v1)) {
                goto label_78;
            }

            this.addMsg(v4, Color.RED, 0);
            return;
        label_78:
            this.addMsg(v4, Color.YELLOW, 0);
            return;
        label_82:
            Util.sysout("ERROR", "GameScreen->fn_announce(1)", "Invalid Announce Flag", Util.toString(aMsg));
        }
        catch(Exception v0) {
        label_27:
            Util.sysout("ERROR", "GameScreen->fn_announce(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_ask(String[] aMsg) {  // has try-catch handlers
        int v7 = 3;
        try {
            if(!GameScreen.bShowMenu) {
                this.addMsg("?????? ???? ?????? ????????????.", Color.GREEN, 0);
            }

            int v1;
            for(v1 = 0; v1 < this.iMaxAsk; ++v1) {
                this.saAsk[v1] = "";
            }

            this.iCurAsk = 0;
            this.addAsk("**** 1:1 ???? ????????????. ****", Color.ORANGE);
            int v2 = aMsg.length - 2;
            v1 = 1;
        }
        catch(Exception v0) {
            goto label_59;
        }

        while(true) {
            if(v1 < v2) {
                goto label_35;
            }

            goto label_19;
            try {
            label_35:
                if(aMsg[v1].length() <= v7 || !"??????".equals(aMsg[v1].substring(0, 3))) {
                    this.addAsk(aMsg[v1], Color.YELLOW);
                }
                else {
                    this.addAsk(aMsg[v1], Color.ORANGE);
                }

                ++v1;
                continue;
            }
            catch(Exception v0) {
                break;
            }
        }

        try {
        label_19:
            this.scpAsk.layout();
            this.scpAsk.scrollTo(0f, this.scpAsk.getMaxHeight(), 0f, this.scpAsk.getMaxHeight());
            return;
        }
        catch(Exception v3) {
            return;
        }

    label_59:
        Util.sysout("ERROR", "GameScreen->fn_ask(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
    }

    private void fn_bank(String[] aMsg) {  // has try-catch handlers
        try {
            if("".equals(aMsg[1])) {
                return;
            }

            if("".equals(aMsg[2])) {
                return;
            }

            if("".equals(aMsg[3])) {
                return;
            }

            this.lbChargeBank.setText(aMsg[1]);
            this.lbChargeOwner.setText(aMsg[2]);
            this.lbChargeAccount.setText(aMsg[3]);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_bank(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_charge_completed(String[] aMsg) {  // has try-catch handlers
        try {
            String v4 = aMsg[1];
            int v1 = Util.strToint(aMsg[2], -1);
            int v2 = Util.strToint(aMsg[3], -1);
            if((Global.id.equals(v4)) && v1 >= 0 && v2 >= 0) {
                goto label_22;
            }

            Util.sysout("ERROR", "GameScreen->fn_charge_completed(1)", "????????????????", Util.toString(aMsg));
            return;
        label_22:
            int v3 = Util.strToint(aMsg[4], -1);
            Global.money = v2;
            GameScreen.updateLabel();
            if(v3 == 1) {
                this.showToast("?????? ?????? " + String.valueOf(v1) + "?? ??????????????.", 1.5f);
            }
            else {
                this.showToast(String.valueOf(String.valueOf(v1)) + "???? ??????????????????.", 1.5f);
            }

            this.effectMoney(15);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_charge_completed", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_coin_info(String[] aMsg) {  // has try-catch handlers
        int v7;
        try {
            int v4 = Integer.parseInt(aMsg[1]);
            int v3 = Integer.parseInt(aMsg[3]);
            int v2 = Integer.parseInt(aMsg[4]);
            int v6 = Integer.parseInt(aMsg[5]);
            int v1 = Integer.parseInt(aMsg[11]);
            if(v3 != Global.machineNo) {
                Util.sysout("ERROR", "GameScreen->fn_coin_info(1)", "??????????????", String.valueOf(Util.toString(aMsg)) + "/Global.channel=" + "0" + "/Global.machine1=" + String.valueOf(Global.machineNo) + "/Global.machine2=" + "0");
                GameScreen.exitApp("?????????? ???????? ????????(CC1).\n?????????? ??????????.");
            }

            Global.money = v4;
            Global.spin = v6;
            Global.gift = v2;
            Global.bet = v1;
            v7 = 9;
            goto label_45;
        }
        catch(Exception v0) {
            goto label_56;
        }

        try {
        label_45:
            Global.reserve = Integer.parseInt(aMsg[v7]);
            this.showReserve();
            goto label_49;
        }
        catch(Exception v7_1) {
            goto label_49;
        }

        try {
        label_49:
            GameScreen.updateLabel();
        }
        catch(Exception v0) {
        label_56:
            Util.sysout("ERROR", "GameScreen->fn_coin_info(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ????????(CC).\n?????????? ??????????.");
        }
    }

    private void fn_confirm_reserve(String[] aMsg) {  // has try-catch handlers
        int v2;
        int v6;
        int v3;
        int v5;
        int v4;
        int v7;
        try {
            if(Global.id.equals(aMsg[1])) {
                goto label_11;
            }

            Util.sysout("ERROR", "GameScreen->fn_confirm_reserve(1)", "Id Dismatched.", Util.toString(aMsg));
            return;
        label_11:
            int v1 = Util.strToint(aMsg[3], -1);
            v7 = Integer.parseInt(aMsg[4]);
            v4 = Integer.parseInt(aMsg[5]);
            v5 = Integer.parseInt(aMsg[6]);
            v3 = Integer.parseInt(aMsg[7]);
            v6 = Integer.parseInt(aMsg[8]);
            v2 = Integer.parseInt(aMsg[9]);
            if(v4 == 0) {
                this.showToast("[" + String.valueOf(v1) + "]?? ?????? ??????????????.", 1.5f);
                if(Global.machineNo == v1) {
                    GameScreen.effectGift(15);
                    GameScreen.effectSpin(15);
                    this.effectNo(15);
                    this.effectMoney(15);
                    this.effectBet(15);
                }
                else {
                    goto label_71;
                }
            }

            goto label_55;
        }
        catch(Exception v0) {
            goto label_69;
        }

    label_71:
        int v9 = 15;
        try {
            this.effectMoney(v9);
        label_55:
            Global.reserve = v7;
            Global.machineNo = v4;
            Global.gift = v3;
            Global.spin = v6;
            Global.money = v5;
            Global.bet = v2;
            GameScreen.updateLabel();
            this.showReserve();
        }
        catch(Exception v0) {
        label_69:
            Util.sysout("ERROR", "GameScreen->fn_confirm_reserve(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_end_scenario_failure(String[] aMsg) {  // has try-catch handlers
        try {
            if(!Global.bTerminating) {
                goto label_3;
            }

            return;
        label_3:
            GameScreen.exitApp(String.valueOf(aMsg[1]) + "\n?????????? ??????????.");
            this.showReserve();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_end_scenario_failure(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?????? ????????????(CS).\n?????????? ??????????.");
        }
    }

    private void fn_end_scenario_success(String[] aMsg) {  // has try-catch handlers
        try {
            if(!Global.bTerminating) {
                goto label_3;
            }

            return;
        label_3:
            if(Global.id.equals(aMsg[1])) {
                goto label_25;
            }

            Util.sysout("ERROR", "GameScreen->fn_end_scenario_success(1)", "Invalid Id.", Util.toString(aMsg));
            GameScreen.exitApp("?????????? ?????? ?? ????????(CS1).\n?????????? ??????????.");
            return;
        }
        catch(Exception v0) {
            goto label_21;
        }

    label_25:
        int v5 = 2;
        try {
            int v3 = Util.strToint(aMsg[v5], 0);
            int v4 = Util.strToint(aMsg[3], -1);
            int v1 = Util.strToint(aMsg[4], -1);
            int v2 = Util.strToint(aMsg[5], -1);
            if(v3 > 0 && v4 >= 0 && v1 >= 0 && v2 >= 0) {
                goto label_53;
            }

            Util.sysout("ERROR", "GameScreen->fn_end_scenario_success(2)", "Invalid Msg.", Util.toString(aMsg));
            GameScreen.exitApp("?????????? ?????? ?? ????????(CS2).\n?????????? ??????????.");
            return;
        label_53:
            if(Global.machineNo == v3 && v3 > 0) {
                Global.spin = v4;
                Global.gift = v1;
                if(v2 > 0) {
                    GameScreen.effectGift(15);
                }

                this.bEnding = false;
                if(Global.betWait <= 0 && Global.bet <= 0 && Global.spin <= 0 && Global.reserve <= 0 && Global.machineNo > 0) {
                    GameScreen.socketWrite("G080\t" + Global.id + "\t" + "0" + "\t" + String.valueOf(Global.machineNo) + "\t" + "1" + "\t" + "180");
                    GameScreen.effectSpin(15);
                }

                GameScreen.updateLabel();
                this.showReserve();
                this.fNextScenarioDelay = 0.3f;
                return;
            }

            Util.sysout("ERROR", "GameScreen->fn_end_scenario_success(3)", "Invalid MachineNo", Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ?? ?????? ????????????(CS3).\n?????????? ??????????.");
        }
        catch(Exception v0) {
        label_21:
            Util.sysout("ERROR", "GameScreen->fn_end_scenario_success(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ?? ?????? ????????????(CS).\n?????????? ??????????.");
        }
    }

    private void fn_exchange_completed(String[] aMsg) {  // has try-catch handlers
        try {
            String v4 = aMsg[1];
            int v1 = Util.strToint(aMsg[2], -1);
            int v2 = Util.strToint(aMsg[3], -1);
            if((Global.id.equals(v4)) && v1 >= 0 && v2 >= 0) {
                goto label_22;
            }

            Util.sysout("ERROR", "GameScreen->fn_exchange_completed(1)", "????????????????", Util.toString(aMsg));
            return;
        label_22:
            int v3 = Util.strToint(aMsg[4], -1);
            Global.money = v2;
            GameScreen.updateLabel();
            if(v3 == 1) {
                this.showToast("?????????? ??????????????.", 1.5f);
            }
            else {
                this.showToast("?????????? ??????????????.", 1.5f);
            }

            this.effectMoney(15);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_exchange_completed", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_game_info(String[] aMsg) {  // has try-catch handlers
        int v1;
        int v2 = 3;
        try {
            Global.mode = v2;
            Global.money = Integer.parseInt(aMsg[2]);
            Global.machineNo = Integer.parseInt(aMsg[4]);
            Global.gift = Integer.parseInt(aMsg[5]);
            Global.spin = Integer.parseInt(aMsg[6]);
            Global.cls = aMsg[10];
            Global.reserve = Integer.parseInt(aMsg[11]);
            this.getCardPos(aMsg[15]);
            Global.errorReport = aMsg[17];
            Global.bank = aMsg[18];
            Global.owner = aMsg[19];
            Global.account = aMsg[20];
            this.lbChargeBank.setText(Global.bank);
            this.lbChargeOwner.setText(Global.owner);
            this.lbChargeAccount.setText(Global.account);
            Global.viewScenario = aMsg[21];
            Global.bet = Integer.parseInt(aMsg[22]);
            Global.betWait = 0;
            v2 = 23;
            goto label_59;
        }
        catch(Exception v0) {
            goto label_117;
        }

        try {
        label_59:
            Item.band = aMsg[v2];
            v1 = 0;
        }
        catch(Exception v0) {
            goto label_106;
        }

        while(true) {
            if(v1 < 39) {
                goto label_99;
            }

            break;
            try {
            label_99:
                GameScreen.item[v1].setNo(v1 + 1);
                ++v1;
                continue;
            }
            catch(Exception v0) {
                goto label_106;
            }
        }

        goto label_65;
        try {
        label_106:
            Util.sysout("ERROR-ItemBelt", "GameScreen->fn_game_info_failure(1)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        label_65:
            this.bGameScreenReady = true;
            this.bCanInsert10000 = true;
            this.bCanSwitchMoney = true;
            this.bCanUpDownChannel = true;
            this.bCanSelectMachine = true;
            this.bCanReserveMachine = true;
            this.bEnding = false;
            this.bWaitingScenario = false;
            this.bCanRequestScenario = true;
            GameScreen.fWelcomeCelemony = 0f;
            GameScreen.bWelcomeCelemony = false;
            this.fWinCelemonyTime = 0f;
            this.fNextScenarioDelay = 4f;
            this.fEndScenarioDelay = 1000000000f;
            GameScreen.updateLabel();
            this.showReserve();
            this.iBackgroundMusic = 1;
            Snd.sndSeaBack1.play();
        }
        catch(Exception v0) {
        label_117:
            Util.sysout("ERROR", "GameScreen->fn_game_info(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ?? ?????? ????????????(CI).\n?????????? ??????????.");
        }
    }

    private void fn_game_info_failure(String[] aMsg) {  // has try-catch handlers
        try {
            GameScreen.exitApp(String.valueOf(aMsg[1]) + "\n?????????? ??????????.");
            this.showReserve();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_game_info_failure", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ?? ?????? ????????????(2).\n?????????? ??????????.");
        }
    }

    private void fn_insert_10000_failure(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanInsert10000 = true;
            this.showToast(aMsg[1], 2f);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_insert_10000_failure(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?? ?????? ????????????(CM).\n?????????? ??????????.");
        }
    }

    private void fn_insert_10000_success(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanInsert10000 = true;
            int v2 = Util.strToint(aMsg[1], 0);
            if(v2 > 0 && v2 == Global.machineNo) {
                Global.gift = Integer.parseInt(aMsg[5]);
                Global.spin = Integer.parseInt(aMsg[6]);
                Global.bet = Integer.parseInt(aMsg[12]);
                Global.money = Integer.parseInt(aMsg[2]);
                this.showToast("[" + String.valueOf(v2) + "] ?? ??????\n?????? ??????????????.", 1.5f);
                GameScreen.updateLabel();
                this.effectBet(15);
                if(Global.reserve <= 0) {
                    return;
                }
                else if(!this.bCanReserveMachine) {
                    return;
                }
                else if(!GameScreen.bReserving) {
                    this.bCanReserveMachine = false;
                    GameScreen.bReserving = true;
                    GameScreen.socketWrite("G080\t" + Global.id + "\t" + String.valueOf(0) + "\t" + String.valueOf(v2) + "\t" + "0" + "\t" + "0");
                    return;
                }
                else {
                    return;
                }
            }

            Util.sysout("ERROR", "GameScreen->fn_insert_10000_success(1)", "????????????", Util.toString(aMsg));
            GameScreen.exitApp("???????? ?? ?????? ????????????(CM1).\n?????????? ??????????.");
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_insert_10000_success(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?? ?????? ????????????(CM).\n?????????? ??????????.");
        }
    }

    private void fn_machine_list(String[] aMsg) {  // has try-catch handlers
        String[] v0;
        int v5 = 3;
        int v8 = 12;
        try {
            this.bCanUpDownChannel = true;
            if(aMsg.length >= v5) {
                goto label_15;
            }

            Util.sysout("ERROR", "GameScreen->fn_machine_list", "???? ?????? ?????? ???? ?? ????????.", Util.toString(aMsg));
            this.showMessage("???? ?????? ???????? ??????????(CM1).");
            return;
        label_15:
            int v3;
            for(v3 = 0; v3 < v8; ++v3) {
                this.iMachineNo[v3] = 0;
                this.iMachineState[v3] = 0;
                this.iMachineYesterday[v3] = 0;
                this.iMachineToday[v3] = 0;
            }

            Global.division = Integer.valueOf(aMsg[1]).intValue();
            v0 = aMsg[2].split(":");
            v3 = 0;
            while(true) {
            label_27:
                if(v3 < v8) {
                    goto label_72;
                }

                goto label_28;
            }
        }
        catch(Exception v2) {
            goto label_111;
        }

        try {
        label_72:
            String[] v1 = v0[v3].split("/");
            this.iMachineNo[v3] = Integer.parseInt(v1[0]);
            this.iMachineState[v3] = Integer.parseInt(v1[1]);
            this.iMachineYesterday[v3] = Integer.parseInt(v1[2]);
            this.iMachineToday[v3] = Integer.parseInt(v1[3]);
            ++v3;
            goto label_27;
        }
        catch(Exception v2) {
            goto label_28;
        }

        try {
        label_28:
            for(v3 = 0; v3 < v8; ++v3) {
                if(this.iMachineNo[v3] == 0) {
                    this.lbMachineNo[v3].setText("");
                }
                else {
                    this.lbMachineNo[v3].setText(String.valueOf(this.iMachineNo[v3]));
                }

                if(this.iMachineNo[v3] <= 0 || this.iMachineNo[v3] != Global.machineNo) {
                    Res.machineBack[v3].setImage(String.valueOf(this.iMachineState[v3]));
                }
                else {
                    Res.machineBack[v3].setImage("3");
                }

                if(this.iMachineState[v3] == 1) {
                    Res.machineScreen[v3].play(true, "1");
                }
                else {
                    Res.machineScreen[v3].play(true, "0");
                }
            }

            return;
        }
        catch(Exception v2) {
        label_111:
            Util.sysout("ERROR", "GameScreen->fn_machine_list", Util.getExceptionMessage(v2), Util.toString(aMsg));
            this.showMessage("???? ?????? ???????? ??????????(CM).");
            return;
        }
    }

    private void fn_machine_select(String[] aMsg) {  // has try-catch handlers
        int v5 = 6000;
        int v4 = 4000;
        int v3 = 2000;
        try {
            this.bCanSelectMachine = true;
            Global.machineNo = Integer.parseInt(aMsg[2]);
            Global.gift = Integer.parseInt(aMsg[3]);
            Global.spin = Integer.parseInt(aMsg[4]);
            Global.reserve = Integer.parseInt(aMsg[5]);
            Global.bet = 0;
            Global.betWait = 0;
            int v1 = Integer.parseInt(aMsg[8]);
            if(v1 < 0 || v1 >= v3) {
                if(v1 >= v3 && v1 < v4) {
                    Res.aniPostLeft.play(true, "1000");
                    Res.aniPostRight.play(true, "1000");
                    goto label_38;
                }

                goto label_68;
            }
            else {
                Res.aniPostLeft.play(true, "0");
                Res.aniPostRight.play(true, "0");
            }

            goto label_38;
        }
        catch(Exception v0) {
            goto label_64;
        }

    label_68:
        if(v1 >= v4 && v1 < v5) {
            try {
                Res.aniPostLeft.play(true, "2000");
                Res.aniPostRight.play(true, "2000");
                goto label_38;
            label_79:
                if(v1 >= v5 && v1 < 8000) {
                    Res.aniPostLeft.play(true, "3000");
                    Res.aniPostRight.play(true, "3000");
                    goto label_38;
                }

                if(v1 < 8000) {
                    goto label_102;
                }

                Res.aniPostLeft.play(true, "4000");
                Res.aniPostRight.play(true, "4000");
                goto label_38;
            label_102:
                Util.sysout("ERROR", "GameScreen->fn_machine_select(1)", "Invalid Lost.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?? ?????? ????????????(CM1).\n?????????? ??????????.");
            label_38:
                this.getCardPos(aMsg[7]);
                this.lsScenario.clear();
                this.effectNo(15);
                GameScreen.updateLabel();
                this.showReserve();
                return;
            }
            catch(Exception v0) {
            label_64:
                Util.sysout("ERROR", "GameScreen->fn_machine_select", Util.getExceptionMessage(v0), Util.toString(aMsg));
                GameScreen.exitApp("???????? ?? ?????? ????????????(CM).\n?????????? ??????????.");
                return;
            }
        }

        goto label_79;
    }

    private void fn_machine_select_failure(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanSelectMachine = true;
            this.showMessage(aMsg[1]);
            GameScreen.updateLabel();
            this.showReserve();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_machine_select", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?? ?????? ????????????(CM).\n?????????? ??????????.");
        }
    }

    private void fn_private_announce(String[] aMsg) {  // has try-catch handlers
        try {
            String v2 = aMsg[1];
            String v1 = aMsg[2];
            String v3 = aMsg[3];
            if(Global.id.equals(v2)) {
                goto label_15;
            }

            Util.sysout("ERROR", "GameScreen->fn_private_announce(1)", "Invalid Id", Util.toString(aMsg));
            return;
        label_15:
            if(!"M".equals(v1)) {
                goto label_27;
            }

            this.showMessage(v3);
            return;
        label_27:
            if(!"T".equals(v1)) {
                goto label_33;
            }

            this.showToast(v3, 3f);
            return;
        label_33:
            if(!"C".equals(v1)) {
                goto label_40;
            }

            this.addMsg(v3, Color.GREEN, 0);
            return;
        label_40:
            Util.sysout("ERROR", "GameScreen->fn_private_announce(1)", "Invalid Announce Flag", Util.toString(aMsg));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_private_announce(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_report(String[] aMsg) {  // has try-catch handlers
        try {
            String v1 = "";
            if(this.lsScenario.size() > 0) {
                v1 = String.valueOf(v1) + "lsScenario[0]=" + this.lsScenario.get(0);
            }

            GameScreen.socketWrite("G001\t" + (String.valueOf(v1) + "/Global.machineNo[]=" + String.valueOf(Global.machineNo) + ":" + "/Global.spin[]=" + String.valueOf(Global.spin) + ":" + "/fNextScenarioDelay[]=" + String.valueOf(this.fNextScenarioDelay) + ":" + "/isReelRunning()=" + String.valueOf(this.isReelRunning()) + ":" + "/Global.reserve[]=" + String.valueOf(Global.reserve) + ":" + "/Global.bAskingGameExit=" + String.valueOf(Global.bAskingGameExit) + ":" + "/Global.bTerminating=" + String.valueOf(Global.bTerminating) + ":" + "/Global.bTerminating=" + String.valueOf(Global.bTerminating) + ":" + "/bWelcomeCelemony=" + String.valueOf(GameScreen.bWelcomeCelemony) + ":" + "/fWinCelemonyTime=" + String.valueOf(this.fWinCelemonyTime) + ":" + "/bEnding[iChannel]=" + String.valueOf(this.bEnding) + ":" + "/bWaitingScenario[iChannel]=" + String.valueOf(this.bWaitingScenario) + ":" + "/bStopScenarioForTest=" + String.valueOf(this.bStopScenarioForTest) + ":" + "/iFireType[]=" + String.valueOf(this.iFireType) + ":" + "/bReserving=" + String.valueOf(GameScreen.bReserving) + ":" + "/ reelMode=" + String.valueOf(this.reelMode) + ":" + "/fEndScenarioDelay[]=" + String.valueOf(this.fEndScenarioDelay) + ":" + "/iMaxPopCount[][0]=" + String.valueOf(this.iMaxPopCount[0]) + ":" + "/iMaxPopCount[][1]=" + String.valueOf(this.iMaxPopCount[1]) + ":" + "/iMaxPopCount[][2]=" + String.valueOf(this.iMaxPopCount[2]) + ":" + "/iPopCount[][0]=" + String.valueOf(this.iPopCount[0]) + ":" + "/iPopCount[][1]=" + String.valueOf(this.iPopCount[1]) + ":" + "/iPopCount[][2]=" + String.valueOf(this.iPopCount[2]) + ":" + "/fReelDeceleratingStartTime[]=" + String.valueOf(this.fReelDeceleratingStartTime[0]) + ":" + "/fReelDeceleratingStartTime[]=" + String.valueOf(this.fReelDeceleratingStartTime[1]) + ":" + "/fReelDeceleratingStartTime[]=" + String.valueOf(this.fReelDeceleratingStartTime[2]) + ":" + "/fReelPoppingStartTime[]=" + String.valueOf(this.fReelPoppingStartTime[0]) + ":" + "/fReelPoppingStartTime[]=" + String.valueOf(this.fReelPoppingStartTime[1]) + ":" + "/fReelPoppingStartTime[]=" + String.valueOf(this.fReelPoppingStartTime[2]) + ":" + "/fTotalReelElapseTime[]=" + String.valueOf(GameScreen.fTotalReelElapseTime) + ":" + "/fStartReelDelayTime[][0]=" + String.valueOf(this.fStartReelDelayTime[0]) + ":" + "/fStartReelDelayTime[][1]=" + String.valueOf(this.fStartReelDelayTime[1]) + ":" + "/fStartReelDelayTime[][2]=" + String.valueOf(this.fStartReelDelayTime[2]) + ":" + "/fTotalReelElapseTime[]=" + String.valueOf(GameScreen.fTotalReelElapseTime) + ":" + "/fTotalReelElapseTime[]=" + String.valueOf(GameScreen.fTotalReelElapseTime) + ":" + "/fTotalReelElapseTime[]=" + String.valueOf(GameScreen.fTotalReelElapseTime) + ":" + "/fTotalReelElapseTime[]=" + String.valueOf(GameScreen.fTotalReelElapseTime)));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_report(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_reserve_machine_failure(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanReserveMachine = true;
            GameScreen.bReserving = false;
            this.showToast(aMsg[1], 2f);
            this.showReserve();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_reserve_machine_failure(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_reserve_machine_success(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanReserveMachine = true;
            GameScreen.bReserving = false;
            if(!Global.id.equals(aMsg[1])) {
                this.showToast("???? ???????? ???????? ????????.", 2f);
            }

            if(Global.machineNo != Integer.parseInt(aMsg[3])) {
                this.showToast("???? ?????????? ???????? ????????.", 2f);
            }

            Global.reserve = Integer.parseInt(aMsg[5]);
            if(Util.strToint(aMsg[8], 0) <= 0) {
                if("1".equals(aMsg[7])) {
                    this.showToast("?????? ??????????????.", 1f);
                }
                else if("0".equals(aMsg[7])) {
                    this.showToast("???? ?????? ??????????????.", 1f);
                }
            }

            this.showReserve();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_reserve_machine_success(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_scenario1(String[] aMsg) {  // has try-catch handlers
        try {
            this.lsScenario.clear();
            if(Integer.parseInt(aMsg[1]) > 0) {
                goto label_7;
            }

            return;
        label_7:
            if(aMsg.length < 3) {
                return;
            }

            String[] v3 = aMsg[2].split("!");
            int v1;
            for(v1 = 0; v1 < v3.length; ++v1) {
                this.lsScenario.add(v3[v1]);
            }

            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_scenario1(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            return;
        }
    }

    private void fn_scenario_failure(String[] aMsg) {  // has try-catch handlers
        try {
            GameScreen.exitApp(String.valueOf(aMsg[1]) + "\n?????????? ??????????.");
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_scenario_failure(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???????? ?? ?????? ????????????(CS).\n?????????? ??????????.");
        }
    }

    private void fn_scenario_success(String[] aMsg) {  // has try-catch handlers
        try {
            int v2 = Integer.parseInt(aMsg[1]);
            int v3 = Integer.parseInt(aMsg[2]);
            if(v2 == 0) {
                goto label_15;
            }

            Util.sysout("ERROR", "GameScreen->fn_scenario_success(1)", "Invalid Channel.", Util.toString(aMsg));
            GameScreen.exitApp("?????? ???? ?? ?? ????????(CS1).\n?????????? ??????????.");
            return;
        label_15:
            if(Global.machineNo == v3) {
                goto label_34;
            }

            Util.sysout("ERROR", "GameScreen->fn_scenario_success(2)", "Invalid MachineNo.", Util.toString(aMsg));
            GameScreen.exitApp("?????????? ???? ?? ?? ????????(CS2).\n?????????? ??????????.");
            return;
        }
        catch(Exception v0) {
            goto label_30;
        }

    label_34:
        int v6 = 3;
        try {
            String v5 = aMsg[v6];
            if(!"".equals(v5) && v5 != null) {
                goto label_48;
            }

            Util.sysout("ERROR", "GameScreen->fn_scenario_success(3)", "Invalid MachineNo.", Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?????? ????????????(CS3).\n?????????? ??????????.");
            return;
        label_48:
            String[] v4 = v5.split("!");
            int v1;
            for(v1 = 0; v1 < v4.length; ++v1) {
                this.lsScenario.add(v4[v1]);
            }

            this.bCanRequestScenario = true;
            this.bWaitingScenario = false;
            return;
        }
        catch(Exception v0) {
        label_30:
            Util.sysout("ERROR", "GameScreen->fn_scenario_success(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?????? ?????? ????????????(CS).\n?????????? ??????????.");
            return;
        }
    }

    private void fn_soojo_info(String[] aMsg) {  // has try-catch handlers
        int v3;
        int v10 = 6000;
        int v9 = 4000;
        int v8 = 2000;
        try {
            if(!Global.id.equals(aMsg[1])) {
                Util.sysout("ERROR", "GameScreen->fn_soojo_info(1)", "Invalid Id.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CSJ1).\n?????????? ??????????.");
            }

            int v2 = Integer.parseInt(aMsg[2]);
            int v1 = Integer.parseInt(aMsg[3]);
            v3 = Integer.parseInt(aMsg[4]);
            if(Global.machineNo != v2) {
                Util.sysout("ERROR", "GameScreen->fn_soojo_info(2)", "Invalid Id.", Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CSJ2).\n?????????? ??????????.");
            }

            if(v1 < 0 || v1 >= v8) {
                if(v1 >= v8 && v1 < v9) {
                    Res.aniPostLeft.play(true, "1000");
                    Res.aniPostRight.play(true, "1000");
                    goto label_43;
                }

                goto label_80;
            }
            else {
                Res.aniPostLeft.play(true, "0");
                Res.aniPostRight.play(true, "0");
            }

            goto label_43;
        }
        catch(Exception v0) {
            goto label_76;
        }

    label_80:
        if(v1 >= v9 && v1 < v10) {
            try {
                Res.aniPostLeft.play(true, "2000");
                Res.aniPostRight.play(true, "2000");
                goto label_43;
            label_91:
                if(v1 >= v10 && v1 < 8000) {
                    Res.aniPostLeft.play(true, "3000");
                    Res.aniPostRight.play(true, "3000");
                    goto label_43;
                }

                if(v1 < 8000) {
                    goto label_43;
                }

                Res.aniPostLeft.play(true, "4000");
                Res.aniPostRight.play(true, "4000");
            label_43:
                if(v3 <= 0) {
                    return;
                }

                Res.aniPostLeft.play(true, "5000", 2);
                Res.aniPostRight.play(true, "5000", 2);
                Global.gift += v3;
                GameScreen.effectGift(15);
                return;
            }
            catch(Exception v0) {
            label_76:
                Util.sysout("ERROR", "GameScreen->fn_soojo_info(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
                GameScreen.exitApp("???????? ?????? ?? ????????(CSJ0).\n?????????? ??????????.");
                return;
            }
        }

        goto label_91;
    }

    private void fn_switch_money_failure(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanSwitchMoney = true;
            this.showToast(aMsg[1], 2f);
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_switch_money_failure", Util.getExceptionMessage(v0), Util.toString(aMsg));
        }
    }

    private void fn_switch_money_success(String[] aMsg) {  // has try-catch handlers
        try {
            this.bCanSwitchMoney = true;
            Global.money = Integer.parseInt(aMsg[3]);
            Global.gift = Integer.parseInt(aMsg[6]);
            int v1 = Util.strToint(aMsg[1], 0);
            if(v1 > 0) {
                this.showToast("[" + v1 + "] ?? ?????? ???????? ??????????????.", 2f);
            }
            else {
                this.showToast("???????? ?????????? ??????????????.", 2f);
            }

            GameScreen.effectGift(15);
            this.effectMoney(15);
            GameScreen.updateLabel();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->fn_switch_money_success(0)", Util.getExceptionMessage(v0), Util.toString(aMsg));
            GameScreen.exitApp("???????? ?? ?????? ????????????(CW).\n?????????? ??????????.");
        }
    }

    private int getCardIdxOnReachLine(int iReelIndex, int iCTB) {
        float v0;
        int v10 = 4;
        int v9 = 3;
        int v8 = 2;
        int v2 = 0;
        if(iCTB == v8) {
            v0 = Res.fBaseLineY4;
        }
        else if(iCTB == 1) {
            v0 = Res.fBaseLineY3;
        }
        else {
            v0 = Res.fBaseLineY2;
        }

        float[] v1 = new float[5];
        v1[0] = Math.abs(this.card[iReelIndex][0].y - v0);
        v1[1] = Math.abs(this.card[iReelIndex][1].y - v0);
        v1[v8] = Math.abs(this.card[iReelIndex][v8].y - v0);
        v1[v9] = Math.abs(this.card[iReelIndex][v9].y - v0);
        v1[v10] = Math.abs(this.card[iReelIndex][v10].y - v0);
        float[] v3 = new float[5];
        v3[0] = v1[0];
        v3[1] = v1[1];
        v3[v8] = v1[v8];
        v3[v9] = v1[v9];
        v3[v10] = v1[v10];
        Arrays.sort(v1);
        if(v3[0] == v1[0]) {
            v2 = 0;
        }
        else if(v3[1] == v1[0]) {
            v2 = 1;
        }
        else if(v3[v8] == v1[0]) {
            v2 = 2;
        }
        else if(v3[v9] == v1[0]) {
            v2 = 3;
        }
        else if(v3[v10] == v1[0]) {
            v2 = 4;
        }
        else {
            Util.sysout("ERROR", "GameScreen->getCardIdxOnReachLine", "Can\'t find the card");
        }

        return v2;
    }

    private void getCardPos(String sPos) {  // has try-catch handlers
        if(sPos == null) {
            sPos = "";
        }

        sPos = sPos.trim();
        if(sPos.length() != 8) {
            sPos = "T1T1T1T1";
        }

        Global.cardPos = sPos;
        String[] v5 = new String[4];
        String[] v4 = new String[4];
        int[] v2 = new int[4];
        int v1;
        for(v1 = 0; true; ++v1) {
            if(v1 < 4) {
                goto label_95;
            }

            goto label_18;
            try {
            label_95:
                v5[v1] = sPos.substring(v1 * 2, v1 * 2 + 1);
                v4[v1] = sPos.substring(v1 * 2 + 1, v1 * 2 + 2);
                v2[v1] = this.posToNo(v4[v1]);
                if(v2[v1] >= 1 && v2[v1] <= 18) {
                    goto label_125;
                }

                goto label_114;
            }
            catch(Exception v0) {
                goto label_123;
            }

        label_125:
        }

        try {
        label_114:
            Util.sysout("ERROR", "GameScreen->getCardPos(2)", "Invalid target pos.", sPos);
            return;
        label_18:
            if((("T".equals(sPos.subSequence(0, 1))) || ("C".equals(sPos.subSequence(0, 1))) || ("B".equals(sPos.subSequence(0, 1)))) && (("T".equals(sPos.subSequence(2, 3))) || ("C".equals(sPos.subSequence(2, 3))) || ("B".equals(sPos.subSequence(2, 3)))) && (("T".equals(sPos.subSequence(4, 5))) || ("C".equals(sPos.subSequence(4, 5))) || ("B".equals(sPos.subSequence(4, 5)))) && (("T".equals(sPos.subSequence(6, 7))) || ("C".equals(sPos.subSequence(6, 7))) || ("B".equals(sPos.subSequence(6, 7))))) {
                goto label_127;
            }

            Util.sysout("ERROR", "GameScreen->getCardPos(3)", "Invalid target pos.", sPos);
            return;
        }
        catch(Exception v0) {
            goto label_123;
        }

        try {
        label_127:
            this.card[0][3].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY5);
            this.card[0][2].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY4);
            this.card[0][1].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY3);
            this.card[0][0].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY2);
            this.card[0][4].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY1);
            this.card[1][3].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY5);
            this.card[1][2].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY4);
            this.card[1][1].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY3);
            this.card[1][0].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY2);
            this.card[1][4].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY1);
            this.card[2][3].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY5);
            this.card[2][2].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY4);
            this.card[2][1].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY3);
            this.card[2][0].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY2);
            this.card[2][4].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY1);
            this.card[3][3].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY5);
            this.card[3][2].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY4);
            this.card[3][1].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY3);
            this.card[3][0].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY2);
            this.card[3][4].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY1);
            int v3;
            for(v3 = 0; v3 < 4; ++v3) {
                this.iNextBandPos[v3] = (v2[v3] + 3) % 18;
                if(this.iNextBandPos[v3] == 0) {
                    this.iNextBandPos[v3] = 18;
                }

                this.card[v3][3].setNo(v2[v3] + 2);
                this.card[v3][2].setNo(v2[v3] + 1);
                this.card[v3][1].setNo(v2[v3]);
                this.card[v3][0].setNo(v2[v3] - 1);
                this.card[v3][4].setNo(v2[v3] - 2);
            }
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->getCardPos(0)", Util.getExceptionMessage(v0), sPos);
        }

        return;
    label_123:
        Util.sysout("ERROR", "GameScreen->getCardPos(4)", "Invalid target pos.", sPos);
    }

    public void getMsgFromOuter(String param) {
    }

    private float getNearestBaseY(float ypos) {
        int v9 = 4;
        int v8 = 3;
        int v7 = 2;
        float v1 = 0f;
        float[] v0 = new float[6];
        v0[5] = Math.abs(ypos - Res.fBaseLineY5);
        v0[v9] = Math.abs(ypos - Res.fBaseLineY4);
        v0[v8] = Math.abs(ypos - Res.fBaseLineY3);
        v0[v7] = Math.abs(ypos - Res.fBaseLineY2);
        v0[1] = Math.abs(ypos - Res.fBaseLineY1);
        v0[0] = Math.abs(ypos - Res.fBaseLineY0);
        float[] v2 = new float[6];
        v2[5] = v0[5];
        v2[v9] = v0[v9];
        v2[v8] = v0[v8];
        v2[v7] = v0[v7];
        v2[1] = v0[1];
        v2[0] = v0[0];
        Arrays.sort(v0);
        if(v2[0] == v0[0]) {
            v1 = Res.fBaseLineY0;
        }
        else if(v2[1] == v0[0]) {
            v1 = Res.fBaseLineY1;
        }
        else if(v2[v7] == v0[0]) {
            v1 = Res.fBaseLineY2;
        }
        else if(v2[v8] == v0[0]) {
            v1 = Res.fBaseLineY3;
        }
        else if(v2[v9] == v0[0]) {
            v1 = Res.fBaseLineY4;
        }
        else if(v2[5] == v0[0]) {
            v1 = Res.fBaseLineY5;
        }
        else {
            Util.sysout("ERROR", "GameScreen->getStopTargetYPos", "Can\'t find the closest base line.");
        }

        return v1;
    }

    private float getStopTargetYPos(int iReelIndex, float ypos) {
        int v9 = 4;
        int v8 = 3;
        int v7 = 2;
        float v1 = 0f;
        float[] v0 = new float[6];
        v0[5] = Math.abs(ypos - Res.fBaseLineY5);
        v0[v9] = Math.abs(ypos - Res.fBaseLineY4);
        v0[v8] = Math.abs(ypos - Res.fBaseLineY3);
        v0[v7] = Math.abs(ypos - Res.fBaseLineY2);
        v0[1] = Math.abs(ypos - Res.fBaseLineY1);
        v0[0] = Math.abs(ypos - Res.fBaseLineY0);
        float[] v2 = new float[6];
        v2[5] = v0[5];
        v2[v9] = v0[v9];
        v2[v8] = v0[v8];
        v2[v7] = v0[v7];
        v2[1] = v0[1];
        v2[0] = v0[0];
        Arrays.sort(v0);
        if(v2[0] == v0[0]) {
            v1 = Res.fBaseLineY0;
        }
        else if(v2[1] == v0[0]) {
            v1 = Res.fBaseLineY1;
        }
        else if(v2[v7] == v0[0]) {
            v1 = Res.fBaseLineY2;
        }
        else if(v2[v8] == v0[0]) {
            v1 = Res.fBaseLineY3;
        }
        else if(v2[v9] == v0[0]) {
            v1 = Res.fBaseLineY4;
        }
        else if(v2[5] == v0[0]) {
            v1 = Res.fBaseLineY5;
        }
        else {
            Util.sysout("ERROR", "GameScreen->getStopTargetYPos", "Can\'t find the closest base line.");
        }

        return v1;
    }

    private String getValue(String _key, String _data) {  // has try-catch handlers
        String v3 = "";
        try {
            int v2 = _data.indexOf("@" + _key + ":");
            if(v2 < 0) {
                goto label_16;
            }

            v3 = _data.substring(v2 + 2 + _key.length(), _data.indexOf("@", v2 + 1));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->getValue", Util.getExceptionMessage(v0), "key=" + _key + "/data=" + _data);
        }

    label_16:
        return v3;
    }

    public void hide() {
        Snd.stopSeaSound();
        Snd.stopAddSound();
    }

    private void ini() {
        Dialog.count = 0;
        this.fToastTime = 0f;
        this.bShowToast = false;
        Global.bTerminating = false;
        Global.bAskingGameExit = false;
        this.lsScenario.clear();
        GameScreen.fTotalReelElapseTime = 0f;
        GameScreen.iReserveConfirmTick = 0;
        this.fNextScenarioDelay = 1000000000f;
        this.fEndScenarioDelay = 1000000000f;
        this.fFishInterval = 0f;
        this.reelMode = 0;
        GameScreen.bShowMenu = false;
        this.iFireType = 0;
        this.iChatFlag = 1;
        this.elapseTime = 0f;
        this.bGameScreenReady = false;
        this.bAdditionalGame = false;
        this.bAdditionalNeedNewGame = false;
        this.bWaitingAdditionalGame = false;
        this.fAdditionalNewGameBtnDelay = 1000000000f;
        this.iAdditionalBaseBet = 0;
        this.iAdditionalStep = 0;
        this.iAdditionalTimer = 999999999;
        this.fAdditionalTimer = 1000000000f;
        this.bAddHurryUp1 = false;
        this.bAddHurryUp2 = false;
        this.bAddSexySnd1 = false;
        this.bAddSexySnd2 = false;
        this.bAddSexySnd3 = false;
        this.iAdditionalMultiple = 0;
        this.fAdditionalBaseBetTimer = 1000000000f;
        this.iAdditionalLady = 0;
        this.bAdditionalWinCeremony = false;
        this.iAdditionalWinMoney = 0;
        this.fAdditionalWinCeremonyMoneyTimer = -1000000000f;
        this.fAdditionalBeep = -1000000000f;
        this.fEndAdditionalGameDelay = 1000000000f;
        this.bCanInsert10000 = false;
        this.bCanSwitchMoney = false;
        this.bCanUpDownChannel = false;
        this.bCanSelectMachine = false;
        this.bCanReserveMachine = false;
        GameScreen.bReserving = false;
        this.bWaitingScenario = true;
        this.bCanRequestScenario = false;
        this.fScenarioInterval = 0f;
        this.fPlusWinMoneyShowDelay = 0f;
        this.bReelRunning[0] = false;
        this.bReelRunning[1] = false;
        this.bReelRunning[2] = false;
        this.bReelRunning[3] = false;
        this.fReelDeceleratingStartTime[0] = 1000000000f;
        this.fReelDeceleratingStartTime[1] = 1000000000f;
        this.fReelDeceleratingStartTime[2] = 1000000000f;
        this.fReelDeceleratingStartTime[3] = 1000000000f;
        this.fReelPoppingStartTime[0] = 1000000000f;
        this.fReelPoppingStartTime[1] = 1000000000f;
        this.fReelPoppingStartTime[2] = 1000000000f;
        this.fReelPoppingStartTime[3] = 1000000000f;
        this.iBackgroundDirection = 1;
        this.iDropperDirection = 1;
        this.iWinTableTopContainer = 1;
        this.iWinTableBottomContainer = 1;
        this.fsBackgroundDayNight = "D";
        this.fDayNightAlpha = 0f;
        this.fiDayNightFlag = 0;
        this.fDropCoinTimer = 0f;
        GameScreen.iGiftFishIdx = 0;
        GameScreen.iSpinAllIdx = 0;
        this.fiBackLight = 0;
        this.fBackLightAlpha = 0f;
        this.fBackLightTimer = 0f;
    }

    private void iniCallback() {  // has try-catch handlers
        Class[] v1;
        try {
            v1 = new Class[1];
            v1[0] = String.class;
            goto label_5;
        }
        catch(Exception v0) {
            goto label_24;
        }

        try {
        label_5:
            this.mCallbackFromOuter = GameScreen.class.getMethod("getMsgFromOuter", v1);
            GameScreen.game.setGameScreenCallback(this, this.mCallbackFromOuter);
        }
        catch(Exception v0) {
            try {
                Util.sysout("ERROR", "GameScreen->iniCallback(1)", Util.getExceptionMessage(v0), "mCallbackFromOuter");
            }
            catch(Exception v0) {
            label_24:
                Util.sysout("ERROR", "GameScreen->iniCallback(2)", Util.getExceptionMessage(v0));
            }
        }
    }

    private void iniCards() {
        if(this.card[0][3] == null) {
            this.card[0][3] = new Card(25, 0.5f, 235, 0, 4, 3);
        }
        else {
            this.card[0][3].setNo(4);
        }

        if(this.card[0][2] == null) {
            this.card[0][2] = new Card(25, 0.5f, 235, 0, 3, 2);
        }
        else {
            this.card[0][2].setNo(3);
        }

        if(this.card[0][1] == null) {
            this.card[0][1] = new Card(25, 0.5f, 235, 0, 2, 1);
        }
        else {
            this.card[0][1].setNo(2);
        }

        if(this.card[0][0] == null) {
            this.card[0][0] = new Card(25, 0.5f, 235, 0, 1, 0);
        }
        else {
            this.card[0][0].setNo(1);
        }

        if(this.card[0][4] == null) {
            this.card[0][4] = new Card(25, 0.5f, 235, 0, 18, 4);
        }
        else {
            this.card[0][4].setNo(18);
        }

        if(this.card[1][3] == null) {
            this.card[1][3] = new Card(25, 0.5f, 235, 1, 4, 3);
        }
        else {
            this.card[1][3].setNo(4);
        }

        if(this.card[1][2] == null) {
            this.card[1][2] = new Card(25, 0.5f, 235, 1, 3, 2);
        }
        else {
            this.card[1][2].setNo(3);
        }

        if(this.card[1][1] == null) {
            this.card[1][1] = new Card(25, 0.5f, 235, 1, 2, 1);
        }
        else {
            this.card[1][1].setNo(2);
        }

        if(this.card[1][0] == null) {
            this.card[1][0] = new Card(25, 0.5f, 235, 1, 1, 0);
        }
        else {
            this.card[1][0].setNo(1);
        }

        if(this.card[1][4] == null) {
            this.card[1][4] = new Card(25, 0.5f, 235, 1, 18, 4);
        }
        else {
            this.card[1][4].setNo(18);
        }

        if(this.card[2][3] == null) {
            this.card[2][3] = new Card(25, 0.5f, 235, 2, 4, 3);
        }
        else {
            this.card[2][3].setNo(4);
        }

        if(this.card[2][2] == null) {
            this.card[2][2] = new Card(25, 0.5f, 235, 2, 3, 2);
        }
        else {
            this.card[2][2].setNo(3);
        }

        if(this.card[2][1] == null) {
            this.card[2][1] = new Card(25, 0.5f, 235, 2, 2, 1);
        }
        else {
            this.card[2][1].setNo(2);
        }

        if(this.card[2][0] == null) {
            this.card[2][0] = new Card(25, 0.5f, 235, 2, 1, 0);
        }
        else {
            this.card[2][0].setNo(1);
        }

        if(this.card[2][4] == null) {
            this.card[2][4] = new Card(25, 0.5f, 235, 2, 18, 4);
        }
        else {
            this.card[2][4].setNo(18);
        }

        if(this.card[3][3] == null) {
            this.card[3][3] = new Card(25, 0.5f, 235, 3, 4, 3);
        }
        else {
            this.card[3][3].setNo(4);
        }

        if(this.card[3][2] == null) {
            this.card[3][2] = new Card(25, 0.5f, 235, 3, 3, 2);
        }
        else {
            this.card[3][2].setNo(3);
        }

        if(this.card[3][1] == null) {
            this.card[3][1] = new Card(25, 0.5f, 235, 3, 2, 1);
        }
        else {
            this.card[3][1].setNo(2);
        }

        if(this.card[3][0] == null) {
            this.card[3][0] = new Card(25, 0.5f, 235, 3, 1, 0);
        }
        else {
            this.card[3][0].setNo(1);
        }

        if(this.card[3][4] == null) {
            this.card[3][4] = new Card(25, 0.5f, 235, 3, 18, 4);
        }
        else {
            this.card[3][4].setNo(18);
        }

        this.card[0][0].setPart(0, "m", 0, 0.18f, new int[]{0, 1, 2, 3, 4});
        this.card[0][0].setPart(1, "mm", 5, 0.18f, new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        this.card[0][0].setPart(2, "f", 17, 0.18f, new int[]{17, 18, 19, 20, 21});
        this.card[0][0].setPart(3, "ff", 22, 0.18f, new int[]{22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33});
        this.card[0][0].setPart(4, "o", 34, 0.18f, new int[]{34, 35, 36, 37, 38});
        this.card[0][0].setPart(5, "oo", 39, 0.18f, new int[]{39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50});
        this.card[0][0].setPart(6, "g", 51, 0.18f, new int[]{51, 52, 53, 54, 55});
        this.card[0][0].setPart(7, "gg", 56, 0.18f, new int[]{56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67});
        this.card[0][0].setPart(8, "ggg", 68, 0.18f, new int[]{68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79});
        this.card[0][0].setPart(9, "h", 80, 0.18f, new int[]{80, 81, 82, 83, 84});
        this.card[0][0].setPart(10, "hh", 85, 0.18f, new int[]{85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96});
        this.card[0][0].setPart(11, "c", 97, 0.18f, new int[]{97, 98, 99, 100, 101});
        this.card[0][0].setPart(12, "cc", 102, 0.18f, new int[]{102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113});
        this.card[0][0].setPart(13, "ccc", 114, 0.18f, new int[]{114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139});
        this.card[0][0].setPart(14, "cccc", 140, 0.18f, new int[]{140, 141, 142, 143, 144, 145, 146, 147, 148, 149});
        this.card[0][0].setPart(15, "t", 150, 0.18f, new int[]{150, 151, 152, 153, 154});
        this.card[0][0].setPart(16, "tt", 155, 0.18f, new int[]{155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166});
        this.card[0][0].setPart(17, "s", 167, 0.18f, new int[]{167, 168, 169, 170, 171});
        this.card[0][0].setPart(18, "ss", 172, 0.18f, new int[]{172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183});
        this.card[0][0].setPart(19, "n", 184, 0.18f, new int[]{184, 185, 186, 187, 188});
        this.card[0][0].setPart(20, "nn", 189, 0.18f, new int[]{189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200});
        this.card[0][0].setPart(21, "b", 201, 0.18f, new int[]{201, 202, 203, 204, 205});
        this.card[0][0].setPart(22, "bb", 206, 0.18f, new int[]{206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217});
        this.card[0][0].setPart(23, "j", 218, 0.18f, new int[]{218, 219, 220, 221, 222});
        this.card[0][0].setPart(24, "jj", 223, 0.18f, new int[]{223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234});
        this.card[0][3].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[0][2].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[0][1].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[0][0].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[0][4].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[1][3].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[1][2].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[1][1].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[1][0].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[1][4].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[2][3].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[2][2].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[2][1].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[2][0].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[2][4].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[3][3].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[3][2].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[3][1].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[3][0].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[3][4].setSize(Util.getWidth(((float)Global.cardSizeX)), Util.getHeight(((float)Global.cardSizeY)));
        this.card[0][3].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY5);
        this.card[0][2].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY4);
        this.card[0][1].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY3);
        this.card[0][0].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY2);
        this.card[0][4].setPosition(Util.XCenter(Res.iBaseLinePixelX1, 0f), Res.fBaseLineY1);
        this.card[1][3].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY5);
        this.card[1][2].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY4);
        this.card[1][1].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY3);
        this.card[1][0].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY2);
        this.card[1][4].setPosition(Util.XCenter(Res.iBaseLinePixelX2, 0f), Res.fBaseLineY1);
        this.card[2][3].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY5);
        this.card[2][2].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY4);
        this.card[2][1].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY3);
        this.card[2][0].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY2);
        this.card[2][4].setPosition(Util.XCenter(Res.iBaseLinePixelX3, 0f), Res.fBaseLineY1);
        this.card[3][3].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY5);
        this.card[3][2].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY4);
        this.card[3][1].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY3);
        this.card[3][0].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY2);
        this.card[3][4].setPosition(Util.XCenter(Res.iBaseLinePixelX4, 0f), Res.fBaseLineY1);
        int v7;
        for(v7 = 0; v7 < 4; ++v7) {
            int v8;
            for(v8 = 0; v8 < 5; ++v8) {
                this.card[v7][v8].startY = this.card[v7][v8].y;
                this.card[v7][v8].mileage = 0f;
                this.card[v7][v8].visible = true;
                this.card[v7][v8].play(true);
            }
        }

        this.iNextBandPos[0] = 5;
        this.iNextBandPos[1] = 5;
        this.iNextBandPos[2] = 5;
        this.iNextBandPos[3] = 5;
    }

    private void iniHelpAskMsg() {
        this.addHelp(" **** ?????? ????????????. ****", Color.ORANGE);
        this.addHelp("", Color.ORANGE);
        this.addHelp(" **** ???????? ****", Color.WHITE);
        this.addHelp("  - ?????? = ?? ???? ", Color.RED);
        this.addHelp("  - ?????? = ???? ???? ???? ", Color.GREEN);
        this.addHelp("  - ?????? = ???? ???? ???? ", Color.YELLOW);
        this.addHelp("", Color.GREEN);
        this.addHelp(" **** ???????? ****", Color.ORANGE);
        this.addHelp(" ???????? ???????? ?????? ????????", Color.ORANGE);
        this.addHelp(" ????????.", Color.ORANGE);
        this.addHelp(" ???? ???? ???? ?????? ???? ??  ", Color.ORANGE);
        this.addHelp(" ???? ???????? ???? ??????????.", Color.ORANGE);
        this.addHelp("", Color.GREEN);
        this.addHelp(" **** ???? ???? ****", Color.WHITE);
        this.addHelp(" ?????? ???????? ???????? ?????? ", Color.WHITE);
        this.addHelp(" ?????????? ??????????????.", Color.WHITE);
        this.addHelp("", Color.GREEN);
        this.addHelp(" **** ???????? ****", Color.YELLOW);
        this.addHelp(" ?????????? ??????????", Color.YELLOW);
        this.addHelp(" ?????? ???????? ?????????? ????????.", Color.YELLOW);
        this.addHelp(" ???? ?????????? ?????????? ??????????", Color.YELLOW);
        this.addHelp(" ?????? ?? ????????.", Color.YELLOW);
        this.addHelp("", Color.GREEN);
        this.addHelp(" **** ???????? ****", Color.GREEN);
        this.addHelp(" ???? ?????? ????????, ?????? ???? ", Color.GREEN);
        this.addHelp(" ?????? ???????? ?????????? ????????.", Color.GREEN);
        this.addHelp(" ???? ?? ?????? ????????????????.", Color.GREEN);
        this.addHelp("", Color.GREEN);
    }

    private void iniItems() {
        int v11 = 8;
        int v10 = 19;
        int v3 = 197;
        float v2 = 0.5f;
        int v1 = 20;
        if(GameScreen.item[0] == null) {
            GameScreen.item[0] = new Item(v1, v2, v3, 1, 0);
        }
        else {
            GameScreen.item[0].setNo(1);
        }

        if(GameScreen.item[1] == null) {
            GameScreen.item[1] = new Item(v1, v2, v3, 2, 1);
        }
        else {
            GameScreen.item[1].setNo(2);
        }

        if(GameScreen.item[2] == null) {
            GameScreen.item[2] = new Item(v1, v2, v3, 3, 2);
        }
        else {
            GameScreen.item[2].setNo(3);
        }

        if(GameScreen.item[3] == null) {
            GameScreen.item[3] = new Item(v1, v2, v3, 4, 3);
        }
        else {
            GameScreen.item[3].setNo(4);
        }

        if(GameScreen.item[4] == null) {
            GameScreen.item[4] = new Item(v1, v2, v3, 5, 4);
        }
        else {
            GameScreen.item[4].setNo(5);
        }

        if(GameScreen.item[5] == null) {
            GameScreen.item[5] = new Item(v1, v2, v3, 6, 5);
        }
        else {
            GameScreen.item[5].setNo(6);
        }

        if(GameScreen.item[6] == null) {
            GameScreen.item[6] = new Item(v1, v2, v3, 7, 6);
        }
        else {
            GameScreen.item[6].setNo(7);
        }

        if(GameScreen.item[7] == null) {
            GameScreen.item[7] = new Item(v1, v2, v3, v11, 7);
        }
        else {
            GameScreen.item[7].setNo(v11);
        }

        if(GameScreen.item[v11] == null) {
            GameScreen.item[v11] = new Item(v1, v2, v3, 9, v11);
        }
        else {
            GameScreen.item[v11].setNo(9);
        }

        if(GameScreen.item[9] == null) {
            GameScreen.item[9] = new Item(v1, v2, v3, 10, 9);
        }
        else {
            GameScreen.item[9].setNo(10);
        }

        if(GameScreen.item[10] == null) {
            GameScreen.item[10] = new Item(v1, v2, v3, 11, 10);
        }
        else {
            GameScreen.item[10].setNo(11);
        }

        if(GameScreen.item[11] == null) {
            GameScreen.item[11] = new Item(v1, v2, v3, 12, 11);
        }
        else {
            GameScreen.item[11].setNo(12);
        }

        if(GameScreen.item[12] == null) {
            GameScreen.item[12] = new Item(v1, v2, v3, 13, 12);
        }
        else {
            GameScreen.item[12].setNo(13);
        }

        if(GameScreen.item[13] == null) {
            GameScreen.item[13] = new Item(v1, v2, v3, 14, 13);
        }
        else {
            GameScreen.item[13].setNo(14);
        }

        if(GameScreen.item[14] == null) {
            GameScreen.item[14] = new Item(v1, v2, v3, 15, 14);
        }
        else {
            GameScreen.item[14].setNo(15);
        }

        if(GameScreen.item[15] == null) {
            GameScreen.item[15] = new Item(v1, v2, v3, 16, 15);
        }
        else {
            GameScreen.item[15].setNo(16);
        }

        if(GameScreen.item[16] == null) {
            GameScreen.item[16] = new Item(v1, v2, v3, 17, 16);
        }
        else {
            GameScreen.item[16].setNo(17);
        }

        if(GameScreen.item[17] == null) {
            GameScreen.item[17] = new Item(v1, v2, v3, 18, 17);
        }
        else {
            GameScreen.item[17].setNo(18);
        }

        if(GameScreen.item[18] == null) {
            GameScreen.item[18] = new Item(v1, v2, v3, v10, 18);
        }
        else {
            GameScreen.item[18].setNo(v10);
        }

        if(GameScreen.item[v10] == null) {
            GameScreen.item[v10] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[v10].setNo(v1);
        }

        if(GameScreen.item[v1] == null) {
            GameScreen.item[v1] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[v1].setNo(21);
        }

        if(GameScreen.item[21] == null) {
            GameScreen.item[21] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[21].setNo(22);
        }

        if(GameScreen.item[22] == null) {
            GameScreen.item[22] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[22].setNo(23);
        }

        if(GameScreen.item[23] == null) {
            GameScreen.item[23] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[23].setNo(24);
        }

        if(GameScreen.item[24] == null) {
            GameScreen.item[24] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[24].setNo(25);
        }

        if(GameScreen.item[25] == null) {
            GameScreen.item[25] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[25].setNo(26);
        }

        if(GameScreen.item[26] == null) {
            GameScreen.item[26] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[26].setNo(27);
        }

        if(GameScreen.item[27] == null) {
            GameScreen.item[27] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[27].setNo(28);
        }

        if(GameScreen.item[28] == null) {
            GameScreen.item[28] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[28].setNo(29);
        }

        if(GameScreen.item[29] == null) {
            GameScreen.item[29] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[29].setNo(30);
        }

        if(GameScreen.item[30] == null) {
            GameScreen.item[30] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[30].setNo(31);
        }

        if(GameScreen.item[31] == null) {
            GameScreen.item[31] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[31].setNo(32);
        }

        if(GameScreen.item[32] == null) {
            GameScreen.item[32] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[32].setNo(33);
        }

        if(GameScreen.item[33] == null) {
            GameScreen.item[33] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[33].setNo(34);
        }

        if(GameScreen.item[34] == null) {
            GameScreen.item[34] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[34].setNo(35);
        }

        if(GameScreen.item[35] == null) {
            GameScreen.item[35] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[35].setNo(36);
        }

        if(GameScreen.item[36] == null) {
            GameScreen.item[36] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[36].setNo(37);
        }

        if(GameScreen.item[37] == null) {
            GameScreen.item[37] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[37].setNo(38);
        }

        if(GameScreen.item[38] == null) {
            GameScreen.item[38] = new Item(v1, v2, v3, v1, v10);
        }
        else {
            GameScreen.item[38].setNo(39);
        }

        int v7;
        for(v7 = 0; v7 < 39; ++v7) {
            Item v0 = GameScreen.item[v7];
            int[] v5 = new int[1];
            v5[0] = 0;
            v0.setPart(0, "f", 0, 0.12f, v5);
            GameScreen.item[v7].setPart(1, "ff", 1, 0.12f, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30});
            v0 = GameScreen.item[v7];
            v5 = new int[1];
            v5[0] = 31;
            v0.setPart(2, "v", 31, 0.12f, v5);
            GameScreen.item[v7].setPart(3, "vv", 32, 0.12f, new int[]{32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61});
            GameScreen.item[v7].setPart(4, "b", 62, 0.12f, new int[]{62, 63, 64, 65, 66, 67, 68, 69});
            GameScreen.item[v7].setPart(5, "bb", 70, 0.12f, new int[]{70, 71, 72, 73, 74, 75, 76, 77});
            GameScreen.item[v7].setPart(6, "c", 78, 0.12f, new int[]{78, 79, 80, 81, 82, 83, 84, 85});
            GameScreen.item[v7].setPart(7, "cc", 78, 0.12f, new int[]{78, 79, 80, 81, 82, 83, 84, 85});
            GameScreen.item[v7].setPart(v11, "g", 97, 0.12f, new int[]{97, 98, 99, 100, 101, 102, 103, 104});
            GameScreen.item[v7].setPart(9, "gg", 105, 0.12f, new int[]{105, 106, 107, 108, 109, 110, 111, 112});
            GameScreen.item[v7].setPart(10, "r", 113, 0.12f, new int[]{113, 114, 115, 116, 117, 118, 119, 120});
            GameScreen.item[v7].setPart(11, "rr", 121, 0.12f, new int[]{121, 122, 123, 124, 125, 126, 127, 128});
            GameScreen.item[v7].setPart(12, "s", 129, 0.12f, new int[]{129, 130, 131, 132, 133, 134, 135, 136});
            GameScreen.item[v7].setPart(13, "ss", 137, 0.12f, new int[]{137, 138, 139, 140, 141, 142, 143, 144});
            GameScreen.item[v7].setPart(14, "w", 145, 0.12f, new int[]{145, 146, 147, 148, 149, 150, 151, 152});
            GameScreen.item[v7].setPart(15, "ww", 153, 0.12f, new int[]{153, 154, 155, 156, 157, 158, 159, 160});
            GameScreen.item[v7].setPart(16, "x", 161, 0.12f, new int[]{161, 162, 163, 164, 165, 166, 167, 168});
            GameScreen.item[v7].setPart(17, "xx", 169, 0.12f, new int[]{169, 170, 171, 172, 173, 174, 175, 192, 193, 194, 195, 196});
            GameScreen.item[v7].setPart(18, "y", 176, 0.12f, new int[]{176, 177, 178, 179, 180, 181, 182, 183});
            GameScreen.item[v7].setPart(v10, "yy", 184, 0.12f, new int[]{184, 185, 186, 187, 188, 189, 190, 191});
        }

        float v6 = Util.getTop(400f, Util.getHeight(128f));
        for(v7 = 0; v7 < 39; ++v7) {
            GameScreen.item[v7].setSize(Util.getWidth(128f), Util.getHeight(128f));
            GameScreen.item[v7].setPosition(Util.getLeft(((float)(v7 * 100))), v6);
            GameScreen.item[v7].setNo(v7 + 1);
        }
    }

    private void iniReel() {
        int v7 = 3;
        int v6 = 2;
        this.fsTargetObj = "";
        this.fiCTB = 1;
        this.fsPos = "";
        this.fsMask = "";
        this.fsFire = "";
        this.fiMultiple = 0;
        this.fiPlusWinMoney = 0;
        this.fPlusWinMoneyShowDelay = 0f;
        int v0;
        for(v0 = 0; v0 < 4; ++v0) {
            int v1;
            for(v1 = 0; v1 < 5; ++v1) {
                this.card[v0][v1].y = this.getNearestBaseY(this.card[v0][v1].y);
                this.card[v0][v1].startY = this.card[v0][v1].y;
                this.card[v0][v1].visible = true;
                this.card[v0][v1].mileage = 0f;
                if(this.card[v0][v1].ani.length() >= v6) {
                    this.card[v0][v1].ani = this.card[v0][v1].ani.substring(0, 1);
                }

                this.card[v0][v1].play(false);
            }
        }

        for(v0 = 0; v0 < 4; ++v0) {
            this.bReelRunning[v0] = true;
            this.bReelPoppingStarted[v0] = false;
            this.bReelDeceleratingStarted[v0] = false;
            this.bReelAccelerating[v0] = true;
            this.bReelDecelerating[v0] = false;
            this.bReelMinSpeedStarted[v0] = false;
            this.bReelPoppingMinSpeedStarted[v0] = false;
            this.iReelTensioningStep[v0] = 0;
            this.fReelDeltaMileage[v0] = 0f;
            this.fReelElapseTime[v0] = 0f;
            this.iPopCount[v0] = 0;
            this.fReelPoppingStartTime[v0] = 60f;
            this.fReelDeceleratingStartTime[v0] = 60f;
        }

        this.fNextScenarioDelay = 1000000000f;
        this.fEndScenarioDelay = 1000000000f;
        GameScreen.fTotalReelElapseTime = 0f;
        this.iFireType = 0;
        this.iMaxPopCount[0] = 7;
        this.iMaxPopCount[1] = 11;
        this.iMaxPopCount[v6] = 11;
        this.iMaxPopCount[v7] = 9;
        this.fStartReelDelayTime[0] = 0f;
        this.fStartReelDelayTime[1] = 0.2f;
        this.fStartReelDelayTime[v6] = 0.3f;
        this.fStartReelDelayTime[v7] = 0.1f;
        this.reelMaxSpeed[0] = 2.7f;
        this.reelMaxSpeed[1] = 2.7f;
        this.reelMaxSpeed[v6] = 2.7f;
        this.reelMaxSpeed[v7] = 2.7f;
        this.reelMinSpeed[0] = 0.4f;
        this.reelMinSpeed[1] = 0.4f;
        this.reelMinSpeed[v6] = 0.4f;
        this.reelMinSpeed[v7] = 0.4f;
        this.reelMinPoppingSpeed[0] = 0.3f;
        this.reelMinPoppingSpeed[1] = 0.3f;
        this.reelMinPoppingSpeed[v6] = 0.3f;
        this.reelMinPoppingSpeed[v7] = 0.3f;
        this.accelerationSpeed[0] = 0.06f;
        this.accelerationSpeed[1] = 0.05f;
        this.accelerationSpeed[v6] = 0.05f;
        this.accelerationSpeed[v7] = 0.06f;
        this.decelerationSpeed[0] = 0.02f;
        this.decelerationSpeed[1] = 0.02f;
        this.decelerationSpeed[v6] = 0.02f;
        this.decelerationSpeed[v7] = 0.02f;
        this.iPostTargetCardNo[0] = 0;
        this.iPostTargetCardNo[1] = 0;
        this.iPostTargetCardNo[v6] = 0;
        this.iPostTargetCardNo[v7] = 0;
        --Global.spin;
        Res.aniLine.play(false);
        Res.aniLine.visible = false;
        Res.aniLineLampLT.visible = false;
        Res.aniLineLampLT.play(false);
        Res.aniLineLampLC.visible = false;
        Res.aniLineLampLC.play(false);
        Res.aniLineLampLB.visible = false;
        Res.aniLineLampLB.play(false);
        Res.aniLineLampRT.visible = false;
        Res.aniLineLampRT.play(false);
        Res.aniLineLampRC.visible = false;
        Res.aniLineLampRC.play(false);
        Res.aniLineLampRB.visible = false;
        Res.aniLineLampRB.play(false);
        Snd.sndReel.play();
        GameScreen.updateLabel();
        this.showWinMoney(false);
        this.startBarEvent(false);
        this.startSevenEvent(false);
        this.startJokerEvent(false);
        this.startMiddleEvent(false, "t");
    }

    private void iniSkin() {  // has try-catch handlers
        try {
            if(this.width < 1080f) {
                goto label_14;
            }

            this.skin = new Skin(Gdx.files.internal("data/uiskin1280.json"));
            GameScreen.skin_window = new Skin(Gdx.files.internal("data/skin1280.json"));
            return;
        label_14:
            this.skin = new Skin(Gdx.files.internal("data/uiskin800.json"));
            GameScreen.skin_window = new Skin(Gdx.files.internal("data/skin800.json"));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->iniSkin", Util.getExceptionMessage(v0));
        }
    }

    private void iniTest() {
        this.lbFps = new Label("", this.skin, "default-font", Color.GREEN);
        this.btnTest1 = new TextButton("[ ?? ??  ]", this.skin);
        this.btnTest2 = new TextButton("[ 3:*MMM ]", this.skin);
        this.btnTest3 = new TextButton("[ 2:ZZZZ ]", this.skin);
        this.btnTest4 = new TextButton("[ 1:BB*B ]", this.skin);
        this.btnTest5 = new TextButton("[ ?????? ]", this.skin);
        this.btnTest6 = new TextButton("[  ????  ]", this.skin);
        this.btnTest7 = new TextButton("[  ????  ]", this.skin);
        this.btnTest8 = new TextButton("[  ????  ]", this.skin);
        this.btnTest9 = new TextButton("[ ?????? ]", this.skin);
        this.btnTest10 = new TextButton("[  ????  ]", this.skin);
        this.btnTest11 = new TextButton("[ ?????? ]", this.skin);
        this.btnTest12 = new TextButton("[  ????  ]", this.skin);
        this.btnTest13 = new TextButton("[  ????  ]", this.skin);
        this.btnTest14 = new TextButton("[ ?????? ]", this.skin);
        this.btnTest15 = new TextButton("[  7???? ]", this.skin);
        this.btnTest16 = new TextButton("[????????]", this.skin);
        this.btnTest17 = new TextButton("[ ?????? ]", this.skin);
        this.btnTest18 = new TextButton("[????????]", this.skin);
        this.btnTest19 = new TextButton("[  ????  ]", this.skin);
        this.btnTest20 = new TextButton("[????????]", this.skin);
        this.btnTest21 = new TextButton("[????????]", this.skin);
        this.btnTest22 = new TextButton("[        ]", this.skin);
        this.btnTest23 = new TextButton("[        ]", this.skin);
        this.btnTest24 = new TextButton("[        ]", this.skin);
        this.btnTest25 = new TextButton("[        ]", this.skin);
        this.btnTest26 = new TextButton("[        ]", this.skin);
        this.btnTest27 = new TextButton("[        ]", this.skin);
        this.btnTest28 = new TextButton("[        ]", this.skin);
        this.btnTest29 = new TextButton("[        ]", this.skin);
        this.btnTest30 = new TextButton("[        ]", this.skin);
        this.btnTest31 = new TextButton("[        ]", this.skin);
        this.btnTest32 = new TextButton("[        ]", this.skin);
        this.tblTest = new Table();
        this.tblTest.setFillParent(true);
        this.tblTest.left().pad(0f).top().padTop(0f);
        this.tblTest.add(this.lbFps);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest1).padLeft(3f);
        this.tblTest.add(this.btnTest2).padLeft(3f);
        this.tblTest.add(this.btnTest3).padLeft(3f);
        this.tblTest.add(this.btnTest4).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest5).padLeft(3f);
        this.tblTest.add(this.btnTest6).padLeft(3f);
        this.tblTest.add(this.btnTest7).padLeft(3f);
        this.tblTest.add(this.btnTest8).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest9).padLeft(3f);
        this.tblTest.add(this.btnTest10).padLeft(3f);
        this.tblTest.add(this.btnTest11).padLeft(3f);
        this.tblTest.add(this.btnTest12).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest13).padLeft(3f);
        this.tblTest.add(this.btnTest14).padLeft(3f);
        this.tblTest.add(this.btnTest15).padLeft(3f);
        this.tblTest.add(this.btnTest16).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest17).padLeft(3f);
        this.tblTest.add(this.btnTest18).padLeft(3f);
        this.tblTest.add(this.btnTest19).padLeft(3f);
        this.tblTest.add(this.btnTest20).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest21).padLeft(3f);
        this.tblTest.add(this.btnTest22).padLeft(3f);
        this.tblTest.add(this.btnTest23).padLeft(3f);
        this.tblTest.add(this.btnTest24).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest25).padLeft(3f);
        this.tblTest.add(this.btnTest26).padLeft(3f);
        this.tblTest.add(this.btnTest27).padLeft(3f);
        this.tblTest.add(this.btnTest28).padLeft(3f);
        this.tblTest.row().padTop(20f);
        this.tblTest.add(this.btnTest29).padLeft(3f);
        this.tblTest.add(this.btnTest30).padLeft(3f);
        this.tblTest.add(this.btnTest31).padLeft(3f);
        this.tblTest.add(this.btnTest32).padLeft(3f);
        GameScreen.stage.addActor(this.tblTest);
        this.lbFps.setVisible(false);
        this.btnTest1.setVisible(false);
        this.btnTest2.setVisible(false);
        this.btnTest3.setVisible(false);
        this.btnTest4.setVisible(false);
        this.btnTest5.setVisible(false);
        this.btnTest6.setVisible(false);
        this.btnTest7.setVisible(false);
        this.btnTest8.setVisible(false);
        this.btnTest9.setVisible(false);
        this.btnTest10.setVisible(false);
        this.btnTest11.setVisible(false);
        this.btnTest12.setVisible(false);
        this.btnTest13.setVisible(false);
        this.btnTest14.setVisible(false);
        this.btnTest15.setVisible(false);
        this.btnTest16.setVisible(false);
        this.btnTest17.setVisible(false);
        this.btnTest18.setVisible(false);
        this.btnTest19.setVisible(false);
        this.btnTest20.setVisible(false);
        this.btnTest21.setVisible(false);
        this.btnTest22.setVisible(false);
        this.btnTest23.setVisible(false);
        this.btnTest24.setVisible(false);
        this.btnTest25.setVisible(false);
        this.btnTest26.setVisible(false);
        this.btnTest27.setVisible(false);
        this.btnTest28.setVisible(false);
        this.btnTest29.setVisible(false);
        this.btnTest30.setVisible(false);
        this.btnTest31.setVisible(false);
        this.btnTest32.setVisible(false);
        this.btnTest1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.bReelRunning[0] = false;
                GameScreen.this.bReelRunning[1] = false;
                GameScreen.this.bReelRunning[2] = false;
                GameScreen.this.bReelRunning[3] = false;
                GameScreen.access$1(GameScreen.this, false);
                GameScreen.access$2(GameScreen.this, 0f);
                ++Global.spin;
                GameScreen.this.reelIdle();
                return 0;
            }
        });
        this.btnTest2.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.bReelRunning[0] = false;
                GameScreen.this.bReelRunning[1] = false;
                GameScreen.this.bReelRunning[2] = false;
                GameScreen.this.bReelRunning[3] = false;
                GameScreen.access$1(GameScreen.this, false);
                GameScreen.access$2(GameScreen.this, 0f);
                ++Global.spin;
                GameScreen.this.reelAllot("M", 3, "16061606", "*MMM", "", 10);
                return 0;
            }
        });
        this.btnTest3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.bReelRunning[0] = false;
                GameScreen.this.bReelRunning[1] = false;
                GameScreen.this.bReelRunning[2] = false;
                GameScreen.this.bReelRunning[3] = false;
                GameScreen.access$1(GameScreen.this, false);
                GameScreen.access$2(GameScreen.this, 0f);
                ++Global.spin;
                GameScreen.this.reelAllot("Z", 2, "04010112", "ZZZZ", "", 200);
                return 0;
            }
        });
        this.btnTest4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.bReelRunning[0] = false;
                GameScreen.this.bReelRunning[1] = false;
                GameScreen.this.bReelRunning[2] = false;
                GameScreen.this.bReelRunning[3] = false;
                GameScreen.access$1(GameScreen.this, false);
                GameScreen.access$2(GameScreen.this, 0f);
                ++Global.spin;
                GameScreen.this.reelAllot("B", 1, "06040717", "BB*B", "", 60);
                return 0;
            }
        });
        this.btnTest5.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startFish();
                GameScreen.playAndroidSound("jocker1.mp3");
                return 0;
            }
        });
        this.btnTest6.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String.valueOf(Util.randomInt(0, 5));
                Res.aniPostLeft.play(true, "5000", 2);
                Res.aniPostRight.play(true, "5000", 2);
                return 0;
            }
        });
        this.btnTest7.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean v0;
                GameScreen v2 = GameScreen.this;
                if(GameScreen.this.bStopScenarioForTest) {
                    v0 = false;
                }
                else {
                    v0 = true;
                }

                GameScreen.access$8(v2, v0);
                return 0;
            }
        });
        this.btnTest8.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.screenShaker.shakeScreen(30, 0.035f, 0.035f);
                return 0;
            }
        });
        this.btnTest9.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startTurtle();
                return 0;
            }
        });
        this.btnTest10.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startWhale();
                return 0;
            }
        });
        this.btnTest11.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startJellyfish();
                return 0;
            }
        });
        this.btnTest12.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startShark();
                return 0;
            }
        });
        this.btnTest13.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startMermaid();
                return 0;
            }
        });
        this.btnTest14.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startBarEvent(true);
                return 0;
            }
        });
        this.btnTest15.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startSevenEvent(true);
                return 0;
            }
        });
        this.btnTest16.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startJokerEvent(true);
                return 0;
            }
        });
        this.btnTest17.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startMiddleEvent(true, "s");
                return 0;
            }
        });
        this.btnTest18.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.startMiddleEvent(true, "t");
                return 0;
            }
        });
        this.btnTest19.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if("D".equals(GameScreen.this.fsBackgroundDayNight)) {
                    GameScreen.this.setBackGround("N");
                }
                else {
                    GameScreen.this.setBackGround("D");
                }

                return 0;
            }
        });
        this.btnTest20.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.addTestScenario();
                return 0;
            }
        });
        this.btnTest21.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                GameScreen.this.showBackLight();
                return 0;
            }
        });
        this.btnTest22.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest23.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest24.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest25.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest26.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest27.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest28.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest29.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest30.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest31.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
        this.btnTest32.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return 0;
            }
        });
    }

    private void iniTimer() {
        this.timer = new Timer();
        this.timer.schedule(new MyTask(), 1000, 1000);
    }

    private void iniUI() {
        float v10 = 350f;
        float v9 = 245f;
        float v8 = 60f;
        int v0;
        for(v0 = 0; v0 < this.iMaxMsg; ++v0) {
            this.saMsg[v0] = "";
            this.clMsg[v0] = Color.WHITE;
        }

        this.tblMsg = new Table();
        this.spsMsg = new ScrollPaneStyle();
        this.scpMsg = new ScrollPane(this.tblMsg, this.skin);
        this.scpMsg.setFadeScrollBars(false);
        this.scpMsg.setScrollbarsOnTop(false);
        this.scpMsg.setFlickScroll(true);
        this.scpMsg.setSize(Util.getActorWidth(525f), Util.getActorHeight(127f));
        this.scpMsg.setPosition(Util.getActorLeft(190f), Util.getActorTop(579f, this.scpMsg.getHeight()));
        this.scpMsg.setStyle(this.spsMsg);
        GameScreen.stage.addActor(this.scpMsg);
        for(v0 = 0; v0 < this.iMaxHelp; ++v0) {
            this.saHelp[v0] = "";
            this.clHelp[v0] = Color.WHITE;
        }

        this.tblHelp = new Table();
        this.spsHelp = new ScrollPaneStyle();
        this.scpHelp = new ScrollPane(this.tblHelp, this.skin);
        this.scpHelp.setFadeScrollBars(false);
        this.scpHelp.setScrollbarsOnTop(false);
        this.scpHelp.setFlickScroll(true);
        this.scpHelp.setSize(Util.getActorWidth(490f), Util.getActorHeight(865f));
        this.scpHelp.setPosition(Util.getActorLeft(93f), Util.getActorTop(336f, this.scpHelp.getHeight()));
        this.scpHelp.setStyle(this.spsHelp);
        this.scpHelp.setVisible(false);
        GameScreen.stage.addActor(this.scpHelp);
        for(v0 = 0; v0 < this.iMaxAsk; ++v0) {
            this.saAsk[v0] = "";
            this.clAsk[v0] = Color.WHITE;
        }

        this.tblAsk = new Table();
        this.spsAsk = new ScrollPaneStyle();
        this.scpAsk = new ScrollPane(this.tblAsk, this.skin);
        this.scpAsk.setFadeScrollBars(false);
        this.scpAsk.setScrollbarsOnTop(false);
        this.scpAsk.setFlickScroll(true);
        this.scpAsk.setSize(Util.getActorWidth(490f), Util.getActorHeight(865f));
        this.scpAsk.setPosition(Util.getActorLeft(93f), Util.getActorTop(336f, this.scpAsk.getHeight()));
        this.scpAsk.setStyle(this.spsAsk);
        this.scpAsk.setVisible(false);
        GameScreen.stage.addActor(this.scpAsk);
        GameScreen.lbMachine = new Label("", this.skin, "number-middle", Color.YELLOW);
        GameScreen.lbMachine.setAlignment(8);
        GameScreen.lbMachine.setSize(Util.getActorWidth(40f), Util.getActorHeight(24f));
        GameScreen.lbMachine.setPosition(Util.getActorLeft(343f), Util.getActorTop(1165f, GameScreen.lbMachine.getHeight()));
        GameScreen.stage.addActor(GameScreen.lbMachine);
        GameScreen.lbGift = new Label("", this.skin, "number-middle", Color.YELLOW);
        GameScreen.lbGift.setAlignment(8);
        GameScreen.lbGift.setSize(Util.getActorWidth(103f), Util.getActorHeight(24f));
        GameScreen.lbGift.setPosition(Util.getActorLeft(623f), Util.getActorTop(1206f, GameScreen.lbGift.getHeight()));
        GameScreen.stage.addActor(GameScreen.lbGift);
        GameScreen.lbSpin = new Label("", this.skin, "number-middle", Color.YELLOW);
        GameScreen.lbSpin.setAlignment(8);
        GameScreen.lbSpin.setSize(Util.getActorWidth(70f), Util.getActorHeight(24f));
        GameScreen.lbSpin.setPosition(Util.getActorLeft(96f), Util.getActorTop(1206f, GameScreen.lbSpin.getHeight()));
        GameScreen.stage.addActor(GameScreen.lbSpin);
        GameScreen.lbBet = new Label("", this.skin, "number-middle", Color.GREEN);
        GameScreen.lbBet.setAlignment(1);
        GameScreen.lbBet.setSize(Util.getActorWidth(250f), Util.getActorHeight(24f));
        GameScreen.lbBet.setPosition(Util.getActorLeft(484f), Util.getActorTop(38f, GameScreen.lbBet.getHeight()));
        GameScreen.stage.addActor(GameScreen.lbBet);
        GameScreen.lbAdditionalMoney = new Label("", this.skin, "hangul-font", Color.GREEN);
        GameScreen.lbAdditionalMoney.setSize(Util.getActorWidth(112f), Util.getActorHeight(24f));
        GameScreen.lbAdditionalMoney.setAlignment(16);
        GameScreen.lbAdditionalMoney.setPosition(Util.getActorLeft(605f), Util.getActorTop(900f, GameScreen.lbAdditionalMoney.getHeight()));
        GameScreen.lbAdditionalMoney.setVisible(false);
        GameScreen.stage.addActor(GameScreen.lbAdditionalMoney);
        GameScreen.lbAdditionalWin = new Label("", this.skin, "hangul-font", Color.GREEN);
        GameScreen.lbAdditionalWin.setSize(Util.getActorWidth(112f), Util.getActorHeight(24f));
        GameScreen.lbAdditionalWin.setAlignment(16);
        GameScreen.lbAdditionalWin.setPosition(Util.getActorLeft(605f), Util.getActorTop(963f, GameScreen.lbAdditionalWin.getHeight()));
        GameScreen.lbAdditionalWin.setVisible(false);
        GameScreen.stage.addActor(GameScreen.lbAdditionalWin);
        GameScreen.lbMoney = new Label("", this.skin, "number-middle", Color.GREEN);
        GameScreen.lbMoney.setSize(Util.getActorWidth(112f), Util.getActorHeight(24f));
        GameScreen.lbMoney.setAlignment(8);
        GameScreen.lbMoney.setPosition(Util.getActorLeft(343f), Util.getActorTop(1218f, GameScreen.lbMoney.getHeight()));
        GameScreen.stage.addActor(GameScreen.lbMoney);
        this.lbPlusWinMoney = new Label("", this.skin, "winnumber", Color.WHITE);
        this.lbPlusWinMoney.setSize(Util.getActorWidth(768f), Util.getActorHeight(200f));
        this.lbPlusWinMoney.setFontScale(2f);
        this.lbPlusWinMoney.setAlignment(1);
        this.lbPlusWinMoney.setPosition(Util.getActorLeft(0f), Util.getActorTop(820f, this.lbPlusWinMoney.getHeight()));
        GameScreen.stage.addActor(this.lbPlusWinMoney);
        GameScreen.lbReserve = new Label("", this.skin, "hangul-font", Color.GREEN);
        GameScreen.lbReserve.setSize(Util.getActorWidth(180f), Util.getActorHeight(40f));
        GameScreen.lbReserve.setAlignment(1);
        GameScreen.lbReserve.setPosition(Util.getActorLeft(379f), Util.getActorTop(258f, GameScreen.lbReserve.getHeight()));
        GameScreen.lbReserve.setVisible(false);
        GameScreen.stage.addActor(GameScreen.lbReserve);
        for(v0 = 0; v0 < 12; ++v0) {
            this.lbMachineNo[v0] = new Label("", this.skin, "number-middle", Color.ORANGE);
            this.lbMachineNo[v0].setSize(Util.getWidth(30f), Util.getHeight(8f));
            this.lbMachineNo[v0].setVisible(false);
            this.lbMachineNo[v0].setAlignment(16);
            GameScreen.stage.addActor(this.lbMachineNo[v0]);
        }

        this.lbMachineNo[0].setPosition(Util.getActorLeft(128f), Util.getActorTop(223f, this.lbMachineNo[0].getHeight()));
        this.lbMachineNo[1].setPosition(Util.getActorLeft(308f), Util.getActorTop(223f, this.lbMachineNo[1].getHeight()));
        this.lbMachineNo[2].setPosition(Util.getActorLeft(489f), Util.getActorTop(223f, this.lbMachineNo[2].getHeight()));
        this.lbMachineNo[3].setPosition(Util.getActorLeft(128f), Util.getActorTop(449f, this.lbMachineNo[3].getHeight()));
        this.lbMachineNo[4].setPosition(Util.getActorLeft(308f), Util.getActorTop(449f, this.lbMachineNo[4].getHeight()));
        this.lbMachineNo[5].setPosition(Util.getActorLeft(489f), Util.getActorTop(449f, this.lbMachineNo[5].getHeight()));
        this.lbMachineNo[6].setPosition(Util.getActorLeft(128f), Util.getActorTop(676f, this.lbMachineNo[6].getHeight()));
        this.lbMachineNo[7].setPosition(Util.getActorLeft(308f), Util.getActorTop(676f, this.lbMachineNo[7].getHeight()));
        this.lbMachineNo[8].setPosition(Util.getActorLeft(489f), Util.getActorTop(676f, this.lbMachineNo[8].getHeight()));
        this.lbMachineNo[9].setPosition(Util.getActorLeft(128f), Util.getActorTop(902f, this.lbMachineNo[9].getHeight()));
        this.lbMachineNo[10].setPosition(Util.getActorLeft(308f), Util.getActorTop(902f, this.lbMachineNo[10].getHeight()));
        this.lbMachineNo[11].setPosition(Util.getActorLeft(489f), Util.getActorTop(902f, this.lbMachineNo[11].getHeight()));
        this.tfChargeMoney = new TextField("", GameScreen.skin_window, "transparent");
        this.tfChargeMoney.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.tfChargeMoney.setPosition(Util.getActorLeft(v9), Util.getActorTop(358f, this.tfChargeMoney.getHeight()));
        this.tfChargeMoney.setVisible(false);
        GameScreen.stage.addActor(this.tfChargeMoney);
        this.tfChargeMoney.setTextFieldListener(new TextFieldListener() {
            public void keyTyped(TextField tf, char key) {
                if(key == 10) {
                    GameScreen.this.tfChargeMoney.getOnscreenKeyboard().show(false);
                }
            }
        });
        this.lbChargeMan = new Label("", this.skin, "hangul-font", Color.BLACK);
        this.lbChargeMan.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbChargeMan.setPosition(Util.getActorLeft(v9), Util.getActorTop(468f, this.lbChargeMan.getHeight()));
        this.lbChargeMan.setVisible(false);
        GameScreen.stage.addActor(this.lbChargeMan);
        this.lbChargeBank = new Label(Global.bank, this.skin, "hangul-font", Color.BLUE);
        this.lbChargeBank.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbChargeBank.setPosition(Util.getActorLeft(v9), Util.getActorTop(574f, this.lbChargeBank.getHeight()));
        this.lbChargeBank.setVisible(false);
        GameScreen.stage.addActor(this.lbChargeBank);
        this.lbChargeOwner = new Label(Global.owner, this.skin, "hangul-font", Color.BLUE);
        this.lbChargeOwner.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbChargeOwner.setPosition(Util.getActorLeft(v9), Util.getActorTop(681f, this.lbChargeOwner.getHeight()));
        this.lbChargeOwner.setVisible(false);
        GameScreen.stage.addActor(this.lbChargeOwner);
        this.lbChargeAccount = new Label(Global.account, this.skin, "hangul-font", Color.BLUE);
        this.lbChargeAccount.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbChargeAccount.setPosition(Util.getActorLeft(v9), Util.getActorTop(789f, this.lbChargeAccount.getHeight()));
        this.lbChargeAccount.setVisible(false);
        GameScreen.stage.addActor(this.lbChargeAccount);
        this.tfExchangeAccount = new TextField("", GameScreen.skin_window, "transparent");
        this.tfExchangeAccount.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.tfExchangeAccount.setPosition(Util.getActorLeft(v9), Util.getActorTop(360f, this.tfExchangeAccount.getHeight()));
        this.tfExchangeAccount.setVisible(false);
        GameScreen.stage.addActor(this.tfExchangeAccount);
        this.tfExchangeAccount.setTextFieldListener(new TextFieldListener() {
            public void keyTyped(TextField tf, char key) {
                if(key == 10) {
                    GameScreen.this.tfExchangeAccount.setFocusTraversal(true);
                    GameScreen.this.tfExchangeAccount.getOnscreenKeyboard().show(false);
                }
            }
        });
        this.tfExchangeMoney = new TextField("", GameScreen.skin_window, "transparent");
        this.tfExchangeMoney.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.tfExchangeMoney.setPosition(Util.getActorLeft(v9), Util.getActorTop(466f, this.tfExchangeMoney.getHeight()));
        this.tfExchangeMoney.setVisible(false);
        GameScreen.stage.addActor(this.tfExchangeMoney);
        this.tfExchangeMoney.setTextFieldListener(new TextFieldListener() {
            public void keyTyped(TextField tf, char key) {
                if(key == 10) {
                    GameScreen.this.tfExchangeMoney.setFocusTraversal(true);
                    GameScreen.this.tfExchangeMoney.getOnscreenKeyboard().show(false);
                }
            }
        });
        this.tfExchangeTel = new TextField("", GameScreen.skin_window, "transparent");
        this.tfExchangeTel.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.tfExchangeTel.setPosition(Util.getActorLeft(v9), Util.getActorTop(575f, this.tfExchangeTel.getHeight()));
        this.tfExchangeTel.setVisible(false);
        GameScreen.stage.addActor(this.tfExchangeTel);
        this.tfExchangeTel.setTextFieldListener(new TextFieldListener() {
            public void keyTyped(TextField tf, char key) {
                if(key == 10) {
                    GameScreen.this.tfExchangeTel.setFocusTraversal(true);
                    GameScreen.this.tfExchangeTel.getOnscreenKeyboard().show(false);
                }
            }
        });
        this.lbExchangeBank = new Label("", this.skin, "hangul-font", Color.BLACK);
        this.lbExchangeBank.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbExchangeBank.setPosition(Util.getActorLeft(v9), Util.getActorTop(678f, this.lbExchangeBank.getHeight()));
        this.lbExchangeBank.setVisible(false);
        GameScreen.stage.addActor(this.lbExchangeBank);
        this.lbExchangeOwner = new Label("", this.skin, "hangul-font", Color.BLACK);
        this.lbExchangeOwner.setSize(Util.getActorWidth(v10), Util.getActorHeight(v8));
        this.lbExchangeOwner.setPosition(Util.getActorLeft(v9), Util.getActorTop(789f, this.lbExchangeOwner.getHeight()));
        this.lbExchangeOwner.setVisible(false);
        GameScreen.stage.addActor(this.lbExchangeOwner);
        this.imgToastBack = new Image(Res.leaner.findRegion("wait_back"));
        this.imgToastBack.setSize(Util.getActorWidth(768f), Util.getActorHeight(1280f));
        this.imgToastBack.setPosition(Util.getActorLeft(0f), Util.getActorTop(0f, this.imgToastBack.getHeight()));
        this.imgToastBack.setVisible(false);
        GameScreen.stage.addActor(this.imgToastBack);
        this.imgToast = new Image(Res.leaner.findRegion("toast_back"));
        this.imgToast.setSize(Util.getActorWidth(700f), Util.getActorHeight(180f));
        this.imgToast.setPosition(Util.getActorLeft(34f), Util.getActorTop(365f, this.imgToast.getHeight()));
        this.imgToast.setVisible(false);
        GameScreen.stage.addActor(this.imgToast);
        this.lbToast = new Label("", this.skin, "hangul-font", Color.YELLOW);
        this.lbToast.setSize(Util.getActorWidth(450f), Util.getActorHeight(100f));
        this.lbToast.setPosition(Util.getActorLeft(175f), Util.getActorTop(410f, this.lbToast.getHeight()));
        this.lbToast.setAlignment(1);
        this.lbToast.setVisible(false);
        GameScreen.stage.addActor(this.lbToast);
    }

    private boolean isReelRunning() {
        boolean v0 = false;
        if((this.bReelRunning[0]) || (this.bReelRunning[1]) || (this.bReelRunning[2]) || (this.bReelRunning[3])) {
            v0 = true;
        }

        return v0;
    }

    private void machineListRequest(int flag) {
        if(this.bCanUpDownChannel) {
            this.bCanUpDownChannel = false;
            int v0 = Global.division;
            if(flag > 0) {
                v0 = Global.division + 1;
            }
            else if(flag < 0) {
                v0 = Global.division - 1;
            }

            GameScreen.socketWrite("G030\t" + String.valueOf(v0));
        }
    }

    private void minusWinMoney() {
        if(this.fiPlusWinMoney > 20000) {
            Snd.sndRoll = null;
        }
        else if(this.fiPlusWinMoney >= 15000) {
            this.fiPlusWinMoney -= this.fiBaseCoin * 4;
        }
        else if(this.fiPlusWinMoney >= 10000) {
            this.fiPlusWinMoney -= this.fiBaseCoin * 3;
        }
        else if(this.fiPlusWinMoney >= 5000) {
            this.fiPlusWinMoney -= this.fiBaseCoin * 2;
        }
        else if(this.fiPlusWinMoney >= 2000) {
            this.fiPlusWinMoney -= this.fiBaseCoin;
        }
        else {
            this.fiPlusWinMoney -= this.fiBaseCoin;
        }

        if(this.fiPlusWinMoney > 0) {
            this.lbPlusWinMoney.setText(String.valueOf(this.fiPlusWinMoney));
        }
        else {
            Snd.sndRoll.stop();
            this.lbPlusWinMoney.setVisible(false);
        }
    }

    private void moveReel(int iReelIndex) {
        int v7 = 18;
        int v6 = 5;
        if(this.iReelTensioningStep[iReelIndex] == 0) {
            int v0;
            for(v0 = 0; v0 < v6; ++v0) {
                this.card[iReelIndex][v0].mileage += this.fReelDeltaMileage[iReelIndex];
                this.card[iReelIndex][v0].y = this.card[iReelIndex][v0].startY - this.card[iReelIndex][v0].mileage;
                if(this.card[iReelIndex][v0].y < Res.fBaseLineY0) {
                    while(this.card[iReelIndex][v0].y < Res.fBaseLineY0) {
                        this.card[iReelIndex][v0].mileage -= Res.fBaseLineY5 - Res.fBaseLineY0;
                        this.card[iReelIndex][v0].y = this.card[iReelIndex][v0].startY - this.card[iReelIndex][v0].mileage;
                    }

                    if(this.bReelPoppingStarted[iReelIndex]) {
                        ++this.iPopCount[iReelIndex];
                        this.iNextBandPos[iReelIndex] = (this.iPopCount[iReelIndex] - this.iMaxPopCount[iReelIndex]) % 18 - (18 - this.iTargetCardNo[iReelIndex] - 2);
                        this.iNextBandPos[iReelIndex] %= 18;
                        if(this.iNextBandPos[iReelIndex] < 0) {
                            this.iNextBandPos[iReelIndex] = 18 - this.iNextBandPos[iReelIndex] * -1;
                        }

                        if(this.iNextBandPos[iReelIndex] == 0) {
                            this.iNextBandPos[iReelIndex] = v7;
                        }

                        this.card[iReelIndex][v0].setNo(this.iNextBandPos[iReelIndex]);
                    }
                    else {
                        this.card[iReelIndex][v0].setNo(this.iNextBandPos[iReelIndex]);
                        ++this.iNextBandPos[iReelIndex];
                        this.iNextBandPos[iReelIndex] %= 18;
                        if(this.iNextBandPos[iReelIndex] < 0) {
                            this.iNextBandPos[iReelIndex] = 18 - this.iNextBandPos[iReelIndex] * -1;
                        }

                        if(this.iNextBandPos[iReelIndex] != 0) {
                            goto label_119;
                        }

                        this.iNextBandPos[iReelIndex] = v7;
                    }
                }

            label_119:
            }

            if(this.iReelTensioningStep[iReelIndex] != 0) {
                return;
            }

            if(this.iPopCount[iReelIndex] <= this.iMaxPopCount[iReelIndex]) {
                return;
            }

            this.bReelDecelerating[iReelIndex] = false;
            this.bReelAccelerating[iReelIndex] = false;
            this.iReelTensioningStep[iReelIndex] = 1;
            for(v0 = 0; v0 < v6; ++v0) {
                this.card[iReelIndex][v0].targetY = this.getStopTargetYPos(iReelIndex, this.card[iReelIndex][v0].y);
            }
        }
    }

    private void newGameRequest() {  // has try-catch handlers
        try {
            if(this.iAdditionalBaseBet != 2000 && this.iAdditionalBaseBet != 10000) {
                Util.sysout("ERROR", "GameScreen->newGameRequest(1)", "Invalid BaseBet.", "Invalid BaseBet.");
                GameScreen.exitApp("???????? ?????? ?? ????????(CNG1).\n?????????? ??????????.");
            }

            if(Global.money < this.iAdditionalBaseBet) {
                Util.sysout("ERROR", "GameScreen->newGameRequest(2)", "Invalid BaseBet.", "Invalid BaseBet.");
                GameScreen.exitApp("???????? ?????? ?? ????????(CNG2).\n?????????? ??????????.");
            }

            if(!this.bWaitingAdditionalGame) {
                goto label_26;
            }

            return;
        label_26:
            this.bWaitingAdditionalGame = true;
            this.bAdditionalNeedNewGame = false;
            Res.btnNewGame.visible = false;
            Res.imgBackNewGame.visible = false;
            this.iAdditionalTimer = 999999999;
            this.fAdditionalTimer = 1000000000f;
            this.bAddHurryUp1 = false;
            this.bAddHurryUp2 = false;
            this.fEndAdditionalGameDelay = 1000000000f;
            Snd.stopAddSound();
            Snd.sndAddChoice.play();
            Res.btnLow.play(true, "x");
            Res.btnHigh.play(true, "x");
            Res.btnGo.play(true, "x");
            Res.btnStop.play(true, "x");
            Res.btnNewGame.visible = false;
            Res.imgBackNewGame.visible = false;
            this.fAdditionalNewGameBtnDelay = 1000000000f;
            GameScreen.socketWrite("G170\t" + Global.id + "\t" + String.valueOf(this.iAdditionalBaseBet) + "\t" + String.valueOf(Global.money));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->newGameRequest(0)", Util.getExceptionMessage(v0), Util.getExceptionMessage(v0));
            GameScreen.exitApp("???????? ?????? ?? ????????(CNG0).\n?????????? ??????????.");
        }
    }

    private String noToPos(int iNo) {
        String v0 = "";
        if(iNo == 1) {
            v0 = "1";
        }
        else if(iNo == 2) {
            v0 = "2";
        }
        else if(iNo == 3) {
            v0 = "3";
        }
        else if(iNo == 4) {
            v0 = "4";
        }
        else if(iNo == 5) {
            v0 = "5";
        }
        else if(iNo == 6) {
            v0 = "6";
        }
        else if(iNo == 7) {
            v0 = "7";
        }
        else if(iNo == 8) {
            v0 = "8";
        }
        else if(iNo == 9) {
            v0 = "9";
        }
        else if(iNo == 10) {
            v0 = "A";
        }
        else if(iNo == 11) {
            v0 = "B";
        }
        else if(iNo == 12) {
            v0 = "C";
        }
        else if(iNo == 13) {
            v0 = "D";
        }
        else if(iNo == 14) {
            v0 = "E";
        }
        else if(iNo == 15) {
            v0 = "F";
        }
        else if(iNo == 16) {
            v0 = "G";
        }
        else if(iNo == 17) {
            v0 = "H";
        }
        else if(iNo == 18) {
            v0 = "I";
        }
        else {
            Util.sysout("ERROR", "GameScreen->noToPos(1)", "Invalid No.", "iNo=" + String.valueOf(iNo));
        }

        return v0;
    }

    private void onMachineClicked(int i, String sNo) {
        if(this.bCanSelectMachine) {
            int v0 = Util.strToint(sNo, 0);
            if(v0 > 0 && v0 != Global.machineNo) {
                if(this.iMachineState[i] != 0) {
                    this.showMessage("???????????? ???????? ??????????.");
                }
                else {
                    if(Global.machineNo > 0 && Global.spin > 0) {
                        this.showMessage("?????? ???????? ??????\n???? ?????? ?????? ?? ????????.");
                        return;
                    }

                    if(Global.machineNo > 0 && (Global.bet > 0 || Global.betWait > 0)) {
                        this.showMessage("BET?? ???????? ??????\n???? ?????? ?????? ?? ????????.");
                        return;
                    }

                    if("member".equals(Global.cls)) {
                        goto label_37;
                    }

                    this.showMessage("?????? ?????? ?????? ???? ?? ?? ????????.");
                    return;
                label_37:
                    new Dialog() {
                        protected void result(Object object) {
                            if(object.intValue() != 0) {
                                GameScreen.this.showMenu(false);
                                GameScreen.access$28(GameScreen.this, false);
                                GameScreen.socketWrite("G040\t" + Global.id + "\t" + "0" + "\t" + String.valueOf(object));
                            }
                        }
                    }.text("[ " + sNo + " ]?? ?????? \n?????????????????\n").button("??", Integer.valueOf(v0)).button("??????", Integer.valueOf(0)).key(66, Integer.valueOf(0)).key(131, Integer.valueOf(0)).show(GameScreen.stage);
                }
            }
        }
    }

    public void pause() {
        Snd.stopSeaSound();
        Snd.stopAddSound();
    }

    private static void playAndroidSound(String sSound) {
        GameScreen.sendMsgToOuter("play_sound\t" + sSound.replace(".mp3", "").toLowerCase());
    }

    private void playBackgroundMusic() {
        int v4 = 3;
        int v3 = 2;
        if(this.fBackgroundMusic >= 10f) {
            this.fBackgroundMusic = 0f;
            Snd.sndSeaBack1.stop();
            Snd.sndSeaBack2.stop();
            Snd.sndSeaBack3.stop();
            if(this.iBackgroundMusic == v3) {
                if(Util.randomTrue(60)) {
                    Snd.sndSeaBack1.play();
                    this.iBackgroundMusic = 1;
                }
                else {
                    Snd.sndSeaBack3.play();
                    this.iBackgroundMusic = v4;
                }
            }
            else if(!Util.randomTrue(75)) {
                Snd.sndSeaBack2.play();
                this.iBackgroundMusic = v3;
            }
            else if(Util.randomTrue(50)) {
                Snd.sndSeaBack1.play();
                this.iBackgroundMusic = 1;
            }
            else {
                Snd.sndSeaBack3.play();
                this.iBackgroundMusic = v4;
            }

            if(!Util.randomTrue(1)) {
                return;
            }

            this.startMermaid();
        }
    }

    private int posToNo(String sPos) {
        int v0 = 0;
        if("1".equals(sPos)) {
            v0 = 1;
        }
        else if("2".equals(sPos)) {
            v0 = 2;
        }
        else if("3".equals(sPos)) {
            v0 = 3;
        }
        else if("4".equals(sPos)) {
            v0 = 4;
        }
        else if("5".equals(sPos)) {
            v0 = 5;
        }
        else if("6".equals(sPos)) {
            v0 = 6;
        }
        else if("7".equals(sPos)) {
            v0 = 7;
        }
        else if("8".equals(sPos)) {
            v0 = 8;
        }
        else if("9".equals(sPos)) {
            v0 = 9;
        }
        else if("A".equals(sPos)) {
            v0 = 10;
        }
        else if("B".equals(sPos)) {
            v0 = 11;
        }
        else if("C".equals(sPos)) {
            v0 = 12;
        }
        else if("D".equals(sPos)) {
            v0 = 13;
        }
        else if("E".equals(sPos)) {
            v0 = 14;
        }
        else if("F".equals(sPos)) {
            v0 = 15;
        }
        else if("G".equals(sPos)) {
            v0 = 16;
        }
        else if("H".equals(sPos)) {
            v0 = 17;
        }
        else if("I".equals(sPos)) {
            v0 = 18;
        }
        else {
            Util.sysout("ERROR", "GameScreen->posToNo(1)", "Invalid No.", "sNo=" + sPos);
        }

        return v0;
    }

    private void processEvent() {
        float v4 = 1000000000f;
        int v5 = 150;
        if(!Global.bTerminating && Dialog.count <= 0 && this.fToastTime <= 0f && (this.bGameScreenReady) && GameScreen.fWelcomeCelemony <= 0f && this.fWinCelemonyTime <= 0f) {
            if((Gdx.input.isKeyPressed(4)) && !Global.bAskingGameExit && !Global.bTerminating) {
                this.askExitGame();
            }

            if(!this.bCanSelectMachine) {
                return;
            }

            if(!this.bCanInsert10000) {
                return;
            }

            if(!this.bCanSwitchMoney) {
                return;
            }

            if(!this.bCanUpDownChannel) {
                return;
            }

            if(!this.bCanSelectMachine) {
                return;
            }

            if(this.bEnding) {
                return;
            }

            if(GameScreen.bReserving) {
                return;
            }

            if(!this.bCanRequestScenario) {
                return;
            }

            if(this.bWaitingScenario) {
                return;
            }

            if(this.bWaitingAdditionalGame) {
                return;
            }

            if(!Gdx.input.justTouched()) {
                return;
            }

            Vector3 v8 = Vector3.Zero;
            this.camera.unproject(v8.set(((float)Gdx.input.getX()), ((float)Gdx.input.getY()), 0f));
            if(this.bAdditionalGame) {
                if((Util.isTouched(Res.btnAdditionalChoice1000.getBoundingRectangle(), v8.x, v8.y)) && (Res.btnAdditionalChoice1000.visible)) {
                    if(Dialog.count > 0) {
                        return;
                    }
                    else if(Global.money < 2000) {
                        this.showMessage("?????????? ??????????.");
                        this.bAdditionalGame = false;
                        this.iAdditionalBaseBet = 0;
                        Snd.stopAddSound();
                        return;
                    }
                    else if(this.iAdditionalBaseBet <= 0) {
                        this.clickBaseBet(2000);
                    }
                }

                if((Util.isTouched(Res.btnAdditionalChoice2000.getBoundingRectangle(), v8.x, v8.y)) && (Res.btnAdditionalChoice2000.visible)) {
                    if(Dialog.count > 0) {
                        return;
                    }
                    else if(Global.money < 10000) {
                        this.showMessage("?????????? ??????????.");
                        this.bAdditionalGame = false;
                        this.iAdditionalBaseBet = 0;
                        Snd.stopAddSound();
                        return;
                    }
                    else if(this.iAdditionalBaseBet >= 0) {
                        this.clickBaseBet(10000);
                    }
                }

                if(Util.isTouched(Res.btnLow.getBoundingRectangle(), v8.x, v8.y)) {
                    if("x".equals(Res.btnLow.name)) {
                        return;
                    }
                    else if(Dialog.count > 0) {
                        return;
                    }
                    else if(this.iAdditionalBaseBet > 0) {
                        this.clickHighLow("L");
                    }
                    else {
                        return;
                    }
                }

                if(Util.isTouched(Res.btnHigh.getBoundingRectangle(), v8.x, v8.y)) {
                    if("x".equals(Res.btnHigh.name)) {
                        return;
                    }
                    else if(Dialog.count > 0) {
                        return;
                    }
                    else if(this.iAdditionalBaseBet > 0) {
                        this.clickHighLow("H");
                    }
                    else {
                        return;
                    }
                }

                if(Util.isTouched(Res.btnGo.getBoundingRectangle(), v8.x, v8.y)) {
                    if("x".equals(Res.btnGo.name)) {
                        return;
                    }
                    else if(Dialog.count > 0) {
                        return;
                    }
                    else if(this.iAdditionalBaseBet > 0) {
                        this.clickGo();
                    }
                    else {
                        return;
                    }
                }

                if(Util.isTouched(Res.btnStop.getBoundingRectangle(), v8.x, v8.y)) {
                    if("x".equals(Res.btnStop.name)) {
                        return;
                    }
                    else if(Dialog.count > 0) {
                        return;
                    }
                    else if(this.iAdditionalBaseBet > 0) {
                        this.clickStop();
                    }
                    else {
                        return;
                    }
                }

                if(!Util.isTouched(Res.btnNewGame.getBoundingRectangle(), v8.x, v8.y)) {
                    goto label_190;
                }

                if(!Res.btnNewGame.visible) {
                    return;
                }

                if(Dialog.count > 0) {
                    return;
                }

                if(this.iAdditionalBaseBet <= 0) {
                    return;
                }

                this.newGameRequest();
            }

        label_190:
            if(!GameScreen.bShowMenu && !this.bAdditionalGame) {
                if(Util.isTouched(Res.btnMenu.getBoundingRectangle(), v8.x, v8.y)) {
                    if(Dialog.count <= 0) {
                        this.clickEffect(111, 134, v5);
                        this.showMenu(true);
                    }
                    else {
                        return;
                    }
                }
                else if(!Util.isTouched(Res.btnSwitch.getBoundingRectangle(), v8.x, v8.y)) {
                    if(!Util.isTouched(Res.btnReserve.getBoundingRectangle(), v8.x, v8.y)) {
                        goto label_455;
                    }

                    if(Dialog.count > 0) {
                        return;
                    }

                    if(!this.bCanReserveMachine) {
                        return;
                    }

                    if(GameScreen.bReserving) {
                        return;
                    }

                    this.clickEffect(111, 246, v5);
                    if(Global.machineNo > 0) {
                        goto label_383;
                    }

                    this.showMessage("???? ?????? ??????\n ?????? ????????.");
                    return;
                label_383:
                    if(Global.spin <= 0 && Global.bet <= 0) {
                        this.showMessage("???????? BET?? ????\n ???????? ?????? ?? ????????.");
                        return;
                    }

                    if("member".equals(Global.cls)) {
                        goto label_397;
                    }

                    this.showMessage("?????? ?????? ?????? ???? ?? ?? ????????.");
                    return;
                label_397:
                    if(Global.reserve > 0) {
                        goto label_427;
                    }

                    new Dialog() {
                        protected void result(Object object) {
                            if(((Boolean)object).booleanValue()) {
                                GameScreen.access$35(GameScreen.this, false);
                                GameScreen.access$36(true);
                                GameScreen.socketWrite("G080\t" + Global.id + "\t" + "0" + "\t" + String.valueOf(Global.machineNo) + "\t" + "1" + "\t" + "0");
                            }
                        }
                    }.text("[" + String.valueOf(Global.machineNo) + "]?? ?????? ?????????????????").button("   ??   ", Boolean.valueOf(true)).button("??????", Boolean.valueOf(false)).key(66, Boolean.valueOf(false)).key(131, Boolean.valueOf(false)).show(GameScreen.stage);
                    goto label_206;
                label_427:
                    new Dialog() {
                        protected void result(Object object) {
                            if(((Boolean)object).booleanValue()) {
                                GameScreen.access$35(GameScreen.this, false);
                                GameScreen.access$36(true);
                                GameScreen.socketWrite("G080\t" + Global.id + "\t" + "0" + "\t" + String.valueOf(Global.machineNo) + "\t" + "0" + "\t" + "0");
                            }
                        }
                    }.text("[" + String.valueOf(Global.machineNo) + "]?? ?????? ?????? ?????????????????").button("   ??   ", Boolean.valueOf(true)).button("??????", Boolean.valueOf(false)).key(66, Boolean.valueOf(false)).key(131, Boolean.valueOf(false)).show(GameScreen.stage);
                    goto label_206;
                label_455:
                    if(!Util.isTouched(Res.imgChatBack.getBoundingRectangle(), v8.x, v8.y)) {
                        goto label_493;
                    }

                    if(this.iChatFlag != 1) {
                        goto label_478;
                    }

                    this.iChatFlag = 0;
                    this.scpMsg.setVisible(false);
                    if(!"D".equals(this.fsBackgroundDayNight)) {
                        goto label_474;
                    }

                    Res.imgInter.setImage("normal");
                    goto label_206;
                label_474:
                    Res.imgInter.setImage("red");
                    goto label_206;
                label_478:
                    this.iChatFlag = 1;
                    this.scpMsg.setVisible(true);
                    if(!"D".equals(this.fsBackgroundDayNight)) {
                        goto label_489;
                    }

                    Res.imgInter.setImage("normal_chat");
                    goto label_206;
                label_489:
                    Res.imgInter.setImage("red_chat");
                    goto label_206;
                label_493:
                    if(!Util.isTouched(Res.btnCoin.getBoundingRectangle(), v8.x, v8.y)) {
                        goto label_559;
                    }

                    if(!this.bCanInsert10000) {
                        return;
                    }

                    if(Dialog.count > 0) {
                        return;
                    }

                    this.clickEffect(114, 332, v5);
                    if(Global.money < 10000 && Global.money > 0) {
                        this.showMessage("?????????? ??????????.\n?????? ??????????????.");
                        return;
                    }

                    if(Global.money >= 10000) {
                        goto label_520;
                    }

                    this.showMessage("?????????? ??????????.");
                    return;
                label_520:
                    if("member".equals(Global.cls)) {
                        goto label_527;
                    }

                    this.showMessage("?????? ?????? ?????? ???? ?? ?? ????????.");
                    return;
                label_527:
                    if(Global.machineNo > 0) {
                        goto label_532;
                    }

                    this.showMessage("[????????]?????? ????????\n ?????? ???? ??????????.");
                    return;
                label_532:
                    new Dialog() {
                        protected void result(Object object) {
                            if(((Boolean)object).booleanValue()) {
                                GameScreen.access$37(GameScreen.this, false);
                                GameScreen.socketWrite("G050\t" + Global.id + "\t" + Global.machineNo);
                            }
                        }
                    }.text("[" + Global.machineNo + "]?? ?????? \n?????? ?????????????????").button("   ??   ", Boolean.valueOf(true)).button("??????", Boolean.valueOf(false)).key(66, Boolean.valueOf(false)).key(131, Boolean.valueOf(false)).show(GameScreen.stage);
                    goto label_206;
                label_559:
                    if(!Util.isTouched(Res.btnAdditional.getBoundingRectangle(), v8.x, v8.y)) {
                        goto label_206;
                    }

                    if(Dialog.count > 0) {
                        return;
                    }

                    if(this.bAdditionalGame) {
                        return;
                    }

                    if(Global.money >= 2000) {
                        goto label_575;
                    }

                    this.showMessage("?????????? ??????????.");
                    return;
                label_575:
                    this.clickEffect(111, 134, v5, false);
                    this.bAdditionalGame = true;
                    this.iAdditionalBaseBet = 0;
                    this.fAdditionalBaseBetTimer = 4.5f;
                    this.fEndAdditionalGameDelay = v4;
                    Res.aniAdditionalWLT.visible = false;
                    Res.btnNewGame.visible = false;
                    Res.imgBackNewGame.visible = false;
                    this.showHideGameLabel();
                    this.iAdditionalTimer = 999999999;
                    this.fAdditionalTimer = v4;
                    this.bAddHurryUp1 = false;
                    this.bAddHurryUp2 = false;
                    this.bAddSexySnd1 = false;
                    this.bAddSexySnd2 = false;
                    this.bAddSexySnd3 = false;
                    this.bAdditionalWinCeremony = false;
                    this.fAdditionalWinCeremonyMoneyTimer = -1000000000f;
                    this.fAdditionalBeep = -1000000000f;
                    this.fAdditionalNewGameBtnDelay = v4;
                    this.fEndAdditionalGameDelay = v4;
                    this.iAdditionalWinMoney = 0;
                    Res.btnAdditionalChoice1000.visible = true;
                    Res.btnAdditionalChoice2000.visible = true;
                    Snd.stopAddSound();
                    Snd.sndAddChoiceBack.setLooping(true);
                    Snd.sndAddChoiceBack.play();
                    Snd.stopSeaSound();
                }
                else if(Dialog.count > 0) {
                    return;
                }
                else if(this.bCanSwitchMoney) {
                    this.clickEffect(111, 190, v5);
                    if(Global.gift > 0) {
                        String v7 = "";
                        if(Global.machineNo > 0) {
                            v7 = "[" + Global.machineNo + "]?? ?????? ";
                        }

                        new Dialog() {
                            protected void result(Object object) {
                                if(((Boolean)object).booleanValue()) {
                                    GameScreen.access$34(GameScreen.this, false);
                                    GameScreen.socketWrite("G060\t" + Global.id + "\t" + Global.machineNo + "\t" + "0" + "\t" + Global.gift);
                                }
                            }
                        }.text(String.valueOf(v7) + "????????\n ?????????? ?????????????????").button("   ??   ", Boolean.valueOf(true)).button("??????", Boolean.valueOf(false)).key(66, Boolean.valueOf(false)).key(131, Boolean.valueOf(false)).show(GameScreen.stage);
                    }
                    else if(Global.machineNo > 0) {
                        this.showMessage("[" + Global.machineNo + "]?? ?????? ?????? ???????? ????????.");
                        return;
                    }
                    else {
                        this.showMessage("?????? ???????? ????????.");
                        return;
                    }
                }
                else {
                    return;
                }
            }

        label_206:
            if(!GameScreen.bShowMenu) {
                return;
            }

            if(this.bAdditionalGame) {
                return;
            }

            if(Util.isTouched(Res.imgBtnMachineOn.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnMachineOn.x, Res.imgBtnMachineOn.y, Res.imgBtnMachineOn.width, Res.imgBtnMachineOn.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.changeMenuItem(5);
            }
            else if(Util.isTouched(Res.imgBtnChargeOn.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnChargeOn.x, Res.imgBtnChargeOn.y, Res.imgBtnChargeOn.width, Res.imgBtnChargeOn.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.changeMenuItem(1);
            }
            else if(Util.isTouched(Res.imgBtnExchangeOn.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnExchangeOn.x, Res.imgBtnExchangeOn.y, Res.imgBtnExchangeOn.width, Res.imgBtnExchangeOn.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.changeMenuItem(2);
            }
            else if(Util.isTouched(Res.imgBtnHelpOn.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnHelpOn.x, Res.imgBtnHelpOn.y, Res.imgBtnHelpOn.width, Res.imgBtnHelpOn.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.changeMenuItem(3);
            }
            else if(Util.isTouched(Res.imgBtnAskOn.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnAskOn.x, Res.imgBtnAskOn.y, Res.imgBtnAskOn.width, Res.imgBtnAskOn.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.changeMenuItem(4);
                GameScreen.socketWrite("G140\t" + Global.id + "\t");
            }
            else if(Util.isTouched(Res.imgBtnCloseMenu.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgBtnCloseMenu.x, Res.imgBtnCloseMenu.y, Res.imgBtnCloseMenu.width, Res.imgBtnCloseMenu.height, v5);
                Gdx.input.setOnscreenKeyboardVisible(false);
                this.showMenu(false);
            }

            if(this.iMenuMode != 5) {
                goto label_730;
            }

            if(Util.isTouched(Res.imgChannelLeft.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgChannelLeft.x, Res.imgChannelLeft.y, Res.imgChannelLeft.width, Res.imgChannelLeft.height, v5);
                this.machineListRequest(-1);
            }

            if(Util.isTouched(Res.imgChannelRight.getBoundingRectangle(), v8.x, v8.y)) {
                this.clickEffect(Res.imgChannelRight.x, Res.imgChannelRight.y, Res.imgChannelRight.width, Res.imgChannelRight.height, v5);
                this.machineListRequest(1);
            }

            int v6;
            for(v6 = 0; true; ++v6) {
                if(v6 >= 12) {
                    return;
                }

                if(!Util.isTouched(Res.machineBack[v6].getBoundingRectangle(), v8.x, v8.y)) {
                    goto label_728;
                }

                this.clickEffect(Res.machineBack[v6].x, Res.machineBack[v6].y, Res.machineBack[v6].width, Res.machineBack[v6].height, v5);
                this.onMachineClicked(v6, this.lbMachineNo[v6].getText().toString().trim());
                return;
            label_728:
            }

        label_730:
            if(this.iMenuMode != 1) {
                goto label_767;
            }

            if(Util.isTouched(Res.imgBtnMoneyMan.getBoundingRectangle(), v8.x, v8.y)) {
                Gdx.input.getTextInput(new TextInputListener() {
                    public void canceled() {
                    }

                    public void input(String text) {
                        if(text != null) {
                            text = text.trim();
                        }

                        Gdx.input.setOnscreenKeyboardVisible(false);
                        GameScreen.this.lbChargeMan.setText(((CharSequence)text));
                    }
                }, "?????????? ???????? ?? OK ?????? ??????????.", "", this.lbChargeMan.getText().toString().trim());
            }

            if(!Util.isTouched(Res.imgBtnCharge.getBoundingRectangle(), v8.x, v8.y)) {
                return;
            }

            this.clickEffect(Res.imgBtnCharge.x, Res.imgBtnCharge.y, Res.imgBtnCharge.width, Res.imgBtnCharge.height, v5);
            Gdx.input.setOnscreenKeyboardVisible(false);
            this.charge();
            return;
        label_767:
            if(this.iMenuMode != 2) {
                goto label_820;
            }

            if(Util.isTouched(Res.imgBtnExchangeBank.getBoundingRectangle(), v8.x, v8.y)) {
                Gdx.input.getTextInput(new TextInputListener() {
                    public void canceled() {
                    }

                    public void input(String text) {
                        if(text != null) {
                            text = text.trim();
                        }

                        GameScreen.this.lbExchangeBank.setText(((CharSequence)text));
                    }
                }, "???????? ???????? ?? OK ?????? ??????????.", "", this.lbExchangeBank.getText().toString().trim());
            }

            if(Util.isTouched(Res.imgBtnExchangeOwner.getBoundingRectangle(), v8.x, v8.y)) {
                Gdx.input.getTextInput(new TextInputListener() {
                    public void canceled() {
                    }

                    public void input(String text) {
                        if(text != null) {
                            text = text.trim();
                        }

                        GameScreen.this.lbExchangeOwner.setText(((CharSequence)text));
                    }
                }, "???????? ???????? ?? OK ?????? ??????????.", "", this.lbExchangeOwner.getText().toString().trim());
            }

            if(!Util.isTouched(Res.imgBtnExchange.getBoundingRectangle(), v8.x, v8.y)) {
                return;
            }

            this.clickEffect(Res.imgBtnExchange.x, Res.imgBtnExchange.y, Res.imgBtnExchange.width, Res.imgBtnExchange.height, v5);
            Gdx.input.setOnscreenKeyboardVisible(false);
            this.exchange();
            return;
        label_820:
            if(this.iMenuMode == 3) {
                return;
            }

            if(this.iMenuMode != 4) {
                return;
            }

            if(!Util.isTouched(Res.imgBtnAskInput.getBoundingRectangle(), v8.x, v8.y)) {
                return;
            }

            this.clickEffect(Res.imgBtnAskInput.x, Res.imgBtnAskInput.y, Res.imgBtnAskInput.width, Res.imgBtnAskInput.height, v5);
            Gdx.input.getTextInput(new TextInputListener() {
                public void canceled() {
                }

                public void input(String text) {
                    int v2 = 100;
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    if(text == null) {
                        text = "";
                    }

                    text = text.trim();
                    if(!"".equals(text)) {
                        if(text.length() > v2) {
                            text = text.substring(0, v2);
                        }

                        GameScreen.this.sendAsk(text);
                    }
                }
            }, "?????????? ???????? ?? OK ?????? ??????????.", "", "");
        }
    }

    private void processMsg() {  // has try-catch handlers
        if(GameScreen.lsGameRead.size() <= 0) {
            return;
        }

        try {
            Object v3 = GameScreen.lsGameRead.get(0);
            GameScreen.lsGameRead.remove(0);
            String[] v0 = ((String)v3).split("\t");
            String v1 = v0[0];
            if(!"G520".equals(v1)) {
                goto label_18;
            }

            this.fn_game_info(v0);
            return;
        label_18:
            if(!"G526".equals(v1)) {
                goto label_29;
            }

            this.fn_end_scenario_success(v0);
            return;
        label_29:
            if(!"G527".equals(v1)) {
                goto label_34;
            }

            this.fn_end_scenario_failure(v0);
            return;
        label_34:
            if(!"G522".equals(v1)) {
                goto label_39;
            }

            this.fn_scenario1(v0);
            return;
        label_39:
            if(!"G525".equals(v1)) {
                goto label_44;
            }

            this.fn_game_info_failure(v0);
            return;
        label_44:
            if(!"G530".equals(v1)) {
                goto label_49;
            }

            this.fn_scenario_success(v0);
            return;
        label_49:
            if(!"G540".equals(v1)) {
                goto label_54;
            }

            this.fn_scenario_failure(v0);
            return;
        label_54:
            if(!"show_ui".equals(v1)) {
                goto label_60;
            }

            GameScreen.sendMsgToOuter("show_ui");
            return;
        label_60:
            if(!"hide_ui".equals(v1)) {
                goto label_66;
            }

            GameScreen.sendMsgToOuter("hide_ui");
            return;
        label_66:
            if(!"G550".equals(v1)) {
                goto label_71;
            }

            this.fn_machine_list(v0);
            return;
        label_71:
            if(!"G560".equals(v1)) {
                goto label_76;
            }

            this.fn_machine_select(v0);
            return;
        label_76:
            if(!"G570".equals(v1)) {
                goto label_81;
            }

            this.fn_machine_select_failure(v0);
            return;
        label_81:
            if(!"G580".equals(v1)) {
                goto label_86;
            }

            this.fn_insert_10000_success(v0);
            return;
        label_86:
            if(!"G590".equals(v1)) {
                goto label_91;
            }

            this.fn_insert_10000_failure(v0);
            return;
        label_91:
            if(!"G610".equals(v1)) {
                goto label_96;
            }

            this.fn_switch_money_success(v0);
            return;
        label_96:
            if(!"G620".equals(v1)) {
                goto label_101;
            }

            this.fn_switch_money_failure(v0);
            return;
        label_101:
            if(!"G710".equals(v1)) {
                goto label_106;
            }

            this.fn_charge_completed(v0);
            return;
        label_106:
            if(!"G715".equals(v1)) {
                goto label_111;
            }

            this.fn_exchange_completed(v0);
            return;
        label_111:
            if(!"G600".equals(v1)) {
                goto label_116;
            }

            this.fn_coin_info(v0);
            return;
        label_116:
            if(!"G650".equals(v1)) {
                goto label_121;
            }

            this.fn_reserve_machine_success(v0);
            return;
        label_121:
            if(!"G660".equals(v1)) {
                goto label_126;
            }

            this.fn_reserve_machine_failure(v0);
            return;
        label_126:
            if(!"G700".equals(v1)) {
                goto label_131;
            }

            this.fn_confirm_reserve(v0);
            return;
        label_131:
            if(!"G680".equals(v1)) {
                goto label_136;
            }

            this.fn_announce(v0);
            return;
        label_136:
            if(!"G690".equals(v1)) {
                goto label_141;
            }

            this.fn_private_announce(v0);
            return;
        label_141:
            if(!"G740".equals(v1)) {
                goto label_146;
            }

            this.fn_ask(v0);
            return;
        label_146:
            if(!"G790".equals(v1)) {
                goto label_151;
            }

            this.fn_bank(v0);
            return;
        label_151:
            if(!"G800".equals(v1)) {
                goto label_156;
            }

            this.fn_report(v0);
            return;
        label_156:
            if(!"G810".equals(v1)) {
                goto label_161;
            }

            this.fn_additional_game_info(v0);
            return;
        label_161:
            if(!"G820".equals(v1)) {
                goto label_166;
            }

            this.fn_additional_time_over(v0);
            return;
        label_166:
            if(!"G830".equals(v1)) {
                goto label_171;
            }

            this.fn_additional_stop(v0);
            return;
        label_171:
            if(!"G840".equals(v1)) {
                goto label_176;
            }

            this.fn_additional_high_low(v0);
            return;
        label_176:
            if(!"G850".equals(v1)) {
                goto label_181;
            }

            this.fn_soojo_info(v0);
            return;
        label_181:
            Util.sysout("ERROR", "GameScreen->processMsg", "Undefined Cmd.", ((String)v3));
        }
        catch(Exception v2) {
            Util.sysout("ERROR", "GameScreen->processMsg", Util.getExceptionMessage(v2));
        }
    }

    private void processScenario() {  // has try-catch handlers
        try {
            if(Global.machineNo <= 0) {
                return;
            }

            if(Global.spin <= 0) {
                return;
            }

            if(this.fNextScenarioDelay > 0f) {
                return;
            }

            if(this.isReelRunning()) {
                return;
            }

            if(Global.reserve > 0) {
                return;
            }

            if(Global.bAskingGameExit) {
                return;
            }

            if(Global.bTerminating) {
                return;
            }

            if(GameScreen.bWelcomeCelemony) {
                return;
            }

            if(this.fWinCelemonyTime > 0f) {
                return;
            }

            if(this.fiPlusWinMoney > 0) {
                return;
            }

            if(this.bEnding) {
                return;
            }

            if(!this.bWaitingScenario) {
                goto label_26;
            }

            return;
        label_26:
            if(this.bStopScenarioForTest) {
                return;
            }

            if(this.lsScenario.size() <= 0 && (this.bCanRequestScenario)) {
                this.requestScenario();
                return;
            }

            if(this.lsScenario.size() <= 0) {
                return;
            }

            Object v11 = this.lsScenario.get(0);
            if("1".equals(Global.viewScenario)) {
                this.addMsg(((String)v11), Color.WHITE, 0);
            }

            String v13 = this.getValue("T", ((String)v11));
            String v10 = this.getValue("D", ((String)v11));
            String v5 = this.getValue("F", ((String)v11));
            if(!"I".equals(v13)) {
                goto label_67;
            }

            if(!this.reelIdle()) {
                return;
            }

            this.setBackGround(v10);
            return;
        label_67:
            if(!"S".equals(v13)) {
                goto label_94;
            }

            String[] v7 = this.getValue("M", ((String)v11)).split(",");
            int v6 = Integer.parseInt(v7[4]);
            if(v6 > 200) {
                v6 = 200;
            }

            if(!this.reelAllot(v7[1], Integer.parseInt(v7[3]), v7[0], v7[2], v5, v6)) {
                return;
            }

            this.setBackGround(v10);
            return;
        label_94:
            if(!"O".equals(v13)) {
                return;
            }

            this.showOmen(this.getValue("X", ((String)v11)));
        }
        catch(Exception v8) {
            Util.sysout("ERROR", "GameScreen->processScenario(8)", Util.getExceptionMessage(v8));
        }
    }

    private boolean reelAllot(String _sTargetObj, int _iCTB, String _sPos, String _sMask, String _sFire, int _iMultiple) {
        int v7 = 3;
        int v6 = -1;
        int v5 = 2;
        boolean v0 = false;
        if(!this.bAdditionalGame && !this.isReelRunning() && Global.reserve <= 0 && Global.spin > 0 && !Global.bAskingGameExit && !Global.bTerminating && this.fNextScenarioDelay <= 0f && this.iFireType <= 0 && !GameScreen.bReserving && !this.bEnding && !this.bWaitingScenario) {
            this.playBackgroundMusic();
            this.iniReel();
            this.fsTargetObj = _sTargetObj;
            this.fiCTB = _iCTB;
            this.fsPos = _sPos;
            this.fsMask = _sMask;
            this.fsFire = _sFire;
            this.fiMultiple = _iMultiple;
            this.reelMode = 1;
            this.iTargetCardNo[0] = Util.strToint(_sPos.substring(0, v5), v6);
            this.iTargetCardNo[1] = Util.strToint(_sPos.substring(v5, 4), v6);
            this.iTargetCardNo[v5] = Util.strToint(_sPos.substring(4, 6), v6);
            this.iTargetCardNo[v7] = Util.strToint(_sPos.substring(6, 8), v6);
            this.fReelDeceleratingStartTime[0] = 1.5f;
            this.fReelDeceleratingStartTime[1] = 2.3f;
            this.fReelDeceleratingStartTime[v5] = 2.7f;
            this.fReelDeceleratingStartTime[v7] = 1.9f;
            this.fReelPoppingStartTime[0] = 1.8f;
            this.fReelPoppingStartTime[1] = 2.5f;
            this.fReelPoppingStartTime[v5] = 2.9f;
            this.fReelPoppingStartTime[v7] = 2.1f;
            v0 = true;
        }

        return v0;
    }

    private boolean reelIdle() {
        int v6 = 3;
        int v5 = 2;
        boolean v1 = false;
        if(!this.bAdditionalGame && !this.isReelRunning() && Global.reserve <= 0 && Global.spin > 0 && !Global.bAskingGameExit && !Global.bTerminating && this.fNextScenarioDelay <= 0f && this.iFireType <= 0 && !GameScreen.bReserving && !this.bEnding && !this.bWaitingScenario) {
            this.playBackgroundMusic();
            this.iniReel();
            this.reelMode = 0;
            this.setReelIdleTargetNo();
            float v0 = 0f;
            if(Util.randomTrue(5)) {
                v0 = 1.5f;
            }

            this.fReelDeceleratingStartTime[0] = 1.3f + v0;
            this.fReelDeceleratingStartTime[1] = 2.1f + v0;
            this.fReelDeceleratingStartTime[v5] = 2.5f + v0;
            this.fReelDeceleratingStartTime[v6] = 1.7f + v0;
            this.fReelPoppingStartTime[0] = 1.5f + v0;
            this.fReelPoppingStartTime[1] = 2.3f + v0;
            this.fReelPoppingStartTime[v5] = 2.7f + v0;
            this.fReelPoppingStartTime[v6] = 1.9f + v0;
            v1 = true;
        }

        return v1;
    }

    public void render(float _deltaTime) {
        float v1;
        int v10 = 3;
        int v9 = 2;
        this.deltaTime = _deltaTime;
        if(_deltaTime > 0.055556f) {
            this.deltaTime = 0.055556f;
        }

        this.elapseTime += this.deltaTime;
        GameScreen.fWelcomeCelemony -= this.deltaTime;
        this.fWinCelemonyTime -= this.deltaTime;
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(16384);
        GameScreen.batch.setProjectionMatrix(this.camera.combined);
        GameScreen.color = GameScreen.batch.getColor();
        this.screenShaker.update(this.camera, this.deltaTime);
        this.camera.update();
        GameScreen.batch.begin();
        if(this.bGameScreenReady) {
            this.updateCards();
        }

        this.drawBackground();
        this.fBackLightTimer += _deltaTime;
        if(this.fBackLightTimer > 3f) {
            this.fBackLightTimer = 0f;
            if(Util.randomTrue(50)) {
                this.showBackLight();
            }
        }

        if(this.fiBackLight < 0) {
            v1 = GameScreen.color.a;
            this.fBackLightAlpha += 0.5f * _deltaTime;
            if(this.fBackLightAlpha > 1f) {
                this.fiBackLight = 1;
            }
            else {
                GameScreen.color.a = this.fBackLightAlpha;
                GameScreen.batch.setColor(GameScreen.color);
                Res.imgBackLight.draw();
                GameScreen.color.a = v1;
                GameScreen.batch.setColor(GameScreen.color);
            }
        }

        if(this.fiBackLight > 0) {
            v1 = GameScreen.color.a;
            this.fBackLightAlpha -= 0.5f * _deltaTime;
            if(this.fBackLightAlpha < 0f) {
                this.fiBackLight = 0;
                Res.imgBackLight.visible = false;
            }
            else {
                GameScreen.color.a = this.fBackLightAlpha;
                GameScreen.batch.setColor(GameScreen.color);
                Res.imgBackLight.draw();
                GameScreen.color.a = v1;
                GameScreen.batch.setColor(GameScreen.color);
            }
        }

        if(this.fiDayNightFlag < 0) {
            v1 = GameScreen.color.a;
            this.fDayNightAlpha += 0.25f * _deltaTime;
            GameScreen.color.a = this.fDayNightAlpha;
            GameScreen.batch.setColor(GameScreen.color);
            Res.imgDayNight.draw();
            GameScreen.color.a = v1;
            GameScreen.batch.setColor(GameScreen.color);
            if(this.fDayNightAlpha >= 1f) {
                this.fiDayNightFlag = 0;
            }
        }
        else if(this.fiDayNightFlag > 0) {
            v1 = GameScreen.color.a;
            this.fDayNightAlpha -= 0.25f * _deltaTime;
            GameScreen.color.a = this.fDayNightAlpha;
            GameScreen.batch.setColor(GameScreen.color);
            Res.imgDayNight.draw();
            GameScreen.color.a = v1;
            GameScreen.batch.setColor(GameScreen.color);
            if(this.fDayNightAlpha <= 0f) {
                this.fiDayNightFlag = 0;
            }
        }
        else if("N".equals(this.fsBackgroundDayNight)) {
            Res.imgDayNight.draw();
        }

        int v0;
        for(v0 = 0; v0 < 10; ++v0) {
            Res.fishRight[v0].draw(_deltaTime);
            Res.fishLeft[v0].draw(_deltaTime);
        }

        for(v0 = 0; v0 < 10; ++v0) {
            Res.turtleRight[v0].draw(_deltaTime);
            Res.turtleLeft[v0].draw(_deltaTime);
        }

        for(v0 = 0; v0 < 15; ++v0) {
            Res.jellyfish[v0].draw(_deltaTime);
        }

        if(Res.aniMermaid.visible) {
            Res.aniMermaid.draw(_deltaTime);
            Res.aniMermaid.x -= 0.2f * _deltaTime;
            if(Res.aniMermaid.x < -1f) {
                Res.aniMermaid.visible = false;
            }
        }

        Res.fin[0].draw(_deltaTime);
        Res.fin[1].draw(_deltaTime);
        Res.shark[0].draw(_deltaTime);
        Res.shark[1].draw(_deltaTime);
        Res.shark[v9].draw(_deltaTime);
        Res.shark[v10].draw(_deltaTime);
        if(!GameScreen.bShowMenu && Dialog.count <= 0) {
            this.drawCards();
            Res.aniLine.draw(_deltaTime);
        }

        this.drawItem();
        Res.gfFish[0].draw(_deltaTime);
        Res.gfFish[1].draw(_deltaTime);
        Res.gfFish[v9].draw(_deltaTime);
        Res.spinAll[0].draw(_deltaTime);
        Res.spinAll[1].draw(_deltaTime);
        Res.spinAll[v9].draw(_deltaTime);
        Res.imgPrize.draw(_deltaTime);
        for(v0 = 0; v0 < Res.pin.length; ++v0) {
            Res.pin[v0].draw(_deltaTime);
        }

        for(v0 = 0; v0 < 30; ++v0) {
            Res.coin[v0].draw(_deltaTime);
        }

        Res.aniEventBack.draw(_deltaTime);
        Res.aniEvent.draw(_deltaTime);
        if(!GameScreen.bShowMenu && Dialog.count <= 0) {
            Res.aniReserveBack.draw(this.deltaTime);
        }

        this.drawInter();
        Res.aniLineLampLT.draw(_deltaTime);
        Res.aniLineLampLC.draw(_deltaTime);
        Res.aniLineLampLB.draw(_deltaTime);
        Res.aniLineLampRT.draw(_deltaTime);
        Res.aniLineLampRC.draw(_deltaTime);
        Res.aniLineLampRB.draw(_deltaTime);
        Res.aniMiddleEvent[0].draw(_deltaTime);
        Res.aniMiddleEvent[1].draw(_deltaTime);
        Res.aniMiddleEvent[v9].draw(_deltaTime);
        Res.aniMiddleEvent[v10].draw(_deltaTime);
        Res.bubbleLeft.draw(_deltaTime);
        Res.bubbleRight.draw(_deltaTime);
        Res.bubbleCenter1.draw(_deltaTime);
        Res.bubbleCenter2.draw(_deltaTime);
        Res.aniPostLeft.draw(_deltaTime);
        Res.aniPostRight.draw(_deltaTime);
        if(this.iDropperDirection > 0) {
            if(Res.aniDropper.x <= Util.getLeft(468f)) {
                Res.aniDropper.x += this.deltaTime * 0.2f;
            }
            else {
                this.iDropperDirection = -1;
            }
        }
        else if(this.iDropperDirection <= 0) {
            if(Res.aniDropper.x <= Util.getLeft(50f)) {
                this.iDropperDirection = 1;
            }
            else {
                Res.aniDropper.x -= this.deltaTime * 0.2f;
            }
        }

        Res.aniDropper.draw(_deltaTime);
        GameScreen.lbBet.setX(Util.getActorLeft((Res.aniDropper.x + 0.5f) * Util.standard_screen_pixel_width));
        Res.btnCoin.draw();
        Res.btnReserve.draw(this.deltaTime);
        Res.btnMenu.draw(this.deltaTime);
        Res.btnAdditional.draw(this.deltaTime);
        Res.btnSwitch.draw();
        Res.aniBackGift.draw(this.deltaTime);
        Res.aniBackSpin.draw(this.deltaTime);
        Res.aniBackNo.draw(this.deltaTime);
        Res.aniBackMoney.draw(this.deltaTime);
        if(GameScreen.bShowMenu) {
            Res.imgBackMenu1.draw();
            Res.imgBackMenu2.draw();
            if(this.iMenuMode == 5) {
                Res.imgBtnMachineOn.draw();
                Res.imgChannelLeft.draw();
                Res.imgChannelRight.draw();
                v0 = 0;
                goto label_306;
            }
            else if(this.iMenuMode == 1) {
                Res.imgBtnChargeOn.draw();
                Res.imgBackCharge.draw();
                Res.imgBtnCharge.draw();
            }
            else if(this.iMenuMode == v9) {
                Res.imgBtnExchangeOn.draw();
                Res.imgBackExchange.draw();
                Res.imgBtnExchange.draw();
            }
            else if(this.iMenuMode == v10) {
                Res.imgBtnHelpOn.draw();
                Res.imgBackHelp.draw();
            }
            else if(this.iMenuMode == 4) {
                Res.imgBtnAskOn.draw();
                Res.imgBtnAskInput.draw();
                Res.imgBackAsk.draw();
                goto label_308;
            label_306:
                while(v0 < 12) {
                    Res.machineBack[v0].draw();
                    Res.machineScreen[v0].draw(this.deltaTime);
                    ++v0;
                }
            }
        }

    label_308:
        if(this.bAdditionalGame) {
            if(this.iAdditionalBaseBet <= 0) {
                Res.imgAdditionalChoiceBack1.draw();
                Res.aniAdditionalChoiceBack2.draw(_deltaTime);
                Res.btnAdditionalChoice1000.draw(_deltaTime);
                Res.btnAdditionalChoice2000.draw(_deltaTime);
            }
            else {
                Res.imgAdditionalBack1.draw();
                Res.imgAdditionalBack2.draw();
                Res.aniAddLeftLight.draw(_deltaTime);
                Res.aniAddRightLight.draw(_deltaTime);
                Res.imgAdditionalTimer.draw();
                Res.aniLady.draw(_deltaTime);
                Res.bigSmall.draw(_deltaTime);
                Res.aniBonus.draw(_deltaTime);
                Res.btnLow.draw(_deltaTime);
                Res.btnHigh.draw(_deltaTime);
                Res.btnGo.draw(_deltaTime);
                Res.btnStop.draw(_deltaTime);
                Res.addCard[9].draw();
                Res.addCard[8].draw();
                Res.addCard[7].draw();
                Res.addCard[6].draw();
                Res.addCard[5].draw();
                Res.addCard[0].draw();
                Res.addCard[1].draw();
                Res.addCard[v9].draw();
                Res.addCard[v10].draw();
                Res.addCard[4].draw();
            }

            Res.aniAdditionalWLT.draw(_deltaTime);
            Res.imgBackNewGame.draw();
            Res.btnNewGame.draw(_deltaTime);
            Res.aniHurryUp.draw(_deltaTime);
        }

        if(this.iFireworksFlag > 0) {
            for(v0 = 0; v0 < 20; ++v0) {
                Res.fireworks[v0].draw(_deltaTime);
            }
        }

        Res.aniClick.draw(this.deltaTime);
        GameScreen.batch.end();
        this.updateToast();
        this.updateFps();
        GameScreen.stage.act(this.deltaTime);
        GameScreen.stage.draw();
        this.processEvent();
        this.processMsg();
        this.fNextScenarioDelay -= this.deltaTime;
        this.fScenarioInterval += this.deltaTime;
        if(this.fScenarioInterval >= 0.2f) {
            this.fScenarioInterval = 0f;
            this.processScenario();
        }

        this.fFishInterval += this.deltaTime;
        if(this.fFishInterval > 3f) {
            this.startFish();
            if(Util.randomTrue(40)) {
                this.startFish();
            }

            if(Util.randomTrue(40)) {
                this.startFish();
            }

            if(Util.randomTrue(50)) {
                this.fFishInterval = 0f;
            }
            else {
                this.fFishInterval = -5f;
            }

            if(!Util.randomTrue(80)) {
                goto label_389;
            }

            if(!Util.randomTrue(50)) {
                goto label_974;
            }

            Res.bubbleCenter1.play(true, "1", 1);
            goto label_389;
        label_974:
            Res.bubbleCenter2.play(true, "1", 1);
        }

    label_389:
        if(this.fiPlusWinMoney > 0) {
            this.fPlusWinMoneyShowDelay += this.deltaTime;
            if(this.fPlusWinMoneyShowDelay > 0.1f) {
                this.fPlusWinMoneyShowDelay = 0f;
                this.minusWinMoney();
            }
        }

        if(Global.bTerminating) {
            Global.fTerminating += this.deltaTime;
            if(Global.fTerminating > 2f) {
                Global.fTerminating = -100000000f;
                this.disposeGameScreen();
                Gdx.app.exit();
            }
        }

        this.fEndScenarioDelay -= this.deltaTime;
        if(this.fEndScenarioDelay < 0f) {
            this.fEndScenarioDelay = 1000000000f;
            this.endScenario();
        }

        if(this.iSharkFlag > 0) {
            this.fSharkTime += this.deltaTime;
            if(this.iSharkFlag == 1 && this.fSharkTime > 9f) {
                this.startShark(1);
                this.iSharkFlag = v9;
                this.addMsg(String.valueOf(Global.id) + "?? ???? ????!! ??????????!!", Color.ORANGE);
            }

            if(this.iSharkFlag == v9 && this.fSharkTime > 13f) {
                this.startShark(4);
                this.iSharkFlag = v10;
            }

            if(this.iSharkFlag == v10 && this.fSharkTime > 16f) {
                this.startShark(v9);
                this.iSharkFlag = 4;
            }

            if(this.iSharkFlag == 4 && this.fSharkTime > 18f) {
                this.startShark(4);
                this.iSharkFlag = 5;
            }

            if(this.iSharkFlag == 5 && this.fSharkTime > 19f) {
                this.startShark(v10);
                this.iSharkFlag = 6;
            }

            if(this.iSharkFlag == 6 && this.fSharkTime > 32f) {
                this.startShark(4);
                this.iSharkFlag = 7;
            }

            if(this.iSharkFlag == 7 && this.fSharkTime > 35f) {
                this.startShark(v9);
                this.iSharkFlag = 8;
            }

            if(this.iSharkFlag == 8 && this.fSharkTime > 39f) {
                this.startShark(4);
                this.iSharkFlag = 9;
            }

            if(this.iSharkFlag == 9 && this.fSharkTime > 40f) {
                this.startShark(v9);
                this.iSharkFlag = 10;
            }

            if(this.iSharkFlag == 10 && this.fSharkTime > 42f) {
                this.startShark(v10);
                this.iSharkFlag = 11;
            }

            if(this.fSharkTime <= 50f) {
                goto label_542;
            }

            this.iSharkFlag = 0;
            Snd.sndSeaBack1.stop();
            Snd.sndSeaBack2.stop();
            Snd.sndSeaBack3.stop();
            if(!Util.randomTrue(60)) {
                goto label_978;
            }

            Snd.sndSeaBack1.play();
            this.iBackgroundMusic = 1;
            goto label_542;
        label_978:
            Snd.sndSeaBack3.play();
            this.iBackgroundMusic = v10;
        }

    label_542:
        if(this.iWhaleFlag > 0) {
            this.fWhaleTime += _deltaTime;
            if(this.fWhaleTime > 20f) {
                this.iWhaleFlag = 0;
                this.addMsg(String.valueOf(Global.id) + "?? ???? ????!! ??????????!!", Color.ORANGE);
            }
        }

        if(this.iFireworksFlag > 0) {
            this.fFireworksTime += this.deltaTime;
            if(this.iFireworksFlag == 1 && (((double)this.fFireworksTime)) > 0.1) {
                this.showFireworks(1);
                this.iFireworksFlag = v9;
            }

            if(this.iFireworksFlag == v9 && (((double)this.fFireworksTime)) > 1.5) {
                this.showFireworks(v9);
                this.iFireworksFlag = v10;
            }

            if(this.iFireworksFlag == v10 && this.fFireworksTime > 3f) {
                this.showFireworks(v10);
                this.iFireworksFlag = 4;
            }

            if(this.iFireworksFlag == 4 && (((double)this.fFireworksTime)) > 4.5) {
                this.showFireworks(4);
                this.iFireworksFlag = 5;
            }

            if(this.iFireworksFlag != 5) {
                goto label_608;
            }

            if(this.fFireworksTime <= 6f) {
                goto label_608;
            }

            this.iFireworksFlag = 1;
            this.fFireworksTime = 0f;
        }

    label_608:
        if(!this.bAdditionalGame) {
            this.fDropCoinTimer += _deltaTime;
            if(this.fDropCoinTimer > 2.5f) {
                this.fDropCoinTimer = 0f;
                this.startCoin();
            }
        }

        if(this.bAdditionalGame) {
            this.fAdditionalBaseBetTimer -= _deltaTime;
            if(this.fAdditionalBaseBetTimer < 0f && !this.bWaitingAdditionalGame && this.iAdditionalBaseBet <= 0) {
                this.bAdditionalGame = false;
                this.fAdditionalBaseBetTimer = 10000000000f;
                this.showHideGameLabel();
                Snd.stopAddSound();
                Snd.stopSeaSound();
                if(this.iBackgroundMusic == 1) {
                    Snd.sndSeaBack1.play();
                }
                else if(this.iBackgroundMusic == v9) {
                    Snd.sndSeaBack2.play();
                }
                else {
                    Snd.sndSeaBack3.play();
                }
            }

            if(this.fAdditionalTimer <= 999f) {
                this.fAdditionalTimer -= _deltaTime;
                if((((int)this.fAdditionalTimer)) != this.iAdditionalTimer) {
                    this.iAdditionalTimer = ((int)this.fAdditionalTimer);
                    this.setAdditionalTimer();
                    if(this.iAdditionalTimer <= 0) {
                        this.fAdditionalTimer = 1000000000f;
                        this.bAddHurryUp1 = false;
                        this.bAddHurryUp2 = false;
                        this.additionalTimeOver();
                    }
                }
            }

            if(this.bAdditionalWinCeremony) {
                if(this.iAdditionalWinMoney > 0) {
                    this.fAdditionalWinCeremonyMoneyTimer += _deltaTime;
                    if(this.fAdditionalWinCeremonyMoneyTimer > 0.07f) {
                        this.fAdditionalWinCeremonyMoneyTimer = 0f;
                        if(this.iAdditionalBaseBet == 2000) {
                            this.iAdditionalWinMoney += -400;
                            GameScreen.lbAdditionalMoney.setText(Util.withComma(((long)(Integer.parseInt(GameScreen.lbAdditionalMoney.getText().toString().replaceAll(",", "")) + 400))));
                            GameScreen.lbAdditionalWin.setText(Util.withComma(((long)this.iAdditionalWinMoney)));
                        }
                        else if(this.iAdditionalBaseBet == 10000) {
                            this.iAdditionalWinMoney += -2000;
                            GameScreen.lbAdditionalMoney.setText(Util.withComma(((long)(Integer.parseInt(GameScreen.lbAdditionalMoney.getText().toString().replaceAll(",", "")) + 2000))));
                            GameScreen.lbAdditionalWin.setText(Util.withComma(((long)this.iAdditionalWinMoney)));
                        }
                    }

                    this.fAdditionalBeep += _deltaTime;
                    if(this.fAdditionalBeep <= 0.13f) {
                        goto label_703;
                    }

                    this.fAdditionalBeep = 0f;
                    Snd.sndAddRoll.play();
                }
                else {
                    this.fEndAdditionalGameDelay = 4.5f;
                    this.bAdditionalWinCeremony = false;
                    this.fAdditionalWinCeremonyMoneyTimer = -1000000000f;
                    this.fAdditionalBeep = -1000000000f;
                    this.startFireworks(false);
                    if(!this.bAdditionalNeedNewGame) {
                        goto label_703;
                    }

                    this.fAdditionalNewGameBtnDelay = 3f;
                    this.fEndAdditionalGameDelay += 4f;
                }
            }

        label_703:
            this.fEndAdditionalGameDelay -= _deltaTime;
            if(this.fEndAdditionalGameDelay <= 0f) {
                this.bAdditionalGame = false;
                this.iAdditionalBaseBet = 0;
                this.fEndAdditionalGameDelay = 1000000000f;
                this.fAdditionalNewGameBtnDelay = 1000000000f;
                Res.aniAdditionalWLT.visible = false;
                Res.btnNewGame.visible = false;
                Res.imgBackNewGame.visible = false;
                GameScreen.updateLabel();
                this.showHideGameLabel();
                Snd.stopAddSound();
                Snd.stopSeaSound();
                if(this.iBackgroundMusic == 1) {
                    Snd.sndSeaBack1.play();
                }
                else if(this.iBackgroundMusic == v9) {
                    Snd.sndSeaBack2.play();
                }
                else {
                    Snd.sndSeaBack3.play();
                }
            }

            this.fAdditionalNewGameBtnDelay -= _deltaTime;
            if(this.fAdditionalNewGameBtnDelay >= 0f) {
                return;
            }

            this.fAdditionalNewGameBtnDelay = 1000000000f;
            Res.btnNewGame.visible = true;
            Res.imgBackNewGame.visible = true;
            Res.btnStop.play(true, "o");
            Snd.stopAddSound();
            Snd.sndAddChoiceBack.setLooping(true);
            Snd.sndAddChoiceBack.play();
        }
        else {
            this.fBackgroundMusic += _deltaTime;
        }
    }

    private void requestScenario() {  // has try-catch handlers
        try {
            if(this.bCanRequestScenario) {
                goto label_3;
            }

            return;
        label_3:
            if(Global.machineNo <= 0) {
                return;
            }

            this.bCanRequestScenario = false;
            this.bWaitingScenario = true;
            int v1 = 0;
            if(this.lsScenario.size() > 0) {
                Object v2 = this.lsScenario.get(this.lsScenario.size() - 1);
                v1 = Util.strToint(this.getValue("A", ((String)v2)), 0);
                if(v1 <= 0) {
                    Util.sysout("ERROR", "GameScreen->requestScenario(1)", "sScenario=" + (((String)v2)));
                    GameScreen.exitApp("?????????? ?????? ?? ????????(CS1).\n?????????? ??????????.");
                    return;
                }
            }

            GameScreen.socketWrite("G090\t" + Global.id + "\t" + "0" + "\t" + Global.machineNo + "\t" + String.valueOf(v1));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->requestScenario(0)", Util.getExceptionMessage(v0), String.valueOf("0"));
        }
    }

    public void resize(int width, int height) {
        GameScreen.stage.getViewport().update(width, height);
    }

    public void resume() {
    }

    private void sendAsk(String msg) {  // has try-catch handlers
        if(msg == null) {
            try {
                msg = "";
            label_2:
                msg = msg.trim();
                if(!"".equals(msg)) {
                    goto label_7;
                }

                return;
            label_7:
                GameScreen.socketWrite("G140\t" + Global.id + "\t" + msg);
                return;
            }
            catch(Exception v0) {
                Util.sysout("ERROR", "GameScreen->sendAsk", Util.getExceptionMessage(v0));
                return;
            }
        }

        goto label_2;
    }

    public static void sendMsgToOuter(String sMsg) {  // has try-catch handlers
        try {
            GameScreen.game.sendMsgToOuter(sMsg);
        }
        catch(Exception v0) {
            Util.sysout("DEBUG", "GameScreen->sendMsgToOuter", Util.getExceptionMessage(v0), sMsg);
        }
    }

    private void setAdditionalHiddenCard(String sHiddenCard) {  // has try-catch handlers
        int v2 = 10;
        if(sHiddenCard != null) {
            try {
                if(sHiddenCard.length() != v2) {
                label_4:
                    Util.sysout("ERROR", "GameScreen->setAdditionalOpenCard(1)", "Invalid Open Cards.", sHiddenCard);
                    GameScreen.exitApp("???????? ?????? ?? ????????(HC1).\n?????????? ??????????.");
                }

            label_10:
                Res.addCard[5].setImage(sHiddenCard.substring(0, 2));
                Res.addCard[6].setImage(sHiddenCard.substring(2, 4));
                Res.addCard[7].setImage(sHiddenCard.substring(4, 6));
                Res.addCard[8].setImage(sHiddenCard.substring(6, 8));
                Res.addCard[9].setImage(sHiddenCard.substring(8, 10));
                return;
            }
            catch(Exception v0) {
                Util.sysout("ERROR", "GameScreen->setAdditionalOpenCard(0)", Util.getExceptionMessage(v0), sHiddenCard);
                GameScreen.exitApp("???????? ?????? ?? ????????(HC0).\n?????????? ??????????.");
                return;
            }
        }
        else {
            goto label_4;
        }

        goto label_10;
    }

    private void setAdditionalOpenCard(String sOpenCard) {  // has try-catch handlers
        if(sOpenCard != null) {
            try {
                if(sOpenCard.length() != 10) {
                label_4:
                    Util.sysout("ERROR", "GameScreen->setAdditionalOpenCard(1)", "Invalid Open Cards.", sOpenCard);
                    GameScreen.exitApp("???????? ?????? ?? ????????(OC1).\n?????????? ??????????.");
                }

            label_10:
                Res.addCard[0].setImage(sOpenCard.substring(0, 2));
                Res.addCard[1].setImage(sOpenCard.substring(2, 4));
                Res.addCard[2].setImage(sOpenCard.substring(4, 6));
                Res.addCard[3].setImage(sOpenCard.substring(6, 8));
                Res.addCard[4].setImage(sOpenCard.substring(8, 10));
                return;
            }
            catch(Exception v0) {
                Util.sysout("ERROR", "GameScreen->setAdditionalOpenCard(0)", Util.getExceptionMessage(v0), sOpenCard);
                GameScreen.exitApp("???????? ?????? ?? ????????(OC0).\n?????????? ??????????.");
                return;
            }
        }
        else {
            goto label_4;
        }

        goto label_10;
    }

    private void setAdditionalTimer() {
        int v1;
        int v9 = 5;
        int v8 = 2;
        int v7 = 13;
        int v6 = 3;
        int v0 = this.iAdditionalTimer / (this.iAdditionalBetTimeLimit / 10);
        if(v0 >= 10) {
            Res.imgAdditionalTimer.setImage("10");
        }
        else if(v0 <= 1) {
            Res.imgAdditionalTimer.setImage("2");
        }
        else {
            Res.imgAdditionalTimer.setImage(String.valueOf(v0));
        }

        if(v0 == 8 && (this.bAddSexySnd1)) {
            v1 = Util.randomInt(1, v7);
            if(v1 == 1) {
                Snd.sndSexy1.play();
            }

            if(v1 == v8) {
                Snd.sndSexy2.play();
            }

            if(v1 == v6) {
                Snd.sndSexy3.play();
            }

            if(v1 == 4) {
                Snd.sndSexy4.play();
            }

            if(v1 == v9) {
                Snd.sndSexy5.play();
            }

            if(v1 == 6) {
                Snd.sndSexy6.play();
            }

            if(v1 == 7) {
                Snd.sndSexy7.play();
            }

            if(v1 == 8) {
                Snd.sndSexy8.play();
            }

            if(v1 == 9) {
                Snd.sndSexy9.play();
            }

            if(v1 == 10) {
                Snd.sndSexy10.play();
            }

            if(v1 == 11) {
                Snd.sndSexy11.play();
            }

            if(v1 == 12) {
                Snd.sndSexy12.play();
            }

            if(v1 == v7) {
                Snd.sndSexy13.play();
            }

            this.bAddSexySnd1 = false;
        }

        if(v0 == 7 && (this.bAddSexySnd3)) {
            v1 = Util.randomInt(1, v7);
            if(v1 == 1) {
                Snd.sndSexy1.play();
            }

            if(v1 == v8) {
                Snd.sndSexy2.play();
            }

            if(v1 == v6) {
                Snd.sndSexy3.play();
            }

            if(v1 == 4) {
                Snd.sndSexy4.play();
            }

            if(v1 == v9) {
                Snd.sndSexy5.play();
            }

            if(v1 == 6) {
                Snd.sndSexy6.play();
            }

            if(v1 == 7) {
                Snd.sndSexy7.play();
            }

            if(v1 == 8) {
                Snd.sndSexy8.play();
            }

            if(v1 == 9) {
                Snd.sndSexy9.play();
            }

            if(v1 == 10) {
                Snd.sndSexy10.play();
            }

            if(v1 == 11) {
                Snd.sndSexy11.play();
            }

            if(v1 == 12) {
                Snd.sndSexy12.play();
            }

            if(v1 == v7) {
                Snd.sndSexy13.play();
            }

            this.bAddSexySnd3 = false;
        }

        if(v0 == v6 && (this.bAddSexySnd2)) {
            v1 = Util.randomInt(1, v7);
            if(v1 == 1) {
                Snd.sndSexy1.play();
            }

            if(v1 == v8) {
                Snd.sndSexy2.play();
            }

            if(v1 == v6) {
                Snd.sndSexy3.play();
            }

            if(v1 == 4) {
                Snd.sndSexy4.play();
            }

            if(v1 == v9) {
                Snd.sndSexy5.play();
            }

            if(v1 == 6) {
                Snd.sndSexy6.play();
            }

            if(v1 == 7) {
                Snd.sndSexy7.play();
            }

            if(v1 == 8) {
                Snd.sndSexy8.play();
            }

            if(v1 == 9) {
                Snd.sndSexy9.play();
            }

            if(v1 == 10) {
                Snd.sndSexy10.play();
            }

            if(v1 == 11) {
                Snd.sndSexy11.play();
            }

            if(v1 == 12) {
                Snd.sndSexy12.play();
            }

            if(v1 == v7) {
                Snd.sndSexy13.play();
            }

            this.bAddSexySnd2 = false;
        }

        if(v0 == v9 && !this.bAddHurryUp1) {
            this.bAddHurryUp1 = true;
            Res.aniHurryUp.visible = true;
            Res.aniHurryUp.play(true, v6);
            Snd.sndAddHurryUp.play();
            Snd.sndAddBack1.stop();
            Snd.sndAddBack2.setLooping(true);
            Snd.sndAddBack2.play();
        }

        if(v0 == v8 && !this.bAddHurryUp2) {
            this.bAddHurryUp2 = true;
            Res.aniHurryUp.visible = true;
            Res.aniHurryUp.play(true, v6);
            Snd.sndAddBack2.stop();
            Snd.sndAddBack3.setLooping(true);
            Snd.sndAddBack3.play();
        }
    }

    private void setBackGround(String _sBackground) {
        if("D".equals(_sBackground)) {
            if(!"D".equals(this.fsBackgroundDayNight)) {
                this.fsBackgroundDayNight = "D";
                this.fDayNightAlpha = 1f;
                this.fiDayNightFlag = 1;
                if(this.iChatFlag == 1) {
                    Res.imgInter.setImage("normal_chat");
                }
                else {
                    Res.imgInter.setImage("normal");
                }

                Res.imgInterRight.setImage("normal");
                Res.imgInterLeft.setImage("normal");
                Res.imgInterBottom.setImage("normal");
                if(!Util.randomTrue(20)) {
                    goto label_32;
                }

                Snd.sndThunder.play();
            }

        label_32:
            Res.imgDayNight.visible = true;
        }
        else {
            if(!"N".equals(_sBackground)) {
                return;
            }

            if(!"N".equals(this.fsBackgroundDayNight)) {
                this.fsBackgroundDayNight = "N";
                this.fDayNightAlpha = 0f;
                this.fiDayNightFlag = -1;
                if(this.iChatFlag == 1) {
                    Res.imgInter.setImage("red_chat");
                }
                else {
                    Res.imgInter.setImage("red");
                }

                Res.imgInterRight.setImage("red");
                Res.imgInterLeft.setImage("red");
                Res.imgInterBottom.setImage("red");
                if(!Util.randomTrue(50)) {
                    goto label_71;
                }

                Snd.sndThunder.play();
            }

        label_71:
            Res.imgDayNight.visible = true;
        }
    }

    private void setReelIdleTargetNo() {
        String v2;
        int v9 = 4;
        int v6 = 2;
        int v7 = -1;
        int v1;
        for(v1 = Util.randomInt(1, 15); v1 == this.iExZeroIdx; v1 = Util.randomInt(1, 15)) {
        }

        this.iExZeroIdx = v1;
        int v0 = Util.randomInt(0, 300);
        if(v1 == 1) {
            v2 = this.sZero1[v0];
        }
        else if(v1 == v6) {
            v2 = this.sZero2[v0];
        }
        else if(v1 == 3) {
            v2 = this.sZero3[v0];
        }
        else if(v1 == v9) {
            v2 = this.sZero4[v0];
        }
        else if(v1 == 5) {
            v2 = this.sZero5[v0];
        }
        else if(v1 == 6) {
            v2 = this.sZero6[v0];
        }
        else if(v1 == 7) {
            v2 = this.sZero7[v0];
        }
        else if(v1 == 8) {
            v2 = this.sZero8[v0];
        }
        else if(v1 == 9) {
            v2 = this.sZero9[v0];
        }
        else if(v1 == 10) {
            v2 = this.sZero10[v0];
        }
        else if(v1 == 11) {
            v2 = this.sZero11[v0];
        }
        else if(v1 == 12) {
            v2 = this.sZero12[v0];
        }
        else if(v1 == 13) {
            v2 = this.sZero13[v0];
        }
        else if(v1 == 14) {
            v2 = this.sZero14[v0];
        }
        else {
            v2 = this.sZero15[v0];
        }

        this.iTargetCardNo[0] = Util.strToint(v2.substring(0, v6), v7);
        this.iTargetCardNo[1] = Util.strToint(v2.substring(v6, v9), v7);
        this.iTargetCardNo[v6] = Util.strToint(v2.substring(v9, 6), v7);
        this.iTargetCardNo[3] = Util.strToint(v2.substring(6, 8), v7);
    }

    public void show() {
    }

    private void showBackLight() {
        this.fiBackLight = -1;
        this.fBackLightAlpha = 0f;
        Res.imgBackLight.setPosition(Util.getLeft(((float)Util.randomInt(50, 600))), Util.getTop(0f, Res.imgBackLight.height));
        Res.imgBackLight.visible = true;
    }

    private void showFireworks(int _iFlag) {
        int v0 = (_iFlag - 1) * 5;
        Res.fireworks[v0].setPosition(Util.getLeft(((float)Util.randomInt(0, 600))), Util.getTop(((float)Util.randomInt(200, 1200)), 0f));
        Res.fireworks[v0 + 1].setPosition(Util.getLeft(((float)Util.randomInt(0, 600))), Util.getTop(((float)Util.randomInt(200, 1200)), 0f));
        Res.fireworks[v0 + 2].setPosition(Util.getLeft(((float)Util.randomInt(0, 600))), Util.getTop(((float)Util.randomInt(200, 1200)), 0f));
        Res.fireworks[v0 + 3].setPosition(Util.getLeft(((float)Util.randomInt(0, 600))), Util.getTop(((float)Util.randomInt(200, 1200)), 0f));
        Res.fireworks[v0 + 4].setPosition(Util.getLeft(((float)Util.randomInt(0, 600))), Util.getTop(((float)Util.randomInt(200, 1200)), 0f));
        int v1 = Util.randomInt(256, 400);
        Res.fireworks[v0].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v1)));
        v1 = Util.randomInt(256, 400);
        Res.fireworks[v0 + 1].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v1)));
        v1 = Util.randomInt(256, 400);
        Res.fireworks[v0 + 2].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v1)));
        v1 = Util.randomInt(256, 400);
        Res.fireworks[v0 + 3].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v1)));
        v1 = Util.randomInt(256, 400);
        Res.fireworks[v0 + 4].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v1)));
        Res.fireworks[v0].play(true, String.valueOf(Util.randomInt(1, 3)));
        Res.fireworks[v0 + 1].play(true, String.valueOf(Util.randomInt(1, 3)));
        Res.fireworks[v0 + 2].play(true, String.valueOf(Util.randomInt(1, 3)));
        Res.fireworks[v0 + 3].play(true, String.valueOf(Util.randomInt(1, 3)));
        Res.fireworks[v0 + 4].play(true, String.valueOf(Util.randomInt(1, 3)));
        Res.fireworks[v0].visible = true;
        Res.fireworks[v0 + 1].visible = true;
        Res.fireworks[v0 + 2].visible = true;
        Res.fireworks[v0 + 3].visible = true;
        Res.fireworks[v0 + 4].visible = true;
    }

    public void showHideGameLabel() {
        if(this.bAdditionalGame) {
            GameScreen.lbMachine.setVisible(false);
            GameScreen.lbGift.setVisible(false);
            GameScreen.lbSpin.setVisible(false);
            GameScreen.lbBet.setVisible(false);
            GameScreen.lbMoney.setVisible(false);
            this.scpMsg.setVisible(false);
            if(this.iAdditionalBaseBet > 0) {
                GameScreen.lbAdditionalMoney.setVisible(true);
                GameScreen.lbAdditionalWin.setVisible(true);
            }
        }
        else {
            GameScreen.lbMachine.setVisible(true);
            GameScreen.lbGift.setVisible(true);
            GameScreen.lbSpin.setVisible(true);
            GameScreen.lbBet.setVisible(true);
            GameScreen.lbMoney.setVisible(true);
            this.scpMsg.setVisible(true);
            GameScreen.lbAdditionalMoney.setVisible(false);
            GameScreen.lbAdditionalWin.setVisible(false);
        }

        this.showReserve();
    }

    private void showMenu(boolean _bShowMenu) {
        boolean v0;
        int v4 = 5;
        boolean v2 = true;
        GameScreen.bShowMenu = _bShowMenu;
        Label v3 = GameScreen.lbMachine;
        if(_bShowMenu) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        v3.setVisible(v0);
        v3 = GameScreen.lbGift;
        if(_bShowMenu) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        v3.setVisible(v0);
        v3 = GameScreen.lbSpin;
        if(_bShowMenu) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        v3.setVisible(v0);
        v3 = GameScreen.lbBet;
        if(_bShowMenu) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        v3.setVisible(v0);
        v3 = GameScreen.lbMoney;
        if(_bShowMenu) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        v3.setVisible(v0);
        ScrollPane v0_1 = this.scpMsg;
        if(_bShowMenu) {
            v2 = false;
        }

        v0_1.setVisible(v2);
        if(!GameScreen.bShowMenu) {
            this.iExMenuMode = this.iMenuMode;
            this.iMenuMode = 0;
        }
        else if(this.iExMenuMode != 0) {
            this.iMenuMode = this.iExMenuMode;
        }
        else {
            this.iMenuMode = v4;
            this.iExMenuMode = v4;
        }

        this.changeMenuItem(this.iMenuMode);
        GameScreen.updateLabel();
        this.showReserve();
    }

    private void showMessage(String sMsg) {  // has try-catch handlers
        try {
            sMsg = sMsg.replaceAll("<br/>", System.getProperty("line.separator")).replaceAll("<BR/>", System.getProperty("line.separator"));
            new Dialog() {
            }.text(sMsg).button("   ????   ", Boolean.valueOf(true)).key(66, Boolean.valueOf(true)).key(131, Boolean.valueOf(true)).show(GameScreen.stage);
            String[] v0 = sMsg.split(System.getProperty("line.separator"));
            int v2;
            for(v2 = 0; v2 < v0.length; ++v2) {
                this.addMsg(v0[v2], Color.WHITE, 0);
            }

            return;
        }
        catch(Exception v1) {
            Util.sysout("ERROR", "GameScreen->showMessage", Util.getExceptionMessage(v1));
            return;
        }
    }

    private void showOmen(String _sOmen) {
        if("T".equals(_sOmen)) {
            this.startTurtle();
        }
        else if("M".equals(_sOmen)) {
            this.startMermaid();
        }
        else if("J".equals(_sOmen)) {
            this.startJellyfish();
        }
        else if("S".equals(_sOmen)) {
            this.startShark();
        }
        else if("W".equals(_sOmen)) {
            this.startWhale();
        }
        else {
            Util.sysout("ERROR", "GameScreen->showOmen(1)", "Undefined omen. _sOmen=" + _sOmen);
        }

        this.endScenario();
    }

    private void showReachLine(boolean _bDrawLine) {
        int v10 = 4;
        int v9 = 3;
        int v7 = 2;
        if(_bDrawLine) {
            Snd.sndReel.stop();
            Snd.sndLine.play();
            if(this.fiCTB == v7) {
                Res.aniLine.setPosition(Util.getLeft(-77f), Util.YCenter(Res.iBaseLinePixelY4, Res.aniLine.height));
                Res.aniLineLampLT.visible = true;
                Res.aniLineLampLT.play(true, "B");
                Res.aniLineLampRT.visible = true;
                Res.aniLineLampRT.play(true, "B");
            }
            else if(this.fiCTB == 1) {
                Res.aniLine.setPosition(Util.getLeft(-77f), Util.YCenter(Res.iBaseLinePixelY3, Res.aniLine.height));
                Res.aniLineLampLC.visible = true;
                Res.aniLineLampLC.play(true, "B");
                Res.aniLineLampRC.visible = true;
                Res.aniLineLampRC.play(true, "B");
            }
            else if(this.fiCTB == v9) {
                Res.aniLine.setPosition(Util.getLeft(-77f), Util.YCenter(Res.iBaseLinePixelY2, Res.aniLine.height));
                Res.aniLineLampLB.visible = true;
                Res.aniLineLampLB.play(true, "B");
                Res.aniLineLampRB.visible = true;
                Res.aniLineLampRB.play(true, "B");
            }

            int v0;
            for(v0 = 0; v0 < v10; ++v0) {
                int v1;
                for(v1 = 0; v1 < 5; ++v1) {
                    if(this.card[v0][v1].ani.length() >= v7) {
                        this.card[v0][v1].ani = this.card[v0][v1].ani.substring(0, 1);
                    }

                    this.card[v0][v1].play(false);
                }
            }

            if(this.fsMask.length() >= v10 && !"*".equals(this.fsMask.substring(0, 1))) {
                this.card[0][this.getCardIdxOnReachLine(0, this.fiCTB)].ani = String.valueOf(this.card[0][this.getCardIdxOnReachLine(0, this.fiCTB)].ani) + this.card[0][this.getCardIdxOnReachLine(0, this.fiCTB)].ani;
                this.card[0][this.getCardIdxOnReachLine(0, this.fiCTB)].play(true);
            }

            if(this.fsMask.length() >= v10 && !"*".equals(this.fsMask.substring(1, v7))) {
                this.card[1][this.getCardIdxOnReachLine(1, this.fiCTB)].ani = String.valueOf(this.card[1][this.getCardIdxOnReachLine(1, this.fiCTB)].ani) + this.card[1][this.getCardIdxOnReachLine(1, this.fiCTB)].ani;
                this.card[1][this.getCardIdxOnReachLine(1, this.fiCTB)].play(true);
            }

            if(this.fsMask.length() >= v10 && !"*".equals(this.fsMask.substring(v7, v9))) {
                this.card[v7][this.getCardIdxOnReachLine(v7, this.fiCTB)].ani = String.valueOf(this.card[v7][this.getCardIdxOnReachLine(v7, this.fiCTB)].ani) + this.card[v7][this.getCardIdxOnReachLine(v7, this.fiCTB)].ani;
                this.card[v7][this.getCardIdxOnReachLine(v7, this.fiCTB)].play(true);
            }

            if(this.fsMask.length() < v10) {
                goto label_176;
            }

            if("*".equals(this.fsMask.substring(v9, v10))) {
                goto label_176;
            }

            this.card[v9][this.getCardIdxOnReachLine(v9, this.fiCTB)].ani = String.valueOf(this.card[v9][this.getCardIdxOnReachLine(v9, this.fiCTB)].ani) + this.card[v9][this.getCardIdxOnReachLine(v9, this.fiCTB)].ani;
            this.card[v9][this.getCardIdxOnReachLine(v9, this.fiCTB)].play(true);
        }

    label_176:
        Res.aniLine.visible = _bDrawLine;
        Res.aniLine.play(true);
    }

    private void showReserve() {
        if((this.bAdditionalGame) || (GameScreen.bShowMenu) || (GameScreen.bReserving) || Global.reserve <= 0 || (Global.bTerminating) || (this.isReelRunning()) || Global.mode != 3) {
            GameScreen.lbReserve.setVisible(false);
            Res.aniReserveBack.visible = false;
        }
        else {
            GameScreen.lbReserve.setText(Util.toTime(Global.reserve));
            GameScreen.lbReserve.setVisible(true);
            Res.aniReserveBack.visible = true;
        }
    }

    private void showToast(String _msg, float _time) {  // has try-catch handlers
        try {
            this.bShowToast = true;
            this.fToastTime = _time;
            this.lbToast.setText(((CharSequence)_msg));
            this.lbToast.setVisible(true);
            this.imgToast.setVisible(true);
            this.imgToastBack.setVisible(true);
            Snd.sndToast.play();
            GameScreen.sendMsgToOuter("hide_ui");
            String[] v0 = _msg.split("\n");
            int v2;
            for(v2 = 0; v2 < v0.length; ++v2) {
                this.addMsg(v0[v2], Color.GREEN, 0);
            }

            return;
        }
        catch(Exception v1) {
            Util.sysout("ERROR", "GameScreen->showToast", Util.getExceptionMessage(v1));
            return;
        }
    }

    private void showWinMoney(boolean _bShow) {
        if(_bShow) {
            this.fiPlusWinMoney = this.fiMultiple * this.fiBaseCoin;
            this.fPlusWinMoneyShowDelay = -1f;
            this.lbPlusWinMoney.setText(String.valueOf(this.fiPlusWinMoney));
            this.lbPlusWinMoney.setVisible(true);
            if(this.fiPlusWinMoney > 0) {
                Snd.sndRoll.setLooping(true);
                Snd.sndRoll.play();
            }
        }
        else {
            this.lbPlusWinMoney.setVisible(false);
        }
    }

    public static void socketWrite(String _msg) {  // has try-catch handlers
        try {
            GameScreen.lsGameWrite.add(_msg);
            if(GameScreen.thdGameWrite != null) {
                goto label_10;
            }

            GameScreen.thdGameWrite = new Thread(new ThdGameWrite());
            GameScreen.thdGameWrite.start();
            return;
        label_10:
            GameScreen.thdGameWrite.interrupt();
            GameScreen.thdGameWrite = null;
            GameScreen.thdGameWrite = new Thread(new ThdGameWrite());
            GameScreen.thdGameWrite.start();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "GameScreen->socketWrite", Util.getExceptionMessage(v0));
        }
    }

    private void startBarEvent(boolean _bShow) {
        Res.aniEventBack.play(_bShow);
        Res.aniEventBack.visible = _bShow;
        Res.aniEvent.play(_bShow, "bar");
        Res.aniEvent.visible = _bShow;
        this.startFireworks(_bShow);
    }

    private void startCoin() {
        int v6 = 50;
        float v5 = 100f;
        if(Global.bet > 0 && Global.machineNo > 0 && Global.reserve <= 0 && Global.spin <= 15) {
            int v0 = 0;
            while(v0 < 30) {
                if(!Res.coin[v0].visible) {
                    Res.coin[v0].x = Res.aniDropper.x + Util.getWidth(v5);
                    Res.coin[v0].y = Util.getTop(v5, 0f);
                    Res.coin[v0].iNowPin = -1;
                    Res.coin[v0].ay = -0.15f;
                    if(Util.randomTrue(33)) {
                        Res.coin[v0].ax = 0.05f;
                    }
                    else if(Util.randomTrue(v6)) {
                        Res.coin[v0].ax = 0.06f;
                    }
                    else {
                        Res.coin[v0].ax = 0.07f;
                    }

                    if(this.iDropperDirection <= 0) {
                        Res.coin[v0].ax *= -1f;
                    }

                    Res.coin[v0].visible = true;
                    if(Util.randomTrue(v6)) {
                        Res.coin[v0].play(true, "R");
                    }
                    else {
                        Res.coin[v0].play(true, "L");
                    }

                    GameScreen.socketWrite("G150\t" + Global.id + "\t" + String.valueOf(Global.machineNo) + "\t" + String.valueOf(Global.bet) + "\t" + String.valueOf(Global.betWait));
                    --Global.bet;
                    ++Global.betWait;
                    GameScreen.updateLabel();
                }
                else {
                    ++v0;
                    continue;
                }

                return;
            }
        }
    }

    private void startFireworks(boolean _bShow) {
        if(_bShow) {
            this.iFireworksFlag = 1;
            this.fFireworksTime = 0f;
        }
        else {
            this.iFireworksFlag = 0;
            this.fFireworksTime = 0f;
        }
    }

    private void startFish() {
        float v5 = 868f;
        float v8 = 0.1f;
        float v4 = -100f;
        this.iFishLeftIdx = (this.iFishLeftIdx + 1) % Res.fishLeft.length;
        this.iFishRightIdx = (this.iFishRightIdx + 1) % Res.fishRight.length;
        int v1 = Util.randomInt(128, 200);
        int v0 = v1 / 2;
        if(Util.randomTrue(50)) {
            if(Util.randomTrue(60)) {
                Res.fishRight[this.iFishRightIdx].setPosition(Util.getLeft(v5), Util.getTop(((float)Util.randomInt(120, 390)), 0f));
            }
            else {
                Res.fishRight[this.iFishRightIdx].setPosition(Util.getLeft(v5), Util.getTop(((float)Util.randomInt(758, 1070)), 0f));
            }

            Res.fishRight[this.iFishRightIdx].speed = -0.07f * Util.getWidth(((float)Util.randomInt(20, 30)));
            Res.fishRight[this.iFishRightIdx].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v0)));
            Res.fishRight[this.iFishRightIdx].visible = true;
            Res.fishRight[this.iFishRightIdx].play(true, String.valueOf(Util.randomInt(1, 7)));
            Res.fishRight[this.iFishRightIdx].setPartFrameDuration(v8);
        }
        else {
            if(Util.randomTrue(60)) {
                Res.fishLeft[this.iFishLeftIdx].setPosition(Util.getLeft(v4), Util.getTop(((float)Util.randomInt(120, 390)), 0f));
            }
            else {
                Res.fishLeft[this.iFishLeftIdx].setPosition(Util.getLeft(v4), Util.getTop(((float)Util.randomInt(758, 1070)), 0f));
            }

            Res.fishLeft[this.iFishLeftIdx].speed = 0.07f * Util.getWidth(((float)Util.randomInt(20, 30)));
            Res.fishLeft[this.iFishLeftIdx].setSize(Util.getWidth(((float)v1)), Util.getHeight(((float)v0)));
            Res.fishLeft[this.iFishLeftIdx].visible = true;
            Res.fishLeft[this.iFishLeftIdx].play(true, String.valueOf(Util.randomInt(1, 7)));
            Res.fishLeft[this.iFishLeftIdx].setPartFrameDuration(v8);
        }
    }

    private void startJellyfish() {
        int v2;
        int v11 = 180;
        int v10 = 2;
        float v9 = 0.1f;
        GameScreen.playAndroidSound("jellyfish.mp3");
        int v0;
        for(v0 = 0; v0 < 10; ++v0) {
            v2 = Util.randomInt(v11, 400);
            Res.jellyfish[v0].setPosition(Util.getLeft(((float)Util.randomInt(500, 1600))), Util.getTop(((float)Util.randomInt(1600, 2200)), 0f));
            Res.jellyfish[v0].speed = 0.3f * Util.getWidth(((float)Util.randomInt(v10, 4)));
            Res.jellyfish[v0].setSize(Util.getWidth(((float)v2)), Util.getHeight(((float)(v2 * 2))));
            Res.jellyfish[v0].visible = true;
            Res.jellyfish[v0].play(true, "1");
            Res.jellyfish[v0].setPartFrameDuration(v9);
        }

        for(v0 = 10; v0 < 15; ++v0) {
            v2 = Util.randomInt(v11, 400);
            Res.jellyfish[v0].setPosition(Util.getLeft(((float)Util.randomInt(700, 1800))), Util.getTop(((float)Util.randomInt(1800, 2100)), 0f));
            Res.jellyfish[v0].speed = 0.5f * Util.getWidth(((float)Util.randomInt(1, v10)));
            Res.jellyfish[v0].setSize(Util.getWidth(((float)v2)), Util.getHeight(((float)(v2 * 2))));
            Res.jellyfish[v0].visible = true;
            Res.jellyfish[v0].play(true, "1");
            Res.jellyfish[v0].setPartFrameDuration(v9);
        }
    }

    private void startJokerEvent(boolean _bShow) {
        Res.aniEventBack.play(_bShow);
        Res.aniEventBack.visible = _bShow;
        this.startFireworks(_bShow);
        this.startMiddleEvent(_bShow, "j");
    }

    private void startMermaid() {
        if(this.fFishInterval >= -6f) {
            Res.aniMermaid.setPosition(Util.XCenter(1000f, Res.aniMermaid.width), Util.YCenter(250f, Res.aniMermaid.height));
            Res.aniMermaid.play(true);
            Res.aniMermaid.visible = true;
        }
    }

    private void startMiddleEvent(boolean _bShow, String _sItem) {
        int v4 = 3;
        int v3 = 2;
        if(_bShow) {
            Res.aniMiddleEvent[0].play(_bShow, _sItem);
            Res.aniMiddleEvent[1].play(_bShow, _sItem);
            Res.aniMiddleEvent[v3].play(_bShow, _sItem);
            Res.aniMiddleEvent[v4].play(_bShow, _sItem);
        }

        Res.aniMiddleEvent[0].visible = _bShow;
        Res.aniMiddleEvent[1].visible = _bShow;
        Res.aniMiddleEvent[v3].visible = _bShow;
        Res.aniMiddleEvent[v4].visible = _bShow;
        this.startFireworks(_bShow);
    }

    private void startSevenEvent(boolean _bShow) {
        Res.aniEventBack.play(_bShow);
        Res.aniEventBack.visible = _bShow;
        Res.aniEvent.play(_bShow, "seven");
        Res.aniEvent.visible = _bShow;
        this.startFireworks(_bShow);
    }

    private void startShark() {
        this.iSharkFlag = 1;
        this.fSharkTime = 0f;
        this.fFishInterval = -120f;
        Snd.sndSeaBack1.stop();
        Snd.sndSeaBack2.stop();
        Snd.sndSeaBack3.stop();
        this.iBackgroundMusic = 2;
        Snd.sndSeaBack2.play();
        GameScreen.playAndroidSound("shark.mp3");
    }

    private void startShark(int iFlag) {
        int v8 = 3;
        int v6 = 2;
        if(iFlag == 1) {
            Res.shark[0].setPosition(Util.getLeft(700f), Util.getTop(200f, 0f));
            Res.shark[0].deltaTime = 0f;
            Res.shark[0].setSize(Util.getWidth(((float)256)), Util.getHeight(((float)128)));
            Res.shark[0].iMoveType = 1;
            Res.shark[0].setPartFrameDuration(0.1f);
            Res.shark[0].visible = true;
            Res.shark[0].play(true, "1", 1);
        }
        else if(iFlag == v6) {
            Res.shark[1].setPosition(Util.getLeft(200f), Util.getTop(300f, 0f));
            Res.shark[1].deltaTime = 0f;
            Res.shark[1].setSize(Util.getWidth(((float)350)), Util.getHeight(((float)175)));
            Res.shark[1].iMoveType = v6;
            Res.shark[1].setPartFrameDuration(0.1f);
            Res.shark[1].visible = true;
            Res.shark[1].play(true, "1", 1);
        }
        else if(iFlag == v8) {
            Res.shark[v6].setPosition(Util.getLeft(500f), Util.getTop(900f, 0f));
            Res.shark[v6].deltaTime = 0f;
            Res.shark[v6].setSize(Util.getWidth(((float)500)), Util.getHeight(((float)250)));
            Res.shark[v6].iMoveType = 1;
            Res.shark[v6].setPartFrameDuration(0.1f);
            Res.shark[v6].visible = true;
            Res.shark[v6].play(true, "1", 1);
        }
        else if(iFlag == 4) {
            Res.shark[v8].setPosition(Util.getLeft(260f), Util.getTop(340f, 0f));
            Res.shark[v8].deltaTime = 0f;
            Res.shark[v8].setSize(Util.getWidth(((float)400)), Util.getHeight(((float)200)));
            Res.shark[v8].iMoveType = 4;
            Res.shark[v8].setPartFrameDuration(0.1f);
            Res.shark[v8].visible = true;
            Res.shark[v8].play(true, "1");
        }
    }

    private void startTurtle() {
        int v1;
        int v2;
        int v11 = -500;
        int v10 = -1300;
        float v9 = 0.1f;
        GameScreen.playAndroidSound("turtle.mp3");
        if(Util.randomTrue(50)) {
            int v0;
            for(v0 = 0; v0 < Res.turtleRight.length; ++v0) {
                v2 = Util.randomInt(200, 400);
                v1 = v2 / 2;
                if(Util.randomTrue(70)) {
                    Res.turtleRight[v0].setPosition(Util.getLeft(((float)Util.randomInt(868, 1800))), Util.getTop(((float)Util.randomInt(80, 500)), 0f));
                }
                else {
                    Res.turtleRight[v0].setPosition(Util.getLeft(((float)Util.randomInt(868, 1800))), Util.getTop(((float)Util.randomInt(800, 1100)), 0f));
                }

                Res.turtleRight[v0].speed = -0.04f * Util.getWidth(((float)Util.randomInt(20, 40)));
                Res.turtleRight[v0].setSize(Util.getWidth(((float)v2)), Util.getHeight(((float)v1)));
                Res.turtleRight[v0].visible = true;
                Res.turtleRight[v0].play(true, "1");
                Res.turtleRight[v0].setPartFrameDuration(v9);
            }
        }
        else {
            for(v0 = 0; v0 < Res.turtleLeft.length; ++v0) {
                v2 = Util.randomInt(200, 400);
                v1 = v2 / 2;
                if(Util.randomTrue(70)) {
                    Res.turtleLeft[v0].setPosition(Util.getLeft(((float)Util.randomInt(v11, v10))), Util.getTop(((float)Util.randomInt(80, 500)), 0f));
                }
                else {
                    Res.turtleLeft[v0].setPosition(Util.getLeft(((float)Util.randomInt(v11, v10))), Util.getTop(((float)Util.randomInt(800, 1100)), 0f));
                }

                Res.turtleLeft[v0].speed = 0.04f * Util.getWidth(((float)Util.randomInt(20, 40)));
                Res.turtleLeft[v0].setSize(Util.getWidth(((float)v2)), Util.getHeight(((float)v1)));
                Res.turtleLeft[v0].visible = true;
                Res.turtleLeft[v0].play(true, "1");
                Res.turtleLeft[v0].setPartFrameDuration(v9);
            }
        }
    }

    private void startWhale() {
        GameScreen.playAndroidSound("whale.mp3");
        Res.fin[0].setPosition(Util.getLeft(-2000f), Util.getTop(((float)Util.randomInt(400, 600)), 0f));
        Res.fin[0].speed = 0.002f;
        Res.fin[0].setSize(Util.getWidth(((float)250)), Util.getHeight(((float)250)));
        Res.fin[0].visible = true;
        Res.fin[0].play(true, "1");
        Res.fin[0].setPartFrameDuration(0.1f);
        Res.fin[1].setPosition(Util.getLeft(-5000f), Util.getTop(((float)Util.randomInt(1000, 1100)), 0f));
        Res.fin[1].speed = 0.002f;
        Res.fin[1].setSize(Util.getWidth(((float)250)), Util.getHeight(((float)250)));
        Res.fin[1].visible = true;
        Res.fin[1].play(true, "1");
        Res.fin[1].setPartFrameDuration(0.1f);
        this.iWhaleFlag = 1;
        this.fWhaleTime = 0f;
    }

    private void tensionReel(int iReelIndex) {
        int v13 = 3;
        int v12 = 2;
        int v9 = 4;
        float v4 = 0.01f;
        float v5 = 0.002f;
        float v1 = 0.05f;
        float v2 = -0.05f;
        float v3 = 0.05f;
        if(this.iReelTensioningStep[iReelIndex] == 1) {
            int v0;
            for(v0 = 0; v0 < 5; ++v0) {
                if(this.card[iReelIndex][v0].y > this.card[iReelIndex][v0].targetY - v4) {
                    this.fReelDeltaMileage[iReelIndex] = this.deltaTime * v1;
                    this.card[iReelIndex][v0].y -= this.fReelDeltaMileage[iReelIndex];
                }
                else {
                    this.iReelTensioningStep[iReelIndex] = v12;
                }
            }
        }

        if(this.iReelTensioningStep[iReelIndex] == v12) {
            for(v0 = 0; v0 < 5; ++v0) {
                if(this.card[iReelIndex][v0].y < this.card[iReelIndex][v0].targetY + v5) {
                    this.fReelDeltaMileage[iReelIndex] = this.deltaTime * v2;
                    this.card[iReelIndex][v0].y -= this.fReelDeltaMileage[iReelIndex];
                }
                else {
                    this.iReelTensioningStep[iReelIndex] = v13;
                }
            }
        }

        if(this.iReelTensioningStep[iReelIndex] == v13) {
            for(v0 = 0; v0 < 5; ++v0) {
                if(this.card[iReelIndex][v0].y > this.card[iReelIndex][v0].targetY) {
                    this.fReelDeltaMileage[iReelIndex] = this.deltaTime * v3;
                    this.card[iReelIndex][v0].y -= this.fReelDeltaMileage[iReelIndex];
                    if(this.card[iReelIndex][v0].y <= this.card[iReelIndex][v0].targetY) {
                        this.card[iReelIndex][v0].y = this.card[iReelIndex][v0].targetY;
                        this.iReelTensioningStep[iReelIndex] = v9;
                        this.bReelRunning[iReelIndex] = false;
                        this.fReelDeltaMileage[iReelIndex] = 0f;
                    }
                }
                else if(this.card[iReelIndex][v0].y < this.card[iReelIndex][v0].targetY) {
                    this.fReelDeltaMileage[iReelIndex] = -1f * v3 * this.deltaTime;
                    this.card[iReelIndex][v0].y -= this.fReelDeltaMileage[iReelIndex];
                    if(this.card[iReelIndex][v0].y >= this.card[iReelIndex][v0].targetY) {
                        this.card[iReelIndex][v0].y = this.card[iReelIndex][v0].targetY;
                        this.iReelTensioningStep[iReelIndex] = v9;
                        this.bReelRunning[iReelIndex] = false;
                        this.fReelDeltaMileage[iReelIndex] = 0f;
                    }
                }
                else {
                    this.iReelTensioningStep[iReelIndex] = v9;
                    this.bReelRunning[iReelIndex] = false;
                    this.fReelDeltaMileage[iReelIndex] = 0f;
                }
            }

            if(this.iReelTensioningStep[iReelIndex] != v9) {
                goto label_94;
            }

            for(v0 = 0; v0 < 5; ++v0) {
                this.card[iReelIndex][v0].y = this.getNearestBaseY(this.card[iReelIndex][v0].y);
            }

            Snd.sndReelStop.play();
            if((iReelIndex != 0 || this.iReelTensioningStep[1] != v9 || this.iReelTensioningStep[v12] != v9 || this.iReelTensioningStep[v13] != v9) && (iReelIndex != 1 || this.iReelTensioningStep[0] != v9 || this.iReelTensioningStep[v12] != v9 || this.iReelTensioningStep[v13] != v9) && (iReelIndex != v12 || this.iReelTensioningStep[0] != v9 || this.iReelTensioningStep[1] != v9 || this.iReelTensioningStep[v13] != v9)) {
                if(iReelIndex != v13) {
                    goto label_94;
                }

                if(this.iReelTensioningStep[0] != v9) {
                    goto label_94;
                }

                if(this.iReelTensioningStep[1] != v9) {
                    goto label_94;
                }

                if(this.iReelTensioningStep[v12] != v9) {
                    goto label_94;
                }
            }

            if(this.reelMode == 1) {
                this.showWinMoney(true);
                this.showReachLine(true);
                if("Z".equals(this.fsTargetObj)) {
                    if(this.countElement(this.fsMask, "Z") >= v9) {
                        GameScreen.playAndroidSound("jocker1.mp3");
                        this.startJokerEvent(true);
                        this.fWinCelemonyTime = 15f;
                    }
                }
                else if("S".equals(this.fsTargetObj)) {
                    if(this.countElement(this.fsMask, "S") >= v9) {
                        GameScreen.playAndroidSound("cele_0001.mp3");
                        this.startMiddleEvent(true, "s");
                        this.fWinCelemonyTime = 7f;
                    }
                }
                else if("T".equals(this.fsTargetObj)) {
                    if(this.countElement(this.fsMask, "T") >= v9) {
                        GameScreen.playAndroidSound("cele_0001.mp3");
                        this.startMiddleEvent(true, "t");
                        this.fWinCelemonyTime = 7f;
                    }
                }
                else if("N".equals(this.fsTargetObj)) {
                    if(this.countElement(this.fsMask, "N") >= v9) {
                        GameScreen.playAndroidSound("seven1.mp3");
                        this.startSevenEvent(true);
                        this.fWinCelemonyTime = 15f;
                    }
                }
                else if(!"B".equals(this.fsTargetObj)) {
                    if(!"M".equals(this.fsTargetObj) && !"H".equals(this.fsTargetObj)) {
                        goto label_93;
                    }

                    if(this.countElement(this.fsMask, "M") < v9 && this.countElement(this.fsMask, "H") < v9) {
                        goto label_93;
                    }

                    Snd.sndFever.play();
                }
                else if(this.countElement(this.fsMask, "B") >= v9) {
                    GameScreen.playAndroidSound("bar1.mp3");
                    this.startBarEvent(true);
                    this.fWinCelemonyTime = 15f;
                }
            }

        label_93:
            this.endScenario();
        }

    label_94:
        if(!this.bReelAccelerating[iReelIndex] && !this.bReelDecelerating[iReelIndex] && this.iReelTensioningStep[iReelIndex] == 0) {
            this.fReelDeltaMileage[iReelIndex] = 0f;
        }
    }

    private void updateCards() {
        int v4 = 4;
        GameScreen.fTotalReelElapseTime += this.deltaTime;
        int v0;
        for(v0 = 0; v0 < v4; ++v0) {
            if(!this.bReelDeceleratingStarted[v0] && GameScreen.fTotalReelElapseTime > this.fReelDeceleratingStartTime[v0]) {
                this.bReelDeceleratingStarted[v0] = true;
                this.bReelAccelerating[v0] = false;
                this.bReelDecelerating[v0] = true;
            }
        }

        for(v0 = 0; v0 < v4; ++v0) {
            if(!this.bReelPoppingStarted[v0] && GameScreen.fTotalReelElapseTime > this.fReelPoppingStartTime[v0]) {
                this.bReelPoppingStarted[v0] = true;
            }
        }

        for(v0 = 0; v0 < v4; ++v0) {
            if((this.bReelRunning[v0]) && GameScreen.fTotalReelElapseTime > this.fStartReelDelayTime[v0]) {
                this.fReelElapseTime[v0] += this.deltaTime;
                this.controlReelSpeed(v0);
                this.moveReel(v0);
                this.tensionReel(v0);
            }
        }
    }

    private void updateFps() {
        this.lbFps.setText("FPS:" + Gdx.graphics.getFramesPerSecond() + ":" + Math.round(GameScreen.fTotalReelElapseTime * 10f));
    }

    public static void updateLabel() {
        GameScreen.lbMachine.setText(String.valueOf(Global.machineNo));
        GameScreen.lbGift.setText(String.valueOf(Global.gift));
        GameScreen.lbSpin.setText(String.valueOf(Global.spin));
        GameScreen.lbBet.setText(String.valueOf(Global.bet));
        GameScreen.lbMoney.setText(Util.withComma(((long)Global.money)));
        GameScreen.lbReserve.setText(Util.toTime(Global.reserve));
        GameScreen.lbAdditionalMoney.setText(Util.withComma(((long)Global.money)));
    }

    private void updateToast() {
        this.fToastTime -= this.deltaTime;
        if((this.bShowToast) && this.fToastTime < 0f) {
            this.bShowToast = false;
            this.fToastTime = 0f;
            this.lbToast.setVisible(false);
            this.imgToast.setVisible(false);
            this.imgToastBack.setVisible(false);
        }
    }
}

