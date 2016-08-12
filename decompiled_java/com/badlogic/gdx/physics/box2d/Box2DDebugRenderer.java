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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer$ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public class Box2DDebugRenderer implements Disposable {
    public final Color AABB_COLOR;
    public final Color JOINT_COLOR;
    public final Color SHAPE_AWAKE;
    public final Color SHAPE_KINEMATIC;
    public final Color SHAPE_NOT_ACTIVE;
    public final Color SHAPE_NOT_AWAKE;
    public final Color SHAPE_STATIC;
    public final Color VELOCITY_COLOR;
    private static Vector2 axis;
    private static final Array bodies;
    private boolean drawAABBs;
    private boolean drawBodies;
    private boolean drawContacts;
    private boolean drawInactiveBodies;
    private boolean drawJoints;
    private boolean drawVelocities;
    private final Vector2 f;
    private static final Array joints;
    private static final Vector2 lower;
    private final Vector2 lv;
    protected ShapeRenderer renderer;
    private static Vector2 t;
    private static final Vector2 upper;
    private final Vector2 v;
    private static final Vector2[] vertices;

    static  {
        Box2DDebugRenderer.vertices = new Vector2[1000];
        Box2DDebugRenderer.lower = new Vector2();
        Box2DDebugRenderer.upper = new Vector2();
        Box2DDebugRenderer.bodies = new Array();
        Box2DDebugRenderer.joints = new Array();
        Box2DDebugRenderer.t = new Vector2();
        Box2DDebugRenderer.axis = new Vector2();
    }

    public Box2DDebugRenderer() {
        this(true, true, false, true, false, true);
    }

    public Box2DDebugRenderer(boolean drawBodies, boolean drawJoints, boolean drawAABBs, boolean drawInactiveBodies, boolean drawVelocities, boolean drawContacts) {
        super();
        this.SHAPE_NOT_ACTIVE = new Color(0.5f, 0.5f, 0.3f, 1f);
        this.SHAPE_STATIC = new Color(0.5f, 0.9f, 0.5f, 1f);
        this.SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, 1f);
        this.SHAPE_NOT_AWAKE = new Color(0.6f, 0.6f, 0.6f, 1f);
        this.SHAPE_AWAKE = new Color(0.9f, 0.7f, 0.7f, 1f);
        this.JOINT_COLOR = new Color(0.5f, 0.8f, 0.8f, 1f);
        this.AABB_COLOR = new Color(1f, 0f, 1f, 1f);
        this.VELOCITY_COLOR = new Color(1f, 0f, 0f, 1f);
        this.f = new Vector2();
        this.v = new Vector2();
        this.lv = new Vector2();
        this.renderer = new ShapeRenderer();
        int v0;
        for(v0 = 0; v0 < Box2DDebugRenderer.vertices.length; ++v0) {
            Box2DDebugRenderer.vertices[v0] = new Vector2();
        }

        this.drawBodies = drawBodies;
        this.drawJoints = drawJoints;
        this.drawAABBs = drawAABBs;
        this.drawInactiveBodies = drawInactiveBodies;
        this.drawVelocities = drawVelocities;
        this.drawContacts = drawContacts;
    }

    public void dispose() {
        this.renderer.dispose();
    }

    private void drawAABB(Fixture fixture, Transform transform) {
        Shape v2;
        int v11 = 4;
        int v10 = 3;
        int v9 = 2;
        if(fixture.getType() == Type.Circle) {
            v2 = fixture.getShape();
            float v1 = ((CircleShape)v2).getRadius();
            Box2DDebugRenderer.vertices[0].set(((CircleShape)v2).getPosition());
            transform.mul(Box2DDebugRenderer.vertices[0]);
            Box2DDebugRenderer.lower.set(Box2DDebugRenderer.vertices[0].x - v1, Box2DDebugRenderer.vertices[0].y - v1);
            Box2DDebugRenderer.upper.set(Box2DDebugRenderer.vertices[0].x + v1, Box2DDebugRenderer.vertices[0].y + v1);
            Box2DDebugRenderer.vertices[0].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.lower.y);
            Box2DDebugRenderer.vertices[1].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.lower.y);
            Box2DDebugRenderer.vertices[v9].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.upper.y);
            Box2DDebugRenderer.vertices[v10].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.upper.y);
            this.drawSolidPolygon(Box2DDebugRenderer.vertices, v11, this.AABB_COLOR, true);
        }
        else if(fixture.getType() == Type.Polygon) {
            v2 = fixture.getShape();
            int v3 = ((PolygonShape)v2).getVertexCount();
            ((PolygonShape)v2).getVertex(0, Box2DDebugRenderer.vertices[0]);
            Box2DDebugRenderer.lower.set(transform.mul(Box2DDebugRenderer.vertices[0]));
            Box2DDebugRenderer.upper.set(Box2DDebugRenderer.lower);
            int v0;
            for(v0 = 1; v0 < v3; ++v0) {
                ((PolygonShape)v2).getVertex(v0, Box2DDebugRenderer.vertices[v0]);
                transform.mul(Box2DDebugRenderer.vertices[v0]);
                Box2DDebugRenderer.lower.x = Math.min(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.vertices[v0].x);
                Box2DDebugRenderer.lower.y = Math.min(Box2DDebugRenderer.lower.y, Box2DDebugRenderer.vertices[v0].y);
                Box2DDebugRenderer.upper.x = Math.max(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.vertices[v0].x);
                Box2DDebugRenderer.upper.y = Math.max(Box2DDebugRenderer.upper.y, Box2DDebugRenderer.vertices[v0].y);
            }

            Box2DDebugRenderer.vertices[0].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.lower.y);
            Box2DDebugRenderer.vertices[1].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.lower.y);
            Box2DDebugRenderer.vertices[v9].set(Box2DDebugRenderer.upper.x, Box2DDebugRenderer.upper.y);
            Box2DDebugRenderer.vertices[v10].set(Box2DDebugRenderer.lower.x, Box2DDebugRenderer.upper.y);
            this.drawSolidPolygon(Box2DDebugRenderer.vertices, v11, this.AABB_COLOR, true);
        }
    }

    private void drawContact(Contact contact) {
        WorldManifold v1 = contact.getWorldManifold();
        if(v1.getNumberOfContactPoints() != 0) {
            Vector2 v0 = v1.getPoints()[0];
            this.renderer.setColor(this.getColorByBody(contact.getFixtureA().getBody()));
            this.renderer.point(v0.x, v0.y, 0f);
        }
    }

    private void drawJoint(Joint joint) {
        Body v0 = joint.getBodyA();
        Body v1 = joint.getBodyB();
        Transform v9 = v0.getTransform();
        Transform v10 = v1.getTransform();
        Vector2 v7 = v9.getPosition();
        Vector2 v8 = v10.getPosition();
        Vector2 v2 = joint.getAnchorA();
        Vector2 v3 = joint.getAnchorB();
        if(joint.getType() == JointType.DistanceJoint) {
            this.drawSegment(v2, v3, this.JOINT_COLOR);
        }
        else if(joint.getType() == JointType.PulleyJoint) {
            Vector2 v5 = joint.getGroundAnchorA();
            Vector2 v6 = joint.getGroundAnchorB();
            this.drawSegment(v5, v2, this.JOINT_COLOR);
            this.drawSegment(v6, v3, this.JOINT_COLOR);
            this.drawSegment(v5, v6, this.JOINT_COLOR);
        }
        else if(joint.getType() == JointType.MouseJoint) {
            this.drawSegment(joint.getAnchorA(), joint.getAnchorB(), this.JOINT_COLOR);
        }
        else {
            this.drawSegment(v7, v2, this.JOINT_COLOR);
            this.drawSegment(v2, v3, this.JOINT_COLOR);
            this.drawSegment(v8, v3, this.JOINT_COLOR);
        }
    }

    private void drawSegment(Vector2 x1, Vector2 x2, Color color) {
        this.renderer.setColor(color);
        this.renderer.line(x1.x, x1.y, x2.x, x2.y);
    }

    private void drawShape(Fixture fixture, Transform transform, Color color) {
        int v4;
        Shape v0;
        int v9 = 2;
        if(fixture.getType() == Type.Circle) {
            Shape v1 = fixture.getShape();
            Box2DDebugRenderer.t.set(((CircleShape)v1).getPosition());
            transform.mul(Box2DDebugRenderer.t);
            this.drawSolidCircle(Box2DDebugRenderer.t, ((CircleShape)v1).getRadius(), Box2DDebugRenderer.axis.set(transform.vals[v9], transform.vals[3]), color);
        }
        else if(fixture.getType() == Type.Edge) {
            Shape v2 = fixture.getShape();
            ((EdgeShape)v2).getVertex1(Box2DDebugRenderer.vertices[0]);
            ((EdgeShape)v2).getVertex2(Box2DDebugRenderer.vertices[1]);
            transform.mul(Box2DDebugRenderer.vertices[0]);
            transform.mul(Box2DDebugRenderer.vertices[1]);
            this.drawSolidPolygon(Box2DDebugRenderer.vertices, v9, color, true);
        }
        else if(fixture.getType() == Type.Polygon) {
            v0 = fixture.getShape();
            v4 = ((PolygonShape)v0).getVertexCount();
            int v3;
            for(v3 = 0; v3 < v4; ++v3) {
                ((PolygonShape)v0).getVertex(v3, Box2DDebugRenderer.vertices[v3]);
                transform.mul(Box2DDebugRenderer.vertices[v3]);
            }

            this.drawSolidPolygon(Box2DDebugRenderer.vertices, v4, color, true);
        }
        else if(fixture.getType() == Type.Chain) {
            v0 = fixture.getShape();
            v4 = ((ChainShape)v0).getVertexCount();
            for(v3 = 0; v3 < v4; ++v3) {
                ((ChainShape)v0).getVertex(v3, Box2DDebugRenderer.vertices[v3]);
                transform.mul(Box2DDebugRenderer.vertices[v3]);
            }

            this.drawSolidPolygon(Box2DDebugRenderer.vertices, v4, color, false);
        }
    }

    private void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
        float v7 = 0f;
        float v8 = 0.314159f;
        this.renderer.setColor(color.r, color.g, color.b, color.a);
        int v9 = 0;
        while(v9 < 20) {
            this.v.set((((float)Math.cos(((double)v7)))) * radius + center.x, (((float)Math.sin(((double)v7)))) * radius + center.y);
            if(v9 == 0) {
                this.lv.set(this.v);
                this.f.set(this.v);
            }
            else {
                this.renderer.line(this.lv.x, this.lv.y, this.v.x, this.v.y);
                this.lv.set(this.v);
            }

            ++v9;
            v7 += v8;
        }

        this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
        this.renderer.line(center.x, center.y, 0f, center.x + axis.x * radius, center.y + axis.y * radius, 0f);
    }

    private void drawSolidPolygon(Vector2[] vertices, int vertexCount, Color color, boolean closed) {
        this.renderer.setColor(color.r, color.g, color.b, color.a);
        this.lv.set(vertices[0]);
        this.f.set(vertices[0]);
        int v0;
        for(v0 = 1; v0 < vertexCount; ++v0) {
            Vector2 v1 = vertices[v0];
            this.renderer.line(this.lv.x, this.lv.y, v1.x, v1.y);
            this.lv.set(v1);
        }

        if(closed) {
            this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
        }
    }

    public static Vector2 getAxis() {
        return Box2DDebugRenderer.axis;
    }

    private Color getColorByBody(Body body) {
        Color v0;
        if(!body.isActive()) {
            v0 = this.SHAPE_NOT_ACTIVE;
        }
        else if(body.getType() == BodyType.StaticBody) {
            v0 = this.SHAPE_STATIC;
        }
        else if(body.getType() == BodyType.KinematicBody) {
            v0 = this.SHAPE_KINEMATIC;
        }
        else if(!body.isAwake()) {
            v0 = this.SHAPE_NOT_AWAKE;
        }
        else {
            v0 = this.SHAPE_AWAKE;
        }

        return v0;
    }

    public boolean isDrawAABBs() {
        return this.drawAABBs;
    }

    public boolean isDrawBodies() {
        return this.drawBodies;
    }

    public boolean isDrawContacts() {
        return this.drawContacts;
    }

    public boolean isDrawInactiveBodies() {
        return this.drawInactiveBodies;
    }

    public boolean isDrawJoints() {
        return this.drawJoints;
    }

    public boolean isDrawVelocities() {
        return this.drawVelocities;
    }

    public void render(World world, Matrix4 projMatrix) {
        this.renderer.setProjectionMatrix(projMatrix);
        this.renderBodies(world);
    }

    private void renderBodies(World world) {
        this.renderer.begin(ShapeType.Line);
        if((this.drawBodies) || (this.drawAABBs)) {
            world.getBodies(Box2DDebugRenderer.bodies);
            Iterator v3 = Box2DDebugRenderer.bodies.iterator();
            while(v3.hasNext()) {
                Object v0 = v3.next();
                if(!((Body)v0).isActive() && !this.drawInactiveBodies) {
                    continue;
                }

                this.renderBody(((Body)v0));
            }
        }

        if(this.drawJoints) {
            world.getJoints(Box2DDebugRenderer.joints);
            Iterator v4 = Box2DDebugRenderer.joints.iterator();
            while(v4.hasNext()) {
                this.drawJoint(v4.next());
            }
        }

        this.renderer.end();
        if(this.drawContacts) {
            this.renderer.begin(ShapeType.Point);
            Iterator v2 = world.getContactList().iterator();
            while(v2.hasNext()) {
                this.drawContact(v2.next());
            }

            this.renderer.end();
        }
    }

    protected void renderBody(Body body) {
        Transform v3 = body.getTransform();
        Iterator v1 = body.getFixtureList().iterator();
        while(true) {
            if(!v1.hasNext()) {
                return;
            }

            Object v0 = v1.next();
            if(this.drawBodies) {
                this.drawShape(((Fixture)v0), v3, this.getColorByBody(body));
                if(this.drawVelocities) {
                    Vector2 v2 = body.getPosition();
                    this.drawSegment(v2, body.getLinearVelocity().add(v2), this.VELOCITY_COLOR);
                }
            }

            if(!this.drawAABBs) {
                continue;
            }

            this.drawAABB(((Fixture)v0), v3);
        }
    }

    public static void setAxis(Vector2 axis) {
        Box2DDebugRenderer.axis = axis;
    }

    public void setDrawAABBs(boolean drawAABBs) {
        this.drawAABBs = drawAABBs;
    }

    public void setDrawBodies(boolean drawBodies) {
        this.drawBodies = drawBodies;
    }

    public void setDrawContacts(boolean drawContacts) {
        this.drawContacts = drawContacts;
    }

    public void setDrawInactiveBodies(boolean drawInactiveBodies) {
        this.drawInactiveBodies = drawInactiveBodies;
    }

    public void setDrawJoints(boolean drawJoints) {
        this.drawJoints = drawJoints;
    }

    public void setDrawVelocities(boolean drawVelocities) {
        this.drawVelocities = drawVelocities;
    }
}

