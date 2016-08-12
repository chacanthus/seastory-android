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

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import java.util.Arrays;

public class ParticleChannels {
    public class ColorInitializer implements ChannelInitializer {
        private static ColorInitializer instance;

        public ColorInitializer() {
            super();
        }

        public static ColorInitializer get() {
            if(ColorInitializer.instance == null) {
                ColorInitializer.instance = new ColorInitializer();
            }

            return ColorInitializer.instance;
        }

        public void init(Channel x0) {
            this.init(((FloatChannel)x0));
        }

        public void init(FloatChannel channel) {
            Arrays.fill(channel.data, 0, channel.data.length, 1f);
        }
    }

    public class Rotation2dInitializer implements ChannelInitializer {
        private static Rotation2dInitializer instance;

        public Rotation2dInitializer() {
            super();
        }

        public static Rotation2dInitializer get() {
            if(Rotation2dInitializer.instance == null) {
                Rotation2dInitializer.instance = new Rotation2dInitializer();
            }

            return Rotation2dInitializer.instance;
        }

        public void init(Channel x0) {
            this.init(((FloatChannel)x0));
        }

        public void init(FloatChannel channel) {
            int v1 = 0;
            int v0 = channel.data.length;
            while(v1 < v0) {
                channel.data[v1] = 1f;
                channel.data[v1 + 1] = 0f;
                v1 += channel.strideSize;
            }
        }
    }

    public class Rotation3dInitializer implements ChannelInitializer {
        private static Rotation3dInitializer instance;

        public Rotation3dInitializer() {
            super();
        }

        public static Rotation3dInitializer get() {
            if(Rotation3dInitializer.instance == null) {
                Rotation3dInitializer.instance = new Rotation3dInitializer();
            }

            return Rotation3dInitializer.instance;
        }

        public void init(Channel x0) {
            this.init(((FloatChannel)x0));
        }

        public void init(FloatChannel channel) {
            int v1 = 0;
            int v0 = channel.data.length;
            while(v1 < v0) {
                float[] v2 = channel.data;
                float[] v4 = channel.data;
                channel.data[v1 + 2] = 0f;
                v4[v1 + 1] = 0f;
                v2[v1] = 0f;
                channel.data[v1 + 3] = 1f;
                v1 += channel.strideSize;
            }
        }
    }

    public class ScaleInitializer implements ChannelInitializer {
        private static ScaleInitializer instance;

        public ScaleInitializer() {
            super();
        }

        public static ScaleInitializer get() {
            if(ScaleInitializer.instance == null) {
                ScaleInitializer.instance = new ScaleInitializer();
            }

            return ScaleInitializer.instance;
        }

        public void init(Channel x0) {
            this.init(((FloatChannel)x0));
        }

        public void init(FloatChannel channel) {
            Arrays.fill(channel.data, 0, channel.data.length, 1f);
        }
    }

    public class TextureRegionInitializer implements ChannelInitializer {
        private static TextureRegionInitializer instance;

        public TextureRegionInitializer() {
            super();
        }

        public static TextureRegionInitializer get() {
            if(TextureRegionInitializer.instance == null) {
                TextureRegionInitializer.instance = new TextureRegionInitializer();
            }

            return TextureRegionInitializer.instance;
        }

        public void init(Channel x0) {
            this.init(((FloatChannel)x0));
        }

        public void init(FloatChannel channel) {
            float v6 = 1f;
            float v5 = 0.5f;
            int v1 = 0;
            int v0 = channel.data.length;
            while(v1 < v0) {
                channel.data[v1] = 0f;
                channel.data[v1 + 1] = 0f;
                channel.data[v1 + 2] = v6;
                channel.data[v1 + 3] = v6;
                channel.data[v1 + 4] = v5;
                channel.data[v1 + 5] = v5;
                v1 += channel.strideSize;
            }
        }
    }

