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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedSet$OrderedSetIterator;
import java.util.Iterator;

public class ArraySelection extends Selection {
    private Array array;
    private boolean rangeSelect;

    public ArraySelection(Array arg2) {
        super();
        this.rangeSelect = true;
        this.array = arg2;
    }

    public void choose(Object arg7) {
        if(arg7 == null) {
            throw new IllegalArgumentException("item cannot be null.");
        }

        if(!this.isDisabled) {
            if(this.selected.size > 0 && (this.rangeSelect) && (this.multiple) && ((Gdx.input.isKeyPressed(59)) || (Gdx.input.isKeyPressed(60)))) {
                int v1 = this.array.indexOf(this.getLastSelected(), false);
                int v0 = this.array.indexOf(arg7, false);
                if(v1 > v0) {
                    int v2 = v1;
                    v1 = v0;
                    v0 = v2;
                }

                this.snapshot();
                if(!UIUtils.ctrl()) {
                    this.selected.clear();
                }

                while(v1 <= v0) {
                    this.selected.add(this.array.get(v1));
                    ++v1;
                }

                if(this.fireChangeEvent()) {
                    this.revert();
                }

                this.cleanup();
                return;
            }

            super.choose(arg7);
        }
    }

    public boolean getRangeSelect() {
        return this.rangeSelect;
    }

    public void setRangeSelect(boolean rangeSelect) {
        this.rangeSelect = rangeSelect;
    }

    public void validate() {
        Array v0 = this.array;
        if(v0.size == 0) {
            this.clear();
        }
        else {
            OrderedSetIterator v1 = this.items().iterator();
            while(((Iterator)v1).hasNext()) {
                if(v0.contains(((Iterator)v1).next(), false)) {
                    continue;
                }

                ((Iterator)v1).remove();
            }

            if((this.required) && this.selected.size == 0) {
                this.set(v0.first());
            }
        }
    }
}

