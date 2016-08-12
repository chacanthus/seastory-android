// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ObjectIntMap;

public abstract interface Input {
    public class Buttons {
        public static final int BACK = 3;
        public static final int FORWARD = 4;
        public static final int LEFT = 0;
        public static final int MIDDLE = 2;
        public static final int RIGHT = 1;

        public Buttons() {
            super();
        }
    }

    public class Keys {
        public static final int A = 29;
        public static final int ALT_LEFT = 57;
        public static final int ALT_RIGHT = 58;
        public static final int ANY_KEY = -1;
        public static final int APOSTROPHE = 75;
        public static final int AT = 77;
        public static final int B = 30;
        public static final int BACK = 4;
        public static final int BACKSLASH = 73;
        public static final int BACKSPACE = 67;
        public static final int BUTTON_A = 96;
        public static final int BUTTON_B = 97;
        public static final int BUTTON_C = 98;
        public static final int BUTTON_CIRCLE = 255;
        public static final int BUTTON_L1 = 102;
        public static final int BUTTON_L2 = 104;
        public static final int BUTTON_MODE = 110;
        public static final int BUTTON_R1 = 103;
        public static final int BUTTON_R2 = 105;
        public static final int BUTTON_SELECT = 109;
        public static final int BUTTON_START = 108;
        public static final int BUTTON_THUMBL = 106;
        public static final int BUTTON_THUMBR = 107;
        public static final int BUTTON_X = 99;
        public static final int BUTTON_Y = 100;
        public static final int BUTTON_Z = 101;
        public static final int C = 31;
        public static final int CALL = 5;
        public static final int CAMERA = 27;
        public static final int CENTER = 23;
        public static final int CLEAR = 28;
        public static final int COLON = 243;
        public static final int COMMA = 55;
        public static final int CONTROL_LEFT = 129;
        public static final int CONTROL_RIGHT = 130;
        public static final int D = 32;
        public static final int DEL = 67;
        public static final int DOWN = 20;
        public static final int DPAD_CENTER = 23;
        public static final int DPAD_DOWN = 20;
        public static final int DPAD_LEFT = 21;
        public static final int DPAD_RIGHT = 22;
        public static final int DPAD_UP = 19;
        public static final int E = 33;
        public static final int END = 132;
        public static final int ENDCALL = 6;
        public static final int ENTER = 66;
        public static final int ENVELOPE = 65;
        public static final int EQUALS = 70;
        public static final int ESCAPE = 131;
        public static final int EXPLORER = 64;
        public static final int F = 34;
        public static final int F1 = 244;
        public static final int F10 = 253;
        public static final int F11 = 254;
        public static final int F12 = 255;
        public static final int F2 = 245;
        public static final int F3 = 246;
        public static final int F4 = 247;
        public static final int F5 = 248;
        public static final int F6 = 249;
        public static final int F7 = 250;
        public static final int F8 = 251;
        public static final int F9 = 252;
        public static final int FOCUS = 80;
        public static final int FORWARD_DEL = 112;
        public static final int G = 35;
        public static final int GRAVE = 68;
        public static final int H = 36;
        public static final int HEADSETHOOK = 79;
        public static final int HOME = 3;
        public static final int I = 37;
        public static final int INSERT = 133;
        public static final int J = 38;
        public static final int K = 39;
        public static final int L = 40;
        public static final int LEFT = 21;
        public static final int LEFT_BRACKET = 71;
        public static final int M = 41;
        public static final int MEDIA_FAST_FORWARD = 90;
        public static final int MEDIA_NEXT = 87;
        public static final int MEDIA_PLAY_PAUSE = 85;
        public static final int MEDIA_PREVIOUS = 88;
        public static final int MEDIA_REWIND = 89;
        public static final int MEDIA_STOP = 86;
        public static final int MENU = 82;
        public static final int META_ALT_LEFT_ON = 16;
        public static final int META_ALT_ON = 2;
        public static final int META_ALT_RIGHT_ON = 32;
        public static final int META_SHIFT_LEFT_ON = 64;
        public static final int META_SHIFT_ON = 1;
        public static final int META_SHIFT_RIGHT_ON = 128;
        public static final int META_SYM_ON = 4;
        public static final int MINUS = 69;
        public static final int MUTE = 91;
        public static final int N = 42;
        public static final int NOTIFICATION = 83;
        public static final int NUM = 78;
        public static final int NUMPAD_0 = 144;
        public static final int NUMPAD_1 = 145;
        public static final int NUMPAD_2 = 146;
        public static final int NUMPAD_3 = 147;
        public static final int NUMPAD_4 = 148;
        public static final int NUMPAD_5 = 149;
        public static final int NUMPAD_6 = 150;
        public static final int NUMPAD_7 = 151;
        public static final int NUMPAD_8 = 152;
        public static final int NUMPAD_9 = 153;
        public static final int NUM_0 = 7;
        public static final int NUM_1 = 8;
        public static final int NUM_2 = 9;
        public static final int NUM_3 = 10;
        public static final int NUM_4 = 11;
        public static final int NUM_5 = 12;
        public static final int NUM_6 = 13;
        public static final int NUM_7 = 14;
        public static final int NUM_8 = 15;
        public static final int NUM_9 = 16;
        public static final int O = 43;
        public static final int P = 44;
        public static final int PAGE_DOWN = 93;
        public static final int PAGE_UP = 92;
        public static final int PERIOD = 56;
        public static final int PICTSYMBOLS = 94;
        public static final int PLUS = 81;
        public static final int POUND = 18;
        public static final int POWER = 26;
        public static final int Q = 45;
        public static final int R = 46;
        public static final int RIGHT = 22;
        public static final int RIGHT_BRACKET = 72;
        public static final int S = 47;
        public static final int SEARCH = 84;
        public static final int SEMICOLON = 74;
        public static final int SHIFT_LEFT = 59;
        public static final int SHIFT_RIGHT = 60;
        public static final int SLASH = 76;
        public static final int SOFT_LEFT = 1;
        public static final int SOFT_RIGHT = 2;
        public static final int SPACE = 62;
        public static final int STAR = 17;
        public static final int SWITCH_CHARSET = 95;
        public static final int SYM = 63;
        public static final int T = 48;
        public static final int TAB = 61;
        public static final int U = 49;
        public static final int UNKNOWN = 0;
        public static final int UP = 19;
        public static final int V = 50;
        public static final int VOLUME_DOWN = 25;
        public static final int VOLUME_UP = 24;
        public static final int W = 51;
        public static final int X = 52;
        public static final int Y = 53;
        public static final int Z = 54;
        private static ObjectIntMap keyNames;

