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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public abstract class Value {
    public class Fixed extends Value {
        private final float value;

        public Fixed(float value) {
            this();
            this.value = value;
        }

        public float get(Actor context) {
            return this.value;
        }
    }

    public static Value maxHeight;
    public static Value maxWidth;
    public static Value minHeight;
    public static Value minWidth;
    public static Value prefHeight;
    public static Value prefWidth;
    public static final Fixed zero;

    static  {
        Value.zero = new Fixed(0f);
        Value.minWidth = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getMinWidth();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getWidth();
                }

                return v0;
            }
        };
        Value.minHeight = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getMinHeight();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getHeight();
                }

                return v0;
            }
        };
        Value.prefWidth = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getPrefWidth();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getWidth();
                }

                return v0;
            }
        };
        Value.prefHeight = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getPrefHeight();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getHeight();
                }

                return v0;
            }
        };
        Value.maxWidth = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getMaxWidth();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getWidth();
                }

                return v0;
            }
        };
        Value.maxHeight = new Value() {
            public float get(Actor context) {
                float v0;
                if((context instanceof Layout)) {
                    v0 = ((Layout)context).getMaxHeight();
                }
                else if(context == null) {
                    v0 = 0f;
                }
                else {
                    v0 = context.getHeight();
                }

                return v0;
            }
        };
    }

    public Value() {
        super();
    }

    public abstract float get(Actor arg0);

    public static Value percentHeight(float percent) {
        return new Value() {
            public float get(Actor actor) {
                return actor.getHeight() * this.val$percent;
            }
        };
    }

    public static Value percentHeight(float percent, Actor actor) {
        if(actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }

        return new Value() {
            public float get(Actor context) {
                return this.val$actor.getHeight() * this.val$percent;
            }
        };
    }

    public static Value percentWidth(float percent) {
        return new Value() {
            public float get(Actor actor) {
                return actor.getWidth() * this.val$percent;
            }
        };
    }

    public static Value percentWidth(float percent, Actor actor) {
        if(actor == null) {
            throw new IllegalArgumentException("actor cannot be null.");
        }

        return new Value() {
            public float get(Actor context) {
                return this.val$actor.getWidth() * this.val$percent;
            }
        };
    }
}

