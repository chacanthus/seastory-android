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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.MotorJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.LongMap$Values;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import java.util.Iterator;

public final class World implements Disposable {
    protected final long addr;
    protected final LongMap bodies;
    private final Contact contact;
    private long[] contactAddrs;
    protected ContactFilter contactFilter;
    protected ContactListener contactListener;
    private final Array contacts;
    protected final Pool freeBodies;
    private final Array freeContacts;
    final Vector2 gravity;
    private final ContactImpulse impulse;
    protected final LongMap joints;
    private final Manifold manifold;
    private QueryCallback queryCallback;
    private RayCastCallback rayCastCallback;
    private Vector2 rayNormal;
    private Vector2 rayPoint;
    final float[] tmpGravity;

    static  {
        new SharedLibraryLoader().load("gdx-box2d");
    }

    public World(Vector2 gravity, boolean doSleep) {
        long v4 = 0;
        super();
        this.freeBodies = new Pool() {
            protected Body newObject() {
                return new Body(World.this, 0);
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.freeFixtures = new Pool() {
            protected Fixture newObject() {
                return new Fixture(null, 0);
            }

            protected Object newObject() {
                return this.newObject();
            }
        };
        this.bodies = new LongMap(100);
        this.fixtures = new LongMap(100);
        this.joints = new LongMap(100);
        this.contactFilter = null;
        this.contactListener = null;
        this.tmpGravity = new float[2];
        this.gravity = new Vector2();
        this.queryCallback = null;
        this.contactAddrs = new long[200];
        this.contacts = new Array();
        this.freeContacts = new Array();
        this.contact = new Contact(this, v4);
        this.manifold = new Manifold(v4);
        this.impulse = new ContactImpulse(this, v4);
        this.rayCastCallback = null;
        this.rayPoint = new Vector2();
        this.rayNormal = new Vector2();
        this.addr = this.newWorld(gravity.x, gravity.y, doSleep);
        this.contacts.ensureCapacity(this.contactAddrs.length);
        this.freeContacts.ensureCapacity(this.contactAddrs.length);
        int v0;
        for(v0 = 0; v0 < this.contactAddrs.length; ++v0) {
            this.freeContacts.add(new Contact(this, v4));
        }
    }

    public void QueryAABB(QueryCallback callback, float lowerX, float lowerY, float upperX, float upperY) {
        this.queryCallback = callback;
        this.jniQueryAABB(this.addr, lowerX, lowerY, upperX, upperY);
    }

    private void beginContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if(this.contactListener != null) {
            this.contactListener.beginContact(this.contact);
        }
    }

    public void clearForces() {
        this.jniClearForces(this.addr);
    }

    private boolean contactFilter(long fixtureA, long fixtureB) {
        boolean v0;
        boolean v3;
        if(this.contactFilter != null) {
            v3 = this.contactFilter.shouldCollide(this.fixtures.get(fixtureA), this.fixtures.get(fixtureB));
        }
        else {
            Filter v1 = this.fixtures.get(fixtureA).getFilterData();
            Filter v2 = this.fixtures.get(fixtureB).getFilterData();
            if(v1.groupIndex == v2.groupIndex && v1.groupIndex != 0) {
                if(v1.groupIndex > 0) {
                    v3 = true;
                    goto label_10;
                }
                else {
                    v3 = false;
                    goto label_10;
                }
            }

            if((v1.maskBits & v2.categoryBits) == 0 || (v1.categoryBits & v2.maskBits) == 0) {
                v0 = false;
            }
            else {
                v0 = true;
            }

            v3 = v0;
        }

    label_10:
        return v3;
    }

    public Body createBody(BodyDef def) {
        long v22 = this.jniCreateBody(this.addr, def.type.getValue(), def.position.x, def.position.y, def.angle, def.linearVelocity.x, def.linearVelocity.y, def.angularVelocity, def.linearDamping, def.angularDamping, def.allowSleep, def.awake, def.fixedRotation, def.bullet, def.active, def.gravityScale);
        Object v2 = this.freeBodies.obtain();
        ((Body)v2).reset(v22);
        this.bodies.put(((Body)v2).addr, v2);
        return ((Body)v2);
    }

    public Joint createJoint(JointDef def) {
        WheelJoint v0_10;
        long v2 = this.createProperJoint(def);
        DistanceJoint v0 = null;
        if(def.type == JointType.DistanceJoint) {
            v0 = new DistanceJoint(this, v2);
        }

        if(def.type == JointType.FrictionJoint) {
            FrictionJoint v0_1 = new FrictionJoint(this, v2);
        }

        if(def.type == JointType.GearJoint) {
            GearJoint v0_2 = new GearJoint(this, v2, def.joint1, def.joint2);
        }

        if(def.type == JointType.MotorJoint) {
            MotorJoint v0_3 = new MotorJoint(this, v2);
        }

        if(def.type == JointType.MouseJoint) {
            MouseJoint v0_4 = new MouseJoint(this, v2);
        }

        if(def.type == JointType.PrismaticJoint) {
            PrismaticJoint v0_5 = new PrismaticJoint(this, v2);
        }

        if(def.type == JointType.PulleyJoint) {
            PulleyJoint v0_6 = new PulleyJoint(this, v2);
        }

        if(def.type == JointType.RevoluteJoint) {
            RevoluteJoint v0_7 = new RevoluteJoint(this, v2);
        }

        if(def.type == JointType.RopeJoint) {
            RopeJoint v0_8 = new RopeJoint(this, v2);
        }

        if(def.type == JointType.WeldJoint) {
            WeldJoint v0_9 = new WeldJoint(this, v2);
        }

        if(def.type == JointType.WheelJoint) {
            v0_10 = new WheelJoint(this, v2);
        }

        if((((DistanceJoint)v0_10)) != null) {
            this.joints.put(((Joint)v0_10).addr, v0_10);
        }

        JointEdge v6 = new JointEdge(def.bodyB, ((Joint)v0_10));
        JointEdge v7 = new JointEdge(def.bodyA, ((Joint)v0_10));
        ((Joint)v0_10).jointEdgeA = v6;
        ((Joint)v0_10).jointEdgeB = v7;
        def.bodyA.joints.add(v6);
        def.bodyB.joints.add(v7);
        return ((Joint)v0_10);
    }

    private long createProperJoint(JointDef def) {
        long v4;
        if(def.type == JointType.DistanceJoint) {
            v4 = this.jniCreateDistanceJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.length, def.frequencyHz, def.dampingRatio);
        }
        else if(def.type == JointType.FrictionJoint) {
            v4 = this.jniCreateFrictionJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.maxForce, def.maxTorque);
        }
        else if(def.type == JointType.GearJoint) {
            v4 = this.jniCreateGearJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.joint1.addr, def.joint2.addr, def.ratio);
        }
        else if(def.type == JointType.MotorJoint) {
            v4 = this.jniCreateMotorJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.linearOffset.x, def.linearOffset.y, def.angularOffset, def.maxForce, def.maxTorque, def.correctionFactor);
        }
        else if(def.type == JointType.MouseJoint) {
            v4 = this.jniCreateMouseJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.target.x, def.target.y, def.maxForce, def.frequencyHz, def.dampingRatio);
        }
        else if(def.type == JointType.PrismaticJoint) {
            v4 = this.jniCreatePrismaticJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.localAxisA.x, def.localAxisA.y, def.referenceAngle, def.enableLimit, def.lowerTranslation, def.upperTranslation, def.enableMotor, def.maxMotorForce, def.motorSpeed);
        }
        else if(def.type == JointType.PulleyJoint) {
            v4 = this.jniCreatePulleyJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.groundAnchorA.x, def.groundAnchorA.y, def.groundAnchorB.x, def.groundAnchorB.y, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.lengthA, def.lengthB, def.ratio);
        }
        else if(def.type == JointType.RevoluteJoint) {
            v4 = this.jniCreateRevoluteJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.referenceAngle, def.enableLimit, def.lowerAngle, def.upperAngle, def.enableMotor, def.motorSpeed, def.maxMotorTorque);
        }
        else if(def.type == JointType.RopeJoint) {
            v4 = this.jniCreateRopeJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.maxLength);
        }
        else if(def.type == JointType.WeldJoint) {
            v4 = this.jniCreateWeldJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.referenceAngle, def.frequencyHz, def.dampingRatio);
        }
        else if(def.type == JointType.WheelJoint) {
            v4 = this.jniCreateWheelJoint(this.addr, def.bodyA.addr, def.bodyB.addr, def.collideConnected, def.localAnchorA.x, def.localAnchorA.y, def.localAnchorB.x, def.localAnchorB.y, def.localAxisA.x, def.localAxisA.y, def.enableMotor, def.maxMotorTorque, def.motorSpeed, def.frequencyHz, def.dampingRatio);
        }
        else {
            v4 = 0;
        }

        return v4;
    }

    void deactivateBody(Body body) {
        this.jniDeactivateBody(this.addr, body.addr);
    }

    public void destroyBody(Body body) {
        Object v7 = null;
        Array v1 = body.getJointList();
        while(v1.size > 0) {
            this.destroyJoint(body.getJointList().get(0).joint);
        }

        this.jniDestroyBody(this.addr, body.addr);
        body.setUserData(v7);
        this.bodies.remove(body.addr);
        Array v0 = body.getFixtureList();
        while(v0.size > 0) {
            this.fixtures.remove(v0.removeIndex(0).addr).setUserData(v7);
        }

        this.freeBodies.free(body);
    }

    void destroyFixture(Body body, Fixture fixture) {
        this.jniDestroyFixture(this.addr, body.addr, fixture.addr);
    }

    public void destroyJoint(Joint joint) {
        joint.setUserData(null);
        this.joints.remove(joint.addr);
        joint.jointEdgeA.other.joints.removeValue(joint.jointEdgeB, true);
        joint.jointEdgeB.other.joints.removeValue(joint.jointEdgeA, true);
        this.jniDestroyJoint(this.addr, joint.addr);
    }

    public void dispose() {
        this.jniDispose(this.addr);
    }

    private void endContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if(this.contactListener != null) {
            this.contactListener.endContact(this.contact);
        }
    }

    public boolean getAutoClearForces() {
        return this.jniGetAutoClearForces(this.addr);
    }

    public void getBodies(Array arg3) {
        arg3.clear();
        arg3.ensureCapacity(this.bodies.size);
        Values v0 = this.bodies.values();
        while(((Iterator)v0).hasNext()) {
            arg3.add(((Iterator)v0).next());
        }
    }

    public int getBodyCount() {
        return this.jniGetBodyCount(this.addr);
    }

    public int getContactCount() {
        return this.jniGetContactCount(this.addr);
    }

    public Array getContactList() {
        int v4 = this.getContactCount();
        if(v4 > this.contactAddrs.length) {
            int v3 = v4 * 2;
            this.contactAddrs = new long[v3];
            this.contacts.ensureCapacity(v3);
            this.freeContacts.ensureCapacity(v3);
        }

        if(v4 > this.freeContacts.size) {
            int v1 = this.freeContacts.size;
            int v2;
            for(v2 = 0; v2 < v4 - v1; ++v2) {
                this.freeContacts.add(new Contact(this, 0));
            }
        }

        this.jniGetContactList(this.addr, this.contactAddrs);
        this.contacts.clear();
        for(v2 = 0; v2 < v4; ++v2) {
            Object v0 = this.freeContacts.get(v2);
            ((Contact)v0).addr = this.contactAddrs[v2];
            this.contacts.add(v0);
        }

        return this.contacts;
    }

    public int getFixtureCount() {
        return this.fixtures.size;
    }

    public void getFixtures(Array arg3) {
        arg3.clear();
        arg3.ensureCapacity(this.fixtures.size);
        Values v0 = this.fixtures.values();
        while(((Iterator)v0).hasNext()) {
            arg3.add(((Iterator)v0).next());
        }
    }

    public Vector2 getGravity() {
        this.jniGetGravity(this.addr, this.tmpGravity);
        this.gravity.x = this.tmpGravity[0];
        this.gravity.y = this.tmpGravity[1];
        return this.gravity;
    }

    public int getJointCount() {
        return this.jniGetJointcount(this.addr);
    }

    public void getJoints(Array arg3) {
        arg3.clear();
        arg3.ensureCapacity(this.joints.size);
        Values v0 = this.joints.values();
        while(((Iterator)v0).hasNext()) {
            arg3.add(((Iterator)v0).next());
        }
    }

    public int getProxyCount() {
        return this.jniGetProxyCount(this.addr);
    }

    public static native float getVelocityThreshold() {
    }

    public boolean isLocked() {
        return this.jniIsLocked(this.addr);
    }

    private native void jniClearForces() {
    }

    private native long jniCreateBody(int arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, boolean arg10, boolean arg11, boolean arg12, boolean arg13, boolean arg14, float arg15) {
    }

    private native long jniCreateDistanceJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10) {
    }

    private native long jniCreateFrictionJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9) {
    }

    private native long jniCreateGearJoint(long arg1, long arg2, boolean arg3, long arg4, long arg5, float arg6) {
    }

    private native long jniCreateMotorJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9) {
    }

    private native long jniCreateMouseJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8) {
    }

    private native long jniCreatePrismaticJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, boolean arg11, float arg12, float arg13, boolean arg14, float arg15, float arg16) {
    }

    private native long jniCreatePulleyJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14) {
    }

    private native long jniCreateRevoluteJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, boolean arg9, float arg10, float arg11, boolean arg12, float arg13, float arg14) {
    }

    private native long jniCreateRopeJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8) {
    }

    private native long jniCreateWeldJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10) {
    }

    private native long jniCreateWheelJoint(long arg1, long arg2, boolean arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, boolean arg10, float arg11, float arg12, float arg13, float arg14) {
    }

    private native void jniDeactivateBody(long arg1) {
    }

    private native void jniDestroyBody(long arg1) {
    }

    private native void jniDestroyFixture(long arg1, long arg2) {
    }

    private native void jniDestroyJoint(long arg1) {
    }

    private native void jniDispose() {
    }

    private native boolean jniGetAutoClearForces() {
    }

    private native int jniGetBodyCount() {
    }

    private native int jniGetContactCount() {
    }

    private native void jniGetContactList(long[] arg1) {
    }

    private native void jniGetGravity(float[] arg1) {
    }

    private native int jniGetJointcount() {
    }

    private native int jniGetProxyCount() {
    }

    private native boolean jniIsLocked() {
    }

    private native void jniQueryAABB(float arg1, float arg2, float arg3, float arg4) {
    }

    private native void jniRayCast(float arg1, float arg2, float arg3, float arg4) {
    }

    private native void jniSetAutoClearForces(boolean arg1) {
    }

    private native void jniSetContiousPhysics(boolean arg1) {
    }

    private native void jniSetGravity(float arg1, float arg2) {
    }

    private native void jniSetWarmStarting(boolean arg1) {
    }

    private native void jniStep(float arg1, int arg2, int arg3) {
    }

    private native long newWorld(float arg1, boolean arg2) {
    }

    private void postSolve(long contactAddr, long impulseAddr) {
        this.contact.addr = contactAddr;
        this.impulse.addr = impulseAddr;
        if(this.contactListener != null) {
            this.contactListener.postSolve(this.contact, this.impulse);
        }
    }

    private void preSolve(long contactAddr, long manifoldAddr) {
        this.contact.addr = contactAddr;
        this.manifold.addr = manifoldAddr;
        if(this.contactListener != null) {
            this.contactListener.preSolve(this.contact, this.manifold);
        }
    }

    public void rayCast(RayCastCallback callback, Vector2 point1, Vector2 point2) {
        this.rayCastCallback = callback;
        this.jniRayCast(this.addr, point1.x, point1.y, point2.x, point2.y);
    }

    private boolean reportFixture(long addr) {
        boolean v0;
        if(this.queryCallback != null) {
            v0 = this.queryCallback.reportFixture(this.fixtures.get(addr));
        }
        else {
            v0 = false;
        }

        return v0;
    }

    private float reportRayFixture(long addr, float pX, float pY, float nX, float nY, float fraction) {
        float v0;
        if(this.rayCastCallback != null) {
            this.rayPoint.x = pX;
            this.rayPoint.y = pY;
            this.rayNormal.x = nX;
            this.rayNormal.y = nY;
            v0 = this.rayCastCallback.reportRayFixture(this.fixtures.get(addr), this.rayPoint, this.rayNormal, fraction);
        }
        else {
            v0 = 0f;
        }

        return v0;
    }

    public void setAutoClearForces(boolean flag) {
        this.jniSetAutoClearForces(this.addr, flag);
    }

    public void setContactFilter(ContactFilter filter) {
        boolean v0;
        this.contactFilter = filter;
        if(filter == null) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        this.setUseDefaultContactFilter(v0);
    }

    public void setContactListener(ContactListener listener) {
        this.contactListener = listener;
    }

    public void setContinuousPhysics(boolean flag) {
        this.jniSetContiousPhysics(this.addr, flag);
    }

    public void setDestructionListener(DestructionListener listener) {
    }

    public void setGravity(Vector2 gravity) {
        this.jniSetGravity(this.addr, gravity.x, gravity.y);
    }

    private native void setUseDefaultContactFilter() {
    }

    public static native void setVelocityThreshold(float arg0) {
    }

    public void setWarmStarting(boolean flag) {
        this.jniSetWarmStarting(this.addr, flag);
    }

    public void step(float timeStep, int velocityIterations, int positionIterations) {
        this.jniStep(this.addr, timeStep, velocityIterations, positionIterations);
    }
}