        public Keys() {
            super();
        }

        private static void initializeKeyNames() {
            Keys.keyNames = new ObjectIntMap();
            int v0;
            for(v0 = 0; v0 < 256; ++v0) {
                String v1 = Keys.toString(v0);
                if(v1 != null) {
                    Keys.keyNames.put(v1, v0);
                }
            }
        }

        public static String toString(int keycode) {
            String v0;
            if(keycode < 0) {
                throw new IllegalArgumentException("keycode cannot be negative, keycode: " + keycode);
            }

            if(keycode > 255) {
                throw new IllegalArgumentException("keycode cannot be greater than 255, keycode: " + keycode);
            }

            switch(keycode) {
                case 0: {
                    v0 = "Unknown";
                    break;
                }
                case 1: {
                    v0 = "Soft Left";
                    break;
                }
                case 2: {
                    v0 = "Soft Right";
                    break;
                }
                case 3: {
                    v0 = "Home";
                    break;
                }
                case 4: {
                    v0 = "Back";
                    break;
                }
                case 5: {
                    v0 = "Call";
                    break;
                }
                case 6: {
                    v0 = "End Call";
                    break;
                }
                case 7: {
                    v0 = "0";
                    break;
                }
                case 8: {
                    v0 = "1";
                    break;
                }
                case 9: {
                    v0 = "2";
                    break;
                }
                case 10: {
                    v0 = "3";
                    break;
                }
                case 11: {
                    v0 = "4";
                    break;
                }
                case 12: {
                    v0 = "5";
                    break;
                }
                case 13: {
                    v0 = "6";
                    break;
                }
                case 14: {
                    v0 = "7";
                    break;
                }
                case 15: {
                    v0 = "8";
                    break;
                }
                case 16: {
                    v0 = "9";
                    break;
                }
                case 17: {
                    v0 = "*";
                    break;
                }
                case 18: {
                    v0 = "#";
                    break;
                }
                case 19: {
                    v0 = "Up";
                    break;
                }
                case 20: {
                    v0 = "Down";
                    break;
                }
                case 21: {
                    v0 = "Left";
                    break;
                }
                case 22: {
                    v0 = "Right";
                    break;
                }
                case 23: {
                    v0 = "Center";
                    break;
                }
                case 24: {
                    v0 = "Volume Up";
                    break;
                }
                case 25: {
                    v0 = "Volume Down";
                    break;
                }
                case 26: {
                    v0 = "Power";
                    break;
                }
                case 27: {
                    v0 = "Camera";
                    break;
                }
                case 28: {
                    v0 = "Clear";
                    break;
                }
                case 29: {
                    v0 = "A";
                    break;
                }
                case 30: {
                    v0 = "B";
                    break;
                }
                case 31: {
                    v0 = "C";
                    break;
                }
                case 32: {
                    v0 = "D";
                    break;
                }
                case 33: {
                    v0 = "E";
                    break;
                }
                case 34: {
                    v0 = "F";
                    break;
                }
                case 35: {
                    v0 = "G";
                    break;
                }
                case 36: {
                    v0 = "H";
                    break;
                }
                case 37: {
                    v0 = "I";
                    break;
                }
                case 38: {
                    v0 = "J";
                    break;
                }
                case 39: {
                    v0 = "K";
                    break;
                }
                case 40: {
                    v0 = "L";
                    break;
                }
                case 41: {
                    v0 = "M";
                    break;
                }
                case 42: {
                    v0 = "N";
                    break;
                }
                case 43: {
                    v0 = "O";
                    break;
                }
                case 44: {
                    v0 = "P";
                    break;
                }
                case 45: {
                    v0 = "Q";
                    break;
                }
                case 46: {
                    v0 = "R";
                    break;
                }
                case 47: {
                    v0 = "S";
                    break;
                }
                case 48: {
                    v0 = "T";
                    break;
                }
                case 49: {
                    v0 = "U";
                    break;
                }
                case 50: {
                    v0 = "V";
                    break;
                }
                case 51: {
                    v0 = "W";
                    break;
                }
                case 52: {
                    v0 = "X";
                    break;
                }
                case 53: {
                    v0 = "Y";
                    break;
                }
                case 54: {
                    v0 = "Z";
                    break;
                }
                case 55: {
                    v0 = ",";
                    break;
                }
                case 56: {
                    v0 = ".";
                    break;
                }
                case 57: {
                    v0 = "L-Alt";
                    break;
                }
                case 58: {
                    v0 = "R-Alt";
                    break;
                }
                case 59: {
                    v0 = "L-Shift";
                    break;
                }
                case 60: {
                    v0 = "R-Shift";
                    break;
                }
                case 61: {
                    v0 = "Tab";
                    break;
                }
                case 62: {
                    v0 = "Space";
                    break;
                }
                case 63: {
                    v0 = "SYM";
                    break;
                }
                case 64: {
                    v0 = "Explorer";
                    break;
                }
                case 65: {
                    v0 = "Envelope";
                    break;
                }
                case 66: {
                    v0 = "Enter";
                    break;
                }
                case 67: {
                    v0 = "Delete";
                    break;
                }
                case 68: {
                    v0 = "`";
                    break;
                }
                case 69: {
                    v0 = "-";
                    break;
                }
                case 70: {
                    v0 = "=";
                    break;
                }
                case 71: {
                    v0 = "[";
                    break;
                }
                case 72: {
                    v0 = "]";
                    break;
                }
                case 73: {
                    v0 = "\\";
                    break;
                }
                case 74: {
                    v0 = ";";
                    break;
                }
                case 75: {
                    v0 = "\'";
                    break;
                }
                case 76: {
                    v0 = "/";
                    break;
                }
                case 77: {
                    v0 = "@";
                    break;
                }
                case 78: {
                    v0 = "Num";
                    break;
                }
                case 79: {
                    v0 = "Headset Hook";
                    break;
                }
                case 80: {
                    v0 = "Focus";
                    break;
                }
                case 81: {
                    v0 = "Plus";
                    break;
                }
                case 82: {
                    v0 = "Menu";
                    break;
                }
                case 83: {
                    v0 = "Notification";
                    break;
                }
                case 84: {
                    v0 = "Search";
                    break;
                }
                case 85: {
                    v0 = "Play/Pause";
                    break;
                }
                case 86: {
                    v0 = "Stop Media";
                    break;
                }
                case 87: {
                    v0 = "Next Media";
                    break;
                }
                case 88: {
                    v0 = "Prev Media";
                    break;
                }
                case 89: {
                    v0 = "Rewind";
                    break;
                }
                case 90: {
                    v0 = "Fast Forward";
                    break;
                }
                case 91: {
                    v0 = "Mute";
                    break;
                }
                case 92: {
                    v0 = "Page Up";
                    break;
                }
                case 93: {
                    v0 = "Page Down";
                    break;
                }
                case 94: {
                    v0 = "PICTSYMBOLS";
                    break;
                }
                case 95: {
                    v0 = "SWITCH_CHARSET";
                    break;
                }
                case 96: {
                    v0 = "A Button";
                    break;
                }
                case 97: {
                    v0 = "B Button";
                    break;
                }
                case 98: {
                    v0 = "C Button";
                    break;
                }
                case 99: {
                    v0 = "X Button";
                    break;
                }
                case 100: {
                    v0 = "Y Button";
                    break;
                }
                case 101: {
                    v0 = "Z Button";
                    break;
                }
                case 102: {
                    v0 = "L1 Button";
                    break;
                }
                case 103: {
                    v0 = "R1 Button";
                    break;
                }
                case 104: {
                    v0 = "L2 Button";
                    break;
                }
                case 105: {
                    v0 = "R2 Button";
                    break;
                }
                case 106: {
                    v0 = "Left Thumb";
                    break;
                }
                case 107: {
                    v0 = "Right Thumb";
                    break;
                }
                case 108: {
                    v0 = "Start";
                    break;
                }
                case 109: {
                    v0 = "Select";
                    break;
                }
                case 110: {
                    v0 = "Button Mode";
                    break;
                }
                case 112: {
                    v0 = "Forward Delete";
                    break;
                }
                case 129: {
                    v0 = "L-Ctrl";
                    break;
                }
                case 130: {
                    v0 = "R-Ctrl";
                    break;
                }
                case 131: {
                    v0 = "Escape";
                    break;
                }
                case 132: {
                    v0 = "End";
                    break;
                }
                case 133: {
                    v0 = "Insert";
                    break;
                }
                case 144: {
                    v0 = "Numpad 0";
                    break;
                }
                case 145: {
                    v0 = "Numpad 1";
                    break;
                }
                case 146: {
                    v0 = "Numpad 2";
                    break;
                }
                case 147: {
                    v0 = "Numpad 3";
                    break;
                }
                case 148: {
                    v0 = "Numpad 4";
                    break;
                }
                case 149: {
                    v0 = "Numpad 5";
                    break;
                }
                case 150: {
                    v0 = "Numpad 6";
                    break;
                }
                case 151: {
                    v0 = "Numpad 7";
                    break;
                }
                case 152: {
                    v0 = "Numpad 8";
                    break;
                }
                case 153: {
                    v0 = "Numpad 9";
                    break;
                }
                case 243: {
                    v0 = ":";
                    break;
                }
                case 244: {
                    v0 = "F1";
                    break;
                }
                case 245: {
                    v0 = "F2";
                    break;
                }
                case 246: {
                    v0 = "F3";
                    break;
                }
                case 247: {
                    v0 = "F4";
                    break;
                }
                case 248: {
                    v0 = "F5";
                    break;
                }
                case 249: {
                    v0 = "F6";
                    break;
                }
                case 250: {
                    v0 = "F7";
                    break;
                }
                case 251: {
                    v0 = "F8";
                    break;
                }
                case 252: {
                    v0 = "F9";
                    break;
                }
                case 253: {
                    v0 = "F10";
                    break;
                }
                case 254: {
                    v0 = "F11";
                    break;
                }
                case 255: {
                    v0 = "F12";
                    break;
                }
                default: {
                    v0 = null;
                    break;
                }
            }

            return v0;
        }

