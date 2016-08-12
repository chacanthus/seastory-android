// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class Material extends Attributes {
    private static int counter;
    public String id;

    static  {
        Material.counter = 0;
    }

    public Material(String id) {
        super();
        this.id = id;
    }

    public Material(Attribute[] attributes) {
        this();
        this.set(attributes);
    }

    public Material() {
        StringBuilder v0 = new StringBuilder().append("mtl");
        int v1 = Material.counter + 1;
        Material.counter = v1;
        this(v0.append(v1).toString());
    }

    public Material(Material copyFrom) {
        this(copyFrom.id, copyFrom);
    }

    public Material(String id, Material copyFrom) {
        this(id);
        Iterator v1 = copyFrom.iterator();
        while(v1.hasNext()) {
            this.set(v1.next().copy());
        }
    }

    public Material(Array arg1) {
        this();
        this.set(((Iterable)arg1));
    }

    public Material(String id, Array arg2) {
        this(id);
        this.set(((Iterable)arg2));
    }

    public Material(String id, Attribute[] attributes) {
        this(id);
        this.set(attributes);
    }

    public final Material copy() {
        return new Material(this);
    }

    public boolean equals(Object other) {
        boolean v0;
        if((other instanceof Material)) {
            if((((Material)other)) != this) {
                if(other.id.equals(this.id)) {
                    if(super.equals(other)) {
                        goto label_10;
                    }

                    goto label_12;
                }
                else {
                    goto label_12;
                }
            }

        label_10:
            v0 = true;
        }
        else {
        label_12:
            v0 = false;
        }

        return v0;
    }

    public int hashCode() {
        return super.hashCode() + this.id.hashCode() * 3;
    }
}

