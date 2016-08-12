// 도박중독 예방 캠페인
// 당신 곁에 우리가 있어요!
// 감당하기 힘든 어려움을 혼자 견디고 계신가요?
// 무엇을 어떻게 해야 할지 막막한가요?
// 당신의 이야기를 듣고 도움을 줄 수 있는 정보를 찾아 드립니다.
// - 한국도박문제관리센터 (국번없이 1336, 24시간)
// - KL중독관리센터 (전화상담  080-7575-535/545)
// - 사행산업통합감독위원회 불법사행산업감시신고센터 (전화상담 1588-0112)
// - 불법도박 등 범죄수익 신고 (지역번호 + 1301)

package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Circle implements Shape2D, Serializable {
    public float radius;
    public float x;
    public float y;

    public Circle(float x, float y, float radius) {
        super();
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Circle() {
        super();
    }

    public Circle(Circle circle) {
        super();
        this.x = circle.x;
        this.y = circle.y;
        this.radius = circle.radius;
    }

    public Circle(Vector2 position, float radius) {
        super();
        this.x = position.x;
        this.y = position.y;
        this.radius = radius;
    }

    public Circle(Vector2 center, Vector2 edge) {
        super();
        this.x = center.x;
        this.y = center.y;
        this.radius = Vector2.len(center.x - edge.x, center.y - edge.y);
    }

    public float area() {
        return this.radius * this.radius * 3.141593f;
    }

    public float circumference() {
        return this.radius * 6.283185f;
    }

    public boolean contains(float x, float y) {
        boolean v0;
        x -= this.x;
        y -= this.y;
        if(x * x + y * y <= this.radius * this.radius) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean contains(Circle c) {
        boolean v3;
        float v0 = this.x - c.x;
        float v1 = this.y - c.y;
        if(v0 * v0 + v1 * v1 + c.radius * c.radius <= this.radius * this.radius) {
            v3 = true;
        }
        else {
            v3 = false;
        }

        return v3;
    }

    public boolean contains(Vector2 point) {
        boolean v2;
        float v0 = this.x - point.x;
        float v1 = this.y - point.y;
        if(v0 * v0 + v1 * v1 <= this.radius * this.radius) {
            v2 = true;
        }
        else {
            v2 = false;
        }

        return v2;
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if((((Circle)o)) != this) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if(this.x == ((Circle)v0).x && this.y == ((Circle)v0).y && this.radius == ((Circle)v0).radius) {
                    goto label_3;
                }

                v1 = false;
                goto label_3;
            }

            v1 = false;
        }

    label_3:
        return v1;
    }

    public int hashCode() {
        return ((NumberUtils.floatToRawIntBits(this.radius) + 41) * 41 + NumberUtils.floatToRawIntBits(this.x)) * 41 + NumberUtils.floatToRawIntBits(this.y);
    }

    public boolean overlaps(Circle c) {
        boolean v4;
        float v1 = this.x - c.x;
        float v2 = this.y - c.y;
        float v3 = this.radius + c.radius;
        if(v1 * v1 + v2 * v2 < v3 * v3) {
            v4 = true;
        }
        else {
            v4 = false;
        }

        return v4;
    }

    public void set(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void set(Circle circle) {
        this.x = circle.x;
        this.y = circle.y;
        this.radius = circle.radius;
    }

    public void set(Vector2 position, float radius) {
        this.x = position.x;
        this.y = position.y;
        this.radius = radius;
    }

    public void set(Vector2 center, Vector2 edge) {
        this.x = center.x;
        this.y = center.y;
        this.radius = Vector2.len(center.x - edge.x, center.y - edge.y);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return this.x + "," + this.y + "," + this.radius;
    }
}