        public static int valueOf(String keyname) {
            if(Keys.keyNames == null) {
                Keys.initializeKeyNames();
            }

            return Keys.keyNames.get(keyname, -1);
        }
    }

    public enum Orientation {
        public static final enum Orientation Landscape;
        public static final enum Orientation Portrait;

        static  {
            Orientation.Landscape = new Orientation("Landscape", 0);
            Orientation.Portrait = new Orientation("Portrait", 1);
            Orientation[] v0 = new Orientation[2];
            v0[0] = Orientation.Landscape;
            v0[1] = Orientation.Portrait;
            Orientation.$VALUES = v0;
        }

        private Orientation(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Orientation valueOf(String name) {
            return Enum.valueOf(Orientation.class, name);
        }

        public static Orientation[] values() {
            return Orientation.$VALUES.clone();
        }
    }

    public enum Peripheral {
        public static final enum Peripheral Accelerometer;
        public static final enum Peripheral Compass;
        public static final enum Peripheral HardwareKeyboard;
        public static final enum Peripheral MultitouchScreen;
        public static final enum Peripheral OnscreenKeyboard;
        public static final enum Peripheral Vibrator;

        static  {
            Peripheral.HardwareKeyboard = new Peripheral("HardwareKeyboard", 0);
            Peripheral.OnscreenKeyboard = new Peripheral("OnscreenKeyboard", 1);
            Peripheral.MultitouchScreen = new Peripheral("MultitouchScreen", 2);
            Peripheral.Accelerometer = new Peripheral("Accelerometer", 3);
            Peripheral.Compass = new Peripheral("Compass", 4);
            Peripheral.Vibrator = new Peripheral("Vibrator", 5);
            Peripheral[] v0 = new Peripheral[6];
            v0[0] = Peripheral.HardwareKeyboard;
            v0[1] = Peripheral.OnscreenKeyboard;
            v0[2] = Peripheral.MultitouchScreen;
            v0[3] = Peripheral.Accelerometer;
            v0[4] = Peripheral.Compass;
            v0[5] = Peripheral.Vibrator;
            Peripheral.$VALUES = v0;
        }

