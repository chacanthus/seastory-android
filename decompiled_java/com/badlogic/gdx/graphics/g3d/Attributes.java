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
import java.util.Comparator;
import java.util.Iterator;

public class Attributes implements Comparable, Iterable, Comparator {
    protected final Array attributes;
    protected long mask;
    protected boolean sorted;

    public Attributes() {
        super();
        this.attributes = new Array();
        this.sorted = true;
    }

    public int attributesHash() {
        this.sort();
        int v2 = this.attributes.size;
        long v4 = 71 + this.mask;
        int v1 = 1;
        int v0;
        for(v0 = 0; v0 < v2; ++v0) {
            v1 = v1 * 7 & 65535;
            v4 += this.mask * (((long)this.attributes.get(v0).hashCode())) * (((long)v1));
        }

        return ((int)(v4 >> 32 ^ v4));
    }

    public void clear() {
        this.mask = 0;
        this.attributes.clear();
    }

    public final int compare(Attribute arg0, Attribute arg1) {
        return ((int)(arg0.type - arg1.type));
    }

    public int compare(Object x0, Object x1) {
        return this.compare(((Attribute)x0), ((Attribute)x1));
    }

    public int compareTo(Attributes other) {
        int v2;
        if(other == this) {
            v2 = 0;
        }
        else if(this.mask == other.mask) {
            this.sort();
            other.sort();
            int v1 = 0;
            while(true) {
                if(v1 < this.attributes.size) {
                    int v0 = this.attributes.get(v1).compareTo(other.attributes.get(v1));
                    if(v0 != 0) {
                        v2 = v0;
                    }
                    else {
                        ++v1;
                        continue;
                    }
                }
                else {
                    break;
                }

                goto label_3;
            }

            v2 = 0;
        }
        else if(this.mask < other.mask) {
            v2 = -1;
        }
        else {
            v2 = 1;
        }

    label_3:
        return v2;
    }

    public int compareTo(Object x0) {
        return this.compareTo(((Attributes)x0));
    }

    private final void disable(long mask) {
        this.mask &= -1 ^ mask;
    }

    private final void enable(long mask) {
        this.mask |= mask;
    }

    public boolean equals(Object other) {
        boolean v0 = true;
        if(!(other instanceof Attributes)) {
            v0 = false;
        }
        else if((((Attributes)other)) != this) {
            v0 = this.same(((Attributes)other), true);
        }

        return v0;
    }

    public final Attribute get(long type) {
        Attribute v1_1;
        if(this.has(type)) {
            int v0 = 0;
            while(true) {
                if(v0 >= this.attributes.size) {
                    goto label_15;
                }
                else if(this.attributes.get(v0).type == type) {
                    Object v1 = this.attributes.get(v0);
                }
                else {
                    ++v0;
                    continue;
                }

                break;
            }
        }
        else {
        label_15:
            v1_1 = null;
        }

        return v1_1;
    }

    public final Attribute get(Class arg3, long type) {
        return this.get(type);
    }

    public final Array get(Array arg7, long type) {
        int v0;
        for(v0 = 0; v0 < this.attributes.size; ++v0) {
            if((this.attributes.get(v0).type & type) != 0) {
                arg7.add(this.attributes.get(v0));
            }
        }

        return arg7;
    }

    public final long getMask() {
        return this.mask;
    }

    public final boolean has(long type) {
        boolean v0;
        if(type == 0 || (this.mask & type) != type) {
            v0 = false;
        }
        else {
            v0 = true;
        }

        return v0;
    }

    public int hashCode() {
        return this.attributesHash();
    }

    protected int indexOf(long type) {
        int v0;
        if(this.has(type)) {
            v0 = 0;
            while(true) {
                if(v0 >= this.attributes.size) {
                    goto label_13;
                }
                else if(this.attributes.get(v0).type != type) {
                    ++v0;
                    continue;
                }

                break;
            }
        }
        else {
        label_13:
            v0 = -1;
        }

        return v0;
    }

    public final Iterator iterator() {
        return this.attributes.iterator();
    }

    public final void remove(long mask) {
        int v0;
        for(v0 = this.attributes.size - 1; v0 >= 0; --v0) {
            long v2 = this.attributes.get(v0).type;
            if((mask & v2) == v2) {
                this.attributes.removeIndex(v0);
                this.disable(v2);
                this.sorted = false;
            }
        }
    }

    public final boolean same(Attributes other, boolean compareValues) {
        boolean v1;
        if(other == this) {
            v1 = true;
        }
        else {
            if(other != null && this.mask == other.mask) {
                if(!compareValues) {
                    v1 = true;
                    goto label_4;
                }
                else {
                    this.sort();
                    other.sort();
                    int v0 = 0;
                    while(true) {
                        if(v0 >= this.attributes.size) {
                            break;
                        }
                        else if(!this.attributes.get(v0).equals(other.attributes.get(v0))) {
                            v1 = false;
                            goto label_4;
                        }
                        else {
                            ++v0;
                            continue;
                        }
                    }

                    v1 = true;
                    goto label_4;
                }
            }

            v1 = false;
        }

    label_4:
        return v1;
    }

    public final boolean same(Attributes other) {
        return this.same(other, false);
    }

    public final void set(Attribute attribute) {
        int v0 = this.indexOf(attribute.type);
        if(v0 < 0) {
            this.enable(attribute.type);
            this.attributes.add(attribute);
            this.sorted = false;
        }
        else {
            this.attributes.set(v0, attribute);
        }
    }

    public final void set(Attribute attribute1, Attribute attribute2) {
        this.set(attribute1);
        this.set(attribute2);
    }

    public final void set(Attribute attribute1, Attribute attribute2, Attribute attribute3) {
        this.set(attribute1);
        this.set(attribute2);
        this.set(attribute3);
    }

    public final void set(Attribute attribute1, Attribute attribute2, Attribute attribute3, Attribute attribute4) {
        this.set(attribute1);
        this.set(attribute2);
        this.set(attribute3);
        this.set(attribute4);
    }

    public final void set(Iterable arg4) {
        Iterator v1 = arg4.iterator();
        while(v1.hasNext()) {
            this.set(v1.next());
        }
    }

    public final void set(Attribute[] attributes) {
        Attribute[] v0 = attributes;
        int v3 = v0.length;
        int v2;
        for(v2 = 0; v2 < v3; ++v2) {
            this.set(v0[v2]);
        }
    }

    public int size() {
        return this.attributes.size;
    }

    public final void sort() {
        if(!this.sorted) {
            this.attributes.sort(((Comparator)this));
            this.sorted = true;
        }
    }
}

