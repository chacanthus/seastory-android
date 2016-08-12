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

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View$OnKeyListener;
import android.view.View$OnTouchListener;
import android.view.ViewGroup$LayoutParams;
import android.view.ViewTreeObserver$OnPreDrawListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout$LayoutParams;
import android.widget.TextView;
import com.badlogic.gdx.Input$Peripheral;
import java.io.IOException;

class AndroidOnscreenKeyboard implements View$OnKeyListener, View$OnTouchListener {
    public class PassThroughEditable implements Editable {
        public PassThroughEditable() {
            super();
        }

        public Editable append(char text) {
            Log.d("Editable", "append: " + text);
            return this;
        }

        public Editable append(CharSequence text) {
            Log.d("Editable", "append: " + text);
            return this;
        }

        public Editable append(CharSequence text, int start, int end) {
            Log.d("Editable", "append: " + text);
            return this;
        }

        public Appendable append(char x0) throws IOException {
            return this.append(x0);
        }

        public Appendable append(CharSequence x0) throws IOException {
            return this.append(x0);
        }

        public Appendable append(CharSequence x0, int x1, int x2) throws IOException {
            return this.append(x0, x1, x2);
        }

        public char charAt(int index) {
            Log.d("Editable", "charAt");
            return 0;
        }

        public void clear() {
            Log.d("Editable", "clear");
        }

        public void clearSpans() {
            Log.d("Editable", "clearSpanes");
        }

        public Editable delete(int st, int en) {
            Log.d("Editable", "delete, " + st + ", " + en);
            return this;
        }

        public void getChars(int start, int end, char[] dest, int destoff) {
            Log.d("Editable", "getChars");
        }

        public InputFilter[] getFilters() {
            Log.d("Editable", "getFilters");
            return new InputFilter[0];
        }

        public int getSpanEnd(Object tag) {
            Log.d("Editable", "getSpanEnd");
            return 0;
        }

        public int getSpanFlags(Object tag) {
            Log.d("Editable", "getSpanFlags");
            return 0;
        }

        public int getSpanStart(Object tag) {
            Log.d("Editable", "getSpanStart");
            return 0;
        }

        public Object[] getSpans(int arg0, int arg1, Class arg5) {
            Log.d("Editable", "getSpans");
            return null;
        }

        public Editable insert(int where, CharSequence text) {
            Log.d("Editable", "insert: " + text);
            return this;
        }

        public Editable insert(int where, CharSequence text, int start, int end) {
            Log.d("Editable", "insert: " + text);
            return this;
        }

        public int length() {
            Log.d("Editable", "length");
            return 0;
        }

        public int nextSpanTransition(int start, int limit, Class type) {
            Log.d("Editable", "nextSpanTransition");
            return 0;
        }

        public void removeSpan(Object what) {
            Log.d("Editable", "removeSpan");
        }

        public Editable replace(int st, int en, CharSequence text) {
            Log.d("Editable", "replace: " + text);
            return this;
        }

        public Editable replace(int st, int en, CharSequence source, int start, int end) {
            Log.d("Editable", "replace: " + source);
            return this;
        }

        public void setFilters(InputFilter[] filters) {
            Log.d("Editable", "setFilters");
        }

        public void setSpan(Object what, int start, int end, int flags) {
            Log.d("Editable", "setSpan");
        }

        public CharSequence subSequence(int start, int end) {
            Log.d("Editable", "subSequence");
            return null;
        }
    }

    final Context context;
    Dialog dialog;
    final Handler handler;
    final AndroidInput input;
    TextView textView;

    public AndroidOnscreenKeyboard(Context context, Handler handler, AndroidInput input) {
        super();
        this.context = context;
        this.handler = handler;
        this.input = input;
    }

    Dialog createDialog() {
        this.textView = AndroidOnscreenKeyboard.createView(this.context);
        this.textView.setOnKeyListener(((View$OnKeyListener)this));
        this.textView.setLayoutParams(new FrameLayout$LayoutParams(-1, -2, 80));
        this.textView.setFocusable(true);
        this.textView.setFocusableInTouchMode(true);
        this.textView.setImeOptions(this.textView.getImeOptions() | 268435456);
        FrameLayout v0 = new FrameLayout(this.context);
        v0.setLayoutParams(new ViewGroup$LayoutParams(-1, 0));
        v0.addView(this.textView);
        v0.setOnTouchListener(((View$OnTouchListener)this));
        this.dialog = new Dialog(this.context, 16973841);
        this.dialog.setContentView(((View)v0));
        return this.dialog;
    }

    public static TextView createView(Context context) {
        return new TextView() {
            Editable editable;

            protected boolean getDefaultEditable() {
                return 1;
            }

            protected MovementMethod getDefaultMovementMethod() {
                return ArrowKeyMovementMethod.getInstance();
            }

            public Editable getEditableText() {
                return this.editable;
            }

            public boolean onKeyDown(int keyCode, KeyEvent event) {
                Log.d("Test", "down keycode: " + event.getKeyCode());
                return super.onKeyDown(keyCode, event);
            }

            public boolean onKeyUp(int keyCode, KeyEvent event) {
                Log.d("Test", "up keycode: " + event.getKeyCode());
                return super.onKeyUp(keyCode, event);
            }
        };
    }

    public boolean onKey(View view, int keycode, KeyEvent e) {
        return 0;
    }

    public boolean onTouch(View view, MotionEvent e) {
        return 0;
    }

    public void setVisible(boolean visible) {
        if((visible) && this.dialog != null) {
            this.dialog.dismiss();
            this.dialog = null;
        }

        if((visible) && this.dialog == null && !this.input.isPeripheralAvailable(Peripheral.HardwareKeyboard)) {
            this.handler.post(new Runnable() {
                public void run() {
                    AndroidOnscreenKeyboard.this.dialog = AndroidOnscreenKeyboard.this.createDialog();
                    AndroidOnscreenKeyboard.this.dialog.show();
                    AndroidOnscreenKeyboard.this.handler.post(new Runnable() {
                        public void run() {
                            this.this$1.this$0.dialog.getWindow().setSoftInputMode(32);
                            Object v0 = this.this$1.this$0.context.getSystemService("input_method");
                            if(v0 != null) {
                                ((InputMethodManager)v0).showSoftInput(this.this$1.this$0.textView, 2);
                            }
                        }
                    });
                    View v0 = AndroidOnscreenKeyboard.this.dialog.getWindow().findViewById(16908290);
                    v0.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver$OnPreDrawListener() {
                        private int keyboardHeight;
                        private boolean keyboardShowing;
                        int[] screenloc;

                        public boolean onPreDraw() {
                            this.val$content.getLocationOnScreen(this.screenloc);
                            this.keyboardHeight = Math.abs(this.screenloc[1]);
                            if(this.keyboardHeight > 0) {
                                this.keyboardShowing = true;
                            }

                            if(this.keyboardHeight == 0 && (this.keyboardShowing)) {
                                this.this$1.this$0.dialog.dismiss();
                                this.this$1.this$0.dialog = null;
                            }

                            return 1;
                        }
                    });
                }
            });
        }
        else if(!visible && this.dialog != null) {
            this.dialog.dismiss();
        }
    }
}

