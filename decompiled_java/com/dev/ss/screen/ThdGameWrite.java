

package com.dev.ss.screen;

import com.dev.ss.Global;
import com.dev.ss.MySocket;
import com.dev.ss.lib.Util;

class ThdGameWrite implements Runnable {
    ThdGameWrite() {
        super();
    }

    public void run() {  // has try-catch handlers
        try {
            do {
            label_2:
                if(GameScreen.lsGameWrite.size() > 0) {
                    goto label_6;
                }

                return;
            label_6:
                Object v2 = GameScreen.lsGameWrite.get(0);
                GameScreen.lsGameWrite.remove(0);
                if(MySocket.write(((String)v2))) {
                    goto label_2;
                }
            }
            while(Global.mode == 5);

            Util.sysout("ERROR", "ThdGameWrite->run(1)", "Unable to write.");
            GameScreen.exitApp("서버에 연결할 수 없습니다(W).\n프로그램을 종료합니다.");
            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "ThdGameWrite->run(2)", Util.getExceptionMessage(v0));
            GameScreen.exitApp("서버에 연결할 수 없습니다(W2).\n프로그램을 종료합니다.");
            return;
        }
    }
}

