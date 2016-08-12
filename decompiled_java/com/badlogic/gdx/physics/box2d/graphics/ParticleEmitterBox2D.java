// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.physics.box2d.graphics;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter$Particle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import java.io.BufferedReader;
import java.io.IOException;

public class ParticleEmitterBox2D extends ParticleEmitter {
    class ParticleBox2D extends Particle {
        public ParticleBox2D(ParticleEmitterBox2D arg1, Sprite sprite) {
            ParticleEmitterBox2D.this = arg1;
            super(sprite);
        }

        public void translate(float velocityX, float velocityY) {
            float v6 = 2f;
            if(velocityX * velocityX + velocityY * velocityY >= 0.001f) {
                float v0 = this.getX() + this.getWidth() / v6;
                float v1 = this.getY() + this.getHeight() / v6;
                ParticleEmitterBox2D.this.particleCollided = false;
                ParticleEmitterBox2D.this.startPoint.set(v0, v1);
                ParticleEmitterBox2D.this.endPoint.set(v0 + velocityX, v1 + velocityY);
                if(ParticleEmitterBox2D.this.world != null) {
                    ParticleEmitterBox2D.this.world.rayCast(ParticleEmitterBox2D.this.rayCallBack, ParticleEmitterBox2D.this.startPoint, ParticleEmitterBox2D.this.endPoint);
                }

                if(ParticleEmitterBox2D.this.particleCollided) {
                    this.angle = ParticleEmitterBox2D.this.normalAngle * v6 - this.angle - 180f;
                    this.angleCos = MathUtils.cosDeg(this.angle);
                    this.angleSin = MathUtils.sinDeg(this.angle);
                    velocityX *= this.angleCos;
                    velocityY *= this.angleSin;
                }

                super.translate(velocityX, velocityY);
            }
        }
    }

    private static final float EPSILON = 0.001f;
    final Vector2 endPoint;
    float normalAngle;
    boolean particleCollided;
    final RayCastCallback rayCallBack;
    final Vector2 startPoint;
    final World world;

    public ParticleEmitterBox2D(World world) {
        super();
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() {
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(normal.y, normal.x) * 57.295776f;
                return fraction;
            }
        };
        this.world = world;
    }

    public ParticleEmitterBox2D(World world, ParticleEmitter emitter) {
        super(emitter);
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() {
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(normal.y, normal.x) * 57.295776f;
                return fraction;
            }
        };
        this.world = world;
    }

    public ParticleEmitterBox2D(World world, BufferedReader reader) throws IOException {
        super(reader);
        this.startPoint = new Vector2();
        this.endPoint = new Vector2();
        this.rayCallBack = new RayCastCallback() {
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                ParticleEmitterBox2D.this.particleCollided = true;
                ParticleEmitterBox2D.this.normalAngle = MathUtils.atan2(normal.y, normal.x) * 57.295776f;
                return fraction;
            }
        };
        this.world = world;
    }

    protected Particle newParticle(Sprite sprite) {
        return new ParticleBox2D(this, sprite);
    }
}

