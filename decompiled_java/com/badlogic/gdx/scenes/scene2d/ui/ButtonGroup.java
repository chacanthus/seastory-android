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

import com.badlogic.gdx.utils.Array;

public class ButtonGroup {
    private final Array buttons;
    private Array checkedButtons;
    private Button lastChecked;
    private int maxCheckCount;
    private int minCheckCount;
    private boolean uncheckLast;

    public ButtonGroup() {
        super();
        this.buttons = new Array();
        this.checkedButtons = new Array(1);
        this.maxCheckCount = 1;
        this.uncheckLast = true;
        this.minCheckCount = 1;
    }

    public ButtonGroup(Button[] arg3) {
        super();
        this.buttons = new Array();
        this.checkedButtons = new Array(1);
        this.maxCheckCount = 1;
        this.uncheckLast = true;
        this.minCheckCount = 0;
        this.add(arg3);
        this.minCheckCount = 1;
    }

    public void add(Button[] arg5) {
        if(arg5 == null) {
            throw new IllegalArgumentException("buttons cannot be null.");
        }

        int v0 = 0;
        int v1 = arg5.length;
        while(v0 < v1) {
            this.add(arg5[v0]);
            ++v0;
        }
    }

    public void add(Button arg5) {
        boolean v0;
        if(arg5 == null) {
            throw new IllegalArgumentException("button cannot be null.");
        }

        arg5.buttonGroup = null;
        if((arg5.isChecked()) || this.buttons.size < this.minCheckCount) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        arg5.setChecked(false);
        arg5.buttonGroup = this;
        this.buttons.add(arg5);
        arg5.setChecked(v0);
    }

    protected boolean canCheck(Button arg6, boolean newState) {
        boolean v1 = false;
        if(arg6.isChecked != newState) {
            if(newState) {
                if(this.maxCheckCount != -1 && this.checkedButtons.size >= this.maxCheckCount) {
                    if(this.uncheckLast) {
                        int v0 = this.minCheckCount;
                        this.minCheckCount = 0;
                        this.lastChecked.setChecked(false);
                        this.minCheckCount = v0;
                    }
                    else {
                        goto label_4;
                    }
                }

                this.checkedButtons.add(arg6);
                this.lastChecked = arg6;
            }
            else if(this.checkedButtons.size > this.minCheckCount) {
                this.checkedButtons.removeValue(arg6, true);
            }
            else {
                goto label_4;
            }

            v1 = true;
        }

    label_4:
        return v1;
    }

    public void clear() {
        this.buttons.clear();
        this.checkedButtons.clear();
    }

    public Array getAllChecked() {
        return this.checkedButtons;
    }

    public Array getButtons() {
        return this.buttons;
    }

    public Button getChecked() {
        Button v0_1;
        if(this.checkedButtons.size > 0) {
            Object v0 = this.checkedButtons.get(0);
        }
        else {
            v0_1 = null;
        }

        return v0_1;
    }

    public int getCheckedIndex() {
        int v0;
        if(this.checkedButtons.size > 0) {
            v0 = this.buttons.indexOf(this.checkedButtons.get(0), true);
        }
        else {
            v0 = -1;
        }

        return v0;
    }

    public void remove(Button arg3) {
        if(arg3 == null) {
            throw new IllegalArgumentException("button cannot be null.");
        }

        arg3.buttonGroup = null;
        this.buttons.removeValue(arg3, true);
        this.checkedButtons.removeValue(arg3, true);
    }

    public void remove(Button[] arg5) {
        if(arg5 == null) {
            throw new IllegalArgumentException("buttons cannot be null.");
        }

        int v0 = 0;
        int v1 = arg5.length;
        while(v0 < v1) {
            this.remove(arg5[v0]);
            ++v0;
        }
    }

    public void setChecked(String text) {
        if(text == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }

        int v1 = 0;
        int v2 = this.buttons.size;
        while(v1 < v2) {
            Object v0 = this.buttons.get(v1);
            if(((v0 instanceof TextButton)) && (text.contentEquals(v0.getText()))) {
                ((Button)v0).setChecked(true);
                return;
            }

            ++v1;
        }
    }

    public void setMaxCheckCount(int maxCheckCount) {
        if(maxCheckCount == 0) {
            maxCheckCount = -1;
        }

        this.maxCheckCount = maxCheckCount;
    }

    public void setMinCheckCount(int minCheckCount) {
        this.minCheckCount = minCheckCount;
    }

    public void setUncheckLast(boolean uncheckLast) {
        this.uncheckLast = uncheckLast;
    }

    public void uncheckAll() {
        int v3 = this.minCheckCount;
        this.minCheckCount = 0;
        int v1 = 0;
        int v2 = this.buttons.size;
        while(v1 < v2) {
            this.buttons.get(v1).setChecked(false);
            ++v1;
        }

        this.minCheckCount = v3;
    }
}