        private Peripheral(String arg1, int arg2) {
            super(arg1, arg2);
        }

        public static Peripheral valueOf(String name) {
            return Enum.valueOf(Peripheral.class, name);
        }

        public static Peripheral[] values() {
            return Peripheral.$VALUES.clone();
        }
    }

    public abstract interface TextInputListener {
        public abstract void canceled();

        public abstract void input(String arg0);
    }

    public abstract void cancelVibrate();

    public abstract float getAccelerometerX();

    public abstract float getAccelerometerY();

    public abstract float getAccelerometerZ();

    public abstract float getAzimuth();

    public abstract long getCurrentEventTime();

    public abstract int getDeltaX();

    public abstract int getDeltaX(int arg0);

    public abstract int getDeltaY();

    public abstract int getDeltaY(int arg0);

    public abstract InputProcessor getInputProcessor();

    public abstract Orientation getNativeOrientation();

    public abstract float getPitch();

    public abstract float getRoll();

    public abstract int getRotation();

    public abstract void getRotationMatrix(float[] arg0);

    public abstract void getTextInput(TextInputListener arg0, String arg1, String arg2, String arg3);

    public abstract int getX();

    public abstract int getX(int arg0);

    public abstract int getY();

    public abstract int getY(int arg0);

    public abstract boolean isButtonPressed(int arg0);

    public abstract boolean isCatchBackKey();

    public abstract boolean isCursorCatched();

    public abstract boolean isKeyJustPressed(int arg0);

    public abstract boolean isKeyPressed(int arg0);

    public abstract boolean isPeripheralAvailable(Peripheral arg0);

    public abstract boolean isTouched();

    public abstract boolean isTouched(int arg0);

    public abstract boolean justTouched();

    public abstract void setCatchBackKey(boolean arg0);

    public abstract void setCatchMenuKey(boolean arg0);

    public abstract void setCursorCatched(boolean arg0);

    public abstract void setCursorImage(Pixmap arg0, int arg1, int arg2);

    public abstract void setCursorPosition(int arg0, int arg1);

    public abstract void setInputProcessor(InputProcessor arg0);

    public abstract void setOnscreenKeyboardVisible(boolean arg0);

    public abstract void vibrate(int arg0);

    public abstract void vibrate(long[] arg0, int arg1);
}

