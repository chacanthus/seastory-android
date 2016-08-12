// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet;
import com.badlogic.gdx.utils.Pools;
import java.util.Iterator;

public class Selection implements Disableable, Iterable {
    private Actor actor;
    boolean isDisabled;
    Object lastSelected;
    boolean multiple;
    private final OrderedSet old;
    private boolean programmaticChangeEvents;
    boolean required;
    final OrderedSet selected;
    private boolean toggle;

    public Selection() {
        super();
        this.selected = new OrderedSet();
        this.old = new OrderedSet();
        this.programmaticChangeEvents = true;
    }

    public void add(Object arg3) {
        if(arg3 == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }

        if(this.selected.add(arg3)) {
            if((this.programmaticChangeEvents) && (this.fireChangeEvent())) {
                this.selected.remove(arg3);
                return;
            }

            this.lastSelected = arg3;
        }
    }

    public void addAll(Array arg7) {
        int v0 = 0;
        this.snapshot();
        int v1 = 0;
        int v3 = arg7.size;
        while(v1 < v3) {
            Object v2 = arg7.get(v1);
            if(v2 == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }

            if(this.selected.add(v2)) {
                v0 = 1;
            }

            ++v1;
        }

        if(v0 == 0 || !this.programmaticChangeEvents || !this.fireChangeEvent()) {
            this.lastSelected = arg7.peek();
        }
        else {
            this.revert();
        }

        this.cleanup();
    }

    public void choose(Object arg4) {  // has try-catch handlers
        if(arg4 == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }

        if(!this.isDisabled) {
            this.snapshot();
            try {
                if(!this.toggle) {
                    if(!this.required && this.selected.size == 1) {
                        goto label_18;
                    }

                    if(!UIUtils.ctrl()) {
                        goto label_37;
                    }

                    goto label_18;
                }
                else {
                label_18:
                    if(!this.selected.contains(arg4)) {
                        goto label_37;
                    }

                    if((this.required) && this.selected.size == 1) {
                        goto label_26;
                    }

                    goto label_28;
                }
            }
            catch(Throwable v1) {
                goto label_69;
            }

        label_26:
            this.cleanup();
            return;
            try {
            label_28:
                this.selected.remove(arg4);
                this.lastSelected = null;
                goto label_32;
            }
            catch(Throwable v1) {
                goto label_69;
            }

        label_37:
            int v0 = 0;
            try {
                if(!this.multiple || !this.toggle && !UIUtils.ctrl()) {
                    if(this.selected.size == 1 && (this.selected.contains(arg4))) {
                        goto label_50;
                    }

                    goto label_52;
                }

                goto label_58;
            }
            catch(Throwable v1) {
                goto label_69;
            }

        label_50:
            this.cleanup();
            return;
            try {
            label_52:
                if(this.selected.size > 0) {
                    v0 = 1;
                }
                else {
                    goto label_64;
                }

                goto label_56;
            }
            catch(Throwable v1) {
                goto label_69;
            }

        label_64:
            v0 = 0;
            try {
            label_56:
                this.selected.clear();
            label_58:
                if(!this.selected.add(arg4)) {
                    goto label_61;
                }

                goto label_66;
            }
            catch(Throwable v1) {
                goto label_69;
            }

        label_61:
            if(v0 == 0) {
                this.cleanup();
                return;
            }

            try {
            label_66:
                this.lastSelected = arg4;
            label_32:
                if(this.fireChangeEvent()) {
                    this.revert();
                }
            }
            catch(Throwable v1) {
            label_69:
                this.cleanup();
                throw v1;
            }

            this.cleanup();
        }
    }

    void cleanup() {
        this.old.clear(32);
    }

    public void clear() {
        if(this.selected.size != 0) {
            this.snapshot();
            this.selected.clear();
            if(!this.programmaticChangeEvents || !this.fireChangeEvent()) {
                this.lastSelected = null;
            }
            else {
                this.revert();
            }

            this.cleanup();
        }
    }

