// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.dev.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.dev.ss.lib.Util;

public class Snd {
    public static Music sndAddBack1;
    public static Music sndAddBack2;
    public static Music sndAddBack3;
    public static Music sndAddCheer;
    public static Music sndAddChoice;
    public static Music sndAddChoiceBack;
    public static Music sndAddClick;
    public static Music sndAddHurryUp;
    public static Music sndAddLose;
    public static Music sndAddRoll;
    public static Music sndAddTie;
    public static Music sndAddTimeOver;
    public static Music sndAddWin;
    public static Music sndAlram;
    public static Music sndBlank;
    public static Music sndBonus1;
    public static Music sndBonus2;
    public static Music sndBonus3;
    public static Music sndButton;
    public static Music sndCele1;
    public static Music sndCele2;
    public static Music sndCele3;
    public static Music sndCele4;
    public static Music sndFever;
    public static Music sndJellyFish;
    public static Music sndLaser;
    public static Music sndLine;
    public static Music sndMermaid;
    public static Music sndPin;
    public static Music sndReel;
    public static Music sndReelStop;
    public static Music sndRoll;
    public static Music sndSeaBack1;
    public static Music sndSeaBack2;
    public static Music sndSeaBack3;
    public static Music sndSexy1;
    public static Music sndSexy10;
    public static Music sndSexy11;
    public static Music sndSexy12;
    public static Music sndSexy13;
    public static Music sndSexy2;
    public static Music sndSexy3;
    public static Music sndSexy4;
    public static Music sndSexy5;
    public static Music sndSexy6;
    public static Music sndSexy7;
    public static Music sndSexy8;
    public static Music sndSexy9;
    public static Music sndShark;
    public static Music sndSoojo;
    public static Music sndSpin;
    public static Music sndThunder;
    public static Music sndToast;
    public static Music sndTurtle;
    public static Music sndWhale;

    static  {
        Snd.sndButton = null;
        Snd.sndToast = null;
        Snd.sndSpin = null;
        Snd.sndReel = null;
        Snd.sndReelStop = null;
        Snd.sndLine = null;
        Snd.sndRoll = null;
        Snd.sndAlram = null;
        Snd.sndSeaBack1 = null;
        Snd.sndSeaBack2 = null;
        Snd.sndSeaBack3 = null;
        Snd.sndLaser = null;
        Snd.sndPin = null;
        Snd.sndSoojo = null;
        Snd.sndThunder = null;
        Snd.sndFever = null;
        Snd.sndMermaid = null;
        Snd.sndTurtle = null;
        Snd.sndJellyFish = null;
        Snd.sndShark = null;
        Snd.sndWhale = null;
        Snd.sndCele1 = null;
        Snd.sndCele2 = null;
        Snd.sndCele3 = null;
        Snd.sndCele4 = null;
        Snd.sndBonus1 = null;
        Snd.sndBonus2 = null;
        Snd.sndBonus3 = null;
        Snd.sndBlank = null;
        Snd.sndSexy1 = null;
        Snd.sndSexy2 = null;
        Snd.sndSexy3 = null;
        Snd.sndSexy4 = null;
        Snd.sndSexy5 = null;
        Snd.sndSexy6 = null;
        Snd.sndSexy7 = null;
        Snd.sndSexy8 = null;
        Snd.sndSexy9 = null;
        Snd.sndSexy10 = null;
        Snd.sndSexy11 = null;
        Snd.sndSexy12 = null;
        Snd.sndSexy13 = null;
        Snd.sndAddChoiceBack = null;
        Snd.sndAddChoice = null;
        Snd.sndAddBack1 = null;
        Snd.sndAddBack2 = null;
        Snd.sndAddBack3 = null;
        Snd.sndAddCheer = null;
        Snd.sndAddWin = null;
        Snd.sndAddLose = null;
        Snd.sndAddTie = null;
        Snd.sndAddHurryUp = null;
        Snd.sndAddTimeOver = null;
        Snd.sndAddClick = null;
        Snd.sndAddRoll = null;
    }

    public Snd() {
        super();
    }

    public static void dispose() {
        Snd.stopSeaSound();
        Snd.stopAddSound();
        Snd.disposeSound();
    }