    public static final ChannelDescriptor Acceleration = null;
    public static final int AlphaOffset = 3;
    public static final ChannelDescriptor AngularVelocity2D = null;
    public static final ChannelDescriptor AngularVelocity3D = null;
    public static final int BlueOffset = 2;
    public static final ChannelDescriptor Color = null;
    public static final int CosineOffset = 0;
    public static final int CurrentLifeOffset = 0;
    public static final int GreenOffset = 1;
    public static final int HalfHeightOffset = 5;
    public static final int HalfWidthOffset = 4;
    public static final ChannelDescriptor Interpolation = null;
    public static final ChannelDescriptor Interpolation4 = null;
    public static final ChannelDescriptor Interpolation6 = null;
    public static final int InterpolationDiffOffset = 1;
    public static final int InterpolationStartOffset = 0;
    public static final ChannelDescriptor Life = null;
    public static final int LifePercentOffset = 2;
    public static final ChannelDescriptor ModelInstance = null;
    public static final ChannelDescriptor ParticleController = null;
    public static final ChannelDescriptor Position = null;
    public static final ChannelDescriptor PreviousPosition = null;
    public static final int RedOffset = 0;
    public static final ChannelDescriptor Rotation2D = null;
    public static final ChannelDescriptor Rotation3D = null;
    public static final ChannelDescriptor Scale = null;
    public static final int SineOffset = 1;
    public static final ChannelDescriptor TextureRegion = null;
    public static final int TotalLifeOffset = 1;
    public static final int U2Offset = 2;
    public static final int UOffset = 0;
    public static final int V2Offset = 3;
    public static final int VOffset = 1;
    public static final int VelocityPhiDiffOffset = 3;
    public static final int VelocityPhiStartOffset = 2;
    public static final int VelocityStrengthDiffOffset = 1;
    public static final int VelocityStrengthStartOffset = 0;
    public static final int VelocityThetaDiffOffset = 1;
    public static final int VelocityThetaStartOffset = 0;
    public static final int WOffset = 3;
    public static final int XOffset = 0;
    public static final int YOffset = 1;
    public static final int ZOffset = 2;
    private static int currentGlobalId;
    private int currentId;

    static  {
        ParticleChannels.Life = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 3);
        ParticleChannels.Position = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 3);
        ParticleChannels.PreviousPosition = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 3);
        ParticleChannels.Color = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 4);
        ParticleChannels.TextureRegion = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 6);
        ParticleChannels.Rotation2D = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 2);
        ParticleChannels.Rotation3D = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 4);
        ParticleChannels.Scale = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 1);
        ParticleChannels.ModelInstance = new ChannelDescriptor(ParticleChannels.newGlobalId(), ModelInstance.class, 1);
        ParticleChannels.ParticleController = new ChannelDescriptor(ParticleChannels.newGlobalId(), ParticleController.class, 1);
        ParticleChannels.Acceleration = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 3);
        ParticleChannels.AngularVelocity2D = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 1);
        ParticleChannels.AngularVelocity3D = new ChannelDescriptor(ParticleChannels.newGlobalId(), Float.TYPE, 3);
        ParticleChannels.Interpolation = new ChannelDescriptor(-1, Float.TYPE, 2);
        ParticleChannels.Interpolation4 = new ChannelDescriptor(-1, Float.TYPE, 4);
        ParticleChannels.Interpolation6 = new ChannelDescriptor(-1, Float.TYPE, 6);
    }

    public ParticleChannels() {
        super();
        this.resetIds();
    }

    public static int newGlobalId() {
        int v0 = ParticleChannels.currentGlobalId;
        ParticleChannels.currentGlobalId = v0 + 1;
        return v0;
    }

    public int newId() {
        int v0 = this.currentId;
        this.currentId = v0 + 1;
        return v0;
    }

    protected void resetIds() {
        this.currentId = ParticleChannels.currentGlobalId;
    }
}