    public boolean contains(Object arg2) {
        boolean v0;
        if(arg2 == null) {
            v0 = false;
        }
        else {
            v0 = this.selected.contains(arg2);
        }

        return v0;
    }

    public boolean fireChangeEvent() {  // has try-catch handlers
        boolean v1;
        if(this.actor == null) {
            v1 = false;
        }
        else {
            Object v0 = Pools.obtain(ChangeEvent.class);
            try {
                v1 = this.actor.fire(((Event)v0));
            }
            catch(Throwable v1_1) {
                Pools.free(v0);
                throw v1_1;
            }

            Pools.free(v0);
        }

        return v1;
    }

    public Object first() {
        Object v0;
        if(this.selected.size == 0) {
            v0 = null;
        }
        else {
            v0 = this.selected.first();
        }

        return v0;
    }

    public Object getLastSelected() {
        Object v0;
        if(this.lastSelected != null) {
            v0 = this.lastSelected;
        }
        else {
            v0 = this.selected.first();
        }

        return v0;
    }

    public boolean getMultiple() {
        return this.multiple;
    }

    public boolean getRequired() {
        return this.required;
    }

    public boolean getToggle() {
        return this.toggle;
    }

    public boolean hasItems() {
        boolean v0;
        if(this.selected.size > 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public boolean isEmpty() {
        boolean v0;
        if(this.selected.size == 0) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public OrderedSet items() {
        return this.selected;
    }

    public Iterator iterator() {
        return this.selected.iterator();
    }

    public void remove(Object arg3) {
        if(arg3 == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }

        if(this.selected.remove(arg3)) {
            if((this.programmaticChangeEvents) && (this.fireChangeEvent())) {
                this.selected.add(arg3);
                return;
            }

            this.lastSelected = null;
        }
    }

    public void removeAll(Array arg7) {
        int v3 = 0;
        this.snapshot();
        int v0 = 0;
        int v2 = arg7.size;
        while(v0 < v2) {
            Object v1 = arg7.get(v0);
            if(v1 == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }

            if(this.selected.remove(v1)) {
                v3 = 1;
            }

            ++v0;
        }

        if(v3 == 0 || !this.programmaticChangeEvents || !this.fireChangeEvent()) {
            this.lastSelected = null;
        }
        else {
            this.revert();
        }

        this.cleanup();
    }

    void revert() {
        this.selected.clear();
        this.selected.addAll(this.old);
    }

    public void set(Object arg3) {
        if(arg3 == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }

        if(this.selected.size != 1 || this.selected.first() != arg3) {
            this.snapshot();
            this.selected.clear();
            this.selected.add(arg3);
            if(!this.programmaticChangeEvents || !this.fireChangeEvent()) {
                this.lastSelected = arg3;
            }
            else {
                this.revert();
            }

            this.cleanup();
        }
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void setAll(Array arg7) {
        int v0 = 0;
        this.snapshot();
        this.selected.clear();
        int v1 = 0;
        int v3 = arg7.size;
        while(v1 < v3) {
            Object v2 = arg7.get(v1);
            if(v2 == null) {
                throw new IllegalArgumentException("item cannot be null.");
            }

            if(this.selected.add(v2)) {
                v0 = 1;
            }

            ++v1;
        }

        if(v0 == 0 || !this.programmaticChangeEvents || !this.fireChangeEvent()) {
            this.lastSelected = arg7.peek();
        }
        else {
            this.revert();
        }

        this.cleanup();
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public void setProgrammaticChangeEvents(boolean programmaticChangeEvents) {
        this.programmaticChangeEvents = programmaticChangeEvents;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public int size() {
        return this.selected.size;
    }

    void snapshot() {
        this.old.clear();
        this.old.addAll(this.selected);
    }

    public Array toArray() {
        return this.selected.iterator().toArray();
    }

    public Array toArray(Array arg2) {
        return this.selected.iterator().toArray(arg2);
    }

    public String toString() {
        return this.selected.toString();
    }
}