    private static void disposeSound() {  // has try-catch handlers
        try {
            Snd.sndButton.dispose();
            Snd.sndToast.dispose();
            Snd.sndAlram.dispose();
            Snd.sndSeaBack1.dispose();
            Snd.sndSeaBack2.dispose();
            Snd.sndSeaBack3.dispose();
            Snd.sndReel.dispose();
            Snd.sndReelStop.dispose();
            Snd.sndLine.dispose();
            Snd.sndRoll.dispose();
            Snd.sndLaser.dispose();
            Snd.sndThunder.dispose();
            Snd.sndFever.dispose();
            Snd.sndMermaid.dispose();
            Snd.sndBonus1.dispose();
            Snd.sndBonus2.dispose();
            Snd.sndBonus3.dispose();
            Snd.sndBlank.dispose();
            Snd.sndSpin.dispose();
            Snd.sndPin.dispose();
            Snd.sndSoojo.dispose();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->disposeSound(1)", Util.getExceptionMessage(v0));
        }

        Music v1 = null;
        try {
            Snd.sndButton = v1;
            Snd.sndToast = null;
            Snd.sndAlram = null;
            Snd.sndSeaBack1 = null;
            Snd.sndSeaBack2 = null;
            Snd.sndSeaBack3 = null;
            Snd.sndReel = null;
            Snd.sndReelStop = null;
            Snd.sndLine = null;
            Snd.sndRoll = null;
            Snd.sndLaser = null;
            Snd.sndThunder = null;
            Snd.sndFever = null;
            Snd.sndMermaid = null;
            Snd.sndBonus1 = null;
            Snd.sndBonus2 = null;
            Snd.sndBonus3 = null;
            Snd.sndBlank = null;
            Snd.sndSpin = null;
            Snd.sndPin = null;
            Snd.sndSoojo = null;
            goto label_84;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->disposeSound(2)", Util.getExceptionMessage(v0));
            goto label_84;
        }

        try {
        label_84:
            Snd.sndAddChoiceBack.dispose();
            Snd.sndAddChoice.dispose();
            Snd.sndAddBack1.dispose();
            Snd.sndAddBack2.dispose();
            Snd.sndAddBack3.dispose();
            Snd.sndAddCheer.dispose();
            Snd.sndAddWin.dispose();
            Snd.sndAddLose.dispose();
            Snd.sndAddTie.dispose();
            Snd.sndAddHurryUp.dispose();
            Snd.sndAddTimeOver.dispose();
            Snd.sndAddClick.dispose();
            Snd.sndAddRoll.dispose();
            Snd.sndSexy1.dispose();
            Snd.sndSexy2.dispose();
            Snd.sndSexy3.dispose();
            Snd.sndSexy4.dispose();
            Snd.sndSexy5.dispose();
            Snd.sndSexy6.dispose();
            Snd.sndSexy7.dispose();
            Snd.sndSexy8.dispose();
            Snd.sndSexy9.dispose();
            Snd.sndSexy10.dispose();
            Snd.sndSexy11.dispose();
            Snd.sndSexy12.dispose();
            Snd.sndSexy13.dispose();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->disposeSound(3)", Util.getExceptionMessage(v0));
        }

        v1 = null;
        try {
            Snd.sndAddChoiceBack = v1;
            Snd.sndAddChoice = null;
            Snd.sndAddBack1 = null;
            Snd.sndAddBack2 = null;
            Snd.sndAddBack3 = null;
            Snd.sndAddCheer = null;
            Snd.sndAddWin = null;
            Snd.sndAddLose = null;
            Snd.sndAddTie = null;
            Snd.sndAddHurryUp = null;
            Snd.sndAddTimeOver = null;
            Snd.sndAddClick = null;
            Snd.sndAddRoll = null;
            Snd.sndSexy1 = null;
            Snd.sndSexy2 = null;
            Snd.sndSexy3 = null;
            Snd.sndSexy4 = null;
            Snd.sndSexy5 = null;
            Snd.sndSexy6 = null;
            Snd.sndSexy7 = null;
            Snd.sndSexy8 = null;
            Snd.sndSexy9 = null;
            Snd.sndSexy10 = null;
            Snd.sndSexy11 = null;
            Snd.sndSexy12 = null;
            Snd.sndSexy13 = null;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->disposeSound(4)", Util.getExceptionMessage(v0));
        }
    }

