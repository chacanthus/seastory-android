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
import com.badlogic.gdx.utils.Array;

public class Body {
    protected long addr;
    private Array fixtures;
    protected Array joints;
    public final Vector2 linVelLoc;
    public final Vector2 linVelWorld;
    private final Vector2 linearVelocity;
    private final Vector2 localCenter;
    private final Vector2 localPoint;
    public final Vector2 localPoint2;
    public final Vector2 localVector;
    private final MassData massData;
    private final Vector2 position;
    private final float[] tmp;
    private final Transform transform;
    private Object userData;
    private final World world;
    private final Vector2 worldCenter;
    private final Vector2 worldVector;

    protected Body(World world, long addr) {
        super();
        this.tmp = new float[4];
        this.fixtures = new Array(2);
        this.joints = new Array(2);
        this.transform = new Transform();
        this.position = new Vector2();
        this.worldCenter = new Vector2();
        this.localCenter = new Vector2();
        this.linearVelocity = new Vector2();
        this.massData = new MassData();
        this.localPoint = new Vector2();
        this.worldVector = new Vector2();
        this.localPoint2 = new Vector2();
        this.localVector = new Vector2();
        this.linVelWorld = new Vector2();
        this.linVelLoc = new Vector2();
        this.world = world;
        this.addr = addr;
    }

    public void applyAngularImpulse(float impulse, boolean wake) {
        this.jniApplyAngularImpulse(this.addr, impulse, wake);
    }

    public void applyForce(float forceX, float forceY, float pointX, float pointY, boolean wake) {
        this.jniApplyForce(this.addr, forceX, forceY, pointX, pointY, wake);
    }

    public void applyForce(Vector2 force, Vector2 point, boolean wake) {
        this.jniApplyForce(this.addr, force.x, force.y, point.x, point.y, wake);
    }

    public void applyForceToCenter(float forceX, float forceY, boolean wake) {
        this.jniApplyForceToCenter(this.addr, forceX, forceY, wake);
    }

