// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.backends.android;

import android.content.ClipData;
import android.content.Context;
import android.os.Build$VERSION;
import android.text.ClipboardManager;
import com.badlogic.gdx.utils.Clipboard;

public class AndroidClipboard implements Clipboard {
    Context context;

    public AndroidClipboard(Context context) {
        super();
        this.context = context;
    }

    public String getContents() {
        String v3 = null;
        if(Build$VERSION.SDK_INT < 11) {
            Object v1 = this.context.getSystemService("clipboard");
            if(((ClipboardManager)v1).getText() != null) {
                v3 = ((ClipboardManager)v1).getText().toString();
            }
        }
        else {
            ClipData v0 = this.context.getSystemService("clipboard").getPrimaryClip();
            if(v0 != null) {
                CharSequence v2 = v0.getItemAt(0).getText();
                if(v2 != null) {
                    v3 = v2.toString();
                }
            }
        }

        return v3;
    }

    public void setContents(String contents) {  // has try-catch handlers
        try {
            this.context.runOnUiThread(new Runnable() {
                public void run() {
                    if(Build$VERSION.SDK_INT < 11) {
                        AndroidClipboard.this.context.getSystemService("clipboard").setText(this.val$contents);
                    }
                    else {
                        AndroidClipboard.this.context.getSystemService("clipboard").setPrimaryClip(ClipData.newPlainText(this.val$contents, this.val$contents));
                    }
                }
            });
        }
        catch(Exception v0) {
        }
    }
}

