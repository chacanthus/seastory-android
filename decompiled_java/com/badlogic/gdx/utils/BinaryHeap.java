// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.utils;

public class BinaryHeap {
    public class Node {
        public Node(float value) {
            super();
            this.value = value;
        }

        public float getValue() {
            return this.value;
        }

        public String toString() {
            return Float.toString(this.value);
        }
    }

    private final boolean isMaxHeap;
    private Node[] nodes;
    public int size;

    public BinaryHeap() {
        this(16, false);
    }

    public BinaryHeap(int capacity, boolean isMaxHeap) {
        super();
        this.isMaxHeap = isMaxHeap;
        this.nodes = new Node[capacity];
    }

    public Node add(Node arg5) {
        if(this.size == this.nodes.length) {
            Node[] v0 = new Node[this.size << 1];
            System.arraycopy(this.nodes, 0, v0, 0, this.size);
            this.nodes = v0;
        }

        arg5.index = this.size;
        this.nodes[this.size] = arg5;
        int v1 = this.size;
        this.size = v1 + 1;
        this.up(v1);
        return arg5;
    }

    public Node add(Node arg2, float value) {
        arg2.value = value;
        return this.add(arg2);
    }

    public void clear() {
        Node[] v2 = this.nodes;
        int v0 = 0;
        int v1 = this.size;
        while(v0 < v1) {
            v2[v0] = null;
            ++v0;
        }

        this.size = 0;
    }

    private void down(int index) {
        int v10;
        float v7;
        Node v6;
        Node[] v4 = this.nodes;
        int v8 = this.size;
        Node v3 = v4[index];
        float v9 = v3.value;
        while(true) {
            int v0 = (index << 1) + 1;
            if(v0 < v8) {
                int v5 = v0 + 1;
                Node v1 = v4[v0];
                float v2 = v1.value;
                if(v5 >= v8) {
                    v6 = null;
                    if(this.isMaxHeap) {
                        v7 = 0f;
                    }
                    else {
                        v7 = 340282346638528860000000000000000000000f;
                    }
                }
                else {
                    v6 = v4[v5];
                    v7 = v6.value;
                }

                if(v2 < v7) {
                    v10 = 1;
                }
                else {
                    v10 = 0;
                }

                if((v10 ^ this.isMaxHeap) == 0) {
                    goto label_44;
                }

                if(v2 == v9) {
                    break;
                }

                if(v2 > v9) {
                    v10 = 1;
                }
                else {
                    v10 = 0;
                }

                if((v10 ^ this.isMaxHeap) != 0) {
                    break;
                }

                v4[index] = v1;
                v1.index = index;
                index = v0;
                continue;
            label_44:
                if(v7 == v9) {
                    break;
                }

                if(v7 > v9) {
                    v10 = 1;
                }
                else {
                    v10 = 0;
                }

                if((v10 ^ this.isMaxHeap) != 0) {
                    break;
                }

                v4[index] = v6;
                v6.index = index;
                index = v5;
                continue;
            }

            break;
        }

        v4[index] = v3;
        v3.index = index;
    }

    public Node peek() {
        if(this.size == 0) {
            throw new IllegalStateException("The heap is empty.");
        }

        return this.nodes[0];
    }

    public Node pop() {
        return this.remove(0);
    }

    private Node remove(int index) {
        Node[] v0 = this.nodes;
        Node v1 = v0[index];
        int v2 = this.size - 1;
        this.size = v2;
        v0[index] = v0[v2];
        v0[this.size] = null;
        if(this.size > 0 && index < this.size) {
            this.down(index);
        }

        return v1;
    }

    public Node remove(Node arg2) {
        return this.remove(arg2.index);
    }

    public void setValue(Node arg4, float value) {
        int v1;
        float v0 = arg4.value;
        arg4.value = value;
        if(value < v0) {
            v1 = 1;
        }
        else {
            v1 = 0;
        }

        if((v1 ^ this.isMaxHeap) != 0) {
            this.up(arg4.index);
        }
        else {
            this.down(arg4.index);
        }
    }

    public String toString() {
        String v3;
        if(this.size == 0) {
            v3 = "[]";
        }
        else {
            Node[] v2 = this.nodes;
            StringBuilder v0 = new StringBuilder(32);
            v0.append('[');
            v0.append(v2[0].value);
            int v1;
            for(v1 = 1; v1 < this.size; ++v1) {
                v0.append(", ");
                v0.append(v2[v1].value);
            }

            v0.append(']');
            v3 = v0.toString();
        }

        return v3;
    }

    private void up(int index) {
        int v5;
        Node[] v1 = this.nodes;
        Node v0 = v1[index];
        float v4 = v0.value;
        while(index > 0) {
            int v3 = index - 1 >> 1;
            Node v2 = v1[v3];
            if(v4 < v2.value) {
                v5 = 1;
            }
            else {
                v5 = 0;
            }

            if((v5 ^ this.isMaxHeap) == 0) {
                break;
            }

            v1[index] = v2;
            v2.index = index;
            index = v3;
        }

        v1[index] = v0;
        v0.index = index;
    }
}

