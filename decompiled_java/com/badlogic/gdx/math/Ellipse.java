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

public class Ellipse implements Shape2D, Serializable {
    public float height;
    private static final long serialVersionUID = 7381533206532032099L;
    public float width;
    public float x;
    public float y;

    public Ellipse(float x, float y, float width, float height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Ellipse() {
        super();
    }

    public Ellipse(Circle circle) {
        super();
        this.x = circle.x;
        this.y = circle.y;
        this.width = circle.radius;
        this.height = circle.radius;
    }

    public Ellipse(Ellipse ellipse) {
        super();
        this.x = ellipse.x;
        this.y = ellipse.y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public Ellipse(Vector2 position, float width, float height) {
        super();
        this.x = position.x;
        this.y = position.y;
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vector2 position, Vector2 size) {
        super();
        this.x = position.x;
        this.y = position.y;
        this.width = size.x;
        this.height = size.y;
    }

    public float area() {
        return 3.141593f * (this.width * this.height) / 4f;
    }

    public float circumference() {
        float v2;
        float v6 = 2f;
        float v7 = 3f;
        float v0 = this.width / v6;
        float v1 = this.height / v6;
        if(v0 * v7 > v1 || v1 * v7 > v0) {
            v2 = ((float)(3.141593 * ((((double)((v0 + v1) * v7))) - Math.sqrt(((double)((v7 * v0 + v1) * (v7 * v1 + v0)))))));
        }
        else {
            v2 = ((float)(6.283185 * Math.sqrt(((double)((v0 * v0 + v1 * v1) / v6)))));
        }

        return v2;
    }

    public boolean contains(float x, float y) {
        boolean v0;
        x -= this.x;
        y -= this.y;
        if(x * x / (this.width * 0.5f * this.width * 0.5f) + y * y / (this.height * 0.5f * this.height * 0.5f) <= 1f) {
            v0 = true;
        }
        else {
            v0 = false;
        }

        return v0;
    }

    public boolean contains(Vector2 point) {
        return this.contains(point.x, point.y);
    }

    public boolean equals(Object o) {
        boolean v1 = true;
        if((((Ellipse)o)) != this) {
            if(o != null && o.getClass() == this.getClass()) {
                Object v0 = o;
                if(this.x == ((Ellipse)v0).x && this.y == ((Ellipse)v0).y && this.width == ((Ellipse)v0).width && this.height == ((Ellipse)v0).height) {
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
        return (((NumberUtils.floatToRawIntBits(this.height) + 53) * 53 + NumberUtils.floatToRawIntBits(this.width)) * 53 + NumberUtils.floatToRawIntBits(this.x)) * 53 + NumberUtils.floatToRawIntBits(this.y);
    }

    public void set(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void set(Circle circle) {
        this.x = circle.x;
        this.y = circle.y;
        this.width = circle.radius;
        this.height = circle.radius;
    }

    public void set(Ellipse ellipse) {
        this.x = ellipse.x;
        this.y = ellipse.y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public void set(Vector2 position, Vector2 size) {
        this.x = position.x;
        this.y = position.y;
        this.width = size.x;
        this.height = size.y;
    }

    public Ellipse setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Ellipse setPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
        return this;
    }

    public Ellipse setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }
}