    public static void ini() {  // has try-catch handlers
        try {
            Snd.sndButton = Gdx.audio.newMusic(Gdx.files.internal("sound/soojo.ogg"));
            Snd.sndToast = Gdx.audio.newMusic(Gdx.files.internal("sound/soojo.ogg"));
            Snd.sndAlram = Gdx.audio.newMusic(Gdx.files.internal("sound/soojo.ogg"));
            Snd.sndSeaBack1 = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm0.ogg"));
            Snd.sndSeaBack1.setLooping(true);
            Snd.sndSeaBack2 = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm1.ogg"));
            Snd.sndSeaBack2.setLooping(true);
            Snd.sndSeaBack3 = Gdx.audio.newMusic(Gdx.files.internal("sound/bgm2.ogg"));
            Snd.sndSeaBack3.setLooping(true);
            Snd.sndReel = Gdx.audio.newMusic(Gdx.files.internal("sound/reel.ogg"));
            Snd.sndReelStop = Gdx.audio.newMusic(Gdx.files.internal("sound/reelstop.ogg"));
            Snd.sndLine = Gdx.audio.newMusic(Gdx.files.internal("sound/beam.ogg"));
            Snd.sndRoll = Gdx.audio.newMusic(Gdx.files.internal("sound/roll.ogg"));
            Snd.sndLaser = Gdx.audio.newMusic(Gdx.files.internal("sound/laser.ogg"));
            Snd.sndBonus1 = Gdx.audio.newMusic(Gdx.files.internal("sound/bonus1.ogg"));
            Snd.sndBonus2 = Gdx.audio.newMusic(Gdx.files.internal("sound/bonus14.ogg"));
            Snd.sndBonus3 = Gdx.audio.newMusic(Gdx.files.internal("sound/bonus57.ogg"));
            Snd.sndBlank = Gdx.audio.newMusic(Gdx.files.internal("sound/blank.ogg"));
            Snd.sndSpin = Gdx.audio.newMusic(Gdx.files.internal("sound/spin.ogg"));
            Snd.sndPin = Gdx.audio.newMusic(Gdx.files.internal("sound/pin.ogg"));
            Snd.sndSoojo = Gdx.audio.newMusic(Gdx.files.internal("sound/soojo.ogg"));
            Snd.sndThunder = Gdx.audio.newMusic(Gdx.files.internal("sound/thunder.ogg"));
            Snd.sndFever = Gdx.audio.newMusic(Gdx.files.internal("sound/fever.ogg"));
            Snd.sndMermaid = Gdx.audio.newMusic(Gdx.files.internal("sound/bok.ogg"));
            Snd.sndAddChoice = Gdx.audio.newMusic(Gdx.files.internal("sound/letsplay.ogg"));
            Snd.sndAddBack1 = Gdx.audio.newMusic(Gdx.files.internal("sound/back2.ogg"));
            Snd.sndAddBack2 = Gdx.audio.newMusic(Gdx.files.internal("sound/back2.ogg"));
            Snd.sndAddBack3 = Gdx.audio.newMusic(Gdx.files.internal("sound/back3.ogg"));
            Snd.sndAddCheer = Gdx.audio.newMusic(Gdx.files.internal("sound/cheer.ogg"));
            Snd.sndAddWin = Gdx.audio.newMusic(Gdx.files.internal("sound/youwin1.ogg"));
            Snd.sndAddLose = Gdx.audio.newMusic(Gdx.files.internal("sound/lose.ogg"));
            Snd.sndAddTie = Gdx.audio.newMusic(Gdx.files.internal("sound/tie.ogg"));
            Snd.sndAddHurryUp = Gdx.audio.newMusic(Gdx.files.internal("sound/hurryup.ogg"));
            Snd.sndAddTimeOver = Gdx.audio.newMusic(Gdx.files.internal("sound/timeover2.ogg"));
            Snd.sndAddClick = Gdx.audio.newMusic(Gdx.files.internal("sound/click.ogg"));
            Snd.sndAddRoll = Gdx.audio.newMusic(Gdx.files.internal("sound/roll.ogg"));
            Snd.sndAddChoiceBack = Gdx.audio.newMusic(Gdx.files.internal("sound/choice.ogg"));
            Snd.sndSexy1 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy1.ogg"));
            Snd.sndSexy2 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy2.ogg"));
            Snd.sndSexy3 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy3.ogg"));
            Snd.sndSexy4 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy4.ogg"));
            Snd.sndSexy5 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy5.ogg"));
            Snd.sndSexy6 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy6.ogg"));
            Snd.sndSexy7 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy7.ogg"));
            Snd.sndSexy8 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy8.ogg"));
            Snd.sndSexy9 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy9.ogg"));
            Snd.sndSexy10 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy10.ogg"));
            Snd.sndSexy11 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy11.ogg"));
            Snd.sndSexy12 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy12.ogg"));
            Snd.sndSexy13 = Gdx.audio.newMusic(Gdx.files.internal("sound/sexy13.ogg"));
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->iniSound", Util.getExceptionMessage(v0));
        }
    }