    public void applyForceToCenter(Vector2 force, boolean wake) {
        this.jniApplyForceToCenter(this.addr, force.x, force.y, wake);
    }

    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake) {
        this.jniApplyLinearImpulse(this.addr, impulseX, impulseY, pointX, pointY, wake);
    }

    public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake) {
        this.jniApplyLinearImpulse(this.addr, impulse.x, impulse.y, point.x, point.y, wake);
    }

    public void applyTorque(float torque, boolean wake) {
        this.jniApplyTorque(this.addr, torque, wake);
    }

    public Fixture createFixture(FixtureDef def) {
        long v18 = this.jniCreateFixture(this.addr, def.shape.addr, def.friction, def.restitution, def.density, def.isSensor, def.filter.categoryBits, def.filter.maskBits, def.filter.groupIndex);
        Object v4 = this.world.freeFixtures.obtain();
        ((Fixture)v4).reset(this, v18);
        this.world.fixtures.put(((Fixture)v4).addr, v4);
        this.fixtures.add(v4);
        return ((Fixture)v4);
    }

    public Fixture createFixture(Shape shape, float density) {
        long v8 = this.jniCreateFixture(this.addr, shape.addr, density);
        Object v0 = this.world.freeFixtures.obtain();
        ((Fixture)v0).reset(this, v8);
        this.world.fixtures.put(((Fixture)v0).addr, v0);
        this.fixtures.add(v0);
        return ((Fixture)v0);
    }

    public void destroyFixture(Fixture fixture) {
        this.world.destroyFixture(this, fixture);
        fixture.setUserData(null);
        this.world.fixtures.remove(fixture.addr);
        this.fixtures.removeValue(fixture, true);
        this.world.freeFixtures.free(fixture);
    }

    public float getAngle() {
        return this.jniGetAngle(this.addr);
    }

    public float getAngularDamping() {
        return this.jniGetAngularDamping(this.addr);
    }

    public float getAngularVelocity() {
        return this.jniGetAngularVelocity(this.addr);
    }

    public Array getFixtureList() {
        return this.fixtures;
    }

    public float getGravityScale() {
        return this.jniGetGravityScale(this.addr);
    }

    public float getInertia() {
        return this.jniGetInertia(this.addr);
    }

    public Array getJointList() {
        return this.joints;
    }

    public float getLinearDamping() {
        return this.jniGetLinearDamping(this.addr);
    }

    public Vector2 getLinearVelocity() {
        this.jniGetLinearVelocity(this.addr, this.tmp);
        this.linearVelocity.x = this.tmp[0];
        this.linearVelocity.y = this.tmp[1];
        return this.linearVelocity;
    }

    public Vector2 getLinearVelocityFromLocalPoint(Vector2 localPoint) {
        this.jniGetLinearVelocityFromLocalPoint(this.addr, localPoint.x, localPoint.y, this.tmp);
        this.linVelLoc.x = this.tmp[0];
        this.linVelLoc.y = this.tmp[1];
        return this.linVelLoc;
    }

    public Vector2 getLinearVelocityFromWorldPoint(Vector2 worldPoint) {
        this.jniGetLinearVelocityFromWorldPoint(this.addr, worldPoint.x, worldPoint.y, this.tmp);
        this.linVelWorld.x = this.tmp[0];
        this.linVelWorld.y = this.tmp[1];
        return this.linVelWorld;
    }

    public Vector2 getLocalCenter() {
        this.jniGetLocalCenter(this.addr, this.tmp);
        this.localCenter.x = this.tmp[0];
        this.localCenter.y = this.tmp[1];
        return this.localCenter;
    }

    public Vector2 getLocalPoint(Vector2 worldPoint) {
        this.jniGetLocalPoint(this.addr, worldPoint.x, worldPoint.y, this.tmp);
        this.localPoint2.x = this.tmp[0];
        this.localPoint2.y = this.tmp[1];
        return this.localPoint2;
    }

    public Vector2 getLocalVector(Vector2 worldVector) {
        this.jniGetLocalVector(this.addr, worldVector.x, worldVector.y, this.tmp);
        this.localVector.x = this.tmp[0];
        this.localVector.y = this.tmp[1];
        return this.localVector;
    }

    public float getMass() {
        return this.jniGetMass(this.addr);
    }

    public MassData getMassData() {
        this.jniGetMassData(this.addr, this.tmp);
        this.massData.mass = this.tmp[0];
        this.massData.center.x = this.tmp[1];
        this.massData.center.y = this.tmp[2];
        this.massData.I = this.tmp[3];
        return this.massData;
    }

    public Vector2 getPosition() {
        this.jniGetPosition(this.addr, this.tmp);
        this.position.x = this.tmp[0];
        this.position.y = this.tmp[1];
        return this.position;
    }

    public Transform getTransform() {
        this.jniGetTransform(this.addr, this.transform.vals);
        return this.transform;
    }

    public BodyType getType() {
        BodyType v1;
        int v0 = this.jniGetType(this.addr);
        if(v0 == 0) {
            v1 = BodyType.StaticBody;
        }
        else if(v0 == 1) {
            v1 = BodyType.KinematicBody;
        }
        else if(v0 == 2) {
            v1 = BodyType.DynamicBody;
        }
        else {
            v1 = BodyType.StaticBody;
        }

        return v1;
    }

    public Object getUserData() {
        return this.userData;
    }

    public World getWorld() {
        return this.world;
    }

    public Vector2 getWorldCenter() {
        this.jniGetWorldCenter(this.addr, this.tmp);
        this.worldCenter.x = this.tmp[0];
        this.worldCenter.y = this.tmp[1];
        return this.worldCenter;
    }

    public Vector2 getWorldPoint(Vector2 localPoint) {
        this.jniGetWorldPoint(this.addr, localPoint.x, localPoint.y, this.tmp);
        this.localPoint.x = this.tmp[0];
        this.localPoint.y = this.tmp[1];
        return this.localPoint;
    }

    public Vector2 getWorldVector(Vector2 localVector) {
        this.jniGetWorldVector(this.addr, localVector.x, localVector.y, this.tmp);
        this.worldVector.x = this.tmp[0];
        this.worldVector.y = this.tmp[1];
        return this.worldVector;
    }

    public boolean isActive() {
        return this.jniIsActive(this.addr);
    }

    public boolean isAwake() {
        return this.jniIsAwake(this.addr);
    }

    public boolean isBullet() {
        return this.jniIsBullet(this.addr);
    }

    public boolean isFixedRotation() {
        return this.jniIsFixedRotation(this.addr);
    }

    public boolean isSleepingAllowed() {
        return this.jniIsSleepingAllowed(this.addr);
    }

    private native void jniApplyAngularImpulse(float arg1, boolean arg2) {
    }

    private native void jniApplyForce(float arg1, float arg2, float arg3, float arg4, boolean arg5) {
    }

    private native void jniApplyForceToCenter(float arg1, float arg2, boolean arg3) {
    }

    private native void jniApplyLinearImpulse(float arg1, float arg2, float arg3, float arg4, boolean arg5) {
    }

    private native void jniApplyTorque(float arg1, boolean arg2) {
    }

    private native long jniCreateFixture(long arg1, float arg2) {
    }

    private native long jniCreateFixture(long arg1, float arg2, float arg3, float arg4, boolean arg5, short arg6, short arg7, short arg8) {
    }

    private native float jniGetAngle() {
    }

    private native float jniGetAngularDamping() {
    }

    private native float jniGetAngularVelocity() {
    }

    private native float jniGetGravityScale() {
    }

    private native float jniGetInertia() {
    }

    private native float jniGetLinearDamping() {
    }

    private native void jniGetLinearVelocity(float[] arg1) {
    }

    private native void jniGetLinearVelocityFromLocalPoint(float arg1, float arg2, float[] arg3) {
    }

    private native void jniGetLinearVelocityFromWorldPoint(float arg1, float arg2, float[] arg3) {
    }

    private native void jniGetLocalCenter(float[] arg1) {
    }

    private native void jniGetLocalPoint(float arg1, float arg2, float[] arg3) {
    }

    private native void jniGetLocalVector(float arg1, float arg2, float[] arg3) {
    }

    private native float jniGetMass() {
    }

    private native void jniGetMassData(float[] arg1) {
    }

    private native void jniGetPosition(float[] arg1) {
    }

    private native void jniGetTransform(float[] arg1) {
    }

    private native int jniGetType() {
    }

    private native void jniGetWorldCenter(float[] arg1) {
    }

    private native void jniGetWorldPoint(float arg1, float arg2, float[] arg3) {
    }

    private native void jniGetWorldVector(float arg1, float arg2, float[] arg3) {
    }

    private native boolean jniIsActive() {
    }

    private native boolean jniIsAwake() {
    }

    private native boolean jniIsBullet() {
    }

    private native boolean jniIsFixedRotation() {
    }

    private native boolean jniIsSleepingAllowed() {
    }

    private native void jniResetMassData() {
    }

    private native void jniSetActive(boolean arg1) {
    }

    private native void jniSetAngularDamping(float arg1) {
    }

    private native void jniSetAngularVelocity(float arg1) {
    }

    private native void jniSetAwake(boolean arg1) {
    }

    private native void jniSetBullet(boolean arg1) {
    }

    private native void jniSetFixedRotation(boolean arg1) {
    }

    private native void jniSetGravityScale(float arg1) {
    }

    private native void jniSetLinearDamping(float arg1) {
    }

    private native void jniSetLinearVelocity(float arg1, float arg2) {
    }

    private native void jniSetMassData(float arg1, float arg2, float arg3, float arg4) {
    }

    private native void jniSetSleepingAllowed(boolean arg1) {
    }

    private native void jniSetTransform(float arg1, float arg2, float arg3) {
    }

    private native void jniSetType(int arg1) {
    }

    protected void reset(long addr) {
        this.addr = addr;
        this.userData = null;
        int v0;
        for(v0 = 0; v0 < this.fixtures.size; ++v0) {
            this.world.freeFixtures.free(this.fixtures.get(v0));
        }

        this.fixtures.clear();
        this.joints.clear();
    }

    public void resetMassData() {
        this.jniResetMassData(this.addr);
    }

    public void setActive(boolean flag) {
        if(flag) {
            this.jniSetActive(this.addr, flag);
        }
        else {
            this.world.deactivateBody(this);
        }
    }

    public void setAngularDamping(float angularDamping) {
        this.jniSetAngularDamping(this.addr, angularDamping);
    }

    public void setAngularVelocity(float omega) {
        this.jniSetAngularVelocity(this.addr, omega);
    }

    public void setAwake(boolean flag) {
        this.jniSetAwake(this.addr, flag);
    }

    public void setBullet(boolean flag) {
        this.jniSetBullet(this.addr, flag);
    }

    public void setFixedRotation(boolean flag) {
        this.jniSetFixedRotation(this.addr, flag);
    }

    public void setGravityScale(float scale) {
        this.jniSetGravityScale(this.addr, scale);
    }

    public void setLinearDamping(float linearDamping) {
        this.jniSetLinearDamping(this.addr, linearDamping);
    }

    public void setLinearVelocity(float vX, float vY) {
        this.jniSetLinearVelocity(this.addr, vX, vY);
    }

    public void setLinearVelocity(Vector2 v) {
        this.jniSetLinearVelocity(this.addr, v.x, v.y);
    }

    public void setMassData(MassData data) {
        this.jniSetMassData(this.addr, data.mass, data.center.x, data.center.y, data.I);
    }

    public void setSleepingAllowed(boolean flag) {
        this.jniSetSleepingAllowed(this.addr, flag);
    }

    public void setTransform(float x, float y, float angle) {
        this.jniSetTransform(this.addr, x, y, angle);
    }

    public void setTransform(Vector2 position, float angle) {
        this.jniSetTransform(this.addr, position.x, position.y, angle);
    }

    public void setType(BodyType type) {
        this.jniSetType(this.addr, type.getValue());
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }
}

