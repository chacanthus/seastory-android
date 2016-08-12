// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.graphics.g3d.particles;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import java.util.Iterator;

public class ParallelArray {
    public abstract class Channel {
        public Object data;
        public int strideSize;

        public Channel(ParallelArray arg1, int id, Object data, int strideSize) {
            ParallelArray.this = arg1;
            super();
            this.id = id;
            this.strideSize = strideSize;
            this.data = data;
        }

        public abstract void add(int arg0, Object[] arg1);

        protected abstract void setCapacity(int arg0);

        public abstract void swap(int arg0, int arg1);
    }

    public class ChannelDescriptor {
        public ChannelDescriptor(int id, Class arg2, int count) {
            super();
            this.id = id;
            this.type = arg2;
            this.count = count;
        }
    }

    public abstract interface ChannelInitializer {
        public abstract void init(Channel arg0);
    }

    public class FloatChannel extends Channel {
        public float[] data;

        public FloatChannel(ParallelArray arg2, int id, int strideSize, int size) {
            ParallelArray.this = arg2;
            super(arg2, id, new float[size * strideSize], strideSize);
            this.data = ((Channel)this).data;
        }

        public void add(int index, Object[] objects) {
            int v1 = this.strideSize * ParallelArray.this.size;
            int v0 = v1 + this.strideSize;
            int v2;
            for(v2 = 0; v1 < v0; ++v2) {
                this.data[v1] = objects[v2].floatValue();
                ++v1;
            }
        }

        public void setCapacity(int requiredCapacity) {
            float[] v0 = new float[this.strideSize * requiredCapacity];
            System.arraycopy(this.data, 0, v0, 0, Math.min(this.data.length, v0.length));
            this.data = v0;
            ((Channel)this).data = v0;
        }

        public void swap(int i, int k) {
            i *= this.strideSize;
            k *= this.strideSize;
            int v0 = i + this.strideSize;
            while(i < v0) {
                float v1 = this.data[i];
                this.data[i] = this.data[k];
                this.data[k] = v1;
                ++i;
                ++k;
            }
        }
    }

    public class IntChannel extends Channel {
        public int[] data;

        public IntChannel(ParallelArray arg2, int id, int strideSize, int size) {
            ParallelArray.this = arg2;
            super(arg2, id, new int[size * strideSize], strideSize);
            this.data = ((Channel)this).data;
        }

        public void add(int index, Object[] objects) {
            int v1 = this.strideSize * ParallelArray.this.size;
            int v0 = v1 + this.strideSize;
            int v2;
            for(v2 = 0; v1 < v0; ++v2) {
                this.data[v1] = objects[v2].intValue();
                ++v1;
            }
        }

        public void setCapacity(int requiredCapacity) {
            int[] v0 = new int[this.strideSize * requiredCapacity];
            System.arraycopy(this.data, 0, v0, 0, Math.min(this.data.length, v0.length));
            this.data = v0;
            ((Channel)this).data = v0;
        }

        public void swap(int i, int k) {
            i *= this.strideSize;
            k *= this.strideSize;
            int v0 = i + this.strideSize;
            while(i < v0) {
                int v1 = this.data[i];
                this.data[i] = this.data[k];
                this.data[k] = v1;
                ++i;
                ++k;
            }
        }
    }

    public class ObjectChannel extends Channel {
        Class componentType;
        public Object[] data;

        public ObjectChannel(ParallelArray arg2, int id, int strideSize, int size, Class arg6) {
            ParallelArray.this = arg2;
            super(arg2, id, ArrayReflection.newInstance(arg6, size * strideSize), strideSize);
            this.componentType = arg6;
            this.data = ((Channel)this).data;
        }

        public void add(int index, Object[] objects) {
            int v1 = this.strideSize * ParallelArray.this.size;
            int v0 = v1 + this.strideSize;
            int v2;
            for(v2 = 0; v1 < v0; ++v2) {
                this.data[v1] = objects[v2];
                ++v1;
            }
        }