    public static void stopAddSound() {  // has try-catch handlers
        try {
            if(Snd.sndAddChoiceBack != null) {
                Snd.sndAddChoiceBack.stop();
            }

            if(Snd.sndAddChoice != null) {
                Snd.sndAddChoice.stop();
            }

            if(Snd.sndAddBack1 != null) {
                Snd.sndAddBack1.stop();
            }

            if(Snd.sndAddBack2 != null) {
                Snd.sndAddBack2.stop();
            }

            if(Snd.sndAddBack3 != null) {
                Snd.sndAddBack3.stop();
            }

            if(Snd.sndAddCheer != null) {
                Snd.sndAddCheer.stop();
            }

            if(Snd.sndAddWin != null) {
                Snd.sndAddWin.stop();
            }

            if(Snd.sndAddLose != null) {
                Snd.sndAddLose.stop();
            }

            if(Snd.sndAddTie != null) {
                Snd.sndAddTie.stop();
            }

            if(Snd.sndAddHurryUp != null) {
                Snd.sndAddHurryUp.stop();
            }

            if(Snd.sndAddTimeOver != null) {
                Snd.sndAddTimeOver.stop();
            }

            if(Snd.sndAddClick != null) {
                Snd.sndAddClick.stop();
            }

            if(Snd.sndAddRoll != null) {
                Snd.sndAddRoll.stop();
            }

            if(Snd.sndSexy1 != null) {
                Snd.sndSexy1.stop();
            }

            if(Snd.sndSexy2 != null) {
                Snd.sndSexy2.stop();
            }

            if(Snd.sndSexy3 != null) {
                Snd.sndSexy3.stop();
            }

            if(Snd.sndSexy4 != null) {
                Snd.sndSexy4.stop();
            }

            if(Snd.sndSexy5 != null) {
                Snd.sndSexy5.stop();
            }

            if(Snd.sndSexy6 != null) {
                Snd.sndSexy6.stop();
            }

            if(Snd.sndSexy7 != null) {
                Snd.sndSexy7.stop();
            }

            if(Snd.sndSexy8 != null) {
                Snd.sndSexy8.stop();
            }

            if(Snd.sndSexy9 != null) {
                Snd.sndSexy9.stop();
            }

            if(Snd.sndSexy10 != null) {
                Snd.sndSexy10.stop();
            }

            if(Snd.sndSexy11 != null) {
                Snd.sndSexy11.stop();
            }

            if(Snd.sndSexy12 != null) {
                Snd.sndSexy12.stop();
            }

            if(Snd.sndSexy13 == null) {
                return;
            }

            Snd.sndSexy13.stop();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->stopAddSound", Util.getExceptionMessage(v0));
        }
    }

    public static void stopSeaSound() {  // has try-catch handlers
        try {
            if(Snd.sndButton != null) {
                Snd.sndButton.stop();
            }

            if(Snd.sndToast != null) {
                Snd.sndToast.stop();
            }

            if(Snd.sndAlram != null) {
                Snd.sndAlram.stop();
            }

            if(Snd.sndSeaBack1 != null) {
                Snd.sndSeaBack1.stop();
            }

            if(Snd.sndSeaBack2 != null) {
                Snd.sndSeaBack2.stop();
            }

            if(Snd.sndSeaBack3 != null) {
                Snd.sndSeaBack3.stop();
            }

            if(Snd.sndReel != null) {
                Snd.sndReel.stop();
            }

            if(Snd.sndReelStop != null) {
                Snd.sndReelStop.stop();
            }

            if(Snd.sndLine != null) {
                Snd.sndLine.stop();
            }

            if(Snd.sndRoll != null) {
                Snd.sndRoll.stop();
            }

            if(Snd.sndLaser != null) {
                Snd.sndLaser.stop();
            }

            if(Snd.sndThunder != null) {
                Snd.sndThunder.stop();
            }

            if(Snd.sndFever != null) {
                Snd.sndFever.stop();
            }

            if(Snd.sndMermaid != null) {
                Snd.sndMermaid.stop();
            }

            if(Snd.sndBonus1 != null) {
                Snd.sndBonus1.stop();
            }

            if(Snd.sndBonus2 != null) {
                Snd.sndBonus2.stop();
            }

            if(Snd.sndBonus3 != null) {
                Snd.sndBonus3.stop();
            }

            if(Snd.sndBlank != null) {
                Snd.sndBlank.stop();
            }

            if(Snd.sndSpin != null) {
                Snd.sndSpin.stop();
            }

            if(Snd.sndPin != null) {
                Snd.sndPin.stop();
            }

            if(Snd.sndSoojo == null) {
                return;
            }

            Snd.sndSoojo.stop();
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "Res->stopSeaSound", Util.getExceptionMessage(v0));
        }
    }
}

