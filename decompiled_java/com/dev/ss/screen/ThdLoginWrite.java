

package com.dev.ss.screen;

import com.dev.ss.Global;
import com.dev.ss.MySocket;
import com.dev.ss.lib.Util;

class ThdLoginWrite implements Runnable {
    ThdLoginWrite() {
        super();
    }

    public void run() {  // has try-catch handlers
        try {
            do {
            label_2:
                if(LoginScreen.lsLoginWrite.size() > 0) {
                    goto label_6;
                }

                return;
            label_6:
                Object v2 = LoginScreen.lsLoginWrite.get(0);
                LoginScreen.lsLoginWrite.remove(0);
                if(MySocket.write(((String)v2))) {
                    goto label_2;
                }
            }
            while(Global.mode == 5);

            Util.sysout("ERROR", "ThdLoginWrite->run(1)", "Unable to write.");
            LoginScreen.exitApp("������ ������ �� �����ϴ�(A).\n���α׷��� �����մϴ�.");
            return;
        }
        catch(Exception v0) {
            Util.sysout("ERROR", "ThdLoginWrite->run(2)", Util.getExceptionMessage(v0));
            LoginScreen.exitApp("������ ������ �� �����ϴ�(A2).\n���α׷��� �����մϴ�.");
            return;
        }
    }
}