        public void setCapacity(int requiredCapacity) {
            Object v0 = ArrayReflection.newInstance(this.componentType, this.strideSize * requiredCapacity);
            System.arraycopy(this.data, 0, v0, 0, Math.min(this.data.length, v0.length));
            this.data = ((Object[])v0);
            ((Channel)this).data = v0;
        }

        public void swap(int i, int k) {
            i *= this.strideSize;
            k *= this.strideSize;
            int v0 = i + this.strideSize;
            while(i < v0) {
                Object v1 = this.data[i];
                this.data[i] = this.data[k];
                this.data[k] = v1;
                ++i;
                ++k;
            }
        }
    }

    Array arrays;
    public int capacity;
    public int size;

    public ParallelArray(int capacity) {
        super();
        this.arrays = new Array(false, 2, Channel.class);
        this.capacity = capacity;
        this.size = 0;
    }

    public Channel addChannel(ChannelDescriptor channelDescriptor) {
        return this.addChannel(channelDescriptor, null);
    }

    public Channel addChannel(ChannelDescriptor channelDescriptor, ChannelInitializer arg4) {
        Channel v0 = this.getChannel(channelDescriptor);
        if(v0 == null) {
            v0 = this.allocateChannel(channelDescriptor);
            if(arg4 != null) {
                arg4.init(v0);
            }

            this.arrays.add(v0);
        }

        return v0;
    }

    public void addElement(Object[] values) {
        if(this.size == this.capacity) {
            throw new GdxRuntimeException("Capacity reached, cannot add other elements");
        }

        int v1 = 0;
        Iterator v0 = this.arrays.iterator();
        while(v0.hasNext()) {
            Object v2 = v0.next();
            ((Channel)v2).add(v1, values);
            v1 += ((Channel)v2).strideSize;
        }

        ++this.size;
    }

    private Channel allocateChannel(ChannelDescriptor channelDescriptor) {
        FloatChannel v0;
        if(channelDescriptor.type == Float.TYPE) {
            v0 = new FloatChannel(this, channelDescriptor.id, channelDescriptor.count, this.capacity);
        }
        else if(channelDescriptor.type == Integer.TYPE) {
            IntChannel v0_1 = new IntChannel(this, channelDescriptor.id, channelDescriptor.count, this.capacity);
        }
        else {
            ObjectChannel v0_2 = new ObjectChannel(this, channelDescriptor.id, channelDescriptor.count, this.capacity, channelDescriptor.type);
        }

        return ((Channel)v0);
    }

    public void clear() {
        this.arrays.clear();
        this.size = 0;
    }

    private int findIndex(int id) {
        int v1 = 0;
        while(true) {
            if(v1 >= this.arrays.size) {
                break;
            }
            else if(this.arrays.items[v1].id != id) {
                ++v1;
                continue;
            }

            goto label_9;
        }

        v1 = -1;
    label_9:
        return v1;
    }

    public Channel getChannel(ChannelDescriptor descriptor) {
        Iterator v1 = this.arrays.iterator();
        do {
            if(v1.hasNext()) {
                Object v0 = v1.next();
                if(((Channel)v0).id != descriptor.id) {
                    continue;
                }
            }
            else {
                break;
            }

            goto label_8;
        }
        while(true);

        Channel v0_1 = null;
    label_8:
        return v0_1;
    }

    public void removeArray(int id) {
        this.arrays.removeIndex(this.findIndex(id));
    }

    public void removeElement(int index) {
        int v1 = this.size - 1;
        Iterator v0 = this.arrays.iterator();
        while(v0.hasNext()) {
            v0.next().swap(index, v1);
        }

        this.size = v1;
    }

    public void setCapacity(int requiredCapacity) {
        if(this.capacity != requiredCapacity) {
            Iterator v1 = this.arrays.iterator();
            while(v1.hasNext()) {
                v1.next().setCapacity(requiredCapacity);
            }

            this.capacity = requiredCapacity;
        }
    }
}

