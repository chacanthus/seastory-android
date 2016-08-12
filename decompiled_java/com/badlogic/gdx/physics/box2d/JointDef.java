// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d;

public class JointDef {
    public enum JointType {
        public static final enum JointType DistanceJoint;
        public static final enum JointType FrictionJoint;
        public static final enum JointType GearJoint;
        public static final enum JointType MotorJoint;
        public static final enum JointType MouseJoint;
        public static final enum JointType PrismaticJoint;
        public static final enum JointType PulleyJoint;
        public static final enum JointType RevoluteJoint;
        public static final enum JointType RopeJoint;
        public static final enum JointType WeldJoint;
        public static final enum JointType WheelJoint;
        private int value;

        static  {
            JointType.Unknown = new JointType("Unknown", 0, 0);
            JointType.RevoluteJoint = new JointType("RevoluteJoint", 1, 1);
            JointType.PrismaticJoint = new JointType("PrismaticJoint", 2, 2);
            JointType.DistanceJoint = new JointType("DistanceJoint", 3, 3);
            JointType.PulleyJoint = new JointType("PulleyJoint", 4, 4);
            JointType.MouseJoint = new JointType("MouseJoint", 5, 5);
            JointType.GearJoint = new JointType("GearJoint", 6, 6);
            JointType.WheelJoint = new JointType("WheelJoint", 7, 7);
            JointType.WeldJoint = new JointType("WeldJoint", 8, 8);
            JointType.FrictionJoint = new JointType("FrictionJoint", 9, 9);
            JointType.RopeJoint = new JointType("RopeJoint", 10, 10);
            JointType.MotorJoint = new JointType("MotorJoint", 11, 11);
            JointType[] v0 = new JointType[12];
            v0[0] = JointType.Unknown;
            v0[1] = JointType.RevoluteJoint;
            v0[2] = JointType.PrismaticJoint;
            v0[3] = JointType.DistanceJoint;
            v0[4] = JointType.PulleyJoint;
            v0[5] = JointType.MouseJoint;
            v0[6] = JointType.GearJoint;
            v0[7] = JointType.WheelJoint;
            v0[8] = JointType.WeldJoint;
            v0[9] = JointType.FrictionJoint;
            v0[10] = JointType.RopeJoint;
            v0[11] = JointType.MotorJoint;
            JointType.$VALUES = v0;
            v0 = new JointType[12];
            v0[0] = JointType.Unknown;
            v0[1] = JointType.RevoluteJoint;
            v0[2] = JointType.PrismaticJoint;
            v0[3] = JointType.DistanceJoint;
            v0[4] = JointType.PulleyJoint;
            v0[5] = JointType.MouseJoint;
            v0[6] = JointType.GearJoint;
            v0[7] = JointType.WheelJoint;
            v0[8] = JointType.WeldJoint;
            v0[9] = JointType.FrictionJoint;
            v0[10] = JointType.RopeJoint;
            v0[11] = JointType.MotorJoint;
            JointType.valueTypes = v0;
        }

        private JointType(String arg1, int arg2, int value) {
            super(arg1, arg2);
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static JointType valueOf(String name) {
            return Enum.valueOf(JointType.class, name);
        }

        public static JointType[] values() {
            return JointType.$VALUES.clone();
        }
    }

    public Body bodyA;
    public Body bodyB;
    public boolean collideConnected;
    public JointType type;

    public JointDef() {
        super();
        this.type = JointType.Unknown;
        this.bodyA = null;
        this.bodyB = null;
        this.collideConnected = false;
    }
}

